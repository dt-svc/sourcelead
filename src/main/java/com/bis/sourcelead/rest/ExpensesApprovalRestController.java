package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class ExpensesApprovalRestController {

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ExpensesApprovalService expensesApprovalService;
	
	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	private ExpensesEntriesService expensesEntriesService;
	
	@RequestMapping(value = "/getExpensesApprovalList")
	public String getExpensesApprovalList(HttpServletRequest request) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesApprovalService.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser, orgLoc);
			return commonUtil.toJSON(expApperoverHierarchy);
   }
	
	
	@RequestMapping(value = "/getExpensesApprovalDetailsByExpenseId")
	public String getExpensesApprovalListByExpenseId(@RequestParam(value="expenseId", required=false) String expenseId) throws Exception {
		Expenses expenses = expensesService.getExpenseByExpenseId(Long.parseLong(expenseId));
		expenses.setStartDateStr(Constants.DMY.format(expenses.getStartDate()));
		if(null != expenses.getEndDate()){
		expenses.setEndDateStr(Constants.DMY.format(expenses.getEndDate()));
		}	
		return commonUtil.toJSON(expenses);
   }
	
	
	@RequestMapping(value = "/getExpensesApprovalCategoryByExpenseId")
	public String getExpensesApprovalCategoryByExpenseId(@RequestParam(value="expenseId", required=false) String expenseId) throws Exception {
		List<ExpensesEntries> expensesEntries = expensesEntriesService.getExpensesEntriesByExpenseId(Long.parseLong(expenseId));
		for(ExpensesEntries expEntries:expensesEntries){
			expEntries.setExpenseDateString(Constants.DMY.format(expEntries.getExpenseDate()));
		}
		return commonUtil.toJSON(expensesEntries);
   }
	
	
	
	
	
	
    @RequestMapping(value = "/saveExpenseApprovalStatusByExpenseId", method = RequestMethod.POST)
    public  String saveExpenseApprovalStatusByExpenseId(HttpServletRequest request,@RequestParam(value="expenseId", required=false) Long expenseId,
                                                @RequestParam(value="approvalStatus", required=false) String approvalStatus,@RequestParam(value="rejectedReason", required=false) String rejectedReason) throws Exception{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	expensesApprovalService.updateApprovalStatusByApproverForExpense(expenseId, user.getId(),approvalStatus, rejectedReason);
        return "success";
    }
	
}
