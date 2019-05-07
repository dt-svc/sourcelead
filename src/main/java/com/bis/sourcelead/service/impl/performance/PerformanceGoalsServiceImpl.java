package com.bis.sourcelead.service.impl.performance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dao.performance.PerformanceGoalsAssignToDao;
import com.bis.sourcelead.dao.performance.PerformanceGoalsDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TeamDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsAssignToDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDoNutChartCountsDTO;
import com.bis.sourcelead.dto.performance.PerformanceTeamMembersDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
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
	
	@Autowired
	PerformanceGoalsAssignToDao performanceGoalsAssignToDao;
	
	@Autowired
	UserRoleDao userRoleDao;
 
	@Override
	public PerformanceGoals storePerformanceGoals(PerformanceGoalsDTO performanceGoalsDTO, Long userId,
			Long orgLocationId,String dateFormat) throws Exception {
		
		PerformanceGoals goals = null;
		User loginUser = new User();
		loginUser.setId(userId);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		
		 Set<PerformanceGoalsAssignTo> performanceGoalsAssignToSet = null;
		
		if(performanceGoalsDTO.getGoalId() != null) {
			goals = getPerformanceGoalsById(performanceGoalsDTO.getGoalId());
			goals.setUpdatedDate(new Date());
			goals.setUpdatedBy(loginUser);
			performanceGoalsAssignToSet = goals.getPerformanceGoalsAssignTo();
		}else {
			goals = new PerformanceGoals();
			goals.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			goals.setCreatedDate(new Date());
			goals.setCreatedBy(loginUser);
			goals.setOrgLocationId(orgLocations);
			goals.setStatus(Constants.RECORD_ACTIVE);
			
			if(StringUtils.isNotBlank(performanceGoalsDTO.getGoalType()) && performanceGoalsDTO.getGoalType().equalsIgnoreCase("Sub_Goal")) {
				PerformanceGoals parentGoal = performanceGoalsDao.getPerformanceGoalsByUUID(performanceGoalsDTO.getGoalUUID());
				goals.setGoalType(performanceGoalsDTO.getGoalType());
				goals.setGoalId(parentGoal);
				if(parentGoal.getTeamId() != null) {
					goals.setTeamId(parentGoal.getTeamId());
				}
				double percentage = getPerformanceParentGoalsPercentageDoneCountBySubGoalsPercentageDoneCount(parentGoal.getId());
				String parentGoalPercentageDone =  String.format("%.2f", percentage);
				parentGoal.setPercentageDone(Double.valueOf(parentGoalPercentageDone));
				performanceGoalsDao.savePerformanceGoals(parentGoal);
				
			}else {
				goals.setGoalType(Constants.GOAL_TYPE_NEW);
			}
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
		
		if(StringUtils.isNotBlank(performanceGoalsDTO.getGoalPercentage())) {
			String percentageDone =  String.format("%.2f", Double.valueOf(performanceGoalsDTO.getGoalPercentage()));
			goals.setPercentageDone(Double.valueOf(percentageDone));
		}else {
			goals.setPercentageDone(0d);
		}
		
		if(performanceGoalsAssignToSet != null && goals.getTeamId() == null) {
			List<Long> goalAssignedUserIds = new ArrayList<>();
			for(PerformanceGoalsAssignTo assignTo : performanceGoalsAssignToSet) {
				 if(assignTo.getStatus() == Constants.RECORD_ACTIVE) {
					 goalAssignedUserIds.add(assignTo.getAssignedUserId().getId());
				 }
			 }
			if(StringUtils.isNotBlank(performanceGoalsDTO.getGoalsAssignTo())) {
				String[] assignedUserIdStr = performanceGoalsDTO.getGoalsAssignTo().split(",");
				List<Long> assignedUserIds = new ArrayList<>();
				for(int i=0; i<assignedUserIdStr.length; i++) {
					assignedUserIds.add(Long.valueOf(assignedUserIdStr[i]));
				}
				for(Long element : goalAssignedUserIds) {
					if(!assignedUserIds.contains(element)) {
						UserRole userRole = userRoleDao.getUserDetailsByUserIdAndLocationId(element, orgLocationId);
						if(userRole != null && userRole.getReportingManager() != null && userRole.getReportingManager().getId().equals(userId)) {
							PerformanceGoalsAssignTo assignTo = performanceGoalsAssignToDao.getPerformanceGoalsAssignToByAssignedUserIdAndOrgLocationId(element, performanceGoalsDTO.getGoalId());
							assignTo.setUpdatedDate(new Date());
							assignTo.setStatus(Constants.RECORD_INACTIVE);
							assignTo.setUpdatedBy(loginUser);
							Set<PerformanceGoalsAssignTo> goalsAssignedToDeletedSet = new HashSet<>();
							goalsAssignedToDeletedSet.add(assignTo);
							goals.setPerformanceGoalsAssignTo(goalsAssignedToDeletedSet);
							performanceGoalsDao.savePerformanceGoals(goals);
						}
					}else {
						if(assignedUserIds.contains(element)) {
							assignedUserIds.remove(element);
						}
					}
				}
				
				performanceGoalsDTO.setGoalsAssignTo(StringUtils.join(assignedUserIds, ','));
			}
		}
		
		if(StringUtils.isNotBlank(performanceGoalsDTO.getTeamId())) {
			
			String performanceGoalTeamMembers = performanceGoalsDao.getTeamMemberUserIdsByTeamId(Long.valueOf(performanceGoalsDTO.getTeamId()));
			performanceGoalsDTO.setGoalsAssignTo(performanceGoalTeamMembers);
			Team team = new Team();
			team.setId(Long.valueOf(performanceGoalsDTO.getTeamId()));
			goals.setTeamId(team);
		}
		
		
		if(performanceGoalsAssignToSet != null && goals.getTeamId() != null) {
			List<Long> goalAssignedUserIds = new ArrayList<>();
			for(PerformanceGoalsAssignTo assignTo : performanceGoalsAssignToSet) {
				 if(assignTo.getStatus() == Constants.RECORD_ACTIVE) {
					 goalAssignedUserIds.add(assignTo.getAssignedUserId().getId());
				 }
			 }
			if(StringUtils.isNotBlank(performanceGoalsDTO.getGoalsAssignTo())) {
				String[] assignedUserIdStr = performanceGoalsDTO.getGoalsAssignTo().split(",");
				List<Long> assignedUserIds = new ArrayList<>();
				for(int i=0; i<assignedUserIdStr.length; i++) {
					assignedUserIds.add(Long.valueOf(assignedUserIdStr[i]));
				}
				for(Long element : goalAssignedUserIds) {
					if(!assignedUserIds.contains(element)) {
						PerformanceGoalsAssignTo assignTo = performanceGoalsAssignToDao.getPerformanceGoalsAssignToByAssignedUserIdAndOrgLocationId(element, performanceGoalsDTO.getGoalId());
						assignTo.setUpdatedDate(new Date());
						assignTo.setStatus(Constants.RECORD_INACTIVE);
						assignTo.setUpdatedBy(loginUser);
						Set<PerformanceGoalsAssignTo> goalsAssignedToDeletedSet = new HashSet<>();
						goalsAssignedToDeletedSet.add(assignTo);
						goals.setPerformanceGoalsAssignTo(goalsAssignedToDeletedSet);
						performanceGoalsDao.savePerformanceGoals(goals);
					}else {
						if(assignedUserIds.contains(element)) {
							assignedUserIds.remove(element);
						}
					}
				}
				
				performanceGoalsDTO.setGoalsAssignTo(StringUtils.join(assignedUserIds, ','));
			}
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
		
		if(goals != null && goals.getGoalId() != null) {
			double percentage = getPerformanceParentGoalsPercentageDoneCountBySubGoalsPercentageDoneCount(goals.getGoalId().getId());
			String parentGoalPercentageDone =  String.format("%.2f", percentage);
			PerformanceGoals parentGoal = getPerformanceGoalsById(goals.getGoalId().getId());
			parentGoal.setPercentageDone(Double.valueOf(parentGoalPercentageDone));
			performanceGoalsDao.savePerformanceGoals(parentGoal);
		}
		
		
		return goals;
	}
	
	
	@Override
	public PerformanceGoals savePerformanceGoals(PerformanceGoals performanceGoals) throws Exception {
		return performanceGoalsDao.savePerformanceGoals(performanceGoals);
	}

	@Override
	public PerformanceGoals getPerformanceGoalsById(Long goalId) throws Exception {
		return performanceGoalsDao.getPerformanceGoalsById(goalId);
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getTotalGoalsDoNutChartCounts(Long userId, Long orgLocationId,String performanceGoalType)
			throws Exception {
		return performanceGoalsDao.getTotalGoalsDoNutChartCounts(userId, orgLocationId,performanceGoalType);
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getInprogressGoalsDoNutChartCounts(Long userId, Long orgLocationId,String performanceGoalType)
			throws Exception {
		return performanceGoalsDao.getInprogressGoalsDoNutChartCounts(userId, orgLocationId,performanceGoalType);
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getCompletedGoalsDoNutChartCounts(Long userId, Long orgLocationId,String performanceGoalType)
			throws Exception {
		return performanceGoalsDao.getCompletedGoalsDoNutChartCounts(userId, orgLocationId,performanceGoalType);
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getOverDueGoalsDoNutChartCounts(Long userId, Long orgLocationId,String performanceGoalType)
			throws Exception {
		return performanceGoalsDao.getOverDueGoalsDoNutChartCounts(userId, orgLocationId,performanceGoalType);
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getNotStartedGoalsDoNutChartCounts(Long userId, Long orgLocationId,String performanceGoalType)
			throws Exception {
		return performanceGoalsDao.getNotStartedGoalsDoNutChartCounts(userId, orgLocationId,performanceGoalType);
	}

	@Override
	public ResponseList<PerformanceGoalsDTO> getPerformanceGoalsList(PerformanceGoalsDTO performanceGoalsDTO,Long userId, Long orgLocationId,String dateFormat,String performanceGoalType) throws Exception {
		return performanceGoalsDao.getPerformanceGoalsList(performanceGoalsDTO,userId, orgLocationId,dateFormat,performanceGoalType);
	}

	@Override
	public PerformanceGoals getPerformanceGoalsByUUID(String goalUUID) throws Exception {
		return performanceGoalsDao.getPerformanceGoalsByUUID(goalUUID);
	}

	@Override
	public List<PerformanceGoalsDTO> getPerformanceSubGoalsList(Long goalId, String dateFormat)
			throws Exception {
		return performanceGoalsDao.getPerformanceSubGoalsList(goalId, dateFormat);
	}

	@Override
	public List<PerformanceGoalsAssignToDTO> getPerformanceGoalsAssignedUsersList(String goalUUID, String dateFormat)
			throws Exception {
		return performanceGoalsDao.getPerformanceGoalsAssignedUsersList(goalUUID, dateFormat);
	}

	@Override
	public double getPerformanceSubGoalsPercentageDoneCount(Long goalId) throws Exception {
		return performanceGoalsDao.getPerformanceSubGoalsPercentageDoneCount(goalId);
	}

	@Override
	public double getPerformanceParentGoalsPercentageDoneCountBySubGoalsPercentageDoneCount(Long goalId)
			throws Exception {
		return performanceGoalsDao.getPerformanceParentGoalsPercentageDoneCountBySubGoalsPercentageDoneCount(goalId);
	}


	@Override
	public List<TeamDTO> getPerformanceGoalsTeamList(Long orgLocationId) throws Exception {
		return performanceGoalsDao.getPerformanceGoalsTeamList(orgLocationId);
	}


	@Override
	public String getTeamMemberUserIdsByTeamId(Long teamId) throws Exception {
		return performanceGoalsDao.getTeamMemberUserIdsByTeamId(teamId);
	}


	@Override
	public List<PerformanceTeamMembersDTO> getPerformanceTeamMembersByTeamId(Long teamId) throws Exception {
		return performanceGoalsDao.getPerformanceTeamMembersByTeamId(teamId);
	}


	@Override
	public List<PerformanceGoalsAssignToDTO> getReportingManagersUsersForAssigningGoals(Long reportingManagerId,
			Long orgLocationId) {
		return performanceGoalsDao.getReportingManagersUsersForAssigningGoals(reportingManagerId, orgLocationId);
	}


}
