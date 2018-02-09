package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "USER_MAIL_BOX")
public class UserMailBox implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")        
    private User userId;

	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID")        
    private OrgLocations orgLocationId;
	

    @JoinColumn(name="SIGNATURE")      
    @Type(type="text")
    private String signature;
	
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
	
	@Column(name = "SALUTATION")
	private String salutation;
	
	@Column(name = "BODY")
	@Type(type="text")
	private String body;
	
	@Column(name = "CAMPAIGN_TABLE_DATA")
	@Type(type="text")
	private String campaignTableData;
	
	@Column(name = "MAIL_TYPE")
	private Integer mailType;
	
	@Column(name = "CANDIDATE_ID")
	@Type(type="text")
	private String candidateId;
	
	@Column(name = "REQUIREMENT_ID")
	@Type(type="text")
	private String requirementId;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name="IS_FAVOURITE")
	private Integer isFavourite;
	
	@Column(name = "NAME_FIELDS")
	private String nameFields;
	
	@Column(name = "PHONE_NO")
	private String phno;

	@Column(name = "COMMENTS")
	@Type(type="text")
	private String comments;
	
	@Column(name="IS_MAIL_READ")
	private Integer isMailRead;
	
	@Column(name = "FILE_URL")
	@Type(type="text")
	private String fileUrl;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Transient
	private Integer unreadInboxCount;
	
	@Transient
	private Integer unreadRequestedCount;
	
	@Transient
	private Integer totalUnReadMailsCount;
	
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	@Type(type="text")
	private String fileName;
	
	
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
	private String calDays;
	
	@Transient
	List<Candidate> candidatesList;
	
	@Transient
	private String nextMailUUID;
	
	@Transient
	private String previousMailUUID;
	
	@Transient
	Map<Long,String> hotListCandidatesList;
		
	
	

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

	

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	
	public Integer getMailType()
	{
		return mailType;
	}

	public void setMailType(Integer mailType)
	{
		this.mailType = mailType;
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

	

	public String getCalDays() {
		return calDays;
	}

	public void setCalDays(String calDays) {
		this.calDays = calDays;
	}

	public String getNameFields() {
		return nameFields;
	}

	public void setNameFields(String nameFields) {
		this.nameFields = nameFields;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<Candidate> getCandidatesList() {
		return candidatesList;
	}

	public void setCandidatesList(List<Candidate> candidatesList) {
		this.candidatesList = candidatesList;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getIsMailRead() {
		return isMailRead;
	}

	public void setIsMailRead(Integer isMailRead) {
		this.isMailRead = isMailRead;
	}

	public Integer getUnreadInboxCount() {
		return unreadInboxCount;
	}

	public void setUnreadInboxCount(Integer unreadInboxCount) {
		this.unreadInboxCount = unreadInboxCount;
	}

	public Integer getUnreadRequestedCount() {
		return unreadRequestedCount;
	}

	public void setUnreadRequestedCount(Integer unreadRequestedCount) {
		this.unreadRequestedCount = unreadRequestedCount;
	}

	public Integer getTotalUnReadMailsCount() {
		return totalUnReadMailsCount;
	}

	public void setTotalUnReadMailsCount(Integer totalUnReadMailsCount) {
		this.totalUnReadMailsCount = totalUnReadMailsCount;
	}

	public String getNextMailUUID() {
		return nextMailUUID;
	}

	public void setNextMailUUID(String nextMailUUID) {
		this.nextMailUUID = nextMailUUID;
	}

	public String getPreviousMailUUID() {
		return previousMailUUID;
	}

	public void setPreviousMailUUID(String previousMailUUID) {
		this.previousMailUUID = previousMailUUID;
	}

	public Map<Long, String> getHotListCandidatesList() {
		return hotListCandidatesList;
	}

	public void setHotListCandidatesList(Map<Long, String> hotListCandidatesList) {
		this.hotListCandidatesList = hotListCandidatesList;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getRequirementId()
	{
		return requirementId;
	}

	public void setRequirementId(String requirementId)
	{
		this.requirementId = requirementId;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	
	
	
}
