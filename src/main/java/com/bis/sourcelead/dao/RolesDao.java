package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.Roles;

public interface RolesDao {
	
	public Roles getRolesByRoleName(String roleName) throws Exception;

}
