package com.bis.sourcelead.dto;

import java.util.List;

public class ApplicantAuthorizationDTO {

private String comment;

private String staticPath;

private String rejectedCategory;

private String rejectedCategoryOther;

private List<FileInfo> fileInfo;

public String getComment() {
	return comment;
}

public void setComment(String comment) {
	this.comment = comment;
}

public String getRejectedCategory() {
	return rejectedCategory;
}

public void setRejectedCategory(String rejectedCategory) {
	this.rejectedCategory = rejectedCategory;
}

public String getRejectedCategoryOther() {
	return rejectedCategoryOther;
}

public void setRejectedCategoryOther(String rejectedCategoryOther) {
	this.rejectedCategoryOther = rejectedCategoryOther;
}

public List<FileInfo> getFileInfo() {
	return fileInfo;
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
