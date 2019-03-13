package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class AssetAssignDTO extends SearchDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	    private Long id;
	    
	    private String shift;
	
		private String shiftEmp;
		
		private String xlCol;
		 
		private List<FileInfo> fileInfo;

		private String staticPath;
		
		private String assignedOn;
		
		private String returnedOn;
		
		public String getShift() {
			return shift;
		}

		public void setShift(String shift) {
			this.shift = shift;
		}

		public String getShiftEmp() {
			return shiftEmp;
		}

		public List<FileInfo> getFileInfo() {
			return fileInfo;
		}

		public void setShiftEmp(String shiftEmp) {
			this.shiftEmp = shiftEmp;
		}

		public void setFileInfo(List<FileInfo> fileInfo) {
			this.fileInfo = fileInfo;
		}

		public String getStaticPath() {
			return staticPath;
		}

		public void setStaticPath(String staticPath) {
			this.staticPath = staticPath;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getXlCol() {
			return xlCol;
		}

		public void setXlCol(String xlCol) {
			this.xlCol = xlCol;
		}

		public String getAssignedOn() {
			return assignedOn;
		}

		public String getReturnedOn() {
			return returnedOn;
		}

		public void setAssignedOn(String assignedOn) {
			this.assignedOn = assignedOn;
		}

		public void setReturnedOn(String returnedOn) {
			this.returnedOn = returnedOn;
		}

}
