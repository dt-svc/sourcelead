package com.bis.sourcelead.dao.impl;


import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpenseProjectApproverHierarchyDao;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ExpenseProjectApproverHierarchyDaoImpl implements ExpenseProjectApproverHierarchyDao{

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

	
	private static final Logger logger = LoggerFactory.getLogger(ExpenseProjectApproverHierarchyDaoImpl.class);


	@Override
	@Transactional
	public void deleteProjectExpenseProjectApproverHierarchy(
			ExpenseProjectApproverHierarchy expenseProjectApproverHierarchy) throws Exception {

		logger.debug("In ExpenseProjectApproverHierarchy delete method");
		Session session = this.sessionFactory.getCurrentSession();
		if(expenseProjectApproverHierarchy != null){
			session.delete(expenseProjectApproverHierarchy);
			logger.info("Record deleted successfully..!");
		}
	
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<ExpenseProjectApproverHierarchy> getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(long projectMemberMasterCandidateId,long projectId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ExpenseProjectApproverHierarchy projectHirachy   left join fetch projectHirachy.approverUserId usr  left join fetch projectHirachy.projectClientsId pc left join fetch pc.orgLocationsId loc where pc.orgLocationsId.org.orgType <> '"+Constants.GUEST+"'  and projectHirachy.projectId =:projectId and  pc.id IN (select pm.projectClientsId.id from ProjectMembers  pm where pm.masterCandidateId.id = :masterCandidateId and pm.projects.id = :projectId) ";
	        List<ExpenseProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setLong("projectId", projectId).setLong("masterCandidateId", projectMemberMasterCandidateId).list();
	        return projectHirachyList;
		
	}

	@Override
	@Transactional
	public ExpenseProjectApproverHierarchy saveProjectExpenseProjectApproverHierarchy(
			ExpenseProjectApproverHierarchy expenseProjectApproverHierarchy) throws Exception {
		logger.debug("In Projects Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			if(expenseProjectApproverHierarchy != null){
				session.saveOrUpdate(expenseProjectApproverHierarchy);
			}
		}catch(Exception exception){
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"EXPENSE_PROJECT_APPROVER_HIERARCHY"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return expenseProjectApproverHierarchy;
	}

	@Override
	@Transactional
	public ExpenseProjectApproverHierarchy getApproverUsersByProjectIdAndProjectClientId(Long projectId,
			Long projectClientId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("In ExpenseProjectApproverHierarchy getProjectApproverByProjectIdAndLevel method");
		String query = "from ExpenseProjectApproverHierarchy projectHirachy left join fetch projectHirachy.projectId projects left join fetch projectHirachy.projectClientsId pc left join fetch projectHirachy.approverUserId where projects.id = :projectId and pc.id = :projectClientId and projectHirachy.level IN(select Max(subpah.level) from ExpenseProjectApproverHierarchy subpah where subpah.projectId.id = :projectId and subpah.projectClientsId.id = :projectClientId)";
		Collection<ExpenseProjectApproverHierarchy> projectApproverHierarchies = session.createQuery(query.toString()).setLong("projectId", projectId).setLong("projectClientId", projectClientId).list();

		ExpenseProjectApproverHierarchy ph = null;
		if (projectApproverHierarchies != null && projectApproverHierarchies.size() > 0) {
			ph = (		ExpenseProjectApproverHierarchy) projectApproverHierarchies.iterator().next();
		}
		return ph;
	}

	@Override
	@Transactional
	public List<ExpenseProjectApproverHierarchy> getProjectsHierarchyListByProjectIdAndOrgLocationId(Long projectId,
			Long orgLocationId) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 String query = "from ExpenseProjectApproverHierarchy hierarchy left join fetch hierarchy.projectId projects left join fetch hierarchy.projectClientsId pclient left join fetch hierarchy.approverUserId "
				+ "where projects.id = :projectId and pclient.orgLocationsId.id = :orgLocationId ";
		 List<ExpenseProjectApproverHierarchy>  projectHirachyList =   session.createQuery(query.toString()).setLong("projectId", projectId).setLong("orgLocationId", orgLocationId).list();
	     return projectHirachyList;
	}

	@Override
	@Transactional
	public ExpenseProjectApproverHierarchy getExpenseApproverUsersByProjectIdAndProjectClientIdAndApproverUserId(
			Long approverUserId, Long projectClientId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("In ExpenseProjectApproverHierarchy getExpenseApproverUsersByProjectIdAndProjectClientIdAndApproverUserId method");
		String query = "from ExpenseProjectApproverHierarchy projectHirachy left join fetch projectHirachy.projectId projects left join fetch projectHirachy.projectClientsId pc  "
				+ " left join fetch projectHirachy.approverUserId approverUser where approverUser.id = :approverUserId and pc.id = :projectClientId and projectHirachy.status = '1' ";
		Collection<ExpenseProjectApproverHierarchy> projectApproverHierarchies = session.createQuery(query.toString()).setLong("approverUserId", approverUserId).setLong("projectClientId", projectClientId).list();

		ExpenseProjectApproverHierarchy ph = null;
		if (projectApproverHierarchies != null && projectApproverHierarchies.size() > 0) {
			ph = (ExpenseProjectApproverHierarchy) projectApproverHierarchies.iterator().next();
		}
		return ph;
	}

	@Override
	@Transactional
	public String getExpenseApprovalMechanismTypeByProjectClientId(Long projectClientId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("In ExpenseProjectApproverHierarchy getProjectApproverByProjectIdAndLevel method");
		String query = "from ExpenseProjectApproverHierarchy projectHirachy where projectHirachy.projectClientsId.id = :projectClientId ";
		Collection<ExpenseProjectApproverHierarchy> projectApproverHierarchies = session.createQuery(query.toString()).setLong("projectClientId", projectClientId).list();

		ExpenseProjectApproverHierarchy ph = null;
		if (projectApproverHierarchies != null && projectApproverHierarchies.size() > 0) {
			ph = (		ExpenseProjectApproverHierarchy) projectApproverHierarchies.iterator().next();
		}
		return ph.getApprovalMechanismType();
	}
	
}
