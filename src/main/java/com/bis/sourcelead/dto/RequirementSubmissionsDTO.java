package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import com.bis.sourcelead.entity.Contracts;

public class RequirementSubmissionsDTO extends SearchDTO implements Serializable {

	private long requirementId;
	private long orgLocationId;
	private long userId;
	private long id;
	private long submissionId;
	private long orgRelationId;
	private long spocId;
	private String submissionType;
	private String loginUserRole;
	private String userCode;
	private String name;
	private String email;
	private String phNo;
	private String resume;
	private String appliedOn;
	private String rate;
	private String source;
	private String exp;
	private String status;
	private String clientSubmissions;
	private String fileContentType;
	private String fileName;
	private String fileURL;
	private String createdUser;
	
	
  //Dashboard realated properties added on 13/08/2018
	private String candidateName;
	private String candidateEmail;
	private String requirementName;
	private String candidateUUID;
	private String requirementUUID;
	private BigInteger reqManagerId;
	private String reqReleaseAllJobUserStatus;
	private String offerJoinDate;
	private String positionType;
	private String rateType;
	private String paymentType;
	private String offerUUID;
	private BigInteger candidateId;
	private BigInteger offerId;
	private String onboardUUId;
	private String onboardStatus;
	
    private BigInteger reqId;
	
	private String submissionStatus;
	
	private String invoiceGenerateDate;
	
	private String paymentTerm;
	
	private String joinedDate;
	
	private String submissionDate;
	
	private String submissionTime;
	
	private String generatedId;
	
	private String submittedBy;
	
	private String clientOrVendorName;
	
	private List<Contracts>contractsList;
	
	private Boolean generateDate = false;
	
	private String workflowSatgeColumns; // contains comma separated string.
	
	
	public String getWorkflowSatgeColumns() {
		return workflowSatgeColumns;
	}
	public void setWorkflowSatgeColumns(String workflowSatgeColumns) {
		this.workflowSatgeColumns = workflowSatgeColumns;
	}
	public long getRequirementId()
	{
		return requirementId;
	}
	public void setRequirementId(long requirementId)
	{
		this.requirementId = requirementId;
	}
	public long getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public long getUserId()
	{
		return userId;
	}
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
	public String getSubmissionType()
	{
		return submissionType;
	}
	public void setSubmissionType(String submissionType)
	{
		this.submissionType = submissionType;
	}
	public String getLoginUserRole()
	{
		return loginUserRole;
	}
	public void setLoginUserRole(String loginUserRole)
	{
		this.loginUserRole = loginUserRole;
	}
	public String getUserCode()
	{
		return userCode;
	}
	public void setUserCode(String userCode)
	{
		this.userCode = userCode;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPhNo()
	{
		return phNo;
	}
	public void setPhNo(String phNo)
	{
		this.phNo = phNo;
	}
	public String getResume()
	{
		return resume;
	}
	public void setResume(String resume)
	{
		this.resume = resume;
	}
	public String getAppliedOn()
	{
		return appliedOn;
	}
	public void setAppliedOn(String appliedOn)
	{
		this.appliedOn = appliedOn;
	}
	public String getRate()
	{
		return rate;
	}
	public void setRate(String rate)
	{
		this.rate = rate;
	}
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public String getExp()
	{
		return exp;
	}
	public void setExp(String exp)
	{
		this.exp = exp;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getClientSubmissions()
	{
		return clientSubmissions;
	}
	public void setClientSubmissions(String clientSubmissions)
	{
		this.clientSubmissions = clientSubmissions;
	}
	public String getFileContentType()
	{
		return fileContentType;
	}
	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public String getFileURL()
	{
		return fileURL;
	}
	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public String getCandidateEmail() {
		return candidateEmail;
	}
	public String getRequirementName() {
		return requirementName;
	}
	public String getCandidateUUID() {
		return candidateUUID;
	}
	public String getRequirementUUID() {
		return requirementUUID;
	}
	public String getReqReleaseAllJobUserStatus() {
		return reqReleaseAllJobUserStatus;
	}
	public String getOfferJoinDate() {
		return offerJoinDate;
	}
	public String getPositionType() {
		return positionType;
	}
	public String getRateType() {
		return rateType;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public String getOfferUUID() {
		return offerUUID;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}
	public void setRequirementUUID(String requirementUUID) {
		this.requirementUUID = requirementUUID;
	}
	 
	public void setReqReleaseAllJobUserStatus(String reqReleaseAllJobUserStatus) {
		this.reqReleaseAllJobUserStatus = reqReleaseAllJobUserStatus;
	}
	public void setOfferJoinDate(String offerJoinDate) {
		this.offerJoinDate = offerJoinDate;
	}
	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public void setOfferUUID(String offerUUID) {
		this.offerUUID = offerUUID;
	}
	public BigInteger getReqManagerId() {
		return reqManagerId;
	}
	public BigInteger getCandidateId() {
		return candidateId;
	}
	public BigInteger getOfferId() {
		return offerId;
	}
	public void setReqManagerId(BigInteger reqManagerId) {
		this.reqManagerId = reqManagerId;
	}
	public void setCandidateId(BigInteger candidateId) {
		this.candidateId = candidateId;
	}
	public void setOfferId(BigInteger offerId) {
		this.offerId = offerId;
	}
	public String getOnboardUUId() {
		return onboardUUId;
	}
	public String getOnboardStatus() {
		return onboardStatus;
	}
	public void setOnboardUUId(String onboardUUId) {
		this.onboardUUId = onboardUUId;
	}
	public void setOnboardStatus(String onboardStatus) {
		this.onboardStatus = onboardStatus;
	}
	public BigInteger getReqId() {
		return reqId;
	}
	public void setReqId(BigInteger reqId) {
		this.reqId = reqId;
	}
	public String getSubmissionStatus() {
		return submissionStatus;
	}
	public void setSubmissionStatus(String submissionStatus) {
		this.submissionStatus = submissionStatus;
	}
	public String getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}
	public String getSubmissionTime() {
		return submissionTime;
	}
	public void setSubmissionTime(String submissionTime) {
		this.submissionTime = submissionTime;
	}
	public String getGeneratedId() {
		return generatedId;
	}
	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}
	public String getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}
	public String getInvoiceGenerateDate() {
		return invoiceGenerateDate;
	}
	public void setInvoiceGenerateDate(String invoiceGenerateDate) {
		this.invoiceGenerateDate = invoiceGenerateDate;
	}
	public String getPaymentTerm() {
		return paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	public String getJoinedDate() {
		return joinedDate;
	}
	public void setJoinedDate(String joinedDate) {
		this.joinedDate = joinedDate;
	}
	public String getClientOrVendorName() {
		return clientOrVendorName;
	}
	public void setClientOrVendorName(String clientOrVendorName) {
		this.clientOrVendorName = clientOrVendorName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSubmissionId() {
		return submissionId;
	}
	public void setSubmissionId(long submissionId) {
		this.submissionId = submissionId;
	}
	public List<Contracts> getContractsList() {
		return contractsList;
	}
	public void setContractsList(List<Contracts> contractsList) {
		this.contractsList = contractsList;
	}
	public long getOrgRelationId() {
		return orgRelationId;
	}
	public void setOrgRelationId(long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}
	public long getSpocId() {
		return spocId;
	}
	public void setSpocId(long spocId) {
		this.spocId = spocId;
	}
	public Boolean getGenerateDate() {
		return generateDate;
	}
	public void setGenerateDate(Boolean generateDate) {
		this.generateDate = generateDate;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	
}