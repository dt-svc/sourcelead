package com.bis.sourcelead.dashboard.service;

import com.bis.sourcelead.dashboard.dto.ModuleConfigureDTO;
import com.bis.sourcelead.dashboard.entity.CustomModuleConfiguration;
import com.bis.sourcelead.entity.OrgLocations;

public interface ModuleDashboardConfService {

	ModuleConfigureDTO saveModuleConfiguration(ModuleConfigureDTO moduleConfDto, OrgLocations orgLocation, Long loginUserId);

	CustomModuleConfiguration getSubModuleById(Long subModuleId);
}
