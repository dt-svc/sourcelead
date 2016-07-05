package com.bis.sourcelead.util;

import java.util.ArrayList;
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
import com.bis.sourcelead.util.EmailMessageBodyConverter;
import com.bis.sourcelead.util.EmailTemplateHTMLConversionUtil;

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
	
	public void sendEmail(EmailMessage emailMessage, ArrayList<EmailRecipient> emailRecipientList,List<EmailAttachment> attachmentList){
		
		   //storeEmailInfoInDB(emailTemplate , emailMessage , emailScenario);
			emailMessageService.sendEmail(emailMessage,emailRecipientList,attachmentList);
		}
	
	
public EmailScenario validateEmailscenerioExists(String uniqueScenarioName,String orgCode,String locationCode , EmailMessage message , String userCode) throws Exception{
	
	EmailScenario scenarioTemplates = emailScenarioService.getEmailScenarioByUniqueNameAndOrgCodeAndLocationsCode(uniqueScenarioName, orgCode, locationCode);
	    
		EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueNameAndOrgCodeAndLocationsCode(uniqueScenarioName, Constants.SOURCELEAD_ORGCODE, Constants.SOURCELEAD_LOCATIONCODE_IN);
		
		/*if(scenarioTemplates == null){
			
			EmailScenario scenario = new EmailScenario();
			
			scenario.setBody(emailScenario.getBody());
			scenario.setSubject(emailScenario.getSubject());
			scenario.setSignature(emailScenario.getSignature());
			scenario.setCallToActionText(emailScenario.getCallToActionText());
			scenario.setButtonText(emailScenario.getButtonText());
			scenario.setFromCompanyEmail(user.getEmail());
    		scenario.setLocationCode(locationCode);
    		scenario.setUseLogo("Yes");
    		scenario.setConfigureTemplate("N");
    		scenario.setCreatedDate(new Date());
    		scenario.setCreatedBy(userCode);
    		scenario.setOrgCode(orgCode);
			
			orgEmailScenarioTemplatesDao.store(scenario);
			
			scenarioTemplates = scenario;
		}*/
		
		return scenarioTemplates;
	}
}