package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dao.GenerateINDPayrollDao;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.dto.INDPayrollDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.TotalHoursDTO;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.service.GenerateINDPayrollService;
import com.bis.sourcelead.payroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.INDPayrollCommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class GenerateINDPayrollServiceImpl implements GenerateINDPayrollService{
	
	@Autowired
	GenerateINDPayrollDao generateINDPayrollDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	INDPayrollCommonUtil iNDPayrollCommonUtil;
	
	@Autowired
	AssociateSalaryService associateSalaryService;
	
	@Autowired
	UserGroupDao userGroupDao;
	
	@Autowired
	IndPaysheetFrequencyService indPaysheetFrequencyService;
	
	@Autowired
	EmployeesService employeesService;

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
					payrollDTO.setPayType(pay.getPayType());
					
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
		List<AssociateSalaryInd> assSallist = associateSalaryService.getIndAssociateSalaryListByGroupId(orgLocation,Long.parseLong(groupId[1]),generateINDPayrollDTO.getPaytype());
		
		UserGroup userGroup = userGroupDao.findUserGroupById(groupId[0]);
		
		if (assSallist != null && assSallist.size() >0) {
			IndPaysheetFrequency paySheetFreq = null;
			if(existedData != null) {
				 paySheetFreq = new IndPaysheetFrequency();
				 paySheetFreq.setId(existedData.getPaySheetFrequencyId());
			}else {
				 paySheetFreq = new IndPaysheetFrequency();
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
			paySheetFreq.setApproverStatus(PayrollConstant.DRAFT);
			paySheetFreq.setActionPerformedBy(""+loginUser.getFullName());
			paySheetFreq.setPaySheetFrequencyUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			
			Long payRunId = generateINDPayrollDao.getMaxPayRunIdByOrgLocationId(orgLocation.getId());
			if(payRunId == null) {
				payRunId = 1l;
			}else {
				payRunId = payRunId+1;
			}
			paySheetFreq.setPayRunId(payRunId);
			Set<IndEmployeePayroll> payrollList = calculatePayroll(existedData,paySheetFreq, loginUser, orgLocation,assSallist,freqStartDate, freqEndDate,userGroup);
			if(payrollList !=null && payrollList.size() > 0) {
				paySheetFreq.setIndEmployeepayroll(payrollList);
				indPaysheetFrequencyService.addIndPaysheetFrequency(paySheetFreq);
			}
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
	
	@SuppressWarnings("static-access")
	private Set<IndEmployeePayroll> calculatePayroll(IndPaysheetFrequencyDTO existedData,IndPaysheetFrequency paySheetFreq, User loginUser, OrgLocations orgLocation, List<AssociateSalaryInd> assoSalList, Date freqStartDate, Date freqEndDate, UserGroup userGroup)
	{
		Set<IndEmployeePayroll> set = new HashSet<>();
		try {
			for (AssociateSalaryInd assoSal : assoSalList) {
				
				if(assoSal.getCandidate().getDesignationType() != null && !PayrollConstant.C2C.equalsIgnoreCase(assoSal.getCandidate().getDesignationType())){
					
					boolean isExisted = isPayrollRunForSameFrequency(existedData,assoSal);
					if(!isExisted) {
						    IndEmployeePayroll employeePayroll = new IndEmployeePayroll();
						    employeePayroll.setIndPaysheetFrequency(paySheetFreq);
							double totalHours = 0;
							BigDecimal payRate = BigDecimal.ZERO;
							BigDecimal amount = BigDecimal.ZERO;
							BigDecimal regularHoursAmount = BigDecimal.ZERO;
							
							if(PayrollConstant.HOURLY_RATE.equalsIgnoreCase(assoSal.getPayType())) {
								 List<TotalHoursDTO> totalHoursDto = userGroupDao.getTotalHoursByCandidateId(assoSal.getCandidate().getId(),freqStartDate,freqEndDate);
								 Map<BigInteger, List<TotalHoursDTO>> map = totalHoursDto.stream().collect(Collectors.groupingBy(TotalHoursDTO::getProjectId));
								
								for (Map.Entry<BigInteger, List<TotalHoursDTO>> entry : map.entrySet()) {
									BigDecimal projectGross = BigDecimal.ZERO;
									double projectTotalHours = 0;
									BigDecimal projectPayRate = BigDecimal.ZERO;
									if(entry.getValue() !=null ) {
										for(TotalHoursDTO totalHoursDTO : entry.getValue()) {
											projectPayRate = new BigDecimal(totalHoursDTO.getPayRate());
											if(StringUtils.isNotBlank(totalHoursDTO.getBillableHours())) {
												projectTotalHours  = commonUtil.getTotalRegularOverTimeHours(Double.parseDouble(totalHoursDTO.getBillableHours()),projectTotalHours);
											}
										}
									}
									projectGross = getTotalAmount(projectTotalHours,projectPayRate);
									amount = amount.add(projectGross);
									payRate = payRate.add(projectPayRate);
									totalHours = totalHours + projectTotalHours;
								} 
								regularHoursAmount = amount;
							}
							
							 else if(PayrollConstant.SALARY.equalsIgnoreCase(assoSal.getPayType())) {
								amount = assoSal.getGrossSalary();
							}
							/*if(associateTaxView.getAccountType() != null) {
								employeePayroll.setDepositType(associateTaxView.getAccountType());
							}*/
							employeePayroll.setPayType(assoSal.getPayType());
							employeePayroll.setPayPerSalary(amount);
							employeePayroll.setRegularHours(String.valueOf(totalHours));
							employeePayroll.setEmpPayRate(payRate);
							employeePayroll.setCandidate(assoSal.getCandidate());
							employeePayroll.setPayCheckDate(paySheetFreq.getPayCheckDate());
							employeePayroll.setRegularHoursAmount(regularHoursAmount);
							employeePayroll.setType(Constants.REGULAR);
							
							Employees employee = employeesService.getEmployeeByUserAndOrgLocationId(assoSal.getCandidate().getConsultantUser().getId(),orgLocation.getId());
							if (employee != null) {
								employeePayroll.setEmployee(employee);
							}
							
							//employeePayroll.setAssociatePayrollPayheadHistory(associatePayrollPayheadHistory);
							 
							
							HashSet<AssociatePayrollPayheadHistory> payHeadHistoryList = iNDPayrollCommonUtil.getAssociatePayrollPayHeadHistoryFromAssociateSalaryPayHeads(assoSal.getAssociateSalaryPayheadIndList(), employeePayroll, loginUser);
							employeePayroll.setAssociatePayrollPayheadHistory(payHeadHistoryList);
							
							employeePayroll.setGrossPay(BigDecimal.ZERO);
							employeePayroll.setNetPay(BigDecimal.ZERO);
							employeePayroll.setProfessionalTax(BigDecimal.ZERO);
						    employeePayroll.setTds(BigDecimal.ZERO);
						    employeePayroll.setSubmitForAproverStatus(PayrollConstant.DRAFT);
							
							employeePayroll.setIndEmpPayrollUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
							employeePayroll.setStatus(1);
							employeePayroll.setCreatedBy(loginUser.getId());
							employeePayroll.setUpdatedBy(loginUser.getId());
							employeePayroll.setCreatedDate(new Date());
							employeePayroll.setUpdatedDate(new Date());
							set.add(employeePayroll);
					}
					
				}
				
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return set;
		
	}
	
	  /**
	     * check same frequency or not 
	    * @param existedData
	    * @param assoSal
	    * @return boolean
	    */
		private boolean isPayrollRunForSameFrequency(IndPaysheetFrequencyDTO existedData, AssociateSalaryInd assoSal) {
			
			boolean isExisted = false;
			if(existedData != null) {
			for (INDPayrollDTO indPayrollDTO : existedData.getIndPayrollDTO()) {
				if((indPayrollDTO.getCandidateId().longValue() == assoSal.getCandidate().getId().longValue()) && (existedData.getPaySheetFrequencyId().longValue() == indPayrollDTO.getPaySheetFrequencyId().longValue())) {
					return true;
				}
			}
			}
			return isExisted;
		}
		/***
		 * calculating total amount by multipltying hours with rate
		 * @param totalHours
		 * @param payRate
		 * @return double
		 */
		private BigDecimal getTotalAmount(double totalHours, BigDecimal payRate)
		{
			String pRate = String.valueOf(totalHours);
			String[] rate = pRate.split("\\.");
			double hr = Double.parseDouble(rate[0]);
			double min = Double.parseDouble(rate[1]);
			double hrsToMin = hr * 60;
			double totalMin = hrsToMin + min;
			double perMinRate = payRate.doubleValue() / 60;
			double totatAmount = totalMin * perMinRate;
			return BigDecimal.valueOf(totatAmount);
		}

		@Override
		public Long getMaxPayRunIdByOrgLocationId(Long orgLocationId) {
			return generateINDPayrollDao.getMaxPayRunIdByOrgLocationId(orgLocationId);
		}
	
}