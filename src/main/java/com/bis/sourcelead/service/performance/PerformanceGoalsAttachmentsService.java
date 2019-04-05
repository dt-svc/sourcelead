package com.bis.sourcelead.service.performance;

import com.bis.sourcelead.entity.performance.PerformanceGoalsAttachments;

public interface PerformanceGoalsAttachmentsService {

	PerformanceGoalsAttachments getPerformanceGoalsAttachmentsByUUID(String uuid) throws Exception;
	
	PerformanceGoalsAttachments savePerformanceGoalsAttachments(PerformanceGoalsAttachments performanceGoalsAttachments) throws Exception;
}
