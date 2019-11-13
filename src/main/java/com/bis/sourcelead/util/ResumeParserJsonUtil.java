package com.bis.sourcelead.util;

import java.util.Set;

import com.bis.sourcelead.dto.FileInfo;

public class ResumeParserJsonUtil {

	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	Set<String> phoneNumbers;
	private String dob;
	private String maritalStatus;
	private String gender;
	private String designation;
	private int totalYearsExp;
	private int totalMonthsExp;
	private Set<String> skills;
	private Set<String> employeers;
	private Set<String> educations;
	private String summary;

	private String addressLine1;
	private String locality;
	private String city;
	private String state;
	private String country;
	private String zipcode;

	private FileInfo fileInfo;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNumbers
	 */
	public Set<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	/**
	 * @param phoneNumbers
	 *            the phoneNumbers to set
	 */
	public void setPhoneNumbers(Set<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * @param maritalStatus
	 *            the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation
	 *            the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @return the totalYearsExp
	 */
	public int getTotalYearsExp() {
		return totalYearsExp;
	}

	/**
	 * @param totalYearsExp
	 *            the totalYearsExp to set
	 */
	public void setTotalYearsExp(int totalYearsExp) {
		this.totalYearsExp = totalYearsExp;
	}

	/**
	 * @return the totalMonthsExp
	 */
	public int getTotalMonthsExp() {
		return totalMonthsExp;
	}

	/**
	 * @param totalMonthsExp
	 *            the totalMonthsExp to set
	 */
	public void setTotalMonthsExp(int totalMonthsExp) {
		this.totalMonthsExp = totalMonthsExp;
	}

	/**
	 * @return the skills
	 */
	public Set<String> getSkills() {
		return skills;
	}

	/**
	 * @param skills
	 *            the skills to set
	 */
	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}

	/**
	 * @return the employeers
	 */
	public Set<String> getEmployeers() {
		return employeers;
	}

	/**
	 * @param employeers
	 *            the employeers to set
	 */
	public void setEmployeers(Set<String> employeers) {
		this.employeers = employeers;
	}

	/**
	 * @return the educations
	 */
	public Set<String> getEducations() {
		return educations;
	}

	/**
	 * @param educations
	 *            the educations to set
	 */
	public void setEducations(Set<String> educations) {
		this.educations = educations;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine1
	 *            the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}

	/**
	 * @param locality
	 *            the locality to set
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * @param zipcode
	 *            the zipcode to set
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * @return the fileInfo
	 */
	public FileInfo getFileInfo() {
		return fileInfo;
	}

	/**
	 * @param fileInfo
	 *            the fileInfo to set
	 */
	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

}