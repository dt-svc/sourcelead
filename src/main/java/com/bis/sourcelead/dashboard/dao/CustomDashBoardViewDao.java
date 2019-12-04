package com.bis.sourcelead.dashboard.dao;

import java.util.List;

import com.bis.sourcelead.dashboard.dto.CommonDataDto;
import com.bis.sourcelead.dashboard.dto.ConfigureDashBoardViewDTO;
import com.bis.sourcelead.dashboard.entity.CustomReportConfiguration;

public interface CustomDashBoardViewDao {

	List<ConfigureDashBoardViewDTO> getConfiguredModuleByDashBoardId(Long dashBoardId);

	List<CustomReportConfiguration> getConfiguredReportByDashBoardId(Long dashBoardId);

	String getMarketplacePurchaseOrderDashBoard(CommonDataDto commonDataDto);

	String getMarketplaceSalesOrderDashBoard(CommonDataDto commonDataDto);

	String getRecentlyAddedCandidates(CommonDataDto commonDataDto);
}
