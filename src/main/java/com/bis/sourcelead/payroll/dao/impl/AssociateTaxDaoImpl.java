package com.bis.sourcelead.payroll.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.AssociateTaxDao;
import com.bis.sourcelead.payroll.entity.AssociateBankData;
import com.bis.sourcelead.payroll.entity.AssociateTax;
import com.bis.sourcelead.payroll.view.AssociateTaxDedContriView;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;

@Repository
public class AssociateTaxDaoImpl implements AssociateTaxDao {
	private static final Logger logger = LoggerFactory.getLogger(AssociateTaxDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public AssociateTax saveAssociateTax(AssociateTax associateTax)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateTax != null) {
				session.saveOrUpdate(associateTax);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return associateTax;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public AssociateTax findAssociateTaxSummaryByCandidateId(Long candidateId, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateTax associateTax = null;
		try {
			List<AssociateTax> associateTaxTaxList = session.createQuery("from AssociateTax aTax where aTax.candidate.id=:candidateId and aTax.orgLocationId.id= :orgLocationId").setLong("candidateId", candidateId).setLong("orgLocationId", orgLocation.getId()).list();
			if (associateTaxTaxList != null && associateTaxTaxList.size() > 0) {
				associateTax = associateTaxTaxList.get(0);
			}
		} catch (Exception e) {
		}
		return associateTax;
	}
	
	@Override
	@Transactional
	public AssociateTax findAssociateTaxById(long associateTaxId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateTax associateTax = null;
		try {
			@SuppressWarnings("unchecked")
			List<AssociateTax> associateTaxTaxList = session.createQuery("from AssociateTax aTax where aTax.id=:associateTaxId").setLong("associateTaxId", associateTaxId).list();
			if (associateTaxTaxList != null && associateTaxTaxList.size() > 0) {
				associateTax = associateTaxTaxList.get(0);
			}
		} catch (Exception e) {
		}
		return associateTax;
	}

	@Override
	@Transactional
	public AssociateTaxDedContriView findUserTaxDeatails(Long candidateId, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateTaxDedContriView associateTax = new AssociateTaxDedContriView();
		try {
			@SuppressWarnings("unchecked")
			List<AssociateTaxDedContriView> associateTaxTaxList = session.createQuery("from AssociateTaxDedContriView aTax where aTax.candidateId=:candidateId and aTax.taxLocationId = :taxLocationId").setLong("candidateId", candidateId).setLong("taxLocationId", orgLocation.getId()).list();
			if (associateTaxTaxList != null && associateTaxTaxList.size() > 0) {
				associateTax = associateTaxTaxList.get(0);
			}
		} catch (Exception e) {
		}
		return associateTax;
	}
	
	@Override
	@Transactional
	public AssociateTax taxDetailsByOrglocationIdAndCandidateId (Long candidateId, Long orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateTax aT = null;
		try {
		String qery="select * from ASSOCIATE_TAX at where at.CANDIDATE_ID=:candidateId and at.ORG_LOCATION_ID=:orgLocation";
		List<AssociateTax> associate=session.createSQLQuery(qery).addEntity(AssociateTax.class).setLong("candidateId", candidateId).setLong("orgLocation", orgLocation).list();
		if (associate != null && associate.size() > 0) {
			aT = associate.get(0);
		}
		}catch (Exception e) {
		}
		return aT;
}
}
