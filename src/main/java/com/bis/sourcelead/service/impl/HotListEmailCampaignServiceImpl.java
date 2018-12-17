package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CampaignListDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.HotListEmailCampaignDTO;
import com.bis.sourcelead.dto.JobListEmailCampaignDTO;
import com.bis.sourcelead.entity.Campaign;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.CampaignRecipient;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.rest.MailBoxRestController;
import com.bis.sourcelead.service.CampaignService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.HotListEmailCampaignService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SourceleadSignatureService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserSignatureService;
import com.bis.sourcelead.util.CampaignUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class HotListEmailCampaignServiceImpl implements HotListEmailCampaignService{
	
	private static final Logger logger = LoggerFactory.getLogger(MailBoxRestController.class);
	
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
	
	@Autowired
	RequirementService requirementService;
	
	
	@Autowired
	private CampaignUtil campaignUtil;
	
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private RequirementLocationsService requirementLocationsService;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public void sendHotListEmailCampaign(HotListEmailCampaignDTO hotListEmailCampaignDTO, User user, OrgLocations orgLocation,MultipartFile multiFile) throws Exception {
		
		// Email Start
//	 EmailMessage emailMessage = new EmailMessage(); 
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
			
			String[] arr = hotListEmailCampaignDTO.getToemails().split(",");
			
			for(String listId : arr) {
				CampaignList campaignList = campaignService.getRecipientListByListId(listId,0);
				if(campaignList.getCampaignRecipient() !=null && campaignList.getCampaignRecipient().size() >0) {
					String reciEmail = getToReciEmails(campaignList.getCampaignRecipient());
					toRecipantsEmailsList.append(reciEmail).append(",");
				}
			}			
			
//	    	  String jsonArray = hotListEmailCampaignDTO.getToemails();
//			   emails = jsonArray.toString();
//	    	  if(null != emails){
//	    		  emails = emails.replaceAll("\\[", "").replaceAll("\\]","");
//	    		  emails = emails.replaceAll("\"", "");
//	    	  }
//	    	 
//	    	  String[] recipantsEmails = emails.split(",");
//		    	 for(int i=0; i < recipantsEmails.length; i++){
//		    		 if(validateEmail(recipantsEmails[i])){
//		    			 toRecipantsEmailsList = toRecipantsEmailsList.append(recipantsEmails[i]);
//		    			 toRecipantsEmailsList.append(",");
//		    		 }
//		    	 }
		    	 

		    	 emails = toRecipantsEmailsList.toString();
		    	 emails = emails.substring(0,emails.length()-1);

		}
		
		String otherRecilistId = null;
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
	    	 List<String> othEmail = new ArrayList<String>(Arrays.asList(otherEmails.split(",")));
	    	 List<String> otherRecipientList = campaignUtil.createOtherRecipients(othEmail);
	    	 if(otherRecipientList !=null && otherRecipientList.size() >0) {
	    		 CampaignListDTO campaignListDTO = new CampaignListDTO();
	    		 String otherReciListName = SourceLeadRandomCodeHelper.generateRandomVerificationCode();
	    		 campaignListDTO.setCpnListName(otherReciListName);
	    		 logger.info("Other Recipient List Name : "+otherReciListName);
	    		 String listResponse = campaignUtil.createList(campaignListDTO);
	    		 JSONObject jObject  = new JSONObject(listResponse); 
	    		 otherRecilistId = jObject.getString("id");
	    		 String res =  campaignUtil.addOtherRecipientsToList(otherRecipientList,otherRecilistId);
	    		if(res !=null) {
	    			logger.info("Other Recipient Added to list");
				}	
	    	 }
		}
		
		StringBuilder tomaillBuilder = new StringBuilder();
		
		if(null != emails && null != otherEmails){
			iAdressArray = InternetAddress.parse(tomaillBuilder.append(emails).append(",").append(otherEmails).toString());
		}else if(null == emails && null != otherEmails){
			iAdressArray = InternetAddress.parse(tomaillBuilder.append(otherEmails).toString());
		}else if(null != emails && null == otherEmails){
			iAdressArray = InternetAddress.parse(tomaillBuilder.append(emails).toString());
		}
		
		
//		 emailMessage.setCreatedBy(user.getUserCode());
//		 emailMessage.setFromEmail(user.getEmail());
//		 emailMessage.setFromName(fullName);
		
		 if(StringUtils.isNotBlank(hotListEmailCampaignDTO.getHotlistcandidates())){
			    
				 String jsonArray = hotListEmailCampaignDTO.getHotlistcandidates();
				   candidates = jsonArray.toString();
				 if(null != candidates){
					 candidates = candidates.replaceAll("\\[", "").replaceAll("\\]","");
					 candidates = candidates.replaceAll("\"", "");
			  	      hotlistedCandidates = candidates.split(",");
				 }
				 
				 
		 }
		 
		String UUID = SourceLeadRandomCodeHelper.generateUUID();
		signature = sourceleadSignatureService.buildSignature(user, orgLocation);
		String str =  getHotListCandidatesTable(hotlistedCandidates,hotListEmailCampaignDTO,signature);
