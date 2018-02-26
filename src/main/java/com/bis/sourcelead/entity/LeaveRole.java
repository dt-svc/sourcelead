package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.UUID;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "LEAVES_ROLE")
public class LeaveRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JsonBackReference
	@JoinColumn(name = "LEAVES_OPTION_ID", nullable = false)
	private Option leavesOptionId;

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JsonBackReference
	@JoinColumn(name = "LEAVE_TYPE_ID", nullable = false)
	private LeaveType leaveTypeid;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JsonBackReference
	@JoinColumn(name = "RESTRICT_DAY_ID", nullable = false)
	private RestrictDay restrictDay;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private Roles roleId;

	@Column(name = "TYPE")
	private String type;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Option getLeavesOptionId() {
		return leavesOptionId;
	}

	public void setLeavesOptionId(Option leavesOptionId) {
		this.leavesOptionId = leavesOptionId;
	}

	public LeaveType getLeaveTypeid() {
		return leaveTypeid;
	}

	public void setLeaveTypeid(LeaveType leaveTypeid) {
		this.leaveTypeid = leaveTypeid;
	}

	public Roles getRoleId() {
		return roleId;
	}

	public void setRoleId(Roles roleId) {
		this.roleId = roleId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public RestrictDay getRestrictDay() {
		return restrictDay;
	}

	public void setRestrictDay(RestrictDay restrictDay) {
		this.restrictDay = restrictDay;
	}

	
}
