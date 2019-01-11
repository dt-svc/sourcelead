package com.bis.sourcelead.service.impl;

import java.math.BigDecimal;
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
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class ProjectBudgetServiceImpl implements ProjectBudgetService{

	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
		
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
	
	@Autowired
	ProjectClientService projectClientService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Override
	public ProjectBudget saveProjectBudget(ProjectBudget projectBudget) throws Exception {
		return projectBudgetDao.saveProjectTaskComments(projectBudget);
	}

	@Override
	public ProjectBudget getProjectBudgetsById(Long projectId) throws Exception {
		return projectBudgetDao.getProjectBudgetsById(projectId);
	}

	@Override
	public List<ProjectBudget> getProjectBudgetByProjectTaskId(Long Id, Long loginOrgLocationId) throws Exception {
		return projectBudgetDao.getProjectBudgetByProjectTaskId(Id,loginOrgLocationId);
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
	public 	List<ProjectBudget>  getProjectBudgetListByOrgLocationIdAndBudgetStatus(Long approverUserId,Long orgLocationId,String budgetStatus, String userRoleName) throws Exception{
		return projectBudgetDao.getProjectBudgetListByOrgLocationIdAndBudgetStatus(approverUserId,orgLocationId, budgetStatus,userRoleName);
	}
	
	@Override
	public 	List<ProjectBudget>  getProjectBudgetListByOrgLocationId(Long approverUserId,Long orgLocationId,String userRoleName) throws Exception{
		return projectBudgetDao.getProjectBudgetListByOrgLocationId(approverUserId,orgLocationId,userRoleName);
	}
	
	@Override
	public List<ProjectBudget> getProjectBudgetByProjectTaskIdAndApprovedStatus(Long projectId, Long orgLocationId) throws Exception {
		return projectBudgetDao.getProjectBudgetByProjectTaskIdAndApprovedStatus(projectId, orgLocationId);
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
			projectBudget.setStatus(Constants.RECORD_ACTIVE);
			projectBudget.setBudgetDescription(projectBudgetRequestDTO.getBaseLine());
			if(StringUtils.isNotBlank(projectBudgetRequestDTO.getBudgetApprover())) {
		     User use = userService.getUserById(Long.valueOf(projectBudgetRequestDTO.getBudgetApprover()));
				projectBudget.setApproverUserId(use);
			}else {
				projectBudget.setApproverUserId(null);
			}
			projectBudgetDao.saveProjectTaskComments(projectBudget);
			
			if(user.getId().longValue() != Long.valueOf(projectBudgetRequestDTO.getBudgetApprover())) {
				sendBudgetApproverEmail(user,projectBudget,projects);
			}
	}
	 
	private void sendBudgetApproverEmail(User loginUser, ProjectBudget projectBudget, Projects projects)throws Exception {
		User approverUse = userService.getUserById(projectBudget.getApproverUserId().getId());
		ProjectClients pc = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(),projects.getOrgLocationsId().getId());
		EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFirstName(loginUser.getFirstName());
        emailMessage.setLastName(loginUser.getLastName());
        emailMessage.setToEmail(approverUse.getEmail());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setCreatedBy(loginUser.getUserCode());
        emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
        
        
        // URL for page redirection
        String redirectURL = "/projectSummary-params-projectUUID="+projectBudget.getProjectId().getProjectUUID()+"-and-projectClientUUID="+pc.getUuid()+"-and-tabType=projectMembers";
        Long redirectOrgLocationId = projects.getOrgLocationsId().getId();
       
        Map<String, String> globalVarsMap = new HashMap<String, String>();
        globalVarsMap.put("projectName", projects.getProjectName());
        globalVarsMap.put("approverName", approverUse.getFullName());
        globalVarsMap.put("loginUserName", loginUser.getFullName());
        
        globalVarsMap.put("descrption", projectBudget.getBudgetDescription());
        DecimalFormat df = new DecimalFormat("0.##");   
        globalVarsMap.put("estimatedBudget", projectBudget.getEstimatedBudgetCurrency() +" "+ BigDecimal.valueOf(projectBudget.getEstimatedBudgetValue()));
        globalVarsMap.put("estimatedHours", String.valueOf(df.format(projectBudget.getEstimatedHours())));
        
        StringBuilder linkBuild = new StringBuilder();
        globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append
                             ("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Budget Request</div></a>").toString());
        
        EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
        
        String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
        notificationScenario.setBody(notificationbody);
        String actionLink = null;
        StringBuilder actionLinkBuilder = new StringBuilder();

        commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,projects.getOrgLocationsId().getOrg(),projects.getOrgLocationsId(),Constants.BUDGET_REQUEST,new ArrayList<EmailAttachment>());
