package com.bis.sourcelead.service.impl;

import java.util.List;

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
	public List<Roles> getRoles(Long orgLocationId) throws Exception {
		return rolesDao.getRoles(orgLocationId);
	}


	@Override
	public Roles getRolesByDisplayName(String displayName) throws Exception {
		
		return rolesDao.getRolesByDisplayName(displayName);
	}


	@Override
	public List<Roles> getMoudleRoles(Long orgId) throws Exception {
		return rolesDao.getMoudleRoles(orgId);
	}


	@Override
	public Roles getRolesById(Long id) throws Exception {
		return rolesDao.getRolesById(id);
	}

}
