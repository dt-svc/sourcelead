package com.bis.sourcelead.dto;

import java.math.BigInteger;
import java.util.List;

import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;

public class MspVendorDTO {
	
	private String mspVendorIds;
	
	private String orgRelationIds;
	
	private String mspOrgRelationIds;
	
	private Long loginOrgLocationId;
	
	private Long mspOrgRelationId;
	
	private Long mspVendorOrgLocationId;
	
	private String orgName;
	
	private String clientName;
	
	private User loginUser;
	
	private BigInteger orgLocationId;

	private BigInteger orgRelationId;
	
	private Integer applicantCount;
	
	private Integer shortlistCount;
	
	private Integer offersCount;
	
	private Integer submitToClientCount;
	
	private Integer joinedCount;
	
	private Integer backoutCount;
	
	private Integer interviewCount;

	private Integer rejectedCount;
	
	private String requirementName;
	
	private String jobLength;
	
	private List<MspVendorDTO> mspReqList;
	
	private String requirementIds;
	
	private Long requirementId;
	
	private String dateSelection;
	
	private String searchData;
	
	private String alphabet;
	
	private String fromDate;
	
	private String toDate;
	
	public String getMspVendorIds() {
		return mspVendorIds;
	}

	public void setMspVendorIds(String mspVendorIds) {
		this.mspVendorIds = mspVendorIds;
	}

	public String getOrgRelationIds() {
		return orgRelationIds;
	}

	public String getMspOrgRelationIds() {
		return mspOrgRelationIds;
	}

	public Long getLoginOrgLocationId() {
		return loginOrgLocationId;
	}

	public void setOrgRelationIds(String orgRelationIds) {
		this.orgRelationIds = orgRelationIds;
	}

	public void setMspOrgRelationIds(String mspOrgRelationIds) {
		this.mspOrgRelationIds = mspOrgRelationIds;
	}

	public void setLoginOrgLocationId(Long loginOrgLocationId) {
		this.loginOrgLocationId = loginOrgLocationId;
	}

	public Long getMspOrgRelationId() {
		return mspOrgRelationId;
	}

	public Long getMspVendorOrgLocationId() {
		return mspVendorOrgLocationId;
	}

	public void setMspOrgRelationId(Long mspOrgRelationId) {
		this.mspOrgRelationId = mspOrgRelationId;
	}

	public void setMspVendorOrgLocationId(Long mspVendorOrgLocationId) {
		this.mspVendorOrgLocationId = mspVendorOrgLocationId;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public BigInteger getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(BigInteger orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public BigInteger getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(BigInteger orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

 
	public Integer getShortlistCount() {
		return shortlistCount;
	}

	public Integer getOffersCount() {
		return offersCount;
	}

	public Integer getSubmitToClientCount() {
		return submitToClientCount;
	}

	public Integer getJoinedCount() {
		return joinedCount;
	}

	public Integer getBackoutCount() {
		return backoutCount;
	}

	public Integer getInterviewCount() {
		return interviewCount;
	}

	public Integer getRejectedCount() {
		return rejectedCount;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public String getJobLength() {
		return jobLength;
	}

	public void setShortlistCount(Integer shortlistCount) {
		this.shortlistCount = shortlistCount;
	}

	public void setOffersCount(Integer offersCount) {
		this.offersCount = offersCount;
	}

	public void setSubmitToClientCount(Integer submitToClientCount) {
		this.submitToClientCount = submitToClientCount;
	}

	public void setJoinedCount(Integer joinedCount) {
		this.joinedCount = joinedCount;
	}

	public void setBackoutCount(Integer backoutCount) {
		this.backoutCount = backoutCount;
	}

	public void setInterviewCount(Integer interviewCount) {
		this.interviewCount = interviewCount;
	}

	public void setRejectedCount(Integer rejectedCount) {
		this.rejectedCount = rejectedCount;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public void setJobLength(String jobLength) {
		this.jobLength = jobLength;
	}

	public List<MspVendorDTO> getMspReqList() {
		return mspReqList;
	}

	public void setMspReqList(List<MspVendorDTO> mspReqList) {
		this.mspReqList = mspReqList;
	}

	public Integer getApplicantCount() {
		return applicantCount;
	}

	public void setApplicantCount(Integer applicantCount) {
		this.applicantCount = applicantCount;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public String getRequirementIds() {
		return requirementIds;
	}

	public void setRequirementIds(String requirementIds) {
		this.requirementIds = requirementIds;
	}

	public String getDateSelection() {
		return dateSelection;
	}

	public void setDateSelection(String dateSelection) {
		this.dateSelection = dateSelection;
	}

	public String getSearchData() {
		return searchData;
	}

	public void setSearchData(String searchData) {
		this.searchData = searchData;
	}

	public String getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
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
	
}