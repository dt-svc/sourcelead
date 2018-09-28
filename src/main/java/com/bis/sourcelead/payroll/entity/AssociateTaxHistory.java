package com.bis.sourcelead.payroll.entity;

import java.io.Serializable;
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
@Table(name = "ASSOCIATE_TAX_HISTORY")
public class AssociateTaxHistory implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	
	@Column(name = "ASSOCIATE_TAX_ID")
	private Long associateTaxId;
	
	@ManyToOne
	@JoinColumn(name = "PAYROLL_ID", nullable = false)
	private EmployeePayroll employeePayroll;
	
	@Column(name = "FEDERAL_TAX_FILLIING_STATUS")
	private String federalTaxFilliingStatus;
	
	@Column(name = "FEDERAL_TAX_ALLOWANCES")
	private String federalTaxAllowances;
	
	@Column(name = "FEDERAL_TAX_ADDITIONNAL_WITH_HOLDING")
	private String federalTaxAdditionnalWithHolding;
	
	@Column(name = "RESIDENCE_TAX_FILLIING_STATUS")
	private String residenceTaxFilliingStatus;

	@Column(name = "RESIDENCE_TAX_ALLOWANCES")
	private String residenceTaxAllowances;
	
	@Column(name = "RESIDENCE_TAX_ADDITIONNAL_WITH_HOLDING")
	private String residenceTaxAdditionnalWithHolding;
	
	@Column(name = "SUPPLEMENT_TYPE")
	private String supplementType;
	
	@Column(name = "FEDERAL_EXEMPT")
	private Boolean federalExempt;
	
	@Column(name = "MEDIACARE_EXEMPT")
	private Boolean mediacareExempt;
	
	@Column(name = "SOCIAL_SECURITY_EXEMPT")
	private Boolean socialSecurityExempt;
	
	@Column(name = "FUTA_EXEMPT")
	private Boolean futaExempt;
	
	@Column(name = "SUTA_EXEMPT")
	private Boolean sutaExempt;
	
	@Column(name = "LOCAL_TAX")
	private String localTax;
	
	@Column(name = "LOCAL_TAX_STATE")
	private String localTaxState;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "ASSOCIATE_TAX_HIS_UUID")
	private String associateTaxHisUuid;

	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssociateTaxId() {
		return associateTaxId;
	}

	public void setAssociateTaxId(Long associateTaxId) {
		this.associateTaxId = associateTaxId;
	}

	public EmployeePayroll getEmployeePayroll() {
		return employeePayroll;
	}

	public void setEmployeePayroll(EmployeePayroll employeePayroll) {
		this.employeePayroll = employeePayroll;
	}

	public String getFederalTaxFilliingStatus() {
		return federalTaxFilliingStatus;
	}

	public void setFederalTaxFilliingStatus(String federalTaxFilliingStatus) {
		this.federalTaxFilliingStatus = federalTaxFilliingStatus;
	}

	public String getFederalTaxAllowances() {
		return federalTaxAllowances;
	}

	public void setFederalTaxAllowances(String federalTaxAllowances) {
		this.federalTaxAllowances = federalTaxAllowances;
	}

	public String getFederalTaxAdditionnalWithHolding() {
		return federalTaxAdditionnalWithHolding;
	}

	public void setFederalTaxAdditionnalWithHolding(String federalTaxAdditionnalWithHolding) {
		this.federalTaxAdditionnalWithHolding = federalTaxAdditionnalWithHolding;
	}

	public String getResidenceTaxFilliingStatus() {
		return residenceTaxFilliingStatus;
	}

	public void setResidenceTaxFilliingStatus(String residenceTaxFilliingStatus) {
		this.residenceTaxFilliingStatus = residenceTaxFilliingStatus;
	}

	public String getResidenceTaxAllowances() {
		return residenceTaxAllowances;
	}

	public void setResidenceTaxAllowances(String residenceTaxAllowances) {
		this.residenceTaxAllowances = residenceTaxAllowances;
	}

	public String getResidenceTaxAdditionnalWithHolding() {
		return residenceTaxAdditionnalWithHolding;
	}

	public void setResidenceTaxAdditionnalWithHolding(String residenceTaxAdditionnalWithHolding) {
		this.residenceTaxAdditionnalWithHolding = residenceTaxAdditionnalWithHolding;
	}

	public String getSupplementType() {
		return supplementType;
	}

	public void setSupplementType(String supplementType) {
		this.supplementType = supplementType;
	}

	public Boolean getFederalExempt() {
		return federalExempt;
	}

	public void setFederalExempt(Boolean federalExempt) {
		this.federalExempt = federalExempt;
	}

	public Boolean getMediacareExempt() {
		return mediacareExempt;
	}

	public void setMediacareExempt(Boolean mediacareExempt) {
		this.mediacareExempt = mediacareExempt;
	}

	public Boolean getSocialSecurityExempt() {
		return socialSecurityExempt;
	}

	public void setSocialSecurityExempt(Boolean socialSecurityExempt) {
		this.socialSecurityExempt = socialSecurityExempt;
	}

	public Boolean getFutaExempt() {
		return futaExempt;
	}

	public void setFutaExempt(Boolean futaExempt) {
		this.futaExempt = futaExempt;
	}

	public Boolean getSutaExempt() {
		return sutaExempt;
	}

	public void setSutaExempt(Boolean sutaExempt) {
		this.sutaExempt = sutaExempt;
	}

	public String getLocalTax() {
		return localTax;
	}

	public void setLocalTax(String localTax) {
		this.localTax = localTax;
	}

	public String getLocalTaxState() {
		return localTaxState;
	}

	public void setLocalTaxState(String localTaxState) {
		this.localTaxState = localTaxState;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAssociateTaxHisUuid() {
		return associateTaxHisUuid;
	}

	public void setAssociateTaxHisUuid(String associateTaxHisUuid) {
		this.associateTaxHisUuid = associateTaxHisUuid;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

}
