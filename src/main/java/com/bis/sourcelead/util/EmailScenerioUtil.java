package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.service.EmailMessageService;
import com.bis.sourcelead.service.EmailScenarioService;
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
	
	
public EmailScenario validateEmailscenerioExists(String uniqueScenarioName,String orgCode,String locationCode , EmailMessage message , String userCode) throws Exception{
	
	EmailScenario scenarioTemplates = emailScenarioService.getEmailScenarioByUniqueNameAndOrgCodeAndLocationsCode(uniqueScenarioName, orgCode, locationCode);
	    
		EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueNameAndOrgCodeAndLocationsCode(uniqueScenarioName, Constants.SOURCELEAD_ORGCODE, Constants.SOURCELEAD_LOCATIONCODE_IN);
		
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
}