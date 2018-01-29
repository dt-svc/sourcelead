package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RecommendationsDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Requirement;

@Repository
public class RecommendationsDaoImpl implements RecommendationsDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception {
          
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Requirement requirement left join fetch requirement.parentRequirement left join fetch requirement.clientId  orgrelation left join fetch orgrelation.responderLocationId orgLocation  where requirement.id =:requirementId";
		List<Requirement> requirementList = session.createQuery(query).setLong("requirementId", requirementId).list();
		
		Requirement requirement = null;
		if(requirementList != null && !requirementList.isEmpty())
		{
			requirement = (Requirement) requirementList.iterator().next();
		}
		return requirement;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Candidate candidate  where candidate.orgLocationId.id =:orgLocationId and candidate.status =1";
        return  session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}
}
