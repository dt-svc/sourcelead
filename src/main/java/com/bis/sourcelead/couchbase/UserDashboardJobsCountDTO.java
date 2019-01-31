package com.bis.sourcelead.couchbase;

import java.io.Serializable;

public class UserDashboardJobsCountDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer applicantsCount;
	
	private Integer clientSubmissionsCount;
	
	private Integer interviewsCount;
	
	private Integer offersCount;
	
	

	public Integer getApplicantsCount() {
		return applicantsCount;
	}

	public void setApplicantsCount(Integer applicantsCount) {
		this.applicantsCount = applicantsCount;
	}

	public Integer getClientSubmissionsCount() {
		return clientSubmissionsCount;
	}

	public void setClientSubmissionsCount(Integer clientSubmissionsCount) {
		this.clientSubmissionsCount = clientSubmissionsCount;
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
