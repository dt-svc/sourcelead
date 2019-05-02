package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateLoanAndAdvancesDao;
import com.bis.sourcelead.dao.AssociateLoanAndAdvancesHistoryDao;
import com.bis.sourcelead.dao.AssociateSalaryDao;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.rest.USPayrollRestController;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesHistoryService;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AssociateLoanAndAdvancesServiceImpl implements AssociateLoanAndAdvancesService{
	
	private static final Logger logger = LoggerFactory.getLogger(USPayrollRestController.class);
	
	@Autowired
	AssociateLoanAndAdvancesDao associateLoanAndAdvancesDao;
	
	@Autowired
	AssociateLoanAndAdvancesHistoryService associateLoanAndAdvancesHistoryService;
	
	@Autowired
	CommonUtil commonUtil;

	@Autowired
	CandidateDao candidateDao;
	
	@Autowired
	AssociateLoanAndAdvancesHistoryDao associateLoanAndAdvancesHistoryDao;
	
	@Autowired
	AssociateSalaryDao associateSalaryDao;
	
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
		try {
			if(associateLoanAndAdvancesDTO.getLoanAndAdvId() != null && !associateLoanAndAdvancesDTO.getLoanAndAdvId().isEmpty()) {
				loanAndAdv = getAssociateLoanAndAdvancesById(Long.valueOf(associateLoanAndAdvancesDTO.getLoanAndAdvId()));
			}else {
				loanAndAdv = new AssociateLoanAndAdvances();
				loanAndAdv.setCreatedDate(new Date());
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
			loanAndAdv.setOutstandingAmount(associateLoanAndAdvancesDTO.getOutstandingAmount());
			loanAndAdv.setRateOfInterest(associateLoanAndAdvancesDTO.getRateOfInterest());
			
			loanAndAdv.setStatus(Constants.RECORD_ACTIVE);
			loanAndAdv.setUpdatedDate(new Date());
			saveAssociateLoanAndAdvances(loanAndAdv);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		
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
		return associateLoanAndAdvancesDao.getAssociateLoanAndAdvancesListByCandidateId(candidateId, orgLocationId,"");
	}

	@Override
	public AssociateLoanAndAdvancesDTO getAssociateLoanAndAssociateDetails(Long candidateId, OrgLocations orgLocations)
			throws Exception {
		
		  AssociateLoanAndAdvancesDTO loanAndAdvDTO = new AssociateLoanAndAdvancesDTO();
		try {
			AssociateLoanAndAdvances loanAndAdv = getAssociateLoanAndAdvancesDetailsByCandidateId(candidateId, orgLocations.getId());
			if(loanAndAdv != null) {
				loanAndAdvDTO = getAssociateLoanAndAdvancesDTODetails(loanAndAdv, orgLocations);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return loanAndAdvDTO;
	}

	@Override
	public AssociateLoanAndAdvancesDTO getAssociateLoanAndAdvancesDetailsById(Long id,OrgLocations orgLocations) throws Exception {
		AssociateLoanAndAdvancesDTO loanAndAdvDTO = new AssociateLoanAndAdvancesDTO();
		try {
			AssociateLoanAndAdvances loanAndAdv = getAssociateLoanAndAdvancesById(id);
			if(loanAndAdv != null) {
				loanAndAdvDTO = getAssociateLoanAndAdvancesDTODetails(loanAndAdv, orgLocations);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return loanAndAdvDTO;
	}
	
	
	public AssociateLoanAndAdvancesDTO getAssociateLoanAndAdvancesDTODetails(AssociateLoanAndAdvances loanAndAdv,OrgLocations orgLocations) {
		AssociateLoanAndAdvancesDTO loanAndAdvDTO = new AssociateLoanAndAdvancesDTO();
		try {
			SimpleDateFormat dateFormat = null;
			if(StringUtils.isNotBlank(orgLocations.getDateFormat())) {
				dateFormat = new SimpleDateFormat(orgLocations.getDateFormat());
			}else {
				dateFormat = (SimpleDateFormat) Constants.DF_YMD;
			}
			loanAndAdvDTO.setName(loanAndAdv.getPayrollLoanAndAdvanceId().getName());
			loanAndAdvDTO.setLoanAndAdvType(loanAndAdv.getLoanAdvanceType());
			loanAndAdvDTO.setLoanAndAdvName(String.valueOf(loanAndAdv.getPayrollLoanAndAdvanceId().getId()));
			loanAndAdvDTO.setLoanAndAdvStatus(loanAndAdv.getLoanAndAdvanceStatus());
			loanAndAdvDTO.setLoanAmount(String.valueOf(loanAndAdv.getAmount()));
			loanAndAdvDTO.setScheduler(loanAndAdv.getScheduler());
//			loanAndAdvDTO.setId(loanAndAdv.getId());
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
			loanAndAdvDTO.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), loanAndAdv.getCreatedDate()));
			
			StringBuilder sb = new StringBuilder();
			if(loanAndAdv.getApproverUser().getLastName() != null) {
				sb.append(loanAndAdv.getApproverUser().getFirstName()).append(" ").append(loanAndAdv.getApproverUser().getLastName());
				loanAndAdvDTO.setApproverUser(sb.toString());
			}else {
				loanAndAdvDTO.setApproverUser(loanAndAdv.getApproverUser().getFirstName());
			}
			loanAndAdvDTO.setStatus(loanAndAdv.getStatus());
			
			if(loanAndAdv.getDescription() != null) {
				loanAndAdvDTO.setDescription(loanAndAdv.getDescription());
			}
			
			List<AssociateLoanAndAdvanceHistory> assLoanAndAdvanceHis = associateLoanAndAdvancesHistoryDao.getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceLoanId(loanAndAdv.getId());
			Double deductedAmount = assLoanAndAdvanceHis.stream().mapToDouble(amt -> amt.getDeductAmount()).sum();
			
			if(assLoanAndAdvanceHis.size() > 0) {
				AssociateLoanAndAdvanceHistory lastContact = Collections.max(assLoanAndAdvanceHis, Comparator.comparing(c -> c.getCreatedDate()));
				loanAndAdvDTO.setLastPaidOnMaxDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),lastContact.getCreatedDate()));
			}
			
			
			Double totalAmount = loanAndAdv.getAmount() - deductedAmount;
			loanAndAdvDTO.setDeductAmount(String.valueOf(totalAmount));
			
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return loanAndAdvDTO;
	}
	

	@Override
	public List<AssociateLoanAndAdvancesDTO> getAssociateLoanAndAdvancesListByCandidateIdByOrgLocationId(
			Long candidateId, OrgLocations orgLocation,String type) throws Exception {
		
		List<AssociateLoanAndAdvancesDTO> assLoansAndAdvancesList = new ArrayList<>();
		try {
			List<AssociateLoanAndAdvances> assLoansAndAdvances1 = associateLoanAndAdvancesDao.getAssociateLoanAndAdvancesListByCandidateId(candidateId, orgLocation.getId(),type);
			AssociateSalary associatePayFrequency = associateSalaryDao.getAssociateSalaryByCandidateIdAndOrgLocationId(candidateId, orgLocation.getId());
			for (AssociateLoanAndAdvances associateLoanAndAdvances : assLoansAndAdvances1) {
				AssociateLoanAndAdvancesDTO dto =  getAssociateLoanAndAdvancesDTODetails(associateLoanAndAdvances,orgLocation);
				Map<String,String> dueAmoutAndDate = calculateNextDueDate(orgLocation, associateLoanAndAdvances, associatePayFrequency);
				dto.setDueAmount(dueAmoutAndDate.get("dueAmout"));
				dto.setNextDueDate(dueAmoutAndDate.get("nextDueDate"));
				assLoansAndAdvancesList.add(dto);
			}
		} catch (Exception e) {
 			logger.error(ExceptionUtils.getFullStackTrace(e));
 			e.printStackTrace();
		}
		return assLoansAndAdvancesList;
	}

	@Override
	public Map<String, Double> getEmpLoansAndAdvancesCountListByOrgLocationId(Long candidateId) {
		return associateLoanAndAdvancesDao.getEmpLoansAndAdvancesCountListByOrgLocationId(candidateId);
	}

	@Override
	public List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByUserIdAndLocationId(User user,
			OrgLocations orgLocation, String loanStatus) throws Exception {
			return associateLoanAndAdvancesDao.getAssociateLoanAndAdvancesListByUserIdAndLocationId(user, orgLocation,loanStatus);
	}

	@Override
	public List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByCandidateIdOrgLocationIdAndLoanStatus(Long candidateId,
			Long orgLocationId, List<String> loanStatus) throws Exception {
		return associateLoanAndAdvancesDao.getAssociateLoanAndAdvancesListByCandidateIdOrgLocationIdAndLoanStatus(candidateId, orgLocationId, loanStatus);
	}
	
	private Map<String,String> calculateNextDueDate(OrgLocations orgLocation, AssociateLoanAndAdvances associateLoanAndAdvances, AssociateSalary associatePayFrequency) {
		Map<String,String> map=new HashMap<String,String>();
		Calendar dueDate = Calendar.getInstance();
		dueDate.setTime(associateLoanAndAdvances.getEffectiveDate());
		
		AssociateLoanAndAdvanceHistory assLoanAndAdvHis = associateLoanAndAdvancesHistoryService.getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceId(associateLoanAndAdvances.getId());
		if(assLoanAndAdvHis != null) {
			if(assLoanAndAdvHis.getSchedule()>0 && assLoanAndAdvHis.getSchedule() != null) {
				Double dueAmout = associateLoanAndAdvances.getPendingAmount() - assLoanAndAdvHis.getSchedule() * associateLoanAndAdvances.getDeductAmount();
				map.put("dueAmout", ""+dueAmout);
				
				if("Every Pay".equalsIgnoreCase(associateLoanAndAdvances.getScheduler())) {
					if("Bi-Weekly".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
						dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DAY_OF_MONTH) + assLoanAndAdvHis.getSchedule() * 14);
					if("Weekly".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
						dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DAY_OF_MONTH) + assLoanAndAdvHis.getSchedule() * 7);						
					if("Monthly".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
						dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH) + assLoanAndAdvHis.getSchedule() * 1, dueDate.get(Calendar.DAY_OF_MONTH));
					if("Annually".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
						dueDate.set(dueDate.get(Calendar.YEAR) + assLoanAndAdvHis.getSchedule() * 1, dueDate.get(Calendar.MONTH) + assLoanAndAdvHis.getSchedule(), dueDate.get(Calendar.DAY_OF_MONTH));
				}
				if("Every Quarter".equalsIgnoreCase(associateLoanAndAdvances.getScheduler()))
					dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH) + assLoanAndAdvHis.getSchedule() * 3, dueDate.get(Calendar.DAY_OF_MONTH));
				if("Half Yearly".equalsIgnoreCase(associateLoanAndAdvances.getScheduler()))
					dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH) + assLoanAndAdvHis.getSchedule() * 6, dueDate.get(Calendar.DAY_OF_MONTH));
				if("Annually".equalsIgnoreCase(associateLoanAndAdvances.getScheduler()))
					dueDate.set(dueDate.get(Calendar.YEAR) + assLoanAndAdvHis.getSchedule() * 1, dueDate.get(Calendar.MONTH) + assLoanAndAdvHis.getSchedule(), dueDate.get(Calendar.DAY_OF_MONTH));
			}
			else {
				if("Every Pay".equalsIgnoreCase(associateLoanAndAdvances.getScheduler())) {
					if("Bi-Weekly".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
						dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DAY_OF_MONTH) + assLoanAndAdvHis.getSchedule() * 14);
					if("Weekly".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
						dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DAY_OF_MONTH) + assLoanAndAdvHis.getSchedule() * 7);						
					if("Monthly".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
						dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH) + assLoanAndAdvHis.getSchedule() * 1, dueDate.get(Calendar.DAY_OF_MONTH));
					if("Annually".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
						dueDate.set(dueDate.get(Calendar.YEAR) + assLoanAndAdvHis.getSchedule() * 1, dueDate.get(Calendar.MONTH) + assLoanAndAdvHis.getSchedule(), dueDate.get(Calendar.DAY_OF_MONTH));
				}
				if("Every Quarter".equalsIgnoreCase(associateLoanAndAdvances.getScheduler()))
					dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH) + 3, dueDate.get(Calendar.DAY_OF_MONTH));
				if("Half Yearly".equalsIgnoreCase(associateLoanAndAdvances.getScheduler()))
					dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH) + 6, dueDate.get(Calendar.DAY_OF_MONTH));
				if("Annually".equalsIgnoreCase(associateLoanAndAdvances.getScheduler()))
					dueDate.set(dueDate.get(Calendar.YEAR) + 1, dueDate.get(Calendar.MONTH) + assLoanAndAdvHis.getSchedule(), dueDate.get(Calendar.DAY_OF_MONTH));
			}
		}
		else {
			if("Every Pay".equalsIgnoreCase(associateLoanAndAdvances.getScheduler())) {
				if("Bi-Weekly".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
					dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DAY_OF_MONTH) + 14);
				if("Weekly".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
					dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DAY_OF_MONTH) + 7);						
				if("Monthly".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
					dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH) + 1, dueDate.get(Calendar.DAY_OF_MONTH));
				if("Annually".equalsIgnoreCase(associatePayFrequency.getPayFrequency()))
					dueDate.set(dueDate.get(Calendar.YEAR) + 1, dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DAY_OF_MONTH));
			}
			if("Every Quarter".equalsIgnoreCase(associateLoanAndAdvances.getScheduler()))
				dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH) + 3, dueDate.get(Calendar.DAY_OF_MONTH));
			if("Half Yearly".equalsIgnoreCase(associateLoanAndAdvances.getScheduler()))
				dueDate.set(dueDate.get(Calendar.YEAR), dueDate.get(Calendar.MONTH) + 6, dueDate.get(Calendar.DAY_OF_MONTH));
			if("Annually".equalsIgnoreCase(associateLoanAndAdvances.getScheduler()))
				dueDate.set(dueDate.get(Calendar.YEAR) + 1, dueDate.get(Calendar.MONTH), dueDate.get(Calendar.DAY_OF_MONTH));
		}
		Date nextDate = dueDate.getTime();
		map.put("nextDueDate", commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), nextDate));
		return map;
	}
}
