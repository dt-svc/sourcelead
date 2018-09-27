package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dao.GenerateINDPayrollDao;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.dto.INDPayrollDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.GenerateINDPayrollService;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class GenerateINDPayrollServiceImpl implements GenerateINDPayrollService{
	
	@Autowired
	GenerateINDPayrollDao generateINDPayrollDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	AssociateSalaryService associateSalaryService;
	
	@Autowired
	UserGroupDao userGroupDao;

	private static final Logger logger = LoggerFactory.getLogger(GenerateINDPayrollServiceImpl.class);

	@Override
	public IndPaysheetFrequencyDTO getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO, OrgLocations orgLocation,User loginUser) {
			
		IndPaysheetFrequency paysheetFrequency = generateINDPayrollDao.getPayrollData(generateINDPayrollDTO, orgLocation, loginUser);
		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = null;
		List<INDPayrollDTO> list = null;
		try {
			if (paysheetFrequency != null && paysheetFrequency.getIndEmployeepayroll() != null) {
				indPaysheetFrequencyDTO = new IndPaysheetFrequencyDTO();
				indPaysheetFrequencyDTO.setPayrollFrequencyUUID(paysheetFrequency.getPaySheetFrequencyUUID());
				indPaysheetFrequencyDTO.setPaySheetFrequencyId(paysheetFrequency.getId());
				list = new ArrayList<>();
				for(IndEmployeePayroll pay : paysheetFrequency.getIndEmployeepayroll()) {
					INDPayrollDTO payrollDTO = new INDPayrollDTO();
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
					payrollDTO.setEmpPayrollUUID(pay.getIndEmpPayrollUUID());
					
					payrollDTO.setGrossPay(pay.getGrossPay());
					payrollDTO.setNetPay(pay.getNetPay());
					payrollDTO.setRegularHrsTotalAmt(pay.getRegularHoursAmount());
					payrollDTO.setPayRate(pay.getEmpPayRate());
					float regHrs = 0;
					if(StringUtils.isNotBlank(pay.getRegularHours())) {
						regHrs = Float.parseFloat(pay.getRegularHours());
					}
					payrollDTO.setRegularHours(regHrs);
					payrollDTO.setOtHoursTotalAmt(pay.getOverTimeAmount());
					payrollDTO.setCandidateId(pay.getCandidate().getId());
					payrollDTO.setPaySheetFrequencyId(pay.getIndPaysheetFrequency().getId());
					double overTimeHours = 0;
					if(StringUtils.isNoneBlank(pay.getOverTimeHours())) {
						overTimeHours = Double.parseDouble(pay.getOverTimeHours());
					}
					payrollDTO.setOvertimeHours(overTimeHours);
					payrollDTO.setApproverStatus(pay.getSubmitForAproverStatus());
					list.add(payrollDTO);	
					
				}
				indPaysheetFrequencyDTO.setIndPayrollDTO(list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indPaysheetFrequencyDTO;
	}

	@Override
	public IndPaysheetFrequencyDTO generateINDPayroll(IndPaysheetFrequencyDTO existedData, OrgLocations orgLocation,
			GenerateINDPayrollDTO generateINDPayrollDTO, User loginUser) throws Exception {

		String dates[] = generateINDPayrollDTO.getPayPeriod().split(",");
		Date freqStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date freqEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = null;
		
		
		String[] groupId = generateINDPayrollDTO.getGroupId().split(",");
		List<AssociateSalary> assSallist = associateSalaryService.getAssociateSalaryListByGroupId(orgLocation,Long.parseLong(groupId[1]),generateINDPayrollDTO.getPaytype());
		
		UserGroup userGroup = userGroupDao.findUserGroupById(groupId[0]);
		
		if (assSallist != null && assSallist.size() >0) {
			PaysheetFrequency paySheetFreq = null;
			if(existedData != null) {
				 paySheetFreq = new PaysheetFrequency();
				 paySheetFreq.setId(existedData.getPaySheetFrequencyId());
			}else {
				 paySheetFreq = new PaysheetFrequency();
			}
			
			paySheetFreq.setUserGroup(userGroup);
			paySheetFreq.setOrgLocation(orgLocation);
			String[] payPeriod = generateINDPayrollDTO.getPayPeriod().split(",");
			Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[0]);
			paySheetFreq.setFrequencyStartDate(startDate);
			Date endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[1]);
			paySheetFreq.setFrequencyEndDate(endDate);
			paySheetFreq.setPayCheckDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),generateINDPayrollDTO.getPayrollPayDate()));
			paySheetFreq.setCreatedDate(new Date());
			paySheetFreq.setUpdatedDate(new Date());
			paySheetFreq.setCreatedBy(loginUser.getId());
			paySheetFreq.setUpdatedBy(loginUser.getId());
			paySheetFreq.setStatus(1);
			paySheetFreq.setType(PayrollConstant.PAYROLL);
			paySheetFreq.setApproverStatus(PayrollConstant.DRAFT);
			paySheetFreq.setPaySheetFrequencyUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			/*Set<EmployeePayroll> payrollList = calculatePayroll(existedData,paySheetFreq, loginUser, orgLocation,assSallist,freqStartDate, freqEndDate,userGroup);
			if(payrollList !=null && payrollList.size() > 0) {
				paySheetFreq.setEmployeepayroll(payrollList);
				uSPaysheetService.addPaysheetFrequency(paySheetFreq);
			}*/
			indPaysheetFrequencyDTO = getPayrollData(generateINDPayrollDTO,orgLocation,loginUser);
				if(indPaysheetFrequencyDTO != null && indPaysheetFrequencyDTO.getIndPayrollDTO() != null) {
					List<INDPayrollDTO> draftPayroll = null;
					if(Constants.ALL_RECORDS.equalsIgnoreCase(generateINDPayrollDTO.getPaytype())) {
						draftPayroll = indPaysheetFrequencyDTO.getIndPayrollDTO()
								.stream()
								.filter(p->p.getEmployeeDesignationType() != null && PayrollConstant.DRAFT.equalsIgnoreCase(p.getApproverStatus()) && !PayrollConstant.C2C.equalsIgnoreCase(p.getEmployeeDesignationType()))
								.collect(Collectors.toList());
					}else {
						draftPayroll = indPaysheetFrequencyDTO.getIndPayrollDTO()
								.stream()
								.filter(p->p.getEmployeeDesignationType() != null && PayrollConstant.DRAFT.equalsIgnoreCase(p.getApproverStatus()) && p.getPayType().equalsIgnoreCase(generateINDPayrollDTO.getPaytype()) && !PayrollConstant.C2C.equalsIgnoreCase(p.getEmployeeDesignationType()))
								.collect(Collectors.toList());
					}
					indPaysheetFrequencyDTO.setIndPayrollDTO(draftPayroll);
				}
		}
		return indPaysheetFrequencyDTO;
	}
	
	
}