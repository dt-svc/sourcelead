package com.bis.sourcelead.service;

import org.json.JSONObject;

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
	public CandidateResumes storeCandidateResume(JSONObject jsonObj, User user, Candidate candidate, OrgLocations orgLocation) throws Exception;
}
