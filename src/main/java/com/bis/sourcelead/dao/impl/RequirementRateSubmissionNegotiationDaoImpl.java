package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementRateSubmissionNegotiationDao;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;

@Repository
public class RequirementRateSubmissionNegotiationDaoImpl  implements RequirementRateSubmissionNegotiationDao{
	
	@Autowired	
	private SessionFactory sessionFactory;
	
	/*@Override
	@Transactional
	public void saveRateNegotiation(RequirementRateSubmissionNegotiation rateNegotiation) {
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(rateNegotiation != null){
				session.saveOrUpdate(rateNegotiation);
			}
		}catch(Exception e){
		}
		
	}*/

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
		String query = "from RequirementRateSubmissionNegotiation rateNegotiation left join fetch rateNegotiation.requirementsubmissionId where rateNegotiation.requirementsubmissionId.orgLocations.id = :orgLocationsId ";
		List<RequirementRateSubmissionNegotiation> rateNegotiation =  session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId).list();
		return rateNegotiation;
	}
	
	
	
	
	
	
	
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
	public RequirementRateSubmissionNegotiation getLatestRequirementRateSubmissionNegotiationBySubmissionId(Long submissionId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "FROM RequirementRateSubmissionNegotiation drn WHERE drn.createdDate=(SELECT MAX(createdDate) FROM RequirementRateSubmissionNegotiation ddrn WHERE ddrn.requirementsubmissionId.id=:submissionId) and drn.requirementsubmissionId.id=:submissionId";
		List rateNegotiationList = session.createQuery(query).setLong("submissionId", submissionId).list();
		RequirementRateSubmissionNegotiation rateNegotiation = null;
        if(rateNegotiationList != null && !rateNegotiationList.isEmpty()){
        	rateNegotiation = (RequirementRateSubmissionNegotiation) rateNegotiationList.iterator().next();
        }
        return rateNegotiation;
	}
	
	@Override
	@Transactional
	public List<RequirementRateSubmissionNegotiation> getAllRateHistoryBySubmissionIdAndCandidateId(Long submissionId, Long candidateId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementRateSubmissionNegotiation rateNegotiation where rateNegotiation.requirementsubmissionId =:submissionId ";
		List<RequirementRateSubmissionNegotiation> rateNegotiation =  session.createQuery(query.toString()).setLong("submissionId", submissionId).list();
		return rateNegotiation;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementRateSubmissionNegotiation getRequirementRateSubmissionByMaxId(Long submissionId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from RequirementRateSubmissionNegotiation rateNegotiation where rateNegotiation.requirementsubmissionId.id =:submissionId and rateNegotiation.id =(SELECT MAX(rate.id) FROM RequirementRateSubmissionNegotiation rate where rate.requirementsubmissionId.id =:submissionId)" ;
		List<RequirementRateSubmissionNegotiation> rateNegotiationList =session.createQuery(query).setLong("submissionId", submissionId).list(); 
		RequirementRateSubmissionNegotiation negotiation = null;
		if(rateNegotiationList != null && !rateNegotiationList.isEmpty()){
			negotiation = rateNegotiationList.iterator().next();
		}
		return negotiation;
	}

}
