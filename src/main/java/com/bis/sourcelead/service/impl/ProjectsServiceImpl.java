package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class ProjectsServiceImpl implements ProjectsService{
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsServiceImpl.class);

	@Autowired
	private ProjectsDao projectsDao;
	
	@Autowired
	private ProjectApproverHierarchyDao 	projectApproverHierarchyDao;
	  
	@Autowired
	private EmailScenarioService emailScenarioService;
	    
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;
	    
	@Autowired
	private UserService userService;

	@Override
	public Projects saveProject(Projects projects) throws Exception{
		logger.debug("In ProjectsServiceImpl class saveProject method...");
		return projectsDao.saveProject(projects);
	}

	@Override
	public Projects updateProject(Projects projects) throws Exception {
		logger.debug("In ProjectsServiceImpl class updateProject method...");
		return projectsDao.updateProject(projects);
	}

	@Override
	public Projects getProjectById(Long id) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProject method...");
		return projectsDao.getProjectById(id);
	}
	
	@Override
	public List<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectByOrgLocationsId method...");
		return projectsDao.getProjectByOrgLocationsId(orgLocationsId);
	}
	
	@Override
	public List<Projects> getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(Long orgLocationsId, Long loginUserId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate method...");
		return projectsDao.getProjectByOrgLocationsIdAndEndDateGraterthanCurrentDate(orgLocationsId, loginUserId);
	}
	
	@Override
	public Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getPrimaryProjectByOrgLocationsId method...");
		return projectsDao.getPrimaryProjectByOrgLocationsId(orgLocationsId);
	}

	@Override
	public Boolean deleteProjectById(Long Id) throws Exception {
		logger.debug("In ProjectsServiceImpl class deleteProject method...");
		Boolean isDeleted = projectsDao.deleteProjectById(Id);
		return isDeleted;
	}
	
	@Override
	public Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class validateProjectSequeneNumber method...");
		Boolean flag = projectsDao.validateProjectSequeneNumber(projectSequeneNumber,orgLocationsId);
		return flag;
	}

	@Override
	public Collection<Projects> getAllProjects() throws Exception {
		logger.debug("In ProjectsServiceImpl class getAllProjects method...");
		Collection<Projects> projectsList = projectsDao.getAllProjects();
		return projectsList;
	}
	
	public Collection<Projects> getAllLoginUserProjectsList(List<Long> projectIds,Long orgLocationId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getAllProjectsAndManagers method...");
		Collection<Projects> projectsList = projectsDao.getAllLoginUserProjectsList(projectIds,orgLocationId);
		return projectsList;
	}
	
	@Override
	public Integer projectIdValidation(Long projectId) throws Exception {
		logger.debug("In ProjectsServiceImpl class deleteProject method...");
		Integer validation = projectsDao.projectIdValidation(projectId);
		return validation;
	}

	@Override
	public Collection<Projects> getAllClientProjectsByOrgRelationId(Long orgRelationId, Long locationId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getAllClientProjectsByOrgRelationId method...");
		Collection<Projects> clientProjectsList = projectsDao.getAllClientProjectsByOrgRelationId(orgRelationId,locationId);
		return clientProjectsList;
	}

	@Override
	public Integer updateStartDateOfProjectId(Date startDate , Long projectId) {
		return projectsDao.updateStartDateOfProjectId(startDate,projectId);
	}

	@Override
	public Integer updateEndDateOfProjectId(Date endDate, Long projectId) {
		return projectsDao.updateEndDateOfProjectId(endDate,projectId);
	}
	
	@Override
	public List<Long> getApprovarAssignedProjects(Long userId, Long orgLocationId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getApprovarAssignedProjects method...");
		List<Long> projectIdsList = projectsDao.getApprovarAssignedProjects(userId, orgLocationId);
		return projectIdsList;
	}

	@Override
	public Projects getProjectByProjectSequenceNumber(String projectSequeneNumber) throws Exception {
		
		return projectsDao.getProjectByProjectSequenceNumber(projectSequeneNumber);
	}
	
	

	@Override
	public List<Projects> getProjectsBasedOnClientsAndStatusSelectionInFilters(Long userId, String clients,
			String status,Long orgLocationsId) throws Exception {
		logger.debug("In ProjectsServiceImpl class getProjectsBasedOnClientsAndStatusSelectionInFilters method...");
    	List<Projects> projectList = projectsDao.getProjectsBasedOnClientsAndStatusSelectionInFilters(userId,clients,status,orgLocationsId);
		 for (Projects projects :projectList ){
	    	    projects.setStartDateStr(Constants.DF_DMY.format(projects.getStartDate()));
	    	    projects.setEmployeeStartDateStr(Constants.DF_YMD.format(projects.getStartDate()));
			    if(projects.getEndDate() != null){
			    	 projects.setEndDateStr(Constants.DF_DMY.format(projects.getEndDate()));
			    	 projects.setEmployeeEndDateStr(Constants.DF_YMD.format(projects.getEndDate()));
			    }
			    if(projects.getOrgRelationId()!=null) {
	                projects.setClient(projects.getOrgRelationId().getClientLocationId().getOrg().getOrgName());
	            }
			    Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyDao.getApproverUsersByProjectId(projects.getId());
			    for(ProjectApproverHierarchy approverHierarchy :projectApproverHierarchies){
			    	if(approverHierarchy.getLevel()==1){
			    		projects.setManagerLevel1(approverHierarchy.getApproverUserId().getFirstName()+" "+approverHierarchy.getApproverUserId().getLastName());
			    	}else if(approverHierarchy.getLevel()==2){
			    		projects.setManagerLevel2(approverHierarchy.getApproverUserId().getFirstName()+" "+approverHierarchy.getApproverUserId().getLastName());
			    	}
			    }
	    }
		return projectList;
		
	}


	@Override
	public List<Projects> getProjectByOrgLocationsIdAndUserId(Long id, Long userId) {
		// TODO Auto-generated method stub
		return projectsDao.getProjectByOrgLocationsIdAndUserId(id,userId);
	}


	@Override
	public Projects getProjectByIdAndLoginOrgId(Long projectId, Long orgLocationId) throws Exception {
		// TODO Auto-generated method stub
		return projectsDao.getProjectByIdAndLoginOrgId(projectId, orgLocationId);
	}

	
	

    @Override
	public Integer getUserAssignedProjectsCount(Long userId, Long orgLocationId) {
		// TODO Auto-generated method stub
		return projectsDao.getUserAssignedProjectsCount(userId, orgLocationId);
	}

	@Override
	public List<User> getAllUesrTimeSheetSubmissionStatus() throws Exception {
		
		return projectsDao.getAllUesrTimeSheetSubmissionStatus();
	}

	@Override
	public void userEmailNotificationForTimeShhetSubmission(User user) throws Exception {
		
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(user.getFirstName());
		 emailMessage.setLastName(user.getLastName());
		 emailMessage.setToEmail(user.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(user.getFirstName()+" "+user.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("lastName",emailMessage.getLastName());
		 globalVarsMap.put("administratorFirstName", user.getFirstName());
		 globalVarsMap.put("administratorLastName",user.getLastName());
		 globalVarsMap.put("role",user.getUserType());
		 globalVarsMap.put("userEmailId",user.getEmail());
		
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIMESHEET_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
		 sendWeeklyBaseNotiFication(user);
		
		//projectsDao.userEmailNotificationForTimeShhetSubmission(user);
	}

//	@Override
//	public List<SubmissionApproverHierarchy> getApproveUserForTimeSheets(Long userId) throws Exception {
//		return projectsDao.getApproveUserForTimeSheets(userId);
//	}
	

	private void sendWeeklyBaseNotiFication(User user) throws Exception {
		List<Employees> empList = userService.getAllUserOrgCodeAndLocationCodeTimeSheetNotification(user.getId());
	    Employees emp = empList.get(0);
	    logger.info("Employee Location code :"+emp.getOrgLocations().getLocationCode());
		logger.info("Employee org code :"+emp.getOrgLocations().getOrg().getOrgCode());
	    User toUser = emp.getUser();
	    List<SubmissionApproverHierarchy> sah = projectsDao.getApproveUserForTimeSheets(user.getId());
	    User fromUser = sah.get(0).getHeirarchyId().getApproverUserId();
	    logger.info("Aprover for the Employee "+user.getFirstName()+" is :"+fromUser.getFirstName()+"("+fromUser.getUserType()+")");
	    EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.WEEK_TIME_SHEET_SUBMISSION,Constants.ENTITY_TYPE_NOTIFICATION);
		    Map<String, String> globalVarsMap = new HashMap<String, String>();
		    globalVarsMap.put("firstName", user.getFirstName());
		    globalVarsMap.put("lastName",user.getLastName());
			String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
			emailScenario.setBody(body);
			emailScenario.setOrgCode(emp.getOrgLocations().getOrg().getOrgCode());
			String actionLink = "";
			OrgLocations loginOrgLocation = new OrgLocations();
		loginOrgLocation.setLocationCode(emp.getOrgLocations().getLocationCode());
			emailScenerioUtil.sendNotification(actionLink, fromUser, toUser, loginOrgLocation, emailScenario);		
	}

	@Override
	public List<User> getAllUesrPrevoiusWeekTimeSheetSubmissionStatus() throws Exception {
		
		return projectsDao.getAllUesrPrevoiusWeekTimeSheetSubmissionStatus();
	}

}
