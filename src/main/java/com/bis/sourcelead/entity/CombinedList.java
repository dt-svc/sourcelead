package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Transient;

public class CombinedList implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	private List<Roles> roles;
	
	@Transient
	private List<Team> teams;

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
}
