package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LeaveTypeDao;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserService;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {
	private static final Logger logger = LoggerFactory.getLogger(LeaveTypeServiceImpl.class);
	@Autowired
	LeaveTypeDao leaveTypeDao;
	@Autowired
	OrgLocationService orgLocationService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	RolesService rolesService;
	@Autowired
	UserService userService;
	
	@Override
	public LeaveType addLeaveType(LeaveTypeDTO leaveTypeDTO) throws Exception
	{
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(leaveTypeDTO.getOrgLocationId()));
		User user = userService.getUserById(Long.parseLong(leaveTypeDTO.getLoginUserId()));
		LeaveType leaveType = null;
		if(StringUtils.isNotBlank(leaveTypeDTO.getLeaveTypeId())){
			leaveType = leaveTypeDao.getLeaveTypeById(leaveTypeDTO.getLeaveTypeId());
			leaveType.setUpdatedBy(user.getUserCode());
			leaveType.setUpdatedDate(new Date());
		}else{
			leaveType = new LeaveType();
			leaveType.setCreatedBy(user.getUserCode());
			leaveType.setCreatedDate(new Date());
		}
		
		leaveType.setOrgLocations(orgLocation);
		if (StringUtils.isNotBlank(leaveTypeDTO.getName())) {
			leaveType.setName(leaveTypeDTO.getName());
		}
		if (StringUtils.isNotBlank(leaveTypeDTO.getDuration()))
			leaveType.setDuration(leaveTypeDTO.getDuration());
		if (StringUtils.isNotBlank(leaveTypeDTO.getHours()))
			leaveType.setHours(leaveTypeDTO.getHours());
		if (StringUtils.isNotBlank(leaveTypeDTO.getHoursType()))
			leaveType.setTypeOfHours(leaveTypeDTO.getHoursType());
		if (StringUtils.isNotBlank(leaveTypeDTO.getBillingType()))
			leaveType.setLeaveType(leaveTypeDTO.getBillingType());
		if (StringUtils.isNotBlank(leaveTypeDTO.getDepartment())) {
			Department dep = departmentService.getDepartmentById(Long.parseLong(leaveTypeDTO.getDepartment()));
			leaveType.setDepartmentId(dep);
		}
		if (leaveTypeDTO.getRoles() != null && !leaveTypeDTO.getRoles().isEmpty() && leaveTypeDTO.getRoles().size() > 0) {
			Set<LeaveRole> leaveRolesSet = getLeaveRoles(leaveTypeDTO.getRoles(), leaveType, user);
			leaveType.setLeaveTypeRolesSet(leaveRolesSet);
		}
		if (StringUtils.isNotBlank(leaveTypeDTO.getApplicableToUsers()))
			leaveType.setApplicableToUsers(leaveTypeDTO.getApplicableToUsers());
		if (StringUtils.isNotBlank(leaveTypeDTO.getCarryforward()))
			leaveType.setLeavesCarryforward(leaveTypeDTO.getCarryforward());
		if (StringUtils.isNotBlank(leaveTypeDTO.getNoOfLeavesPerYear()))
			leaveType.setNumbefrOfLeavesPeryear(leaveTypeDTO.getNoOfLeavesPerYear());
		if (StringUtils.isNotBlank(leaveTypeDTO.getNoOfLeavesPerMonth()))
			leaveType.setNumbefrOfLeavesPerMonth(leaveTypeDTO.getNoOfLeavesPerMonth());
		if (StringUtils.isNotBlank(leaveTypeDTO.getDescription()))
			leaveType.setDescription(leaveTypeDTO.getDescription());
		if (StringUtils.isNotBlank(leaveTypeDTO.getLeavePolicy()))
			leaveType.setLeavePolicy(leaveTypeDTO.getLeavePolicy());
		if (StringUtils.isNotBlank(leaveTypeDTO.getPolicyName()))
			leaveType.setPolicyName(leaveTypeDTO.getPolicyName());
		if (StringUtils.isNotBlank(leaveTypeDTO.getVisibilityToAllUsers()))
			leaveType.setVisibilityToAllUsers(leaveTypeDTO.getVisibilityToAllUsers());
		if (StringUtils.isNotBlank(leaveTypeDTO.getLeavePolicyDescription()))
			leaveType.setPolicyDescription(leaveTypeDTO.getLeavePolicyDescription());
		leaveType.setStatus(Constants.RECORD_ACTIVE.toString());
		leaveType.setColorCode(leaveTypeDTO.getColorCode());
		leaveType = leaveTypeDao.addLeaveType(leaveType);
		return leaveType;
	}
	
	private Set<LeaveRole> getLeaveRoles(List<String> roles, LeaveType leaveType, User user) throws Exception
	{
		Set<LeaveRole> leaveRolesSet = new HashSet<>();
		for(String role : roles) {
//		roles.forEach(role -> {
			LeaveRole leaveRole = null;
			Long l = Long.parseLong(role);
			if(leaveType.getLeaveTypeRolesSet() !=null && leaveType.getLeaveTypeRolesSet().size() >0) {
				List<LeaveRole> list = leaveType.getLeaveTypeRolesSet().stream().filter(r->r.getRoleId().getId().longValue() == l.longValue()).collect(Collectors.toList());
				if(!list.isEmpty()) {
					leaveRole = list.iterator().next();
				}else {
					leaveRole = new LeaveRole();
				}
			}else {
				leaveRole = new LeaveRole();
			}

			leaveRole.setLeaveTypeid(leaveType);
			leaveRole.setType(Constants.LEAVE_TYPE);
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
		}
		return leaveRolesSet;
	}

	@Override
	public List<LeaveType> getLeaveTypeListByLocationId(String orgLocationId)
	{
		return leaveTypeDao.getLeaveTypeListByLocationId(orgLocationId);
	}
	
	@Override
	public List<LeaveType> getLeaveTypeListByLocationIdAndStatus(String orgLocationId)
	{
		return leaveTypeDao.getLeaveTypeListByLocationIdAndStatus(orgLocationId);
	}

	@Override
	public LeaveType getLeaveTypeById(String leaveTypeId) throws Exception
	{
		LeaveType type = leaveTypeDao.getLeaveTypeById(leaveTypeId);
		if(type.getCreatedBy() != null){
			User createdUser = userService.getUserByUserCode(type.getCreatedBy());
			StringBuilder sb = new StringBuilder();
			if(StringUtils.isNotBlank(createdUser.getFirstName()) && StringUtils.isNotBlank(createdUser.getLastName()))
				type.setCreatedUser(sb.append(createdUser.getFirstName()).append(" ").append(createdUser.getLastName()).toString());
			else
				type.setCreatedUser(createdUser.getFirstName());
		}
		
		if(type.getUpdatedBy() != null){
			User updatedUser = userService.getUserByUserCode(type.getUpdatedBy());
			StringBuilder sb = new StringBuilder();
			if(StringUtils.isNotBlank(updatedUser.getFirstName()) && StringUtils.isNotBlank(updatedUser.getLastName()))
				type.setUpdatedUser(sb.append(updatedUser.getFirstName()).append(" ").append(updatedUser.getLastName()).toString());
			else
				type.setUpdatedUser(updatedUser.getFirstName());
		}
		return type;
	}
}
