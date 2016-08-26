package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.entity.CandidateEducation;

public class CandidateEducationDaoImpl implements CandidateEducationDao{
	@Autowired
	SessionFactory sessionfactory;
	
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
