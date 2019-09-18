package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.ProjectMembersHistoryDao;
import com.bis.sourcelead.dao.impl.ProjectsDaoImpl;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.MarginSheetReportDTO;
import com.bis.sourcelead.dto.ProjectAssignedMembersDTO;
import com.bis.sourcelead.dto.ProjectMembersDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadRoles;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ExpensesReportService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadRolesService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ProjectMembersServiceImpl implements ProjectMembersService{

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${pmsUrl}")
	private String pmsUrl;
	
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
	 
	 @Autowired
	 CandidateService candidateService;
	 
	 @Autowired
	 ProjectClientService projectClientService;
	 
	 @Autowired
	 ProjectMembersHistoryDao projectMembersHistoryDao;
	 
	 @Autowired
	 OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 ClientService clientService;
	 
	 @Autowired
	 OrgSpocService orgSpocService;
	 
	 @Autowired
	 RolesService rolesService;
	 
	 @Autowired
	 OrgLicenseSubscriptionService orgLicenseSubscriptionService;
	 
	 @Autowired
	 SourceleadRolesService sourceleadRolesService;
	 
	 @Autowired
	 TabsService tabService;
	 
	 @Autowired
	 SourceleadActionsService sourceleadActionsService;
	 
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
	public List<ProjectMembers> getAllProjectMembersByProjectId(Long id) throws Exception {
		logger.debug("In ProjectMembersServiceImpl class getAllProjectMembersByProjectId method...");
		List<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectMembersByProjectId(id);
		return projectMembersList;
	}

	@Override
	public List<Long> getAllProjectMembersIdsByemployeeId(Long userId) throws Exception {
		
		List<Long> projectMembersList = projectMembersDao.getAllProjectMembersIdsByemployeeId(userId);
		return projectMembersList;
		
	}
	
	
	@Override
	public List<ProjectMembers> getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(Long userId,OrgLocations orgLocations) throws Exception {
		List<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(userId,orgLocations);
		return projectMembersList;
		
	}

	/*@Override
	public List<ProjectMembers> getAllProjectsByUserId(Long userId,Long orgLocationId) {
		return projectMembersDao.getAllProjectsByUserId(userId,orgLocationId);
	}*/
	
	

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

	/*
	 *  For User Tab Approved And Rejected Hours
	 */
	@Override
	public List<ProjectMembers> getAllProjectsByUserIdAndStatus(Long userId, String filterValue,Long orgLocationId) throws Exception {
		List<ProjectMembers> allProjectsList = null;
		if(filterValue.equals("All")){
			 allProjectsList = projectMembersDao.getAllProjectsByUserId(userId,orgLocationId,null);
		}
		if(filterValue.equals("Active")){
			 allProjectsList = projectMembersDao.getAllProjectsByUserIdAndStatus(userId,Constants.MEMBER_ACTIVE,orgLocationId);
		}
		if(filterValue.equals("In-Active")){
			allProjectsList = projectMembersDao.getAllProjectsByUserIdAndStatus(userId,Constants.MEMBER_IN_ACTIVE,orgLocationId);
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
	        Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(projectMembers.getMasterCandidateId().getId(), projectMembers.getProjects().getId(), "DRAFT");
	        Float draftTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId, projectMembers.getProjects().getId(), "DRAFT");
	        Float draftTimesheetsSubmissionsCount =  draftTimesheetsSubmissionsCountAsEmployee + draftTimesheetsSubmissionsCountAsApprover;
	        projectMembers.setDraftHrs(draftTimesheetsSubmissionsCount.toString());

	        Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(projectMembers.getMasterCandidateId().getId(), projectMembers.getProjects().getId(), "SUBMITTED");
	        Float submittedTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId, projectMembers.getProjects().getId(), "SUBMITTED");
	        Float submittedTimesheetsSubmissionsCount =  submittedTimesheetsSubmissionsCountAsEmployee + submittedTimesheetsSubmissionsCountAsApprover;
			projectMembers.setSubmittedHrs(submittedTimesheetsSubmissionsCount.toString());
			
		    Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(projectMembers.getMasterCandidateId().getId(), projectMembers.getProjects().getId(), "APPROVED");
		    Float approvalTimesheetsSubmissionsCountAsApprover = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId, projectMembers.getProjects().getId(), "APPROVED");
	        Float approvalTimesheetsSubmissionsCount =  approvalTimesheetsSubmissionsCountAsEmployee + approvalTimesheetsSubmissionsCountAsApprover;
			projectMembers.setApprovedHrs(approvalTimesheetsSubmissionsCount.toString());
			
			Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(projectMembers.getMasterCandidateId().getId(), projectMembers.getProjects().getId(), "REJECTED");
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
					projectMemberList.add(saveManagersAsProjectMembers(jsonObj, projects, user, levelManager[0],locations));
				}
	    	}
    	}
		
		if(jsonObj.has("level2Manager")){ 	
			if(!jsonObj.getString("level2Manager").isEmpty()){
				String levelManager = jsonObj.getString("level2Manager");
				Boolean isAdmin = userRoleService.isUserAdmin(Long.parseLong(levelManager));
				if(!isAdmin){
					projectMemberList.add(saveManagersAsProjectMembers(jsonObj, projects, user, levelManager,locations));
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
	public ProjectMembers saveManagersAsProjectMembers(JSONObject jsonObj,Projects projects, User user, String levelManager,OrgLocations orgLocations){
			/*String pattern = "dd-MM-yyyy";
	        SimpleDateFormat format = new SimpleDateFormat(pattern);
		*/
			ProjectMembers pm = new ProjectMembers();
			HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
			ProjectMembersHistory pmh = new ProjectMembersHistory();
			
			pmh.setProjectMemberId(pm);
			pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
	        try {
				if(jsonObj.getString("StartDate") != null && jsonObj.getString("StartDate") != "" && jsonObj.getString("StartDate") != "undefined"){
					Date startDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),jsonObj.getString("StartDate"));
					pmh.setProjectAssignedDate(startDate);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				if(!jsonObj.getString("EndDate").isEmpty()){
				    Date endDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),jsonObj.getString("EndDate"));
				    pmh.setProjectTerminationDate(endDate);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
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
	public List<ProjectMembers> getAllProjectsAssigendToUserByIdAndStatus(Long userId, String status, Long orgLocationId) throws Exception {
		return projectMembersDao.getAllProjectsByUserIdAndStatus(userId, status,orgLocationId);
	}

	@Override
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndEmpUserId(Long locationsId, Long empUserId) {
		return projectMembersDao.getProjectMembersListByOrgLocationsIdAndEmpUserId(locationsId, empUserId);
	}

	@Override
	public List<ProjectMembers> getProjectMembersListByOrgLocationsIdAndCandidateId(Long locationsId,Long orgId, Long candidateId) {
		return projectMembersDao.getProjectMembersListByOrgLocationsIdAndCandidateId(locationsId, orgId, candidateId);
	}
	
	@Override
	public List<ProjectMembers> getProjectMembersListByOrgIdAndMasterCandidateId(Long orgId, Long masterCandidateId) {
		return projectMembersDao.getProjectMembersListByOrgIdAndMasterCandidateId(orgId, masterCandidateId);
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
	public ResponseList<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(
			String timesheetFrequency, Date startDate, Date endDate, Long clientId, Long projectId, String status, Long locationsId,
			Long approverUserId,String searchValue,String projectName, int start, int length,String timesheetFor,String isGlobalUser) {
		return projectMembersDao.getProjectMembersListByTimesheetFrequencyOrgLocationsIdAndApproverUserId(timesheetFrequency, startDate, endDate, clientId,projectId, status, locationsId, approverUserId,searchValue,projectName,start,length,timesheetFor,isGlobalUser);
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
		 globalVarsMap.put("LoginUserFirstName",loginUser.getFirstName());
		 //globalVarsMap.put("loginUserLastName",loginUser.getLastName());
		 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
		 globalVarsMap.put("managerLastName",projectManager.getLastName());
		 globalVarsMap.put("EmployeeFirstName",emailMessage.getFirstName());
		 //globalVarsMap.put("employeeLastName",emailMessage.getLastName());
		 globalVarsMap.put("ProjectName", projects.getProjectName());
		 globalVarsMap.put("OrgName",loginOrgLocation.getOrgLocationName());
		 globalVarsMap.put("loginURL",loginURLSB.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		 //calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.MAIL_TEAM_MEMBERS,new ArrayList<EmailAttachment>());

		 
		 
		//calling the sendNotification method of emailScenerioUtil
		EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		//String url = null;
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getUser(), loginOrgLocation, notificationScenario,new HashMap<String,String>());
	}

	@Override
	public List<ProjectMembers> getAllProjectsByUserIdForCandidatesHotList(Long userId) throws Exception {

		return projectMembersDao.getAllProjectsByUserIdForCandidatesHotList(userId)  ;
		
		
	}

	@Override
	public List<ProjectMembers> getAllProjectMembersByProjectIdAndStatus(Long projectId, String status,OrgLocations loginOrgLocation,String roleIds,String teamIds,String isGlobalAccess)
			throws Exception {
		ProjectClients projectClients = null;
		if(StringUtils.isNotBlank(isGlobalAccess) && isGlobalAccess.equalsIgnoreCase(Constants.YES)) {
			projectClients = projectClientService.getProjectClientsByProjectIdAndOrgId(projectId, loginOrgLocation.getOrgId());
		}else {
			projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projectId, loginOrgLocation.getId());
		}
		List<ProjectMembers> projectMembersList = null;
		List<ProjectMembers> projectMembList = new ArrayList<>();
		if(projectClients != null){
			projectMembersList = projectMembersDao.getAllProjectMembersByProjectClientIdAndStatus(projectClients.getId(),status,roleIds,teamIds);
			
			if(projectMembersList != null && projectMembersList.size() > 0) {
				for(ProjectMembers projectMembers : projectMembersList) {
					if(projectMembers.getProjectAssignedDate() == null) {
						ProjectMembersHistory history = projectMembersHistoryDao.getProjectMembersAllHistoryByProjectMemberId(projectMembers.getId());
						if(history.getProjectAssignedDate() != null) {
							SimpleDateFormat dateFormat = loginOrgLocation.getLocFullFormDateFormatter();
							projectMembers.setProjectAssignedDateStr(dateFormat.format(history.getProjectAssignedDate()));
						}
					}
					projectMembList.add(projectMembers);
				}
			}
		}
		return projectMembList;
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
	public List<ProjectMembers> getAllAssignedProjectsForUser(Long userId, Long orgLocationId,String fromType) throws Exception
	{
		List<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectsByUserId(userId,orgLocationId,fromType);
		Float finalExpenseAmount = 0f;
		OrgLocations orglo = orgLocationService.getOrgLocations(orgLocationId);
		//SimpleDateFormat simpleDateFormat = orglo.getLocFullFormDateFormatter();
		for (ProjectMembers projectMember : projectMembersList) {
			try {
				ProjectMembersHistory pmh = null;
				pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMember.getId());
				if(pmh == null) {
					pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMember.getId());
				}
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
					Float draftTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId(), "DRAFT");
					projectMember.setDraftHrs(String.valueOf(draftTimesheetsSubmissionsCountAsEmployee));
					Float submittedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId(), "SUBMITTED");
					projectMember.setSubmittedHrs(String.valueOf(submittedTimesheetsSubmissionsCountAsEmployee));
					Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId(), "APPROVED");
					projectMember.setApprovedHrs(String.valueOf(approvalTimesheetsSubmissionsCountAsEmployee));
					Float rejectedTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId(), "REJECTED");
					projectMember.setRejectedHrs(String.valueOf(rejectedTimesheetsSubmissionsCountAsEmployee));
				}
				if (projectMember.getProjects() != null) {
					if (projectMember.getProjects().getStartDate() != null) {
						List<Expenses> expensesClaimendAmount = expensesReportService.getExpensesByProjectMemberIdAndExpenseStartAndEndDate(projectMember.getId(), null, null);
						if (expensesClaimendAmount != null && !expensesClaimendAmount.isEmpty()) {
							for (Expenses exp : expensesClaimendAmount) {
								finalExpenseAmount = finalExpenseAmount + exp.getTotalAmount().floatValue();
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
			if(pm.getProjectMemberCandidteId() != null ) {
				if (pm.getProjectMemberCandidteId() != null && pm.getProjectMemberCandidteId().getId() != null && StringUtils.isNotBlank(pm.getProjectMemberCandidteId().getLastName())) {
					sb.append(pm.getProjectMemberCandidteId().getFirstName()).append(" ")
							.append(pm.getProjectMemberCandidteId().getLastName());
				} else {
					sb.append(pm.getProjectMemberCandidteId().getFirstName());
				}
				pm.setEmpName(sb.toString());
			}
			
		});
		return (List<ProjectMembers>) projectMemberList;
	}

	@Override
	public List<ProjectMembers> getVendorAssignedMembersForProjects(Long projectId, Long orgLocationId,
			Long orgRelationId,String isGlobalUser) throws Exception {

		Collection<ProjectMembers> projectMemberList = projectMembersDao.getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(projectId,orgLocationId,isGlobalUser);

		List<ProjectMembers> projectMemberfilteredList = projectMemberList.stream()
						.filter(predicate -> predicate.getProjectMemberCandidteId().getOrgRelationId() !=null && predicate.getProjectMemberCandidteId().getOrgRelationId().getId().longValue() == orgRelationId)
				.collect(Collectors.toList());
		
		projectMemberfilteredList.forEach(pm -> {
			try {
				StringBuilder sb = new StringBuilder();
				if (pm.getProjectMemberCandidteId() != null && StringUtils.isNotEmpty(pm.getProjectMemberCandidteId().getLastName())) {
					sb.append(pm.getProjectMemberCandidteId().getFirstName()).append(" ")
							.append(pm.getProjectMemberCandidteId().getLastName());
				} else {
					sb.append(pm.getProjectMemberCandidteId().getFirstName());
				}
				pm.setEmpName(sb.toString());

			} catch (Exception exception) {
				logger.error(ExceptionUtils.getFullStackTrace(exception));
			}

		});
		return projectMemberfilteredList;
	}

	@Override
	public void saveAssignedProjectMembers(ProjectAssignedMembersDTO projectAssignedMembersDTO, String projectUUID,
			User user, OrgLocations orgLocations,String isGlobalUser) throws Exception {
		
		Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
		ProjectClients projectClients = null;
		List<Long> updatedProjectMembers = new ArrayList<Long>();
		if(projectAssignedMembersDTO.getProjectMembers() != null && projectAssignedMembersDTO.getProjectMembers().size() > 0){
			for(ProjectMembersDTO projectMembersDTO : projectAssignedMembersDTO.getProjectMembers()){
				if(projectMembersDTO.getCandidateId() != null){
					ProjectMembers projectMembers = null;
					if(projectMembersDTO.getProjectMemberId() != null){
						projectMembers = projectMembersDao.getProjectMemberById(projectMembersDTO.getProjectMemberId());
						projectMembers.setId(projectMembersDTO.getProjectMemberId());
						updatedProjectMembers.add(projectMembersDTO.getProjectMemberId());
					}else{
						projectMembers = new ProjectMembers();
						projectMembers.setProjects(projects);
					}
					
					Candidate candidate = candidateService.getCandidateByCandidateId(projectMembersDTO.getCandidateId());
					candidate.setId(projectMembersDTO.getCandidateId());
					projectMembers.setProjectMemberCandidteId(candidate);
					MasterCandidate masterCandidate = new MasterCandidate();
					masterCandidate.setId(candidate.getMasterCandidateId().getId());
					projectMembers.setMasterCandidateId(masterCandidate);
					projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocations.getId());
					if(candidate.getConsultantUser() != null){
						Employees employees = employeesService.getEmployeeByUserIdAndOrgLocationsId(candidate.getConsultantUser().getId(), orgLocations.getId());
						projectMembers.setEmployee(employees);
					}
					projectMembers = saveProjectMembers(projectMembersDTO, user, projectClients, projectMembers, orgLocations, projects);
					
					if(projects.getOrgLocationsId().getId().equals(orgLocations.getId())){
						saveProjectMembersHistory(projectMembers,user,projectClients);
					}else{
						
						ProjectClients clientProjectClients = null;
						
						OrgLocations clientOrgLocation = null;
						if(projectClients.getOrgRelationId().getResponderLocationId().getId().equals(orgLocations.getId())){
							clientOrgLocation = projectClients.getOrgRelationId().getLocationId();
						}else{
							clientOrgLocation = projectClients.getOrgRelationId().getResponderLocationId();
						}
						
						OrgRelations orgRelations = orgRelationsService.getVendorOrgRelationByResponderLocationIdAndLocationId(orgLocations.getId(), clientOrgLocation.getId());
						
						Candidate clientCandidate = candidateService.findByCandidateEmail(candidate.getEmail(), clientOrgLocation.getId());
						MasterCandidate clientMasterCandidate = null;
						if(clientCandidate == null){
							clientCandidate = new Candidate();
							clientMasterCandidate = new MasterCandidate();
							clientMasterCandidate.setId(candidate.getMasterCandidateId().getId());
							clientCandidate.setMasterCandidateId(clientMasterCandidate);
							clientCandidate.setParentCandidateId(candidate);
							clientCandidate.setOrgLocationId(clientOrgLocation);
							clientCandidate.setCandidateType("Candidate");
							clientCandidate.setFirstName(candidate.getFirstName());
							clientCandidate.setLastName(candidate.getLastName());
							clientCandidate.setEmail(candidate.getEmail());
							clientCandidate.setCountry(candidate.getCountry());
							clientCandidate.setStatus(1);
							clientCandidate.setIsAssociate(Constants.NO);
							clientCandidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
							
							clientCandidate.setOrgRelationId(orgRelations);
							clientCandidate.setCreatedBy(user.getUserCode());
							clientCandidate.setCreatedDate(new Date());
							clientCandidate.setUpdatedDate(new Date());
							Candidate savedCandidate = candidateService.addCandidate(clientCandidate);
							candidateService.saveOrUpdateCandidateElkDocument(savedCandidate);
						}else{
							clientMasterCandidate = clientCandidate.getMasterCandidateId();
						}
						saveProjectMembersHistory(projectMembers,user,projectClients);
						clientProjectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clientOrgLocation.getId());
					   
						projectMembers = projectMembersDao.getProjectMemberByCandidateIdAndProjectIdAndOrgLocationId(clientCandidate.getId(), projects.getId(), clientOrgLocation.getId());
						if(projectMembers == null){
							projectMembers = new ProjectMembers();
						}
						
						projectMembers.setProjectMemberCandidteId(clientCandidate);
						projectMembers.setMasterCandidateId(clientMasterCandidate);
						
						if(clientCandidate.getConsultantUser() != null){
							Employees clientEmployee = employeesService.getEmployeeByUserIdAndOrgLocationsId(clientCandidate.getConsultantUser().getId(), clientOrgLocation.getId());
							projectMembers.setEmployee(clientEmployee);
						}
						
						projectMembers = saveProjectMembers(projectMembersDTO, user, clientProjectClients, projectMembers, orgLocations, projects);
						saveProjectMembersHistory(projectMembers,user,clientProjectClients);
						
						if(clientProjectClients != null && clientProjectClients.getOrgLocationsId().getId().equals(clientOrgLocation.getId())){
							projectClients.setOrgRelationId(orgRelations);
							projectClientService.storeProjectClients(projectClients);
						}
						
					}
					
					if(candidate.getParentCandidateId() != null && (candidate.getDesignationType().equalsIgnoreCase("C2C") || candidate.getDesignationType().equalsIgnoreCase("C2C-1099"))){
						Long parentCandidatId = candidate.getParentCandidateId().getId();
						Long orgLocationId = candidate.getOrgLocationId().getId();
						int level = 1;
						while(parentCandidatId != null){
							Candidate cand = candidateService.getCandidateByCandidateId(parentCandidatId);
							projectMembers = projectMembersDao.getProjectMembersByCandidateIdAndProjectId(cand.getId(), projects.getId());
							if(projectMembers == null){
								projectMembers = new ProjectMembers();
							}
							if(cand != null){
								projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), cand.getOrgLocationId().getId());
								if(projectClients == null){
									Candidate clildCandidate = candidateService.getCandidateByParentCandidateIdAndOrgLocationId(parentCandidatId,orgLocationId);
									projectClients = new ProjectClients();
									projectClients.setProjectId(projects);
									projectClients.setOrgRelationId(clildCandidate.getOrgRelationId());
									projectClients.setOrgLocationsId(cand.getOrgLocationId());
									projectClients.setOrgId(cand.getOrgLocationId().getOrg());
									projectClients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
									projectClients.setStatus(1);
									
								
									Candidate pCandidate = candidateService.getCandidateByParentCandidateId(clildCandidate.getId());
									if(pCandidate != null && pCandidate.getOrgRelationId() != null){
										ProjectClients pClient = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clildCandidate.getOrgLocationId().getId());
										if(pClient != null && pClient.getLevel() != null){
											level = pClient.getLevel()+1;
										}else {
											level = level+1;
										}
									}
									else{
										level = level+1;
									}
									
									
									projectClients.setLevel(level);
									projectClients.setCreatedDate(new Date());
									projectClients.setCreatedBy(user);
									projectClients.setUpdatedDate(new Date());
									projectClients.setUpdatedBy(user);
									
									//prepare ProjectApproverHierarchy
									Set<ProjectApproverHierarchy> projectApproverHierarchyList = new HashSet<ProjectApproverHierarchy>();
									ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
									pah.setProjectId(projects);
									pah.setProjectClientsId(projectClients);
									
									if(cand.getOrgLocationId().getUser() != null) {
										pah.setApproverUserId(cand.getOrgLocationId().getUser());
									}else {
										OrgSpoc orgSpoc = orgSpocService.getSpocByOrgRelationIdForNewVendor(clildCandidate.getOrgRelationId().getId());
										pah.setApproverUserId(orgSpoc.getVendorUser());
									}
									
									
									pah.setLevel(1);
									pah.setStatus("1");
									projectApproverHierarchyList.add(pah);
									projectClients.setProjectApproverHierarchyList(projectApproverHierarchyList);
									
									
									
									
									//prepare ExpenseApproverHierarchy
									Set<ExpenseProjectApproverHierarchy> expenseApproverHierarchyList = new HashSet<ExpenseProjectApproverHierarchy>();
									ExpenseProjectApproverHierarchy eah = new ExpenseProjectApproverHierarchy();
									eah.setProjectId(projects);
									eah.setProjectClientsId(projectClients);
									
									if(cand.getOrgLocationId().getUser() != null) {
										eah.setApproverUserId(cand.getOrgLocationId().getUser());
									}else {
										OrgSpoc orgSpoc = orgSpocService.getSpocByOrgRelationIdForNewVendor(clildCandidate.getOrgRelationId().getId());
										eah.setApproverUserId(orgSpoc.getVendorUser());
									}
									
									
									eah.setLevel(1);
									eah.setStatus("1");
									expenseApproverHierarchyList.add(eah);
									projectClients.setExpenseProjectApproverHierarchyList(expenseApproverHierarchyList);
									
									
									projectClientService.storeProjectClients(projectClients);
								}
								if(cand.getConsultantUser() != null){
									Employees clientEmployee = employeesService.getEmployeeByUserIdAndOrgLocationsId(cand.getConsultantUser().getId(), cand.getOrgLocationId().getId());
									projectMembers.setEmployee(clientEmployee);
								}
								projectMembers.setProjectMemberCandidteId(cand);
								projectMembers.setMasterCandidateId(cand.getMasterCandidateId());
								projectMembers = saveProjectMembers(projectMembersDTO, user, projectClients, projectMembers, orgLocations, projects);
								saveProjectMembersHistory(projectMembers,user,projectClients);
								if(cand.getParentCandidateId() != null){
									parentCandidatId = cand.getParentCandidateId().getId();
									orgLocationId = cand.getOrgLocationId().getId();
								}else{
									parentCandidatId = null;
								}
							}
						}
					}
				  
					List<ProjectApproverHierarchy> approverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectId(projects.getId());
					   StringBuilder managers = new StringBuilder();
					  
					   for (ProjectApproverHierarchy projectApproverHierarchy : approverHierarchy) {
						 StringBuilder fullname=new StringBuilder();
						 if(projectApproverHierarchy.getApproverUserId().getLastName()!=null && projectApproverHierarchy.getApproverUserId().getLastName()!="")
						   managers=managers.append(fullname.append(projectApproverHierarchy.getApproverUserId().getFirstName()).append(" ").append(projectApproverHierarchy.getApproverUserId().getLastName())).append(" & ");
						 else
							 managers=managers.append(projectApproverHierarchy.getApproverUserId().getFirstName()).append(" & ");
					}
					  String projectManagers=managers.toString();
					  String projectmanagerss= managers.substring(0, projectManagers.length()-2);
					  
					  if(StringUtils.isNotEmpty(projectAssignedMembersDTO.getType()) && projectAssignedMembersDTO.getType().equalsIgnoreCase("projectSummary"))
					  sendEmailToTeamMembersInProjectSummary(projects, projectMembers, user, projectmanagerss, orgLocations, orgLocations.getOrg().getOrgCode());
				}
			}
			
			
			/*updatedProjectMembers.retainAll(savedProjectMemberIds);
			savedProjectMemberIds.removeAll(updatedProjectMembers);
			
			if(savedProjectMemberIds != null && savedProjectMemberIds.size() > 0){
				for(Long memberId : savedProjectMemberIds){
					ProjectMembers pm = projectMembersDao.getProjectMemberById(memberId);
		    		List<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectMembersByMasterCandidateId(pm.getMasterCandidateId().getId(), pm.getProjects().getId());
		    		if(projectMembersList != null && projectMembersList.size() > 0){
		    			
		    			for(ProjectMembers members : projectMembersList){
		    				
		    				members.setIsManuallyTerminated(Primary.Yes);
		    				members.setStatus("0");
		    				projectMembersDao.updateProjectMember(members);
		    				
		        			List<ProjectMembersHistory>  pmhList = projectMembersHistoryService.getProjectMembersHistoryListByProjectMemberId(members.getId());
		            		if(pmhList != null && pmhList.size() > 0){
		            			for(ProjectMembersHistory pmh : pmhList){
		            				pmh.setProjectTerminationDate(new Date());
		            				pmh.setStatus("CLOSED");
		            	    		projectMembersHistoryService.updateProjectMembersHistory(pmh);
		            			}
		            		}
		    			}
		    		}
				}
			}*/
		}
		
		projects.setIsProjectMembersFilled(Constants.YES);
		projectsService.saveProject(projects);
		
		
	}
	
	private void saveProjectMembersHistory(ProjectMembers projectMembers,User user,ProjectClients projectClients) throws Exception{
		
		ProjectMembersHistory projectMembersHistory = null;
		if(projectMembers.getId() != null){
			projectMembersHistory = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectClientsId(projectClients.getId(),projectMembers.getId());
		}
		
		if(projectMembersHistory == null){
			projectMembersHistory = new ProjectMembersHistory();
		}
		
		projectMembersHistory.setProjectMemberId(projectMembers);
		//projectMembersHistory.setProjectClientsId(projectClients);
		
		if(projectMembers.getProjectAssignedDate() != null){
			projectMembersHistory.setProjectAssignedDate(projectMembers.getProjectAssignedDate());
		}else{
			projectMembersHistory.setProjectAssignedDate(null);
		}
		
		if(projectMembers.getProjectEndDate() != null){
			projectMembersHistory.setProjectTerminationDate(projectMembers.getProjectEndDate());
		}else{
			projectMembersHistory.setProjectTerminationDate(null);
		}
		
		/*Candidate candidate =  candidateService.findByCandidateEmail(projectMembers.getProjectMemberCandidteId().getEmail(), projectClients.getOrgLocationsId().getId());
		if(candidate != null){
			projectMembersHistory.setCandidateId(candidate);
		}*/
		projectMembersHistory.setStatus("OPEN");
		projectMembersHistory.setCreatedBy(user.getUserCode());
		projectMembersHistory.setUpdatedBy(user.getUserCode());
		projectMembersHistory.setCreatedDate(new Date());
		projectMembersHistory.setUpdatedDate(new Date());
		projectMembersHistoryDao.updateProjectMembersHistory(projectMembersHistory);
		
	}
	
	
	private ProjectMembers saveProjectMembers(ProjectMembersDTO projectMembersDTO,User user,ProjectClients projectClients,ProjectMembers projectMembers,OrgLocations orgLocations,Projects projects) throws Exception{
		
		if(projectMembersDTO.getPayRate() != null){
			projectMembers.setPayRate(projectMembersDTO.getPayRate());
			projectMembers.setPayRateCurrency(projectMembersDTO.getPayRateCurrency());
			projectMembers.setPayRateType(projectMembersDTO.getPayRateRateType());
		}else {
			projectMembers.setPayRate(null);
			projectMembers.setPayRateCurrency(null);
			projectMembers.setPayRateType(null);
		}
		
		if(projectMembersDTO.getBillRate() != null){
			projectMembers.setCurrentBillRate(projectMembersDTO.getBillRate());
			projectMembers.setBillRateCurrency(projectMembersDTO.getBillRateCurrency());
			projectMembers.setRateType(projectMembersDTO.getBillRateRateType());
			
		}else{
			projectMembers.setCurrentBillRate(null);
			projectMembers.setBillRateCurrency(null);
			projectMembers.setRateType(null);
		}
		
		if(projectMembersDTO.getMemberStartDate() != null && !projectMembersDTO.getMemberStartDate().isEmpty()){
			Date projectAssignedStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getMemberStartDate());
			projectMembers.setProjectAssignedDate(projectAssignedStartDate);
		}
		
		if(projectMembersDTO.getMemberEndDate() != null && !projectMembersDTO.getMemberEndDate().isEmpty()){
			Date projectAssignedEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getMemberEndDate());
			projectMembers.setProjectEndDate(projectAssignedEndDate);
		}else{
			projectMembers.setProjectEndDate(null);
		}
		
		if(projectMembersDTO.getEstHours() != null){
			projectMembers.setEstimatedHours(projectMembersDTO.getEstHours());
		}else{
			projectMembers.setEstimatedHours(null);
		}
		
		if(projectMembersDTO.getEstBudget() != null){
			projectMembers.setEstimatedBudget(projectMembersDTO.getEstBudget());
		}else{
			projectMembers.setEstimatedBudget(null);
		}
		
		if(projectMembersDTO.getPercentageOfWork() != null){
			projectMembers.setPercentageOfWork(projectMembersDTO.getPercentageOfWork());
		}else{
			projectMembers.setPercentageOfWork(null);
		}
		projectMembers.setProjects(projects);
		projectMembers.setStatus("1");
		projectMembers.setCreatedBy(user.getUserCode());
		projectMembers.setUpdatedBy(user.getUserCode());
		projectMembers.setCreatedDate(new Date());
		projectMembers.setUpdatedDate(new Date());
		projectMembers.setProjectClientsId(projectClients);
		projectMembersDao.saveProjectMember(projectMembers);
		return projectMembers;
	}
	
	

