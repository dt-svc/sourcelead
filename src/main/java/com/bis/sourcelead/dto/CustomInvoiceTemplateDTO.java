package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CustomInvoiceTemplateDTO extends SearchDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String uuid;
	private String name;
	private String description;
	private long orgLocationId;
	private long orgRelationId;
	private String jsonData;
	// @JsonIgnoreProperties(ignoreUnknown = true)
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
