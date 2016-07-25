package com.bis.sourcelead.dao;

import java.util.Collection;

import com.bis.sourcelead.entity.Tabs;

public interface TabsDao {
	
	Collection<Tabs> getTabsListByLoginUserRoleIdAndOrgLocationId(Long roleId,Long orgLocationId) throws Exception;
	
	Collection<Tabs> getSourceLeadTabsListByOrgId(Long roleId,Long orgId) throws Exception;

}
