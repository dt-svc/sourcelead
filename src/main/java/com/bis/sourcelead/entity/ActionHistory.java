package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.star.util.DateTime;

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
    private DateTime actionTime;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_LOGIN_LOGOUT_SESSION_ID", nullable=false)
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

	public DateTime getActionTime() {
		return actionTime;
	}

	public void setActionTime(DateTime actionTime) {
		this.actionTime = actionTime;
	}

	public UserLoginLogoutSession getUserLoginLogoutSessionId() {
		return userLoginLogoutSessionId;
	}

	public void setUserLoginLogoutSessionId(UserLoginLogoutSession userLoginLogoutSessionId) {
		this.userLoginLogoutSessionId = userLoginLogoutSessionId;
	}

	


}
