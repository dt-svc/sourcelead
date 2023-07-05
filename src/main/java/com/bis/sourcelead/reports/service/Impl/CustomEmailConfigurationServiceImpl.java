package com.bis.sourcelead.reports.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.reports.dao.CustomEmailConfigurationDao;
import com.bis.sourcelead.reports.service.CustomEmailConfigurationService;

@Service
public class CustomEmailConfigurationServiceImpl implements CustomEmailConfigurationService{
	
	@Autowired
	CustomEmailConfigurationDao customEmailConfigurationDao;

	@Override
	public List<EmailScenarioConfiguration> getCustomEmailConfigurationByLocation(Long locationId) {
		return customEmailConfigurationDao.getCustomEmailConfigurationByLocation(locationId);
	}

}
