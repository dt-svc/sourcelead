package com.bis.sourcelead.dto;

public class AttachmentDTO {

	private String fileName;
	private String url;
	private String path;
	private String fileType;
	private String fileExtension;
	private float fileSize;
	private String sizeUnit;
	private byte[] docFile;
	private boolean newUpload;
	private boolean attachmentNotFoundOrCorrupted;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public float getFileSize() {
		return fileSize;
	}

	public void setFileSize(float fileSize) {
		this.fileSize = fileSize;
	}

	public String getSizeUnit() {
		return sizeUnit;
	}

	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
	}

	public boolean isNewUpload() {
		return newUpload;
	}

	public void setNewUpload(boolean newUpload) {
		this.newUpload = newUpload;
	}

	public boolean isAttachmentNotFoundOrCorrupted() {
		return attachmentNotFoundOrCorrupted;
	}

	public void setAttachmentNotFoundOrCorrupted(boolean attachmentNotFoundOrCorrupted) {
		this.attachmentNotFoundOrCorrupted = attachmentNotFoundOrCorrupted;
	}

}
