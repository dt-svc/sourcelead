package com.bis.sourcelead.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jadira.usertype.spi.utils.lang.StringUtils;
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
	public List<Expenses> getAllExpenses(Long orgLocId,Long loginUserId, String expenseStatus, Date startDateUTC, Date endDateUTC){
		Map<String, Object> params = new HashMap<String,Object>();
		String[] expStatus = expenseStatus.split(",");
		String subQuery2 = "";
		if (Arrays.asList(expStatus).contains("ALL") || StringUtils.isEmpty(expenseStatus)) {
			subQuery2 = subQuery2
					+ "  expenses.expenseStatus = 'APPROVED' or expenses.expenseStatus = 'SUBMITTED' or expenses.expenseStatus = 'DRAFT' or expenses.expenseStatus = 'REJECTED' ";
		}
		else{
			if (Arrays.asList(expStatus).contains("APPROVED")) {
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'APPROVED' ";
			}
			if (Arrays.asList(expStatus).contains("SUBMITTED")) {
				if(StringUtils.isNotEmpty(subQuery2)){
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'SUBMITTED' ";
			}
			if (Arrays.asList(expStatus).contains("DRAFT")) {
				if(StringUtils.isNotEmpty(subQuery2)){
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'DRAFT' ";
			}
			if (Arrays.asList(expStatus).contains("REJECTED")) {
				if(StringUtils.isNotEmpty(subQuery2)){
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'REJECTED' ";
			}
		}
		
		String startDateSubQuery = "";
		if(null != startDateUTC){
			startDateSubQuery = "and (expenses.startDate >= :startDateUTC)";
			  params.put("startDateUTC",startDateUTC);
		}
		
		String endDateSubQuery = "";
		if(null != endDateUTC){
			endDateSubQuery = "and (expenses.endDate <= :endDateUTC)";
			  params.put("endDateUTC",endDateUTC);
		}

		/*if (expStatus[0] != null && expStatus[0].length() > 0) {
			subQuery2 = "and (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
		} else if (subQuery2 != null && !"".equals(subQuery2)) {
			subQuery2 = " (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
		}
		*/
		
		Session session = this.sessionFactory.getCurrentSession();
		String from = "from Expenses expenses left join fetch expenses.orgLocationId left join fetch expenses.projectMembers pm left join fetch pm.employee emp left join fetch emp.user user where expenses.orgLocationId.id =:orgLocId and user.id =:loginUserId and ( " + subQuery2 +" ) "+startDateSubQuery+" "+endDateSubQuery+" ";
		params.put("loginUserId",loginUserId);
		params.put("orgLocId",orgLocId);
		
		Query query = session.createQuery(from);
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("loginUserId")){
				query.setParameter(str, (long) params.get(str));
			}if(str.equalsIgnoreCase("startDateUTC")){
				query.setParameter( str, (Date) params.get(str) );
			}
			if(str.equalsIgnoreCase("endDateUTC")){
				query.setParameter( str, (Date) params.get(str) );
			}
			if(str.equalsIgnoreCase("orgLocId")){
				query.setParameter(str, (long) params.get(str));
			}
			
        }
		return query.setCacheable(true).list();
		/*List<Expenses> expensesList =session.createQuery(query).setLong("orgLocId", orgLocId).setLong("loginUserId", loginUserId).setCacheable(true).list();
		return expensesList;*/
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
	public Expenses getExpenseByExpenseUniqueId(String expenseId,Long OrgLocationID) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		Expenses  expense =  null;
		List<Expenses>  expensesList = session.createQuery("from Expenses expenses left join fetch expenses.orgLocationId orglocId left join fetch expenses.projectMembers pm left join fetch pm.employee emp left join fetch emp.user user where expenses.expenseId =:expenseId and orglocId.id =:OrgLocationID").setString("expenseId", expenseId).setLong("OrgLocationID", OrgLocationID).setCacheable(true).list();
		if(null != expensesList && expensesList.size() >0){
			expense = expensesList.get(0);
		}
		return expense;
	}

	@Override
	@Transactional
	public List getExpensesByProjectMemberId(Long projectMemberId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Expenses>  expensesList = session.createQuery("from Expenses expenses left join fetch expenses.orgLocationId orglocId left join fetch expenses.projectMembers pm left join fetch pm.employee emp left join fetch emp.user user where pm.id =:projectMemberId").setLong("projectMemberId", projectMemberId).setCacheable(true).list();
		
		return expensesList;
	}

}
