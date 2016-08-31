package com.bis.sourcelead.service.impl;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateSkillsService;
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
	
	@Autowired
	CandidateEducationService candidateEducationService;
	
	@Autowired
	CandidateEmployersService candidateEmployersService;
	
	@Autowired
	CandidateSkillsService candidateSkillsService;
	
	@Autowired
	CandidateResumesService candidateResumesService;
	/**
	 * @author prasad k
	 * @since  aug 26 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save candidate in candidate table.
	 * 1.internalley store/save in candidateEducation in candidateEducation table .
	 * 2.internalley store/save in candidateSkills in candidateSkills table .
	 * 3.internalley store/save in candidateResumes in candidateResumes table .
	 * 4.internalley store/save in candidateEmployers in candidateEmployers table .
	 *  
	 *  
	 * 
	 * @param jsonObj This is the first paramter to storeCandidate method
	 * @param orgLocations This is the second paramter to storeCandidate method
	 * @param user This is the third paramter to storeCandidate method
	 * @param multiFile This is the fourth paramter to storeCandidate method
	 * 
	 * @return Candidate 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public Candidate storeCandidate(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile) throws Exception {
		
		Candidate candidate = new Candidate();
		
		if(orgLocations != null){
		candidate.setOrgLocationId(orgLocations);
		}
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
		if(jsonObj.has("expectedRateCurrencey")){
			candidate.setExpectedRateCurrencey(jsonObj.getString("expectedRateCurrencey"));
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
		
		
		
		HashSet<CandidateEducation>	 candidateEducationList = candidateEducationService.getCandidateEducationDetails(jsonObj, orgLocations,candidate,user.getUserCode());
		candidate.setCandidateEducation(candidateEducationList);
		
		HashSet<CandidateEmployers> candidateEmployersList = candidateEmployersService.getCandidateEmployersDetails(jsonObj, orgLocations.getId(),user.getUserCode());
		candidate.setCandidateEmployers(candidateEmployersList);
		
		HashSet<CandidateSkills> candidateSkillsList = candidateSkillsService.getCandidateSkillsDetails(jsonObj, orgLocations,candidate,user.getUserCode());
		candidate.setCandidateSkills(candidateSkillsList);
		
		HashSet<CandidateResumes> candidateResumesList = candidateResumesService.getCandidateResumesDetails(jsonObj, orgLocations,user.getUserCode(),candidate,multiFile);
		candidate.setCandidateResumes(candidateResumesList);
		
		candidate.setCreatedDate(commonUtil.currentDate());
		candidate.setCreatedBy(user.getUserCode());
		candidate.setUpdatedBy(user.getUserCode());
		candidate.setUpdatedDate(commonUtil.currentDate());
		Candidate savedCandidate = candidateDao.storeCandidate(candidate);
		return savedCandidate;
	}

	@Override
	public Candidate addCandidate(Candidate candidate) throws Exception {
		return candidateDao.storeCandidate(candidate);
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
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId) throws Exception {
		 return candidateDao.getCandidatesListByOrgLocationID(orgLocationId);
	}
	
	/**
	 * to get Candidate By CandidateId
	 * @author Venkat Kesav
	 * @param candidateId
	 * @return Candidate
	 * @throws Exception
    */
	@Override
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception {
		return candidateDao.getCandidateByCandidateId(candidateId);
	}
	
	
	@Override
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception {
	
		 Candidate  candidate = candidateDao.getCandidateByCandidateIdAndOrgLocationId(candidateId,orgLocationId);
		 return candidate;
	}
	
}
