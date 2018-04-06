package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.Tabs;

public interface TabsDao {
	
	List<Tabs> getTabsListByLoginUserRoleIdAndOrgLocationId(Long roleId,Long orgLocationId) throws Exception;
	
	List<Tabs> getSourceLeadTabsListByOrgId(Long roleId,Long orgId) throws Exception;
	
	List<Tabs> getTabsList(List<Long> modulesId,Long roleId,Long orgId) throws Exception;
	
	Collection<Tabs> getTabsListByOrgLocationId(Long roleId,Long orgLocationId) throws Exception;
	
	List<Long> getTabsIdsListByUserRoleIdAndOrgLocationId(Long roleId,Long orgLocationId) throws Exception;
	
	List<Tabs> getTabsListByIds(List<Long> tabIds) throws Exception;
	
	boolean getTabAccessByRoleIdAndTabId(Long roleId,Long orgLocationId,Long tabId) throws Exception;
	
	Collection<Tabs> getCrowdSourcingTabsListByRoleId(Long roleId) throws Exception;
	
	List<String> getTabsNamesListByActionsIds(List<Long> actionIdsList) throws Exception;

	Tabs getTabListByTabId(Long tabId);
	
	Collection<Tabs> getAllTabsList() throws Exception;
	
	List<Tabs> getClildTabsListByTabId(Long tabId) throws Exception;
	
	List<Tabs> getTabListByActionsIds(List<Long> actionIdsList) throws Exception;

	
}
