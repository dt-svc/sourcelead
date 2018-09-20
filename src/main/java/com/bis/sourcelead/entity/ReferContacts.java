package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bis.sourcelead.enums.Contact_Source;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "REFER_CONTACTS")
public class ReferContacts implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REFER_FROM_USER_ID")        
    private User referFromUser;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CONTACT_USER_ID")        
    private User contactUser;
	
	@Column(name = "FILE_URL", columnDefinition = "TEXT default NULL")
	private String fileURL;
	
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;

	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORGLOCATION_ID")        
    private OrgLocations orgLocationId;

	@Column(name = "CONTACT_USER_FIRSTNAME")
	private String firstName;
	
	@Column(name = "CONTACT_USER_LASTNAME")
	private String lastName;
	
	@Column(name = "CONTACT_USER_EMAIL")
	private String contactUserEmail;
	
	@Column(name = "CONTACT_USER_PHONE")
	private String contactUserPhone;
	
	@Column(name = "CONTACT_USER_UUID")
	private String contactUserUUID;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "CONTACT_SOURCE")
	@Enumerated(EnumType.STRING)
	private Contact_Source contactSource;
	
	@Column(name = "JSON_OUTPUT" , columnDefinition = "TEXT default NULL")
	private String jsonOutPut;
	
	@Column(name = "CONTACT_USER_PHONE2")
	private String contactUserPhone2;
	
	@Column(name = "CONTACT_USER_EMAIL2")
	private String contactUserEmail2;
	
	@Column(name = "GROUP_ID" , columnDefinition = "TEXT default NULL")
	private String groupId;
	
	@Column(name = "CLIENT_TYPE")
	private Integer clientType;
	
	@Column(name = "VENDOR_TYPE")
	private Integer vendorType;
	
	@Column(name = "CANDIDATE_TYPE")
	private Integer candidateType;
	
	@Column(name = "OTHER_TYPE")
	private Integer otherType;
	
	@Transient
	private String contactUserName;
	
	@Transient
	@JsonProperty
	private String fullName;
	
	@ManyToMany(mappedBy="contactsList",cascade = CascadeType.ALL)
	//@Fetch(FetchMode.SELECT)
	private List<ContactsGroup> groupList;
	
	@Column(name = "CONTACT_TYPE")
	private String contactType;
	
	@Column(name = "COMPANY_NAME")
	private String companyName;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getReferFromUser() {
		return referFromUser;
	}

	public void setReferFromUser(User referFromUser) {
		this.referFromUser = referFromUser;
	}

	public User getContactUser() {
		return contactUser;
	}

	public void setContactUser(User contactUser) {
		this.contactUser = contactUser;
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

	public String getContactUserEmail() {
		return contactUserEmail;
	}

	public void setContactUserEmail(String contactUserEmail) {
		this.contactUserEmail = contactUserEmail;
	}

	public String getContactUserPhone() {
		return contactUserPhone;
	}

	public void setContactUserPhone(String contactUserPhone) {
		this.contactUserPhone = contactUserPhone;
	}

	public String getContactUserUUID() {
		return contactUserUUID;
	}

	public void setContactUserUUID(String contactUserUUID) {
		this.contactUserUUID = contactUserUUID;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
	}

	public Contact_Source getContactSource() {
		return contactSource;
	}

	public void setContactSource(Contact_Source contactSource) {
		this.contactSource = contactSource;
	}
	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	
	public String getJsonOutPut() {
		return jsonOutPut;
	}

	public void setJsonOutPut(String jsonOutPut) {
		this.jsonOutPut = jsonOutPut;
	}

	public String getContactUserPhone2() {
		return contactUserPhone2;
	}

	public void setContactUserPhone2(String contactUserPhone2) {
		this.contactUserPhone2 = contactUserPhone2;
	}

	public String getContactUserEmail2() {
		return contactUserEmail2;
	}

	public void setContactUserEmail2(String contactUserEmail2) {
		this.contactUserEmail2 = contactUserEmail2;
	}

	public String getFullName() {

		if(StringUtils.isNotEmpty(fullName))
			return fullName;
		else{
			if(StringUtils.isNotEmpty(firstName) && StringUtils.isEmpty(lastName)){
				fullName = firstName;
			}
			else if(StringUtils.isEmpty(firstName) && StringUtils.isNotEmpty(lastName)){
				fullName = lastName;
			}
			else if(StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)){
				fullName = firstName+" "+lastName;
			}
			return fullName;
		}
	
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}


	public List<ContactsGroup> getGroupList()
	{
		return groupList;
	}

	public void setGroupList(List<ContactsGroup> groupList)
	{
		this.groupList = groupList;
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

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public Integer getVendorType() {
		return vendorType;
	}

	public void setVendorType(Integer vendorType) {
		this.vendorType = vendorType;
	}

	public Integer getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(Integer candidateType) {
		this.candidateType = candidateType;
	}

	public Integer getOtherType() {
		return otherType;
	}

	public void setOtherType(Integer otherType) {
		this.otherType = otherType;
	}
}