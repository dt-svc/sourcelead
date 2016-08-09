package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ActionRolesDao;
import com.bis.sourcelead.entity.ActionRoles;
import com.bis.sourcelead.service.ActionRolesService;

@Service
public class ActionRolesServiceImpl implements ActionRolesService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private ActionRolesDao actionRolesDao;
	
	@Override
	public ActionRoles addActionRoles(ActionRoles actionRoles) throws Exception {
		logger.debug("In ActionRolesService class...");
		return actionRolesDao.addActionRoles(actionRoles);
	}

	@Override
	public List<ActionRoles> getSourceLeadActionRolesListByOrgId(Long orgId,List<Long> modulesId) throws Exception {
		logger.debug("In ActionRolesService class...");
		return actionRolesDao.getSourceLeadActionRolesListByOrgId(orgId,modulesId);
	}

	@Override
	public List<ActionRoles> getOrgActionRolesByRoleAndOrgLocationId(Long roleId, Long orgLocationId)
			throws Exception {
		logger.debug("In ActionRolesService class...");
		return actionRolesDao.getOrgActionRolesByRoleAndOrgLocationId(roleId, orgLocationId);
	}

}
