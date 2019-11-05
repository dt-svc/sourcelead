package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateComments;
import com.bis.sourcelead.entity.OrgLocations;

/**
 * @author Venkat Kesav
 *
 */
public interface CandidateCommentsDao {
	
	public CandidateComments storeCandidateComments(CandidateComments candidateComments) throws Exception; 
	
	
	public List<CandidateComments> getAllCommentsOnCandidateByCandidateIdAndOrgLocation(Long candidateId, Long orgLocationId) throws Exception;
	
	public List<CandidateComments> getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId(Long candidateId, Long submissionId, Long orgLocationId) throws Exception;
	
	public List<CandidateComments> getAllCommentsOnCandidateGlobalByCandidateIdAndOrgLocation(Long candidateId, Long orgLocationId) throws Exception;

}
