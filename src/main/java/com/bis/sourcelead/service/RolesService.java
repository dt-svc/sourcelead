package com.bis.sourcelead.service;

import java.util.Collection;

import com.bis.sourcelead.entity.Roles;

public interface RolesService {
	
	public Roles getRolesByRoleName(String roleName) throws Exception;
	
	Collection<Roles> getRoles() throws Exception;
	
	public Roles getRolesByDisplayName(String displayName) throws Exception;

}
