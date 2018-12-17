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
import com.bis.sourcelead.enums.Contact_Source;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ReferContactsService;
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
	public void saveuserContactsFromConnections(User fromUser, User toUser)
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
