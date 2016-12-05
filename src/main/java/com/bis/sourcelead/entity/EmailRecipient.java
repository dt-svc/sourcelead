package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Map;

public class EmailRecipient implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String email;
	
	private Map<String, String> varMap;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, String> getVarMap() {
		return varMap;
	}

	public void setVarMap(Map<String, String> varMap) {
		this.varMap = varMap;
	}

}
