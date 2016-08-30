package com.bis.sourcelead.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Ajith Matta
 *
 */
public interface CandidateResumesService {
	
	/**
	* @author Venkat Kesav 
	* This Service is used to save the candidate resumes
	* @param jsonObj, user, candidate, orgLocations
	* @return CandidateResumes
	* @throws NullPointerException
	*/
	public CandidateResumes storeCandidateResume(JSONObject jsonObj, User user, Candidate candidate, OrgLocations orgLocation, MultipartFile multiFile) throws Exception;
	
	public HashSet<CandidateResumes> getCandidateResumesDetails(JSONObject jsonObj,OrgLocations orgLocations,String userCode,Candidate candidate,MultipartFile multiFile)throws JSONException, IOException;
	
	public List<CandidateResumes> getCandidateResumesListByCandidateId(Long candidateId) throws Exception;
	
	public CandidateResumes updateCandidateResume(CandidateResumes candidateResumes) throws Exception;
	
	public CandidateResumes getCandidateResumeByResumeId(Long resumeId) throws Exception;
}
