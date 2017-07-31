package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateReferencesDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateReferences;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateReferencesService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class CandidateReferencesServiceImpl implements CandidateReferencesService {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateReferencesServiceImpl.class);
	
	
	@Autowired
	private CandidateReferencesDao candidateReferencesDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
    CandidateActivityCommonUtil candidateActivityCommonUtil; 

	@Override
	public CandidateReferences storeCandidateReferences(JSONObject jsonObj,Long candidateId,Long orgLocationId,User user) throws JSONException, Exception {
		
		CandidateReferences candidateReferences = null;
		Candidate candidate = new Candidate();
		OrgLocations orgLocations = new OrgLocations();
		StringBuffer activitySummary = new StringBuffer();
		if(candidateId!=null){
        	if(jsonObj.has("refId")){
	       		 if(jsonObj.getString("refId")!=null && !jsonObj.getString("refId").isEmpty()){
	       			candidateReferences = candidateReferencesDao.getCandidateReferenceByReferenceId(Long.valueOf(jsonObj.getString("refId")));
	       	     }
       	    }else{
       	    	candidateReferences = new CandidateReferences();
       	    }
		candidate.setId(candidateId);
		candidateReferences.setCandidateId(candidate);
		
		orgLocations.setId(orgLocationId);
		candidateReferences.setOrgLocationId(orgLocations);
		
		if(jsonObj.has("name") && jsonObj.getString("name") !=null){
			if(candidateReferences.getName() != null && candidateReferences.getName() != "" && !candidateReferences.getName().equalsIgnoreCase(jsonObj.getString("name"))){
				activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("name")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else{
				activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("name")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setName(jsonObj.getString("name"));
		}
			
		if(jsonObj.has("email") && jsonObj.getString("email") !=null){
			if(candidateReferences.getEmail() != null && candidateReferences.getEmail() != "" && !candidateReferences.getEmail().equalsIgnoreCase(jsonObj.getString("email"))){
				activitySummary.append("Email").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getEmail()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("email")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else{
				activitySummary.append("Email").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("email")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setEmail(jsonObj.getString("email") );
		}
		
		if(jsonObj.has("mobilePhone") && jsonObj.getString("mobilePhone") !=null){
			if(candidateReferences.getMobilePhone() != null && candidateReferences.getMobilePhone() != "" && !candidateReferences.getMobilePhone().equalsIgnoreCase(jsonObj.getString("mobilePhone"))){
				activitySummary.append("Mobile Phone").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getMobilePhone()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("mobilePhone")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setMobilePhone(jsonObj.getString("mobilePhone"));
		}
		
		if(jsonObj.has("relation") && jsonObj.getString("relation") !=null){
			if(candidateReferences.getRelation() != null && candidateReferences.getRelation() != "" && !candidateReferences.getRelation().equalsIgnoreCase(jsonObj.getString("relation"))){
				activitySummary.append("Relation").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getRelation()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("relation")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setRelation(jsonObj.getString("relation"));
		}
		
		if(jsonObj.has("designation") && jsonObj.getString("designation") !=null){
			if(candidateReferences.getDesignation() != null && candidateReferences.getDesignation() != "" && !candidateReferences.getDesignation().equalsIgnoreCase(jsonObj.getString("designation"))){
				activitySummary.append("Designation").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getDesignation()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("designation")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setDesignation(jsonObj.getString("designation"));
		}
		
		if(jsonObj.has("comment") && jsonObj.getString("comment") !=null){
			if(candidateReferences.getComment() != null && candidateReferences.getComment() != "" && !candidateReferences.getComment().equalsIgnoreCase(jsonObj.getString("comment"))){
				activitySummary.append("Comment").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getComment()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(jsonObj.getString("comment")).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setComment(jsonObj.getString("comment"));
		}
		
		candidateReferences.setStatus(Constants.CANDIDATE_ACTIVE);
		candidateReferences.setCreatedBy(user.getUserCode());
		candidateReferences.setCreatedDate(commonUtil.currentDate());
		candidateReferences.setUpdatedBy(user.getUserCode());
		candidateReferences.setUpdatedDate(commonUtil.currentDate());
		}
		candidateReferences = candidateReferencesDao.storeCandidateReferences(candidateReferences);
		
		candidateActivityCommonUtil.storeCandidateReferencesHistory(candidateReferences, user);
		
		candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_REFERENCES_UPDATED, activitySummary.toString(), "CANDIDATE");
		
		return candidateReferences;
	
	}

	@Override
	public List<CandidateReferences> getCandidateReferencesListByCandidateIdAndOrgLocationID(Long candidateId,
			Long orgLocationId) throws Exception {
		return candidateReferencesDao.getCandidateReferencesListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
	}

	@Override
	public CandidateReferences getCandidateReferencesByCandidateIdAndReferenceId(Long candidateId,Long refId) throws Exception {
		return candidateReferencesDao.getCandidateReferencesByCandidateIdAndReferenceId(candidateId,refId);
	}

	@Override
	public CandidateReferences updateCandidateReferences(CandidateReferences candidateReferences) throws Exception {
		return candidateReferencesDao.updateCandidateReferences(candidateReferences);
	}

	@Override
	public CandidateReferences getCandidateReferenceByReferenceId(Long refId) throws Exception {
		return candidateReferencesDao.getCandidateReferenceByReferenceId(refId);
	}

}
