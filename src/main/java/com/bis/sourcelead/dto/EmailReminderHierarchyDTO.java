package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.entity.User;

public class EmailReminderHierarchyDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long emailReminderId;
	private Long reminderdays;
	private String remindertime;
	private String reminderhierarchy;
	private Integer status;
	private Date createdDate;
	private User createdBy;
	private User updatedby;
	private String updatedDate;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEmailReminderId() {
		return emailReminderId;
	}
	public void setEmailReminderId(Long emailReminderId) {
		this.emailReminderId = emailReminderId;
	}
	public Long getReminderdays() {
		return reminderdays;
	}
	public void setReminderdays(Long reminderdays) {
		this.reminderdays = reminderdays;
	}
	public String getRemindertime() {
		return remindertime;
	}
	public void setRemindertime(String remindertime) {
		this.remindertime = remindertime;
	}
	public String getReminderhierarchy() {
		return reminderhierarchy;
	}
	public void setReminderhierarchy(String reminderhierarchy) {
		this.reminderhierarchy = reminderhierarchy;
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
	
}
