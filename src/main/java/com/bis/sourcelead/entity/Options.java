package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LEAVE_OPTIONS", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "leaveoptions")
public class Options {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "ORGLOCATION_ID", nullable = false)
	private OrgLocations orgLocations;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID", nullable = false)
	private Department departmentId;

	@Column(name = "WEEKENDS")
	private String weekends;

	@Column(name = "HALFDAY_REQUEST")
	private String halfdayRequest;

	@Column(name = "LEAVE_TRANSFER")
	private String leaveTransfer;

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
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "leavesOptionId")
	private Set<LeaveRoles> leaverolesSet;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}



	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public Department getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Department departmentId) {
		this.departmentId = departmentId;
	}

	public String getWeekends() {
		return weekends;
	}

	public void setWeekends(String weekends) {
		this.weekends = weekends;
	}

	public String getHalfdayRequest() {
		return halfdayRequest;
	}

	public void setHalfdayRequest(String halfdayRequest) {
		this.halfdayRequest = halfdayRequest;
	}

	public String getLeaveTransfer() {
		return leaveTransfer;
	}

	public void setLeaveTransfer(String leaveTransfer) {
		this.leaveTransfer = leaveTransfer;
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

	public Set<LeaveRoles> getLeaverolesSet() {
		return leaverolesSet;
	}

	public void setLeaverolesSet(Set<LeaveRoles> leaverolesSet) {
		this.leaverolesSet = leaverolesSet;
	}

	
}
