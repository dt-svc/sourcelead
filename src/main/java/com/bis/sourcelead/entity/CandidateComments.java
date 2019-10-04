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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Venkat Kesav
 *
 */

@Entity
@Table(name = "CANDIDATE_COMMENTS")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "candidateComments")
public class CandidateComments implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID", nullable=false)
	private CandidateGlobal candidateId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBMISSION_ID", nullable=false)
	private RequirementSubmission submissionId;
	
	@Column(name = "COMMENT", columnDefinition = "TEXT default NULL")
	private String comment;
	
	@Column(name = "STATUS ")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = " UPDATED_DATE ")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	@JsonProperty
	private String commentTime;
	
	@Transient
	@JsonProperty
	private String commentAuthor;
	
	@Transient
	@JsonProperty
	private Long commentAuthorUserId;
	
	@Transient
	@JsonProperty
	private String commentAuthorUserImage;
	
	@Transient
	@JsonProperty
	private String candidateName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CandidateGlobal getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(CandidateGlobal candidateId) {
		this.candidateId = candidateId;
	}
	
	public RequirementSubmission getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(RequirementSubmission submissionId) {
		this.submissionId = submissionId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentAuthor() {
		return commentAuthor;
	}

	public void setCommentAuthor(String commentAuthor) {
		this.commentAuthor = commentAuthor;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public Long getCommentAuthorUserId() {
		return commentAuthorUserId;
	}

	public void setCommentAuthorUserId(Long commentAuthorUserId) {
		this.commentAuthorUserId = commentAuthorUserId;
	}

	public String getCommentAuthorUserImage() {
		return commentAuthorUserImage;
	}

	public void setCommentAuthorUserImage(String commentAuthorUserImage) {
		this.commentAuthorUserImage = commentAuthorUserImage;
	}


}
