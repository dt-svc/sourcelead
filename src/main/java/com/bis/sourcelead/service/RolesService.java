package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Roles;

public interface RolesService {
	
	public Roles getRolesByRoleName(String roleName) throws Exception;
	
	List<Roles> getRoles(Long orgLocationId) throws Exception;
	
	public Roles getRolesByDisplayName(String displayName) throws Exception;
	
	List<Roles> getMoudleRoles(Long orgId) throws Exception;

}
