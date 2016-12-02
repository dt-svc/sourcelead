package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SourceleadRolesDao;
import com.bis.sourcelead.entity.SourceleadRoles;
import com.bis.sourcelead.service.SourceleadRolesService;

@Service
public class SourceleadRolesServiceImpl implements SourceleadRolesService{
	
	@Autowired
	SourceleadRolesDao sourceleadRolesDao;

	@Override
	public List<SourceleadRoles> getSourceleadRoles() {
		return sourceleadRolesDao.getSourceleadRoles();
	}

}
