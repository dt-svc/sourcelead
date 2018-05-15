package com.bis.sourcelead.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity for LETTER_HEAD
 * @author Rakshith
 *
 */

@Entity
@Table(name = "LETTER_HEAD")
public class LetterHead {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "LETTERHEAD_NAME")
	private String letterHeadName;
	
	@Column(name = "LETTERHEAD_DESCRIPTION",columnDefinition = "TEXT default NULL")
	private String letterHeadDescription;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORGLOCATION_ID", nullable=false )     
	private OrgLocations orgLocationId;
	
	@Column(name = "LETTERHEAD_UUID")
	private String letterHeadUUID;
	
	@Column(name = "TEMPLATE_BACKGROUND_COLOR")
	private String templateBgColor;
	
	@Column(name = "TEMPLATE_TYPE")
	private String templateType;
	
	@Column(name = "PADDING_LEFT")
	private String paddingLeft;
	
	@Column(name = "PADDING_RIGHT")
	private String paddingRight;
	
	@Column(name = "PADDING_TOP")
	private String paddingTop;
	
	@Column(name = "PADDING_BOTTOM")
	private String paddingBottom;
	
	@Column(name = "ALIGN_LOGO")
	private String alignLogo;
	
	@Column(name = "HEADER_BACKGROUND_COLOR")
	private String headerBgColor;
	
	@Column(name = "HEADER_TEXT",columnDefinition = "TEXT default NULL")
	private String headerText;
	
	@Column(name = "FOOTER_TEXT",columnDefinition = "TEXT default NULL")
	private String footerText;
	
	@Column(name = "ALIGN_HEADER_TEXT")
	private String alignHeaderText;
	
	@Column(name = "ALIGN_FOOTER_TEXT")
	private String alignFooterText;
	
	@Column(name = "FOOTER_BACKGROUND_COLOR")
	private String footerBgColor;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_TYPE")
	private String fileType;
	
	@Column(name = "FILE_URL")
	private String fileURL;
	
	@Column(name = "STATUS")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	
	public String getLetterHeadUUID() {
		return letterHeadUUID;
	}

	public void setLetterHeadUUID(String letterHeadUUID) {
		this.letterHeadUUID = letterHeadUUID;
	}

	public String getTemplateBgColor() {
		return templateBgColor;
	}

	public void setTemplateBgColor(String templateBgColor) {
		this.templateBgColor = templateBgColor;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
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

	public String getHeaderBgColor() {
		return headerBgColor;
	}

	public void setHeaderBgColor(String headerBgColor) {
		this.headerBgColor = headerBgColor;
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

	public String getAlignHeaderText() {
		return alignHeaderText;
	}

	public void setAlignHeaderText(String alignHeaderText) {
		this.alignHeaderText = alignHeaderText;
	}

	public String getAlignFooterText() {
		return alignFooterText;
	}

	public void setAlignFooterText(String alignFooterText) {
		this.alignFooterText = alignFooterText;
	}

	public String getFooterBgColor() {
		return footerBgColor;
	}

	public void setFooterBgColor(String footerBgColor) {
		this.footerBgColor = footerBgColor;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
