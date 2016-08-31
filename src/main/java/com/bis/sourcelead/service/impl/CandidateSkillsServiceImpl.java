package com.bis.sourcelead.service.impl;

import java.util.HashSet;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.OrgLocations;
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
	public HashSet<CandidateSkills> getCandidateSkillsDetails(JSONObject jsonObj,OrgLocations orgLocations,Candidate candidate,String userCode)throws JSONException{
			
		 HashSet<CandidateSkills> candidateSkillsList = new HashSet<CandidateSkills>();
		 if(jsonObj.has("candidateSkills")){
	    	  String skills = jsonObj.getString("candidateSkills");
	    	  skills = skills.replaceAll("\\[", "").replaceAll("\\]","");
	  	      String[] candSkills = skills.split(",");
	    	  for (String skill : candSkills) {
	    		  CandidateSkills candSkill = new  CandidateSkills();
	    		  skill = skill.replace("\"", "");
	    		  candSkill.setName(skill);
	    		  candSkill.setCandidateId(candidate);
	    		  candSkill.setOrgLocationID(orgLocations);
	    		  candidateSkillsList.add(candSkill);
	    	  }
	
          }
		return candidateSkillsList;    

   }
}