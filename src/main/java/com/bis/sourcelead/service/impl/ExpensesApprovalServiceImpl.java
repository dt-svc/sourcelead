package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesApprovalDao;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.OrgRelationsService;

@Service
public class ExpensesApprovalServiceImpl implements ExpensesApprovalService{

	@Autowired
	private ExpensesApprovalDao expensesApprovalDao;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	
	
	
	@Override
	public List<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(User loginUser, OrgLocations orgLoc) throws Exception {
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesApprovalDao.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser.getId(),orgLoc.getId());
		for(ExpensesApproverHierarchy expensesApproverHierarchy : expApperoverHierarchy){
			
			if(null != expensesApproverHierarchy.getExpenseId().getEndDate()){
				expensesApproverHierarchy.setExpenseStartDateString(Constants.DMY.format(expensesApproverHierarchy.getExpenseId().getStartDate()));
				expensesApproverHierarchy.setExpenseEndDateString(Constants.DMY.format(expensesApproverHierarchy.getExpenseId().getEndDate()));
			}else{
				expensesApproverHierarchy.setExpenseStartDateString(Constants.DMY.format(expensesApproverHierarchy.getExpenseId().getStartDate()));
			}
			
			if (null != expensesApproverHierarchy.getExpenseId()) {
				if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers()) {
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects()){
								expensesApproverHierarchy.setExpensesProjectName(expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects().getProjectName());
							}else{
								expensesApproverHierarchy.setExpensesProjectName("--");
							}
					
					if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee()) {
						
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId() && !"".equalsIgnoreCase(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId())){
								expensesApproverHierarchy.setExpensesEmployeeUserId(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId());
							}else{
								expensesApproverHierarchy.setExpensesEmployeeUserId("--");
							}
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUserEmploymentType() && !"".equalsIgnoreCase(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUserEmploymentType())){
								String empType = expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUserEmploymentType();
								if(empType.equalsIgnoreCase("C2C") || empType.equalsIgnoreCase("C2C-1099")){
									OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getOrgLocations().getId(), expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getOrgRelationId().getId());
									expensesApproverHierarchy.setUserTypeVendorName(orgRelation.getResponderLocationId().getOrg().getOrgName());
									expensesApproverHierarchy.setExpenseEmpUserType(empType);
								 }else{
									 expensesApproverHierarchy.setExpenseEmpUserType(empType);
								 }
							}else{
								expensesApproverHierarchy.setExpenseEmpUserType("--");
							}
						
						if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser()) {
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName()){
								StringBuilder firstLastName = new StringBuilder();
								expensesApproverHierarchy.setExpenseEmpUserFirstNameLastName(firstLastName.append(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getFirstName()).append("").append(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName()).toString());
							}else{
								expensesApproverHierarchy.setExpenseEmpUserFirstNameLastName(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getFirstName());	
							}
								expensesApproverHierarchy.setExpenseEmpUserEmail(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getEmail());
								expensesApproverHierarchy.setExpensesUserid(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getId());
						}
					}
				}
			}

		 }
		return expApperoverHierarchy;
	}




	@Override
	public ExpensesApproverHierarchy updateSubmissionApproverHierarchy(
			ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception {
		return expensesApprovalDao.updateSubmissionApproverHierarchy(expensesApproverHierarchy);
	}




	@Override
	public void updateApprovalStatusByApproverForExpense(Long expenseId, Long approverUserId, String status,
			String rejectedReason) throws Exception {
		 expensesApprovalDao.updateApprovalStatusByApproverForExpense(expenseId, approverUserId, status, rejectedReason);
	}
}