package com.bis.sourcelead.dto;

import java.util.List;

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

		
}
