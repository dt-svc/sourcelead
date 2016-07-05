package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.Roles;

public interface RolesService {
	
	public Roles getRolesByRoleName(String roleName) throws Exception;

}