//		String table = str.replace("\"","\'");
		String  table = str.replaceAll("\\s+"," ");
		JobListEmailCampaignDTO jobListEmailCampaignDTO = new JobListEmailCampaignDTO();
		 jobListEmailCampaignDTO.setSenderIdentity(hotListEmailCampaignDTO.getSenderIdentity());
		 jobListEmailCampaignDTO.setSubject(hotListEmailCampaignDTO.getSubject());
		 jobListEmailCampaignDTO.setToemails(hotListEmailCampaignDTO.getToemails());
		 jobListEmailCampaignDTO.setBodytext(hotListEmailCampaignDTO.getBodytext());
		 
		 String createCampaignRes = campaignUtil.createCampaign(jobListEmailCampaignDTO, table,otherRecilistId);
		
		 if(createCampaignRes !=null) {
			 JSONObject jObject = new JSONObject(createCampaignRes);
				String campaignId = jObject.getString("id");
				 String scheduleCampaignRes = campaignUtil.sendCampaign(campaignId);
				if(scheduleCampaignRes !=null) {
				 Campaign campaign= saveScheduledCampaignHistory(createCampaignRes,scheduleCampaignRes,orgLocation,user,0);
				 if(campaign !=null) {
					 logger.info("Campaign saved into campaig history table");
				 }
				 
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
//				 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
//				 emailMessage.setSubject(subject);
//				 emailMessage.setMessageBody(emailTemplate);
//				 emailMessage.setOrgLocationId(orgLocation.getId());
//				 emailMessage.setEmailType(Constants.EMAIL_CAMPAIGN);
				 
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
				 
//				 if(multiFile.getSize() > 0){
//					 fileAttachmentList =  new ArrayList<EmailAttachment>();
//					 attachment.setName(multiFile.getOriginalFilename());
//					 attachment.setDocFile(multiFile.getBytes());
//					 attachment.setType(multiFile.getContentType());
//					 fileAttachmentList.add(attachment);
//				 }
				 
						
				//calling the sendEmail method of emailScenerioUtil
//				 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,fileAttachmentList);
				 
				 //Email End
				 
				 UserMailBox userMailBox =  saveUserMailBox(user,orgLocation,iAdressArray,subject,table,UUID,hotListEmailCampaignDTO.getBodytext(),signature,fileInfo);
				 userMailBox.setCandidateId(candidates);
				 userMailBox.setNameFields(hotListEmailCampaignDTO.getNamefield());
				 userMailBoxService.saveUserMailBox(userMailBox);
				 userMailBoxService.saveOrUpdateUserMailBoxELK(userMailBox);
				 
				}
		 }
		 
		
	}
	
	
