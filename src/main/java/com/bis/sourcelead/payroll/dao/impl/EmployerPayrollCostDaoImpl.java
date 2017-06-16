package com.bis.sourcelead.payroll.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.payroll.dao.EmployerPayrollCostDao;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;

@Repository
public class EmployerPayrollCostDaoImpl implements EmployerPayrollCostDao{

	private static final Logger logger = LoggerFactory.getLogger(EmployerPayrollCostDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmployerPayrollCost saveEmployerPayrollCost(EmployerPayrollCost employerPayrollCost) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(employerPayrollCost);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return employerPayrollCost;
	}

}
