package com.bis.sourcelead.dto;

import java.util.List;

public class LetterHeadDTO extends SearchDTO {

	private String letterHeadName;
	private String letterHeadDescription;
	private String letterHeadUUID;
	private String templateBackgroundColor;
	private String templateType;
	private String paddingLeft;
	private String paddingRight;
	private String paddingTop;
	private String paddingBottom;
	private String alignLogo;
	private String headerBackgroundColor;
	private String headerText;
	private String headerTextAlign;
	private String headerTextColor;
	private String footerText;
	private String alignFooter;
	private String footerBackgroundColor;
	private List<FileInfo> fileInfo;
	
	public String getTemplateBackgroundColor() {
		return templateBackgroundColor;
	}
	public void setTemplateBackgroundColor(String templateBackgroundColor) {
		this.templateBackgroundColor = templateBackgroundColor;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getLetterHeadUUID() {
		return letterHeadUUID;
	}
	public void setLetterHeadUUID(String letterHeadUUID) {
		this.letterHeadUUID = letterHeadUUID;
	}
	public String getPaddingLeft() {
		return paddingLeft;
	}
	public void setPaddingLeft(String paddingLeft) {
		this.paddingLeft = paddingLeft;
	}
	public String getPaddingRight() {
		return paddingRight;
	}
	public void setPaddingRight(String paddingRight) {
		this.paddingRight = paddingRight;
	}
	public String getPaddingTop() {
		return paddingTop;
	}
	public void setPaddingTop(String paddingTop) {
		this.paddingTop = paddingTop;
	}
	public String getPaddingBottom() {
		return paddingBottom;
	}
	public void setPaddingBottom(String paddingBottom) {
		this.paddingBottom = paddingBottom;
	}
	public String getAlignLogo() {
		return alignLogo;
	}
	public void setAlignLogo(String alignLogo) {
		this.alignLogo = alignLogo;
	}
	public String getHeaderBackgroundColor() {
		return headerBackgroundColor;
	}
	public void setHeaderBackgroundColor(String headerBackgroundColor) {
		this.headerBackgroundColor = headerBackgroundColor;
	}
	public String getHeaderText() {
		return headerText;
	}
	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}
	public String getFooterText() {
		return footerText;
	}
	public void setFooterText(String footerText) {
		this.footerText = footerText;
	}
	public String getAlignFooter() {
		return alignFooter;
	}
	public void setAlignFooter(String alignFooter) {
		this.alignFooter = alignFooter;
	}
	public String getFooterBackgroundColor() {
		return footerBackgroundColor;
	}
	public void setFooterBackgroundColor(String footerBackgroundColor) {
		this.footerBackgroundColor = footerBackgroundColor;
	}
	public String getHeaderTextAlign() {
		return headerTextAlign;
	}
	public void setHeaderTextAlign(String headerTextAlign) {
		this.headerTextAlign = headerTextAlign;
	}
	public String getHeaderTextColor() {
		return headerTextColor;
	}
	public void setHeaderTextColor(String headerTextColor) {
		this.headerTextColor = headerTextColor;
	}
	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}
	public String getLetterHeadName() {
		return letterHeadName;
	}
	public void setLetterHeadName(String letterHeadName) {
		this.letterHeadName = letterHeadName;
	}
	public String getLetterHeadDescription() {
		return letterHeadDescription;
	}
	public void setLetterHeadDescription(String letterHeadDescription) {
		this.letterHeadDescription = letterHeadDescription;
	}
}
