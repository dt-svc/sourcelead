package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RolesDao;
import com.bis.sourcelead.dto.RoleDTO;
import com.bis.sourcelead.entity.Org;
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
	public List<Roles> getMoudleRoles(Long orgId,Long userRoleId) throws Exception {
		return rolesDao.getMoudleRoles(orgId,userRoleId);
	}


	@Override
	public Roles getRolesById(Long id) throws Exception {
		return rolesDao.getRolesById(id);
	}


	@Override
	public List<Roles> getSourceleadRoles(Long orgId) throws Exception {
		return rolesDao.getSourceleadRoles(orgId);
	}


	@Override
	public Roles storeRoles(Roles roles) {
		return rolesDao.storeRoles(roles);
	}


	@Override
	public Roles getRolesByRoleNameAndOrgLocationId(String roleName, Long orgLocationId) throws Exception {
		return rolesDao.getRolesByRoleNameAndOrgLocationId(roleName, orgLocationId);
	}


	@Override
	public List<Roles> getUserRolesListByModuleAndLoginUserRoleHierarchy(Org org, Long orgLocationId,Long hierarchyId) {
		return rolesDao.getUserRolesListByModuleAndLoginUserRoleHierarchy(org, orgLocationId,hierarchyId);
	}


	@Override
	public List<Roles> getClientOrVendorRolesList(Long orgLocationId,String moduleType) {
		return rolesDao.getClientOrVendorRolesList(orgLocationId,moduleType);
	}


	@Override
	public List<Roles> getOrgEnrolledRolesByOrgLocationId(Org org, Long orgLocationId) throws Exception {
		return rolesDao.getOrgEnrolledRolesByOrgLocationId(org, orgLocationId);
	}


	@Override
	public Roles getRolesByIdAndOrgLocationId(Long id, Long orgLocationId) throws Exception {
		return rolesDao.getRolesByIdAndOrgLocationId(id, orgLocationId);
	}


	@Override
	public Roles getRolesByRoleDisplayNameAndOrgLocationId(String roleName, Long orgLocationId) throws Exception {
		return rolesDao.getRolesByRoleDisplayNameAndOrgLocationId(roleName, orgLocationId);
	}
	
	@Override
	public List<Roles> getTabAccessRolesListByOrgLocationIdAndTabId(Long orgLocationsId, Long tabId) throws Exception {
		return rolesDao.getTabAccessRolesListByOrgLocationIdAndTabId(orgLocationsId, tabId);
	}


	@Override
	public List<Roles> getRolesListExpcetCurrentUserRole(Long roleId, Long orgLocationId) throws Exception {
		return rolesDao.getRolesListExpcetCurrentUserRole(roleId, orgLocationId);
	}


	@Override
	public List<Roles> getUserRolesListByRoleHierarchyIdAndOrgLocationId(Long orgLocationId, Long hierarchyId,String orgCode)
			throws Exception {
		return rolesDao.getUserRolesListByRoleHierarchyIdAndOrgLocationId(orgLocationId, hierarchyId,orgCode);
	}


	@Override
	public Roles getRolesByRoleNameAndOrgId(String roleName, Long orgId) throws Exception {
		return rolesDao.getRolesByRoleNameAndOrgId(roleName, orgId);
	}


	@Override
	public List<Roles> getAllRolesListByOrgLocationIdList(List<Long> orgLocationIdsList) throws Exception {
		return rolesDao.getAllRolesListByOrgLocationIdList(orgLocationIdsList);
	}


	@Override
	public Roles getRolesByUUIDAndOrgLocationId(String uuid, Long orgLocationId) throws Exception {
		return rolesDao.getRolesByUUIDAndOrgLocationId(uuid, orgLocationId);
	}


	@Override
	public Roles getRolesByUUID(String uuid) throws Exception {
		return rolesDao.getRolesByUUID(uuid);
	}


	@Override
	public List<String> getRolesListByOrgLocationId(Long orgLocationId) throws Exception {
		return rolesDao.getRolesListByOrgLocationId(orgLocationId);
	}


	@Override
	public List<Roles> getRolesListUsingMultipleIds(String[] roleIds) throws Exception {
		
		return rolesDao.getRolesListUsingMultipleIds(roleIds);
	}
	@Override
	public List<RoleDTO> getRolesDTOListByOrgLocationId(Long orgLocationId) throws Exception {
		return rolesDao.getRolesDTOListByOrgLocationId(orgLocationId);

	}
	

}
