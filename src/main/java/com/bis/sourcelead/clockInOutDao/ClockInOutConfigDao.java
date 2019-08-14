package com.bis.sourcelead.clockInOutDao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.clockInOutDTO.ClockInOutAssociateReportSummaryDTO;
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


public interface ClockInOutConfigDao {

	public ClockInOutConfig saveClockInOutConfiguration(ClockInOutConfig clockInOut);

	public ClockInOutConfig getClockInOutConfigBasedOnOrgLocationId(Long orgLocationId);

	public ClockInOutConfig getClockInOutConfigBasedOnId(Long clockInOutId);

	public ClockInOut saveClockInOutTime(ClockInOut clockInOut);
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId);

	public ResponseList<Projects> getProjectsListByUserId(ProjectsDTO projectsDTO, Long userId, String clients, String values, Long orgLocationId, String userRole, Long candidateId);

	public List<ProjectTasks> getProjectsTasksByProjectId(Long projectId);

	public List<Tasks> getGeneralTasks(Long orgLocationId, Long userId);

	public ClockInOut getExistedClockTime(Long orgLocationId, Long userId);

	public ClockInOut getClockInOutById(Long clockInOutId);

	public Breaks saveBreakTime(Breaks breaks);

	public Breaks getBreakById(Long breakId);

	public Breaks getExistedBreakTime(Long clockInOutId);

	public ResponseList<ClockInOutUserTimeEntryDTO> getUserTimeEntries(Long orgLocationId, Long userId, Date[] dates);

	public List<ClockInOutUserTimeEntryDTO> getTimeReports(Date[] dates, OrgLocations loginOrgLocation, User user);

	public List<ClockInOut> getWorkingTimeByClockId(OrgLocations loc, User user);

	public List<Breaks> getBreakListByClockInId(Long id);
	
	public List<ClockInOutAssociateReportSummaryDTO> getAssociateClockInOutReportSummary(ClockInOutFiltersDTO clockInOutFiltersDTO, OrgLocations orgLocation, Date sendDate);

	public ClockInOut getPreviousClockInData(OrgLocations loginOrgLocation, User user);

	public List<ClockInOut> getListOfClockDataBasedOnDate(String selectedDate, OrgLocations loginOrgLocation, Long userId);


}
