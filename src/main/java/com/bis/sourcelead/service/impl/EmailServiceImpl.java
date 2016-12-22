package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.UserSignature;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmailService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserSignatureService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	UserSignatureService userSignatureService;
	
	@Autowired
	UserMailBoxService userMailBoxService;
	
	@Autowired
	UserRoleService userRoleService;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public void sendEmail(JSONObject jsonObj, User user, OrgLocations orgLocation, MultipartFile multiFile)
			throws Exception {
		
		
		EmailMessage emailMessage = new EmailMessage(); 
		 String fullName = null;
		 UserSignature userSignature = null;
		 String signature = "";
		 String emails = null;
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 
		 
		   	 if(!isNullOrEmpty(user.getLastName())){
		   		 fullName = user.getFirstName()+" "+user.getLastName();
		   	 }else{
		   		 fullName = user.getFirstName();
		   	 }
				
		   	if(jsonObj.has("userSignature") && !isNullOrEmpty(jsonObj.getString("userSignature"))){
		   		userSignature = userSignatureService.getUserSignatureById(Long.parseLong(jsonObj.getString("userSignature")));
		   	}
	   	 
			if(jsonObj.has("Toemails") && !isNullOrEmpty(jsonObj.getString("Toemails"))){
				emails = jsonObj.getString("Toemails").replaceAll("\\[", "").replaceAll("\\]","");
				emails = emails.replaceAll("\"", "");
				String[] toEmails = emails.split(",");
				 
				for(int i=0 ; i< toEmails.length ; i++){
					 EmailRecipient emailRecipient = new EmailRecipient();
					 emailRecipient.setEmail(toEmails[i]);
					 emailRecipientList.add(emailRecipient);
				}
			}
			
			 emailMessage.setCreatedBy(user.getUserCode());
			 emailMessage.setFromEmail(user.getEmail());
			 emailMessage.setFromName(fullName);
			
			 
			 
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("salutation", jsonObj.getString("saluation"));
			 globalVarsMap.put("body", jsonObj.getString("bodytext"));
			 
			 EmailScenario emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.EMAIL_TO_USERS,Constants.ENTITY_TYPE_EMAIL);
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(jsonObj.getString("subject"),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 
			 if(null != userSignature){
				 signature = emailScenerioUtil.buildSignature(userSignature.getSignature(),globalVarsMap);
			 }
			 
			 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 emailMessage.setOrgLocationId(orgLocation.getId());
			 
			 ArrayList<EmailAttachment> attachmentList = null;
			 if(multiFile.getSize() > 0){
				 attachmentList = new ArrayList<EmailAttachment>();
				 EmailAttachment emailAttachment = new EmailAttachment();
				 emailAttachment.setName(multiFile.getOriginalFilename());
				 emailAttachment.setDocFile(multiFile.getBytes());
				 emailAttachment.setType(multiFile.getContentType());
				 attachmentList.add(emailAttachment);
			 }
			 
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),emailRecipientList,attachmentList);
			 
			 saveUserMailBox(user,orgLocation,emailRecipientList,subject,jsonObj.getString("bodytext"),userSignature,multiFile);
			 
			 saveToUserMailBox(user,orgLocation,emailRecipientList,subject,jsonObj.getString("bodytext"),userSignature,multiFile);
		
	}

	private void saveUserMailBox(User user,OrgLocations orgLocation,ArrayList<EmailRecipient> emailRecipientList,String subject,String body,UserSignature userSignature,MultipartFile multiFile) throws Exception{
		
		UserMailBox userMailBox = new UserMailBox();
		StringBuilder toEmails = new StringBuilder();
		for (EmailRecipient recipient : emailRecipientList) {
			toEmails.append(recipient.getEmail()).append(",");
		 }
		toEmails.toString();
		
		userMailBox.setUserId(user);
		userMailBox.setOrgLocationId(orgLocation);
		userMailBox.setFromMail(user.getEmail());
		userMailBox.setToMail(toEmails.substring(0,toEmails.length()-1));
		userMailBox.setMailStatus(Constants.MAIL_BOX_SENT);
		userMailBox.setSubject(subject);
		userMailBox.setBody(body);
		userMailBox.setSignatureId(userSignature);
		userMailBox.setIsMailRead(0);
		userMailBox.setIsFavourite(0);
		userMailBox.setUuid(SourceLeadRandomCodeHelper.generateUUID());
		userMailBox.setStatus(1);
		userMailBox.setCreatedBy(user.getUserCode());
		userMailBox.setCreatedDate(new Date());
		userMailBox.setUpdatedBy(user.getUserCode());
		userMailBox.setUpdatedDate(new Date());
		
		
		 if(null != multiFile.getContentType() && !multiFile.getContentType().equalsIgnoreCase("application/octet-stream")){
	   	  try {
	   		userMailBox.setFileContentType(multiFile.getContentType());
	   		userMailBox.setDocFile(multiFile.getBytes());
	   		userMailBox.setFileName(multiFile.getOriginalFilename());
	   	   
		       }catch (Exception e) {
	   	      	 e.printStackTrace();
				    }
	       }
		
		
		userMailBoxService.saveUserMailBox(userMailBox);
		
	}
	
	
	private void saveToUserMailBox(User user,OrgLocations orgLocation,ArrayList<EmailRecipient> emailRecipientList,String subject,String body,UserSignature userSignature,MultipartFile multiFile) throws Exception{
		
		
		for (EmailRecipient recipient : emailRecipientList) {
			
			UserRole userRole = userRoleService.getUserDetailsByEmailAndLocationId(recipient.getEmail(), orgLocation.getId());
			
			if(null != userRole){
				
				UserMailBox userMailBox = new UserMailBox();
				userMailBox.setUserId(userRole.getUser());
				userMailBox.setOrgLocationId(orgLocation);
				userMailBox.setFromMail(user.getEmail());
				userMailBox.setToMail(recipient.getEmail());
				userMailBox.setMailStatus(Constants.MAIL_BOX_INBOX);
				userMailBox.setSubject(subject);
				userMailBox.setBody(body);
				userMailBox.setSignatureId(userSignature);
				userMailBox.setIsMailRead(0);
				userMailBox.setIsFavourite(0);
				userMailBox.setUuid(SourceLeadRandomCodeHelper.generateUUID());
				userMailBox.setStatus(1);
				userMailBox.setCreatedBy(user.getUserCode());
				userMailBox.setCreatedDate(new Date());
				userMailBox.setUpdatedBy(user.getUserCode());
				userMailBox.setUpdatedDate(new Date());
				
				
				 if(null != multiFile.getContentType() && !multiFile.getContentType().equalsIgnoreCase("application/octet-stream")){
			   	  try {
			   		userMailBox.setFileContentType(multiFile.getContentType());
			   		userMailBox.setDocFile(multiFile.getBytes());
			   		userMailBox.setFileName(multiFile.getOriginalFilename());
			   	   
				       }catch (Exception e) {
			   	      	 e.printStackTrace();
						    }
			       }
				
				
				userMailBoxService.saveUserMailBox(userMailBox);
				
			}
		 }
		
		
	}

	
}
