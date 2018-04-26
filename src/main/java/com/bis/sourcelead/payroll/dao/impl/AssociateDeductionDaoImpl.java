package com.bis.sourcelead.payroll.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.payroll.dao.AssociateDeductionDao;
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
}
