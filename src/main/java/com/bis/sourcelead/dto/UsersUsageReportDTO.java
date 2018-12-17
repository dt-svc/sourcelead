package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.UserRole;

public class UsersUsageReportDTO extends SearchDTO implements Serializable{
	
	private long id;
	private String name;
	private String designation;
	private String profileImage;
	private long parentId;
	private String userType;
	private String firstName;
	private String lastName;
	private String mobilePhone;
	private String userRole;
	private String searchByName;
	private String userState;
	private String loginUserRole;
	private String roleId;
	private String fileURL;
	private String userName;
	private String userEmail;
	private String createdOn;
	private List<UserRole> enrolledOrgs;
	private int activeCount;
	private int inactiveCount;
	private int notLoggedinCount;
	private List<Long> orgId;
	private String noOfActivities;
	private String lastActivity;
	private long userRoleId;
	private long locationId;
	private long multipleIps;
	private String customDates;
	private List<Long> userIds;
	private String fromDate;
	private String toDate;
	private String ipAddress;
	private int noOfUsers;
	private String location;
	private Date[] freqDates;
	private String exportType;
	private String dates;
	private long userId;
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDesignation()
	{
		return designation;
	}
	public void setDesignation(String designation)
	{
		this.designation = designation;
	}
	public String getProfileImage()
	{
		return profileImage;
	}
	public void setProfileImage(String profileImage)
	{
		this.profileImage = profileImage;
	}
	public long getParentId()
	{
		return parentId;
	}
	public void setParentId(long parentId)
	{
		this.parentId = parentId;
	}
	public String getUserType()
	{
		return userType;
	}
	public void setUserType(String userType)
	{
		this.userType = userType;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getMobilePhone()
	{
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}
	public String getUserRole()
	{
		return userRole;
	}
	public void setUserRole(String userRole)
	{
		this.userRole = userRole;
	}
	public String getSearchByName()
	{
		return searchByName;
	}
	public void setSearchByName(String searchByName)
	{
		this.searchByName = searchByName;
	}
	public String getUserState()
	{
		return userState;
	}
	public void setUserState(String userState)
	{
		this.userState = userState;
	}
	public String getLoginUserRole()
	{
		return loginUserRole;
	}
	public void setLoginUserRole(String loginUserRole)
	{
		this.loginUserRole = loginUserRole;
	}
	public String getRoleId()
	{
		return roleId;
	}
	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
	}
	public String getFileURL()
	{
		return fileURL;
	}
	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getUserEmail()
	{
		return userEmail;
	}
	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}
	public String getCreatedOn()
	{
		return createdOn;
	}
	public void setCreatedOn(String createdOn)
	{
		this.createdOn = createdOn;
	}
	public List<UserRole> getEnrolledOrgs()
	{
		return enrolledOrgs;
	}
	public void setEnrolledOrgs(List<UserRole> enrolledOrgs)
	{
		this.enrolledOrgs = enrolledOrgs;
	}
	public int getActiveCount()
	{
		return activeCount;
	}
	public void setActiveCount(int activeCount)
	{
		this.activeCount = activeCount;
	}
	public int getInactiveCount()
	{
		return inactiveCount;
	}
	public void setInactiveCount(int inactiveCount)
	{
		this.inactiveCount = inactiveCount;
	}
	public int getNotLoggedinCount()
	{
		return notLoggedinCount;
	}
	public void setNotLoggedinCount(int notLoggedinCount)
	{
		this.notLoggedinCount = notLoggedinCount;
	}
	public List<Long> getOrgId()
	{
		return orgId;
	}
	public void setOrgId(List<Long> orgId)
	{
		this.orgId = orgId;
	}
	public String getNoOfActivities()
	{
		return noOfActivities;
	}
	public void setNoOfActivities(String noOfActivities)
	{
		this.noOfActivities = noOfActivities;
	}
	public String getLastActivity()
	{
		return lastActivity;
	}
	public void setLastActivity(String lastActivity)
	{
		this.lastActivity = lastActivity;
	}
	public long getUserRoleId()
	{
		return userRoleId;
	}
	public void setUserRoleId(long userRoleId)
	{
		this.userRoleId = userRoleId;
	}
	public long getLocationId()
	{
		return locationId;
	}
	public void setLocationId(long locationId)
	{
		this.locationId = locationId;
	}
	public long getMultipleIps()
	{
		return multipleIps;
	}
	public void setMultipleIps(long multipleIps)
	{
		this.multipleIps = multipleIps;
	}
	public String getCustomDates()
	{
		return customDates;
	}
	public void setCustomDates(String customDates)
	{
		this.customDates = customDates;
	}
	public List<Long> getUserIds()
	{
		return userIds;
	}
	public void setUserIds(List<Long> userIds)
	{
		this.userIds = userIds;
	}
	public String getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}
	public String getToDate()
	{
		return toDate;
	}
	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}
	public String getIpAddress()
	{
		return ipAddress;
	}
	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}
	public int getNoOfUsers()
	{
		return noOfUsers;
	}
	public void setNoOfUsers(int noOfUsers)
	{
		this.noOfUsers = noOfUsers;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	public Date[] getFreqDates()
	{
		return freqDates;
	}
	public void setFreqDates(Date[] freqDates)
	{
		this.freqDates = freqDates;
	}
	public String getExportType()
	{
		return exportType;
	}
	public void setExportType(String exportType)
	{
		this.exportType = exportType;
	}
	public String getDates()
	{
		return dates;
	}
	public void setDates(String dates)
	{
		this.dates = dates;
	}
	public long getUserId()
	{
		return userId;
	}
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
}
