package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;

import org.json.JSONException;

import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CandidateGlobalService {

	public CandidateGlobal getCandidateGlobalByCandidateId(Long candidateGlobalId) throws Exception;
	
	public CandidateGlobal storeCandidateGlobal(CandidateGlobal candidateGlobal) throws Exception;
	
	HashSet<CandidateEducation> getCandidateEducationDetails(CandidateDTO candidateDTO,OrgLocations orgLocations,CandidateGlobal candidate,String userCode) throws Exception;
	
	public  HashSet<CandidateEmployers> getCandidateEmployersDetails(CandidateDTO candidateDTO,OrgLocations orgLocations,CandidateGlobal candidate,String userCode) throws JSONException;
	
	public HashSet<CandidateResumes> getCandidateResumesDetails(OrgLocations orgLocations,String userCode,CandidateGlobal candidate,FileInfo fileInfo)throws Exception;
	
	public CandidateGlobal findByCandidateGlobalEmail(String email,Long orgLocationId) throws Exception;
	
	HashSet<CandidateHistory> getCandidateGlobalHistoryDetails(CandidateGlobal candidate,User user) throws Exception;
	
	public void saveOrUpdateCandidateGlobalElkDocument(CandidateGlobal candidateGlobal) throws Exception;
	
	public void deleteCandidateGlobalElkDocument(CandidateGlobal candidateGlobal) throws Exception;
	
	public CandidateGlobal getCandidateGlobalByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	CandidateGlobal getCandidateGlobalByUUID(String candidateUUID) throws Exception ;
	
	Integer getCandidateGlobalProfilePercentageByCandidateId(Long id) throws Exception;
	
	public CandidateDTO getCandidateGlobalDTOByCandidateGlobalId(Long candidateGlobalId) throws Exception;
	
	boolean isCandidateExistsInCandidateGlobal(String email,Long orgLocationId) throws Exception;
	
	Long getCandidateGlobalGeneratedIdByOrgLocationId(Long orgLocationId, Integer generatedIdcount);
	
	public List<CandidateGlobal> getAllRequestedGlboalCandidatesForCampaign(List<Long> candidateId)throws Exception;
}
