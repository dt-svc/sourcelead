package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateReferencesDao;
import com.bis.sourcelead.dto.CandidateReferencesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
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
	
	 @Autowired
	 private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;	

	@Override
	public CandidateReferences storeCandidateReferences(CandidateReferencesDTO candidateReferencesDTO,Long candidateId,Long orgLocationId,User user) throws Exception {
		
		CandidateReferences candidateReferences = null;
		Candidate candidate = new Candidate();
		OrgLocations orgLocations = new OrgLocations();
		StringBuffer activitySummary = new StringBuffer();
		if(candidateId !=null){
        	if(StringUtils.isNotBlank(candidateReferencesDTO.getRefId())){
	       	   candidateReferences = candidateReferencesDao.getCandidateReferenceByReferenceId(Long.valueOf(candidateReferencesDTO.getRefId()));
       	    }else{
       	    	candidateReferences = new CandidateReferences();
       	    }
		candidate.setId(candidateId);
		candidateReferences.setCandidateId(candidate);
		
		orgLocations.setId(orgLocationId);
		candidateReferences.setOrgLocationId(orgLocations);
		
		if(StringUtils.isNotBlank(candidateReferencesDTO.getName())){
			if(candidateReferences.getName() != null && candidateReferences.getName() != "" && !candidateReferences.getName().equalsIgnoreCase(candidateReferencesDTO.getName())){
				activitySummary.append("Name").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateReferencesDTO.getName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else{
				activitySummary.append("Name").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateReferencesDTO.getName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setName(candidateReferencesDTO.getName());
		}
			
		if(StringUtils.isNotBlank(candidateReferencesDTO.getEmail())){
			if(candidateReferences.getEmail() != null && candidateReferences.getEmail() != "" && !candidateReferences.getEmail().equalsIgnoreCase(candidateReferencesDTO.getEmail())){
				activitySummary.append("Email").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getEmail()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateReferencesDTO.getEmail()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else{
				activitySummary.append("Email").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateReferencesDTO.getEmail()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setEmail(candidateReferencesDTO.getEmail());
		}
		
		if(StringUtils.isNotBlank(candidateReferencesDTO.getMobilePhone())){
			if(candidateReferences.getMobilePhone() != null && candidateReferences.getMobilePhone() != "" && !candidateReferences.getMobilePhone().equalsIgnoreCase(candidateReferencesDTO.getMobilePhone())){
				activitySummary.append("Mobile Phone").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getMobilePhone()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateReferencesDTO.getMobilePhone()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setMobilePhone(candidateReferencesDTO.getMobilePhone());
		}else{
			candidateReferences.setMobilePhone("");
		}
		
		if(StringUtils.isNotBlank(candidateReferencesDTO.getRelation())){
			if(candidateReferences.getRelation() != null && candidateReferences.getRelation() != "" && !candidateReferences.getRelation().equalsIgnoreCase(candidateReferencesDTO.getRelation())){
				activitySummary.append("Relation").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getRelation()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateReferencesDTO.getRelation()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setRelation(candidateReferencesDTO.getRelation());
		}else{
			candidateReferences.setRelation("");
		}
		
		if(StringUtils.isNotBlank(candidateReferencesDTO.getDesignation())){
			if(candidateReferences.getDesignation() != null && candidateReferences.getDesignation() != "" && !candidateReferences.getDesignation().equalsIgnoreCase(candidateReferencesDTO.getDesignation())){
				activitySummary.append("Designation").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getDesignation()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateReferencesDTO.getDesignation()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setDesignation(candidateReferencesDTO.getDesignation());
		}else{
			candidateReferences.setDesignation("");
		}
		
		if(StringUtils.isNotBlank(candidateReferencesDTO.getComment())){
			if(candidateReferences.getComment() != null && candidateReferences.getComment() != "" && !candidateReferences.getComment().equalsIgnoreCase(candidateReferencesDTO.getComment())){
				activitySummary.append("Comment").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidateReferences.getComment()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateReferencesDTO.getComment()).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}
			candidateReferences.setComment(candidateReferencesDTO.getComment());
		}else{
			candidateReferences.setComment("");
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
		
		//saveOrUpdateCandidateElkDocumentWithReferenceDetails(candidateReferences.getCandidateId(),orgLocationId);
		
		return candidateReferences;
	
	}
	
	@Override
    @Async
	public void saveOrUpdateCandidateElkDocumentWithReferenceDetails(Candidate candidate,Long locationId) throws Exception {
		if(null != candidate && null != candidate.getId()){
			    CandidateIndex candidateIndex = new CandidateIndex();
			    candidateIndex.setCandidateId(candidate.getId());
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				candidateIndex.setUpdatedDate(dateFormat.format(new Date()));
			    sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
		}
	}

	@Override
	public ResponseList<CandidateReferencesDTO> getCandidateReferencesListByCandidateIdAndOrgLocationID(Long candidateId,
			Long orgLocationId, String loginUserCode) throws Exception {
		List<CandidateReferences> referList =  candidateReferencesDao.getCandidateReferencesListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
		ResponseList<CandidateReferencesDTO> responseReferList = new ResponseList<>();
		List<CandidateReferencesDTO> referDTOLIst = new ArrayList<>();
		for(CandidateReferences candiRefer : referList){
			CandidateReferencesDTO refer = new CandidateReferencesDTO();
			refer.setId(candiRefer.getId());
			refer.setRefId(candiRefer.getId().toString());
			refer.setName(candiRefer.getName());
			refer.setEmail(candiRefer.getEmail());
			refer.setMobilePhone(candiRefer.getMobilePhone());
			refer.setRelation(candiRefer.getRelation());
			refer.setComment(candiRefer.getComment());
			refer.setDesignation(candiRefer.getDesignation());
			refer.setCandidateId(candidateId);
			refer.setOrgLocationId(orgLocationId);
			refer.setUserCode(loginUserCode);
			referDTOLIst.add(refer);
		}
		responseReferList.setData(referDTOLIst);
 		return responseReferList;
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

	@Override
	public Boolean validateReferenceEmail(String referenceEmail, Long candidateId,Long candidateReferenceId) throws Exception {
		return candidateReferencesDao.validateReferenceEmail(referenceEmail,candidateId,candidateReferenceId);
	}

	@Override
	public CandidateReferences validateReferenceEmailByReferEmailAndReferId(String referEmail, Long referId) throws Exception {
		return candidateReferencesDao.validateReferenceEmailByReferEmailAndReferId(referEmail, referId);
	}

	@Override
	public Boolean validateReferenceEmailByReferEmailAndCandidateId(String referenceEmail, Long candidateId) throws Exception {
		return candidateReferencesDao.validateReferenceEmailByReferEmailAndCandidateId(referenceEmail, candidateId);
	}

}
