package com.bis.sourcelead.payroll.util;

import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.FilteredClientDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PayrollScheduleCalenderDTO;
import com.bis.sourcelead.payroll.dto.PayrollScheduleDTO;
import com.bis.sourcelead.payroll.dto.StaffingFilteredDTO;
import com.bis.sourcelead.payroll.dto.StaffingFilteredDetailsDTO;
import com.bis.sourcelead.payroll.dto.StaffingFilteredReviewsDTO;
import com.bis.sourcelead.payroll.dto.VendorRecruitersDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.PayitemHistory;
import com.bis.sourcelead.payroll.entity.PayrollLopHis;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.util.CommonUtil;

import javassist.compiler.NoFieldException;

@Component
public class PayrollUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollUtil.class);
	
	private static DecimalFormat df2 = new DecimalFormat(".##");
	
	@Autowired
	AssociateDeductionService associateDeductionService;
	
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	PaysheetFrequencyService paysheetFrequencyService;
	
	@Autowired
	LeaveService leaveService;
	
	/***
	 * calculating total amount by multipltying hours with rate
	 * 
	 * @param totalHours
	 * @param payRate
	 * @return double
	 */
	public double getTotalAmount(float totalHours, Double payRate)
	{
		String pRate = String.valueOf(totalHours);
		String[] rate = pRate.split("\\.");
		double hr = Double.parseDouble(rate[0]);
		double min = Double.parseDouble(rate[1]);
		double hrsToMin = hr * 60;
		double totalMin = hrsToMin + min;
		double perMinRate = payRate / 60;
		return totalMin * perMinRate;
	}
	
	/**"
	 * Project Frequency
	 * @param invoiceFrequency
	 * @return
	 */
	public int getProjectInvoiceFrequency(String invoiceFrequency)
	{
		// Weekly Bi-Weekly Monthly
		int payPeriod = 0;
		if ("Weekly".equalsIgnoreCase(invoiceFrequency)) {
			payPeriod = 52;
		}
		if ("Bi-Weekly".equalsIgnoreCase(invoiceFrequency)) {
			payPeriod = 26;
		}
		if ("Monthly".equalsIgnoreCase(invoiceFrequency)) {
			payPeriod = 12;
		}
		return payPeriod;
	}

	public static boolean getEmployeeResident(Candidate candidate)
	{
		boolean isResident = false;
		
		String employeeState = candidate.getResidenceState();
		switch (employeeState) {
			case PayrollConstant.ALABAMA :
				isResident = true;
				break;
			case PayrollConstant.COLORADO :
				isResident = true;
				break;
			case PayrollConstant.DELAWARE :
				isResident = true;
				break;
			case PayrollConstant.INDIANA_COUNTY_TAX :
				isResident = true;
				break;
			case PayrollConstant.KENTUCKY :
				isResident = true;
				break;
			case PayrollConstant.MARYLAND :
				isResident = true;
				break;
			case PayrollConstant.MICHIGAN :
				isResident = true;
				break;
			case PayrollConstant.MISSOURI :
				isResident = true;
				break;
			case PayrollConstant.NEW_YORK :
				isResident = true;
				break;
			case PayrollConstant.OHIO :
				isResident = true;
				break;
			case PayrollConstant.OREGON :
				isResident = true;
				break;
			case PayrollConstant.PENNSYLVANIA :
				isResident = true;
				break;
			case PayrollConstant.WASHINGTON :
				isResident = true;
				break;
			case PayrollConstant.WEST_VIRGINIA :
				isResident = true;
				break;
			default:
				break;
		}
		return isResident;
	}
	
	/**
	 * Checking Deduction Schedles to Deduct
	 * 
	 * @param assoDeduction
	 * @param freqStartDate
	 * @param freqEndDate
	 * @return boolean
	 */
	public boolean findDeductionSchedulePay(AssociateDeduction assoDeduction, Date freqStartDate, Date freqEndDate)
	{
		String schedule = assoDeduction.getDeductionTypeId().getPayrollScheduled();
		boolean deduction = false;
		switch (schedule) {
			case PayrollConstant.EVERY_PAY:
				deduction = false;
				break;
			case PayrollConstant.SEMI_ANNUALLY:
				if (StringUtils.isNotBlank(assoDeduction.getDedStatus())) {
					deduction = isDateInBetweenIncludingEndPoints(assoDeduction.getDedStartDate(), assoDeduction.getDedEndDate(), freqStartDate, freqEndDate);
				}
				break;
			case PayrollConstant.MONTHLY:
				if (StringUtils.isNotBlank(assoDeduction.getDedStatus())) {
				deduction = isDateInBetweenIncludingEndPoints(assoDeduction.getDedStartDate(), assoDeduction.getDedEndDate(), freqStartDate, freqEndDate);
				}
				break;
			case PayrollConstant.QUARTERLY:
				if (StringUtils.isNotBlank(assoDeduction.getDedStatus())) {
				deduction = isDateInBetweenIncludingEndPoints(assoDeduction.getDedStartDate(), assoDeduction.getDedEndDate(), freqStartDate, freqEndDate);
				}
				break;
			case PayrollConstant.ANNUALLY:
				if (StringUtils.isNotBlank(assoDeduction.getDedStatus())) {
				deduction = isDateInBetweenIncludingEndPoints(assoDeduction.getDedStartDate(), assoDeduction.getDedEndDate(), freqStartDate, freqEndDate);
				}
				break;
			default:
				break;
		}
		return deduction;
	}
	
	public boolean findPayItemSchedulePay(AssociatePayItem associatePayItem, Date freqStartDate, Date freqEndDate)
	{
		String schedule = associatePayItem.getPayItemSchedule();
		boolean deduction = false;
		switch (schedule) {
		case PayrollConstant.EVERY_PAY:
			deduction = false;
			break;
		case PayrollConstant.SEMI_ANNUALLY:
			if (StringUtils.isNotBlank(associatePayItem.getPayitemStatus())) {
				deduction = isDateInBetweenIncludingEndPoints(associatePayItem.getPayitemStartDate(), associatePayItem.getPayitemEndDate(), freqStartDate, freqEndDate);
			}
			break;
		case PayrollConstant.MONTHLY:
			if (StringUtils.isNotBlank(associatePayItem.getPayitemStatus())) {
				deduction = isDateInBetweenIncludingEndPoints(associatePayItem.getPayitemStartDate(), associatePayItem.getPayitemEndDate(), freqStartDate, freqEndDate);
			}
			break;
		case PayrollConstant.QUARTERLY:
			if (StringUtils.isNotBlank(associatePayItem.getPayitemStatus())) {
				deduction = isDateInBetweenIncludingEndPoints(associatePayItem.getPayitemStartDate(), associatePayItem.getPayitemEndDate(), freqStartDate, freqEndDate);
			}
			break;
		case PayrollConstant.ANNUALLY:
			if (StringUtils.isNotBlank(associatePayItem.getPayitemStatus())) {
				deduction = isDateInBetweenIncludingEndPoints(associatePayItem.getPayitemStartDate(), associatePayItem.getPayitemEndDate(), freqStartDate, freqEndDate);
			}
			break;
		default:
			break;
	}
		return deduction;
	}
	
	/**
	 * Check Date avaialbiltity
	 * @param minDate
	 * @param maxDate
	 * @param sdate
	 * @param eDate
	 * @return boolean
	 */
	private boolean isDateInBetweenIncludingEndPoints(Date minDate, Date maxDate, Date date, Date cDate)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(minDate);
		Date min = cal.getTime();
		cal.setTime(maxDate);
		Date max = cal.getTime();
		boolean match = false;
		try {
			if (((date.after(min) && date.before(max)) || (date.equals(min) || date.equals(max)))
					&& ((cDate.after(min) && cDate.before(max))
							|| (cDate.equals(max) || cDate.equals(min)))) {
				match = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return match;
	}

	
	/**
	 * Check Date avaialbiltity
	 * 
	 * @param minDate
	 * @param maxDate
	 * @param sdate
	 * @param eDate
	 * @return boolean
	 */
	public boolean isDateBetweenEffectiveDate(Date minDate, Date maxDate, Date ferquenctStarDate, Date frequencyEndDate)
	{
		boolean match = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(minDate);
		Date min = cal.getTime();
		
		try {
			if (maxDate != null && maxDate != null) {
				cal.setTime(maxDate);
				Date max = cal.getTime();
				if (((ferquenctStarDate.after(min) && ferquenctStarDate.before(max)) || (ferquenctStarDate.equals(min) || ferquenctStarDate.equals(max)))
						&& ((frequencyEndDate.after(min) && frequencyEndDate.before(max)) || (frequencyEndDate.equals(max) || frequencyEndDate.equals(min)))) {
					match = true;
				}
			}
			else {
				if (ferquenctStarDate.after(min) || (ferquenctStarDate.equals(min) )) {
					match = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return match;
	}

	public Date[] getDeductionDates(Date currentDate, String schedule)
	{
		Date[] dates = new Date[2];
		dates[0] = currentDate;
		try {
			Calendar cal = Calendar.getInstance();
			 cal.setTime(currentDate);
			switch (schedule) {
				case PayrollConstant.EVERY_PAY:
					break;
				case PayrollConstant.SEMI_ANNUALLY:
					
					break;
				case PayrollConstant.MONTHLY:
					    cal.add(Calendar.MONTH, +1);  
				        dates[1]  = cal.getTime();
					break;
				case PayrollConstant.QUARTERLY:
					    cal.add(Calendar.MONTH, +3);  
				        dates[1]  = cal.getTime();
					break;
				case PayrollConstant.ANNUALLY:
					 cal.add(Calendar.MONTH, +12);  
				        dates[1]  = cal.getTime();
					break;
				default:
					break;
			}
		}catch (Exception e) {
		 e.printStackTrace();
		}
		return dates;
	}
	
	
	public Date[] getLoanAndAdvancesScheduleDates(Date currentDate, String schedule)
	{
		Date[] dates = new Date[2];
		dates[0] = currentDate;
		try {
			Calendar cal = Calendar.getInstance();
			 cal.setTime(currentDate);
			switch (schedule) {
				case PayrollConstant.LOAN_EVERY_PAY:
					break;
				case PayrollConstant.LOAN_EVERY_QUARTER:
					    cal.add(Calendar.MONTH, +3);  
				        dates[1]  = cal.getTime();
					break;
				case PayrollConstant.LOAN_HALF_YEARLY:
				    cal.add(Calendar.MONTH, +6);  
			        dates[1]  = cal.getTime();
				break;
				case PayrollConstant.LOAN_ANNUALLY:
					 cal.add(Calendar.MONTH, +12);  
				        dates[1]  = cal.getTime();
					break;
				default:
					break;
			}
		}catch (Exception e) {
		 e.printStackTrace();
		}
		return dates;
	}
	

	/**
	 * Calculation Deduction of employee payroll
	 * 
	 * @param payroll
	 */
	public List<ContributionDTO> getContributionData(EmployeePayroll payroll)
	{
		List<ContributionDTO> contributionList = new ArrayList<>();
		try {
			Set<DeductionHistory> hisList = payroll.getDeductionHistory();
			if (hisList != null && hisList.size() > 0) {
				for (DeductionHistory dedHis : hisList) {
					double precontrAmt = 0;
					double postcontrAmt = 0;
					double amount = 0;
					if (dedHis.getContributionId() != null) {
						ContributionDTO contDTO = new ContributionDTO();
						if (dedHis.getPreContributionAmount() != null) {
							precontrAmt = dedHis.getPreContributionAmount().doubleValue();
							if (dedHis.getPreContributionAmount().compareTo(BigDecimal.ZERO) !=  0) {
								amount = dedHis.getPreContributionAmount().doubleValue();
							}
						}
						if (dedHis.getPostContributionAmount() != null) {
							postcontrAmt = dedHis.getPostContributionAmount().doubleValue();
							if (dedHis.getPostContributionAmount().compareTo(BigDecimal.ZERO) !=  0 ) {
								amount = dedHis.getPostContributionAmount().doubleValue();
							}
						}
						contDTO.setId(dedHis.getId().toString());
						contDTO.setAmount(precontrAmt);
						contDTO.setPostContributionAmt(postcontrAmt);
						contDTO.setContributionType(dedHis.getContributionId().getContributionTypeId().getDeductionTypeId().getPayrollDeductionType());
						contDTO.setTaxType(dedHis.getContributionId().getContributionTypeId().getDeductionTypeId().getTaxType());
						contDTO.setContributionAmount(dedHis.getContributionId().getContributionAmount());
						contDTO.setContributionAmt(amount);
						contributionList.add(contDTO);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contributionList;
	}

	/**
	 * Calculation Deduction of employee payroll
	 * @param payroll
	 */
	public List<DeductionDTO> getDeductionData(EmployeePayroll payroll)
	{
		List<DeductionDTO> dedList = new ArrayList<>();
		try {
			Set<DeductionHistory> hisList = payroll.getDeductionHistory();
			if (hisList != null && hisList.size() > 0) {
				for (DeductionHistory dedHis : hisList) {
					DeductionDTO dedDTO = new DeductionDTO();
					dedDTO.setId(String.valueOf(dedHis.getId()));
					dedDTO.setDeductionType(dedHis.getDeductionId().getDeductionTypeId().getPayrollDeductionType());
					double preDedAmt = 0;
					double postDedAmt = 0;
					double dedAmount = 0;
					if (dedHis.getPreDeductionAmount() != null) {
						preDedAmt = dedHis.getPreDeductionAmount().doubleValue();
						if (dedHis.getPreDeductionAmount().compareTo(BigDecimal.ZERO) !=  0) {
							dedAmount = dedHis.getPreDeductionAmount().doubleValue();
						}
					}
					if (dedHis.getPostDeductionAmount() != null) {
						postDedAmt = dedHis.getPostDeductionAmount().doubleValue();
						if (dedHis.getPostDeductionAmount().compareTo(BigDecimal.ZERO) !=  0) {
							dedAmount = dedHis.getPostDeductionAmount().doubleValue();
						}
					}
					dedDTO.setDedAmount(dedAmount);
					dedDTO.setPreDeductionAmount(preDedAmt);
					dedDTO.setPostDeductionAmt(postDedAmt);
					dedDTO.setDedTaxType(dedHis.getDeductionId().getDeductionTypeId().getTaxType());
					dedDTO.setAmount(dedHis.getDeductionId().getDeductionAmount());
					dedList.add(dedDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dedList;
	}

	/**
	 * Get Year Start Date and current date
	 * @param orgLocation
	 * @return Date[]
	 */
	public Date[] getYTDDates(OrgLocations orgLocation)
	{
		Date[] ytd = new Date[2];
		try {
			Calendar c = Calendar.getInstance(); // this takes current date
			ytd[1] = commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), c.getTime());
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			ytd[0] = commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ytd;
	}

	/**
	 * 
	 * Calculating PayItem
	 * 
	 * @param payroll
	 * @return List<AssociatePayItemDTO>
	 */
	public List<AssociatePayItemDTO> getPayItemData(EmployeePayroll payroll)
	{
		List<AssociatePayItemDTO> list = new ArrayList<>();
		if (payroll.getPayitemHistory() != null) {
			for (PayitemHistory his : payroll.getPayitemHistory()) {
				AssociatePayItemDTO payItemDTO = new AssociatePayItemDTO();
				payItemDTO.setId(his.getId().toString());
				payItemDTO.setPayItemName(his.getAssociatePayItem().getPayItemName());
				payItemDTO.setPayItemAmt(his.getAmount().doubleValue());
				if(his.getIsBlocked() != null) {
					payItemDTO.setIsBlocked(his.getIsBlocked());	
				}else {
					payItemDTO.setIsBlocked("");
				}
				if(StringUtils.isNotBlank(his.getIsBlocked())) {
					payItemDTO.setIsBlocked(his.getIsBlocked());	
				}
				list.add(payItemDTO);
			}
		}
		return list;
	}

	/**
	 * Get Houry rate by lca wage type
	 * 
	 * @param amount
	 * @param lcaWageType
	 * @param payFrequency
	 * @return BigDecimal
	 */
	public BigDecimal getActualRateByPayFrequency(BigDecimal amount, String lcaWageType, String payFrequency)
	{
		BigDecimal houryRate = BigDecimal.ZERO;
		BigDecimal payAmount = BigDecimal.ZERO;
		BigDecimal hours = BigDecimal.ZERO;
		if (PayrollConstant.PAY_YEARLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(2088), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_MONTHLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(176), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_BI_WEEKLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(80), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_WEEKLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(40), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_BI_MONTHLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(88), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.HOURLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount;
		}
		
		
		// amount calculation
		if (PayrollConstant.PAY_ANNUAL.equalsIgnoreCase(payFrequency)) {
			hours = new BigDecimal(2088);
		}
		else if (PayrollConstant.PAY_MONTHLY.equalsIgnoreCase(payFrequency)) {
			hours = new BigDecimal(176);
		}
		else if (PayrollConstant.PAY_BI_WEEKLY.equalsIgnoreCase(payFrequency)) {
			hours = new BigDecimal(80);
		}
		else if (PayrollConstant.PAY_WEEKLY.equalsIgnoreCase(payFrequency)) {
			hours = new BigDecimal(40);
		}
		payAmount = houryRate.multiply(hours);
		return payAmount;
	}
	
	
	/**
	 * Get Houry rate by lca wage type
	 * 
	 * @param amount
	 * @param lcaWageType
	 * @param payFrequency
	 * @return BigDecimal
	 */
	public BigDecimal getHourlyRate(BigDecimal amount, String lcaWageType)
	{
		BigDecimal houryRate = BigDecimal.ZERO;
		if (PayrollConstant.PAY_YEARLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(2088), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_MONTHLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(176), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_BI_WEEKLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(80), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_WEEKLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(40), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_BI_MONTHLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(88), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.HOURLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount;
		}
		return houryRate;
	}
	
	
	/**
	 * Calaculation slary up to termination date
	 * 
	 * @param payFrequency
	 * @param noOfDays
	 * @param amount
	 * @param salType 
	 * @return BigDecimal
	 */
	public BigDecimal calculateSalaryUptoTerminationDate(String payFrequency, int noOfDays, BigDecimal amount)
	{
		BigDecimal perDaySalary = BigDecimal.ZERO;
		BigDecimal salary = BigDecimal.ZERO;
		try {
			if (PayrollConstant.PAY_YEARLY.equalsIgnoreCase(payFrequency.trim())) {
				perDaySalary = amount.divide(new BigDecimal(365), 3, RoundingMode.CEILING);
			}
			else if (PayrollConstant.PAY_MONTHLY.equalsIgnoreCase(payFrequency.trim())) {
				perDaySalary = amount.divide(new BigDecimal(30), 3, RoundingMode.CEILING);
			}
			else if (PayrollConstant.PAY_BI_WEEKLY.equalsIgnoreCase(payFrequency.trim())) {
				perDaySalary = amount.divide(new BigDecimal(14), 3, RoundingMode.CEILING);
			}
			else if (PayrollConstant.PAY_WEEKLY.equalsIgnoreCase(payFrequency.trim())) {
				perDaySalary = amount.divide(new BigDecimal(7), 3, RoundingMode.CEILING);
			}
			else if (PayrollConstant.PAY_BI_MONTHLY.equalsIgnoreCase(payFrequency.trim())) {
				perDaySalary = amount.divide(new BigDecimal(15), 3, RoundingMode.CEILING);
			}
			salary = perDaySalary.multiply(new BigDecimal(noOfDays));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return salary;
	}
	
	
	public BigDecimal calculateSalaryUptoTerminationDateLcaWage(BigDecimal amount,String lcaWageType, int noOfDays, OrgLocations orgLocation)
	{
		BigDecimal houryRate = BigDecimal.ZERO;
		BigDecimal payAmount = BigDecimal.ZERO;
		BigDecimal hours = BigDecimal.ZERO;
		if (PayrollConstant.PAY_YEARLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(2088), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_MONTHLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(176), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_BI_WEEKLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(80), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_WEEKLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(40), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.PAY_BI_MONTHLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount.divide(new BigDecimal(88), 3, RoundingMode.CEILING);
		}
		else if (PayrollConstant.HOURLY.equalsIgnoreCase(lcaWageType.trim())) {
			houryRate = amount;
		}
		
		// amount calculation
		int averageWorkingHours = orgLocation.getAverageWorkingHours();
		int totalHours = averageWorkingHours * noOfDays;
		
		payAmount = houryRate.multiply(new BigDecimal(totalHours));
		return payAmount;
	}
	
	/**
	 * Calculating YTD of an Employee
	 * @param payroll
	 * @param orgLocation
	 * @param approverStatus
	 * @param periodYear 
	 * @return YTDPayrollDTO
	 */
	public YTDPayrollDTO getEmployeeYTDPayroll(Candidate candidate, OrgLocations orgLocation, String approverStatus, int periodYear)
	{
		YTDPayrollDTO ytd = new YTDPayrollDTO();
		BigDecimal ytdFederal = BigDecimal.ZERO;
		BigDecimal ytdFICA = BigDecimal.ZERO;
		BigDecimal ytdMedicare = BigDecimal.ZERO;
		BigDecimal ytdCounty = BigDecimal.ZERO;
		BigDecimal ytdlocaltax = BigDecimal.ZERO;
		BigDecimal ytdStateWithholding = BigDecimal.ZERO;
		BigDecimal ytdEarnedIncCredit = BigDecimal.ZERO;
		BigDecimal ytdSUI = BigDecimal.ZERO;
		
		BigDecimal ytdSDI = BigDecimal.ZERO;
		BigDecimal ytdFLI = BigDecimal.ZERO;
		BigDecimal ytdHCSF = BigDecimal.ZERO;
		BigDecimal ytdWC = BigDecimal.ZERO;
		
		double ytdGross = 0;
		double ytdDeduction = 0;
		double ytdNet = 0;
		double ytdTaxes = 0;
		
		List<DeductionDTO> dedHisList = new ArrayList<>();
		List<ContributionDTO> contrHisList = new ArrayList<>();
		try {
			List<EmployeePayroll> ytdList = paysheetFrequencyService.employeePayrollYTD(candidate,orgLocation,approverStatus,periodYear);
			if(ytdList != null && ytdList.size() >0) {
				 ytdGross = ytdList.stream().mapToDouble(pay->pay.getGrossPay().doubleValue()).sum();
				 ytdNet = ytdList.stream().mapToDouble(pay->pay.getNetPay().doubleValue()).sum();
				 ytdTaxes = ytdList.stream().mapToDouble(pay->pay.getTaxes().doubleValue()).sum();
				for(EmployeePayroll ytdPayroll : ytdList) {
					EmployeePayrollTax payrollTax = ytdPayroll.getUsPayrollTax().iterator().next();
					ytdFederal = ytdFederal.add(payrollTax.getFederalWithholding());
					ytdFICA = ytdFICA.add(payrollTax.getFicaWithholding());
					ytdMedicare = ytdMedicare.add(payrollTax.getMedicareWithholding());
					ytdCounty = ytdCounty.add(payrollTax.getCountyWithholding());
					ytdlocaltax = ytdlocaltax.add(payrollTax.getLocalTax());
					ytdStateWithholding = ytdStateWithholding.add(payrollTax.getStateWithholding());
					ytdEarnedIncCredit = payrollTax.getEarnedIncCredit();
					List<DeductionDTO> ytdDedlist = getDeductionData(ytdPayroll);
					dedHisList.addAll(ytdDedlist);
					List<ContributionDTO> ytdContributionList = getContributionData(ytdPayroll);
					contrHisList.addAll(ytdContributionList);
				}
				if(dedHisList.size() >0) {
					 ytdDeduction = dedHisList.stream().mapToDouble(ded->ded.getDedAmount()).sum();
					 Map<String, Double> ytdMap = dedHisList.stream().collect(Collectors.groupingBy(DeductionDTO::getDeductionType,Collectors.summingDouble(DeductionDTO::getDedAmount)));
					 ytd.setYtdDedTypeMap(ytdMap);
				}
				if(contrHisList.size() >0) {
					 Map<String, Double> ytdContributionMap = contrHisList.stream().collect(Collectors.groupingBy(ContributionDTO::getContributionType,Collectors.summingDouble(ContributionDTO::getContributionAmt)));
					 ytd.setYtdContributionTypeMap(ytdContributionMap);
				}
			}
			df2.setRoundingMode(RoundingMode.UP);
			ytd.setYtdFederal(ytdFederal);
			ytd.setYtdFICA(ytdFICA);
			ytd.setYtdGross(new BigDecimal(df2.format(ytdGross)));
			ytd.setYtdNet(new BigDecimal(df2.format(ytdNet)));
			ytd.setYtdTaxes(new BigDecimal(df2.format(ytdTaxes)));
			ytd.setYtdDeduction(new BigDecimal(df2.format(ytdDeduction)));	
			ytd.setYtdCounty(ytdCounty);
			ytd.setYtdMedicare(ytdMedicare);
			ytd.setYtdEarnedIncCredit(ytdEarnedIncCredit);
			ytd.setytdStateWithholding(ytdStateWithholding);
			ytd.setYtdlocaltax(ytdlocaltax);
			ytd.setYtdSUI(ytdSUI);
			ytd.setYtdSDI(ytdSDI);
			ytd.setYtdFLI(ytdFLI);
			ytd.setYtdHCSF(ytdHCSF);
			ytd.setYtdWC(ytdWC);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ytd;
	}
	
	
	/**
	 * Get YTD Deductions and Contributions
	 * 
	 * @param payrollId
	 * @param candidateId
	 * @return BigDecimal
	 */
	public YTDPayrollDTO getContributionYTD(EmployeePayroll payroll, Long contributionId, OrgLocations orglocation)
	{
		YTDPayrollDTO yTDPayrollDTO = paysheetFrequencyService.getContributionYTD(payroll, contributionId, orglocation, "report");
		return yTDPayrollDTO;
	}
	
	/**
	 * Get start date and end date of months
	 * @param month
	 * @param orgLocation
	 * @return
	 */
	public List<String> getNumberOfMonths(int month, OrgLocations orgLocation)
	{
		List<String> dateList = new ArrayList<>();
		for (int i = 0; i <month; i++) {
			java.util.Date[] datesss = commonUtil.getMonthStartDateEndDateByMonth(i);
			StringBuilder sb = new StringBuilder();
			dateList.add(sb.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),datesss[0])).append(",").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),datesss[1])).toString());
		}
		return dateList;
	}
		
		
		/**
		 * Get all week start date and end date of year
		 * @param year
		 * @param month1
		 * @param orgLocation 
		 * @return List<List<String>> 
		 */
	public List<String> getNumberOfWeeks(int year, int month, OrgLocations orgLocation)
	{
		List<String> payFrequency = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		List<List<String>> weekdates = new ArrayList<List<String>>();
		List<String> dates;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		for (int i = 0; i <=month; i++) {
			c.set(Calendar.MONTH, i);
			c.set(Calendar.DAY_OF_MONTH, 1);
			while (c.get(Calendar.MONTH) == i) {
				dates = new ArrayList<String>();
				while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
					c.add(Calendar.DAY_OF_MONTH, -1);
				}
				dates.add(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), c.getTime()));
				c.add(Calendar.DAY_OF_MONTH, 6);
				dates.add(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), c.getTime()));
				weekdates.add(dates);
				c.add(Calendar.DAY_OF_MONTH, 7);
			}
		}
		for (List<String> innerList : weekdates) {
			StringBuilder sb = new StringBuilder();
			sb.append(innerList.get(0)).append(",").append(innerList.get(1));
			payFrequency.add(sb.toString());
		}
		return payFrequency;
	}

	/**
	 * @ get biweekly dates of current year
	 * @param orgLocation
	 */
		public List<String> getNumberOfBiWeeks(OrgLocations orgLocation) {
			

			Calendar cal = Calendar.getInstance();
			cal.get(Calendar.YEAR);
			cal.set(Calendar.DAY_OF_YEAR, 1);    
			Date start = cal.getTime();
			
			List<List<String>> dates = new ArrayList<List<String>>();
			List<String> dateFreq = null;
			for(int i=1; i<=12;i++) {
				StringBuilder sb = new StringBuilder();
				StringBuilder sb1 = new StringBuilder();
				dateFreq = new ArrayList<>();
				String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), start);
				
				cal.add(Calendar.DATE, 14);
				String endDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), cal.getTime());
				
				sb.append(startDate).append(",").append(endDate);
				
				cal.add(Calendar.DAY_OF_YEAR, 1);
				String secondStartDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), cal.getTime());
				
				cal.add(Calendar.DATE, 14);
				String secondEndDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), cal.getTime());
				
				sb1.append(secondStartDate).append(",").append(secondEndDate);
				
				cal.add(Calendar.DAY_OF_YEAR, 1);
				start = cal.getTime();
				
				dateFreq.add(sb.toString());
				dateFreq.add(sb1.toString());
				dates.add(dateFreq);
			}
			List<String> finalDates = new ArrayList<>();
			for (List<String> date : dates) {
				finalDates.add(date.get(0));
				finalDates.add(date.get(1));
			}
			List<String> frequencyDates = new ArrayList<>();
			for (String frequency : finalDates) {
				frequencyDates.add(frequency);
			}
			return frequencyDates;
		}

		/**
		 * get annual start date and end date
		 * @param orgLocation
		 * @return
		 */
		public List<String> getAnnuallyDates(OrgLocations orgLocation) {
			
			List<String> frequency = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			Calendar cal = Calendar.getInstance();
			cal.get(Calendar.YEAR);
			cal.set(Calendar.DAY_OF_YEAR, 1);    
			Date start = cal.getTime();
			
			cal.set(Calendar.MONTH, 11); 
			cal.set(Calendar.DAY_OF_MONTH, 31);
			Date end = cal.getTime();
			
			sb.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), start))
			.append(",")
			.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), end));
			frequency.add(sb.toString());
			
			return frequency;
			
		}

		/**
		 * YTD Deductions
		 * 
		 * @param payroll
		 * @param deductionId
		 * @param orgLocation
		 * @return YTDPayrollDTO
		 */
		public YTDPayrollDTO getDeductionYTD(EmployeePayroll payroll, Long deductionId, OrgLocations orgLocation) {
			return paysheetFrequencyService.getDeductionYTD(payroll,deductionId,orgLocation,"report");
		}
		
		
		public YTDPayrollDTO getPayrollTaxesYTD(EmployeePayroll payroll,OrgLocations orgLocation) {
			return paysheetFrequencyService.getPayrollTaxesYTD(payroll,orgLocation);
		}

		
	/**
	 * Ths is for loan and advances in edit payroll
	 * 
	 * @param payroll
	 * @return
	 */
	public List<AssociateLoanAndAdvancesDTO> getLoanAndAdvancesData(EmployeePayroll payroll)
	{
		List<AssociateLoanAndAdvancesDTO> list = new ArrayList<>();
		if (payroll.getPayitemHistory() != null) {
			for (AssociateLoanAndAdvanceHistory his : payroll.getLoanHistory()) {
				AssociateLoanAndAdvancesDTO loanDto = new AssociateLoanAndAdvancesDTO();
				loanDto.setId(his.getId());
				loanDto.setLoanAndAdvName(his.getAssociateLoanAndAdvances().getPayrollLoanAndAdvanceId().getName());
				loanDto.setLoanAmount(his.getDeductAmount().toString());
				list.add(loanDto);
			}
		}
		return list;
	}

	public List<PayrollScheduleCalenderDTO> getPayScheduleWeeksByDate(UsPayrollSchedule paySchedule, OrgLocations orgLocation)
	{
		
		List<PayrollScheduleCalenderDTO> payFrequency = new ArrayList<>();
		try {
			List<List<String>> weekdates = new ArrayList<List<String>>();
			List<String> dates;
			int numberOfDays = 0;
			if(paySchedule != null) {
			if("Monthly".equalsIgnoreCase(paySchedule.getPayFrequency().trim())) {
			  String[] mm = paySchedule.getNextPayMonth().split("-");
			  int month = getMonth(mm[0]);
			    Calendar cal = Calendar.getInstance();
				int year = Integer.parseInt(mm[1]);
				SimpleDateFormat sds = new SimpleDateFormat("MMM-YYYY");
				Calendar calEndDate = Calendar.getInstance();
				calEndDate.set(Calendar.MONTH,month);
				calEndDate.set(Calendar.YEAR,year);
				calEndDate.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				int checkDateDiff = commonUtil.diffBtwTowDates(calEndDate.getTime(),paySchedule.getPayCheckDate());
				int runPayDateDiff = commonUtil.diffBtwTowDates(calEndDate.getTime(),paySchedule.getRunPayrollDate());
				for(int h=0;h<2;h++) {
					cal.set(Calendar.YEAR,year+h);
					if(cal.get(Calendar.YEAR) <= Calendar.getInstance().get(Calendar.YEAR)){
						for(int i=month;i<12;i++) {
							PayrollScheduleCalenderDTO calenderDTO = new PayrollScheduleCalenderDTO();
							cal.set(Calendar.MONTH, i);
		//					cal.set(Calendar.DATE,1);
							cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
							Date monthStartDate = cal.getTime();
							calenderDTO.setPayPeriodStartDate(monthStartDate);
							cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
							Date monthEndDate = cal.getTime();
							calenderDTO.setPayPeriodEndDate(monthEndDate);
							// System.out.println(nextMonthLastDay);
							Calendar ncal = Calendar.getInstance();
							ncal.setTime(monthEndDate); // Now use today date.
							ncal.add(Calendar.DATE, checkDateDiff); // Adding 5 days
							String nextPayCheckDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), ncal.getTime());
							calenderDTO.setCheckDate(ncal.getTime());
							Calendar runPalCal = Calendar.getInstance();
							runPalCal.setTime(monthEndDate); // Now use today date.
							runPalCal.add(Calendar.DATE, runPayDateDiff); // Adding 5 days
							String nextPayRunDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), runPalCal.getTime());
							calenderDTO.setRunDate(runPalCal.getTime());
							calenderDTO.setPayPeriod(sds.format(monthStartDate).toUpperCase());
							calenderDTO.setPayFrequency(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), monthStartDate)+","+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), monthEndDate));
							calenderDTO.setStartDate(new SimpleDateFormat("MM/dd/yyyy").format(monthStartDate));
							calenderDTO.setEndDate(new SimpleDateFormat("MM/dd/yyyy").format(monthEndDate));
							calenderDTO.setPayCheckDate(nextPayCheckDate);
							calenderDTO.setPayRunDate(nextPayRunDate);
							calenderDTO.setDateFormat(sds.format(monthStartDate).toUpperCase());
							calenderDTO.setPayRunDateFormat(new SimpleDateFormat("MM/dd/yyyy").format(runPalCal.getTime()));
							calenderDTO.setPayCheckDateFormat(new SimpleDateFormat("MM/dd/yyyy").format(ncal.getTime()));
							calenderDTO.setMonth(i);
							payFrequency.add(calenderDTO);
						}
						month=0;
					}
				}
			}
