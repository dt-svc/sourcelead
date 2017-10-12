package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.HotListEmailCampaignDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
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
import com.bis.sourcelead.util.FileUploadUtil;
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
	public void sendHotListEmailCampaign(HotListEmailCampaignDTO hotListEmailCampaignDTO, User user, OrgLocations orgLocation,MultipartFile multiFile) throws Exception {
		
		
	 EmailMessage emailMessage = new EmailMessage(); 
	 String fullName = null;
	 String[] hotlistedCandidates = null;
	 String signature = "";
	 String candidates = null;
	 String emails = null;
	 StringBuilder toRecipantsEmailsList = new StringBuilder();
	 StringBuilder otherRecipantsEmailsList = new StringBuilder();
	 
	 String otherEmails = "";
	 InternetAddress[] iAdressArray = null;
	   	 if(!isNullOrEmpty(user.getLastName())){
	   		 fullName = user.getFirstName()+" "+user.getLastName();
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
		    		 if(validateEmail(recipantsEmails[i])){
		    			 toRecipantsEmailsList = toRecipantsEmailsList.append(recipantsEmails[i]);
		    			 toRecipantsEmailsList.append(",");
		    		 }
		    	 }
		    	 
		    	 emails = toRecipantsEmailsList.toString();
		    	 emails = emails.substring(0,emails.length()-1);
		}
		
		
		if(StringUtils.isNotBlank(hotListEmailCampaignDTO.getOtherEmails())){
			 String jsonArray = hotListEmailCampaignDTO.getOtherEmails();
			   otherEmails = jsonArray.toString();
			 if(null != otherEmails){
				 otherEmails = otherEmails.replaceAll("\\[", "").replaceAll("\\]","");
				 otherEmails = otherEmails.replaceAll("\"", "");
			 }
			 
			 String[] recipantsEmails = otherEmails.split(",");
	    	 for(int i=0; i < recipantsEmails.length; i++){
	    		 if(validateEmail(recipantsEmails[i])){
	    			 otherRecipantsEmailsList = otherRecipantsEmailsList.append(recipantsEmails[i]);
	    			 otherRecipantsEmailsList.append(",");
	    		 }
	    	 }
	    	 
	    	 otherEmails = otherRecipantsEmailsList.toString();
	    	 otherEmails = otherEmails.substring(0,otherEmails.length()-1);
		}
		
		StringBuilder tomaillBuilder = new StringBuilder();
		
		if(null != emails && null != otherEmails){
			iAdressArray = InternetAddress.parse(tomaillBuilder.append(emails).append(",").append(otherEmails).toString());
		}else if(null == emails && null != otherEmails){
			iAdressArray = InternetAddress.parse(tomaillBuilder.append(otherEmails).toString());
		}else if(null != emails && null == otherEmails){
			iAdressArray = InternetAddress.parse(tomaillBuilder.append(emails).toString());
		}
		
		
		 emailMessage.setCreatedBy(user.getUserCode());
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setFromName(fullName);
		
		 if(StringUtils.isNotBlank(hotListEmailCampaignDTO.getHotlistcandidates())){
			    
				 String jsonArray = hotListEmailCampaignDTO.getHotlistcandidates();
				   candidates = jsonArray.toString();
				 if(null != candidates){
					 candidates = candidates.replaceAll("\\[", "").replaceAll("\\]","");
					 candidates = candidates.replaceAll("\"", "");
			  	      hotlistedCandidates = candidates.split(",");
				 }
				 
				 
		 }
		 
		String table =  getHotListCandidatesTable(hotlistedCandidates,hotListEmailCampaignDTO.getNamefield());
		String UUID = SourceLeadRandomCodeHelper.generateUUID();
		
		signature = sourceleadSignatureService.buildSignature(user, orgLocation);
		 
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("salutation", hotListEmailCampaignDTO.getSaluation());
		 globalVarsMap.put("body", hotListEmailCampaignDTO.getBodytext());
		 globalVarsMap.put("noOfCandidates", String.valueOf(hotlistedCandidates.length));
		 
		 globalVarsMap.put("hotlistTable", table);
		 globalVarsMap.put("requestResume","<a href='"+sourceleadUrl+"/"+"requestForCandidateResume?UUID="+UUID+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Request For Resume</div></a>");
		 
		 EmailScenario emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.HOT_LIST_CAMPAIGN,Constants.ENTITY_TYPE_EMAIL);
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(hotListEmailCampaignDTO.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 
		
		 
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 emailMessage.setOrgLocationId(orgLocation.getId());
		 emailMessage.setEmailType(Constants.EMAIL_CAMPAIGN);
		 
		 ArrayList<EmailAttachment> attachmentList = null;
		 EmailAttachment emailAttachment = new EmailAttachment();
		 FileInfo fileInfo = null;
		 if(hotListEmailCampaignDTO.getFileInfo() != null) {
			   fileInfo = hotListEmailCampaignDTO.getFileInfo().get(0);
			 if(fileInfo != null){
				 attachmentList =  new ArrayList<EmailAttachment>();
				 emailAttachment.setName(fileInfo.getFileName());
				 emailAttachment.setFileUrl(fileInfo.getFileURL());
				 emailAttachment.setType(fileInfo.getContentType());
				 attachmentList.add(emailAttachment);
			     FileUploadUtil.moveFile(hotListEmailCampaignDTO.getStaticPath(), fileInfo, "Hotlist Campaign Documents");
			    }
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
		 
		 saveUserMailBox(user,orgLocation,iAdressArray,candidates,subject,table,UUID,hotListEmailCampaignDTO.getBodytext(),signature,hotListEmailCampaignDTO.getNamefield(),fileInfo);
	}
	
	
private void saveUserMailBox(User user,OrgLocations orgLocation,InternetAddress[] iAdressArray,String candidates,String subject,String table,String UUID,String body,String signature,String nameFields,FileInfo fileInfo) throws Exception{
	
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
	
	
	 if(fileInfo != null){
   	  try {
   		userMailBox.setFileContentType(fileInfo.getContentType());
   		userMailBox.setFileUrl(fileInfo.getFileURL());
   		userMailBox.setFileName(fileInfo.getFileName());
   	   
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

	private boolean validateEmail(String email) {
			Pattern pattern = Pattern.compile(
					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		}

}
