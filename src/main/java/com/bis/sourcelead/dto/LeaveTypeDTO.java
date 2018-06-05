package com.bis.sourcelead.dto;

import java.util.List;

import javax.persistence.Column;

import com.bis.sourcelead.annotations.Required;

public class LeaveTypeDTO {
	@Required(desc = "Name")
	private String name;
	@Required(desc = "Duration")
	private String duration;
	private String hours;
	private String hoursType;
	private String billingType;
	private String applicableToUsers;
	@Required(desc = "Department", dependsOn="applicableToUsers",parentValue="N")
	private String department;
	@Required(desc = "Roles", dependsOn="applicableToUsers",parentValue="N")
	private List<String> roles;
	private String carryforward;
	private String noOfLeavesPerYear;
	private String description;
	private String leavePolicy;
	@Required(desc = "Policy Name", dependsOn="leavePolicy",parentValue="Y")
	private String policyName;
	private String visibilityToAllUsers;
	@Required(desc = "Policy Description", dependsOn="leavePolicy",parentValue="Y")
	private String leavePolicyDescription;
	private String orgLocationId;
	private String noOfLeavesPerMonth;
	private String loginUserId;
	private long leaveTypeId;
	private String deleteReason;
	@Required(desc = "Color Code",dependsOn="leaveEffective",parentValue={"All Employees","Experienced"})
	private String colorCode;
	private int approvedCount;
	private int pendingCount;
	private int rejectedCount;
	private int cancelCount;
	private String numberOfDaysCount;
	private String status;
	@Required(desc = "Period")
	private String period;
	@Required(desc = "Leave Effective")
	private String leaveEffective;
	@Required(desc = "New Joinee",dependsOn="leaveEffective",parentValue="Experienced")
	private String newJoinee;
	@Required(desc = "Min Days",dependsOn="leaveEffective",parentValue="Experienced")
	private String experiencedType;
	@Required(desc = "Min Days",dependsOn="leaveEffective",parentValue="Experienced")
	private String experiencedDuration;
	@Required(desc = "Min Days",dependsOn="leaveEffective",parentValue="Experienced")
	private String experiencedApplicableDays;
	private String reasonRequired;
	private String attachmentRequired;
	private String overdrawLeaves;
	private String donateLeave;
	private String priorLeaveApplying;
	@Required(desc = "Min Days", dependsOn="priorLeaveApplying",parentValue="Y")
	private String priorDays;
	private String leaveDuration;
	@Required(desc = "Min Days", dependsOn="leaveDuration",parentValue="Y")
	private String minDays;
	@Required(desc = "Max Days", dependsOn="leaveDuration",parentValue="Y")
	private String maxDays;
	@Required(desc = "No. of Days",dependsOn="leaveEffective",parentValue="All Employees")
	private String noOfDays;
	private String leaveTypeUUID;
	
	private float totalApprovedCount;
	private float totalPendingCount;
	private float totalRejectedCount;
	private float totalCancelCount;
	private float totalNoOfDays;
	private String createdUser;
	private String updatedUser;
	
