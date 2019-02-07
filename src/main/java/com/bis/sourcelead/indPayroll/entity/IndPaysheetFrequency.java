package com.bis.sourcelead.indPayroll.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserGroup;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "IND_PAY_SHEET_FREQUENCY")
public class IndPaysheetFrequency {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column (name="PAY_RUN_ID")
	private Long payRunId;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocation;
	
	@ManyToOne
	@JoinColumn(name = "GROUP_ID", nullable = false)
	private UserGroup userGroup;
	
	@Column(name = "FREQUENCY_START_DATE")
	private Date frequencyStartDate;
	
	@Column(name = "FREQUENCY_END_DATE")
	private Date frequencyEndDate;
	
	@Column (name = "PAY_CHECK_DATE")
	private Date payCheckDate;
	
	@Column (name = "PAY_SHEET_FREQUENCY_UUID")
	private String paySheetFrequencyUUID;
	
	@Column(name = "ACTIONPERFORMEDBY")
	@Type(type="text")
	private String actionPerformedBy;
	
	@Column (name="APPROVER_STATUS")
	private String approverStatus;
	
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
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "indPaysheetFrequency")
	private Set<IndEmployeePayroll> indEmployeepayroll;
	
	@Transient
	@JsonProperty
    private String frequencyStartDateStr;
	
	@Transient
	@JsonProperty
    private String frequencyEndDateStr;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocation() {
		return orgLocation;
	}

	public void setOrgLocation(OrgLocations orgLocation) {
		this.orgLocation = orgLocation;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public Date getFrequencyStartDate() {
		return frequencyStartDate;
	}

	public void setFrequencyStartDate(Date frequencyStartDate) {
		this.frequencyStartDate = frequencyStartDate;
	}

	public Date getFrequencyEndDate() {
		return frequencyEndDate;
	}

	public void setFrequencyEndDate(Date frequencyEndDate) {
		this.frequencyEndDate = frequencyEndDate;
	}

	public Date getPayCheckDate() {
		return payCheckDate;
	}

	public void setPayCheckDate(Date payCheckDate) {
		this.payCheckDate = payCheckDate;
	}

	public String getPaySheetFrequencyUUID() {
		return paySheetFrequencyUUID;
	}

	public void setPaySheetFrequencyUUID(String paySheetFrequencyUUID) {
		this.paySheetFrequencyUUID = paySheetFrequencyUUID;
	}

	public String getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Set<IndEmployeePayroll> getIndEmployeepayroll() {
		return indEmployeepayroll;
	}

	public void setIndEmployeepayroll(Set<IndEmployeePayroll> indEmployeepayroll) {
		this.indEmployeepayroll = indEmployeepayroll;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getActionPerformedBy() {
		return actionPerformedBy;
	}

	public void setActionPerformedBy(String actionPerformedBy) {
		this.actionPerformedBy = actionPerformedBy;
	}

	public Long getPayRunId() {
		return payRunId;
	}

	public void setPayRunId(Long payRunId) {
		this.payRunId = payRunId;
	}

	public String getFrequencyStartDateStr() {
		return frequencyStartDateStr;
	}

	public String getFrequencyEndDateStr() {
		return frequencyEndDateStr;
	}

	public void setFrequencyStartDateStr(String frequencyStartDateStr) {
		this.frequencyStartDateStr = frequencyStartDateStr;
	}

	public void setFrequencyEndDateStr(String frequencyEndDateStr) {
		this.frequencyEndDateStr = frequencyEndDateStr;
	}
}
