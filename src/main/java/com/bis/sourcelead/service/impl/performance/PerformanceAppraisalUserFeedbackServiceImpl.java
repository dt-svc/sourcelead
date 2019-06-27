package com.bis.sourcelead.service.impl.performance;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceAppraisalAssignedAssociatesDao;
import com.bis.sourcelead.dao.performance.PerformanceAppraisalUserFeedbackDao;
import com.bis.sourcelead.dao.performance.PerformanceFeedbackTemplateDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.performance.PerformanceFeedbackCategoryDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalAssignedAssociates;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalUserFeedback;
import com.bis.sourcelead.entity.performance.PerformanceFeedbackTemplate;
import com.bis.sourcelead.service.performance.PerformanceAppraisalUserFeedbackService;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PerformanceAppraisalUserFeedbackServiceImpl implements PerformanceAppraisalUserFeedbackService{

	@Autowired
	PerformanceAppraisalUserFeedbackDao performanceAppraisalUserFeedbackDao;
	
	@Autowired
	PerformanceAppraisalAssignedAssociatesDao performanceAppraisalAssignedAssociatesDao;
	
	@Autowired
	PerformanceFeedbackTemplateDao performanceFeedbackTemplateDao;
	
	@Override
	public void storePerformanceAppraisalUserFeedback(PerformanceFeedbackCategoryDTO performanceFeedbackCategoryDTO)
			throws Exception {
		
		PerformanceAppraisalUserFeedback performanceAppraisalUserFeedback = null;
		PerformanceAppraisalAssignedAssociates assignedAssociates = performanceAppraisalAssignedAssociatesDao.getPerformanceAppraisalAssignedAssociatesByUUID(performanceFeedbackCategoryDTO.getAssignedUUID());
		PerformanceFeedbackTemplate feedbackTemplae = performanceFeedbackTemplateDao.getPerformanceFeedbackTemplateByUUID(performanceFeedbackCategoryDTO.getFeedbackTemplateUUID());
		performanceAppraisalUserFeedback = performanceAppraisalUserFeedbackDao.getPerformanceAppraisalUserFeedbackAssignedUserId(performanceFeedbackCategoryDTO.getUserId(), assignedAssociates.getId(),feedbackTemplae.getId());
		
		User user = new User();
		user.setId(performanceFeedbackCategoryDTO.getUserId());
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(performanceFeedbackCategoryDTO.getOrgLocationId());
		Org org = new Org();
		org.setId(performanceFeedbackCategoryDTO.getOrgId());
		
		if(performanceAppraisalUserFeedback == null) {
			performanceAppraisalUserFeedback = new PerformanceAppraisalUserFeedback();
			performanceAppraisalUserFeedback.setOrgId(org);
			performanceAppraisalUserFeedback.setOrgLocationId(orgLocations);
			performanceAppraisalUserFeedback.setUserId(user);
			performanceAppraisalUserFeedback.setCreatedBy(user);
			performanceAppraisalUserFeedback.setCreatedDate(new Date());
			performanceAppraisalUserFeedback.setAppraisalAssignedId(assignedAssociates);
			performanceAppraisalUserFeedback.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			performanceAppraisalUserFeedback.setStatus(Constants.RECORD_ACTIVE);
			performanceAppraisalUserFeedback.setFeedbackTemplateId(feedbackTemplae);
		}else {
			performanceAppraisalUserFeedback.setUpdatedBy(user);
			performanceAppraisalUserFeedback.setUpdatedDate(new Date());
		}
		
		performanceAppraisalUserFeedback.setFeedbackJsonString(feedbackTemplae.getJsonString());
		performanceAppraisalUserFeedback.setFeedbackJsonStringValues(performanceFeedbackCategoryDTO.getJsonDataValues());
		
		
		if(performanceFeedbackCategoryDTO.getUploadedFileInfo() != null && !performanceFeedbackCategoryDTO.getUploadedFileInfo().isEmpty()) {
			performanceFeedbackCategoryDTO.setStaticPath(performanceFeedbackCategoryDTO.getStaticPath());
			 for(FileInfo fileInfo : performanceFeedbackCategoryDTO.getUploadedFileInfo()){
				 if(fileInfo.getFileURL() != null && fileInfo.getStatus() == 0) {
					 FileUploadUtil.moveFile(performanceFeedbackCategoryDTO.getStaticPath(), fileInfo, "feedbackTemplateAttachments");
				 }
					
				}
		}
		
		performanceAppraisalUserFeedbackDao.savePerformanceAppraisalUserFeedback(performanceAppraisalUserFeedback);	
		
	}

}
