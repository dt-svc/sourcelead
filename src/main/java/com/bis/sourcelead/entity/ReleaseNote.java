package com.bis.sourcelead.entity;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "RELEASE_NOTE")
public class ReleaseNote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "RELEASE_NAME")
	private String releaseName;
	
	@Column(name = "RELEASE_DATE")
	private Date releaseDate;
	
	@Column(name = "RELEASE_PUBLISH_DATE")
	private Date publishDate;

	@Column(name="RELEASE_NOTES")
	private String releaseNotes;
	
	@Column(name="RELEASE_SHORT_DESCRPTION", columnDefinition = "LONGTEXT default NULL")
	private String shortDescrption;
	
	@Column(name="RELEASE_DELETE_REASON", columnDefinition = "TEXT default NULL")
	private String deleteReason;
	
	@Column(name="RELEASE_DESCRPTION", columnDefinition = "LONGTEXT default NULL")
	private String releaseDescrption;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "RELEASE_UUID")
	private String releaseUUID;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY", nullable = false)
	private User createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY", nullable = false)
	private User updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	 @Transient
	 @JsonProperty
	 private String releaseDateStr;
	 
	 
	 @Transient
	 @JsonProperty
	 private String publishDateStr;
	 

	public Long getId() {
		return id;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public String getReleaseNotes() {
		return releaseNotes;
	}

	public String getReleaseDescrption() {
		return releaseDescrption;
	}

	public Integer getStatus() {
		return status;
	}

	public String getReleaseUUID() {
		return releaseUUID;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public void setReleaseNotes(String releaseNotes) {
		this.releaseNotes = releaseNotes;
	}

	public void setReleaseDescrption(String releaseDescrption) {
		this.releaseDescrption = releaseDescrption;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setReleaseUUID(String releaseUUID) {
		this.releaseUUID = releaseUUID;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getReleaseDateStr() {
		return releaseDateStr;
	}

	public String getPublishDateStr() {
		return publishDateStr;
	}

	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}

	public void setPublishDateStr(String publishDateStr) {
		this.publishDateStr = publishDateStr;
	}

	public String getShortDescrption() {
		return shortDescrption;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setShortDescrption(String shortDescrption) {
		this.shortDescrption = shortDescrption;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}
	
}
