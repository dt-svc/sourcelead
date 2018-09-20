package com.bis.sourcelead.dto;

import java.math.BigInteger;

public class ConnectionsDTO {
	private BigInteger id;
	private String userName;
	private String email;
	private String userType;
	private String orgName;
	private BigInteger count;
	private String userCode;
	private String fileUrl;
	private String connectionUUID;
	private BigInteger requestSent;
	private BigInteger requestRecieved;
	private BigInteger connectedCount;
	private BigInteger sentCount;
	private BigInteger receivedCount;
	private String countFlag; //flag set to true when list size > 0
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public BigInteger getCount() {
		return count;
	}
	public void setCount(BigInteger count) {
		this.count = count;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getConnectionUUID() {
		return connectionUUID;
	}
	public void setConnectionUUID(String connectionUUID) {
		this.connectionUUID = connectionUUID;
	}
	public BigInteger getRequestSent() {
		return requestSent;
	}
	public void setRequestSent(BigInteger requestSent) {
		this.requestSent = requestSent;
	}
	public BigInteger getRequestRecieved() {
		return requestRecieved;
	}
	public void setRequestRecieved(BigInteger requestRecieved) {
		this.requestRecieved = requestRecieved;
	}
	public BigInteger getConnectedCount() {
		return connectedCount;
	}
	public void setConnectedCount(BigInteger connectedCount) {
		this.connectedCount = connectedCount;
	}
	public BigInteger getSentCount() {
		return sentCount;
	}
	public void setSentCount(BigInteger sentCount) {
		this.sentCount = sentCount;
	}
	public BigInteger getReceivedCount() {
		return receivedCount;
	}
	public void setReceivedCount(BigInteger receivedCount) {
		this.receivedCount = receivedCount;
	}
	public String getCountFlag() {
		return countFlag;
	}
	public void setCountFlag(String countFlag) {
		this.countFlag = countFlag;
	}
	@Override
	public String toString() {
		return "ConnectionsDTO [id=" + id + ", userName=" + userName + ", email=" + email + ", userType=" + userType
				+ ", orgName=" + orgName + ", count=" + count + ", userCode=" + userCode + ", fileUrl=" + fileUrl
				+ ", connectionUUID=" + connectionUUID + ", requestSent=" + requestSent + ", requestRecieved="
				+ requestRecieved + ", connectedCount=" + connectedCount + ", sentCount=" + sentCount
				+ ", receivedCount=" + receivedCount + ", countFlag=" + countFlag + "]";
	}
	
}
