package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class AssetAssignDTO extends SearchDTO implements Serializable {

	private static final long serialVersionUID = 1L;

		private String shiftEmp;
		 
		private List<FileInfo> fileInfo;

		private String staticPath;
		
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

}
