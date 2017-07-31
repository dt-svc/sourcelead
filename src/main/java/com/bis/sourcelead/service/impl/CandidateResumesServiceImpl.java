package com.bis.sourcelead.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.web.LoginSessionController;

/**
 * @author Ajith Matta
 *
 */

@Service
public class CandidateResumesServiceImpl implements CandidateResumesService {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateResumesServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private CandidateResumesDao candidateResumesDao;
	
	@Autowired
    private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	@Autowired
    CandidateActivityCommonUtil candidateActivityCommonUtil;
	

	/**
	 * @author Venkat Kesav
	 * @since  aug 30 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save the candidate resumes in candidate_Resumes table.
	 * 
	 * @param jsonObj This is the first parameter to storeCandidateResume method
	 * @param user This is the second parameter to storeCandidateResume method
	 * @param candidate This is the third parameter to storeCandidateResume method
	 * @param resumeId This is the third parameter to storeCandidateResume method
	 * @param orgLocations This is the third parameter to storeCandidateResume method
	 * @param multiFile This is the fourth parameter to storeCandidateResume method
	 * 
	 * @return CandidateResumes 
	 * 
	 * @throws NullPointer
	 */
	@Override
	public CandidateResumes storeCandidateResume(JSONObject jsonObj, User user, Candidate candidate, String resumeId, OrgLocations orgLocations, MultipartFile multiFile) throws Exception {
		CandidateResumes candidateResume = null;
		StringBuffer activitySummary = new StringBuffer();
		if(!resumeId.equalsIgnoreCase("null")){
			candidateResume = candidateResumesDao.getCandidateResumeByResumeId(Long.parseLong(resumeId));
			if(jsonObj.has("title")){
				if(candidateResume.getTitle() != null && candidateResume.getTitle() != "" && !candidateResume.getTitle().equalsIgnoreCase(jsonObj.getString("title"))){
					activitySummary.append("Title").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateResume.getTitle()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("title")).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}
				candidateResume.setTitle(jsonObj.getString("title"));
			}
			if(jsonObj.has("description")){
				if(candidateResume.getDescription() != null && candidateResume.getDescription() != "" && !candidateResume.getDescription().equalsIgnoreCase(jsonObj.getString("description"))){
					activitySummary.append("Description").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateResume.getDescription()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("description")).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}
				candidateResume.setDescription(jsonObj.getString("description"));
			}
			candidateResume.setUpdatedBy(user.getUserCode());
			candidateResume.setUpdatedDate(commonUtil.currentDate());
			if(!jsonObj.getString("docFile").equalsIgnoreCase("")){
				if(multiFile != null){
					if(candidateResume.getFileName() != null && candidateResume.getFileName() != "" && !candidateResume.getFileName().equalsIgnoreCase(multiFile.getOriginalFilename())){
						activitySummary.append("File").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateResume.getFileName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(multiFile.getOriginalFilename()).append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					 candidateResume.setDocFile(multiFile.getBytes());
					 candidateResume.setFileName(multiFile.getOriginalFilename());
					 candidateResume.setFileContentType(multiFile.getContentType());
				 }
			}
		}else{
			candidateResume = new CandidateResumes();
			if(jsonObj.has("title")){
				candidateResume.setTitle(jsonObj.getString("title"));
			}
			if(jsonObj.has("description")){
				candidateResume.setDescription(jsonObj.getString("description"));
			}
			if(candidate != null){
				candidateResume.setCandidateId(candidate);
			}
			if(orgLocations != null){
				candidateResume.setOrgLocationId(orgLocations);
			}
			candidateResume.setStatus(Constants.CANDIDATE_ACTIVE);
			candidateResume.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			candidateResume.setCreatedDate(commonUtil.currentDate());
			candidateResume.setUpdatedBy(user.getUserCode());
			candidateResume.setCreatedBy(user.getUserCode());
			candidateResume.setUpdatedDate(commonUtil.currentDate());
			if(multiFile != null){
				if(candidateResume.getTitle() != null && candidateResume.getTitle() != "" && candidateResume.getTitle() != "undefined"){
					activitySummary.append("Title").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateResume.getTitle()).append(Constants.NEW_VALUE_TAG_END);
				}else{
					activitySummary.append("File").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(multiFile.getOriginalFilename()).append(Constants.NEW_VALUE_TAG_END);
				}
				 candidateResume.setDocFile(multiFile.getBytes());
				 candidateResume.setFileName(multiFile.getOriginalFilename());
				 candidateResume.setFileContentType(multiFile.getContentType());
			 }
		}
		candidateResumesDao.updateCandidateResume(candidateResume);
		
		candidateActivityCommonUtil.storeCandidateResumesHistory(candidateResume, user);
		
		candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_RESUME_UPDATED, activitySummary.toString(), "CANDIDATE");
		
		/*
		 * updating Candidate Resume DB data in existing candidateindex
		 */
		saveOrUpdateCandidateElkDocumentWithResumeDetails(candidate);
		return candidateResume;
	}
	
	/**
	 * @author prasad.k
	 * @since  aug 30 2016
	 * @version 0.0.1
	 *  
	 * This method is used to get CandidateResumesDetails from candidate_Resumes table.
	 *  
	 * 
	 * @param orgLocations This is the first parameter to getCandidateResumesDetails method
	 * @param userCode This is the second parameter to getCandidateResumesDetails method
	 * @param candidate This is the third parameter to getCandidateResumesDetails method
	 * @param multiFile This is the fourth parameter to getCandidateResumesDetails method
	 * 
	 * @return candidateResumesList
	 * 
	 * @throws NullPointer
	 */
	@Override
	public HashSet<CandidateResumes> getCandidateResumesDetails(OrgLocations orgLocations,String userCode,Candidate candidate,MultipartFile multiFile)throws JSONException, IOException{
			 HashSet<CandidateResumes> candidateResumesList = new HashSet<CandidateResumes>();
			 if(multiFile != null && multiFile.getOriginalFilename() != null && !multiFile.getOriginalFilename().isEmpty()){
				 CandidateResumes candidateResumes = new CandidateResumes();
				 candidateResumes.setCandidateId(candidate);
				 candidateResumes.setDocFile(multiFile.getBytes());
				 candidateResumes.setFileName(multiFile.getOriginalFilename());
				 candidateResumes.setFileContentType(multiFile.getContentType());
				 candidateResumes.setStatus(Constants.CANDIDATE_ACTIVE);
				 candidateResumes.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				 candidateResumes.setCreatedDate(new Date());
				 candidateResumes.setCreatedBy(userCode);
				 candidateResumes.setUpdatedBy(userCode);
				 candidateResumes.setUpdatedDate(new Date());
				 candidateResumes.setOrgLocationId(orgLocations);
				 candidateResumesList.add(candidateResumes);
			 }
	     return candidateResumesList;
	}
	
	/**
	 * @author Venkat Kesav
	 * @since  aug 30 2016
	 * @version 0.0.1
	 * 
	 * This method is used to Candidates resumes list by candidate Id And Status from candidate_Resumes table.
	 *  
	 * 
	 * @param candidateId This is the first parameter to getCandidateResumesListByCandidateIdAndStatus method
	 * @param status This is the second parameter to getCandidateResumesListByCandidateIdAndStatus method
	 * 
	 * @return candidateResumesList
	 * 
	 * @throws NullPointer
	 */
	@Override
	public List<CandidateResumes> getCandidateResumesListByCandidateIdAndStatus(Long candidateId, Integer status) throws Exception {
		// return candidateResumesDao.getCandidateResumesListByCandidateIdAndStatus(candidateId, status);
	
		 List<CandidateResumes> candidateResumesList = candidateResumesDao.getCandidateResumesListByCandidateIdAndStatus(candidateId, status);
			for(CandidateResumes canResumes :candidateResumesList){
				if(canResumes.getUpdatedDate() != null){
					canResumes.setCandidateResumeUpdateDateToString(LoginSessionController.DATEFORMAT.format(canResumes.getUpdatedDate()));
				}
			}
			return candidateResumesList;
	
	}
	
	@Override
	public List<CandidateResumes> getCandidateResumesByIds(List<Long> submissionResumeIds) {
		return candidateResumesDao.getCandidateResumesByIds(submissionResumeIds);
	}
	
	
	/**
	 * @author Venkat Kesav
	 * @since  aug 30 2016
	 * @version 0.0.1
	 * 
	 * This method is used to update Candidate Resume in candidate_Resumes table.
	 *  
	 * 
	 * @param candidateResumes This is the first parameter to updateCandidateResume method
	 * 
	 * @return CandidateResumes
	 * 
	 * @throws NullPointer
	 */
	@Override
	public CandidateResumes updateCandidateResume(CandidateResumes candidateResumes) throws Exception {
		logger.debug("In CandidateResumeServiceImpl class updateCandidateResume method...");
		candidateResumes.setUpdatedDate(commonUtil.currentDate());
		candidateResumes = candidateResumesDao.updateCandidateResume(candidateResumes);
				
		 /*
		 * updating Candidate Resume DB data in existing candidateindex
		 */
	   // saveOrUpdateCandidateElkDocumentWithResumeDetails(candidateResumes.getCandidateId());
		return candidateResumes;
		
	}

	/**
	 * @author Venkat Kesav
	 * @since  aug 30 2016
	 * @version 0.0.1
	 * 
	 * This method is used to update Candidate Resume in candidate_Resumes table.
	 *  
	 * 
	 * @param resumeId This is the first parameter to getCandidateResumeByResumeId method
	 * 
	 * @return CandidateResumes
	 * 
	 * @throws NullPointer
	 */
	@Override
	public CandidateResumes getCandidateResumeByResumeId(Long resumeId) throws Exception {
		logger.debug("In CandidateResumeServiceImpl class getCandidateResumeByResumeId method...");
		return candidateResumesDao.getCandidateResumeByResumeId(resumeId);
	}


	@Override
	public CandidateResumes getCandidateLatesResumes(Long candidateId, int status) throws Exception {
		return candidateResumesDao.getCandidateLatesResumes(candidateId,status);
	}

	@Override
	@Async
	public void saveOrUpdateCandidateElkDocumentWithResumeDetails(Candidate candidate) throws Exception {
		if(null != candidate && null != candidate.getId()){
			CandidateIndex candidateIndex = new CandidateIndex();
			candidateIndex.setCandidateId(candidate.getId());
			CandidateResumes candidateResumes =getCandidateLatesResumes(candidate.getId(),Constants.CANDIDATE_SKILL_ACTIVE);
			if(null != candidateResumes){
				candidateIndex.setCurrentResumeId(candidateResumes.getId());
				candidateIndex.setCurrentResumeName(candidateResumes.getFileName());
				candidateIndex.setResumeContent("");
			}
			sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
		}
	}

	@Override
	public CandidateResumes getCandidateResumeByResumeUUID(String resumeUUID) throws Exception {
		return candidateResumesDao.getCandidateResumeByResumeUUID(resumeUUID);
	}

	@Override
	public Integer getCandidateResumeCountByResumeId(Long resumeId) throws Exception {
		return candidateResumesDao.getCandidateResumeCountByResumeId(resumeId);
	}

	@Override
	public Integer getCandidateResumeCountByCandidateId(Long candidateId) throws Exception {
		return candidateResumesDao.getCandidateResumeCountByCandidateId(candidateId);
	}

	@Override
	public CandidateResumes getCandidateResumesListByCandidateId(Long candidateId) {
		return candidateResumesDao.getCandidateResumesListByCandidateId(candidateId);
		
	}

	@Override
	public CandidateResumes getCandidateResumeByCandidateId(Long candidateId) throws Exception {
		return candidateResumesDao.getCandidateResumeByCandidateId(candidateId);
	}

}