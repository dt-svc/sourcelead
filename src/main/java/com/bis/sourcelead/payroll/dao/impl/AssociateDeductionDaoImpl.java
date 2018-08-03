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
import com.bis.sourcelead.payroll.dao.AssociateDeductionDao;
import com.bis.sourcelead.payroll.entity.AssociateBankData;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;

@Repository
public class AssociateDeductionDaoImpl implements AssociateDeductionDao {

	
private static final Logger logger = LoggerFactory.getLogger(AssociateTaxDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	@Override
	@Transactional
	public AssociateDeduction saveAssociateDeduction(AssociateDeduction associateDeduction)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateDeduction != null) {
				session.saveOrUpdate(associateDeduction);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return associateDeduction;
	}


	@Override
	@Transactional
	public List<AssociateDeduction> findAllDeductionSummaryByUserId(Long userId, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateDeduction>  associateDeductionList = session.createQuery("from AssociateDeduction ded where ded.userId.id=:userId and ded.orgLocationId.id= :orgLocationId and ded.status = 1" ).setLong( "userId", userId).setLong("orgLocationId", orgLocation.getId()).list();
		return associateDeductionList;
	}


	@Override
	@Transactional
	public AssociateDeduction findAssociateDeductionById(Long associateDeductionId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateDeduction associateDeduction = null;
		List<AssociateDeduction>  associateDeductionList = session.createQuery("from AssociateDeduction ded where ded.id=:associateDeductionId").setLong( "associateDeductionId", associateDeductionId).list();
		if(associateDeductionList !=null && associateDeductionList.size() >0) {
			associateDeduction = associateDeductionList.get(0);
		}
		return associateDeduction;
	}


	@Override
	@Transactional
	public AssociateDeduction getAsociateDedByUserIdDeductionId(long deductionId, Long userId)
	{
		AssociateDeduction associateDeduction = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			associateDeduction = (AssociateDeduction) session.createQuery("from AssociateDeduction ded where ded.id=:deductionId and ded.userId.id=:userId").setLong("deductionId", deductionId).setLong("userId", userId).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return associateDeduction;
	}
}
