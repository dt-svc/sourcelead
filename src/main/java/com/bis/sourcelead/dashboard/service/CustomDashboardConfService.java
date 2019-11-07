package com.bis.sourcelead.dashboard.service;

import java.util.List;

import com.bis.sourcelead.dashboard.dto.CustomDashBoardConfDTO;
import com.bis.sourcelead.dashboard.entity.CustomDashBoardConfiguration;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SubTabsDTO;
import com.bis.sourcelead.dto.TabsDTO;
import com.bis.sourcelead.entity.OrgLocations;

public interface CustomDashboardConfService {

	CustomDashBoardConfiguration saveCustomDashBoardConf(CustomDashBoardConfDTO customDashBoardConfDTO, OrgLocations orgLocations, Long loginUsrId);

	ResponseList<CustomDashBoardConfDTO> getCustomConfDashBoardByLocationId(Long locationId);

	CustomDashBoardConfiguration getCustomDashboardConfByUUID(String customDashBoardUUID, Long orgLocationId);
	
	CustomDashBoardConfiguration getCustomDashboardConfById(Long customDashBoardConfID, Long orgLocationId);

	List<TabsDTO> getRoleBasedModules(Long locationId, long roleId, Long dashBoardId);

	List<SubTabsDTO> getSubModules(Long tabId);
	
	void getCustomConfDashBoardListByOrgLocationId(Long orgLOcatinId, String customDashBoardUUID);
	
}
