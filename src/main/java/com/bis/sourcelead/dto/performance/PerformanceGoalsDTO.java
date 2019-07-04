package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.math.BigDecimal;
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
	
	private String teamId;
	
	private String description;
	
	private String staticPath;
	
    private List<Map<String, String>> fileInfo;

	private List<FileInfo> uploadedFileInfo;
	
	private String goalPercentage;
	
	private String uuid;
	
	private BigInteger id;
	
    private double percentageDone;
    
    private int start;
	
	private int length;
	
	private String searchValue;
	
	private String sortColumn;
	
	private String sortOrder;
	
	private List<Map<String, String>> order;
	
	private List<PerformanceGoalsAttachmentsDTO> attachmentsList;
	
	private String goalUUID;
	
	private String goalType;
	
	private String createdDate;
	
	private BigInteger subGoalsCount;
	
	private String teamName;
	
	private BigDecimal toalAssignedGoalsCount;
	
	private BigDecimal toalAssignedInprogressGoalsCount;
	
	private BigDecimal toalAssignedCompletedGoalsCount;
	
	private BigDecimal toalAssignedOverDueGoalsCount;
	
	private BigDecimal toalAssignedNotStartedGoalsCount;
	
	private String categoriesJsonString;
	
	private Integer goalsScore;
	
	

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

	public double getPercentageDone() {
		return percentageDone;
	}

	public void setPercentageDone(double percentageDone) {
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

	public String getGoalUUID() {
		return goalUUID;
	}

	public void setGoalUUID(String goalUUID) {
		this.goalUUID = goalUUID;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getGoalPercentage() {
		return goalPercentage;
	}

	public void setGoalPercentage(String goalPercentage) {
		this.goalPercentage = goalPercentage;
	}

	public BigInteger getSubGoalsCount() {
		return subGoalsCount;
	}

	public void setSubGoalsCount(BigInteger subGoalsCount) {
		this.subGoalsCount = subGoalsCount;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public BigDecimal getToalAssignedGoalsCount() {
		return toalAssignedGoalsCount;
	}

	public void setToalAssignedGoalsCount(BigDecimal toalAssignedGoalsCount) {
		this.toalAssignedGoalsCount = toalAssignedGoalsCount;
	}

	public BigDecimal getToalAssignedInprogressGoalsCount() {
		return toalAssignedInprogressGoalsCount;
	}

	public void setToalAssignedInprogressGoalsCount(BigDecimal toalAssignedInprogressGoalsCount) {
		this.toalAssignedInprogressGoalsCount = toalAssignedInprogressGoalsCount;
	}

	public BigDecimal getToalAssignedCompletedGoalsCount() {
		return toalAssignedCompletedGoalsCount;
	}

	public void setToalAssignedCompletedGoalsCount(BigDecimal toalAssignedCompletedGoalsCount) {
		this.toalAssignedCompletedGoalsCount = toalAssignedCompletedGoalsCount;
	}

	public BigDecimal getToalAssignedOverDueGoalsCount() {
		return toalAssignedOverDueGoalsCount;
	}

	public void setToalAssignedOverDueGoalsCount(BigDecimal toalAssignedOverDueGoalsCount) {
		this.toalAssignedOverDueGoalsCount = toalAssignedOverDueGoalsCount;
	}

	public BigDecimal getToalAssignedNotStartedGoalsCount() {
		return toalAssignedNotStartedGoalsCount;
	}

	public void setToalAssignedNotStartedGoalsCount(BigDecimal toalAssignedNotStartedGoalsCount) {
		this.toalAssignedNotStartedGoalsCount = toalAssignedNotStartedGoalsCount;
	}

	public String getCategoriesJsonString() {
		return categoriesJsonString;
	}

	public void setCategoriesJsonString(String categoriesJsonString) {
		this.categoriesJsonString = categoriesJsonString;
	}

	public Integer getGoalsScore() {
		return goalsScore;
	}

	public void setGoalsScore(Integer goalsScore) {
		this.goalsScore = goalsScore;
	}

	
}
