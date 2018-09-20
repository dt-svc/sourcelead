package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
 
public class EmailConfigurationFromMailDTO implements Serializable{
	
	private String id;
	
	private String email;
	
	private String email_UUID;
	
	private String emailModules;
	
    private OrgLocations orgLocationId;
    
    private List<String> entities;
    
    private String entityList;
    

	public String getEntityList() {
		return entityList;
	}

	public void setEntityList(String entityList) {
		this.entityList = entityList;
	}

	public List<String> getEntities() {
		return entities;
	}

	public void setEntities(List<String> entities) {
		this.entities = entities;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail_UUID() {
		return email_UUID;
	}

	public void setEmail_UUID(String email_UUID) {
		this.email_UUID = email_UUID;
	}

	public String getEmailModules() {
		return emailModules;
	}

	public void setEmailModules(String emailModules) {
		this.emailModules = emailModules;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	
		
}
