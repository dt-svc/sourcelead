package com.bis.sourcelead.dto;

import java.util.Map;

public class RequirementSubmissionsCountDTO {

	private Integer applicantsCount;
	private Integer submissionsCount;
	private Integer duplicateApplicantsCount;
	private Integer requestSent;
	private Integer shortlistCount;
	private Integer submitToClientCount;
	private Integer interviewCount;
	private Integer offersCount;
	private Integer joinedCount;
	private Integer rejectedCount;
	private Integer backoutCount;
	private Integer onHoldCount;
	private Integer noResponseCount;
	private Integer dynamicCount;
	private Map<String, Integer> stagesCountMap;
	
	public Integer getApplicantsCount() {
		return applicantsCount;
	}
	public void setApplicantsCount(Integer applicantsCount) {
		this.applicantsCount = applicantsCount;
	}
	public Integer getSubmissionsCount() {
		return submissionsCount;
	}
	public void setSubmissionsCount(Integer submissionsCount) {
		this.submissionsCount = submissionsCount;
	}
	public Integer getDuplicateApplicantsCount() {
		return duplicateApplicantsCount;
	}
	public void setDuplicateApplicantsCount(Integer duplicateApplicantsCount) {
		this.duplicateApplicantsCount = duplicateApplicantsCount;
	}
	public Integer getRequestSent() {
		return requestSent;
	}
	public void setRequestSent(Integer requestSent) {
		this.requestSent = requestSent;
	}
	public Integer getShortlistCount() {
		return shortlistCount;
	}
	public void setShortlistCount(Integer shortlistCount) {
		this.shortlistCount = shortlistCount;
	}
	public Integer getSubmitToClientCount() {
		return submitToClientCount;
	}
	public void setSubmitToClientCount(Integer submitToClientCount) {
		this.submitToClientCount = submitToClientCount;
	}
	public Integer getInterviewCount() {
		return interviewCount;
	}
	public void setInterviewCount(Integer interviewCount) {
		this.interviewCount = interviewCount;
	}
	public Integer getOffersCount() {
		return offersCount;
	}
	public void setOffersCount(Integer offersCount) {
		this.offersCount = offersCount;
	}
	public Integer getJoinedCount() {
		return joinedCount;
	}
	public void setJoinedCount(Integer joinedCount) {
		this.joinedCount = joinedCount;
	}
	public Integer getRejectedCount() {
		return rejectedCount;
	}
	public void setRejectedCount(Integer rejectedCount) {
		this.rejectedCount = rejectedCount;
	}
	public Integer getBackoutCount() {
		return backoutCount;
	}
	public void setBackoutCount(Integer backoutCount) {
		this.backoutCount = backoutCount;
	}
	public Integer getOnHoldCount()
	{
		return onHoldCount;
	}
	public void setOnHoldCount(Integer onHoldCount)
	{
		this.onHoldCount = onHoldCount;
	}
	public Integer getNoResponseCount()
	{
		return noResponseCount;
	}
	public void setNoResponseCount(Integer noResponseCount)
	{
		this.noResponseCount = noResponseCount;
	}
	public Integer getDynamicCount() {
		return dynamicCount;
	}
	public void setDynamicCount(Integer dynamicCount) {
		this.dynamicCount = dynamicCount;
	}
	public Map<String, Integer> getStagesCountMap() {
		return stagesCountMap;
	}
	public void setStagesCountMap(Map<String, Integer> stagesCountMap) {
		this.stagesCountMap = stagesCountMap;
	}
	
}
