package com.bis.sourcelead.dao.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.entity.CandidateEmployers;

/**
 * @author Ajith Matta
 * @create date 25 Aug 2016
 */

@Repository
public class CandidateEmployersDaoImpl  implements CandidateEmployersDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateSkillsDaoImpl.class);
	
		
	/* 
	 *This ADO method is used for save the candidate skills 
	 */
	@Override
	public CandidateEmployers storeCandidateEmployers(CandidateEmployers candidateEmployers) throws Exception {
		return null;
	}

}
