package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadRoles;

public interface SourceleadRolesService {

	List<SourceleadRoles> getSourceleadRoles();
	
	boolean roleHaveActionAccessOrNot(String actionId,String roleName);
	
	SourceleadRoles getSourceleadRolesByRoleName(String  roleName) throws Exception;
	
	List<SourceleadRoles> getGuestOrgSourceleadRoles() throws Exception;
	
	SourceleadRoles getSourceleadRolesByUUID(String uuid) throws Exception;
	
	SourceleadRoles saveSourceleadRoles(SourceleadRoles sourceleadRoles) throws Exception;
	


}
