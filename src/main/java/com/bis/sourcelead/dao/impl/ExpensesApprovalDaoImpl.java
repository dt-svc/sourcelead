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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesApprovalDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class ExpensesApprovalDaoImpl implements ExpensesApprovalDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	UserDao userDao;
	
	
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
	
			
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateApprovalStatusByApproverForExpense(Long expenseId,Long approverUserId,String status,String rejectedReason) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		User loginUser = userDao.getUserById(approverUserId);
		
		/*
		 *  get ExpensesApproverHierarchy list by using empId and logged in user id as approver(either user may exist in project hierarchy  or user may be admin)
		 */
		String query = " from ExpensesApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.expenseId expense left join fetch expense.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user "
				+ " where pm.id =:projectMemberId and expense.id =:expenseId"
				        + "and ("
				           /*
						   *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
						   */
					        + "    hierarchy.approverUserId.id = :approverUserId  "
					        /*
							 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
							 */
					          + " OR ( :approverUserId NOT IN( select subsah.hierarchyId.approverUserId.id from ExpensesApproverHierarchy subsah where subsah.expenseId.projectMembers.id = :projectMemberId and subsah.expenseApprovalStatus != 'APPROVED' and subsah.expenseId.id = :expenseId) "
					        + " and "+ Constants.loginUserAsAdminWithMaxHeirarchyLevel + ")"
				            + ")";
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> sahMainList = session.createQuery(query)
																	  .setLong("expenseId", expenseId)	
																	  .setLong("approverUserId",approverUserId)
																	  .setLong("userId",approverUserId)
				                                                      .list();
		
	
		if(sahMainList != null && !sahMainList.isEmpty()){
	    	for(ExpensesApproverHierarchy sah :sahMainList){
	    		/*
	    		 * get Max Level
	    	    */
	    		Query queryForMaxLevel = session.createQuery("select Max(sah.hierarchyId.level) from ExpensesApproverHierarchy sah  where  sah.expenseId.id = :expenseId ");
	    		List<Integer> maxList = queryForMaxLevel.setLong("expenseId", expenseId).list();
	    		Integer maxLevel = 0;
	    		if(maxList !=null && !maxList.isEmpty()){
	    			maxLevel = maxList.iterator().next();
	    		}
	    		
	    		if(status!=null && status.equalsIgnoreCase("REJECTED")){
            	 	sah.setExpenseApprovalStatus(Submission_ApprovalStatus.REJECTED);
            	 	sah.setExpenseStatus(Submission_Status.REJECTED);
            	 	sah.setActionPerformedBy(""+loginUser.getFullName());
            	 	sah.setRejectedReason(rejectedReason);
            	 	sah.setReopenReason(null);
            	    sah.setReopenedUser(null);
            	    sah.setReopenRejectedReason(null);
            	 	
	             }else if(status!=null && status.equalsIgnoreCase("APPROVED")){
	            	 sah.setExpenseApprovalStatus(Submission_ApprovalStatus.APPROVED);
	            	 sah.setExpenseStatus(Submission_Status.APPROVED);
	            	 sah.setReopenReason(null);
	            	 sah.setReopenedUser(null);
	            	 sah.setReopenRejectedReason(null);
	            	 sah.setActionPerformedBy(""+loginUser.getFullName());
	             }
	    		 try {
	    			 updateSubmissionApproverHierarchy(sah);
				} catch (Exception e) {
				}
	    		
	    		String queryForAllApprovers = " from ExpensesApproverHierarchy sah left join fetch "
	    				+ " sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.expenseId expense left join fetch expense.projectMembers pm  left join fetch pm.employee emp left join fetch emp.user where sah.id <> :id and  sah.expenseId = :expenseId";
	    		@SuppressWarnings("unchecked")
        		List<ExpensesApproverHierarchy> approversList = session.createQuery(queryForAllApprovers)
        																	  .setLong("expenseId", sah.getExpenseId().getId())
        																	  .setLong("id", sah.getId())
        				                                                      .list();
	    		
                	if(approversList != null && !approversList.isEmpty()){
                		for(ExpensesApproverHierarchy approver  :  approversList){
                			 if(status!=null && status.equalsIgnoreCase("REJECTED")){
                				approver.setExpenseApprovalStatus(Submission_ApprovalStatus.REJECTED);
                				approver.setExpenseStatus(Submission_Status.REJECTED);
                				approver.setActionPerformedBy(" "+loginUser.getFullName());
                				approver.setRejectedReason(rejectedReason);
                				approver.setReopenReason("");
                				approver.setReopenedUser(null);
                				approver.setReopenRejectedReason("");
                        	  	
            	              }
                			 else if(sah.getHierarchyId().getLevel()==(maxLevel) && (status!=null && status.equalsIgnoreCase("APPROVED"))){
		            	            	 approver.setExpenseApprovalStatus(Submission_ApprovalStatus.APPROVED);
		            	            	 approver.setExpenseStatus(Submission_Status.APPROVED);
		            	            	 approver.setActionPerformedBy(" "+loginUser.getFullName());
		            	            	 approver.setReopenReason("");
		                 				 approver.setReopenedUser(null);
		                 				 approver.setReopenRejectedReason("");
	                		  }
                	        try {
                	        	updateSubmissionApproverHierarchy(approver);
             				} catch (Exception e) {
             				}
                	   }
                	}
	    	}
	    }
		
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesApproverHierarchy updateSubmissionApproverHierarchy(
			ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		if(expensesApproverHierarchy != null){
			 session.saveOrUpdate(expensesApproverHierarchy);
		}
		if(expensesApproverHierarchy == null){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"SUBMISSION_APPROVE_HIERARCHY"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
	   
	    return expensesApproverHierarchy;
	}

}