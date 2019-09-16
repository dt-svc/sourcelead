package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.CandidateResumesDTO;
import com.bis.sourcelead.entity.CandidateResumes;

/**
 * @author Ajith Matta
 *
 */
public interface CandidateResumesDao {
	
	public CandidateResumes storeCandidateResumes(CandidateResumes candidateResumes) throws Exception; 
	
	public List<CandidateResumes> getCandidateResumesListByCandidateIdAndStatus(Long candidateId, Integer status) throws Exception;
	
	/**
	 * @author Prasad Salina
	 * @param submissionResumeIds
	 * @return
	 * To get List of CandidateResumes by resumeIds
	 */
	public List<CandidateResumes> getCandidateResumesByIds(List<Long> submissionResumeIds);
	
	public CandidateResumes updateCandidateResume(CandidateResumes candidateResumes) throws Exception;
	
	public CandidateResumes getCandidateResumeByResumeId(Long resumeId) throws Exception;

	public CandidateResumes getCandidateResumeByResumeUUID(String resumeUUID) throws Exception;
	
	public CandidateResumes getCandidateLatesResumes(Long candidateId, int status)throws Exception;

	Integer getCandidateResumeCountByResumeId(Long candidateId)throws Exception;
	
	Integer getCandidateResumeCountByCandidateId(Long candidateId) throws Exception;

	public CandidateResumes getCandidateResumesListByCandidateId(Long candidateId);
	
	public CandidateResumes getCandidateResumeByCandidateId(Long candidateId) throws Exception;
	
	CandidateResumesDTO getCandidateResumesDTOByCandidateId(Long candidateId) throws Exception;
	
	Integer getCandidateActiveResumeCountByCandidateId(Long candidateId) throws Exception;
	
	public CandidateResumesDTO  getCandidateLatesResumeDetails(Long candidateId) throws Exception;
	
	public CandidateResumesDTO  getCandidateGlobalLatesResumeDetails(Long candidateId) throws Exception;
	
	Integer getCandidateGlobalActiveResumeCountByCandidateId(Long candidateId) throws Exception;
}
