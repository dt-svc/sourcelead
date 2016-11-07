package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RoleActionMapping;

public interface RoleActionMappingService {

	List<RoleActionMapping> getRoleActionMappingForRegistrationByOrgId(Long orgId);
	
	public RoleActionMapping saveRoleActionMapping(RoleActionMapping roleActionMapping);
}
