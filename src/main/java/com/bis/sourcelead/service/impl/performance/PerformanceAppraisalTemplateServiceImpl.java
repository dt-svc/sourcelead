package com.bis.sourcelead.service.impl.performance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.performance.PerformanceAppraisalTemplateDao;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.service.performance.PerformanceAppraisalTemplateService;

@Service
public class PerformanceAppraisalTemplateServiceImpl implements PerformanceAppraisalTemplateService{
	
	@Autowired
	PerformanceAppraisalTemplateDao performanceAppraisalTemplateDao;
	
	@Override
	public PerformanceAppraisalTemplate savePerformanceAppraisalTemplate(
			PerformanceAppraisalTemplate performanceAppraisalTemplate) throws Exception {
		return performanceAppraisalTemplateDao.savePerformanceAppraisalTemplate(performanceAppraisalTemplate);
	}

}
