package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ExpensesDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;

@SuppressWarnings("rawtypes")
@Repository
public class ExpensesDaoImpl implements ExpensesDao {

	@Autowired
	private SessionFactory sessionFactory;

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
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Expenses> getAllExpenses(Long orgLocId,Long loginUserId){
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Expenses expenses left join fetch expenses.orgLocationId left join fetch expenses.projectMembers pm left join fetch pm.employee emp left join fetch emp.user user where expenses.orgLocationId.id =:orgLocId and user.id =:loginUserId ";
		List<Expenses> expensesList =session.createQuery(query).setLong("orgLocId", orgLocId).setLong("loginUserId", loginUserId).setCacheable(true).list();
		return expensesList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Expenses> getIndividualExpense(Long orgLocId, Long loginUserId, String expenseId) {
		Session session = this.sessionFactory.getCurrentSession();
		Long expenseIdLong= Long.parseLong(expenseId);
		String query = "from Expenses expenses left join fetch expenses.orgLocationId left join fetch expenses.projectMembers pm left join fetch pm.employee emp left join fetch emp.user user where expenses.orgLocationId.id =:orgLocId and expenses.id =:expenseIdLong"; 
		List<Expenses> expensesList =session.createQuery(query).setLong("orgLocId", orgLocId).setLong("expenseIdLong", expenseIdLong).setCacheable(true).list();
		return expensesList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesEntries> getExpensesEntries(String expenseId) {
		Session session = this.sessionFactory.getCurrentSession(); 
		Long expenseIdLong= Long.parseLong(expenseId);
		String query = "from ExpensesEntries expensesEntries  left join fetch expensesEntries.expensesCategoryId left join fetch expensesEntries.expensesId ex where ex.id =:expenseIdLong"; 
		List<ExpensesEntries> expensesEntriesList =session.createQuery(query).setLong("expenseIdLong", expenseIdLong).setCacheable(true).list();
		return expensesEntriesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Expenses getExpenseByExpenseId(long expenseId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Expenses expenses where expenses.id =:expenseId";
		List<Expenses> expenses = session.createQuery(query).setLong("expenseId", expenseId).setCacheable(true).list();
		Expenses expensesList = null;
		if (expenses != null && expenses.size() > 0) {
			expensesList = (Expenses) expenses.iterator().next();
		}
		return expensesList;
	}
	
	@Override
	@Transactional
	public Expenses getExpenseByExpenseUniqueId(String expenseId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		Expenses  expense =  null;
		List<Expenses>  expensesList = session.createQuery("from Expenses expenses left join fetch expenses.orgLocationId left join fetch expenses.projectMembers pm left join fetch pm.employee emp left join fetch emp.user user where expenses.expenseId =:expenseId").setString("expenseId", expenseId).setCacheable(true).list();
		if(null != expensesList && expensesList.size() >0){
			expense = expensesList.get(0);
		}
		return expense;
	}
}
