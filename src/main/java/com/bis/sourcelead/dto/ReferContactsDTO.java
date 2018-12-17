package com.bis.sourcelead.dto;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;

public class ReferContactsDTO {

	 private Long id;
	 
	 @Required(desc = "first name")
	 private String firstName;
	 
	 private String lastName;
	 
	 @Required(desc = "email")
	 private String email;
	 
	 private String phone;
	 
	 private List<String> userContacts;
	 
	 private Long contactReferRequirementId;
	 
	 private String fullName;
	 
	 private String source;
	 
	 private String sortColumn;
		
	 private String sortOrder;
	 
	 private List<Map<String, String>> order; 
	 
	 private String groupIdsList;
	 
	 private String searchValue;
	 
	 private int start;
	 
	 private int length;
	 
	 List<String> groupsList;
	 
	 private String contactType;
	 
	 private String companyName;

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

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public List<String> getUserContacts()
	{
		return userContacts;
	}

	public void setUserContacts(List<String> userContacts)
	{
		this.userContacts = userContacts;
	}

	public Long getContactReferRequirementId()
	{
		return contactReferRequirementId;
	}

	public void setContactReferRequirementId(Long contactReferRequirementId)
	{
		this.contactReferRequirementId = contactReferRequirementId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public List<Map<String, String>> getOrder() {
		return order;
	}

	public void setOrder(List<Map<String, String>> order) {
		this.order = order;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getGroupsList()
	{
		return groupsList;
	}

	public void setGroupsList(List<String> groupsList)
	{
		this.groupsList = groupsList;
	}

	public String getGroupIdsList()
	{
		return groupIdsList;
	}

	public void setGroupIdsList(String groupIdsList)
	{
		this.groupIdsList = groupIdsList;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
