package com.bis.sourcelead.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.ReferContactsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Contact_Source;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class ReferContactsServiceImpl implements ReferContactsService{
	
	@Autowired
	ReferContactsDao referContactsDao;
	
	@Autowired
	UserDao userDao;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;

	@Autowired
	UserService userService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Value("${static.path}")
    private String staticPath;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public ReferContacts addReferContacts(ReferContacts referContacts) throws Exception {
		return referContactsDao.addReferContacts(referContacts);
	}

	@Override
	public ReferContacts getReferContactsByUUID(String contactUUID) throws Exception {
		return referContactsDao.getReferContactsByUUID(contactUUID);
	}

	@Override
	public ResponseList<ReferContactsDTO> getReferContactsListByUserIdAndOrglocationId(ReferContactsDTO contactsDTO,Long userId, Long orgLocationId) throws Exception {
		return referContactsDao.getReferContactsListByUserIdAndOrglocationId(contactsDTO,userId,orgLocationId);
	}

	@Override
	public ReferContacts getReferContactsByEmails(Long userID, String toEmail) throws Exception {
		return referContactsDao.getReferContactsByEmails(userID, toEmail);

	}

	@Override
	public void saveuserContacts(User user,  ReferContactsDTO referContactsDTO) throws Exception {
		
		ReferContacts referContacts=null;
		
		if(referContactsDTO.getId() !=null){
			referContacts=referContactsDao.getReferContactsById(referContactsDTO.getId());
			if(referContacts !=null){
				if(StringUtils.isNoneBlank(referContactsDTO.getFirstName())){
					referContacts.setFirstName(referContactsDTO.getFirstName());
				}
				
				if(StringUtils.isNoneBlank(referContactsDTO.getLastName())){
					referContacts.setLastName(referContactsDTO.getLastName());
				}else {
					referContacts.setLastName(null);
				}
				
				if(StringUtils.isNoneBlank(referContactsDTO.getPhone())){
					referContacts.setContactUserPhone(referContactsDTO.getPhone());
				}else {
					referContacts.setContactUserPhone(null);
				}
				if(StringUtils.isNoneBlank(referContactsDTO.getContactType())){
					if(referContactsDTO.getContactType().equalsIgnoreCase("vendor")){
						referContacts.setVendorType(1);
						referContacts.setClientType(null);
						referContacts.setCandidateType(null);
						referContacts.setOtherType(null);
					}else if(referContactsDTO.getContactType().equalsIgnoreCase("client")){
						referContacts.setClientType(1);
						referContacts.setVendorType(null);
						referContacts.setCandidateType(null);
						referContacts.setOtherType(null);
					}else if(referContactsDTO.getContactType().equalsIgnoreCase("candidate")){
						referContacts.setCandidateType(1);
						referContacts.setVendorType(null);
						referContacts.setClientType(null);
						referContacts.setOtherType(null);
					}else{
						referContacts.setOtherType(1);
						referContacts.setVendorType(null);
						referContacts.setClientType(null);
						referContacts.setCandidateType(null);
					}
					
				}
				
				if(StringUtils.isNotBlank(referContactsDTO.getEmail())) {
					referContacts.setContactUserEmail(referContactsDTO.getEmail());
				}
				if(StringUtils.isNoneBlank(referContactsDTO.getCompanyName())){
					referContacts.setCompanyName(referContactsDTO.getCompanyName());
				}else {
					referContacts.setCompanyName(null);
				}
				referContacts.setUpdatedDate(new Date());
				referContacts.setUpdatedBy(user.getUserCode());
			}
		}else{
			 referContacts = new ReferContacts();
			
			if(StringUtils.isNoneBlank(referContactsDTO.getFirstName())){
				referContacts.setFirstName(referContactsDTO.getFirstName());
			}
			
			if(StringUtils.isNoneBlank(referContactsDTO.getLastName())){
				referContacts.setLastName(referContactsDTO.getLastName());
			}
			
			if(StringUtils.isNoneBlank(referContactsDTO.getEmail())){
				referContacts.setContactUserEmail(referContactsDTO.getEmail());
				User contactUser = userDao.getUserByEmailAndUserType(referContactsDTO.getEmail(), Constants.PROFESSIONAL);
				if(null != contactUser){
					referContacts.setContactUser(contactUser);
				}
			}
			
			if(StringUtils.isNoneBlank(referContactsDTO.getPhone())){
				referContacts.setContactUserPhone(referContactsDTO.getPhone());
			}
			if(StringUtils.isNoneBlank(referContactsDTO.getContactType())){
				if(referContactsDTO.getContactType().equalsIgnoreCase("vendor")){
					referContacts.setVendorType(1);
				}else if(referContactsDTO.getContactType().equalsIgnoreCase("client")){
					referContacts.setClientType(1);
				}else if(referContactsDTO.getContactType().equalsIgnoreCase("candidate")){
					referContacts.setCandidateType(1);
				}else{
					referContacts.setOtherType(1);
				}
				
			}
			if(StringUtils.isNoneBlank(referContactsDTO.getCompanyName())){
				referContacts.setCompanyName(referContactsDTO.getCompanyName());
			}
			
			referContacts.setReferFromUser(user);
			String contactUUID = SourceLeadRandomCodeHelper.generateUUID();
			referContacts.setContactUserUUID(contactUUID);
			referContacts.setCreatedDate(new Date());
			
			referContacts.setCreatedBy(user.getUserCode());
			
			referContacts.setContactSource(Contact_Source.SELF);
			
			
			sendEmailToReferedContact(user,referContactsDTO,contactUUID);
		}
		
		referContactsDao.addReferContacts(referContacts);
	}
	
	
	public void sendEmailToReferedContact(User user,ReferContactsDTO referContactsDTO,String contactUUID) throws Exception {
		
		 
		 StringBuilder sb = new StringBuilder();
		 StringBuilder loginUserName = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String professionalName = null;
		 String userName = null;
		 
		 if(!isNullOrEmpty(user.getLastName())){
			 userName = loginUserName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		 }else{
			 userName = loginUserName.append(user.getFirstName()).toString();
		 }
		 
		 if(StringUtils.isNoneBlank(referContactsDTO.getLastName())){
			 professionalName = sb.append(referContactsDTO.getFirstName()).append(" ").append(referContactsDTO.getLastName()).toString();
		 }else{
			 professionalName = sb.append(referContactsDTO.getFirstName()).toString();
		 }
		 
		 emailMessage.setToEmail(referContactsDTO.getEmail());
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setCreatedBy(user.getUserCode());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("contactName", professionalName);
		 globalVarsMap.put("userName",userName);
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("registerLink",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("register?contactUUID=").append(contactUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Register</div></a>").toString());
		 
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ADD_NEW_USER_CONTACT,Constants.ENTITY_TYPE_EMAIL);
		
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org(),new OrgLocations());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		 
		 
		
	}

	@Override
	public List<ReferContacts> getUserContactsListByUserIdAndRequirementId(Long userId, Long requirementId)
			throws Exception {
		return referContactsDao.getUserContactsListByUserIdAndRequirementId(userId, requirementId);
	}

	@Override
	public ReferContacts getReferContactsById(Long id) throws Exception {
		return referContactsDao.getReferContactsById(id);
	}

	@Override
	public void saveAllReferContacts(List<ReferContacts> referContactsList) throws Exception {
		  referContactsDao.saveAllReferContacts(referContactsList);
	}

	@Override
	public Boolean validateGoogleEamil(String email, Long loginUserId) throws Exception {
		return referContactsDao.validateGoogleEamil(email, loginUserId);
	}

	@Override
	public ReferContacts findByEmail(String recipientMail) throws Exception
	{
		return referContactsDao.findByEmail(recipientMail);
	}

	@Override
	public void saveuserContactsFromConnections(User fromUser, User toUser,OrgLocations loginOrgLocation, String sourceleadUrl, String sourceleadLoginUrl)
			throws Exception {
		
		ReferContacts referContacts = new ReferContacts();
		if(StringUtils.isNoneBlank(toUser.getFirstName())){
			referContacts.setFirstName(toUser.getFirstName());
		}
		if(StringUtils.isNoneBlank(toUser.getLastName())){
			referContacts.setLastName(toUser.getLastName());
		}

		referContacts.setContactUser(toUser);
		if(StringUtils.isNoneBlank(toUser.getMobilePhone())){
			referContacts.setContactUserPhone(toUser.getMobilePhone());
		}
		referContacts.setReferFromUser(fromUser);
		String contactUUID = SourceLeadRandomCodeHelper.generateUUID();
		referContacts.setContactUserEmail(toUser.getEmail());
		referContacts.setContactUserUUID(contactUUID);
		referContacts.setCreatedDate(new Date());
		referContacts.setUpdatedDate(new Date());
		referContacts.setCreatedBy(fromUser.getUserCode());
		referContacts.setUpdatedBy(fromUser.getUserCode());
		referContacts.setContactSource(Contact_Source.SELF);
		referContactsDao.addReferContacts(referContacts);
		
		connectionInvitationMailToUser(fromUser, toUser, loginOrgLocation.getId(), referContacts.getContactUserUUID(), sourceleadUrl, sourceleadLoginUrl);
		
		
	}

	
	private void connectionInvitationMailToUser(User loginUser, User toUser, Long locationId, String connectionUUID, String sourceleadUrl, String sourceleadLoginUrl)
			throws Exception {
		OrgLocations locations = orgLocationService.getOrgLocations(locationId);
		UserRole userRole = userRoleService.getUserRoleByUserId(toUser.getId());
		StringBuilder sb = new StringBuilder();
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(toUser.getFirstName());
		emailMessage.setLastName(toUser.getLastName());
		emailMessage.setToEmail(toUser.getEmail());
		emailMessage.setFromEmail("donotreply@sourcelead.com");
		emailMessage.setFromName("donotreply@sourcelead.com");
		emailMessage.setCreatedBy(loginUser.getUserCode());
		// emailMessage.setOrgLocationId(locationId);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("receiverUserName", toUser.getFullName());
		String loginUserFullName = loginUser.getFullName().substring(0, 1).toUpperCase()
				+ loginUser.getFullName().substring(1);
		globalVarsMap.put("loginUserName", loginUserFullName);
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("loginLink", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Login</div></a>")
				.toString());
		globalVarsMap.put("receiverEmail", toUser.getEmail());
		StringBuilder connectionsBuilder = sendEmailToInvitationUserTable(loginUser, toUser, locations, connectionUUID,sourceleadUrl, sourceleadLoginUrl);
		globalVarsMap.put("profilePicUrl", connectionsBuilder.toString());
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = null;
		 if(locations != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.INVITE_CONNECTION_USER,Constants.ENTITY_TYPE_EMAIL, locations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_CONNECTION_USER,Constants.ENTITY_TYPE_EMAIL);
			   }
		EmailScenario noificationEmailScenario = emailScenarioService
				.getEmailScenarioByUniqueName(Constants.INVITE_CONNECTION_USER, Constants.ENTITY_TYPE_NOTIFICATION);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = "";
		 String url = sourceleadUrl;
		StringBuilder acceptBuilder = new StringBuilder();
		String notificationbody = emailScenerioUtil.buildTemplateBody(noificationEmailScenario.getBody(), globalVarsMap,noificationEmailScenario);
		noificationEmailScenario.setBody(notificationbody);
		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, userRole.getOrgLocations().getOrg(), userRole.getOrgLocations());
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, locationId, iAdressArray, new ArrayList<EmailAttachment>());
		 StringBuilder notificationLink = new StringBuilder();
		 String actionLink="";
	     actionLink = notificationLink.append(sourceleadUrl).append("/").append("loadReceivedConnections?status=received").toString();
		UserRole role = userRoleService.getUserRoleByUserId(toUser.getId());
		emailScenerioUtil.sendNotification(actionLink, loginUser, toUser, role.getOrgLocations(),
				noificationEmailScenario, null);
	}
	
	public StringBuilder sendEmailToInvitationUserTable(User loginUser, User toUser, OrgLocations locations,
			String connectionUUID, String sourceleadUrl, String sourceleadLoginUrl) throws Exception {
		StringBuilder tempHTML = new StringBuilder();
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(loginUser.getId(), locations.getId());
		String imageUrl = StringUtils.removeEnd(sourceleadUrl, sourceleadUrl.substring(sourceleadUrl.lastIndexOf("/")));
		StringBuilder loginBuilder12 = new StringBuilder();
		if (loginUser.getFileUrl() != null) {
			loginBuilder12.append("<img src='" + imageUrl + "" + loginUser.getFileUrl()
					+ "' style='width: 60px; height: 60px; border-radius: 50%;'>").toString();
		} else {
			loginBuilder12.append("<img src='" + imageUrl
					+ "/resources/images/userprofileImg.jpg'  style='width: 60px; height: 60px; border-radius: 50%;'>")
					.toString();
		}
		tempHTML.append("<a style='text-decoration:none; display:block;' href='javascript:void(0)'>");
		tempHTML.append("<div style='background-color: #fff; padding: 10px; border-bottom: 1px solid #f1f1f1;'>");
		tempHTML.append("		<div class='userImg' style='width: 60px; padding: 0px 20px 0px 5px; float: left;'>");
		tempHTML.append(loginBuilder12.toString());
		tempHTML.append("		</div>");
		tempHTML.append("		<div style='width:350px; float: left; padding: 0px 0px;'>");
		tempHTML.append(
				"			<p style='padding: 0px 0px 3px; margin: 0px 0px; font-size: 14px; font-weight: 600; color: #454545;'>")
				.append(loginUser.getFullName()).append("</p>");
//		tempHTML.append(
//				"			<p style='padding: 0px 0px 3px; margin: 0px 0px; font-size: 13px; font-weight: 500; color: #666;'>")
//				.append(userRole.getUserType()).append(" at ").append(locations.getOrgLocationName() + "</span></p>");
		// tempHTML.append(" <p style='padding: 0px 0px 3px; margin: 0px 0px;
		// font-size: 13px; font-weight: 500; color: #666;'>16 mutual
		// connections</p>");
		tempHTML.append("		</div>");
		tempHTML.append("   	<div style='float: right; padding: 5px;'>");
		tempHTML.append("<a href='").append(sourceleadUrl).append("/ws/acceptConnection?connectionUUID=")
				.append(connectionUUID)
				.append(" ' style='display: block; background-color: #1176d4; color: #fff; text-decoration: none; padding: 4px 15px 6px; border-radius: 2px; font-size: 14px; line-height: 20px;' href='javascript:void(0)'>Accept </a>");
		tempHTML.append("   	</div>");
		tempHTML.append("   	<div style='clear:both'></div>");
		tempHTML.append("	</div>");
		tempHTML.append("   	<div style='text-align: center; padding: 15px 0px 5px'>");
		tempHTML.append("<a href='").append(sourceleadLoginUrl).append(
				" ' style='text-decoration: none; font-size: 14px; font-weight: 600; color: #214b90;' href='javascript:void(0)'>Manage Invitations </a>");
		tempHTML.append("   	</div>");
		tempHTML.append(" </a>");
		return tempHTML;
	}
	@Override
	public ReferContacts getReferContactsByFromUserIdAndToUserId(Long fromUserId, Long toUserId) throws Exception {
		return referContactsDao.getReferContactsByFromUserIdAndToUserId(fromUserId, toUserId);
	}

	@Override
	public boolean deleteContactById(Long id) throws Exception {
		
		ReferContacts  referContacts=referContactsDao.getReferContactsById(id);
		if(referContacts != null){
			ReferContacts referContact=referContactsDao.deleteContactById(referContacts);
			if(referContact != null){
				return true;
			}else
				return false;
		}
		return false;
	}

	

}
