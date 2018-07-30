package com.bis.sourcelead.payroll.service.impl;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.USPaysheetDao;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.USPayPeriod;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.payroll.service.USPayPeriodService;
import com.bis.sourcelead.payroll.service.USPaysheetService;

@Service
public class PaysheetFrequencyServiceImpl implements PaysheetFrequencyService{

	private static final Logger logger = LoggerFactory.getLogger(PaysheetFrequencyServiceImpl.class);
	
	@Autowired
	USPaysheetService uSPaysheetService;
	
	@Autowired
	USPaysheetDao uSPaysheetDao;
	
	@Autowired
	USPayPeriodService uSPayPeriodService;
	
	@Override
	public Response<PayrollApproverDTO> sendPayrollForApprover(PayrollApproverDTO payrollApproverDTO, OrgLocations orgLocation)
	{
		Response<PayrollApproverDTO> res = new Response<>();
		try {
			PaysheetFrequency pf = uSPaysheetService.getPayFrequencyByUUID(payrollApproverDTO.getPayrollFrequencyUUID());
			pf.setApproverStatus(PayrollConstant.SENT);
		    USPayPeriod period = uSPayPeriodService.getPayPeriodNumber(pf.getId());
		    int payPeriodNum = 1;
		    if(period != null) {
		    	payPeriodNum = payPeriodNum + period.getPayPeriodNum();
		    }
		    
		    USPayPeriod uSPayPeriod = saevPayPeriodNumber(pf,payPeriodNum,orgLocation);
		    
			Set<EmployeePayroll> payrollList = pf.getEmployeepayroll();
			for (EmployeePayroll payroll : payrollList) {
				for (USPayrollDTO payrollDTO : payrollApproverDTO.getUspayroll()) {
					if (payrollDTO.getIsExcept() == null && payrollDTO.getEmpPayrollId().longValue() == payroll.getId().longValue()) {
						payroll.setSubmitForAproverStatus(PayrollConstant.SENT);
						payroll.setPayPeriodNum(String.valueOf(uSPayPeriod.getPayPeriodNum()));
					}
				}
				
			}
			pf.setEmployeepayroll(payrollList);
			PaysheetFrequency savedPF = uSPaysheetDao.addPaysheetFrequency(pf);
			if (savedPF != null) {
				res.setResponse(Result.SUCCESS.name());
				res.setResponseMessage(Result.SUCCESS.getValue());
				res.setObject(payrollApproverDTO);
				
			}
		}catch (Exception e) {
			res.setResponse(Result.AWKWARD.name());
			res.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return res;
	}

	private USPayPeriod saevPayPeriodNumber(PaysheetFrequency pf, int payPeriodNum, OrgLocations orgLocation)
	{
		USPayPeriod  uSPayPeriod = new USPayPeriod();
		uSPayPeriod.setPaysheetFrequency(pf);
		uSPayPeriod.setPayPeriodNum(payPeriodNum);
		uSPayPeriod.setStatus(1);
		uSPayPeriod.setOrgLocation(orgLocation);
		uSPayPeriod.setCreatedDate(new Date());
		uSPayPeriod.setUpdatedDate(new Date());
		uSPayPeriod.setApproverStatus(PayrollConstant.SENT);
		return uSPayPeriodService.savePayPeriodNumber(uSPayPeriod);
	}
}
