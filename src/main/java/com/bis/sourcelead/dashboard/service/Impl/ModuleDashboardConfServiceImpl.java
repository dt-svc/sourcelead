package com.bis.sourcelead.dashboard.service.Impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dashboard.dao.CustomDashBoardConfDao;
import com.bis.sourcelead.dashboard.dao.ModuleDashboardConfDao;
import com.bis.sourcelead.dashboard.dto.ModuleConfigureDTO;
import com.bis.sourcelead.dashboard.entity.CustomDashBoardConfiguration;
import com.bis.sourcelead.dashboard.entity.CustomModuleConfiguration;
import com.bis.sourcelead.dashboard.service.ModuleDashboardConfService;
import com.bis.sourcelead.entity.OrgLocations;

@Service
public class ModuleDashboardConfServiceImpl implements ModuleDashboardConfService{
	
	private static final Logger logger = LoggerFactory.getLogger(ModuleDashboardConfServiceImpl.class);
	
	@Autowired
	private ModuleDashboardConfDao moduleDashboardConfDao;
	
	@Autowired
	private CustomDashBoardConfDao  customDashBoardConfDao;

	@Override
	public ModuleConfigureDTO saveModuleConfiguration(ModuleConfigureDTO moduleConfDto, OrgLocations orgLocation, Long loginUserId)
	{
		
		try {
			 CustomDashBoardConfiguration dashBoardConf = customDashBoardConfDao.getCustomDashboardConfByUUID(moduleConfDto.getCustomDashBoardConfUUID(), orgLocation.getId());
			 String[] commaSeparatedArr = moduleConfDto.getSubModuleIds().split("\\s*,\\s*");
			 List<String> subIds = Arrays.stream(commaSeparatedArr).collect(Collectors.toList());
			 for(String subId : subIds) {
				 CustomModuleConfiguration customModConf = new CustomModuleConfiguration();
				 customModConf.setDashBoardConfId(dashBoardConf);
				 customModConf.setSubTabId(Long.parseLong(subId));
				 customModConf.setStatus(1);
				 customModConf.setCreatedDate(new Date());
				 customModConf.setCreatedBy(loginUserId);
				 customModConf.setUpdatedDate(new Date());
				 customModConf.setUpdatedBy(loginUserId);
				 moduleDashboardConfDao.saveModuleConfiguration(customModConf);
			 }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return moduleConfDto;
	}

	@Override
	public CustomModuleConfiguration getSubModuleByIdAndConfDashBoardId(Long subModuleId,Long confDashBoardId)
	{
		return moduleDashboardConfDao.getSubModuleByIdAndConfDashBoardId(subModuleId,confDashBoardId);
	}

	
}
