package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.enums.Status;

/**
 * 
 * @author Rajashekar
 * @date 6th July 2016
 *
 */

public class UsersImport implements Serializable{

private static final long serialVersionUID = 1L;

private String employeeId;		// Employee ID

private String firstName; 		// First Name

private String lastName;		// Last Name

private String emailId;			// Email id

private String mobileNumber;	// Phone Number

private String extensionNumber; // Extension Number

private String rate;			// Rate

private String ratePer;			// Rate Per

private String dateofJoin;		// Date of Join

private String role;

private String roleType;			// Role

private String invoiceFrequency; //Invoice Frequency

private String dayHours; 
// Day Hours

private String dateString;

private Status isMailChecked;

private String reason;

private String manager;

private String vendor;

private String vendors;

private String vendorContact;

private String vendorcontact;

private String employmentType;

private String grade;

private String designation;

private String shiftId;

private String shift;

private String reportingManager;

private String email;

private String genderValue;

private String designationType;

private String phoneNumber;

private String department;

private String middleName;

private String maritalStatus;

private String isGlobalAccess;

private String dateofBirth;


public String getReason() {
	return reason;
}

public void setReason(String reason) {
	this.reason = reason;
}

public Status getIsMailChecked() {
	return isMailChecked;
}

public void setIsMailChecked(Status isMailChecked) {
	this.isMailChecked = isMailChecked;
}

public String getDateString() {
	return dateString;
}

public void setDateString(String dateString) {
	this.dateString = dateString;
}

public String getEmployeeId() {
	return employeeId;
}

public void setEmployeeId(String employeeId) {
	this.employeeId = employeeId;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getEmailId() {
	return emailId;
}

public void setEmailId(String emailId) {
	this.emailId = emailId;
}

public String getMobileNumber() {
	return mobileNumber;
}

public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
}

public String getExtensionNumber()
{
	return extensionNumber;
}

public void setExtensionNumber(String extensionNumber)
{
	this.extensionNumber = extensionNumber;
}

public String getRate() {
	return rate;
}

public void setRate(String rate) {
	this.rate = rate;
}

public String getRatePer() {
	return ratePer;
}

public void setRatePer(String ratePer) {
	this.ratePer = ratePer;
}
 
public String getDateofJoin() {
	return dateofJoin;
}

public void setDateofJoin(String dateofJoin) {
	this.dateofJoin = dateofJoin;
}

public String getInvoiceFrequency() {
	return invoiceFrequency;
}

public void setInvoiceFrequency(String invoiceFrequency) {
	this.invoiceFrequency = invoiceFrequency;
}

public String getDayHours() {
	return dayHours;
}

public void setDayHours(String dayHours) {
	this.dayHours = dayHours;
}

public String getManager() {
	return manager;
}

public void setManager(String manager) {
	this.manager = manager;
}

public String getVendor() {
	return vendor;
}

public void setVendor(String vendor) {
	this.vendor = vendor;
}

public String getVendorContact() {
	return vendorContact;
}

public void setVendorContact(String vendorContact) {
	this.vendorContact = vendorContact;
}

public String getEmploymentType() {
	return employmentType;
}

public void setEmploymentType(String employmentType) {
	this.employmentType = employmentType;
}

public String getGrade() {
	return grade;
}

public void setGrade(String grade) {
	this.grade = grade;
}

public String getDesignation() {
	return designation;
}

public void setDesignation(String designation) {
	this.designation = designation;
}

public String getRoleType() {
	return roleType;
}

public String getShiftId() {
	return shiftId;
}

public void setRoleType(String roleType) {
	this.roleType = roleType;
}

public void setShiftId(String shiftId) {
	this.shiftId = shiftId;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

public String getShift() {
	return shift;
}

public void setShift(String shift) {
	this.shift = shift;
}

public String getReportingManager() {
	return reportingManager;
}

public void setReportingManager(String reportingManager) {
	this.reportingManager = reportingManager;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getGenderValue() {
	return genderValue;
}

public void setGenderValue(String genderValue) {
	this.genderValue = genderValue;
}

public String getDesignationType() {
	return designationType;
}

public void setDesignationType(String designationType) {
	this.designationType = designationType;
}

public String getPhoneNumber() {
	return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}

public String getDepartment() {
	return department;
}

public void setDepartment(String department) {
	this.department = department;
}

public String getMiddleName() {
	return middleName;
}

public void setMiddleName(String middleName) {
	this.middleName = middleName;
}

public String getMaritalStatus() {
	return maritalStatus;
}

public void setMaritalStatus(String maritalStatus) {
	this.maritalStatus = maritalStatus;
}

public String getVendors() {
	return vendors;
}

public void setVendors(String vendors) {
	this.vendors = vendors;
}

public String getVendorcontact() {
	return vendorcontact;
}

public void setVendorcontact(String vendorcontact) {
	this.vendorcontact = vendorcontact;
}

public String getIsGlobalAccess() {
	return isGlobalAccess;
}

public void setIsGlobalAccess(String isGlobalAccess) {
	this.isGlobalAccess = isGlobalAccess;
}

public String getDateofBirth() {
	return dateofBirth;
}

public void setDateofBirth(String dateofBirth) {
	this.dateofBirth = dateofBirth;
}



}
