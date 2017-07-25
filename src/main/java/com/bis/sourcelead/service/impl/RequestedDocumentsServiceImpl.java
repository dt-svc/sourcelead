package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateDocumentsDao;
import com.bis.sourcelead.dao.RequestedDocumentsDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequestedDocuments;
import com.bis.sourcelead.entity.RequestedDocumentsHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.RequestedDocumentsHistoryService;
import com.bis.sourcelead.service.RequestedDocumentsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class RequestedDocumentsServiceImpl implements RequestedDocumentsService{

	@Autowired
	RequestedDocumentsDao requestedDocumentsDao;
	
	@Autowired
	CandidateDocumentsDao candidateDocumentsDao;
	
	@Autowired
	CandidateDao candidateDao;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	RequestedDocumentsHistoryService requestedDocumentsHistoryService;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	public RequestedDocuments saveRequestedDocuments(RequestedDocuments requestedDocuments) throws Exception
	{
		RequestedDocuments req = requestedDocumentsDao.saveRequestedDocuments(requestedDocuments);
	
		sendEmailToRequestedDocuments(requestedDocuments);
		
		return req;
		
	}
	
	@Override
	public void storeRequestedDocumentsDetails(JSONObject jsonObj, User user, OrgLocations orgLocations) throws Exception
	{
		
		if ((jsonObj.has("docuemntMaxRowNumber")) && jsonObj.has("requestedCandidateId") && !isNullOrEmpty("requestedCandidateId")){
			String docuemntMaxRowNumber = jsonObj.getString("docuemntMaxRowNumber");
			int docuemntMaxRowNumberCount = Integer.parseInt(docuemntMaxRowNumber);
			
			RequestedDocuments requestedDocuments = new RequestedDocuments();
			
			Candidate candidate = candidateDao.getCandidateByCandidateId(Long.valueOf(jsonObj.getString("requestedCandidateId")));
			requestedDocuments.setCandidateId(candidate);
			requestedDocuments.setUserId(user);
			requestedDocuments.setOrgLocationId(orgLocations);
			requestedDocuments.setRequestDocUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			requestedDocuments.setIsDocsUploaded(Constants.NO);
			requestedDocuments.setStatus(1);
			requestedDocuments.setCreatedBy(user.getUserCode());
			requestedDocuments.setUpdatedBy(user.getUserCode());
			requestedDocuments.setCreatedDate(new Date());
			requestedDocuments.setUpdatedDate(new Date());
			requestedDocumentsDao.saveRequestedDocuments(requestedDocuments);
			
			for (int a = 0; a < docuemntMaxRowNumberCount; a++) {
				
				if(jsonObj.has(a+"docType")){
					
					RequestedDocumentsHistory requestedDocumentsHistory = new RequestedDocumentsHistory();
					requestedDocumentsHistory.setRequestedDocumentsId(requestedDocuments);
					if(jsonObj.has(a+"otherDocType") && !isNullOrEmpty(a+"otherDocType")){
						requestedDocumentsHistory.setDocumentType(jsonObj.getString(a+"otherDocType"));
					}else{
						requestedDocumentsHistory.setDocumentType(jsonObj.getString(a+"docType"));
					}
					
					if(jsonObj.has(a+"description") && !isNullOrEmpty(a+"description")){
						requestedDocumentsHistory.setDescription(jsonObj.getString(a+"description"));
					}else{
						requestedDocumentsHistory.setDescription(null);
					}
					
					if(jsonObj.has(a+"checked") && !isNullOrEmpty(a+"checked") && 
							"Y".equalsIgnoreCase(jsonObj.getString(a+"checked"))){
						requestedDocumentsHistory.setIsRequestedDocRequired(jsonObj.getString(a+"checked"));
					}else{
						requestedDocumentsHistory.setIsRequestedDocRequired(Constants.NO);
					}
					
					requestedDocumentsHistory.setStatus(1);
					requestedDocumentsHistory.setCreatedBy(user.getUserCode());
					requestedDocumentsHistory.setUpdatedBy(user.getUserCode());
					requestedDocumentsHistory.setCreatedDate(new Date());
					requestedDocumentsHistory.setUpdatedDate(new Date());
					requestedDocumentsHistoryService.saveRequestedDocumentsHistory(requestedDocumentsHistory);
					
					CandidateDocuments candidateDocuments = new CandidateDocuments();	
					candidateDocuments.setRequestedDocumentsId(requestedDocuments);
					candidateDocuments.setCandidateId(candidate);
					candidateDocuments.setDocUploadStatus(Constants.DOCUMENT_UPLOAD_REQUESTED);
					candidateDocuments.setDocumentType(jsonObj.getString(a+"docType"));
					
					if(jsonObj.has(a+"otherDocType") && !isNullOrEmpty(a+"otherDocType")){
						candidateDocuments.setDocumentOtherType(jsonObj.getString(a+"otherDocType"));
					}
					
					if(jsonObj.has(a+"description") && !isNullOrEmpty(a+"description")){
						candidateDocuments.setDescription(jsonObj.getString(a+"description"));
					}
					
					if(jsonObj.has(a+"checked") && !isNullOrEmpty(a+"checked") && 
							"Y".equalsIgnoreCase(jsonObj.getString(a+"checked"))){
						candidateDocuments.setIsRequestedDocRequired(jsonObj.getString(a+"checked"));
					}else{
						candidateDocuments.setIsRequestedDocRequired(Constants.NO);
					}
					
					candidateDocuments.setOrgLocationId(orgLocations);
					candidateDocuments.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					candidateDocuments.setStatus(1);
					candidateDocuments.setCreatedBy(user.getUserCode());
					candidateDocuments.setUpdatedBy(user.getUserCode());
					candidateDocuments.setCreatedDate(new Date());
					candidateDocuments.setUpdatedDate(new Date());
					candidateDocumentsDao.storeCandidateDocuments(candidateDocuments);
				}
			}
			
			sendEmailToCandidateForDocument(user,candidate,orgLocations,requestedDocuments.getRequestDocUUID());
			
		}
	}
	
	
	public void sendEmailToCandidateForDocument(User user,Candidate candidate,OrgLocations orgLocations,String requestDocUUID) throws Exception {
		
		 
		 StringBuilder sb1 = new StringBuilder();
		 StringBuilder sb2 = new StringBuilder();
		 
		 EmailMessage emailMessage = new EmailMessage();
		 String userName = null;
		 String candidateName = null;
		 
		 if(!isNullOrEmpty(user.getLastName())){
			 userName = sb1.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		 }else{
			 userName = sb1.append(user.getFirstName()).toString();
		 }
		 
		 if(!isNullOrEmpty(candidate.getLastName())){
			 candidateName = sb2.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString();
		 }else{
			 candidateName = sb2.append(candidate.getFirstName()).toString();
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 emailMessage.setToEmail(candidate.getEmail());
		 emailMessage.setFromName(userName);
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setCreatedBy(user.getUserCode());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("candidateName", candidateName);
		 globalVarsMap.put("loginUser", userName);
		 globalVarsMap.put("loginOrg", orgLocations.getOrg().getOrgName());
		 
		 StringBuilder loginBuilder = new StringBuilder();
		 
		 
		 globalVarsMap.put("requestLink",loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append("requestedDocuments?").append("requestDocUUID=").append(requestDocUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Upload Documents</div></a>").toString());
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REQUESTED_DOCUMENTS,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		
	}
	
	
	public void sendEmailToRequestedDocuments(RequestedDocuments requestedDocuments) throws Exception{
		
		 EmailMessage emailMessage = new EmailMessage();
		 
		 
		 emailMessage.setToEmail(requestedDocuments.getUserId().getEmail());
		 emailMessage.setFromName(requestedDocuments.getCandidateId().getFirstName());
		 emailMessage.setFromEmail(requestedDocuments.getCandidateId().getEmail());
		 emailMessage.setCreatedBy(requestedDocuments.getUserId().getUserCode());
		 
		 
		
		/* String userName = null;
		 String candidateName = null;*/
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(!isNullOrEmpty(requestedDocuments.getUserId().getLastName())){
			 StringBuilder sb1 = new StringBuilder();
			// userName = sb1.append(requestedDocuments.getUserId().getFirstName()).append(" ").append(requestedDocuments.getUserId().getLastName()).toString();
			 globalVarsMap.put("userName", sb1.append(requestedDocuments.getUserId().getFirstName()).append(" ").append(requestedDocuments.getUserId().getLastName()).toString());
		 }else{
			 globalVarsMap.put("userName", requestedDocuments.getUserId().getFirstName());
		 }
		 
		 if(!isNullOrEmpty(requestedDocuments.getCandidateId().getLastName())){
			 StringBuilder sb2 = new StringBuilder();
			 globalVarsMap.put("candidateName", sb2.append(requestedDocuments.getCandidateId().getFirstName()).append(" ").append(requestedDocuments.getCandidateId().getLastName()).toString());
		 }else{
			 globalVarsMap.put("candidateName", requestedDocuments.getCandidateId().getFirstName());
		 }
		
		
		 
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPLOADED_REQUESTED_DOCUMENTS,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPLOADED_REQUESTED_DOCUMENTS,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, requestedDocuments.getOrgLocationId().getOrg(),requestedDocuments.getOrgLocationId());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 User fromUser = userService.getUserById(Constants.USERID);
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,requestedDocuments.getOrgLocationId().getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, fromUser, requestedDocuments.getUserId(), requestedDocuments.getOrgLocationId(), notificationScenario);
	}

	@Override
	public RequestedDocuments getRequestedDocumentsByRequestDocUUID(String requestDocUUID) throws Exception
	{
		return requestedDocumentsDao.getRequestedDocumentsByRequestDocUUID(requestDocUUID);
	}

	@Override
	public void rejectRequestedDocuments(JSONObject jsonObj, User user, OrgLocations orgLocations, String documentsIds) throws Exception
	{
		if(!isNullOrEmpty(documentsIds)){
			String[] documents = documentsIds.split(",");
			
				Candidate candidate = candidateDao.getCandidateByCandidateId(Long.valueOf(jsonObj.getString("rejectedDocsCandidateId")));
				
				RequestedDocuments requestedDocuments = new RequestedDocuments();
				requestedDocuments.setCandidateId(candidate);
				requestedDocuments.setUserId(user);
				requestedDocuments.setOrgLocationId(orgLocations);
				requestedDocuments.setRequestDocUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				requestedDocuments.setIsDocsUploaded(Constants.NO);
				requestedDocuments.setStatus(1);
				requestedDocuments.setCreatedBy(user.getUserCode());
				requestedDocuments.setUpdatedBy(user.getUserCode());
				requestedDocuments.setCreatedDate(new Date());
				requestedDocuments.setUpdatedDate(new Date());
				requestedDocumentsDao.saveRequestedDocuments(requestedDocuments);
				
				
				for(int i=0; i<documents.length; i++){
					
					CandidateDocuments candidateDocuments = candidateDocumentsDao.getCandidateDocumentByDocumentId(Long.valueOf(documents[i]));
					
					RequestedDocumentsHistory requestedDocumentsHistory = new RequestedDocumentsHistory();
					requestedDocumentsHistory.setRequestedDocumentsId(requestedDocuments);
					
					if(!isNullOrEmpty(candidateDocuments.getDocumentOtherType())){
						requestedDocumentsHistory.setDocumentType(candidateDocuments.getDocumentOtherType());
					}else{
						requestedDocumentsHistory.setDocumentType(candidateDocuments.getDocumentType());
					}
					
					if(!isNullOrEmpty(candidateDocuments.getDescription())){
						requestedDocumentsHistory.setDescription(candidateDocuments.getDescription());
					}else{
						requestedDocumentsHistory.setDescription(null);
					}
					
					requestedDocumentsHistory.setIsRequestedDocRequired(candidateDocuments.getIsRequestedDocRequired());
					
					requestedDocumentsHistory.setStatus(1);
					requestedDocumentsHistory.setCreatedBy(user.getUserCode());
					requestedDocumentsHistory.setUpdatedBy(user.getUserCode());
					requestedDocumentsHistory.setCreatedDate(new Date());
					requestedDocumentsHistory.setUpdatedDate(new Date());
					requestedDocumentsHistoryService.saveRequestedDocumentsHistory(requestedDocumentsHistory);
					
					candidateDocuments.setRequestedDocumentsId(requestedDocuments);
					candidateDocuments.setRequestedDocStatus(Constants.REQUESTED_DOCUMENT_REJECTED);
					
					if(jsonObj.has("rejectedReason") && !isNullOrEmpty(jsonObj.getString("rejectedReason"))){
						candidateDocuments.setRequestedDocRejectedReason(jsonObj.getString("rejectedReason"));
					}
					
					candidateDocuments.setCreatedBy(user.getUserCode());
					candidateDocuments.setUpdatedBy(user.getUserCode());
					candidateDocuments.setCreatedDate(new Date());
					candidateDocuments.setUpdatedDate(new Date());
					candidateDocumentsDao.storeCandidateDocuments(candidateDocuments);
				}
				sendEmailToCandidateForDocument(user,candidate,orgLocations,requestedDocuments.getRequestDocUUID());
		}
	}

	@Override
	public void acceptRequestedDocuments(User user, String documentsIds) throws Exception
	{
		if(!isNullOrEmpty(documentsIds)){
			String[] documents = documentsIds.split(",");
			for(int i=0; i<documents.length; i++){
				CandidateDocuments candidateDocuments = candidateDocumentsDao.getCandidateDocumentByDocumentId(Long.valueOf(documents[i]));
				candidateDocuments.setRequestedDocStatus(Constants.REQUESTED_DOCUMENT_ACCEPTED);
				candidateDocuments.setCreatedBy(user.getUserCode());
				candidateDocuments.setUpdatedBy(user.getUserCode());
				candidateDocuments.setCreatedDate(new Date());
				candidateDocuments.setUpdatedDate(new Date());
				candidateDocumentsDao.storeCandidateDocuments(candidateDocuments);
			}
			
		}
		
	}
	
}
