package com.bis.sourcelead.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectBudgetDao;
import com.bis.sourcelead.dto.ProjectBudgetRequestDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class ProjectBudgetServiceImpl implements ProjectBudgetService{

	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	ProjectBudgetDao projectBudgetDao;
 
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectsService  projectsService;
	
	@Override
	public ProjectBudget saveProjectBudget(ProjectBudget projectBudget) throws Exception {
		return projectBudgetDao.saveProjectTaskComments(projectBudget);
	}

	@Override
	public ProjectBudget getProjectBudgetsById(Long projectId) throws Exception {
		return projectBudgetDao.getProjectBudgetsById(projectId);
	}

	@Override
	public List<ProjectBudget> getProjectBudgetByProjectTaskId(Long Id) throws Exception {
		return projectBudgetDao.getProjectBudgetByProjectTaskId(Id);
	}

	@Override
	public Long getProjectBudgetReqestedIdByProjectId(Long projectId, Integer generatedIdcount) {
		return projectBudgetDao.getProjectBudgetReqestedIdByProjectId(projectId, generatedIdcount);
	}

	@Override
	public ProjectBudget getProjectBudgetsByProjectId(Long projectId) throws Exception {
		return projectBudgetDao.getProjectBudgetsByProjectId(projectId);
	}
	
	@Override
	public List<ProjectBudget> getProjectBudgetListByOrgLocationIdAndBudgetStatus(Long orgLocationId, String budgetStatus) throws Exception {
		return projectBudgetDao.getProjectBudgetListByOrgLocationIdAndBudgetStatus(orgLocationId, budgetStatus);
	}
	
	@Override
	public List<ProjectBudget> getProjectBudgetListByOrgLocationId(Long orgLocationId) throws Exception {
		return projectBudgetDao.getProjectBudgetListByOrgLocationId(orgLocationId);
	}
	
	@Override
	public List<ProjectBudget> getProjectBudgetByProjectTaskIdAndApprovedStatus(Long projectId,String filterDate, Date startDateUTC, Date endDateUTC) throws Exception {
		return projectBudgetDao.getProjectBudgetByProjectTaskIdAndApprovedStatus(projectId, filterDate, startDateUTC, endDateUTC);
	}
	
	@Override
	public void saveProjectBudgetRequset(ProjectBudgetRequestDTO projectBudgetRequestDTO, User user) throws Exception {
		ProjectBudget projectBudget = null;
	      Projects projects = projectsService.getProjectByProjectUUID(projectBudgetRequestDTO.getProjectUUID());
		   if(StringUtils.isNotBlank(projectBudgetRequestDTO.getBudgetRequestedId())) {
			   projectBudget = projectBudgetDao.getProjectBudgetsById(Long.valueOf(projectBudgetRequestDTO.getBudgetRequestedId()));
			   projectBudget.setUpdatedDate(new Date());
			   projectBudget.setUpdateUserId(user);
		   }else {
			   projectBudget = new ProjectBudget();
			   projectBudget.setCreateUserId(user);
				projectBudget.setCreatedDate(new Date());
				projectBudget.setBudgetRequestedId(generatebudgetRequestId(projects));
		   }
	    	projectBudget.setProjectId(projects);
	    	projectBudget.setEstimatedBudgetCurrency(projectBudgetRequestDTO.getEstimatedBudgetCurrency());
			projectBudget.setBudgetStatus(Constants.BUDGET_REQUEST_WAITING_FOR_APPROVAL);
			projectBudget.setEstimatedHours(projectBudgetRequestDTO.getEstimatedHours());
			projectBudget.setEstimatedBudgetValue(projectBudgetRequestDTO.getEstimatedBudget());
			projectBudget.setBudgetDescription(projectBudgetRequestDTO.getBaseLine());
			if(StringUtils.isNotBlank(projectBudgetRequestDTO.getBudgetApprover())) {
		     User use = userService.getUserById(Long.valueOf(projectBudgetRequestDTO.getBudgetApprover()));
				projectBudget.setApproverUserId(use);
			}else {
				projectBudget.setApproverUserId(null);
			}
			projectBudgetDao.saveProjectTaskComments(projectBudget);
			
			if(user.getId().longValue() != Long.valueOf(projectBudgetRequestDTO.getBudgetApprover())) {
				sendBudgetApproverEmail(user,projectBudgetRequestDTO,projects);
			}
	}
	 
	private void sendBudgetApproverEmail(User loginUser, ProjectBudgetRequestDTO projectBudgetRequestDTO, Projects projects)throws Exception {
		User approverUse = userService.getUserById(Long.valueOf(projectBudgetRequestDTO.getBudgetApprover()));
		EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFirstName(loginUser.getFirstName());
        emailMessage.setLastName(loginUser.getLastName());
        emailMessage.setToEmail(approverUse.getEmail());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setCreatedBy(loginUser.getUserCode());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
       
        Map<String, String> globalVarsMap = new HashMap<String, String>();
        globalVarsMap.put("projectName", projects.getProjectName());
        globalVarsMap.put("approverName", approverUse.getFullName());
        globalVarsMap.put("loginUserName", loginUser.getFullName());
        
        globalVarsMap.put("descrption", projectBudgetRequestDTO.getBaseLine());
        globalVarsMap.put("estimatedBudget", projectBudgetRequestDTO.getEstimatedBudgetCurrency() +" "+ projectBudgetRequestDTO.getEstimatedBudget());
        globalVarsMap.put("estimatedHours", projectBudgetRequestDTO.getEstimatedHours());
        
        StringBuilder linkBuild = new StringBuilder();
        globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
        
        EmailScenario   emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST,Constants.ENTITY_TYPE_EMAIL);
        EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
        
        String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
        notificationScenario.setBody(notificationbody);
        
        //covert subject,body and signature
        String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
        String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
        String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
        String url = sourceleadUrl;
       
        InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
       
        String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, projects.getOrgLocationsId().getOrg(),projects.getOrgLocationsId());
        emailMessage.setSubject(subject);
        emailMessage.setMessageBody(emailTemplate);
       
        emailScenerioUtil.sendEmail(emailMessage,projects.getOrgLocationsId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
        String actionLink = null;
         
        emailScenerioUtil.sendNotification(actionLink, loginUser, approverUse, projects.getOrgLocationsId(), notificationScenario);
		
	}

	
	@Override
	public void sendEmailForBugetRequestApproversByAcceptAndRejectAndOnHold(User loginUser, ProjectBudget projectBudget) throws Exception {
		User approverUse = userService.getUserById(Long.valueOf(projectBudget.getApproverUserId().getId()));
		EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFirstName(loginUser.getFirstName());
        emailMessage.setLastName(loginUser.getLastName());
        emailMessage.setToEmail(approverUse.getEmail());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setCreatedBy(loginUser.getUserCode());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
       
        Map<String, String> globalVarsMap = new HashMap<String, String>();
        globalVarsMap.put("projectName", projectBudget.getProjectId().getProjectName());
        globalVarsMap.put("approverName", approverUse.getFullName());
        globalVarsMap.put("loginUserName", loginUser.getFullName());
        
        EmailScenario   emailScenario = null;
        EmailScenario  notificationScenario = null;
        if(projectBudget.getBudgetStatus().equalsIgnoreCase(Constants.BUDGET_REQUEST_APPROVED)) {
        	emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST_ACCEPTED,Constants.ENTITY_TYPE_EMAIL);
            notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST_ACCEPTED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
        }else if(projectBudget.getBudgetStatus().equalsIgnoreCase(Constants.BUDGET_REQUEST_REJECTED)) {
        	globalVarsMap.put("rejectedReason", projectBudget.getRejectedReason());
        	emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST_REJECTED_EMAIL,Constants.ENTITY_TYPE_EMAIL);
            notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST_REJECTED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
        }else if(projectBudget.getBudgetStatus().equalsIgnoreCase(Constants.BUDGET_REQUEST_ON_HOLD)) {
        	globalVarsMap.put("rejectedReason", projectBudget.getRejectedReason());
        	emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST_ONHOLD_EMAIL,Constants.ENTITY_TYPE_EMAIL);
            notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST_ONHOLD_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
        }
        String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
        notificationScenario.setBody(notificationbody);
        
        //covert subject,body and signature
        String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
        String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
        String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
        String url = sourceleadUrl;
       
        InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
       
        String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, projectBudget.getProjectId().getOrgLocationsId().getOrg(),projectBudget.getProjectId().getOrgLocationsId());
        emailMessage.setSubject(subject);
        emailMessage.setMessageBody(emailTemplate);
       
        emailScenerioUtil.sendEmail(emailMessage,projectBudget.getProjectId().getOrgLocationsId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
        String actionLink = null;
        emailScenerioUtil.sendNotification(actionLink, loginUser, approverUse, projectBudget.getProjectId().getOrgLocationsId(), notificationScenario);
		
	}
	
	
	private String generatebudgetRequestId(Projects projects) {
		String companyShortCode = null;
       	Long number = 1L;
   		String candidateId = null;
   		Integer generatedIdcount = 0;
   		StringBuilder sbuilder = new StringBuilder();
   		sbuilder.append(projects.getProjectShortCode()).append("BR");
   		String generatedUsreId = sbuilder.toString();
   		for(int i=0;i< generatedUsreId.length();i++){
   			char ch = generatedUsreId.charAt(i);
   			if(Character.isAlphabetic(ch)){
   				generatedIdcount++;
   			}
   		}
   		// in dao to count from next character
   		generatedIdcount = generatedIdcount+1;
   		Long checkNumber = null;
   		if(projects.getId() != null) {
   			 checkNumber = projectBudgetDao.getProjectBudgetReqestedIdByProjectId(projects.getId(),generatedIdcount);
   		}
   		 
   		if(checkNumber == null){
   			candidateId = number.toString();
   		}else{
   		Long canId = checkNumber+1;
   		candidateId = canId.toString();
   		}
   		final DecimalFormat decimalFormat = new DecimalFormat("0");                
           String convetredCandidateId = decimalFormat.format(Long.parseLong(candidateId));
           StringBuffer sb = new StringBuffer();
           if(projects.getProjectShortCode() != null && !"".equalsIgnoreCase(projects.getProjectShortCode())){
           	 companyShortCode = projects.getProjectShortCode();
           }
   		return sb.append(companyShortCode).append("BR").append(convetredCandidateId).toString();
	}

	
}
