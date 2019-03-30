package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.dto.FileInfo;

public class PerformanceGoalsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long goalId;
	
	@Required(desc="goalName")
	private String goalName;
	
	@Required(desc="goalStartDate")
	private String goalStartDate;
	
	@Required(desc="goalEndDate")
	private String goalEndDate;
	
	private String goalsAssignTo;
	
	private String description;
	
	private String staticPath;
	
    private List<Map<String, String>> fileInfo;

	private List<FileInfo> uploadedFileInfo;
	
	private String uuid;
	
	private BigInteger id;
	
    private Integer percentageDone;
    
    private int start;
	
	private int length;
	
	private String searchValue;
	
	private String sortColumn;
	
	private String sortOrder;
	
	private List<Map<String, String>> order;
	
	private List<PerformanceGoalsAttachmentsDTO> attachmentsList;
	
	private String goalType;

	public Long getGoalId() {
		return goalId;
	}

	public void setGoalId(Long goalId) {
		this.goalId = goalId;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public String getGoalStartDate() {
		return goalStartDate;
	}

	public void setGoalStartDate(String goalStartDate) {
		this.goalStartDate = goalStartDate;
	}

	public String getGoalEndDate() {
		return goalEndDate;
	}

	public void setGoalEndDate(String goalEndDate) {
		this.goalEndDate = goalEndDate;
	}

	public String getGoalsAssignTo() {
		return goalsAssignTo;
	}

	public void setGoalsAssignTo(String goalsAssignTo) {
		this.goalsAssignTo = goalsAssignTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<Map<String, String>> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<Map<String, String>> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getPercentageDone() {
		return percentageDone;
	}

	public void setPercentageDone(Integer percentageDone) {
		this.percentageDone = percentageDone;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<Map<String, String>> getOrder() {
		return order;
	}

	public void setOrder(List<Map<String, String>> order) {
		this.order = order;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public List<PerformanceGoalsAttachmentsDTO> getAttachmentsList() {
		return attachmentsList;
	}

	public void setAttachmentsList(List<PerformanceGoalsAttachmentsDTO> attachmentsList) {
		this.attachmentsList = attachmentsList;
	}

	public String getGoalType() {
		return goalType;
	}

	public void setGoalType(String goalType) {
		this.goalType = goalType;
	}

	
}
