package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

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
	 public EmployeesService employeesService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 public ProjectApproverHierarchyService projectApproverHierarchyService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDaoImpl.class);
	
	@Override
	public ProjectMembers saveProjectMember(ProjectMembers projectMembers) throws Exception{
		logger.debug("In ProjectMembersServiceImpl class saveProjectMember method");
		return projectMembersDao.saveProjectMember(projectMembers);
	}

	@Override
	public ProjectMembers updateProjectMember(ProjectMembers projectMembers) throws Exception {
		logger.debug("In ProjectMembersServiceImpl class updateProjectMember method...");
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
			 if(pmh == null){
				 pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMembers.getId());
			 }
			 if(pmh != null && pmh.getProjectAssignedDate()!=null){
				 projectMembers.setStartDate(Constants.DF_YMD.format(pmh.getProjectAssignedDate()));
             }
        	 if(pmh != null && pmh.getProjectTerminationDate()!=null){
        		 projectMembers.setEndDate(Constants.DF_YMD.format(pmh.getProjectTerminationDate()));
        	 }
        	 
        	 /*projectMembers.setStartDate(com.bis.sourcelead.constants.Constants.DF_YMD.format(projectMembers.getProjectAssignedDate()));
			if (projectMembers.getProjectEndDate() != null) {
				projectMembers.setEndDate(com.bis.sourcelead.constants.Constants.DF_YMD.format(projectMembers.getProjectEndDate()));
			}*/
			if (projectMembers.getProjects().getEndDate() != null) {
				projectMembers.setProjectEndDateStr(com.bis.sourcelead.constants.Constants.DF_YMD.format(projectMembers.getProjects().getEndDate()));
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
		
		ProjectMembers pm = new ProjectMembers();
		  if(projectId != null){
			  
				Projects projects = projectsService.getProjectById(projectId);
				Employees emp = employeesService.getEmployee(employeeId.toString());
			 	pm.setEmployee(emp);
				pm.setProjects(projects);
				String pattern = "yyyy-MM-dd";
	            SimpleDateFormat format = new SimpleDateFormat(pattern);
	            
	            HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
				
				ProjectMembersHistory pmh = new ProjectMembersHistory();
				pmh.setProjectMemberId(pm);
				pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
	            if(StringUtils.isNotEmpty(startDate)){
	            	 Date currentStartDate = format.parse(startDate);
	            	 pmh.setProjectAssignedDate(currentStartDate);
	            }
	            else{
	            	pmh.setProjectAssignedDate(projects.getStartDate());
	            }
	            if(StringUtils.isNotEmpty(endDate)){
	            	if(endDate != null && endDate != "--"){
	            		Date currentEndDate = format.parse(endDate);
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
				pm.setCreatedDate(new Date());
				pm.setUpdatedDate(new Date());
				pm.setCreatedBy(user.getUserCode());
				pm.setUpdatedBy(user.getUserCode());
				pm.setMemberHistoryList(pmhList);
				saveProjectMember(pm);
				commonUtil.storeTimesheetSubmissionRecords(emp.getUser());
				
				/**
			     * This method for send Email to team member while assign employee from project dashboard
			     */
				ProjectApproverHierarchy projectApproverHierarchy = projectApproverHierarchyService.getLevelOneProjectApproverByProjectId(projects.getId());
				
				if(!isNullOrEmpty(mailtoMembers) && ("YES".equals(mailtoMembers))){
					sendEmailToTeamMembers(projects, emp, user, projectApproverHierarchy, loginOrgLocation, loginOrgCode);
				}
		  }
			return null;
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
	  	
	     EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(emp.getUser().getFirstName());
		 emailMessage.setLastName(emp.getUser().getLastName());
		 emailMessage.setToEmail(emp.getUser().getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());

		 //setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("loginUserFirstName",loginUser.getFirstName());
		 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
		 globalVarsMap.put("managerFirstName",projectManager.getFirstName());
		 globalVarsMap.put("managerLastName",projectManager.getLastName());
		 globalVarsMap.put("employeeFirstName",emailMessage.getFirstName());
		 globalVarsMap.put("employeeLastName",emailMessage.getLastName());
		 globalVarsMap.put("projectName", projects.getProjectName());
		 globalVarsMap.put("orgName",loginOrgLocation.getOrg().getOrgName());
		 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
		 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
		 
		 
		//calling the sendNotification method of emailScenerioUtil
		EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		//String url = null;
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, emp.getUser(), loginOrgLocation, notificationScenario);
	}
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
}