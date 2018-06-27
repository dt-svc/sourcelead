package com.bis.sourcelead.reportsDTO;

import java.math.BigInteger;

public class ApplicantsBackoutDTO {

	private String counts;
	
    private BigInteger currentCompany;
	
	private BigInteger compensation;
	
	private BigInteger betterOffer;
	
	private BigInteger coldFeet;
	
	private BigInteger personal;
	
	private BigInteger other;
	
	private BigInteger totalCount;
	
	private String jobName;
	
	private String generatedId;
	
	

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

	public BigInteger getCurrentCompany() {
		return currentCompany;
	}

	public void setCurrentCompany(BigInteger currentCompany) {
		this.currentCompany = currentCompany;
	}

	public BigInteger getCompensation() {
		return compensation;
	}

	public void setCompensation(BigInteger compensation) {
		this.compensation = compensation;
	}

	public BigInteger getBetterOffer() {
		return betterOffer;
	}

	public void setBetterOffer(BigInteger betterOffer) {
		this.betterOffer = betterOffer;
	}

	public BigInteger getColdFeet() {
		return coldFeet;
	}

	public void setColdFeet(BigInteger coldFeet) {
		this.coldFeet = coldFeet;
	}

	public BigInteger getPersonal() {
		return personal;
	}

	public void setPersonal(BigInteger personal) {
		this.personal = personal;
	}

	public BigInteger getOther() {
		return other;
	}

	public void setOther(BigInteger other) {
		this.other = other;
	}

	public BigInteger getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(BigInteger totalCount) {
		this.totalCount = totalCount;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	
	
	
}


