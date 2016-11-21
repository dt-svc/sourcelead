package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.SourceleadActions;

public interface SourceleadActionsDao {
	
	List<SourceleadActions> getLoginUserRoleActions(List<Long> modulesId,Long roleId,Long orgId) throws Exception;
	
	List<SourceleadActions> getLoginUserRoleActionsByOrgLocationId(List<Long> modulesId,Long roleId,Long orgLocationId) throws Exception;

	List<SourceleadActions> getAllSourceleadActions(Org org) throws Exception;
	
	List<Long> getAllSourceleadActionsIds(Org org) throws Exception;
	
	List<Long> getSourceLeadActionsByModuleIdAndTabId(Long moduleId,Long tabId) throws Exception;
	
	List<Long> getSourceLeadTimeSheetActionsByModuleIdAndTabId(Long moduleId, Long myTimeSheetTabId,Long timeSheetApprovalTabId) throws Exception;
	
	List<Long> getSourceLeadActionsByTabId(Long tabId) throws Exception;
	
	List<Long> getSourceLeadActionsByModuleId(Long moduleId) throws Exception;
}
