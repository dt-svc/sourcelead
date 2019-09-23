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

	@Override
	public boolean roleHaveActionAccessOrNot(String actionId,String roleName) {
		
		return sourceleadRolesDao.roleHaveActionAccessOrNot(actionId,roleName);
	}

	@Override
	public SourceleadRoles getSourceleadRolesByRoleName(String roleName) throws Exception {
		return sourceleadRolesDao.getSourceleadRolesByRoleName(roleName);
	}

	@Override
	public List<SourceleadRoles> getGuestOrgSourceleadRoles() throws Exception {
		return sourceleadRolesDao.getGuestOrgSourceleadRoles();
	}

	@Override
	public SourceleadRoles getSourceleadRolesByUUID(String uuid) throws Exception {
		return sourceleadRolesDao.getSourceleadRolesByUUID(uuid);
	}

	@Override
	public SourceleadRoles saveSourceleadRoles(SourceleadRoles sourceleadRoles) throws Exception {
		return sourceleadRolesDao.saveSourceleadRoles(sourceleadRoles);
	}

}
