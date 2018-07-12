package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class DashboardItemsDTO implements Serializable {
	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	/* private String items; */
	private long userId;
	private long orgLocationId;
	/* private List<String> itemsList; */
	/* private JSONArray itemsArray; */
	private List<JSONObject> itemsObjectList;
	private String jsonData;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonString) {
		this.jsonData = jsonString;
	}

	public List<JSONObject> getItemsObjectList() {
		return itemsObjectList;
	}

	public void setItemsObjectList(List<JSONObject> itemsObjectList) {
		this.itemsObjectList = itemsObjectList;
	}

}
