package com.bis.sourcelead.payroll.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
