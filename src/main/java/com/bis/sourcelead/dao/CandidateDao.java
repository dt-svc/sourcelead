package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Team;

public interface CandidateDao {

	
	public Candidate storeCandidate(Candidate candidate) throws Exception;
	
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception;
	

	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId, Integer candidateState,String updatedDateRange,String candidateType) throws Exception;
	
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	public Candidate findByCandidateEmail(String email,Long orgLocationId) throws Exception;
	
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId,Long requirementId) throws Exception;

	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId);
	
	public List<Candidate> getHotListCandidatesListByOrgLocationID(Long orgLocationId,String candidateType) throws Exception;
	
	public List<Candidate> getAllActiveCandidatesForCampaignByLocation(Long orgLocationId,String type)throws Exception;
	
	public List<Candidate> getAllCandidatesForCampaignByTeam(List<Team> teamId,String type)throws Exception;
	
}