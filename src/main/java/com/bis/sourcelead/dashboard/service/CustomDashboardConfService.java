package com.bis.sourcelead.dashboard.service;

import com.bis.sourcelead.dashboard.dto.CustomDashBoardConfDTO;
import com.bis.sourcelead.dashboard.entity.CustomDashBoardConfiguration;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;

public interface CustomDashboardConfService {

	CustomDashBoardConfiguration saveCustomDashBoardConf(CustomDashBoardConfDTO customDashBoardConfDTO, OrgLocations orgLocations, Long loginUsrId);

	ResponseList<CustomDashBoardConfDTO> getCustomConfDashBoardByLocationId(Long locationId);

	CustomDashBoardConfiguration getCustomDashboardConfByUUID(String customDashBoardUUID, Long orgLocationId);
	
	CustomDashBoardConfiguration getCustomDashboardConfById(Long customDashBoardConfID, Long orgLocationId);
}
