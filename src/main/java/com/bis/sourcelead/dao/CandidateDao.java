package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Team;

public interface CandidateDao {

	
	public Candidate storeCandidate(Candidate candidate) throws Exception;
	
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception;
	
	public Long getCandidatesCountByOrgLocationID(Long orgLocationId);
	
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId, Integer candidateState,String updatedDateRange,String candidateType) throws Exception;
	
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	public Candidate findByCandidateEmail(String email,Long orgLocationId) throws Exception;
	
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId,Long requirementId, String candidateType) throws Exception;

	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId);
	
	public List<Candidate> getHotListCandidatesListByOrgLocationID(Long orgLocationId,String candidateType) throws Exception;
	
	public List<Candidate> getAllRequestedCandidatesForCampaign(List<Long> candidateId)throws Exception;
	
	public Integer getHotlistCandidatesCountByTeamIdAndOrgLocationId(Long teamId,Long orgLocationId) throws Exception;
	
	public List<Candidate> getHotListCandidatesByTeamAndOrgLocationId(Long teamId,Long orgLocationId)throws Exception;
	
	public Integer getAllActiveCandidatesCountForCampaignByLocation(Long orgLocationId,String type)throws Exception;
	
	public Integer getAllHotlistCandidatesCountNotAssignedToTeamByLocation(Long orgLocationId)throws Exception;
	
	public Integer getAllCandidatesCountForCampaignByTeam(List<Long> teamId,String type,Long orgLocationId)throws Exception;

	public List<Candidate> getAllHotlistCandidatesAndNotAssignedToTeamByLocation(Long orgLocationId)throws Exception;
	
	public List<Candidate> getAllCandidatesForHotListingWhoAreTerminating(Long orgLocationId,Integer datRange)throws Exception;

}