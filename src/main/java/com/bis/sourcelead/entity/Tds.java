package com.bis.sourcelead.entity;

import java.io.Serializable;

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
@Table(name = "TDS")
public class Tds implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CENTRAL_TAX_ID", nullable = false)
	private CentralTax centraltaxid;
	
	@Column(name = "AGE")
	private String age;

	@Column(name = "FROM_AMOUNT")
	private Integer fromamount;
	
	@Column(name = "TO_AMOUNT")
	private Integer toamount;
	
	@Column(name = "PERCENTAGE")
	private Double percentage;
	
	@Column(name = "EDUCATION_CEISS")
	private Double educationceiss;
	
	@Column(name = "SURCHARGE")
	private Double surcharge;
	
	public Double getEducationceiss() {
		return educationceiss;
	}

	public void setEducationceiss(Double educationceiss) {
		this.educationceiss = educationceiss;
	}

	public Double getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(Double surcharge) {
		this.surcharge = surcharge;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public CentralTax getCentraltaxid() {
		return centraltaxid;
	}

	

	public Integer getFromamount() {
		return fromamount;
	}

	public Integer getToamount() {
		return toamount;
	}

	

	public void setId(long id) {
		this.id = id;
	}

	public void setCentraltaxid(CentralTax centraltaxid) {
		this.centraltaxid = centraltaxid;
	}

	

	public void setFromamount(Integer fromamount) {
		this.fromamount = fromamount;
	}

	public void setToamount(Integer toamount) {
		this.toamount = toamount;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	
	
	
	
}
