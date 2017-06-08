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
		String query = "from  ExpensesApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId approver left join fetch sah.expenseId exp  left join fetch exp.projectMembers pm left join fetch pm.projects projects left join fetch pm.employee emp left join fetch emp.user user where exp.orgLocationId.id = :orgLocationId"
				
			+ " and ( ("
			+ "      hierarchy.approverUserId.id = :userId and "
			+ "       (     (hierarchy.level = '2' and emp.user.id NOT IN(select subeah.hierarchyId.approverUserId.id from ExpensesApproverHierarchy subeah where subeah.expenseId.id = sah.expenseId.id and subeah.hierarchyId.level = '2')) "
			+ "          OR (hierarchy.level = '1' and emp.user.id NOT IN(select subeah.hierarchyId.approverUserId.id from ExpensesApproverHierarchy subeah where subeah.expenseId.id = sah.expenseId.id) ) "
			+ "       )"
			+ "      ) "
		    /*
			 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
			 */
				+ "  OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from ExpensesApproverHierarchy subsah where subsah.expenseId.id = sah.expenseId.id ) and "+Constants.loginUserAsAdminWithMaxHeirarchyLevel+") "
				+ "   )";
				
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return tslist;
	}

}