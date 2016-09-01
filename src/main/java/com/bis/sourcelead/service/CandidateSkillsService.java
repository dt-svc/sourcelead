package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.OrgLocations;

/**
 * @author Ajith Matta
 * 
 */
public interface CandidateSkillsService {
	
	
	
		
	/**
	 * @author  prasad.k
	 * @param candidateSkills
	 * @return
	 * @throws Exception
	 */
	public CandidateSkills storeCandidateSkills(CandidateSkills candidateSkills) throws Exception;
	
	/**
	 * @author prasad.k
	 * @param jsonObj
	 * @param orgLocations
	 * @param candidate
	 * @param userCode
	 * @return
	 * @throws JSONException
	 */
	public HashSet<CandidateSkills> getCandidateSkillsDetails(JSONObject jsonObj,OrgLocations orgLocations,Candidate candidate,String userCode)throws JSONException;
	
	/**
	 * @author prasad.k
	 * @param candidateId
	 * @return
	 * @throws Exception
	 */
	public List<CandidateSkills> getCandidateSkillsByCandidateId(String candidateId) throws Exception;
		
}
