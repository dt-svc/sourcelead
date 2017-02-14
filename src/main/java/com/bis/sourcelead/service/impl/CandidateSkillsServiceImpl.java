package com.bis.sourcelead.service.impl;

import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.CandidateSkillsService;
import com.bis.sourcelead.util.CommonUtil;

/**
 *@author Ajith Matta
 *@created date 25 Aug 2016
 */

@Service
public class CandidateSkillsServiceImpl implements CandidateSkillsService {

	/*@Autowired
	CandidateSkillsDao candidateSkillsDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	 @Autowired
	 private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	*//**
	 * @author  prasad.k
	 * This method is used for to store candidate skills 
	 * @param candidateSkills
	 * @return candidateSkills
	 * @throws Exception
	 *//*
	@Override
	public CandidateSkills storeCandidateSkills(CandidateSkills candidateSkills) throws Exception {
		String newlist = candidateSkills.getName();
		candidateSkills.setUpdatedDate(commonUtil.currentDate());
		candidateSkills = candidateSkillsDao.storeCandidateSkills(candidateSkills);
		
		 * updating Candidate Skills DB data in existing candidateindex
		 
		//saveOrUpdateCandidateElkDocumentWithSkillDetails(candidateSkills.getCandidateId());
		return candidateSkills;
	}
	
	*//**
	 * @author prasad.k
	 * This method is used for to get CandidateSkills
	 * @param jsonObj
	 * @param orgLocations
	 * @param candidate
	 * @param userCode
	 * @return candidateSkillsList
	 * @throws JSONException
	 *//*
	
	@Override
	public HashSet<CandidateSkills> getCandidateSkillsDetails(JSONObject jsonObj,OrgLocations orgLocations,Candidate candidate,String userCode)throws JSONException{
			
		 HashSet<CandidateSkills> candidateSkillsList = new HashSet<CandidateSkills>();
		 if(jsonObj.has("candidateSkills")){
			
			 String skills = null;
			 try{
	    		   JSONArray jsonArray = jsonObj.getJSONArray("candidateSkills");
	    		   skills = jsonArray.toString();
	    	  }
	    	  catch(JSONException e){
	    		  skills= jsonObj.getString("candidateSkills");
	    	  }
			 	skills = skills.replaceAll("\\[", "").replaceAll("\\]","");
			 	skills = skills.replace("\"", "");
			  CandidateSkills candSkill = new  CandidateSkills();
	   		  candSkill.setName(skills);
	   		  candSkill.setCandidateId(candidate);
	   		  candSkill.setStatus(Constants.CANDIDATE_SKILL_ACTIVE);
	   		  candSkill.setOrgLocationID(orgLocations);
	   		  candidateSkillsList.add(candSkill);
			 
	    	  skills = skills.replaceAll("\\[", "").replaceAll("\\]","");
	  	      String[] candSkills = skills.split(",");
	    	  for (String skill : candSkills) {
	    		  CandidateSkills candSkill = new  CandidateSkills();
	    		  skill = skill.replace("\"", "");
	    		  candSkill.setName(skill);
	    		  candSkill.setCandidateId(candidate);
	    		  candSkill.setStatus(Constants.CANDIDATE_SKILL_ACTIVE);
	    		  candSkill.setOrgLocationID(orgLocations);
	    		  candidateSkillsList.add(candSkill);
	    	  }
	
          }
		return candidateSkillsList;    

   }
	*//**
	 * @author prasad.k
	 * This method is used for to get CandidateSkills
	 * @param candidateId
	 * @return CandidateSkills By CandidateId
	 * @throws Exception
	 *//*

	@Override
	public List<CandidateSkills> getCandidateSkillsByCandidateId(String candidateId) throws Exception {
		 
		return candidateSkillsDao.getCandidateSkillsByCandidateId(candidateId);
	}
	
	@Override
	public CandidateSkills getAllCandidateSkillsByCandidateId(String candidateId) throws Exception {
		 
		return candidateSkillsDao.getAllCandidateSkillsByCandidateId(candidateId);
	}
	
	
	@Override
	public List<String> getCandidateSkillNamesByCandidateId(String candidateId) throws Exception {
		return candidateSkillsDao.getCandidateSkillNamesByCandidateId(candidateId);
	}

	*//**
	 * @author Venkat Kesav
	 * This method is used for to get CandidateSkill by Id
	 * @param candidateSkillId
	 * @return CandidateSkills By candidateSkillId
	 * @throws Exception
	 *//*
	@Override
	public CandidateSkills getCandidateSkillBySkillId(Long candidateSkillId) throws Exception {
		
		return candidateSkillsDao.getCandidateSkillBySkillId(candidateSkillId);
	}

	@Override
	public List<CandidateSkills> getSkillByCandidateId(Long candidateId) throws Exception {
		return candidateSkillsDao.getSkillByCandidateId(candidateId);
	}

	@Override
	public CandidateSkills getSkillsByCandidateIdSkillName(String candidateId, String skillName) {
		
		return candidateSkillsDao.getSkillsByCandidateIdSkillName(candidateId, skillName);
	}

	@Override
	public Boolean validateSkillName(String skillName) {
		Boolean flag = candidateSkillsDao.validateSkillName(skillName);
		return flag;
	}
	
	@Override
    @Async
	public void saveOrUpdateCandidateElkDocumentWithSkillDetails(Candidate candidate) throws Exception {
		if(null != candidate && null != candidate.getId()){
			CandidateIndex candidateIndex = new CandidateIndex();
			candidateIndex.setCandidateId(candidate.getId());
			ArrayList<String> skills =(ArrayList<String>) getCandidateSkillNamesByCandidateId(candidate.getId().toString());
			if(null != skills){
				candidateIndex.setSkills(skills);
			}
			sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
		}
	}
	*/
	
	
	
	
	
	
}