package com.bis.sourcelead.service.impl.performance;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceAppraisalTemplateDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalAssignToListDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalPeersDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
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
		orgLocations.setId(orgLocationId);
		User user = new User();
		user.setId(userId);
		
		PerformanceAppraisalTemplate template = null;
		if(performanceAppraisalTemplateDTO.getAppraisalTemplateId() != null) {
			template = performanceAppraisalTemplateDao.getPerformanceAppraisalTemplateById(performanceAppraisalTemplateDTO.getAppraisalTemplateId());
			template.setUpdatedBy(user);
			template.setUpdatedDate(new Date());
		}else {
			template = new PerformanceAppraisalTemplate();
			template.setCreatedBy(user);
			template.setCreatedDate(new Date());
			template.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
		template.setName(performanceAppraisalTemplateDTO.getName());
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalStartDate())) {
			template.setAppraisalStartDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalStartDate()));
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalEndDate())) {
			template.setAppraisalEndDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalEndDate()));
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalDepartment())) {
			template.setDepartment(performanceAppraisalTemplateDTO.getAppraisalDepartment());
		}else {
			template.setDepartment(null);
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalRoles())) {
			template.setRoles(performanceAppraisalTemplateDTO.getAppraisalRoles());
		}else {
			template.setRoles(null);
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalEmpType())) {
			template.setEmployeeType(performanceAppraisalTemplateDTO.getAppraisalEmpType());
		}else {
			template.setEmployeeType(null);
		}
		
		template.setIsSelfReviewRequired(performanceAppraisalTemplateDTO.getIsSelfAppraisalRequired());
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getSelfAppraisalStartDate())) {
			template.setSelfAppraisalStartDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getSelfAppraisalStartDate()));
		}else {
			template.setSelfAppraisalStartDate(null);
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getSelfAppraisalEndDate())) {
			template.setSelfAppraisalEndDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getSelfAppraisalEndDate()));
		}else {
			template.setSelfAppraisalEndDate(null);
		}
		
		template.setIsReportingManagerReviewRequired(performanceAppraisalTemplateDTO.getIsReportingManagerAppraisalRequired());
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalApprovingStartDate())) {
			template.setApprovingFromStartDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalApprovingStartDate()));
		}else {
			template.setApprovingFromStartDate(null);
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalApprovingEndDate())) {
			template.setApprovingFromEndDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalApprovingEndDate()));
		}else {
			template.setApprovingFromEndDate(null);
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalProcessStartDate())) {
			template.setProcessPeriodStartDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalProcessStartDate()));
		}else {
			template.setProcessPeriodStartDate(null);
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalProcessEndDate())) {
			template.setProcessPeriodEndDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getAppraisalProcessEndDate()));
		}else {
			template.setProcessPeriodEndDate(null);
		}
		
		template.setIsResourceManagerReviewRequried(performanceAppraisalTemplateDTO.getIsResourceManagerAppraisalRequired());
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getResourceManagerReviewStartDate())) {
			template.setResourceManagerReviewStartDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getResourceManagerReviewStartDate()));
		}else {
			template.setResourceManagerReviewStartDate(null);
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getResourceManagerReviewEndDate())) {
			template.setResourceManagerReviewEndDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getResourceManagerReviewEndDate()));
		}else {
			template.setResourceManagerReviewEndDate(null);
		}
		
		template.setIsPeerReviewRequried(performanceAppraisalTemplateDTO.getIsPeerReviewRequired());
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getPeerReviewStartDate())) {
			template.setPeerReviewStartDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getPeerReviewStartDate()));
		}else {
			template.setPeerReviewStartDate(null);
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getPeerReviewEndDate())) {
			template.setPeerReviewEndDate(commonUtil.orgLocationDateFormat(dateFormat,performanceAppraisalTemplateDTO.getPeerReviewEndDate()));
		}else {
			template.setPeerReviewEndDate(null);
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getDescriptionDisplay())) {
			template.setDescriptionDisplay(performanceAppraisalTemplateDTO.getDescriptionDisplay());
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getDescription())) {
			template.setDescription(performanceAppraisalTemplateDTO.getDescription());
		}else {
			template.setDescription(null);
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalCategories())) {
			template.setCategories(performanceAppraisalTemplateDTO.getAppraisalCategories());
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getComptencyCategories())) {
			template.setCompetencies(performanceAppraisalTemplateDTO.getComptencyCategories());
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getJsonCategoriesString())) {
			template.setCategoriesJsonString(performanceAppraisalTemplateDTO.getJsonCategoriesString());
		}
		
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getAppraisalGoals()) && 
				performanceAppraisalTemplateDTO.getAppraisalGoals().equalsIgnoreCase(Constants.YES)) {
			template.setIsGoalsConfigured(performanceAppraisalTemplateDTO.getAppraisalGoals());
		}else{
			template.setIsGoalsConfigured(Constants.NO);
		}
		
		template.setOrgLocationId(orgLocations);
		template.setStatus(Constants.RECORD_ACTIVE);
		performanceAppraisalTemplateDao.savePerformanceAppraisalTemplate(template);
		return template;
	}

	@Override
	public List<PerformanceAppraisalTemplate> getPerformanceAppraisalTemplateList(Long orgLocationId) throws Exception {
		return performanceAppraisalTemplateDao.getPerformanceAppraisalTemplateList(orgLocationId);
	}

	@Override
	public PerformanceAppraisalTemplateDTO getPerformanceAppraisalTemplateDTOById(String templateUUId) throws Exception {
		   PerformanceAppraisalTemplateDTO templateDTO = new PerformanceAppraisalTemplateDTO();
		   PerformanceAppraisalTemplate template = performanceAppraisalTemplateDao.getPerformanceAppraisalTemplateByUUID(templateUUId);
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
		   
		   templateDTO.setIsSelfAppraisalRequired(template.getIsSelfReviewRequired());
		   
		   if(StringUtils.isNotBlank(template.getSelfAppraisalStartDateStr())) {
			   templateDTO.setSelfAppraisalStartDate(template.getSelfAppraisalStartDateStr());
		   }
		   
		   if(StringUtils.isNotBlank(template.getSelfAppraisalEndDateStr())) {
			   templateDTO.setSelfAppraisalEndDate(template.getSelfAppraisalStartDateStr());
		   }
		   
		   templateDTO.setIsReportingManagerAppraisalRequired(template.getIsReportingManagerReviewRequired());
		   
		   if(StringUtils.isNotBlank(template.getApprovingFromStartDateStr())) {
			   templateDTO.setAppraisalApprovingStartDate(template.getApprovingFromStartDateStr());
		   }
		   
		   if(StringUtils.isNotBlank(template.getApprovingFromEndDateStr())) {
			   templateDTO.setAppraisalApprovingEndDate(template.getApprovingFromEndDateStr());
		   }
		   
		   
		   if(StringUtils.isNotBlank(template.getProcessPeriodStartDateStr())) {
			   templateDTO.setAppraisalProcessStartDate(template.getProcessPeriodStartDateStr());
		   }
		   
		   if(StringUtils.isNotBlank(template.getProcessPeriodEndDateStr())) {
			   templateDTO.setAppraisalProcessEndDate(template.getProcessPeriodEndDateStr());
		   }
		   
		   templateDTO.setIsPeerReviewRequired(template.getIsPeerReviewRequried());
		   
		   if(StringUtils.isNotBlank(template.getPeerReviewStartDateStr())) {
			   templateDTO.setPeerReviewStartDate(template.getPeerReviewStartDateStr());
		   }
		   
		   if(StringUtils.isNotBlank(template.getPeerReviewEndDateStr())) {
			   templateDTO.setPeerReviewEndDate(template.getPeerReviewEndDateStr());
		   }
		   
		   templateDTO.setIsResourceManagerAppraisalRequired(template.getIsResourceManagerReviewRequried());
		   
		   if(StringUtils.isNotBlank(template.getResourceManagerReviewStartDateStr())) {
			   templateDTO.setResourceManagerReviewStartDate(template.getResourceManagerReviewStartDateStr());
		   }
		   
		   if(StringUtils.isNotBlank(template.getResourceManagerReviewEndDateStr())) {
			   templateDTO.setResourceManagerReviewEndDate(template.getResourceManagerReviewEndDateStr());
		   }
		   
		   if(StringUtils.isNotBlank(template.getDescriptionDisplay())) {
			   templateDTO.setDescriptionDisplay(template.getDescriptionDisplay());
		   }
		   
		   if(StringUtils.isNotBlank(template.getDescription())) {
			   templateDTO.setDescription(template.getDescription());
		   }
		   
		   if(StringUtils.isNotBlank(template.getCategories())) {
			   templateDTO.setAppraisalCategories(template.getCategories());
		   }
		   
		   if(StringUtils.isNotBlank(template.getCompetencies())) {
			   templateDTO.setComptencyCategories(template.getCompetencies());
		   }
		   
		   if(StringUtils.isNotBlank(template.getCategoriesJsonString())) {
			   templateDTO.setJsonCategoriesString(template.getCategoriesJsonString());
		   }
		   
		   
		   return templateDTO;
	}

	@Override
	public PerformanceAppraisalTemplate getPerformanceAppraisalTemplateById(Long id) throws Exception {
		return performanceAppraisalTemplateDao.getPerformanceAppraisalTemplateById(id);
	}

	@Override
	public ResponseList<PerformanceAppraisalAssignToListDTO> getPerformanceAppraisalAssignToList(
			PerformanceAppraisalAssignToListDTO performanceAppraisalAssignToListDTO, Long orgLocationId)
			throws Exception {
		return performanceAppraisalTemplateDao.getPerformanceAppraisalAssignToList(performanceAppraisalAssignToListDTO, orgLocationId);
	}

	@Override
	public PerformanceAppraisalTemplate getPerformanceAppraisalTemplateByUUID(String uuid) throws Exception {
		return performanceAppraisalTemplateDao.getPerformanceAppraisalTemplateByUUID(uuid);
	}

	@Override
	public ResponseList<PerformanceAppraisalPeersDTO> getPerformancePeersList(
			PerformanceAppraisalPeersDTO performanceAppraisalPeersDTO, Long orgLocationId) throws Exception {
		return performanceAppraisalTemplateDao.getPerformancePeersList(performanceAppraisalPeersDTO, orgLocationId);
	}

	@Override
	public List<PerformanceAppraisalTemplateDTO> getPerformanceAppraisalTemplateDTOList(Long orgLocationId,String dateFormat)
			throws Exception {
		return performanceAppraisalTemplateDao.getPerformanceAppraisalTemplateDTOList(orgLocationId,dateFormat);
	}

}
