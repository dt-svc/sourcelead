package com.bis.sourcelead.dto;

import java.util.List;

public class ReferContactsDTO {

	 private String firstName;
	 
	 private String lastName;
	 
	 private String email;
	 
	 private String phone;
	 
	 private List<String> userContacts;
	 
	 private Long contactReferRequirementId;
	 
	 private String fullName;
	 
	 private String source;

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
	 
}
