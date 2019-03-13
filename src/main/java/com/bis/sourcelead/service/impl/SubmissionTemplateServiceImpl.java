package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SubmissionTemplateDao;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class SubmissionTemplateServiceImpl implements SubmissionTemplateService {
	
	@Autowired
	private SubmissionTemplateDao submissionTemplateDao;
	
	@Autowired
	private CommonUtil commonUtil;

	@Override
	public SubmissionTemplate addSubmissionTemplate(SubmissionTemplate submissionTemplate) throws Exception {
		submissionTemplate.setUpdatedDate(commonUtil.currentDate());
		return submissionTemplateDao.addSubmissionTemplate(submissionTemplate);
	}

	@Override
	public List<SubmissionTemplate> getSubmissionTemplateByClientLocationIdAndOrgLocationIdAndTemplateType(Long clientLocationId, Long orgLocationId,String templateType)
			throws Exception {
		return submissionTemplateDao.getSubmissionTemplateByClientLocationIdAndOrgLocationIdAndTemplateType(clientLocationId, orgLocationId,templateType);
	}

	@Override
	public SubmissionTemplate getSubmissionTemplateById(Long Id) throws Exception {
		return submissionTemplateDao.getSubmissionTemplateById(Id);
	}
	
	@Override
	public SubmissionTemplate getDefaultSubmissionTemplateByOrgLocationId(Long orgLocationId) throws Exception {
		return submissionTemplateDao.getDefaultSubmissionTemplateByOrgLocationId(orgLocationId);
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

	@Override
	public SubmissionTemplate getSubmissionTemplateByLabelName(Long templateId,String labelName) throws Exception {
		return submissionTemplateDao.getSubmissionTemplateByLabelName(templateId, labelName);
	}

	@Override
	public List<SubmissionTemplate> getOrgSubmissionTemplatesByOrgLocationIdAndTemplateType(Long orgLocationId,String templateType) throws Exception {
		return submissionTemplateDao.getOrgSubmissionTemplatesByOrgLocationIdAndTemplateType(orgLocationId,templateType);
	}

	@Override
	public SubmissionTemplate getSubmissionTemplateByTemplateNameByOrg(Long orgLocationId, String templateName) throws Exception {
		return submissionTemplateDao.getSubmissionTemplateByTemplateNameByOrg(orgLocationId, templateName);
		}
	
	@Override
	public List<SubmissionTemplate> getInterviewFeedbackTemplatesByOrgLocationId(Long orgLocationId) throws Exception {
		return submissionTemplateDao.getInterviewFeedbackTemplatesByOrgLocationId(orgLocationId);
	}

	@Override
	public SubmissionTemplate getSubmissionTemplateByUUID(String uuid) throws Exception {
		return submissionTemplateDao.getSubmissionTemplateByUUID(uuid);
	}
	
}
