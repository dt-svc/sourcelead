package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailMessageService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.util.Constants;

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
	
	public String buildSignature(String signature,Map<String, String> globalVarsMap) throws Exception{
		signature = signature.replaceAll("\n", "<br/>");
		signature = signature.replaceAll(" ", "&nbsp;");
		signature = emailMessageBodyConverter.convertBodyOfEmailTemplate(signature,globalVarsMap);
		return signature ;
	}

	public String AssembleEmailMessage(EmailScenario emailScenario, EmailMessage emailMessage,String subject , String body, String signature, String url, Org org ){
		String emailTemplate = EmailTemplateHTMLConversionUtil.convertBodyOfEmailTemplateToHTML(body, signature,emailScenario.getCallToActionText(), emailScenario.getButtonText(), url , emailMessage,org);
		return emailTemplate ;
	} 
	
	public void sendEmail(EmailMessage emailMessage, ArrayList<EmailRecipient> emailRecipientList,List<EmailAttachment> attachmentList) throws Exception{
			emailMessageService.sendEmail(emailMessage,emailRecipientList,attachmentList);
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


public void sendNotification(String actionLink,User loginUser,EmailScenario emailScenario) throws Exception{
	
		User u = new User();
		User u1 = new User();
		u.setId(1L);
		u1.setId(2L);
	//new Notification logic
	Notifications notification = new Notifications();
	notification.setActionLink(actionLink);
	notification.setBody(emailScenario.getBody());
	notification.setNotificationFrom(u);
	notification.setNotificationTo(u1);
	notification.setNotificationScenarioId(emailScenario);
	notification.setMode(Constants.NOTIFICATION_UNREAD);
	notification.setCreatedBy("usercode");
	notification.setCreatedDate(new Date());
	notification.setLocationCode(emailScenario.getLocationCode());
	notification.setOrgCode(emailScenario.getOrgCode());
	notification.setNotificationStatus(Constants.NOTIFICATION_NOT_DELETED);
	
	Notifications notification1 = notificationsService.saveNotification(notification);
	
	}


}