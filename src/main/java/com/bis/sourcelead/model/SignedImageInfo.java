package com.bis.sourcelead.model;

public class SignedImageInfo {
	
	
	private String userId;
	
	
	private String signatureText;
	private String initialText;
	
	private int fontId;

	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

	public String getSignatureText() {
		return signatureText;
	}

	public void setSignatureText(String signatureText) {
		this.signatureText = signatureText;
	}

	

	public int getFontId() {
		return fontId;
	}

	public void setFontId(int fontId) {
		this.fontId = fontId;
	}

	public String getInitialText() {
		return initialText;
	}

	public void setInitialText(String initialText) {
		this.initialText = initialText;
	}
	

}
