package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AssociateSalaryDao;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.AssociateSalaryHistory;
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


	@Override
	@Transactional
	public AssociateSalary findAssociateSalaryByUserId(Long userId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateSalary associateSalary = null;
		try {
			List<AssociateSalary> associateSalaryList = session.createQuery("from AssociateSalary associateSalary where associateSalary.userId.id=:userId and associateSalary.status = 1" ).setLong("userId", userId).list();
		 if(associateSalaryList !=null && associateSalaryList.size() >0) {
			 associateSalary =  associateSalaryList.get(0);
		 }
		}catch (Exception e) {
			logger.error("findAssociateSalaryByUserId failed due to :"+e.getMessage());
		}
		return associateSalary;
	}


	@Override
	@Transactional
	public AssociateSalary findAssociateSalaryById(String id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateSalary associateSalary = null;
		try {
			List<AssociateSalary> associateSalaryList = session.createQuery("from AssociateSalary associateSalary where associateSalary.id=:id and associateSalary.status = 1").setLong("id", Long.parseLong(id)).list();
			if (associateSalaryList != null && associateSalaryList.size() > 0) {
				associateSalary = associateSalaryList.get(0);
			}
		} catch (Exception e) {
			logger.error("findAssociateSalaryByUserId failed due to :" + e.getMessage());
		}
		return associateSalary;
	}


	@Override
	@Transactional
	public AssociateSalaryHistory updaetAssociateSalHis(AssociateSalaryHistory associateSalHis)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateSalHis != null) {
				session.saveOrUpdate(associateSalHis);
			}
		} catch (Exception exception) {
			logger.error("add associate salary History failed due to : "+exception.getMessage());
		}
		return associateSalHis;
	}
}
