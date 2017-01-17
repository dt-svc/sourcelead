package com.bis.sourcelead.elk.index;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.enums.Status;

public class ClientVendorIndex implements Serializable {

	private static final long serialVersionUID = 1L;

	private String orgName;
	
	private String orgLocation;
	
	private String tier;
	
    private String createdBy;	
	
	private String documentName;
	
	private String documentType;
	
	private String submissionTemplateName;
	
	private String contactName;
	
	private String contactEmail;
	
	private String contactPhoneNumber;
	
	private Status invitationType;

	private Date createdDate;
	
	private String email;
	
	private String mobilePhone;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgLocation() {
		return orgLocation;
	}

	public void setOrgLocation(String orgLocation) {
		this.orgLocation = orgLocation;
	}

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getSubmissionTemplateName() {
		return submissionTemplateName;
	}

	public void setSubmissionTemplateName(String submissionTemplateName) {
		this.submissionTemplateName = submissionTemplateName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	public Status getInvitationType() {
		return invitationType;
	}

	public void setInvitationType(Status invitationType) {
		this.invitationType = invitationType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
