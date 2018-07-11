package com.bis.sourcelead.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "JOB_FAIR_REQUIREMENTS")
public class JobFairRequirements implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "JOB_FAIR_ID", referencedColumnName = "ID")
	private JobFair jobFair;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REQUIREMENT_ID", referencedColumnName = "ID")
	private Requirement requirement;

	@Column(name = "FAIR_DATES")
	private String fairDates;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public JobFair getJobFair() {
		return jobFair;
	}

	public void setJobFair(JobFair jobFair) {
		this.jobFair = jobFair;
	}

	public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}

	public String getFairDates() {
		return fairDates;
	}

	public void setFairDates(String fairDates) {
		this.fairDates = fairDates;
	}

}
