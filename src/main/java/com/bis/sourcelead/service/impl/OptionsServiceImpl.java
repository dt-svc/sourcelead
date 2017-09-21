package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.bis.sourcelead.service.OptionsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserService;

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
	
	@Override
	public Option saveLeaveOptions(OptionsDTO optionsDTO) throws Exception {
		Option options = new Option();
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(optionsDTO.getOrgLocationId()));
		User user = userService.getUserById(Long.parseLong(optionsDTO.getUserId()));
		if (StringUtils.isNotBlank(optionsDTO.getDepartment())) {
			Department department = departmentService.getDepartmentById(Long.parseLong(optionsDTO.getDepartment()));
			options.setDepartmentId(department);
		}
		options.setOrgLocations(orgLocations);
		options.setCalendarMonth(optionsDTO.getCalendarMonth());
		options.setWeekends(optionsDTO.getCalendarMonth());
		options.setHalfdayRequest(optionsDTO.getHalfDayRequest());
		options.setLeaveTransfer(optionsDTO.getLeaveTransfer());
		if (StringUtils.isNotBlank(optionsDTO.getWorkingHours())) {
			options.setWeekends(optionsDTO.getWorkingHours());
		}
		if (optionsDTO.getRoles() != null && !optionsDTO.getRoles().isEmpty()) {
			Set<LeaveRole> leaveRolesSet = getLeaveRoles(optionsDTO.getRoles(), options, user);
			options.setLeaverolesSet(leaveRolesSet);
		}
		if(optionsDTO.getWorkingHours() !=null) {
			options.setWorkingHours(optionsDTO.getWorkingHours());
		}
		options.setStatus("1");
		options.setCreatedDate(new Date());
		options.setUpdatedDate(new Date());
		options.setCreatedBy(user.getUserCode());
		options.setUpdatedBy(user.getUserCode());
		
		return optionsDao.saveLeaveOption(options);
	}

	
	/**
	 * To save roles to give leave permission
	 * 
	 * @param roles
	 * @param options
	 * @param user
	 */
	private Set<LeaveRole> getLeaveRoles(List<String> roles, Option options, User user) throws Exception {
		Set<LeaveRole> leaveRolesSet = new HashSet<>();
		roles.forEach(role -> {
			LeaveRole leaveRoles = new LeaveRole();
			leaveRoles.setLeavesOptionId(options);
			leaveRoles.setType(Constants.LEAVE_OPTION);
			try {
				Roles rol = rolesService.getRolesById(Long.parseLong(role));
				leaveRoles.setRoleId(rol);
			} catch (Exception e) {
				e.printStackTrace();
			}
			leaveRoles.setStatus("1");
			leaveRoles.setCreatedDate(new Date());
			leaveRoles.setUpdatedDate(new Date());
			leaveRoles.setCreatedBy(user.getUserCode());
			leaveRoles.setUpdatedBy(user.getUserCode());
			leaveRolesSet.add(leaveRoles);
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
			optionsDTOList.add(optionsDTO);
		});
		return optionsDTOList;
	}

	@Override
	public Option getOptionsById(String optionId) throws Exception {
		return optionsDao.getOptionsById(optionId);
	}

}
