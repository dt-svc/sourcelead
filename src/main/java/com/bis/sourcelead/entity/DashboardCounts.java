package com.bis.sourcelead.entity;

import java.io.Serializable;

public class DashboardCounts implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer submissionCount;
	
	private Integer applicantsCount;	
	
	private Integer interviewsCount;	
	
	private Integer offersCount;

	public Integer getSubmissionCount() {
		return submissionCount;
	}

	public void setSubmissionCount(Integer submissionCount) {
		this.submissionCount = submissionCount;
	}

	public Integer getApplicantsCount() {
		return applicantsCount;
	}

	public void setApplicantsCount(Integer applicantsCount) {
		this.applicantsCount = applicantsCount;
	}

	public Integer getInterviewsCount() {
		return interviewsCount;
	}

	public void setInterviewsCount(Integer interviewsCount) {
		this.interviewsCount = interviewsCount;
	}

	public Integer getOffersCount() {
		return offersCount;
	}

	public void setOffersCount(Integer offersCount) {
		this.offersCount = offersCount;
	}
	
	
	
}
