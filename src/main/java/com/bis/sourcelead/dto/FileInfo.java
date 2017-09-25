package com.bis.sourcelead.dto;

public class FileInfo {

	private String fileURL;
	private String fileName;
	private String contentType;
	private String tempFolderName;
	private String date;

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getTempFolderName()
	{
		return tempFolderName;
	}

	public void setTempFolderName(String tempFolderName)
	{
		this.tempFolderName = tempFolderName;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
