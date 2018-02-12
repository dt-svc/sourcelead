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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LEAVE_MANAGEMENT_HISTORY")
public class LeaveManagementHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LEAVE_ID", nullable = false)
	private Leave leaveid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LEAVE_TYPE_ID", nullable = false)
	private LeaveType leavetypeId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPTION_ID", nullable = false)
	private Option optionId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOLIDAY_ID", nullable = false)
	private Holiday holidayId;	
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "FROM_STAGE")
	private String fromStage;
	
	@Column(name = "TO_STAGE")
	private String toStage;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	public Leave getLeaveid() {
		return leaveid;
	}

	public void setLeaveid(Leave leaveid) {
		this.leaveid = leaveid;
	}

	public LeaveType getLeavetypeId() {
		return leavetypeId;
	}

	public void setLeavetypeId(LeaveType leavetypeId) {
		this.leavetypeId = leavetypeId;
	}

	public Option getOptionId() {
		return optionId;
	}

	public void setOptionId(Option optionId) {
		this.optionId = optionId;
	}

	public Holiday getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(Holiday holidayId) {
		this.holidayId = holidayId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFromStage() {
		return fromStage;
	}

	public void setFromStage(String fromStage) {
		this.fromStage = fromStage;
	}

	public String getToStage() {
		return toStage;
	}

	public void setToStage(String toStage) {
		this.toStage = toStage;
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



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	
}
