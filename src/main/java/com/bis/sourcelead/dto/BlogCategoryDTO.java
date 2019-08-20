package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.annotations.Required;

public class BlogCategoryDTO extends SearchDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long Id;
	
	@Required(desc = "category name")
	private String categoryName;
	
	@CleanHtml(escape=false)
	private String description;
	
	private String categoryUUID;
	
	private Integer status;
	
	private String createdDate;

	private String createdBy;

	private String updatedDate;

	private String updatedBy;
	
	private String blogCategoryId;
	
	private String isDefaultCategory;
	
	private int blogCount;
	
	private String href;
	
	private String orgLocationDateFormate;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryUUID() {
		return categoryUUID;
	}

	public void setCategoryUUID(String categoryUUID) {
		this.categoryUUID = categoryUUID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getBlogCategoryId() {
		return blogCategoryId;
	}

	public void setBlogCategoryId(String blogCategoryId) {
		this.blogCategoryId = blogCategoryId;
	}

	public int getBlogCount() {
		return blogCount;
	}

	public void setBlogCount(int blogCount) {
		this.blogCount = blogCount;
	}

	public String getIsDefaultCategory() {
		return isDefaultCategory;
	}

	public void setIsDefaultCategory(String isDefaultCategory) {
		this.isDefaultCategory = isDefaultCategory;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getOrgLocationDateFormate() {
		return orgLocationDateFormate;
	}

	public void setOrgLocationDateFormate(String orgLocationDateFormate) {
		this.orgLocationDateFormate = orgLocationDateFormate;
	}

	
}