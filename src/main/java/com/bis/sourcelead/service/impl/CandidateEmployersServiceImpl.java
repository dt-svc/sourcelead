package com.bis.sourcelead.service.impl;

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
	 * @param  jsonObject      This is the first parameter of storeCandidateEmployers
	 * @param  user          This is the second parameter of storeCandidateEmployers
	 * @param  orgLocationId   This is the third parameter of storeCandidateEmployers
	 * @param  candidateId     This is the fourth parameter of storeCandidateEmployers
	 * @return CandidateEmployers
	 * @throws Exception
    */
	
	@Override
	public CandidateEmployers storeCandidateEmployers(JSONObject jsonObject, User user, OrgLocations orgLocationId,Long candidateId) throws Exception {

        CandidateEmployers candidateEmployers = new CandidateEmployers();
		
        if(candidateId!=null){
			
        	candidateEmployers.setName(jsonObject.getString("name"));
        	Candidate can  = new Candidate();
        	can.setId(candidateId);
        	candidateEmployers.setCandidateId(can);
        	candidateEmployers.setAddress(jsonObject.getString("address"));
        	if(jsonObject.getString("isCurrentPosition")!=null && !jsonObject.getString("isCurrentPosition").isEmpty()){
        		candidateEmployers.setIsCurrentPosition(jsonObject.getString("isCurrentPosition"));
        		candidateEmployers.setStatus(Constants.CANDIDATE_ACTIVE);
        	}else{
        		candidateEmployers.setStatus(Constants.CANDIDATE_IN_ACTIVE);
        	}
        	candidateEmployers.setDesignation(jsonObject.getString("designation"));
        	if(jsonObject.has("startYear") && !(jsonObject.getString("startYear").isEmpty())){
 				   candidateEmployers.setStartYear(jsonObject.getString("startYear"));
 				   candidateEmployers.setStartMonth(jsonObject.getString("startMonth"));
        	}
        	if(jsonObject.has("endYear") && !(jsonObject.getString("endYear").isEmpty())){
  				   candidateEmployers.setEndYear(jsonObject.getString("endYear"));
  				   candidateEmployers.setEndMonth(jsonObject.getString("endMonth"));
        	}
        	candidateEmployers.setCreatedBy(user.getUserCode());
        	candidateEmployers.setOrgLocationId(orgLocationId);
        	candidateEmployers.setCreatedDate(commonUtil.currentDate());
        	candidateEmployers.setUpdatedBy(user.getUserCode());
        	candidateEmployers.setUpdatedDate(commonUtil.currentDate());
		}
		
		return candidateEmployersDao.storeCandidateEmployers(candidateEmployers);
	}
	
	/**
	 * This method is used for remove Candidate Employer By candidateId and Candidate Employer ID
	 * @author Sammeta David Raju
	 * @param  candidateId  This is the first parameter of removeCandidateEmployerBycandidateIdandCandidateEmployerID
	 * @param candidateEmployerId  This is the second parameter of removeCandidateEmployerBycandidateIdandCandidateEmployerID
	 * @return CandidateEmployers   removeCandidateEmployerBycandidateIdandCandidateEmployerID
	 * @exception NullPointerException
	 * 
	 */
	@Override
	public CandidateEmployers   removeCandidateEmployerBycandidateIdandCandidateEmployerID(Long candidateId, Long candidateEmployerId) throws Exception {
		
		 CandidateEmployers can = candidateEmployersDao.getCandidateEmployerBycandidateIdandCandidateEmployerID(candidateEmployerId, candidateId);
		 can.setStatus(Constants.CANDIDATE_IN_ACTIVE);
		 return candidateEmployersDao.storeCandidateEmployers(can);
	}

	
	/**
	 * This method is used for display the CandidateEmployers list
	 * @author Ajith Matta
	 * @param candidateId    This is the first parameter of getCandidatesEmployersListByCandidateIdAndOrgLocationID
	 * @param orgLocationId  This is the second parameter of getCandidatesEmployersListByCandidateIdAndOrgLocationID
	 * @return String
	 * @throws Exception
    */
	@Override
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception {
		
		 return candidateEmployersDao.getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
	}

	
	
	/**
	 * This method is used for display the get Candidate Employers Details list
	 * @author Sammeta David 
	 * @param  jsonObj This is the first parameter of getCandidateEmployersDetails
	 * @param orgLocations This is the second parameter of getCandidateEmployersDetails
	 * @param candidate    This is the third parameter of getCandidateEmployersDetails
	 * @param userCode     This is the fourth parameter of getCandidateEmployersDetails
	 * @return HashSet<CandidateEmployers>
	 * @throws Exception
    */
	@Override
	public  HashSet<CandidateEmployers> getCandidateEmployersDetails(JSONObject jsonObj,OrgLocations orgLocations,Candidate candidate,String userCode)throws JSONException{
		  HashSet<CandidateEmployers> candidateEmployersList = new HashSet<CandidateEmployers>();
		  
		  if(jsonObj.has("candidateEmployers")){
	    	  String employers = jsonObj.getString("candidateEmployers");
	    	  employers = employers.replaceAll("\\[", "").replaceAll("\\]","");
	  	      String[] candidateEmployers = employers.split(",");
	    	  for (String emps : candidateEmployers) {
	    		  CandidateEmployers cemp = new  CandidateEmployers();
	    		  emps = emps.replace("\"", "");
	    		  cemp.setName(emps);
	    		  candidateEmployersList.add(cemp);
	    	  }
	    	  
	    }
		 
		return candidateEmployersList;
	 }

	
	/**
	 * This method is used for display the get Candidate Employer By candidateId and CandidateEmployerID 
	 * @author Sammeta David Raju
	 * @param
	 * @return CandidateEmployers
	 * @throws Exception
    */
	
	@Override
	public CandidateEmployers getCandidateEmployerBycandidateIdandCandidateEmployerID(Long candidateId, Long candidateEmployerId)
			throws Exception {
		  return candidateEmployersDao.getCandidateEmployerBycandidateIdandCandidateEmployerID(candidateId, candidateEmployerId);
	}

}
