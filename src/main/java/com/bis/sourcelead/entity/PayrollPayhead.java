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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PAYROLL_PAYHEAD")
public class PayrollPayhead {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SHORT_CODE")
	private String shortCode;
	
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@Column(name = "TYPE")
	private String type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "PAY_TYPE")
    private PayrollPaytype payType;
	
	@Column(name = "FORM_16")
	private String form16;
	
	@Column(name = "TAX")
	private String tax;
	
	@Column(name = "APPLICABLE_FOR")
	private String applicableFor;
	
	@Column(name = "EXPERIENCE_YEARS")
	private String experienceYears;
	
	@Column(name = "EXPERIENCE_MONTHS")
	private String experienceMonths;
	
	@Column(name = "COMPONENT")
	private String component;
	
	@Column(name = "PAID_COMPONENT")
	private String paidComponent;
	
	@Column(name = "PART_OF_GRATITUTIY")
	private String partOfGratitutiy;
	
	@Column(name = "FBP_COMPONENT")
	private String fbpComponent;
	
	@Column(name = "LEAVE_DEPENDENT")
	private String leaveDependent;
	
	@Column(name = "SHOW_IN_PAYSLIP")
	private String showInPayslip;
	
	@Column(name = "PF_APPLICABLE")
	private String pfApplicable;
	
	@Column(name = "ESI_APPLICABLE")
	private String esiApplicable;
	
	@Column(name = "PT_APPLICABLE")
	private String ptApplicable;

	@Column(name = "METHOD")
	private String method;
	
	@Column(name = "FORMULA", columnDefinition = "TEXT default NULL")
	private String formula;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "ORGLOCATION_ID")
    private OrgLocations orgLocationId;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "ACTIVE")
	private String active;
	
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
    
    @Transient
    @JsonProperty
    private String effectiveDateStr;
    
    @Transient
    @JsonProperty
    private String isFormulaEditable;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PayrollPaytype getPayType() {
		return payType;
	}

	public void setPayType(PayrollPaytype payType) {
		this.payType = payType;
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

	public String getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(String experienceYears) {
		this.experienceYears = experienceYears;
	}

	public String getExperienceMonths() {
		return experienceMonths;
	}

	public void setExperienceMonths(String experienceMonths) {
		this.experienceMonths = experienceMonths;
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

	public String getPartOfGratitutiy() {
		return partOfGratitutiy;
	}

	public void setPartOfGratitutiy(String partOfGratitutiy) {
		this.partOfGratitutiy = partOfGratitutiy;
	}

	public String getFbpComponent() {
		return fbpComponent;
	}

	public void setFbpComponent(String fbpComponent) {
		this.fbpComponent = fbpComponent;
	}

	public String getLeaveDependent() {
		return leaveDependent;
	}

	public void setLeaveDependent(String leaveDependent) {
		this.leaveDependent = leaveDependent;
	}

	public String getShowInPayslip() {
		return showInPayslip;
	}

	public void setShowInPayslip(String showInPayslip) {
		this.showInPayslip = showInPayslip;
	}

	public String getPfApplicable() {
		return pfApplicable;
	}

	public void setPfApplicable(String pfApplicable) {
		this.pfApplicable = pfApplicable;
	}

	public String getEsiApplicable() {
		return esiApplicable;
	}

	public void setEsiApplicable(String esiApplicable) {
		this.esiApplicable = esiApplicable;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public String getEffectiveDateStr() {
		return effectiveDateStr;
	}

	public void setEffectiveDateStr(String effectiveDateStr) {
		this.effectiveDateStr = effectiveDateStr;
	}

	public String getIsFormulaEditable() {
		return isFormulaEditable;
	}

	public void setIsFormulaEditable(String isFormulaEditable) {
		this.isFormulaEditable = isFormulaEditable;
	}
	
	public String getPtApplicable() {
		return ptApplicable;
	}

	public void setPtApplicable(String ptApplicable) {
		this.ptApplicable = ptApplicable;
	}
}
