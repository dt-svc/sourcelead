package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AddExpensesDao;
import com.bis.sourcelead.dao.ExpensesApprovalDao;
import com.bis.sourcelead.dao.ExpensesDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.Expenses;
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
	
	@Autowired
	ExpensesDao expensesDao;
	
	@Autowired
	AddExpensesDao addExpensesDao;
	
	
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
	public List<ExpensesApproverHierarchy> getExpensesApproverHierarchyListByExpenseId(Long expenseId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from  ExpensesApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId approver  left join fetch sah.expenseId exp  left join fetch exp.projectMembers pm left join fetch pm.projects projects left join fetch pm.employee emp left join fetch emp.user user "
			+ " where exp.id = :expenseId ";
				
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("expenseId", expenseId).setCacheable(true).list();
		return tslist;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(Long userId,Long orgLocationId, String expenseStatus, Date startDateUTC, Date endDateUTC) {
		Map<String, Object> params = new HashMap<String,Object>();
		String[] expStatus = expenseStatus.split(",");
		String subQuery2 = "";
		if (Arrays.asList(expStatus).contains("ALL") || StringUtils.isEmpty(expenseStatus)) {
			subQuery2 = subQuery2
					+ "  sah.expenseApprovalStatus = 'SUBMITTEDFORAPPROVAL' or sah.expenseApprovalStatus = 'APPROVED' or sah.expenseApprovalStatus = 'REJECTED' or sah.expenseApprovalStatus = 'REOPENED' ";
		}
		else{
			if (Arrays.asList(expStatus).contains("SUBMITTEDFORAPPROVAL")) {
				subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'SUBMITTEDFORAPPROVAL' ";
			}
			if (Arrays.asList(expStatus).contains("APPROVED")) {
				if(StringUtils.isNotEmpty(subQuery2)){
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'APPROVED' ";
			}
			if (Arrays.asList(expStatus).contains("REJECTED")) {
				if(StringUtils.isNotEmpty(subQuery2)){
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'REJECTED' ";
			}
			if (Arrays.asList(expStatus).contains("REOPENED")) {
				if(StringUtils.isNotEmpty(subQuery2)){
					subQuery2 = subQuery2 + " OR ";
				}
				subQuery2 = subQuery2 + " sah.expenseApprovalStatus = 'REOPENED' ";
			}
		}
		
		String startDateSubQuery = "";
		if(null != startDateUTC){
			startDateSubQuery = "and (sah.expenseId.startDate >= :startDateUTC)";
			  params.put("startDateUTC",startDateUTC);
		}
		
		String endDateSubQuery = "";
		if(null != endDateUTC){
			endDateSubQuery = "and (sah.expenseId.endDate <= :endDateUTC)";
			  params.put("endDateUTC",endDateUTC);
		}
		Session session = this.sessionFactory.getCurrentSession();
		String from = "from  ExpensesApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.projectClientsId pc left join fetch hierarchy.approverUserId approver left join fetch sah.expenseId exp  left join fetch exp.projectMembers pm left join fetch pm.projects projects left join fetch pm.employee emp left join fetch emp.user user where "
			+ " pc.orgLocationsId.id = :orgLocationId"
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
				+ "   ) and ( " + subQuery2 +" )"+startDateSubQuery+" "+endDateSubQuery+" ";
		
		
		params.put("userId",userId);
		params.put("orgLocationId",orgLocationId);
		
		Query query = session.createQuery(from);
		for (String str : query.getNamedParameters())
        {
			if(str.equalsIgnoreCase("userId")){
				query.setParameter(str, (long) params.get(str));
			}if(str.equalsIgnoreCase("startDateUTC")){
				query.setParameter( str, (Date) params.get(str) );
			}
			if(str.equalsIgnoreCase("endDateUTC")){
				query.setParameter( str, (Date) params.get(str) );
			}
			if(str.equalsIgnoreCase("orgLocationId")){
				query.setParameter(str, (long) params.get(str));
			}
			
        }
		return query.setCacheable(true).list();
				
		/*@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> tslist = session.createQuery(query.toString()).setLong("userId", userId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return tslist;*/
	}
	
	@Override	
	@Transactional(rollbackFor = { Exception.class })
	public User updateExpenseStatusByExpenseIdAndReOpenReason(Long expenseId,String reason,User loginUser, String type, String isSuperUser) {
		Session session = this.sessionFactory.getCurrentSession();
		Query expenseApproverHierarchyListQuery= session.createQuery("from  ExpensesApproverHierarchy eah left join fetch eah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch eah.expenseId expense  where expense.id = :expenseId");
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy>  expenseApproverHierarchyList=expenseApproverHierarchyListQuery.setLong("expenseId", expenseId).setCacheable(true).list();
	    if(expenseApproverHierarchyList != null && !expenseApproverHierarchyList.isEmpty()){
	    	for(ExpensesApproverHierarchy eah :expenseApproverHierarchyList){
	    		if(null != type && "notify".equalsIgnoreCase(type) && null != isSuperUser && isSuperUser.equalsIgnoreCase(Constants.YES)){
	    			eah.setReopenReason(reason);
	    			eah.setReopenedUser(loginUser);
	    			eah.getExpenseId().setExpenseStatus(Submission_Status.SUBMITTED);;
	    			eah.setExpenseStatus(Submission_Status.SUBMITTED);
	    			eah.setExpenseApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
	    			eah.setReopenRejectedReason(null);
	    		}else{
	    			if(null != type && "notify".equalsIgnoreCase(type)){
	    				eah.setReopenReason(reason);
	    				eah.setReopenedUser(loginUser);
	    				eah.setReopenRejectedReason(null);
		    		}else if(null != type && "reject".equalsIgnoreCase(type)){
		    			eah.setReopenRejectedReason(reason);
		    			eah.setReopenReason(null);
		    			eah.setExpenseStatus(Submission_Status.APPROVED);
		    			eah.setExpenseApprovalStatus(Submission_ApprovalStatus.REJECTED);
		    		}else{
		    			eah.setExpenseStatus(Submission_Status.SUBMITTED);
		    			eah.setExpenseApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
		    			eah.setReopenRejectedReason(null);
		    			eah.setReopenReason(null);
		    		}
	    		}
	    		try {
	    			 session.saveOrUpdate(eah);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	return expenseApproverHierarchyList.get(0).getReopenedUser();
	    }else{
	    	return null;
	    }
		
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer updateApprovalStatusByApproverForExpense(Long expenseId,Long approverUserId,String status,String rejectedReason) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		User loginUser = userDao.getUserById(approverUserId);
		
		/*
		 *  get ExpensesApproverHierarchy list by using empId and logged in user id as approver(either user may exist in project hierarchy  or user may be admin)
		 */
		String query = " from ExpensesApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.expenseId expense left join fetch expense.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user "
				+ " where expense.id =:expenseId "
				        + " and ("
				           /*
						   *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
						   */
					        + "    hierarchy.approverUserId.id = :approverUserId  "
					        /*
							 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
							 */
					          + " OR ( :approverUserId NOT IN( select subsah.hierarchyId.approverUserId.id from ExpensesApproverHierarchy subsah where  subsah.expenseApprovalStatus != 'APPROVED' and subsah.expenseId.id = :expenseId ) "
					        + " and "+ Constants.loginUserAsAdminWithMaxHeirarchyLevel + ")"
				            + ") ";
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> sahMainList = session.createQuery(query)
																	  .setLong("expenseId", expenseId)	
																	  .setLong("approverUserId",approverUserId)
																	  .setLong("userId",approverUserId)
				                                                      .list();
		
		
		Integer maxLevel = 0;
		ArrayList<String> expenseStatus = new ArrayList<>();
		if(sahMainList != null && !sahMainList.isEmpty()){
	    	for(ExpensesApproverHierarchy sah :sahMainList){
	    		/*
	    		 * get Max Level
	    	    */
	    		Query queryForMaxLevel = session.createQuery("select Max(sah.hierarchyId.level) from ExpensesApproverHierarchy sah  where  sah.expenseId.id = :expenseId ");
	    		List<Integer> maxList = queryForMaxLevel.setLong("expenseId", expenseId).list();
	    		
	    		if(maxList !=null && !maxList.isEmpty()){
	    			maxLevel = maxList.iterator().next();
	    		}
	    		
	    		if(status!=null && status.equalsIgnoreCase("REJECTED")){
	    			expenseStatus.add(Submission_Status.REJECTED.getValue());
            	 	sah.setExpenseApprovalStatus(Submission_ApprovalStatus.REJECTED);
            	 	sah.setExpenseStatus(Submission_Status.REJECTED);
            	 	sah.setActionPerformedBy(""+loginUser.getFullName());
            	 	sah.setRejectedReason(rejectedReason);
            	 	sah.setReopenReason(null);
            	    sah.setReopenedUser(null);
            	    sah.setReopenRejectedReason(null);
            	 	
	             }else if(status!=null && status.equalsIgnoreCase("APPROVED")){
	            	 expenseStatus.add(Submission_Status.APPROVED.getValue());
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
                			    expenseStatus.add(Submission_Status.APPROVED.getValue());
                				approver.getExpenseId().setExpenseStatus(Submission_Status.REJECTED);
                				approver.setExpenseApprovalStatus(Submission_ApprovalStatus.REJECTED);
                				approver.setExpenseStatus(Submission_Status.REJECTED);
                				approver.setActionPerformedBy(" "+loginUser.getFullName());
                				approver.setRejectedReason(rejectedReason);
                				approver.setReopenReason("");
                				approver.setReopenedUser(null);
                				approver.setReopenRejectedReason("");
                        	  	
            	              }
                			 else if(status!=null && status.equalsIgnoreCase("APPROVED")){
                				 if(approver.getHierarchyId().getProjectClientsId() != null && 
                					approver.getHierarchyId().getProjectClientsId().getOrgLocationsId() != null && 
                				    approver.getHierarchyId().getProjectClientsId().getOrgLocationsId().getOrg() != null && 
                				     approver.getHierarchyId().getProjectClientsId().getOrgLocationsId().getOrg().getOrgType().equals(Constants.GUEST)) {
                					     expenseStatus.add(Submission_Status.APPROVED.getValue());
                					     approver.getExpenseId().setExpenseStatus(Submission_Status.APPROVED);
    	            				 	 approver.setExpenseApprovalStatus(Submission_ApprovalStatus.APPROVED);
    	            	            	 approver.setExpenseStatus(Submission_Status.APPROVED);
    	            	            	 approver.setActionPerformedBy(" "+loginUser.getFullName());
    	            	            	 approver.setReopenReason("");
    	                 				 approver.setReopenedUser(null);
    	                 				 approver.setReopenRejectedReason("");
                				 }
                				 else if((approver.getHierarchyId().getProjectClientsId().getLevel() >=  sah.getHierarchyId().getProjectClientsId().getLevel())
                					     || (approver.getHierarchyId().getProjectClientsId().getLevel() ==  sah.getHierarchyId().getProjectClientsId().getLevel() && sah.getHierarchyId().getLevel()==(maxLevel))){
                					      expenseStatus.add(Submission_Status.APPROVED.getValue());
                					     approver.getExpenseId().setExpenseStatus(Submission_Status.APPROVED);
                					     approver.setExpenseApprovalStatus(Submission_ApprovalStatus.APPROVED);
    	            	            	 approver.setExpenseStatus(Submission_Status.APPROVED);
    	            	            	 approver.setActionPerformedBy(" "+loginUser.getFullName());
    	            	            	 approver.setReopenReason("");
    	                 				 approver.setReopenedUser(null);
    	                 				 approver.setReopenRejectedReason("");
                    		     }
                				 else{
                					 expenseStatus.add(Submission_Status.SUBMITTED.getValue());
                				 }
                			 }
                	        try {
                	        	updateSubmissionApproverHierarchy(approver);
             				} catch (Exception e) {
             				}
                	   }
                	}
	    	}
	    }
		Expenses expenses = expensesDao.getExpenseById(expenseId);
		if(expenseStatus.contains(Submission_Status.REJECTED.getValue())){
			expenses.setExpenseStatus(Submission_Status.REJECTED);
		}else if(expenseStatus.contains(Submission_Status.SUBMITTED.getValue())){
			expenses.setExpenseStatus(Submission_Status.SUBMITTED);
		}else{
			expenses.setExpenseStatus(Submission_Status.APPROVED);
		}
		addExpensesDao.storeAddExpensesData(expenses);
		return maxLevel;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public void updateExpenseStatusByExpenseId(Long expenseId,String status) {
		Session session = this.sessionFactory.getCurrentSession();
		Query expenseApproverHierarchyListQuery= session.createQuery("from  ExpensesApproverHierarchy eah left join fetch eah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch eah.expenseId expenses where expenses.id = :expenseId");
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy>  expensesApproverHierarchyList=expenseApproverHierarchyListQuery.setLong("expenseId", expenseId).setCacheable(true).list();
	    if(expensesApproverHierarchyList != null && !expensesApproverHierarchyList.isEmpty()){
	    	for(ExpensesApproverHierarchy eah :expensesApproverHierarchyList){
	    		if(status.equalsIgnoreCase("Draft")){
	    			eah.getExpenseId().setExpenseStatus(Submission_Status.DRAFT);
	    			eah.setExpenseStatus(Submission_Status.DRAFT);
	    			eah.setExpenseApprovalStatus(Submission_ApprovalStatus.NEW);
	    		}
	    		else if(status.equalsIgnoreCase("Submit")){
	    			eah.getExpenseId().setExpenseStatus(Submission_Status.SUBMITTED);
	    			eah.setExpenseStatus(Submission_Status.SUBMITTED);
	    			eah.setExpenseApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
	    		}
	    		try {
	    			 session.saveOrUpdate(eah);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesApproverHierarchy isThereAnyApproverApprovedTheExpense(Long expenseId) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		
		Query expensesApproverHierarchyListQuery= session.createQuery("from  ExpensesApproverHierarchy eah left join fetch eah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch eah.expenseId expense where expense.id = :expenseId  and eah.expenseApprovalStatus = :approvalStatus");
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> expenseApproverHierarchyList=expensesApproverHierarchyListQuery.setLong("expenseId", expenseId).setString("approvalStatus", "APPROVED").setCacheable(true).list();
		ExpensesApproverHierarchy eah = null;
		if(expenseApproverHierarchyList != null && !expenseApproverHierarchyList.isEmpty()){
			eah = expenseApproverHierarchyList.iterator().next();
		}
		return eah;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean isTheExpenseTotallyApproved(Long expenseId) {
		Session session = this.sessionFactory.getCurrentSession();
		Query expensesApproverHierarchyListQuery= session.createQuery("from  ExpensesApproverHierarchy eah left join fetch eah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch eah.expenseId expense where expense.id = :expenseId  and eah.expenseApprovalStatus != :approvalStatus");
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> expenseApproverHierarchyList=expensesApproverHierarchyListQuery.setLong("expenseId", expenseId).setString("approvalStatus", "APPROVED").setCacheable(true).list();
		Boolean flag = true;
		if(expenseApproverHierarchyList != null && !expenseApproverHierarchyList.isEmpty()){
			flag = false;
		}
		return flag;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesApproverHierarchy getexpensesApproverHierarchyForEmployeeByExpenseId(Long expenseId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		Query expensesApproverHierarchyListQuery= session.createQuery("from ExpensesApproverHierarchy eah left join fetch eah.hierarchyId left join fetch eah.expenseId expense  left join fetch expense.projectMembers pm where expense.id = :expenseId");
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> expenseApproverHierarchyList=expensesApproverHierarchyListQuery.setLong("expenseId", expenseId).setCacheable(true).list();
		//return expensesApproverHierarchyList;
		
		Boolean isRejected = false; 
	    Boolean isAPPROVED = false; 
	    Boolean isSUBMITTED = false; 
	    Boolean isNEW = false;
	    Boolean isDRAFT = false;
	    ExpensesApproverHierarchy expensesApproverHierarchy = null;
	    if(expenseApproverHierarchyList != null && !expenseApproverHierarchyList.isEmpty()){
	    	for(ExpensesApproverHierarchy eah : expenseApproverHierarchyList){
				
				if(eah.getExpenseStatus() == Submission_Status.REJECTED){
					isRejected   = true;  
					expensesApproverHierarchy = eah;
				}
				else if(!isRejected  && eah.getExpenseStatus() == Submission_Status.NEW){
					 isNEW   = true;  
					 expensesApproverHierarchy = eah;
				}
				else if(!isRejected && !isNEW &&  eah.getExpenseStatus() == Submission_Status.DRAFT){
					 isDRAFT   = true;  
					 expensesApproverHierarchy = eah;
				}
				else if(!isRejected && !isNEW && !isDRAFT && eah.getExpenseStatus() == Submission_Status.SUBMITTED){
					isSUBMITTED   = true;  
					expensesApproverHierarchy = eah;
				}
				else if(!isRejected && !isNEW && !isDRAFT && !isSUBMITTED && eah.getExpenseStatus() == Submission_Status.APPROVED ){
					 isAPPROVED   = true;  
					 expensesApproverHierarchy = eah;
				}
	      }
	    }
	    else{
	    	 expensesApproverHierarchy  = new ExpensesApproverHierarchy();
	    	 expensesApproverHierarchy.setExpenseStatus(Submission_Status.getNameByValue("new"));
	    	 expensesApproverHierarchy.setExpenseApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
	    }
		
		// TODO Auto-generated method stub
		return expensesApproverHierarchy;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ExpensesApproverHierarchy getExpensesApproverHierarchyByApproverUserIdAndExpenseId(Long approverUserId,Long expenseId,Long locationsId) throws Exception {
			
		Session session = this.sessionFactory.getCurrentSession();
		
		/*
		 *  get ExpensesApproverHierarchy list by using empId and logged in user id as approver(either user may exist in project hierarchy  or user may be admin)
		 */
		String query = " from ExpensesApproverHierarchy sah left join fetch sah.hierarchyId hierarchy left join fetch hierarchy.approverUserId left join fetch sah.expenseId expense left join fetch expense.projectMembers pm left join fetch pm.projects project left join fetch project.orgLocationsId left join fetch pm.employee emp left join fetch emp.user "
				+ " where expense.id =:expenseId "
				        + " and ("
				           /*
						   *  logged in user as approver in project_approver_hierarchy (Either Admins/project_managers/team leads ...etc)
						   */
					        + "    (hierarchy.approverUserId.id = :approverUserId)  "
					        /*
							 *  logged in user as admin but not in project_approver_hierarchy(hint:logged in user is admin but not assigned to project as approver)
							 */
					          + " OR ( :userId NOT IN( select subsah.hierarchyId.approverUserId.id from ExpensesApproverHierarchy subsah where subsah.expenseId.id = :expenseId and subsah.hierarchyId.projectClientsId.orgLocationsId.id = :locationsId) "
					        + " and "+ Constants.loginUserAsAdminWithMaxHeirarchyLevel + ")"
				            + ") ";
		@SuppressWarnings("unchecked")
		List<ExpensesApproverHierarchy> sahMainList = session.createQuery(query)
																	  .setLong("expenseId", expenseId)	
																	  .setLong("approverUserId",approverUserId)
																	  .setLong("locationsId",locationsId)
																	  .setLong("userId",approverUserId)
				                                                      .list();
		
		
		ExpensesApproverHierarchy eah = null;
		if(sahMainList != null && !sahMainList.isEmpty()){
			eah = sahMainList.iterator().next();
		}
		return eah;
	}


}