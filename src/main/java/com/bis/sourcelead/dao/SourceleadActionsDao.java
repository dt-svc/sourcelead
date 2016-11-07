package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SourceleadActions;

public interface SourceleadActionsDao {
	
	List<SourceleadActions> getLoginUserRoleActions(List<Long> modulesId,Long roleId,Long orgId) throws Exception;

}
