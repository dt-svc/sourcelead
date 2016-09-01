package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateSkillsDao;
import com.bis.sourcelead.entity.CandidateSkills;

/**
 * @author Ajith Matta
 * @create date 25 Aug 2016
 */

@Repository
public class CandidateSkillsDaoImpl  implements CandidateSkillsDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateSkillsDaoImpl.class);
	
		
	/* 
	 *This ADO method is used for save the candidate skills 
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateSkills storeCandidateSkills(CandidateSkills candidateSkills) throws Exception {
	  
			Session session = this.sessionFactory.getCurrentSession();
			if (candidateSkills != null) {
				session.saveOrUpdate(candidateSkills);
			}
		return candidateSkills;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateSkills> getCandidateSkillsByCandidateId(String candidateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<CandidateSkills> candidateSkillsList = null;
		Query citiesListQuery= session.createQuery("from CandidateSkills cs where cs.candidateId.id =:candidateId ");
		candidateSkillsList = citiesListQuery.setString("candidateId", candidateId).list();
		return candidateSkillsList;
	}
	
}
