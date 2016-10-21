package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Tabs;

public interface TabsService {
	
	List<Tabs> getTabsListByLoginUserRoleIdAndOrgLocationId(Long roleId,Long orgLocationId) throws Exception;
	
	List<Tabs> getSourceLeadTabsListByOrgId(Long roleId,Long orgId) throws Exception;

}
