package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dto.IndPayrollPayheadUtilDTO;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.AssociateSalaryPayheadInd;
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
	public IndPaysheetFrequencyDTO getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO,IndPaysheetFrequency paysheetFrequency, OrgLocations orgLocation,User loginUser) {
		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = null;
		if (paysheetFrequency != null && paysheetFrequency.getIndEmployeepayroll() != null) {
			try {
					indPaysheetFrequencyDTO = iNDPayrollCommonUtil.getIndPaysheetFrequencyDTOFromIndPaysheetFrequency(paysheetFrequency, orgLocation);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return indPaysheetFrequencyDTO;
	}

	@Override
	public IndPaysheetFrequencyDTO generateINDPayroll(OrgLocations orgLocation,
			GenerateINDPayrollDTO generateINDPayrollDTO, User loginUser) throws Exception {

		String dates[] = generateINDPayrollDTO.getPayPeriod().split(",");
		Date freqStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date freqEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = null;
		
		
		String[] groupId = generateINDPayrollDTO.getGroupId().split(",");
		List<AssociateSalaryInd> assSallist = associateSalaryService.getIndAssociateSalaryListByGroupIdForInitiatePayRoll(orgLocation,Long.parseLong(groupId[1]),generateINDPayrollDTO.getPaytype(),freqStartDate,freqEndDate);
		UserGroup userGroup = userGroupDao.findUserGroupById(groupId[0]);
		if (assSallist != null && assSallist.size() >0) {
		     IndPaysheetFrequency paySheetFreq = new IndPaysheetFrequency();
			 Long payRunId = generateINDPayrollDao.getMaxPayRunIdByOrgLocationId(orgLocation.getId());
			 if(payRunId == null) {
				payRunId = 1l;
			 }else {
				payRunId = payRunId+1;
			 }
			 paySheetFreq.setPayRunId(payRunId);
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
			paySheetFreq.setApproverStatus(PayrollConstant.NEW);
			paySheetFreq.setActionPerformedBy(""+loginUser.getFullName());
			paySheetFreq.setPaySheetFrequencyUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			
			
			Set<IndEmployeePayroll> payrollList = calculatePayroll(paySheetFreq, loginUser, orgLocation,assSallist,freqStartDate, freqEndDate,userGroup);
			if(payrollList !=null && payrollList.size() > 0) {
				paySheetFreq.setIndEmployeepayroll(payrollList);
			}
			indPaysheetFrequencyDTO = getPayrollData(generateINDPayrollDTO,paySheetFreq,orgLocation,loginUser);
				if(indPaysheetFrequencyDTO != null && indPaysheetFrequencyDTO.getIndPayrollDTO() != null) {
					List<INDPayrollDTO> draftPayroll = null;
					if(Constants.ALL_RECORDS.equalsIgnoreCase(generateINDPayrollDTO.getPaytype())) {
						draftPayroll = indPaysheetFrequencyDTO.getIndPayrollDTO()
								.stream()
								.filter(p->p.getEmployeeDesignationType() != null && (PayrollConstant.NEW.equalsIgnoreCase(p.getApproverStatus()) || PayrollConstant.DRAFT.equalsIgnoreCase(p.getApproverStatus()))&& !PayrollConstant.C2C.equalsIgnoreCase(p.getEmployeeDesignationType()))
								.collect(Collectors.toList());
					}else {
						draftPayroll = indPaysheetFrequencyDTO.getIndPayrollDTO()
								.stream()
								.filter(p->p.getEmployeeDesignationType() != null && (PayrollConstant.NEW.equalsIgnoreCase(p.getApproverStatus()) || PayrollConstant.DRAFT.equalsIgnoreCase(p.getApproverStatus()))&& p.getPayType().equalsIgnoreCase(generateINDPayrollDTO.getPaytype()) && !PayrollConstant.C2C.equalsIgnoreCase(p.getEmployeeDesignationType()))
								.collect(Collectors.toList());
					}
					indPaysheetFrequencyDTO.setIndPayrollDTO(draftPayroll);
				}
		}
		return indPaysheetFrequencyDTO;
	}
	
	@SuppressWarnings({ "static-access", "null" })
	private Set<IndEmployeePayroll> calculatePayroll(IndPaysheetFrequency paySheetFreq, User loginUser, OrgLocations orgLocation, List<AssociateSalaryInd> assoSalList, Date freqStartDate, Date freqEndDate, UserGroup userGroup)
	{
		Set<IndEmployeePayroll> set = new HashSet<>();
		try {
			for (AssociateSalaryInd assoSal : assoSalList) {
				    IndEmployeePayroll employeePayroll = new IndEmployeePayroll();
				    employeePayroll.setIndPaysheetFrequency(paySheetFreq);
					double totalHours = 0;
					BigDecimal payRate = BigDecimal.ZERO;
					BigDecimal amount = BigDecimal.ZERO;
					BigDecimal regularHoursAmount = BigDecimal.ZERO;
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
				    if(PayrollConstant.SALARY.equalsIgnoreCase(assoSal.getPayType())) {
						 amount = assoSal.getGrossSalary();
					}
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
					
					Set<AssociateSalaryPayheadInd> salaryPayheadList = new HashSet<AssociateSalaryPayheadInd>();
					for(AssociateSalaryPayheadInd associateSalaryPayheadInd : assoSal.getAssociateSalaryPayheadIndList()) {
						boolean isPayheadApplincable = true;
						if((StringUtils.isNotBlank(associateSalaryPayheadInd.getPayrollPayhead().getApplicableFor()) && associateSalaryPayheadInd.getPayrollPayhead().getApplicableFor().equalsIgnoreCase("Experienced"))) {
							Date joinDate = null;
							if(employee.getHireDate() != null) {
								joinDate = employee.getHireDate();
							}else {
								joinDate = employee.getCreatedDate();
							}
							Date expDate = joinDate;
							DateUtils.addYears(expDate, Integer.parseInt(org.apache.commons.lang.StringUtils.isNotBlank(associateSalaryPayheadInd.getPayrollPayhead().getExperienceYears()) ? associateSalaryPayheadInd.getPayrollPayhead().getExperienceYears() : "0" ));
							DateUtils.addMonths(expDate, Integer.parseInt(org.apache.commons.lang.StringUtils.isNotBlank(associateSalaryPayheadInd.getPayrollPayhead().getExperienceMonths()) ? associateSalaryPayheadInd.getPayrollPayhead().getExperienceMonths() : "0" ));
							if(expDate.before(freqStartDate)) {
								isPayheadApplincable = false;
							}
						}
						if(associateSalaryPayheadInd.getPayrollPayhead() != null && associateSalaryPayheadInd.getPayrollPayhead().getEffectiveDate() != null && associateSalaryPayheadInd.getPayrollPayhead().getEffectiveDate().before(freqEndDate) 
								&& isPayheadApplincable ) {
							salaryPayheadList.add(associateSalaryPayheadInd);
						}else if(associateSalaryPayheadInd.getPayrollPayhead() != null && associateSalaryPayheadInd.getPayrollPayhead().getEffectiveDate() == null
								&& isPayheadApplincable ) {
							salaryPayheadList.add(associateSalaryPayheadInd);
						}
					}
					
					IndPayrollPayheadUtilDTO utilDTO = iNDPayrollCommonUtil.getAssociatePayrollPayHeadHistoryFromAssociateSalaryPayHeads(salaryPayheadList, employeePayroll, loginUser,userGroup.getPayFrequency());
					employeePayroll.setAssociatePayrollPayheadHistory(utilDTO.getAssociatePayrollPayheadHistory());
					employeePayroll.setGrossPay(utilDTO.getGrossPay());
					employeePayroll.setNetPay(utilDTO.getNetPay());
					employeePayroll.setProfessionalTax(utilDTO.getProfessionalTax());
				    employeePayroll.setTds(utilDTO.getTds());
				    employeePayroll.setSubmitForAproverStatus(PayrollConstant.NEW);
					
					employeePayroll.setIndEmpPayrollUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					employeePayroll.setStatus(1);
					employeePayroll.setCreatedBy(loginUser.getId());
					employeePayroll.setUpdatedBy(loginUser.getId());
					employeePayroll.setCreatedDate(new Date());
					employeePayroll.setUpdatedDate(new Date());
					set.add(employeePayroll);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return set;
		
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