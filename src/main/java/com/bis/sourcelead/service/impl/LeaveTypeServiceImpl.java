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
import com.bis.sourcelead.service.LeaveService;
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
	
	@Autowired 
	LeaveService leaveService;
	
	@Override
	public LeaveType addLeaveType(LeaveTypeDTO leaveTypeDTO) throws Exception
	{
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(leaveTypeDTO.getOrgLocationId()));
		User user = userService.getUserById(Long.parseLong(leaveTypeDTO.getLoginUserId()));
		LeaveType leaveType = null;
		if(leaveTypeDTO.getLeaveTypeId() != null && !"".equalsIgnoreCase(leaveTypeDTO.getLeaveTypeId())){
			leaveType = leaveTypeDao.getLeaveTypeById(leaveTypeDTO.getLeaveTypeId());
			leaveType.setUpdatedBy(user.getUserCode());
			leaveType.setUpdatedDate(new Date());
		}else{
			leaveType = new LeaveType();
			leaveType.setCreatedBy(user.getUserCode());
			leaveType.setCreatedDate(new Date());
		}
		
		leaveType.setOrgLocations(orgLocation);
		if (leaveTypeDTO.getName() != null) {
			leaveType.setName(leaveTypeDTO.getName());
		}
		if (leaveTypeDTO.getDuration() != null)
			leaveType.setDuration(leaveTypeDTO.getDuration());
		if (leaveTypeDTO.getHours() != null)
			leaveType.setHours(leaveTypeDTO.getHours());
		if (leaveTypeDTO.getHoursType() != null)
			leaveType.setTypeOfHours(leaveTypeDTO.getHoursType());
		if (leaveTypeDTO.getBillingType() != null)
			leaveType.setLeaveType(leaveTypeDTO.getBillingType());
		if (leaveTypeDTO.getDepartment() != null && !"".equalsIgnoreCase(leaveTypeDTO.getDepartment())) {
			Department dep = departmentService.getDepartmentById(Long.parseLong(leaveTypeDTO.getDepartment()));
			leaveType.setDepartmentId(dep);
		}
		if (leaveTypeDTO.getRoles() != null && !leaveTypeDTO.getRoles().isEmpty() && leaveTypeDTO.getRoles().size() > 0) {
			Set<LeaveRole> leaveRolesSet = getLeaveRoles(leaveTypeDTO.getRoles(), leaveType, user);
			leaveType.setLeaveTypeRolesSet(leaveRolesSet);
		}
		if (leaveTypeDTO.getApplicableToUsers() != null)
			leaveType.setApplicableToUsers(leaveTypeDTO.getApplicableToUsers());
		if (leaveTypeDTO.getCarryforward() != null)
			leaveType.setLeavesCarryforward(leaveTypeDTO.getCarryforward());
		if (leaveTypeDTO.getNoOfLeavesPerYear() != null)
			leaveType.setNumbefrOfLeavesPeryear(leaveTypeDTO.getNoOfLeavesPerYear());
		if (leaveTypeDTO.getNoOfLeavesPerMonth() != null)
			leaveType.setNumbefrOfLeavesPerMonth(leaveTypeDTO.getNoOfLeavesPerMonth());
		if (leaveTypeDTO.getDescription() != null)
			leaveType.setDescription(leaveTypeDTO.getDescription());
		if (leaveTypeDTO.getLeavePolicy() != null){
			leaveType.setLeavePolicy(leaveTypeDTO.getLeavePolicy());
		}else{
			leaveType.setLeavePolicy("");
		}
		if (leaveTypeDTO.getPolicyName() != null)
			leaveType.setPolicyName(leaveTypeDTO.getPolicyName());
		if (leaveTypeDTO.getVisibilityToAllUsers() != null)
			leaveType.setVisibilityToAllUsers(leaveTypeDTO.getVisibilityToAllUsers());
		else
			leaveType.setVisibilityToAllUsers(Constants.NO);
		if (leaveTypeDTO.getLeavePolicyDescription() != null)
			leaveType.setPolicyDescription(leaveTypeDTO.getLeavePolicyDescription());
		if(leaveType !=null && "0".equals(leaveType.getStatus())) {
			leaveType.setStatus(Constants.RECORD_INACTIVE.toString());
		}else {
			leaveType.setStatus(Constants.RECORD_ACTIVE.toString());
		}
		if(leaveTypeDTO.getPeriod() != null){
			leaveType.setPeriod(leaveTypeDTO.getPeriod());
		}if(leaveTypeDTO.getLeaveEffective() != null){
			leaveType.setLeaveEffective(leaveTypeDTO.getLeaveEffective());
		}
		if(leaveTypeDTO.getExperiencedType() != null ){
			leaveType.setExperiencedType(leaveTypeDTO.getExperiencedType());
			if(leaveTypeDTO.getExperiencedDuration() != null && !"".equalsIgnoreCase(leaveTypeDTO.getExperiencedDuration())){
			    leaveType.setExperiencedDuration(Integer.parseInt(leaveTypeDTO.getExperiencedDuration()));
			}else{
				leaveType.setExperiencedDuration(0);
			}
			if(leaveTypeDTO.getExperiencedApplicableDays() != null && !"".equalsIgnoreCase(leaveTypeDTO.getExperiencedApplicableDays())){
			    leaveType.setExperiencedApplicableDays(Integer.parseInt(leaveTypeDTO.getExperiencedApplicableDays()));
			}else{
				leaveType.setExperiencedApplicableDays(0);
			}
		}
		if(leaveTypeDTO.getReasonRequired() != null){
			leaveType.setReasonRequired(leaveTypeDTO.getReasonRequired());
		}else{
			leaveType.setReasonRequired(Constants.NO);
		}
		if(leaveTypeDTO.getAttachmentRequired() != null){
			leaveType.setAttachmentRequired(leaveTypeDTO.getAttachmentRequired());
		}else{
			leaveType.setAttachmentRequired(Constants.NO);
		}
		if(leaveTypeDTO.getOverdrawLeaves() != null){
			leaveType.setOverdrawLeaves(leaveTypeDTO.getOverdrawLeaves());
		}else{
			leaveType.setOverdrawLeaves(Constants.NO);
		}
		if(leaveTypeDTO.getDonateLeave() != null){
			leaveType.setDonateLeave(leaveTypeDTO.getDonateLeave());
		}else{
			leaveType.setDonateLeave(Constants.NO);
		}
		if(leaveTypeDTO.getPriorLeaveApplying() != null){
			leaveType.setPriorLeaveApplying(leaveTypeDTO.getPriorLeaveApplying());
		}else{
			leaveType.setPriorLeaveApplying(Constants.NO);
		}
		if(leaveTypeDTO.getPriorDays() != null && !"".equalsIgnoreCase(leaveTypeDTO.getPriorDays())){
			leaveType.setPriorDays(Integer.parseInt(leaveTypeDTO.getPriorDays()));
		}else{
			leaveType.setPriorDays(0);
		}
		if(leaveTypeDTO.getLeaveDuration() != null){
			leaveType.setLeaveDuration(leaveTypeDTO.getLeaveDuration());
		}else{
			leaveType.setLeaveDuration(Constants.NO);
		}
		if(leaveTypeDTO.getNoOfDays() != null && !"".equalsIgnoreCase(leaveTypeDTO.getNoOfDays())){
			leaveType.setNoOfDays(Integer.parseInt(leaveTypeDTO.getNoOfDays()));
		}else{
			leaveType.setNoOfDays(0);
		}
		if(leaveTypeDTO.getNewJoinee() !=null && !"".equalsIgnoreCase(leaveTypeDTO.getNewJoinee())){
			 leaveType.setNewJoinee(Integer.parseInt(leaveTypeDTO.getNewJoinee()));
		}else{
			leaveType.setNewJoinee(0);
		}
		   if(leaveTypeDTO.getMaxDays() !=null && !"".equalsIgnoreCase(leaveTypeDTO.getMaxDays())){
			   leaveType.setMaxDays(Integer.parseInt(leaveTypeDTO.getMaxDays()));
		   }else{
				leaveType.setMaxDays(0);
			}
		   if(leaveTypeDTO.getMinDays() !=null && !"".equalsIgnoreCase(leaveTypeDTO.getMinDays())){
			   leaveType.setMinDays(Integer.parseInt(leaveTypeDTO.getMinDays())); 
		   }else{
				leaveType.setMinDays(0);
			}
//		    
			
         
		leaveType.setColorCode(leaveTypeDTO.getColorCode());
		leaveType = leaveTypeDao.addLeaveType(leaveType);
		return leaveType;
	}
	 
	private Set<LeaveRole> getLeaveRoles(List<String> roles, LeaveType leaveType, User user) throws Exception
	{
		Set<LeaveRole> leaveRolesSet = new HashSet<>();
		boolean flag = leaveService.deleteAllLeaveRolesByParticularTableId(Constants.LEAVE_TYPE,leaveType.getId());
		for(String role : roles) {
//		roles.forEach(role -> {
			LeaveRole leaveRole = new LeaveRole();
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
