package com.bis.sourcelead.entity;

import java.math.BigInteger;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pavan
 *
 */
@Entity
@Table(name = "LEAVE_TYPE_HISTORY")
public class LeaveTypeHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "LEAVE_TYPE_ID")
	private Long leaveTypeId;
	
	@Column(name = "ORGLOCATION_ID")
	private Long orgLocations;
	
	@Column(name = "DEPARTMENT_ID")
	private Long departmentId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DURATION")
	private String duration;
	
	@Column(name = "HOURS")
	private String hours;
	
	@Column(name = "TYPE_OF_HOURS")
	private String typeOfHours;
	
	@Column(name = "LEAVE_TYPE")
	private String leaveType;
	
	@Column(name = "APPLICABLE_TO_USERS")
	private String applicableToUsers;
	
	@Column(name = "LEAVES_CARRYFORWARD")
	private String leavesCarryforward;
	
	@Column(name = "NUMBEFR_OF_LEAVES_PERYEAR")
	private String numbefrOfLeavesPeryear;
	
	@Column(name = "NUMBEFR_OF_LEAVES_PERMONTH")
	private String numbefrOfLeavesPerMonth;
	
	@Column(name = "DESCRIPTION",columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "COLOR_CODE")
	private String colorCode;

	@Column(name = "DEPARTMENT_IDS")
	private String departmentIds;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "LEAVE_TYPE_UUID")
	private String leaveTypeUUID;
	
	@Column(name = "DELETE_REASON",columnDefinition = "TEXT default NULL")
	private String deleteReason;
	
	@Column(name = "LEAVE_POLICY")
	private String leavePolicy;
	
	@Column(name = "POLICY_DESCRIPTION",columnDefinition = "TEXT default NULL")
	private String policyDescription;
	
	@Column(name = "POLICY_NAME")
	private String policyName;
	
	@Column(name = "VISIBILITY_TO_ALL_USERS")
	private String visibilityToAllUsers;
	
	@Column(name = "PERIOD")
	private String period;
	
	@Column(name = "LEAVE_EFFECTIVE")
	private String leaveEffective;
	
	@Column(name = "NEW_JOINEE")
	private Integer newJoinee;
	
	@Column(name = "EXPERIENCED_TYPE")
	private String experiencedType;
	
	@Column(name = "EXPERIENCED_DURATION")
	private Integer experiencedDuration;
	
	@Column(name = "EXPERIENCED_APPLICABLE_DAYS")
	private Integer experiencedApplicableDays;
	
	@Column(name = "REASON_REQUIRED")
	private String reasonRequired;
	
	@Column(name = "ATTACHMENT_REQUIRED")
	private String attachmentRequired;
	
	@Column(name = "OVERDRAW_LEAVES")
	private String overdrawLeaves;
	
	@Column(name = "DONATE_LEAVE")
	private String donateLeave;
	
	@Column(name = "PRIOR_LEAVE_APPLYING")
	private String priorLeaveApplying;
	
	@Column(name = "PRIOR_DAYS")
	private Integer priorDays;
	
	@Column(name = "LEAVE_DURATION")
	private String leaveDuration;
	
	@Column(name = "MIN_DAYS")
	private Float minDays;
	
	@Column(name = "MAX_DAYS")
	private Float maxDays;
	
	@Column(name = "NO_OF_DAYS")
	private Integer noOfDays;
	
	@Column(name = "NO_OF_DAYS_PER_PERIOD")
	private Float noOfDaysPerPeriod;
	
	@Column(name = "AUTO_APPROVE")
	private String autoApprove;
	
	@Column(name = "AUTO_APPROVE_TYPE")
	private String autoApproveType;
	
	@Column(name = "AUTO_APPROVE_TYPE_LIST")
	private String autoApproveTypeList;
	
	@Column(name = "TIMESHEET_AUTOFILL")
	private String timeSheetAutofill;
	
	@Column(name = "EFFECTIVE_GENDER")
	private String effectiveGender;
	
	@Column(name = "INCLUDE_WEEKOFF")
	private String includeWeekoff;
	
	@Column(name = "INCLUDE_HOLIDAYS")
	private String includeHolidays;
	
	@Column(name = "VISIBLE_IN_BALANCE_LEAVES")
	private String visibleInBalanceLeaves;
	
	@Column(name = "LEAVE_EMP_TYPE")
	private String leaveEmpType;
	
	@Column(name = "ALLOWABILITY")
	private String allowability;
	
	@Column(name = "MAX_DAYS_TO_CARRYFORWARD")
	private Integer maxDaysToCarryforward;
	
	@Column(name = "INCLUDE_IN_PAYROLL")
	private String includeInPayroll;
	
	@Column(name = "LEAVE_ENCASHMENT")
	private String leaveEncashment;
	
	@Column(name = "LEAVE_ENCASH_MIN_DAYS")
	private Integer leaveEncashMinDays;
	
	@Column(name = "LEAVE_ENCASH_MAX_DAYS")
	private Integer leaveEncashMaxDays;
	
	@Column(name = "ACCURAL_POLICY")
	private String accuralPolicy;
	
	@Column(name = "NEW_JOINEE_PER_PERIOD")
	private Float newJoinPerPeriod;
	
	@Column(name = "EXPERIENCED_PER_PERIOD")
	private Float experiencedPerPeriod;
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(Long leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public Long getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(Long orgLocations) {
		this.orgLocations = orgLocations;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDuration()
	{
		return duration;
	}

	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	public String getHours()
	{
		return hours;
	}

	public void setHours(String hours)
	{
		this.hours = hours;
	}

	public String getTypeOfHours()
	{
		return typeOfHours;
	}

	public void setTypeOfHours(String typeOfHours)
	{
		this.typeOfHours = typeOfHours;
	}

	public String getLeaveType()
	{
		return leaveType;
	}

	public void setLeaveType(String leaveType)
	{
		this.leaveType = leaveType;
	}

	public String getApplicableToUsers()
	{
		return applicableToUsers;
	}

	public void setApplicableToUsers(String applicableToUsers)
	{
		this.applicableToUsers = applicableToUsers;
	}

	public String getLeavesCarryforward()
	{
		return leavesCarryforward;
	}

	public void setLeavesCarryforward(String leavesCarryforward)
	{
		this.leavesCarryforward = leavesCarryforward;
	}

	public String getNumbefrOfLeavesPeryear()
	{
		return numbefrOfLeavesPeryear;
	}

	public void setNumbefrOfLeavesPeryear(String numbefrOfLeavesPeryear)
	{
		this.numbefrOfLeavesPeryear = numbefrOfLeavesPeryear;
	}

	public String getNumbefrOfLeavesPerMonth()
	{
		return numbefrOfLeavesPerMonth;
	}

	public void setNumbefrOfLeavesPerMonth(String numbefrOfLeavesPerMonth)
	{
		this.numbefrOfLeavesPerMonth = numbefrOfLeavesPerMonth;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getColorCode()
	{
		return colorCode;
	}

	public void setColorCode(String colorCode)
	{
		this.colorCode = colorCode;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getLeaveTypeUUID()
	{
		return leaveTypeUUID;
	}

	public void setLeaveTypeUUID(String leaveTypeUUID)
	{
		this.leaveTypeUUID = leaveTypeUUID;
	}

	public String getDeleteReason()
	{
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason)
	{
		this.deleteReason = deleteReason;
	}

	public String getLeavePolicy()
	{
		return leavePolicy;
	}

	public void setLeavePolicy(String leavePolicy)
	{
		this.leavePolicy = leavePolicy;
	}

	public String getPolicyDescription()
	{
		return policyDescription;
	}

	public void setPolicyDescription(String policyDescription)
	{
		this.policyDescription = policyDescription;
	}

	public String getPolicyName()
	{
		return policyName;
	}

	public void setPolicyName(String policyName)
	{
		this.policyName = policyName;
	}

	public String getVisibilityToAllUsers()
	{
		return visibilityToAllUsers;
	}

	public void setVisibilityToAllUsers(String visibilityToAllUsers)
	{
		this.visibilityToAllUsers = visibilityToAllUsers;
	}

	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	public String getLeaveEffective()
	{
		return leaveEffective;
	}

	public void setLeaveEffective(String leaveEffective)
	{
		this.leaveEffective = leaveEffective;
	}

	public Integer getNewJoinee()
	{
		return newJoinee;
	}

	public void setNewJoinee(Integer newJoinee)
	{
		this.newJoinee = newJoinee;
	}

	public String getExperiencedType()
	{
		return experiencedType;
	}

	public void setExperiencedType(String experiencedType)
	{
		this.experiencedType = experiencedType;
	}

	public Integer getExperiencedDuration()
	{
		return experiencedDuration;
	}

	public void setExperiencedDuration(Integer experiencedDuration)
	{
		this.experiencedDuration = experiencedDuration;
	}

	public Integer getExperiencedApplicableDays()
	{
		return experiencedApplicableDays;
	}

	public void setExperiencedApplicableDays(Integer experiencedApplicableDays)
	{
		this.experiencedApplicableDays = experiencedApplicableDays;
	}

	public String getReasonRequired()
	{
		return reasonRequired;
	}

	public void setReasonRequired(String reasonRequired)
	{
		this.reasonRequired = reasonRequired;
	}

	public String getAttachmentRequired()
	{
		return attachmentRequired;
	}

	public void setAttachmentRequired(String attachmentRequired)
	{
		this.attachmentRequired = attachmentRequired;
	}

	public String getOverdrawLeaves()
	{
		return overdrawLeaves;
	}

	public void setOverdrawLeaves(String overdrawLeaves)
	{
		this.overdrawLeaves = overdrawLeaves;
	}

	public String getDonateLeave()
	{
		return donateLeave;
	}

	public void setDonateLeave(String donateLeave)
	{
		this.donateLeave = donateLeave;
	}

	public String getPriorLeaveApplying()
	{
		return priorLeaveApplying;
	}

	public void setPriorLeaveApplying(String priorLeaveApplying)
	{
		this.priorLeaveApplying = priorLeaveApplying;
	}

	public Integer getPriorDays()
	{
		return priorDays;
	}

	public void setPriorDays(Integer priorDays)
	{
		this.priorDays = priorDays;
	}

	public String getLeaveDuration()
	{
		return leaveDuration;
	}

	public void setLeaveDuration(String leaveDuration)
	{
		this.leaveDuration = leaveDuration;
	}

	public Float getMinDays()
	{
		return minDays;
	}

	public void setMinDays(Float minDays)
	{
		this.minDays = minDays;
	}

	public Float getMaxDays()
	{
		return maxDays;
	}

	public void setMaxDays(Float maxDays)
	{
		this.maxDays = maxDays;
	}

	public Integer getNoOfDays()
	{
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays)
	{
		this.noOfDays = noOfDays;
	}

	public Float getNoOfDaysPerPeriod()
	{
		return noOfDaysPerPeriod;
	}

	public void setNoOfDaysPerPeriod(Float noOfDaysPerPeriod)
	{
		this.noOfDaysPerPeriod = noOfDaysPerPeriod;
	}

	public String getAutoApprove()
	{
		return autoApprove;
	}

	public void setAutoApprove(String autoApprove)
	{
		this.autoApprove = autoApprove;
	}

	public String getAutoApproveType()
	{
		return autoApproveType;
	}

	public void setAutoApproveType(String autoApproveType)
	{
		this.autoApproveType = autoApproveType;
	}

	public String getAutoApproveTypeList()
	{
		return autoApproveTypeList;
	}

	public void setAutoApproveTypeList(String autoApproveTypeList)
	{
		this.autoApproveTypeList = autoApproveTypeList;
	}

	public String getTimeSheetAutofill()
	{
		return timeSheetAutofill;
	}

	public void setTimeSheetAutofill(String timeSheetAutofill)
	{
		this.timeSheetAutofill = timeSheetAutofill;
	}

	public String getEffectiveGender()
	{
		return effectiveGender;
	}

	public void setEffectiveGender(String effectiveGender)
	{
		this.effectiveGender = effectiveGender;
	}

	public String getIncludeWeekoff()
	{
		return includeWeekoff;
	}

	public void setIncludeWeekoff(String includeWeekoff)
	{
		this.includeWeekoff = includeWeekoff;
	}

	public String getIncludeHolidays()
	{
		return includeHolidays;
	}

	public void setIncludeHolidays(String includeHolidays)
	{
		this.includeHolidays = includeHolidays;
	}

	public String getVisibleInBalanceLeaves()
	{
		return visibleInBalanceLeaves;
	}

	public void setVisibleInBalanceLeaves(String visibleInBalanceLeaves)
	{
		this.visibleInBalanceLeaves = visibleInBalanceLeaves;
	}

	public String getLeaveEmpType()
	{
		return leaveEmpType;
	}

	public void setLeaveEmpType(String leaveEmpType)
	{
		this.leaveEmpType = leaveEmpType;
	}

	public String getAllowability()
	{
		return allowability;
	}

	public void setAllowability(String allowability)
	{
		this.allowability = allowability;
	}

	public Integer getMaxDaysToCarryforward()
	{
		return maxDaysToCarryforward;
	}

	public void setMaxDaysToCarryforward(Integer maxDaysToCarryforward)
	{
		this.maxDaysToCarryforward = maxDaysToCarryforward;
	}

	public String getIncludeInPayroll()
	{
		return includeInPayroll;
	}

	public void setIncludeInPayroll(String includeInPayroll)
	{
		this.includeInPayroll = includeInPayroll;
	}

	public String getLeaveEncashment()
	{
		return leaveEncashment;
	}

	public void setLeaveEncashment(String leaveEncashment)
	{
		this.leaveEncashment = leaveEncashment;
	}

	public Integer getLeaveEncashMinDays()
	{
		return leaveEncashMinDays;
	}

	public void setLeaveEncashMinDays(Integer leaveEncashMinDays)
	{
		this.leaveEncashMinDays = leaveEncashMinDays;
	}

	public Integer getLeaveEncashMaxDays()
	{
		return leaveEncashMaxDays;
	}

	public void setLeaveEncashMaxDays(Integer leaveEncashMaxDays)
	{
		this.leaveEncashMaxDays = leaveEncashMaxDays;
	}

	public String getAccuralPolicy()
	{
		return accuralPolicy;
	}

	public void setAccuralPolicy(String accuralPolicy)
	{
		this.accuralPolicy = accuralPolicy;
	}

	public Float getNewJoinPerPeriod() {
		return newJoinPerPeriod;
	}

	public Float getExperiencedPerPeriod() {
		return experiencedPerPeriod;
	}

	public void setNewJoinPerPeriod(Float newJoinPerPeriod) {
		this.newJoinPerPeriod = newJoinPerPeriod;
	}

	public void setExperiencedPerPeriod(Float experiencedPerPeriod) {
		this.experiencedPerPeriod = experiencedPerPeriod;
	}

	public String getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
	}
}
