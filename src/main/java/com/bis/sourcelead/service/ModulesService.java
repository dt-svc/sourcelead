package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Org;

public interface ModulesService {

	List<Long> getModulesIdByModuleName(String modules) throws Exception;
	
	List<Long> getOrgRegisteredModules(Org org) throws Exception;
}
