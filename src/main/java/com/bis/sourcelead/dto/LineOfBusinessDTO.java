package com.bis.sourcelead.dto;

import java.math.BigInteger;

import com.bis.sourcelead.annotations.Required;

public class LineOfBusinessDTO {

	@Required(desc="lobName")
	private String lobName;
	
	@Required(desc="departmentId")
	private String departmentId;
	
	private String description;
	
	private BigInteger lobId;
	
	private String departmentName;
	
	private BigInteger deptId;
	

	public String getLobName() {
		return lobName;
	}

	public void setLobName(String lobName) {
		this.lobName = lobName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigInteger getLobId() {
		return lobId;
	}

	public void setLobId(BigInteger lobId) {
		this.lobId = lobId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public BigInteger getDeptId() {
		return deptId;
	}

	public void setDeptId(BigInteger deptId) {
		this.deptId = deptId;
	}
	
	
	

}
