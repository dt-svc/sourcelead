package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.HotListEmailCampaignService;
import com.bis.sourcelead.service.SourceleadSignatureService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserSignatureService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class HotListEmailCampaignServiceImpl implements HotListEmailCampaignService{
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	/*@Autowired
    CandidateSkillsService candidateSkillsService;*/
	
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
	
	@Autowired
	SourceleadSignatureService sourceleadSignatureService;
	
	@Autowired
	UserRoleService userRoleService;
	
	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public void sendHotListEmailCampaign(JSONObject jsonObj, User user, OrgLocations orgLocation,
			MultipartFile multiFile) throws Exception {
		
		
	 EmailMessage emailMessage = new EmailMessage(); 
	 String fullName = null;
	 String[] hotlistedCandidates = null;
	 String signature = "";
	 String candidates = null;
	 String emails = null;
	 
	 String otherEmails = "";
	 InternetAddress[] iAdressArray = null;
	   	 if(!isNullOrEmpty(user.getLastName())){
	   		 fullName = user.getFirstName()+" "+user.getLastName();
	   	 }else{
	   		 fullName = user.getFirstName();
	   	 }
			
		if(jsonObj.has("Toemails") && !isNullOrEmpty(jsonObj.getString("Toemails"))){
			
	    	  try{
	    		   JSONArray jsonArray = jsonObj.getJSONArray("Toemails");
	    		   emails = jsonArray.toString();
	    	  }
	    	  catch(JSONException e){
	    		  emails= jsonObj.getString("Toemails");
	    	  }
	    	  if(null != emails){
	    		  emails = emails.replaceAll("\\[", "").replaceAll("\\]","");
	    		  emails = emails.replaceAll("\"", "");
	    	  }
	    	 
		}
		
		
		if(jsonObj.has("otherEmails") && !isNullOrEmpty(jsonObj.getString("otherEmails"))){
			 try{
	    		   JSONArray jsonArray = jsonObj.getJSONArray("otherEmails");
	    		   otherEmails = jsonArray.toString();
	    	  }
	    	  catch(JSONException e){
	    		  otherEmails= jsonObj.getString("otherEmails");
	    	  }
			 if(null != otherEmails){
				 otherEmails = otherEmails.replaceAll("\\[", "").replaceAll("\\]","");
				 otherEmails = otherEmails.replaceAll("\"", "");
			 }
		}
		StringBuilder tomaillBuilder = new StringBuilder();
		iAdressArray = InternetAddress.parse(tomaillBuilder.append(emails).append(",").append(otherEmails).toString());
		
		 emailMessage.setCreatedBy(user.getUserCode());
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setFromName(fullName);
		
		 if(jsonObj.has("hotlistcandidates") && !isNullOrEmpty(jsonObj.getString("hotlistcandidates"))){
			    
				 try{
		    		   JSONArray jsonArray = jsonObj.getJSONArray("hotlistcandidates");
		    		   candidates = jsonArray.toString();
		    	  }
		    	  catch(JSONException e){
		    		  candidates= jsonObj.getString("hotlistcandidates");
		    	  }
				 if(null != candidates){
					 candidates = candidates.replaceAll("\\[", "").replaceAll("\\]","");
					 candidates = candidates.replaceAll("\"", "");
			  	      hotlistedCandidates = candidates.split(",");
				 }
				 
				 
		 }
		 
		String table =  getHotListCandidatesTable(hotlistedCandidates,jsonObj.getString("namefield"));
		String UUID = SourceLeadRandomCodeHelper.generateUUID();
		
		signature = sourceleadSignatureService.buildSignature(user, orgLocation);
		 
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("salutation", jsonObj.getString("saluation"));
		 globalVarsMap.put("body", jsonObj.getString("bodytext"));
		 globalVarsMap.put("noOfCandidates", String.valueOf(hotlistedCandidates.length));
		 
		 globalVarsMap.put("hotlistTable", table);
		 globalVarsMap.put("requestResume","<a href='"+sourceleadUrl+"/"+"requestForCandidateResume?UUID="+UUID+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Request For Resume</div></a>");
		 
		 EmailScenario emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.HOT_LIST_CAMPAIGN,Constants.ENTITY_TYPE_EMAIL);
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(jsonObj.getString("subject"),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 
		
		 
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 emailMessage.setOrgLocationId(orgLocation.getId());
		 emailMessage.setEmailType(Constants.EMAIL_CAMPAIGN);
		 
		 ArrayList<EmailAttachment> attachmentList = null;
		 EmailAttachment emailAttachment = new EmailAttachment();
		 
		 
		 if(multiFile.getSize() > 0){
			 attachmentList =  new ArrayList<EmailAttachment>();
			 emailAttachment.setName(multiFile.getOriginalFilename());
			 emailAttachment.setDocFile(multiFile.getBytes());
			 emailAttachment.setType(multiFile.getContentType());
			 attachmentList.add(emailAttachment);
		 }
		
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,attachmentList);
		 
		 saveUserMailBox(user,orgLocation,iAdressArray,candidates,subject,table,UUID,jsonObj.getString("bodytext"),signature,jsonObj.getString("namefield"),multiFile);
		
	}
	
	
private void saveUserMailBox(User user,OrgLocations orgLocation,InternetAddress[] iAdressArray,String candidates,String subject,String table,String UUID,String body,String signature,String nameFields,MultipartFile multiFile) throws Exception{
	
	UserMailBox userMailBox = new UserMailBox();
	
	if(null != iAdressArray && iAdressArray.length >0){
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
	userMailBox.setCandidateId(candidates);
	userMailBox.setSubject(subject);
	userMailBox.setCampaignTableData(table);
	userMailBox.setBody(body);
	userMailBox.setSignature(signature);
	userMailBox.setIsMailRead(0);
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
	
	userMailBoxService.saveOrUpdateUserMailBoxELK(userMailBox);
	
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
		 
		 Set<String> candidateEmailsList = new HashSet<String>();
		 
		 for(int i=0 ; i < hotlistedCandidates.length; i++){
			 candidateEmailsList.add(hotlistedCandidates[i]);
		 }
		 
		 for(String email : candidateEmailsList){
			/* StringBuilder skills = new StringBuilder();
			 StringBuilder candidateSkills = new StringBuilder();
			 List<CandidateSkills> skillsList = candidateSkillsService.getCandidateSkillsByCandidateId(email);
			 if(skillsList.size() > 0){
				 for(CandidateSkills skill : skillsList){
					 skills.append(skill.getName()).append(", ");
				 }
				 candidateSkills.append(skills);
			 }else{
				 candidateSkills.append("-----");
			 }*/
			 
			 
			 
			 StringBuilder candidateName = new StringBuilder();
			 String experienceInYears = null;
			 String experienceInMonths = null;
			 StringBuilder totalExp = new StringBuilder();
			 StringBuilder rate = new StringBuilder();
			 
			 StringBuilder nameBuilder = new StringBuilder();
			 StringBuilder expBuilder = new StringBuilder();
			 StringBuilder rateBuilder = new StringBuilder();
			
			 Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(email));
			 
			 if(nameField.equalsIgnoreCase("firstname")){
				 candidateName = nameBuilder.append(candidate.getFirstName());
			 }
		 	 else if(nameField.equalsIgnoreCase("firstnameandlastName")){
				 if(!isNullOrEmpty(candidate.getLastName())){
					 candidateName = nameBuilder.append(candidate.getFirstName()).append(" ").append(candidate.getLastName());
			   	 }else{
			   		candidateName = nameBuilder.append(candidate.getFirstName());
			   	 }
			 }
		 	 else if(nameField.equalsIgnoreCase("firstname1stletterlastname")){
		 		 if(!isNullOrEmpty(candidate.getLastName())){
		 			candidateName = nameBuilder.append(candidate.getFirstName()).append(" ").append(String.valueOf(candidate.getLastName().charAt(0)));
		 		 }else{
		 			candidateName = nameBuilder.append(candidate.getFirstName());
		 		 }
		 		
		 	 }
		 	 else if(nameField.equalsIgnoreCase("1letterfirstnamelastname")){
		 		 if(!isNullOrEmpty(candidate.getLastName())){
		 			candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0))).append(" ").append(candidate.getLastName());
		 		 }else{
		 			candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0)));
		 		 }
		 		
		 	 }
		 	else if(nameField.equalsIgnoreCase("1letterfirstname1letterlastname")){
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
			 String skills;
			 if(null != candidate.getSkillsName()){
				 skills = candidate.getSkillsName();
			 }else{
				 skills = "-----";
			 }
			 tempHTML = tempHTML+"<tr align='center'>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+candidateName.toString()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+totalExp.toString()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+rate.toString()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+skills+"</td>";
			 
			 tempHTML = tempHTML+"</tr>";
			 
		 }
		 tempHTML = tempHTML+"</table>";
		 
		 return tempHTML;
	}

}
