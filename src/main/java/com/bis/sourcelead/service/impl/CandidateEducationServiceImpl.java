package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class CandidateEducationServiceImpl implements CandidateEducationService {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateEducationServiceImpl.class);
	
	
	@Autowired
	private CandidateEducationDao candidateEducationDao;
	
	@Autowired
	private CommonUtil commonUtil;
 
	 @Autowired
	 private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 CandidateActivityCommonUtil candidateActivityCommonUtil;
	
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
		
		CandidateEducation candidateEducation = null;
		Candidate candidate = new Candidate();
		OrgLocations orgLocations = new OrgLocations();
		StringBuffer activitySummary = new StringBuffer();
		if(candidateId!=null){
        	if(jsonObject.has("candidateEduId")){
	       		 if(jsonObject.getString("candidateEduId")!=null && !jsonObject.getString("candidateEduId").isEmpty()){
	       			//candidateEducation.setId(Long.valueOf(jsonObject.getString("candidateEduId")));
	       			candidateEducation = candidateEducationDao.getCandidateEducationBycandidateIdandEducationId(candidateId, Long.valueOf(jsonObject.getString("candidateEduId")));
	       	     }
       	    }else{
       	    	candidateEducation =new CandidateEducation();
       	     }
		
		candidate.setId(candidateId);
		candidateEducation.setCandidateId(candidate);
		
		orgLocations.setId(orgLocationId);
		candidateEducation.setOrglocationId(orgLocations);
		
		
		if(jsonObject.has("name")){
			if(candidateEducation != null && candidateEducation.getName() != null && candidateEducation.getName() != "" && !candidateEducation.getName().equalsIgnoreCase(jsonObject.getString("name"))){
				activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("name")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else if(candidateEducation.getId() == null){
				activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("name")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setName(jsonObject.getString("name"));
			String educationName = jsonObject.getString("name");
			String[] educations = educationName.split(",");
			for(String edu : educations){
				candidateEducation.setName(edu);
			}
		}
		if(jsonObject.has("specialization")){
			if(candidateEducation != null && candidateEducation.getSpecialization() != null && candidateEducation.getSpecialization() != "" && !candidateEducation.getSpecialization().equalsIgnoreCase(jsonObject.getString("specialization"))){
				activitySummary.append("Specialization").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getSpecialization()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("specialization")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setSpecialization(jsonObject.getString("specialization"));
		}
		if(jsonObject.has("startDate") && !(jsonObject.getString("startDate").isEmpty()) && !" ".equals(jsonObject.get("startDate"))){
			try {
				
				String pattern = "dd-MM-yyyy";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                Date startDate = format.parse(jsonObject.getString("startDate"));
                candidateEducation.setStartDate(startDate);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (jsonObject.has("endDate") && !(jsonObject.getString("endDate").isEmpty()) && !" ".equals(jsonObject.get("endDate"))) {
			try {
				String pattern = "dd-MM-yyyy";
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                Date validTo = format.parse(jsonObject.getString("endDate"));
                candidateEducation.setEndDate(validTo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		if(jsonObject.has("grade")){
			if(candidateEducation != null && candidateEducation.getGrade() != null && candidateEducation.getGrade() != "" && !candidateEducation.getGrade().equalsIgnoreCase(jsonObject.getString("grade"))){
				activitySummary.append("Grade").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getGrade()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("grade")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setGrade(jsonObject.getString("grade"));
		}
		if(jsonObject.has("gradeType")){
			if(candidateEducation != null && candidateEducation.getGradeType() != null && candidateEducation.getGradeType() != "" && !candidateEducation.getGradeType().equalsIgnoreCase(jsonObject.getString("gradeType"))){
				activitySummary.append("Grade Type").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getGradeType()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("gradeType")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setGradeType(jsonObject.getString("gradeType"));
		}
		if(jsonObject.has("institute")){
			if(candidateEducation != null && candidateEducation.getInstitute() != null && candidateEducation.getInstitute() != "" && !candidateEducation.getInstitute().equalsIgnoreCase(jsonObject.getString("institute"))){
				activitySummary.append("Institute").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getInstitute()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("institute")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setInstitute(jsonObject.getString("institute"));
		}
		if(jsonObject.has("address")){
			if(candidateEducation != null && candidateEducation.getAddress() != null && candidateEducation.getAddress() != "" && !candidateEducation.getAddress().equalsIgnoreCase(jsonObject.getString("address"))){
				activitySummary.append("Address").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateEducation.getAddress()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObject.getString("address")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateEducation.setAddress(jsonObject.getString("address"));
		}
		candidateEducation.setStatus(Constants.CANDIDATE_ACTIVE);
		candidateEducation.setCreatedBy(user.getUserCode());
		candidateEducation.setCreatedDate(commonUtil.currentDate());
		candidateEducation.setUpdatedBy(user.getUserCode());
		candidateEducation.setUpdatedDate(commonUtil.currentDate());
		}
		candidateEducation = candidateEducationDao.storeCandidateEducation(candidateEducation);
		
		candidateActivityCommonUtil.storeCandidateEducationHistory(candidateEducation, user);
		
		candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_EDUCATION_UPDATED, activitySummary.toString(), "CANDIDATE");
		
		/*
		 * updating Candidate Education DB data in existing candidateindex
		 */
		saveOrUpdateCandidateElkDocumentWithEducationDetails(candidateEducation.getCandidateId(),orgLocationId);
		
		return candidateEducation;
	}
	
	
	/**
	 * This Method is used to get candidate education details 
	 * @author Ajith Matta
	 * 
	 * @param candidateId
	 * 
	 * @return CandidateEducation list
	 * 
	 * @throws Exception
	 */

	@Override
	public List<CandidateEducation>   getCandidateEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception{
      
		List<CandidateEducation> edulist = candidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
		
		for(CandidateEducation education :edulist){
			if(education.getStartDate() != null){
			education.setStartDateStr(Constants.DF_DMY.format(education.getStartDate()));
			}
			if(education.getEndDate() != null){
			education.setEndDateStr(Constants.DF_DMY.format(education.getEndDate()));
			}
		}
		return edulist;
	}

	@Override
	public HashSet<CandidateEducation> getCandidateEducationDetails(JSONObject jsonObj,OrgLocations orgLocations,Candidate candidate,String userCode) throws Exception {
		    HashSet<CandidateEducation> candidateEducationList = new HashSet<CandidateEducation>(); 
		    if(jsonObj.has("candidateEducation")){
		    	  String education = null;
		    	  try{
		    		   JSONArray jsonArray = jsonObj.getJSONArray("candidateEducation");
		    		   education = jsonArray.toString();
		    	  }
		    	  catch(JSONException e){
		    		  education= jsonObj.getString("candidateEducation");
		    	  }
		    	  education = education.replaceAll("\\[", "").replaceAll("\\]","");
		  	      String[] educations = education.split(",");
		  	    Set<String> courses = new HashSet();
		    	  for (String edu : educations) {
		    		  /*CandidateEducation ce = new  CandidateEducation();
		    		  edu = edu.replace("\"", "");*/
		    		  CandidateEducation ce = null;
		    		  edu = edu.replace("\"", "");
		    		  String eduTrim = edu.trim();
		    		  if(null != candidate.getId()){
		    		  List<CandidateEducation> eduList= candidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(candidate.getId(),orgLocations.getId());
		    			if(courses.size() != eduList.size()){
		    				for(CandidateEducation candidateEducation :eduList){
			    				String DbEdu = candidateEducation.getName().replace("\"", "");
			    				courses.add(DbEdu.trim());
			    			}
		    			}
	    			  if(courses.add(eduTrim)){
	    				  ce = new  CandidateEducation();
	    				  if(null !=ce){
			    			  ce.setName(edu);
				    		  ce.setCandidateId(candidate);
				    		  ce.setOrglocationId(orgLocations);
				    		  ce.setStatus(Constants.CANDIDATE_ACTIVE);
				    		  candidateEducationList.add(ce);
			    		  }
	    			  }
		    		  }else{
		    			  ce = new  CandidateEducation();
		    			  if(null !=ce){
			    			  ce.setName(edu);
				    		  ce.setCandidateId(candidate);
				    		  ce.setOrglocationId(orgLocations);
				    		  ce.setStatus(Constants.CANDIDATE_ACTIVE);
				    		  candidateEducationList.add(ce);
			    		  }
		    		  }
		    		  
		    		
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
			Long candidateEducationId, User user, OrgLocations orgLocations) throws Exception {
			CandidateEducation can = candidateEducationDao.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
		     can.setStatus(Constants.CANDIDATE_IN_ACTIVE);
		     can =  candidateEducationDao.storeCandidateEducation(can);
		     StringBuffer activitySummary = new StringBuffer();
		     activitySummary.append("Education").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(can.getName()).append(Constants.NEW_VALUE_TAG_END);
			 candidateActivityCommonUtil.storeCandidateEducationHistory(can, user);
			 candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, can.getCandidateId(), "", Constants.CANDIDATE_DELETED_EDUCATION, activitySummary.toString(), "CANDIDATE");
				
		        /*
				 * updating Candidate Skills DB data in existing candidateindex
				 */
				saveOrUpdateCandidateElkDocumentWithEducationDetails(can.getCandidateId(),can.getOrglocationId().getId());
		     
		     return can;
	}
	
	/**
	 * @author Prasad Salina
	 * @param candidateId
	 * @throws Exception 
	 */
	@Override
	public CandidateEducation getCandidateEducationByCandidateId(Long candidateId) throws Exception {
		return candidateEducationDao.getCandidateEducationByCandidateId(candidateId);
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
			Long candidateEducationId) throws Exception {
		  return candidateEducationDao.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
	}
	
	@Override
	public Boolean validateEducationName(Long candidateId, String candidateEducation) throws Exception {
		return candidateEducationDao.validateEducationName(candidateId, candidateEducation);
	}

	
	@Override
    @Async
	public void saveOrUpdateCandidateElkDocumentWithEducationDetails(Candidate candidate,Long locationId) throws Exception {
		if(null != candidate && null != candidate.getId()){
			CandidateIndex candidateIndex = new CandidateIndex();
			candidateIndex.setCandidateId(candidate.getId());
			ArrayList<CandidateEducation> educationList =(ArrayList<CandidateEducation>) getCandidateEducationListByCandidateIdAndOrgLocationID(candidate.getId(),locationId);
			if(null != educationList && !educationList.isEmpty()){
				ArrayList<String> eduList = new ArrayList<>();
				for(CandidateEducation edu :educationList){
					eduList.add(edu.getName());
				}
				candidateIndex.setEducation(eduList);
			}
			sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
		}
	}


	@Override
	public List<String> getCandidateEducationNamesByCandidateId(Long candidateId) throws Exception {
		return candidateEducationDao.getCandidateEducationNamesByCandidateId(candidateId);
	}

}
