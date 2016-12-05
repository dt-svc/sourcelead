package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Tabs;

public interface TabsDao {
	
	List<Tabs> getTabsListByLoginUserRoleIdAndOrgLocationId(Long roleId,Long orgLocationId) throws Exception;
	
	List<Tabs> getSourceLeadTabsListByOrgId(Long roleId,Long orgId) throws Exception;
	
	List<Tabs> getTabsList(List<Long> modulesId,Long roleId,Long orgId) throws Exception;
	
	List<Tabs> getTabsListByOrgLocationId(List<Long> modulesId,Long roleId,Long orgLocationId) throws Exception;

}
