package com.bis.sourcelead.service.impl.performance;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.performance.PerformanceAppraisalTemplateDao;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.service.performance.PerformanceAppraisalTemplateService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class PerformanceAppraisalTemplateServiceImpl implements PerformanceAppraisalTemplateService{
	
	@Autowired
	PerformanceAppraisalTemplateDao performanceAppraisalTemplateDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public PerformanceAppraisalTemplate savePerformanceAppraisalTemplate(
			PerformanceAppraisalTemplate performanceAppraisalTemplate) throws Exception {
		return performanceAppraisalTemplateDao.savePerformanceAppraisalTemplate(performanceAppraisalTemplate);
	}

	@Override
	public PerformanceAppraisalTemplate storePerformanceAppraisalTemplate(
			PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO, Long orgLocationId, Long userId, String dateFormat)
			throws Exception {
		
		PerformanceAppraisalTemplate template = new PerformanceAppraisalTemplate();
		template.setName(performanceAppraisalTemplateDTO.getName());
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalStartDate())) {
			template.setAppraisalStartDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalStartDate()));
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalEndDate())) {
			template.setAppraisalEndDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalEndDate()));
		}
		
		return null;
	}

}
