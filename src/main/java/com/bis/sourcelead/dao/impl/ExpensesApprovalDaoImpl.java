package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesApprovalDao;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;

@Repository
public class ExpensesApprovalDaoImpl implements ExpensesApprovalDao{

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
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(Long userId,Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ExpensesApproverHierarchy eah left join fetch eah.hierarchyId hierarchy left join fetch hierarchy.approverUserId approver left join fetch eah.expenseId exp  left join fetch exp.projectMembers pm left join fetch pm.projects projects left join fetch pm.employee emp left join fetch emp.user user where exp.orgLocationId.id = :orgLocationId"
				+ " and ( "
				+ "      (  projects.id IN ( select pah.projectId.id from ProjectApproverHierarchy pah  where pah.approverUserId.id =:userId and ((pah.level = '2' and user.id NOT IN(select subpah.approverUserId.id from ProjectApproverHierarchy subpah where subpah.projectId.id = pah.projectId.id and subpah.level = '2')) OR (pah.level = '1' and user.id NOT IN(select subpah.approverUserId.id from ProjectApproverHierarchy subpah where subpah.projectId.id = pah.projectId.id )))))"
				+ "       OR " + Constants.loginUserAsAdminForProjects + "" + "     ) ";
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return tslist;
	}

}