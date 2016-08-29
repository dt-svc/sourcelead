package com.bis.sourcelead.service;

import java.util.HashSet;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.CandidateSkills;

/**
 * @author Ajith Matta
 * 
 */
public interface CandidateSkillsService {
	
	/**
	 * @param candidateSkills
	 * @return CandidateSkills
	 * @throws Exception
	 */
	
		
	public CandidateSkills storeCandidateSkills(CandidateSkills candidateSkills) throws Exception;
	
	public HashSet<CandidateSkills> getCandidateSkillsDetails(JSONObject jsonObj,Long orgLocationId,String userCode)throws JSONException;

}
