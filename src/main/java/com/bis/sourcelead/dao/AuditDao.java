package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.AuditRecord;

public interface AuditDao {
	public void saveAuditReport(AuditRecord audit);
}
