package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Org;

public interface ModulesDao {

	List<Long> getModulesIdByModuleName(String modules) throws Exception;
	
	List<Long> getOrgRegisteredModules(Org org) throws Exception;
}
