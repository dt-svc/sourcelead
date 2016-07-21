package com.bis.sourcelead.dao;

import java.util.Collection;

import com.bis.sourcelead.entity.Roles;

public interface RolesDao {
	
	public Roles getRolesByRoleName(String roleName) throws Exception;
	
	Collection<Roles> getRoles() throws Exception;
	
	public Roles getRolesByDisplayName(String displayName) throws Exception;

}
