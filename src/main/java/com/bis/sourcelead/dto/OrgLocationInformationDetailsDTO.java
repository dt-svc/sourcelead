package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.annotations.Required;

public class OrgLocationInformationDetailsDTO implements Serializable{

    private static final long serialVersionUID = 1L;
	
	private long id;
	
	@Required(desc="cin")
	private String cin;
	
	@Required(desc="gstin")
	private String gstin;
	
	@Required(desc="incorporationDate")
	private String incorporationDate;
	
	@Required(desc="pan")
	private String pan;
	
	@Required(desc="tan")
	private String tan;
	
	@Required(desc="tds")
	private String tds;
	
	@Required(desc="pfNumber")
	private String pfNumber;
	
	@Required(desc="pfRegistrationDate")
	private String pfregistrationDate;
	
	@Required(desc="esiNumber")
	private String esiNumber;
	
	@Required(desc="esiRegistrationDate")
	private String esiregistrationDate;
	
	@Required(desc="ptNumber")
	private String ptNumber;
	
	@Required(desc="ptregistrationDate")
	private String ptregistrationDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getIncorporationDate() {
		return incorporationDate;
	}

	public void setIncorporationDate(String incorporationDate) {
		this.incorporationDate = incorporationDate;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getTan() {
		return tan;
	}

	public void setTan(String tan) {
		this.tan = tan;
	}

	public String getTds() {
		return tds;
	}

	public void setTds(String tds) {
		this.tds = tds;
	}

	public String getPfNumber() {
		return pfNumber;
	}

	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}

	public String getPfregistrationDate() {
		return pfregistrationDate;
	}

	public void setPfregistrationDate(String pfregistrationDate) {
		this.pfregistrationDate = pfregistrationDate;
	}

	public String getEsiNumber() {
		return esiNumber;
	}

	public void setEsiNumber(String esiNumber) {
		this.esiNumber = esiNumber;
	}

	public String getEsiregistrationDate() {
		return esiregistrationDate;
	}

	public void setEsiregistrationDate(String esiregistrationDate) {
		this.esiregistrationDate = esiregistrationDate;
	}

	public String getPtNumber() {
		return ptNumber;
	}

	public void setPtNumber(String ptNumber) {
		this.ptNumber = ptNumber;
	}

	public String getPtregistrationDate() {
		return ptregistrationDate;
	}

	public void setPtregistrationDate(String ptregistrationDate) {
		this.ptregistrationDate = ptregistrationDate;
	}
	

	
}
