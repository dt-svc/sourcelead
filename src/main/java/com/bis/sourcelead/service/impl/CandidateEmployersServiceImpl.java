package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Ajith Matta
 *
 */

@Service
public class CandidateEmployersServiceImpl implements CandidateEmployersService {
	
	@Autowired
	CandidateEmployersDao candidateEmployersDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	
	/**
	 * This method is used for save the candidate employers
	 * @author Ajith Matta
	 * @return CandidateEmployers
	 * @throws Exception
    */
	
	@Override
	public CandidateEmployers storeCandidateEmployers(JSONObject jsonObject, User user, OrgLocations orgLocationId,Long candidateId) throws Exception {

        CandidateEmployers candidateEmployers = new CandidateEmployers();
		
        if(jsonObject.getString("candidateId") != null && !jsonObject.getString("candidateId").isEmpty()){
			
        	candidateEmployers.setName(jsonObject.getString("employerName"));
        	Candidate can  = new Candidate();
        	can.setId(Long.valueOf(jsonObject.getString("candidateId")));
        	candidateEmployers.setCandidateId(can);
        	candidateEmployers.setAddress(jsonObject.getString("employerAddress"));
        	if(jsonObject.getString("isCurrentPosition")!=null && !jsonObject.getString("isCurrentPosition").isEmpty()){
        		candidateEmployers.setIsCurrentPosition(jsonObject.getString("isCurrentPosition"));
        		candidateEmployers.setStatus(Constants.CANDIDATE_ACTIVE);
        	}else{
        		candidateEmployers.setStatus(Constants.CANDIDATE_IN_ACTIVE);
        	}
        	candidateEmployers.setIsCurrentPosition(jsonObject.getString("isCurrentPosition"));
        	candidateEmployers.setDesignation(jsonObject.getString("employerDesignation"));
        	if(jsonObject.has("startDate") && !(jsonObject.getString("startDate").isEmpty())){
        		try {
 				   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 				   candidateEmployers.setStartDate(sdf.parse(jsonObject.getString("startDate")));
 				   candidateEmployers.setStartMonth(jsonObject.getString("startMonth"));
 				} catch (Exception e) {
 				  e.printStackTrace();
 				}
        	}
        	if(jsonObject.has("endDate") && !(jsonObject.getString("endDate").isEmpty())){
        		try {
  				   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  				   candidateEmployers.setEndDate(sdf.parse(jsonObject.getString("endDate")));
  				   candidateEmployers.setEndMonth(jsonObject.getString("endMonth"));
  				} catch (Exception e) {
  				  e.printStackTrace();
  				}
        	}
        	candidateEmployers.setCreatedBy(user.getUserCode());
        	candidateEmployers.setCreatedDate(commonUtil.currentDate());
        	candidateEmployers.setUpdatedBy(user.getUserCode());
        	candidateEmployers.setUpdatedDate(commonUtil.currentDate());
		}
		
		return candidateEmployersDao.storeCandidateEmployers(candidateEmployers);
	}
	
	/**
	 * This method is used for display the CandidateEmployers list
	 * @author Ajith Matta
	 * @return String
	 * @throws Exception
    */
	@Override
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception {
		
		 return candidateEmployersDao.getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
	}

	
	@Override
	public  HashSet<CandidateEmployers> getCandidateEmployersDetails(JSONObject jsonObj,Long orgLocationId,String userCode)throws JSONException{
		  HashSet<CandidateEmployers> candidateEmployersList = new HashSet<CandidateEmployers>();
		  if(jsonObj.has("candidateEmployers")){
			  CandidateEmployers cemp = new  CandidateEmployers();
			  cemp.setName(jsonObj.getString("candidateEmployers"));
			  candidateEmployersList.add(cemp);
		    }
		return candidateEmployersList;
	 }

}
