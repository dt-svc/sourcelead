package com.bis.sourcelead.couchbase;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UserLocationStatsCountDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Map<String, UserDashboardJobsCountDTO>> jobsCountsList;

	public List<Map<String, UserDashboardJobsCountDTO>> getJobsCountsList() {
		return jobsCountsList;
	}

	public void setJobsCountsList(List<Map<String, UserDashboardJobsCountDTO>> jobsCountsList) {
		this.jobsCountsList = jobsCountsList;
	}

	
}
