package com.bis.sourcelead.clockInOutService;

import java.util.List;

import com.bis.sourcelead.clockInOutDTO.ClockInOutConfigDTO;
import com.bis.sourcelead.clockInOutEntity.ClockInOutConfig;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;

public interface ClockInOutConfigService {
	
	public ClockInOutConfig saveClockInOutConfiguration(ClockInOutConfigDTO clockInOutConfigDTO, OrgLocations loginOrgLocation, Long userId);

	public ClockInOutConfig getClockInOutConfigBasedOnOrgLocationId(Long orgLocationId);

	public void saveClockInOutTime(OrgLocations loginOrgLocation, User loginUser);

	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userI);

	public ResponseList<Projects> getProjectsListByUserId(ProjectsDTO projectsDTO, Long userId, String clients, String values, Long orgLocationId, String userRole, Long candidateId);

	public List<ProjectTasks> getProjectsTasksByProjectId(Long projectId);

	public List<Tasks> getGeneralTasks(Long orgLocationId, Long userId);

}
