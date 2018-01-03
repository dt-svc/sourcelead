package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OnBoardingTemplateDao;
import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OnBoardingTemplateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

import cucumber.deps.difflib.StringUtills;

@Service
public class OnBoardingTemplateServiceImpl implements OnBoardingTemplateService{
	
	@Autowired
	OnBoardingTemplateDao onBoardingTemplateDao;
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public List<OnBoardingTemplate> getOnBoardingTemplatesList(Long orgLocationId) throws Exception {
		return onBoardingTemplateDao.getOnBoardingTemplatesList(orgLocationId);
	}

	@Override
	public OnBoardingTemplate saveOnBoardingTemplate(OnBoardingTemplate onboardingTemplate) throws Exception {
		return onBoardingTemplateDao.saveOnBoardingTemplate(onboardingTemplate);
	}

	@Override
	public OnBoardingTemplate getOnBoardingTemplateById(Long templateId) throws Exception {
		return onBoardingTemplateDao.getOnBoardingTemplateById(templateId);
	}

	@Override
	public OnBoardingTemplate getOnBoardingTemplateByTemplateNameAndOrgLocationId(String templateName,
			Long orgLocationId) throws Exception {
		return onBoardingTemplateDao.getOnBoardingTemplateByTemplateNameAndOrgLocationId(templateName, orgLocationId);
	}

	@Override
	public OnBoardingTemplate getOnBoardingTemplateByTemplateUUID(String templateUUID) throws Exception {
		return onBoardingTemplateDao.getOnBoardingTemplateByTemplateUUID(templateUUID);
	}

	@Override
	public List<OnBoardingTemplate> getOnBoardingFinishedTemplatesList(Long orgLocationId) throws Exception {
		return onBoardingTemplateDao.getOnBoardingFinishedTemplatesList(orgLocationId);
	}
	

	@Override
	public OnBoardingTemplate getOnBoardTemplateDetails(OnBoardingTemplateDTO onBoardingTemplateDTO, User user, OrgLocations orgLocations)
			throws Exception {
		
		 OnBoardingTemplate onBoardingTemplate = null;
		
		 if(StringUtils.isNotBlank(onBoardingTemplateDTO.getTemplateId())){
		 		onBoardingTemplate = getOnBoardingTemplateById(Long.valueOf(onBoardingTemplateDTO.getTemplateId()));
		 		onBoardingTemplate.setTemplateName(onBoardingTemplateDTO.getTemplateName());
			 	onBoardingTemplate.setDescription(onBoardingTemplateDTO.getDescription().trim());
			 	onBoardingTemplate.setUpdatedBy(user.getUserCode());
			 	onBoardingTemplate.setUpdatedDate(new Date());
		 	 
		 	 }else{
		 		 onBoardingTemplate = new OnBoardingTemplate();
			 	 onBoardingTemplate.setTemplateName(onBoardingTemplateDTO.getTemplateName());
			 	 onBoardingTemplate.setDescription(onBoardingTemplateDTO.getDescription().trim());
			 	 onBoardingTemplate.setOrgLocationId(orgLocations);
			 	
			 	 StringBuilder userName = new StringBuilder();
				if(!isNullOrEmpty(user.getLastName())){
					onBoardingTemplate.setCreatedUser(userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}else{
					onBoardingTemplate.setCreatedUser(userName.append(user.getFirstName()).toString());
				}
				onBoardingTemplate.setTemplateStatus("Draft");
				onBoardingTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
				onBoardingTemplate.setTemplateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			 	onBoardingTemplate.setCreatedBy(user.getUserCode());
			 	onBoardingTemplate.setCreatedDate(new Date());
			 	onBoardingTemplate.setUpdatedBy(user.getUserCode());
			 	onBoardingTemplate.setUpdatedDate(new Date());
		 	 }
		 
		        onBoardingTemplate = saveOnBoardingTemplate(onBoardingTemplate);
		        return onBoardingTemplate;
		}

}
