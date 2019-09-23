package com.bis.sourcelead.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "TIMEZONE")
public class Timezone implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "COUNTRY_CODE")
	private String countryCode;
	
	@Column(name = "TIMEZONE_ID")
	private String timeZoneId;
	
	@Column(name = "GMTOFFSET")
	private Float gmtOffSet;
	
	@Column(name = "DSTOFFSET")
	private Float dstOffest;
	
	@Column(name = "RAWOFFSET")
	private Float rawOffset;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public Float getGmtOffSet() {
		return gmtOffSet;
	}

	public void setGmtOffSet(Float gmtOffSet) {
		this.gmtOffSet = gmtOffSet;
	}

	public Float getDstOffest() {
		return dstOffest;
	}

	public void setDstOffest(Float dstOffest) {
		this.dstOffest = dstOffest;
	}

	public Float getRawOffset() {
		return rawOffset;
	}

	public void setRawOffset(Float rawOffset) {
		this.rawOffset = rawOffset;
	}

	
	
	
	
}
