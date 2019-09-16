package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDocumentsDao;
import com.bis.sourcelead.dto.CandidateDocumentsDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class CandidateDocumentsServiceImpl implements CandidateDocumentsService {
	private static final Logger logger = LoggerFactory.getLogger(CandidateDocumentsServiceImpl.class);
	@Autowired
	private CandidateDocumentsDao candidateDocumentsDao;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;

	@Autowired
	CandidateActivityCommonUtil candidateActivityCommonUtil;
	
	@Autowired
	CandidateIndexHelper candidateIndexHelper;

	@Override
	public CandidateDocuments storeCandidateDocuments(CandidateDocumentsDTO candidateDocumentsDTO, Long candidateId, String documentUUID,
			Long orgLocationId, User user) throws Exception {
		CandidateDocuments candidateDocuments = null;
		Candidate candidate = new Candidate();
		OrgLocations orgLocations = new OrgLocations();
		StringBuffer activitySummary = new StringBuffer();
		if (null != candidateId) {
			candidateDocuments = candidateDocumentsDao.getCandidateDocumentByCandidateId(candidateId, documentUUID);
			if (null != candidateDocuments) {
				if (StringUtils.isNotBlank(candidateDocumentsDTO.getName())) {
					if (candidateDocumentsDTO.getName().equalsIgnoreCase("Other")) {
						if (candidateDocuments != null && candidateDocuments.getDocumentType() != null
								&& candidateDocuments.getDocumentType() != ""
								&& !candidateDocuments.getDocumentType().equalsIgnoreCase(candidateDocumentsDTO.getName())) {
							activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
									.append(candidateDocuments.getDocumentType()).append(Constants.OLD_VALUE_TAG_END).append(" ")
									.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocumentsDTO.getName())
									.append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentType(candidateDocumentsDTO.getName());

						if (candidateDocuments != null && candidateDocuments.getDocumentOtherType() != null
								&& candidateDocuments.getDocumentOtherType() != "" && !candidateDocuments.getDocumentOtherType()
										.equalsIgnoreCase(candidateDocumentsDTO.getDocumentOtherType())) {
							activitySummary.append("Document Other Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
									.append(candidateDocuments.getDocumentOtherType()).append(Constants.OLD_VALUE_TAG_END).append(" ")
									.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocumentsDTO.getDocumentOtherType())
									.append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentOtherType(candidateDocumentsDTO.getDocumentOtherType());
					} else {
						if (candidateDocuments != null && candidateDocuments.getDocumentType() != null
								&& candidateDocuments.getDocumentType() != ""
								&& !candidateDocuments.getDocumentType().equalsIgnoreCase(candidateDocumentsDTO.getName())) {
							activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
									.append(candidateDocuments.getDocumentType()).append(Constants.OLD_VALUE_TAG_END).append(" ")
									.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocumentsDTO.getName())
									.append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						//candidateDocuments.setRequestedDocStatus(Constants.REQUESTED_DOCUMENT_SUBMITTED);
						candidateDocuments.setDocumentType(candidateDocumentsDTO.getName());
						if (candidateDocuments != null && candidateDocuments.getDocumentOtherType() != null
								&& candidateDocuments.getDocumentOtherType() != "" && !candidateDocuments.getDocumentOtherType()
										.equalsIgnoreCase(candidateDocumentsDTO.getDocumentOtherType())) {
							activitySummary.append("Document Other Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
									.append(candidateDocuments.getDocumentOtherType()).append(Constants.OLD_VALUE_TAG_END).append(" ")
									.append(Constants.NEW_VALUE_TAG_INITIATION).append("").append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentOtherType(null);
					}
				}
				if (StringUtils.isNotBlank(candidateDocumentsDTO.getDescription())) {
					if (candidateDocuments != null && candidateDocuments.getDescription() != null
							&& candidateDocuments.getDescription() != ""
							&& !candidateDocuments.getDescription().equalsIgnoreCase(candidateDocumentsDTO.getDescription())) {
						activitySummary.append("Description").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
								.append(candidateDocuments.getDescription()).append(Constants.OLD_VALUE_TAG_END).append(" ")
								.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocumentsDTO.getDescription())
								.append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setDescription(candidateDocumentsDTO.getDescription());
				} else {
					if (candidateDocuments != null && candidateDocuments.getDescription() != null
							&& candidateDocuments.getDescription() != ""
							&& !candidateDocuments.getDescription().equalsIgnoreCase(candidateDocumentsDTO.getDescription())) {
						activitySummary.append("Description").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
								.append(candidateDocuments.getDescription()).append(Constants.OLD_VALUE_TAG_END).append(" ")
								.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocumentsDTO.getDescription())
								.append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setDescription(null);
				}
				// for professional user add is confidential Y/N
				if (StringUtils.isNotBlank(candidateDocumentsDTO.getIsConfidential())
						&& !(candidateDocumentsDTO.getIsConfidential().isEmpty())) {
					if (candidateDocuments != null && candidateDocuments.getIsConfidential() != null
							&& candidateDocuments.getIsConfidential() != ""
							&& !candidateDocuments.getIsConfidential().equalsIgnoreCase("Y")) {
						activitySummary.append("Is Confidential").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
								.append(candidateDocuments.getIsConfidential()).append(Constants.OLD_VALUE_TAG_END).append(" ")
								.append(Constants.NEW_VALUE_TAG_INITIATION).append("Y").append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setIsConfidential("Y");
				} else {
					if (candidateDocuments != null && candidateDocuments.getIsConfidential() != null
							&& candidateDocuments.getIsConfidential() != ""
							&& !candidateDocuments.getIsConfidential().equalsIgnoreCase("N")) {
						activitySummary.append("Is Confidential").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
								.append(candidateDocuments.getIsConfidential()).append(Constants.OLD_VALUE_TAG_END).append(" ")
								.append(Constants.NEW_VALUE_TAG_INITIATION).append("N").append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setIsConfidential("N");
				}
				candidateDocuments.setUpdatedBy(user.getUserCode());
				candidateDocuments.setUpdatedDate(commonUtil.currentDate());

				if (candidateDocumentsDTO.getFileInfo() != null && candidateDocumentsDTO.getFileInfo().size() > 0) {
					FileInfo fileInfo = candidateDocumentsDTO.getFileInfo().get(0);
					if (fileInfo != null) {
						if (fileInfo.getFileName() != null) {
							activitySummary.append("Document").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
									.append(candidateDocuments.getFileName()).append(Constants.OLD_VALUE_TAG_END).append(" ")
									.append(Constants.NEW_VALUE_TAG_INITIATION).append(fileInfo.getFileName())
									.append(Constants.NEW_VALUE_TAG_END).append(" ");
						}

						candidateDocuments.setFileName(fileInfo.getFileName());
						candidateDocuments.setFileContentType(fileInfo.getContentType());

						candidateDocuments.setFileURL(fileInfo.getFileURL());
						;
						// candidate.setFileName(fileInfo.getFileName());
						try {
							FileUploadUtil.moveFile(candidateDocumentsDTO.getStaticPath(), fileInfo, "candidateDocuments");
						} catch (Exception e) {
							
						}
						
					}
				}

				candidate.setId(candidateDocuments.getCandidateId().getId());
				orgLocations.setId(candidateDocuments.getOrgLocationId().getId());
			} else {
				candidateDocuments = new CandidateDocuments();
				if (StringUtils.isNotBlank(candidateDocumentsDTO.getName())) {
					if (candidateDocumentsDTO.getName().equalsIgnoreCase("Other")) {
						if (candidateDocuments != null && candidateDocuments.getDocumentType() != null
								&& candidateDocuments.getDocumentType() != ""
								&& !candidateDocuments.getDocumentType().equalsIgnoreCase(candidateDocumentsDTO.getName())) {
							activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
									.append(candidateDocuments.getDocumentType()).append(Constants.OLD_VALUE_TAG_END).append(" ")
									.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocumentsDTO.getName())
									.append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentType(candidateDocumentsDTO.getName());
						if (candidateDocuments != null && candidateDocuments.getDocumentOtherType() != null
								&& candidateDocuments.getDocumentOtherType() != "" && !candidateDocuments.getDocumentOtherType()
										.equalsIgnoreCase(candidateDocumentsDTO.getDocumentOtherType())) {
							activitySummary.append("Document Other Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
									.append(candidateDocuments.getDocumentOtherType()).append(Constants.OLD_VALUE_TAG_END).append(" ")
									.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocumentsDTO.getDocumentOtherType())
									.append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentOtherType(candidateDocumentsDTO.getDocumentOtherType());
					} else {
						if (candidateDocuments != null && candidateDocuments.getDocumentType() != null
								&& candidateDocuments.getDocumentType() != ""
								&& !candidateDocuments.getDocumentType().equalsIgnoreCase(candidateDocumentsDTO.getName())) {
							activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
									.append(candidateDocuments.getDocumentType()).append(Constants.OLD_VALUE_TAG_END).append(" ")
									.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocumentsDTO.getName())
									.append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidateDocuments.setDocumentType(candidateDocumentsDTO.getName());
					}
				}
				if (StringUtils.isNotBlank(candidateDocumentsDTO.getDescription())) {
					if (candidateDocuments != null && candidateDocuments.getDescription() != null
							&& candidateDocuments.getDescription() != ""
							&& !candidateDocuments.getDescription().equalsIgnoreCase(candidateDocumentsDTO.getDescription())) {
						activitySummary.append("Description").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
								.append(candidateDocuments.getDescription()).append(Constants.OLD_VALUE_TAG_END).append(" ")
								.append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateDocumentsDTO.getDescription())
								.append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setDescription(candidateDocumentsDTO.getDescription());
				}
				// for professional user add is confidential Y/N
				if (StringUtils.isNotBlank(candidateDocumentsDTO.getIsConfidential())) {
					if (candidateDocuments != null && candidateDocuments.getIsConfidential() != null
							&& candidateDocuments.getIsConfidential() != ""
							&& !candidateDocuments.getIsConfidential().equalsIgnoreCase("Y")) {
						activitySummary.append("Is Confidential").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
								.append(candidateDocuments.getIsConfidential()).append(Constants.OLD_VALUE_TAG_END).append(" ")
								.append(Constants.NEW_VALUE_TAG_INITIATION).append("Y").append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateDocuments.setIsConfidential("Y");
				} else {
					if (candidateDocuments != null && candidateDocuments.getIsConfidential() != null
							&& candidateDocuments.getIsConfidential() != ""
							&& !candidateDocuments.getIsConfidential().equalsIgnoreCase("N")) {
						activitySummary.append("Is Confidential").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
								.append(candidateDocuments.getIsConfidential()).append(Constants.OLD_VALUE_TAG_END).append(" ")
								.append(Constants.NEW_VALUE_TAG_INITIATION).append("N").append(Constants.NEW_VALUE_TAG_END).append(" ");
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

				if (candidateDocumentsDTO.getFileInfo() != null && !candidateDocumentsDTO.getFileInfo().isEmpty()) {
					FileInfo fileInfo = candidateDocumentsDTO.getFileInfo().get(0);
					if (fileInfo.getFileName() != null) {
						activitySummary.append("Document").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION)
								.append(fileInfo.getFileName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
					}

					candidateDocuments.setFileName(fileInfo.getFileName());
					candidateDocuments.setFileContentType(fileInfo.getContentType());
					candidateDocuments.setFileURL(fileInfo.getFileURL());
					;
					if (fileInfo != null) {
						// candidate.setFileName(fileInfo.getFileName());
						FileUploadUtil.moveFile(candidateDocumentsDTO.getStaticPath(), fileInfo, "candidateDocuments");
					}

				}

			}
			candidateDocuments = candidateDocumentsDao.updateCandidateDocuments(candidateDocuments);

			candidateActivityCommonUtil.storeCandidateDocumentsHistory(candidateDocuments, user);

			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_DOCUMENTS_UPDATED,
					activitySummary.toString(), "CANDIDATE");

			saveOrUpdateCandidateElkDocumentWithDocumentDetails(candidateId, orgLocationId);
		}
		return candidateDocuments;
	}

	@Override
	@Async
	public void saveOrUpdateCandidateElkDocumentWithDocumentDetails(Long candidateId, Long orgLocationId) throws Exception {
		if (null != candidateId) {
			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candidate);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			candidateIndex.setUpdatedDate(dateFormat.format(new Date()));
			sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,
					candidateId.toString());
		}
	}

	@Override
	public List<CandidateDocumentsDTO> getCandidateDocumentsDTOListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId)
			throws Exception {
		List<CandidateDocuments> docList = candidateDocumentsDao.getCandidateDocumentsListByCandidateIdAndOrgLocationID(candidateId,
				orgLocationId);
		List<CandidateDocumentsDTO> candiDocList = new ArrayList<>();
		for (CandidateDocuments doc : docList) {
			CandidateDocumentsDTO docDto = new CandidateDocumentsDTO();
			docDto.setId(doc.getId().toString());
			if (StringUtils.isNotBlank(doc.getDocumentOtherType()))
				docDto.setDocumentOtherType(doc.getDocumentOtherType());
			if (StringUtils.isNotBlank(doc.getDocumentType()))
				docDto.setDocumentType(doc.getDocumentType());
			docDto.setName(doc.getDocumentType());
			if (StringUtils.isNotBlank(doc.getIsConfidential()))
				docDto.setIsConfidential(doc.getIsConfidential());
			if (StringUtils.isNotBlank(doc.getFileName()))
				docDto.setFileName(doc.getFileName());
			if (StringUtils.isNotBlank(doc.getFileURL()))
				docDto.setFileURL(doc.getFileURL());
			if (StringUtils.isNotBlank(doc.getFileContentType()))
				docDto.setFileContentType(doc.getFileContentType());
			if (StringUtils.isNotBlank(doc.getDescription()))
				docDto.setDescription(doc.getDescription());
			if (StringUtils.isNotBlank(doc.getDocumentUUID()))
				docDto.setDocumentUUID(doc.getDocumentUUID());
			if (StringUtils.isNotBlank(doc.getDocUploadStatus()))
				docDto.setDocUploadStatus(doc.getDocUploadStatus());
			if (doc.getRequestedDocumentsId() != null)
				docDto.setRequestDocumentId(doc.getRequestedDocumentsId().getId().toString());
			if (StringUtils.isNotBlank(doc.getDocUploadStatus()))
				docDto.setDocUploadStatus(doc.getDocUploadStatus());
			if (StringUtils.isNotBlank(doc.getRequestedDocRejectedReason()))
				docDto.setRequestedDocRejectedReason(doc.getRequestedDocRejectedReason());

			if (StringUtils.isNotBlank(doc.getRequestedDocStatus())) {
				docDto.setRequestedDocStatus(doc.getRequestedDocStatus());
			}
			docDto.setDoctype(doc.getDocumentType());
			docDto.setUserIdStr(doc.getCreatedBy());
			docDto.setCandidateId(String.valueOf(candidateId));
			docDto.setDocumentUUID(doc.getDocumentUUID());
			docDto.setDocumentId(String.valueOf(doc.getId()));
			candiDocList.add(docDto);
		}
		return candiDocList;
	}

	@Override
	public List<CandidateDocuments> getCandidateDocumentsListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId)
			throws Exception {
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
	public List<CandidateDocuments> getCandidateDocumentsListByRequestedDocumentsId(Long requestedDocumentsId) throws Exception {
		return candidateDocumentsDao.getCandidateDocumentsListByRequestedDocumentsId(requestedDocumentsId);
	}

	@Override
	public Boolean validateCandidateDocument(Long candidateId, String candidateDocument) throws Exception {
		return candidateDocumentsDao.validateCandidateDocument(candidateId, candidateDocument);
	}

	@Override
	public Integer getCandidateActiveDocumentsByCandidateId(Long candidateId) {
		return candidateDocumentsDao.getCandidateActiveDocumentsByCandidateId(candidateId);
	}

	@Override
	public Integer getCandidateGlobalActiveDocumentsByCandidateId(Long candidateId) {
		return candidateDocumentsDao.getCandidateGlobalActiveDocumentsByCandidateId(candidateId);
	}
}
