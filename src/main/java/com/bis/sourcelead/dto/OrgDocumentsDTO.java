package com.bis.sourcelead.dto;

import java.util.List;

import javax.persistence.Transient;

import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;

public class OrgDocumentsDTO {
	private String docName;
	private String contractType;
	private String spocName;
	private String fileName;
	 private String startDateStr;
	 private String endDateStr;
	 private String ownerName;
	 private String fileType;
	 private String fileUrl;
	 private String id;
	 private String msg;
	 private String description;
	 private User spocUser;
	 private User ownerUser;
	 private Long ownerId;
	 private Long spocId;
	 
	 public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public User getOwnerUser() {
		return ownerUser;
	}
	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}
	public User getSpocUser() {
		return spocUser;
	}
	public void setSpocUser(User spocUser) {
		this.spocUser = spocUser;
	}
	private List<OrgSpoc> orgSpocsList;
		
	 private List<User> ownersList;
	 
	public List<OrgSpoc> getOrgSpocsList() {
		return orgSpocsList;
	}
	public void setOrgSpocsList(List<OrgSpoc> orgSpocsList) {
		this.orgSpocsList = orgSpocsList;
	}
	public List<User> getOwnersList() {
		return ownersList;
	}
	public void setOwnersList(List<User> ownersList) {
		this.ownersList = ownersList;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getSpocName() {
		return spocName;
	}
	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getSpocId()
	{
		return spocId;
	}
	public void setSpocId(Long spocId)
	{
		this.spocId = spocId;
	}
	 

}
