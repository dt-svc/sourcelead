package com.bis.sourcelead.dto.helper;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.TreeMap;

import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesEntriesDTO;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.CommonUtil;

public class ExpensesDTOConverter {

	public static ExpensesDTO  getExpensesDTOFromExpenses(OrgLocations orgLoc,Expenses expenses,ExpensesDTO expensesDTO){
		expensesDTO = new ExpensesDTO();
		expensesDTO.setId(expenses.getId());
		expensesDTO.setExpenseAutoId(expenses.getExpenseId());
		expensesDTO.setExpensePurpose(expenses.getPurpose());
		expensesDTO.setTotalAmount(expenses.getTotalAmount()); 
		expensesDTO.setTotalsubmittedAmount(expenses.getTotalsubmittedAmount());
		
		if(expenses.getOrgRelationUUID() != null){
		expensesDTO.setOrgRelationUUID(expenses.getOrgRelationUUID());
		}
		/*BigDecimal totalAmo = new BigDecimal(expenses.getTotalAmount());
		BigDecimal totalAmountRoundOff = totalAmo.setScale(2, BigDecimal.ROUND_HALF_EVEN);*/
		expensesDTO.setExpenseAmountDivId( expenses.getTotalAmount());
		expensesDTO.setClientName(expenses.getClientName());
		if(expenses.getProjectMembers() != null && expenses.getProjectMembers().getProjects() != null){
			expensesDTO.setProjectMemberId(expenses.getProjectMembers().getId());
			expensesDTO.setMasterCandidateId(expenses.getProjectMembers().getMasterCandidateId().getId());
			expensesDTO.setProjectId(expenses.getProjectMembers().getProjects().getId());
			expensesDTO.setProjectName(expenses.getProjectMembers().getProjects().getProjectName());
			expensesDTO.setProjectUUID(expenses.getProjectMembers().getProjects().getProjectUUID());
			expensesDTO.setProjectClientUUID(expenses.getProjectMembers().getProjectClientUUID());
			expensesDTO.setProjectStatus(expenses.getProjectMembers().getProjects().getProjectStatus().name());
			if(expenses.getBillingStatus() != null) {
			expensesDTO.setBillingStatus(expenses.getBillingStatus().toUpperCase());
			}
			SimpleDateFormat dateFormat=orgLoc.getLocFullFormDateFormatter();
			if(expenses.getProjectMembers().getProjectAssignedDate() != null) {
			expensesDTO.setProjectMemberStartDate(dateFormat.format(expenses.getProjectMembers().getProjectAssignedDate()));
			}
			SimpleDateFormat dateFormat1=orgLoc.getLocFullFormDateFormatter();
			if(expenses.getProjectMembers().getProjectEndDate() != null) {
			expensesDTO.setProjectMemberEndDate(dateFormat1.format(expenses.getProjectMembers().getProjectEndDate()));
		}
		}
		SimpleDateFormat dateFormat=orgLoc.getLocFullFormDateFormatter();
		if(expenses.getStartDate() != null){
			expensesDTO.setStartDate(dateFormat.format(expenses.getStartDate()));	
		}
		if(expenses.getEndDate() != null){
			expensesDTO.setEndDate(dateFormat.format(expenses.getEndDate()));	
		}
		if(expenses.getSubmittedDate() != null) {
			expensesDTO.setSubmittedDate(dateFormat.format(expenses.getSubmittedDate()));	
		}
		
		expensesDTO.setExpenseLocationCode(expenses.getOrgLocationId().getLocationCode());
		expensesDTO.setNoOfExpenses(expenses.getNoOfExpenses());
		TreeMap<Long,ExpensesEntriesDTO> eeDTOMap = new TreeMap<>();
		if(expenses.getExpensesEntries() != null && !expenses.getExpensesEntries().isEmpty() && expenses.getExpensesEntries().size() > 0){
			for(ExpensesEntries ee : expenses.getExpensesEntries()){
				if(ee != null) {
					eeDTOMap.put(ee.getId(), ExpensesEntriesDTOConverter.getExpensesEntriesDTOFromExpensesEntries(orgLoc,ee, expensesDTO));
				}
			}
		}
		if(expenses.getProjectMembers() != null && expenses.getProjectMembers().getProjectMemberCandidteId() != null){
			expensesDTO.setProjectMemberName(expenses.getProjectMembers().getEmpName());
		}
		if(eeDTOMap != null && !eeDTOMap.isEmpty()){
			expensesDTO.setExpenseEntry(new LinkedList(eeDTOMap.values()));
		}
		expensesDTO.setExpensesRejectedReason(expenses.getExpensesRejectedReason());
		expensesDTO.setExpenseStatus(expenses.getExpenseStatus().getValue().toUpperCase());
		
		return expensesDTO;
	}
}
