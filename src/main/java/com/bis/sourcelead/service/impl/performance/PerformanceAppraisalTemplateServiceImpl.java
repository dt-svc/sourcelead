package com.bis.sourcelead.service.impl.performance;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceAppraisalTemplateDao;
import com.bis.sourcelead.dto.DepartmentDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.performance.PerformanceAppraisalTemplateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

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
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);;
		User user = new User();
		user.setId(userId);
		
		PerformanceAppraisalTemplate template = new PerformanceAppraisalTemplate();
		template.setName(performanceAppraisalTemplateDTO.getName());
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalStartDate())) {
			template.setAppraisalStartDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalStartDate()));
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalEndDate())) {
			template.setAppraisalEndDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalEndDate()));
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalDepartment())) {
			template.setDepartment(performanceAppraisalTemplateDTO.getAppraisalDepartment());
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalRoles())) {
			template.setRoles(performanceAppraisalTemplateDTO.getAppraisalRoles());
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalEmpType())) {
			template.setEmployeeType(performanceAppraisalTemplateDTO.getAppraisalEmpType());
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getSelfAppraisalStartDate())) {
			template.setSelfAppraisalStartDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getSelfAppraisalStartDate()));
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getSelfAppraisalEndDate())) {
			template.setSelfAppraisalEndDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getSelfAppraisalEndDate()));
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalApprovingStartDate())) {
			template.setApprovingFromStartDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalApprovingStartDate()));
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalApprovingEndDate())) {
			template.setApprovingFromEndDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalApprovingEndDate()));
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getApplicableFor())) {
			template.setAppraisalApplicableFor(performanceAppraisalTemplateDTO.getApplicableFor());
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getExperienceYears())) {
			template.setExperienceYears(Integer.valueOf(performanceAppraisalTemplateDTO.getExperienceYears()));
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getExperienceMonths())) {
			template.setExperienceMonths(Integer.valueOf(performanceAppraisalTemplateDTO.getExperienceMonths()));
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getDescriptionDisplay())) {
			template.setDescriptionDisplay(performanceAppraisalTemplateDTO.getDescriptionDisplay());
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getDescription())) {
			template.setDescription(performanceAppraisalTemplateDTO.getDescription());
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalCategories())) {
			template.setCategories(performanceAppraisalTemplateDTO.getAppraisalCategories());
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getJsonCategoriesString())) {
			template.setCategoriesJsonString(performanceAppraisalTemplateDTO.getJsonCategoriesString());
		}
		template.setOrgLocationId(orgLocations);
		template.setCreatedBy(user);
		template.setUpdatedBy(user);
		template.setCreatedDate(new Date());
		template.setUpdatedDate(new Date());
		template.setStatus(Constants.RECORD_ACTIVE);
		template.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		performanceAppraisalTemplateDao.savePerformanceAppraisalTemplate(template);
		return template;
	}

	@Override
	public List<PerformanceAppraisalTemplate> getPerformanceAppraisalTemplateList(Long orgLocationId) throws Exception {
		return performanceAppraisalTemplateDao.getPerformanceAppraisalTemplateList(orgLocationId);
	}

	@Override
	public PerformanceAppraisalTemplateDTO getPerformanceAppraisalTemplateById(Long id) throws Exception {
		   PerformanceAppraisalTemplateDTO templateDTO = new PerformanceAppraisalTemplateDTO();
		   PerformanceAppraisalTemplate template = performanceAppraisalTemplateDao.getPerformanceAppraisalTemplateById(id);
		   templateDTO.setAppraisalTemplateId(template.getId());
		   templateDTO.setName(template.getName());
		   templateDTO.setAppraisalStartDate(template.getAppraisalStartDateStr());
		   templateDTO.setAppraisalEndDate(template.getAppraisalEndDateStr());
		   if(StringUtils.isNotBlank(template.getDepartment())) {
			   templateDTO.setAppraisalDepartment(template.getDepartment());
		   }
		   
		   if(StringUtils.isNotBlank(template.getRoles())) {
			   templateDTO.setAppraisalRoles(template.getRoles());
		   }
		   
		   if(StringUtils.isNotBlank(template.getEmployeeType())) {
			   templateDTO.setAppraisalEmpType(template.getEmployeeType());
		   }
		   
		   if(StringUtils.isNotBlank(template.getCategories())) {
			   templateDTO.setAppraisalCategories(template.getCategories());
		   }
		   
		   return templateDTO;
	}

}
