package com.bis.sourcelead.dto;

import java.io.Serializable;

public class RequirementSearchDTO extends SearchDTO implements Serializable {
	private String status;
	private String clients;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getClients() {
		return clients;
	}
	public void setClients(String clients) {
		this.clients = clients;
	}
	

	
}
