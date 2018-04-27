package com.bis.sourcelead.model;

public class SignedDocumentInfo {
	private CoordinatesInfo coordinatesInfo;
	private String fileName;
	private int fontId;
	private String signatureText;
	private String initialsText;

	public CoordinatesInfo getCoordinatesInfo() {
		return coordinatesInfo;
	}

	public void setCoordinatesInfo(CoordinatesInfo coordinatesInfo) {
		this.coordinatesInfo = coordinatesInfo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public int getFontId() {
		return fontId;
	}

	public void setFontId(int fontId) {
		this.fontId = fontId;
	}

	public String getSignatureText() {
		return signatureText;
	}

	public void setSignatureText(String signatureText) {
		this.signatureText = signatureText;
	}

	public String getInitialsText() {
		return initialsText;
	}

	public void setInitialsText(String initialsText) {
		this.initialsText = initialsText;
	}
	

}
