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

@Entity
@Table(name = "SOURCELEAD_BLOGS")
public class SourceleadBlogs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "BLOG_TITLE")
	private String blogTitle;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	private BlogCategory categoryId;
	
	@Column(name="KEY_WORDS", columnDefinition = "LONGTEXT default NULL")
	private String keywords;
	
	@Column(name = "PUBLISH_ON")
	private Date publishOn;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name="IMG_FILE_URL", columnDefinition = "LONGTEXT default NULL")
	private String imgFileUrl;
	
	@Column(name = "BLOG_UUID")
	private String blogUUID;
	
	@Column(name="DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
	private String description;
	
	@Column(name="SHORT_DESCRIPTION")
	private String shortDescription;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "BLOG_STATUS")
	private String blogStatus;
	
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
	
	@Column(name = "HREF")
	private String href;
	
	
	@Transient
	private String blogImage;

	public String getBlogImage() {
		return blogImage;
	}

	public void setBlogImage(String blogImage) {
		this.blogImage = blogImage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public BlogCategory getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(BlogCategory categoryId) {
		this.categoryId = categoryId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Date getPublishOn() {
		return publishOn;
	}

	public void setPublishOn(Date publishOn) {
		this.publishOn = publishOn;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getImgFileUrl() {
		return imgFileUrl;
	}

	public void setImgFileUrl(String imgFileUrl) {
		this.imgFileUrl = imgFileUrl;
	}

	public String getBlogUUID() {
		return blogUUID;
	}

	public void setBlogUUID(String blogUUID) {
		this.blogUUID = blogUUID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getBlogStatus() {
		return blogStatus;
	}

	public void setBlogStatus(String blogStatus) {
		this.blogStatus = blogStatus;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
}
