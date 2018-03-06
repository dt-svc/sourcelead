package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ClientOrgRelationsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long orgLocationId;
	
	private String orgName;
	
	private Long orgRelationId;

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}
}
