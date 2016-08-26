package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.CandidateResumes;

/**
 * @author Ajith Matta
 *
 */
public interface CandidateResumesService {
	
	/**
	 * @param candidateResumes
	 * @return CandidateResumes
	 * @throws Exception
	 */
	public CandidateResumes storeCandidateResumes(CandidateResumes candidateResumes) throws Exception;
		
	

}
