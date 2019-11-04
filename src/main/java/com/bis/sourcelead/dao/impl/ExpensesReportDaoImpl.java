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
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;

@Repository
public class ExpensesReportDaoImpl implements ExpensesReportDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProjectMembersDao projectMembersDao;
	
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
		List<ExpensesApproverHierarchy> expensesList =session.createQuery(query).setLong("loginOrgLocationId", loginOrgLocationId).setLong("loginUserId", loginUserId).list();
		logger.info("list for expenses");
		return expensesList;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDate(Long projectMembersId,Date startDate, Date endDate) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Expenses> expenses = null;
		try {
			ProjectMembers pm = projectMembersDao.getProjectMemberById(projectMembersId);
			if(startDate !=null && endDate !=null){
				java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
				java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
				String query = "from Expenses expenses left join fetch expenses.projectMembers proMembers left join fetch expenses.projects project left join fetch expenses.masterCandidateId mc  where project.id = :projectId and mc.id = :masterCandidateId and expenses.endDate  BETWEEN :startDate and :endDate and expenses.expenseStatus != 'DRAFT' ";
				expenses =session.createQuery(query).setLong("projectId", pm.getProjects().getId()).setLong("masterCandidateId", pm.getProjectMemberCandidteId().getMasterCandidateId().getId()).setDate("startDate", sqlStartDate).setDate("endDate", sqlEndDate).list();
			}else{
				String query = "from Expenses expenses left join fetch expenses.projectMembers proMembers left join fetch expenses.projects project left join fetch expenses.masterCandidateId mc where project.id = :projectId and mc.id = :masterCandidateId";
				expenses =session.createQuery(query).setLong("projectId", pm.getProjects().getId()).setLong("masterCandidateId", pm.getProjectMemberCandidteId().getMasterCandidateId().getId()).list();
			}
		} catch (Exception e) {}
		return expenses;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDateForClaimedExpenses(Long projectMembersId,Date startDate, Date endDate) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Expenses> expenses = null;
		try {
			ProjectMembers pm = projectMembersDao.getProjectMemberById(projectMembersId);
			if(startDate !=null && endDate !=null){
				java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
				java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
				String query = "from Expenses expenses left join fetch expenses.projectMembers proMembers left join fetch expenses.projects project left join fetch expenses.masterCandidateId mc  where project.id = :projectId and mc.id = :masterCandidateId and  (:startDate<= date(expenses.updatedDate) AND date(expenses.updatedDate)<= :endDate)";
				expenses =session.createQuery(query).setLong("projectId", pm.getProjects().getId()).setLong("masterCandidateId", pm.getMasterCandidateId().getId()).setDate("startDate", sqlStartDate).setDate("endDate", sqlEndDate).list();
			}else{
				String query = "from Expenses expenses left join fetch expenses.projectMembers proMembers left join fetch expenses.projects project left join fetch expenses.masterCandidateId mc where project.id = :projectId and mc.id = :masterCandidateId";
				expenses =session.createQuery(query).setLong("projectId", pm.getProjects().getId()).setLong("masterCandidateId", pm.getMasterCandidateId().getId()).list();
			}
		} catch (Exception e) {}
		return expenses;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getExpensesTotalMontWiseCountBasedOnprojectMemberIdStartDateAndEndDate(Date startDate, Date endDate,Long proMembersId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "ExpensesApproverHierarchy expensesHierarchy left join fetch expensesHierarchy.expenseId expenses left join fetch expenses.projectMembers proMembers left join fetch expenses.projects project left join fetch expenses.masterCandidateId mc where proMembers.id =:projectMembersId and expenses.startDate  >= :startDate and :endDate <= expenses.endDate";
		List<ExpensesApproverHierarchy> expensesApproverHierarchy = null;
		try {
			    ProjectMembers pm = projectMembersDao.getProjectMemberById(proMembersId); 
				session.createQuery(query).setLong("projectId", pm.getProjects().getId()).setLong("masterCandidateId", pm.getMasterCandidateId().getId()).setDate("startDate", startDate).setDate("endDate", endDate).list();
		} catch (Exception e) {}
		return expensesApproverHierarchy;
	}
}
