package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.User;

public interface AuditService {
	
	public void saveAuditReport(String tableName, String oldData, String newData, String actionPerformed, User user);

	public String getData(Object entityObject, List<Object> childClassList);
}
