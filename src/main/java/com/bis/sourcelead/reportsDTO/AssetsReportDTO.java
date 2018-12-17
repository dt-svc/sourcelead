package com.bis.sourcelead.reportsDTO;

import java.util.Date;
import java.util.List;

public class AssetsReportDTO {

	private String employeeId;
	private String employeeName;
	private String category;
	private String assignedOn;
	private String returnedOn;
	private String shift;
	private String document;
	private List<Long> assetNames;
	private List<Long> categories;
	private List<Long> shiftsIds;
	private String fromDate;
	private String toDate;
	private int dateType;
	private Date convertedFromDate;
	private Date convertedToDate;
	private String type;
	private String serialNo;
	private String assetName;
	private String assignBy;
	private String fileUrl;
	private String fileContentType;
	
	
	
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getConvertedFromDate() {
		return convertedFromDate;
	}
	public void setConvertedFromDate(Date convertedFromDate) {
		this.convertedFromDate = convertedFromDate;
	}
	public Date getConvertedToDate() {
		return convertedToDate;
	}
	public void setConvertedToDate(Date convertedToDate) {
		this.convertedToDate = convertedToDate;
	}
	public int getDateType() {
		return dateType;
	}
	public void setDateType(int dateType) {
		this.dateType = dateType;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getAssignedOn() {
		return assignedOn;
	}
	public void setAssignedOn(String assignedOn) {
		this.assignedOn = assignedOn;
	}
	public String getReturnedOn() {
		return returnedOn;
	}
	public void setReturnedOn(String returnedOn) {
		this.returnedOn = returnedOn;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}


	public List<Long> getAssetNames() {
		return assetNames;
	}
	public void setAssetNames(List<Long> assetNames) {
		this.assetNames = assetNames;
	}
	public List<Long> getCategories() {
		return categories;
	}
	public void setCategories(List<Long> categories) {
		this.categories = categories;
	}
	public List<Long> getShiftsIds() {
		return shiftsIds;
	}
	public void setShiftsIds(List<Long> shiftsIds) {
		this.shiftsIds = shiftsIds;
	}
	public String getAssignBy() {
		return assignBy;
	}
	public void setAssignBy(String assignBy) {
		this.assignBy = assignBy;
	}
	
}
