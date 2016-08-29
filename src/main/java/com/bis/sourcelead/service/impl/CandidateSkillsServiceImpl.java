package com.bis.sourcelead.service.impl;

import java.util.HashSet;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.service.CandidateSkillsService;

/**
 *@author Ajith Matta
 *@created date 25 Aug 2016
 */

@Service
public class CandidateSkillsServiceImpl implements CandidateSkillsService {

	
	
	
	/* 
	 * This Service is used to save the candidate skills
	 */
	@Override
	public CandidateSkills storeCandidateSkills(CandidateSkills candidateSkills) throws Exception {
		
		return null;
	}
	
	@Override
	public HashSet<CandidateSkills> getCandidateSkillsDetails(JSONObject jsonObj,Long orgLocationId,String userCode)throws JSONException{
			
		 HashSet<CandidateSkills> candidateSkillsList = new HashSet<CandidateSkills>();
		 
		 if(jsonObj.has("candidateSkills")){
			 CandidateSkills candSkills = new  CandidateSkills();
			 candSkills.setName(jsonObj.getString("candidateSkills"));
			  candidateSkillsList.add(candSkills);
		    }
		return candidateSkillsList;
	
     }

}
