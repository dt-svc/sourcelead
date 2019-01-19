package com.bis.sourcelead.model;

import java.util.List;

public class CoordinatesInfo {
	private List<Coordinates> signatures;
	
	private List<Coordinates> initials;
	
	private String userId;
	private String docId;
	private int fontId;
	
	private float signatureWidth;
	private float signatureHeight;
	private float initialsWidth;
	private float initialsHeight;
	
	private int pageCount;
	
	public List<Coordinates> getSignatures() {
		return signatures;
	}
	public void setSignatures(List<Coordinates> signatures) {
		this.signatures = signatures;
	}
	public List<Coordinates> getInitials() {
		return initials;
	}
	public void setInitials(List<Coordinates> initials) {
		this.initials = initials;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public float getSignatureWidth() {
		//return (float) (signatureWidth * 0.75);
		return (float)signatureWidth;
	}
	public void setSignatureWidth(float signatureWidth) {
		this.signatureWidth = signatureWidth;
	}
	public float getSignatureHeight() {
		//return (float)(signatureHeight * 0.75);
		return (float)signatureHeight;
	}
	public void setSignatureHeight(float signatureHeight) {
		this.signatureHeight = signatureHeight;
	}
	public float getInitialsWidth() {
		//return (float)(initialsWidth * 0.75);
		return (float)initialsWidth;
	}
	public void setInitialsWidth(float initialsWidth) {
		this.initialsWidth = initialsWidth;
	}
	public float getInitialsHeight() {
		//return (float)(initialsHeight * 0.75);
		return (float)initialsHeight;
	}
	public void setInitialsHeight(float initialsHeight) {
		this.initialsHeight = initialsHeight;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public int getFontId() {
		return fontId;
	}
	public void setFontId(int fontId) {
		this.fontId = fontId;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	
}
