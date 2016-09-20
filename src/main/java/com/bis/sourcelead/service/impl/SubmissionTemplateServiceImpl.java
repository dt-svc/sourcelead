package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SubmissionTemplateDao;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.service.SubmissionTemplateService;

@Service
public class SubmissionTemplateServiceImpl implements SubmissionTemplateService {
	
	@Autowired
	private SubmissionTemplateDao submissionTemplateDao;

	@Override
	public SubmissionTemplate addSubmissionTemplate(SubmissionTemplate submissionTemplate) throws Exception {
		return submissionTemplateDao.addSubmissionTemplate(submissionTemplate);
	}

	@Override
	public List<SubmissionTemplate> getSubmissionTemplateByClientLocationIdAndOrgLocationId(Long clientLocationId, Long orgLocationId)
			throws Exception {
		return submissionTemplateDao.getSubmissionTemplateByClientLocationIdAndOrgLocationId(clientLocationId, orgLocationId);
	}

	@Override
	public SubmissionTemplate getSubmissionTemplateById(Long Id) throws Exception {
		return submissionTemplateDao.getSubmissionTemplateById(Id);
	}

	@Override
	public SubmissionTemplate getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId(Long clientLocationId,
			Long orgLocationId) throws Exception {
		return submissionTemplateDao.getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId(clientLocationId, orgLocationId);
	}

	@Override
	public SubmissionTemplate getSubmissionTemplateByTemplateName(Long clientLocationId, Long orgLocationId,
			String templateName) throws Exception {
		return submissionTemplateDao.getSubmissionTemplateByTemplateName(clientLocationId, orgLocationId, templateName);
	}

}
