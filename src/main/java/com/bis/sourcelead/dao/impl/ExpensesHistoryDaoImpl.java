package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ExpensesHistoryDao;
import com.bis.sourcelead.entity.ExpensesHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ExpensesHistoryDaoImpl implements ExpensesHistoryDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	
	@Value("${SourceLead.101}")
	private String noRecords;
	
	@Value("${SourceLead.102}")
	private String statusUpdate;
	
	@Value("${SourceLead.103}")
	private String statusDelete;
	
	@Value("${SourceLead.106}")
	private String duplicateValue;

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesHistory storeExpensesHistory(ExpensesHistory expensesHistory) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		try {
			if (expensesHistory != null) {
				session.saveOrUpdate(expensesHistory);
			}
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"expensesHistory"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return expensesHistory;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesHistory getExpensesHistoryByExpensesHistoryId(Long expensesHistoryId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ExpensesHistory eh where eh.id =:expensesHistoryId";
		List<ExpensesHistory> expensesHistoryList = session.createQuery(query).setLong("expensesHistoryId", expensesHistoryId).list();
		
		ExpensesHistory expensesHistory = null;
		if(expensesHistoryList != null && !expensesHistoryList.isEmpty())
		{
			expensesHistory = (ExpensesHistory) expensesHistoryList.iterator().next();
		}
		return expensesHistory;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesHistory> getExpensesHistoryListByExpensesHistoryId(Long expensesHistoryId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<ExpensesHistory> expensesHistoryList = null;
		Query expHistoryListQuery= session.createQuery("from ExpensesHistory eh where eh.id = :expensesHistoryId ORDER BY eh.id DESC ");
		expensesHistoryList = expHistoryListQuery.setLong("expensesHistoryId", expensesHistoryId).list();
		return expensesHistoryList;
	}
	
}
