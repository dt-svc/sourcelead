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

import com.bis.sourcelead.constants.Constants;
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
		Integer status = Constants.CANDIDATE_SKILL_ACTIVE;
		List<CandidateSkills> candidateSkillsList = null;
		Query citiesListQuery= session.createQuery("from CandidateSkills cs where cs.candidateId.id =:candidateId and (cs.status =:status or cs.status is null)");
		candidateSkillsList = citiesListQuery.setString("candidateId", candidateId).setInteger( "status", status ).setCacheable(true).list();
		return candidateSkillsList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateSkills getCandidateSkillBySkillId(Long candidateSkillId) throws Exception {
		logger.debug("In Candidate Skill by SkillId");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from CandidateSkills candidateSkills where candidateSkills.id = :candidateSkillId" ;
		List<CandidateSkills> candidateSkillsList =session.createQuery(query).setLong("candidateSkillId", candidateSkillId).setCacheable(true).list(); 
		CandidateSkills candidateSkills = null;
		if(candidateSkillsList != null && !candidateSkillsList.isEmpty()){
			candidateSkills = candidateSkillsList.iterator().next();
		}
		return candidateSkills;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<CandidateSkills> getSkillByCandidateId(Long candidateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from CandidateSkills candidateSkills where candidateSkills.candidateId.id = :candidateId" ;
		return session.createQuery(query).setLong("candidateId", candidateId).setCacheable(true).list(); 
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateSkills getSkillsByCandidateIdSkillName(String candidateId, String skillName) {
		
		logger.debug("In Store getSkillsByCandidateIdSkillName method");
		Session session = this.sessionFactory.getCurrentSession();
		Integer status = Constants.CANDIDATE_SKILL_ACTIVE;
		String query = "from CandidateSkills s where s.name = :skillName and s.candidateId = :candidateId and s.status = "+status+" ";
		List<CandidateSkills> candidateSkillsList = session.createQuery(query).setString("candidateId", candidateId).setString("skillName", skillName).setCacheable(true).list();
		CandidateSkills candidateSkills = null;
		if(candidateSkillsList != null && !candidateSkillsList.isEmpty())
		{
			candidateSkills = (CandidateSkills) candidateSkillsList.iterator().next();
		}
		return candidateSkills;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean validateSkillName(String skillName) {
		logger.debug("In Store validateskillName method");
		 Session session = this.sessionFactory.getCurrentSession();
		 Integer status = Constants.CANDIDATE_SKILL_ACTIVE;
		 boolean flag = false;
		 if(skillName != null){
				String query = "from CandidateSkills s where s.name <> :skillName and s.status = "+status+" ";
				List<CandidateSkills> candidateSkillsList =session.createQuery(query).setString("skillName", skillName).setCacheable(true).list();
				if (candidateSkillsList != null && candidateSkillsList.size() > 0) {
					if(flag = true){
						return flag;	
					}else{
						flag = false;
					}
				}
		 }
		return flag;
	}
} 
