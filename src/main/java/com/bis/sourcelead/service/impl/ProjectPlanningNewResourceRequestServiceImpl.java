package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectPlanningNewResourceRequestDao;
import com.bis.sourcelead.dto.ProjectPlanningNewResourceRequestDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ProjectPlanningNewResourceRequestService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class ProjectPlanningNewResourceRequestServiceImpl implements ProjectPlanningNewResourceRequestService{

	@Autowired
	ProjectPlanningNewResourceRequestDao projectPlanningNewResourceRequestDao;

	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public ProjectPlanningNewResourceRequest saveProjectPlanningNewResourceRequest(ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest) throws Exception {
		return projectPlanningNewResourceRequestDao.saveProjectPlanningNewResourceRequest(projectPlanningNewResourceRequest);
	}

	@Override
	public ProjectPlanningNewResourceRequest getProjectPlanningNewResourceRequestByRequestUUID(String requestUUID) throws Exception {
		return projectPlanningNewResourceRequestDao.getProjectPlanningNewResourceRequestByRequestUUID(requestUUID);
	}
	
	@Override
	public void sendMailToAcceptAndRejectSingleNewResourceRequestApprover(ProjectPlanningNewResourceRequest projectPlanningRequest, User loginUser, OrgLocations orgLocations) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFirstName(loginUser.getFirstName());
        emailMessage.setLastName(loginUser.getLastName());
        emailMessage.setToEmail(projectPlanningRequest.getProjectPlanningRequestId().getApproverUser().getEmail());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setCreatedBy(loginUser.getUserCode());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setCreatedBy(loginUser.getUserCode());
        emailMessage.setFromName(projectPlanningRequest.getProjectPlanningRequestId().getRequestedUser());
        emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
       
        Map<String, String> globalVarsMap = new HashMap<String, String>();
        globalVarsMap.put("projectName", projectPlanningRequest.getProjectPlanningRequestId().getProjectName());
        globalVarsMap.put("approverName", projectPlanningRequest.getProjectPlanningRequestId().getApproverName());
        globalVarsMap.put("loginUserName", loginUser.getFullName());
        globalVarsMap.put("loginOrgName", orgLocations.getOrgLocationName());
        if(projectPlanningRequest.getBillRate() != null && projectPlanningRequest.getBillRate() !=""){
        	 StringBuilder billRate = new StringBuilder();
        	 globalVarsMap.put("billRate", billRate.append(projectPlanningRequest.getRateCurrency()).append(" ").append(projectPlanningRequest.getBillRate()).append(" ").append(projectPlanningRequest.getRateType()).toString());
        }else{
        	globalVarsMap.put("billRate","--");
        }
        if(projectPlanningRequest.getPayRate() != null && projectPlanningRequest.getPayRate() !=""){
        	StringBuilder payRate = new StringBuilder();
        	globalVarsMap.put("payRate", payRate.append(projectPlanningRequest.getRateCurrency()).append(" ").append(projectPlanningRequest.getPayRate()).append(" ").append(projectPlanningRequest.getRateType()).toString());
       }else{
    	   globalVarsMap.put("payRate","--");
       }
        globalVarsMap.put("roleName", projectPlanningRequest.getRoleName());
        if(projectPlanningRequest.getEstimatedHours() != null){
        globalVarsMap.put("estimatedHours", String.valueOf(projectPlanningRequest.getEstimatedHours()));
        }
        else{
        	globalVarsMap.put("estimatedHours","--");
        }
        if(projectPlanningRequest.getEstimatedBudget() != null ){
        globalVarsMap.put("estimatedBudget", projectPlanningRequest.getEstimatedBudgetCurrency()+" "+projectPlanningRequest.getEstimatedBudget());
       
        }
        else{
        	globalVarsMap.put("estimatedBudget","--");
        }
        StringBuilder linkBuild = new StringBuilder();
        globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
        
        EmailScenario  emailScenario = null;
        EmailScenario  notificationScenario  = null;
        if(projectPlanningRequest.getResourceStatus().equalsIgnoreCase("Approved")){
   		   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_SINGLE_NEW_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
           commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.ACCEPT_SINGLE_NEW_RESOURCE_REQUEST,new ArrayList<EmailAttachment>());       

           }else if(projectPlanningRequest.getResourceStatus().equalsIgnoreCase("On hold")){
          	globalVarsMap.put("rejectReason", projectPlanningRequest.getRejectedReason());
   		   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ONHOLD_BULK_NEW_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
           commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.ONHOLD_BULK_NEW_RESOURCE_REQUEST,new ArrayList<EmailAttachment>());       

          }else if(projectPlanningRequest.getResourceStatus().equalsIgnoreCase("Rejected")){
          	 globalVarsMap.put("rejectReason", projectPlanningRequest.getRejectedReason());
     		   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_SINGLE_NEW_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
     	        commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.REJECT_SINGLE_NEW_RESOURCE_REQUEST,new ArrayList<EmailAttachment>());       

          }
     
        
        String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
        notificationScenario.setBody(notificationbody);
       
       /* Commented by Pradeep Konda -- We are not maintaining the Resource Requested User Details in database.
        StringBuilder notificationLink = new StringBuilder();
        String actionLink = notificationLink.append(sourceleadUrl).append("/").append("newRequestedAssociatesList?requestUUID="+projectPlanningRequest.getRequestUUID()).toString();
        emailScenerioUtil.sendNotification(actionLink, loginUser, projectPlanningRequest.getProjectPlanningRequestId().getApproverUser(), orgLocations, notificationScenario);*/
	}

	@Override
	public void sendMailToAcceptAndRejectBulkNewResourceRequestApprover(ProjectPlanningRequest projectPlanningRequest, User loginUser, OrgLocations orgLocations) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFirstName(loginUser.getFirstName());
        emailMessage.setLastName(loginUser.getLastName());
        emailMessage.setToEmail(projectPlanningRequest.getApproverUser().getEmail());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setCreatedBy(loginUser.getUserCode());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setCreatedBy(loginUser.getUserCode());
        emailMessage.setFromName(projectPlanningRequest.getRequestedUser());
        emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
       
        Map<String, String> globalVarsMap = new HashMap<String, String>();
        globalVarsMap.put("projectName", projectPlanningRequest.getProjectName());
        globalVarsMap.put("approverName", projectPlanningRequest.getApproverName());
        globalVarsMap.put("loginUserName", loginUser.getFullName());
        globalVarsMap.put("loginOrgName", orgLocations.getOrgLocationName());
        globalVarsMap.put("estimatedHours", String.valueOf(projectPlanningRequest.getEstimatedHours()));
        globalVarsMap.put("estimatedBudget", projectPlanningRequest.getEstimatedBudgetCurrency()+" "+projectPlanningRequest.getEstimatedBudget());
        StringBuilder linkBuild = new StringBuilder();
        globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
        
        Set<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList = projectPlanningRequest.getProjectPlanningNewResourceRequestList();
        globalVarsMap.put("resourceCount", String.valueOf(projectPlanningNewResourceRequestList.size()));
        String resourceTable = getNewRequestedResourcesTable(projectPlanningNewResourceRequestList);
        globalVarsMap.put("resourceTable", resourceTable);
        
        EmailScenario  emailScenario = null;
        EmailScenario  notificationScenario = null;
        if(projectPlanningRequest.getRejectedReason() != null){
        	globalVarsMap.put("rejectReason", projectPlanningRequest.getRejectedReason());
     		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.REJECT_BULK_NEW_RESOURCE_REQUEST,new ArrayList<EmailAttachment>());
        	 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_BULK_NEW_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
            
        }else{
      		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.ACCEPT_BULK_NEW_RESOURCE_REQUEST,new ArrayList<EmailAttachment>());
        	 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_BULK_NEW_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
            
        }
        
        String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
        notificationScenario.setBody(notificationbody);
       
        StringBuilder notificationLink = new StringBuilder();
        String actionLink = notificationLink.append(sourceleadUrl).append("/").append("newRequestedAssociatesList?requestUUID="+projectPlanningRequest.getRequestUUID()).toString();
         
        emailScenerioUtil.sendNotification(actionLink, loginUser, projectPlanningRequest.getApproverUser(), orgLocations, notificationScenario,new HashMap<String,String>());
		
	}

	@Override
	public void sendMailToOnHoldBulkNewResourceRequestApprover(ProjectPlanningRequest projectPlanningRequest, User loginUser, OrgLocations orgLocations) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFirstName(loginUser.getFirstName());
        emailMessage.setLastName(loginUser.getLastName());
        emailMessage.setToEmail(projectPlanningRequest.getApproverUser().getEmail());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setCreatedBy(loginUser.getUserCode());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setCreatedBy(loginUser.getUserCode());
        emailMessage.setFromName(projectPlanningRequest.getRequestedUser());
        emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
       
        Map<String, String> globalVarsMap = new HashMap<String, String>();
        globalVarsMap.put("projectName", projectPlanningRequest.getProjectName());
        globalVarsMap.put("approverName", projectPlanningRequest.getApproverName());
        globalVarsMap.put("loginUserName", loginUser.getFullName());
        globalVarsMap.put("loginOrgName", orgLocations.getOrgLocationName());
        globalVarsMap.put("estimatedHours", String.valueOf(projectPlanningRequest.getEstimatedHours()));
        globalVarsMap.put("estimatedBudget", projectPlanningRequest.getEstimatedBudgetCurrency()+" "+projectPlanningRequest.getEstimatedBudget());
        StringBuilder linkBuild = new StringBuilder();
        globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
        
        Set<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList = projectPlanningRequest.getProjectPlanningNewResourceRequestList();
        globalVarsMap.put("resourceCount", String.valueOf(projectPlanningNewResourceRequestList.size()));
        String resourceTable = getNewRequestedResourcesTable(projectPlanningNewResourceRequestList);
        globalVarsMap.put("resourceTable", resourceTable);
        globalVarsMap.put("rejectReason", projectPlanningRequest.getRejectedReason());
        
        EmailScenario  emailScenario = null;
        if(orgLocations != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.ONHOLD_BULK_NEW_RESOURCE_REQUEST,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ONHOLD_BULK_NEW_RESOURCE_REQUEST,Constants.ENTITY_TYPE_EMAIL);
			   }
        EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ONHOLD_BULK_NEW_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
            
        String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
        notificationScenario.setBody(notificationbody);
        
        //covert subject,body and signature
        String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
        String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
        String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
        String url = sourceleadUrl;
       
        InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
       
        String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
        emailMessage.setSubject(subject);
        emailMessage.setMessageBody(emailTemplate);
       
        emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
        StringBuilder notificationLink = new StringBuilder();
        String actionLink = notificationLink.append(sourceleadUrl).append("/").append("newRequestedAssociatesList?requestUUID="+projectPlanningRequest.getRequestUUID()).toString();
         
        emailScenerioUtil.sendNotification(actionLink, loginUser, projectPlanningRequest.getApproverUser(), orgLocations, notificationScenario,new HashMap<String,String>());
	}
	
	
	private String getNewRequestedResourcesTable(Set<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList) throws Exception{
		 String tempHTML = null;
		tempHTML = " <table style='display:table-cell;vertical-align:middle;font-family:arial; font-size:12px;' width='900' cellpadding='5'>";
		tempHTML = tempHTML+"<thead>";
		tempHTML = tempHTML+"<tr align='center'>";
		tempHTML = tempHTML+"<th style='width:150px; background:#375d81; color:#fff;'>Role Name</th>";
		tempHTML = tempHTML+"<th style='width:50px; background:#375d81; color:#fff;'>No Res.</th>";
		tempHTML = tempHTML+"<th style='width:100px; background:#375d81; color:#fff;'>Experience</th>";
		tempHTML = tempHTML+"<th style='width:175px; background:#375d81; color:#fff;'>Primary Sills</th>";
		tempHTML = tempHTML+"<th style='width:240px; background:#375d81; color:#fff;'>Bill / Pay Rate</th>";
		tempHTML = tempHTML+"<th style='width:65px; background:#375d81; color:#fff;'>Duration</th>";
		tempHTML = tempHTML+"<th style='width:50px; background:#375d81; color:#fff;'>Consulting Type</th>";
		tempHTML = tempHTML+"<th style='width:65px; background:#375d81; color:#fff;'>Estimated Hours</th>";
		tempHTML = tempHTML+"<th style='width:65px; background:#375d81; color:#fff;'>Estimated Budget</th>";
		tempHTML = tempHTML+"</tr>";
		tempHTML = tempHTML+"</thead>";
		tempHTML = tempHTML+"<tbody>";
			
		 for(ProjectPlanningNewResourceRequest planningResourceRequest : projectPlanningNewResourceRequestList){
			 Integer minEx = 0;
			 Integer MaxEx = 0;
			 String starDate = "";
			 String endDate = "";
			 String billRate = "";
			 String payRate = "";
			if(planningResourceRequest.getMinExperience() != null){
				 minEx = planningResourceRequest.getMinExperience();
			}
			if(planningResourceRequest.getMaxExperience() != null){
				MaxEx = planningResourceRequest.getMaxExperience();
			}
			if(planningResourceRequest.getStartDateStr() != null && planningResourceRequest.getStartDateStr() !=""){
				starDate = planningResourceRequest.getStartDateStr();
			}else{
				starDate = "--";
			}
			if(planningResourceRequest.getEndDateStr() != null && planningResourceRequest.getEndDateStr() !=""){
				endDate = planningResourceRequest.getEndDateStr();
			}else{
				endDate = "--";
			}
			if(planningResourceRequest.getBillRate() != null){
				billRate = planningResourceRequest.getBillRate();
			}else{
				billRate = "--";
			}
			if(planningResourceRequest.getPayRate() != null){
				payRate = planningResourceRequest.getPayRate();
			}else{
				payRate = "--";
			}
			
		 tempHTML = tempHTML+" <tr align='center'>";
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top; vertical-align:top;'>"+planningResourceRequest.getRoleName()+"</td>";
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>"+planningResourceRequest.getNoOfResource()+"</td>";
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>";
		 tempHTML = tempHTML+"		<div style='float:left; width:80px;'>";
		 tempHTML = tempHTML+"			<div style='float:left; width:25px; text-align:center;'>"+minEx+"</div>";
		 tempHTML = tempHTML+"			<div style='float:left; width:25px; text-align:center;'>"+MaxEx+"</div>";
		 tempHTML = tempHTML+"			<div style='float:left; width:30px; text-align:center;'>Yr(s)</div>";
		 tempHTML = tempHTML+"		</div>";
		 tempHTML = tempHTML+"	</td>";
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>"+planningResourceRequest.getSkills()+"</td>";
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>";
		 tempHTML = tempHTML+"		<div style='float:left; width:25px; font-size:12px; margin-top: 8px;'>"+planningResourceRequest.getRateCurrency()+"</div>";
		 tempHTML = tempHTML+"		<div style='float:left; width:85px;'>";
		 tempHTML = tempHTML+"			<div style='float:left; width:85px; text-align:center;'>"+billRate+"</div>";
		 tempHTML = tempHTML+"			<div style='float:left; width:85px; text-align:center;'>"+payRate+"</div>";
		 tempHTML = tempHTML+"		</div>";
		 tempHTML = tempHTML+"		<div style='float:left; width:65px; font-size:12px; margin-top: 8px;overflow: hidden; white-space: nowrap; text-overflow: ellipsis;' title='Semi Weekly'>"+planningResourceRequest.getRateType()+"</div>";
		 tempHTML = tempHTML+"	</td>";
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>";
		 tempHTML = tempHTML+"			<div style='width:65px; text-align:center;'>"+starDate+"</div>";
		 tempHTML = tempHTML+"			<div style='width:65px; text-align:center;'>"+endDate+"</div>";
		 tempHTML = tempHTML+"	</td>";
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>"+planningResourceRequest.getConsultingType()+"</td>";
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>"+planningResourceRequest.getEstimatedHours()+"</td>";
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>"+planningResourceRequest.getEstimatedBudget()+"</td>";
		 tempHTML = tempHTML+"	</tr>";
		 }
		 tempHTML = tempHTML+"</tbody>";
		 tempHTML = tempHTML+"</table>";
		 
		 return tempHTML;
	}

	@Override
	public ResponseList<ProjectPlanningNewResourceRequestDTO> getNewResourceRequestListByApproverIdAndOrgLocationId(
			ProjectPlanningNewResourceRequestDTO resourceRequestDTO, Long approverUserId, Long orgLocationId,
			String userRole) throws Exception {
		return projectPlanningNewResourceRequestDao.getNewResourceRequestListByApproverIdAndOrgLocationId(resourceRequestDTO, approverUserId, orgLocationId, userRole);
	}

	@Override
	public List<ProjectPlanningNewResourceRequest> getNewResourceRequestListByApproverIdAndOrgLocationIdAndStatus(
			Long approverUserId, Long orgLocationId, String userRole) throws Exception {
		return projectPlanningNewResourceRequestDao.getNewResourceRequestListByApproverIdAndOrgLocationIdAndStatus(approverUserId, orgLocationId, userRole);
	}

}
