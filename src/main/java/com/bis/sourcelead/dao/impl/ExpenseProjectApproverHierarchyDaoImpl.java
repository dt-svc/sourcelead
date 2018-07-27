package com.bis.sourcelead.dao.impl;


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
	
}
