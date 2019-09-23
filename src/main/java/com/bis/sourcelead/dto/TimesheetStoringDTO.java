package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;


public class TimesheetStoringDTO implements Serializable {
 private static final long serialVersionUID = 1L;

 private String json;
 
 private String status;
 
 private String savingType;
 
 private List<FileInfo> fileInfo;

public String getJson() {
	return json;
}

public void setJson(String json) {
	this.json = json;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getSavingType() {
	return savingType;
}

public void setSavingType(String savingType) {
	this.savingType = savingType;
}

public List<FileInfo> getFileInfo() {
	return fileInfo;
}

public void setFileInfo(List<FileInfo> fileInfo) {
	this.fileInfo = fileInfo;
}


 

}
 