//			Weekly Bi-Weekly
			if("Weekly".equalsIgnoreCase(paySchedule.getPayFrequency().trim())) {
				numberOfDays = 6;
			}else if("Bi-Weekly".equalsIgnoreCase(paySchedule.getPayFrequency().trim())) {
				numberOfDays = 13; 
			}
			Date date = paySchedule.getPayPeriodStartDate();
			if(!"Monthly".equalsIgnoreCase(paySchedule.getPayFrequency().trim())) {
				Calendar now = Calendar.getInstance();
				now.setTime(date);
				int year = now.get(Calendar.YEAR);
				int month = now.get(Calendar.MONTH);
				int dateInt = now.get(Calendar.DATE);
				for(int h=0;h<2;h++){
					Calendar c = Calendar.getInstance();
					c.set(Calendar.YEAR, year+h);
					if(c.get(Calendar.YEAR) <= Calendar.getInstance().get(Calendar.YEAR)){
						for (int i =month; i <=11; i++) {
							c.set(Calendar.MONTH, i);
							c.set(Calendar.DAY_OF_MONTH, dateInt);
							while (c.get(Calendar.MONTH) == i) {
								dates = new ArrayList<String>();
								dates.add(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), c.getTime()));
								c.add(Calendar.DAY_OF_MONTH, numberOfDays);
								dates.add(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), c.getTime()));
								weekdates.add(dates);
								c.add(Calendar.DAY_OF_MONTH, 1);
								dateInt = c.get(Calendar.DATE);
							}
						}
						month=0;
					}
				}	
				int checkDateDiff = commonUtil.diffBtwTowDates(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), weekdates.get(0).get(1)),paySchedule.getPayCheckDate());
				int runPayDateDiff = commonUtil.diffBtwTowDates(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), weekdates.get(0).get(1)),paySchedule.getRunPayrollDate());
				
				for (List<String> innerList : weekdates) {
					PayrollScheduleCalenderDTO calenderDTO = new PayrollScheduleCalenderDTO();
					StringBuilder sb = new StringBuilder();
					sb.append(innerList.get(0)).append(",").append(innerList.get(1));
					Calendar cal = Calendar.getInstance();
					cal.setTime(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), innerList.get(1))); // Now use today date.
					cal.add(Calendar.DATE, checkDateDiff); // Adding 5 days
					String nextPayCheckDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), cal.getTime());
					calenderDTO.setCheckDate(cal.getTime());
					Calendar runPalCal = Calendar.getInstance();
					runPalCal.setTime(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), innerList.get(1))); // Now use today date.
					runPalCal.add(Calendar.DATE, runPayDateDiff); // Adding 5 days
					String nextPayRunDate = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), runPalCal.getTime());
					calenderDTO.setRunDate(runPalCal.getTime());
					calenderDTO.setPayPeriod(sb.toString());
