package com.bis.sourcelead.service.impl.performance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.performance.PerformanceGoalsAttachmentsDao;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAttachments;
import com.bis.sourcelead.service.performance.PerformanceGoalsAttachmentsService;

@Service
public class PerformanceGoalsAttachmentsServiceImpl implements PerformanceGoalsAttachmentsService{
	
	@Autowired
	PerformanceGoalsAttachmentsDao performanceGoalsAttachmentsDao;

	@Override
	public PerformanceGoalsAttachments getPerformanceGoalsAttachmentsByUUID(String uuid) throws Exception {
		return performanceGoalsAttachmentsDao.getPerformanceGoalsAttachmentsByUUID(uuid);
	}

	@Override
	public PerformanceGoalsAttachments savePerformanceGoalsAttachments(
			PerformanceGoalsAttachments performanceGoalsAttachments) throws Exception {
		return performanceGoalsAttachmentsDao.savePerformanceGoalsAttachments(performanceGoalsAttachments);
	}

	
	
}
