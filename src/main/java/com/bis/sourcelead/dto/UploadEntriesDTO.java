package com.bis.sourcelead.dto;

import java.io.Serializable;

public class UploadEntriesDTO implements Serializable{

	    private static final long serialVersionUID = 1L;

	    private String uploadType;
	    
	    private String exportFilePath;
	    
	    private String headerPosition;
	    
	    private String importStatus;
	    
	    

		public String getUploadType() {
			return uploadType;
		}

		public void setUploadType(String uploadType) {
			this.uploadType = uploadType;
		}

		public String getExportFilePath() {
			return exportFilePath;
		}

		public void setExportFilePath(String exportFilePath) {
			this.exportFilePath = exportFilePath;
		}

		public String getHeaderPosition() {
			return headerPosition;
		}

		public void setHeaderPosition(String headerPosition) {
			this.headerPosition = headerPosition;
		}

		public String getImportStatus() {
			return importStatus;
		}

		public void setImportStatus(String importStatus) {
			this.importStatus = importStatus;
		}
}
