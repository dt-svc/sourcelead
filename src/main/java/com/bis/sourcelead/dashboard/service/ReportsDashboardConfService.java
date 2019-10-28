package com.bis.sourcelead.dashboard.service;

import java.util.List;

import com.bis.sourcelead.dashboard.dto.ReportConfigureDTO;
import com.bis.sourcelead.dashboard.entity.CustomReportConfiguration;
import com.bis.sourcelead.entity.OrgLocations;

public interface ReportsDashboardConfService {

	CustomReportConfiguration saveReportDashBoardConfiguration(ReportConfigureDTO reportConfDto, OrgLocations orgLocation, Long loginUserId);

	List<CustomReportConfiguration> getAllReportConfiByCustomDashBoardReportUUID(String customDashBoardConfUUID);
}