//        commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,userRole.getOrgLocations().getOrg(),userRole.getOrgLocations(),Constants.INVITE_USER);
        actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("projectSummary?projectUUID="+projectBudget.getProjectId().getProjectUUID()+"&projectClientUUID="+pc.getUuid()+"&tabType=projectMembers").toString();

        emailScenerioUtil.sendNotification(actionLink, loginUser, approverUse, projects.getOrgLocationsId(), notificationScenario,new HashMap<String,String>());
		
	}

	
	@Override
	public void sendEmailForBugetRequestApproversByAcceptAndRejectAndOnHold(User loginUser, ProjectBudget projectBudget) throws Exception {
		User approverUse = userService.getUserById(Long.valueOf(projectBudget.getApproverUserId().getId()));
		ProjectClients pc = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projectBudget.getProjectId().getId(),projectBudget.getProjectId().getOrgLocationsId().getId());
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
        String actionLink = null;
        StringBuilder actionLinkBuilder = new StringBuilder();
        if(projectBudget.getBudgetStatus().equalsIgnoreCase(Constants.BUDGET_REQUEST_APPROVED)) {
	        commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,projectBudget.getProjectId().getOrgLocationsId().getOrg(),projectBudget.getProjectId().getOrgLocationsId(),Constants.BUDGET_REQUEST_ACCEPTED,new ArrayList<EmailAttachment>());
        	notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST_ACCEPTED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
			actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("projectSummary?projectUUID="+projectBudget.getProjectId().getProjectUUID()+"&projectClientUUID="+pc.getUuid()+"&tabType=projectMembers").toString();
        }else if(projectBudget.getBudgetStatus().equalsIgnoreCase(Constants.BUDGET_REQUEST_REJECTED)) {
        	globalVarsMap.put("reason", projectBudget.getRejectedReason());
	        commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,projectBudget.getProjectId().getOrgLocationsId().getOrg(),projectBudget.getProjectId().getOrgLocationsId(),Constants.BUDGET_REQUEST_REJECTED_EMAIL,new ArrayList<EmailAttachment>());
        	notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST_REJECTED_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
            actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("projectSummary?projectUUID="+projectBudget.getProjectId().getProjectUUID()+"&projectClientUUID="+pc.getUuid()+"&tabType=projectMembers").toString();

        }else if(projectBudget.getBudgetStatus().equalsIgnoreCase(Constants.BUDGET_REQUEST_ON_HOLD)) {
        	globalVarsMap.put("reason", projectBudget.getRejectedReason());
	        commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,projectBudget.getProjectId().getOrgLocationsId().getOrg(),projectBudget.getProjectId().getOrgLocationsId(),Constants.BUDGET_REQUEST_ONHOLD_EMAIL,new ArrayList<EmailAttachment>());
            notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.BUDGET_REQUEST_ONHOLD_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

        }
        String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
        notificationScenario.setBody(notificationbody);

        emailScenerioUtil.sendNotification(actionLink, loginUser, approverUse, projectBudget.getProjectId().getOrgLocationsId(), notificationScenario,new HashMap<String,String>());
		
	}
	
	
	private String generatebudgetRequestId(Projects projects) {
		String companyShortCode = "";
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
