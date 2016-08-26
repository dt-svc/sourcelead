package com.bis.sourcelead.service.impl;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * 
 * @author prasad K
 * @date 25th Aug 2016
 *
 */

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	CandidateDao candidateDao;

	@Autowired
	private CommonUtil commonUtil;
	
	/**
	 * This method for to store Candidate Into db
	 */
	
	@Override
	public Candidate storeCandidate(JSONObject jsonObj,OrgLocations orgLocations,User user) throws Exception {
		
		Candidate candidate = new Candidate();
		if(jsonObj.has("firstName")){
		candidate.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName")){
		candidate.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("country")){
		candidate.setCountry(jsonObj.getString("country"));
		}
		if(jsonObj.has("state")){
		candidate.setState(jsonObj.getString("state"));
		}
		if(jsonObj.has("city")){
		candidate.setCity(jsonObj.getString("city"));
		}
		if(jsonObj.has("phone")){
		candidate.setMobilePhone(jsonObj.getString("phone"));
		}
		if(jsonObj.has("email")){
		candidate.setEmail(jsonObj.getString("email"));
		}
		if(jsonObj.has("currentRate")){
		candidate.setCurrentRate(jsonObj.getString("currentRate"));
		}
		if(jsonObj.has("currentRateCurrency")){
		candidate.setCurrentRateCurrency(jsonObj.getString("currentRateCurrency"));
		}
		if(jsonObj.has("designation")){
		candidate.setDesignation(jsonObj.getString("designation"));
		}
		if(jsonObj.has("expectedRate")){
		candidate.setExpectedRate(jsonObj.getString("expectedRate"));
		}
		if(jsonObj.has("summary")){
		candidate.setSummary(jsonObj.getString("summary"));
		}
		if(jsonObj.has("designationType")){
		candidate.setDesignationType(jsonObj.getString("designationType"));
		}
		if(jsonObj.has("experienceYears")){
		candidate.setExperienceYears(jsonObj.getString("experienceYears"));
		}
		if(jsonObj.has("experienceMonths")){
		candidate.setExperienceMonths(jsonObj.getString("experienceMonths"));	
		}
		if(jsonObj.has("address")){
			candidate.setAddress(jsonObj.getString("address"));
		}
		
		
		HashSet<CandidateEducation>	 candidateEducationList = getCandidateEducationDetails(jsonObj, orgLocations.getId(),user.getUserCode());
		candidate.setCandidateEducation(candidateEducationList);
		
		HashSet<CandidateEmployers> candidateEmployersList = getCandidateEmployersDetails(jsonObj, orgLocations.getId(),user.getUserCode());
		candidate.setCandidateEmployers(candidateEmployersList);
		
		HashSet<CandidateSkills> candidateSkillsList = getCandidateSkillsDetails(jsonObj, orgLocations.getId(),user.getUserCode());
		candidate.setCandidateSkills(candidateSkillsList);
		
		HashSet<CandidateResumes> candidateResumesList = getCandidateResumesDetails(jsonObj, orgLocations.getId(),user.getUserCode());
		candidate.setCandidateResumes(candidateResumesList);
		
		candidate.setCreatedDate(commonUtil.currentDate());
		candidate.setCreatedBy("prasad");
		candidate.setUpdatedBy("prasad");
		candidate.setUpdatedDate(commonUtil.currentDate());
		
		return candidate;
	}

	@Override
	public Candidate addCandidate(Candidate candidate) throws Exception {
		return candidateDao.storeCandidate(candidate);
	}

	
	 HashSet<CandidateEducation> getCandidateEducationDetails(JSONObject jsonObj,Long orgLocationId,String userCode) throws JSONException {
		    HashSet<CandidateEducation> candidateEducationList = new HashSet<CandidateEducation>(); 
		    if(jsonObj.has("candidateEducation")){
		    	CandidateEducation ce = new  CandidateEducation();
		    	ce.setName(jsonObj.getString("candidateEducation"));
		    	candidateEducationList.add(ce);
		    }
			return  candidateEducationList;
	  }
	
	
	 HashSet<CandidateEmployers> getCandidateEmployersDetails(JSONObject jsonObj,Long orgLocationId,String userCode)throws JSONException{
		  HashSet<CandidateEmployers> candidateEmployersList = new HashSet<CandidateEmployers>();
		  if(jsonObj.has("candidateEmployers")){
			  CandidateEmployers cemp = new  CandidateEmployers();
			  cemp.setName(jsonObj.getString("candidateEmployers"));
			  candidateEmployersList.add(cemp);
		    }
		return candidateEmployersList;
	 }
	 
	 HashSet<CandidateSkills> getCandidateSkillsDetails(JSONObject jsonObj,Long orgLocationId,String userCode)throws JSONException{
		
		 HashSet<CandidateSkills> candidateSkillsList = new HashSet<CandidateSkills>();
		 
		 if(jsonObj.has("candidateSkills")){
			 CandidateSkills candSkills = new  CandidateSkills();
			 candSkills.setName(jsonObj.getString("candidateSkills"));
			  candidateSkillsList.add(candSkills);
		    }
		return candidateSkillsList;
	
       }
	 
	 HashSet<CandidateResumes> getCandidateResumesDetails(JSONObject jsonObj,Long orgLocationId,String userCode)throws JSONException{
			
		 HashSet<CandidateResumes> candidateResumesList = new HashSet<CandidateResumes>();
		 
		 if(jsonObj.has("candidateResumes")){
			 CandidateResumes candResumes = new  CandidateResumes();
			 candResumes.setFileName(jsonObj.getString("candidateResumes"));
			 candidateResumesList.add(candResumes);
		    }
		return candidateResumesList;
	
     }

	/**
	 * @author David Raju Sammeta
	 * 
	 * This Method is for getting Candidate by Candidate Id OrgId and OrgLocationID
	 * 
	 * @return Candidate
	 * 
	 * @exception NullPointerException
	 */
	@Override
	public Candidate getCandidateByCandidateIdAndOrgLocationIdAndOrgId(Long candidateId,Long orgId,Long orgLocationId) throws Exception {
	
		   Candidate  candidate = candidateDao.getCandidateByCandidateIdAndOrgLocationIdAndOrgId(candidateId, orgId, orgLocationId);
	
		 return candidate;
	}

	/**
	 * to Candidates List by OrgCode OrgLocationCode
	 * @author David Sammeta
	 * @param request
	 * @return String
	 * @throws Exception
    */
	@Override
	public List<Candidate> getCandidatesListByOrgIdAndOrgLocationID(Long orgLocationId) throws Exception {
		 return candidateDao.getCandidatesListByOrgIdAndOrgLocationID(orgLocationId);
	}
	
	
}
