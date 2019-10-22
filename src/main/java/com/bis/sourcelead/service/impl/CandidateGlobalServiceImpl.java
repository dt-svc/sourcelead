package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateGlobalDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateGlobalIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateGlobalService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class CandidateGlobalServiceImpl implements CandidateGlobalService{
	
	@Autowired
	CandidateGlobalDao candidateGlobalDao;
	
	@Autowired
    private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	CandidateGlobalIndexHelper candidateGlobalIndexHelper;

	@Override
	public CandidateGlobal getCandidateGlobalByCandidateId(Long candidateGlobalId) throws Exception {
		return candidateGlobalDao.getCandidateGlobalByCandidateId(candidateGlobalId);
	}

	@Override
	public CandidateGlobal storeCandidateGlobal(CandidateGlobal candidateGlobal) throws Exception {
		return candidateGlobalDao.storeCandidateGlobal(candidateGlobal);
	}

	@Override
	public HashSet<CandidateEducation> getCandidateEducationDetails(CandidateDTO candidateDTO,
			OrgLocations orgLocations, CandidateGlobal candidate, String userCode) throws Exception {

	    HashSet<CandidateEducation> candidateEducationList = new HashSet<CandidateEducation>(); 
	    if(StringUtils.isNotBlank(candidateDTO.getCandidateEducation())){
	  	      String[] educations = candidateDTO.getCandidateEducation().split(",");
	  	    Set<String> courses = new HashSet();
	    	  for (String edu : educations) {
	    		  CandidateEducation ce = null;
	    		  edu = edu.replace("\"", "");
	    		  String eduTrim = edu.trim();
	    		  if(null != candidate.getId()){
	    		  List<CandidateEducation> eduList= candidateGlobalDao.getCandidateGlobalEducationListByCandidateIdAndOrgLocationID(candidate.getId(),orgLocations.getId());
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

	@Override
	public HashSet<CandidateEmployers> getCandidateEmployersDetails(CandidateDTO candidateDTO,
			OrgLocations orgLocations, CandidateGlobal candidate, String userCode) throws JSONException {
		
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

	@Override
	public HashSet<CandidateResumes> getCandidateResumesDetails(OrgLocations orgLocations, String userCode,
			CandidateGlobal candidate, FileInfo fileInfo) throws Exception {
	
		HashSet<CandidateResumes> candidateResumesList = new HashSet<CandidateResumes>();
		if (StringUtils.isNotBlank(fileInfo.getFileURL())) {
			CandidateResumes candidateResumes = new CandidateResumes();
			candidateResumes.setCandidateId(candidate);
			candidateResumes.setFileName(fileInfo.getFileName());
			candidateResumes.setFileContentType(fileInfo.getContentType());
			candidateResumes.setFileURL(fileInfo.getFileURL().replaceAll(" ", "_"));
			candidateResumes.setStatus(Constants.CANDIDATE_ACTIVE);
			candidateResumes.setResumeUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			candidateResumes.setCreatedDate(new Date());
			candidateResumes.setCreatedBy(userCode);
			candidateResumes.setUpdatedBy(userCode);
			candidateResumes.setUpdatedDate(new Date());
			candidateResumes.setOrgLocationId(orgLocations);
			candidateResumesList.add(candidateResumes);
		}
		return candidateResumesList;
	}

	@Override
	public CandidateGlobal findByCandidateGlobalEmail(String email, Long orgLocationId) throws Exception {
		return candidateGlobalDao.findByCandidateGlobalEmail(email, orgLocationId);
	}

	@Override
	public HashSet<CandidateHistory> getCandidateGlobalHistoryDetails(CandidateGlobal candidate, User user) throws Exception {
		return candidateGlobalDao.getCandidateGlobalHistoryDetails(candidate, user);
	}

	@Override
	public void saveOrUpdateCandidateGlobalElkDocument(CandidateGlobal candidateGlobal) throws Exception {
		CandidateIndex candidateIndex = candidateGlobalIndexHelper.getCandidateGlobalIndexFromCandidate(candidateGlobal);
		sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidateGlobal.getId().toString());
		
	}

	@Override
	public CandidateGlobal getCandidateGlobalByCandidateIdAndOrgLocationId(Long candidateId, Long orgLocationId)
			throws Exception {
		return candidateGlobalDao.getCandidateGlobalByCandidateIdAndOrgLocationId(candidateId, orgLocationId);
	}

	@Override
	public CandidateGlobal getCandidateGlobalByUUID(String candidateUUID) throws Exception {
		return candidateGlobalDao.getCandidateGlobalByUUID(candidateUUID);
	}

	@Override
	public void deleteCandidateGlobalElkDocument(CandidateGlobal candidateGlobal) throws Exception {
		CandidateIndex candidateIndex = candidateGlobalIndexHelper.getCandidateGlobalIndexFromCandidate(candidateGlobal);
		sourceleadELKBaseIndexer.deleteElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidateGlobal.getId().toString());
	}

	@Override
	public Integer getCandidateGlobalProfilePercentageByCandidateId(Long id) throws Exception {
		return candidateGlobalDao.getCandidateGlobalProfilePercentageByCandidateId(id);
	}

	@Override
	public CandidateDTO getCandidateGlobalDTOByCandidateGlobalId(Long candidateGlobalId) throws Exception {
		return candidateGlobalDao.getCandidateGlobalDTOByCandidateGlobalId(candidateGlobalId);
	}

	@Override
	public boolean isCandidateExistsInCandidateGlobal(String email, Long orgLocationId) throws Exception {
		return candidateGlobalDao.isCandidateExistsInCandidateGlobal(email, orgLocationId);
	}

	@Override
	public Long getCandidateGlobalGeneratedIdByOrgLocationId(Long orgLocationId, Integer generatedIdcount) {
		return candidateGlobalDao.getCandidateGlobalGeneratedIdByOrgLocationId(orgLocationId, generatedIdcount);
	}

	@Override
	public List<CandidateGlobal> getAllRequestedGlboalCandidatesForCampaign(List<Long> candidateId) throws Exception {
		return candidateGlobalDao.getAllRequestedGlboalCandidatesForCampaign(candidateId);
	}

}
