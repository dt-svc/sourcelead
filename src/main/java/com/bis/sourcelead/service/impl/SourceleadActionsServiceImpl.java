package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SourceleadActionsDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.service.SourceleadActionsService;

@Service
public class SourceleadActionsServiceImpl implements SourceleadActionsService {
	
	@Autowired
	SourceleadActionsDao  sourceleadActionsDao;

	@Override
	public List<SourceleadActions> getLoginUserRoleActions(List<Long> modulesId, Long roleId,Long orgId) throws Exception {
		return sourceleadActionsDao.getLoginUserRoleActions(modulesId, roleId,orgId);
	}

	@Override
	public List<SourceleadActions> getLoginUserRoleActionsByOrgLocationId(List<Long> modulesId, Long roleId,
			Long orgLocationId) throws Exception {
		return sourceleadActionsDao.getLoginUserRoleActionsByOrgLocationId(modulesId, roleId, orgLocationId);
	}

	@Override
	public List<SourceleadActions> getAllSourceleadActions(Org org) throws Exception {
		return sourceleadActionsDao.getAllSourceleadActions(org);
	}

	@Override
	public List<Long> getAllSourceleadActionsIds(Org org) throws Exception {
		return sourceleadActionsDao.getAllSourceleadActionsIds(org);
	}

	@Override
	public List<Long> getSourceLeadActionsByModuleIdAndTabId(Long moduleId, Long tabId) throws Exception {
		return sourceleadActionsDao.getSourceLeadActionsByModuleIdAndTabId(moduleId, tabId);
	}
	
	@Override
	public List<Long> getSourceLeadTimeSheetActionsByModuleIdAndTabId(Long moduleId, Long myTimeSheetTabId,Long timeSheetApprovalTabId) throws Exception {
		return sourceleadActionsDao.getSourceLeadTimeSheetActionsByModuleIdAndTabId(moduleId, myTimeSheetTabId, timeSheetApprovalTabId);
	}

}
