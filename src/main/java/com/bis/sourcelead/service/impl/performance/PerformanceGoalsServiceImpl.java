package com.bis.sourcelead.service.impl.performance;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceGoalsDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDoNutChartCountsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceGoals;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAttachments;
import com.bis.sourcelead.service.performance.PerformanceGoalsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PerformanceGoalsServiceImpl implements PerformanceGoalsService{
	
	@Autowired
	PerformanceGoalsDao performanceGoalsDao;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public PerformanceGoals storePerformanceGoals(PerformanceGoalsDTO performanceGoalsDTO, Long userId,
			Long orgLocationId,String dateFormat) throws Exception {
		
		PerformanceGoals goals = null;
		User loginUser = new User();
		loginUser.setId(userId);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		
		if(performanceGoalsDTO.getGoalId() != null) {
			goals = getPerformanceGoalsById(performanceGoalsDTO.getGoalId());
			goals.setUpdatedDate(new Date());
			goals.setUpdatedBy(loginUser);
		}else {
			goals = new PerformanceGoals();
			goals.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			goals.setCreatedDate(new Date());
			goals.setCreatedBy(loginUser);
			goals.setOrgLocationId(orgLocations);
			goals.setStatus(Constants.RECORD_ACTIVE);
		}
		
		goals.setGoalName(performanceGoalsDTO.getGoalName());
		
		if(StringUtils.isNotBlank(performanceGoalsDTO.getGoalStartDate())) {
			Date startDate = commonUtil.orgLocationDateFormat(dateFormat,performanceGoalsDTO.getGoalStartDate());
			goals.setStartDate(startDate);
		}else {
			goals.setStartDate(null);
		}
		
		if(StringUtils.isNotBlank(performanceGoalsDTO.getGoalEndDate())) {
			Date endDate = commonUtil.orgLocationDateFormat(dateFormat,performanceGoalsDTO.getGoalEndDate());
			goals.setEndDate(endDate);
		}else {
			goals.setEndDate(null);
		}
		
		if(StringUtils.isNotBlank(performanceGoalsDTO.getDescription())) {
			goals.setDescription(performanceGoalsDTO.getDescription());
		}else {
			goals.setDescription(null);
		}
		
		if(StringUtils.isNotBlank(performanceGoalsDTO.getGoalsAssignTo())) {
			Set<PerformanceGoalsAssignTo> goalsAssignedToSet = new HashSet<>();
			String[] assignTo = performanceGoalsDTO.getGoalsAssignTo().split(",");
			for(int i=0; i < assignTo.length; i++) {
				PerformanceGoalsAssignTo goalsAssignTo = new PerformanceGoalsAssignTo();
				goalsAssignTo.setGoalId(goals);
				User assignedUser = new User();
				assignedUser.setId(Long.valueOf(assignTo[i]));
				goalsAssignTo.setAssignedUserId(assignedUser);
				goalsAssignTo.setOrgLocationId(orgLocations);
				goalsAssignTo.setCreatedBy(loginUser);
				goalsAssignTo.setCreatedDate(new Date());
				goalsAssignTo.setStatus(Constants.RECORD_ACTIVE);
				goalsAssignTo.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				goalsAssignedToSet.add(goalsAssignTo);
			}
			
			goals.setPerformanceGoalsAssignTo(goalsAssignedToSet);
		}
		
	
		if(performanceGoalsDTO.getUploadedFileInfo() != null &&  !performanceGoalsDTO.getUploadedFileInfo().isEmpty()) {
			Set<PerformanceGoalsAttachments> performaneGoalsAttachmentsList = new HashSet<>();
				List<FileInfo> goalsAttachemtList = performanceGoalsDTO .getUploadedFileInfo();
				for(FileInfo fileInfo : goalsAttachemtList){
					PerformanceGoalsAttachments attachment = new PerformanceGoalsAttachments();
					attachment.setFileName(fileInfo.getFileName());
					attachment.setFileType(fileInfo.getContentType());
					attachment.setFileUrl(fileInfo.getFileURL());
					attachment.setStatus(Constants.RECORD_ACTIVE);
					attachment.setGoalId(goals);
					attachment.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
					performaneGoalsAttachmentsList.add(attachment);
				}
			
				goals.setPerformanceGoalsAttachments(performaneGoalsAttachmentsList);
				
		        for(FileInfo fileInfo : performanceGoalsDTO.getUploadedFileInfo()){
					FileUploadUtil.moveFile(performanceGoalsDTO.getStaticPath(), fileInfo, "goals");
				}
		}
		
		performanceGoalsDao.savePerformanceGoals(goals);
		
		
		return null;
	}

	@Override
	public PerformanceGoals getPerformanceGoalsById(Long goalId) throws Exception {
		return performanceGoalsDao.getPerformanceGoalsById(goalId);
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getTotalGoalsDoNutChartCounts(Long userId, Long orgLocationId)
			throws Exception {
		return performanceGoalsDao.getTotalGoalsDoNutChartCounts(userId, orgLocationId);
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getInprogressGoalsDoNutChartCounts(Long userId, Long orgLocationId)
			throws Exception {
		return performanceGoalsDao.getInprogressGoalsDoNutChartCounts(userId, orgLocationId);
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getCompletedGoalsDoNutChartCounts(Long userId, Long orgLocationId)
			throws Exception {
		return performanceGoalsDao.getCompletedGoalsDoNutChartCounts(userId, orgLocationId);
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getOverDueGoalsDoNutChartCounts(Long userId, Long orgLocationId)
			throws Exception {
		return performanceGoalsDao.getOverDueGoalsDoNutChartCounts(userId, orgLocationId);
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getNotStartedGoalsDoNutChartCounts(Long userId, Long orgLocationId)
			throws Exception {
		return performanceGoalsDao.getNotStartedGoalsDoNutChartCounts(userId, orgLocationId);
	}

	@Override
	public ResponseList<PerformanceGoalsDTO> getPerformanceGoalsList(PerformanceGoalsDTO performanceGoalsDTO,Long userId, Long orgLocationId,String dateFormat) throws Exception {
		return performanceGoalsDao.getPerformanceGoalsList(performanceGoalsDTO,userId, orgLocationId,dateFormat);
	}

}
