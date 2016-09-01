package com.bis.sourcelead.service.impl;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateSkillsDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.CandidateSkillsService;

/**
 *@author Ajith Matta
 *@created date 25 Aug 2016
 */

@Service
public class CandidateSkillsServiceImpl implements CandidateSkillsService {

	@Autowired
	CandidateSkillsDao candidateSkillsDao;
	
	/* 
	 * This Service is used to save the candidate skills
	 */
	@Override
	public CandidateSkills storeCandidateSkills(CandidateSkills candidateSkills) throws Exception {
		
		return candidateSkillsDao.storeCandidateSkills(candidateSkills);
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

	@Override
	public List<CandidateSkills> getCandidateSkillsByCandidateId(String candidateId) throws Exception {
		 
		return candidateSkillsDao.getCandidateSkillsByCandidateId(candidateId);
	}
}