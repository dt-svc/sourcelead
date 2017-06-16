package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Modules;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;

public interface ModulesService {

	List<Long> getModulesIdByModuleName(String modules) throws Exception;
	
	List<Modules> getSourceLeadModules() throws Exception;
	
	List<Modules> getRequirementSubModules() throws Exception;
	
	List<Long> getModulesIds() throws Exception;
	
	List<Modules> getRequirementSubModulesByParentId(Long moduleId) throws Exception;
	
	List<Long> getOrgRegisteredModulesByOrgLocationId(OrgLocations orgLocations) throws Exception;
}
