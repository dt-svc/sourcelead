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

@Entity
@Table(name = "EMAIL_ACCOUNT_SETTINGS")
public class EmailAccountSettings implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "INCOMING_SERVER")
	private String incomingServer;

	@Column(name = "INCOMING_PORT")
	private String incomingPort;

	@Column(name = "INCOMING_SSL")
	private boolean incomingSSL;

	@Column(name = "OUTGOING_SERVER")
	private String outgoingServer;

	@Column(name = "OUTGOING_PORT")
	private String outgoingPort;

	@Column(name = "OUTGOING_SSL")
	private boolean outgoingSSL;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static void setSerialversionuid(long serialversionuid) {
		serialVersionUID = serialversionuid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIncomingServer() {
		return incomingServer;
	}

	public void setIncomingServer(String incomingServer) {
		this.incomingServer = incomingServer;
	}

	public String getIncomingPort() {
		return incomingPort;
	}

	public void setIncomingPort(String incomingPort) {
		this.incomingPort = incomingPort;
	}

	public boolean isIncomingSSL() {
		return incomingSSL;
	}

	public void setIncomingSSL(boolean incomingSSL) {
		this.incomingSSL = incomingSSL;
	}

	public String getOutgoingServer() {
		return outgoingServer;
	}

	public void setOutgoingServer(String outgoingServer) {
		this.outgoingServer = outgoingServer;
	}

	public String getOutgoingPort() {
		return outgoingPort;
	}

	public void setOutgoingPort(String outgoingPort) {
		this.outgoingPort = outgoingPort;
	}

	public boolean isOutgoingSSL() {
		return outgoingSSL;
	}

	public void setOutgoingSSL(boolean outgoingSSL) {
		this.outgoingSSL = outgoingSSL;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
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

}
