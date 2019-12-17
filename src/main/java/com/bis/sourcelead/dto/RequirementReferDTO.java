package com.bis.sourcelead.dto;

public class RequirementReferDTO {

	 private String firstName;
	 
	 private String lastName;
	 
	 private String email;
	 
	 private String phone;
	 
	 private Long referRequirementId;
	 
	 private Long referId;
	 
	 private Integer appliedJobs;
	 
	 private Integer referByMeJobs;
	 
	 private Integer referToMeJobs;
	 
	 private String dateString;

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

	public Long getReferRequirementId()
	{
		return referRequirementId;
	}

	public void setReferRequirementId(Long referRequirementId)
	{
		this.referRequirementId = referRequirementId;
	}

	public Long getReferId()
	{
		return referId;
	}

	public void setReferId(Long referId)
	{
		this.referId = referId;
	}

	public Integer getAppliedJobs() {
		return appliedJobs;
	}

	public void setAppliedJobs(Integer appliedJobs) {
		this.appliedJobs = appliedJobs;
	}

	public Integer getReferByMeJobs() {
		return referByMeJobs;
	}

	public void setReferByMeJobs(Integer referByMeJobs) {
		this.referByMeJobs = referByMeJobs;
	}

	public Integer getReferToMeJobs() {
		return referToMeJobs;
	}

	public void setReferToMeJobs(Integer referToMeJobs) {
		this.referToMeJobs = referToMeJobs;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	

	
}
