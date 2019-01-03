package com.bis.sourcelead.entity;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.bis.sourcelead.payroll.entity.EmployerPayrollTax;


@Entity
@Table(name = "EMAIL_REMINDER")
public class EmailReminder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "SCENARIO_NAME")
	private String scenarionName;
	
	@Column(name = "SCENARIO_STATUS")
	private String scenarioStatus = "N";
	
	
	@Column(name = "SCENARIO_ID")
	private Long scenarioId;
	
	
	@Column(name = "TIME")
	private String time;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "emailReminder")
	private Set<EmailReminderHierarchy> emailReminderHierarchyList;


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


	public String getScenarioStatus() {
		return scenarioStatus;
	}


	public void setScenarioStatus(String scenarioStatus) {
		this.scenarioStatus = scenarioStatus;
	}


	public Long getScenarioId() {
		return scenarioId;
	}


	public void setScenarioId(Long scenarioId) {
		this.scenarioId = scenarioId;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
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


	public Set<EmailReminderHierarchy> getEmailReminderHierarchyList() {
		return emailReminderHierarchyList;
	}


	public void setEmailReminderHierarchyList(Set<EmailReminderHierarchy> emailReminderHierarchyList) {
		this.emailReminderHierarchyList = emailReminderHierarchyList;
	}

	
}
