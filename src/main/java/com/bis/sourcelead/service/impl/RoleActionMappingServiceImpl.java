package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RoleActionMappingDao;
import com.bis.sourcelead.entity.RoleActionMapping;
import com.bis.sourcelead.service.RoleActionMappingService;

@Service
public class RoleActionMappingServiceImpl implements RoleActionMappingService{
	
	@Autowired
	RoleActionMappingDao roleActionMappingDao;

	@Override
	public List<RoleActionMapping> getRoleActionMappingForRegistrationByOrgId(Long orgId) {
		return roleActionMappingDao.getRoleActionMappingForRegistrationByOrgId(orgId);
	}

	@Override
	public RoleActionMapping saveRoleActionMapping(RoleActionMapping roleActionMapping) {
		return roleActionMappingDao.saveRoleActionMapping(roleActionMapping);
	}

}
