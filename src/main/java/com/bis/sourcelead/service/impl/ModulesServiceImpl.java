package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ModulesDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.service.ModulesService;

@Service
public class ModulesServiceImpl implements ModulesService{
	
private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private ModulesDao modulesDao;
	

	@Override
	public List<Long> getModulesIdByModuleName(String modules) throws Exception {
		return modulesDao.getModulesIdByModuleName(modules);
	}


	@Override
	public List<Long> getOrgRegisteredModules(Org org) throws Exception {
		return modulesDao.getOrgRegisteredModules(org);
	}

}
