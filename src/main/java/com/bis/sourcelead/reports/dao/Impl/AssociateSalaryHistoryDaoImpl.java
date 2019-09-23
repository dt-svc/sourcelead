package com.bis.sourcelead.reports.dao.Impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.AssociateSalaryHistory;
import com.bis.sourcelead.reports.dao.AssociateSalaryHistoryDao;

@Repository
public class AssociateSalaryHistoryDaoImpl implements AssociateSalaryHistoryDao{
	
	private static final Logger logger = LoggerFactory.getLogger(AssociateSalaryHistoryDaoImpl.class);
	
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public List<AssociateSalaryHistory> findAssociateSalHistory(Long associateSalid)
	{

		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateSalaryHistory> associateSalaryHisList = null;
		try {
			associateSalaryHisList = session.createQuery("from AssociateSalaryHistory assHis where assHis.associateSalary.id=:associateSalid " ).setLong("associateSalid", associateSalid).list();		
		}catch (Exception e) {
			logger.error("Find Associate Salary History failed due to :"+e.getMessage());
		}
		return associateSalaryHisList;
	}
}
