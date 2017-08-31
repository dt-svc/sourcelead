package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
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
	
	 @Autowired
	 private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 CandidateActivityCommonUtil candidateActivityCommonUtil;
	
	
	/**
	 * This method is used for save the candidate employers
	 * 
	 * @author Ajith Matta
	 * 
	 * @param  jsonObject      This is the first parameter of storeCandidateEmployers
	 * @param  user          This is the second parameter of storeCandidateEmployers
	 * @param  orgLocationId   This is the third parameter of storeCandidateEmployers
	 * @param  candidateId     This is the fourth parameter of storeCandidateEmployers
	 * 
	 * @return CandidateEmployers
	 * 
	 * @throws Exception
    */
	
	@Override
	public CandidateEmployers storeCandidateEmployers(JSONObject jsonObject, User user, OrgLocations orgLocationId,Long candidateId) throws Exception {

        CandidateEmployers candidateEmployers = null;
        CandidateEmployers currentEmployer = null;
        Candidate can  = new Candidate();
        StringBuffer activitySummary = new StringBuffer();
        if(candidateId!=null){
        	if(jsonObject.has("candidateEmployeeId")){
	       		 if(jsonObject.getString("candidateEmployeeId")!=null && !jsonObject.getString("candidateEmployeeId").isEmpty()){
	       			candidateEmployers = candidateEmployersDao.getCandidateEmployerBycandidateIdandCandidateEmployerID(Long.parseLong(jsonObject.getString("candidateEmployeeId")), candidateId);
	       			// candidateEmployers.setId(Long.valueOf(jsonObject.getString("candidateEmployeeId")));
	       	     }
       	    }else{
    	    	candidateEmployers = new CandidateEmployers();
    	    }
        	if(candidateEmployers != null && candidateEmployers.getName() != null && candidateEmployers.getName() != "" && !candidateEmployers.getName().equalsIgnoreCase(jsonObject.getString("name"))){
				activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("name")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if(candidateEmployers.getId() == null){
				activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("name")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
        	candidateEmployers.setName(jsonObject.getString("name"));
        	can.setId(candidateId);
        	candidateEmployers.setCandidateId(can);
        	
        	if(candidateEmployers != null && candidateEmployers.getAddress() != null && candidateEmployers.getAddress() != "" && !candidateEmployers.getAddress().equalsIgnoreCase(jsonObject.getString("address"))){
				activitySummary.append("Address").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getAddress()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("address")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
        	candidateEmployers.setAddress(jsonObject.getString("address"));
        	
        	if(candidateEmployers != null && candidateEmployers.getDesignation() != null && candidateEmployers.getDesignation() != "" && !candidateEmployers.getDesignation().equalsIgnoreCase(jsonObject.getString("designation"))){
				activitySummary.append("Designation").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getDesignation()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("designation")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
        	candidateEmployers.setDesignation(jsonObject.getString("designation"));

        	if(jsonObject.has("isCurrentPosition") && !(jsonObject.getString("isCurrentPosition").isEmpty())){
	        	
        		currentEmployer = candidateEmployersDao.getCandidateCurrentEmployer(candidateId, orgLocationId.getId());
        		if(null != currentEmployer){
        			currentEmployer.setIsCurrentPosition(Constants.NO);
        		}
        		
        		if(jsonObject.getString("isCurrentPosition")!=null && !jsonObject.getString("isCurrentPosition").isEmpty()){
        			if(candidateEmployers != null && candidateEmployers.getIsCurrentPosition() != null && candidateEmployers.getIsCurrentPosition() != "" && !candidateEmployers.getIsCurrentPosition().equalsIgnoreCase(jsonObject.getString("isCurrentPosition"))){
        				activitySummary.append("Is Current Position").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getIsCurrentPosition()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("isCurrentPosition")).append(Constants.NEW_VALUE_TAG_END).append(" ");
        			}
	        		candidateEmployers.setIsCurrentPosition(jsonObject.getString("isCurrentPosition"));
	        	}
        		
        	}else{
        		if(candidateEmployers != null && candidateEmployers.getIsCurrentPosition() != null && candidateEmployers.getIsCurrentPosition() != "" && !candidateEmployers.getIsCurrentPosition().equalsIgnoreCase("N")){
    				activitySummary.append("Is Current Position").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getIsCurrentPosition()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append("N").append(Constants.NEW_VALUE_TAG_END).append(" ");
    			}
        		candidateEmployers.setIsCurrentPosition("N");
        	}
        	if(jsonObject.has("startYear") && !(jsonObject.getString("startYear").isEmpty())){
        		if(candidateEmployers != null && candidateEmployers.getStartYear() != null && candidateEmployers.getStartYear() != "" && !candidateEmployers.getStartYear().equalsIgnoreCase(jsonObject.getString("startYear"))){
    				activitySummary.append("Start Year").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getStartYear()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("startYear")).append(Constants.NEW_VALUE_TAG_END).append(" ");
    			}
 				   candidateEmployers.setStartYear(jsonObject.getString("startYear"));
 				  // candidateEmployers.setStartMonth(jsonObject.getString("startMonth").split("&")[1]);
        	}
        	if(jsonObject.has("endYear") && !(jsonObject.getString("endYear").isEmpty()) && !"Y".equals(candidateEmployers.getIsCurrentPosition())){
        		if(candidateEmployers != null && candidateEmployers.getEndYear() != null && candidateEmployers.getEndYear() != "" && !candidateEmployers.getEndYear().equalsIgnoreCase(jsonObject.getString("endYear"))){
    				activitySummary.append("End Year").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getEndYear()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("endYear")).append(Constants.NEW_VALUE_TAG_END).append(" ");
    			}   
        		candidateEmployers.setEndYear(jsonObject.getString("endYear"));
  				   //candidateEmployers.setEndMonth(jsonObject.getString("endMonth").split("&")[1]);
        	}
        	candidateEmployers.setStatus(Constants.CANDIDATE_ACTIVE);
        	candidateEmployers.setCreatedBy(user.getUserCode());
        	candidateEmployers.setOrgLocationId(orgLocationId);
        	candidateEmployers.setCreatedDate(commonUtil.currentDate());
        	candidateEmployers.setUpdatedBy(user.getUserCode());
        	candidateEmployers.setUpdatedDate(commonUtil.currentDate());
		}
		
        candidateEmployers = candidateEmployersDao.storeCandidateEmployers(candidateEmployers);
        candidateActivityCommonUtil.storeCandidateEmployersHistory(candidateEmployers, user);
        if(null != currentEmployer){
        	candidateEmployersDao.storeCandidateEmployers(currentEmployer);
        	candidateActivityCommonUtil.storeCandidateEmployersHistory(currentEmployer, user);
        }
        
        candidateActivityCommonUtil.storeCandidateActivity(user, orgLocationId, can, "", Constants.CANDIDATE_EMPLOYERS_UPDATED, activitySummary.toString(), "CANDIDATE");
		
		/*
		 * updating Candidate Employer DB data in existing candidateindex
		 */
		saveOrUpdateCandidateElkDocumentWithEmployerDetails(candidateId,orgLocationId.getId());
		
		return candidateEmployers;
	}
	
	/**
	 * This method is used for remove Candidate Employer By candidateId and Candidate Employer ID
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param  candidateId  This is the first parameter of removeCandidateEmployerBycandidateIdandCandidateEmployerID
	 * @param candidateEmployerId  This is the second parameter of removeCandidateEmployerBycandidateIdandCandidateEmployerID
	 *
	 * @return CandidateEmployers   removeCandidateEmployerBycandidateIdandCandidateEmployerID
	 * 
	 * @exception NullPointerException
	 * 
	 */
	@Override
	public CandidateEmployers   removeCandidateEmployerBycandidateIdandCandidateEmployerID(Long candidateId, Long candidateEmployerId) throws Exception {
		
		 CandidateEmployers can = candidateEmployersDao.getCandidateEmployerBycandidateIdandCandidateEmployerID(candidateEmployerId, candidateId);
		 can.setStatus(Constants.CANDIDATE_IN_ACTIVE);
		 can = candidateEmployersDao.storeCandidateEmployers(can);
	 
	     /*
		 * updating Candidate Employer DB data in existing candidateindex
		 */
		 saveOrUpdateCandidateElkDocumentWithEmployerDetails(candidateId,can.getOrgLocationId().getId());
			
	    return can;
	}

	
	/**
	 * This method is used for display the CandidateEmployers list
	 * 
	 * @author Ajith Matta
	 * 
	 * @param candidateId    This is the first parameter of getCandidatesEmployersListByCandidateIdAndOrgLocationID
	 * @param orgLocationId  This is the second parameter of getCandidatesEmployersListByCandidateIdAndOrgLocationID
	 * 
	 * @return String
	 * 
	 * @throws Exception
    */
	@Override
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long loc) throws Exception {
		
		 return candidateEmployersDao.getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateId, loc);
	}

	
	
	/**
	 * This method is used for display the get Candidate Employers Details list
	 * 
	 * @author Sammeta David 
	 * 
	 * @param  jsonObj This is the first parameter of getCandidateEmployersDetails
	 * @param orgLocations This is the second parameter of getCandidateEmployersDetails
	 * @param candidate    This is the third parameter of getCandidateEmployersDetails
	 * @param userCode     This is the fourth parameter of getCandidateEmployersDetails
	 * 
	 * @return HashSet<CandidateEmployers>
	 * 
	 * @throws Exception
    */
	@Override
	public  HashSet<CandidateEmployers> getCandidateEmployersDetails(CandidateDTO candidateDTO,OrgLocations orgLocations,Candidate candidate,String userCode)throws JSONException{
		  HashSet<CandidateEmployers> candidateEmployersList = new HashSet<CandidateEmployers>();
		  
		  if(StringUtils.isNotBlank(candidateDTO.getCandidateEmployers())){
	    	  String employers = candidateDTO.getCandidateEmployers();
	    	  if(employers != null && !employers.isEmpty() ){
		    	  employers = employers.replaceAll("\\[", "").replaceAll("\\]","");
		  	      String[] candidateEmployers = employers.split(",");
		    	  for (String emps : candidateEmployers) {
		    		  CandidateEmployers cemp = new  CandidateEmployers();
		    		  emps = emps.replace("\"", "");
		    		  cemp.setName(emps);
		    		  cemp.setCandidateId(candidate);
		    		  cemp.setOrgLocationId(orgLocations);
		    		  cemp.setStatus(Constants.CANDIDATE_ACTIVE);
		    		  candidateEmployersList.add(cemp);
		    	  }
	    	  }  
	    }
		 
		return candidateEmployersList;
	 }

	
	/**
	 * This method is used for display the get Candidate Employer By candidateId and CandidateEmployerID 
	 * 
	 * @author Sammeta David Raju
	 * 
	 * @param candidateId
	 * @param candidateEmployerId
	 * 
	 * @return CandidateEmployers
	 * 
	 * @throws Exception
    */
	
	@Override
	public CandidateEmployers getCandidateEmployerBycandidateIdandCandidateEmployerID(Long candidateId, Long candidateEmployerId)
			throws Exception {
		  return candidateEmployersDao.getCandidateEmployerBycandidateIdandCandidateEmployerID(candidateEmployerId, candidateId);	}

	@Override
    @Async
	public void saveOrUpdateCandidateElkDocumentWithEmployerDetails(Long candidateId,Long locationId) throws Exception {
		if(null != candidateId){
			CandidateIndex candidateIndex = new CandidateIndex();
			candidateIndex.setCandidateId(candidateId);
			ArrayList<CandidateEmployers> employerList =(ArrayList<CandidateEmployers>) getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateId,locationId);
			if(null != employerList && !employerList.isEmpty()){
				ArrayList<String> empList = new ArrayList<>();
				for(CandidateEmployers employer :employerList){
					empList.add(employer.getName());
				}
				candidateIndex.setEmployer(empList);
			}
			sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidateId.toString());
		}
	}

	@Override
	public CandidateEmployers getCandidateCurrentEmployer(Long candidateId, Long orgLocationId) throws Exception {
		return candidateEmployersDao.getCandidateCurrentEmployer(candidateId, orgLocationId);
	}

	@Override
	public CandidateEmployers getCandidateLastCompanyEmployer(Long candidateId, Long orgLocationId) throws Exception {
		return candidateEmployersDao.getCandidateLastCompanyEmployer(candidateId, orgLocationId);
	}

}
