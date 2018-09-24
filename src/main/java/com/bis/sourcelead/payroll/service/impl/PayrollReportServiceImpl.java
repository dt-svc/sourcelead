package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PayrollReportDao;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.service.PayrollReportService;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmployeesService;

@Service
public class PayrollReportServiceImpl implements PayrollReportService{

	@Autowired
	PayrollReportDao payrollReportDao;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	DepartmentService departmentService;
	
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
					YTDPayrollDTO ytd = payrollUtil.getEmployeeYTDPayroll(payroll.getCandidate(), orgLocation, "APPROVED");
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
		    return res;
	}

	@Override
	public ResponseList<PayrollReportDTO> getDeductionAndContributionReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO)
	{
		ResponseList<DeductionHistory> payrollResponse = payrollReportDao.getDeductionAndContributionReports(orgLocation,payrollReportDTO);
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
					BigDecimal dedAmount = BigDecimal.ZERO;
					if (dedHis.getPreContributionAmount() != null) {
						if (dedHis.getPreContributionAmount().compareTo(BigDecimal.ZERO) !=  0) {
							dedAmount = dedHis.getPreContributionAmount();
						}
					}
					if (dedHis.getPostContributionAmount() != null) {
						if (dedHis.getPostContributionAmount().compareTo(BigDecimal.ZERO) !=  0 ) {
							dedAmount = dedHis.getPostContributionAmount();
						}
					}
					dto.setDedAmount(dedAmount);
					dto.setSchedule(dedHis.getContributionId().getContributionTypeId().getDeductionTypeId().getPayrollScheduled().toLowerCase());
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
	
}
