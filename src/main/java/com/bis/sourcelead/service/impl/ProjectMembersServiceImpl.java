package com.bis.sourcelead.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.impl.ProjectsDaoImpl;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExpensesReportService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.LoginSessionController;

@Service
public class ProjectMembersServiceImpl implements ProjectMembersService{

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private ProjectMembersDao projectMembersDao;
	
	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	 @Autowired
	 public UserService userService;
	 
	 @Autowired
	 private ProjectsService projectsService; 
	 
	 @Autowired
	 EmailScenerioUtil emailScenerioUtil;
	 
	 @Autowired
	 EmailScenarioService emailScenarioService;
	 
	 @Autowired
	 UserRoleService userRoleService;
	 
	 @Autowired
	 private EmployeesService employeesService;
	 
	 @Autowired
	 OrgLocationService orgLocationService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 ConversionUtil conversionUtil;
	 
	 @Autowired
	 private ProjectApproverHierarchyService projectApproverHierarchyService;
	
	 
	 @Autowired
	 private ExpensesReportService expensesReportService;
	 
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDaoImpl.class);
	
	@Override
	public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception{
		logger.debug("In ProjectMembersServiceImpl class saveProjectMember method");
		projectMembers.setUpdatedDate(commonUtil.currentDate());
		return projectMembersDao.saveProjectMember(projectMembers);
	}

	@Override
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception {
		logger.debug("In ProjectMembersServiceImpl class updateProjectMember method...");
		projectMembers.setUpdatedDate(commonUtil.currentDate());
		return projectMembersDao.updateProjectMember(projectMembers);
	}

	@Override
	public ProjectMembers getProjectMemberById(Long id) throws Exception {
		logger.debug("In ProjectMembersServiceImpl class getProjectMember method...");
		return projectMembersDao.getProjectMemberById(id);
	}

	@Override
	public Boolean deleteProjectMemberById(Long id) throws Exception {
		logger.debug("In ProjectMembersServiceImpl class deleteProjectMember method...");
		Boolean isDeleted =  projectMembersDao.deleteProjectMemberById(id);
		return isDeleted;
	}

	@Override
	public Collection<ProjectMembers> getAllProjectMembers() throws Exception {
		logger.debug("In ProjectMembersServiceImpl class getAllProjectMembers method...");
		Collection<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectMembers();
		return projectMembersList;
	}
	
	@Override
	public Collection<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception {
		logger.debug("In ProjectMembersServiceImpl class getAllProjectMembersByProjectId method...");
		Collection<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectMembersByProjectId(id);
		return projectMembersList;
	}

	@Override
	public List<Long> getAllProjectMembersIdsByemployeeId(Long userId) throws Exception {
		
		List<Long> projectMembersList = projectMembersDao.getAllProjectMembersIdsByemployeeId(userId);
		return projectMembersList;
		
	}
	
	
	@Override
	public List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId) throws Exception {
		List<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(userId);
		return projectMembersList;
		
	}

	@Override
	public List<ProjectMembers> getAllProjectsByUserId(Long userId) {
		return projectMembersDao.getAllProjectsByUserId(userId);
	}
	
	

	@Override
	public ProjectMembers getProjectMemberByUserIdAndProjectId(Long userId, Long projectId) throws Exception {
		return projectMembersDao.getProjectMemberByUserIdAndProjectId(userId,projectId);
	}
	
	@Override
	public ProjectMembers getProjectMemberByEmpIdAndProjectId(Long empId, Long projectId) throws Exception {
		return projectMembersDao.getProjectMemberByEmpIdAndProjectId(empId,projectId);
	}
	
	@Override
	public Integer getAllProjectMembersCountByProjectId(Long id) throws Exception {
		
		Integer projectMembersCount = projectMembersDao.getAllProjectMembersCountByProjectId(id);
		
		return projectMembersCount;
	}
	

	@Override
	public Integer updateProjectMemberDeletedReason(Long projectMemberId ,String reason) {
		return projectMembersDao.updateProjectMemberDeletedReason(projectMemberId,reason);
	}

	@Override
	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String filterValue) throws Exception {
		List<ProjectMembers> allProjectsList = null;
		if(filterValue.equals("All")){
			 allProjectsList = projectMembersDao.getAllProjectsByUserId(userId);
		}
		if(filterValue.equals("Active")){
			 allProjectsList = projectMembersDao.getAllProjectsByUserIdAndStatus(userId,Constants.MEMBER_ACTIVE);
		}
		if(filterValue.equals("In-Active")){
			allProjectsList = projectMembersDao.getAllProjectsByUserIdAndStatus(userId,Constants.MEMBER_IN_ACTIVE);
		}
		for (ProjectMembers projectMembers : allProjectsList) {
			projectMembers.setProjectName(projectMembers.getProjects().getProjectName());
			 ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMembers.getId());
			 OrgLocations orglo = orgLocationService.getOrgLocations( projectMembers.getProjects().getOrgLocationsId().getId());	
			 if(pmh == null){
				 pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMembers.getId());
			 }
			 if(pmh != null && pmh.getProjectAssignedDate()!=null){
				 projectMembers.setStartDate(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),pmh.getProjectAssignedDate()));
             }
        	 if(pmh != null && pmh.getProjectTerminationDate()!=null){
        		 projectMembers.setEndDate(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),pmh.getProjectTerminationDate()));
        	 }
        	 
        	 /*projectMembers.setStartDate(com.bis.sourcelead.constants.Constants.DF_YMD.format(projectMembers.getProjectAssignedDate()));
			if (projectMembers.getProjectEndDate() != null) {
				projectMembers.setEndDate(com.bis.sourcelead.constants.Constants.DF_YMD.format(projectMembers.getProjectEndDate()));
			}*/
			if (projectMembers.getProjects().getEndDate() != null) {
				projectMembers.setProjectEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),projectMembers.getProjects().getEndDate()));
			}
	        Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(userId, projectMembers.getProjects().getId(), "DRAFT");
	        Float draftTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId, projectMembers.getProjects().getId(), "DRAFT");
	        Float draftTimesheetsSubmissionsCount =  draftTimesheetsSubmissionsCountAsEmployee + draftTimesheetsSubmissionsCountAsApprover;
	        projectMembers.setDraftHrs(draftTimesheetsSubmissionsCount.toString());

	        Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(userId, projectMembers.getProjects().getId(), "SUBMITTED");
	        Float submittedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId, projectMembers.getProjects().getId(), "SUBMITTED");
	        Float submittedTimesheetsSubmissionsCount =  submittedTimesheetsSubmissionsCountAsEmployee + submittedTimesheetsSubmissionsCountAsApprover;
			projectMembers.setSubmittedHrs(submittedTimesheetsSubmissionsCount.toString());
			
		    Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(userId, projectMembers.getProjects().getId(), "APPROVED");
		    Float approvalTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId, projectMembers.getProjects().getId(), "APPROVED");
	        Float approvalTimesheetsSubmissionsCount =  approvalTimesheetsSubmissionsCountAsEmployee + approvalTimesheetsSubmissionsCountAsApprover;
			projectMembers.setApprovedHrs(approvalTimesheetsSubmissionsCount.toString());
			
			Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(userId, projectMembers.getProjects().getId(), "REJECTED");
		    Float rejectedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId, projectMembers.getProjects().getId(), "REJECTED");
		    Float rejectedTimesheetsSubmissionsCount =  rejectedTimesheetsSubmissionsCountAsEmployee + rejectedTimesheetsSubmissionsCountAsApprover;
		    projectMembers.setRejectedHrs(rejectedTimesheetsSubmissionsCount.toString());

		}
		return allProjectsList;
	}
	@Override
	public Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId) {
		return projectMembersDao.updateEndDateOfProjectMemberId(endDate,projectMemberId);
	}

	@Override
	public Integer getEmployeeAssignedProjectsCount(Long orgRelationId, Long orgLocationId , Long projectId) {
		return projectMembersDao.getEmployeeAssignedProjectsCount(orgRelationId, orgLocationId ,projectId);
	}

	@Override
	public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId)
			throws Exception {
		// TODO Auto-generated method stub
		return projectMembersDao.getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(userId);
	}
	
	@Override
	public List<ProjectMembers> getAllTerminatedProjectMembersIdsByTimesheet()throws Exception {
		return projectMembersDao.getAllTerminatedProjectMembersIdsByTimesheet();
	}

	@Override
	public List<ProjectMembers> getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(Long projectId,Primary isManuallyTerminated) throws Exception {
		return projectMembersDao.getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(projectId,isManuallyTerminated);
	}

	/**
	 * Assign Employee to project by  project id	 
	 * @param projectId
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @param mailtoMembers
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	
	@Override
	public String storeAssignEmployeeByProjectIdEmployeeIdStartDateEndaDate(Long projectId, Long employeeId,
			String startDate, String endDate, String mailtoMembers, String loginOrgCode, User user,OrgLocations loginOrgLocation) throws Exception {
		
		  if(projectId != null){
			  
				Projects projects = projectsService.getProjectById(projectId);
				ProjectMembers pm = projectMembersDao.getProjectMemberByEmpIdAndProjectId(employeeId, projectId);
				if(pm == null){
					pm = new ProjectMembers();
					pm.setCreatedDate(new Date());
					pm.setCreatedBy(user.getUserCode());
				}else{
					pm.setIsManuallyTerminated(null);
					pm.setTerminateReason(null);
				}
				
				Employees emp = employeesService.getEmployee(employeeId.toString());
			 	pm.setEmployee(emp);
				pm.setProjects(projects);
				/*String pattern = "dd-MM-yyyy";
	            SimpleDateFormat format = new SimpleDateFormat(pattern);
				SimpleDateFormat format=loginOrgLocation.getLocFullFormDateFormatter();*/
	            HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
				
				ProjectMembersHistory pmh = new ProjectMembersHistory();
				pmh.setProjectMemberId(pm);
				pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
	            if(StringUtils.isNotEmpty(startDate)){
	            	 Date currentStartDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),startDate);
	            	 pmh.setProjectAssignedDate(currentStartDate);
	            }
	            else{
	            	pmh.setProjectAssignedDate(projects.getStartDate());
	            }
	            if(StringUtils.isNotEmpty(endDate)){
	            	if(endDate != null && !"--".equalsIgnoreCase(endDate)){
	            		Date currentEndDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),endDate);
	            		pmh.setProjectTerminationDate(currentEndDate);
	            	}
	            	
	            }else{
	            	 pmh.setProjectTerminationDate(projects.getEndDate());
	            }
	            pmh.setCreatedDate(new Date());
				pmh.setCreatedBy(user.getUserCode());
				pmh.setUpdatedDate(new Date());
				pmh.setUpdatedBy(user.getUserCode());
				pmhList.add(pmh);
	            
	            pm.setStatus(Constants.MEMBER_ACTIVE);
				pm.setUpdatedDate(new Date());
				pm.setUpdatedBy(user.getUserCode());
				pm.setMemberHistoryList(pmhList);
				saveProjectMember(pm);
				
				/**
			     * This method for send Email to team member while assign employee from project dashboard
			     */
				ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.getProjectApproverByProjectId(projects.getId());
				
				if(!isNullOrEmpty(mailtoMembers) && ("YES".equals(mailtoMembers))){
					if(user.getId() != projectApproverHierarchy.getApproverUserId().getId()){
						sendEmailToTeamMembers(projects, emp, user, projectApproverHierarchy, loginOrgLocation, loginOrgCode);
					}
					
				}
		  }
			return null;
	}

	 
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public Set<ProjectMembers> prepareProjectMembersListByJson(JSONObject jsonObj,HttpServletRequest request,Projects projects) throws Exception {
		 
		HashSet<ProjectMembers> projectMemberList = new HashSet<ProjectMembers>();
		String teamMembersList = "";
		OrgLocations locations=orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
		//SimpleDateFormat simpleDateFormat=locations.getLocFullFormDateFormatter();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
//		String pattern = "dd-MM-yyyy";
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
		if (jsonObj.has("employeeMaxCount")){
			String employeeMaxCount = jsonObj.getString("employeeMaxCount");
			if(!jsonObj.getString("employeeMaxCount").isEmpty()){
					int empMaxCount = Integer.parseInt(employeeMaxCount);
					for (int c = 1; c < empMaxCount; c++) {
						
						ProjectMembers pm = new ProjectMembers();
						if(StringUtils.isNotEmpty(jsonObj.getString("employeeId"+c))){
							Employees emp = employeesService.getEmployee(jsonObj.getString("employeeId"+c));
						    if(emp != null){
						    	pm.setEmployee(emp);
						    	
						        HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
									
								ProjectMembersHistory pmh = new ProjectMembersHistory();
								pmh.setProjectMemberId(pm);
								pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
					            if(StringUtils.isNotEmpty(jsonObj.getString("employeestartDate"+c))){
					            	 Date currentStartDate = commonUtil.orgLocationDateFormat(locations.getDateFormat(),jsonObj.getString("employeestartDate"+c));
					            	 pmh.setProjectAssignedDate(currentStartDate);
					            }else{
					            	
					            	if(jsonObj.getString("StartDate") != null && jsonObj.getString("StartDate") != "" && jsonObj.getString("StartDate") != "undefined"){
					            		Date startDate = commonUtil.orgLocationDateFormat(locations.getDateFormat(),jsonObj.getString("StartDate"));
					            		pmh.setProjectAssignedDate(startDate);
					            	}
					            }
					            
					            if(StringUtils.isNotEmpty(jsonObj.getString("employeeendDate"+c))){
					            	Date currentEndDate = commonUtil.orgLocationDateFormat(locations.getDateFormat(),jsonObj.getString("employeeendDate"+c));
					            	pmh.setProjectTerminationDate(currentEndDate);
					            }
					            else{
					            	 if(!jsonObj.getString("EndDate").isEmpty()){
						            		Date endDate = commonUtil.orgLocationDateFormat(locations.getDateFormat(),jsonObj.getString("EndDate"));
						            		pmh.setProjectTerminationDate(endDate);
						             }
						    	}

					            pmh.setCreatedDate(new Date());
								pmh.setCreatedBy(user.getUserCode());
								pmh.setUpdatedDate(new Date());
								pmh.setUpdatedBy(user.getUserCode());
								pmhList.add(pmh);
								
								pm.setProjects(projects);
								pm.setStatus(Constants.MEMBER_ACTIVE);
								pm.setCreatedDate(new Date());
								pm.setUpdatedDate(new Date());
								pm.setCreatedBy(user.getUserCode());
								pm.setUpdatedBy(user.getUserCode());
								pm.setMemberHistoryList(pmhList);
								/*projectMembersService.saveProjectMember(pm);*/
								projectMemberList.add(pm);
								
								/*commonUtil.storeTimesheetSubmissionRecords(emp.getUser());*/
								StringBuilder nameStringBuilder=new StringBuilder();
								pm.setEmpName(nameStringBuilder.append(emp.getUser().getFirstName()).append(" ").append(emp.getUser().getLastName()).toString());
								/*String teamMember = "";
								teamMember = emp.getUser().getFirstName()+" "+emp.getUser().getLastName();
								teamMembersList = teamMembersList+teamMember+"<br/>";*/
								
						    }
						}
				    }
			 }
	    }
		
		if(jsonObj.has("level1Manager")){
	    	if(jsonObj.getString("level1Manager") != null && !jsonObj.getString("level1Manager").isEmpty()){
	    		String levelManager[] = jsonObj.getString("level1Manager").split(",");
	    		Boolean isAdmin = userRoleService.isUserAdmin(Long.parseLong(levelManager[0]));
				if(!isAdmin){
					projectMemberList.add(saveManagersAsProjectMembers(jsonObj, projects, user, levelManager[0]));
				}
	    	}
    	}
		
		if(jsonObj.has("level2Manager")){ 	
			if(!jsonObj.getString("level2Manager").isEmpty()){
				String levelManager = jsonObj.getString("level2Manager");
				Boolean isAdmin = userRoleService.isUserAdmin(Long.parseLong(levelManager));
				if(!isAdmin){
					projectMemberList.add(saveManagersAsProjectMembers(jsonObj, projects, user, levelManager));
				}
				
			}
		 }
		return projectMemberList;
	}
	
	/**
	* This method for Save Manager As ProjectMember
	* @return
	* @throws Exception
	* @author  venkatKesav
	* @since   2016-07-25
	*/
	public ProjectMembers saveManagersAsProjectMembers(JSONObject jsonObj,Projects projects, User user, String levelManager){
			String pattern = "dd-MM-yyyy";
	        SimpleDateFormat format = new SimpleDateFormat(pattern);
		
			ProjectMembers pm = new ProjectMembers();
			HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
			ProjectMembersHistory pmh = new ProjectMembersHistory();
			
			pmh.setProjectMemberId(pm);
			pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
	        try {
				if(jsonObj.getString("StartDate") != null && jsonObj.getString("StartDate") != "" && jsonObj.getString("StartDate") != "undefined"){
					Date startDate = format.parse(jsonObj.getString("StartDate"));
					pmh.setProjectAssignedDate(startDate);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				if(!jsonObj.getString("EndDate").isEmpty()){
				    Date endDate = format.parse(jsonObj.getString("EndDate"));
				    pmh.setProjectTerminationDate(endDate);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        pmh.setCreatedDate(new Date());
			pmh.setCreatedBy(user.getUserCode());
			pmh.setUpdatedDate(new Date());
			pmh.setUpdatedBy(user.getUserCode());
			pmhList.add(pmh);
			
	    	User approverUserId = new User();
			try {
				approverUserId.setId(Long.parseLong(levelManager));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Employees emp = null;
			try {
				emp = employeesService.getEmployeeByUserId(approverUserId.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	pm.setProjects(projects);
	    	pm.setEmployee(emp);
			pm.setStatus(Constants.MEMBER_ACTIVE);
			pm.setCreatedDate(new Date());
			pm.setUpdatedDate(new Date());
			pm.setCreatedBy(user.getUserCode());
			pm.setUpdatedBy(user.getUserCode());
			pm.setMemberHistoryList(pmhList);
			return pm;
	}

	@Override
	public List<ProjectMembers> getAllProjectMembersAssignedOrTerminateDateisGreaterThanProjectEndDate(Long projectId) {
		return projectMembersDao.getAllProjectMembersAssignedOrTerminateDateisGreaterThanProjectEndDate(projectId);
	}

	@Override
	public List<ProjectMembers> getAllProjectsAssigendToUserByIdAndStatus(Long userId, String status) throws Exception {
		return projectMembersDao.getAllProjectsByUserIdAndStatus(userId, status);
	}

	@Override
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndEmpUserId(Long locationsId, Long empUserId) {
		return projectMembersDao.getProjectMembersListByOrgLocationsIdAndEmpUserId(locationsId, empUserId);
	}

	@Override
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectIdAndStratDateAndEndDateAndStatus(
			String status, String startDate, String endDate, Long projectId) {
		 
		return projectMembersDao.getProjectMembersListByOrgLocationsIdAndProjectIdAndStratDateAndEndDateAndStatus(status, startDate, endDate, projectId);
		
	}

	@Override
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Long locationsId,Long approverUserId) {
		return projectMembersDao.getProjectMembersListByOrgLocationsIdAndApproverUserId(locationsId,approverUserId);
	}

	@Override
	public List<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(
			String timesheetFrequency, Date startDate, Date endDate, Long clientId, String status, Long locationsId,
			Long approverUserId) {
		return projectMembersDao.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(timesheetFrequency, startDate, endDate, clientId, status, locationsId, approverUserId);
	}
	
	@Override
	public List<ProjectMembers> getAllProjectMembersByProjectIdsList(List<Long> projectIds) {
		return projectMembersDao.getAllProjectMembersByProjectIdsList(projectIds);
	}

	 /**
	  * This method for send Email to team member
	  * @return
	  * @throws Exception
	  * @author  venkatKesav
	  * @since   2016-07-22
	  */
	
	private void sendEmailToTeamMembers(Projects projects, Employees emp, User loginUser, ProjectApproverHierarchy projectApproverHierarchy, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
	  	
		User projectManager = userService.getUserById(projectApproverHierarchy.getApproverUserId().getId());
	  	
	  	 StringBuilder fromNameSB=new StringBuilder();
	  	 StringBuilder loginURLSB=new StringBuilder();
	  	 StringBuilder nameSB=new StringBuilder();
	     EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(nameSB.append(emp.getUser().getFirstName()).append(" ").append(emp.getUser().getLastName()).toString());
		 emailMessage.setLastName(emp.getUser().getLastName());
		 emailMessage.setToEmail(emp.getUser().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(fromNameSB.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 //setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
		 //globalVarsMap.put("loginUserLastName",loginUser.getLastName());
		 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
		 globalVarsMap.put("managerLastName",projectManager.getLastName());
		 globalVarsMap.put("employeeFirstName",emailMessage.getFirstName());
		 //globalVarsMap.put("employeeLastName",emailMessage.getLastName());
		 globalVarsMap.put("projectName", projects.getProjectName());
		 globalVarsMap.put("orgName",loginOrgLocation.getOrgLocationName());
		 globalVarsMap.put("loginURL",loginURLSB.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 
		 
		//calling the sendNotification method of emailScenerioUtil
		EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		//String url = null;
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getUser(), loginOrgLocation, notificationScenario);
	}

	@Override
	public List<ProjectMembers> getAllProjectsByUserIdForCandidatesHotList(Long userId) throws Exception {

		return projectMembersDao.getAllProjectsByUserIdForCandidatesHotList(userId)  ;
		
		
	}

	@Override
	public Collection<ProjectMembers> getAllProjectMembersByProjectIdAndStatus(Long projectId, String status)
			throws Exception {
		Collection<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectMembersByProjectIdAndStatus(projectId,status);
		return projectMembersList;
	}

	@Override
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserId(Date startDate, Date endDate,
			Long locationsId, Long approverUserId) {
		return projectMembersDao.getProjectMembersListByOrgLocationsIdAndApproverUserId(startDate,endDate,locationsId,approverUserId);
	}

	@Override
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndApproverUserIdForExpenseReports(Date startDate, Date endDate,Long locationsId, Long approverUserId) {
		return projectMembersDao.getProjectMembersListByOrgLocationsIdAndApproverUserIdForExpenseReports(startDate,endDate,locationsId,approverUserId);
	}
	
	@Override
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndProjectId(Date startDate, Date endDate, Long locationsId, Long projectId) throws Exception
	{
		return projectMembersDao.getProjectMembersListByOrgLocationsIdAndProjectId(startDate,endDate,locationsId,projectId);
	}
	
	@Override
	public Map<String, Float> getTotalHours(Long projectMemberId, Date startDate, Date endDate) throws Exception
	{
		return projectMembersDao.getTotalHours(projectMemberId, startDate, endDate);
	}

	@Override
	public List<ProjectMembers> getAllProjectsByOrgLocationId(Long orglocationId) throws Exception {
		return projectMembersDao.getAllProjectsByOrgLocationId(orglocationId);
	}


	@Override
	public List<ProjectMembers> getAllAssignedProjectsForUser(Long userId, Long orgLocationId) throws Exception
	{
		List<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectsByUserId(userId);
		Float finalExpenseAmount = 0f;
		OrgLocations orglo = orgLocationService.getOrgLocations(orgLocationId);
		//SimpleDateFormat simpleDateFormat = orglo.getLocFullFormDateFormatter();
		for (ProjectMembers projectMember : projectMembersList) {
			try {
				ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersAllHistoryByProjectMemberId(projectMember.getId());
				projectMember.setProjectName(projectMember.getProjects().getProjectName());
				if (pmh.getProjectAssignedDate() != null) {
					projectMember.setProjectAssignedDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),pmh.getProjectAssignedDate()));
				}
				if (pmh.getProjectTerminationDate() != null) {
					projectMember.setProjectEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),pmh.getProjectTerminationDate()));
				}
				if (pmh.getStatus() != null) {
					projectMember.setProjectStatus(pmh.getStatus());
				}
				if(projectMember.getProjects() != null){
					projectMember.setProjectUUID(projectMember.getProjects().getProjectUUID());
				}
				
				if(projectMember.getProjects() != null){
					projectMember.setMemberEnddate(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),projectMember.getProjects().getEndDate()));
				}
				
				if (pmh.getProjectAssignedDate() != null
						&& pmh.getProjectTerminationDate() != null) {
					Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getId(), "DRAFT");
					projectMember.setDraftHrs(String.valueOf(draftTimesheetsSubmissionsCountAsEmployee));
					Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getId(), "SUBMITTED");
					projectMember.setSubmittedHrs(String.valueOf(submittedTimesheetsSubmissionsCountAsEmployee));
					Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getId(), "APPROVED");
					projectMember.setApprovedHrs(String.valueOf(approvalTimesheetsSubmissionsCountAsEmployee));
					Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(projectMember.getEmployee().getUser().getId(), projectMember.getProjects().getId(), "REJECTED");
					projectMember.setRejectedHrs(String.valueOf(rejectedTimesheetsSubmissionsCountAsEmployee));
				}
				if (projectMember.getProjects() != null) {
					if (projectMember.getProjects().getStartDate() != null) {
						List<Expenses> expensesClaimendAmount = expensesReportService.getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(), null, null);
						if (expensesClaimendAmount != null && !expensesClaimendAmount.isEmpty()) {
							for (Expenses exp : expensesClaimendAmount) {
								finalExpenseAmount = finalExpenseAmount + exp.getTotalAmount();
								projectMember.setUserExpensesClaimedAmount(finalExpenseAmount);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return projectMembersList;
	}
	
	
	@Override
	public List<ProjectMembers> getAllProjectsbyEmployeeId(Long employeeId)
	{
		return projectMembersDao.getAllProjectsbyEmployeeId(employeeId);
	}
	
	@Override
	public List<ProjectMembers> getProjectsAssignedMembersForExpenses(Long projectId) throws Exception {
		Collection<ProjectMembers> projectMemberList = projectMembersDao.getAllProjectMembersByProjectId(projectId);

		projectMemberList.forEach(pm -> {
			StringBuilder sb = new StringBuilder();
			if (pm.getEmployee().getUser().getLastName() != null) {
				sb.append(pm.getEmployee().getUser().getFirstName()).append(" ")
						.append(pm.getEmployee().getUser().getLastName());
			} else {
				sb.append(pm.getEmployee().getUser().getFirstName());
			}
			pm.setEmpName(sb.toString());
		});
		return (List<ProjectMembers>) projectMemberList;
	}

}