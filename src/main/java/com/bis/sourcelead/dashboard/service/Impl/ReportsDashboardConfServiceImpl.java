package com.bis.sourcelead.dashboard.service.Impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dashboard.dao.CustomDashBoardConfDao;
import com.bis.sourcelead.dashboard.dao.ReportsDashboardConfDao;
import com.bis.sourcelead.dashboard.dto.ReportConfigureDTO;
import com.bis.sourcelead.dashboard.entity.CustomDashBoardConfiguration;
import com.bis.sourcelead.dashboard.entity.CustomReportConfiguration;
import com.bis.sourcelead.dashboard.service.ReportsDashboardConfService;
import com.bis.sourcelead.entity.OrgLocations;

@Service
public class ReportsDashboardConfServiceImpl implements ReportsDashboardConfService{

	@Autowired
	ReportsDashboardConfDao reportsDashboardConfDao;
	
	@Autowired
	CustomDashBoardConfDao customDashBoardConfDao;
	
	@Override
	public CustomReportConfiguration saveReportDashBoardConfiguration(ReportConfigureDTO reportConfDto, OrgLocations orgLocation, Long loginUserId)
	{
		CustomReportConfiguration savedCustomReportConf = null;
		try {
			 CustomDashBoardConfiguration dashBoardConf = customDashBoardConfDao.getCustomDashboardConfByUUID(reportConfDto.getCustomDashBoardConfUUID(), orgLocation.getId());
			 boolean isExistedConfRemoved = reportsDashboardConfDao.deleteReportConfByDashBoardId(dashBoardConf.getId());
			 String[] commaSeparatedArr = reportConfDto.getReportTypeArray().split("\\s*,\\s*");
			 List<String> subReports = Arrays.stream(commaSeparatedArr).collect(Collectors.toList());
			 for(String reportName : subReports) {
				 CustomReportConfiguration customReportsConf = new CustomReportConfiguration();
				 customReportsConf.setDashBoardConfId(dashBoardConf);
				 customReportsConf.setReportName(reportName);
				 customReportsConf.setStatus(1);
				 customReportsConf.setCreatedDate(new Date());
				 customReportsConf.setCreatedBy(loginUserId);
				 customReportsConf.setUpdatedDate(new Date());
				 customReportsConf.setUpdatedBy(loginUserId);
				 savedCustomReportConf =  reportsDashboardConfDao.saveReportsConfiguration(customReportsConf);
			 }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return savedCustomReportConf;
	}

	@Override
	public List<CustomReportConfiguration> getAllReportConfiByCustomDashBoardReportUUID(String customDashBoardConfUUID)
	{
		return reportsDashboardConfDao.getAllReportConfiByCustomDashBoardReportUUID(customDashBoardConfUUID);
	}
}
