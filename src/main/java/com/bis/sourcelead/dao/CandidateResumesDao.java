package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.CandidateResumes;

/**
 * @author Ajith Matta
 *
 */
public interface CandidateResumesDao {
	
	public CandidateResumes storeCandidateResumes(CandidateResumes candidateResumes) throws Exception; 
	
	public List<CandidateResumes> getCandidateResumesListByCandidateIdAndStatus(Long candidateId, Integer status) throws Exception;
	
	public CandidateResumes updateCandidateResume(CandidateResumes candidateResumes) throws Exception;
	
	public CandidateResumes getCandidateResumeByResumeId(Long resumeId) throws Exception;

}
