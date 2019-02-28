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
import com.bis.sourcelead.entity.AssociateGrades;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.LeaveTypeHistory;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AssociateGradesService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

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
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	private ProjectTasksService projectTasksService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	AssociateGradesService associateGradesService;
	
	@Override
	public LeaveType addLeaveType(LeaveTypeDTO leaveTypeDTO) throws Exception
	{
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.parseLong(leaveTypeDTO.getOrgLocationId()));
		User user = userService.getUserById(Long.parseLong(leaveTypeDTO.getLoginUserId()));
		LeaveType leaveType = null;
		boolean flag = false;
		if(leaveTypeDTO.getLeaveTypeId() > 0){
			leaveType = leaveTypeDao.getLeaveTypeById(leaveTypeDTO.getLeaveTypeId());
			leaveType.setUpdatedBy(user.getUserCode());
			leaveType.setUpdatedDate(new Date());
		}else{
			leaveType = new LeaveType();
			leaveType.setLeaveTypeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			leaveType.setCreatedBy(user.getUserCode());
			leaveType.setCreatedDate(new Date());
			flag = true;
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
		if (leaveTypeDTO.getDepartment() != null) {
//			Department dep = departmentService.getDepartmentById(Long.parseLong(leaveTypeDTO.getDepartment()));
			leaveType.setDepartmentIds(leaveTypeDTO.getDepartment());
		}else {
			leaveType.setDepartmentIds(null);
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
		
		if(leaveTypeDTO.getEffectiveGender() != null){
			leaveType.setEffectiveGender(leaveTypeDTO.getEffectiveGender());
		}
		
		if(leaveTypeDTO.getIncludeHoliday() != null){
			leaveType.setIncludeHolidays(leaveTypeDTO.getIncludeHoliday());
		}else{
			leaveType.setIncludeHolidays(Constants.NO);
		}
		
		if(leaveTypeDTO.getIncludeInPayRoll() != null){
			leaveType.setIncludeInPayroll(leaveTypeDTO.getIncludeInPayRoll());
		}else{
			leaveType.setIncludeInPayroll(Constants.NO);
		}
		
		if(leaveTypeDTO.getIncludeWeekoff() != null){
			leaveType.setIncludeWeekoff(leaveTypeDTO.getIncludeWeekoff());
		}else{
			leaveType.setIncludeWeekoff(Constants.NO);
		}
		
		if(leaveTypeDTO.getVisibleInBalanceLeaves() != null){
			leaveType.setVisibleInBalanceLeaves(leaveTypeDTO.getVisibleInBalanceLeaves());
		}else{
			leaveType.setVisibleInBalanceLeaves(Constants.NO);
		}
		
		if(leaveTypeDTO.getLeaveAllowability() != null){
			leaveType.setAllowability(leaveTypeDTO.getLeaveAllowability());
		}
		
		if(leaveTypeDTO.getLeveEmpType() != null && leaveTypeDTO.getLeveEmpType().size() > 0){
			leaveType.setLeaveEmpType(String.join(",", leaveTypeDTO.getLeveEmpType()));
		}else{
			leaveType.setLeaveEmpType("");
		}
		
		if(leaveTypeDTO.getLeaveEncashment() != null){
			leaveType.setLeaveEncashment(leaveTypeDTO.getLeaveEncashment());
			leaveType.setLeaveEncashMinDays(leaveTypeDTO.getLeaveEncashMinDays());
			leaveType.setLeaveEncashMaxDays(leaveTypeDTO.getLeaveEncashMaxDays());
		}
		
		leaveType.setMaxDaysToCarryforward(leaveTypeDTO.getNoOfdaysToCarryforward());
		
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
		if(leaveTypeDTO.getAccuralPolicy() != null) {
			leaveType.setAccuralPolicy(leaveTypeDTO.getAccuralPolicy());
		}else {
			leaveType.setAccuralPolicy(Constants.NO);
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
			   leaveType.setMaxDays(Float.parseFloat(leaveTypeDTO.getMaxDays()));
		   }else{
				leaveType.setMaxDays(0f);
			}
		   if(leaveTypeDTO.getMinDays() !=null && !"".equalsIgnoreCase(leaveTypeDTO.getMinDays())){
			   leaveType.setMinDays(Float.parseFloat(leaveTypeDTO.getMinDays())); 
		   }else{
				leaveType.setMinDays(0f);
			}
		
		if(leaveTypeDTO.getNoOfDaysPerPeriod() != null) {
			leaveType.setNoOfDaysPerPeriod(leaveTypeDTO.getNoOfDaysPerPeriod());
		}
		
		if(leaveTypeDTO.getFresherPeriod() != null) {
			leaveType.setNewJoinPerPeriod(leaveTypeDTO.getFresherPeriod());
		}
		
		if(leaveTypeDTO.getExperiencedPeriod() != null) {
			leaveType.setExperiencedPerPeriod(leaveTypeDTO.getExperiencedPeriod());
		}
		
		if(leaveTypeDTO.getAutoApprove() !=null && !"".equalsIgnoreCase(leaveTypeDTO.getAutoApprove())){
			leaveType.setAutoApprove(leaveTypeDTO.getAutoApprove());
		}else{
			leaveType.setAutoApprove(Constants.NO);
		}
		
		if(leaveTypeDTO.getAutoApproveType() !=null && !"".equalsIgnoreCase(leaveTypeDTO.getAutoApproveType())){
			leaveType.setAutoApproveType(leaveTypeDTO.getAutoApproveType());
		}else{
			leaveType.setAutoApproveType("");
		}
		
		if(leaveTypeDTO.getAutoApproveTypeList() !=null && leaveTypeDTO.getAutoApproveTypeList().size() > 0){
			leaveType.setAutoApproveTypeList(leaveTypeDTO.getAutoApproveTypeList().stream().collect(Collectors.joining(","))); 
		}else{
			leaveType.setAutoApproveType("");
		}
		
		if(leaveTypeDTO.getTimeSheetAutofill() !=null && !"".equalsIgnoreCase(leaveTypeDTO.getTimeSheetAutofill())){
			leaveType.setTimeSheetAutofill(leaveTypeDTO.getTimeSheetAutofill());
		}else{
			leaveType.setTimeSheetAutofill(Constants.NO);
		}
         
		leaveType.setColorCode(leaveTypeDTO.getColorCode());
		
		leaveType = leaveTypeDao.addLeaveType(leaveType);
		storeLeaveTypeHistory(leaveType);
		if (leaveTypeDTO.getRoles() == null || leaveTypeDTO.getRoles().isEmpty()) {
			leaveService.deleteAllLeaveRolesByParticularTableId(Constants.LEAVE_TYPE,leaveType.getId());
		} 
		/*if(flag == true) {
			addOrUpdateLeaveTypeToprojectTask(orgLocation.getId(),leaveTypeDTO,leaveType,user,flag);
		}else if(flag == false) {
			addOrUpdateLeaveTypeToprojectTask(orgLocation.getId(),leaveTypeDTO,leaveType,user,flag);
		}*/
		return leaveType;
	}
	 
	public void storeLeaveTypeHistory(LeaveType leaveType)
	{
		LeaveTypeHistory lt= new LeaveTypeHistory();
		lt.setLeaveTypeId(leaveType.getId());
		lt.setOrgLocations(leaveType.getOrgLocations().getId());
		if(leaveType.getDepartmentId()!=null)
			lt.setDepartmentId(leaveType.getDepartmentId().getId());
		lt.setName(leaveType.getName());
		lt.setDuration(leaveType.getDuration());
		lt.setHours(leaveType.getHours());
		lt.setTypeOfHours(leaveType.getTypeOfHours());
		lt.setLeaveType(leaveType.getLeaveType());
		lt.setApplicableToUsers(leaveType.getApplicableToUsers());
		lt.setLeavesCarryforward(leaveType.getLeavesCarryforward());
		lt.setNumbefrOfLeavesPerMonth(leaveType.getNumbefrOfLeavesPerMonth());
		lt.setNumbefrOfLeavesPeryear(leaveType.getNumbefrOfLeavesPeryear());
		lt.setDescription(leaveType.getDescription());
		lt.setStatus(leaveType.getStatus());
		lt.setColorCode(leaveType.getColorCode());
		lt.setDepartmentIds(leaveType.getDepartmentIds());
		lt.setCreatedDate(leaveType.getCreatedDate());
		lt.setCreatedBy(leaveType.getCreatedBy());
		lt.setUpdatedBy(leaveType.getUpdatedBy());
		lt.setUpdatedDate(leaveType.getUpdatedDate());
		lt.setLeaveTypeUUID(leaveType.getLeaveTypeUUID());
		lt.setDeleteReason(leaveType.getDeleteReason());
		lt.setLeavePolicy(leaveType.getLeavePolicy());
		lt.setPolicyDescription(leaveType.getPolicyDescription());
		lt.setPolicyName(leaveType.getPolicyName());
		lt.setVisibilityToAllUsers(leaveType.getVisibilityToAllUsers());
		lt.setPeriod(leaveType.getPeriod());
		lt.setLeaveEffective(leaveType.getLeaveEffective());
		lt.setNewJoinee(leaveType.getNewJoinee());
		lt.setExperiencedType(leaveType.getExperiencedType());
		lt.setExperiencedDuration(leaveType.getExperiencedDuration());
		lt.setExperiencedApplicableDays(leaveType.getExperiencedApplicableDays());
		lt.setReasonRequired(leaveType.getReasonRequired());
		lt.setAttachmentRequired(leaveType.getAttachmentRequired());
		lt.setOverdrawLeaves(leaveType.getOverdrawLeaves());
		lt.setDonateLeave(leaveType.getDonateLeave());
		lt.setPriorLeaveApplying(leaveType.getPriorLeaveApplying());
		lt.setPriorDays(leaveType.getPriorDays());
		lt.setLeaveDuration(leaveType.getLeaveDuration());
		lt.setMinDays(leaveType.getMinDays());
		lt.setMaxDays(leaveType.getMaxDays());
		lt.setNoOfDays(leaveType.getNoOfDays());
		lt.setNoOfDaysPerPeriod(leaveType.getNoOfDaysPerPeriod());
		lt.setAutoApprove(leaveType.getAutoApprove());
		lt.setAutoApproveType(leaveType.getAutoApproveType());
		lt.setAutoApproveTypeList(leaveType.getAutoApproveTypeList());
		lt.setTimeSheetAutofill(leaveType.getTimeSheetAutofill());
		lt.setEffectiveGender(leaveType.getEffectiveGender());
		lt.setIncludeHolidays(leaveType.getIncludeHolidays());
		lt.setVisibleInBalanceLeaves(leaveType.getVisibleInBalanceLeaves());
		lt.setLeaveEmpType(leaveType.getLeaveEmpType());
		lt.setAllowability(leaveType.getAllowability());
		lt.setMaxDaysToCarryforward(leaveType.getMaxDaysToCarryforward());
		lt.setIncludeInPayroll(leaveType.getIncludeInPayroll());
		lt.setLeaveEncashment(leaveType.getLeaveEncashment());
		lt.setLeaveEncashMaxDays(leaveType.getLeaveEncashMaxDays());
		lt.setLeaveEncashMinDays(leaveType.getLeaveEncashMinDays());
		lt.setAccuralPolicy(leaveType.getAccuralPolicy());
		lt.setNewJoinee(leaveType.getNewJoinee());
		lt.setExperiencedPerPeriod(leaveType.getExperiencedPerPeriod());
		leaveTypeDao.storeLeaveTypeHistory(lt);
	}
	
		private void deleteLeaveRoleIds(long leaveTypeId) {
		// TODO Auto-generated method stub
		
	}

		private void addOrUpdateLeaveTypeToprojectTask(Long orgLocationId, LeaveTypeDTO leaveTypeDTO, LeaveType leaveType, User user, boolean flag) throws Exception {
			List<Projects> projectsList = projectsService.getAllProjectByOrgLocationsId(orgLocationId);
			if(projectsList != null && projectsList.size() > 0) {
				for(Projects projects : projectsList) {
					if(flag == true) {	
						ProjectTasks  tasks  = null;
						tasks  = new ProjectTasks();
						LookUpData taskType = lookUpDataService.getTasksTypeByUniqueKey(Constants.GENERAL);
						if(projects.getProjectShortCode() != null) {
							tasks.setGeneratedId(commonUtil.getProjectTaskGenaratedId(projects));
						}
						tasks.setProjectTasksUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
						tasks.setTaskName(leaveTypeDTO.getName());
						tasks.setDescription(leaveTypeDTO.getName());
						if(leaveTypeDTO.getBillingType().equalsIgnoreCase("Non Billable")) {
							tasks.setBillableType("Non-Billable");
						}else {
							tasks.setBillableType("Billable");
						}
						tasks.setProjectId(projects);
						tasks.setTaskType(taskType);
						tasks.setLeaveType(leaveType);
						LookUpData taskStatus = lookUpDataService.getTasksById(Constants.TaskStatusID);
						tasks.setTaskStatus(taskStatus);
						tasks.setStatus(String.valueOf(Constants.CANDIDATE_ACTIVE));
						tasks.setCreatedBy(user.getUserCode());
						tasks.setCreatedDate(new Date());
						tasks.setUpdatedBy(user.getUserCode());
						tasks.setUpdatedDate(new Date());
						projectTasksService.addTasks(tasks);
					}else {
						 ProjectTasks tasks = projectTasksService.getProjectTaskByleaveTypeId(leaveTypeDTO.getLeaveTypeId(),projects.getId());
						 if(tasks != null) {
							 tasks.setTaskName(leaveTypeDTO.getName());
							 tasks.setDescription(leaveTypeDTO.getName());
							 if(leaveTypeDTO.getBillingType().equalsIgnoreCase("Non Billable")) {
								tasks.setBillableType("Non-Billable");
							}else {
								tasks.setBillableType("Billable");
							}
							 projectTasksService.updateTasks(tasks);
						 }
				}
			
			}
		}
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
	public LeaveType getLeaveTypeById(Long leaveTypeId) throws Exception
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

	@Override
	public LeaveType getLeaveTypeByUUID(String leaveTypeUUID) throws Exception
	{
		LeaveType type = leaveTypeDao.getLeaveTypeByUUID(leaveTypeUUID);
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
		
		if(StringUtils.isNotBlank(type.getAutoApproveTypeList())){
			StringBuilder sb = new StringBuilder();
			if("Grade".equalsIgnoreCase(type.getAutoApproveType())){
				String[] appr = type.getAutoApproveTypeList().split(",");
				for(int i=0; i<appr.length; i++){
					AssociateGrades grade = associateGradesService.getAssociateGradeById(Long.parseLong(appr[i]));
					sb.append(grade.getGradeName()).append(",");
				}
			}else if("Role".equalsIgnoreCase(type.getAutoApproveType())){
				String[] roleList = type.getAutoApproveTypeList().split(",");
				for(int i=0; i<roleList.length; i++){
					Roles role = rolesService.getRolesById(Long.parseLong(roleList[i]));
					sb.append(role.getRoleName()).append(",");
				}
			}
			type.setAutoApproveTypeListNames(sb.toString().replaceAll("\\s*,\\s*$", ""));
		}
		
		return type;
		
	}

	@Override
	public List<ProjectTasks> getProjectTaskByorgLocationIdAndLeaveTypeId(Long orgLocationId, Long leaveTypeId) {
		return leaveTypeDao.getProjectTaskByorgLocationIdAndLeaveTypeId(orgLocationId,leaveTypeId);		
	}

	@Override
	public List<LeaveType> getLeaveTypesByTypeList(List<String> typeList, OrgLocations orgLocation)
	{
		return leaveTypeDao.getLeaveTypesByTypeList(typeList,orgLocation);
	}

	@Override
	public LeaveType getLeaveTypeByLeaveTypeName(String leaveTypeName,OrgLocations orgLocation)
	{
		return leaveTypeDao.getLeaveTypeByLeaveTypeName(leaveTypeName,orgLocation);
	}

	@Override
	public List<String> getLeaveTypeNameListByLocationId(Long orgLocationId)
	{
		return leaveTypeDao.getLeaveTypeNameListByLocationId(orgLocationId);
	}
}
