package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RoleHierarchy;

public interface RoleHierarchyDao {

	
	RoleHierarchy saveRoleHierarchy(RoleHierarchy roleHierarchy) throws Exception;
	
	List<RoleHierarchy> getRoleHierarchyByOrgLocationId(Long orgLocationId) throws Exception;
	
	RoleHierarchy getRoleHierarchyByHierarchyLevelAndOrgLocationId(Integer hierarchyLevel,Long orgLocationId) throws Exception;
	
	RoleHierarchy getRoleHierarchyById(Long id) throws Exception;
}
