package com.bis.sourcelead.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.EntityType;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailMessageService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.NotificationsService;
import com.google.gson.Gson;

/**
 * This class is for covert subject,body,signature
 * @author Prasad salina
 *
 */
@Component
public class EmailScenerioUtil {
	
	@Autowired
	EmailMessageBodyConverter emailMessageBodyConverter;
	
	@Autowired
	 EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailMessageService emailMessageService;
	
	@Autowired
	NotificationsService notificationsService;
  
	public String bulidSubject(String subject,Map<String, String> globalVarsMap) throws Exception{
		
		subject = emailMessageBodyConverter.convertBodyOfEmailTemplate(subject,globalVarsMap);
		return subject ;
	}
	
   public String bulidTemplateSubject(String subject,Map<String, String> globalVarsMap,EmailScenario emailScenario) throws Exception{
		
		subject = emailMessageBodyConverter.convertBodyOfEmailScenerioTemplate(subject,globalVarsMap,emailScenario);
		return subject ;
	}
	
	public String buildBody(String body,Map<String, String> globalVarsMap) throws Exception{
		body = body.replaceAll("\n", "<br/>");
		body = body.replaceAll(" ", "&nbsp;");
		body = emailMessageBodyConverter.convertBodyOfEmailTemplate(body,globalVarsMap);
		return body ;
	}
	
	public String buildTempBody(String body,Map<String, String> globalVarsMap) throws Exception{
		body = body.replaceAll("\n", "<br/>");
		body = body.replaceAll(" ", "&nbsp;");
		body = emailMessageBodyConverter.convertTempBodyOfEmailTemplate(body,globalVarsMap);
		return body ;
	}
	
	public String buildTemplateBody(String body,Map<String, String> globalVarsMap,EmailScenario emailScenario) throws Exception{
		body = body.replaceAll("\n", "<br/>");
		body = body.replaceAll(" ", "&nbsp;");
		//body = emailMessageBodyConverter.convertTempBodyOfEmailTemplate(body,globalVarsMap);
		body = emailMessageBodyConverter.convertTemplateBodyOfEmailTemplate(body,globalVarsMap,emailScenario);
		return body ;
	}
	
	public String buildSignature(String signature,Map<String, String> globalVarsMap) throws Exception{
		signature = signature.replaceAll("\n", "<br/>");
		signature = signature.replaceAll("\r", "<br/>");
		signature = signature.replaceAll(" ", "&nbsp;");
		signature = emailMessageBodyConverter.convertBodyOfEmailTemplate(signature,globalVarsMap);
		return signature ;
	}
	
	public String buildTemplateSignature(String signature,Map<String, String> globalVarsMap,EmailScenario emailScenario) throws Exception{
		signature = signature.replaceAll("\n", "<br/>");
		signature = signature.replaceAll("\r", " ");
		signature = signature.replaceAll(" ", "&nbsp;");
		signature = emailMessageBodyConverter.convertTemplateBodyOfEmailTemplate(signature,globalVarsMap,emailScenario);
		return signature ;
	}

	public String AssembleEmailMessage(EmailScenario emailScenario, EmailMessage emailMessage,String subject , String body, String signature, String url, Org org,OrgLocations orgLocations ){
		String emailTemplate = EmailTemplateHTMLConversionUtil.convertBodyOfEmailTemplateToHTML(body, signature,emailScenario.getCallToActionText(), emailScenario.getButtonText(), url , emailMessage,org,orgLocations, emailScenario.getTabId(),emailScenario.getTemplateType());
		return emailTemplate ;
	} 
	
