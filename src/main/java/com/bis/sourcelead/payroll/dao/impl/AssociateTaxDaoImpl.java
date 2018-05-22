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

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
@Repository
public class AssociateTaxDaoImpl implements AssociateTaxDao{

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
	public AssociateTax findAssociateTaxSummaryByUserId(Long userId, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateTax associateTax = null;
		try {
			List<AssociateTax>  associateTaxTaxList = session.createQuery("from AssociateTax aTax where aTax.userId.id=:userId and aTax.orgLocationId.id= :orgLocationId" ).setLong( "userId", userId).setLong("orgLocationId", orgLocation.getId()).list();
		    if(associateTaxTaxList !=null && associateTaxTaxList.size() >0) {
		    	associateTax = associateTaxTaxList.get(0);
		    }
		
		}catch (Exception e) {
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
}
