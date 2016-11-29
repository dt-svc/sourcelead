package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RoleHierarchyDao;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.service.RoleHierarchyService;

@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService{
	
	@Autowired
	RoleHierarchyDao roleHierarchyDao;

	@Override
	public RoleHierarchy saveRoleHierarchy(RoleHierarchy roleHierarchy) throws Exception {
		return roleHierarchyDao.saveRoleHierarchy(roleHierarchy);
	}

	@Override
	public List<RoleHierarchy> getRoleHierarchyByOrgLocationId(Long orgLocationId) throws Exception {
		return roleHierarchyDao.getRoleHierarchyByOrgLocationId(orgLocationId);
	}

	@Override
	public RoleHierarchy getRoleHierarchyByHierarchyLevelAndOrgLocationId(Integer hierarchyLevel, Long orgLocationId)
			throws Exception {
		return roleHierarchyDao.getRoleHierarchyByHierarchyLevelAndOrgLocationId(hierarchyLevel, orgLocationId);
	}

	@Override
	public RoleHierarchy getRoleHierarchyById(Long id) throws Exception {
		return roleHierarchyDao.getRoleHierarchyById(id);
	}

}
