package com.bis.sourcelead.service.impl;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.CandidateEducationService;

@Service
public class CandidateEducationServiceImpl implements CandidateEducationService {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateEducationServiceImpl.class);
	@Autowired
	private CandidateEducationDao candidateEducationDao;
 
	/**
	 * @author ajith kumar
	 * @since  aug 26 2016
	 * @version 0.0.1
	 * 
	 * This method is used to save/store the candidate education details.
	 * this method is called at onload when click on timesheet left menu
	 * 
	 * @param jsonObject This is the first paramter to storeCandidateEducation method
	 *  
	 * @return CandidateEducation 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public CandidateEducation storeCandidateEducation(JSONObject jsonObject,Long candidateId, Long orgLocationId) throws Exception {
		
		CandidateEducation candidateEducation =new CandidateEducation();
		
		Candidate candidate = new Candidate();
		candidate.setId(candidateId);
		candidateEducation.setCandidateId(candidate);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(candidateId);
		candidateEducation.setOrglocationId(orgLocations);
		
		
		if(jsonObject.has("name")){
			candidateEducation.setName(jsonObject.getString("name"));
		}
		if(jsonObject.has("specialization")){
			candidateEducation.setSpecialization(jsonObject.getString("specialization"));
		}
		/*if(jsonObject.has("startDate")){
			candidateEducation.setStartDate((Date) jsonObject.get("startDate"));
		}
		if(jsonObject.has("endDate")){
			candidateEducation.setEndDate((Date)jsonObject.get("endDate"));
		}*/
		if(jsonObject.has("grade")){
			candidateEducation.setGrade(jsonObject.getString("grade"));
		}
		if(jsonObject.has("gradeType")){
			candidateEducation.setGradeType(jsonObject.getString("gradeType"));
		}
		if(jsonObject.has("institute")){
			candidateEducation.setInstitute(jsonObject.getString("institute"));
		}
		if(jsonObject.has("address")){
			candidateEducation.setAddress(jsonObject.getString("address"));
		}
		
		return candidateEducationDao.storeCandidateEducation(candidateEducation);
	}
	
	
	/**
	 *  This Method is used to get candidate education details 
	 *   @author Ajith Matta
	 * @param candidateId
	 * @return CandidateEducation list
	 * @throws Exception
	 */

	@Override
	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception{

		return candidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
	}

	@Override
	public HashSet<CandidateEducation> getCandidateEducationDetails(JSONObject jsonObj,Long orgLocationId,String userCode) throws JSONException {
		    HashSet<CandidateEducation> candidateEducationList = new HashSet<CandidateEducation>(); 
		    if(jsonObj.has("candidateEducation")){
		    	  String education = jsonObj.getString("candidateEducation");
		    	  education = education.replaceAll("\\[", "").replaceAll("\\]","");
		  	      String[] educations = education.split(",");
		    	  for (String edu : educations) {
		    		  CandidateEducation ce = new  CandidateEducation();
		    		  edu = edu.replace("\"", "");
		    		  ce.setName(edu);
		    		  candidateEducationList.add(ce);
		    	  }
		    }
			return  candidateEducationList;
	  }


}
