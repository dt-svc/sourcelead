package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * @author BIS_SL2
 *
 */
@Entity
@Table(name = "HOLIDAY")
public class Holiday implements Serializable {
private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGLOCATION_ID", nullable = false)
	private OrgLocations orgLocations;
	
	@Column(name = "HOLIDAY_NAME")
	private String holidayName;
	
	@Column(name = "FROM_DATE")
	private Date fromDate;
	
	@Column(name = "TO_DATE")
	private Date toDate;
	
	@Column(name = "FROM_HALF_DAY")
	private String fromHalfDayCheckBox;
	
	@Column(name = "FROM_HALF_DAY_AM_PM")
	private String fromHalfDayAmPm;
	
	@Column(name = "TO_HALF_DAY")
	private String toHalfDayCheckBox;
	
	@Column(name = "TO_HALF_DAY_AM_PM")
	private String toHalfDayAmPm;
	
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "BILLABLE_TYPE")
	private String billableType;
	
	@Column(name = "HOLIDAY_STATUS")
	private String holidayStatus;
	
	@Column(name = "DELETE_REASON",columnDefinition = "TEXT default NULL")
	private String deleteReason;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "HOLIDAY_UUID")
	private String holidayUUID;
	
	@Column(name = "ORG_AVG_HRS")
	private String orgAvgHrs;
	
	@Transient
	private String fromDateStr;
	
	@Transient
	private String toDateStr;
	
	@OneToMany(mappedBy="holiday",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonBackReference
    private Set<HolidayShift> holidayShift;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getFromHalfDayCheckBox() {
		return fromHalfDayCheckBox;
	}

	public void setFromHalfDayCheckBox(String fromHalfDayCheckBox) {
		this.fromHalfDayCheckBox = fromHalfDayCheckBox;
	}

	public String getFromHalfDayAmPm() {
		return fromHalfDayAmPm;
	}

	public void setFromHalfDayAmPm(String fromHalfDayAmPm) {
		this.fromHalfDayAmPm = fromHalfDayAmPm;
	}

	public String getToHalfDayCheckBox() {
		return toHalfDayCheckBox;
	}

	public void setToHalfDayCheckBox(String toHalfDayCheckBox) {
		this.toHalfDayCheckBox = toHalfDayCheckBox;
	}

	public String getToHalfDayAmPm() {
		return toHalfDayAmPm;
	}

	public void setToHalfDayAmPm(String toHalfDayAmPm) {
		this.toHalfDayAmPm = toHalfDayAmPm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHolidayStatus() {
		return holidayStatus;
	}

	public void setHolidayStatus(String holidayStatus) {
		this.holidayStatus = holidayStatus;
	}
	
	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFromDateStr() {
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getHolidayUUID()
	{
		return holidayUUID;
	}

	public void setHolidayUUID(String holidayUUID)
	{
		this.holidayUUID = holidayUUID;
	}

	public Set<HolidayShift> getHolidayShift() {
		return holidayShift;
	}

	public void setHolidayShift(Set<HolidayShift> holidayShift) {
		this.holidayShift = holidayShift;
	}

	public String getOrgAvgHrs()
	{
		return orgAvgHrs;
	}

	public void setOrgAvgHrs(String orgAvgHrs)
	{
		this.orgAvgHrs = orgAvgHrs;
	}

	public String getBillableType()
	{
		return billableType;
	}

	public void setBillableType(String billableType)
	{
		this.billableType = billableType;
	}
	
}
