package com.bis.sourcelead.service.impl.performance;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.performance.PerformanceAppraisalDao;
import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalManagersDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalPeersDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.dto.performance.PerformanceFeedbackDTO;
import com.bis.sourcelead.dto.performance.PerformanceGeneralTasksDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceJobsDetailsDTO;
import com.bis.sourcelead.service.performance.PerformanceAppraisalService;

@Service
public class PerformanceAppraisalServiceImpl implements PerformanceAppraisalService{

	@Autowired
	PerformanceAppraisalDao performanceAppraisalDao;
	
	@Override
	public ResponseList<PerformanceAppraisalTemplateDTO> getAssignedMyAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,Long userId, Long orgLocationId,String dateFormat)
			throws Exception {
		return performanceAppraisalDao.getAssignedMyAppraisalList(performanceAppraisalTemplateDTO,userId, orgLocationId, dateFormat);
	}

	@Override
	public ResponseList<PerformanceAppraisalTemplateDTO> getAssignedPeersAppraisalList(
			PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO, Long userId, Long orgLocationId,
			String dateFormat) throws Exception {
		return performanceAppraisalDao.getAssignedPeersAppraisalList(performanceAppraisalTemplateDTO, userId, orgLocationId, dateFormat);
	}

	@Override
	public PerformanceAppraisalDetailsDTO getSelfAppraisalTemplateDetails(String assignedUUID, String dateFormat)
			throws Exception {
		return performanceAppraisalDao.getSelfAppraisalTemplateDetails(assignedUUID, dateFormat);
	}

	@Override
	public List<PerformanceFeedbackTemplateDTO> getPerformanceAssignedFeedbackTemplateDTOList(String assignedUUID,String templateIds)
			throws Exception {
		return performanceAppraisalDao.getPerformanceAssignedFeedbackTemplateDTOList(assignedUUID,templateIds);
	}

	@Override
	public PerformanceFeedbackTemplateDTO getPerformanceFeedbackTemplateDTOByUUID(String uuid) throws Exception {
		return performanceAppraisalDao.getPerformanceFeedbackTemplateDTOByUUID(uuid);
	}

	@Override
	public PerformanceFeedbackTemplateDTO getPerformanceSubmittedFeedbackTemplateDTOByUUID(String templateUUID,
			String assignedUUID, Long userId) throws Exception {
		return performanceAppraisalDao.getPerformanceSubmittedFeedbackTemplateDTOByUUID(templateUUID, assignedUUID, userId);
	}

	@Override
	public PerformanceGoalsDTO getperformanceGoalsDetails(String assignedUUID, Long userId) throws Exception {
		return performanceAppraisalDao.getperformanceGoalsDetails(assignedUUID, userId);
	}

	@Override
	public List<ProjectassignTasksDTO> getPerformanceAssignedProjectAssignedTasksDTOList(String assignedUUID,String dateFormat)
			throws Exception {
		return performanceAppraisalDao.getPerformanceAssignedProjectAssignedTasksDTOList(assignedUUID, dateFormat);
	}

	@Override
	public List<PerformanceJobsDetailsDTO> getPerformanceJobsDetails(String assignedUUID, String dateFormat)
			throws Exception {
		return performanceAppraisalDao.getPerformanceJobsDetails(assignedUUID, dateFormat);
	}

	@Override
	public PerformanceGeneralTasksDetailsDTO getGeneralTasksDetails(String assignedUUID, String dateFormat)
			throws Exception {
		return performanceAppraisalDao.getGeneralTasksDetails(assignedUUID, dateFormat);
	}

	@Override
	public PerformanceFeedbackDTO getPerformanceFeedbackDetails(String assignedUUID, Long userId)
			throws Exception {
		return performanceAppraisalDao.getPerformanceFeedbackDetails(assignedUUID, userId);
	}

	@Override
	public String getJsonCategoriesString(String assignedUUID) throws Exception {
		return performanceAppraisalDao.getJsonCategoriesString(assignedUUID);
	}

	@Override
	public ResponseList<PerformanceAppraisalTemplateDTO> getEmployeeAssignedAppraisalList(
			PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO, Long userId, Long orgLocationId,
			String dateFormat,Long orgId,String isGlobalUser) throws Exception {
		return performanceAppraisalDao.getEmployeeAssignedAppraisalList(performanceAppraisalTemplateDTO, userId, orgLocationId, dateFormat,orgId,isGlobalUser);
	}

	@Override
	public ResponseList<PerformanceAppraisalTemplateDTO> getAppraisalAssignedEmployeesList(
			PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO, Long userId, Long orgLocationId,
			String dateFormat,Long orgId, String isGlobalUser) throws Exception {
		return performanceAppraisalDao.getAppraisalAssignedEmployeesList(performanceAppraisalTemplateDTO, userId, orgLocationId, dateFormat,orgId,isGlobalUser);
	}

	@Override
	public List<PerformanceAppraisalPeersDTO> getAppraisalAssignedPeers(String assignedUUID) throws Exception {
		return performanceAppraisalDao.getAppraisalAssignedPeers(assignedUUID);
	}

	@Override
	public Integer getPeersTotalCategoriesCount(String assignedUUID, String templateUUID) throws Exception {
		return performanceAppraisalDao.getPeersTotalCategoriesCount(assignedUUID, templateUUID);
	}

	@Override
	public List<PerformanceAppraisalManagersDTO> getAppraisalAssignedManagers(String assignedUUID) throws Exception {
		return performanceAppraisalDao.getAppraisalAssignedManagers(assignedUUID);
	}

	@Override
	public List<PerformanceFeedbackDTO> getEmployeePerformanceFeedbackDetails(String assignedUUID, Long userId)
			throws Exception {
		return performanceAppraisalDao.getEmployeePerformanceFeedbackDetails(assignedUUID, userId);
	}

	@Override
	public PerformanceFeedbackDTO getAppraisalUserLeavesAndTimesheetDetails(String assignedUUID,String dateFormat) throws Exception {
		return performanceAppraisalDao.getAppraisalUserLeavesAndTimesheetDetails(assignedUUID,dateFormat);
	}

	@Override
	public List<PerformanceAppraisalTemplateDTO> getEmployeePerformanceAppraisalList(Long userId, Long orgLocationId, Date startDate,
			Date endDate,String dateFormat) throws Exception {
		return performanceAppraisalDao.getEmployeePerformanceAppraisalList(userId, orgLocationId, startDate, endDate, dateFormat);
	}

	

}