private ProjectMembers saveSharedProjectMembers(ProjectMembersDTO projectMembersDTO,Candidate candidate,User user,ProjectClients projectClients,OrgLocations orgLocations,Projects projects) throws Exception{
	
	ProjectMembers projectMembers = null;
	projectMembers = projectMembersDao.getProjectMemberByCandidateIdAndProjectIdAndOrgLocationId(candidate.getId(), projects.getId(), orgLocations.getId());
	if(projectMembers == null) {
		projectMembers = new ProjectMembers();
	}
	projectMembers.setProjectMemberCandidteId(candidate);
	projectMembers.setMasterCandidateId(candidate.getMasterCandidateId());
	projectMembers.setProjectClientsId(projectClients);
	if(projectMembersDTO.getPayRate() != null){
		projectMembers.setPayRate(projectMembersDTO.getPayRate());
		projectMembers.setPayRateCurrency(projectMembersDTO.getPayRateCurrency());
		projectMembers.setPayRateType(projectMembersDTO.getPayRateRateType());
	}else {
		projectMembers.setPayRate(null);
		projectMembers.setPayRateCurrency(null);
		projectMembers.setPayRateType(null);
	}
	
	if(projectMembersDTO.getBillRate() != null){
		projectMembers.setCurrentBillRate(projectMembersDTO.getBillRate());
		projectMembers.setBillRateCurrency(projectMembersDTO.getBillRateCurrency());
		projectMembers.setRateType(projectMembersDTO.getBillRateRateType());
		
	}else{
		projectMembers.setCurrentBillRate(null);
		projectMembers.setBillRateCurrency(null);
		projectMembers.setRateType(null);
	}
	
	if(projectMembersDTO.getMemberStartDate() != null && !projectMembersDTO.getMemberStartDate().isEmpty()){
		Date projectAssignedStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getMemberStartDate());
		projectMembers.setProjectAssignedDate(projectAssignedStartDate);
	}
	
	if(projectMembersDTO.getMemberEndDate() != null && !projectMembersDTO.getMemberEndDate().isEmpty()){
		Date projectAssignedEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),projectMembersDTO.getMemberEndDate());
		projectMembers.setProjectEndDate(projectAssignedEndDate);
	}else{
		projectMembers.setProjectEndDate(null);
	}
	
	if(projectMembersDTO.getEstHours() != null){
		projectMembers.setEstimatedHours(projectMembersDTO.getEstHours());
	}else{
		projectMembers.setEstimatedHours(null);
	}
	
	if(projectMembersDTO.getEstBudget() != null){
		projectMembers.setEstimatedBudget(projectMembersDTO.getEstBudget());
	}else{
		projectMembers.setEstimatedBudget(null);
	}
	projectMembers.setProjects(projects);
	projectMembers.setStatus("1");
	projectMembers.setCreatedBy(user.getUserCode());
	projectMembers.setUpdatedBy(user.getUserCode());
	projectMembers.setCreatedDate(new Date());
	projectMembers.setUpdatedDate(new Date());
	projectMembers.setProjectClientsId(projectClients);
	projectMembersDao.saveProjectMember(projectMembers);
	return projectMembers;
}
	
	
	
	private ProjectMembers saveTransferProjectMembers(ProjectMembers pm,User user,ProjectClients projectClients,Projects projects,Candidate candidate,OrgLocations orgLocations) throws Exception{
		
		ProjectMembers projectMembers = null;
		projectMembers = projectMembersDao.getProjectMembersByCandidateIdAndProjectId(candidate.getId(), projects.getId());
		if(projectMembers == null) {
			projectMembers = new ProjectMembers();
		}
		
		projectMembers.setProjects(projects);
		projectMembers.setProjectMemberCandidteId(candidate);
		projectMembers.setMasterCandidateId(candidate.getMasterCandidateId());
		
		if(candidate.getConsultantUser() != null){
			Employees clientEmployee = employeesService.getEmployeeByUserIdAndOrgLocationsId(candidate.getConsultantUser().getId(), orgLocations.getId());
			projectMembers.setEmployee(clientEmployee);
		}
		
		if(pm.getPayRate() != null){
			projectMembers.setPayRate(pm.getPayRate());
			projectMembers.setPayRateCurrency(pm.getPayRateCurrency());
			projectMembers.setPayRateType(pm.getPayRateType());
		}
		
		if(pm.getCurrentBillRate() != null){
			projectMembers.setCurrentBillRate(pm.getCurrentBillRate());
			projectMembers.setBillRateCurrency(pm.getBillRateCurrency());
			projectMembers.setRateType(pm.getRateType());
			
		}else{
			pm.setCurrentBillRate(null);
			projectMembers.setBillRateCurrency(null);
			projectMembers.setRateType(null);
		}
		
		//commented by laxman
		
	/*	if(pm.getProjectAssignedDate() != null){
			projectMembers.setProjectAssignedDate(pm.getProjectAssignedDate());
		}
		
		if(pm.getProjectEndDate() != null ){
			projectMembers.setProjectEndDate(pm.getProjectEndDate());
		}else{
			projectMembers.setProjectEndDate(null);
		}*/
		
		if(projects.getStartDate() != null){
			projectMembers.setProjectAssignedDate(projects.getStartDate());
		}
		
		if(projects.getEndDate() != null ){
			projectMembers.setProjectEndDate(projects.getEndDate());
		}else{
			projectMembers.setProjectEndDate(null);
		}
		
		if(pm.getEstimatedHours() != null){
			projectMembers.setEstimatedHours(pm.getEstimatedHours());
		}else{
			projectMembers.setEstimatedHours(null);
		}
		
		if(pm.getEstimatedBudget() != null){
			projectMembers.setEstimatedBudget(pm.getEstimatedBudget());
		}else{
			projectMembers.setEstimatedBudget(null);
		}
		projectMembers.setProjects(projects);
		projectMembers.setStatus("1");
		projectMembers.setCreatedBy(user.getUserCode());
		projectMembers.setUpdatedBy(user.getUserCode());
		projectMembers.setCreatedDate(new Date());
		projectMembers.setUpdatedDate(new Date());
		projectMembers.setProjectClientsId(projectClients);
		projectMembersDao.saveProjectMember(projectMembers);
		return projectMembers;
	}

	@Override
	public List<Long> getAllProjectMembersIdsByProjectId(Long projectId) throws Exception {
		return projectMembersDao.getAllProjectMembersIdsByProjectId(projectId);
	}
	
	@Override
	public List<ProjectMembers> getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(Long projectId,Long locationId,String isGlobalUser) throws Exception {
		return projectMembersDao.getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(projectId, locationId,isGlobalUser);
	}
	

	@Override
	public void deleteProjectMember(ProjectMembers projectMembers) throws Exception {
		projectMembersDao.deleteProjectMember(projectMembers);
		
	}

	@Override
	public List<ProjectMembers> getAllProjectMembersByProjectClientIdAndStatus(Long projectClientsId, String status)
			throws Exception {
		return projectMembersDao.getAllProjectMembersByProjectClientIdAndStatus(projectClientsId, status,"","");
	}

	@Override
	public String transferMembers(OrgLocations orgLocations, Org org, Projects projects,String projectMembers,User user,Long projectOrgLocationId) throws Exception {
		
		String result = "failure";
		String isInternalOrgProject = null;
    	if(projects.getOrgLocationsId().getId().equals(orgLocations.getId())){
    		isInternalOrgProject = Constants.YES;
		}else{
			isInternalOrgProject = Constants.NO;
		}
    	
    	String[] transferedPMs = projectMembers.split(",");
    	for(int i=0; i <transferedPMs.length; i++){
    		
    		ProjectMembers pm = projectMembersDao.getProjectMemberById(Long.valueOf(transferedPMs[i]));
    		List<ProjectMembers> projectMembersList = projectMembersDao.getAllProjectMembersByMasterCandidateId(pm.getMasterCandidateId().getId(), pm.getProjects().getId());
    		if(projectMembersList != null && projectMembersList.size() > 0){
    			
    			for(ProjectMembers members : projectMembersList){
    				
    				members.setIsManuallyTerminated(Primary.Yes);
    				members.setStatus("0");
    				projectMembersDao.updateProjectMember(members);
    				
        			List<ProjectMembersHistory>  pmhList = projectMembersHistoryService.getProjectMembersHistoryListByProjectMemberId(members.getId());
            		if(pmhList != null && pmhList.size() > 0){
            			for(ProjectMembersHistory pmh : pmhList){
            				pmh.setProjectTerminationDate(new Date());
            				pmh.setStatus("CLOSED");
            	    		projectMembersHistoryService.updateProjectMembersHistory(pmh);
            			}
            		}
    				
    			}
    		}
    		
    		if(isInternalOrgProject.equalsIgnoreCase(Constants.YES)){
    			ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), projects.getOrgLocationsId().getId());
    			Candidate candidate = candidateService.findByCandidateEmail(pm.getProjectMemberCandidteId().getEmail(), orgLocations.getId());
				if(candidate.getParentCandidateId() != null){
					saveVendorProjectClientDetails(candidate,projects,user,projectClients,pm);
				}
				
    			ProjectMembers members = saveTransferProjectMembers(pm, user, projectClients, projects,candidate,orgLocations);
    			saveProjectMembersHistory(members,user,projectClients);
				
    		}else{
    			OrgRelations orgRelations = null;
    			ProjectClients projectClients = null;
    			Candidate candidate = candidateService.findByCandidateEmail(pm.getProjectMemberCandidteId().getEmail(), orgLocations.getId());
    			OrgLocations orgLoc = orgLocationService.getOrgLocations(projectOrgLocationId);
    			OrgLocations vendorOrgLocations = null;
    			if(!orgLoc.getId().equals(orgLocations.getId()) && orgLoc.getOrg().getId().equals(orgLocations.getOrg().getId())){
    				if(candidate.getOrgRelationId() != null){
    					if(candidate.getOrgRelationId().getResponderLocationId().getId().equals(orgLocations.getId())){
    						vendorOrgLocations = candidate.getOrgRelationId().getLocationId();
    					}else{
    						vendorOrgLocations = candidate.getOrgRelationId().getResponderLocationId();
    					}
    					
    					orgRelations = orgRelationsService.getVendorOrgRelationByResponderLocationIdAndLocationId(vendorOrgLocations.getId(),orgLoc.getId());
    					if(orgRelations == null){
    						orgRelations = saveOrgRelations(vendorOrgLocations,orgLoc,user);
			 				orgRelations = orgRelationsService.addOrgRelations(orgRelations);
			 			
							String modules = orgLoc.getModulesId();
							List<Integer> moduleIds = new ArrayList<Integer>();
							 for (String s : orgLoc.getModulesId().split(",")){
								 moduleIds.add(Integer.parseInt(s));
							 }
							 boolean flag = moduleIds.contains(Constants.MODULE_VMS);
							 if(flag == false){
							     modules = modules+","+Constants.MODULE_VMS;
							 }
							 
							 boolean tsflag = moduleIds.contains(Constants.MODULE_TIMESHEET);
							 if(tsflag == false) {
				    				modules = modules+","+Constants.MODULE_TIMESHEET;
				    			}
							 saveUserRoleActions(orgLoc);
							 
							 orgLoc.setModulesId(modules);
							 orgLocationService.updateOrgLocations(orgLoc);
							 
							 Set<OrgSpoc> orgSpocList = orgRelations.getOrgSpocList();
							 OrgSpoc orgSpoc = null;
							 for(OrgSpoc spoc : orgSpocList){
								 orgSpoc =  spoc;
							 }
							 
							 clientService.saveOrUpdateClientVendorElkDocument(null, orgLoc, "Client", orgSpoc, orgRelations,null, user, vendorOrgLocations);
    					}
    				}
    				
    				ProjectClients clientPC = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLoc.getId());
    				
        			Candidate orgLocCandidate = candidateService.findByCandidateEmail(pm.getProjectMemberCandidteId().getEmail(), orgLoc.getId());
    				if(orgLocCandidate == null){
    					orgLocCandidate = getCandidateDetails(candidate, user, projects, orgLoc);
    					if(candidate.getParentCandidateId() != null){
    						orgLocCandidate.setParentCandidateId(candidate.getParentCandidateId());
    					}
       					orgLocCandidate.setOrgLocationId(orgLoc);
    					orgLocCandidate.setOrgRelationId(orgRelations);
    					Candidate savedCandidate = candidateService.addCandidate(orgLocCandidate);
    					candidateService.saveOrUpdateCandidateElkDocument(savedCandidate);
    				}
    				if(orgLocCandidate.getParentCandidateId() != null){
    					saveVendorProjectClientDetails(orgLocCandidate,projects,user,projectClients,pm);
    				}
    				ProjectMembers members = saveTransferProjectMembers(pm, user, clientPC, projects,orgLocCandidate,orgLoc);
    				saveProjectMembersHistory(members,user,clientPC);
    				
    			}else{
    				ProjectMembers members = null;
    				ProjectClients projClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocations.getId());
					OrgLocations clientOrgLocation = null;
					if(projClients.getOrgRelationId().getResponderLocationId().getId().equals(orgLocations.getId())){
						clientOrgLocation = projClients.getOrgRelationId().getLocationId();
					}else{
						clientOrgLocation = projClients.getOrgRelationId().getResponderLocationId();
					}
    				
    				orgRelations = orgRelationsService.getVendorOrgRelationByResponderLocationIdAndLocationId(orgLocations.getId(), clientOrgLocation.getId());
        			projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationIdAndOrgRelationId(projects.getId(), orgLocations.getId(), orgRelations.getId());
        			members = saveTransferProjectMembers(pm, user, projectClients, projects,pm.getProjectMemberCandidteId(),orgLocations);
        			saveProjectMembersHistory(members,user,projectClients);
        			
        			ProjectClients clientPC = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clientOrgLocation.getId());
        			Candidate clientCandidate = candidateService.findByCandidateEmail(pm.getProjectMemberCandidteId().getEmail(), clientOrgLocation.getId());
    				if(clientCandidate == null){
    					clientCandidate = getCandidateDetails(candidate, user, projects, orgLocations);
    					clientCandidate.setParentCandidateId(candidate);
    					clientCandidate.setOrgRelationId(orgRelations);
    					clientCandidate.setOrgLocationId(clientOrgLocation);
    					Candidate savedCandidate = candidateService.addCandidate(clientCandidate);
    					candidateService.saveOrUpdateCandidateElkDocument(savedCandidate);
    				}
    				members = saveTransferProjectMembers(pm, user, clientPC, projects,clientCandidate,clientOrgLocation);
    				saveProjectMembersHistory(members,user,clientPC);
    				if(candidate.getParentCandidateId() != null){
    					saveVendorProjectClientDetails(candidate,projects,user,projectClients,pm);
    				}
    			}
				
    		}
    	}
		
    	result = "success";
		return result;
	}
	
	
	
	 public void saveUserRoleActions(OrgLocations orgLocations) throws Exception{
		 
		   SourceleadRoles soruceLeadRoles = sourceleadRolesService.getSourceleadRolesByRoleName(Constants.ROLE_GUEST_CLIENT);
			List<Long> soureleadActionIdList = new ArrayList<>();
			String[] actionsIds = null;
			actionsIds = soruceLeadRoles.getActionId().split(",");
			 for(int i=0 ; i < actionsIds.length; i++) {
				 soureleadActionIdList.add(Long.parseLong(actionsIds[i]));
			 }
			
			Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
			 List<Long> adminRoleActonList = new ArrayList<Long>();
			 for (String s : adminrole.getActionId().split(",")){
				 adminRoleActonList.add(Long.parseLong(s));
			 }
			 
			 adminRoleActonList.retainAll(soureleadActionIdList);
			 soureleadActionIdList.removeAll(adminRoleActonList);
			
			
	    	StringBuilder strbul  = new StringBuilder();
		     Iterator<Long> iter = soureleadActionIdList.iterator();
		     while(iter.hasNext())
		     {
		         strbul.append(iter.next());
		        if(iter.hasNext()){
		         strbul.append(",");
		        }
		     }
		     strbul.toString();
			
			
	        String adminids = adminrole.getActionId()+","+strbul;
			adminrole.setActionId(adminids);
			rolesService.storeRoles(adminrole);
			
			Roles managerrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER, orgLocations.getId());
			String managerids = managerrole.getActionId()+","+strbul;
		    managerrole.setActionId(managerids);
			rolesService.storeRoles(managerrole);
			
			
			updateOrgSubscriptionRegsiteredTabs(orgLocations,adminids);
			
			
	 }
	 
	 
 public void updateOrgSubscriptionRegsiteredTabs(OrgLocations orglocations,String actionIds) throws Exception {
		 
		 String[] sourceleadActionIds = actionIds.split(",");
		 List<Long> actionIdsList = new ArrayList<>();
		 for(int i=0; i<sourceleadActionIds.length;i++) {
			 actionIdsList.add(Long.parseLong(sourceleadActionIds[i]));
		 }
		 
		 List<SourceleadActions> actionsList = sourceleadActionsService.getAllSourceleadActionsByIdsList(actionIdsList);
		 Set<Long> tabIds = new HashSet<>();
		 if(actionsList != null && !actionsList.isEmpty()) {
			 for(SourceleadActions action : actionsList) {
				 Tabs tabs = tabService.getTabListByTabId(action.getTabs().getId());
				 if(tabs.getTabs() != null){
					 if(tabs.getId() == 14L) {
						 tabIds.add(tabs.getId());
					 }else {
						 tabIds.add(tabs.getTabs().getId());
					 }
				 }else {
					 tabIds.add(tabs.getId());
				 }
			 }
		 }
		 
		 StringBuilder tabsBuilder = new StringBuilder();
		 for (Long tabId : tabIds) {
			 tabsBuilder.append(tabId).append(",");
		 }
		 String tabsBuilderString = tabsBuilder.substring(0, tabsBuilder.length()-1);
		 
		 OrgLicenseSubscription orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgIdAndCountry(orglocations.getOrg().getId(), orglocations.getCountry());
		 orgLicenseSubscription.setSubscriptionTabs(tabsBuilderString);
		 orgLicenseSubscriptionService.saveOrgLicenseSubscription(orgLicenseSubscription);
	 }
	
	
	public OrgRelations saveOrgRelations(OrgLocations vendorOrgLocations,OrgLocations clientOrgLocation,User user){
		
		OrgRelations orgRelations = new OrgRelations();
			orgRelations.setLocationId(vendorOrgLocations);
			orgRelations.setResponderLocationId(clientOrgLocation);
			orgRelations.setInitiatorType("Vendor");
			orgRelations.setResponderType("Client");
			orgRelations.setGenerateInvoice("Bulk");
			orgRelations.setGenerateBill("Bulk");
			orgRelations.setInitiatorLevel("Tier-5");
			orgRelations.setResponderLevel("Tier-5");
			orgRelations.setStatus("ACTIVE");
			orgRelations.setCreatedBy(user.getUserCode());
			orgRelations.setCreatedDate(new Date());
			orgRelations.setUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			orgRelations.setUpdatedBy(user.getUserCode());
			orgRelations.setUpdatedDate(new Date());
			
			Set<OrgSpoc> orgSpocSet = new HashSet<>();
			OrgSpoc orgSpoc = new OrgSpoc();
			orgSpoc.setOrgRelations(orgRelations);
			orgSpoc.setClientUser(clientOrgLocation.getUser());
			orgSpoc.setVendorUser(user);
			orgSpoc.setInvitationStatus(Constants.MAIL_NEW);
			orgSpoc.setClientLocationId(clientOrgLocation);
			orgSpoc.setVendorLocationId(vendorOrgLocations);
			orgSpoc.setUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			orgSpoc.setStatus(Constants.MEMBER_ACTIVE);
			orgSpoc.setCreatedDate(new Date());
			orgSpoc.setCreatedBy(user.getUserCode());
			orgSpoc.setUpdatedDate(new Date());
			orgSpoc.setUpdatedBy(user.getUserCode());
			orgSpocSet.add(orgSpoc);
			orgRelations.setOrgSpocList(orgSpocSet);
			return orgRelations;
	}
	
	
	public Candidate getCandidateDetails(Candidate candidate,User user,Projects projects,OrgLocations orgLocations) throws Exception{
		Candidate cand = new Candidate();
		MasterCandidate masterCandidate = new MasterCandidate();
		masterCandidate.setId(candidate.getMasterCandidateId().getId());
		cand.setMasterCandidateId(masterCandidate);
		cand.setCandidateType("Candidate");
		cand.setFirstName(candidate.getFirstName());
		cand.setLastName(candidate.getLastName());
		cand.setEmail(candidate.getEmail());
		cand.setCountry(candidate.getCountry());
		cand.setStatus(1);
		cand.setIsAssociate(Constants.YES);
		cand.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		cand.setCreatedBy(user.getUserCode());
		cand.setCreatedDate(new Date());
		cand.setUpdatedDate(new Date());
		return cand;
	}
	
	
	
	public void saveVendorProjectClientDetails(Candidate candidate,Projects projects,User user,ProjectClients projectClients,ProjectMembers pm) throws Exception{
		
		Long parentCandidatId = candidate.getParentCandidateId().getId();
		int level = 1;
		while(parentCandidatId != null){
			Candidate cand = candidateService.getCandidateByCandidateId(parentCandidatId);
			if(cand != null){
				ProjectClients pc = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), cand.getOrgLocationId().getId());
				if(pc == null){
					Candidate clildCandidate = candidateService.getCandidateByParentCandidateId(parentCandidatId);
					pc = new ProjectClients();
					pc.setProjectId(projects);
					pc.setOrgRelationId(clildCandidate.getOrgRelationId());
					pc.setOrgLocationsId(cand.getOrgLocationId());
					pc.setOrgId(cand.getOrgLocationId().getOrg());
					pc.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
					pc.setStatus(1);
					pc.setCreatedDate(new Date());
					pc.setCreatedBy(user);
					pc.setUpdatedDate(new Date());
					pc.setUpdatedBy(user);
					
					Candidate pCandidate = candidateService.getCandidateByParentCandidateId(clildCandidate.getId());
					if(pCandidate != null && pCandidate.getOrgRelationId() != null){
						ProjectClients pClient = projectClientService.getProjectClientsByProjectIdAndOrgLocationIdAndOrgRelationId(projects.getId(), clildCandidate.getOrgLocationId().getId(),pCandidate.getOrgRelationId().getId());
						if(pClient != null && pClient.getLevel() != null){
							level = pClient.getLevel()+1;
						}
					}
					else{
						level = level+1;
					}
					
					pc.setLevel(level);
					
					//prepare ProjectApproverHierarchy
					Set<ProjectApproverHierarchy> projectApproverHierarchyList = new HashSet<ProjectApproverHierarchy>();
					ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
					pah.setProjectId(projects);
					pah.setProjectClientsId(pc);
					
					if(cand.getOrgLocationId().getUser() != null) {
						pah.setApproverUserId(cand.getOrgLocationId().getUser());
					}else {
						OrgSpoc orgSpoc = orgSpocService.getSpocByOrgRelationIdForNewVendor(clildCandidate.getOrgRelationId().getId());
						pah.setApproverUserId(orgSpoc.getVendorUser());
					}
					
					pah.setLevel(1);
					pah.setStatus("1");
					projectApproverHierarchyList.add(pah);
					pc.setProjectApproverHierarchyList(projectApproverHierarchyList);
					
					projectClientService.storeProjectClients(pc);
				}
				ProjectMembers members = saveTransferProjectMembers(pm, user, pc, projects,cand,cand.getOrgLocationId());
				saveProjectMembersHistory(members,user,pc);
				if(cand.getParentCandidateId() != null){
					parentCandidatId = cand.getParentCandidateId().getId();
				}else{
					parentCandidatId = null;
				}
			}
		}
	}

	@Override
	public Integer getProjectMembersCountByProjectClientId(Long projectClientId) throws Exception {
		return projectMembersDao.getProjectMembersCountByProjectClientId(projectClientId);
	}

	@Override
	public ProjectMembers getProjectMembersByCandidateIdAndProjectId(Long candidateId, Long projectId)
			throws Exception {
		return projectMembersDao.getProjectMembersByCandidateIdAndProjectId(candidateId, projectId);
	}

	@Override
	public ProjectMembers getClientProjectMemberByProjectMemberId(Long projectMemberId) throws Exception {
		return projectMembersDao.getClientProjectMemberByProjectMemberId(projectMemberId);
	}

	@Override
	public ProjectMembers getConsultantUserProjectMemberByProjectMemberId(Long projectMemberId) throws Exception {
		return projectMembersDao.getConsultantUserProjectMemberByProjectMemberId(projectMemberId);
	}

	@Override
	public ProjectMembers getProjectMembersByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId, Long projectId,
			Long locationId,String isGlobalUser) throws Exception {
		return projectMembersDao.getProjectMembersByMasterCandidateIdAndProjectIdLocationId(masterCandidateId, projectId, locationId,isGlobalUser);
	}
	
	@Override
	public ProjectMembers getProjectMembersByMasterCandidateIdAndProjectIdLocationIdForFinance(Long masterCandidateId, Long projectId,
			Long locationId,String isGlobalUser) throws Exception {
		return projectMembersDao.getProjectMembersByMasterCandidateIdAndProjectIdLocationIdForFinance(masterCandidateId, projectId, locationId,isGlobalUser);
	}

	@Override
	public List<ProjectMembers> getAllProjectMembersByProjectClientIdAndActiveStatusRecords(Long projectClientsId) throws Exception {
		return projectMembersDao.getAllProjectMembersByProjectClientIdAndActiveStatusRecords(projectClientsId);
	}

	@Override
	public ProjectMembers getProjectMembersByCandidateIdAndProjectClientId(Long candidateId, Long projectClientsId)
			throws Exception {
		return projectMembersDao.getProjectMembersByCandidateIdAndProjectClientId(candidateId, projectClientsId);
	}

	@Override
	public List<ProjectMembers> getAllProjectMembersByProjectClientId(Long projectClientsId) throws Exception {
		return projectMembersDao.getAllProjectMembersByProjectClientId(projectClientsId);
	}

	@Override
	public ProjectMembers getActiveOrNonProjectMemberByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId,
			Long projectId, Long locationId,Long orgId,String isGlobalAccess) throws Exception {
		return projectMembersDao.getActiveOrNonProjectMemberByMasterCandidateIdAndProjectIdLocationId(masterCandidateId, projectId, locationId,orgId,isGlobalAccess);
	}

	@Override
	public List<ProjectMembers> getAllProjectMembersByMasterCandidateId(Long masterCandidateId, Long projectId) throws Exception {
		return projectMembersDao.getAllProjectMembersByMasterCandidateId(masterCandidateId, projectId);
	}

	@Override
	public Boolean getPayRateOrBillRateChanges(ProjectMembers projectMembers, ProjectMembersDTO projectMembersDTO)
			throws Exception {
		
		boolean flag = false;
		Integer count = 0;
		if(projectMembersDTO.getBillRate() != null || projectMembers.getCurrentBillRate() != null) {
			if(projectMembers.getCurrentBillRate() == null) {
				if(projectMembersDTO.getBillRate() != null) {
					count = count+1;
				}
			}else {
				if(!projectMembers.getCurrentBillRate().equals(projectMembersDTO.getBillRate())) {
					count = count+1;
				}
			}
			
			if(StringUtils.isBlank(projectMembers.getBillRateCurrency())) {
				if(StringUtils.isNotBlank(projectMembersDTO.getBillRateCurrency())){
					count = count+1;
				}
			}else {
				if(!projectMembers.getBillRateCurrency().equalsIgnoreCase(projectMembersDTO.getBillRateCurrency())) {
					count = count+1;
				}
			}
			
			if(StringUtils.isBlank(projectMembers.getRateType())) {
				if(StringUtils.isNotBlank(projectMembersDTO.getBillRateRateType())) {
					count = count+1;
				}
			}else {
				if(!projectMembers.getRateType().equalsIgnoreCase(projectMembersDTO.getBillRateRateType())) {
					count = count+1;
				}
			}
			
		}
		
		if(projectMembersDTO.getPayRate() != null || projectMembers.getPayRate() != null) {
			
			if(projectMembers.getPayRate() == null) {
				if(projectMembersDTO.getPayRate() != null) {
					count = count+1;
				}
			}else {
				if(!projectMembers.getPayRate().equals(projectMembersDTO.getPayRate())) {
					count = count+1;
				}
			}
			
			if(StringUtils.isBlank(projectMembers.getPayRateCurrency())) {
				if(StringUtils.isNotBlank(projectMembersDTO.getPayRateCurrency())) {
					count = count+1;
				}
			}else {
				if(!projectMembers.getPayRateCurrency().equalsIgnoreCase(projectMembersDTO.getPayRateCurrency())) {
					count = count+1;
				}
			}
			
			if(StringUtils.isBlank(projectMembers.getPayRateType())) {
				if(StringUtils.isNotBlank(projectMembersDTO.getPayRateRateType())) {
					count = count+1;
				}
			}else {
				if(!projectMembers.getPayRateType().equalsIgnoreCase(projectMembersDTO.getPayRateRateType())) {
					count = count+1;
				}
			}
			
			
		}
		
		if(count > 0) {
			flag = true;
		}
		return flag;
	}
	
	private void sendEmailToTeamMembersInProjectSummary(Projects projects, ProjectMembers projectMembers, User loginUser, String managers, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
	  	 StringBuilder fromNameBuilder=new StringBuilder();
	     EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(projectMembers.getProjectMemberCandidteId().getFirstName());
		 emailMessage.setLastName(projectMembers.getProjectMemberCandidteId().getLastName());
		 emailMessage.setToEmail(projectMembers.getProjectMemberCandidteId().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 //setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 StringBuilder sb=new StringBuilder();
		 String name="";
		 if(loginUser.getLastName()!=null)
		 {
		 name=sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString();
		 }
		 else
		 {
		name= sb.append(loginUser.getFirstName()).toString();
		 }
		 globalVarsMap.put("LoginUserFirstName",name);
//	 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
		 globalVarsMap.put("managersName",managers.toString());
//		globalVarsMap.put("managerLastName",projectManager.getLastName());
		 globalVarsMap.put("EmployeeFirstName",emailMessage.getFirstName());
//		 globalVarsMap.put("employeeLastName",emailMessage.getLastName());
		 globalVarsMap.put("ProjectName", projects.getProjectName());
		 globalVarsMap.put("OrgName",loginOrgLocation.getOrgLocationName());
		 globalVarsMap.put("userStartDate",projectMembers.getStartDateStr());
		 globalVarsMap.put("projectEndDate",projectMembers.getEndDateStr());
		 StringBuilder loginURLBuilder=new StringBuilder();
		 globalVarsMap.put("loginURL",loginURLBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		 //calling the sendEmail method of emailScenerioUtil
		 if(null != projects.getOrgLocationsId())
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.MAIL_TEAM_MEMBERS,new ArrayList<EmailAttachment>());
		 
		//calling the sendNotification method of emailScenerioUtil
		EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		//String url = null;
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(pmsUrl).append("/").append("projectSummary?projectUUID="+projects.getProjectUUID()+"&projectClientUUID="+projectMembers.getProjectClientUUID()).toString();
	
		emailScenerioUtil.sendNotification(actionLink, loginUser, projectMembers.getProjectMemberCandidteId().getConsultantUser(), loginOrgLocation, notificationScenario,new HashMap<String,String>());
	}

	@Override
	public ResponseList<ProjectMembers> getProjectMembersListByTimesheetFrequencyOrgLocationsIdForPayroll(Long locationsId, Date startDate, Date endDate, int start, int length)
	{
		return projectMembersDao.getProjectMembersListByTimesheetFrequencyOrgLocationsIdForPayroll(locationsId, startDate, endDate, start, length);
	}

	@Override
	public List<ProjectMembers> getAllProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto) throws Exception {
		return projectMembersDao.getAllProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(dayWiseTimesheetReoprtsDto);
	}

	@Override
	public ProjectMembers getProjectMemberByUserIdAndLoginOrglocationId(Long userId, Long orgLocationId)throws Exception {
		return projectMembersDao.getProjectMemberByUserIdAndLoginOrglocationId(userId, orgLocationId);
	}
	@Override
	public List<String> getProjectMembersTimesheetFrequenciesByOrgLocationsIdAndCandidateId(Long locationsId, Long candidateId){
		return projectMembersDao.getProjectMembersTimesheetFrequenciesByOrgLocationsIdAndCandidateId(locationsId, candidateId);
		
	}
	@Override
	public List<ProjectMembers> getProjectMembersByOrgLocationsIdAndCandidateIdTimesheetFrequency(Long locationsId,Long orgId, Long candidateId,String timesheetFrequency){
		return projectMembersDao.getProjectMembersByOrgLocationsIdAndCandidateIdTimesheetFrequency(locationsId,orgId, candidateId, timesheetFrequency);
	}
	
	
	@Override
	public List<String> getProjectMembersTimesheetFrequenciesByOrgLocationsIdAndMasterCandidateId(Long locationsId,Long orgId, Long masterCandidateId){
		return projectMembersDao.getProjectMembersTimesheetFrequenciesByOrgLocationsIdAndMasterCandidateId(locationsId,orgId, masterCandidateId);
		
	}

	@Override
	public void saveAssignedSharedProjectMembers(ProjectAssignedMembersDTO projectAssignedMembersDTO,
			String projectUUID, User user, OrgLocations orgLocations, String isGlobalUser) throws Exception {
		
		Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
		ProjectClients projectClients = null;
		List<Long> updatedProjectMembers = new ArrayList<Long>();
		if(projectAssignedMembersDTO.getProjectMembers() != null && projectAssignedMembersDTO.getProjectMembers().size() > 0){
			for(ProjectMembersDTO projectMembersDTO : projectAssignedMembersDTO.getProjectMembers()){
				if(projectMembersDTO.getCandidateId() != null){
					ProjectMembers projectMembers = null;
					if(projectMembersDTO.getProjectMemberId() != null){
						projectMembers = projectMembersDao.getProjectMemberById(projectMembersDTO.getProjectMemberId());
						projectMembers.setId(projectMembersDTO.getProjectMemberId());
						updatedProjectMembers.add(projectMembersDTO.getProjectMemberId());
					}else{
						projectMembers = new ProjectMembers();
						projectMembers.setProjects(projects);
					}
					
					Candidate candidate = candidateService.getCandidateByCandidateId(projectMembersDTO.getCandidateId());
					candidate.setId(projectMembersDTO.getCandidateId());
					projectMembers.setProjectMemberCandidteId(candidate);
					MasterCandidate masterCandidate = new MasterCandidate();
					masterCandidate.setId(candidate.getMasterCandidateId().getId());
					projectMembers.setMasterCandidateId(masterCandidate);
					projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocations.getId());
					if(candidate.getConsultantUser() != null){
						Employees employees = employeesService.getEmployeeByUserIdAndOrgLocationsId(candidate.getConsultantUser().getId(), orgLocations.getId());
						projectMembers.setEmployee(employees);
					}
					projectMembers = saveProjectMembers(projectMembersDTO, user, projectClients, projectMembers, orgLocations, projects);
					saveProjectMembersHistory(projectMembers,user,projectClients);
					
					if(!projects.getOrgLocationsId().getId().equals(orgLocations.getId())){
						
						if(StringUtils.isNotBlank(projectAssignedMembersDTO.getProjectClientUUID())) {
			    			ProjectClients pc = projectClientService.getProjectClientsByUUID(projectAssignedMembersDTO.getProjectClientUUID());
			    			Long orgRelationId = pc.getOrgRelationId().getId();
			    			Long orgLocationId = orgLocations.getId();
			    			while(orgRelationId != null){
			    				OrgRelations orgRelations = pc.getOrgRelationId();
			    			
			    				OrgLocations clientOrgLocation = null;
								if(orgRelations.getLocationId().getId().equals(orgLocationId)) {
									clientOrgLocation = orgRelations.getResponderLocationId();
								}else {
									clientOrgLocation = orgRelations.getLocationId();
								}
								
								Candidate clientCandidate = candidateService.findByCandidateEmail(candidate.getEmail(), clientOrgLocation.getId());
								
			    				OrgSpoc orgSpoc = null;
								if(clientCandidate == null) {
									clientCandidate = new Candidate();
									masterCandidate.setId(candidate.getMasterCandidateId().getId());
									clientCandidate.setMasterCandidateId(masterCandidate);
									clientCandidate.setParentCandidateId(candidate);
									clientCandidate.setConsultantUser(candidate.getConsultantUser());
									clientCandidate.setOrgRelationId(orgRelations);
									 Set<OrgSpoc> orgSpocList = orgRelations.getOrgSpocList();
									 for(OrgSpoc spoc : orgSpocList){
										 orgSpoc =  spoc;
									 }
									 clientCandidate.setSpocId(orgSpoc);
									 clientCandidate.setCandidateType("Candidate");
									 clientCandidate.setFirstName(candidate.getFirstName());
									 clientCandidate.setLastName(candidate.getLastName());
									 clientCandidate.setEmail(candidate.getEmail());
									 clientCandidate.setCountry(candidate.getCountry());
									 clientCandidate.setOrgLocationId(clientOrgLocation);
									 clientCandidate.setIsAssociate(Constants.NO);
									 clientCandidate.setDesignationType("C2C");
									
									 clientCandidate.setStatus(1);
									 clientCandidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
									 clientCandidate.setCreatedBy(user.getUserCode());
									 clientCandidate.setCreatedDate(new Date());
									 clientCandidate.setUpdatedDate(new Date());
									
									Candidate savedCandidate = candidateService.addCandidate(clientCandidate);
			    					candidateService.saveOrUpdateCandidateElkDocument(savedCandidate);
			    					
								}
								
								pc = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clientOrgLocation.getId());
								projectMembers = saveSharedProjectMembers(projectMembersDTO,clientCandidate,user,pc,clientOrgLocation,projects);
								saveProjectMembersHistory(projectMembers, user, pc);
								candidate = candidateService.findByCandidateEmail(candidate.getEmail(), clientOrgLocation.getId());
								
								if(pc.getOrgRelationId() != null) {
									orgRelationId = pc.getOrgRelationId().getId();
									orgLocationId = clientOrgLocation.getId();
								}else {
									orgRelationId = null;
								}
								
			    			}
			    			
			    			
			    		}else {
			    			
			    			Candidate clientCandidate = candidateService.findByCandidateEmail(candidate.getEmail(), projects.getOrgLocationsId().getId());
							OrgRelations orgRelations = orgRelationsService.getOrgRelationByResponderLocationIdAndLocationId(projects.getSharedLocationId().getId(),projects.getOrgLocationsId().getId());
				    		if(orgRelations == null) {
				    			orgRelations = orgRelationsService.getOrgRelationByResponderLocationIdAndLocationId(projects.getSharedLocationId().getId(),projects.getOrgLocationsId().getId());
				    		}
				    		
				    		
							OrgSpoc orgSpoc = null;
							if(clientCandidate == null) {
								clientCandidate = new Candidate();
								masterCandidate.setId(candidate.getMasterCandidateId().getId());
								clientCandidate.setMasterCandidateId(masterCandidate);
								clientCandidate.setParentCandidateId(candidate);
								clientCandidate.setConsultantUser(candidate.getConsultantUser());
								clientCandidate.setOrgRelationId(orgRelations);
								 Set<OrgSpoc> orgSpocList = orgRelations.getOrgSpocList();
								 for(OrgSpoc spoc : orgSpocList){
									 orgSpoc =  spoc;
								 }
								 clientCandidate.setSpocId(orgSpoc);
								 clientCandidate.setCandidateType("Candidate");
								 clientCandidate.setFirstName(candidate.getFirstName());
								 clientCandidate.setLastName(candidate.getLastName());
								 clientCandidate.setEmail(candidate.getEmail());
								 clientCandidate.setCountry(candidate.getCountry());
								 clientCandidate.setOrgLocationId(projects.getOrgLocationsId());
								 clientCandidate.setIsAssociate(Constants.NO);
								 clientCandidate.setDesignationType("C2C");
								
								 clientCandidate.setStatus(1);
								 clientCandidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
								 clientCandidate.setCreatedBy(user.getUserCode());
								 clientCandidate.setCreatedDate(new Date());
								 clientCandidate.setUpdatedDate(new Date());
								
								Candidate savedCandidate = candidateService.addCandidate(clientCandidate);
		    					candidateService.saveOrUpdateCandidateElkDocument(savedCandidate);
		    					
							}
							projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), projects.getOrgLocationsId().getId());
							projectMembers = saveSharedProjectMembers(projectMembersDTO,clientCandidate,user,projectClients,projects.getOrgLocationsId(),projects);
							saveProjectMembersHistory(projectMembers, user, projectClients);
			    		}
						
						
					}
					
					if(candidate.getParentCandidateId() != null && (candidate.getDesignationType() != null && candidate.getDesignationType().equalsIgnoreCase("C2C") || candidate.getDesignationType().equalsIgnoreCase("C2C-1099"))){
						Long parentCandidatId = candidate.getParentCandidateId().getId();
						Long orgLocationId = candidate.getOrgLocationId().getId();
						int level = 1;
						while(parentCandidatId != null){
							Candidate cand = candidateService.getCandidateByCandidateId(parentCandidatId);
							projectMembers = projectMembersDao.getProjectMembersByCandidateIdAndProjectId(cand.getId(), projects.getId());
							if(projectMembers == null){
								projectMembers = new ProjectMembers();
							}
							if(cand != null){
								projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), cand.getOrgLocationId().getId());
								if(projectClients == null){
									Candidate clildCandidate = candidateService.getCandidateByParentCandidateIdAndOrgLocationId(parentCandidatId,orgLocationId);
									projectClients = new ProjectClients();
									projectClients.setProjectId(projects);
									projectClients.setOrgRelationId(clildCandidate.getOrgRelationId());
									projectClients.setOrgLocationsId(cand.getOrgLocationId());
									projectClients.setOrgId(cand.getOrgLocationId().getOrg());
									projectClients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
									projectClients.setStatus(1);
									
								
									Candidate pCandidate = candidateService.getCandidateByParentCandidateId(clildCandidate.getId());
									if(pCandidate != null && pCandidate.getOrgRelationId() != null){
										ProjectClients pClient = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), clildCandidate.getOrgLocationId().getId());
										if(pClient != null && pClient.getLevel() != null){
											level = pClient.getLevel()+1;
										}else {
											level = level+1;
										}
									}
									else{
										level = level+1;
									}
									
									
									projectClients.setLevel(level);
									projectClients.setCreatedDate(new Date());
									projectClients.setCreatedBy(user);
									projectClients.setUpdatedDate(new Date());
									projectClients.setUpdatedBy(user);
									
									//prepare ProjectApproverHierarchy
									Set<ProjectApproverHierarchy> projectApproverHierarchyList = new HashSet<ProjectApproverHierarchy>();
									ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
									pah.setProjectId(projects);
									pah.setProjectClientsId(projectClients);
									
									if(cand.getOrgLocationId().getUser() != null) {
										pah.setApproverUserId(cand.getOrgLocationId().getUser());
									}else {
										OrgSpoc orgSpoc = orgSpocService.getSpocByOrgRelationIdForNewVendor(clildCandidate.getOrgRelationId().getId());
										pah.setApproverUserId(orgSpoc.getVendorUser());
									}
									
									
									pah.setLevel(1);
									pah.setStatus("1");
									//pah.setApprovalMechanismType(Constants.PARALLEL);
									projectApproverHierarchyList.add(pah);
									projectClients.setProjectApproverHierarchyList(projectApproverHierarchyList);
									
									
									//prepare ExpenseApproverHierarchy
									Set<ExpenseProjectApproverHierarchy> expenseApproverHierarchyList = new HashSet<ExpenseProjectApproverHierarchy>();
									ExpenseProjectApproverHierarchy eah = new ExpenseProjectApproverHierarchy();
									eah.setProjectId(projects);
									eah.setProjectClientsId(projectClients);
									
									if(cand.getOrgLocationId().getUser() != null) {
										eah.setApproverUserId(cand.getOrgLocationId().getUser());
									}else {
										OrgSpoc orgSpoc = orgSpocService.getSpocByOrgRelationIdForNewVendor(clildCandidate.getOrgRelationId().getId());
										eah.setApproverUserId(orgSpoc.getVendorUser());
									}
									
									
									eah.setLevel(1);
									eah.setStatus("1");
									//eah.setApprovalMechanismType(Constants.PARALLEL);
									expenseApproverHierarchyList.add(eah);
									projectClients.setExpenseProjectApproverHierarchyList(expenseApproverHierarchyList);
									
									projectClientService.storeProjectClients(projectClients);
								}
								if(cand.getConsultantUser() != null){
									Employees clientEmployee = employeesService.getEmployeeByUserIdAndOrgLocationsId(cand.getConsultantUser().getId(), cand.getOrgLocationId().getId());
									projectMembers.setEmployee(clientEmployee);
								}
								projectMembers.setProjectMemberCandidteId(cand);
								projectMembers.setMasterCandidateId(cand.getMasterCandidateId());
								projectMembers = saveProjectMembers(projectMembersDTO, user, projectClients, projectMembers, orgLocations, projects);
								saveProjectMembersHistory(projectMembers,user,projectClients);
								if(cand.getParentCandidateId() != null){
									parentCandidatId = cand.getParentCandidateId().getId();
									orgLocationId = cand.getOrgLocationId().getId();
								}else{
									parentCandidatId = null;
								}
							}
						}
					}
				  
					List<ProjectApproverHierarchy> approverHierarchy = projectApproverHierarchyService.getApproverUsersByProjectId(projects.getId());
					   StringBuilder managers = new StringBuilder();
					  
					   for (ProjectApproverHierarchy projectApproverHierarchy : approverHierarchy) {
						 StringBuilder fullname=new StringBuilder();
						 if(projectApproverHierarchy.getApproverUserId().getLastName()!=null && projectApproverHierarchy.getApproverUserId().getLastName()!="")
						   managers=managers.append(fullname.append(projectApproverHierarchy.getApproverUserId().getFirstName()).append(" ").append(projectApproverHierarchy.getApproverUserId().getLastName())).append(" & ");
						 else
							 managers=managers.append(projectApproverHierarchy.getApproverUserId().getFirstName()).append(" & ");
					}
					  String projectManagers=managers.toString();
					  String projectmanagerss= managers.substring(0, projectManagers.length()-2);
				 
					  //dont open the code By Laxman
