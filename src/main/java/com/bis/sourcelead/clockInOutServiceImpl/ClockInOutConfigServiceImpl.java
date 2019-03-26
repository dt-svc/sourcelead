package com.bis.sourcelead.clockInOutServiceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.clockInOutDTO.ClockInOutConfigDTO;
import com.bis.sourcelead.clockInOutDao.ClockInOutConfigDao;
import com.bis.sourcelead.clockInOutEntity.ClockInOutConfig;
import com.bis.sourcelead.clockInOutService.ClockInOutConfigService;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;

@Service
public class ClockInOutConfigServiceImpl implements ClockInOutConfigService {
	
	
	@Autowired
	private ClockInOutConfigDao clockInOutConfigDao;

	@Override
	public ClockInOutConfig saveClockInOutConfiguration(ClockInOutConfigDTO clockInOutConfigDTO, OrgLocations loginOrgLocation,Long userId) {
		
		ClockInOutConfig clockInOut = null;
		
		if(clockInOutConfigDTO.getClockInOutId() != null) {
			clockInOut = clockInOutConfigDao.getClockInOutConfigBasedOnId(clockInOutConfigDTO.getClockInOutId());
			clockInOut.setUpdatedDate(new Date());
			clockInOut.setUpdatedBy(userId);
		}else {
			clockInOut = new ClockInOutConfig();
		}
		
		if(Constants.STATUS_YES.equalsIgnoreCase(clockInOutConfigDTO.getIsTrackEmpLocation())) {
			clockInOut.setIsTrackEmpLocation(true);
		}else {
			clockInOut.setIsTrackEmpLocation(false);
		}
		
		if(Constants.STATUS_YES.equalsIgnoreCase(clockInOutConfigDTO.getIsTrackTaskAssigned())) {
			clockInOut.setIsTrackTaskAssigned(true);
		}else {
			clockInOut.setIsTrackTaskAssigned(false);
		}
		
		if(Constants.STATUS_YES.equalsIgnoreCase(clockInOutConfigDTO.getIsJobChecked())) {
			clockInOut.setIsJobChecked(true);
		}else {
			clockInOut.setIsJobChecked(false);
		}
		
		if(Constants.STATUS_YES.equalsIgnoreCase(clockInOutConfigDTO.getIsProjectTasksChecked())) {
			clockInOut.setIsProjectTasksChecked(true);
		}else {
			clockInOut.setIsProjectTasksChecked(false);
		}
		
		if(Constants.STATUS_YES.equalsIgnoreCase(clockInOutConfigDTO.getIsGeneralTaskChecked())) {
			clockInOut.setIsGeneralTaskChecked(true);
		}else {
			clockInOut.setIsGeneralTaskChecked(false);
		}
		
		if(Constants.STATUS_YES.equalsIgnoreCase(clockInOutConfigDTO.getIsGoalChecked())) {
			clockInOut.setIsGoalChecked(true);
		}else {
			clockInOut.setIsGoalChecked(false);
		}
		
		if(Constants.STATUS_YES.equalsIgnoreCase(clockInOutConfigDTO.getIsOtherChecked())) {
			clockInOut.setIsOtherChecked(true);
		}else {
			clockInOut.setIsOtherChecked(false);
		}
		
		if(Constants.STATUS_YES.equalsIgnoreCase(clockInOutConfigDTO.getIsClockInComment())) {
			clockInOut.setIsClockInComment(true);
		}else {
			clockInOut.setIsClockInComment(false);
		}
		
		if(Constants.STATUS_YES.equalsIgnoreCase(clockInOutConfigDTO.getIsBreak())) {
			clockInOut.setIsBreak(true);
		}else {
			clockInOut.setIsBreak(false);
		}
		
		if(Constants.STATUS_YES.equalsIgnoreCase(clockInOutConfigDTO.getIsAutomaticCheckOut())) {
			clockInOut.setIsAutomaticCheckOut(true);
		}else {
			clockInOut.setIsAutomaticCheckOut(false);
		}
		
		clockInOut.setOrgLocationId(loginOrgLocation);
		clockInOut.setCreatedDate(new Date());
		clockInOut.setCreatedBy(userId);
		
		return clockInOutConfigDao.saveClockInOutConfiguration(clockInOut);
		
	}

	@Override
	public ClockInOutConfig getClockInOutConfigBasedOnOrgLocationId(Long orgLocationId) {
		return clockInOutConfigDao.getClockInOutConfigBasedOnOrgLocationId(orgLocationId);
	}

	@Override
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userI)
	{
		return clockInOutConfigDao.getAssignedRequirementListByOrgLocationIDAndUserId(orgLocationId,userType,userI);
	}

	@Override
	public ResponseList<Projects> getProjectsListByUserId(ProjectsDTO projectsDTO, Long userId, String clients, String values, Long orgLocationId, String userRole, Long candidateId)
	{
		return clockInOutConfigDao.getProjectsListByUserId(projectsDTO,userId,clients,values,orgLocationId,userRole,candidateId);
	}

}
