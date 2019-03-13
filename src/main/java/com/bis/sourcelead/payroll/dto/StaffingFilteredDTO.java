package com.bis.sourcelead.payroll.dto;

import org.json.simple.JSONObject;

public class StaffingFilteredDTO {
	private String categoryId;
	private String categoryName;
	private StaffingFilteredDetailsDTO details;
	private String officeCity;
	private String officeCountry;
	private String officeMetroArea;
	private String officePhone;
	private String officeState;
	private String officeStreet;
	private String officeWebsite;
	private String officeZip;
	private String vendorId;
	private String vendorName;
	private String vendorWebsite;
	
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public StaffingFilteredDetailsDTO getDetails() {
		return details;
	}
	public void setDetails(StaffingFilteredDetailsDTO details) {
		this.details = details;
	}
	public String getOfficeCity() {
		return officeCity;
	}
	public void setOfficeCity(String officeCity) {
		this.officeCity = officeCity;
	}
	public String getOfficeCountry() {
		return officeCountry;
	}
	public void setOfficeCountry(String officeCountry) {
		this.officeCountry = officeCountry;
	}
	public String getOfficeMetroArea() {
		return officeMetroArea;
	}
	public void setOfficeMetroArea(String officeMetroArea) {
		this.officeMetroArea = officeMetroArea;
	}
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	public String getOfficeState() {
		return officeState;
	}
	public void setOfficeState(String officeState) {
		this.officeState = officeState;
	}
	public String getOfficeStreet() {
		return officeStreet;
	}
	public void setOfficeStreet(String officeStreet) {
		this.officeStreet = officeStreet;
	}
	public String getOfficeWebsite() {
		return officeWebsite;
	}
	public void setOfficeWebsite(String officeWebsite) {
		this.officeWebsite = officeWebsite;
	}
	public String getOfficeZip() {
		return officeZip;
	}
	public void setOfficeZip(String officeZip) {
		this.officeZip = officeZip;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorWebsite() {
		return vendorWebsite;
	}
	public void setVendorWebsite(String vendorWebsite) {
		this.vendorWebsite = vendorWebsite;
	}
}
