package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.VendorOnboardTemplateDao;
import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboardingTemplate;
import com.bis.sourcelead.service.VendorOnboardTemplateService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class VendorOnboardTemplateServiceImpl implements VendorOnboardTemplateService {

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Autowired
	VendorOnboardTemplateDao vendorOnboardTemplateDao;
	
	@Override
	public List<VendorOnboardingTemplate> getVendorOnBoardingTemplatesList(Long orgLocationId) throws Exception {
		return vendorOnboardTemplateDao.getVendorOnBoardingTemplatesList(orgLocationId);
	}

	@Override
	public VendorOnboardingTemplate getVendorOnBoardTemplateDetails(OnBoardingTemplateDTO onBoardingTemplateDTO,
			User user, OrgLocations orgLocations) throws Exception {
		
		  VendorOnboardingTemplate onBoardingTemplate = null;
		
		 if(StringUtils.isNotBlank(onBoardingTemplateDTO.getTemplateId())){
		 		onBoardingTemplate = getVendorOnBoardingTemplateById(Long.valueOf(onBoardingTemplateDTO.getTemplateId()));
		 		onBoardingTemplate.setTemplateName(onBoardingTemplateDTO.getTemplateName());
		 		 if(StringUtils.isNotBlank(onBoardingTemplateDTO.getDescription())) {
			 		 onBoardingTemplate.setDescription(onBoardingTemplateDTO.getDescription());
			 	 }else {
			 		 onBoardingTemplate.setDescription(null);
			 	 }
			 	onBoardingTemplate.setUpdatedBy(user);
			 	onBoardingTemplate.setUpdatedDate(new Date());
		 	 }else{
		 		 onBoardingTemplate = new VendorOnboardingTemplate();
			 	 onBoardingTemplate.setTemplateName(onBoardingTemplateDTO.getTemplateName());
			 	 if(StringUtils.isNotBlank(onBoardingTemplateDTO.getDescription())) {
			 		 onBoardingTemplate.setDescription(onBoardingTemplateDTO.getDescription());
			 	 }
			 	 onBoardingTemplate.setOrgLocationId(orgLocations);
				 onBoardingTemplate.setTemplateStatus("Draft");
				 onBoardingTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
				 onBoardingTemplate.setTemplateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			 	 onBoardingTemplate.setCreatedBy(user);
			 	 onBoardingTemplate.setCreatedDate(new Date());
			 	 onBoardingTemplate.setUpdatedBy(user);
			 	 onBoardingTemplate.setUpdatedDate(new Date());
		 	 }
		 
		        onBoardingTemplate = saveVendorOnBoardingTemplate(onBoardingTemplate);
		        return onBoardingTemplate;
		
	}

	@Override
	public VendorOnboardingTemplate getVendorOnBoardingTemplateById(Long templateId) throws Exception {
		return vendorOnboardTemplateDao.getVendorOnBoardingTemplateById(templateId);
	}

	@Override
	public VendorOnboardingTemplate saveVendorOnBoardingTemplate(VendorOnboardingTemplate onboardingTemplate)
			throws Exception {
		return vendorOnboardTemplateDao.saveVendorOnBoardingTemplate(onboardingTemplate);
	}

	@Override
	public VendorOnboardingTemplate getVendorOnBoardingTemplateByTemplateNameAndOrgLocationId(String templateName,
			Long orgLocationId) throws Exception {
		return vendorOnboardTemplateDao.getVendorOnBoardingTemplateByTemplateNameAndOrgLocationId(templateName, orgLocationId);
	}

	@Override
	public VendorOnboardingTemplate getVendorOnBoardingTemplateByTemplateUUID(String templateUUID) throws Exception {
		return vendorOnboardTemplateDao.getVendorOnBoardingTemplateByTemplateUUID(templateUUID);
	}

	@Override
	public List<VendorOnboardingTemplate> getVendorOnBoardingFinishedTemplatesList(Long orgLocationId)
			throws Exception {
		return vendorOnboardTemplateDao.getVendorOnBoardingFinishedTemplatesList(orgLocationId);
	}

}
