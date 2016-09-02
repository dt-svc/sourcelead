package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Roles;

public interface RolesDao {
	
	public Roles getRolesByRoleName(String roleName) throws Exception;
	
	Roles getRolesById(Long id) throws Exception;
	
	List<Roles> getRoles(Long orgLocationId) throws Exception;
	
	public Roles getRolesByDisplayName(String displayName) throws Exception;
	
	List<Roles> getMoudleRoles(Long orgId) throws Exception;

}
