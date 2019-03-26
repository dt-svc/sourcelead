package com.bis.sourcelead.clockInOutDao;

import java.util.List;

import com.bis.sourcelead.clockInOutEntity.ClockInOutConfig;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;

public interface ClockInOutConfigDao {

	public ClockInOutConfig saveClockInOutConfiguration(ClockInOutConfig clockInOut);

	public ClockInOutConfig getClockInOutConfigBasedOnOrgLocationId(Long orgLocationId);

	public ClockInOutConfig getClockInOutConfigBasedOnId(Long clockInOutId);

	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId);

	public ResponseList<Projects> getProjectsListByUserId(ProjectsDTO projectsDTO, Long userId, String clients, String values, Long orgLocationId, String userRole, Long candidateId);

}
