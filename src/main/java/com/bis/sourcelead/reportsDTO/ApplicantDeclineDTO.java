package com.bis.sourcelead.reportsDTO;

import java.math.BigInteger;

public class ApplicantDeclineDTO {
	
	private String jobName;
	
	private String generatedId;

	private BigInteger skillsCount;
	
	private BigInteger unsuitableCount;
	
	private BigInteger badfitCount;
	
	private BigInteger interviewCount;
	
	private BigInteger salaryCount;
	
	private BigInteger otherCount;
	
	private BigInteger totalCount;
	
	
	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public BigInteger getSkillsCount() {
		return skillsCount;
	}

	public void setSkillsCount(BigInteger skillsCount) {
		this.skillsCount = skillsCount;
	}

	public BigInteger getUnsuitableCount() {
		return unsuitableCount;
	}

	public void setUnsuitableCount(BigInteger unsuitableCount) {
		this.unsuitableCount = unsuitableCount;
	}

	public BigInteger getBadfitCount() {
		return badfitCount;
	}

	public void setBadfitCount(BigInteger badfitCount) {
		this.badfitCount = badfitCount;
	}

	public BigInteger getInterviewCount() {
		return interviewCount;
	}

	public void setInterviewCount(BigInteger interviewCount) {
		this.interviewCount = interviewCount;
	}

	public BigInteger getSalaryCount() {
		return salaryCount;
	}

	public void setSalaryCount(BigInteger salaryCount) {
		this.salaryCount = salaryCount;
	}

	public BigInteger getOtherCount() {
		return otherCount;
	}

	public void setOtherCount(BigInteger otherCount) {
		this.otherCount = otherCount;
	}

	public BigInteger getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(BigInteger totalCount) {
		this.totalCount = totalCount;
	}

	
		
}
