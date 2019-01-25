package com.bis.sourcelead.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSOCIATES_DEPARTMENT_VIEW")
public class AssociateDepartmentView {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long userId;

	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "DEPARTMENT_ID")
	private Long departmentId;
	
	@Column(name = "EMPLOYMENT_TYPE")
	private String employmentType;
	
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name = "ROLE_ID")
	private Long roleId;
	
	@Column(name = "ORG_LOCATIONS_ID")
	private Long orgLocationsId;
	
	@Column(name = "EMP_ORGLOCATION_ID")
	private Long empOrgLocationsId;
	
	@Column(name = "USER_STATE")
	private String userState;
	
	@Column(name = "DISPLAY_NAME")
	private String displayName;

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
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

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public String getEmploymentType()
	{
		return employmentType;
	}

	public void setEmploymentType(String employmentType)
	{
		this.employmentType = employmentType;
	}

	public String getEmployeeId()
	{
		return employeeId;
	}

	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
	}

	public Long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getOrgLocationsId()
	{
		return orgLocationsId;
	}

	public void setOrgLocationsId(Long orgLocationsId)
	{
		this.orgLocationsId = orgLocationsId;
	}

	public String getUserState()
	{
		return userState;
	}

	public void setUserState(String userState)
	{
		this.userState = userState;
	}

	public Long getEmpOrgLocationsId()
	{
		return empOrgLocationsId;
	}

	public void setEmpOrgLocationsId(Long empOrgLocationsId)
	{
		this.empOrgLocationsId = empOrgLocationsId;
	}
}
