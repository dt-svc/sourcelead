package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.AssociateContributionDao;
import com.bis.sourcelead.payroll.entity.AssociateContribution;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;

@Repository
public class AssociateContributionDaoImpl implements AssociateContributionDao {

private static final Logger logger = LoggerFactory.getLogger(AssociateContributionDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public AssociateContribution saveAssociateContribution(AssociateContribution associateContribution)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateContribution != null) {
				session.saveOrUpdate(associateContribution);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return associateContribution;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AssociateContribution> findAllContributionSummaryByCandidateId(Long candidateId, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateContribution>  associateContributionList = session.createQuery("from AssociateContribution contri where contri.candidate.id=:candidateId and contri.orgLocationId.id= :orgLocationId and contri.status = 1" ).setLong( "candidateId", candidateId).setLong("orgLocationId", orgLocation.getId()).list();
		return associateContributionList.stream().filter(ad ->ad.getContributionTypeId().getStatus() == 1).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public AssociateContribution findAssociateContributionById(Long assContriId)
	{
		AssociateContribution associateContribution = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<AssociateContribution> associateContributionList = session.createQuery("from AssociateContribution contri where contri.id=:assContriId").setLong("assContriId", assContriId).list();
			if(associateContributionList !=null && associateContributionList.size() >0) {
				associateContribution = associateContributionList.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return associateContribution;
	}

	@Override
	@Transactional
	public AssociateContribution getValidateContribution(Long candidateId,Long contriTypId, OrgLocations orgLocation) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateContribution assContribution where assContribution.candidate.id =:candidateId and assContribution.contributionTypeId.id =:contriTypeId and assContribution.orgLocationId.id =:orgLocationId and assContribution.status = 1";
		List<AssociateContribution> assContribution = session.createQuery(query).setLong("candidateId",candidateId).setLong("contriTypeId", contriTypId).setLong("orgLocationId", orgLocation.getId()).list();
		if(assContribution.size() > 0) {
			return assContribution.get(0);
		}else {
			return null;
		}
	}

	@Override
	@Transactional
	public AssociateContribution findContributionByUserIdContributionId(Long userId, Long contributionId)
	{
		AssociateContribution associateContribution = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			List<AssociateContribution>  associateContributionList = session.createQuery("from AssociateContribution contri where contri.id=:contributionId and userId.id =:userId").setLong("contributionId", contributionId).setLong("userId", userId).list();
			if(associateContributionList != null && associateContributionList.size() >0) {
				associateContribution = associateContributionList.get(0);
			}
		}catch (Exception e) {
         e.printStackTrace();
		}
		
		return associateContribution;
	}

	@Override
	@Transactional
	public void deleteAssociateContributionByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) {
		
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "update AssociateContribution assContribution set assContribution.status = 0 where assContribution.orgLocationId.id =:orgLocationId and assContribution.candidate.id = :candidateId";
			session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();;
		}
	}

}
