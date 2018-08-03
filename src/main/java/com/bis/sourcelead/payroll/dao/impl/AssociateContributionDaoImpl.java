package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

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
	public List<AssociateContribution> findAllContributionSummaryByUserId(Long userId, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateContribution>  associateContributionList = session.createQuery("from AssociateContribution contri where contri.userId.id=:userId and contri.orgLocationId.id= :orgLocationId" ).setLong( "userId", userId).setLong("orgLocationId", orgLocation.getId()).list();
		return associateContributionList;
	}

	@Override
	@Transactional
	public AssociateContribution findAssociateContributionById(Long assContriId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateContribution associateContribution = null;
		List<AssociateContribution>  associateContributionList = session.createQuery("from AssociateContribution contri where contri.id=:assContriId").setLong("assContriId", assContriId).list();
		if(associateContributionList != null && associateContributionList.size() >0) {
			associateContribution = associateContributionList.get(0);
		}
		return associateContribution;
	}

	@Override
	@Transactional
	public AssociateContribution getValidateContribution(Long contriTypId, OrgLocations orgLocation) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociateContribution assContribution where assContribution.contributionTypeId.id =:contriTypeId and assContribution.orgLocationId.id =:orgLocationId";
		List<AssociateContribution> assContribution = session.createQuery(query).setLong("contriTypeId", contriTypId).setLong("orgLocationId", orgLocation.getId()).list();
		if(assContribution.size() > 0) {
			return assContribution.get(0);
		}else {
			return null;
		}
	}
}
