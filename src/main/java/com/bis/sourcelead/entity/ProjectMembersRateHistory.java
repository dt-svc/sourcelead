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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "PROJECT_MEMBERS_RATE_HISTORY")
public class ProjectMembersRateHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_MEMBER_ID", nullable = false)
	@JsonBackReference
	private ProjectMembers projectMemberId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	@JsonBackReference
	private Candidate candidateId;
	
	@Column(name = "BILL_RATE")
	private Double billRate;
	
	@Column(name = "BILL_RATE_CURRENCY")
	private String billRateCurrency;
	
	@Column(name = "BILL_RATE_TYPE")
	private String billRateType;
	
	@Column(name = "PAY_RATE")
	private Double payRate;
	
	@Column(name = "PAY_RATE_CURRENCY")
	private String payRateCurrency;
	
	@Column(name = "PAY_RATE_TYPE")
	private String payRateType;
	
	@Column(name = "PAY_RATE_START_DATE")
	private Date payRateStartDate;
	
	@Column(name = "PAY_RATE_END_DATE")
	private Date payRateEndDate;
	
	@Column(name = "BILL_RATE_START_DATE")
	private Date billRateStartDate;
	
	@Column(name = "BILL_RATE_END_DATE")
	private Date billRateEndDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RATE_HISTORY_ID", nullable = false)
	@JsonBackReference
	private ProjectMembersRateHistory rateHistoryId;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY", nullable = false)
	@JsonBackReference
	private User createdBy;
	
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

	public ProjectMembers getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(ProjectMembers projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public Double getBillRate() {
		return billRate;
	}

	public void setBillRate(Double billRate) {
		this.billRate = billRate;
	}

	public String getBillRateCurrency() {
		return billRateCurrency;
	}

	public void setBillRateCurrency(String billRateCurrency) {
		this.billRateCurrency = billRateCurrency;
	}

	public String getBillRateType() {
		return billRateType;
	}

	public void setBillRateType(String billRateType) {
		this.billRateType = billRateType;
	}

	public Double getPayRate() {
		return payRate;
	}

	public void setPayRate(Double payRate) {
		this.payRate = payRate;
	}

	public String getPayRateCurrency() {
		return payRateCurrency;
	}

	public void setPayRateCurrency(String payRateCurrency) {
		this.payRateCurrency = payRateCurrency;
	}

	public String getPayRateType() {
		return payRateType;
	}

	public void setPayRateType(String payRateType) {
		this.payRateType = payRateType;
	}

	public Date getPayRateStartDate() {
		return payRateStartDate;
	}

	public void setPayRateStartDate(Date payRateStartDate) {
		this.payRateStartDate = payRateStartDate;
	}

	public Date getPayRateEndDate() {
		return payRateEndDate;
	}

	public void setPayRateEndDate(Date payRateEndDate) {
		this.payRateEndDate = payRateEndDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
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

	public Date getBillRateStartDate() {
		return billRateStartDate;
	}

	public void setBillRateStartDate(Date billRateStartDate) {
		this.billRateStartDate = billRateStartDate;
	}

	public Date getBillRateEndDate() {
		return billRateEndDate;
	}

	public void setBillRateEndDate(Date billRateEndDate) {
		this.billRateEndDate = billRateEndDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ProjectMembersRateHistory getRateHistoryId() {
		return rateHistoryId;
	}

	public void setRateHistoryId(ProjectMembersRateHistory rateHistoryId) {
		this.rateHistoryId = rateHistoryId;
	}
	
}
