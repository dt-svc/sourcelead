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

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="NOTIFICATIONS")
public class Notifications implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="NOTIFICATION_SCENARIO_ID", nullable=false )	
	private EmailScenario notificationScenarioId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="NOTIFICATION_TO", nullable=false )	
	private User notificationTo;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="NOTIFICATION_FORM", nullable=false )	
	private User notificationFrom;
	
	@Column(name="BODY", columnDefinition = "TEXT default NULL")
	private String body;
	
	@Column(name="MODE")
	private String mode;
	
	@Column(name="ACTION_LINK")
	private String actionLink;
	
	@Column(name = "REJECT_ACTION_LINK")
	@Type(type="text")
	private String rejectActionLink;
	
	@Column(name="ORG_CODE")
	private String orgCode;
	
	@Column(name="LOCATION_CODE")
	private String locationCode;
	
	@DateTimeFormat(pattern="dd/MM/yyyy  HH:mm:ss")
    @Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="NOTIFICATION_STATUS")
	private String notificationStatus;
	
	@Column(name="USER_NOTIFICATION_STATUS")
	private String userNotificationStatus;
	
	@Column(name="UUID")
	private String uuId;
	
	@Column(name = "PARAMETER_LIST", columnDefinition = "TEXT default NULL")
	private String parameterList;
	
	@Column(name="SPOC_UUID")
	private String spocUUID;
	
	//to get only time from datetime
	@Transient
	@JsonProperty
	private String time;
	
	//to get only date from datetime
	@Transient
	@JsonProperty
	private String dateValue;
	
	//to calculate days
	@Transient
	@JsonProperty
	private String calDays;
	
	//to notification count
	@Transient
	private Long notificationCount;

	@Transient
	@JsonProperty
	private String notificationFirstName;
	
	@Transient
	@JsonProperty
	private String notificationLastName;
	
	@Transient
	@JsonProperty
	private String notificationImage;
	
	@Transient
	@JsonProperty
	private String spocInvitationStatus;
	
	@Transient
	@JsonProperty
	private String orgRelationUUID;
	
	@Transient
	@JsonProperty
	private String responderType;
	
	
	@Transient
	@JsonProperty
	private String notificationUserType;
	
	@Transient
	@JsonProperty
	private String notificationFormId;
	
	
	
	public String getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public String getDateValue() {
		return dateValue;
	}

	public void setDateValue(String dateValue) {
		this.dateValue = dateValue;
	}

	public Long getNotificationCount() {
		return notificationCount;
	}

	public void setNotificationCount(Long notificationCount) {
		this.notificationCount = notificationCount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmailScenario getNotificationScenarioId() {
		return notificationScenarioId;
	}

	public void setNotificationScenarioId(EmailScenario notificationScenarioId) {
		this.notificationScenarioId = notificationScenarioId;
	}

	public User getNotificationTo() {
		return notificationTo;
	}

	public void setNotificationTo(User notificationTo) {
		this.notificationTo = notificationTo;
	}

	public User getNotificationFrom() {
		return notificationFrom;
	}

	public void setNotificationFrom(User notificationFrom) {
		this.notificationFrom = notificationFrom;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getActionLink() {
		return actionLink;
	}

	public void setActionLink(String actionLink) {
		this.actionLink = actionLink;
	}

	public String getRejectActionLink() {
		return rejectActionLink;
	}

	public void setRejectActionLink(String rejectActionLink) {
		this.rejectActionLink = rejectActionLink;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
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

	public String getUserNotificationStatus() {
		return userNotificationStatus;
	}

	public void setUserNotificationStatus(String userNotificationStatus) {
		this.userNotificationStatus = userNotificationStatus;
	}
	
	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getSpocUUID() {
		return spocUUID;
	}

	public void setSpocUUID(String spocUUID) {
		this.spocUUID = spocUUID;
	}

	public String getNotificationFirstName() {
		return notificationFirstName;
	}

	public void setNotificationFirstName(String notificationFirstName) {
		this.notificationFirstName = notificationFirstName;
	}

	public String getNotificationLastName() {
		return notificationLastName;
	}

	public void setNotificationLastName(String notificationLastName) {
		this.notificationLastName = notificationLastName;
	}

	public String getNotificationUserType() {
		return notificationUserType;
	}

	public void setNotificationUserType(String notificationUserType) {
		this.notificationUserType = notificationUserType;
	}

	public String getNotificationFormId() {
		return notificationFormId;
	}

	public void setNotificationFormId(String notificationFormId) {
		this.notificationFormId = notificationFormId;
	}

	public String getCalDays() {
		return calDays;
	}

	public void setCalDays(String calDays) {
		this.calDays = calDays;
	}

	public String getNotificationImage() {
		return notificationImage;
	}

	public void setNotificationImage(String notificationImage) {
		this.notificationImage = notificationImage;
	}

	public String getSpocInvitationStatus() {
		return spocInvitationStatus;
	}

	public void setSpocInvitationStatus(String spocInvitationStatus) {
		this.spocInvitationStatus = spocInvitationStatus;
	}

	public String getOrgRelationUUID() {
		return orgRelationUUID;
	}

	public void setOrgRelationUUID(String orgRelationUUID) {
		this.orgRelationUUID = orgRelationUUID;
	}

	public String getResponderType() {
		return responderType;
	}

	public void setResponderType(String responderType) {
		this.responderType = responderType;
	}

	public String getParameterList() {
		return parameterList;
	}

	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}
	
	
}
