package com.bis.sourcelead.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ExpensesReportDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;

@Repository
public class ExpensesReportDaoImpl implements ExpensesReportDao {
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

	private static final Logger logger = LoggerFactory.getLogger(ExpensesReportDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getAllExpensesReportListByLoginUserIdAndOrgLocationId(Long loginUserId, Long loginOrgLocationId)throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from ExpensesApproverHierarchy expensesHierarchy left join fetch expensesHierarchy.expenseId expenses left join fetch expensesHierarchy.hierarchyId pah left join fetch pah.projectMembers proMembers left join fetch proMembers.employee left join fetch pah.approverUserId user where user.id =:loginUserId and expenses.orgLocationId.id =:loginOrgLocationId";
		List<ExpensesApproverHierarchy> expensesList =session.createQuery(query).setLong("loginOrgLocationId", loginOrgLocationId).setLong("loginUserId", loginUserId).setCacheable(true).list();
		logger.info("list for expenses");
		return expensesList;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDate(Long projectMembersId,Date startDate, Date endDate) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Expenses> expenses = null;
		if(startDate !=null && endDate !=null){
			java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
			String query = "from Expenses expenses left join fetch expenses.projectMembers proMembers where proMembers.id = :projectMembersId and expenses.endDate  BETWEEN :startDate and :endDate";
			expenses =session.createQuery(query).setLong("projectMembersId", projectMembersId).setDate("startDate", sqlStartDate).setDate("endDate", sqlEndDate).setCacheable(true).list();
		}else{
			String query = "from Expenses expenses left join fetch expenses.projectMembers proMembers where proMembers.id = :projectMembersId";
			expenses =session.createQuery(query).setLong("projectMembersId", projectMembersId).list();
		}
		return expenses;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getExpensesTotalMontWiseCountBasedOnprojectMemberIdStartDateAndEndDate(Date startDate, Date endDate,Long proMembersId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "ExpensesApproverHierarchy expensesHierarchy left join fetch expensesHierarchy.expenseId expenses left join fetch expenses.projectMembers proMembers where proMembers.id =:projectMembersId and expenses.startDate  >= :startDate and :endDate <= expenses.endDate";
		List<ExpensesApproverHierarchy> expensesApproverHierarchy =session.createQuery(query).setLong("projectMembersId", proMembersId).setDate("startDate", startDate).setDate("endDate", endDate).setCacheable(true).list();
		return expensesApproverHierarchy;
	}
}
