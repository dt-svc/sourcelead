package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.bis.sourcelead.enums.Contact_Source;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "REFER_CONTACTS", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "referContacts")
public class ReferContacts implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="REFER_FROM_USER_ID")        
    private User referFromUser;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="CONTACT_USER_ID")        
    private User contactUser;
	
	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;

	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
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
	
	@Transient
	private String contactUserName;
	
	@Transient
	@JsonProperty
	private String fullName;
	
	
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
	
	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
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
	

}
