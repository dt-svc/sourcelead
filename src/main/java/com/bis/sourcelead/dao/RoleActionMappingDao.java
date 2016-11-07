package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RoleActionMapping;

public interface RoleActionMappingDao {

	List<RoleActionMapping> getRoleActionMappingForRegistrationByOrgId(Long orgId);
	
	public RoleActionMapping saveRoleActionMapping(RoleActionMapping roleActionMapping);
}
