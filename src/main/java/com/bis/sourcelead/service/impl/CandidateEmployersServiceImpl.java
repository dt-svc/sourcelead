package com.bis.sourcelead.service.impl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateEmployerDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateEmployersHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;

/**
 * @author Ajith Matta
 *
 */

@Service
public class CandidateEmployersServiceImpl implements CandidateEmployersService {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateEmployersServiceImpl.class);
	
	@Autowired
	CandidateEmployersDao candidateEmployersDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Value("${static.path}")
	private String staticPath;
	
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
	public CandidateEmployers storeCandidateEmployers(CandidateEmployerDTO candidateEmployerDTO, User user, OrgLocations orgLocationId,Long candidateId) throws Exception {

        CandidateEmployers candidateEmployers = null;
        CandidateEmployers currentEmployer = null;
        Candidate can  = new Candidate();
        StringBuffer activitySummary = new StringBuffer();
        if(candidateId!=null){
        	if(StringUtils.isNotBlank(candidateEmployerDTO.getCandidateEmployeeId())){
	       			candidateEmployers = candidateEmployersDao.getCandidateEmployerBycandidateIdandCandidateEmployerID(Long.parseLong(candidateEmployerDTO.getCandidateEmployeeId()), candidateId);
	       			// candidateEmployers.setId(Long.valueOf(jsonObject.getString("candidateEmployeeId")));
       	    }else{
    	    	candidateEmployers = new CandidateEmployers();
    	    }
        	
        	if(candidateEmployers != null && candidateEmployers.getName() != null && candidateEmployers.getName() != "" && !candidateEmployers.getName().equalsIgnoreCase(candidateEmployerDTO.getName())){
				activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEmployerDTO.getName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if(candidateEmployers.getId() == null){
				activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEmployerDTO.getName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
        	candidateEmployers.setName(candidateEmployerDTO.getName());
        	can.setId(candidateId);
        	candidateEmployers.setCandidateId(can);
        	if(StringUtils.isNotBlank(candidateEmployerDTO.getAddress())){
        	if(candidateEmployers != null && candidateEmployers.getAddress() != null){
				activitySummary.append("Address").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getAddress()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEmployerDTO.getAddress()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
        	candidateEmployers.setAddress(candidateEmployerDTO.getAddress());
        	}else{
        	candidateEmployers.setAddress("");
        	}
        	if(StringUtils.isNotBlank(candidateEmployerDTO.getDesignation())){
        	if(candidateEmployers != null && candidateEmployers.getDesignation() != null ){
				activitySummary.append("Designation").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getDesignation()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEmployerDTO.getDesignation()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
        	candidateEmployers.setDesignation(candidateEmployerDTO.getDesignation());
        	}else{
        	candidateEmployers.setDesignation("");
        	}
        	
        		if(StringUtils.isNotBlank(candidateEmployerDTO.getIsCurrentPosition())){
        			if(candidateEmployers != null && candidateEmployers.getIsCurrentPosition() != null && candidateEmployers.getIsCurrentPosition() != "" && !candidateEmployers.getIsCurrentPosition().equalsIgnoreCase(candidateEmployerDTO.getIsCurrentPosition())){
        				activitySummary.append("Is Current Position").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getIsCurrentPosition()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEmployerDTO.getIsCurrentPosition()).append(Constants.NEW_VALUE_TAG_END).append(" ");
        			}
	        		candidateEmployers.setIsCurrentPosition(candidateEmployerDTO.getIsCurrentPosition());
	        	}
        		
        	else{
        		if(candidateEmployers != null && candidateEmployers.getIsCurrentPosition() != null && candidateEmployers.getIsCurrentPosition() != "" && !candidateEmployers.getIsCurrentPosition().equalsIgnoreCase("N")){
    				activitySummary.append("Is Current Position").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getIsCurrentPosition()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append("N").append(Constants.NEW_VALUE_TAG_END).append(" ");
    			}
        		candidateEmployers.setIsCurrentPosition("N");
        	}
        	
        	
        	if(StringUtils.isNotBlank(candidateEmployerDTO.getStartMonth())){
        		
        		if(candidateEmployers != null && candidateEmployers.getStartMonth() != null && candidateEmployers.getStartMonth() != "" && !candidateEmployers.getStartMonth().equalsIgnoreCase(candidateEmployerDTO.getStartMonth())){
    				activitySummary.append("Start Month").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getStartMonth()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEmployerDTO.getStartMonth()).append(Constants.NEW_VALUE_TAG_END).append(" ");
    			}
 				   candidateEmployers.setStartMonth(candidateEmployerDTO.getStartMonth());
        		
        	}else{
        		candidateEmployers.setStartMonth("");
        	  }
        	
        	if(StringUtils.isNotBlank(candidateEmployerDTO.getStartYear())){
        		if(candidateEmployers != null && candidateEmployers.getStartYear() != null && candidateEmployers.getStartYear() != "" && !candidateEmployers.getStartYear().equalsIgnoreCase(candidateEmployerDTO.getStartYear())){
    				activitySummary.append("Start Year").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getStartYear()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEmployerDTO.getStartYear()).append(Constants.NEW_VALUE_TAG_END).append(" ");
    			}
 				   candidateEmployers.setStartYear(candidateEmployerDTO.getStartYear());
 				  // candidateEmployers.setStartMonth(jsonObject.getString("startMonth").split("&")[1]);
        	}else{
        		 candidateEmployers.setStartYear("");
        	}
        	
        	
        	
        	if(StringUtils.isNotBlank(candidateEmployerDTO.getEndYear()) && !"Y".equals(candidateEmployers.getIsCurrentPosition())){
        		if(candidateEmployers != null && candidateEmployers.getEndYear() != null && candidateEmployers.getEndYear() != "" && !candidateEmployers.getEndYear().equalsIgnoreCase(candidateEmployerDTO.getEndYear())){
    				activitySummary.append("End Year").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getEndYear()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEmployerDTO.getEndYear()).append(Constants.NEW_VALUE_TAG_END).append(" ");
    			}   
        		candidateEmployers.setEndYear(candidateEmployerDTO.getEndYear());
  				   //candidateEmployers.setEndMonth(jsonObject.getString("endMonth").split("&")[1]);
        	}else{
       		 candidateEmployers.setEndYear("");
        	}
        	
            if(StringUtils.isNotBlank(candidateEmployerDTO.getEndMonth())){
        		
        		if(candidateEmployers != null && candidateEmployers.getEndMonth() != null && candidateEmployers.getEndMonth() != "" && !candidateEmployers.getEndMonth().equalsIgnoreCase(candidateEmployerDTO.getEndMonth())){
    				activitySummary.append("End Month").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEmployers.getEndMonth()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateEmployerDTO.getEndMonth()).append(Constants.NEW_VALUE_TAG_END).append(" ");
    			}
 				   candidateEmployers.setEndMonth(candidateEmployerDTO.getEndMonth());
        		
        	}else{
        		candidateEmployers.setEndMonth("");
        	  }
        	
            if(candidateEmployerDTO.getFileInfo() != null){
    			FileInfo fileinfo = candidateEmployerDTO.getFileInfo().get(0);
    			candidateEmployers.setEmployerFileUrl(fileinfo.getFileURL());
    			candidateEmployers.setEmployerFileName(fileinfo.getFileName());
    			candidateEmployers.setEmployerFileContentType(fileinfo.getContentType());
    			FileUploadUtil.moveFile(candidateEmployerDTO.getStaticPath(), fileinfo, "candidateDocuments");
    			
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
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationIDImmigration(Long candidateId, String searchVal) throws Exception {
		
		List<CandidateEmployers> ss = candidateEmployersDao.getCandidatesEmployersListByCandidateIdAndOrgLocationIDImmigration(candidateId, searchVal);
		for(CandidateEmployers exp:ss) {
			if(StringUtils.isNotBlank(exp.getStartMonth())) {
			String startMonth = exp.getStartMonth().substring(exp.getStartMonth().lastIndexOf('&') + 1);
			exp.setStartMonth(startMonth);
			}
			if(StringUtils.isNotBlank(exp.getEndMonth())) {
			String endMonth = exp.getEndMonth().substring(exp.getEndMonth().lastIndexOf('&') + 1);
			exp.setEndMonth(endMonth);
			}
		}
		 return ss;
	}

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
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				candidateIndex.setUpdatedDate(dateFormat.format(new Date()));
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

	@Override
	public String removeCandidateEmployerDocById(Long candidateEmployerId) throws Exception {
		CandidateEmployers candidateEmployers = candidateEmployersDao.getCandidateEmployerById(candidateEmployerId);
		if(candidateEmployers != null){
		try {
			String rootPath = StringUtils.removeEnd(staticPath, staticPath.substring(staticPath.lastIndexOf("/")));
			File file = new File(rootPath + candidateEmployers.getEmployerFileUrl());
			
			if (file.delete()) {
				logger.debug(file.getName() + " is deleted!");
			} else {
				logger.error(file.getName() + " is deleted!");
			}
				candidateEmployers.setEmployerFileName(null);
				candidateEmployers.setEmployerFileUrl(null);
				candidateEmployers.setEmployerFileContentType(null);
			
			candidateEmployersDao.storeCandidateEmployers(candidateEmployers);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		return "success";
	}

	@Override
	public Integer getCandidateActiveEmployersCountByCandidateId(Long candidateId) {
		return candidateEmployersDao.getCandidateActiveEmployersCountByCandidateId(candidateId);
	}

	@Override
	public HashSet<CandidateEmployers> getCandidateEmployersDetailsList(CandidateDTO candidateDTO,
			OrgLocations orgLocations, Candidate candidate, User user) throws Exception {
		
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
		    		  
    		        HashSet<CandidateEmployersHistory> candidateEmployersHistorySet = new HashSet<CandidateEmployersHistory>();
    				CandidateEmployersHistory candidateEmployersHistory = new CandidateEmployersHistory();
    				candidateEmployersHistory.setCandidateEmployers(cemp);
    				candidateEmployersHistory.setName(emps);
    				candidateEmployersHistory.setCreatedUser(user);
    				candidateEmployersHistory.setCreatedBy(user.getUserCode());
    				candidateEmployersHistory.setCreatedDate(commonUtil.currentDate());
    				candidateEmployersHistorySet.add(candidateEmployersHistory);
    				cemp.setCandidateEmployersHistoryList(candidateEmployersHistorySet);	
	    		    candidateEmployersList.add(cemp);
		    	  }
	    	  }  
	    }
		 
		return candidateEmployersList;
	}

}
