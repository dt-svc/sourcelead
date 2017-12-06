package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.entity.Employees;

public class AssetDTO extends CommonDTO {

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
		
		private String staticPath;
		
		private List<FileInfo> attachmentUpload;
		
		private List<FileInfo> uploadBtndskAUpload;
		
		private List<FileInfo> uploadBtndskBUpload;
		
		private String fileName;
		
		private String fileUrl;
		
		private String fileContentType;
		
		private String shiftAFileContentType;
		
		private String shiftAFileUrl;
		
		private String shiftAFileName;
		
		private String shiftBFileContentType;
		
		private String shiftBFileUrl;
		
		private String shiftBFileName;
		
		private List<Employees> employeesList;
		
		private String shiftAEmpName;
		
		private String shiftBEmpName;
		
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

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getStaticPath() {
			return staticPath;
		}

		public void setStaticPath(String staticPath) {
			this.staticPath = staticPath;
		}

		public List<FileInfo> getAttachmentUpload() {
			return attachmentUpload;
		}

		public void setAttachmentUpload(List<FileInfo> attachmentUpload) {
			this.attachmentUpload = attachmentUpload;
		}

		public List<FileInfo> getUploadBtndskAUpload() {
			return uploadBtndskAUpload;
		}

		public void setUploadBtndskAUpload(List<FileInfo> uploadBtndskAUpload) {
			this.uploadBtndskAUpload = uploadBtndskAUpload;
		}

		public List<FileInfo> getUploadBtndskBUpload() {
			return uploadBtndskBUpload;
		}

		public void setUploadBtndskBUpload(List<FileInfo> uploadBtndskBUpload) {
			this.uploadBtndskBUpload = uploadBtndskBUpload;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileUrl() {
			return fileUrl;
		}

		public void setFileUrl(String fileUrl) {
			this.fileUrl = fileUrl;
		}

		public String getFileContentType() {
			return fileContentType;
		}

		public void setFileContentType(String fileContentType) {
			this.fileContentType = fileContentType;
		}

		public String getShiftAFileContentType() {
			return shiftAFileContentType;
		}

		public void setShiftAFileContentType(String shiftAFileContentType) {
			this.shiftAFileContentType = shiftAFileContentType;
		}

		public String getShiftAFileUrl() {
			return shiftAFileUrl;
		}

		public void setShiftAFileUrl(String shiftAFileUrl) {
			this.shiftAFileUrl = shiftAFileUrl;
		}

		public String getShiftAFileName() {
			return shiftAFileName;
		}

		public void setShiftAFileName(String shiftAFileName) {
			this.shiftAFileName = shiftAFileName;
		}

		public String getShiftBFileContentType() {
			return shiftBFileContentType;
		}

		public void setShiftBFileContentType(String shiftBFileContentType) {
			this.shiftBFileContentType = shiftBFileContentType;
		}

		public String getShiftBFileUrl() {
			return shiftBFileUrl;
		}

		public void setShiftBFileUrl(String shiftBFileUrl) {
			this.shiftBFileUrl = shiftBFileUrl;
		}

		public String getShiftBFileName() {
			return shiftBFileName;
		}

		public void setShiftBFileName(String shiftBFileName) {
			this.shiftBFileName = shiftBFileName;
		}

		public List<Employees> getEmployeesList() {
			return employeesList;
		}

		public void setEmployeesList(List<Employees> employeesList) {
			this.employeesList = employeesList;
		}

		public String getShiftAEmpName() {
			return shiftAEmpName;
		}

		public void setShiftAEmpName(String shiftAEmpName) {
			this.shiftAEmpName = shiftAEmpName;
		}

		public String getShiftBEmpName() {
			return shiftBEmpName;
		}

		public void setShiftBEmpName(String shiftBEmpName) {
			this.shiftBEmpName = shiftBEmpName;
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

		
}
