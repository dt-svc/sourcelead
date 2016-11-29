package com.bis.sourcelead.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "cities")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="cities")
public class Cities implements Serializable{

	
	private static final long serialVersionUID = 1L;


	@Id
    @Column(name = "countryCode", nullable = false)
	private String countryCode;
	
	
	@Column(name = "stateCode")
	private String stateCode;
	
	@Column(name = "cityName")
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
