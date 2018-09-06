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
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "SOURCELEAD_ACTIONS")
public class SourceleadActions implements Serializable{

	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@Column(name = "ACTION_NAME")
	@Type(type="text")
	private String actionName;
	
	@Column(name = "ACTION_ROLE")
	private String actionRole;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAB_ID")
	private Tabs tabs;
	
	@Transient
	private String isActionConfigured;
	
	@Column(name = "STATUS", nullable = false)
   	private Integer status;
	
	@Column(name = "LOCATION_CODE")
   	private String locationCode;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionRole() {
		return actionRole;
	}

	public void setActionRole(String actionRole) {
		this.actionRole = actionRole;
	}

	public Tabs getTabs() {
		return tabs;
	}

	public void setTabs(Tabs tabs) {
		this.tabs = tabs;
	}

	public String getIsActionConfigured() {
		return isActionConfigured;
	}

	public void setIsActionConfigured(String isActionConfigured) {
		this.isActionConfigured = isActionConfigured;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLoctionCode(String loctionCode) {
		this.locationCode = loctionCode;
	}

	
	
	
}
