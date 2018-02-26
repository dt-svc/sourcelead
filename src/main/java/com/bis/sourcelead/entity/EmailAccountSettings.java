package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "EMAIL_ACCOUNT_SETTINGS")
public class EmailAccountSettings implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
		
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable=false )        
    private User userId;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
    
	@Column(name = "RECIEVE_SERVER_NAME")
	private String recieveServerName;
	
	@Column(name = "RECIEVE_PORT")
	private String recievePort;
	
	@Column(name = "RECIEVE_EMAIL")
	private String recieveEmail;
	
	@Column(name = "RECIEVE_SSL")
	private String recieveSSL;
	
	@Column(name = "SEND_SERVER_NAME")
	private String SendServerName;
	
	@Column(name = "SEND_PORT")
	private String sendPort;
	
	@Column(name = "SEND_EMAIL")
	private String sendEmail;
	
	@Column(name = "SEND_SSL")
	private String sendSSL;
	
	@Column(name = "EMAIL_TYPE")
	private String emailType;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRecieveServerName() {
		return recieveServerName;
	}

	public void setRecieveServerName(String recieveServerName) {
		this.recieveServerName = recieveServerName;
	}

	public String getRecievePort() {
		return recievePort;
	}

	public void setRecievePort(String recievePort) {
		this.recievePort = recievePort;
	}

	public String getRecieveEmail() {
		return recieveEmail;
	}

	public void setRecieveEmail(String recieveEmail) {
		this.recieveEmail = recieveEmail;
	}

	public String getRecieveSSL() {
		return recieveSSL;
	}

	public void setRecieveSSL(String recieveSSL) {
		this.recieveSSL = recieveSSL;
	}

	public String getSendServerName() {
		return SendServerName;
	}

	public void setSendServerName(String sendServerName) {
		SendServerName = sendServerName;
	}

	public String getSendPort() {
		return sendPort;
	}

	public void setSendPort(String sendPort) {
		this.sendPort = sendPort;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getSendSSL() {
		return sendSSL;
	}

	public void setSendSSL(String sendSSL) {
		this.sendSSL = sendSSL;
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

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
}

