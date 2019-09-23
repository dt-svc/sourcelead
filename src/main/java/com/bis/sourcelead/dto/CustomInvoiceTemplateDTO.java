package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.annotations.Type;
import com.fasterxml.jackson.annotation.JsonBackReference;

public class CustomInvoiceTemplateDTO extends SearchDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String uuid;
	
	@Required(desc = "name")
	@Type(desc = "name", maxLength = 100)
	private String name;
	
	@Type(desc = "description", maxLength = 1000)
	private String description;
	
	private int status;
	
	private String createdByUserId;
	
	private String createdByUserName;
	
	private String createdDate;
	
	private String updatedByUserId;
	
	private String updatedByUserName;
	
	private String updatedDate;
	
	private long orgLocationId;
	
	private long orgRelationId;
	
	private String jsonData;
	
	@JsonBackReference
	private List<JSONObject> itemObjectList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(String createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public String getCreatedByUserName() {
		return createdByUserName;
	}

	public void setCreatedByUserName(String createdByUserName) {
		this.createdByUserName = createdByUserName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedByUserId() {
		return updatedByUserId;
	}

	public void setUpdatedByUserId(String updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}

	public String getUpdatedByUserName() {
		return updatedByUserName;
	}

	public void setUpdatedByUserName(String updatedByUserName) {
		this.updatedByUserName = updatedByUserName;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public long getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public List<JSONObject> getItemObjectList() {
		return itemObjectList;
	}

	public void setItemObjectList(List<JSONObject> itemObjectList) {
		this.itemObjectList = itemObjectList;
	}

}
