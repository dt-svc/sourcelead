package com.bis.sourcelead.service.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Ajith Matta
 *
 */

@Service
public class CandidateResumesServiceImpl implements CandidateResumesService {
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private CandidateResumesDao candidateResumesDao;

	/**
	* @author Venkat Kesav 
	* This Service is used to save the candidate resumes
	* @param jsonObj, user, candidate, orgLocations
	* @return CandidateResumes
	* @throws NullPointerException
	*/
	@Override
	public CandidateResumes storeCandidateResume(JSONObject jsonObj, User user, Candidate candidate, OrgLocations orgLocations, MultipartFile multiFile) throws Exception {
		CandidateResumes candidateResume = new CandidateResumes();
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
		
		candidateResume.setCreatedDate(commonUtil.currentDate());
		candidateResume.setUpdatedBy(user.getUserCode());
		candidateResume.setCreatedBy(user.getUserCode());
		candidateResume.setUpdatedDate(commonUtil.currentDate());
		
		if(multiFile != null){
			 candidateResume.setDocFile(multiFile.getBytes());
			 candidateResume.setFileName(multiFile.getOriginalFilename());
			 candidateResume.setFileContentType(multiFile.getContentType());
		 }
		
		candidateResumesDao.storeCandidateResumes(candidateResume);
		
		return candidateResume;
	}
	
	@Override
	public HashSet<CandidateResumes> getCandidateResumesDetails(JSONObject jsonObj,Long orgLocationId,String userCode,MultipartFile multiFile)throws JSONException, IOException{
			 HashSet<CandidateResumes> candidateResumesList = new HashSet<CandidateResumes>();
			 CandidateResumes candidateResumes = new CandidateResumes();
			 if(multiFile != null){
				 candidateResumes.setDocFile(multiFile.getBytes());
				 candidateResumes.setFileName(multiFile.getOriginalFilename());
				 candidateResumes.setFileContentType(multiFile.getContentType());
				 candidateResumesList.add(candidateResumes);
			 }
	     return candidateResumesList;
	}
	
	/**
	 * to Candidates resumes list by candidate Id
	 * @author Venkat Kesav
	 * @param candidateId
	 * @return List<CandidateResumes>
	 * @throws Exception
    */
	@Override
	public List<CandidateResumes> getCandidateResumesListByCandidateId(Long candidateId) throws Exception {
		 return candidateResumesDao.getCandidateResumesListByCandidateId(candidateId);
	}

}
