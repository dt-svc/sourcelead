package com.bis.sourcelead.entity;

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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "CANDIDATE_VISA")
public class CandidateVisa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID")
	private Candidate candidate;

	@Column(name = "VISA_NUMBER")
	private String  visaNumber;

	@Column(name = "VISA_TYPE")
	private String visaType;

	@Column(name = "COUNTRY")
	private String country;
	


	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "CREATED_DATE ")
	private Date createdDate;

	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;
	@Transient
	@JsonProperty
	private String validFromDateStr;
	
	@Transient
	@JsonProperty
	private String validToDateStr;
	
	@Transient
	@JsonProperty
	private String currentStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getValidFromDateStr() {
		return validFromDateStr;
	}

	public void setValidFromDateStr(String validFromDateStr) {
		this.validFromDateStr = validFromDateStr;
	}

	public String getValidToDateStr() {
		return validToDateStr;
	}

	public void setValidToDateStr(String validToDateStr) {
		this.validToDateStr = validToDateStr;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	
}
