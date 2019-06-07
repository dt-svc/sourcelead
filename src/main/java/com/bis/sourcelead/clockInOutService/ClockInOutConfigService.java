package com.bis.sourcelead.clockInOutService;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.clockInOutDTO.ClockInOutAssociateReportSummaryDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutConfigDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutFiltersDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutUserTimeEntryDTO;
import com.bis.sourcelead.clockInOutEntity.Breaks;
import com.bis.sourcelead.clockInOutEntity.ClockInOut;
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

	public ClockInOut saveClockInOutTime(ClockInOutDTO clockInOutDTO, OrgLocations loginOrgLocation, User loginUser);

	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userI);

	public ResponseList<Projects> getProjectsListByUserId(ProjectsDTO projectsDTO, Long userId, String clients, String values, Long orgLocationId, String userRole, Long candidateId);

	public List<ProjectTasks> getProjectsTasksByProjectId(Long projectId);

	public List<Tasks> getGeneralTasks(Long orgLocationId, Long userId);

	public ClockInOut getExistedClockTime(Long orgLocationId, Long userId, String dateFormat);

	public void updateClockInOutTime(ClockInOutDTO clockInOutDTO, OrgLocations loginOrgLocation, User user);

	public Breaks saveBreakTime(ClockInOutDTO clockInOutDTO, Long userId, OrgLocations loginOrgLocation);

	public Breaks getExistedBreakTime(Long clockInOutId);

	public ResponseList<ClockInOutUserTimeEntryDTO> getUserTimeEntries(Long orgLocationId, Long userId,String dayWisetype, String previousDate, String type);

	public ClockInOutUserTimeEntryDTO getTimeReports(OrgLocations loginOrgLocation, User user);

	public String getWorkingTimeByClockId(OrgLocations loginOrgLocation, User user);

	public ClockInOutDTO getPreviousClockInData(OrgLocations loginOrgLocation, User user);

	public String getTotalClockInOutHours(OrgLocations loginOrgLocation, Long userId, Long projectId, Long projectTaskId);

	public List<ClockInOutAssociateReportSummaryDTO> getAssociateClockInOutReportSummary(ClockInOutFiltersDTO clockInOutFiltersDTO, Date empJoiningDate, OrgLocations orgLocation);

	public List<ClockInOutUserTimeEntryDTO> getListOfClockDataBasedOnDate(String selectedDate, OrgLocations loginOrgLocation, Long userId);

}
