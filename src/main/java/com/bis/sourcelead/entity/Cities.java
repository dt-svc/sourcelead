package com.bis.sourcelead.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "CITIES")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Cities {

	@Id
    @Column(name = "COUNTRY_CODE", nullable = false)
	private String countryCode;
	
	
	@Column(name = "STATE_CODE")
	private String stateCode;
	
	@Column(name = "CITY_NAME")
	private String cityName;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
	
	
}
