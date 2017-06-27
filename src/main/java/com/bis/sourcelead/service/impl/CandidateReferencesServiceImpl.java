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
import com.bis.sourcelead.util.CommonUtil;

@Service
public class CandidateReferencesServiceImpl implements CandidateReferencesService {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateReferencesServiceImpl.class);
	
	
	@Autowired
	private CandidateReferencesDao candidateReferencesDao;
	
	@Autowired
	private CommonUtil commonUtil;
 

	@Override
	public CandidateReferences storeCandidateReferences(JSONObject jsonObj,Long candidateId,Long orgLocationId,User user) throws JSONException, Exception {
		
		CandidateReferences candidateReferences = null;
		if(candidateId!=null){
        	if(jsonObj.has("refId")){
	       		 if(jsonObj.getString("refId")!=null && !jsonObj.getString("refId").isEmpty()){
	       			candidateReferences = candidateReferencesDao.getCandidateReferenceByReferenceId(Long.valueOf(jsonObj.getString("refId")));
	       	     }
       	    }else{
       	    	candidateReferences = new CandidateReferences();
       	    }
		Candidate candidate = new Candidate();
		candidate.setId(candidateId);
		candidateReferences.setCandidateId(candidate);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		candidateReferences.setOrgLocationId(orgLocations);
		
		if(jsonObj.has("name") && jsonObj.getString("name") !=null){
			candidateReferences.setName(jsonObj.getString("name"));
			}
			
		if(jsonObj.has("email") && jsonObj.getString("email") !=null){
			candidateReferences.setEmail(jsonObj.getString("email") );
		  }
		
		if(jsonObj.has("mobilePhone") && jsonObj.getString("mobilePhone") !=null){
			candidateReferences.setMobilePhone(jsonObj.getString("mobilePhone"));
			}
		
		if(jsonObj.has("relation") && jsonObj.getString("relation") !=null){
			candidateReferences.setRelation(jsonObj.getString("relation"));
			}
		
		if(jsonObj.has("designation") && jsonObj.getString("designation") !=null){
			candidateReferences.setDesignation(jsonObj.getString("designation"));
			}
		
		if(jsonObj.has("comment") && jsonObj.getString("comment") !=null){
			candidateReferences.setComment(jsonObj.getString("comment"));
			}
		
		candidateReferences.setStatus(Constants.CANDIDATE_ACTIVE);
		candidateReferences.setCreatedBy(user.getUserCode());
		candidateReferences.setCreatedDate(commonUtil.currentDate());
		candidateReferences.setUpdatedBy(user.getUserCode());
		candidateReferences.setUpdatedDate(commonUtil.currentDate());
		}
		candidateReferencesDao.storeCandidateReferences(candidateReferences);
		
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
