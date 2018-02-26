package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgRequestDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRequest;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgRequestService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class OrgRequestServiceImpl implements OrgRequestService{
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	OrgRequestDao orgRequestDao;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	UserService userService;
	
	
	@Override
	public OrgRequest saveOrgRequest(OrgRequest orgRequest) throws Exception {
		return orgRequestDao.saveOrgRequest(orgRequest);
	}

	@Override
	public Boolean validateOrgRequestCompanyNameByName(String companyName) throws Exception {
		return orgRequestDao.validateOrgRequestCompanyNameByName(companyName);
	}

	@Override
	public void senEmailOrgRequestToSourcelead(OrgRequest orgRequest)throws Exception {
		 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(orgRequest.getFirstName());
		 emailMessage.setLastName(orgRequest.getLastName());
		 emailMessage.setToEmail("info@sourcelead.com");
		 emailMessage.setFromEmail(orgRequest.getEmail());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("companyName",orgRequest.getCompanyName());
		 globalVarsMap.put("companyWebsite", orgRequest.getCompanywebsite());
		 globalVarsMap.put("companyLocation",orgRequest.getCountry());
		 globalVarsMap.put("companyEmail",orgRequest.getEmail());
		  

		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ORGREQUEST_EMAIL,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url,new Org(),new OrgLocations());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		 
		 /*EmailScenario noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.ORGREQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION); 
		 String actionLink = "";
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
		 noificationEmailScenario.setBody(notificationbody);
		 
		 User sourceLeadAdmin = userService.getUserById(Constants.SOURCELEAD_ADMIN_USERID);
		 
		 emailScenerioUtil.sendNotification(actionLink, sourceLeadAdmin, null, new OrgLocations(), noificationEmailScenario);*/
		
	}

	
}
