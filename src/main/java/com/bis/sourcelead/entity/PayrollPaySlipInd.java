package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "IND_PAYSLIP")
public class PayrollPaySlipInd {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "PAYSLIP_NAME")
	private String paySlipName;
	
	@Column(name = "PAYSLIP_DESCRIPTION")
	private String paySlipDescription;
	
	@Column(name = "COMPANY_NAME")
	private String companyName;
	
	@Column(name = "COMPANY_ADDRESS")
	private String companyAddress;
	
	@Column(name = "COMPANY_CONTACT_DETAILS")
	private String companyContactDetails;
	
	@Column(name = "COMPANY_LOGO")
	private String companyLogo;
	
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name = "EMPLOYEE_NAME")
	private String employeeName;
	
	@Column(name = "EMPLOYEE_TYPE")
	private String employeeType;
	
	@Column(name = "EMPLOYEE_CONTACT_DETAILS")
	private String employeeContactDetails;
	
	@Column(name = "EMPLOYEE_PROJECT")
	private String employeeProject;
	
	@Column(name = "EMPLOYEE_JOIN_DATE")
	private String employeeJoinDate;
	
	@Column(name = "EMPLOYEE_UAN_NUMBER")
	private String employeeUanNumber;
	
	@Column(name = "EMPLOYEE_PF_NUMBER")
	private String employeePfNumber;
	
	@Column(name = "EMPLOYEE_PAN_NUMBER")
	private String employeePanNumber;
	
	@Column(name = "EMPLOYEE_ESI_NUMBER")
	private String employeeEsiNumber;
	
	@Column(name = "EMPLOYEE_BANK_DETAILS")
	private String employeeBankDetails;
	
	@Column(name = "EMPLOYEE_DESIGNATION")
	private String employeeDesignation;
	
	@Column(name = "EMPLOYEE_LOCATION")
	private String employeeLocation;
	
	@Column(name = "LEAVE_ATTENDED")
	private String leaveAttended;
	
	@Column(name = "LEAVE_LEAVES")
	private String leaveLeaves;
	
	@Column(name = "LEAVE_LOP")
	private String leaveLop;
	
	@Column(name = "PREVIOUS_MONTH_PAID")
	private String previousMonthPaid;
	
	@Column(name = "REGULAR_HOURS")
	private String regularHours;
	
	@Column(name = "OVERTIME_HOURS")
	private String overtimeHours;
	
	@Column(name = "IS_ACTIVE")
	private String isActive;
	
	@Column(name = "DELETE_PERMISSION")
	private String deletePermission;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "ORGLOCATION_ID")
    private OrgLocations orgLocationId;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "PAYSLIP_STATUS")
	private String payslipStatus;
    
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	 
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
    
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
    
    @Column(name = "UPDATED_DATE")
   	private Date updatedDate;

    
	public String getEmployeeDesignation() {
		return employeeDesignation;
	}

	public void setEmployeeDesignation(String employeeDesignation) {
		this.employeeDesignation = employeeDesignation;
	}

	public Long getId() {
		return id;
	}

	public String getPaySlipName() {
		return paySlipName;
	}

	public String getPaySlipDescription() {
		return paySlipDescription;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public String getCompanyContactDetails() {
		return companyContactDetails;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public String getEmployeeContactDetails() {
		return employeeContactDetails;
	}

	public String getEmployeeProject() {
		return employeeProject;
	}

	public String getEmployeeJoinDate() {
		return employeeJoinDate;
	}

	public String getEmployeePfNumber() {
		return employeePfNumber;
	}

	public String getEmployeeEsiNumber() {
		return employeeEsiNumber;
	}

	public String getEmployeeBankDetails() {
		return employeeBankDetails;
	}

	public String getEmployeeLocation() {
		return employeeLocation;
	}

	public String getLeaveAttended() {
		return leaveAttended;
	}

	public String getLeaveLeaves() {
		return leaveLeaves;
	}

	public String getLeaveLop() {
		return leaveLop;
	}

	public String getPreviousMonthPaid() {
		return previousMonthPaid;
	}

	public String getRegularHours() {
		return regularHours;
	}

	public String getOvertimeHours() {
		return overtimeHours;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public String getUuid() {
		return uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPaySlipName(String paySlipName) {
		this.paySlipName = paySlipName;
	}

	public void setPaySlipDescription(String paySlipDescription) {
		this.paySlipDescription = paySlipDescription;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public void setCompanyContactDetails(String companyContactDetails) {
		this.companyContactDetails = companyContactDetails;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public void setEmployeeContactDetails(String employeeContactDetails) {
		this.employeeContactDetails = employeeContactDetails;
	}

	public void setEmployeeProject(String employeeProject) {
		this.employeeProject = employeeProject;
	}

	public void setEmployeeJoinDate(String employeeJoinDate) {
		this.employeeJoinDate = employeeJoinDate;
	}

	public void setEmployeePfNumber(String employeePfNumber) {
		this.employeePfNumber = employeePfNumber;
	}

	public void setEmployeeEsiNumber(String employeeEsiNumber) {
		this.employeeEsiNumber = employeeEsiNumber;
	}

	public void setEmployeeBankDetails(String employeeBankDetails) {
		this.employeeBankDetails = employeeBankDetails;
	}

	public void setEmployeeLocation(String employeeLocation) {
		this.employeeLocation = employeeLocation;
	}

	public void setLeaveAttended(String leaveAttended) {
		this.leaveAttended = leaveAttended;
	}

	public void setLeaveLeaves(String leaveLeaves) {
		this.leaveLeaves = leaveLeaves;
	}

	public void setLeaveLop(String leaveLop) {
		this.leaveLop = leaveLop;
	}

	public void setPreviousMonthPaid(String previousMonthPaid) {
		this.previousMonthPaid = previousMonthPaid;
	}

	public void setRegularHours(String regularHours) {
		this.regularHours = regularHours;
	}

	public void setOvertimeHours(String overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getPayslipStatus() {
		return payslipStatus;
	}

	public void setPayslipStatus(String payslipStatus) {
		this.payslipStatus = payslipStatus;
	}

	public String getEmployeeUanNumber() {
		return employeeUanNumber;
	}

	public void setEmployeeUanNumber(String employeeUanNumber) {
		this.employeeUanNumber = employeeUanNumber;
	}

	public String getEmployeePanNumber() {
		return employeePanNumber;
	}

	public void setEmployeePanNumber(String employeePanNumber) {
		this.employeePanNumber = employeePanNumber;
	}

	public String getIsActive() {
		return isActive;
	}

	public String getDeletePermission() {
		return deletePermission;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public void setDeletePermission(String deletePermission) {
		this.deletePermission = deletePermission;
	}
}
