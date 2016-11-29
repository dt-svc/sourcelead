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

private String rate;			// Rate

private String ratePer;			// Rate Per

private Date dateOfJoin;		// Date of Join

private String role;			// Role

private String invoiceFrequency; //Invoice Frequency

private String dayHours; 
// Day Hours

private String dateString;

private Status isMailChecked;

private String reason;



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

public Date getDateOfJoin() {
	return dateOfJoin;
}

public void setDateOfJoin(Date dateOfJoin) {
	this.dateOfJoin = dateOfJoin;
}

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
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


}
