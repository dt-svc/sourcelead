package com.bis.sourcelead.dashboard.dao;

import com.bis.sourcelead.dashboard.entity.CustomModuleConfiguration;

public interface ModuleDashboardConfDao {

	CustomModuleConfiguration saveModuleConfiguration(CustomModuleConfiguration customModConf);

	CustomModuleConfiguration getSubModuleById(Long subModuleId);
}
