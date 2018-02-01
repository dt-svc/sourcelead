package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.sun.star.util.DateTime;

@Entity
@Table(name = "JOB_SHARE_CANDIDATES")
public class JobShareCandidate implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@NotFound(action=NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidateId;
    
	@NotFound(action=NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JOB_ID", nullable = false)
	private Requirement jobId;
	
   	@Column(name = "CREATED_DATE")
   	private Date createdDate;

	public Long getId() {
		return id;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public Requirement getJobId() {
		return jobId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public void setJobId(Requirement jobId) {
		this.jobId = jobId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
