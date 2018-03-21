package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "USER_MANUAL_LEVEL_TWO")
public class UserManualLevelTwo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@OneToMany(mappedBy = "UserManualLevelTwoId",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<UserManualLevelThree> userManualLevelThree = new HashSet<UserManualLevelThree>();
	
	@Column(name = "LEVEL_TWO_UUID")
	private String levelTwoUUID;
	
	@Column(name = "TITLE")
	private String title;
	
	
	@Column(name = "DESCRIPTION")
	@Type(type="text")
	private String description;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "USER_MANUAL_LEVEL_ONE_ID")
	private UserManualLevelOne UserManualLevelOneId;
	
	@Column(name = "ORDER_TWO")
	private Integer order;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_DATE")
    private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
    
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public UserManualLevelOne getUserManualLevelOneId() {
		return UserManualLevelOneId;
	}


	public void setUserManualLevelOneId(UserManualLevelOne userManualLevelOneId) {
		UserManualLevelOneId = userManualLevelOneId;
	}


	public Integer getOrder() {
		return order;
	}


	public void setOrder(Integer order) {
		this.order = order;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Date getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}


	public User getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}


	public User getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getLevelTwoUUID() {
		return levelTwoUUID;
	}


	public void setLevelTwoUUID(String levelTwoUUID) {
		this.levelTwoUUID = levelTwoUUID;
	}

}
