package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CombinedList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.UserSignature;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailService;
import com.bis.sourcelead.service.HotListEmailCampaignService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadSignatureService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.UserSignatureService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class MailBoxRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(MailBoxRestController.class);
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	UserSignatureService userSignatureService;
	
	@Autowired
	HotListEmailCampaignService hotListEmailCampaignService;
	
	@Autowired
	UserMailBoxService userMailBoxService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	SourceleadSignatureService sourceleadSignatureService;
	
	@Autowired
	private RequirementAssignToService requirementAssignToService;
	
	@Autowired
	private RolesService roleService;
	
	@Autowired
	TeamService teamService;
	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/getClientContactsEmailsByOrgRelationId", method = RequestMethod.POST)
	@ResponseBody String getClientContactsEmailsByOrgRelationId(@RequestParam(value = "orgRelationIds", required = false) String orgRelationIds,
			HttpServletRequest request) throws Exception {
		
		
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    
		String[] relationsId = orgRelationIds.split(",");
		StringBuilder emails  = new StringBuilder();
		for(int i=0 ; i < relationsId.length; i++){
			List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocContactEmailsByOrgRelationIdAndUserId(user.getId(), Long.valueOf(relationsId[i]), orgLocations.getId());
			for(OrgSpoc orgSpoc :orgSpocList){
				emails.append(orgSpoc.getClientUser().getEmail()).append(",");
			}
		}
		emails.toString();
		
		 String spocEmails =  emails.substring(0,emails.length()-1);
		 
		return commonUtil.toJSON(spocEmails);
	}
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/sendHotListCampaign", method = RequestMethod.POST)
	public String sendHotListCampaign(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		hotListEmailCampaignService.sendHotListEmailCampaign(jsonObj, user, orgLocation, multiFile);
		
		
		
		return "success";
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_MAIL_BOX')")
	@RequestMapping(value = "/sendEmailToUsers", method = RequestMethod.POST)
	public String sendEmailToUsers(MultipartHttpServletRequest request) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		
		/*String jsonc = "{" + json + "}";*/
		JSONObject jsonObj = new JSONObject(request.getParameter("json"));
		
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		emailService.sendEmail(jsonObj, user, orgLocation, multiFile);
		
		
		return "success";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX')")
	@RequestMapping(value = "/saveUserSignature", method = RequestMethod.POST)
	public String saveUserSignature(@RequestParam(value = "json", required = false) String json,HttpServletRequest request) throws Exception {
		
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		userSignatureService.setUserSignatureDetails(jsonObj, user, orgLocation);
		
		
		return "success";
	}
	
	
	@RequestMapping(value = "/sendRequestForResume", method = RequestMethod.POST)
	public String sendRequestForResume(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "candidatesIds", required = false) String candidatesIds,
			@RequestParam(value = "UUID", required = false) String UUID,
			MultipartHttpServletRequest request) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		userMailBoxService.setRequestForResumeDetails(jsonObj,candidatesIds,UUID,multiFile);
		
		
		return "success";
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX')")
	@RequestMapping(value = "/saveMailAsFavourite", method = RequestMethod.POST)
	public String saveMailAsFavourite(@RequestParam(value = "mailboxId", required = false) String mailboxId) throws Exception {
		
		
		UserMailBox mailBox = userMailBoxService.getUserMailBoxById(mailboxId);
		if(null != mailBox){
			if(mailBox.getIsFavourite().equals(1)){
				mailBox.setIsFavourite(0);
			}else{
				mailBox.setIsFavourite(1);
			}
			
			userMailBoxService.saveUserMailBox(mailBox);
			userMailBoxService.saveOrUpdateUserMailBoxELK(mailBox);
		}
		
		return "success";
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX')")
	@RequestMapping(value = "/sendMailsToTrash", method = RequestMethod.POST)
	public String sendMailsToTrash(@RequestParam(value = "mailIds", required = false) String mailIds,HttpServletRequest request) throws Exception {
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		UserMailBox mailbox = new UserMailBox();
		String resultJson;
		
		if(!isNullOrEmpty(mailIds)){
			String[] mailBoxIds = mailIds.split(",");
			for(int i=0; i < mailBoxIds.length; i++){
				
				UserMailBox userMailBox = userMailBoxService.getUserMailBoxById(mailBoxIds[i]);
				userMailBox.setIsFavourite(0);
				if(!userMailBox.getMailStatus().equalsIgnoreCase("Trash")){
					userMailBox.setMailStatus("Trash");
				}else{
					userMailBox.setMailStatus("Deleted");
					userMailBox.setStatus(0);
				}
				
				userMailBox.setUpdatedBy(user.getCreatedBy());
				userMailBox.setUpdatedDate(new Date());
				userMailBoxService.saveUserMailBox(userMailBox);
				userMailBoxService.saveOrUpdateUserMailBoxELK(userMailBox);
			}
		}
		
		Integer inboxUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocations.getId(), "Inbox");
		Integer requestedUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocations.getId(), "Requested");
		mailbox.setUnreadInboxCount(inboxUnreadMailsCount);
		mailbox.setUnreadRequestedCount(requestedUnreadMailsCount);
		
		resultJson = commonUtil.toJSON(mailbox);
		return resultJson;
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX')")
	@RequestMapping(value = "/getHotListedCandidateDetailsByUUID")
	public @ResponseBody String getHotListedCandidateDetailsByUUID(@RequestParam(value = "UUID", required = false) String UUID,
			@RequestParam(value = "type", required = false) String type,
			HttpServletRequest request) throws Exception {
		
		 String resultJson;
		 List<Candidate> hotListCandidatesList = new ArrayList<>();
		 UserMailBox userMailbox = userMailBoxService.getUserMailBoxByUUID(UUID);
		 if(userMailbox != null && !isNullOrEmpty(userMailbox.getCandidateId())){
			 String[] candidateId = userMailbox.getCandidateId().split(",");
			 for(int i=0; i < candidateId.length; i++){
				 Candidate candidate = candidateService.getCandidateByCandidateId(Long.valueOf(candidateId[i]));
				 hotListCandidatesList.add(candidate);
			 }
		 }
		 
	   resultJson = commonUtil.toJSON(hotListCandidatesList);
	   return resultJson;
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX')")
	@RequestMapping(value = "/getEmailDetails")
	public @ResponseBody String getEmailDetails(@RequestParam(value = "UUID", required = false) String UUID,
			@RequestParam(value = "type", required = false) String type,
			HttpServletRequest request) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
	    String resultJson;
	    
	    UserMailBox userMailbox = userMailBoxService.getUserMailBoxByUUID(UUID);
	    List<Candidate> reqCandidatesList = new ArrayList<Candidate>();
	    List<UserMailBox> mailBoxList = new ArrayList<UserMailBox>();
	    
	    if(userMailbox != null){
	    	
	    	UserMailBox previousMail = userMailBoxService.getUserPreviousMail(userMailbox.getId(), user.getId(), orgLocation.getId(), type);
	    	
	    	UserMailBox nextMail = userMailBoxService.getUserNextMail(userMailbox.getId(), user.getId(), orgLocation.getId(), type);
	    	
		    	userMailbox.setIsMailRead(1);
				userMailBoxService.saveUserMailBox(userMailbox);
				userMailBoxService.saveOrUpdateUserMailBoxELK(userMailbox);
				
				Integer inboxUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocation.getId(), "Inbox");
				Integer requestedUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocation.getId(), "Requested");
				userMailbox.setUnreadInboxCount(inboxUnreadMailsCount);
				userMailbox.setUnreadRequestedCount(requestedUnreadMailsCount);
				userMailbox.setTotalUnReadMailsCount(inboxUnreadMailsCount+requestedUnreadMailsCount);
				SourceLeadSessionManager.setSessionAttribute(request, "unReadMailsCount", inboxUnreadMailsCount+requestedUnreadMailsCount);
				userMailbox.setCandidatesList(reqCandidatesList);
				if(null != nextMail){
					userMailbox.setNextMailUUID(nextMail.getUuid());
				}
				
				if(null != previousMail){
					userMailbox.setPreviousMailUUID(previousMail.getUuid());
				}
				
				
				
				mailBoxList.add(userMailbox);
				calculateDays(mailBoxList);
				
	    	
		}
	    resultJson = commonUtil.toJSON(mailBoxList);
		return resultJson;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX')")
	@RequestMapping(value = "/downloadMailAttachment", method = RequestMethod.GET)
    public void downloadMailAttachment(@RequestParam(value="UUID", required=false) String UUID ,HttpServletResponse response) throws Exception {
		
		 UserMailBox userMailbox = userMailBoxService.getUserMailBoxByUUID(UUID);
		    
		    response.setContentType("application/octet-stream");
		    StringBuilder fileName = new StringBuilder();
	        response.setHeader("Content-Disposition",  fileName.append("attachment; filename=\"").append(userMailbox.getFileName()).append("\"").toString());
		    
			InputStream inputStream = new ByteArrayInputStream(userMailbox.getDocFile());
			try {
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();

			} catch (FileNotFoundException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			} 
 }
	
	
	

	@RequestMapping(value = "/validateCampaignToEmail", method = RequestMethod.POST)
	public String validateCampaignToEmail(@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "UUID", required = false) String UUID,HttpServletRequest request) throws Exception {
		
		String result = "Invalid";
		UserMailBox userMailBox = userMailBoxService.getUserMailBoxByUUID(UUID);
		if(userMailBox != null){
			String[] toEmail = userMailBox.getToMail().split(",");
			for(int i = 0; i < toEmail.length; i++ ){
				if(email.equalsIgnoreCase(toEmail[i].replaceAll("\"", ""))){
					result = "success";
				}
			}
			
		}
		
		return result;
	}
	
	
	 @PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX')")
	 @RequestMapping(value = "/getSignatures", method = RequestMethod.POST)
		public @ResponseBody String getSignatures(HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			List<UserSignature> singaturesList = userSignatureService.getUserSignatureListByUserIdAndLocationId(user.getId(), orgLocations.getId());
			resultJson = toJSON(singaturesList);
			return resultJson;
		}
	 
	 
	 @PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX')")
	 @RequestMapping(value = "/deleteReadMail", method = RequestMethod.POST)
		public String deleteReadMail(@RequestParam(value = "UUID", required = false) String UUID,
				@RequestParam(value = "type", required = false) String type,HttpServletRequest request) throws Exception {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			UserMailBox mailbox = userMailBoxService.getUserMailBoxByUUID(UUID);
			if(null != mailbox && null != type){
				if(type.equalsIgnoreCase(Constants.MAIL_TRASH)){
					mailbox.setMailStatus(Constants.MAIL_DELETE);
					mailbox.setStatus(0);
				}else{
					mailbox.setMailStatus(Constants.MAIL_TRASH);
				}
				userMailBoxService.saveUserMailBox(mailbox);
				userMailBoxService.saveOrUpdateUserMailBoxELK(mailbox);
			}
			return "success";
		}

	 
	 
	 @RequestMapping(value = "/getRequestedCandidates", method = RequestMethod.POST)
		public @ResponseBody String getRequestedCandidates(@RequestParam(value = "UUID", required = false) String UUID,
				HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			UserMailBox userMailbox = userMailBoxService.getUserMailBoxByUUID(UUID);
			List<Long> candidateId = new ArrayList<Long>();
			 for (String s : userMailbox.getCandidateId().split(",")){
				 candidateId.add(Long.parseLong(s));
			 }
			
			List<Candidate> reqCandidatesList = new ArrayList<Candidate>();
			List<Candidate> candidatesList = candidateService.getAllRequestedCandidatesForCampaign(candidateId);
			if(null != candidatesList){
			
				for(Candidate candidate : candidatesList){
					StringBuilder skills = new StringBuilder();
					String candidateSkills = null;
//					List<CandidateSkills> skillsList = candidateSkillsService.getCandidateSkillsByCandidateId(candidate.getId().toString());
					String  skillsNames = candidate.getSkillsName();
					if(null != skillsNames){
						//List<String> skillsList = new ArrayList<String>(Arrays.asList(skillsNames.split(",")));
						String [] skillsList = skillsNames.split(",");
						if(skillsList.length > 0){
							 for(String skill : skillsList){
								 skills.append(skill).append(", ");
							 }
							 skills.toString();
							 candidateSkills =  skills.substring(0,skills.length()-2);
						 }
					
					
					}
					
					 
					
					 
					
					 StringBuilder candidateName = new StringBuilder();
					 String experienceInYears = null;
					 String experienceInMonths = null;
					 StringBuilder totalExp = new StringBuilder();
					 StringBuilder rate = new StringBuilder();
					 
					 StringBuilder nameBuilder = new StringBuilder();
					 StringBuilder expBuilder = new StringBuilder();
					 StringBuilder rateBuilder = new StringBuilder();
					 
					 if(userMailbox.getNameFields().equalsIgnoreCase("firstname")){
						 candidateName = nameBuilder.append(candidate.getFirstName());
					 }
				 	 else if(userMailbox.getNameFields().equalsIgnoreCase("firstnameandlastName")){
				 		if(!isNullOrEmpty(candidate.getLastName())){
							 candidateName = nameBuilder.append(candidate.getFirstName()).append(" ").append(candidate.getLastName());
					   	 }else{
					   		candidateName = nameBuilder.append(candidate.getFirstName());
					   	 }
					 }
				 	 else if(userMailbox.getNameFields().equalsIgnoreCase("firstname1stletterlastname")){
				 		if(!isNullOrEmpty(candidate.getLastName())){
				 			candidateName = nameBuilder.append(candidate.getFirstName()).append(" ").append(String.valueOf(candidate.getLastName().charAt(0)));
				 		 }else{
				 			candidateName = nameBuilder.append(candidate.getFirstName());
				 		 }
				 		
				 	 }
				 	 else if(userMailbox.getNameFields().equalsIgnoreCase("1letterfirstnamelastname")){
				 		 if(!isNullOrEmpty(candidate.getLastName())){
					 			candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0))).append(" ").append(candidate.getLastName());
					 		 }else{
					 			candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0)));
					 		 }
				 		
				 	 }
				 	else if(userMailbox.getNameFields().equalsIgnoreCase("1letterfirstname1letterlastname")){
				 		 if(!isNullOrEmpty(candidate.getLastName())){
					 			candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0))).append(" ").append(String.valueOf(candidate.getLastName().charAt(0)));
					 		 }else{
					 			candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0)));
					 		 }
				 		
				 	 }
					 
					 if(!isNullOrEmpty(candidate.getExperienceYears())){
						 experienceInYears = candidate.getExperienceYears();
					 }
					 
					 if(!isNullOrEmpty(candidate.getExperienceMonths())){
						 experienceInMonths = candidate.getExperienceMonths();
					 }else{
						 experienceInMonths = "0";
					 }
					 
					 if(isNullOrEmpty(candidate.getExperienceMonths()) && isNullOrEmpty(candidate.getExperienceMonths())){
						 totalExp = expBuilder.append("-----");
					 }else{
						 totalExp = expBuilder.append(experienceInYears).append(" Years ").append(experienceInMonths).append(" Months");
					 }
					 
					 if(!isNullOrEmpty(candidate.getExpectedRate())){
						 rate = rateBuilder.append(candidate.getExpectedRate()).append(" /").append(candidate.getRateType());
					 }else{
						 rate = rateBuilder.append("-----");
					 }
					 candidate.setCandidateName(candidateName.toString());
					 candidate.setTotalExp(totalExp.toString());
					 candidate.setRate(rate.toString());
					 candidate.setSkills(candidateSkills);
					 
					 reqCandidatesList.add(candidate);
				}
			}
			
			
			
			resultJson = toJSON(reqCandidatesList);
			return resultJson;
		}
	 
	 
	 
	 @RequestMapping(value = "/getMailingUserList", method = RequestMethod.POST)
		public @ResponseBody String getMailingUserList(@RequestParam(value="teamType",required = false) String teamType,HttpServletRequest request) throws Exception {
			
			String resultJson;
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<User> usersList = userService.getUsersByTeamType(orgLocation.getId(),teamType);
			for(User user : usersList){
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocation.getId());
				if(userRole !=null){
					user.setUserType(userRole.getUserType());
				}
			}
			resultJson = commonUtil.toJSON(usersList);
			return resultJson;
		}
	 
	 
	 
	    @PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
		@RequestMapping(value = "/saveOrgSignature", method = RequestMethod.POST)
		public String saveOrgSignature(@RequestParam(value = "json", required = false) String json,HttpServletRequest request) throws Exception {
			
			String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);
			
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			sourceleadSignatureService.saveOrgSignatrue(jsonObj, orgLocation, user);
			
			return "success";
		}
		
	    
		
	        @PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	  		@RequestMapping(value = "/getSignaturePreview", method = RequestMethod.POST)
	  		public String getSignaturePreview(HttpServletRequest request) throws Exception {
	  			
	  			
	  			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	  			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	  			String signature = sourceleadSignatureService.buildSignature(user, orgLocation);
	  			
	  			return signature;
	  		}
	        
	        
	        @PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	  		@RequestMapping(value = "/restoreSignature", method = RequestMethod.POST)
	  		public String restoreSignature(HttpServletRequest request) throws Exception {
	  			
	  			
	  			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	  			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	  			sourceleadSignatureService.restoreSignature(user, orgLocation);
	  			
	  			return "success";
	  		}
	 
	 
		
	        public void calculateDays(List<UserMailBox> mailBoxList) throws ParseException {
	    		for (UserMailBox userMailBox : mailBoxList) {
	    			long diff = commonUtil.currentDate().getTime() - userMailBox.getCreatedDate().getTime();
	    			
	    		    long diffSeconds = diff / 1000 % 60;
	    			long diffMinutes = diff / (60 * 1000) % 60;
	    			long diffHours = diff / (60 * 60 * 1000) % 24;
	    			long diffDays = diff / (24 * 60 * 60 * 1000);
	    			StringBuilder  time = new StringBuilder();
	    			
	    			if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
	    				userMailBox.setCalDays(time.append(diffSeconds).append(" sec ago ").toString());
	    			}else if(diffDays == 0 && diffHours == 0){
	    				userMailBox.setCalDays(time.append(diffMinutes).append(" min ").append(diffSeconds).append(" sec ago ").toString());
	    			}else if(diffDays == 0){
	    				userMailBox.setCalDays(time.append(diffHours).append(" hours ").append(diffMinutes).append(" min ago ").toString());
	    			}else if(diffDays < 7){
	    				userMailBox.setCalDays(time.append(diffDays).append(" day(s) ").toString());
	    			}else{
	    				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
	    				String commonDateFormat = standardFormat.format(userMailBox.getCreatedDate());
	    				userMailBox.setCalDays(commonDateFormat.toString());
	    			} 

	    		}

	    	}
	        
        
        @PreAuthorize("hasRole('ROLE_MAIL_BOX')")    
        @RequestMapping(value = "/getUserMailBoxDetailsByUUID")
    	public String getUserMailBoxDetailsByUUID(@RequestParam(value = "UUID", required = false) String UUID,
    			HttpServletRequest request) throws Exception {

    		String resultJson = null;
    		UserMailBox mailBox = userMailBoxService.getUserMailBoxByUUID(UUID);
    	
    		resultJson = toJSON(mailBox);
    		return resultJson;

    	}
        
        
        
        @RequestMapping(value = "/getComposeMailTeamsList", method = RequestMethod.POST)
    	public @ResponseBody String getComposeMailTeamsList(@RequestParam(value = "reqId", required = false) Long reqId, HttpServletRequest request) throws Exception {
    		String resultJson;
    		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    		CombinedList combinedList = new CombinedList();
    		
    		List<RequirementAssignTo> requirementAssignTo = null;
    		if(!Objects.isNull(reqId)){
    			requirementAssignTo = requirementAssignToService.getAssignedToMembersByRequirementIdAndOrgLocationId(reqId, orgLocation.getId());
    		}

    		
    			List<Team> teamsList = teamService.getTeamListByOrgLocationID(orgLocation.getId());
    			if (null != teamsList) {
    				for(Team team : teamsList){
    					
    					if(null != requirementAssignTo){
    						if (requirementAssignTo.stream().anyMatch(t -> t.getTeamId() != null && t.getTeamId().getId().equals(team.getId()))) {
    							team.setIsTeamAssigned("Y");
    						}
    					}
    				}
    				combinedList.setTeams(teamsList);
    			}

    		List<Roles> rolesList = roleService.getTabAccessRolesListByOrgLocationIdAndTabId(orgLocation.getId(),
    				Long.valueOf(Constants.TAB_REQUIREMENT));
    		if (null != rolesList) {
    			for(Roles role : rolesList){
    				if(null != requirementAssignTo){
    					if (requirementAssignTo.stream().anyMatch(t -> t.getTeamId() == null && t.getRoleId() != null && t.getRoleId().getId().equals(role.getId()))) {
    						role.setIsRoleAssigned("Y");
    					}
    				}
    			}
    			combinedList.setRoles(rolesList);
    		}

    		resultJson = commonUtil.toJSON(combinedList);
    		return resultJson;
    	}


	 
	 public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
}
