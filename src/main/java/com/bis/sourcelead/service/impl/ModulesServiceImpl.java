package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ModulesDao;
import com.bis.sourcelead.entity.Modules;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.ModulesService;

@Service
public class ModulesServiceImpl implements ModulesService{
	
	
	@Autowired
	private ModulesDao modulesDao;
	

	@Override
	public List<Long> getModulesIdByModuleName(String modules) throws Exception {
		return modulesDao.getModulesIdByModuleName(modules);
	}



	@Override
	public List<Modules> getSourceLeadModules() throws Exception {
		return modulesDao.getSourceLeadModules();
	}


	@Override
	public List<Modules> getRequirementSubModules() throws Exception {
		return modulesDao.getRequirementSubModules();
	}


	@Override
	public List<Long> getModulesIds() throws Exception {
		return modulesDao.getModulesIds();
	}


	@Override
	public List<Modules> getRequirementSubModulesByParentId(Long moduleId) throws Exception {
		return modulesDao.getRequirementSubModulesByParentId(moduleId);
	}


	@Override
	public List<Long> getOrgRegisteredModulesByOrgLocationId(OrgLocations orgLocations) throws Exception
	{
		return modulesDao.getOrgRegisteredModulesByOrgLocationId(orgLocations);
	}

}
