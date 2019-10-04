package com.bis.sourcelead.dao;

import java.util.HashSet;
import java.util.List;

import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.User;

public interface CandidateGlobalDao {

	public CandidateGlobal getCandidateGlobalByCandidateId(Long candidateGlobalId) throws Exception;
	
	public CandidateGlobal storeCandidateGlobal(CandidateGlobal candidateGlobal) throws Exception;
	
	public List<CandidateEducation> getCandidateGlobalEducationListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	public CandidateGlobal findByCandidateGlobalEmail(String email,Long orgLocationId) throws Exception;
	
	HashSet<CandidateHistory> getCandidateGlobalHistoryDetails(CandidateGlobal candidate,User user) throws Exception;
	
	CandidateGlobal getCandidateGlobalByEmailAndRecruiterUserId(String email, Long recruiterUserId) throws Exception;
	
	public CandidateGlobal getCandidateGlobalByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	CandidateGlobal getCandidateGlobalByUUID(String candidateUUID) throws Exception ;
	
	Integer getCandidateGlobalProfilePercentageByCandidateId(Long id) throws Exception;
	
	 public CandidateDTO getCandidateGlobalDTOByCandidateGlobalId(Long candidateGlobalId) throws Exception;
	 
	 boolean isCandidateExistsInCandidateGlobal(String email,Long orgLocationId) throws Exception;
	 
	 Long getCandidateGlobalGeneratedIdByOrgLocationId(Long orgLocationId, Integer generatedIdcount);
	 
	 public List<CandidateGlobal> getAllRequestedGlboalCandidatesForCampaign(List<Long> candidateId)throws Exception;
}
