package com.bis.sourcelead.payroll.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.DeductionDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;

@Component
public class PayrollUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollUtil.class);
	
	@Autowired
	AssociateDeductionService associateDeductionService;
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
}
