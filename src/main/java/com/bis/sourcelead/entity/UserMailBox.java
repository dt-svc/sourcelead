package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "USER_MAIL_BOX", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "userMailBox")
public class UserMailBox implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="USER_ID", nullable=false )        
    private User userId;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="USER_SIGNATURE_ID", nullable=false )        
    private UserSignature signatureId;
	
	@Column(name = "TO_MAIL")
	@Type(type="text")
	private String toMail;
	
	@Column(name = "FROM_MAIL")
	@Type(type="text")
	private String fromMail;
	
	@Column(name = "MAIL_STATUS")
	private String mailStatus;
	
	@Column(name = "SUBJECT")
	@Type(type="text")
	private String subject;
	
	@Column(name = "BODY")
	@Type(type="text")
	private String body;
	
	@Column(name = "CAMPAIGN_TABLE_DATA")
	@Type(type="text")
	private String campaignTableData;
	
	@Column(name = "CANDIDATE_ID")
	@Type(type="text")
	private String candidateId;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name="IS_FAVOURITE")
	private Integer isFavourite;
	
	@Column(name = "NAME_FIELDS")
	private String nameFields;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	//to get only time from datetime
	@Transient
	private String time;
	
	//to get only date from datetime
	@Transient
	private String dateValue;
	
	//to calculate days
	@Transient
	private Long calDays;
		
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public UserSignature getSignatureId() {
		return signatureId;
	}

	public void setSignatureId(UserSignature signatureId) {
		this.signatureId = signatureId;
	}

	public String getToMail() {
		return toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCampaignTableData() {
		return campaignTableData;
	}

	public void setCampaignTableData(String campaignTableData) {
		this.campaignTableData = campaignTableData;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getIsFavourite() {
		return isFavourite;
	}

	public void setIsFavourite(Integer isFavourite) {
		this.isFavourite = isFavourite;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDateValue() {
		return dateValue;
	}

	public void setDateValue(String dateValue) {
		this.dateValue = dateValue;
	}

	public Long getCalDays() {
		return calDays;
	}

	public void setCalDays(Long calDays) {
		this.calDays = calDays;
	}

	public String getNameFields() {
		return nameFields;
	}

	public void setNameFields(String nameFields) {
		this.nameFields = nameFields;
	}
	
	
}
