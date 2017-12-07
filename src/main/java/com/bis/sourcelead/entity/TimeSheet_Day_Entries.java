package com.bis.sourcelead.entity;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TIMESHEET_Day_ENTRIES")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL,region="timeSheet_Day_Entries")
public class TimeSheet_Day_Entries implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TIMESHEET_ID") 
	private TimesheetSubmissions timesheetSubmissions;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne(fetch=FetchType.EAGER,cascade = {CascadeType.ALL})
	@JoinColumn(name = "PROJECT_TASK")
	private ProjectTasks projectTask;
	
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "TIMESHEET_DATE")
	private Date timesheetDate;
	
	@Column(name = "BILLABLE_HOURS")
	private String billableHours;
	
	 
	@Column(name = "NONBILLABLE_HOURS")
	private String nonBillableHours;
	  
	@Column(name = "TOTALHOURS")
	private String totalHours;
	 
	@Column(name = "COMMENTS", columnDefinition = "TEXT default NULL")
    private String comments;
	 
	@NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "CREATED_DATE")
	private Date createdDate;
		
    @Column(name = "CREATED_BY")
	private String createdBy;
		
    @NotNull
    @DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss") 
	@Column(name = "UPDATED_DATE")
    private Date updatedDate; 
		
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public TimesheetSubmissions getTimesheetSubmissions() {
		return timesheetSubmissions;
	}

	public void setTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) {
		this.timesheetSubmissions = timesheetSubmissions;
	}

	public ProjectTasks getProjectTask() {
		return projectTask;
	}

	public void setProjectTask(ProjectTasks projectTask) {
		this.projectTask = projectTask;
	}

	public Date getTimesheetDate() {
		return timesheetDate;
	}

	public void setTimesheetDate(Date timesheetDate) {
		this.timesheetDate = timesheetDate;
	}

	public String getBillableHours() {
		return billableHours;
	}

	public void setBillableHours(String billableHours) {
		this.billableHours = billableHours;
	}

	public String getNonBillableHours() {
		return nonBillableHours;
	}

	public void setNonBillableHours(String nonBillableHours) {
		this.nonBillableHours = nonBillableHours;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
