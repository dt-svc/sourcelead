package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PayrollReportDao;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.service.PayrollReportService;
import com.bis.sourcelead.payroll.util.PayrollUtil;

@Service
public class PayrollReportServiceImpl implements PayrollReportService{

	@Autowired
	PayrollReportDao payrollReportDao;
	
	@Autowired
	PayrollUtil payrollUtil;
	
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
	
}
