package com.bis.sourcelead.payroll.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.COMM_FAILURE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.payroll.entity.PayitemHistory;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;
import com.bis.sourcelead.payroll.service.PaysheetFrequencyService;
import com.bis.sourcelead.util.CommonUtil;
import com.ibm.icu.text.DateFormat;

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
			if(PayrollConstant.PAY_YEARLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate =  amount.divide(new BigDecimal(2088),3, RoundingMode.CEILING);
			}else if(PayrollConstant.PAY_MONTHLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate = amount.divide(new BigDecimal(176),3, RoundingMode.CEILING);
			}else if(PayrollConstant.PAY_BI_WEEKLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate = amount.divide(new BigDecimal(80),3, RoundingMode.CEILING);
			}else if(PayrollConstant.PAY_WEEKLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate = amount.divide(new BigDecimal(40),3, RoundingMode.CEILING);
			}else if(PayrollConstant.PAY_BI_MONTHLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate = amount.divide(new BigDecimal(88),3, RoundingMode.CEILING);
			}else if(PayrollConstant.HOURLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate = amount;
			}
			
			if(PayrollConstant.PAY_ANNUAL.equalsIgnoreCase(payFrequency)){
				hours = new BigDecimal(2088);
			}else if(PayrollConstant.PAY_MONTHLY.equalsIgnoreCase(payFrequency)){
				hours = new BigDecimal(176);
			}else if(PayrollConstant.PAY_BI_WEEKLY.equalsIgnoreCase(payFrequency)){
				hours = new BigDecimal(80);
			}else if(PayrollConstant.PAY_WEEKLY.equalsIgnoreCase(payFrequency)){
				hours = new BigDecimal(40);
			}
			payAmount = houryRate.multiply(hours);
			return payAmount;
			
	}
	
	
	/**
	 * Get Houry rate by lca wage type
	 * @param amount
	 * @param lcaWageType
	 * @param payFrequency 
	 * @return BigDecimal
	 */
	public BigDecimal getHourlyRate(BigDecimal amount, String lcaWageType)
	{
		BigDecimal houryRate = BigDecimal.ZERO;
			if(PayrollConstant.PAY_YEARLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate =  amount.divide(new BigDecimal(2088),3, RoundingMode.CEILING);
			}else if(PayrollConstant.PAY_MONTHLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate = amount.divide(new BigDecimal(176),3, RoundingMode.CEILING);
			}else if(PayrollConstant.PAY_BI_WEEKLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate = amount.divide(new BigDecimal(80),3, RoundingMode.CEILING);
			}else if(PayrollConstant.PAY_WEEKLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate = amount.divide(new BigDecimal(40),3, RoundingMode.CEILING);
			}else if(PayrollConstant.PAY_BI_MONTHLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate = amount.divide(new BigDecimal(88),3, RoundingMode.CEILING);
			}else if(PayrollConstant.HOURLY.equalsIgnoreCase(lcaWageType.trim())){
				houryRate = amount;
			}
			return houryRate;
			
	}
	
	/**
	 * Calculating YTD of an Employee
	 * @param payroll
	 * @param orgLocation
	 * @param approverStatus
	 * @return YTDPayrollDTO
	 */
	public YTDPayrollDTO getEmployeeYTDPayroll(Candidate candidate, OrgLocations orgLocation, String approverStatus)
	{
		YTDPayrollDTO ytd = new YTDPayrollDTO();
		BigDecimal ytdFederal = BigDecimal.ZERO;
		BigDecimal ytdFICA = BigDecimal.ZERO;
		BigDecimal ytdMedicare = BigDecimal.ZERO;
		BigDecimal ytdCounty = BigDecimal.ZERO;
		BigDecimal ytdlocaltax = BigDecimal.ZERO;
		double ytdGross = 0;
		double ytdDeduction = 0;
		double ytdNet = 0;
		double ytdTaxes = 0;
		List<DeductionDTO> dedHisList = new ArrayList<>();
		List<ContributionDTO> contrHisList = new ArrayList<>();
		try {
			List<EmployeePayroll> ytdList = paysheetFrequencyService.employeePayrollYTD(candidate,orgLocation,approverStatus);
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
			ytd.setYtdlocaltax(ytdlocaltax);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ytd;
	}
	
	
	/**
	 * Get YTD Deductions and Contributions
	 * @param payrollId
	 * @param candidateId
	 * @return BigDecimal
	 */
	public BigDecimal getContributionYTD(EmployeePayroll payroll,Long contributionId,OrgLocations orglocation) {
		List<DeductionHistory> dedHisList = paysheetFrequencyService.getContributionYTD(payroll,contributionId,orglocation);
		BigDecimal contributionYTD = BigDecimal.ZERO;
		for(DeductionHistory dedHis : dedHisList) {
			if(dedHis.getPreContributionAmount() != null) {
				contributionYTD = contributionYTD.add(dedHis.getPreContributionAmount());
			}
			if(dedHis.getPostContributionAmount() != null) {
				contributionYTD = contributionYTD.add(dedHis.getPostContributionAmount());
			}
		}
		return contributionYTD;
		
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
	

	

}
