package com.bis.sourcelead.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OFFER_ACCEPTANCE_RATE_VIEW")
public class OfferAcceptanceRateView {

	@Id
	@Column(name = "RSH_ID", nullable = false)
	private Long id;

	@Column(name = "ORG_RELATION_ID")
	private Long orgRelationId;

	@Column(name = "ORG_SPOC_ID")
	private Long orgSpocId;

	@Column(name = "OFFERMADE_COUNT")
	private BigDecimal OFFERMADE_COUNT;

	@Column(name = "OFFERACCEPTED_COUNT")
	private BigDecimal OFFERACCEPTED_COUNT;

	@Column(name = "OFFERREJECTED_COUNT")
	private BigDecimal OFFERREJECTED_COUNT;

	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;

	@Column(name = "ORG_ID")
	private Long orgId;

	@Column(name = "CREATED_YEAR")
	private String createdYear;

	@Column(name = "CREATED_YEAR_MONTH")
	private String createdYearMonth;

	@Column(name = "CREATED_YEAR_MONTH_DATE")
	private String createdYearMonthDate;

	@Column(name = "USER_CODE")
	private String userCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public Long getOrgSpocId() {
		return orgSpocId;
	}

	public void setOrgSpocId(Long orgSpocId) {
		this.orgSpocId = orgSpocId;
	}

	public BigDecimal getOFFERMADE_COUNT() {
		return OFFERMADE_COUNT;
	}

	public void setOFFERMADE_COUNT(BigDecimal oFFERMADE_COUNT) {
		OFFERMADE_COUNT = oFFERMADE_COUNT;
	}

	public BigDecimal getOFFERACCEPTED_COUNT() {
		return OFFERACCEPTED_COUNT;
	}

	public void setOFFERACCEPTED_COUNT(BigDecimal oFFERACCEPTED_COUNT) {
		OFFERACCEPTED_COUNT = oFFERACCEPTED_COUNT;
	}

	public BigDecimal getOFFERREJECTED_COUNT() {
		return OFFERREJECTED_COUNT;
	}

	public void setOFFERREJECTED_COUNT(BigDecimal oFFERREJECTED_COUNT) {
		OFFERREJECTED_COUNT = oFFERREJECTED_COUNT;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getCreatedYear() {
		return createdYear;
	}

	public void setCreatedYear(String createdYear) {
		this.createdYear = createdYear;
	}

	public String getCreatedYearMonth() {
		return createdYearMonth;
	}

	public void setCreatedYearMonth(String createdYearMonth) {
		this.createdYearMonth = createdYearMonth;
	}

	public String getCreatedYearMonthDate() {
		return createdYearMonthDate;
	}

	public void setCreatedYearMonthDate(String createdYearMonthDate) {
		this.createdYearMonthDate = createdYearMonthDate;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
