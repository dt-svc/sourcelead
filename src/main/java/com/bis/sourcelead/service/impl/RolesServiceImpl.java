package com.bis.sourcelead.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RolesDao;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.service.RolesService;

@Service
public class RolesServiceImpl implements RolesService{

	@Autowired
	RolesDao rolesDao;
	
	
	@Override
	public Roles getRolesByRoleName(String roleName) throws Exception {
		return rolesDao.getRolesByRoleName(roleName);
	}


	@Override
	public Collection<Roles> getRoles(Long orgLocationId) throws Exception {
		return rolesDao.getRoles(orgLocationId);
	}


	@Override
	public Roles getRolesByDisplayName(String displayName) throws Exception {
		
		return rolesDao.getRolesByDisplayName(displayName);
	}

}
