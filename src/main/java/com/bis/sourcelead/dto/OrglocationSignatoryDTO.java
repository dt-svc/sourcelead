package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class OrglocationSignatoryDTO {

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phoneNumber;
	
	private String ssn;
	
	private String roleType;
	
	private String designationType;
	
	private String designationOtherType;
	
	private String uuid;
	
	@Required(desc="associateId")
	private Long associateId;
	
	@Required(desc="authorizedType")
	private List<String> authorizedType;
	
	private String authorizedAmount;
	
	private String signatoryFromType;
	
	private String status;

	
	public void setStatus(String status) {
		this.status = status;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getDesignationType() {
		return designationType;
	}

	public void setDesignationType(String designationType) {
		this.designationType = designationType;
	}

	public String getDesignationOtherType() {
		return designationOtherType;
	}

	public void setDesignationOtherType(String designationOtherType) {
		this.designationOtherType = designationOtherType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getAssociateId()
	{
		return associateId;
	}

	public void setAssociateId(Long associateId)
	{
		this.associateId = associateId;
	}

	public List<String> getAuthorizedType()
	{
		return authorizedType;
	}

	public void setAuthorizedType(List<String> authorizedType)
	{
		this.authorizedType = authorizedType;
	}

	public String getAuthorizedAmount()
	{
		return authorizedAmount;
	}

	public void setAuthorizedAmount(String authorizedAmount)
	{
		this.authorizedAmount = authorizedAmount;
	}

	public String getSignatoryFromType()
	{
		return signatoryFromType;
	}

	public void setSignatoryFromType(String signatoryFromType)
	{
		this.signatoryFromType = signatoryFromType;
	}

	public String getStatus()
	{
		return status;
	}
}
