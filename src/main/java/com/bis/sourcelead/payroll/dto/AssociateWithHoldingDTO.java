package com.bis.sourcelead.payroll.dto;

import java.math.BigInteger;

import com.bis.sourcelead.dto.SearchDTO;

public class AssociateWithHoldingDTO  extends SearchDTO{
	
	private BigInteger candidateId;
	
	private String firstName;
	
	private String lastName; 
	
	private String employeeId;
	
	private BigInteger departmentId;
	
	private String workLocation;
	
	private String ssnNumber;
	
	private String federalFillingStatus;
	
	private String federalTaxAllowances;
	
	private String federalAdditinalWithHolding;
	
	private String stateFillingStatus;
	
	private String stateTaxAllowances;
	
	private String stateAdditionalWithHolding;
	
	private String empName;
	
	private String residenceStateCode;
	
	private String dept;
	
	private String empType;
	
	private String employeeMentType;
	
	private String exportType;
	

	public String getEmployeeMentType()
	{
		return employeeMentType;
	}

	public void setEmployeeMentType(String employeeMentType)
	{
		this.employeeMentType = employeeMentType;
	}

	public String getDept()
	{
		return dept;
	}

	public String getEmpType()
	{
		return empType;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public void setEmpType(String empType)
	{
		this.empType = empType;
	}

	public BigInteger getCandidateId()
	{
		return candidateId;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getEmployeeId()
	{
		return employeeId;
	}

	public BigInteger getDepartmentId()
	{
		return departmentId;
	}

	public String getWorkLocation()
	{
		return workLocation;
	}

	public String getSsnNumber()
	{
		return ssnNumber;
	}

	public String getFederalFillingStatus()
	{
		return federalFillingStatus;
	}

	public String getFederalTaxAllowances()
	{
		return federalTaxAllowances;
	}


	public String getStateFillingStatus()
	{
		return stateFillingStatus;
	}

	public String getStateTaxAllowances()
	{
		return stateTaxAllowances;
	}

	public String getStateAdditionalWithHolding()
	{
		return stateAdditionalWithHolding;
	}

	public void setCandidateId(BigInteger candidateId)
	{
		this.candidateId = candidateId;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
	}

	public void setDepartmentId(BigInteger departmentId)
	{
		this.departmentId = departmentId;
	}

	public void setWorkLocation(String workLocation)
	{
		this.workLocation = workLocation;
	}

	public void setSsnNumber(String ssnNumber)
	{
		this.ssnNumber = ssnNumber;
	}

	public void setFederalFillingStatus(String federalFillingStatus)
	{
		this.federalFillingStatus = federalFillingStatus;
	}

	public void setFederalTaxAllowances(String federalTaxAllowances)
	{
		this.federalTaxAllowances = federalTaxAllowances;
	}

	public void setStateFillingStatus(String stateFillingStatus)
	{
		this.stateFillingStatus = stateFillingStatus;
	}

	public void setStateTaxAllowances(String stateTaxAllowances)
	{
		this.stateTaxAllowances = stateTaxAllowances;
	}

	public void setStateAdditionalWithHolding(String stateAdditionalWithHolding)
	{
		this.stateAdditionalWithHolding = stateAdditionalWithHolding;
	}

	public String getEmpName()
	{
		return empName;
	}

	public void setEmpName(String empName)
	{
		this.empName = empName;
	}

	public String getFederalAdditinalWithHolding()
	{
		return federalAdditinalWithHolding;
	}

	public void setFederalAdditinalWithHolding(String federalAdditinalWithHolding)
	{
		this.federalAdditinalWithHolding = federalAdditinalWithHolding;
	}

	public String getResidenceStateCode()
	{
		return residenceStateCode;
	}

	public void setResidenceStateCode(String residenceStateCode)
	{
		this.residenceStateCode = residenceStateCode;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}
	
	
}
