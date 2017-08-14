package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OnBoardingTemplateDao;
import com.bis.sourcelead.dao.OnBoardingTemplateDocumentsDao;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OnBoardingTemplateDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class OnBoardingTemplateDocumentsServiceImpl implements OnBoardingTemplateDocumentsService {

	@Autowired
	OnBoardingTemplateDocumentsDao onBoardingTemplateDocumentsDao;
	
	@Autowired
	OnBoardingTemplateDao onBoardingTemplateDao;
	
	
	
	@Override
	public OnBoardingTemplateDocuments saveOnBoardingTemplateDocuments(
			OnBoardingTemplateDocuments onBoardingTemplateDocuments) throws Exception {
		return onBoardingTemplateDocumentsDao.saveOnBoardingTemplateDocuments(onBoardingTemplateDocuments);
	}

	@Override
	public List<OnBoardingTemplateDocuments> getOnBoardingTemplateDocumentsByTemplateId(Long templateId,String documentType)
			throws Exception {
		return onBoardingTemplateDocumentsDao.getOnBoardingTemplateDocumentsByTemplateId(templateId,documentType);
	}

	@Override
	public OnBoardingTemplateDocuments getOnBoardingTemplateDocumentsBydocumentUUID(String documentUUID)
			throws Exception {
		return onBoardingTemplateDocumentsDao.getOnBoardingTemplateDocumentsBydocumentUUID(documentUUID);
	}

	@Override
	public List<OnBoardingTemplateDocuments> getCandidateOnBoardingTemplateDocumentsByTemplateId(List<Long> templateIds,
			String documentType) throws Exception {
		return onBoardingTemplateDocumentsDao.getCandidateOnBoardingTemplateDocumentsByTemplateId(templateIds, documentType);
	}

	@Override
	public OnBoardingTemplateDocuments getOnBoardingTemplateDocumentsById(Long Id) throws Exception {
		return onBoardingTemplateDocumentsDao.getOnBoardingTemplateDocumentsById(Id);
	}

	@Override
	public Integer getCandidateOnboardingDocumentsByCandidateOnboardingId(Long candidateOnBoardingId) throws Exception {
		return onBoardingTemplateDocumentsDao.getCandidateOnboardingDocumentsByCandidateOnboardingId(candidateOnBoardingId);
	}

	@Override
	public List<OnBoardingTemplateDocuments> getOnBoardingTemplateDocumentsByCandidateOnBoardingId(
			Long candidateOnBoardingId, String documentType) throws Exception {
		return onBoardingTemplateDocumentsDao.getOnBoardingTemplateDocumentsByCandidateOnBoardingId(candidateOnBoardingId, documentType);
	}

	@Override
	public Integer getCandidateOnboardingDocumentsCountByCandidateOnboardingIdExpectSupportDocs(
			Long candidateOnBoardingId) throws Exception {
		return onBoardingTemplateDocumentsDao.getCandidateOnboardingDocumentsCountByCandidateOnboardingIdExpectSupportDocs(candidateOnBoardingId);
	}

	@Override
	public Integer getCandidateAcceptedOnboardingDocumentsCountByCandidateOnboardingId(Long candidateOnBoardingId)
			throws Exception {
		return onBoardingTemplateDocumentsDao.getCandidateAcceptedOnboardingDocumentsCountByCandidateOnboardingId(candidateOnBoardingId);
	}

	@Override
	public void storeOnBoardTemplateDocuments(JSONObject jsonObj, User loginUser, MultipartHttpServletRequest request) throws Exception {
		
		//save  mutliple attachment for contracts Documents.
		if ((jsonObj.has("contractsAttcahmentMaxRowNumber"))){
	    	String contractsAttcahmentMaxRowNumber = jsonObj.getString("contractsAttcahmentMaxRowNumber");
			int contractsAttcahmentMaxRowNumberCount = Integer.parseInt(contractsAttcahmentMaxRowNumber);
			
			OnBoardingTemplate template = onBoardingTemplateDao.getOnBoardingTemplateById(Long.valueOf(jsonObj.getString("templateId")));
			
	    	for (int a = 0; a < contractsAttcahmentMaxRowNumberCount; a++) {
				   
				   OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
				   final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
       		       final Map files = multiRequest.getFileMap();
       		       
       		       documents.setOnboardingTemplateId(template);
                   MultipartFile  multiFile = (MultipartFile)files.get(a+"contractsdocFile");
                   if(multiFile != null && !multiFile.isEmpty()){
                	   documents.setDocFile(multiFile.getBytes());
                	   documents.setFileName(multiFile.getOriginalFilename());
                       
                       if(multiFile.getContentType()!=null){
                    	   documents.setFileContentType(multiFile.getContentType());
                       }
                       documents.setDocumentType("Contracts");
                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
                       documents.setCreatedDate(CommonUtil.currentDate());
                       documents.setCreatedBy(loginUser.getUserCode());
                       documents.setUpdatedDate(CommonUtil.currentDate());
                       documents.setUpdatedBy(loginUser.getUserCode());
                       saveOnBoardingTemplateDocuments(documents);
					   
                   }
                 
	    	}
		}
		
		//save  mutliple attachment for forms Documents.
				if ((jsonObj.has("formsAttcahmentMaxRowNumber"))){
			    	String formsAttcahmentMaxRowNumber = jsonObj.getString("formsAttcahmentMaxRowNumber");
					int formsAttcahmentMaxRowNumberCount = Integer.parseInt(formsAttcahmentMaxRowNumber);
					
					OnBoardingTemplate template = onBoardingTemplateDao.getOnBoardingTemplateById(Long.valueOf(jsonObj.getString("templateId")));
					
			    	for (int a = 0; a < formsAttcahmentMaxRowNumberCount; a++) {
						   
						   OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
						   final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
		       		       final Map files = multiRequest.getFileMap();
		       		       
		       		       documents.setOnboardingTemplateId(template);
		                   MultipartFile  multiFile = (MultipartFile)files.get(a+"formsdocFile");
		                   if(multiFile != null && !multiFile.isEmpty()){
		                	   documents.setDocFile(multiFile.getBytes());
		                	   documents.setFileName(multiFile.getOriginalFilename());
		                       
		                       if(multiFile.getContentType()!=null){
		                    	   documents.setFileContentType(multiFile.getContentType());
		                       }
		                       documents.setDocumentType("Forms");
		                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
		                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		                       documents.setCreatedDate(CommonUtil.currentDate());
		                       documents.setCreatedBy(loginUser.getUserCode());
		                       documents.setUpdatedDate(CommonUtil.currentDate());
		                       documents.setUpdatedBy(loginUser.getUserCode());
		                       saveOnBoardingTemplateDocuments(documents);
							   
		                   }
		                 
			    	}
				}
		
		
		//save  mutliple attachment for policies Documents.
		if ((jsonObj.has("policiesAttcahmentMaxRowNumber"))){
	    	String policiesAttcahmentMaxRowNumber = jsonObj.getString("policiesAttcahmentMaxRowNumber");
			int policiesAttcahmentMaxRowNumberCount = Integer.parseInt(policiesAttcahmentMaxRowNumber);
			
			OnBoardingTemplate template = onBoardingTemplateDao.getOnBoardingTemplateById(Long.valueOf(jsonObj.getString("templateId")));
			
	    	for (int a = 0; a < policiesAttcahmentMaxRowNumberCount; a++) {
				   
				   OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
				   final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
       		       final Map files = multiRequest.getFileMap();
       		       
       		       documents.setOnboardingTemplateId(template);
                   MultipartFile  multiFile = (MultipartFile)files.get(a+"docFile");
                   if(multiFile != null && !multiFile.isEmpty()){
                	   documents.setDocFile(multiFile.getBytes());
                	   documents.setFileName(multiFile.getOriginalFilename());
                       
                       if(multiFile.getContentType()!=null){
                    	   documents.setFileContentType(multiFile.getContentType());
                       }
                       documents.setDocumentType("Policies");
                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
                       documents.setCreatedDate(CommonUtil.currentDate());
                       documents.setCreatedBy(loginUser.getUserCode());
                       documents.setUpdatedDate(CommonUtil.currentDate());
                       documents.setUpdatedBy(loginUser.getUserCode());
                       saveOnBoardingTemplateDocuments(documents);
					   
                   }
                   
                 
	    	}
		}
		
		//save  mutliple attachment for supported Documents.
				if ((jsonObj.has("supportingAttcahmentMaxRowNumber"))){
			    	String supportingAttcahmentMaxRowNumber = jsonObj.getString("supportingAttcahmentMaxRowNumber");
					int supportedAttcahmentMaxRowNumberCount = Integer.parseInt(supportingAttcahmentMaxRowNumber);
					
					OnBoardingTemplate template = onBoardingTemplateDao.getOnBoardingTemplateById(Long.valueOf(jsonObj.getString("templateId")));
					
			    	for (int a = 0; a < supportedAttcahmentMaxRowNumberCount; a++) {
						   
						   OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
						   final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
		       		       final Map files = multiRequest.getFileMap();
		       		       
		       		       documents.setOnboardingTemplateId(template);
		                   MultipartFile  multiFile = (MultipartFile)files.get(a+"supportingdocFile");
		                   if(multiFile != null && !multiFile.isEmpty()){
		                	   documents.setDocFile(multiFile.getBytes());
		                	   documents.setFileName(multiFile.getOriginalFilename());
		                       
		                       if(multiFile.getContentType()!=null){
		                    	   documents.setFileContentType(multiFile.getContentType());
		                       }
		                       documents.setDocumentType("Supported");
		                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
		                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		                       documents.setCreatedDate(CommonUtil.currentDate());
		                       documents.setCreatedBy(loginUser.getUserCode());
		                       documents.setUpdatedDate(CommonUtil.currentDate());
		                       documents.setUpdatedBy(loginUser.getUserCode());
		                       saveOnBoardingTemplateDocuments(documents);
							   
		                   }
			    	}
				}
	}

	
	
	
}
