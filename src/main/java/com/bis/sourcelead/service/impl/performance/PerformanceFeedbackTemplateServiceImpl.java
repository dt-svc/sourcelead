package com.bis.sourcelead.service.impl.performance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.performance.PerformanceFeedbackTemplateDao;
import com.bis.sourcelead.entity.performance.PerformanceFeedbackTemplate;
import com.bis.sourcelead.service.performance.PerformanceFeedbackTemplateService;

@Service
public class PerformanceFeedbackTemplateServiceImpl implements PerformanceFeedbackTemplateService{

	@Autowired
	PerformanceFeedbackTemplateDao performanceFeedbackTemplateDao;
	
	@Override
	public PerformanceFeedbackTemplate savePerformanceFeedbackTemplate(
			PerformanceFeedbackTemplate performanceFeedbackTemplate) throws Exception {
		return performanceFeedbackTemplateDao.savePerformanceFeedbackTemplate(performanceFeedbackTemplate);
	}

	
}
