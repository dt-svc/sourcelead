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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "LEAVE_TYPE")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "leaveType")
public class LeaveType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
//	@Column(columnDefinition = "BINARY(16)")
	private String id;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "ORGLOCATION_ID", nullable = false)
	private OrgLocations orgLocations;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID", nullable = false)
	private Department departmentId;
	
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

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "leaveTypeid")
	private Set<LeaveRole> leaveTypeRolesSet;
	
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
	private Integer minDays;
	
	@Column(name = "MAX_DAYS")
	private Integer maxDays;
	
	@Column(name = "NO_OF_DAYS")
	private Integer noOfDays;
	
	@Transient
	@JsonProperty
	private String createdUser;
	
	@Transient
	@JsonProperty
	private String updatedUser;
	
	@Transient
	@JsonProperty
	private int remainingTotalLeaveCounts;
	
	@Transient
	@JsonProperty
	private int pendingLeaveCounts;
	
	@Transient
	@JsonProperty
	private int balanceLeaveCounts;
	
	@Transient
	@JsonProperty
	private int takenLeaveCounts;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public OrgLocations getOrgLocations()
	{
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations)
	{
		this.orgLocations = orgLocations;
	}

	public Department getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Department departmentId)
	{
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

	public Set<LeaveRole> getLeaveTypeRolesSet()
	{
		return leaveTypeRolesSet;
	}

	public void setLeaveTypeRolesSet(Set<LeaveRole> leaveTypeRolesSet)
	{
		this.leaveTypeRolesSet = leaveTypeRolesSet;
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

	public Integer getMinDays()
	{
		return minDays;
	}

	public void setMinDays(Integer minDays)
	{
		this.minDays = minDays;
	}

	public Integer getMaxDays()
	{
		return maxDays;
	}

	public void setMaxDays(Integer maxDays)
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

	public int getRemainingTotalLeaveCounts()
	{
		return remainingTotalLeaveCounts;
	}

	public void setRemainingTotalLeaveCounts(int remainingTotalLeaveCounts)
	{
		this.remainingTotalLeaveCounts = remainingTotalLeaveCounts;
	}

	public int getPendingLeaveCounts()
	{
		return pendingLeaveCounts;
	}

	public void setPendingLeaveCounts(int pendingLeaveCounts)
	{
		this.pendingLeaveCounts = pendingLeaveCounts;
	}

	public int getBalanceLeaveCounts()
	{
		return balanceLeaveCounts;
	}

	public void setBalanceLeaveCounts(int balanceLeaveCounts)
	{
		this.balanceLeaveCounts = balanceLeaveCounts;
	}

	public int getTakenLeaveCounts()
	{
		return takenLeaveCounts;
	}

	public void setTakenLeaveCounts(int takenLeaveCounts)
	{
		this.takenLeaveCounts = takenLeaveCounts;
	}

	
 
}
