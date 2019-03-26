package com.bis.sourcelead.clockInOutService;

import java.util.List;

import com.bis.sourcelead.clockInOutDTO.ClockInOutConfigDTO;
import com.bis.sourcelead.clockInOutEntity.ClockInOutConfig;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;

public interface ClockInOutConfigService {
	
	public ClockInOutConfig saveClockInOutConfiguration(ClockInOutConfigDTO clockInOutConfigDTO, OrgLocations loginOrgLocation, Long userId);

	public ClockInOutConfig getClockInOutConfigBasedOnOrgLocationId(Long orgLocationId);

	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userI);

	public ResponseList<Projects> getProjectsListByUserId(ProjectsDTO projectsDTO, Long userId, String clients, String values, Long orgLocationId, String userRole, Long candidateId);
}
