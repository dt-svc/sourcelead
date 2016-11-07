package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Roles;

public interface RolesService {
	
	public Roles getRolesByRoleName(String roleName) throws Exception;
	
	Roles getRolesById(Long id) throws Exception;
	
	List<Roles> getRoles(Long orgLocationId) throws Exception;
	
	public Roles getRolesByDisplayName(String displayName) throws Exception;
	
	List<Roles> getMoudleRoles(Long orgId,Long userRoleId) throws Exception;
	
	List<Roles> getSourceleadRoles(Long orgId) throws Exception;
	
	public Roles storeRoles(Roles roles);
	
	public Roles getRolesByRoleNameAndOrgLocationId(String roleName,Long orgLocationId) throws Exception;

}
