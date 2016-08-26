package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ActionRoles;
import com.bis.sourcelead.entity.Org;

public interface ActionRolesService {
	
	public ActionRoles addActionRoles(ActionRoles actionRoles) throws Exception;
	
	List<ActionRoles> getSourceLeadActionRolesListByOrgId(Long orgId,List<Long> modulesId,Org org) throws Exception;
	
	List<ActionRoles> getOrgActionRolesByRoleAndOrgLocationId(Long roleId,Long orgLocationId) throws Exception;

}
