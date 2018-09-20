package com.bis.sourcelead.payroll.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAY_SHEET")
public class Paysheet {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "CANDIDATE_ID")
	private Long candidateId;
	
	@ManyToOne
	@JoinColumn(name = "PAY_SHEET_FREQUENCY_ID", nullable = false)
	private PaysheetFrequency paysheetFrequency;
	
	@Column(name = "PROJECT_MEMBER_ID")
	private Long projectMember;
	
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name = "HOURS")
	private String hours;
	
	@Column(name = "PROJ_BILL_RATE")
	private BigDecimal projBillRate;
	
	@Column(name = "PROJ_PAY_RATE")
	private BigDecimal projPayRate;
	
	@Column(name = "PROJ_PAY_PERCENT")
	private Double projPayPercent;
	
	@Column(name = "EXPENSES_REIMBURSMENT")
	private BigDecimal expensesReimbursment;
	
	@Column(name = "PRE_DEDUCTION")
	private BigDecimal preDeduction;
	
	@Column(name = "LCA_SALARY_MONTH")
	private BigDecimal lcaSalaryMonth;
	
	@Column(name = "ADDITIONAL_PAY")
	private BigDecimal additionalPay;
	
	@Column(name = "GROSS_PAY")
	private BigDecimal grossPay;
	
	@Column(name = "BONUS_ACCURAL_PAY_PERIOD")
	private BigDecimal bonusAccuralPayPeriod;
	
	@Column(name = "BONUS_ACCURAL_YTD")
	private BigDecimal bonusAccuralYtd;
	
	@Column(name = "POST_DEDUCTION")
	private BigDecimal postDeduction;
	
	@Column(name = "VENDOR_LAST_PAID_DATE")
	private Date vendorLastPaidDate;
	
	@Column(name = "MODE_OF_PAY")
	private String modeOfPay;
	
	@Column(name = "NOTES", columnDefinition = "TEXT default NULL")
	private String notes;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}


	public PaysheetFrequency getPaysheetFrequency()
	{
		return paysheetFrequency;
	}

	public String getEmployeeId()
	{
		return employeeId;
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

	public BigDecimal getExpensesReimbursment()
	{
		return expensesReimbursment;
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

	public BigDecimal getBonusAccuralPayPeriod()
	{
		return bonusAccuralPayPeriod;
	}

	public BigDecimal getBonusAccuralYtd()
	{
		return bonusAccuralYtd;
	}

	public BigDecimal getPostDeduction()
	{
		return postDeduction;
	}

	public Date getVendorLastPaidDate()
	{
		return vendorLastPaidDate;
	}

	public String getModeOfPay()
	{
		return modeOfPay;
	}
	

	public String getNotes()
	{
		return notes;
	}

	public Integer getStatus()
	{
		return status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;	

	}

	public Long getUpdatedBy()
	{
		return updatedBy;
	}

	public void setId(Long id)
	{
		this.id = id;
	}


	public void setPaysheetFrequency(PaysheetFrequency paysheetFrequency)
	{
		this.paysheetFrequency = paysheetFrequency;
	}

	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
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

	public void setExpensesReimbursment(BigDecimal expensesReimbursment)
	{
		this.expensesReimbursment = expensesReimbursment;
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

	public void setBonusAccuralPayPeriod(BigDecimal bonusAccuralPayPeriod)
	{
		this.bonusAccuralPayPeriod = bonusAccuralPayPeriod;
	}

	public void setBonusAccuralYtd(BigDecimal bonusAccuralYtd)
	{
		this.bonusAccuralYtd = bonusAccuralYtd;
	}

	public void setPostDeduction(BigDecimal postDeduction)
	{
		this.postDeduction = postDeduction;
	}

	public void setVendorLastPaidDate(Date vendorLastPaidDate)
	{
		this.vendorLastPaidDate = vendorLastPaidDate;
	}

	public void setModeOfPay(String modeOfPay)
	{
		this.modeOfPay = modeOfPay;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(Long updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public Long getProjectMember()
	{
		return projectMember;
	}

	public void setProjectMember(Long projectMember)
	{
		this.projectMember = projectMember;
	}


	public Long getCandidateId()
	{
		return candidateId;
	}

	public void setCandidateId(Long candidateId)
	{
		this.candidateId = candidateId;
	}

	public Long getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(Long createdBy)
	{
		this.createdBy = createdBy;
	}

	public String getHours()
	{
		return hours;
	}

	public void setHours(String hours)
	{
		this.hours = hours;
	}


	
}