	public void sendEmail(EmailMessage emailMessage,Long locationsId, InternetAddress[] iAdressArray,List<EmailAttachment> attachmentList) throws Exception{
			emailMessageService.sendEmail(emailMessage,locationsId,iAdressArray,attachmentList);
		}
	
	
public EmailScenario validateEmailscenerioExists(String uniqueScenarioName,String entityType,String orgCode,String locationCode , EmailMessage message , String userCode) throws Exception{
	
	EmailScenario scenarioTemplates = emailScenarioService.getEmailScenarioByUniqueNameAndEntityTypeAndOrgCodeAndLocationsCode(uniqueScenarioName, entityType, orgCode, locationCode);
	    
		EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueNameAndEntityTypeAndOrgCodeAndLocationsCode(uniqueScenarioName, entityType, Constants.SOURCELEAD_ORGCODE, Constants.SOURCELEAD_LOCATIONCODE_IN);
		
		if(scenarioTemplates == null){
			
			EmailScenario scenario = new EmailScenario();
			
			scenario.setScenarioName(emailScenario.getScenarioName());
			scenario.setUniqueScenarioName(emailScenario.getUniqueScenarioName());
			scenario.setDescription(emailScenario.getDescription());
			scenario.setSubject(emailScenario.getSubject());
			scenario.setTemplateType("Org");
			scenario.setSignature(emailScenario.getSignature());
			scenario.setBody(emailScenario.getBody());
			scenario.setIsTemplateModified("N");
			scenario.setCallToActionText(emailScenario.getCallToActionText());
			scenario.setButtonText(emailScenario.getButtonText());
    		scenario.setLocationCode(locationCode);
    		scenario.setUseLogo("Yes");
    		scenario.setDisclaimerText(emailScenario.getDisclaimerText());
    		scenario.setCreatedDate(new Date());
    		scenario.setCreatedBy(userCode);
    		scenario.setOrgCode(orgCode);
			
    		emailScenarioService.save(scenario);
			
			scenarioTemplates = scenario;
		}
		
		return scenarioTemplates;
	}


public void sendNotification(String actionLink,User loginUser,User toUser, OrgLocations loginOrgLocation, EmailScenario emailScenario,HashMap<String,String> parameterList) throws Exception{
	//new Notification logic
	Notifications notification = new Notifications();
	
	if(null != emailScenario.getUUID()  && !emailScenario.getUUID().isEmpty()){
		notification.setUuId(emailScenario.getUUID());
	}
	else {
		notification.setUuId(SourceLeadRandomCodeHelper.generateNotificationUUID());
	}
	if(StringUtils.isNotEmpty(actionLink) && actionLink.contains(Constants.PLACE_UUID)) {
		actionLink = actionLink.replace(Constants.PLACE_UUID, notification.getUuId());
	}
	notification.setActionLink(actionLink);
	notification.setBody(emailScenario.getBody());
	
	if(loginUser != null) {
	notification.setNotificationFrom(loginUser);
	notification.setCreatedBy(loginUser.getUserCode());
	}
	notification.setNotificationTo(toUser);
	notification.setNotificationScenarioId(emailScenario);
	notification.setMode(Constants.NOTIFICATION_UNREAD);
	notification.setCreatedDate(new Date());
	notification.setLocationCode(loginOrgLocation.getLocationCode());
    if(loginOrgLocation.getOrgCode() != null) {
		notification.setOrgCode(loginOrgLocation.getOrgCode());
	}else {
		if(loginOrgLocation.getOrg() != null && loginOrgLocation.getOrg().getOrgCode() != null) {
			notification.setOrgCode(loginOrgLocation.getOrg().getOrgCode());
		}
	}
	notification.setUserNotificationStatus("Sent");
	notification.setNotificationStatus(Constants.NOTIFICATION_NOT_DELETED);
	if(parameterList != null && !parameterList.isEmpty()) {
		System.out.println("parameterList != null");
		 Gson gsonObj = new Gson();
		 String str = gsonObj.toJson(parameterList);
		notification.setParameterList(str);
	}
	 notificationsService.saveNotification(notification);
	}

public void sendNotificationToClientOrVendor(String actionLink,String rejectActionLink, User loginUser,User fromUser, User toUser, OrgLocations loginOrgLocation, EmailScenario emailScenario, String spocUUID) throws Exception{
	
	//new Notification logic
	Notifications notification = new Notifications();
	notification.setActionLink(actionLink);
	notification.setRejectActionLink(rejectActionLink);
	notification.setSpocUUID(spocUUID);
	notification.setBody(emailScenario.getBody());
	notification.setNotificationFrom(fromUser);
	notification.setNotificationTo(toUser);
	notification.setNotificationScenarioId(emailScenario);
	notification.setMode(Constants.NOTIFICATION_UNREAD);
	notification.setCreatedBy(loginUser.getUserCode());
	notification.setCreatedDate(new Date());
	notification.setLocationCode(loginOrgLocation.getLocationCode());
	if(StringUtils.isNoneBlank(loginOrgLocation.getOrgCode())) {
		notification.setOrgCode(loginOrgLocation.getOrgCode());
	}else {
		notification.setOrgCode(loginOrgLocation.getOrg().getOrgCode());
	}
	notification.setUserNotificationStatus("Sent");
	notification.setNotificationStatus(Constants.NOTIFICATION_NOT_DELETED);
	if(null != emailScenario.getUUID()  && !emailScenario.getUUID().isEmpty()){
		notification.setUuId(emailScenario.getUUID());
	}
	
	Notifications notification1 = notificationsService.saveNotification(notification);
	
	}


public void sendNotificationToClientOrVendorWhenOwnerChange(String actionLink,String rejectActionLink, User loginUser,User fromUser, User toUser, OrgLocations loginOrgLocation, EmailScenario emailScenario, String spocUUID, OrgLocations clientOrgLocation) throws Exception{
	
	//new Notification logic
	Notifications notification = new Notifications();
	notification.setActionLink(actionLink);
	notification.setRejectActionLink(rejectActionLink);
	notification.setSpocUUID(spocUUID);
	notification.setBody(emailScenario.getBody());
	notification.setNotificationFrom(fromUser);
	notification.setNotificationTo(toUser);
	notification.setNotificationScenarioId(emailScenario);
	notification.setMode(Constants.NOTIFICATION_UNREAD);
	notification.setCreatedBy(loginUser.getUserCode());
	notification.setCreatedDate(new Date());
	notification.setLocationCode(clientOrgLocation.getLocationCode());
	notification.setOrgCode(clientOrgLocation.getOrg().getOrgCode());
	notification.setUserNotificationStatus("Sent");
	notification.setNotificationStatus(Constants.NOTIFICATION_NOT_DELETED);
	if(null != emailScenario.getUUID()  && !emailScenario.getUUID().isEmpty()){
		notification.setUuId(emailScenario.getUUID());
	}
	
	Notifications clientVendorNotification = notificationsService.saveNotification(notification);
	
	}

public void sendWithAttachmentEmail(EmailMessage emailMessage, Long locationsId, InternetAddress[] iAdressArray,List<EmailAttachment> attachmentList, String[] submissionIds) throws Exception{
	emailMessageService.sendEmailWithAttachment(emailMessage,locationsId,iAdressArray,attachmentList,submissionIds);
	
}




}