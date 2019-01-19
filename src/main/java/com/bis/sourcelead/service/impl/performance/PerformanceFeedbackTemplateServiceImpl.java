package com.bis.sourcelead.service.impl.performance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.performance.PerformanceFeedbackTemplateDao;
import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
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

	@Override
	public PerformanceFeedbackTemplate getPerformanceFeedbackTemplateById(Long id) throws Exception {
		return performanceFeedbackTemplateDao.getPerformanceFeedbackTemplateById(id);
	}

	@Override
	public List<PerformanceFeedbackTemplate> getPerformanceFeedbackTemplateList(Long orgLocationId) throws Exception {
		return performanceFeedbackTemplateDao.getPerformanceFeedbackTemplateList(orgLocationId);
	}

	@Override
	public List<PerformanceFeedbackTemplateDTO> getPerformanceFeedbackTemplateDTOList(Long orgLocationId)
			throws Exception {
		return performanceFeedbackTemplateDao.getPerformanceFeedbackTemplateDTOList(orgLocationId);
	}

	
}
