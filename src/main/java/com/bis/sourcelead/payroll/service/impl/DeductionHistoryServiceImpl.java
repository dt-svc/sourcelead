package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.DeductionHistoryDao;
import com.bis.sourcelead.payroll.dto.ContributionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionSummaryDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.DeductionHistoryService;
import com.bis.sourcelead.payroll.util.PayrollUtil;

@Service
public class DeductionHistoryServiceImpl implements DeductionHistoryService{

	@Autowired
	DeductionHistoryDao deductionHistoryDao;
	
	@Autowired
	private PayrollUtil payrollUtil;
	
	@Override
	public List<DeductionHistoryDTO> getDeductionsByPayrollId(Long payrollId)
	{
		return deductionHistoryDao.getDeductionsByPayrollId(payrollId);
	}

	@Override
	public List<ContributionHistoryDTO> getContributionsByPayrollId(Long payrollId)
	{
		return deductionHistoryDao.getContributionsByPayrollId(payrollId);
	}

	@Override
	public ResponseList<DeductionSummaryDTO> getDedudctionDataBasedOnPayrollCheckDate(OrgLocations orgLocation,
			PayrollReportDTO payrollReportDTO) {
		List<DeductionHistory> list =  deductionHistoryDao.getDedudctionDataBasedOnPayrollCheckDate(orgLocation,payrollReportDTO);
	    
		for (DeductionHistory deductionHistory : list) {
			if(deductionHistory.getDeductionId() != null)
			deductionHistory.setDeductionType(deductionHistory.getDeductionId().getDeductionTypeId().getPayrollDeductionType());
		}
		
		
		Map<String, List<DeductionHistory>> uniqueDedType = list.stream().collect(Collectors.groupingBy(DeductionHistory::getDeductionType));
		
		List<DeductionSummaryDTO> listDto = new ArrayList<>();
		DeductionSummaryDTO dto = null;
		Map<Long, List<DeductionHistory>> uniqueCandidate = null;
		YTDPayrollDTO ytdqtd = null;
		
		ResponseList<DeductionSummaryDTO> resList = new ResponseList<>();
		
			for (Entry<String, List<DeductionHistory>> entry : uniqueDedType.entrySet()) {
				
				BigDecimal finalDeduction = BigDecimal.ZERO;
				BigDecimal finalYtd = BigDecimal.ZERO;
				BigDecimal finalQtd = BigDecimal.ZERO;
				
				uniqueCandidate = entry.getValue().stream().collect(Collectors.groupingBy(DeductionHistory::getCandidateId));

				for (Entry<Long, List<DeductionHistory>> iterator : uniqueCandidate.entrySet()) {
					
					BigDecimal preTaxDeduction = iterator.getValue().stream().map(DeductionHistory::getPreDeductionAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
					
					BigDecimal postTaxDeduction = iterator.getValue().stream().map(DeductionHistory::getPostDeductionAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
					
					BigDecimal totalDeduction = preTaxDeduction.add(postTaxDeduction);
					finalDeduction = finalDeduction.add(totalDeduction);
					
					
					EmployeePayroll payroll = new EmployeePayroll();
					payroll.setPaysheetFrequency(iterator.getValue().get(0).getPayroll().getPaysheetFrequency());
					Candidate can = new Candidate();
					can.setId(iterator.getKey());
					payroll.setCandidate(can);
					ytdqtd = payrollUtil.getDeductionYTD(payroll, iterator.getValue().get(0).getDeductionId().getId(), orgLocation);
					finalYtd = finalYtd.add(ytdqtd.getDeductionYTD());
					finalQtd = finalQtd.add(ytdqtd.getDeductionQTD());
					
				}
				
				dto = new DeductionSummaryDTO();
				dto.setDeductionType(entry.getKey());
				dto.setCandidateCount(uniqueCandidate.keySet().size());
				dto.setCurrentAmt(finalDeduction);
				dto.setYtd(finalYtd);
				dto.setQtd(finalQtd);
				listDto.add(dto);
				
			}
			resList.setData(listDto);
			resList.setRecordsTotal(uniqueDedType.size());
			resList.setRecordsFiltered(listDto.size());
		
		return resList;
	}

	@Override
	public ResponseList<DeductionSummaryDTO> getContributionDataBasedOnPayrollCheckDate(OrgLocations orgLocation,
			PayrollReportDTO payrollReportDTO) {
		
		List<DeductionHistory> list =  deductionHistoryDao.getDedudctionDataBasedOnPayrollCheckDate(orgLocation,payrollReportDTO);
		for (DeductionHistory deductionHistory : list) {
			if(deductionHistory.getContributionId() != null)
			deductionHistory.setContributionType(deductionHistory.getContributionId().getContributionTypeId().getDeductionTypeId().getPayrollDeductionType());
		}
		
		
		Map<String, List<DeductionHistory>> uniqueConType = list.stream().collect(Collectors.groupingBy(DeductionHistory::getContributionType));
		
		List<DeductionSummaryDTO> listDto = new ArrayList<>();
		DeductionSummaryDTO dto = null;
		Map<Long, List<DeductionHistory>> uniqueCandidate = null;
		YTDPayrollDTO ytdqtd = null;
		
		ResponseList<DeductionSummaryDTO> resList = new ResponseList<>();
		
			for (Entry<String, List<DeductionHistory>> entry : uniqueConType.entrySet()) {
				
				BigDecimal finalContribution = BigDecimal.ZERO;
				BigDecimal finalYtd = BigDecimal.ZERO;
				BigDecimal finalQtd = BigDecimal.ZERO;
				
				uniqueCandidate = entry.getValue().stream().collect(Collectors.groupingBy(DeductionHistory::getCandidateId));

				for (Entry<Long, List<DeductionHistory>> iterator : uniqueCandidate.entrySet()) {
					
					BigDecimal preContAmount = iterator.getValue().stream().map(DeductionHistory::getPreContributionAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
					
					BigDecimal postContAmount = iterator.getValue().stream().map(DeductionHistory::getPostContributionAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
					
					BigDecimal totalDeduction = preContAmount.add(postContAmount);
					finalContribution = finalContribution.add(totalDeduction);
					
					
					EmployeePayroll payroll = new EmployeePayroll();
					payroll.setPaysheetFrequency(iterator.getValue().get(0).getPayroll().getPaysheetFrequency());
					Candidate can = new Candidate();
					can.setId(iterator.getKey());
					payroll.setCandidate(can);
					ytdqtd = payrollUtil.getContributionYTD(payroll, iterator.getValue().get(0).getContributionId().getId(), orgLocation);
					finalYtd = finalYtd.add(ytdqtd.getContributionYTD());
					finalQtd = finalQtd.add(ytdqtd.getContributionQTD());
					
				}
				
				dto = new DeductionSummaryDTO();
				dto.setDeductionType(entry.getKey());
				dto.setCandidateCount(uniqueCandidate.keySet().size());
				dto.setCurrentAmt(finalContribution);
				dto.setYtd(finalYtd);
				dto.setQtd(finalQtd);
				listDto.add(dto);
				
			}
			resList.setData(listDto);
			resList.setRecordsTotal(listDto.size());
			resList.setRecordsFiltered(listDto.size());
		
		return resList;
	}
}
