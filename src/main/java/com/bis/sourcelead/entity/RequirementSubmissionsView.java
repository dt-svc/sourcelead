package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

//Dashboard realated properties added on 13/08/2018
@Entity
@Table(name = "CANDIDATE_ONBAORDING_VIEW", schema = "SOURCELEAD")
public class RequirementSubmissionsView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OFFER_ID")
	private Long offerId;

	@Column(name = "CANDIDATE_ID")
	private Long candidateId;
	
	
	@Column(name = "CANDIDATE_NAME")
	private String candidateName;
	
	@Column(name = "CANDIDATE_EMAIL")
	private String candidateEmail;
	
	@Column(name = "REQUIREMENT_NAME")
	private String requirementName;
	
	@Column(name = "CANDIDATE_UUID")
	private String candidateUUID;
	
	@Column(name = "REQUIREMENT_UUID")
	private String requirementUUID;
	
	@Column(name = "REQ_MANAGERID")
	private Long reqManagerId;
	
	@Column(name = "REQRELEASE_ALLJOB_USERSTATUS")
	private String reqReleaseAllJobUserStatus;
	
	@Column(name = "OFFER_JOIN_DATE")
	private String offerJoinDate;
	
	@Column(name = "POSITION_TYPE")
	private String positionType;
	
	@Column(name = "RATE_TYPE")
	private String rateType;
	
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	
	@Column(name = "OFFER_UUID")
	private String offerUUID;

	@Column(name = "ORGLOCATION_ID")
	private Long orgLocationId;
	
	@Column(name = "RATE")
	private String rate;
	
	@Transient
	private String onboardUUId;
	
	@Transient
	private String onboardStatus;
	
	


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Long getCandidateId() {
		return candidateId;
	}


	public String getCandidateName() {
		return candidateName;
	}


	public String getCandidateEmail() {
		return candidateEmail;
	}


	public String getRequirementName() {
		return requirementName;
	}


	public String getCandidateUUID() {
		return candidateUUID;
	}


	public String getRequirementUUID() {
		return requirementUUID;
	}


	public Long getReqManagerId() {
		return reqManagerId;
	}


	public String getReqReleaseAllJobUserStatus() {
		return reqReleaseAllJobUserStatus;
	}


	public String getOfferJoinDate() {
		return offerJoinDate;
	}


	public String getPositionType() {
		return positionType;
	}


	public String getRateType() {
		return rateType;
	}


	public String getPaymentType() {
		return paymentType;
	}


	public String getOfferUUID() {
		return offerUUID;
	}


	public Long getOfferId() {
		return offerId;
	}


	public Long getOrgLocationId() {
		return orgLocationId;
	}


	public String getRate() {
		return rate;
	}


	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}


	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}


	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}


	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}


	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}


	public void setRequirementUUID(String requirementUUID) {
		this.requirementUUID = requirementUUID;
	}


	public void setReqManagerId(Long reqManagerId) {
		this.reqManagerId = reqManagerId;
	}


	public void setReqReleaseAllJobUserStatus(String reqReleaseAllJobUserStatus) {
		this.reqReleaseAllJobUserStatus = reqReleaseAllJobUserStatus;
	}


	public void setOfferJoinDate(String offerJoinDate) {
		this.offerJoinDate = offerJoinDate;
	}


	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}


	public void setRateType(String rateType) {
		this.rateType = rateType;
	}


	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


	public void setOfferUUID(String offerUUID) {
		this.offerUUID = offerUUID;
	}


	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}


	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}


	public void setRate(String rate) {
		this.rate = rate;
	}


	public String getOnboardUUId() {
		return onboardUUId;
	}


	public String getOnboardStatus() {
		return onboardStatus;
	}


	public void setOnboardUUId(String onboardUUId) {
		this.onboardUUId = onboardUUId;
	}


	public void setOnboardStatus(String onboardStatus) {
		this.onboardStatus = onboardStatus;
	}
	 
}
