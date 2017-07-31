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
import com.bis.sourcelead.dao.ProjectPlanningResourceRequestDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ProjectPlanningResourceRequestService;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class ProjectPlanningResourceRequestServiceImpl implements ProjectPlanningResourceRequestService{

	@Autowired
	ProjectPlanningResourceRequestDao projectPlanningResourceRequestDao;

	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Override
	public List<Long> getProjectPlanningResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception {
		return projectPlanningResourceRequestDao.getProjectPlanningResourceRequestIdsByRequestId(projectPlanningRequestId);
	}

	@Override
	public ProjectPlanningResourceRequest getProjectPlanningResourceRequestByRequestUUID(String requestUUID)
			throws Exception {
		return projectPlanningResourceRequestDao.getProjectPlanningResourceRequestByRequestUUID(requestUUID);
	}

	@Override
	public ProjectPlanningResourceRequest saveProjectPlanningResourceRequest(
			ProjectPlanningResourceRequest projectPlanningResourceRequest) throws Exception {
		return projectPlanningResourceRequestDao.saveProjectPlanningResourceRequest(projectPlanningResourceRequest);
	}

	@Override
	public void sendMailToAcceptAndRejectBulkResourceRequestApprover(ProjectPlanningRequest projectPlanningRequest, User loginUser, OrgLocations orgLocations)
			throws Exception {
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
        globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
        
        Set<ProjectPlanningResourceRequest> projectPlanningResourceRequestList = projectPlanningRequest.getProjectPlanningResourceRequestList();
        globalVarsMap.put("resourceCount", String.valueOf(projectPlanningResourceRequestList.size()));
        String resourceTable = getRequestedResourcesTable(projectPlanningResourceRequestList);
        globalVarsMap.put("resourceTable", resourceTable);
        
        EmailScenario  emailScenario = null;
        EmailScenario  notificationScenario = null;
        if(!"".equalsIgnoreCase(projectPlanningRequest.getRejectedReason()) && projectPlanningRequest.getRejectedReason() != null){
          	 globalVarsMap.put("rejectReason", projectPlanningRequest.getRejectedReason());
        	 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_BULK_RESOURCE_REQUEST,Constants.ENTITY_TYPE_EMAIL);
             notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_BULK_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
        }else{
        	 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_BULK_RESOURCE_REQUEST,Constants.ENTITY_TYPE_EMAIL);
             notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_BULK_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
           
        }
         
        String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
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
        String actionLink = notificationLink.append(sourceleadUrl).append("/").append("requestedAssociatesList?requestUUID="+projectPlanningRequest.getRequestUUID()).toString();
         
        emailScenerioUtil.sendNotification(actionLink, loginUser, projectPlanningRequest.getApproverUser(), orgLocations, notificationScenario);
		
	}
	
	@Override
	public void sendMailToOnHoldBulkResourceRequestApprover(ProjectPlanningRequest projectPlanningRequest, User loginUser, OrgLocations orgLocations) throws Exception {
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
        globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
        
        Set<ProjectPlanningResourceRequest> projectPlanningResourceRequestList = projectPlanningRequest.getProjectPlanningResourceRequestList();
        globalVarsMap.put("resourceCount", String.valueOf(projectPlanningResourceRequestList.size()));
        String resourceTable = getRequestedResourcesTable(projectPlanningResourceRequestList);
        globalVarsMap.put("resourceTable", resourceTable);
        
      	globalVarsMap.put("rejectReason", projectPlanningRequest.getRejectedReason());
      	EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ONHOLD_BULK_RESOURCE_REQUEST,Constants.ENTITY_TYPE_EMAIL);
      	EmailScenario   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ONHOLD_BULK_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
         
        String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
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
        String actionLink = notificationLink.append(sourceleadUrl).append("/").append("requestedAssociatesList?requestUUID="+projectPlanningRequest.getRequestUUID()).toString();
         
        emailScenerioUtil.sendNotification(actionLink, loginUser, projectPlanningRequest.getApproverUser(), orgLocations, notificationScenario);
		
	}
	
	private String getRequestedResourcesTable(Set<ProjectPlanningResourceRequest> projectPlanningResourceRequestList) throws Exception{
		
		
		 String tempHTML = null;
		 
		 
		 tempHTML = "<table style='display:table-cell;vertical-align:middle'>";
		 tempHTML = tempHTML+"<tr align='center'>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Resource Name<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Pay Rate<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Bill Rate<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Start Date<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>End Date<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Estimated Hours<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Estimated Budget<span></th>";
		 tempHTML = tempHTML+"</tr>";
		 
		 for(ProjectPlanningResourceRequest planningResourceRequest : projectPlanningResourceRequestList){
			
			 tempHTML = tempHTML+"<tr align='center'>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getCandidateName()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getPayRateStr()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getBillRateStr()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getStartDateStr()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getEndDateStr()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getEstimatedHours()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getEstimatedBudgetCurrency()+" "+planningResourceRequest.getEstimatedBudget()+"</td>";
			 tempHTML = tempHTML+"</tr>";
			 
		 }
		 tempHTML = tempHTML+"</table>";
		 
		 return tempHTML;
	}

	@Override
	public void sendMailToAcceptAndRejectSingleResourceRequestApprover(ProjectPlanningResourceRequest projectPlanningRequest, User loginUser, OrgLocations orgLocations)throws Exception {
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
      	globalVarsMap.put("billRate", projectPlanningRequest.getBillRateStr());
        globalVarsMap.put("payRate", projectPlanningRequest.getPayRateStr());
        globalVarsMap.put("resourceName", projectPlanningRequest.getCandidateName());
        globalVarsMap.put("estimatedHours", String.valueOf(projectPlanningRequest.getEstimatedHours()));
        globalVarsMap.put("estimatedBudget", projectPlanningRequest.getEstimatedBudgetCurrency()+" "+projectPlanningRequest.getEstimatedBudget());
        StringBuilder linkBuild = new StringBuilder();
        globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
        
        EmailScenario  emailScenario = null;
        EmailScenario  notificationScenario  = null;
        if(projectPlanningRequest.getRejectedReason() != null){
        	   globalVarsMap.put("rejectReason", projectPlanningRequest.getRejectedReason());
        	   emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_SINGLE_RESOURCE_REQUEST,Constants.ENTITY_TYPE_EMAIL);
              notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_SINGLE_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
        }else{
        	 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_SINGLE_RESOURCE_REQUEST,Constants.ENTITY_TYPE_EMAIL);
             notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_SINGLE_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
            
        }
        
        String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
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
        String actionLink = notificationLink.append(sourceleadUrl).append("/").append("requestedAssociatesList?requestUUID="+projectPlanningRequest.getRequestUUID()).toString();
         
        emailScenerioUtil.sendNotification(actionLink, loginUser, projectPlanningRequest.getProjectPlanningRequestId().getApproverUser(), orgLocations, notificationScenario);
	}
	
}
