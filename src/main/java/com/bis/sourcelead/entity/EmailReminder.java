package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.List;
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

@Entity
@Table(name = "EMAIL_REMINDER")
public class EmailReminder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "SCENARIO_NAME")
	private String scenarionName;

	@Column(name = "SCENARIO_ID")
	private Long scenarioId;

	@Column(name = "NOTIFY_TO", columnDefinition = "TEXT default NULL")
	private String notifyTo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATIONS_ID")
	private OrgLocations orgLocationsId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TABS_ID")
	private Tabs tabsId;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;
	
	@Column(name = "ROLES", columnDefinition = "TEXT default NULL")
	private String roles;

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "emailReminder")
	private List<EmailReminderHierarchy> emailReminderHierarchyList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getScenarionName() {
		return scenarionName;
	}

	public void setScenarionName(String scenarionName) {
		this.scenarionName = scenarionName;
	}

	public String getNotifyTo() {
		return notifyTo;
	}

	public void setNotifyTo(String notifyTo) {
		this.notifyTo = notifyTo;
	}

	public Long getScenarioId() {
		return scenarioId;
	}

	public void setScenarioId(Long scenarioId) {
		this.scenarioId = scenarioId;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<EmailReminderHierarchy> getEmailReminderHierarchyList() {
		return emailReminderHierarchyList;
	}

	public void setEmailReminderHierarchyList(List<EmailReminderHierarchy> emailReminderHierarchyList) {
		this.emailReminderHierarchyList = emailReminderHierarchyList;
	}

	public OrgLocations getOrgLocationsId() {
		return orgLocationsId;
	}

	public void setOrgLocationsId(OrgLocations orgLocationsId) {
		this.orgLocationsId = orgLocationsId;
	}

	public Tabs getTabsId() {
		return tabsId;
	}

	public void setTabsId(Tabs tabsId) {
		this.tabsId = tabsId;
	}

}
