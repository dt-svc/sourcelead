package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import org.json.JSONObject;

public class CustomInvoiceTemplateDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String jsonData;
	private List<JSONObject> itemObjectList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
