package com.bis.sourcelead.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ExpensesApproverHierarchyDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesCount;
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
	 * Expenses dashboard counts for employee expenses tab(Expenses dial)
	 * 
	 */
	
	@Override
	public ExpensesCount getExpensesCountByUserIdAndOrgLocationIdForApprover(Long userId, Long orgLocationId,String isGlobalAccess)
			throws Exception {
		        ExpensesCount expCount = new ExpensesCount();
				
		        Collection<ExpensesApproverHierarchy> submittedForApprovalEmployeeList   = expensesApproverHierarchyDao.getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"SUBMITTEDFORAPPROVAL",isGlobalAccess);
				expCount.setEmpExpensesSubmittedForApprovalCount(submittedForApprovalEmployeeList.size());
				
				Collection<ExpensesApproverHierarchy> reopenEmployeeList   = expensesApproverHierarchyDao.getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"REOPENED",isGlobalAccess);
				expCount.setEmpExpensesReopenCount(reopenEmployeeList.size());
				
				Collection<ExpensesApproverHierarchy> approvedEmployeeList   = expensesApproverHierarchyDao.getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"APPROVED",isGlobalAccess);
				expCount.setEmpExpensesApprovedCount(approvedEmployeeList.size());
				
				Collection<ExpensesApproverHierarchy> rejectedEmployeeList   = expensesApproverHierarchyDao.getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId, orgLocationId,"REJECTED",isGlobalAccess);
				expCount.setEmpExpensesRejectedCount(rejectedEmployeeList.size());
				
				return expCount;
	}
     
	/**
	 * Expenses dashboard counts for My expenses tab(Expenses dial)
	 * 
	 */
	@Override
	public ExpensesCount getExpensesCountByMasterCandidateId(Long masterCandidateId) throws Exception {
		    
		    ExpensesCount expCount = new ExpensesCount();
		 
			Collection<Expenses> draftMyexpensesList = expensesApproverHierarchyDao.getTotalMyExpensesListByMasterCandidateIdForEmployeeLogin(masterCandidateId, "DRAFT");
			Integer expensesDraftCount = draftMyexpensesList.size();
			expCount.setExpensesDraftCount(expensesDraftCount);
			
			Collection<Expenses> submittedMyexpensesList = expensesApproverHierarchyDao.getTotalMyExpensesListByMasterCandidateIdForEmployeeLogin(masterCandidateId, "SUBMITTED");
			expCount.setExpensesSubmittedForApprovalCount(submittedMyexpensesList.size());
			
			Collection<Expenses> approvedMyexpensesList = expensesApproverHierarchyDao.getTotalMyExpensesListByMasterCandidateIdForEmployeeLogin(masterCandidateId, "APPROVED");
			expCount.setExpensesApprovedCount(approvedMyexpensesList.size());
			
			Collection<Expenses> rejectedMyexpensesList = expensesApproverHierarchyDao.getTotalMyExpensesListByMasterCandidateIdForEmployeeLogin(masterCandidateId, "REJECTED");
			expCount.setExpensesRejectCount(rejectedMyexpensesList.size());
			
			return expCount;
	}
	
	
	
	
}
