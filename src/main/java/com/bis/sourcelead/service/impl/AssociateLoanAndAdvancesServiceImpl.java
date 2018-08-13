package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AssociateLoanAndAdvancesDao;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AssociateLoanAndAdvancesServiceImpl implements AssociateLoanAndAdvancesService{
	
	@Autowired
	AssociateLoanAndAdvancesDao associateLoanAndAdvancesDao;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public AssociateLoanAndAdvances saveAssociateLoanAndAdvances(AssociateLoanAndAdvances associateLoanAndAdvances)
			throws Exception {
		return associateLoanAndAdvancesDao.saveAssociateLoanAndAdvances(associateLoanAndAdvances);
	}

	@Override
	public AssociateLoanAndAdvances getAssociateLoanAndAdvancesById(Long id) throws Exception {
		return associateLoanAndAdvancesDao.getAssociateLoanAndAdvancesById(id);
	}

	@Override
	public AssociateLoanAndAdvances storeAssociateLoanAndAdvances(
			AssociateLoanAndAdvancesDTO associateLoanAndAdvancesDTO, OrgLocations orgLocations, User user)
			throws Exception {
		
		AssociateLoanAndAdvances loanAndAdv = null;
		if(associateLoanAndAdvancesDTO.getLoanAndAdvId() != null && !associateLoanAndAdvancesDTO.getLoanAndAdvId().isEmpty()) {
			loanAndAdv = getAssociateLoanAndAdvancesById(Long.valueOf(associateLoanAndAdvancesDTO.getLoanAndAdvId()));
		}else {
			loanAndAdv = new AssociateLoanAndAdvances();
			loanAndAdv.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
		loanAndAdv.setLoanAdvanceType(associateLoanAndAdvancesDTO.getLoanAndAdvType());
		PayrollLoanAndAdvances padv = new PayrollLoanAndAdvances();
		padv.setId(Long.valueOf(associateLoanAndAdvancesDTO.getLoanAndAdvName()));
		loanAndAdv.setPayrollLoanAndAdvanceId(padv);
		loanAndAdv.setLoanAndAdvanceStatus(associateLoanAndAdvancesDTO.getLoanAndAdvStatus());
		loanAndAdv.setAmount(Double.valueOf(associateLoanAndAdvancesDTO.getLoanAmount()));
		loanAndAdv.setScheduler(associateLoanAndAdvancesDTO.getScheduler());
		loanAndAdv.setEffectiveDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), associateLoanAndAdvancesDTO.getEffectiveDate()));
		if(associateLoanAndAdvancesDTO.getIssuedDate() != null && !associateLoanAndAdvancesDTO.getIssuedDate().isEmpty()) {
			loanAndAdv.setIssuedDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), associateLoanAndAdvancesDTO.getIssuedDate()));
		}
		
		if(associateLoanAndAdvancesDTO.getEndDate() != null && !associateLoanAndAdvancesDTO.getEndDate().isEmpty()) {
			loanAndAdv.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), associateLoanAndAdvancesDTO.getEndDate()));
		}
		
		loanAndAdv.setNoOfSchedules(Integer.valueOf(associateLoanAndAdvancesDTO.getNoOfSchedules()));
		loanAndAdv.setDeductAmount(Double.valueOf(associateLoanAndAdvancesDTO.getDeductAmount()));
		
		if(associateLoanAndAdvancesDTO.getPaidAmount() != null && !associateLoanAndAdvancesDTO.getPaidAmount().isEmpty()) {
			loanAndAdv.setPaidAmount(Double.valueOf(associateLoanAndAdvancesDTO.getPaidAmount()));
		}
		
		if(associateLoanAndAdvancesDTO.getPendingAmount() != null && !associateLoanAndAdvancesDTO.getPendingAmount().isEmpty()) {
			loanAndAdv.setPendingAmount(Double.valueOf(associateLoanAndAdvancesDTO.getPendingAmount()));
		}
		
		User approverUser = new User();
		approverUser.setId(Long.valueOf(associateLoanAndAdvancesDTO.getLoanAndAdvApprover()));
		
		if(associateLoanAndAdvancesDTO.getDescription() != null && !associateLoanAndAdvancesDTO.getDescription().isEmpty()) {
			loanAndAdv.setDescription(associateLoanAndAdvancesDTO.getDescription());
		}
		
		saveAssociateLoanAndAdvances(loanAndAdv);
		
		return loanAndAdv;
	}

}
