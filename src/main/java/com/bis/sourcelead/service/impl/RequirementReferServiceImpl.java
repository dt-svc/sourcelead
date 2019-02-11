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
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.RequirementReferDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.ReferContactsDTO;
import com.bis.sourcelead.dto.RequirementReferDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Contact_Source;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementReferService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class RequirementReferServiceImpl implements RequirementReferService {

	
	@Autowired
	RequirementReferDao requirementReferDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ReferContactsDao referContactsDao;
	
	@Autowired
	RequirementDao requirementDao;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Value("${atsUrl}")
    private String atsUrl;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	@Value("${static.path}")
    private String staticPath;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	public RequirementRefer addRequirementRefer(RequirementRefer requirementRefer) throws Exception {
		return requirementReferDao.addRequirementRefer(requirementRefer);
	}

	@Override
	public void saveReferCandidate(User user, OrgLocations orgLocations, RequirementReferDTO requirementReferDTO) throws Exception {
		
		RequirementRefer requirementRefer = new RequirementRefer();
		Requirement requirement = null;
		User referToUser = null;
		
		requirementRefer.setReferFromEmail(user.getEmail());
		requirementRefer.setReferFromUser(user);
		
		
		if(StringUtils.isNoneBlank(requirementReferDTO.getFirstName())){
			requirementRefer.setReferedUserFirstName(requirementReferDTO.getFirstName());
		}
		
		if(StringUtils.isNoneBlank(requirementReferDTO.getLastName())){
			requirementRefer.setReferedUserLastName(requirementReferDTO.getLastName());
		}
		
		if(StringUtils.isNoneBlank(requirementReferDTO.getEmail())){
			requirementRefer.setReferToEmail(requirementReferDTO.getEmail());
			referToUser = userDao.findByEmail(requirementReferDTO.getEmail());
			if(referToUser != null){
				requirementRefer.setReferToUser(referToUser);
			}
		}
		
		if(requirementReferDTO.getReferRequirementId() != null){
			requirement = requirementDao.getRequirementByRequirementId(requirementReferDTO.getReferRequirementId());
			requirementRefer.setRequirementId(requirement);
		}
		
		if(requirementReferDTO.getReferId() != null){
			RequirementRefer parentRefer = new RequirementRefer();
			parentRefer.setId(Long.valueOf(requirementReferDTO.getReferId()));
			requirementRefer.setReferId(parentRefer);
		}
		
		
		if(StringUtils.isNoneBlank(requirementReferDTO.getPhone())){
			requirementRefer.setPhoneNumber(requirementReferDTO.getPhone());
		}
		
		requirementRefer.setReferUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		requirementRefer.setOrgLocationId(orgLocations);
		requirementRefer.setCreatedDate(new Date());
		requirementRefer.setUpdatedDate(new Date());
		requirementRefer.setCreatedBy(user.getUserCode());
		requirementRefer.setUpdatedBy(user.getUserCode());
		
		requirementReferDao.addRequirementRefer(requirementRefer);
		
		ReferContacts referContacts = referContactsDao.getReferContactsByEmails(user.getId(), requirementReferDTO.getEmail());
		String contactUUID = null;
		if(null == referContacts){
			
			referContacts = new ReferContacts();
			
			
			if(StringUtils.isNoneBlank(requirementReferDTO.getFirstName())){
				referContacts.setFirstName(requirementReferDTO.getFirstName());
			}
			
			
			if(StringUtils.isNoneBlank(requirementReferDTO.getLastName())){
				referContacts.setLastName(requirementReferDTO.getLastName());
			}
			
			
			if(StringUtils.isNoneBlank(requirementReferDTO.getEmail())){
				referContacts.setContactUserEmail(requirementReferDTO.getEmail());
				if(null != referToUser){
					referContacts.setContactUser(referToUser);
				}
			}
			
			
			if(StringUtils.isNoneBlank(requirementReferDTO.getPhone())){
				referContacts.setContactUserPhone(requirementReferDTO.getPhone());
			}
			
			referContacts.setReferFromUser(user);
			contactUUID = SourceLeadRandomCodeHelper.generateUUID();
			referContacts.setContactUserUUID(contactUUID);
			if(referToUser != null){
				referContacts.setContactUser(referToUser);
			}
			referContacts.setOrgLocationId(orgLocations);
			referContacts.setCreatedDate(new Date());
			referContacts.setUpdatedDate(new Date());
			referContacts.setCreatedBy(user.getUserCode());
			referContacts.setUpdatedBy(user.getUserCode());
			referContacts.setContactSource(Contact_Source.SELF);
			
			referContactsDao.addReferContacts(referContacts);
			
		}else{
			contactUUID = referContacts.getContactUserUUID();
		}
		
		sendEmailToReferedProfessional(user,requirementReferDTO,requirement,contactUUID,requirementRefer.getReferUUID());
		
	}
	
	
	public void sendEmailToReferedProfessional(User user,RequirementReferDTO requirementReferDTO,Requirement requirement,String contactUUID,String referUUID) throws Exception {
		
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String professionalName = null;
		 
		 StringBuilder loginUser = new StringBuilder();
		 
		 
		 if(StringUtils.isNoneBlank(requirementReferDTO.getLastName())){
			 professionalName = sb.append(requirementReferDTO.getFirstName()).append(" ").append(requirementReferDTO.getLastName()).toString();
		 }else{
			 professionalName = sb.append(requirementReferDTO.getFirstName()).toString();
		 }
		 

		 if(!isNullOrEmpty(user.getLastName())){
			 loginUser = loginUser.append(user.getFirstName()).append(" ").append(user.getLastName());
		 }else{
			 loginUser = loginUser.append(user.getFirstName());
		 }
		 
		 emailMessage.setUserName(loginUser.toString());
		 emailMessage.setToEmail(requirementReferDTO.getEmail());
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setCreatedBy(user.getUserCode());
		 emailMessage.setUserType(user.getUserType());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 emailMessage.setFromName(loginUser.toString());
		 emailMessage.setFirstName(professionalName);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		// globalVarsMap.put("requirementTitle", requirement.getJobTitle());
		 globalVarsMap.put("professinalName", professionalName);
//		 globalVarsMap.put("professinalName", user.getUserName());
 if(user.getLastName() != null && !"".equalsIgnoreCase(user.getLastName())){
			 
			 StringBuilder lname=new StringBuilder();
			 
			 globalVarsMap.put("referedProfessionalFirstName",lname.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			 
		 }
		 else{
			 
			 globalVarsMap.put("referedProfessionalFirstName", user.getFirstName());
		 }
		 
		 
		 
		 
		 if(requirement.getDescription() !=null && !"".equalsIgnoreCase(requirement.getDescription())){
			 globalVarsMap.put("jobDescription", requirement.getDescription()); 
		 }
		 List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList;
			String location = "";
			StringBuilder requirementBuilder=new StringBuilder();
			try {
				locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						requirementLocationsList.add(locationsJob.getLocation());
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							location = requirementBuilder.append(locationsJob.getLocation()).toString();
						}else{
							location = "--";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 if(location !=null && !"".equalsIgnoreCase(location)){
			 globalVarsMap.put("jobLocation", location); 
		 }
		 
		 if(requirement.getJobId() !=null && !"".equalsIgnoreCase(requirement.getJobId())){
			 StringBuilder reqsb=new StringBuilder();
			 
			 globalVarsMap.put("requirementTitle",reqsb.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
		 }
		 
		 else{
			 globalVarsMap.put("requirementTitle", requirement.getJobTitle()); 
			 
		 }
		 
		 String sourceleadUrlWithoutApp = sourceleadUrl;
		 sourceleadUrlWithoutApp = sourceleadUrlWithoutApp.split("/app")[0];
			
		 
		 globalVarsMap.put("professinalName", professionalName);
//		 globalVarsMap.put("professinalName", user.getUserName());
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("registerLink",loginBuilder.append("<a href='").append(atsUrl).append("/").append("referRequirementToProfessional?requirementUUID=").append(requirement.getRequirementUUID()).append("&contactUUID=").append(contactUUID).append("&referUUID=").append(referUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View</div></a>").toString());
		
		 StringBuilder professionalReg = new StringBuilder();
		 globalVarsMap.put("professionalReg",professionalReg.append("<a href='").append(sourceleadUrlWithoutApp).append("/register").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Register</div></a>").toString());
		 
		 
		 StringBuilder linkBuilder = new StringBuilder();
		 globalVarsMap.put("supportLink",linkBuilder.append("<a href='http://www.sourcelead.com/contact.html' target='_blank' style='outline:none; border:none;'>http://www.sourcelead.com/contact.html</a>").toString());
		
		 StringBuilder fbLinkBuilder = new StringBuilder();
		 globalVarsMap.put("facebookLink",fbLinkBuilder.append("<a href='http://www.facebook.com' target='_blank' alt='Facebook' title='Facebook' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/facebook-35x35.gif' width='30px' height='30px'></a>").toString());
		
		 StringBuilder linkedInLinkBuilder = new StringBuilder();
		 globalVarsMap.put("linkedInLink",linkedInLinkBuilder.append("<a href='http://in.linkedin.com' target='_blank' alt='LinkedIn' title='LinkedIn' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/linkedin-35x35.gif' width='30px' height='30px'></a>").toString());
		
		 StringBuilder twitterLinkBuilder = new StringBuilder();
		 globalVarsMap.put("twitterLink",twitterLinkBuilder.append("<a href='http://twitter.com/login' target='_blank' alt='Twitter' title='Twitter' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/twitter-35x35.gif' width='30px' height='30px'></a>").toString());
		
		 StringBuilder googleLinkBuilder = new StringBuilder();
		 globalVarsMap.put("googleLink",googleLinkBuilder.append("<a href='http://www.google.com' target='_blank' alt='Google+' title='Google+' style='margin:5px 5px 0px 0px; display: inline-block;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/gplus-35x35.gif' width='30px' height='30px'></a>").toString());
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REFER_REQUIREMENT_TO_PROFESSIONAL,Constants.ENTITY_TYPE_EMAIL);
		
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
	public Boolean isProfessionalReferToRequirement(String email, Long requirementId, Long userId) throws Exception {
		return requirementReferDao.isProfessionalReferToRequirement(email, requirementId, userId);
	}

	@Override
	public Boolean isReferToRequirement(String fromEmail, Long requirementId) throws Exception {
		return requirementReferDao.isReferToRequirement(fromEmail, requirementId);
	}

	@Override
	public RequirementRefer getProfessionalReferToRequirement(String email, Long requirementId, Long userId)
			throws Exception {
		return requirementReferDao.getProfessionalReferToRequirement(email, requirementId, userId);
	}

	@Override
	public List<Long> getReferByMeRequirementsByUserId(Long userId,Integer offset,Integer maxResults) throws Exception {
		return requirementReferDao.getReferByMeRequirementsByUserId(userId,offset,maxResults);
	}

	@Override
	public List<RequirementRefer> getRequirementReferListByEmail(String email) throws Exception {
		return requirementReferDao.getRequirementReferListByEmail(email);
	}

	@Override
	public List<RequirementRefer> getReferedToMeRequirementsByUserId(Long userId,Integer offset,Integer maxResults,String jobTitle,String location,String postedOn) throws Exception {
		return requirementReferDao.getReferedToMeRequirementsByUserId(userId,offset,maxResults,jobTitle,location,postedOn);
	}

	@Override
	public RequirementRefer getRequirementReferById(Long referId) throws Exception {
		return requirementReferDao.getRequirementReferById(referId);
	}

	@Override
	public RequirementRefer getAppliedRequirementReferByRequirementIdAndUserId(Long requirementId, Long userId)
			throws Exception {
		return requirementReferDao.getAppliedRequirementReferByRequirementIdAndUserId(requirementId, userId);
	}

	@Override
	public Integer getReferJobsByReferType(Long userId, String referType, Date startDate, Date endDate)
			throws Exception {
		return requirementReferDao.getReferJobsByReferType(userId, referType, startDate, endDate);
	}

	@Override
	public Integer getReferJobsByReferTypeByReferedDate(Long userId, String referType, Date referedDate)
			throws Exception {
		return requirementReferDao.getReferJobsByReferTypeByReferedDate(userId, referType, referedDate);
	}

	@Override
	public List<RequirementRefer> getReferJobsListByReferType(Long userId, String referType, Date startDate, Date endDate) throws Exception {
		return requirementReferDao.getReferJobsListByReferType(userId, referType, startDate, endDate);
	}

	@Override
	public List<RequirementRefer> getReferJobsListByReferTypeByReferedDate(Long userId, String referType, Date referedDate) throws Exception {
		return requirementReferDao.getReferJobsListByReferTypeByReferedDate(userId, referType, referedDate);
	}
	
	@Override
	public Integer getReferredByMeCountByStartDateAndEndDate(Long userId, Date startDate, Date endDate) throws Exception {
		return requirementReferDao.getReferredByMeCountByStartDateAndEndDate(userId, startDate, endDate);
	}
	
	@Override
	public List<RequirementRefer> getReferredByMeCountListByStartDateAndEndDate(Long userId, Date startDate, Date endDate) throws Exception {
		return requirementReferDao.getReferredByMeCountListByStartDateAndEndDate(userId, startDate, endDate);
	}

	@Override
	public List<RequirementRefer> getLoginUserReferredListByUserId(Long userId,Long requirementId) throws Exception {
		return requirementReferDao.getLoginUserReferredListByUserId(userId, requirementId);
	}

	@Override
	public List<RequirementRefer> getLoginUserReferToMeListByUserId(Long userId) throws Exception {
		return requirementReferDao.getLoginUserReferToMeListByUserId(userId);
	}

	@Override
	public void submitUserContactsToRequirement(User user, OrgLocations orgLocations, ReferContactsDTO referContactsDTO)
			throws Exception {
		
		
		if(referContactsDTO.getUserContacts() != null && referContactsDTO.getUserContacts().size() > 0){
			
			
			Requirement requirement = requirementDao.getRequirementByRequirementId(referContactsDTO.getContactReferRequirementId());
			
			for(String userContacts : referContactsDTO.getUserContacts()){
				ReferContacts referContacts = referContactsDao.getReferContactsById(Long.parseLong(userContacts));
				RequirementRefer refer = new RequirementRefer();
				refer.setReferFromUser(user);
				refer.setReferToUser(referContacts.getContactUser());
				refer.setReferFromEmail(user.getEmail());
				refer.setReferedUserFirstName(referContacts.getFirstName());
				refer.setReferedUserLastName(referContacts.getLastName());
				refer.setReferToEmail(referContacts.getContactUserEmail());
				refer.setRequirementId(requirement);
				refer.setOrgLocationId(orgLocations);
				refer.setReferUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				refer.setCreatedDate(new Date());
				refer.setUpdatedDate(new Date());
				refer.setCreatedBy(user.getUserCode());
				refer.setPhoneNumber(referContacts.getContactUserPhone());
				refer.setUpdatedBy(user.getUserCode());
				requirementReferDao.addRequirementRefer(refer);
				
				sendEmailToContactUsers(user, referContacts, requirement, referContacts.getContactUserUUID(),refer.getReferUUID());
			}
		}
	
	}
	
	
	
	public void sendEmailToContactUsers(User user,ReferContacts referContacts,Requirement requirement,String contactUUID,String referUUID) throws Exception {
		
		 
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 String professionalName = null;
		 
		 StringBuilder loginUser = new StringBuilder();
		 
		 if(!isNullOrEmpty(referContacts.getLastName())){
			 professionalName = sb.append(referContacts.getFirstName()).append(" ").append(referContacts.getLastName()).toString();
		 }else{
			 professionalName = sb.append(referContacts.getFirstName()).toString();
		 }
		 

		 if(!isNullOrEmpty(user.getLastName())){
			 loginUser = loginUser.append(user.getFirstName()).append(" ").append(user.getLastName());
		 }else{
			 loginUser = loginUser.append(user.getFirstName());
		 }
		 
		 emailMessage.setUserName(loginUser.toString());
		 emailMessage.setToEmail(referContacts.getContactUserEmail());
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setCreatedBy(user.getUserCode());
		 emailMessage.setUserType(user.getUserType());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 emailMessage.setFromName(loginUser.toString());
		 emailMessage.setFirstName(professionalName);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("requirementTitle", requirement.getJobTitle());
		 globalVarsMap.put("professinalName", professionalName);
		 globalVarsMap.put("referedProfessionalFirstName", user.getFullName());
		 if(requirement.getDescription() !=null && !"".equalsIgnoreCase(requirement.getDescription())){
			 globalVarsMap.put("jobDescription", requirement.getDescription()); 
		 }
		 List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList;
			String location = "";
			StringBuilder requirementBuilder=new StringBuilder();
			try {
				locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						requirementLocationsList.add(locationsJob.getLocation());
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							location = requirementBuilder.append(locationsJob.getLocation()).toString();
						}else{
							location = "--";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 if(location !=null && !"".equalsIgnoreCase(location)){
			 globalVarsMap.put("jobLocation", location); 
		 }
		 StringBuilder loginBuilder = new StringBuilder();
		 globalVarsMap.put("registerLink",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("referRequirementToProfessional?requirementUUID=").append(requirement.getRequirementUUID()).append("&contactUUID=").append(contactUUID).append("&referUUID=").append(referUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View</div></a>").toString());
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REFER_REQUIREMENT_TO_PROFESSIONAL,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = null;
		 if(StringUtils.isNotBlank(emailScenario.getSignature())){
			 signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario); 
		 }
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org(),new OrgLocations());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,1L,iAdressArray,new ArrayList<EmailAttachment>());
		
	}

	@Override
	public RequirementRefer getRequirementReferByReferUUID(String referUUID) throws Exception
	{
		return requirementReferDao.getRequirementReferByReferUUID(referUUID);
	}
	

}
