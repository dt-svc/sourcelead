package com.bis.sourcelead.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesApproverHierarchyDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;

/**
*
* @author  Pradeep Reddy
*          
*          |Ver_Number|  |Modified By|   |Modified On|  |Comments|
*          -----------   -------------   -------------  ----------
* @version    1.0        Pradeep Reddy     New_File       New_File
* 
* @since   2017-06-02
*/

@Repository
public class ExpensesApproverHierarchyDaoImpl implements ExpensesApproverHierarchyDao {
	private static final Logger logger = LoggerFactory.getLogger(ExpensesApproverHierarchyDaoImpl.class);
	
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
	
	@Autowired
	private UserDao userDao;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Expenses> getTotalMyExpensesListByMasterCandidateIdForEmployeeLogin(Long masterCandidateId,
			 String submissionStatus) throws Exception {
		Map<String, Object> params = new HashMap<String,Object>();
			String subQuery2 = "";
			if ("APPROVED".equalsIgnoreCase(submissionStatus)) {
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'APPROVED' ";
			}
			if ("SUBMITTED".equalsIgnoreCase(submissionStatus)) {
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'SUBMITTED' ";
			}
			if ("REJECTED".equalsIgnoreCase(submissionStatus)) {
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'REJECTED' ";
			}
			if ("DRAFT".equalsIgnoreCase(submissionStatus)) {
				subQuery2 = subQuery2 + " expenses.expenseStatus = 'DRAFT' ";
			}
		
		Session session = this.sessionFactory.getCurrentSession();
		String from = "from Expenses expenses left join fetch expenses.orgLocationId left join fetch expenses.masterCandidateId mc left join fetch expenses.projectMembers pm  left join fetch pm.projectClientsId pc left join fetch pm.employee emp left join fetch emp.user user where  mc.id =:masterCandidateId and ( " + subQuery2 +" ) ";
		params.put("masterCandidateId",masterCandidateId);
		
		Query query = session.createQuery(from);
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("masterCandidateId")){
				query.setParameter(str, (long) params.get(str));
			}
			
        }
		return query.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus,String isGlobalAccess) throws Exception {
		Map<String, Object> params = new HashMap<String,Object>();
		String subQuery2 = "";
		if ("SUBMITTEDFORAPPROVAL".equalsIgnoreCase(submissionStatus)) {
			subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'SUBMITTEDFORAPPROVAL' ";
		}
		if ("APPROVED".equalsIgnoreCase(submissionStatus)) {
			subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'APPROVED' ";
		}
		if ("REJECTED".equalsIgnoreCase(submissionStatus)) {
			subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'REJECTED' ";
		}
		if ("REOPENED".equalsIgnoreCase(submissionStatus)) {
			subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'REOPENED' ";
		}
		
		String globalUserQuery = "";
	    if(StringUtils.isNotEmpty(isGlobalAccess) && isGlobalAccess.equalsIgnoreCase("Y")) {
	    	globalUserQuery = " pc.orgLocationsId.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :orgLocationId) and ";
	    }else {
	    	globalUserQuery = " pc.orgLocationsId.id = :orgLocationId and ";
	    }
		 
		Session session = this.sessionFactory.getCurrentSession();
		String from = "from  ExpensesApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pc left join fetch hierarchy.approverUserId approver left join fetch sah.expenseId exp  left join fetch exp.projectMembers pm left join fetch pm.projectMemberCandidteId cand left join fetch cand.orgRelationId rel left join fetch pm.projects projects left join fetch pm.employee emp left join fetch emp.user user where "
			+globalUserQuery
			+ " pc.orgLocationsId.id = :orgLocationId"
			+ " and ( ( hierarchy.approverUserId.id = :userId )"
		    /*
			 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
			 */
			+ "  OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from ExpensesApproverHierarchy subsah where subsah.expenseId.id = sah.expenseId.id ) and  "+Constants.loginUserAsAdminWithMaxHeirarchyLevelForExp+")  "
		    +"   OR  (sah.hierarchyId.level = '1' and  sah.hierarchyId.status = '1'  and pm.projects.isReportingManagerAsApprover = 'Y'  and sah.hierarchyId.projectClientsId.orgLocationsId.id = pm.projectMemberCandidteId.orgLocationId.id and pm.projectMemberCandidteId.consultantUser IS NOT NULL and :userId IN (Select userRole.reportingManager.id from UserRole userRole where userRole.user.id = pm.projectMemberCandidteId.consultantUser.id))"
			+ "   ) and ( " + subQuery2 +" )";
		
		
		params.put("userId",userId);
		params.put("orgLocationId",orgLocationId);
		
		Query query = session.createQuery(from);
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("userId")){
				query.setParameter(str, (long) params.get(str));
			}
			if(str.equalsIgnoreCase("orgLocationId")){
				query.setParameter(str, (long) params.get(str));
			}
        }
		return query.list();
	}

	
}
