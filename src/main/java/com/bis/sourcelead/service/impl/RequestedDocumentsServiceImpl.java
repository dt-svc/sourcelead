package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateDocumentsDao;
import com.bis.sourcelead.dao.CandidateGlobalDao;
import com.bis.sourcelead.dao.RequestedDocumentsDao;
import com.bis.sourcelead.dto.CandidateDocumentsDTO;
import com.bis.sourcelead.dto.RequestDocumentList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.CandidateGlobal;
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
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
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
	
	@Value("${atsUrl}")
    private String atsUrl;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	RequestedDocumentsHistoryService requestedDocumentsHistoryService;
	
	@Autowired
	 CandidateActivityCommonUtil candidateActivityCommonUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateGlobalDao candidateGlobalDao;
	
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
	public void storeRequestedDocumentsDetails(CandidateDocumentsDTO candidateDocumentsDTO, User user, OrgLocations orgLocations) throws Exception
	{
		
		if (StringUtils.isNotBlank(candidateDocumentsDTO.getDocuemntMaxRowNumber()) && StringUtils.isNotBlank(candidateDocumentsDTO.getRequestedCandidateId())){
			String docuemntMaxRowNumber = candidateDocumentsDTO.getDocuemntMaxRowNumber();
			int docuemntMaxRowNumberCount = Integer.parseInt(docuemntMaxRowNumber);
			CandidateGlobal candidateGlobal = null;
			Candidate candidate = null;
			String candidateName = null;
			StringBuilder sb = new StringBuilder();
			RequestedDocuments requestedDocuments = new RequestedDocuments();
			StringBuffer activitySummary = new StringBuffer();
			if(StringUtils.isNotBlank(candidateDocumentsDTO.getCandidateType()) && candidateDocumentsDTO.getCandidateType().equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
				candidateGlobal = candidateGlobalDao.getCandidateGlobalByCandidateId(Long.valueOf(candidateDocumentsDTO.getRequestedCandidateId()));
				requestedDocuments.setCandidateGlobalId(candidateGlobal);
			}else {
				candidate = candidateDao.getCandidateByCandidateId(Long.valueOf(candidateDocumentsDTO.getRequestedCandidateId()));
				requestedDocuments.setCandidateId(candidate);
			}
			
			requestedDocuments.setUserId(user);
			requestedDocuments.setOrgLocationId(orgLocations);
			requestedDocuments.setRequestDocUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			requestedDocuments.setIsDocsUploaded(Constants.NO);
			requestedDocuments.setStatus(Constants.RECORD_ACTIVE);
			requestedDocuments.setCreatedBy(user.getUserCode());
			requestedDocuments.setUpdatedBy(user.getUserCode());
			requestedDocuments.setCreatedDate(new Date());
			requestedDocuments.setUpdatedDate(new Date());
			requestedDocumentsDao.saveRequestedDocuments(requestedDocuments);
			activitySummary.append("Document Types").append(" : ");
			
			
			for( RequestDocumentList docList:candidateDocumentsDTO.getRequestDocumentList()){
				
				if(StringUtils.isNotBlank(docList.getDocType())){
					
					RequestedDocumentsHistory requestedDocumentsHistory = new RequestedDocumentsHistory();
					requestedDocumentsHistory.setRequestedDocumentsId(requestedDocuments);
					
					if(StringUtils.isNotBlank(docList.getOtherDocType())){
						requestedDocumentsHistory.setDocumentType(docList.getOtherDocType());
						activitySummary.append(Constants.NEW_VALUE_TAG_INITIATION).append(docList.getOtherDocType()).append(Constants.NEW_VALUE_TAG_END).append(", ");
					}else{
						requestedDocumentsHistory.setDocumentType(docList.getDocType());
						activitySummary.append(Constants.NEW_VALUE_TAG_INITIATION).append(docList.getDocType()).append(Constants.NEW_VALUE_TAG_END).append(", ");
					}
					
					if(StringUtils.isNotBlank(docList.getDescription())){
						requestedDocumentsHistory.setDescription(docList.getDescription());
					}else{
						requestedDocumentsHistory.setDescription(null);
					}
					
					if(StringUtils.isNotBlank(docList.getChecked())&&"Y".equalsIgnoreCase(docList.getChecked())){
						requestedDocumentsHistory.setIsRequestedDocRequired(docList.getChecked());
					}else{
						requestedDocumentsHistory.setIsRequestedDocRequired(Constants.NO);
					}
					
					requestedDocumentsHistory.setStatus(Constants.RECORD_ACTIVE);
					requestedDocumentsHistory.setCreatedBy(user.getUserCode());
					requestedDocumentsHistory.setUpdatedBy(user.getUserCode());
					requestedDocumentsHistory.setCreatedDate(new Date());
					requestedDocumentsHistory.setUpdatedDate(new Date());
					requestedDocumentsHistoryService.saveRequestedDocumentsHistory(requestedDocumentsHistory);
					
					CandidateDocuments candidateDocuments = new CandidateDocuments();	
					candidateDocuments.setRequestedDocumentsId(requestedDocuments);
					if(StringUtils.isNotBlank(candidateDocumentsDTO.getCandidateType()) && candidateDocumentsDTO.getCandidateType().equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
						candidateDocuments.setCandidateGlobalId(candidateGlobal);
					}else {
						candidateDocuments.setCandidateId(candidate);
					}
					
					candidateDocuments.setDocUploadStatus(Constants.DOCUMENT_UPLOAD_REQUESTED);
					candidateDocuments.setDocumentType(docList.getDocType());
					
					if(StringUtils.isNotBlank(docList.getOtherDocType())){
						candidateDocuments.setDocumentOtherType(docList.getOtherDocType());
					}
					
					if(StringUtils.isNotBlank(docList.getDescription())){
						candidateDocuments.setDescription(docList.getDescription());
					}
					
					if(StringUtils.isNotBlank(docList.getChecked())&&"Y".equalsIgnoreCase(docList.getChecked())){
						candidateDocuments.setIsRequestedDocRequired(docList.getChecked());
					}else{
						candidateDocuments.setIsRequestedDocRequired(Constants.NO);
					}
					
					candidateDocuments.setOrgLocationId(orgLocations);
					candidateDocuments.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					candidateDocuments.setStatus(Constants.RECORD_ACTIVE);
					candidateDocuments.setCreatedBy(user.getUserCode());
					candidateDocuments.setUpdatedBy(user.getUserCode());
					candidateDocuments.setCreatedDate(new Date());
					candidateDocuments.setUpdatedDate(new Date());
					candidateDocumentsDao.storeCandidateDocuments(candidateDocuments);
					
				}
				
			}
			
			
			
			if(StringUtils.isNotBlank(candidateDocumentsDTO.getCandidateType()) && candidateDocumentsDTO.getCandidateType().equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
				 if(!isNullOrEmpty(candidateGlobal.getLastName())){
					 candidateName = sb.append(candidateGlobal.getFirstName()).append(" ").append(candidateGlobal.getLastName()).toString();
				 }else{
					 candidateName = sb.append(candidateGlobal.getFirstName()).toString();
				 }
				sendEmailToCandidateForDocument(user,candidateName,candidateGlobal.getEmail(),orgLocations,requestedDocuments.getRequestDocUUID());
				candidateActivityCommonUtil.storeCandidateGlobalActivity(user, orgLocations, candidateGlobal, "", Constants.CANDIDATE_REQUEST_DOCUMENTS, activitySummary.toString().substring(0, activitySummary.toString().length() - 2), "CANDIDATE");
			}else {
				 if(!isNullOrEmpty(candidate.getLastName())){
					 candidateName = sb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString();
				 }else{
					 candidateName = sb.append(candidate.getFirstName()).toString();
				 }
				sendEmailToCandidateForDocument(user,candidateName,candidate.getEmail(),orgLocations,requestedDocuments.getRequestDocUUID());
				candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_REQUEST_DOCUMENTS, activitySummary.toString().substring(0, activitySummary.toString().length() - 2), "CANDIDATE");
			}
			
		}
	}
	
	
	public void sendEmailToCandidateForDocument(User user,String candidateName,String candidateEmail,OrgLocations orgLocations,String requestDocUUID) throws Exception {
		
		 
		 StringBuilder sb1 = new StringBuilder();
		 
		 EmailMessage emailMessage = new EmailMessage();
		 String userName = null;
		
		 
		 if(!isNullOrEmpty(user.getLastName())){
			 userName = sb1.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		 }else{
			 userName = sb1.append(user.getFirstName()).toString();
		 }
		
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 emailMessage.setToEmail(candidateEmail);
		 emailMessage.setFromName(userName);
		 emailMessage.setFromEmail(user.getEmail());
		 emailMessage.setCreatedBy(user.getUserCode());
		 emailMessage.setFirstName(candidateName);
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("candidateName", candidateName);
		 globalVarsMap.put("loginUser", userName);
		 globalVarsMap.put("loginOrg", orgLocations.getOrgLocationName());
		 StringBuilder loginBuilder = new StringBuilder();
		 
		 
		 globalVarsMap.put("requestLink",loginBuilder.append("<a href='").append(atsUrl).append("/").append("ws/requestedDocuments?").append("requestDocUUID=").append(requestDocUUID).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Upload Documents</div></a>").toString());
		 
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.REQUESTED_DOCUMENTS,new ArrayList<EmailAttachment>());
		
	}
	
	
	public void sendEmailToRequestedDocuments(RequestedDocuments requestedDocuments) throws Exception{
		
		 EmailMessage emailMessage = new EmailMessage();
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 emailMessage.setToEmail(requestedDocuments.getUserId().getEmail());
		 if(requestedDocuments.getCandidateGlobalId() != null && requestedDocuments.getCandidateId() == null) {
			 emailMessage.setFromName(requestedDocuments.getCandidateGlobalId().getFirstName());
			 emailMessage.setFromEmail(requestedDocuments.getCandidateGlobalId().getEmail());
			 emailMessage.setCreatedBy(requestedDocuments.getUserId().getUserCode());
			 emailMessage.setFirstName(requestedDocuments.getCandidateGlobalId().getFirstName());
			 
			 if(!isNullOrEmpty(requestedDocuments.getCandidateGlobalId().getLastName())){
				 StringBuilder sb2 = new StringBuilder();
				 globalVarsMap.put("candidateName", sb2.append(requestedDocuments.getCandidateGlobalId().getFirstName()).append(" ").append(requestedDocuments.getCandidateGlobalId().getLastName()).toString());
			 }else{
				 globalVarsMap.put("candidateName", requestedDocuments.getCandidateGlobalId().getFirstName());
			 }
		 }else {
			 emailMessage.setFromName(requestedDocuments.getCandidateId().getFirstName());
			 emailMessage.setFromEmail(requestedDocuments.getCandidateId().getEmail());
			 emailMessage.setCreatedBy(requestedDocuments.getUserId().getUserCode());
			 emailMessage.setFirstName(requestedDocuments.getCandidateId().getFirstName());
			 
			 if(!isNullOrEmpty(requestedDocuments.getCandidateId().getLastName())){
				 StringBuilder sb2 = new StringBuilder();
				 globalVarsMap.put("candidateName", sb2.append(requestedDocuments.getCandidateId().getFirstName()).append(" ").append(requestedDocuments.getCandidateId().getLastName()).toString());
			 }else{
				 globalVarsMap.put("candidateName", requestedDocuments.getCandidateId().getFirstName());
			 }
		 }
		
		
		/* String userName = null;
		 String candidateName = null;*/
		
		 if(!isNullOrEmpty(requestedDocuments.getUserId().getLastName())){
			 StringBuilder sb1 = new StringBuilder();
			 globalVarsMap.put("userName", sb1.append(requestedDocuments.getUserId().getFirstName()).append(" ").append(requestedDocuments.getUserId().getLastName()).toString());
		 }else{
			 globalVarsMap.put("userName", requestedDocuments.getUserId().getFirstName());
		 }
		 
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPLOADED_REQUESTED_DOCUMENTS,Constants.ENTITY_TYPE_NOTIFICATION);

		 String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		 notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 User fromUser = userService.getUserById(Constants.USERID);
		 //calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,requestedDocuments.getOrgLocationId().getOrg(),requestedDocuments.getOrgLocationId(),Constants.UPLOADED_REQUESTED_DOCUMENTS,new ArrayList<EmailAttachment>());
		 StringBuilder notificationLink = new StringBuilder();
		 String actionLink="";
		 if(requestedDocuments.getCandidateGlobalId() != null && requestedDocuments.getCandidateId() == null) {
			 actionLink = notificationLink.append(atsUrl).append("/").append("candidatesList?&candidateIdSearchId="+requestedDocuments.getCandidateGlobalId().getId()+"&candidateKeyword=requestedDocumentNotification").toString();
		 }else {
			 actionLink = notificationLink.append(atsUrl).append("/").append("candidatesList?&candidateIdSearchId="+requestedDocuments.getCandidateId().getId()+"&candidateKeyword=requestedDocumentNotification").toString();
		 }
	    
		 emailScenerioUtil.sendNotification(actionLink, fromUser, requestedDocuments.getUserId(), requestedDocuments.getOrgLocationId(), notificationScenario,new HashMap<String,String>());
	}

	@Override
	public RequestedDocuments getRequestedDocumentsByRequestDocUUID(String requestDocUUID) throws Exception
	{
		return requestedDocumentsDao.getRequestedDocumentsByRequestDocUUID(requestDocUUID);
	}

	@Override
	public void rejectRequestedDocuments(JSONObject jsonObj, User user, OrgLocations orgLocations, String documentsIds, String candidateType) throws Exception
	{
		if(!isNullOrEmpty(documentsIds)){
			String[] documents = documentsIds.split(",");
			Candidate candidate = null;
			CandidateGlobal candidateGlobal = null;
			String candidateName = null;
			StringBuilder sb = new StringBuilder();
			RequestedDocuments requestedDocuments = new RequestedDocuments();
			if(StringUtils.isNotBlank(candidateType) && candidateType.equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
				 candidateGlobal = candidateGlobalDao.getCandidateGlobalByCandidateId(Long.valueOf(jsonObj.getString("rejectedDocsCandidateId")));
				 requestedDocuments.setCandidateGlobalId(candidateGlobal);
			}else {
				 candidate = candidateDao.getCandidateByCandidateId(Long.valueOf(jsonObj.getString("rejectedDocsCandidateId")));
					requestedDocuments.setCandidateId(candidate);
			}
				
				StringBuffer activitySummary = new StringBuffer();
				StringBuffer rejectSummary = new StringBuffer();
			
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
				
				activitySummary.append("Document Types").append(" : ");
				rejectSummary.append("Rejected Reason").append(" : ");
				for(int i=0; i<documents.length; i++){
					
					CandidateDocuments candidateDocuments = candidateDocumentsDao.getCandidateDocumentByDocumentId(Long.valueOf(documents[i]));
					
					RequestedDocumentsHistory requestedDocumentsHistory = new RequestedDocumentsHistory();
					requestedDocumentsHistory.setRequestedDocumentsId(requestedDocuments);
					
					if(!isNullOrEmpty(candidateDocuments.getDocumentOtherType())){
						requestedDocumentsHistory.setDocumentType(candidateDocuments.getDocumentOtherType());
						activitySummary.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentOtherType()).append(Constants.NEW_VALUE_TAG_END).append(", ");
					}else{
						requestedDocumentsHistory.setDocumentType(candidateDocuments.getDocumentType());
						activitySummary.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentType()).append(Constants.NEW_VALUE_TAG_END).append(", ");
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
				if(StringUtils.isNotBlank(candidateType) && candidateType.equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
					
					 if(!isNullOrEmpty(candidateGlobal.getLastName())){
						 candidateName = sb.append(candidateGlobal.getFirstName()).append(" ").append(candidateGlobal.getLastName()).toString();
					 }else{
						 candidateName = sb.append(candidateGlobal.getFirstName()).toString();
					 }
					 
					sendEmailToCandidateForDocument(user,candidateName,candidateGlobal.getEmail(),orgLocations,requestedDocuments.getRequestDocUUID());
					String summaryStr = activitySummary.toString().substring(0, activitySummary.toString().length() - 2) +"<br />" + rejectSummary.append(jsonObj.getString("rejectedReason")).toString();
					candidateActivityCommonUtil.storeCandidateGlobalActivity(user, orgLocations, candidateGlobal, "", Constants.CANDIDATE_REJECT_REQUEST_DOCUMENTS, summaryStr, "CANDIDATE");
				}else {
					
					 if(!isNullOrEmpty(candidate.getLastName())){
						 candidateName = sb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString();
					 }else{
						 candidateName = sb.append(candidate.getFirstName()).toString();
					 }
					sendEmailToCandidateForDocument(user,candidateName,candidate.getEmail(),orgLocations,requestedDocuments.getRequestDocUUID());
					String summaryStr = activitySummary.toString().substring(0, activitySummary.toString().length() - 2) +"<br />" + rejectSummary.append(jsonObj.getString("rejectedReason")).toString();
					candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_REJECT_REQUEST_DOCUMENTS, summaryStr, "CANDIDATE");
				}
				
		}
	}

	@Override
	public void acceptRequestedDocuments(User user, String documentsIds, OrgLocations orgLocations, Candidate candidate) throws Exception
	{
		StringBuffer activitySummary = new StringBuffer();
		if(!isNullOrEmpty(documentsIds)){
			activitySummary.append("Document Types").append(" : ");
			String[] documents = documentsIds.split(",");
			for(int i=0; i<documents.length; i++){
				CandidateDocuments candidateDocuments = candidateDocumentsDao.getCandidateDocumentByDocumentId(Long.valueOf(documents[i]));
				candidateDocuments.setRequestedDocStatus(Constants.REQUESTED_DOCUMENT_ACCEPTED);
				candidateDocuments.setCreatedBy(user.getUserCode());
				candidateDocuments.setUpdatedBy(user.getUserCode());
				candidateDocuments.setCreatedDate(new Date());
				candidateDocuments.setUpdatedDate(new Date());
				candidateDocumentsDao.storeCandidateDocuments(candidateDocuments);
				if(!isNullOrEmpty(candidateDocuments.getDocumentOtherType())){
					activitySummary.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentOtherType()).append(Constants.NEW_VALUE_TAG_END).append(", ");
				}else{
					activitySummary.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentType()).append(Constants.NEW_VALUE_TAG_END).append(", ");
				}
			}
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_ACCEPT_REQUEST_DOCUMENTS, activitySummary.toString().substring(0, activitySummary.toString().length() - 2), "CANDIDATE");
		}
		
	}

	@Override
	public void acceptRequestedCandidateGlobalDocuments(User user, String documentsIds, OrgLocations orgLocations,
			CandidateGlobal candidateGlobal) throws Exception {

		StringBuffer activitySummary = new StringBuffer();
		if(!isNullOrEmpty(documentsIds)){
			activitySummary.append("Document Types").append(" : ");
			String[] documents = documentsIds.split(",");
			for(int i=0; i<documents.length; i++){
				CandidateDocuments candidateDocuments = candidateDocumentsDao.getCandidateDocumentByDocumentId(Long.valueOf(documents[i]));
				candidateDocuments.setRequestedDocStatus(Constants.REQUESTED_DOCUMENT_ACCEPTED);
				candidateDocuments.setCreatedBy(user.getUserCode());
				candidateDocuments.setUpdatedBy(user.getUserCode());
				candidateDocuments.setCreatedDate(new Date());
				candidateDocuments.setUpdatedDate(new Date());
				candidateDocumentsDao.storeCandidateDocuments(candidateDocuments);
				if(!isNullOrEmpty(candidateDocuments.getDocumentOtherType())){
					activitySummary.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentOtherType()).append(Constants.NEW_VALUE_TAG_END).append(", ");
				}else{
					activitySummary.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentType()).append(Constants.NEW_VALUE_TAG_END).append(", ");
				}
			}
			candidateActivityCommonUtil.storeCandidateGlobalActivity(user, orgLocations, candidateGlobal, "", Constants.CANDIDATE_ACCEPT_REQUEST_DOCUMENTS, activitySummary.toString().substring(0, activitySummary.toString().length() - 2), "CANDIDATE");
		}
		
	
		
	}
	
}
