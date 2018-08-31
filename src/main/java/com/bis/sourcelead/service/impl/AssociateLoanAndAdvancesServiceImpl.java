package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateLoanAndAdvancesDao;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.Candidate;
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

	@Autowired
	CandidateDao candidateDao;
	
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
			Candidate candidate = candidateDao.getCandidateByConsultantUserId(Long.valueOf(associateLoanAndAdvancesDTO.getAssociateUserId()), orgLocations.getId());
			loanAndAdv.setCandidateId(candidate);
		}
		
		loanAndAdv.setLoanAdvanceType(associateLoanAndAdvancesDTO.getLoanAndAdvType());
		PayrollLoanAndAdvances padv = new PayrollLoanAndAdvances();
		padv.setId(Long.valueOf(associateLoanAndAdvancesDTO.getLoanAndAdvName()));
		loanAndAdv.setPayrollLoanAndAdvanceId(padv);
		loanAndAdv.setLoanAndAdvanceStatus(associateLoanAndAdvancesDTO.getLoanAndAdvStatus());
		loanAndAdv.setAmount(Double.valueOf(associateLoanAndAdvancesDTO.getLoanAmount()));
		loanAndAdv.setScheduler(associateLoanAndAdvancesDTO.getScheduler());
		loanAndAdv.setEffectiveDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), associateLoanAndAdvancesDTO.getEffectiveDate()));
		
		
		if(associateLoanAndAdvancesDTO.getEndDate() != null && !associateLoanAndAdvancesDTO.getEndDate().isEmpty()) {
			loanAndAdv.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), associateLoanAndAdvancesDTO.getEndDate()));
		}else {
			loanAndAdv.setEndDate(null);
		}
		
		loanAndAdv.setNoOfSchedules(Integer.valueOf(associateLoanAndAdvancesDTO.getNoOfSchedules()));
		loanAndAdv.setDeductAmount(Double.valueOf(associateLoanAndAdvancesDTO.getDeductAmount()));
		
		
		if(associateLoanAndAdvancesDTO.getLoanAndAdvStatus().equalsIgnoreCase("Outstanding") || associateLoanAndAdvancesDTO.getLoanAndAdvStatus().equalsIgnoreCase("Completed")) {
			if(associateLoanAndAdvancesDTO.getIssuedDate() != null && !associateLoanAndAdvancesDTO.getIssuedDate().isEmpty()) {
				loanAndAdv.setIssuedDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), associateLoanAndAdvancesDTO.getIssuedDate()));
			}
		}else {
			loanAndAdv.setIssuedDate(null);
		}
		
		if(associateLoanAndAdvancesDTO.getLoanAndAdvStatus().equalsIgnoreCase("Outstanding")) {
			if(associateLoanAndAdvancesDTO.getPaidAmount() != null && !associateLoanAndAdvancesDTO.getPaidAmount().isEmpty()) {
				loanAndAdv.setPaidAmount(Double.valueOf(associateLoanAndAdvancesDTO.getPaidAmount()));
			}	
		}else {
			loanAndAdv.setPaidAmount(null);
		}
		
		if(associateLoanAndAdvancesDTO.getLoanAndAdvStatus().equalsIgnoreCase("Outstanding")) {
			if(associateLoanAndAdvancesDTO.getPendingAmount() != null && !associateLoanAndAdvancesDTO.getPendingAmount().isEmpty()) {
				loanAndAdv.setPendingAmount(Double.valueOf(associateLoanAndAdvancesDTO.getPendingAmount()));
			}
		}else{
			loanAndAdv.setPendingAmount(null);
		}
		
		User approverUser = new User();
		approverUser.setId(Long.valueOf(associateLoanAndAdvancesDTO.getLoanAndAdvApprover()));
		loanAndAdv.setApproverUser(approverUser);
		loanAndAdv.setOrgLocationId(orgLocations);
		if(associateLoanAndAdvancesDTO.getDescription() != null && !associateLoanAndAdvancesDTO.getDescription().isEmpty()) {
			loanAndAdv.setDescription(associateLoanAndAdvancesDTO.getDescription());
		}else {
			loanAndAdv.setDescription(null);
		}
		
		
		loanAndAdv.setStatus(Constants.RECORD_ACTIVE);
		saveAssociateLoanAndAdvances(loanAndAdv);
		
		return loanAndAdv;
	}

	@Override
	public AssociateLoanAndAdvances getAssociateLoanAndAdvancesDetailsByCandidateId(Long candidateId,
			Long orgLocationId) throws Exception {
		return associateLoanAndAdvancesDao.getAssociateLoanAndAdvancesDetailsByCandidateId(candidateId, orgLocationId);
	}

	@Override
	public List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByCandidateId(Long candidateId,
			Long orgLocationId) throws Exception {
		return associateLoanAndAdvancesDao.getAssociateLoanAndAdvancesListByCandidateId(candidateId, orgLocationId);
	}

	@Override
	public AssociateLoanAndAdvancesDTO getAssociateLoanAndAssociateDetails(Long candidateId, OrgLocations orgLocations)
			throws Exception {
		
		AssociateLoanAndAdvancesDTO loanAndAdvDTO = new AssociateLoanAndAdvancesDTO();
		AssociateLoanAndAdvances loanAndAdv = getAssociateLoanAndAdvancesDetailsByCandidateId(candidateId, orgLocations.getId());
		if(loanAndAdv != null) {
			loanAndAdvDTO = getAssociateLoanAndAdvancesDTODetails(loanAndAdv, orgLocations);
		}
		return loanAndAdvDTO;
	}

	@Override
	public AssociateLoanAndAdvancesDTO getAssociateLoanAndAdvancesDetailsById(Long id,OrgLocations orgLocations) throws Exception {
		AssociateLoanAndAdvances loanAndAdv = getAssociateLoanAndAdvancesById(id);
		AssociateLoanAndAdvancesDTO loanAndAdvDTO = new AssociateLoanAndAdvancesDTO();
		if(loanAndAdv != null) {
			loanAndAdvDTO = getAssociateLoanAndAdvancesDTODetails(loanAndAdv, orgLocations);
		}
		return loanAndAdvDTO;
	}
	
	
	public AssociateLoanAndAdvancesDTO getAssociateLoanAndAdvancesDTODetails(AssociateLoanAndAdvances loanAndAdv,OrgLocations orgLocations) {
		AssociateLoanAndAdvancesDTO loanAndAdvDTO = new AssociateLoanAndAdvancesDTO();
		SimpleDateFormat dateFormat = null;
		if(StringUtils.isNotBlank(orgLocations.getDateFormat())) {
			dateFormat = new SimpleDateFormat(orgLocations.getDateFormat());
		}else {
			dateFormat = (SimpleDateFormat) Constants.DF_YMD;
		}
		
		loanAndAdvDTO.setLoanAndAdvType(loanAndAdv.getLoanAdvanceType());
		loanAndAdvDTO.setLoanAndAdvName(String.valueOf(loanAndAdv.getPayrollLoanAndAdvanceId().getId()));
		loanAndAdvDTO.setLoanAndAdvStatus(loanAndAdv.getLoanAndAdvanceStatus());
		loanAndAdvDTO.setLoanAmount(String.valueOf(loanAndAdv.getAmount()));
		loanAndAdvDTO.setScheduler(loanAndAdv.getScheduler());
		loanAndAdvDTO.setId(loanAndAdv.getId());
		if(loanAndAdv.getEffectiveDate() != null) {
			loanAndAdvDTO.setEffectiveDate(dateFormat.format(loanAndAdv.getEffectiveDate()));
		}
		
		if(loanAndAdv.getIssuedDate() != null) {
			loanAndAdvDTO.setIssuedDate(dateFormat.format(loanAndAdv.getIssuedDate()));
		}
		
		if(loanAndAdv.getEndDate() != null) {
			loanAndAdvDTO.setEndDate(dateFormat.format(loanAndAdv.getEndDate()));
		}
		
		loanAndAdvDTO.setNoOfSchedules(String.valueOf(loanAndAdv.getNoOfSchedules()));
		loanAndAdvDTO.setDeductAmount(String.valueOf(loanAndAdv.getDeductAmount()));
		
		if(loanAndAdv.getPaidAmount() != null) {
			loanAndAdvDTO.setPaidAmount(String.valueOf(loanAndAdv.getPaidAmount()));
		}
		
		if(loanAndAdv.getPendingAmount() != null) {
			loanAndAdvDTO.setPendingAmount(String.valueOf(loanAndAdv.getPendingAmount()));
		}
		
		loanAndAdvDTO.setLoanAndAdvApprover(String.valueOf(loanAndAdv.getApproverUser().getId()));
		
		if(loanAndAdv.getDescription() != null) {
			loanAndAdvDTO.setDescription(loanAndAdv.getDescription());
		}
		return loanAndAdvDTO;
	}

}
