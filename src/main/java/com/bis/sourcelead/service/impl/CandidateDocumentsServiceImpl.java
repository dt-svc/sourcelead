package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDocumentsDao;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class CandidateDocumentsServiceImpl implements CandidateDocumentsService {
	private static final Logger logger = LoggerFactory.getLogger(CandidateDocumentsServiceImpl.class);
	@Autowired
	private CandidateDocumentsDao candidateDocumentsDao;
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	 CandidateActivityCommonUtil candidateActivityCommonUtil;
	
	@Override
	public CandidateDocuments storeCandidateDocuments(JSONObject jsonObj, Long candidateId, String documentUUID, Long orgLocationId, User user, MultipartFile multiFile) throws JSONException, Exception
	{
		CandidateDocuments candidateDocuments = null;
		Candidate candidate = new Candidate();
		OrgLocations orgLocations = new OrgLocations();
		StringBuffer activitySummary = new StringBuffer();
		if (null != candidateId) {
			candidateDocuments = candidateDocumentsDao.getCandidateDocumentByCandidateId(candidateId, documentUUID);
			if (null != candidateDocuments) {
				if (jsonObj.has("name")) {
					if (jsonObj.getString("name").equalsIgnoreCase("Other")) {
						if(candidateDocuments != null && candidateDocuments.getDocumentType() != null && candidateDocuments.getDocumentType() != "" && !candidateDocuments.getDocumentType().equalsIgnoreCase(jsonObj.getString("name"))){
							activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("name")).append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentType(jsonObj.getString("name"));
						
						if(candidateDocuments != null && candidateDocuments.getDocumentOtherType() != null && candidateDocuments.getDocumentOtherType() != "" && !candidateDocuments.getDocumentOtherType().equalsIgnoreCase(jsonObj.getString("documentOtherType"))){
							activitySummary.append("Document Other Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentOtherType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("documentOtherType")).append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentOtherType(jsonObj.getString("documentOtherType"));
					}
					else {
						if(candidateDocuments != null && candidateDocuments.getDocumentType() != null && candidateDocuments.getDocumentType() != "" && !candidateDocuments.getDocumentType().equalsIgnoreCase(jsonObj.getString("name"))){
							activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("name")).append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentType(jsonObj.getString("name"));
						if(candidateDocuments != null && candidateDocuments.getDocumentOtherType() != null && candidateDocuments.getDocumentOtherType() != "" && !candidateDocuments.getDocumentOtherType().equalsIgnoreCase(jsonObj.getString("documentOtherType"))){
							activitySummary.append("Document Other Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentOtherType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append("").append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentOtherType(null);
					}
				}
				if (jsonObj.has("description")) {
					if(candidateDocuments != null && candidateDocuments.getDescription() != null && candidateDocuments.getDescription() != "" && !candidateDocuments.getDescription().equalsIgnoreCase(jsonObj.getString("description"))){
						activitySummary.append("Description").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getDescription()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("description")).append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setDescription(jsonObj.getString("description"));
				}
				// for professional user add is confidential Y/N
				if (jsonObj.has("isConfidential")
						&& !(jsonObj.getString("isConfidential").isEmpty())) {
					if(candidateDocuments != null && candidateDocuments.getIsConfidential() != null && candidateDocuments.getIsConfidential() != "" && !candidateDocuments.getIsConfidential().equalsIgnoreCase("Y")){
						activitySummary.append("Is Confidential").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getIsConfidential()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append("Y").append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setIsConfidential("Y");
				}
				else {
					if(candidateDocuments != null && candidateDocuments.getIsConfidential() != null && candidateDocuments.getIsConfidential() != "" && !candidateDocuments.getIsConfidential().equalsIgnoreCase("N")){
						activitySummary.append("Is Confidential").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getIsConfidential()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append("N").append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setIsConfidential("N");
				}
				candidateDocuments.setUpdatedBy(user.getUserCode());
				candidateDocuments.setUpdatedDate(commonUtil.currentDate());
				if (!jsonObj.getString("docFile").equalsIgnoreCase("")) {
					if (multiFile != null) {
						candidateDocuments.setDocFile(multiFile.getBytes());
						if(multiFile.getOriginalFilename() != null && multiFile.getOriginalFilename() != "" && !multiFile.getOriginalFilename().equalsIgnoreCase(candidateDocuments.getFileName())){
							activitySummary.append("File Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getFileName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(multiFile.getOriginalFilename()).append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setFileName(multiFile.getOriginalFilename());
						candidateDocuments.setFileContentType(multiFile.getContentType());
					}
				}
				
				candidate.setId(candidateDocuments.getCandidateId().getId());
				orgLocations.setId(candidateDocuments.getOrgLocationId().getId());
			}
			else {
				candidateDocuments = new CandidateDocuments();
				if (jsonObj.has("name")) {
					if (jsonObj.getString("name").equalsIgnoreCase("Other")) {
						if(candidateDocuments != null && candidateDocuments.getDocumentType() != null && candidateDocuments.getDocumentType() != "" && !candidateDocuments.getDocumentType().equalsIgnoreCase(jsonObj.getString("name"))){
							activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("name")).append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentType(jsonObj.getString("name"));
						if(candidateDocuments != null && candidateDocuments.getDocumentOtherType() != null && candidateDocuments.getDocumentOtherType() != "" && !candidateDocuments.getDocumentOtherType().equalsIgnoreCase(jsonObj.getString("documentOtherType"))){
							activitySummary.append("Document Other Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentOtherType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("documentOtherType")).append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentOtherType(jsonObj.getString("documentOtherType"));
					}
					else {
						if(candidateDocuments != null && candidateDocuments.getDocumentType() != null && candidateDocuments.getDocumentType() != "" && !candidateDocuments.getDocumentType().equalsIgnoreCase(jsonObj.getString("name"))){
							activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getDocumentType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("name")).append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentType(jsonObj.getString("name"));
					}
				}
				if (jsonObj.has("description")) {
					if(candidateDocuments != null && candidateDocuments.getDescription() != null && candidateDocuments.getDescription() != "" && !candidateDocuments.getDescription().equalsIgnoreCase(jsonObj.getString("description"))){
						activitySummary.append("Description").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getDescription()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("description")).append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setDescription(jsonObj.getString("description"));
				}
				// for professional user add is confidential Y/N
				if (jsonObj.has("isConfidential")
						&& !(jsonObj.getString("isConfidential").isEmpty())) {
					if(candidateDocuments != null && candidateDocuments.getIsConfidential() != null && candidateDocuments.getIsConfidential() != "" && !candidateDocuments.getIsConfidential().equalsIgnoreCase("Y")){
						activitySummary.append("Is Confidential").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getIsConfidential()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append("Y").append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setIsConfidential("Y");
				}
				else {
					if(candidateDocuments != null && candidateDocuments.getIsConfidential() != null && candidateDocuments.getIsConfidential() != "" && !candidateDocuments.getIsConfidential().equalsIgnoreCase("N")){
						activitySummary.append("Is Confidential").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateDocuments.getIsConfidential()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append("N").append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setIsConfidential("N");
				}
				candidateDocuments.setDocUploadStatus(Constants.DOCUMENT_UPLOAD_MANUALLY);
				candidate.setId(candidateId);
				candidateDocuments.setCandidateId(candidate);
				orgLocations.setId(orgLocationId);
				if (orgLocationId != null) {
					candidateDocuments.setOrgLocationId(orgLocations);
				}
				candidateDocuments.setStatus(Constants.CANDIDATE_ACTIVE);
				candidateDocuments.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				candidateDocuments.setCreatedDate(commonUtil.currentDate());
				candidateDocuments.setUpdatedBy(user.getUserCode());
				candidateDocuments.setCreatedBy(user.getUserCode());
				candidateDocuments.setUpdatedDate(commonUtil.currentDate());
				if (multiFile != null) {
					candidateDocuments.setDocFile(multiFile.getBytes());
					if(multiFile.getOriginalFilename() != null && multiFile.getOriginalFilename() != ""){
						activitySummary.append("File Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(multiFile.getOriginalFilename()).append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setFileName(multiFile.getOriginalFilename());
					candidateDocuments.setFileContentType(multiFile.getContentType());
				}
			}
			candidateDocuments = candidateDocumentsDao.updateCandidateDocuments(candidateDocuments);
			
			candidateActivityCommonUtil.storeCandidateDocumentsHistory(candidateDocuments, user);
			
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_DOCUMENTS_UPDATED, activitySummary.toString(), "CANDIDATE");
			
		}
		return candidateDocuments;
	}
	
	@Override
	public List<CandidateDocuments> getCandidateDocumentsListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception
	{
		return candidateDocumentsDao.getCandidateDocumentsListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
	}
	
	@Override
	public CandidateDocuments getCandidateDocumentByDocumentId(Long documentId) throws Exception
	{
		return candidateDocumentsDao.getCandidateDocumentByDocumentId(documentId);
	}
	
	@Override
	public CandidateDocuments getCandidateDocumentByDocumentUUID(String documentUUID) throws Exception
	{
		return candidateDocumentsDao.getCandidateDocumentByDocumentUUID(documentUUID);
	}
	
	@Override
	public CandidateDocuments updateCandidateDocuments(CandidateDocuments candidateDocuments) throws Exception
	{
		return candidateDocumentsDao.updateCandidateDocuments(candidateDocuments);
	}
	
	@Override
	public List<CandidateDocuments> getCandidateDocumentsListByRequestedDocumentsId(Long requestedDocumentsId) throws Exception
	{
		return candidateDocumentsDao.getCandidateDocumentsListByRequestedDocumentsId(requestedDocumentsId);
	}
	
	@Override
	public Boolean validateCandidateDocument(Long candidateId, String candidateDocument) throws Exception
	{
		return candidateDocumentsDao.validateCandidateDocument(candidateId, candidateDocument);
	}
}
