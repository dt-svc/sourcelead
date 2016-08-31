package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class CandidateEducationServiceImpl implements CandidateEducationService {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateEducationServiceImpl.class);
	
	
	@Autowired
	private CandidateEducationDao candidateEducationDao;
	
	@Autowired
	private CommonUtil commonUtil;
 
	
	
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
	public CandidateEducation storeCandidateEducation(JSONObject jsonObject,Long candidateId, Long orgLocationId,User user) throws Exception {
		
		CandidateEducation candidateEducation =new CandidateEducation();
		
		Candidate candidate = new Candidate();
		candidate.setId(candidateId);
		candidateEducation.setCandidateId(candidate);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		candidateEducation.setOrglocationId(orgLocations);
		
		
		if(jsonObject.has("name")){
			candidateEducation.setName(jsonObject.getString("name"));
		}
		if(jsonObject.has("specialization")){
			candidateEducation.setSpecialization(jsonObject.getString("specialization"));
		}
		if(jsonObject.has("startDate")){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				candidateEducation.setStartDate(sdf.parse(jsonObject.getString("startDate")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(jsonObject.has("endDate")){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				candidateEducation.setEndDate(sdf.parse(jsonObject.getString("endDate")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
		candidateEducation.setStatus(Constants.CANDIDATE_ACTIVE);
		candidateEducation.setStatus(Constants.CANDIDATE_ACTIVE);
		candidateEducation.setCreatedBy(user.getUserCode());
		candidateEducation.setCreatedDate(commonUtil.currentDate());
		candidateEducation.setUpdatedBy(user.getUserCode());
		candidateEducation.setUpdatedDate(commonUtil.currentDate());
		
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
      
		List<CandidateEducation> edulist = candidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
		for(CandidateEducation education :edulist){
			education.setStartDateStr(Constants.DF_DMY.format(education.getStartDate()));
			education.setEndDateStr(Constants.DF_DMY.format(education.getEndDate()));
		}
		return edulist;
	}

	@Override
	public HashSet<CandidateEducation> getCandidateEducationDetails(JSONObject jsonObj,OrgLocations orgLocations,Candidate candidate,String userCode) throws JSONException {
		    HashSet<CandidateEducation> candidateEducationList = new HashSet<CandidateEducation>(); 
		    if(jsonObj.has("candidateEducation")){
		    	  String education = jsonObj.getString("candidateEducation");
		    	  education = education.replaceAll("\\[", "").replaceAll("\\]","");
		  	      String[] educations = education.split(",");
		    	  for (String edu : educations) {
		    		  CandidateEducation ce = new  CandidateEducation();
		    		  edu = edu.replace("\"", "");
		    		  ce.setName(edu);
		    		  ce.setCandidateId(candidate);
		    		  ce.setOrglocationId(orgLocations);
		    		  candidateEducationList.add(ce);
		    	  }
		    	  
		    }
			return  candidateEducationList;
	  }

	
	/**
	 * @author Sammeta David Raju
	 * This Method is for removing  CandidateEducation By candidateId and candidateEducationId
	 * 
	 * @param candidateId
	 * 
	 * @param  candidateEducationId
	 * 
	 * @return {@link CandidateEducation}
	 * 
	 * @exception NullPointerException
	 * 
	 */
	@Override
	public CandidateEducation removeCandidateEducationBycandidateIdandcandidateEducationId(Long candidateId,
			Long candidateEducationId) throws Exception {
	
		CandidateEducation can = candidateEducationDao.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
		     can.setStatus(Constants.CANDIDATE_IN_ACTIVE);
		     return candidateEducationDao.storeCandidateEducation(can);
	}
	
	

	/**
	 * @author Sammeta David Raju
	 * This Method is for getting  CandidateEducation By candidateId and candidateEducationId
	 * 
	 * @param candidateId
	 * 
	 * @param  candidateEducationId
	 * 
	 * @return {@link CandidateEducation}
	 * 
	 * @exception NullPointerException
	 * 
	 */
	@Override
	public CandidateEducation getCandidateEducationBycandidateIdandEducationId(Long candidateId,
			Long candidateCertificateId) throws Exception {
		  return candidateEducationDao.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateCertificateId);
	}


}
