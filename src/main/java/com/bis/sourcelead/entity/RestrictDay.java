package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Rakshith
 */

@Entity
@Table(name = "RESTRICT_DAY")
public class RestrictDay implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGLOCATION_ID")
	private OrgLocations orgLocationId;

	@Column(name = "NAME")
	private String restrictName;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "REASON" , columnDefinition = "TEXT default NULL")
	private String reason;
	
	@Column(name = "APPLICABLE_TO_ALL")
	private String applicableToAll;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "DELETE_REASON", columnDefinition = "TEXT default NULL")
	private String deleteReason;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "RESTRICT_UUID")
	private String restrictUUID;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "restrictDay")
	private Set<LeaveRole> restrictDayRolesList;

	@Transient
	@JsonProperty
	private String startDateStr;
	
	@Transient
	@JsonProperty
	private String endDateStr;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getRestrictName() {
		return restrictName;
	}

	public void setRestrictName(String restrictName) {
		this.restrictName = restrictName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getApplicableToAll() {
		return applicableToAll;
	}

	public void setApplicableToAll(String applicableToAll) {
		this.applicableToAll = applicableToAll;
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

	public String getStartDateStr() {
		if(orgLocationId != null) {
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			startDateStr = dateFormat.format(startDate);
		}
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		if(orgLocationId != null) {
		SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
		endDateStr = dateFormat.format(endDate);
		}
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}


	public Set<LeaveRole> getRestrictDayRolesList() {
		return restrictDayRolesList;
	}

	public void setRestrictDayRolesList(Set<LeaveRole> restrictDayRolesList) {
		this.restrictDayRolesList = restrictDayRolesList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getRestrictUUID()
	{
		return restrictUUID;
	}

	public void setRestrictUUID(String restrictUUID)
	{
		this.restrictUUID = restrictUUID;
	}

}
