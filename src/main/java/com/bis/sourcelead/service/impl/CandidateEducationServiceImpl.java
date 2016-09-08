package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.service.CandidateEducationService;

public class CandidateEducationServiceImpl implements CandidateEducationService {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateEducationServiceImpl.class);
	@Autowired
	private CandidateEducationDao candidateEducationDao;

	@Override
	public CandidateEducation storeCandidateEducation(CandidateEducation candidateEducation) throws Exception {
		return null;
	}

	@Override
	public CandidateEducation updateCandidateEducation(CandidateEducation candidateEducation) throws Exception {
		return null;
	}

	@Override
	public Boolean deleteCandidateEducation(Long id) throws Exception {
		return null;
	}

	@Override
	public List<CandidateEducation> getAllCandidateEducation() throws Exception {
		return null;
	}

}
