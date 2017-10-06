package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OnBoardingTemplateDao;
import com.bis.sourcelead.dao.OnBoardingTemplateDocumentsDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OnBoardingTemplateDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

import cucumber.deps.difflib.StringUtills;

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
	public void storeOnBoardTemplateDocuments(OnBoardingTemplateDTO onBoardingTemplateDTO, User loginUser) throws Exception {
		
			OnBoardingTemplate template = onBoardingTemplateDao.getOnBoardingTemplateById(Long.valueOf(onBoardingTemplateDTO.getTemplateId()));
			if(onBoardingTemplateDTO.getContractsFileInfo() != null &&  onBoardingTemplateDTO.getContractsFileInfo().size() > 0) {
				for(FileInfo fileInfo : onBoardingTemplateDTO.getContractsFileInfo()) {
					OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
					if(fileInfo != null && fileInfo.getFileURL() != null) {
						   documents.setFileName(fileInfo.getFileName());
						   documents.setFileContentType(fileInfo.getContentType());
						   documents.setFileURL(fileInfo.getFileURL());
					   }
					    documents.setOnboardingTemplateId(template);
					    documents.setDocumentType("Contracts");
	                    documents.setStatus(Constants.CANDIDATE_ACTIVE);
	                    documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	                    documents.setCreatedDate(CommonUtil.currentDate());
	                    documents.setCreatedBy(loginUser.getUserCode());
	                    documents.setUpdatedDate(CommonUtil.currentDate());
	                    documents.setUpdatedBy(loginUser.getUserCode());
	                    saveOnBoardingTemplateDocuments(documents);
	                    FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Contracts-Documents");
				}
			}
			if(onBoardingTemplateDTO.getFormsFileInfo() != null && onBoardingTemplateDTO.getFormsFileInfo().size() > 0) {
				for(FileInfo fileInfo : onBoardingTemplateDTO.getFormsFileInfo()) {
					OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
					if(fileInfo != null && fileInfo.getFileURL() != null) {
						   documents.setFileName(fileInfo.getFileName());
						   documents.setFileContentType(fileInfo.getContentType());
						   documents.setFileURL(fileInfo.getFileURL());
					   }
				        documents.setOnboardingTemplateId(template);
	                    documents.setDocumentType("Forms");
	                    documents.setStatus(Constants.CANDIDATE_ACTIVE);
	                    documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	                    documents.setCreatedDate(CommonUtil.currentDate());
	                    documents.setCreatedBy(loginUser.getUserCode());
	                    documents.setUpdatedDate(CommonUtil.currentDate());
	                    documents.setUpdatedBy(loginUser.getUserCode());
	                    saveOnBoardingTemplateDocuments(documents);
       					FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Forms-Documents");
				}
			}
			if(onBoardingTemplateDTO.getPolaciesFileInfo() != null && onBoardingTemplateDTO.getPolaciesFileInfo().size() > 0) {
				for(FileInfo fileInfo : onBoardingTemplateDTO.getPolaciesFileInfo()) {
					OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
					if(fileInfo != null && fileInfo.getFileURL() != null) {
						   documents.setFileName(fileInfo.getFileName());
						   documents.setFileContentType(fileInfo.getContentType());
						   documents.setFileURL(fileInfo.getFileURL());
					   }
	                    documents.setOnboardingTemplateId(template);
	                    documents.setDocumentType("Policies");
	                    documents.setStatus(Constants.CANDIDATE_ACTIVE);
	                    documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	                    documents.setCreatedDate(CommonUtil.currentDate());
	                    documents.setCreatedBy(loginUser.getUserCode());
	                    documents.setUpdatedDate(CommonUtil.currentDate());
	                    documents.setUpdatedBy(loginUser.getUserCode());
	                    saveOnBoardingTemplateDocuments(documents);
	   					FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Policies-Documetns");
				}
			}
			
			if(onBoardingTemplateDTO.getSupportingFileInfo() != null && onBoardingTemplateDTO.getSupportingFileInfo().size() > 0) {
				for(FileInfo fileInfo : onBoardingTemplateDTO.getSupportingFileInfo()) {
					OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
					 if(fileInfo != null && fileInfo.getFileURL() != null) {
						   documents.setFileName(fileInfo.getFileName());
						   documents.setFileContentType(fileInfo.getContentType());
						   documents.setFileURL(fileInfo.getFileURL());
					   }
				         documents.setOnboardingTemplateId(template);
	                     documents.setDocumentType("Supported");
	                     documents.setStatus(Constants.CANDIDATE_ACTIVE);
	                     documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	                     documents.setCreatedDate(CommonUtil.currentDate());
	                     documents.setCreatedBy(loginUser.getUserCode());
	                     documents.setUpdatedDate(CommonUtil.currentDate());
	                     documents.setUpdatedBy(loginUser.getUserCode());
	                     saveOnBoardingTemplateDocuments(documents);
		      			 FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Supporting-Documents");
				}
			}
			/*for(FileInfo fileInfo : onBoardingTemplateDTO.getFileInfo()) {
				OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
				if(onBoardingTemplateDTO.getContractFileName() != null && onBoardingTemplateDTO.getContractFileName().contains(fileInfo.getFileName())) {
					   if(fileInfo != null && fileInfo.getFileURL() != null) {
						   documents.setFileName(fileInfo.getFileName());
						   documents.setFileContentType(fileInfo.getContentType());
						   documents.setFileURL(fileInfo.getFileURL());
					   }
					   documents.setOnboardingTemplateId(template);
					   documents.setDocumentType("Contracts");
                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
                       documents.setCreatedDate(CommonUtil.currentDate());
                       documents.setCreatedBy(loginUser.getUserCode());
                       documents.setUpdatedDate(CommonUtil.currentDate());
                       documents.setUpdatedBy(loginUser.getUserCode());
                       saveOnBoardingTemplateDocuments(documents);
                       FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Contracts-Documents");
				}
				if(onBoardingTemplateDTO.getFormFileName() != null && onBoardingTemplateDTO.getFormFileName().contains(fileInfo.getFileName())) {
						   if(fileInfo != null && fileInfo.getFileURL() != null) {
							   documents.setFileName(fileInfo.getFileName());
							   documents.setFileContentType(fileInfo.getContentType());
							   documents.setFileURL(fileInfo.getFileURL());
						   }
					       documents.setOnboardingTemplateId(template);
	                       documents.setDocumentType("Forms");
	                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
	                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	                       documents.setCreatedDate(CommonUtil.currentDate());
	                       documents.setCreatedBy(loginUser.getUserCode());
	                       documents.setUpdatedDate(CommonUtil.currentDate());
	                       documents.setUpdatedBy(loginUser.getUserCode());
	                       saveOnBoardingTemplateDocuments(documents);
	          					 FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Forms-Documents");
				}
				if(onBoardingTemplateDTO.getPoliciesFileName() != null && onBoardingTemplateDTO.getPoliciesFileName().contains(fileInfo.getFileName())) {
						   if(fileInfo != null && fileInfo.getFileURL() != null) {
							   documents.setFileName(fileInfo.getFileName());
							   documents.setFileContentType(fileInfo.getContentType());
							   documents.setFileURL(fileInfo.getFileURL());
						   }
	                       documents.setOnboardingTemplateId(template);
	                       documents.setDocumentType("Policies");
	                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
	                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	                       documents.setCreatedDate(CommonUtil.currentDate());
	                       documents.setCreatedBy(loginUser.getUserCode());
	                       documents.setUpdatedDate(CommonUtil.currentDate());
	                       documents.setUpdatedBy(loginUser.getUserCode());
	                       saveOnBoardingTemplateDocuments(documents);
	      					 FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Policies-Documetns");
				}
				if(onBoardingTemplateDTO.getSupportingFileName() != null && onBoardingTemplateDTO.getSupportingFileName().contains(fileInfo.getFileName())){
						   if(fileInfo != null && fileInfo.getFileURL() != null) {
							   documents.setFileName(fileInfo.getFileName());
							   documents.setFileContentType(fileInfo.getContentType());
							   documents.setFileURL(fileInfo.getFileURL());
						   }
					       documents.setOnboardingTemplateId(template);
	                       documents.setDocumentType("Supported");
	                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
	                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	                       documents.setCreatedDate(CommonUtil.currentDate());
	                       documents.setCreatedBy(loginUser.getUserCode());
	                       documents.setUpdatedDate(CommonUtil.currentDate());
	                       documents.setUpdatedBy(loginUser.getUserCode());
	                       saveOnBoardingTemplateDocuments(documents);
		      					 FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Supporting-Documents");
				}
				
			}*/
			
			
	    	/*for (int a = 0; a < contractsAttcahmentMaxRowNumberCount; a++) {
	    		 FileInfo fileInfo = null;
				   if(onBoardingTemplateDTO.getUploadedFileInfo() != null && !onBoardingTemplateDTO.getUploadedFileInfo().isEmpty()){
					    fileInfo = onBoardingTemplateDTO.getUploadedFileInfo().get(0);
					   
					   if(fileInfo != null && fileInfo.getFileURL() != null) {
						   documents.setFileName(fileInfo.getFileName());
						   documents.setFileContentType(fileInfo.getContentType());
						   documents.setFileURL(fileInfo.getFileURL());
					   }
				   }
				   documents.setOnboardingTemplateId(template);
				   
				   final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) fileInfo; 
       		       final Map files = multiRequest.getFileMap();
       		       
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
                       if(fileInfo != null){
          					FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Contracts-Documents");
          				 }
					   
                   }*/
                 
		
		 /* //save  mutliple attachment for forms Documents.
				if (StringUtils.isNotBlank(onBoardingTemplateDTO.getFormsAttcahmentMaxRowNumber())){
			    	String formsAttcahmentMaxRowNumber = onBoardingTemplateDTO.getFormsAttcahmentMaxRowNumber();
					int formsAttcahmentMaxRowNumberCount = Integer.parseInt(formsAttcahmentMaxRowNumber);
					
					OnBoardingTemplate template = onBoardingTemplateDao.getOnBoardingTemplateById(Long.valueOf(onBoardingTemplateDTO.getTemplateId()));
					
			    	for (int a = 0; a < formsAttcahmentMaxRowNumberCount; a++) {
						   
						   OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
						   FileInfo fileInfo = null;
						   if(onBoardingTemplateDTO.getUploadedFileInfo() != null && !onBoardingTemplateDTO.getUploadedFileInfo().isEmpty()){
							    fileInfo = onBoardingTemplateDTO.getUploadedFileInfo().get(a);
							   if(fileInfo != null && fileInfo.getFileURL() != null) {
								   documents.setFileName(fileInfo.getFileName());
								   documents.setFileContentType(fileInfo.getContentType());
								   documents.setFileURL(fileInfo.getFileURL());
							   }
						   }
						   
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
						       documents.setOnboardingTemplateId(template);
		                       documents.setDocumentType("Forms");
		                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
		                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		                       documents.setCreatedDate(CommonUtil.currentDate());
		                       documents.setCreatedBy(loginUser.getUserCode());
		                       documents.setUpdatedDate(CommonUtil.currentDate());
		                       documents.setUpdatedBy(loginUser.getUserCode());
		                       saveOnBoardingTemplateDocuments(documents);
		                       if(fileInfo != null){
		          					 FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Forms-Documents");
	          				   } 
		                   }
		                 
			    	}
		
		
		//save  mutliple attachment for policies Documents.
		if (StringUtils.isNotBlank(onBoardingTemplateDTO.getPoliciesAttcahmentMaxRowNumber())){
	    	String policiesAttcahmentMaxRowNumber = onBoardingTemplateDTO.getPoliciesAttcahmentMaxRowNumber();
			int policiesAttcahmentMaxRowNumberCount = Integer.parseInt(policiesAttcahmentMaxRowNumber);
			
			OnBoardingTemplate template = onBoardingTemplateDao.getOnBoardingTemplateById(Long.valueOf(onBoardingTemplateDTO.getTemplateId()));
			
	    	for (int a = 0; a < policiesAttcahmentMaxRowNumberCount; a++) {
				   
				   OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
				   FileInfo fileInfo = null;
				   if(onBoardingTemplateDTO.getUploadedFileInfo() != null && !onBoardingTemplateDTO.getUploadedFileInfo().isEmpty()){
					    fileInfo = onBoardingTemplateDTO.getUploadedFileInfo().get(a);
					   if(fileInfo != null && fileInfo.getFileURL() != null) {
						   documents.setFileName(fileInfo.getFileName());
						   documents.setFileContentType(fileInfo.getContentType());
						   documents.setFileURL(fileInfo.getFileURL());
					   }
				   }
				   
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
                       documents.setOnboardingTemplateId(template);
                       documents.setDocumentType("Policies");
                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
                       documents.setCreatedDate(CommonUtil.currentDate());
                       documents.setCreatedBy(loginUser.getUserCode());
                       documents.setUpdatedDate(CommonUtil.currentDate());
                       documents.setUpdatedBy(loginUser.getUserCode());
                       saveOnBoardingTemplateDocuments(documents);
                       if(fileInfo != null){
      					 FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Policies-Documetns");
      				   }
                   }
                   
                 
	    	}
		
		//save  mutliple attachment for supported Documents.
				if (StringUtils.isNotBlank(onBoardingTemplateDTO.getSupportingAttcahmentMaxRowNumber())){
			    	String supportingAttcahmentMaxRowNumber = onBoardingTemplateDTO.getSupportingAttcahmentMaxRowNumber();
					int supportedAttcahmentMaxRowNumberCount = Integer.parseInt(supportingAttcahmentMaxRowNumber);
					
					OnBoardingTemplate template = onBoardingTemplateDao.getOnBoardingTemplateById(Long.valueOf(onBoardingTemplateDTO.getTemplateId()));
					
			    	for (int a = 0; a < supportedAttcahmentMaxRowNumberCount; a++) {
						   
						   OnBoardingTemplateDocuments documents = new OnBoardingTemplateDocuments();
						   FileInfo fileInfo = null;
						   if(onBoardingTemplateDTO.getUploadedFileInfo() != null && !onBoardingTemplateDTO.getUploadedFileInfo().isEmpty()){
							    fileInfo = onBoardingTemplateDTO.getUploadedFileInfo().get(a);
							   if(fileInfo != null && fileInfo.getFileURL() != null) {
								   documents.setFileName(fileInfo.getFileName());
								   documents.setFileContentType(fileInfo.getContentType());
								   documents.setFileURL(fileInfo.getFileURL());
							   }
						   }
						   
						   final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
		       		       final Map files = multiRequest.getFileMap();
		       		       
		       		      
		                   MultipartFile  multiFile = (MultipartFile)files.get(a+"supportingdocFile");
		                   if(multiFile != null && !multiFile.isEmpty()){
		                	   documents.setDocFile(multiFile.getBytes());
		                	   documents.setFileName(multiFile.getOriginalFilename());
		                       
		                       if(multiFile.getContentType()!=null){
		                    	   documents.setFileContentType(multiFile.getContentType());
		                       }
						       documents.setOnboardingTemplateId(template);
		                       documents.setDocumentType("Supported");
		                       documents.setStatus(Constants.CANDIDATE_ACTIVE);
		                       documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		                       documents.setCreatedDate(CommonUtil.currentDate());
		                       documents.setCreatedBy(loginUser.getUserCode());
		                       documents.setUpdatedDate(CommonUtil.currentDate());
		                       documents.setUpdatedBy(loginUser.getUserCode());
		                       saveOnBoardingTemplateDocuments(documents);
		                       if(fileInfo != null){
			      					 FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Onboard-Template-Supporting-Documents");
		      				   }
		                   }
			    	}*/
	    }

	@Override
	public List<Long> getOnBoardingTemplateDocumentIdsByCandidateOnBoardingId(Long candidateOnBoardingId,String documentType) throws Exception {
		return onBoardingTemplateDocumentsDao.getOnBoardingTemplateDocumentIdsByCandidateOnBoardingId(candidateOnBoardingId, documentType);
	}

	
	
	
}
