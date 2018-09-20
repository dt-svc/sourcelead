package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OptionsDao;
import com.bis.sourcelead.dto.OptionsDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.Option;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.OptionsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class OptionsServiceImpl implements OptionsService{

	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	OptionsDao optionsDao; 
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	UserService userService;
	
	@Autowired 
	LeaveService leaveService;
	
	@Override
	public Option saveLeaveOptions(OptionsDTO optionsDTO) throws Exception {
		
		User user = userService.getUserById(Long.parseLong(optionsDTO.getUserId()));
		Option option = null;
		if(StringUtils.isNotBlank(optionsDTO.getOptionId())) {
			option = optionsDao.getOptionsByOptionId(optionsDTO.getOptionId());
		}
		else {
			option = new Option();
			option.setCreatedDate(new Date());
			option.setCreatedBy(user.getUserCode());
			option.setOptionUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(optionsDTO.getOrgLocationId()));
		
		if (StringUtils.isNotBlank(optionsDTO.getDepartment())) {
			Department department = departmentService.getDepartmentById(Long.parseLong(optionsDTO.getDepartment()));
			option.setDepartmentId(department);
		}
		option.setOrgLocations(orgLocations);
		
		option.setCalendarMonth(optionsDTO.getCalendarMonth());
		
		option.setWeekends(optionsDTO.getWeekends());
		option.setHalfdayRequest(optionsDTO.getHalfDayRequest());
		option.setLeaveTransfer(optionsDTO.getLeaveTransfer());
		if (StringUtils.isNotBlank(optionsDTO.getWorkingHours())) {
			option.setWorkingHours(optionsDTO.getWorkingHours());
		}
		if (optionsDTO.getRoles() != null && !optionsDTO.getRoles().isEmpty()) {
			Set<LeaveRole> leaveRolesSet = getLeaveRoles(optionsDTO.getRoles(), option, user);
			option.setLeaverolesSet(leaveRolesSet);
		}
		if(optionsDTO.getWorkingHours() !=null) {
			option.setWorkingHours(optionsDTO.getWorkingHours());
		}
		
		option.setStatus("1");
		option.setUpdatedDate(new Date());
		option.setUpdatedBy(user.getUserCode());
		
		return optionsDao.saveLeaveOption(option);
	}

	
	/**
	 * To save roles to give leave permission
	 * 
	 * @param roles
	 * @param options
	 * @param user
	 */
	private Set<LeaveRole> getLeaveRoles(List<String> roles, Option option, User user) throws Exception {
		Set<LeaveRole> leaveRolesSet = new HashSet<>();
		boolean flag = leaveService.deleteAllLeaveRolesByParticularTableId(Constants.LEAVE_OPTION,option.getId());
		roles.forEach(role -> {
			LeaveRole leaveRole = new LeaveRole();
			/*if(option.getLeaverolesSet() !=null && option.getLeaverolesSet().size() >0) {
				List<LeaveRole> list = option.getLeaverolesSet().stream().filter(r->r.getRoleId().getId().longValue() == Long.parseLong(role)).collect(Collectors.toList());
				if(!list.isEmpty()) {
					leaveRole = list.iterator().next();
				}else {
					leaveRole = new LeaveRole();
				}
			}else {
				leaveRole = new LeaveRole();
			}*/
			leaveRole.setLeavesOptionId(option);
			leaveRole.setType(Constants.LEAVE_OPTION);
			try {
				Roles rol = rolesService.getRolesById(Long.parseLong(role));
				leaveRole.setRoleId(rol);
			} catch (Exception e) {
				e.printStackTrace();
			}
			leaveRole.setStatus("1");
			leaveRole.setCreatedDate(new Date());
			leaveRole.setUpdatedDate(new Date());
			leaveRole.setCreatedBy(user.getUserCode());
			leaveRole.setUpdatedBy(user.getUserCode());
			leaveRolesSet.add(leaveRole);
		});
		return leaveRolesSet;
	}

	@Override
	public List<OptionsDTO> getOptionsListByLocationId(Long orgLocationId) throws Exception {
		
		List<Option> optionsList = optionsDao.getOptionsListByLocationId(orgLocationId);
		List<OptionsDTO>  optionsDTOList = new ArrayList<OptionsDTO>();
		optionsList.forEach(op->{
			OptionsDTO optionsDTO = new OptionsDTO();
			optionsDTO.setId(op.getId());
			optionsDTO.setCalendarMonth(op.getCalendarMonth());
			if(op.getDepartmentId() !=null) {
				optionsDTO.setDepartment(op.getDepartmentId().getDepartmentName());
			}
			optionsDTO.setHalfDayRequest(op.getHalfdayRequest());
			optionsDTO.setLeaveTransfer(op.getLeaveTransfer());
			optionsDTO.setWeekends(op.getWeekends());
			if(op.getWorkingHours() !=null) {
				optionsDTO.setWorkingHours(op.getWorkingHours());
			}
			optionsDTO.setOptionUUID(op.getOptionUUID());
			optionsDTOList.add(optionsDTO);
		});
		return optionsDTOList;
	}

	@Override
	public Option getOptionsById(String optionId) throws Exception {
		return optionsDao.getOptionsById(optionId);
	}


	@Override
	public List<Long> getOptionRoleIds(Long orgLocationId) throws Exception {
		List<Option> optionsList = optionsDao.getOptionsListByLocationId(orgLocationId);
		List<Long>  rolesIdsList = new ArrayList<Long>();
		optionsList.forEach(op->{
			if (op.getLeaverolesSet() !=null && op.getLeaverolesSet().size() >0){
				Set<LeaveRole> leaveList = op.getLeaverolesSet();
				for(LeaveRole leaveRole : leaveList) {
					rolesIdsList.add(leaveRole.getRoleId().getId());
				}
			}
		});
		return rolesIdsList;
	}

}
