package com.bis.sourcelead.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "ASSOCIATE_PAY_ITEM")
public class AssociatePayItem {
	
private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name= "ASSOCIATE_SALARY_ID")
	private AssociateSalary associateSalary;
	
	@Column(name = "PAY_ITEM_NAME")
	private String payItemName;
	
	@Column(name = "ORG_PAY_ITEM_ID")
	private Long orgPayItemId;

	@Column(name = "PAY_ITEM_SCHEDULE")
	private String payItemSchedule;

	@Column( name ="OVERRIDE")
	private String overRide;
	
	@Column(name ="EFFECTIVE_DATE")
	private Date effectiveDate;
	
    @Column(name ="END_DATE")
    private Date endDate;
    
    @Column( name ="PAY_ITEM_AMOUNT")
    private BigDecimal payItemAmount;
    
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "PAYITEM_STATUS")
	private String payitemStatus;
	
	@Column(name = "PAYITEM_START_DATE")
	private Date payitemStartDate;
	
	@Column(name = "PAYITEM_END_DATE")
	private Date payitemEndDate;
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public AssociateSalary getAssociateSalary()
	{
		return associateSalary;
	}

	public void setAssociateSalary(AssociateSalary associateSalary)
	{
		this.associateSalary = associateSalary;
	}

	public String getPayItemName()
	{
		return payItemName;
	}

	public void setPayItemName(String payItemName)
	{
		this.payItemName = payItemName;
	}

	public String getPayItemSchedule()
	{
		return payItemSchedule;
	}

	public void setPayItemSchedule(String payItemSchedule)
	{
		this.payItemSchedule = payItemSchedule;
	}

	public String getOverRide()
	{
		return overRide;
	}

	public void setOverRide(String overRide)
	{
		this.overRide = overRide;
	}

	public Date getEffectiveDate()
	{
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public BigDecimal getPayItemAmount()
	{
		return payItemAmount;
	}

	public void setPayItemAmount(BigDecimal payItemAmount)
	{
		this.payItemAmount = payItemAmount;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getPayitemStatus() {
		return payitemStatus;
	}

	public void setPayitemStatus(String payitemStatus) {
		this.payitemStatus = payitemStatus;
	}

	public Date getPayitemStartDate() {
		return payitemStartDate;
	}

	public void setPayitemStartDate(Date payitemStartDate) {
		this.payitemStartDate = payitemStartDate;
	}

	public Date getPayitemEndDate() {
		return payitemEndDate;
	}

	public void setPayitemEndDate(Date payitemEndDate) {
		this.payitemEndDate = payitemEndDate;
	}

	public Long getOrgPayItemId() {
		return orgPayItemId;
	}

	public void setOrgPayItemId(Long orgPayItemId) {
		this.orgPayItemId = orgPayItemId;
	}
    
}