//					  sendEmailToTeamMembersInProjectSummary(projects, projectMembers, user, projectmanagerss, orgLocations, orgLocations.getOrg().getOrgCode());
				}
			}
		}
		
		projects.setIsProjectMembersFilled(Constants.YES);
		projectsService.saveProject(projects);
		
		
	}

	@Override
	public List<ProjectMembers> getAllProjectMembersListByMasterCandidateId(Long masterCandidateId, Long projectId)
			throws Exception {
		return projectMembersDao.getAllProjectMembersListByMasterCandidateId(masterCandidateId, projectId);
	}

	@Override
	public List<ProjectMembers> getAllActiveProjectsByUserIdAndOrgLocationId(Long userId, Long orgLocationId)
			throws Exception {
		return projectMembersDao.getAllActiveProjectsByUserIdAndOrgLocationId(userId, orgLocationId);
	}

	@Override
	public ProjectMembers getProjectMemberByCandidateIdAndProjectIdAndOrgLocationId(Long candidateId, Long projectId,
			Long orgLocationId) throws Exception {
		return projectMembersDao.getProjectMemberByCandidateIdAndProjectIdAndOrgLocationId(candidateId, projectId, orgLocationId);
	}

	@Override
	public List<ProjectMembers> getProjectMembersListByCandidateIdAndProjectClientId(Long candidateId,
			List<Long> projectClientsId) throws Exception {
		return projectMembersDao.getProjectMembersListByCandidateIdAndProjectClientId(candidateId, projectClientsId);
	}

	@Override
	public List<Long> getAllProjectMembersIdsByProjectIdAndOrgLocationId(Long projectId, Long orglocationId)
			throws Exception {
		return projectMembersDao.getAllProjectMembersIdsByProjectIdAndOrgLocationId(projectId, orglocationId);
	}

	@Override
	public ResponseList<MarginSheetReportDTO> getProjectMemeberByClientProjectAndOrglocation(Long orglocationId,Date[] searchDates,MarginSheetReportDTO marginSheetReportDTO)
	{
		return projectMembersDao.getProjectMemeberByClientProjectAndOrglocation(orglocationId,searchDates,marginSheetReportDTO);

	}

	@Override
	public ProjectMembers getProjectMembersByMasterCandiateIdAndProjectClientId(Long masterCandidateId,
			Long projectClientsId) throws Exception {
		return projectMembersDao.getProjectMembersByMasterCandiateIdAndProjectClientId(masterCandidateId, projectClientsId);
	}

	@Override
	public ProjectMembers getAllProjectMembersByMasterCandidateIdAndProjectIdLocationId(Long masterCandidateId,
			Long projectId, Long locationId, String isGlobalUser) throws Exception {
		return projectMembersDao.getAllProjectMembersByMasterCandidateIdAndProjectIdLocationId(masterCandidateId, projectId, locationId, isGlobalUser);
	}

	@Override

	public List<Candidate> getAllAvailableAssociates(Long orgLocationId) {
	List<Candidate> candidateList = new LinkedList<>();
	List<Object[]> objList = projectMembersDao.getAllAvailableAssociates( orgLocationId);
	if(objList != null && !objList.isEmpty()) {
	for(Object[] obj : objList) {
	Candidate candidate = new Candidate();
	candidate.setId(Long.parseLong(String.valueOf(obj[0])));
	candidate.setFirstName(obj[1] != null ? String.valueOf(obj[1]) : null);
	candidate.setMiddelName(obj[2] != null ? String.valueOf(obj[2]) : null);
	candidate.setLastName(obj[3] != null ? String.valueOf(obj[3]) : null);
	candidateList.add(candidate);
	}
	}
	return candidateList;
	}

	@Override
	public ProjectMembers getProjectMembersByMasterCandidateIdAndProjectClientId(Long masterCandidateId,
			Long projectClientsId) throws Exception {
		return projectMembersDao.getProjectMembersByMasterCandidateIdAndProjectClientId(masterCandidateId, projectClientsId);
	}

	@Override
	public List<ProjectMembers> getAllProjectsAssigendToCandidateIdAndStatus(Long candidateId,String status) throws Exception {
		return projectMembersDao.getAllProjectsAssigendToCandidateIdAndStatus(candidateId,status);
	}

	@Override
	public List<ProjectMembers> getAllResourceTimeEntryProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(
			DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto) throws Exception {
		return projectMembersDao.getAllResourceTimeEntryProjectMembersBasedOnProjectIdStartDateEndDateAndEmploymentType(dayWiseTimesheetReoprtsDto);
	}



}