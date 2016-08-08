package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ActionRoles;

public interface ActionRolesDao {

	public ActionRoles addActionRoles(ActionRoles actionRoles) throws Exception;
	
	List<ActionRoles> getSourceLeadActionRolesListByOrgId(Long orgId,List<Long> modulesId) throws Exception;
	
	List<ActionRoles> getOrgActionRolesByRoleAndOrgLocationId(Long roleId,Long orgLocationId) throws Exception;
}
