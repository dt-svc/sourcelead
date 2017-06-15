package com.bis.sourcelead.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ExpensesApproverHierarchyDao;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesCount;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.service.ExpensesApproverHierarchyService;

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

@Service
public class ExpensesApproverHierarchyServiceImpl implements ExpensesApproverHierarchyService {

	@Autowired
	ExpensesApproverHierarchyDao expensesApproverHierarchyDao;
    
	/**
	 * employees For counts
	 */
	
	@Override
	public ExpensesCount getExpensesCountByUserIdAndOrgLocationIdForApprover(Long userId, Long orgLocationId)
			throws Exception {
		   
		        ExpensesCount expCount = new ExpensesCount();
			 
			    Collection<ExpensesApproverHierarchy> approvedindividualEmployeeList = expensesApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,  "APPROVED");
			    expCount.setExpensesApprovedCount(approvedindividualEmployeeList.size());;
				 
				Collection<ExpensesApproverHierarchy> rejectedindividualEmployeeList = expensesApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId, "REJECTED");
				expCount.setExpensesRejectCount(rejectedindividualEmployeeList.size());
				
				Collection<ExpensesApproverHierarchy> submittedForApprovalindividualEmployeeList   = expensesApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"SUBMITTED");
				expCount.setExpensesSubmittedForApprovalCount(submittedForApprovalindividualEmployeeList.size());
				
				
				Collection<ExpensesApproverHierarchy> draftEmployeeList   = expensesApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"DRAFT");
				expCount.setExpensesDraftCount(draftEmployeeList.size());
				
			    return expCount;
	}
     
	/**
	 * Myexpenses For counts
	 */
	@Override
	public ExpensesCount getExpensesCountByUserIdAndOrgLocationId(Long userId, Long orgLocationId) throws Exception {
		    
		    ExpensesCount expCount = new ExpensesCount();
		 
			Collection<ExpensesApproverHierarchy> draftMyexpensesList = expensesApproverHierarchyDao.getTotalDraftListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId,"DRAFT");
			Integer expensesDraftCount = draftMyexpensesList.size();
			expCount.setExpensesDraftCount(expensesDraftCount);
			
			Collection<ExpensesApproverHierarchy> submittedMyexpensesList = expensesApproverHierarchyDao.getTotalSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, "SUBMITTED",orgLocationId);
			expCount.setExpensesSubmittedForApprovalCount(submittedMyexpensesList.size());
			
			Collection<ExpensesApproverHierarchy> approvedMyexpensesList = expensesApproverHierarchyDao.getTotalApprovedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, "APPROVED");
			expCount.setExpensesApprovedCount(approvedMyexpensesList.size());
			
			Collection<ExpensesApproverHierarchy> rejectedMyexpensesList = expensesApproverHierarchyDao.getTotalRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(userId, orgLocationId, "REJECTED");
			expCount.setExpensesRejectCount(rejectedMyexpensesList.size());
			
			return expCount;
	}
	
	
	
	
}
