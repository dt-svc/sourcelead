package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "IMMIGRATION_DASHBOARD_VIEW")
public class ImmigrationDashboardView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "IMMIGRATION_ID")
	private Long immigrationId;
	
	@Column(name = "ORG_LOCATION_ID ")
	private Long orgLocationId;

	@Column(name = "ATTORNEY_LOCATION_ID ")
	private Long attorneyLocationId;
	
	@Column(name = "DRAFT_COUNT ")
	private BigDecimal draftsCount;

	@Column(name = "REQUEST_SENT_COUNT ")
	private BigDecimal reqSentCount;

	@Column(name = "APPROVED_COUNT ")
	private BigDecimal apporovedCount;
	
	@Column(name = "REJECTED_COUNT")
	private BigDecimal rejectedCount;
	
	@Column(name = "INPROGRESS_COUNT")
	private BigDecimal inProgressCount;
	
	@Column(name = "OTHERS_COUNT")
	private BigDecimal othersCount;
	
	@Column(name = "RFE_COUNT")
	private BigDecimal rfeCount;
	
	@Column(name = "DENIED_COUNT")
	private BigDecimal deniedCount;

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public BigDecimal getDraftsCount() {
		return draftsCount;
	}

	public void setDraftsCount(BigDecimal draftsCount) {
		this.draftsCount = draftsCount;
	}

	public BigDecimal getReqSentCount() {
		return reqSentCount;
	}

	public void setReqSentCount(BigDecimal reqSentCount) {
		this.reqSentCount = reqSentCount;
	}

	public BigDecimal getApporovedCount() {
		return apporovedCount;
	}

	public void setApporovedCount(BigDecimal apporovedCount) {
		this.apporovedCount = apporovedCount;
	}

	public BigDecimal getRejectedCount() {
		return rejectedCount;
	}

	public void setRejectedCount(BigDecimal rejectedCount) {
		this.rejectedCount = rejectedCount;
	}

	public BigDecimal getInProgressCount() {
		return inProgressCount;
	}

	public void setInProgressCount(BigDecimal inProgressCount) {
		this.inProgressCount = inProgressCount;
	}

	public BigDecimal getOthersCount() {
		return othersCount;
	}

	public void setOthersCount(BigDecimal othersCount) {
		this.othersCount = othersCount;
	}

	public Long getAttorneyLocationId() {
		return attorneyLocationId;
	}

	public void setAttorneyLocationId(Long attorneyLocationId) {
		this.attorneyLocationId = attorneyLocationId;
	}

	public BigDecimal getRfeCount() {
		return rfeCount;
	}

	public void setRfeCount(BigDecimal rfeCount) {
		this.rfeCount = rfeCount;
	}

	public BigDecimal getDeniedCount() {
		return deniedCount;
	}

	public void setDeniedCount(BigDecimal deniedCount) {
		this.deniedCount = deniedCount;
	}

	public Long getImmigrationId() {
		return immigrationId;
	}

	public void setImmigrationId(Long immigrationId) {
		this.immigrationId = immigrationId;
	}

	
}
