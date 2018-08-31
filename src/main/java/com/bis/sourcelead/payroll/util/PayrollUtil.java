package com.bis.sourcelead.payroll.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import com.bis.sourcelead.payroll.entity.AssociateDeduction;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PayitemHistory;
import com.bis.sourcelead.payroll.service.AssociateContributionService;
import com.bis.sourcelead.payroll.service.AssociateDeductionService;
import com.bis.sourcelead.util.CommonUtil;

@Component
public class PayrollUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollUtil.class);
	
	@Autowired
	AssociateDeductionService associateDeductionService;
	
	
	@Autowired
	CommonUtil commonUtil;
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
						contDTO.setAmount(precontrAmt);
						contDTO.setId(dedHis.getContributionId().toString());
						contDTO.setPostContributionAmt(postcontrAmt);
						contDTO.setId(String.valueOf(dedHis.getContributionId().getId()));
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
					dedDTO.setId(String.valueOf(dedHis.getDeductionId().getId()));
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
	 * Calculating PayItem
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
				payItemDTO.setPayItemAmt(his.getAssociatePayItem().getPayItemAmount().doubleValue());
				list.add(payItemDTO);
			}
		}
		return list;
	}
}
