package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;

public interface SourceleadActionsService {

	List<SourceleadActions> getLoginUserRoleActions(List<Long> modulesId,Long roleId,Long orgId) throws Exception;
	
	List<SourceleadActions> getLoginUserRoleActionsByOrgLocationId(List<Long> modulesId,Long roleId,Long orgLocationId) throws Exception;
	
	List<Long> getAllSourceleadActionsIds(OrgLocations orgLocations) throws Exception;
	
	List<Long> getSourceLeadActionsByModuleIdAndTabId(Long moduleId,Long tabId) throws Exception;
	
	List<Long> getSourceLeadTimeSheetActionsByModuleIdAndTabId(Long moduleId, Long myTimeSheetTabId,Long timeSheetApprovalTabId) throws Exception;
	
	List<Long> getSourceLeadActionsByTabId(Long tabId) throws Exception;
	
	List<Long> getSourceLeadActionsByModuleId(Long moduleId) throws Exception;
	
	List<SourceleadActions> getLoginCrowdSourcingUserRoleActionsByOrgLocationId(Long roleId) throws Exception;
	
	List<SourceleadActions> getAllSourceleadActionsByOrgLocationId(OrgLocations orgLocations) throws Exception;
	
	SourceleadActions getSourceleadActionsByActionRole(String actionRole);
}
