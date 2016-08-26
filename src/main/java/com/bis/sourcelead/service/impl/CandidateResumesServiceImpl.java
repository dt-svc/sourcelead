package com.bis.sourcelead.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	/**
	* @author Venkat Kesav 
	* This Service is used to save the candidate resumes
	* @param jsonObj, user, candidate, orgLocations
	* @return CandidateResumes
	* @throws NullPointerException
	*/
	@Override
	public CandidateResumes storeCandidateResume(JSONObject jsonObj, User user, Candidate candidate, OrgLocations orgLocations) throws Exception {
		CandidateResumes candidateResume = new CandidateResumes();
		candidateResume.setTitle(jsonObj.getString("firstName"));
		candidateResume.setDescription(jsonObj.getString("firstName"));
		candidateResume.setCandidateId(candidate);
		candidateResume.setOrgLocationId(orgLocations);
		
		candidateResume.setFileContentType(jsonObj.getString("firstName"));
		candidateResume.setFileName(jsonObj.getString("firstName"));
		
		candidateResume.setCreatedDate(commonUtil.currentDate());
		candidateResume.setUpdatedBy(user.getUserCode());
		candidateResume.setCreatedBy(user.getUserCode());
		candidateResume.setUpdatedDate(commonUtil.currentDate());
		
		return candidateResume;
	}

}
