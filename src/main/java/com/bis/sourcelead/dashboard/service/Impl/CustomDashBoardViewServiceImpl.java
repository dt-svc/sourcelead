package com.bis.sourcelead.dashboard.service.Impl;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dashboard.dao.CustomDashBoardViewDao;
import com.bis.sourcelead.dashboard.dto.CommonDataDto;
import com.bis.sourcelead.dashboard.dto.ConfigureDashBoardViewDTO;
import com.bis.sourcelead.dashboard.entity.CustomReportConfiguration;
import com.bis.sourcelead.dashboard.service.CustomDashBoardViewService;
import com.bis.sourcelead.dashboard.util.LeaveDashBoardDataUtil;
import com.bis.sourcelead.dto.LeaveDTO;
import javax.servlet.http.HttpServletRequest;


@Service
public class CustomDashBoardViewServiceImpl implements CustomDashBoardViewService{

	
	@Autowired
	CustomDashBoardViewDao customDashBoardViewDao;
	
	@Autowired
	LeaveDashBoardDataUtil leaveDashBoardDataUtil;
	
	@Autowired
	HttpServletRequest httpServletRequest;
	  
	@Override
	public List<ConfigureDashBoardViewDTO> getConfiguredDataByDashBoardId(Long dashBoardId)
	{
		List<ConfigureDashBoardViewDTO> viewData = new ArrayList<>();
		try {
			List<ConfigureDashBoardViewDTO> configuredModList =  customDashBoardViewDao.getConfiguredModuleByDashBoardId(dashBoardId);
			List<CustomReportConfiguration> configuredReportList = customDashBoardViewDao.getConfiguredReportByDashBoardId(dashBoardId);
			if(configuredModList != null && configuredModList.size() >0) {
				for(ConfigureDashBoardViewDTO module : configuredModList) {
					ConfigureDashBoardViewDTO dto = new ConfigureDashBoardViewDTO();
					dto.setSubTabName(module.getSubTabName());
					dto.setDisplayTabName(module.getDisplayTabName());
					viewData.add(dto);
				}	
			}
			if(configuredReportList != null && configuredReportList.size() >0) {
				for(CustomReportConfiguration report : configuredReportList) {
					ConfigureDashBoardViewDTO dto = new ConfigureDashBoardViewDTO();
					dto.setSubTabName(report.getReportName());
					dto.setDisplayTabName(getDisplayTabName(report.getReportName()));
					viewData.add(dto);
				}	
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
				return viewData;
	}

	
	/**
	 * Converting capital letter report name to Diaplay name
	 * @param reportName
	 * @return
	 */
	private String getDisplayTabName(String reportName)
	{
		String [] arr = reportName.toLowerCase().split("_");
		StringBuilder sb = new StringBuilder();
		for(String str1 : arr) {
			sb.append(str1.substring(0, 1).toUpperCase() + str1.substring(1)).append(" ");
		}
		return sb.toString();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigureDashBoardViewDTO> getDashBoardData(List<ConfigureDashBoardViewDTO> configureDashBoardView,
			CommonDataDto commonDataDto) {
		try {
			for (ConfigureDashBoardViewDTO dtoObj : configureDashBoardView) {
				System.out.println(dtoObj.getSubTabName());
				String jsonData = "";
				System.out.println(dtoObj.getSubTabName());
				if("LEAVE_MANAGEMENT".equalsIgnoreCase(dtoObj.getSubTabName())) {
				  jsonData = leaveDashBoardDataUtil.getLeaveDashBoardData(commonDataDto);
				}else if("TIMESHEETS".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = leaveDashBoardDataUtil.getTimesheetsDashboardData(commonDataDto);
				}else if("VENDORS_BY_CONTACTS".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = leaveDashBoardDataUtil.getVendorByitsContacts(commonDataDto);
				}else if("PROJECT_REQUESTS".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = leaveDashBoardDataUtil.getProjectRequests(commonDataDto);
				}else if("ASSIGNED_PROJECT_TASKS".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = leaveDashBoardDataUtil.getAssignedProjectTasks(commonDataDto);
				}else if("CURRENT_ONBOARD".equalsIgnoreCase(dtoObj.getSubTabName())) {
				jsonData =	leaveDashBoardDataUtil.getCandidateOnBoarding(commonDataDto);
				}else if("CURRENT_OFFBOARD".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData =	leaveDashBoardDataUtil.getCandidateOFFBoarding(commonDataDto);
				}else if("CLIENTS_BY_CONTACTS".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData =	leaveDashBoardDataUtil.getCliendbycontacts(commonDataDto);
				}else if("CANDIDATES_ON_ONBOARDING".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData =	leaveDashBoardDataUtil.getCandidatesOnBoarding(commonDataDto);
				}else if("CANDIDATE_READY_FOR_INVOICE".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData =	leaveDashBoardDataUtil.getCandidateReadyForInvoice(commonDataDto);
				}else if("SUBMIT_TO_CLIENT".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = leaveDashBoardDataUtil.getSubmitToClientCandidatesDashBoardData(commonDataDto);
				}
				else if("INTERVIEW".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = leaveDashBoardDataUtil.getInterviewDashBoardData(commonDataDto);
				}
				else if("EXPENSES".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = leaveDashBoardDataUtil.getExpensesDashBoardData(commonDataDto);
				}
				else if("JOB_FAIR".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = leaveDashBoardDataUtil.getJobFairDashBoardData(commonDataDto);
				}
				else if("APPLICANT_PERMISSION".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = leaveDashBoardDataUtil.getApplicantPremissionDashBoardData(commonDataDto);
				}
				else if("ASSIGNED_JOBS".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = leaveDashBoardDataUtil.getAssignedJobsDashBoardData(commonDataDto);
				}
				else if("JOBS_PIPELINE".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = leaveDashBoardDataUtil.getJobsPipeLineDashBoardData(commonDataDto);
				}else if("PURCHASE_ORDER_LIST".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = customDashBoardViewDao.getMarketplacePurchaseOrderDashBoard(commonDataDto);
				}else if("SALES_ORDER_LIST".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = customDashBoardViewDao.getMarketplaceSalesOrderDashBoard(commonDataDto);
				}else if("RECENTLY_ADDED".equalsIgnoreCase(dtoObj.getSubTabName())) {
					jsonData = customDashBoardViewDao.getRecentlyAddedCandidates(commonDataDto);
				}
				dtoObj.setJsonStringData(jsonData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return configureDashBoardView;
	}


	
}