	private Float noOfDaysPerPeriod;
	private String autoApprove;
	private String autoApproveType;
	private List<String> autoApproveTypeList;
	private String timeSheetAutofill;
	private String effectiveGender;
	
	
	public String getLeaveTypeUUID()
	{
		return leaveTypeUUID;
	}
	public void setLeaveTypeUUID(String leaveTypeUUID)
	{
		this.leaveTypeUUID = leaveTypeUUID;
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
	public String getHoursType()
	{
		return hoursType;
	}
	public void setHoursType(String hoursType)
	{
		this.hoursType = hoursType;
	}
	public String getBillingType()
	{
		return billingType;
	}
	public void setBillingType(String billingType)
	{
		this.billingType = billingType;
	}
	public String getApplicableToUsers()
	{
		return applicableToUsers;
	}
	public void setApplicableToUsers(String applicableToUsers)
	{
		this.applicableToUsers = applicableToUsers;
	}
	public String getDepartment()
	{
		return department;
	}
	public void setDepartment(String department)
	{
		this.department = department;
	}
	public List<String> getRoles()
	{
		return roles;
	}
	public void setRoles(List<String> roles)
	{
		this.roles = roles;
	}
	public String getCarryforward()
	{
		return carryforward;
	}
	public void setCarryforward(String carryforward)
	{
		this.carryforward = carryforward;
	}
	public String getNoOfLeavesPerYear()
	{
		return noOfLeavesPerYear;
	}
	public void setNoOfLeavesPerYear(String noOfLeavesPerYear)
	{
		this.noOfLeavesPerYear = noOfLeavesPerYear;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getLeavePolicy()
	{
		return leavePolicy;
	}
	public void setLeavePolicy(String leavePolicy)
	{
		this.leavePolicy = leavePolicy;
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
	public String getLeavePolicyDescription()
	{
		return leavePolicyDescription;
	}
	public void setLeavePolicyDescription(String leavePolicyDescription)
	{
		this.leavePolicyDescription = leavePolicyDescription;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public String getNoOfLeavesPerMonth()
	{
		return noOfLeavesPerMonth;
	}
	public void setNoOfLeavesPerMonth(String noOfLeavesPerMonth)
	{
		this.noOfLeavesPerMonth = noOfLeavesPerMonth;
	}
	public String getLoginUserId()
	{
		return loginUserId;
	}
	public void setLoginUserId(String loginUserId)
	{
		this.loginUserId = loginUserId;
	}
	public long getLeaveTypeId()
	{
		return leaveTypeId;
	}
	public void setLeaveTypeId(long leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}
	public String getDeleteReason()
	{
		return deleteReason;
	}
	public void setDeleteReason(String deleteReason)
	{
		this.deleteReason = deleteReason;
	}
	public String getColorCode()
	{
		return colorCode;
	}
	public void setColorCode(String colorCode)
	{
		this.colorCode = colorCode;
	}
	public int getApprovedCount()
	{
		return approvedCount;
	}
	public void setApprovedCount(int approvedCount)
	{
		this.approvedCount = approvedCount;
	}
	public int getPendingCount()
	{
		return pendingCount;
	}
	public void setPendingCount(int pendingCount)
	{
		this.pendingCount = pendingCount;
	}
	public int getRejectedCount()
	{
		return rejectedCount;
	}
	public void setRejectedCount(int rejectedCount)
	{
		this.rejectedCount = rejectedCount;
	}
	public int getCancelCount()
	{
		return cancelCount;
	}
	public void setCancelCount(int cancelCount)
	{
		this.cancelCount = cancelCount;
	}
	public String getNumberOfDaysCount()
	{
		return numberOfDaysCount;
	}
	public void setNumberOfDaysCount(String numberOfDaysCount)
	{
		this.numberOfDaysCount = numberOfDaysCount;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
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
	
	public String getExperiencedType()
	{
		return experiencedType;
	}
	public void setExperiencedType(String experiencedType)
	{
		this.experiencedType = experiencedType;
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
	public String getLeaveDuration()
	{
		return leaveDuration;
	}
	public void setLeaveDuration(String leaveDuration)
	{
		this.leaveDuration = leaveDuration;
	}
	public String getNewJoinee()
	{
		return newJoinee;
	}
	public void setNewJoinee(String newJoinee)
	{
		this.newJoinee = newJoinee;
	}
	public String getExperiencedDuration()
	{
		return experiencedDuration;
	}
	public void setExperiencedDuration(String experiencedDuration)
	{
		this.experiencedDuration = experiencedDuration;
	}
	public String getExperiencedApplicableDays()
	{
		return experiencedApplicableDays;
	}
	public void setExperiencedApplicableDays(String experiencedApplicableDays)
	{
		this.experiencedApplicableDays = experiencedApplicableDays;
	}
	public String getPriorDays()
	{
		return priorDays;
	}
	public void setPriorDays(String priorDays)
	{
		this.priorDays = priorDays;
	}
	public String getMinDays()
	{
		return minDays;
	}
	public void setMinDays(String minDays)
	{
		this.minDays = minDays;
	}
	public String getMaxDays()
	{
		return maxDays;
	}
	public void setMaxDays(String maxDays)
	{
		this.maxDays = maxDays;
	}
	public String getNoOfDays()
	{
		return noOfDays;
	}
	public void setNoOfDays(String noOfDays)
	{
		this.noOfDays = noOfDays;
	}
	public float getTotalApprovedCount()
	{
		return totalApprovedCount;
	}
	public void setTotalApprovedCount(float totalApprovedCount)
	{
		this.totalApprovedCount = totalApprovedCount;
	}
	public float getTotalPendingCount()
	{
		return totalPendingCount;
	}
	public void setTotalPendingCount(float totalPendingCount)
	{
		this.totalPendingCount = totalPendingCount;
	}
	public float getTotalRejectedCount()
	{
		return totalRejectedCount;
	}
	public void setTotalRejectedCount(float totalRejectedCount)
	{
		this.totalRejectedCount = totalRejectedCount;
	}
	public float getTotalCancelCount()
	{
		return totalCancelCount;
	}
	public void setTotalCancelCount(float totalCancelCount)
	{
		this.totalCancelCount = totalCancelCount;
	}
	public float getTotalNoOfDays()
	{
		return totalNoOfDays;
	}
	public void setTotalNoOfDays(float totalNoOfDays)
	{
		this.totalNoOfDays = totalNoOfDays;
	}
	public String getCreatedUser()
	{
		return createdUser;
	}
	public void setCreatedUser(String createdUser)
	{
		this.createdUser = createdUser;
	}
	public String getUpdatedUser()
	{
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser)
	{
		this.updatedUser = updatedUser;
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
	public List<String> getAutoApproveTypeList()
	{
		return autoApproveTypeList;
	}
	public void setAutoApproveTypeList(List<String> autoApproveTypeList)
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
	
}
