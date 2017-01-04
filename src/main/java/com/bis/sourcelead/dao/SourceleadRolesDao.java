package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadRoles;

public interface SourceleadRolesDao {
	
	List<SourceleadRoles> getSourceleadRoles();
	
	boolean roleHaveActionAccessOrNot(String actionId,String roleName);

}
