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

import com.bis.sourcelead.entity.AssociatePayItem;

@Entity
@Table(name = "US_PAYITEM_HIS")
public class PayitemHistory {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn (name="PAY_ITEM_ID")
	private AssociatePayItem associatePayItem;
	
	@Column (name ="AMOUNT")
	private BigDecimal amount;
	
	@ManyToOne
	@JoinColumn(name = "PAYROLL_ID")
	private EmployeePayroll payrollId;
	
	@Column (name="FREQUENCY_START_DATE")
	private Date frequencyStartDate;
	
	@Column (name="FREQUENCY_END_DATE")
	private Date frequencyEndDate;
	
	@Column (name="PAY_CHECK_DATE")
	private Date payCheckDate;
	
	
	@Column (name="PAYITEM_DATE")
	private Date payItemDate;
	
	@Column (name="PAYITEM_HIS_UUID")
	private String payItemHisUUID;
	
	@Column(name = "IS_BLOCKED")
	private String isBlocked;
	
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


	public Date getFrequencyStartDate()
	{
		return frequencyStartDate;
	}

	public Date getFrequencyEndDate()
	{
		return frequencyEndDate;
	}

	public Date getPayCheckDate()
	{
		return payCheckDate;
	}

	public Date getPayItemDate()
	{
		return payItemDate;
	}

	

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public Long getCreatedBy()
	{
		return createdBy;
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


	public void setFrequencyStartDate(Date frequencyStartDate)
	{
		this.frequencyStartDate = frequencyStartDate;
	}

	public void setFrequencyEndDate(Date frequencyEndDate)
	{
		this.frequencyEndDate = frequencyEndDate;
	}

	public void setPayCheckDate(Date payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}

	public void setPayItemDate(Date payItemDate)
	{
		this.payItemDate = payItemDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setCreatedBy(Long createdBy)
	{
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(Long updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public EmployeePayroll getPayrollId()
	{
		return payrollId;
	}

	public void setPayrollId(EmployeePayroll payrollId)
	{
		this.payrollId = payrollId;
	}

	public String getPayItemHisUUID()
	{
		return payItemHisUUID;
	}

	public void setPayItemHisUUID(String payItemHisUUID)
	{
		this.payItemHisUUID = payItemHisUUID;
	}

	public AssociatePayItem getAssociatePayItem()
	{
		return associatePayItem;
	}

	public void setAssociatePayItem(AssociatePayItem associatePayItem)
	{
		this.associatePayItem = associatePayItem;
	}

	public BigDecimal getAmount()
	{
		if(amount != null) {
			return amount;
		}else{
			return BigDecimal.ZERO;
		}
		
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public String getIsBlocked()
	{
		return isBlocked;
	}

	public void setIsBlocked(String isBlocked)
	{
		this.isBlocked = isBlocked;
	}
	
	
	
	
	
}
