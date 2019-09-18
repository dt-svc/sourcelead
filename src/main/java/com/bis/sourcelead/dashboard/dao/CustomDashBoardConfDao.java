package com.bis.sourcelead.dashboard.dao;

import java.util.List;

import com.bis.sourcelead.dashboard.entity.CustomDashBoardConfiguration;
import com.bis.sourcelead.entity.SubTabs;

public interface CustomDashBoardConfDao {

	CustomDashBoardConfiguration saveCustomDashBoardConf(CustomDashBoardConfiguration customDashBoardConf);

	List<CustomDashBoardConfiguration> getCustomConfDashBoardByLocationId(Long locationId);

	CustomDashBoardConfiguration getCustomDashboardConfByUUID(String customDashBoardUUID, Long orgLocationId);

	CustomDashBoardConfiguration getCustomDashboardConfById(Long customDashBoardConfId, Long orgLocationId);

	int deleteExistedDashBoardAssociates(Long customDashBoardConfId);

	List<SubTabs> getSubModules(Long tabId);
}
