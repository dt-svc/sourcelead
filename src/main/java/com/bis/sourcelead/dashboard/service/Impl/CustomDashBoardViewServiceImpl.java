package com.bis.sourcelead.dashboard.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

@Service
public class CustomDashBoardViewServiceImpl implements CustomDashBoardViewService{

	
	@Autowired
	CustomDashBoardViewDao customDashBoardViewDao;
	
	@Autowired
	LeaveDashBoardDataUtil leaveDashBoardDataUtil;
	  
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
				String jsonData = "";
				if("LEAVE_MANAGEMENT".equalsIgnoreCase(dtoObj.getSubTabName())) {
				  jsonData = leaveDashBoardDataUtil.getLeaveDashBoardData(commonDataDto);
				}else if("VENDORS_BY_CONTACTS".equalsIgnoreCase(dtoObj.getSubTabName())) {
					
				}
				
				
				dtoObj.setJsonStringData(jsonData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return configureDashBoardView;
	}


	
}
