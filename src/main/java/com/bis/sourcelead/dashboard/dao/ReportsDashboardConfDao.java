package com.bis.sourcelead.dashboard.dao;

import java.util.List;

import com.bis.sourcelead.dashboard.entity.CustomReportConfiguration;

public interface ReportsDashboardConfDao {

	CustomReportConfiguration saveReportsConfiguration(CustomReportConfiguration customReportsConf);

	List<CustomReportConfiguration> getAllReportConfiByCustomDashBoardReportUUID(String customDashBoardConfUUID);

	boolean deleteReportConfByDashBoardId(Long dashBoardId);
}
