package com.bis.sourcelead.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
@SuppressWarnings("serial")
@Entity
@Table(name = "TIMESHEET_SUMMARY")
public class TimesheetSummary implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PROJECT_MEMBER_ID ")
	private ProjectApproverHierarchy projectmemberid ;
	
	@Column(name = "MONTH_YEAR_FREQUENCY ")
	@Enumerated(EnumType.STRING)
	private Submission_Status monthyearfrequency ;

	@Column(name = " START_DATE ")
	private Date  startdate ;
	
	@Column(name = " END_DATE ")
	private Date  enddate ;
	
	@Column(name = " PAY_RATE  ")
	private Float  payrate  ;
	 
	@Column(name = "NO_OF_WEEKS ")
    private Long noofweeks ;
	
	@Column(name = "WEEK_1_FREQUENCY ")
    private String week1frequency ;
	
	@Column(name = "WEEK_2_FREQUENCY ")
    private String week2frequency ;
	
	
	@Column(name = "WEEK_3_FREQUENCY ")
    private String week3frequency ;
	
	@Column(name = "WEEK_4_FREQUENCY ")
    private String week4frequency ;
	
	@Column(name = "WEEK_5_FREQUENCY ")
    private String week5frequency ;
	
	@Column(name = "WEEK_6_FREQUENCY ")
    private String week6frequency ;

	@Column(name = "WEEK_1_TOTAL_HOURS  ")
    private Float week1totalhours  ;

	@Column(name = "WEEK_2_TOTAL_HOURS  ")
    private Float week2totalhours  ;

	@Column(name = "WEEK_3_TOTAL_HOURS  ")
    private Float week3totalhours  ;

	@Column(name = "WEEK_4_TOTAL_HOURS  ")
    private Float week4totalhours  ;

	@Column(name = "WEEK_5_TOTAL_HOURS  ")
    private Float week5totalhours  ;

	@Column(name = "WEEK_6_TOTAL_HOURS  ")
    private Float week6totalhours  ;
	
	@Column(name = "WEEK_1_BILLABLE_HOURS   ")
    private Float week1billablehours   ;

	@Column(name = "WEEK_2_BILLABLE_HOURS   ")
    private Float week2billablehours   ;

	@Column(name = "WEEK_3_BILLABLE_HOURS   ")
    private Float week3billablehours   ;

	@Column(name = "WEEK_4_BILLABLE_HOURS   ")
    private Float week4billablehours   ;

	@Column(name = "WEEK_5_BILLABLE_HOURS   ")
    private Float week5billablehours   ;

	@Column(name = "WEEK_6_BILLABLE_HOURS   ")
    private Float week6billablehours   ;

	@Column(name = "WEEK_1_NON_BILLABLE_HOURS   ")
    private Float week1nonbillablehours   ;

	@Column(name = "WEEK_2_NON_BILLABLE_HOURS   ")
    private Float week2nonbillablehours   ;

	@Column(name = "WEEK_3_NON_BILLABLE_HOURS   ")
    private Float week3nonbillablehours   ;

	@Column(name = "WEEK_4_NON_BILLABLE_HOURS   ")
    private Float week4nonbillablehours   ;

	@Column(name = "WEEK_5_NON_BILLABLE_HOURS   ")
    private Float week5nonbillablehours   ;

	@Column(name = "WEEK_6_NON_BILLABLE_HOURS   ")
    private Float week6nonbillablehours   ;
	
	@Column(name = "WEEK_1_APPROVAL_STATUS ")
	@Enumerated(EnumType.STRING)
    private Submission_ApprovalStatus week1approvalstatus ;

	@Column(name = "WEEK_2_APPROVAL_STATUS ")
	@Enumerated(EnumType.STRING)
    private Submission_ApprovalStatus week2approvalstatus ;

	@Column(name = "WEEK_3_APPROVAL_STATUS ")
	@Enumerated(EnumType.STRING)
    private Submission_ApprovalStatus week3approvalstatus ;

	@Column(name = "WEEK_4_APPROVAL_STATUS ")
	@Enumerated(EnumType.STRING)
    private Submission_ApprovalStatus week4approvalstatus ;

	@Column(name = "WEEK_5_APPROVAL_STATUS ")
	@Enumerated(EnumType.STRING)
    private Submission_ApprovalStatus week5approvalstatus ;

	@Column(name = "WEEK_6_APPROVAL_STATUS ")
	@Enumerated(EnumType.STRING)
    private Submission_ApprovalStatus week6approvalstatus ;
	
	@Column(name = "STATUS ")
	private Long status ;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY ", nullable = false)
	private User createdBy ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectApproverHierarchy getProjectmemberid() {
		return projectmemberid;
	}

	public void setProjectmemberid(ProjectApproverHierarchy projectmemberid) {
		this.projectmemberid = projectmemberid;
	}

	public Submission_Status getMonthyearfrequency() {
		return monthyearfrequency;
	}

	public void setMonthyearfrequency(Submission_Status monthyearfrequency) {
		this.monthyearfrequency = monthyearfrequency;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Float getPayrate() {
		return payrate;
	}

	public void setPayrate(Float payrate) {
		this.payrate = payrate;
	}

	public Long getNoofweeks() {
		return noofweeks;
	}

	public void setNoofweeks(Long noofweeks) {
		this.noofweeks = noofweeks;
	}

	public String getWeek1frequency() {
		return week1frequency;
	}

	public void setWeek1frequency(String week1frequency) {
		this.week1frequency = week1frequency;
	}

	public String getWeek2frequency() {
		return week2frequency;
	}

	public void setWeek2frequency(String week2frequency) {
		this.week2frequency = week2frequency;
	}

	public String getWeek3frequency() {
		return week3frequency;
	}

	public void setWeek3frequency(String week3frequency) {
		this.week3frequency = week3frequency;
	}

	public String getWeek4frequency() {
		return week4frequency;
	}

	public void setWeek4frequency(String week4frequency) {
		this.week4frequency = week4frequency;
	}

	public String getWeek5frequency() {
		return week5frequency;
	}

	public void setWeek5frequency(String week5frequency) {
		this.week5frequency = week5frequency;
	}

	public String getWeek6frequency() {
		return week6frequency;
	}

	public void setWeek6frequency(String week6frequency) {
		this.week6frequency = week6frequency;
	}

	public Float getWeek1totalhours() {
		return week1totalhours;
	}

	public void setWeek1totalhours(Float week1totalhours) {
		this.week1totalhours = week1totalhours;
	}

	public Float getWeek2totalhours() {
		return week2totalhours;
	}

	public void setWeek2totalhours(Float week2totalhours) {
		this.week2totalhours = week2totalhours;
	}

	public Float getWeek3totalhours() {
		return week3totalhours;
	}

	public void setWeek3totalhours(Float week3totalhours) {
		this.week3totalhours = week3totalhours;
	}

	public Float getWeek4totalhours() {
		return week4totalhours;
	}

	public void setWeek4totalhours(Float week4totalhours) {
		this.week4totalhours = week4totalhours;
	}

	public Float getWeek5totalhours() {
		return week5totalhours;
	}

	public void setWeek5totalhours(Float week5totalhours) {
		this.week5totalhours = week5totalhours;
	}

	public Float getWeek6totalhours() {
		return week6totalhours;
	}

	public void setWeek6totalhours(Float week6totalhours) {
		this.week6totalhours = week6totalhours;
	}

	public Float getWeek1billablehours() {
		return week1billablehours;
	}

	public void setWeek1billablehours(Float week1billablehours) {
		this.week1billablehours = week1billablehours;
	}

	public Float getWeek2billablehours() {
		return week2billablehours;
	}

	public void setWeek2billablehours(Float week2billablehours) {
		this.week2billablehours = week2billablehours;
	}

	public Float getWeek3billablehours() {
		return week3billablehours;
	}

	public void setWeek3billablehours(Float week3billablehours) {
		this.week3billablehours = week3billablehours;
	}

	public Float getWeek4billablehours() {
		return week4billablehours;
	}

	public void setWeek4billablehours(Float week4billablehours) {
		this.week4billablehours = week4billablehours;
	}

	public Float getWeek5billablehours() {
		return week5billablehours;
	}

	public void setWeek5billablehours(Float week5billablehours) {
		this.week5billablehours = week5billablehours;
	}

	public Float getWeek6billablehours() {
		return week6billablehours;
	}

	public void setWeek6billablehours(Float week6billablehours) {
		this.week6billablehours = week6billablehours;
	}

	public Float getWeek1nonbillablehours() {
		return week1nonbillablehours;
	}

	public void setWeek1nonbillablehours(Float week1nonbillablehours) {
		this.week1nonbillablehours = week1nonbillablehours;
	}

	public Float getWeek2nonbillablehours() {
		return week2nonbillablehours;
	}

	public void setWeek2nonbillablehours(Float week2nonbillablehours) {
		this.week2nonbillablehours = week2nonbillablehours;
	}

	public Float getWeek3nonbillablehours() {
		return week3nonbillablehours;
	}

	public void setWeek3nonbillablehours(Float week3nonbillablehours) {
		this.week3nonbillablehours = week3nonbillablehours;
	}

	public Float getWeek4nonbillablehours() {
		return week4nonbillablehours;
	}

	public void setWeek4nonbillablehours(Float week4nonbillablehours) {
		this.week4nonbillablehours = week4nonbillablehours;
	}

	public Float getWeek5nonbillablehours() {
		return week5nonbillablehours;
	}

	public void setWeek5nonbillablehours(Float week5nonbillablehours) {
		this.week5nonbillablehours = week5nonbillablehours;
	}

	public Float getWeek6nonbillablehours() {
		return week6nonbillablehours;
	}

	public void setWeek6nonbillablehours(Float week6nonbillablehours) {
		this.week6nonbillablehours = week6nonbillablehours;
	}

	

	public Submission_ApprovalStatus getWeek1approvalstatus() {
		return week1approvalstatus;
	}

	public void setWeek1approvalstatus(Submission_ApprovalStatus week1approvalstatus) {
		this.week1approvalstatus = week1approvalstatus;
	}

	public Submission_ApprovalStatus getWeek2approvalstatus() {
		return week2approvalstatus;
	}

	public void setWeek2approvalstatus(Submission_ApprovalStatus week2approvalstatus) {
		this.week2approvalstatus = week2approvalstatus;
	}

	public Submission_ApprovalStatus getWeek3approvalstatus() {
		return week3approvalstatus;
	}

	public void setWeek3approvalstatus(Submission_ApprovalStatus week3approvalstatus) {
		this.week3approvalstatus = week3approvalstatus;
	}

	public Submission_ApprovalStatus getWeek4approvalstatus() {
		return week4approvalstatus;
	}

	public void setWeek4approvalstatus(Submission_ApprovalStatus week4approvalstatus) {
		this.week4approvalstatus = week4approvalstatus;
	}

	public Submission_ApprovalStatus getWeek5approvalstatus() {
		return week5approvalstatus;
	}

	public void setWeek5approvalstatus(Submission_ApprovalStatus week5approvalstatus) {
		this.week5approvalstatus = week5approvalstatus;
	}

	public Submission_ApprovalStatus getWeek6approvalstatus() {
		return week6approvalstatus;
	}

	public void setWeek6approvalstatus(Submission_ApprovalStatus week6approvalstatus) {
		this.week6approvalstatus = week6approvalstatus;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}

	public User getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(User updatedby) {
		this.updatedby = updatedby;
	}

	@Column(name = "CREATED_DATE ")
	private Date createddate ;
	
	@Column(name = "UPDATED_DATE  ")
	private Date updateddate  ;
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY ", nullable = false)
	private User updatedby ;

	
	
	
	
	
}
