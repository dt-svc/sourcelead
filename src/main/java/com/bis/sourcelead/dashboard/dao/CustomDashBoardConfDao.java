package com.bis.sourcelead.dashboard.dao;

import java.util.List;

import com.bis.sourcelead.dashboard.entity.CustomDashBoardConfiguration;

public interface CustomDashBoardConfDao {

	CustomDashBoardConfiguration saveCustomDashBoardConf(CustomDashBoardConfiguration customDashBoardConf);

	List<CustomDashBoardConfiguration> getCustomConfDashBoardByLocationId(Long locationId);

	CustomDashBoardConfiguration getCustomDashboardConfByUUID(String customDashBoardUUID, Long orgLocationId);

	CustomDashBoardConfiguration getCustomDashboardConfById(Long customDashBoardConfId, Long orgLocationId);

	int deleteExistedDashBoardRoles(Long customDashBoardConfId);

	int deleteExistedDashBoardAssociates(Long customDashBoardConfId);
}
