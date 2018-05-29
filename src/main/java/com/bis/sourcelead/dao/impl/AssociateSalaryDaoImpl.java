package com.bis.sourcelead.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AssociateSalaryDao;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.payroll.dao.impl.AssociateBankDataDaoImpl;

@Repository
public class AssociateSalaryDaoImpl implements AssociateSalaryDao{

	
private static final Logger logger = LoggerFactory.getLogger(AssociateBankDataDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	@Override
	@Transactional
	public AssociateSalary addAssociateSal(AssociateSalary associateSalary)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateSalary != null) {
				session.saveOrUpdate(associateSalary);
			}
		} catch (Exception exception) {
			logger.error("add associate salary failed due to : "+exception.getMessage());
		}
		return associateSalary;
	}
}
