package com.bis.sourcelead.dto;

import java.io.Serializable;

public class JobFairRequirementsDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private long jobFairId;
	private String jobFairName;
	private long requirementId;
	private String requirementTitle;
	private String fairDates;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getJobFairId() {
		return jobFairId;
	}

	public void setJobFairId(long jobFairId) {
		this.jobFairId = jobFairId;
	}

	public String getJobFairName() {
		return jobFairName;
	}

	public void setJobFairName(String jobFairName) {
		this.jobFairName = jobFairName;
	}

	public long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(long requirementId) {
		this.requirementId = requirementId;
	}

	public String getRequirementTitle() {
		return requirementTitle;
	}

	public void setRequirementTitle(String requirementTitle) {
		this.requirementTitle = requirementTitle;
	}

	public String getFairDates() {
		return fairDates;
	}

	public void setFairDates(String fairDates) {
		this.fairDates = fairDates;
	}

}
