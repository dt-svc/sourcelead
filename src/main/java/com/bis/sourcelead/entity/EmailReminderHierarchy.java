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

import com.bis.sourcelead.payroll.entity.EmployeePayroll;

@Entity
@Table(name = "EMAIL_REMINDER_HIERARCHY")
public class EmailReminderHierarchy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "EMAIL_REMINDER_ID",nullable = false)
	private EmailReminder emailReminder;
	
	@Column(name = "REMINDER_DAYS")
	private Long reminderdays;
	
	@Column(name = "REMINDER_TIME")
	private String remindertime;
	
	@Column(name = "REMINDER_HIERARCHY")
	private String reminderhierarchy;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createddate;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	

	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public Date getCreateddate() {
		return createddate;
	}


	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
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


	public EmailReminder getEmailReminder() {
		return emailReminder;
	}


	public void setEmailReminder(EmailReminder emailReminder) {
		this.emailReminder = emailReminder;
	}
	
	
}
