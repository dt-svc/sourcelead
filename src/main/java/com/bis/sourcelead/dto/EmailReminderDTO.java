package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bis.sourcelead.entity.EmailReminderHierarchy;
import com.bis.sourcelead.entity.User;

public class EmailReminderDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String scenarioName;
	private Long scenarioId;
	private String notifyTo;
	private Integer status;
	private Date createdDate;
	private User createdBy;
	private User updatedby;
	private String updatedDate;
	private Long orgLocationId;
	private Long tabsId;
	private List<EmailReminderHierarchyDTO> emailReminderHierarchyList;
	private String reminderTo;
	
	
	public String getReminderTo() {
		return reminderTo;
	}
	public void setReminderTo(String reminderTo) {
		this.reminderTo = reminderTo;
	}
	public Long getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	public Long getTabsId() {
		return tabsId;
	}
	public void setTabsId(Long tabsId) {
		this.tabsId = tabsId;
	}
	public User getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(User updatedby) {
		this.updatedby = updatedby;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	public Long getScenarioId() {
		return scenarioId;
	}
	public void setScenarioId(Long scenarioId) {
		this.scenarioId = scenarioId;
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
	public String getNotifyTo() {
		return notifyTo;
	}
	public void setNotifyTo(String notifyTo) {
		this.notifyTo = notifyTo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<EmailReminderHierarchyDTO> getEmailReminderHierarchyList() {
		return emailReminderHierarchyList;
	}
	public void setEmailReminderHierarchyList(List<EmailReminderHierarchyDTO> emailReminderHierarchyList) {
		this.emailReminderHierarchyList = emailReminderHierarchyList;
	}

}
