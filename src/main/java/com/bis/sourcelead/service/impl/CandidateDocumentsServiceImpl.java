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

	@Override
	public CandidateDocuments storeCandidateDocuments(JSONObject jsonObj,Long candidateId, String documentUUID, Long orgLocationId,User user,
			MultipartFile multiFile) throws JSONException, Exception {
		
		CandidateDocuments candidateDocuments = null;
		if(null != candidateId){
		
			candidateDocuments = candidateDocumentsDao.getCandidateDocumentByCandidateId(candidateId,documentUUID);
			 if(null != candidateDocuments){
				if(jsonObj.has("name")){
					/*candidateDocuments.setDocumentType(jsonObj.getString("name"));*/
					if(jsonObj.getString("name").equalsIgnoreCase("Other")){
						candidateDocuments.setDocumentType(jsonObj.getString("name"));
						candidateDocuments.setDocumentOtherType(jsonObj.getString("documentOtherType"));
					}else{
					candidateDocuments.setDocumentType(jsonObj.getString("name"));
					candidateDocuments.setDocumentOtherType(null);
					}
				}
				
				if(jsonObj.has("description")){
					candidateDocuments.setDescription(jsonObj.getString("description"));
				}
				
				// for professional user add is confidential Y/N
				if(jsonObj.has("isConfidential") && !(jsonObj.getString("isConfidential").isEmpty())){
					candidateDocuments.setIsConfidential("Y");
	        		
	        	}else{
	        		candidateDocuments.setIsConfidential("N");
	        	}
				
				candidateDocuments.setUpdatedBy(user.getUserCode());
				candidateDocuments.setUpdatedDate(commonUtil.currentDate());
				if(!jsonObj.getString("docFile").equalsIgnoreCase("")){
					if(multiFile != null){
						candidateDocuments.setDocFile(multiFile.getBytes());
						candidateDocuments.setFileName(multiFile.getOriginalFilename());
						candidateDocuments.setFileContentType(multiFile.getContentType());
					 }
				}
			}else{
				candidateDocuments = new CandidateDocuments();
				if(jsonObj.has("name")){
					if(jsonObj.getString("name").equalsIgnoreCase("Other")){
						candidateDocuments.setDocumentType(jsonObj.getString("name"));
						candidateDocuments.setDocumentOtherType(jsonObj.getString("documentOtherType"));
					}else{
					candidateDocuments.setDocumentType(jsonObj.getString("name"));
					}
				}
				if(jsonObj.has("description")){
					candidateDocuments.setDescription(jsonObj.getString("description"));
				}
				
				// for professional user add is confidential Y/N
				if(jsonObj.has("isConfidential") && !(jsonObj.getString("isConfidential").isEmpty())){
					candidateDocuments.setIsConfidential("Y");
	        		
	        	}else{
	        		candidateDocuments.setIsConfidential("N");
	        	}
				
				candidateDocuments.setDocUploadStatus(Constants.DOCUMENT_UPLOAD_MANUALLY);
				
				Candidate candidate = new Candidate();
				candidate.setId(candidateId);
				candidateDocuments.setCandidateId(candidate);
				
				OrgLocations orgLocations = new OrgLocations();
				orgLocations.setId(orgLocationId);
				if(orgLocationId != null){
					candidateDocuments.setOrgLocationId(orgLocations);
				}
				
				candidateDocuments.setStatus(Constants.CANDIDATE_ACTIVE);
				candidateDocuments.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				candidateDocuments.setCreatedDate(commonUtil.currentDate());
				candidateDocuments.setUpdatedBy(user.getUserCode());
				candidateDocuments.setCreatedBy(user.getUserCode());
				candidateDocuments.setUpdatedDate(commonUtil.currentDate());
				if(multiFile != null){
					candidateDocuments.setDocFile(multiFile.getBytes());
					candidateDocuments.setFileName(multiFile.getOriginalFilename());
					candidateDocuments.setFileContentType(multiFile.getContentType());
				 }
			
		}
			 candidateDocumentsDao.updateCandidateDocuments(candidateDocuments); 
		}
		return candidateDocuments;
	
	}
	@Override
	public List<CandidateDocuments> getCandidateDocumentsListByCandidateIdAndOrgLocationID(Long candidateId,
			Long orgLocationId) throws Exception {
		return candidateDocumentsDao.getCandidateDocumentsListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
	}
	
	@Override
	public CandidateDocuments getCandidateDocumentByDocumentId(Long documentId) throws Exception {
		return candidateDocumentsDao.getCandidateDocumentByDocumentId(documentId);
	}
	
	@Override
	public CandidateDocuments getCandidateDocumentByDocumentUUID(String documentUUID) throws Exception {
		return candidateDocumentsDao.getCandidateDocumentByDocumentUUID(documentUUID);
	}
	
	@Override
	public CandidateDocuments updateCandidateDocuments(CandidateDocuments candidateDocuments) throws Exception {
		return candidateDocumentsDao.updateCandidateDocuments(candidateDocuments);
	}
	@Override
	public List<CandidateDocuments> getCandidateDocumentsListByRequestedDocumentsId(Long requestedDocumentsId) throws Exception
	{
		return candidateDocumentsDao.getCandidateDocumentsListByRequestedDocumentsId(requestedDocumentsId);
	}
	
	

}
