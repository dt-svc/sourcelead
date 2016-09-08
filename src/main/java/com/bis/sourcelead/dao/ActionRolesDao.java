package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ActionRoles;
import com.bis.sourcelead.entity.Org;

public interface ActionRolesDao {

	public ActionRoles addActionRoles(ActionRoles actionRoles) throws Exception;
	
	List<ActionRoles> getSourceLeadActionRolesListByOrgId(Long orgId,List<Long> modulesId,Org org) throws Exception;
	
	List<ActionRoles> getOrgActionRolesByRoleAndOrgLocationId(Long roleId,Long orgLocationId) throws Exception;
	
	ActionRoles getActionRolesByActionIdAndRoleIdAndLocationId(Long actionId,Long roleId,Long orgId) throws Exception;
}
