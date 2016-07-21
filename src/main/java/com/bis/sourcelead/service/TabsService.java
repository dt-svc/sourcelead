package com.bis.sourcelead.service;

import java.util.Collection;

import com.bis.sourcelead.entity.Tabs;

public interface TabsService {
	
	Collection<Tabs> getTabsListByLoginUserRoleIdAndOrgLocationId(Long roleId,Long orgLocationId) throws Exception;
	
	Collection<Tabs> getSourceLeadTabsListByOrgId(Long roleId,Long orgId) throws Exception;

}
