package com.bis.sourcelead.dto;

import java.io.Serializable;

public class NotificationDTO extends SearchDTO implements Serializable{
	
	private String firstName;
	private String lastName;
	private String userType;
	private String fileUrl;
	private String body;
	private String calDays;
	private String spocUUID;
	private String actionLink;
	private String spocInvitationStatus;
	private String responderType;
	private String orgRelationUUID;
	private String uuId;
	private String mode;
	
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getUserType()
	{
		return userType;
	}
	public void setUserType(String userType)
	{
		this.userType = userType;
	}
	public String getFileUrl()
	{
		return fileUrl;
	}
	public void setFileUrl(String fileUrl)
	{
		this.fileUrl = fileUrl;
	}
	public String getBody()
	{
		return body;
	}
	public void setBody(String body)
	{
		this.body = body;
	}
	public String getCalDays()
	{
		return calDays;
	}
	public void setCalDays(String calDays)
	{
		this.calDays = calDays;
	}
	public String getSpocUUID() {
		return spocUUID;
	}
	public String getActionLink() {
		return actionLink;
	}
	public String getSpocInvitationStatus() {
		return spocInvitationStatus;
	}
	public String getResponderType() {
		return responderType;
	}
	public String getOrgRelationUUID() {
		return orgRelationUUID;
	}
	public void setSpocUUID(String spocUUID) {
		this.spocUUID = spocUUID;
	}
	public void setActionLink(String actionLink) {
		this.actionLink = actionLink;
	}
	public void setSpocInvitationStatus(String spocInvitationStatus) {
		this.spocInvitationStatus = spocInvitationStatus;
	}
	public void setResponderType(String responderType) {
		this.responderType = responderType;
	}
	public void setOrgRelationUUID(String orgRelationUUID) {
		this.orgRelationUUID = orgRelationUUID;
	}
	public String getUuId() {
		return uuId;
	}
	public void setUuId(String uuId) {
		this.uuId = uuId;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
