package com.bis.sourcelead.dto;

import java.util.LinkedHashSet;
import java.util.List;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.LookUpData;

public class CandidateEducationDTO extends CommonDTO {

	
	@Required(desc = "Education name")
	private String name;
	
	private String specialization;
	
	private String candidateEduId;
	 
	private String startDate;
	
	private String endDate;

	private String institute;
	
	private String address;
	
	private String createdDate;
	
	private String createdBy;
	
	private String updatedDate;
	
	private String updatedBy;
	
	private String staticPath;
	
	private List<FileInfo> fileInfo;
	
	private String scoreType;
	
	private String score;
	
    private String startDateStr;
	
	private String msg;
	
	private String endDateStr;
	
	private List<LookUpData> educationList;
	
	private String grade;
	
	private String gradeType;
	
	private long id;
	
	private LinkedHashSet<String> allAndCandidateEducations;
	
	private long candidateId;
	
	private long loginUserId;
	
	private long orgLocationId;
	
	private long candidateEducationId;
	
	private String startMonth;
	
	private String startYear;
	
	private String endMonth;
	
	private String endYear;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCandidateEduId() {
		return candidateEduId;
	}

	public void setCandidateEduId(String candidateEduId) {
		this.candidateEduId = candidateEduId;
	}

	

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getStartDateStr()
	{
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr)
	{
		this.startDateStr = startDateStr;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getEndDateStr()
	{
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr)
	{
		this.endDateStr = endDateStr;
	}

	public List<LookUpData> getEducationList()
	{
		return educationList;
	}

	public void setEducationList(List<LookUpData> educationList)
	{
		this.educationList = educationList;
	}

	public String getGrade()
	{
		return grade;
	}

	public void setGrade(String grade)
	{
		this.grade = grade;
	}

	public String getGradeType()
	{
		return gradeType;
	}

	public void setGradeType(String gradeType)
	{
		this.gradeType = gradeType;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public LinkedHashSet<String> getAllAndCandidateEducations()
	{
		return allAndCandidateEducations;
	}

	public void setAllAndCandidateEducations(LinkedHashSet<String> allAndCandidateEducations)
	{
		this.allAndCandidateEducations = allAndCandidateEducations;
	}

	public long getCandidateId()
	{
		return candidateId;
	}

	public void setCandidateId(long candidateId)
	{
		this.candidateId = candidateId;
	}

	public long getLoginUserId()
	{
		return loginUserId;
	}

	public void setLoginUserId(long loginUserId)
	{
		this.loginUserId = loginUserId;
	}

	public long getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public long getCandidateEducationId()
	{
		return candidateEducationId;
	}

	public void setCandidateEducationId(long candidateEducationId)
	{
		this.candidateEducationId = candidateEducationId;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

}