//					logger.info("*****************  "+sb.toString());
					calenderDTO.setPayFrequency(sb.toString());
					Date sDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), innerList.get(0));
					Date eDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), innerList.get(1));
					calenderDTO.setPayPeriodStartDate(sDate);
					calenderDTO.setPayPeriodEndDate(eDate);
					calenderDTO.setStartDate(new SimpleDateFormat("MM/dd/yyyy").format(sDate));
					calenderDTO.setEndDate(new SimpleDateFormat("MM/dd/yyyy").format(eDate));
					calenderDTO.setPayCheckDate(nextPayCheckDate);
					calenderDTO.setPayRunDate(nextPayRunDate);
					calenderDTO.setPayRunDateFormat(new SimpleDateFormat("MM/dd/yyyy").format(runPalCal.getTime()));
					calenderDTO.setPayCheckDateFormat(new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime()));
					String dateFormat = getDateChangedFormat(sDate,eDate);
					calenderDTO.setDateFormat(dateFormat);
					payFrequency.add(calenderDTO);
				}
			}
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
//		if("Monthly".equalsIgnoreCase(paySchedule.getPayFrequency().trim())) {
//			Collections.sort(payFrequency, (p1, p2) -> p1.getMonth() - p2.getMonth());
//			return payFrequency;
//		}
		return payFrequency;
	}
	
	
	
	private  List<String> getDaysBetweenDates(Date startdate, Date enddate)
	{
	    List<String> dates = new ArrayList<String>();
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(startdate);
        
	    while (calendar.getTime().getTime() <= enddate.getTime())
	    {
	        Date result = calendar.getTime();
	        dates.add(new SimpleDateFormat("MM/dd/yyyy").format(result));
	        System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(result));
//			boolean sameMonth = cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	        calendar.add(Calendar.DATE, 1);
	    }
	    return dates;
	}

	/**
	 * Changing Date format like Decemeber : 9-15 ,2018
	 * @param sDate
	 * @param eDate
	 * @return String
	 */
	private String getDateChangedFormat(Date sDate, Date eDate)
	{
		StringBuilder sb = new StringBuilder("");
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(sDate);
			String startDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
			cal.setTime(eDate);
			String endDate = new SimpleDateFormat("dd MMM yyyy").format(cal.getTime());
			sb.append(startDate).append(" / ").append(endDate);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Get Month Numbers
	 * @param monthName
	 * @return
	 */
	private int getMonth(String monthName)
	{
		int month = 0;
		switch (monthName) {
			case "JAN":
				month = 0;
				break;
			case "FEB":
				month = 1;
				break;
			case "MAR":
				month = 2;
				break;
			case "APR":
				month = 3;
				break;
			case "MAY":
				month = 4;
				break;
			case "JUN":
				month = 5;
				break;
			case "JUL":
				month = 6;
				break;
			case "AUG":
				month = 7;
				break;
			case "SEP":
				month = 8;
				break;
			case "OCT":
				month = 9;
				break;
			case "NOV":
				month = 10;
				break;
			case "DEC":
				month = 11;
				break;
			default:
				month = 0;
				break;
		}
		return month;
	}

	/**
	 * Calculating LOP amount for Leaves
	 * @param employee
	 * @param orgLocation 
	 * @param freqEndDate 
	 * @param freqStartDate 
	 * @param assoSal 
	 * @param amount 
	 * @param employeePayroll 
	 * @return
	 */
	public Set<PayrollLopHis> getLOPAmount(Employees employee, AssociateSalary assoSal, Date freqStartDate, Date freqEndDate, OrgLocations orgLocation, BigDecimal amount, EmployeePayroll employeePayroll)
	{
		
		String dayHours = "";
		BigDecimal lopAmt = BigDecimal.ZERO;
		Set<PayrollLopHis> lopHiList = null;
		try {
			lopHiList = leaveService.getLeaveByFrequency(assoSal.getCandidate().getConsultantUser().getId(), freqStartDate,freqEndDate,orgLocation,employeePayroll);
			if(lopHiList != null && lopHiList.size() > 0 ) {
			for(PayrollLopHis payrollLopHis : lopHiList) {
//			    	Double noOfLeaves = lopHiList.stream().mapToDouble(lop->lop.getLopLeaves()).sum();
			    			BigDecimal lopLeaves = new BigDecimal(payrollLopHis.getLopLeaves());
			    			BigDecimal compareLeave = new BigDecimal("0.5");
			    			 int res = compareLeave.compareTo(lopLeaves);
			    			 BigDecimal hourlyRate = BigDecimal.ZERO;
			    			 if(PayrollConstant.SALARY.equalsIgnoreCase(assoSal.getPayType())) {
			    				  hourlyRate = assoSal.getHourlyRate();
			    			 }else {
			    				  hourlyRate = amount.divide(new BigDecimal(176));
			    			 }
			    			 if( res == 0 || res == 1) {
			    				 //Calculating Half Day
			    					 if(employee != null && StringUtils.isNotBlank(employee.getDailyHours())) {
			    						 BigDecimal hours = new BigDecimal(dayHours.replace(":", "."));
			    						 BigDecimal dayHrs = hours.divide(new BigDecimal(2));
			    						 lopAmt = dayHrs.multiply(hourlyRate);
			    					 }else {
			    						 BigDecimal dayHrs = new BigDecimal(4);
			    						 lopAmt = dayHrs.multiply(hourlyRate);
			    					 }
			    			 }else if( res == -1 ) {
			    				 //Calculating Full Day
			    				 if(PayrollConstant.SALARY.equalsIgnoreCase(assoSal.getPayType())) {
			    					 String averageHours = "";
			    					 if(employee != null && StringUtils.isNotBlank(employee.getDailyHours())) {
			    						 averageHours = employee.getDailyHours().replace(":", ".");
			    					 }else {
			    						 averageHours = "8";
			    					 }
			    					 System.out.println("******************************averageHours = "+averageHours);
			    					 System.out.println("******************************averageHours = "+averageHours);
			    					 
			    					 
			    					 BigDecimal one = new BigDecimal("1");
			    					 double avgHrs = new BigDecimal(averageHours).doubleValue();
			    					 double leaves = lopLeaves.doubleValue();
			    					 double dyHours = 0;
			    					 int res1 = one.compareTo(lopLeaves);
			    					    if(res1 == 0 || res1 == 1) {
			    					    	 dyHours = avgHrs/2;
			    					    }else if(res1 == -1) {
			    					    	  dyHours = (avgHrs * leaves);
			    					    }
			    						 System.out.println("******************************leaves = "+leaves);
			    						 System.out.println("******************************leaves = "+dyHours);
			    						 System.out.println("******************************LOP Amount = "+lopAmt);
			    						 lopAmt = getTotalAmountForDouble(dyHours, hourlyRate);
			    					
			    				 }
			    			 }
			    			 System.out.println("******************************LOP Amount = "+lopAmt);
			    			 payrollLopHis.setLopAmount(lopAmt);
			    }	
			}
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lopHiList;
	}

	
	
	/***
	 * calculating total amount by multipltying hours with rate
	 * @param totalHours
	 * @param payRate
	 * @return double
	 */
	public BigDecimal getTotalAmount(String totalHours, BigDecimal payRate)
	{
		String pRate = String.valueOf(totalHours);
		String[] rate = pRate.split("\\.");
		double hr = Double.parseDouble(rate[0]);
		double min = (Double.parseDouble(rate[1]));
		double hrsToMin = hr * 60;
		double totalMin = hrsToMin + min;
//		BigDecimal perMinRate= payRate.divide(PayrollConstant.ONE_MIN_SEC,);
		double perMinRate = payRate.doubleValue() / 60;
		double totatAmount = totalMin * perMinRate;
		return BigDecimal.valueOf(totatAmount);
	}
	
	/***
	 * calculating total amount by multipltying hours with rate
	 * @param totalHours
	 * @param payRate
	 * @return double
	 */
	public BigDecimal getTotalAmountForDouble(double totalHours, BigDecimal payRate)
	{
		String pRate = String.valueOf(totalHours);
		String[] rate = pRate.split("\\.");
		double hr = Double.parseDouble(rate[0]);
		double min = (Double.parseDouble(rate[1]));
		double hrsToMin = hr * 60;
		double totalMin = hrsToMin + min;
//		BigDecimal perMinRate= payRate.divide(PayrollConstant.ONE_MIN_SEC,);
		double perMinRate = payRate.doubleValue() / 60;
		double totatAmount = totalMin * perMinRate;
		return BigDecimal.valueOf(totatAmount);
	}


	public ResponseList<FilteredClientDTO> getFilteredClientListData(FileReader reader) {
		ResponseList<FilteredClientDTO> res = new ResponseList<>();
		List<FilteredClientDTO> list = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		try {
			Object obj = jsonParser.parse(reader);
			JSONObject json = (JSONObject) obj;
			Set<String> s = json.keySet();
			for(String name : s) {
				JSONArray arr = (JSONArray) json.get(name);
				for(int i=0;i< arr.size(); i++) {
					try{
						JSONObject ob=new JSONObject((Map) arr.get(i));
						FilteredClientDTO dto = parseFilteredClientObject(ob);
						list.add(dto);
					}catch(Exception e) {
						System.out.println("error with index number :"+i);
					}
				}
			}
			res.setData(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private FilteredClientDTO parseFilteredClientObject(JSONObject ob)
    {
		FilteredClientDTO dto = new FilteredClientDTO();
		
		dto.setCompanyName((String) ob.get("company_name"));
		dto.setCompanyId((String) ob.get("company_id"));
		dto.setCompanyWebsite((String)ob.get("company_website"));
		dto.setOfficeCity((String)ob.get("office_city"));
		dto.setOfficeCountry((String)ob.get("office_country"));
		dto.setOfficeMetroArea((String)ob.get("office_metro_area"));
		dto.setOfficePhone((String)ob.get("office_phone"));
		dto.setOfficeState((String) ob.get("office_state"));
		dto.setOfficeStreet((String)ob.get("office_street"));
		dto.setOfficeWebsite((String)ob.get("office_website"));
		dto.setOfficeStreet((String)ob.get("office_street"));
         
		return dto;
    }

	public Response<Map<String, String>> getCategoriesForStaffingFilteredData(FileReader reader) {
		Response<Map<String,String>> res = new Response<>();
		JSONParser jsonParser = new JSONParser();
		Map<String,String> map = new HashMap<>();
		try {
			Object obj = jsonParser.parse(reader);

			JSONObject json = (JSONObject) obj;
			Set<String> s = json.keySet();
			for(String name : s) {
				if("categories".equalsIgnoreCase(name)) {
					System.out.println(json.get(name));
					JSONObject json1 = (JSONObject) json.get(name);
					Set<String> catSet = json1.keySet();
					System.out.println(catSet);
					for(String str : catSet) {
						JSONObject ob = new JSONObject((Map) json1.get(str));
						map.put(str, (String) ob.get("category_name"));
					}
				}
			}
			res.setObject(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public ResponseList<StaffingFilteredDTO> getStaffingFilteredListByCategoryIdData(FileReader reader, String categoryId) {
		ResponseList<StaffingFilteredDTO> res = new ResponseList<>();
		JSONParser jsonParser = new JSONParser();
		try {
			Map<String,String> map = new HashMap<>();
			Object obj = jsonParser.parse(reader);
			JSONObject json = (JSONObject) obj;
			Set<String> s = json.keySet();
			List<StaffingFilteredDTO> list = new ArrayList<>(); 		// create list object for staffing filtered
			for(String name : s) {
				if("all".equalsIgnoreCase(name)) {
					JSONObject json1 = (JSONObject) json.get(name);
					Set<String> catSet = json1.keySet();
					for(String catId : catSet) {
						if(categoryId.equalsIgnoreCase(catId)) {
							JSONArray arr = (JSONArray) json1.get(catId);
							for(int i=0;i< arr.size(); i++) {
								try{
									JSONObject ob=new JSONObject((Map) arr.get(i));
									StaffingFilteredDTO dto = prepareStaffingFilteredDTO(ob);
									list.add(dto);                                          // add dto to list object
								}catch(Exception e) {
									e.printStackTrace();
									System.out.println("error with index number :"+i);
								}
							}
						}
					}
					
				}
			}
			res.setData(list);     	// set list object to response
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private StaffingFilteredDTO prepareStaffingFilteredDTO(JSONObject ob) {

		StaffingFilteredDTO dto = new StaffingFilteredDTO();
		dto.setCategoryId((String) ob.get("category_id"));
		dto.setCategoryName((String) ob.get("category_name"));
		dto.setOfficeCity((String) ob.get("office_city"));
		dto.setOfficeCountry((String) ob.get("office_country"));
		dto.setOfficeMetroArea((String) ob.get("office_metro_area"));
		dto.setOfficePhone((String) ob.get("office_phone"));
		dto.setOfficeState((String) ob.get("office_state"));
		dto.setOfficeStreet((String) ob.get("office_street"));
		dto.setOfficeWebsite((String) ob.get("office_website"));
		dto.setOfficeZip((String) ob.get("office_zip"));
		dto.setVendorId((String) ob.get("vendor_id"));
		dto.setVendorName((String) ob.get("vendor_name"));
		dto.setVendorWebsite((String) ob.get("vendor_website"));

		//details
		JSONObject json = (JSONObject) ob.get("details");
		List<StaffingFilteredDetailsDTO> detailsDtoList = new ArrayList<>();
//		for(int i=0; i<arr.size();i++) {
//			JSONObject json = (JSONObject) arr.get(i);
			StaffingFilteredDetailsDTO detailsDto = new StaffingFilteredDetailsDTO();
			detailsDto.setRating(String.valueOf(json.get("rating")));
			detailsDto.setVendorId((String) json.get("vendor_id"));
			detailsDto.setVendorName((String) json.get("vendor_name"));
			JSONArray clientArr = (JSONArray) json.get("clients");
			List<String> clientList = new ArrayList<>();
			for(int j=0; j<clientArr.size(); j++) {
				JSONObject cliObj = (JSONObject) clientArr.get(j);
				clientList.add((String) cliObj.get("company_name"));
			}
			detailsDto.setClients(clientList);
			
			JSONArray hlccArr = (JSONArray) json.get("h1cc");
			List<Map<String,String>> mapList = new ArrayList<>();
			for(int k=0; k<hlccArr.size();k++) {
				JSONObject hlccObj = (JSONObject) hlccArr.get(k);
				Map<String,String> map = new HashMap<>();
				map.put("hire_corpcorp",(String) hlccObj.get("hire_corpcorp"));
				map.put("sponsor_h1",(String) hlccObj.get("sponsor_h1"));
				mapList.add(map);
			}
			detailsDto.setHlcc(mapList);
			
			//reviews
			
			JSONArray revarr = (JSONArray) ob.get("reviews");
			if(revarr != null) {
				List<StaffingFilteredReviewsDTO> reviewDtoList = new ArrayList<>();
				for(int l=0; l<revarr.size(); l++) {
					JSONObject jsonrev = (JSONObject) revarr.get(l);
					StaffingFilteredReviewsDTO reviewsDto = new StaffingFilteredReviewsDTO();
					reviewsDto.setAnonymous((String) jsonrev.get("anonymous"));
					reviewsDto.setDescription((String) jsonrev.get("description"));
					reviewsDto.setRating((String) jsonrev.get("rating"));
					reviewsDto.setVendorId((String) jsonrev.get("vendor_id"));
					reviewDtoList.add(reviewsDto);
				}
				detailsDto.setReviews(reviewDtoList);
			}
			
			JSONArray jobArr = (JSONArray) ob.get("jobs");
			if(jobArr != null) {
				List<String> jobsList = new ArrayList<>();
				for(int job = 0; job<jobArr.size();job++) {
					// to do
				}
				detailsDto.setJobs(jobsList);
			}
			
			detailsDtoList.add(detailsDto);
//		}
		return dto;

	}

	public ResponseList<VendorRecruitersDTO> getVendorRecruiterData(FileReader reader) {
		ResponseList<VendorRecruitersDTO> res = new ResponseList<>();
		JSONParser jsonParser = new JSONParser();
		try {
			List<VendorRecruitersDTO> list = new ArrayList<>();
			Object obj = jsonParser.parse(reader);
			JSONObject json = (JSONObject) obj;
			Set<String> s = json.keySet();
			for(String name : s) {
				JSONArray arr = (JSONArray) json.get(name);
				for(int i=0;i< arr.size(); i++) {
					try{
						JSONObject ob=new JSONObject((Map) arr.get(i));
						VendorRecruitersDTO dto = parseVendorRecruitersObject(ob);
						list.add(dto);
					}catch(Exception e) {
						System.out.println("error with index number :"+i);
					}
				}
			}
			res.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private VendorRecruitersDTO parseVendorRecruitersObject(JSONObject ob)
	{
		VendorRecruitersDTO dto = new VendorRecruitersDTO();
		dto.setCompanyName((String) ob.get("company_name"));
		dto.setExperienceAs((String) ob.get("experience_as"));
		dto.setExperienceDetail((String) ob.get("experience_detail"));
		dto.setExperienceDetails((String) ob.get("experience_details"));
		dto.setExperienceFrom((String) ob.get("experience_from"));
		dto.setExperienceId((String) ob.get("experience_id"));
		dto.setExperienceTillPresent((String) ob.get("experience_till_present"));
		dto.setExperienceTo((String) ob.get("experience_to"));
		dto.setUserFname((String) ob.get("user_fname"));
		dto.setUserId((String) ob.get("user_id"));
		dto.setUserLname((String) ob.get("user_lname"));
		dto.setVendorName((String) ob.get("vendor_name"));
		return dto;
	}

	/**
	 * Get Year to current Dates with Year
	 * @param orgLocation
	 * @param periodYear
	 * @return Date[]
	 */
	public Date[] getYTDDatesPerYear(OrgLocations orgLocation, int periodYear)
	{
		Date[] ytd = new Date[2];
		try {
			
			Calendar c = Calendar.getInstance(); // this takes current date
			
			int currentYear = c.get(Calendar.YEAR);
			if(periodYear == currentYear) {
				
				ytd[1] = commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), c.getTime());
				c.set(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, 1);
				ytd[0] = commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), c.getTime());
				
			}else {
				c.set(Calendar.YEAR,periodYear);
				c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_YEAR));
				ytd[1] = commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), c.getTime());
				c.set(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, 1);
				ytd[0] = commonUtil.getOrgLocationCurrentDate(orgLocation.getDateFormat(), c.getTime());
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ytd;
	}

	/**
	 * Adding Total Time 
	 * @param regulraHours
	 * @param overTimeHours
	 * @return String
	 */ 
	public String getTotalRegularOverTimeHours(double regulraHours, double overTimeHours)
	{
		String actualTimeString = "";
		try {
			double cthHours = regulraHours;
			double cthHoursValue = (int) cthHours;
			double cthMinutesValue = cthHours - cthHoursValue;
			double totalCthMinutes = (cthHoursValue * 60) + (cthMinutesValue * 100);
			double wthHours = overTimeHours;
			double wthHoursValue = (int) wthHours;
			double wthMinutesValue = wthHours - wthHoursValue;
			double totalWthMinutes = (wthHoursValue * 60) + (wthMinutesValue * 100);
			double totalMinutes = totalCthMinutes + totalWthMinutes;
			double actualHours = (int) (totalMinutes / 60); 
			double actualMinutes = (totalMinutes % 60) / 100;
			actualTimeString = String.format("%.2f", (actualHours + actualMinutes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actualTimeString;
	}
}
