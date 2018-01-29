package com.bis.sourcelead.payroll.entity;




import java.io.Serializable;
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

import com.bis.sourcelead.entity.OrgLocations;

@Entity
@Table(name = "HR_DEDUCTIONS_DETAILS")
public class HrDeductionsDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HR_DEDUCTIONS_ID", nullable = false)
	private HrDeductions hrDeductions;
	
	@Column(name = "PAY_CYCLE_NAME")
	private String payCycleName;

	@Column(name = "DED_AMOUNT_PER_CYCLE")
	private float dedAmountPerCycle;
	
	@Column(name = "PAID_STATUS")
	private String paidStatus;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
	private String description;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private String fromDateStr;
	
	@Transient
	private String toDateStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public HrDeductions getHrDeductions() {
		return hrDeductions;
	}

	public void setHrDeductions(HrDeductions hrDeductions) {
		this.hrDeductions = hrDeductions;
	}

	public String getPayCycleName() {
		return payCycleName;
	}

	public void setPayCycleName(String payCycleName) {
		this.payCycleName = payCycleName;
	}

	public float getDedAmountPerCycle() {
		return dedAmountPerCycle;
	}

	public void setDedAmountPerCycle(float dedAmountPerCycle) {
		this.dedAmountPerCycle = dedAmountPerCycle;
	}

	public String getPaidStatus() {
		return paidStatus;
	}

	public void setPaidStatus(String paidStatus) {
		this.paidStatus = paidStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getFromDateStr() {
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
