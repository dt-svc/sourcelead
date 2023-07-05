package com.bis.sourcelead.reports.dao;

import java.util.List;

import com.bis.sourcelead.entity.EmailScenarioConfiguration;

public interface CustomEmailConfigurationDao {

	public List<EmailScenarioConfiguration> getCustomEmailConfigurationByLocation(Long locationId);
}
