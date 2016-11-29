package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RoleHierarchy;

public interface RoleHierarchyService {

	RoleHierarchy saveRoleHierarchy(RoleHierarchy roleHierarchy) throws Exception;
	
	List<RoleHierarchy> getRoleHierarchyByOrgLocationId(Long orgLocationId) throws Exception;
	
	RoleHierarchy getRoleHierarchyByHierarchyLevelAndOrgLocationId(Integer hierarchyLevel,Long orgLocationId) throws Exception;
	
	RoleHierarchy getRoleHierarchyById(Long id) throws Exception;
}
