package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TabsDao;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.service.TabsService;

@Service
public class TabsServiceImpl implements TabsService {
	
	@Autowired
	TabsDao tabsDao;

	@Override
	public List<Tabs> getTabsListByLoginUserRoleIdAndOrgLocationId(Long roleId, Long orgLocationId)throws Exception {
		return tabsDao.getTabsListByLoginUserRoleIdAndOrgLocationId(roleId, orgLocationId);
	}

	@Override
	public List<Tabs> getSourceLeadTabsListByOrgId(Long roleId,Long orgId) throws Exception {
		return tabsDao.getSourceLeadTabsListByOrgId(roleId,orgId);
	}

	@Override
	public List<Tabs> getTabsList(List<Long> modulesId, Long roleId, Long orgId) throws Exception {
		return tabsDao.getTabsList(modulesId, roleId, orgId);
	}

	@Override
	public Collection<Tabs> getTabsListByOrgLocationId(Long roleId, Long orgLocationId)
			throws Exception {
		return tabsDao.getTabsListByOrgLocationId(roleId, orgLocationId);
	}

	@Override
	public List<Long> getTabsIdsListByUserRoleIdAndOrgLocationId(Long roleId, Long orgLocationId)
			throws Exception {
		return tabsDao.getTabsIdsListByUserRoleIdAndOrgLocationId(roleId, orgLocationId);
	}

	@Override
	public List<Tabs> getTabsListByIds(List<Long> tabIds) throws Exception {
		return tabsDao.getTabsListByIds(tabIds);
	}

	@Override
	public boolean getTabAccessByRoleIdAndTabId(Long roleId, Long orgLocationId, Long tabId) throws Exception {
		return tabsDao.getTabAccessByRoleIdAndTabId(roleId, orgLocationId, tabId);
	}

	@Override
	public Collection<Tabs> getCrowdSourcingTabsListByRoleId(Long roleId) throws Exception {
		return tabsDao.getCrowdSourcingTabsListByRoleId(roleId);
	}

	@Override
	public List<String> getTabsNamesListByActionsIds(List<Long> actionIdsList) throws Exception
	{
		return tabsDao.getTabsNamesListByActionsIds(actionIdsList);
	}

	@Override
	public Tabs getTabListByTabId(Long tabId) {
		return tabsDao.getTabListByTabId(tabId);
	}

	@Override
	public Collection<Tabs> getAllTabsList() throws Exception {
		return tabsDao.getAllTabsList();
	}

	@Override
	public List<Tabs> getClildTabsListByTabId(Long tabId) throws Exception {
		return tabsDao.getClildTabsListByTabId(tabId);
	}

	@Override
	public List<Tabs> getTabListByActionsIds(List<Long> actionIdsList) throws Exception {
		return tabsDao.getTabListByActionsIds(actionIdsList);
	}

	@Override
	public List<Tabs> getTabsByRoleIdAndOrgLocationId(long roleId, Long locationId)
	{
		return tabsDao.getTabsByRoleIdAndOrgLocationId(roleId,locationId);
	}

}
