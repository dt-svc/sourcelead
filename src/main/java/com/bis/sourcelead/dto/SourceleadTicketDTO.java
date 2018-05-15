package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.annotations.Required;

@SuppressWarnings("serial")
public class SourceleadTicketDTO extends SearchDTO implements Serializable{

	private String id;
	
	@Required(desc = "ticketTitle")
	private String ticketTitle;
	
	@Required(desc = "titcketType")
	private String titcketType;
	
	@Required(desc = "ticketTabs")
	private String ticketTabs;

	private String ticketUrl;
	
	@Required(desc = "description")
	@CleanHtml(escape=true)
	private String description;
	
	private List<FileInfo> fileInfo;
	
	private String staticPath;

	private String requestedOn;
	
	private String requestedBy;
	
	private String ticketStatus;
	
	private String dateSelection;
	
    private String fromDate;
	
	private String toDate;
	
	private String ticketUUID;
	
	private String attachmentId;
	
	private String ticketComment;
	
	private String userId;
	
	private String orgLocationsId;
	
	private String loginUserRole;
	
	private String ticketGeneratedId;
	
	private String selectCompanyIds;
	
	private String companyName;
	
	private String createdByUserId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public String getTitcketType() {
		return titcketType;
	}

	public String getTicketTabs() {
		return ticketTabs;
	}

	public String getTicketUrl() {
		return ticketUrl;
	}

	public String getDescription() {
		return description;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public void setTitcketType(String titcketType) {
		this.titcketType = titcketType;
	}

	public void setTicketTabs(String ticketTabs) {
		this.ticketTabs = ticketTabs;
	}

	public void setTicketUrl(String ticketUrl) {
		this.ticketUrl = ticketUrl;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getRequestedOn() {
		return requestedOn;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedOn(String requestedOn) {
		this.requestedOn = requestedOn;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getDateSelection() {
		return dateSelection;
	}

	public void setDateSelection(String dateSelection) {
		this.dateSelection = dateSelection;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTicketUUID() {
		return ticketUUID;
	}

	public void setTicketUUID(String ticketUUID) {
		this.ticketUUID = ticketUUID;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getTicketComment() {
		return ticketComment;
	}

	public void setTicketComment(String ticketComment) {
		this.ticketComment = ticketComment;
	}

	public String getUserId() {
		return userId;
	}

	public String getOrgLocationsId() {
		return orgLocationsId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setOrgLocationsId(String orgLocationsId) {
		this.orgLocationsId = orgLocationsId;
	}

	public String getLoginUserRole() {
		return loginUserRole;
	}

	public void setLoginUserRole(String loginUserRole) {
		this.loginUserRole = loginUserRole;
	}

	public String getTicketGeneratedId() {
		return ticketGeneratedId;
	}

	public void setTicketGeneratedId(String ticketGeneratedId) {
		this.ticketGeneratedId = ticketGeneratedId;
	}

	public String getSelectCompanyIds() {
		return selectCompanyIds;
	}

	public void setSelectCompanyIds(String selectCompanyIds) {
		this.selectCompanyIds = selectCompanyIds;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(String createdByUserId) {
		this.createdByUserId = createdByUserId;
	}
}