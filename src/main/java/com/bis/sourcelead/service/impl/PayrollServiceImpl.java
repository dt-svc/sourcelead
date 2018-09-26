package com.bis.sourcelead.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PayrollDao;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.INDPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.TotalHoursDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.service.impl.GenerateUSPayrollServiceImpl;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.PayrollService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

/**
 * @author Ajith Matta
 *
 */
@Service
public class PayrollServiceImpl implements PayrollService {
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollServiceImpl.class);
	
	@Autowired
	private PayrollDao payrollDao;
	
	@Autowired
	private UserGroupDao userGroupDao;
	
	@Autowired
	private AssociateSalaryService associateSalaryService;
	
	@Autowired
	USPaysheetService uSPaysheetService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	private CommonUtil commonUtil;
	

	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@Override
	public Payroll getPayrollById(Long id) {
		
		return payrollDao.getPayrollById(id);
		
	}


	@Override
	public List<Payroll> getAllPayroll() {

		return payrollDao.getAllPayroll();
		
	}


	@Override
	public PaysheetFrequencyDTO getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO, OrgLocations orgLocation,
			User loginUser) {
		PaysheetFrequency paysheetFrequency = payrollDao.getPayrollData(generateINDPayrollDTO, orgLocation, loginUser);
		PaysheetFrequencyDTO paysheetFrequencyDTO = null;
		List<INDPayrollDTO> list = null;
		try {
			if (paysheetFrequency != null && paysheetFrequency.getEmployeepayroll() != null) {
				paysheetFrequencyDTO = new PaysheetFrequencyDTO();
				paysheetFrequencyDTO.setPayrollFrequencyUUID(paysheetFrequency.getPaySheetFrequencyUUID());
				paysheetFrequencyDTO.setPaySheetFrequencyId(paysheetFrequency.getId());
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
					payrollDTO.setEmpName(sb.toString());
					payrollDTO.setEmpPayrollId(pay.getId());
//					payrollDTO.setEmpPayrollUUID(pay.getUsEmpPayrollUUID());
					payrollDTO.setGrossPay(pay.getGrossPay());
					payrollDTO.setNetPay(pay.getNetPay());
					payrollDTO.setPayRate(pay.getEmpPayRate());
					payrollDTO.setRegularHours(Float.parseFloat(pay.getRegularHours()));
//					payrollDTO.setTaxes(pay.getTaxes());
					payrollDTO.setOtHoursTotalAmt(pay.getOverTimeAmount());
					payrollDTO.setCandidateId(pay.getCandidate().getId());
					//payrollDTO.setPaySheetFrequencyId(pay.getPaysheetFrequency().getId());
					double overTimeHours = 0;
					if(StringUtils.isNoneBlank(pay.getOverTimeHours())) {
						overTimeHours = Double.parseDouble(pay.getOverTimeHours());
					}
					payrollDTO.setOvertimeHours(overTimeHours);
					if(PayrollConstant.DRAFT.equalsIgnoreCase(pay.getSubmitForAproverStatus())) {
						list.add(payrollDTO);	
					}
					
				}
				paysheetFrequencyDTO.setIndPayrollDTO(list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paysheetFrequencyDTO;
	}


	@Override
	public PaysheetFrequencyDTO generateINDPayroll(PaysheetFrequencyDTO existedData, OrgLocations orgLocation,
			GenerateINDPayrollDTO generateINDPayrollDTO, User loginUser) {
		PaysheetFrequencyDTO paysheetFrequencyDTO = null;
		try {
			String dates[] = generateINDPayrollDTO.getPayPeriod().split(",");
			Date freqStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),
					dates[0].replaceAll("/", "-"));
			Date freqEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),
					dates[1].replaceAll("/", "-"));

			String[] groupId = generateINDPayrollDTO.getGroupId().split(",");
			List<AssociateSalaryInd> assSallist = associateSalaryService.getIndAssociateSalaryListByGroupId(orgLocation,
					Long.parseLong(groupId[1]));
			UserGroup userGroup = userGroupDao.findUserGroupById(groupId[0]);

			if (assSallist != null && assSallist.size() > 0) {
				PaysheetFrequency paySheetFreq = null;
				if (existedData != null) {
					paySheetFreq = new PaysheetFrequency();
					paySheetFreq.setId(existedData.getPaySheetFrequencyId());
				} else {
					paySheetFreq = new PaysheetFrequency();
				}

				paySheetFreq.setUserGroup(userGroup);
				paySheetFreq.setOrgLocation(orgLocation);
				String[] payPeriod = generateINDPayrollDTO.getPayPeriod().split(",");
				Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[0]);
				paySheetFreq.setFrequencyStartDate(startDate);
				Date endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), payPeriod[1]);
				paySheetFreq.setFrequencyEndDate(endDate);
				paySheetFreq.setPayCheckDate(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),
						generateINDPayrollDTO.getPayrollPayDate()));
				paySheetFreq.setCreatedDate(new Date());
				paySheetFreq.setUpdatedDate(new Date());
				paySheetFreq.setCreatedBy(loginUser.getId());
				paySheetFreq.setUpdatedBy(loginUser.getId());
				paySheetFreq.setStatus(1);
				paySheetFreq.setType(PayrollConstant.PAYROLL);
				paySheetFreq.setApproverStatus(PayrollConstant.DRAFT);
				paySheetFreq.setPaySheetFrequencyUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				Set<IndEmployeePayroll> payrollList = calculatePayroll(existedData, paySheetFreq, loginUser, orgLocation,
						assSallist, freqStartDate, freqEndDate, userGroup);
				paySheetFreq.setIndEmployeepayroll(payrollList);
				if (payrollList != null && payrollList.size() > 0) {
					PaysheetFrequency savedPaysheetFrequency = uSPaysheetService.addPaysheetFrequency(paySheetFreq);
				}
				paysheetFrequencyDTO = getPayrollData(generateINDPayrollDTO, orgLocation, loginUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paysheetFrequencyDTO;
	}
	
	private Set<IndEmployeePayroll> calculatePayroll(PaysheetFrequencyDTO existedData, PaysheetFrequency paysheetFrequency,
			User loginUser, OrgLocations orgLocation, List<AssociateSalaryInd> assoSalList, Date freqStartDate,
			Date freqEndDate, UserGroup userGroup) {
		Set<IndEmployeePayroll> indEmployeePayrollList = new HashSet<>();
		try {
			for (AssociateSalaryInd associateSalaryInd : assoSalList) {
				boolean isExisted = false;
				// TODO add a service to retrieve existing Payroll for the same given frequency
				if (!isExisted) {
					IndEmployeePayroll indEmployeePayroll = new IndEmployeePayroll();
					indEmployeePayroll.setCandidate(associateSalaryInd.getCandidate());
					Employees employee = employeesService.getEmployeeByUserAndOrgLocationId(associateSalaryInd.getCandidate().getConsultantUser().getId(),orgLocation.getId());
					if (employee != null) {
						indEmployeePayroll.setEmployee(employee);
					}
					//indEmployeePayroll.setPaysheetFrequency(paysheetFrequency);
					
					float totalHours = 0;
					BigDecimal payRate = BigDecimal.ZERO;
					BigDecimal amount = BigDecimal.ZERO;
					if (PayrollConstant.HOURLY_RATE.equalsIgnoreCase(associateSalaryInd.getPayType())) {
						List<TotalHoursDTO> totalHoursList = userGroupDao.getTotalHoursByCandidateId(associateSalaryInd.getCandidate().getId(), freqStartDate, freqEndDate);
						payRate = associateSalaryInd.getHourlyRate();
						if (totalHoursList != null && !totalHoursList.isEmpty()) {
							for(TotalHoursDTO totalHoursDTO : totalHoursList) {
								totalHours += Float.parseFloat(totalHoursDTO.getTotalHours());
							}
						}
						amount = getTotalAmount(totalHours, payRate);
					} else if (PayrollConstant.SALARY.equalsIgnoreCase(associateSalaryInd.getPayType())) {
						amount = associateSalaryInd.getGrossSalary();
					}
					indEmployeePayroll.setEmpPayRate(payRate);
					indEmployeePayroll.setRegularHours(String.valueOf(totalHours));
					
					//TODO calculate noof leaves
					BigDecimal noOfLeaves = BigDecimal.ZERO;
					indEmployeePayroll.setNoOfLeaves(noOfLeaves);
					logger.info("***** No.of Leaves : " + noOfLeaves);
					
					BigDecimal grossPay = new BigDecimal(0);
					indEmployeePayroll.setGrossPay(grossPay);
					logger.info("***** Gross Pay : " + grossPay);
					
					BigDecimal netPay = new BigDecimal(0);
					logger.info("***** Net Pay : " + netPay);
					indEmployeePayroll.setNetPay(netPay);
					
					BigDecimal professionalTax = new BigDecimal(0);
					logger.info("***** Professional Tax : " + netPay);
					indEmployeePayroll.setProfessionalTax(professionalTax);
					
					BigDecimal tds = new BigDecimal(0);
					logger.info("***** TDS : " + netPay);
					indEmployeePayroll.setTds(tds);
					indEmployeePayroll.setSubmitForAproverStatus(PayrollConstant.DRAFT);
					indEmployeePayrollList.add(indEmployeePayroll);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indEmployeePayrollList;
	}
	
	/***
	 * calculating total amount by multipltying hours with rate
	 * 
	 * @param totalHours
	 * @param payRate
	 * @return double
	 */
	private BigDecimal getTotalAmount(float totalHours, BigDecimal payRate) {
		String pRate = String.valueOf(totalHours);
		String[] rate = pRate.split("\\.");
		double hr = Double.parseDouble(rate[0]);
		double min = Double.parseDouble(rate[1]);
		double hrsToMin = hr * 60;
		double totalMin = hrsToMin + min;
		// BigDecimal perMinRate= payRate.divide(PayrollConstant.ONE_MIN_SEC,);
		double perMinRate = payRate.doubleValue() / 60;
		double totatAmount = totalMin * perMinRate;
		return BigDecimal.valueOf(totatAmount);
	}
	
}
