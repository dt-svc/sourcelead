package com.bis.sourcelead.dto;

import java.io.Serializable;

public class SourceleadRolesDTO implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	 private Long id;
	 
	 private String RoleName;
	 
	 private String DisplayName;
	 
	 private String uuid;
	 
	 private Integer actionsCount;
	 
	 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}

	public String getDisplayName() {
		return DisplayName;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getActionsCount() {
		return actionsCount;
	}

	public void setActionsCount(Integer actionsCount) {
		this.actionsCount = actionsCount;
	}

}
