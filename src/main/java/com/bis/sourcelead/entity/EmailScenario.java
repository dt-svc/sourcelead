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

@Entity
@Table(name="EMAIL_SCENARIO")
public class EmailScenario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="SCENARIO_NAME")
	private String scenarioName;
	
	@Column(name = "ENTITY_TYPE", nullable = false)
	private String entityType;
	
	@Column(name="UNIQUE_SCENARIO_NAME")
	private String uniqueScenarioName;
	
	@Column(name="DESCRIPTION", columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name="SUBJECT", columnDefinition = "TEXT default NULL")
	private String subject;
	
	@Column(name="TEMPLATE_TYPE")
	private String templateType;
	
	@Column(name="SIGNATURE", columnDefinition = "TEXT default NULL")
	private String signature;
	
	@Column(name="BODY", columnDefinition = "TEXT default NULL")
	private String body;
	
	@Column(name="CALL_TO_ACTION_TEXT", columnDefinition = "TEXT default NULL")
	private String callToActionText;
	
	@Column(name="BUTTON_TEXT", columnDefinition = "TEXT default NULL")
	private String buttonText;
	
	@Column(name="IS_TEMPLATE_MODIFIED")
	private String isTemplateModified;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="USE_LOGO")
	private String useLogo;
	
	@ManyToOne
	@JoinColumn(name = "TAB_ID")
	private Tabs tabId;
	
	@Column(name="ORG_CODE")
	private String orgCode;
	
	@Column(name="LOCATION_CODE")
	private String locationCode;
	
	@Column(name="DISCLAIMER_TEXT", columnDefinition = "TEXT default NULL")
	private String disclaimerText;
	
	@Column(name="BODY_URL",columnDefinition = "TEXT default NULL")
	private String bodyUrl;
	
	@Column(name="DEFAULT_TEMPLATE")
	private String defaultTemplate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID")
    private OrgLocations orgLocationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
    private EmailScenario parentId;
	
	
	@Column(name="EMAIL_UUID")
	private String emailUUID;
	
	@Column(name="STATUS")
	private Integer status;
	
	@Transient
	private String UUID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getUniqueScenarioName() {
		return uniqueScenarioName;
	}

	public void setUniqueScenarioName(String uniqueScenarioName) {
		this.uniqueScenarioName = uniqueScenarioName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCallToActionText() {
		return callToActionText;
	}

	public void setCallToActionText(String callToActionText) {
		this.callToActionText = callToActionText;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public String getIsTemplateModified() {
		return isTemplateModified;
	}

	public void setIsTemplateModified(String isTemplateModified) {
		this.isTemplateModified = isTemplateModified;
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

	public String getUseLogo() {
		return useLogo;
	}

	public void setUseLogo(String useLogo) {
		this.useLogo = useLogo;
	}

	public Tabs getTabId()
	{
		return tabId;
	}

	public void setTabId(Tabs tabId)
	{
		this.tabId = tabId;
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

	public String getDisclaimerText() {
		return disclaimerText;
	}

	public void setDisclaimerText(String disclaimerText) {
		this.disclaimerText = disclaimerText;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getBodyUrl() {
		return bodyUrl;
	}

	public void setBodyUrl(String bodyUrl) {
		this.bodyUrl = bodyUrl;
	}

	public String getDefaultTemplate() {
		return defaultTemplate;
	}

	public void setDefaultTemplate(String defaultTemplate) {
		this.defaultTemplate = defaultTemplate;
	}


	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public EmailScenario getParentId() {
		return parentId;
	}

	public void setParentId(EmailScenario parentId) {
		this.parentId = parentId;
	}

	public String getEmailUUID() {
		return emailUUID;
	}

	public void setEmailUUID(String emailUUID) {
		this.emailUUID = emailUUID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
