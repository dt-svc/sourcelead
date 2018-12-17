package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.EmailAccountSettingsDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.HotListEmailCampaignDTO;
import com.bis.sourcelead.dto.ReadMailDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.EmailAccountSettings;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmailService;
import com.bis.sourcelead.service.SourceleadSignatureService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserSignatureService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.MailBox;
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
	
	@Autowired
	SourceleadSignatureService sourceleadSignatureService;
	
	@Autowired
	private EmailAccountSettingsDao emailAccountSettingsDao;
	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public void sendEmail(HotListEmailCampaignDTO hotListEmailCampaignDTO, User user, OrgLocations orgLocation,MultipartFile multiFile)
			throws Exception {
		
		
		EmailMessage emailMessage = new EmailMessage(); 
		 String fullName = null;
		 String signature = "";
		 String emails = null;
		 StringBuilder recipantsEmailsList = new StringBuilder();
		 
		 InternetAddress[] iAdressArray = null;
		 	StringBuilder lastName=new StringBuilder();
		   	 if(!isNullOrEmpty(user.getLastName())){
		   		 fullName = lastName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		   	 }else{
		   		 fullName = user.getFirstName();
		   	 }
				
			
			if(StringUtils.isNotBlank(hotListEmailCampaignDTO.getToemails())){
		    	  String jsonArray = hotListEmailCampaignDTO.getToemails();
				   emails = jsonArray.toString();
		    	 if(null != emails){
		    		  emails = emails.replaceAll("\\[", "").replaceAll("\\]","");
		    		  emails = emails.replaceAll("\"", "");
		    	  }
		    	 
		    	 String[] recipantsEmails = emails.split(",");
		    	 for(int i=0; i < recipantsEmails.length; i++){
		    		 if(validateEmail(recipantsEmails[i].trim())){
		    			 recipantsEmailsList = recipantsEmailsList.append(recipantsEmails[i]);
		    			 recipantsEmailsList.append(",");
		    		 }
		    	 }
		    	 
		    	 emails = recipantsEmailsList.toString();
		    	 emails = emails.substring(0,emails.length()-1);
		    	
		    	  iAdressArray = InternetAddress.parse(emails);
		    	 
			}
			
			 emailMessage.setCreatedBy(user.getUserCode());
			 emailMessage.setFromEmail(user.getEmail());
			 emailMessage.setFromName(fullName);
			 emailMessage.setFirstName(fullName);
			 emailMessage.setEmailType(Constants.EMAIL_CAMPAIGN);
			 
			 signature = sourceleadSignatureService.buildSignature(user, orgLocation);
			 
			 
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("salutation", hotListEmailCampaignDTO.getSaluation());
			 globalVarsMap.put("body", hotListEmailCampaignDTO.getBodytext());
			 
			 EmailScenario  emailScenario = null;
			 if(orgLocation != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.EMAIL_TO_USERS,Constants.ENTITY_TYPE_EMAIL, orgLocation.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EMAIL_TO_USERS,Constants.ENTITY_TYPE_EMAIL);
			   }
			  //covert subject,body and signature
				 String subject = emailScenerioUtil.bulidTemplateSubject(hotListEmailCampaignDTO.getSubject(),globalVarsMap,emailScenario);
				 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
				 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 emailMessage.setOrgLocationId(orgLocation.getId());
			 
			 ArrayList<EmailAttachment> attachmentList = null;
			 FileInfo fileInfo = null;
				 if(hotListEmailCampaignDTO.getFileInfo() != null) {
					 fileInfo = hotListEmailCampaignDTO.getFileInfo().get(0);
					 attachmentList = new ArrayList<EmailAttachment>();
					 EmailAttachment emailAttachment = new EmailAttachment();
					 emailAttachment.setName(fileInfo.getFileName());
					 emailAttachment.setType(fileInfo.getContentType());
					 emailAttachment.setFileUrl(fileInfo.getFileURL());
					 attachmentList.add(emailAttachment);
					 FileUploadUtil.moveFile(hotListEmailCampaignDTO.getStaticPath(), fileInfo, "Email Documents");
				 }
				 
			 
				 ArrayList<EmailAttachment> fileAttachmentList = null;
				 EmailAttachment attachment = new EmailAttachment();
				 
				 if(multiFile.getSize() > 0){
					 fileAttachmentList =  new ArrayList<EmailAttachment>();
					 attachment.setName(multiFile.getOriginalFilename());
					 attachment.setDocFile(multiFile.getBytes());
					 attachment.setType(multiFile.getContentType());
					 fileAttachmentList.add(attachment);
				 }
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,fileAttachmentList);
			 
			saveUserMailBox(user,orgLocation,iAdressArray,subject,hotListEmailCampaignDTO,signature,fileInfo);
			 
			saveToUserMailBox(user,orgLocation,iAdressArray,subject,hotListEmailCampaignDTO.getBodytext(),signature,fileInfo);
		
	}
	
	
	

	private void saveUserMailBox(User user,OrgLocations orgLocation,InternetAddress[] iAdressArray,String subject,HotListEmailCampaignDTO hotListEmailCampaignDTO,String signature,FileInfo fileInfo) throws Exception{
		
	   UserMailBox userMailBox = new UserMailBox();
       if(iAdressArray != null && iAdressArray.length >0){
			StringBuilder recipients = new StringBuilder("");
			int count = 1;
			for(InternetAddress address :iAdressArray){
				if(count >1){
					recipients.append(",");
				}
				count++;
				recipients.append(address.getAddress());
			}
			userMailBox.setToMail(recipients.toString());
		}
		
		userMailBox.setUserId(user);
		userMailBox.setOrgLocationId(orgLocation);
		userMailBox.setFromMail(user.getEmail());
		userMailBox.setMailStatus(Constants.MAIL_BOX_SENT);
		userMailBox.setSalutation(hotListEmailCampaignDTO.getSaluation());
		userMailBox.setSubject(subject);
		userMailBox.setBody(hotListEmailCampaignDTO.getBodytext());
		userMailBox.setSignature(signature);
		userMailBox.setIsMailRead(0);
		userMailBox.setIsFavourite(0);
		userMailBox.setUuid(SourceLeadRandomCodeHelper.generateUUID());
		userMailBox.setStatus(Constants.RECORD_ACTIVE);
		userMailBox.setMailType(1);
		userMailBox.setCreatedBy(user.getUserCode());
		userMailBox.setCreatedDate(new Date());
		userMailBox.setUpdatedBy(user.getUserCode());
		userMailBox.setUpdatedDate(new Date());
		
		if(fileInfo != null) {
			userMailBox.setFileContentType(fileInfo.getContentType());
	   		userMailBox.setFileName(fileInfo.getFileName());
	   		userMailBox.setFileUrl(fileInfo.getFileURL());
		}
		
		userMailBoxService.saveUserMailBox(userMailBox);
		userMailBoxService.saveOrUpdateUserMailBoxELK(userMailBox);
		
	}
	
	
	private void saveToUserMailBox(User user,OrgLocations orgLocation,InternetAddress[] iAdressArray,String subject,String body,String signature,FileInfo fileInfo) throws Exception{
		
		if(iAdressArray != null && iAdressArray.length >0){
			for(InternetAddress address :iAdressArray){
				
				UserRole userRole = userRoleService.getUserDetailsByEmailAndLocationId(address.getAddress(), orgLocation.getId());
				
				if(null != userRole){
					
					UserMailBox userMailBox = new UserMailBox();
					userMailBox.setUserId(userRole.getUser());
					userMailBox.setOrgLocationId(orgLocation);
					userMailBox.setFromMail(user.getEmail());
					userMailBox.setToMail(address.getAddress());
					userMailBox.setMailStatus(Constants.MAIL_BOX_INBOX);
					userMailBox.setSubject(subject);
					userMailBox.setBody(body);
					userMailBox.setSignature(signature);
					userMailBox.setIsMailRead(0);
					userMailBox.setIsFavourite(0);
					userMailBox.setUuid(SourceLeadRandomCodeHelper.generateUUID());
					userMailBox.setStatus(1);
					userMailBox.setMailType(1);
					userMailBox.setCreatedBy(user.getUserCode());
					userMailBox.setCreatedDate(new Date());
					userMailBox.setUpdatedBy(user.getUserCode());
					userMailBox.setUpdatedDate(new Date());
					if(fileInfo != null) {
						userMailBox.setFileContentType(fileInfo.getContentType());
				   		userMailBox.setFileName(fileInfo.getFileName());
				   		userMailBox.setFileUrl(fileInfo.getFileURL());
					}
					userMailBoxService.saveUserMailBox(userMailBox);
					userMailBoxService.saveOrUpdateUserMailBoxELK(userMailBox);
					
				}
			 }
		}
	}

	private boolean validateEmail(String email) {
  		Pattern pattern = Pattern.compile(
  				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
  		Matcher matcher = pattern.matcher(email);
  		return matcher.matches();
  	}

	@Override
	public Response<Boolean> sendEmailFromMailBox(ReadMailDTO readMailDTO, long userId, long orgId, long orgLocationId, String staticPath) {
		Response<Boolean> response = new Response<>();
		try {
			EmailAccountSettings emailAccountSettings = emailAccountSettingsDao.getEmailAccount(readMailDTO.getEmailAccountId(), userId);
			String email = emailAccountSettings.getEmail();
			String password = emailAccountSettings.getPassword();
			String outgoingHost = emailAccountSettings.getOutgoingServer();
			String outgoingPort = emailAccountSettings.getOutgoingPort();
			boolean outgoingSSL = emailAccountSettings.isOutgoingSSL();
			String incomingHost = emailAccountSettings.getIncomingServer();
			String incomingPort = emailAccountSettings.getIncomingPort();
			boolean incomingSSL = emailAccountSettings.isIncomingSSL();
			String storeType = "smtp";
			response = MailBox.composeEmail(outgoingHost, outgoingPort, outgoingSSL, incomingHost, incomingPort, incomingSSL, storeType, email, password,
					orgId, orgLocationId, staticPath, readMailDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
