package com.bis.sourcelead.entity;

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

@Entity
@Table(name="NOTIFICATIONS")
public class Notifications {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="NOTIFICATION_SCENARIO_ID", nullable=false )	
	private EntityType notificationScenarioId;
	
	@Column(name="NOTIFICATION_TO")
	private String notificationTo;
	
	@Column(name="NOTIFICATION_FORM")
	private String notificationFrom;
	
	@Column(name="BODY", columnDefinition = "TEXT default NULL")
	private String body;
	
	@Column(name="MODE")
	private String mode;
	
	@Column(name="ACTION_LINK")
	private String actionLink;
	
	@Column(name="ORG_CODE")
	private String orgCode;
	
	@Column(name="LOCATION_CODE")
	private String locationCode;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EntityType getNotificationScenarioId() {
		return notificationScenarioId;
	}

	public void setNotificationScenarioId(EntityType notificationScenarioId) {
		this.notificationScenarioId = notificationScenarioId;
	}

	public String getNotificationTo() {
		return notificationTo;
	}

	public void setNotificationTo(String notificationTo) {
		this.notificationTo = notificationTo;
	}

	public String getNotificationFrom() {
		return notificationFrom;
	}

	public void setNotificationFrom(String notificationFrom) {
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

}
