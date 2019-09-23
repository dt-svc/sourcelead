package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PayrollReportDao;
import com.bis.sourcelead.payroll.dto.AssociateWithHoldingDTO;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.CompareDataDTO;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.PayrollComparisonDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.PayrollReportService;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class PayrollReportServiceImpl implements PayrollReportService{

	@Autowired
	PayrollReportDao payrollReportDao;
	
	@Autowired
	PaysheetFrequencyService paysheetFrequencyService;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private CandidateService candidateService;
	
	@Override
	public ResponseList<PayrollReportDTO> getPayrollSummaryByPayPeriodAndPayCheckDate(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO)
	{
		ResponseList<PayrollReportDTO> res = new ResponseList<>();
		List<PayrollReportDTO> list = new ArrayList<>();
		try {
			ResponseList<EmployeePayroll> data = payrollReportDao.getPayrollSummaryByPayPeriodAndPayCheckDate(orgLocation, payrollReportDTO);
			if (data.getData() != null) {
				for (EmployeePayroll payroll : data.getData()) {
					PayrollReportDTO dto = new PayrollReportDTO();
					StringBuilder sb = new StringBuilder();
					if (StringUtils.isNotBlank(payroll.getCandidate().getLastName())) {
						sb.append(payroll.getCandidate().getFirstName()).append(" ").append(payroll.getCandidate().getLastName());
					}
					else {
						sb.append(payroll.getCandidate().getFirstName());
					}
					dto.setEmpName(sb.toString());
					BigDecimal payRate = BigDecimal.ZERO;
					if (payroll.getEmpPayRate() != null) {
						payRate = payroll.getEmpPayRate();
					}
					dto.setEmpPayRate(payRate);
					dto.setRegularHours(payroll.getRegularHours());
					String otHours = "0.0";
					if (StringUtils.isNotBlank(payroll.getOverTimeHours())) {
						otHours = payroll.getOverTimeHours();
					}
					dto.setPayrollUUID(payroll.getUsEmpPayrollUUID());
					dto.setOverTimeHours(otHours);
					dto.setPreTaxDeduction(payroll.getPreTaxDeduction());
					dto.setPostTaxDeduction(payroll.getPostTaxDeduction());
					dto.setTaxes(payroll.getTaxes());
					dto.setNetPay(payroll.getNetPay());
					dto.setGrossPay(payroll.getGrossPay());
					
					//passing frequency year to calculate ytd
					Date date = payroll.getPaysheetFrequency().getFrequencyStartDate();
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					int year = c.get(Calendar.YEAR);
					
					YTDPayrollDTO ytd = payrollUtil.getEmployeeYTDPayroll(payroll.getCandidate(), orgLocation, "APPROVED",year);
					dto.setYtdNet(ytd.getYtdNet());
					list.add(dto);
				}
				res.setData(list);
				res.setRecordsFiltered(data.getRecordsFiltered());
				res.setRecordsTotal(res.getRecordsTotal());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public ResponseList<CheckRegisterReportDTO> getCheckRegisterReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO)
	{
			ResponseList<CheckRegisterReportDTO> res = payrollReportDao.getCheckRegisterReports(orgLocation,payrollReportDTO);
			List<CheckRegisterReportDTO> list = new ArrayList<>();
			
			for (CheckRegisterReportDTO data : res.getData()) {
				CheckRegisterReportDTO resDTO = new CheckRegisterReportDTO();
				try {
//					Candidate candidate = candidateService.getCandidateByCandidateId(data.getCandidateId().longValue());
					Candidate candidate = new Candidate();
					candidate.setId(data.getCandidateId().longValue());
					
					//passing frequency year to calculate ytd
					Date date = data.getFreqStartDate();
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					int year = c.get(Calendar.YEAR);
					
					YTDPayrollDTO ytdDTO = payrollUtil.getEmployeeYTDPayroll(candidate, orgLocation, Constants.APPROVED,year);
					resDTO.setYtdNet(ytdDTO.getYtdNet());
					resDTO.setEmpId(data.getEmpId());
					
					if(StringUtils.isNoneBlank(data.getLastName())) {
						resDTO.setLastName(data.getLastName());
					}else {
						resDTO.setLastName(" ");
					}
					
					resDTO.setFirstName(data.getFirstName());
					resDTO.setDepartmentId(data.getDepartmentId());
					resDTO.setNetPay(data.getNetPay());
					resDTO.setTotalChecks(data.getTotalChecks());
					resDTO.setEmployeeId(data.getEmployeeId());
					list.add(resDTO);
					res.setData(list);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		    return res;
	}

	@Override
	public ResponseList<PayrollReportDTO> getContributionReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO)
	{
		ResponseList<DeductionHistory> payrollResponse = payrollReportDao.getContributionReports(orgLocation,payrollReportDTO);
		ResponseList<PayrollReportDTO>  res = new ResponseList<>();
		List<PayrollReportDTO> list = new ArrayList<>();
		try {
			if(payrollResponse.getData() != null) {
				for(DeductionHistory dedHis : payrollResponse.getData()) {
					PayrollReportDTO dto = new PayrollReportDTO();
					StringBuilder sb = new StringBuilder();
					if(StringUtils.isNotBlank(dedHis.getPayroll().getCandidate().getLastName())) {
						sb.append(dedHis.getPayroll().getCandidate().getFirstName()).append(" ").append(dedHis.getPayroll().getCandidate().getLastName());
					}else {
						sb.append(dedHis.getPayroll().getCandidate().getFirstName());
					}
					dto.setEmpName(sb.toString());
					if(dedHis.getPayroll().getEmployee() != null) {
						dto.setEmpId(dedHis.getPayroll().getEmployee().getEmployeeId());
					}
					if(StringUtils.isNotBlank(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getDepartmentId())) {
						Department dept = departmentService.getDepartmentById(Long.parseLong(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getDepartmentId()));
						dto.setDepartmentName(dept.getDepartmentName());
					}
					dto.setContributionType(dedHis.getContributionId().getContributionTypeId().getDeductionTypeId().getPayrollDeductionType());
					BigDecimal contributionAmount = BigDecimal.ZERO;
					if (dedHis.getPreContributionAmount() != null) {
						if (dedHis.getPreContributionAmount().compareTo(BigDecimal.ZERO) !=  0) {
							contributionAmount = dedHis.getPreContributionAmount();
						}
					}
					if (dedHis.getPostContributionAmount() != null) {
						if (dedHis.getPostContributionAmount().compareTo(BigDecimal.ZERO) !=  0 ) {
							contributionAmount = dedHis.getPostContributionAmount();
						}
					}
					dto.setContributionAmount(contributionAmount);
					dto.setSchedule(dedHis.getContributionId().getContributionTypeId().getDeductionTypeId().getPayrollScheduled().toLowerCase());
					YTDPayrollDTO ytd = payrollUtil.getContributionYTD(dedHis.getPayroll(), dedHis.getContributionId().getId(), orgLocation);
					dto.setContributionYTD(ytd.getContributionYTD());
					list.add(dto);
				}
			}
			res.setData(list);
			res.setRecordsFiltered(payrollResponse.getRecordsFiltered());
			res.setRecordsTotal(payrollResponse.getRecordsTotal());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public PayrollComparisonDTO payrollComparisonData(PayrollComparisonDTO payrollComparisonDTO,OrgLocations orgLocation) {
		
		PayrollComparisonDTO compare = new PayrollComparisonDTO();
		try {
			String [] compareDates = new String[2];
			compareDates[0] = payrollComparisonDTO.getPayPeriod();
			compareDates[1] = payrollComparisonDTO.getPayPeriod2();
			int i=0;
			for(String freqDates : compareDates) {
				CompareDataDTO compareData1 = new CompareDataDTO();
				PaysheetFrequency paySheet = paysheetFrequencyService.getPaySheetFrequencyByGroupIdAndFrequencyStartEnddate(freqDates,orgLocation,payrollComparisonDTO.getGroupId());
				List<EmployeePayroll> list1 = paySheet.getEmployeepayroll().stream().filter(emp ->emp.getSubmitForAproverStatus().equalsIgnoreCase(PayrollConstant.APPROVE)).collect(Collectors.toList());
				
				List<USPayrollDTO> employeePayList = new ArrayList<>();
				
				List<ContributionDTO> contributionHisList = new ArrayList<>();
				List<DeductionDTO> dedHisList = new ArrayList<>();
				List<AssociatePayItemDTO> payItemList = new ArrayList<>();
				
				Map<String, BigDecimal> taxMap = new HashMap<>();
				BigDecimal totalFederalWithholdAmt = BigDecimal.ZERO;
				BigDecimal totalFica = BigDecimal.ZERO;
				BigDecimal totalMedicare = BigDecimal.ZERO;
				BigDecimal totalEarnedIncCredit = BigDecimal.ZERO;
				BigDecimal totalStateWithloldAmt = BigDecimal.ZERO;
				BigDecimal totalLocalTax = BigDecimal.ZERO;
				BigDecimal totalSdi = BigDecimal.ZERO;
				BigDecimal totalSui = BigDecimal.ZERO;
				BigDecimal totalfli = BigDecimal.ZERO;
				BigDecimal totalhcsf = BigDecimal.ZERO;
				BigDecimal totalwc = BigDecimal.ZERO;
				
				for (EmployeePayroll payroll : list1) {
					USPayrollDTO usPayroll = new USPayrollDTO();
					usPayroll.setRegularHours(payroll.getRegularHours());
					if(payroll.getOverTimeHours() != null)
					usPayroll.setOvertimeHours(Double.parseDouble(payroll.getOverTimeHours()));
					usPayroll.setGrossPay(payroll.getGrossPay());
					usPayroll.setNetPay(payroll.getNetPay());
					
					
					List<ContributionDTO> clist = payrollUtil.getContributionData(payroll);
					contributionHisList.addAll(clist);	
					
					List<DeductionDTO> dlist = payrollUtil.getDeductionData(payroll);
					dedHisList.addAll(dlist);
					
					List<AssociatePayItemDTO> pList = payrollUtil.getPayItemData(payroll);
					payItemList.addAll(pList);
					
					
					for(EmployeePayrollTax tax : payroll.getUsPayrollTax()) {
						
						if(tax.getFederalWithholding() != null)
						totalFederalWithholdAmt = totalFederalWithholdAmt.add(tax.getFederalWithholding());
						if(tax.getFicaWithholding() != null)
						totalFica = totalFica.add(tax.getFicaWithholding());
						if(tax.getMedicareWithholding() != null)
						totalMedicare = totalMedicare.add(tax.getMedicareWithholding());
						if(tax.getEarnedIncCredit() != null)
						totalEarnedIncCredit = totalEarnedIncCredit.add(tax.getEarnedIncCredit());
						if(tax.getStateWithholding() != null)
						totalStateWithloldAmt = totalStateWithloldAmt.add(tax.getStateWithholding());
						if(tax.getLocalTax() != null)
						totalLocalTax = totalLocalTax.add(tax.getLocalTax());
						if(tax.getSDI() != null)
						totalSdi = totalSdi.add(tax.getSDI());
						if(tax.getSUI() != null)
						totalSui = totalSui.add(tax.getSUI());
						if(tax.getFLI() != null)
						totalfli = totalfli.add(tax.getFLI());
						if(tax.getHCSF() != null)
						totalhcsf = totalhcsf.add(tax.getHCSF());
						if(tax.getWC() != null)
						totalwc = totalwc.add(tax.getWC());
						
					}
					
					employeePayList.add(usPayroll);
				}
				
				taxMap.put("Federal Withholding", totalFederalWithholdAmt);
				taxMap.put("FICA",totalFica);
				taxMap.put("Medicare",totalMedicare);
				taxMap.put("Advanced Earned Income Credit",totalEarnedIncCredit);
				taxMap.put("State Withholding",totalStateWithloldAmt);
				taxMap.put("LocalTax",totalLocalTax);
				taxMap.put("SUI",totalSdi);
				taxMap.put("SUI", totalSui);
				taxMap.put("FLI", totalfli);
				taxMap.put("HCSF", totalhcsf);
				taxMap.put("WC", totalwc);
				
				//contributionData
				Map<String, Double> contribution = null;
				if(contributionHisList.size() >0) {
					 contribution = contributionHisList.stream().collect(Collectors.groupingBy(ContributionDTO::getContributionType,Collectors.summingDouble(ContributionDTO::getContributionAmt)));
				}
				
				//deductionData
				Map<String, Double> deductionMap = null;
				if(payItemList.size() > 0) {
					deductionMap = dedHisList.stream().collect(Collectors.groupingBy(DeductionDTO::getDeductionType,Collectors.summingDouble(DeductionDTO::getDedAmount)));
				}
				
				
				
				//payItemDta
				Map<String, Double> payItem = null;
				if(payItemList.size() > 0) {
					payItem = payItemList.stream().collect(Collectors.groupingBy(AssociatePayItemDTO::getPayItemName,Collectors.summingDouble(AssociatePayItemDTO::getPayItemAmt)));
				}
		
				
				double totalRegularHours = employeePayList.stream().mapToDouble(reg ->Double.parseDouble(reg.getRegularHours())).sum();
				double totalOvertimeHours = employeePayList.stream().mapToDouble(reg ->reg.getOvertimeHours()).sum();
				BigDecimal totalGrossPay = employeePayList.stream().map(USPayrollDTO::getGrossPay).reduce(BigDecimal.ZERO, BigDecimal::add);
				BigDecimal totalNetPay = employeePayList.stream().map(USPayrollDTO::getNetPay).reduce(BigDecimal.ZERO, BigDecimal::add);
				
				compareData1.setContriMap(contribution);
				compareData1.setPayItemMap(payItem);
				compareData1.setDedMap(deductionMap);
				compareData1.setTotalRegHours(totalRegularHours);
				compareData1.setTotalOTHours(totalOvertimeHours);
				compareData1.setTotalGross(totalGrossPay);
				compareData1.setNetPay(totalNetPay);
				compareData1.setTaxMap(taxMap);
				if(i == 0)
				compare.setCompareData1(compareData1);
				
				if(i == 1) {
				compare.setCompareData2(compareData1);
				}
				i++;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return compare;
		
	}

	@Override
	public ResponseList<PayrollReportDTO> getDeductionReports(OrgLocations orgLocation,PayrollReportDTO payrollReportDTO) {
		
		ResponseList<DeductionHistory> payrollResponse = payrollReportDao.getDeductionReports(orgLocation,payrollReportDTO);
		ResponseList<PayrollReportDTO>  res = new ResponseList<>();
		List<PayrollReportDTO> list = new ArrayList<>();
		try {
			if(payrollResponse.getData() != null) {
				for(DeductionHistory dedHis : payrollResponse.getData()) {
					PayrollReportDTO dto = new PayrollReportDTO();
					StringBuilder sb = new StringBuilder();
					if(StringUtils.isNotBlank(dedHis.getPayroll().getCandidate().getLastName())) {
						sb.append(dedHis.getPayroll().getCandidate().getFirstName()).append(" ").append(dedHis.getPayroll().getCandidate().getLastName());
					}else {
						sb.append(dedHis.getPayroll().getCandidate().getFirstName());
					}
					dto.setEmpName(sb.toString());
					if(dedHis.getPayroll().getEmployee() != null) {
						dto.setEmpId(dedHis.getPayroll().getEmployee().getEmployeeId());
					}
					if(StringUtils.isNotBlank(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getDepartmentId())) {
						Department dept = departmentService.getDepartmentById(Long.parseLong(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getDepartmentId()));
						dto.setDepartmentName(dept.getDepartmentName());
					}
					dto.setDeductionType(dedHis.getDeductionId().getDeductionTypeId().getPayrollDeductionType());
					BigDecimal dedAmount = BigDecimal.ZERO;
					if (dedHis.getPreDeductionAmount() != null) {
						if (dedHis.getPreDeductionAmount().compareTo(BigDecimal.ZERO) !=  0) {
							dedAmount = dedHis.getPreDeductionAmount();
						}
					}
					if (dedHis.getPostDeductionAmount() != null) {
						if (dedHis.getPostDeductionAmount().compareTo(BigDecimal.ZERO) !=  0 ) {
							dedAmount = dedHis.getPostDeductionAmount();
						}
					}
					dto.setDedAmount(dedAmount);
					dto.setSchedule(dedHis.getDeductionId().getDeductionTypeId().getPayrollScheduled().toLowerCase());
					YTDPayrollDTO deductionYTD = payrollUtil.getDeductionYTD(dedHis.getPayroll(), dedHis.getDeductionId().getId(), orgLocation);
					dto.setDeductionYTD(deductionYTD.getDeductionYTD());
					list.add(dto);
				}
			}
			res.setData(list);
			res.setRecordsFiltered(payrollResponse.getRecordsFiltered());
			res.setRecordsTotal(payrollResponse.getRecordsTotal());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<CheckRegisterReportDTO> getexportToPdfCheckRegisterReport(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO)
	{
		List<CheckRegisterReportDTO>  checkRegisterList = payrollReportDao.getexportToPdfCheckRegisterReport(orgLocation,payrollReportDTO);
		return checkRegisterList;
	}

	@Override
	public List<PayrollReportDTO>  exportToPdfContributionReport(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO)
	{
		List<DeductionHistory> dedHisList =  payrollReportDao.exportToPdfContributionReport(orgLocation,payrollReportDTO);
		
		List<PayrollReportDTO> list = new ArrayList<>();
		try {
			if(dedHisList != null) {
				for(DeductionHistory dedHis : dedHisList) {
					PayrollReportDTO dto = new PayrollReportDTO();
					StringBuilder sb = new StringBuilder();
					if(StringUtils.isNotBlank(dedHis.getPayroll().getCandidate().getLastName())) {
						sb.append(dedHis.getPayroll().getCandidate().getFirstName()).append(" ").append(dedHis.getPayroll().getCandidate().getLastName());
					}else {
						sb.append(dedHis.getPayroll().getCandidate().getFirstName());
					}
					dto.setEmpName(sb.toString());
					if(StringUtils.isNotBlank(dedHis.getPayroll().getPayPeriodNum()))
						dto.setRunNo(dedHis.getPayroll().getPayPeriodNum());
					else
						dto.setRunNo("0");
					if(dedHis.getPayroll().getEmployee() != null) {
						dto.setEmpId(dedHis.getPayroll().getEmployee().getEmployeeId());
					}
					if(StringUtils.isNotBlank(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getDepartmentId())) {
						Department dept = departmentService.getDepartmentById(Long.parseLong(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getDepartmentId()));
						dto.setDepartmentName(dept.getDepartmentName());
					}
					dto.setContributionType(dedHis.getContributionId().getContributionTypeId().getDeductionTypeId().getPayrollDeductionType());
					BigDecimal contributionAmount = BigDecimal.ZERO;
					if (dedHis.getPreContributionAmount() != null) {
						if (dedHis.getPreContributionAmount().compareTo(BigDecimal.ZERO) !=  0) {
							contributionAmount = dedHis.getPreContributionAmount();
						}
					}
					if (dedHis.getPostContributionAmount() != null) {
						if (dedHis.getPostContributionAmount().compareTo(BigDecimal.ZERO) !=  0 ) {
							contributionAmount = dedHis.getPostContributionAmount();
						}
					}
					dto.setContributionAmount(contributionAmount);
					dto.setFreqStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), dedHis.getPayroll().getPaysheetFrequency().getFrequencyStartDate()));
					dto.setFreqEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), dedHis.getPayroll().getPaysheetFrequency().getFrequencyEndDate()));
					dto.setSchedule(dedHis.getContributionId().getContributionTypeId().getDeductionTypeId().getPayrollScheduled().toLowerCase());
					YTDPayrollDTO ytd = payrollUtil.getContributionYTD(dedHis.getPayroll(), dedHis.getContributionId().getId(), orgLocation);
					dto.setCheckDate(dedHis.getPayroll().getPaysheetFrequency().getPayCheckDate());
					dto.setGroupName(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getGroupName());
					dto.setGroupFrequency(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getPayFrequency());
					dto.setType(dedHis.getPayroll().getType());
					dto.setContributionYTD(ytd.getContributionYTD());
					dto.setContributionMTD(ytd.getContributionMTD());
					dto.setContributionQTD(ytd.getContributionQTD());
					dto.setRunDate(dedHis.getPayroll().getCreatedDate());
					list.add(dto);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<PayrollReportDTO> exportToPdfDeductionReport(OrgLocations orgLocation,
			PayrollReportDTO payrollReportDTO) {
		List<DeductionHistory> dedHisList =  payrollReportDao.exportToPdfDeductionReport(orgLocation,payrollReportDTO);
		
		List<PayrollReportDTO> list = new ArrayList<>();
		try {
			if(dedHisList != null) {
				for(DeductionHistory dedHis : dedHisList) {
					PayrollReportDTO dto = new PayrollReportDTO();
					StringBuilder sb = new StringBuilder();
					if(StringUtils.isNotBlank(dedHis.getPayroll().getCandidate().getLastName())) {
						sb.append(dedHis.getPayroll().getCandidate().getFirstName()).append(" ").append(dedHis.getPayroll().getCandidate().getLastName());
					}else {
						sb.append(dedHis.getPayroll().getCandidate().getFirstName());
					}
					dto.setEmpName(sb.toString());
					if(StringUtils.isNotBlank(dedHis.getPayroll().getPayPeriodNum()))
						dto.setRunNo(dedHis.getPayroll().getPayPeriodNum());
					else
						dto.setRunNo("0");
					if(dedHis.getPayroll().getEmployee() != null) {
						dto.setEmpId(dedHis.getPayroll().getEmployee().getEmployeeId());
					}
					if(StringUtils.isNotBlank(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getDepartmentId())) {
						Department dept = departmentService.getDepartmentById(Long.parseLong(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getDepartmentId()));
						dto.setDepartmentName(dept.getDepartmentName());
					}
					dto.setDeductionType(dedHis.getDeductionId().getDeductionTypeId().getPayrollDeductionType());
					BigDecimal deductionAmount = BigDecimal.ZERO;
					if (dedHis.getPreDeductionAmount() != null) {
						if (dedHis.getPreDeductionAmount().compareTo(BigDecimal.ZERO) !=  0) {
							deductionAmount = dedHis.getPreDeductionAmount();
						}
					}
					if (dedHis.getPostDeductionAmount() != null) {
						if (dedHis.getPostDeductionAmount().compareTo(BigDecimal.ZERO) !=  0 ) {
							deductionAmount = dedHis.getPostDeductionAmount();
						}
					}
					dto.setDedAmount(deductionAmount);
					dto.setFreqStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), dedHis.getPayroll().getPaysheetFrequency().getFrequencyStartDate()));
					dto.setFreqEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), dedHis.getPayroll().getPaysheetFrequency().getFrequencyEndDate()));
					dto.setSchedule(dedHis.getDeductionId().getDeductionTypeId().getPayrollScheduled().toLowerCase());
					YTDPayrollDTO deductionYTD = payrollUtil.getDeductionYTD(dedHis.getPayroll(), dedHis.getDeductionId().getId(), orgLocation);
					dto.setDeductionYTD(deductionYTD.getDeductionYTD());
					dto.setDeductionMTD(deductionYTD.getDeductionMTD());
					dto.setDeductionQTD(deductionYTD.getDeductionQTD());
					
					dto.setCheckDate(dedHis.getPayroll().getPaysheetFrequency().getPayCheckDate());
					dto.setGroupName(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getGroupName());
					dto.setGroupFrequency(dedHis.getPayroll().getPaysheetFrequency().getUserGroup().getPayFrequency());
					dto.setType(dedHis.getPayroll().getType());
					dto.setRunDate(dedHis.getPayroll().getCreatedDate());
					list.add(dto);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public ResponseList<AssociateWithHoldingDTO> getAssociateWithHoldingReports(OrgLocations orgLocation, AssociateWithHoldingDTO associateWithHoldingDTO)
	{
		ResponseList<AssociateWithHoldingDTO> response = payrollReportDao.getAssociateWithHoldingReport(orgLocation,associateWithHoldingDTO);
		return response;
	}


}