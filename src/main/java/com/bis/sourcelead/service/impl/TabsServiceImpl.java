package com.bis.sourcelead.service.impl;

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

}
