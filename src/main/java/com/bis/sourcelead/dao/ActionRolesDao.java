package com.bis.sourcelead.dao;

import java.util.Collection;

import com.bis.sourcelead.entity.ActionRoles;

public interface ActionRolesDao {

	public ActionRoles addActionRoles(ActionRoles actionRoles) throws Exception;
	
	Collection<ActionRoles> getSourceLeadActionRolesListByOrgId(Long orgId) throws Exception;
	
	Collection<ActionRoles> getOrgActionRolesByRoleAndOrgLocationId(Long roleId,Long orgLocationId) throws Exception;
}
