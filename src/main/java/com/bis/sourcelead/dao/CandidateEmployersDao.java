package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface CandidateEmployersDao {

	public CandidateEmployers storeCandidateEmployers(CandidateEmployers candidateEmployers) throws Exception;
	
	public List<CandidateEmployers>   getCandidatesEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;

	public CandidateEmployers getCandidateEmployerBycandidateIdandCandidateEmployerID(Long candidateEmployersId,Long candidateId);
	
	CandidateEmployers getCandidateCurrentEmployer(Long candidateId, Long orgLocationId) throws Exception;
	
	CandidateEmployers getCandidateEmployerById(Long candidateEmployersId) throws Exception;
	
	CandidateEmployers getCandidateLastCompanyEmployer(Long candidateId, Long orgLocationId) throws Exception;
	
	public List<CandidateEmployers> getCandidatesEmployersListByCandidateIdAndOrgLocationIDImmigration(Long candidateId, String searchVal) throws Exception;
	
	public Integer getCandidateActiveEmployersCountByCandidateId(Long candidateId);
	
	public List<CandidateEmployers>   getCandidatesGlobalEmployersListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
}
