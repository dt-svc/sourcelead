package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.RoleDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.UserRole;

public interface RolesDao {
	
	public Roles getRolesByRoleName(String roleName) throws Exception;
	
	Roles getRolesById(Long id) throws Exception;
	
	List<Roles> getRoles(Long orgLocationId) throws Exception;
	
	public Roles getRolesByDisplayName(String displayName) throws Exception;
	
	List<Roles> getMoudleRoles(Long orgId,Long userRoleId) throws Exception;
	
	List<Roles> getSourceleadRoles(Long orgId) throws Exception;
	
	public Roles storeRoles(Roles roles);
	
	public Roles getRolesByRoleNameAndOrgLocationId(String roleName,Long orgLocationId) throws Exception;
	
	List<Roles> getUserRolesListByModuleAndLoginUserRoleHierarchy(Org org, Long orgLocationId,Long hierarchyId);
	
	List<Roles> getClientOrVendorRolesList(Long orgLocationId,String moduleType);
	
	List<Roles> getOrgEnrolledRolesByOrgLocationId(Org org,Long orgLocationId) throws Exception;
	
	Roles getRolesByIdAndOrgLocationId(Long id,Long orgLocationId) throws Exception;
	
	public Roles getRolesByRoleDisplayNameAndOrgLocationId(String roleName,Long orgLocationId) throws Exception;
	
	public List<Roles> getTabAccessRolesListByOrgLocationIdAndTabId(Long orgLocationsId,Long tabId) throws Exception;
	
	List<Roles> getRolesListExpcetCurrentUserRole(Long roleId,Long orgLocationId) throws Exception;
	
	List<Roles> getRolesListUsingMultipleIds(String[] roleIds) throws Exception;
	
	List<Roles> getUserRolesListByRoleHierarchyIdAndOrgLocationId(Long orgLocationId,Long hierarchyId,String orgCode) throws Exception;
	
	Roles getRolesByRoleNameAndOrgId(String roleName,Long orgId) throws Exception;
	
	List<Roles> getAllRolesListByOrgLocationIdList(List<Long> orgLocationIdsList) throws Exception;
	
	Roles getRolesByUUIDAndOrgLocationId(String uuid,Long orgLocationId) throws Exception;
	
	Roles getRolesByUUID(String uuid) throws Exception;
	
	List<String> getRolesListByOrgLocationId(Long orgLocationId) throws Exception;

	List<RoleDTO> getRolesDTOListByOrgLocationId(Long orgLocationId) throws Exception;
	
	List<String> getRolesNamesListByRolesId(List<String> roleIds) throws Exception;
}
