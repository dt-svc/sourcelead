package com.bis.sourcelead.dto;

import java.util.List;


public class AssetsDataImportDTO {
    private String sno;
	
	private String make;
	
	private String model;
	
	private String name;
	
	private String memory;
	
	private String cpu;
	
	private String harddisk;
	
	private String monitor;
	
	private String category;
	
	private String workingStatus;
	
	private String shiftAEmp;
	
	private String shiftBEmp;
	
	private String description;
	
	private List<FileInfo> attachmentUpload;
	
	private String fileUrl;
	
	private String categoryName;
	
	private String categoryId;
	
	private String shiftAEmployee;
	
	private String shiftBEmployee;

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getHarddisk() {
		return harddisk;
	}

	public void setHarddisk(String harddisk) {
		this.harddisk = harddisk;
	}

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(String workingStatus) {
		this.workingStatus = workingStatus;
	}

	public String getShiftAEmp() {
		return shiftAEmp;
	}

	public void setShiftAEmp(String shiftAEmp) {
		this.shiftAEmp = shiftAEmp;
	}

	public String getShiftBEmp() {
		return shiftBEmp;
	}

	public void setShiftBEmp(String shiftBEmp) {
		this.shiftBEmp = shiftBEmp;
	}

	public List<FileInfo> getAttachmentUpload() {
		return attachmentUpload;
	}

	public void setAttachmentUpload(List<FileInfo> attachmentUpload) {
		this.attachmentUpload = attachmentUpload;
	}

	 
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
  

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getShiftAEmployee() {
		return shiftAEmployee;
	}

	public void setShiftAEmployee(String shiftAEmployee) {
		this.shiftAEmployee = shiftAEmployee;
	}

	public String getShiftBEmployee() {
		return shiftBEmployee;
	}

	public void setShiftBEmployee(String shiftBEmployee) {
		this.shiftBEmployee = shiftBEmployee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
