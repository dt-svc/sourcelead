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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ACTION_HISTORY")
public class ActionHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "USERID")
    private Long userId;
	
	@Column(name = "ACTION_NAME")
    private String actionName;
	
	@Column(name = "ACTION_PERFORMED")
	private String actionPerformed;
	
	@Column(name = "ACTION_TIME")
    private Date actionTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocations;
	
	@Transient
	@JsonProperty
	private String actionTimeStr;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_LOGIN_LOGOUT_SESSION_ID")
    private UserLoginLogoutSession userLoginLogoutSessionId;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionPerformed() {
		return actionPerformed;
	}

	public void setActionPerformed(String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}
	
	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public String getActionTimeStr() {
		return actionTimeStr;
	}

	public void setActionTimeStr(String actionTimeStr) {
		this.actionTimeStr = actionTimeStr;
	}

	public UserLoginLogoutSession getUserLoginLogoutSessionId() {
		return userLoginLogoutSessionId;
	}

	public void setUserLoginLogoutSessionId(UserLoginLogoutSession userLoginLogoutSessionId) {
		this.userLoginLogoutSessionId = userLoginLogoutSessionId;
	}

	


}
