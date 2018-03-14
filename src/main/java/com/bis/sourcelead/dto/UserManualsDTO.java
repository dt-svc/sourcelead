package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.bis.sourcelead.entity.UserManualAttachment;
 
public class UserManualsDTO  extends SearchDTO implements Serializable{

	
	private String id;
	
	private String level1Title;
	
	private String userManualUUID;
	
	private String title;
	
	private String description;
	
	private String level;
	
	private String level2Title;
	
	private String levelName;
	
	private String publishOn; 
	
	private String urlName;
	
	private String staticPath;
	
	private List<FileInfo> fileinfo;
	
	private String status;
	
	private String createdBy;
	
	private String createdDate;
	
	private String updatedBy;
	
	private String updatedDate;
	
	private Set<UserManualAttachment> userManualAttachment;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getLevel2Title() {
		return level2Title;
	}

	public void setLevel2Title(String level2Title) {
		this.level2Title = level2Title;
	}

	 
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel1Title() {
		return level1Title;
	}

	public void setLevel1Title(String level1Title) {
		this.level1Title = level1Title;
	}

	public String getPublishOn() {
		return publishOn;
	}

	public void setPublishOn(String publishOn) {
		this.publishOn = publishOn;
	}

	public List<FileInfo> getFileinfo() {
		return fileinfo;
	}

	public void setFileinfo(List<FileInfo> fileinfo) {
		this.fileinfo = fileinfo;
	}

	public String getUserManualUUID() {
		return userManualUUID;
	}

	public void setUserManualUUID(String userManualUUID) {
		this.userManualUUID = userManualUUID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Set<UserManualAttachment> getUserManualAttachment() {
		return userManualAttachment;
	}

	public void setUserManualAttachment(Set<UserManualAttachment> userManualAttachment) {
		this.userManualAttachment = userManualAttachment;
	}
	
}