private UserMailBox saveUserMailBox(User user,OrgLocations orgLocation,InternetAddress[] iAdressArray,String subject,String table,String UUID,String body,String signature,FileInfo fileInfo) throws Exception{
	
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
	userMailBox.setSubject(subject);
	userMailBox.setCampaignTableData(table);
	userMailBox.setBody(body);
	userMailBox.setSignature(signature);
	userMailBox.setIsMailRead(0);
	userMailBox.setIsFavourite(0);
	userMailBox.setUuid(UUID);
	userMailBox.setStatus(1);
	userMailBox.setMailType(2);
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
	
	return userMailBox;
	
}


	private String getHotListCandidatesTable(String[] hotlistedCandidates,HotListEmailCampaignDTO hotListEmailCampaignDTO, String signature) throws Exception{
		
		String tempHTML = "";
		String bodyText = "";
		String bText = hotListEmailCampaignDTO.getBodytext();
		if(StringUtils.isNotBlank(bText)) {
			bodyText = bText.replace("\"","\'");
		}
		
		String imageUrl = sourceleadUrl+"/resources/images/logo-sourceld.png";
		
		tempHTML = tempHTML + "<table width='650px' cellpadding='0' cellspacing='0' style='background-color:#fff; font-family:verdana, arial;  margin:0px;'>";
		tempHTML = tempHTML + " <tr>";
		tempHTML = tempHTML + " <td><table width='650' align='center' cellpadding='0' cellspacing='0' style='padding:5px 0px; background-color:#fff; border:1px solid #E0E0E0;  margin:0px;'>";
		tempHTML = tempHTML + "   <tr>";
		tempHTML = tempHTML + "  <td><table width='600' align='center' style='margin:0px;'>";
		tempHTML = tempHTML + "     <tr>";
		tempHTML = tempHTML + "      <td><span style='width: 200px; height: 45px; margin: 0 auto; display: block;'> <img src="+imageUrl+" width='200' height='45'> </span></td>";
		tempHTML = tempHTML + "     </tr>";
		tempHTML = tempHTML + "   </table></td>";
		tempHTML = tempHTML + " </tr>";
		tempHTML = tempHTML + " </table>";
		tempHTML = tempHTML + "  <table width='650px'style='border-spacing:0; font-family:sans-serif; font-size: 14px; padding:20px 0; line-height: 22px;'>";
		tempHTML = tempHTML + "    <tbody>";
		tempHTML = tempHTML + "     <tr>";
		tempHTML = tempHTML + "   <td align='left'>"+hotListEmailCampaignDTO.getSaluation()+", <br>";
		tempHTML = tempHTML + "    <br></td>";
		tempHTML = tempHTML + "   </tr>";
		tempHTML = tempHTML + "  <tr>";
		tempHTML = tempHTML + "<td align='left'><p>"+bodyText+"</p></td>";
		tempHTML = tempHTML + "    </tr>";
		tempHTML = tempHTML + "   </tbody>";
		tempHTML = tempHTML + "  </table>";
		      // table start
		 tempHTML = tempHTML + "<table style='display:table-cell;vertical-align:middle; width:650px; font-size: 12px;  margin:0px;'>";
		 tempHTML = tempHTML + "<tr align='center'>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Name<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='15%' bgcolor='#5bace8'><span>Experience<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='15%' bgcolor='#5bace8'><span>Rate<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='50%' bgcolor='#5bace8'><span>Skills<span></th>";
		 tempHTML = tempHTML + " </tr>";
		 tempHTML = tempHTML + " <tbody>";
		 tempHTML = tempHTML + " <tr align='center'>";
		 Set<String> candidateEmailsList = new HashSet<String>();
		 
		 for(int i=0 ; i < hotlistedCandidates.length; i++){
			 candidateEmailsList.add(hotlistedCandidates[i]);
		 }
		 
		 for(String email : candidateEmailsList){
			 
			 StringBuilder candidateName = new StringBuilder();
			 Integer experienceInYears = 0;
			 Integer experienceInMonths = 0;
			 StringBuilder totalExp = new StringBuilder();
			 StringBuilder rate = new StringBuilder();
			 
			 StringBuilder nameBuilder = new StringBuilder();
			 StringBuilder expBuilder = new StringBuilder();
			 StringBuilder rateBuilder = new StringBuilder();
			
			 Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(email));
			 
			 if(hotListEmailCampaignDTO.getNamefield().equalsIgnoreCase("firstname")){
				 candidateName = nameBuilder.append(candidate.getFirstName());
			 }
		 	 else if(hotListEmailCampaignDTO.getNamefield().equalsIgnoreCase("firstnameandlastName")){
				 if(!isNullOrEmpty(candidate.getLastName())){
					 candidateName = nameBuilder.append(candidate.getFirstName()).append(" ").append(candidate.getLastName());
			   	 }else{
			   		candidateName = nameBuilder.append(candidate.getFirstName());
			   	 }
			 }
		 	 else if(hotListEmailCampaignDTO.getNamefield().equalsIgnoreCase("firstname1stletterlastname")){
		 		 if(!isNullOrEmpty(candidate.getLastName())){
		 			candidateName = nameBuilder.append(candidate.getFirstName()).append(" ").append(String.valueOf(candidate.getLastName().charAt(0)));
		 		 }else{
		 			candidateName = nameBuilder.append(candidate.getFirstName());
		 		 }
		 		
		 	 }
		 	 else if(hotListEmailCampaignDTO.getNamefield().equalsIgnoreCase("1letterfirstnamelastname")){
		 		 if(!isNullOrEmpty(candidate.getLastName())){
		 			candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0))).append(" ").append(candidate.getLastName());
		 		 }else{
		 			candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0)));
		 		 }
		 		
		 	 }
		 	else if(hotListEmailCampaignDTO.getNamefield().equalsIgnoreCase("1letterfirstname1letterlastname")){
		 		 if(!isNullOrEmpty(candidate.getLastName())){
		 			candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0))).append(" ").append(String.valueOf(candidate.getLastName().charAt(0)));
		 		 }else{
		 			candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0)));
		 		 }
		 		
		 	 }
			 
			 if(!isNullOrEmpty(candidate.getExperienceYears())){
				 experienceInYears = Integer.parseInt(candidate.getExperienceYears());
			 }
			 
			 if(!isNullOrEmpty(candidate.getExperienceMonths())){
				 experienceInMonths = Integer.parseInt(candidate.getExperienceMonths());
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
		
		            
		            
		tempHTML = tempHTML + "</tr>";
		tempHTML = tempHTML + "</tbody>";
		tempHTML = tempHTML + " </table>";
		      // table end
		tempHTML = tempHTML + signature;
		      tempHTML = tempHTML + " <table width='650' bgcolor='#ededed' align='center' cellpadding='0' cellspacing='0' style='background-color:#ededed; padding:0px; margin-top: 15px;'>";
		      tempHTML = tempHTML + " <tr>";
		      tempHTML = tempHTML + "   <td><table bgcolor='#ededed'  width='650' align='center' style='background-color:#ededed; padding:10px 15px; border:solid #E0E0E0; border-width:0 1px 0px 1px; margin:0px;'>";
		      tempHTML = tempHTML + "     <tr>";
		      tempHTML = tempHTML + "     <td><p style='color:#214b90; text-transform: uppercase; padding:5px 0px; margin:0px 0px; font-size:13px; font-weight:600; text-align:center'>get started!</p>";
		      tempHTML = tempHTML + "       <p style='padding:0px 0px 2px 0px; margin:0px 0px; font-weight:400; font-size:11px; text-align:center'>For more information about SourceLead, go to <a target='blank' href='http://www.sourcelead.com/' style='text-decoration:none; color:#0D47A1; display:inline-block; padding:0; border-radius:0; margin:0; font-size:14px'>sourcelead.com</a></p>";
		      tempHTML = tempHTML + "       <p style='padding:0px 0px 2px 0px; margin:0px 0px; font-weight:400; font-size:11px; text-align:center'>3535 Victory Group Way, Building 4 Frisco, TX 75034, USA, Phone : (214) 295-5535, FAX : (972) 559-3641</p></td>";
		      tempHTML = tempHTML + "    </tr>";
		      tempHTML = tempHTML + "   <tr>";
		      tempHTML = tempHTML + "   <td><p style='text-align:center; padding:2px 0px; margin:0px 0px; font-size:11px; font-weight:400; border-top:1px solid #BDBDBD;'><a target='blank' href='http://www.sourcelead.com/about-us.html' style='text-decoration:none; color:#333;' title='About Us'>About Us</a> | <a target='blank' href='http://www.sourcelead.com/contact.html' style='text-decoration:none; color:#333;' title='Contact Us'>Contact Us</a> | <a target='blank' href='http://www.sourcelead.com/ats.html' style='text-decoration:none; color:#333;' title='ATS'>ATS</a> | <a target='blank' href='http://www.sourcelead.com/vms.html' style='text-decoration:none; color:#333;' title='VMS'>VMS</a> | <a target='blank' href='http://www.sourcelead.com/crms.html' style='text-decoration:none; color:#333;' title='CRMS'>CRMS</a> | <a target='blank' href='http://www.sourcelead.com/pms.html' style='text-decoration:none; color:#333;' title='PMS'>PMS</a> | <a target='blank' href='http://www.sourcelead.com/hrms.html' style='text-decoration:none; color:#333;' title='HRMS'>HRMS</a> | <a target='blank' href='http://www.sourcelead.com/ims.html' style='text-decoration:none; color:#333;' title='IMS'>IMS</a> | <a target='blank' href='http://www.sourcelead.com/search-jobs.html' style='text-decoration:none; color:#333;' title='Job Search'>Job Search</a> | <a target='blank' href='http://www.sourcelead.com/market-place.html' style='text-decoration:none; color:#333;' title='Marketplace'>Marketplace</a> <br> <a target='blank' href='http://www.sourcelead.com/terms-of-use.html' style='text-decoration:none; color:#333;' title='Terms of Use'>Terms of Use</a> | <a target='blank' href='http://www.sourcelead.com/privacy-policy.html' style='text-decoration:none; color:#333;' title='Privacy policy'>Privacy policy</a></p></td>";
		      tempHTML = tempHTML + "   </tr>";
		      tempHTML = tempHTML + "   <tr><td><p style='padding:5px 0px 5px 0px; margin:0px 0px; font-weight:400; font-size:11px; text-align:center;'>If you do not wish to receive any further communication, please  <a target='_blank' href='"+sourceleadUrl+"/ws/unsubscribe' style='color:#a21e23;'>unsubscribe</a></p></td></tr>";
		      tempHTML = tempHTML + "    </table></td>";
		      tempHTML = tempHTML + "  </tr>";
		      tempHTML = tempHTML + "   </table></td>";
		      tempHTML = tempHTML + " </tr>";
              tempHTML = tempHTML + "</table>";
              tempHTML = tempHTML + "<a href='[unsubscribe]' style='display:none'>Click Here to Unsubscribe</a>";
		
		/* String tempHTML = null;
		 
		 Actual Code
		 
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
			 
			 StringBuilder candidateName = new StringBuilder();
			 Integer experienceInYears = 0;
			 Integer experienceInMonths = 0;
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
				 experienceInYears = Integer.parseInt(candidate.getExperienceYears());
			 }
			 
			 if(!isNullOrEmpty(candidate.getExperienceMonths())){
				 experienceInMonths = Integer.parseInt(candidate.getExperienceMonths());
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
		 tempHTML = tempHTML+"</table>";*/
		 
		 return tempHTML;
	}

	private boolean validateEmail(String email) {
			Pattern pattern = Pattern.compile(
					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		}

	@Override
	public void sendJobListEmailCampaign(JobListEmailCampaignDTO jobListEmailCampaignDTO, User user, OrgLocations orgLocation, MultipartFile multiFile) throws Exception
	{
		 String[] campaignJobs = null;
		 String jobs = null;
		 String emails = null;
		 StringBuilder toRecipantsEmailsList = new StringBuilder();
		 StringBuilder otherRecipantsEmailsList = new StringBuilder();
		 
		 String otherEmails = "";
		 InternetAddress[] iAdressArray = null;
		   	 if(!isNullOrEmpty(user.getLastName())){
		   	 }else{
		   	 }
				
			if(StringUtils.isNotBlank(jobListEmailCampaignDTO.getToemails())){
				
				String[] arr = jobListEmailCampaignDTO.getToemails().split(",");
				
				for(String listId : arr) {
					CampaignList campaignList = campaignService.getRecipientListByListId(listId,1);
					if(campaignList.getCampaignRecipient() !=null && campaignList.getCampaignRecipient().size() >0) {
						String reciEmail = getToReciEmails(campaignList.getCampaignRecipient());
						toRecipantsEmailsList.append(reciEmail).append(",");
					}
				}
				
//		    	  String jsonArray = jobListEmailCampaignDTO.getToemails();
//				   emails = jsonArray.toString();
//		    	  if(null != emails){
//		    		  emails = emails.replaceAll("\\[", "").replaceAll("\\]","");
//		    		  emails = emails.replaceAll("\"", "");
//		    	  }
//		    	 
//		    	  String[] recipantsEmails = emails.split(",");
//			    	 for(int i=0; i < recipantsEmails.length; i++){
//			    		 if(validateEmail(recipantsEmails[i])){
//			    			 toRecipantsEmailsList = toRecipantsEmailsList.append(recipantsEmails[i]);
//			    			 toRecipantsEmailsList.append(",");
//			    		 }
//			    	 }
			    	 emails = toRecipantsEmailsList.toString();
			    	 emails = emails.substring(0,emails.length()-1);
			}
			
			String otherReciListId = null; 
			if(StringUtils.isNotBlank(jobListEmailCampaignDTO.getOtherEmails())){
				 String jsonArray = jobListEmailCampaignDTO.getOtherEmails();
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
		    	 
		    	 List<String> othEmail = new ArrayList<String>(Arrays.asList(otherEmails.split(",")));
		    	 List<String> otherRecipientList = campaignUtil.createOtherRecipients(othEmail);
		    	 if(otherRecipientList !=null && otherRecipientList.size() >0) {
		    		 CampaignListDTO campaignListDTO = new CampaignListDTO();
		    		 campaignListDTO.setCpnListName(SourceLeadRandomCodeHelper.generateRandomVerificationCode());
		    		 String listResponse = campaignUtil.createList(campaignListDTO);
		    		 JSONObject jObject  = new JSONObject(listResponse); 
		 			  otherReciListId = jObject.getString("id");
		    		String res =  campaignUtil.addOtherRecipientsToList(otherRecipientList,otherReciListId);
		    		if(res !=null) {
		    			logger.info("Other Recipient Added to list");
					}	
		    	 }
			}
			
			StringBuilder tomaillBuilder = new StringBuilder();
			
			if(null != emails && null != otherEmails){
				iAdressArray = InternetAddress.parse(tomaillBuilder.append(emails).append(",").append(otherEmails).toString());
			}else if(null == emails && null != otherEmails){
				iAdressArray = InternetAddress.parse(tomaillBuilder.append(otherEmails).toString());
			}else if(null != emails && null == otherEmails){
				iAdressArray = InternetAddress.parse(tomaillBuilder.append(emails).toString());
			}
			
//			 emailMessage.setCreatedBy(user.getUserCode());
//			 emailMessage.setFromEmail(user.getEmail());
//			 emailMessage.setFromName(fullName);
			
			 if(StringUtils.isNotBlank(jobListEmailCampaignDTO.getCampaignjobs())){
				    
					 String jsonArray = jobListEmailCampaignDTO.getCampaignjobs();
					 jobs = jsonArray.toString();
					 if(null != jobs){
						 jobs = jobs.replaceAll("\\[", "").replaceAll("\\]","");
						 jobs = jobs.replaceAll("\"", "");
						 campaignJobs = jobs.split(",");
					 }
			 }
			String signature = sourceleadSignatureService.buildSignature(user, orgLocation);
			 String UUID = SourceLeadRandomCodeHelper.generateUUID();
			 String str =  getJobListCampaignsTable(campaignJobs,UUID,jobListEmailCampaignDTO,signature); 
//			 String table = str.replace("\"","\'");
			 String  table = str.replaceAll("\\s+"," ");
			 String createCampaignRes = campaignUtil.createCampaign(jobListEmailCampaignDTO, table,otherReciListId);
			 if (createCampaignRes != null) {
					JSONObject jObject = new JSONObject(createCampaignRes);
					String campaignId = jObject.getString("id");
					 String scheduleCampaignRes = campaignUtil.sendCampaign(campaignId);
					if(scheduleCampaignRes !=null) {
					 Campaign campaign= saveScheduledCampaignHistory(createCampaignRes,scheduleCampaignRes,orgLocation,user,1);
					 if(campaign !=null) {
						 logger.info("Campaign saved into campaig his table");
					 }
					  
					 Map<String, String> globalVarsMap = new HashMap<String, String>();
					 globalVarsMap.put("salutation", jobListEmailCampaignDTO.getSaluation());
					 globalVarsMap.put("body", jobListEmailCampaignDTO.getBodytext());
					 globalVarsMap.put("noOfJobs", String.valueOf(campaignJobs.length));
					 
//					 globalVarsMap.put("joblistTable", table);
					 
//					 EmailScenario emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.JOB_LIST_CAMPAIGN,Constants.ENTITY_TYPE_EMAIL);
					 //covert subject,body and signature
					 String subject = emailScenerioUtil.bulidSubject(jobListEmailCampaignDTO.getSubject(),globalVarsMap);
//					 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
					 
//					 String url = sourceleadUrl;
					 
					 //construct HTML template using required data
//					 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocation.getOrg(),orgLocation);
//					 emailMessage.setSubject(subject);
//					 emailMessage.setMessageBody(emailTemplate);
//					 emailMessage.setOrgLocationId(orgLocation.getId());
//					 emailMessage.setEmailType(Constants.EMAIL_CAMPAIGN);
					 
					 ArrayList<EmailAttachment> attachmentList = null;
					 EmailAttachment emailAttachment = new EmailAttachment();
					 FileInfo fileInfo = null;
					 if(jobListEmailCampaignDTO.getFileInfo() != null) {
						   fileInfo = jobListEmailCampaignDTO.getFileInfo().get(0);
						 if(fileInfo != null){
							 attachmentList =  new ArrayList<EmailAttachment>();
							 emailAttachment.setName(fileInfo.getFileName());
							 emailAttachment.setFileUrl(fileInfo.getFileURL());
							 emailAttachment.setType(fileInfo.getContentType());
							 attachmentList.add(emailAttachment);
						     FileUploadUtil.moveFile(jobListEmailCampaignDTO.getStaticPath(), fileInfo, "Joblist Campaign Documents");
						    }
					 }
					 
					 ArrayList<EmailAttachment> fileAttachmentList = null;
					 EmailAttachment attachment = new EmailAttachment();
					 
//					 if(multiFile.getSize() > 0){
//						 fileAttachmentList =  new ArrayList<EmailAttachment>();
//						 attachment.setName(multiFile.getOriginalFilename());
//						 attachment.setDocFile(multiFile.getBytes());
//						 attachment.setType(multiFile.getContentType());
//						 fileAttachmentList.add(attachment);
//					 }
					 
					//calling the sendEmail method of emailScenerioUtil
//					 emailScenerioUtil.sendEmail(emailMessage,orgLocation.getId(),iAdressArray,fileAttachmentList);
					 
					 UserMailBox userMailBox =  saveUserMailBox(user,orgLocation,iAdressArray,subject,table,UUID,jobListEmailCampaignDTO.getBodytext(),signature,fileInfo);
					 userMailBox.setRequirementId(jobs);
					 userMailBoxService.saveUserMailBox(userMailBox);
					 userMailBoxService.saveOrUpdateUserMailBoxELK(userMailBox);
			 
			 }
	}
			 
			
			 
			 
	}
	/**
	 * Saving Campaign info
	 * @param createCampaignRes
	 * @param scheduleCampaignRes
	 * @param orgLocation
	 * @param user
	 * @param campaignType 
	 * @return Campaign
	 * @throws Exception
	 */
	private Campaign saveScheduledCampaignHistory(String createCampaignRes, String scheduleCampaignRes, OrgLocations orgLocation, User user, int campaignType) throws Exception
	{
		Campaign campaign = new Campaign();
		JSONObject createCpnRes = new JSONObject(createCampaignRes); // json
		campaign.setCampaignId(createCpnRes.getString("id"));
		campaign.setCampaignType(campaignType);
		campaign.setCampaignSubject(createCpnRes.getString("subject"));
		campaign.setCampaignTitle(createCpnRes.getString("title"));
		if (StringUtils.isNotBlank(createCpnRes.getString("ip_pool"))) {
			campaign.setIpPool(createCpnRes.getString("ip_pool"));
		}
		campaign.setPlainContent(createCpnRes.getString("plain_content"));
		campaign.setHtmlContent(createCpnRes.getString("html_content"));
		campaign.setSenderId(createCpnRes.getString("sender_id"));
		campaign.setCampaignCategory((String) createCpnRes.getJSONArray("categories").get(0));
		JSONArray listArr = createCpnRes.getJSONArray("list_ids");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < listArr.length(); i++) {
			int listId = (int) listArr.get(i);
			sb.append(String.valueOf(listId)).append(",");
		}
		String str = sb.toString();
		str = str.substring(0, str.length() - 1);
		campaign.setListIds(str);
		JSONObject scheduleCpnResJosnObj = new JSONObject(scheduleCampaignRes); // json
		campaign.setCampaignScheduleId(scheduleCpnResJosnObj.getString("id"));
		campaign.setCampaignScheduleStaus(scheduleCpnResJosnObj.getString("status"));
		campaign.setOrgLocation(orgLocation);
		Date date = commonUtil.orgLocationCommonDate(new Date(), orgLocation);
		campaign.setCreatedDate(date);
		campaign.setCreatedBy(user);
		campaign.setUpdatedBy(user.getUserCode());
		campaign.setUpdatedDate(date);
		Campaign savedcpn = campaignService.saveCampaignScheduledHistroy(campaign);
		return savedcpn;
	}
	
	private String getToReciEmails(Set<CampaignRecipient> campaignRecipient)
	{
		List<String> reciList = new ArrayList<>();
		for(CampaignRecipient campaignEmail : campaignRecipient) {
			reciList.add(campaignEmail.getEmail());
		}
		String reciEmail = String.join(",", reciList);
		return reciEmail;
	}


	private String getJobListCampaignsTable(String[] campaignJobs, String UUID, JobListEmailCampaignDTO jobListEmailCampaignDTO, String signature) throws Exception
	{
		String tempHTML = "";
		String bodyText = "";
		String bText = jobListEmailCampaignDTO.getBodytext();
		if(StringUtils.isNotBlank(bText)) {
			bodyText = bText.replace("\"","\'");
		}
		String imageUrl = sourceleadUrl+"/resources/images/logo-sourceld.png";
		tempHTML = tempHTML + "<table width='650px' cellpadding='0' cellspacing='0' style='background-color:#fff; font-family:verdana, arial; margin:0px;'>";
		tempHTML = tempHTML + " <tr>";
		tempHTML = tempHTML + "<td><table width='650' align='center' cellpadding='0' cellspacing='0' style='padding:5px 0px; background-color:#fff; border:1px solid #E0E0E0; margin:0px;'>";
		tempHTML = tempHTML + "<tr>";
		tempHTML = tempHTML + "<td><table width='600' align='center' style='margin:0px;'>";
		tempHTML = tempHTML +  "<tr>";
		tempHTML = tempHTML +   "<td><span style='width: 200px; height: 45px; margin: 0 auto; display: block;'> <img src='"+imageUrl+"' width='200' height='45'> </span></td>";
		tempHTML = tempHTML +  "</tr>";
		tempHTML = tempHTML +  "</table></td>";
		tempHTML = tempHTML + "</tr>";
		tempHTML = tempHTML + "</table>";
		tempHTML = tempHTML + "<table width='650px'style='border-spacing:0; font-family:sans-serif; font-size: 14px; padding:20px 0; line-height: 22px; margin:0px;'>";
		tempHTML = tempHTML +  "<tbody>";
		tempHTML = tempHTML +   "<tr>";
		tempHTML = tempHTML +   "<td align='left' class='text-left'>"+jobListEmailCampaignDTO.getSaluation()+",<br>";
		tempHTML = tempHTML +     " <br></td>";
		tempHTML = tempHTML +   " </tr>";
		tempHTML = tempHTML +   "<tr>";
		tempHTML = tempHTML +    " <td align='left'  class='text-left'><p class='text-left'>"+bodyText+"</p></td>";
		tempHTML = tempHTML +   "  </tr>";
		tempHTML = tempHTML +    "</tbody>";
		tempHTML = tempHTML + "</table>";
		      //Table Design
		 tempHTML = tempHTML + "<table style='display:table-cell;vertical-align:middle; margin:0px;'>";
		 tempHTML = tempHTML+"<tr align='center'>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Job Title<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='15%' bgcolor='#5bace8'><span>Location<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='15%' bgcolor='#5bace8'><span>Employment Type<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='50%' bgcolor='#5bace8'><span>Skills<span></th>";
		 tempHTML = tempHTML+"</tr>";
		 
         Set<Long> jobsList = new HashSet<Long>();
         
         for(int i=0 ; i < campaignJobs.length; i++){
        	 jobsList.add(Long.valueOf(campaignJobs[i]));
		 }
		 
         int j=1;
		 
        for(Long jobId : jobsList){
			 if(j<=10){
				 
				 Requirement requirement = requirementService.getRequirementByRequirementId(jobId);
				 List<RequirementLocations> reqLoc = requirementLocationsService.getRequirementLocationsListByRequirementId(jobId);
				 StringBuilder location = new StringBuilder();
				 for (RequirementLocations requirementLocations : reqLoc) {
					 if(requirement.getId().longValue() == requirementLocations.getRequirementId().getId().longValue()) {
						 if(!isNullOrEmpty(requirementLocations.getCity())){
							 location = location.append(requirementLocations.getCity());
						 }
					 }
				}
				 
				 StringBuilder jobTitle = new StringBuilder();
				 if(!isNullOrEmpty(requirement.getJobId())){
					 jobTitle = jobTitle.append(requirement.getJobId()).append("-").append(requirement.getJobTitle());
				 }else{
					 jobTitle = jobTitle.append(requirement.getJobTitle());
				 }
				 
				 
//				 StringBuilder location = new StringBuilder();
//				 if(!isNullOrEmpty(reqLoc.getCity())){
//					 location = location.append(reqLoc.getCity());
//				 }
				 
				 StringBuilder employmentType = new StringBuilder();
				 if(!isNullOrEmpty(requirement.getJobType())){
					 employmentType = employmentType.append(requirement.getJobType());
				 }else{
					 employmentType = employmentType.append("----");
				 }
				 
				 
				 StringBuilder jobSkills = new StringBuilder();
				 String skills = null;
				 if(null != requirement.getSkills()){
					 String[] skillsList = requirement.getSkills().split(",");
					 for(String skill : skillsList){
						 jobSkills = jobSkills.append(skill).append(", ");
					 }
					 
					 jobSkills.deleteCharAt(jobSkills.length()-2) ;
					 skills = jobSkills.toString();
				 }else{
					 skills = "----";
				 }
				 
				 tempHTML = tempHTML+"<tr align='center'>";
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'><a style='text-decoration:none;' target='_blank' href='"+sourceleadUrl+"/"+"jobCampaignSummary?requirementUUID="+requirement.getRequirementUUID()+"'> "+jobTitle.toString()+" </a></td>";
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+location+"</td>";
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+employmentType+"</td>";
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+skills+"</td>";
				 
				 tempHTML = tempHTML+"</tr>";
				 
			 }
			 j++;
			 }
        
	         tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
			 tempHTML = tempHTML+"</td>";
			 tempHTML = tempHTML+"</tr>";;
			 tempHTML = tempHTML+"<tr style='background-color:#ffffff;min-height:32px;'>";
			 tempHTML = tempHTML+"<td style='padding-top:15px;padding-bottom:15px;padding-right:10px;padding-left:10px;'>";
			 tempHTML = tempHTML+"<table width='100%' style='border-spacing:0;font-family:sans-serif;color:#ffffff; margin:0px;'>";
			 tempHTML = tempHTML+"<tbody>";
			 tempHTML = tempHTML+"<tr>";
			 tempHTML = tempHTML+"<td style='cursor:pointer;padding-bottom:10px;padding-left:0;vertical-align:top;'>";
			 
			 if(jobsList.size() > 10){
				 tempHTML = tempHTML+"<br/><a href='"+sourceleadUrl+"/"+"viewAllJobCampaigns?jobCampaignUUID="+UUID+"'  target='_blank' style='color:#fff;text-decoration:none;font-size:15px!important;font-weight: 600; border: 1px solid #1565c0; padding: 5px 10px; background: #1e88e5;'>View all jobs</a> </td>";
			 }
			 
			 tempHTML = tempHTML+"</tr>";
			 tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
			 tempHTML = tempHTML+"</td>";
			 tempHTML = tempHTML+"</tr>";
			 tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
		 
			 tempHTML = tempHTML+"<br><br>";
			  // Table Design
			 tempHTML = tempHTML + signature;
		tempHTML = tempHTML + "<table width='650' bgcolor='#ededed' align='center' cellpadding='0' cellspacing='0' style='15px 0 15px 0; background-color:#ededed; padding:0px; margin:0px'>";
		tempHTML = tempHTML +" <tr>";
		tempHTML = tempHTML +"<td><table bgcolor='#ededed'  width='650' align='center' style='background-color:#ededed; padding:10px 15px; border:solid #E0E0E0; border-width:0 1px 0px 1px; margin:0px'>";
		tempHTML = tempHTML + "<tr>";
		tempHTML = tempHTML +"<td><p style='color:#214b90; text-transform: uppercase; padding:5px 0px; margin:0px 0px; font-size:13px; font-weight:600; text-align:center'>get started!</p>";
		tempHTML = tempHTML +"<p style='padding:0px 0px 2px 0px; margin:0px 0px; font-weight:400; font-size:11px; text-align:center'>For more information about SourceLead, go to <a target='blank' href='http://www.sourcelead.com/' style='text-decoration:none; color:#0D47A1; display:inline-block; padding:0; border-radius:0; margin:0; font-size:14px'>sourcelead.com</a></p>";
		tempHTML = tempHTML +"<p style='padding:0px 0px 2px 0px; margin:0px 0px; font-weight:400; font-size:11px; text-align:center'>3535 Victory Group Way, Building 4 Frisco, TX 75034, USA, Phone : (214) 295-5535, FAX : (972) 559-3641</p></td>";
		tempHTML = tempHTML +"</tr>";
		tempHTML = tempHTML +"<tr>";
		tempHTML = tempHTML +"<td><p style='text-align:center; padding:2px 0px; margin:0px 0px; font-size:11px; font-weight:400; border-top:1px solid #BDBDBD;'><a target='blank' href='http://www.sourcelead.com/about-us.html' style='text-decoration:none; color:#333;' title='About Us'>About Us</a> | <a target='blank' href='http://www.sourcelead.com/contact.html' style='text-decoration:none; color:#333;' title='Contact Us'>Contact Us</a> | <a target='blank' href='http://www.sourcelead.com/ats.html' style='text-decoration:none; color:#333;' title='ATS'>ATS</a> | <a target='blank' href='http://www.sourcelead.com/vms.html' style='text-decoration:none; color:#333;' title='VMS'>VMS</a> | <a target='blank' href='http://www.sourcelead.com/crms.html' style='text-decoration:none; color:#333;' title='CRMS'>CRMS</a> | <a target='blank' href='http://www.sourcelead.com/pms.html' style='text-decoration:none; color:#333;' title='PMS'>PMS</a> | <a target='blank' href='http://www.sourcelead.com/hrms.html' style='text-decoration:none; color:#333;' title='HRMS'>HRMS</a> | <a target='blank' href='http://www.sourcelead.com/ims.html' style='text-decoration:none; color:#333;' title='IMS'>IMS</a> | <a target='blank' href='http://www.sourcelead.com/search-jobs.html' style='text-decoration:none; color:#333;' title='Job Search'>Job Search</a> | <a target='blank' href='http://www.sourcelead.com/market-place.html' style='text-decoration:none; color:#333;' title='Marketplace'>Marketplace</a> <br> <a target='blank' href='http://www.sourcelead.com/terms-of-use.html' style='text-decoration:none; color:#333;' title='Terms of Use'>Terms of Use</a> | <a target='blank' href='http://www.sourcelead.com/privacy-policy.html' style='text-decoration:none; color:#333;' title='Privacy policy'>Privacy policy</a></p></td>";
		tempHTML = tempHTML +" </tr>";
		tempHTML = tempHTML +"<tr><td><p style='padding:5px 0px 5px 0px; margin:0px 0px; font-weight:400; font-size:11px; text-align:center;'>If you do not wish to receive any further communication, please  <a target='_blank' href='"+sourceleadUrl+"/ws/unsubscribe' style='color:#a21e23;'>unsubscribe</a></p></td></tr>";
		tempHTML = tempHTML + "</table></td>";
		tempHTML = tempHTML +" </tr>";
		tempHTML = tempHTML + " </table></td>";
		tempHTML = tempHTML +"</tr>";
		tempHTML = tempHTML +"</table>";
        tempHTML = tempHTML + "<a href='[unsubscribe]' style='display:none'>Click Here to Unsubscribe</a>";
		
		
		
		// Actual code
//         String tempHTML = null;
		 
		/* tempHTML = "<table style='display:table-cell;vertical-align:middle'>";
		 tempHTML = tempHTML+"<tr align='center'>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Job Title<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='15%' bgcolor='#5bace8'><span>Location<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='15%' bgcolor='#5bace8'><span>Employment Type<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='50%' bgcolor='#5bace8'><span>Skills<span></th>";
		 tempHTML = tempHTML+"</tr>";
		 
         Set<Long> jobsList = new HashSet<Long>();
         
         for(int i=0 ; i < campaignJobs.length; i++){
        	 jobsList.add(Long.valueOf(campaignJobs[i]));
		 }
		 
         int j=1;
		 
        for(Long jobId : jobsList){
			 if(j<=10){
				 
				 Requirement requirement = requirementService.getRequirementByRequirementId(jobId);
				 StringBuilder jobTitle = new StringBuilder();
				 if(!isNullOrEmpty(requirement.getJobId())){
					 jobTitle = jobTitle.append(requirement.getJobId()).append("-").append(requirement.getJobTitle());
				 }else{
					 jobTitle = jobTitle.append(requirement.getJobTitle());
				 }
				 
				 
				 StringBuilder location = new StringBuilder();
				 if(!isNullOrEmpty(requirement.getCity())){
					 location = location.append(requirement.getCity());
				 }
				 
				 StringBuilder employmentType = new StringBuilder();
				 if(!isNullOrEmpty(requirement.getJobType())){
					 employmentType = employmentType.append(requirement.getJobType());
				 }else{
					 employmentType = employmentType.append("----");
				 }
				 
				 
				 StringBuilder jobSkills = new StringBuilder();
				 String skills = null;
				 if(null != requirement.getSkills()){
					 String[] skillsList = requirement.getSkills().split(",");
					 for(String skill : skillsList){
						 jobSkills = jobSkills.append(skill).append(", ");
					 }
					 
					 jobSkills.deleteCharAt(jobSkills.length()-2) ;
					 skills = jobSkills.toString();
				 }else{
					 skills = "----";
				 }
				 
				 tempHTML = tempHTML+"<tr align='center'>";
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'><a style='text-decoration:none;' target='_blank' href='"+sourceleadUrl+"/"+"jobCampaignSummary?requirementUUID="+requirement.getRequirementUUID()+"'> "+jobTitle.toString()+" </a></td>";
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+location+"</td>";
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+employmentType+"</td>";
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+skills+"</td>";
				 
				 tempHTML = tempHTML+"</tr>";
				 
			 }
			 j++;
			 }
        
	         tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
			 tempHTML = tempHTML+"</td>";
			 tempHTML = tempHTML+"</tr>";;
			 tempHTML = tempHTML+"<tr style='background-color:#ffffff;min-height:32px;'>";
			 tempHTML = tempHTML+"<td style='padding-top:15px;padding-bottom:15px;padding-right:10px;padding-left:10px;'>";
			 tempHTML = tempHTML+"<table width='100%' style='border-spacing:0;font-family:sans-serif;color:#ffffff;'>";
			 tempHTML = tempHTML+"<tbody>";
			 tempHTML = tempHTML+"<tr>";
			 tempHTML = tempHTML+"<td style='cursor:pointer;padding-bottom:10px;padding-left:0;vertical-align:top;'>";
			 
			 if(jobsList.size() > 10){
				 tempHTML = tempHTML+"<br/><a href='"+sourceleadUrl+"/"+"viewAllJobCampaigns?jobCampaignUUID="+UUID+"'  target='_blank' style='color:#fff;text-decoration:none;font-size:15px!important;font-weight: 600; border: 1px solid #1565c0; padding: 5px 10px; background: #1e88e5;'>View all jobs</a> </td>";
			 }
			 
			 tempHTML = tempHTML+"</tr>";
			 tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
			 tempHTML = tempHTML+"</td>";
			 tempHTML = tempHTML+"</tr>";
			 tempHTML = tempHTML+"</tbody>";
			 tempHTML = tempHTML+"</table>";
		 
			 tempHTML = tempHTML+"<br><br>";
			 tempHTML = tempHTML + "<a href='[unsubscribe]'>Click Here to Unsubscribe</a>";
		 return tempHTML;*/
		 
		 // Actual code End
		 return tempHTML;
		
	}

}
