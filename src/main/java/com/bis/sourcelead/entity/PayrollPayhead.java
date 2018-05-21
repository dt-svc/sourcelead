package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PAYROLL_PAYHEAD")
public class PayrollPayhead {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="PARENT_PAYHEAD_ID")        
    private PayrollPayhead parentPayhead;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SHORT_CODE")
	private String shortCode;
	
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "PAY_TYPE")
    private PayrollPaytype payType;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Column(name = "FORMULA")
	private String formula;
	
	@Column(name = "FORM_16")
	private String form16;
	
	@Column(name = "TAX")
	private String tax;
	
	@Column(name = "APPLICABLE_FOR")
	private String applicableFor;
	
	@Column(name = "COMPONENT")
	private String component;
	
	@Column(name = "PAID_COMPONENT")
	private String paidComponent;
	
	@Column(name = "PART_OF_A_CTC")
	private String partofaCTC;
	
	@Column(name = "FBP_COMPONENT")
	private String fbpComponent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "ORGLOCATION_ID")
    private OrgLocations orgLocationId;
	
	@Column(name = "STATUS")
	private Integer status;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
    
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
    
    @Column(name = "CREATED_DATE")
  	private Date createdDate;
    
    @Column(name = "UPDATED_DATE")
   	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PayrollPayhead getParentPayhead() {
		return parentPayhead;
	}

	public void setParentPayhead(PayrollPayhead parentPayhead) {
		this.parentPayhead = parentPayhead;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public PayrollPaytype getPayType() {
		return payType;
	}

	public void setPayType(PayrollPaytype payType) {
		this.payType = payType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getForm16() {
		return form16;
	}

	public void setForm16(String form16) {
		this.form16 = form16;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getApplicableFor() {
		return applicableFor;
	}

	public void setApplicableFor(String applicableFor) {
		this.applicableFor = applicableFor;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getPaidComponent() {
		return paidComponent;
	}

	public void setPaidComponent(String paidComponent) {
		this.paidComponent = paidComponent;
	}

	public String getPartofaCTC() {
		return partofaCTC;
	}

	public void setPartofaCTC(String partofaCTC) {
		this.partofaCTC = partofaCTC;
	}

	public String getFbpComponent() {
		return fbpComponent;
	}

	public void setFbpComponent(String fbpComponent) {
		this.fbpComponent = fbpComponent;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
    
    
}
