package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SourceleadActionsDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
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
	public List<Long> getAllSourceleadActionsIds(OrgLocations orgLocations) throws Exception {
		return sourceleadActionsDao.getAllSourceleadActionsIds(orgLocations);
	}

	@Override
	public List<Long> getSourceLeadActionsByModuleIdAndTabId(Long moduleId, Long tabId) throws Exception {
		return sourceleadActionsDao.getSourceLeadActionsByModuleIdAndTabId(moduleId, tabId);
	}
	
	@Override
	public List<Long> getSourceLeadTimeSheetActionsByModuleIdAndTabId(Long moduleId, Long myTimeSheetTabId,Long timeSheetApprovalTabId) throws Exception {
		return sourceleadActionsDao.getSourceLeadTimeSheetActionsByModuleIdAndTabId(moduleId, myTimeSheetTabId, timeSheetApprovalTabId);
	}

	@Override
	public List<Long> getSourceLeadActionsByTabId(Long tabId) throws Exception {
		return sourceleadActionsDao.getSourceLeadActionsByTabId(tabId);
	}

	@Override
	public List<Long> getSourceLeadActionsByModuleId(Long moduleId) throws Exception {
		return sourceleadActionsDao.getSourceLeadActionsByModuleId(moduleId);
	}

	@Override
	public List<SourceleadActions> getLoginCrowdSourcingUserRoleActionsByOrgLocationId(Long roleId) throws Exception {
		return sourceleadActionsDao.getLoginCrowdSourcingUserRoleActionsByOrgLocationId(roleId);
	}

	@Override
	public List<SourceleadActions> getAllSourceleadActionsByOrgLocationId(OrgLocations orgLocations) throws Exception
	{
		return sourceleadActionsDao.getAllSourceleadActionsByOrgLocationId(orgLocations);
	}

	@Override
	public SourceleadActions getSourceleadActionsByActionRole(String actionRole) {
		return sourceleadActionsDao.getSourceleadActionsByActionRole(actionRole);
	}

	@Override
	public List<SourceleadActions> getAllSourceleadActionsForEnrollByOrgLocationIdAndOrgId(OrgLocations orgLocations,Org org)
			throws Exception {
		return sourceleadActionsDao.getAllSourceleadActionsForEnrollByOrgLocationIdAndOrgId(orgLocations,org);
	}

	@Override
	public List<SourceleadActions> getAllSourceleadActionsByTabsList(List<Long> tabsList) throws Exception {
		return sourceleadActionsDao.getAllSourceleadActionsByTabsList(tabsList);
	}

	@Override
	public List<SourceleadActions> getAllSourceleadGuestOrgActionsForEnroll() throws Exception {
		return sourceleadActionsDao.getAllSourceleadGuestOrgActionsForEnroll();
	}

	@Override
	public List<SourceleadActions> getAllSourceleadActionsByIdsList(List<Long> actionIdList) throws Exception {
		return sourceleadActionsDao.getAllSourceleadActionsByIdsList(actionIdList);
	}

	@Override
	public List<Long> getAllSourceleadActionsIdsByTabsList(List<Long> tabsList) throws Exception {
		return sourceleadActionsDao.getAllSourceleadActionsIdsByTabsList(tabsList);
	}

}
