package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementRateSubmissionNegotiationDao;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;

@Repository
public class RequirementRateSubmissionNegotiationDaoImpl  implements RequirementRateSubmissionNegotiationDao{
private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void saveRateNegotiation(RequirementRateSubmissionNegotiation rateNegotiation) {
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(rateNegotiation != null){
				session.saveOrUpdate(rateNegotiation);
			}
		}catch(Exception e){
		}
		
	}

	@Override
	@Transactional
	public RequirementRateSubmissionNegotiation getRequirementRateSubmissionById(Long ratesubmissionId) {
		Session session = this.sessionFactory.getCurrentSession();
		return  session.get(RequirementRateSubmissionNegotiation.class, ratesubmissionId);
	}

	@Override
	@Transactional
	public List<RequirementRateSubmissionNegotiation> getRequirementRateSubmissionByOrgLocationsId(Long orgLocationsId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementRateSubmissionNegotiation rateNegotiation where rateNegotiation.orgLocationsId = :orgLocationsId ";
		List<RequirementRateSubmissionNegotiation> rateNegotiation =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
		return rateNegotiation;
	}

}
