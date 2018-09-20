package com.bis.sourcelead.reportsDTO;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.SearchDTO;
import com.bis.sourcelead.entity.LeaveType;

public class LeaveBalanceReportDTO extends SearchDTO implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private List<String> leaveTypeNames;
	private String empName;
	private String empType;
	private String name;
	private BigInteger id;
	private BigInteger userId;
	private String department;
	private String leaveTypeName;
	private String role;
	private Map<String, Float> map;
	private Map<Long,String> depart;
	private String deparmentNames;
	private BigInteger departmentId;
	private float leaveTaken;
	private String searchValue;

	
	
	
	

	public float getLeaveTaken() {
		return leaveTaken;
	}
	public void setLeaveTaken(float leaveTaken) {
		this.leaveTaken = leaveTaken;
	}
	public Map<Long, String> getDepart() {
		return depart;
	}
	public void setDepart(Map<Long, String> depart) {
		this.depart = depart;
	}
	public String getDeparmentNames() {
		return deparmentNames;
	}
	public void setDeparmentNames(String deparmentNames) {
		this.deparmentNames = deparmentNames;
	}

	public BigInteger getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(BigInteger departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getLeaveTypeName() {
		return leaveTypeName;
	}
	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Map<String, Float> getMap() {
		return map;
	}
	public void setMap(Map<String, Float> map) {
		this.map = map;
	}
	public BigInteger getUserId() {
		return userId;
	}
	private List<LeaveBalanceReportDTO> users;
	

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	public List<String> getLeaveTypeNames() {
		return leaveTypeNames;
	}
	public void setLeaveTypeNames(List<String> leaveTypeNames) {
		this.leaveTypeNames = leaveTypeNames;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public List<LeaveBalanceReportDTO> getUsers() {
		return users;
	}
	public void setUsers(List<LeaveBalanceReportDTO> users) {
		this.users = users;
	}
	public String getSearchValue()
	{
		return searchValue;
	}
	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}
	
	
}