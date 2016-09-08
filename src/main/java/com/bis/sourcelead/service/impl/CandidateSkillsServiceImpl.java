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
	
	/**
	 * @author  prasad.k
	 * This method is used for to store candidate skills 
	 * @param candidateSkills
	 * @return candidateSkills
	 * @throws Exception
	 */
	@Override
	public CandidateSkills storeCandidateSkills(CandidateSkills candidateSkills) throws Exception {
		
		return candidateSkillsDao.storeCandidateSkills(candidateSkills);
	}
	
	/**
	 * @author prasad.k
	 * This method is used for to get CandidateSkills
	 * @param jsonObj
	 * @param orgLocations
	 * @param candidate
	 * @param userCode
	 * @return candidateSkillsList
	 * @throws JSONException
	 */
	
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
	/**
	 * @author prasad.k
	 * This method is used for to get CandidateSkills
	 * @param candidateId
	 * @return CandidateSkills By CandidateId
	 * @throws Exception
	 */

	@Override
	public List<CandidateSkills> getCandidateSkillsByCandidateId(String candidateId) throws Exception {
		 
		return candidateSkillsDao.getCandidateSkillsByCandidateId(candidateId);
	}

	/**
	 * @author Venkat Kesav
	 * This method is used for to get CandidateSkill by Id
	 * @param candidateSkillId
	 * @return CandidateSkills By candidateSkillId
	 * @throws Exception
	 */
	@Override
	public CandidateSkills getCandidateSkillBySkillId(Long candidateSkillId) throws Exception {
		
		return candidateSkillsDao.getCandidateSkillBySkillId(candidateSkillId);
	}
}