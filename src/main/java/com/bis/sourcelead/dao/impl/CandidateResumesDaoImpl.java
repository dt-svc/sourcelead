package com.bis.sourcelead.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.entity.CandidateResumes;

/**
 * @author Ajith Matta
 *
 */
@Repository
public class CandidateResumesDaoImpl implements CandidateResumesDao {

	
	private static final Logger logger = LoggerFactory.getLogger(CandidateSkillsDaoImpl.class);
	
	/* 
	 * This ADO method is used for save the candidate resumes 
	 */
	@Override
	public CandidateResumes storeCandidateResumes(CandidateResumes candidateResumes) throws Exception {
		
		return null;
	}

}
