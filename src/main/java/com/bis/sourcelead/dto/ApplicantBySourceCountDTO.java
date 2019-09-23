package com.bis.sourcelead.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class ApplicantBySourceCountDTO {

	/**
	 * 
	 */
	
	private String  createdDate;
	
	private BigDecimal submissionCount;

	private BigDecimal shortlistCount;
	
	private BigDecimal submitToClientCount;

	private BigDecimal interviewCount;

	private BigDecimal clientInterviewCount;
	
	private BigDecimal rejectedCount;
	
	private BigDecimal offermadeCount;
	
	private BigDecimal clientOffermadeCount;
	
	private BigDecimal offerAcceptedCount;
	
	private BigDecimal clientOfferAcceptedCount;
	
	private BigDecimal offerRejectedCount;
	
	private BigDecimal backoutCount;

	

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getSubmissionCount() {
		return submissionCount;
	}

	public void setSubmissionCount(BigDecimal submissionCount) {
		this.submissionCount = submissionCount;
	}

	public BigDecimal getShortlistCount() {
		return shortlistCount;
	}

	public void setShortlistCount(BigDecimal shortlistCount) {
		this.shortlistCount = shortlistCount;
	}

	public BigDecimal getSubmitToClientCount() {
		return submitToClientCount;
	}

	public void setSubmitToClientCount(BigDecimal submitToClientCount) {
		this.submitToClientCount = submitToClientCount;
	}

	public BigDecimal getInterviewCount() {
		return interviewCount;
	}

	public void setInterviewCount(BigDecimal interviewCount) {
		this.interviewCount = interviewCount;
	}

	public BigDecimal getClientInterviewCount() {
		return clientInterviewCount;
	}

	public void setClientInterviewCount(BigDecimal clientInterviewCount) {
		this.clientInterviewCount = clientInterviewCount;
	}

	public BigDecimal getRejectedCount() {
		return rejectedCount;
	}

	public void setRejectedCount(BigDecimal rejectedCount) {
		this.rejectedCount = rejectedCount;
	}

	public BigDecimal getOffermadeCount() {
		return offermadeCount;
	}

	public void setOffermadeCount(BigDecimal offermadeCount) {
		this.offermadeCount = offermadeCount;
	}

	public BigDecimal getClientOffermadeCount() {
		return clientOffermadeCount;
	}

	public void setClientOffermadeCount(BigDecimal clientOffermadeCount) {
		this.clientOffermadeCount = clientOffermadeCount;
	}

	public BigDecimal getOfferAcceptedCount() {
		return offerAcceptedCount;
	}

	public void setOfferAcceptedCount(BigDecimal offerAcceptedCount) {
		this.offerAcceptedCount = offerAcceptedCount;
	}

	

	public BigDecimal getClientOfferAcceptedCount() {
		return clientOfferAcceptedCount;
	}

	public void setClientOfferAcceptedCount(BigDecimal clientOfferAcceptedCount) {
		this.clientOfferAcceptedCount = clientOfferAcceptedCount;
	}

	public BigDecimal getOfferRejectedCount() {
		return offerRejectedCount;
	}

	public void setOfferRejectedCount(BigDecimal offerRejectedCount) {
		this.offerRejectedCount = offerRejectedCount;
	}

	public BigDecimal getBackoutCount() {
		return backoutCount;
	}

	public void setBackoutCount(BigDecimal backoutCount) {
		this.backoutCount = backoutCount;
	}
	
}
