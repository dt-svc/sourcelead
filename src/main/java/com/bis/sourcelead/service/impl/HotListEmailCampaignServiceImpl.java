package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.entity.UserSignature;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateSkillsService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.HotListEmailCampaignService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserSignatureService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class HotListEmailCampaignServiceImpl implements HotListEmailCampaignService{
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
    CandidateSkillsService candidateSkillsService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	UserSignatureService userSignatureService;
	
	@Autowired
	UserMailBoxService userMailBoxService;
	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public void sendHotListEmailCampaign(JSONObject jsonObj, User user, OrgLocations orgLocation,
			MultipartFile multiFile) throws Exception {
		
		
	 EmailMessage emailMessage = new EmailMessage(); 
	 String fullName = null;
	 String[] hotlistedCandidates = null;
	 UserSignature userSignature = null;
	 String signature = "";
	 String candidates = null;
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
		
		
		if(jsonObj.has("otherEmails") && !isNullOrEmpty(jsonObj.getString("otherEmails"))){
			String othmails = jsonObj.getString("otherEmails").replaceAll("\\[", "").replaceAll("\\]","");
			othmails = othmails.replaceAll("\"", "");
			String[] otherEmails = othmails.split(",");
			for(int i=0 ; i< otherEmails.length ; i++){
				 EmailRecipient emailRecipient = new EmailRecipient();
				 emailRecipient.setEmail(otherEmails[i]);
				 emailRecipientList.add(emailRecipient);
			}
		}
		
		 emailMessage.setCreatedBy(user.getUserCode());
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setFromName(fullName);
		
		 if(jsonObj.has("hotlistcandidates") && !isNullOrEmpty(jsonObj.getString("hotlistcandidates"))){
			 
			    candidates = jsonObj.getString("hotlistcandidates").replaceAll("\\[", "").replaceAll("\\]","");
			    candidates = candidates.replaceAll("\"", "");
				
				hotlistedCandidates = candidates.split(",");
		 }
		 
		String table =  getHotListCandidatesTable(hotlistedCandidates,jsonObj.getString("namefield"));
		String UUID = SourceLeadRandomCodeHelper.generateUUID();
		 
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("salutation", jsonObj.getString("saluation"));
		 globalVarsMap.put("body", jsonObj.getString("bodytext"));
		 globalVarsMap.put("noOfCandidates", String.valueOf(hotlistedCandidates.length));
		 
		 globalVarsMap.put("hotlistTable", table);
		 globalVarsMap.put("requestResume","<a href='"+sourceleadUrl+"/"+"requestForCandidateResume?UUID="+UUID+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Request For Resume</div></a>");
		 globalVarsMap.put("signature", String.valueOf(hotlistedCandidates.length));
		 
		 EmailScenario emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.HOT_LIST_CAMPAIGN,Constants.ENTITY_TYPE_EMAIL);
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
		 
		 ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		 EmailAttachment emailAttachment = new EmailAttachment();
		 emailAttachment.setName(multiFile.getOriginalFilename());
		 emailAttachment.setDocFile(multiFile.getBytes());
		 emailAttachment.setType(multiFile.getContentType());
		 attachmentList.add(emailAttachment);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),emailRecipientList,attachmentList);
		 
		 saveUserMailBox(user,orgLocation,emailRecipientList,candidates,subject,table,UUID,jsonObj.getString("bodytext"),userSignature,jsonObj.getString("namefield"),multiFile);
		
	}
	
	
private void saveUserMailBox(User user,OrgLocations orgLocation,ArrayList<EmailRecipient> emailRecipientList,String candidates,String subject,String table,String UUID,String body,UserSignature userSignature,String nameFields,MultipartFile multiFile) throws Exception{
	
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
	userMailBox.setCandidateId(candidates);
	userMailBox.setSubject(subject);
	userMailBox.setCampaignTableData(table);
	userMailBox.setBody(body);
	userMailBox.setSignatureId(userSignature);
	userMailBox.setIsFavourite(0);
	userMailBox.setUuid(UUID);
	userMailBox.setStatus(1);
	userMailBox.setNameFields(nameFields);
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
	
private String getHotListCandidatesTable(String[] hotlistedCandidates,String nameField) throws Exception{
		
		
		 String tempHTML = null;
		 
		 tempHTML = "<table style='display:table-cell;vertical-align:middle'>";
		 tempHTML = tempHTML+"<tr align='center'>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Name<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='15%' bgcolor='#5bace8'><span>Experience<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='15%' bgcolor='#5bace8'><span>Rate<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='50%' bgcolor='#5bace8'><span>Skills<span></th>";
		 tempHTML = tempHTML+"</tr>";
		   
		 
		 for(int i=0 ; i < hotlistedCandidates.length; i++){
			 StringBuilder skills = new StringBuilder();
			 List<CandidateSkills> skillsList = candidateSkillsService.getCandidateSkillsByCandidateId(hotlistedCandidates[i]);
			 if(skillsList != null){
				 for(CandidateSkills skill : skillsList){
					 skills.append(skill.getName()).append(", ");
				 }
			 }
			 
			 skills.toString();
			 String candidateSkills =  skills.substring(0,skills.length()-2);
			 
			 String candidateName = null;
			 String experienceInYears = null;
			 String experienceInMonths = null;
			 String totalExp = null;
			 String rate = null;
			
			 Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(hotlistedCandidates[i]));
			 
			 if(nameField.equalsIgnoreCase("firstname")){
				 candidateName = candidate.getFirstName();
			 }
		 	 else if(nameField.equalsIgnoreCase("firstnameandlastName")){
				 if(!isNullOrEmpty(candidate.getLastName())){
					 candidateName = candidate.getFirstName()+" "+candidate.getLastName();
			   	 }else{
			   		candidateName = candidate.getFirstName();
			   	 }
			 }
		 	 else if(nameField.equalsIgnoreCase("firstname1stletterlastname")){
		 		 if(!isNullOrEmpty(candidate.getLastName())){
		 			candidateName = candidate.getFirstName()+" "+String.valueOf(candidate.getLastName().charAt(0));
		 		 }else{
		 			candidateName = candidate.getFirstName();
		 		 }
		 		
		 	 }
		 	 else if(nameField.equalsIgnoreCase("1letterfirstnamelastname")){
		 		 if(!isNullOrEmpty(candidate.getLastName())){
		 			candidateName = String.valueOf(candidate.getFirstName().charAt(0))+" "+candidate.getLastName();
		 		 }else{
		 			candidateName = String.valueOf(candidate.getFirstName().charAt(0));
		 		 }
		 		
		 	 }
		 	else if(nameField.equalsIgnoreCase("1letterfirstname1letterlastname")){
		 		 if(!isNullOrEmpty(candidate.getLastName())){
		 			candidateName = String.valueOf(candidate.getFirstName().charAt(0))+" "+String.valueOf(candidate.getLastName().charAt(0));
		 		 }else{
		 			candidateName = String.valueOf(candidate.getFirstName().charAt(0));
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
				 totalExp = "-----";
			 }else{
				 totalExp = experienceInYears+" Years "+experienceInMonths+" Months";
			 }
			 
			 if(!isNullOrEmpty(candidate.getExpectedRate())){
				 rate = candidate.getExpectedRate()+" /"+candidate.getRateType();
			 }else{
				 rate = "-----";
			 }
			 
			 tempHTML = tempHTML+"<tr align='center'>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+candidateName+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+totalExp+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+rate+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+candidateSkills+"</td>";
			 
			 tempHTML = tempHTML+"</tr>";
			 
		 }
		 tempHTML = tempHTML+"</table>";
		 
		 return tempHTML;
	}

}
