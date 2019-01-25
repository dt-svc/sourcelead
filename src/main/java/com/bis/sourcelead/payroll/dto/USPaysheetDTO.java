package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.Date;

public class USPaysheetDTO {
	

	private String employeeId;       
	
	private String employeeName; 
	
	private String hours;        
	
	private BigDecimal projBillRate;    
	
	private BigDecimal projPayRate; 
	
	private Double projPayPercent;   
	
	private BigDecimal expensesReimbursement;   
	
	private BigDecimal preDeduction; // (health insurance)
	
	private BigDecimal lcaSalaryMonth;

	private BigDecimal additionalPay;  

	private BigDecimal grossPay; 

	private BigDecimal bonusAccrualPayPeriod; 

	private BigDecimal bonusAccrualYTD; 

	private BigDecimal postDeduction;  // (Advances)
	
	private String vendorLastpaidDate;   
	
	private String modeOfPay; 
	
	private String notes;
	
	private String projectMemberId;
	
	private String userId;
	
	public String getEmployeeId()
	{
		return employeeId;
	}

	public String getEmployeeName()
	{
		return employeeName;
	}

	public String getHours()
	{
		return hours;
	}


	public BigDecimal getExpensesReimbursement()
	{
		return expensesReimbursement;
	}

	public BigDecimal getPreDeduction()
	{
		return preDeduction;
	}

	public BigDecimal getLcaSalaryMonth()
	{
		return lcaSalaryMonth;
	}

	public BigDecimal getAdditionalPay()
	{
		return additionalPay;
	}

	public BigDecimal getGrossPay()
	{
		return grossPay;
	}



	public BigDecimal getPostDeduction()
	{
		return postDeduction;
	}


	public String getModeOfPay()
	{
		return modeOfPay;
	}

	public String getNotes()
	{
		return notes;
	}

	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
	}

	public void setEmployeeName(String employeeName)
	{
		this.employeeName = employeeName;
	}

	public void setHours(String hours)
	{
		this.hours = hours;
	}


	public void setExpensesReimbursement(BigDecimal expensesReimbursement)
	{
		this.expensesReimbursement = expensesReimbursement;
	}

	public void setPreDeduction(BigDecimal preDeduction)
	{
		this.preDeduction = preDeduction;
	}

	public void setLcaSalaryMonth(BigDecimal lcaSalaryMonth)
	{
		this.lcaSalaryMonth = lcaSalaryMonth;
	}

	public void setAdditionalPay(BigDecimal additionalPay)
	{
		this.additionalPay = additionalPay;
	}

	public void setGrossPay(BigDecimal grossPay)
	{
		this.grossPay = grossPay;
	}

	

	public BigDecimal getBonusAccrualPayPeriod()
	{
		return bonusAccrualPayPeriod;
	}

	public BigDecimal getBonusAccrualYTD()
	{
		return bonusAccrualYTD;
	}

	public void setBonusAccrualPayPeriod(BigDecimal bonusAccrualPayPeriod)
	{
		this.bonusAccrualPayPeriod = bonusAccrualPayPeriod;
	}

	public void setBonusAccrualYTD(BigDecimal bonusAccrualYTD)
	{
		this.bonusAccrualYTD = bonusAccrualYTD;
	}

	public void setPostDeduction(BigDecimal postDeduction)
	{
		this.postDeduction = postDeduction;
	}


	public void setModeOfPay(String modeOfPay)
	{
		this.modeOfPay = modeOfPay;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public BigDecimal getProjBillRate()
	{
		return projBillRate;
	}

	public BigDecimal getProjPayRate()
	{
		return projPayRate;
	}

	public Double getProjPayPercent()
	{
		return projPayPercent;
	}

	public void setProjBillRate(BigDecimal projBillRate)
	{
		this.projBillRate = projBillRate;
	}

	public void setProjPayRate(BigDecimal projPayRate)
	{
		this.projPayRate = projPayRate;
	}

	public void setProjPayPercent(Double projPayPercent)
	{
		this.projPayPercent = projPayPercent;
	}

	public String getProjectMemberId()
	{
		return projectMemberId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setProjectMemberId(String projectMemberId)
	{
		this.projectMemberId = projectMemberId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getVendorLastpaidDate()
	{
		return vendorLastpaidDate;
	}

	public void setVendorLastpaidDate(String vendorLastpaidDate)
	{
		this.vendorLastpaidDate = vendorLastpaidDate;
	}
	
	
}
