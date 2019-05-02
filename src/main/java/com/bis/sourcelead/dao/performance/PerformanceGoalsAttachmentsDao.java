package com.bis.sourcelead.dao.performance;

import com.bis.sourcelead.entity.performance.PerformanceGoalsAttachments;

public interface PerformanceGoalsAttachmentsDao {
	
	PerformanceGoalsAttachments getPerformanceGoalsAttachmentsByUUID(String uuid) throws Exception;
	
	PerformanceGoalsAttachments savePerformanceGoalsAttachments(PerformanceGoalsAttachments performanceGoalsAttachments) throws Exception;

}
