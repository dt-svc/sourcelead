package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.GenerateINDPayrollDao;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.service.GenerateINDPayrollService;

@Service
public class GenerateINDPayrollServiceImpl implements GenerateINDPayrollService{
	
	@Autowired
	GenerateINDPayrollDao generateINDPayrollDao;

	private static final Logger logger = LoggerFactory.getLogger(GenerateINDPayrollServiceImpl.class);

	@Override
	@Transactional
	public IndPaysheetFrequencyDTO getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO, OrgLocations orgLocation,User loginUser) {
			
		IndPaysheetFrequency paysheetFrequency = generateINDPayrollDao.getPayrollData(generateINDPayrollDTO, orgLocation, loginUser);
		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = null;
		List<USPayrollDTO> list = null;
		/*try {
			if (paysheetFrequency != null && paysheetFrequency.getEmployeepayroll() != null) {
				paysheetFrequencyDTO = new PaysheetFrequencyDTO();
				paysheetFrequencyDTO.setPayrollFrequencyUUID(paysheetFrequency.getPaySheetFrequencyUUID());
				paysheetFrequencyDTO.setPaySheetFrequencyId(paysheetFrequency.getId());
				list = new ArrayList<>();
				for(EmployeePayroll pay : paysheetFrequency.getEmployeepayroll()) {
					USPayrollDTO payrollDTO = new USPayrollDTO();
					payrollDTO.setEmpId(pay.getEmployee().getEmployeeId());
					StringBuilder sb = new StringBuilder();
					if (StringUtils.isNoneBlank(pay.getCandidate().getLastName())) {
						sb.append(pay.getCandidate().getFirstName()).append(" ").append(pay.getCandidate().getLastName());
					}
					else {
						sb.append(pay.getCandidate().getFirstName());
					}
					if(pay.getCandidate().getDesignationType() != null)
					payrollDTO.setEmployeeDesignationType(pay.getCandidate().getDesignationType());
					payrollDTO.setEmpName(sb.toString());
					payrollDTO.setEmpPayrollId(pay.getId());
					payrollDTO.setEmpPayrollUUID(pay.getUsEmpPayrollUUID());
					payrollDTO.setGrossPay(pay.getGrossPay());
					payrollDTO.setNetPay(pay.getNetPay());
					payrollDTO.setRegularHrsTotalAmt(pay.getRegularHoursAmount());
					payrollDTO.setPayRate(pay.getEmpPayRate());
					payrollDTO.setPostTaxDeduction(pay.getPostTaxDeduction());
					payrollDTO.setPreTaxDeduction(pay.getPreTaxDeduction());
					float regHrs = 0;
					if(StringUtils.isNotBlank(pay.getRegularHours())) {
						regHrs = Float.parseFloat(pay.getRegularHours());
					}
					payrollDTO.setRegularHours(regHrs);
					payrollDTO.setTaxes(pay.getTaxes());
					payrollDTO.setOtHoursTotalAmt(pay.getOverTimeAmount());
					payrollDTO.setCandidateId(pay.getCandidate().getId());
					payrollDTO.setPaySheetFrequencyId(pay.getPaysheetFrequency().getId());
					double overTimeHours = 0;
					if(StringUtils.isNoneBlank(pay.getOverTimeHours())) {
						overTimeHours = Double.parseDouble(pay.getOverTimeHours());
					}
					payrollDTO.setOvertimeHours(overTimeHours);
					if(pay.getPayType() != null) {
						payrollDTO.setPayType(pay.getPayType());
					}
					payrollDTO.setApproverStatus(pay.getSubmitForAproverStatus());
//					if(PayrollConstant.DRAFT.equalsIgnoreCase(pay.getSubmitForAproverStatus())) {
						list.add(payrollDTO);	
//					}
					
				}
				paysheetFrequencyDTO.setPayrollDTO(list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return indPaysheetFrequencyDTO;
	}
	
	
}