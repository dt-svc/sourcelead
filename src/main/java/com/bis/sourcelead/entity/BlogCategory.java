package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "BLOG_CATEGORY")
public class BlogCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	
	@Column(name="DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
	private String description;
	
	@Column(name = "IS_DEFAULT_CATEGORY")
	private String isDefaultCategory;
	
	@Column(name = "CATEGORY_UUID")
	private String categoryUUID;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY", nullable = false)
	@JsonBackReference
	private User createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY", nullable = false)
	@JsonBackReference
	private User updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@OneToMany(mappedBy = "categoryId")
	private List<SourceleadBlogs> blogList;
	
	@Column(name = "HREF")
	private String href;
	
	@Transient
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<SourceleadBlogs> getBlogList() {
		List<SourceleadBlogs> sbList = new LinkedList<>();
		for(SourceleadBlogs sb : blogList) {
			if(sb.getStatus()==1)
				sbList.add(sb);
		}
		return sbList;
	}

	public void setBlogList(List<SourceleadBlogs> blogList) {
		this.blogList = blogList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getIsDefaultCategory() {
		return isDefaultCategory;
	}

	public void setIsDefaultCategory(String isDefaultCategory) {
		this.isDefaultCategory = isDefaultCategory;
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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
}
