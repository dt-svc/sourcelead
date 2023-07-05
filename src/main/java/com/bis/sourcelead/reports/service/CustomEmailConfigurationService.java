package com.bis.sourcelead.reports.service;

import java.util.List;

import com.bis.sourcelead.entity.EmailScenarioConfiguration;

public interface CustomEmailConfigurationService {
	
	List<EmailScenarioConfiguration>  getCustomEmailConfigurationByLocation(Long locationId);

}
