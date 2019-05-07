package com.bis.sourcelead.clockInOutServiceImpl;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.clockInOutDTO.ClockInOutAssociateReportSummaryDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutConfigDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutFiltersDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutSubDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutUserTimeEntryDTO;
import com.bis.sourcelead.clockInOutDao.ClockInOutConfigDao;
import com.bis.sourcelead.clockInOutEntity.Breaks;
import com.bis.sourcelead.clockInOutEntity.ClockInOut;
import com.bis.sourcelead.clockInOutEntity.ClockInOutConfig;
import com.bis.sourcelead.clockInOutService.ClockInOutConfigService;
import com.bis.sourcelead.clockInOutUtil.ClockInOutUtil;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class ClockInOutConfigServiceImpl implements ClockInOutConfigService {
	
	
	@Autowired
	private ClockInOutConfigDao clockInOutConfigDao;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	private ClockInOutUtil clockInOutUtil;
	
	@Autowired
	private CommonUtil commonUtil;

	@Override
	public ClockInOutConfig saveClockInOutConfiguration(ClockInOutConfigDTO clockInOutConfigDTO, OrgLocations loginOrgLocation,Long userId) {
		
		ClockInOutConfig clockInOut = null;
		
		if(clockInOutConfigDTO.getClockInOutId() != null) {
			clockInOut = clockInOutConfigDao.getClockInOutConfigBasedOnId(clockInOutConfigDTO.getClockInOutId());
			clockInOut.setUpdatedDate(new Date());
			clockInOut.setUpdatedBy(userId);
		}else {
			clockInOut = new ClockInOutConfig();
			clockInOut.setCreatedDate(new Date());
			clockInOut.setCreatedBy(userId);
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
		
		return clockInOutConfigDao.saveClockInOutConfiguration(clockInOut);
		
	}

	@Override
	public ClockInOutConfig getClockInOutConfigBasedOnOrgLocationId(Long orgLocationId) {
		return clockInOutConfigDao.getClockInOutConfigBasedOnOrgLocationId(orgLocationId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ClockInOut saveClockInOutTime(ClockInOutDTO clockInOutDTO,OrgLocations loginOrgLocation, User loginUser) {
		
		ClockInOut clockInOut = null;
		try {
			
			if(clockInOutDTO.getClockInOutId() != null && clockInOutDTO.getClockInOutId() > 0) {
				clockInOut = clockInOutConfigDao.getClockInOutById(clockInOutDTO.getClockInOutId());
				clockInOut.setUpdatedDate(new Date());
				clockInOut.setUpdatedBy(loginUser.getId());
			}else {
				clockInOut = new ClockInOut();
				clockInOut.setCreatedDate(new Date());
				clockInOut.setCreatedBy(loginUser.getId());
			}
			
			java.sql.Time timeValue = null;
			 
			
			clockInOut.setOrgLocations(loginOrgLocation);
			clockInOut.setUserId(loginUser);
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			String s = formatter.format(Calendar.getInstance().getTime());
			timeValue =  new java.sql.Time(formatter.parse(s).getTime());
			
			if(Constants.CLOCK_IN.equalsIgnoreCase(clockInOutDTO.getType())) {
				clockInOut.setClockInTime(timeValue);
				clockInOut.setClockInDate(new Date());
			}
			
			if(Constants.CLOCK_OUT.equalsIgnoreCase(clockInOutDTO.getType())) {
				
				int sec = timeValue.getSeconds() - clockInOut.getClockInTime().getSeconds();
				int min = timeValue.getMinutes() - clockInOut.getClockInTime().getMinutes();
				int hr = timeValue.getHours() - clockInOut.getClockInTime().getHours();
				Time totTime = new Time(hr, min, sec);
				
				clockInOut.setTotalClockTime(totTime);
				clockInOut.setClockOutTime(timeValue);
				clockInOut.setClockOutDate(new Date());
				
			}
			
			clockInOut.setJobsTaskId(clockInOutDTO.getJobs());
			clockInOut.setProjectId(clockInOutDTO.getProjects());
			clockInOut.setProjectsTaskId(clockInOutDTO.getProjectTasks());
			clockInOut.setGeneralTaskId(clockInOutDTO.getGeneralTasks());
			
			clockInOut = clockInOutConfigDao.saveClockInOutTime(clockInOut);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clockInOut;
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

	@Override
	public List<ProjectTasks> getProjectsTasksByProjectId(Long projectId) {
		return clockInOutConfigDao.getProjectsTasksByProjectId(projectId);
	}

	@Override
	public List<Tasks> getGeneralTasks(Long orgLocationId, Long userId) {
		return clockInOutConfigDao.getGeneralTasks(orgLocationId,userId);
	}

	@Override
	public ClockInOut getExistedClockTime(Long orgLocationId, Long userId) {
		return clockInOutConfigDao.getExistedClockTime(orgLocationId,userId);
	}

	@Override
	public void updateClockInOutTime(ClockInOutDTO clockInOutDTO, OrgLocations loginOrgLocation, User user) {
		ClockInOut clk =  getExistedClockTime(loginOrgLocation.getId(),user.getId());
		if(clk != null) {
			saveClockInOutTime(clockInOutDTO,loginOrgLocation,user);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public Breaks saveBreakTime(ClockInOutDTO clockInOutDTO,Long userId) {
		
		Breaks breaks = null;
		try {
			
			if(clockInOutDTO.getBreakId() != null) {
				breaks = clockInOutConfigDao.getBreakById(clockInOutDTO.getBreakId());
				breaks.setUpdatedDate(new Date());
				breaks.setUpdatedBy(userId);
			}else {
				breaks = new Breaks();
				breaks.setCreatedDate(new Date());
				breaks.setCreatedBy(userId);
			}
			
			breaks.setClockInOutId(clockInOutDTO.getClockInOutId());
			breaks.setBreakDate(new Date());
			
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			String s = formatter.format(Calendar.getInstance().getTime());
			java.sql.Time timeValue =  new java.sql.Time(formatter.parse(s).getTime());
			
			if(Constants.PAUSE.equalsIgnoreCase(clockInOutDTO.getType()))
				breaks.setBreakStartTime(timeValue);
			
			if(Constants.PLAY.equalsIgnoreCase(clockInOutDTO.getType())) {
				
				int sec = timeValue.getSeconds() - breaks.getBreakStartTime().getSeconds();
				int min = timeValue.getMinutes() - breaks.getBreakStartTime().getMinutes();
				int hr = timeValue.getHours() - breaks.getBreakStartTime().getHours();
				Time totTime = new Time(hr, min, sec);
				
				breaks.setBreakEndTime(timeValue);
				breaks.setTotalBreakTime(totTime);
					
			}
				
			breaks = clockInOutConfigDao.saveBreakTime(breaks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return breaks;
		
	}

	@Override
	public Breaks getExistedBreakTime(Long clockInOutId) {
		return clockInOutConfigDao.getExistedBreakTime(clockInOutId);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ResponseList<ClockInOutUserTimeEntryDTO> getUserTimeEntries(Long orgLocationId, Long userId,String dateFormat,String dayWisetype,String currentDate,String type) {
		
		try {
			OrgLocations location = orgLocationService.getOrgLocations(orgLocationId);
			Date[] dates = null;
			
			if("previous".equalsIgnoreCase(type)) {
				 dates = clockInOutUtil.getClockFilterPreviousDates(currentDate,null,location,dayWisetype);
			}else if("next".equalsIgnoreCase(type)) {
				 dates = clockInOutUtil.getClockFilterNextDates(currentDate,null,location,dayWisetype);
			}else {
				 dates = clockInOutUtil.getClockFilterDate(dayWisetype, location);
			}
			
			ResponseList<ClockInOutUserTimeEntryDTO> resList = clockInOutConfigDao.getUserTimeEntries(orgLocationId,userId,dates);
			
			ResponseList<ClockInOutUserTimeEntryDTO> resMainList = new ResponseList<>();
			List<ClockInOutUserTimeEntryDTO> mainList = new ArrayList<>();
			
			if(!"Current_Year".equalsIgnoreCase(type)) {
				  Map<java.sql.Date, List<ClockInOutUserTimeEntryDTO>> uniqueList = resList.getData().stream().collect(Collectors.groupingBy(ClockInOutUserTimeEntryDTO::getClockInDate));
				    for (Entry<java.sql.Date, List<ClockInOutUserTimeEntryDTO>> uniqueData : uniqueList.entrySet()) {
				    	
				    	List<ClockInOutSubDTO> subListDto = new ArrayList<>();
				    	ClockInOutUserTimeEntryDTO main = new ClockInOutUserTimeEntryDTO();
				    	
				    	int sec = 0;
				    	int min = 0;
				    	int hr = 0;
				    	Time sum = null;
				    	
				    	for (ClockInOutUserTimeEntryDTO data : uniqueData.getValue()) {
				    		
				    		ClockInOutSubDTO subDto = new ClockInOutSubDTO();
				    		subDto.setClockInTime(data.getClockInTime());
				    		subDto.setClockOutTime(data.getClockOutTime());
				    		subDto.setIndividualNoOfHours(data.getTotalTime());
				    		subDto.setTaskName(data.getProjectTaskName());
				    		subDto.setLocation(null);
				    		subDto.setComments(data.getComments());
				    		subDto.setClockOutDate(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockInDate()));
				    		subListDto.add(subDto);
				    		
				    		main.setClockInDate(data.getClockInDate());
				    		main.setClockInDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockInDate()));
				    		main.setGraphForamatDate(commonUtil.dateStrFromOrgDateFormatter("yyyy-MM-dd", data.getClockInDate()));
				    		main.setClockOutDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockOutDate()));
						
				    		sec += data.getTotalTime().getSeconds();
				    		min += data.getTotalTime().getMinutes();
				    		hr += data.getTotalTime().getHours();
				    		sum = new Time(hr, min, sec);
				    	}
				    	
				    	main.setTotalTime(sum);
				    	String tTime [] = sum.toString().split(":");
		                StringBuilder sb = new StringBuilder();
		                sb.append(tTime[0]).append(".").append(tTime[1]);
				    	main.setGraphTotalTime(sb.toString());
				    	main.setSubList(subListDto);
				    	main.setType(type);
				    	mainList.add(main);
				    	Collections.sort(mainList, new Comparator<ClockInOutUserTimeEntryDTO>() {
				    	    public int compare(ClockInOutUserTimeEntryDTO m1, ClockInOutUserTimeEntryDTO m2) {
				    	        return m1.getClockInDate().compareTo(m2.getClockInDate());
				    	    }
				    	});
						
					}	
			}else {
				 Map<String, List<ClockInOutUserTimeEntryDTO>> uniqueList = resList.getData().stream().collect(Collectors.groupingBy(ClockInOutUserTimeEntryDTO::getYearMonth));
				    for (Entry<String, List<ClockInOutUserTimeEntryDTO>> uniqueData : uniqueList.entrySet()) {
				    	
				    	List<ClockInOutSubDTO> subListDto = new ArrayList<>();
				    	ClockInOutUserTimeEntryDTO main = new ClockInOutUserTimeEntryDTO();
				    	
				    	int sec = 0;
				    	int min = 0;
				    	int hr = 0;
				    	Time sum = null;
				    	
				    	for (ClockInOutUserTimeEntryDTO data : uniqueData.getValue()) {
				    		
				    		ClockInOutSubDTO subDto = new ClockInOutSubDTO();
				    		subDto.setClockInTime(data.getClockInTime());
				    		subDto.setClockOutTime(data.getClockOutTime());
				    		subDto.setIndividualNoOfHours(data.getTotalTime());
				    		subDto.setTaskName(data.getProjectTaskName());
				    		subDto.setLocation(null);
				    		subDto.setComments(data.getComments());
				    		subDto.setClockOutDate(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockInDate()));
				    		subListDto.add(subDto);
				    		
				    		main.setClockInDateStr(uniqueData.getKey());
				    		main.setGraphForamatDate(uniqueData.getKey());
				    		main.setClockOutDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockOutDate()));
						
				    		sec += data.getTotalTime().getSeconds();
				    		min += data.getTotalTime().getMinutes();
				    		hr += data.getTotalTime().getHours();
				    		sum = new Time(hr, min, sec);
				    	}
				    	
				    	main.setTotalTime(sum);
				    	String tTime [] = sum.toString().split(":");
		                StringBuilder sb = new StringBuilder();
		                sb.append(tTime[0]).append(".").append(tTime[1]);
				    	main.setGraphTotalTime(sb.toString());
				    	main.setSubList(subListDto);
				    	main.setType(type);
				    	mainList.add(main);
						
					}	
			}
		  
		    resMainList.setData(mainList);
		    resMainList.setRecordsTotal(mainList.size());
		    resMainList.setRecordsFiltered(mainList.size());
			return resMainList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public ClockInOutUserTimeEntryDTO getTimeReports(OrgLocations loginOrgLocation, User user) {
		
			ClockInOutUserTimeEntryDTO dto = new ClockInOutUserTimeEntryDTO();
		
		try {
			
			Date[] currentDates = clockInOutUtil.getClockFilterDate("today", loginOrgLocation);
			List<ClockInOutUserTimeEntryDTO> list1 = clockInOutConfigDao.getTimeReports(currentDates,loginOrgLocation,user);
			
			int sec = 0;
	    	int min = 0;
	    	int hr = 0;
	    	Time sum = null;
	    	
	    	int brksec = 0;
	    	int brkmin = 0;
	    	int brkhr = 0;
	    	Time brksum = null;
			
			for (ClockInOutUserTimeEntryDTO data : list1) {
				
				List<Breaks> breakList = clockInOutConfigDao.getBreakListByClockInId(data.getId().longValue());
				for (Breaks breaks : breakList) {
					if(breaks.getTotalBreakTime() != null) {
						brksec += breaks.getTotalBreakTime().getSeconds();
						brkmin += breaks.getTotalBreakTime().getMinutes();
						brkhr += breaks.getTotalBreakTime().getHours();
						brksum = new Time(brkhr, brkmin, brksec);
					}
				}
				
	    		sec += data.getTotalTime().getSeconds();
	    		min += data.getTotalTime().getMinutes();
	    		hr += data.getTotalTime().getHours();
	    		sum = new Time(hr, min, sec);
	    	}
			
			Time totSum = null;
			if(brksum != null) {
				int totSec = sum.getSeconds() - brksum.getSeconds();
				int totMin = sum.getMinutes() - brksum.getMinutes();
				int totHr = sum.getHours() - brksum.getHours();
				totSum = new Time(totHr, totMin, totSec);
			}
			dto.setCurrentTotTime(totSum);
			
			Date[] currentWeek = clockInOutUtil.getClockFilterDate("Last_7_days", loginOrgLocation);
			List<ClockInOutUserTimeEntryDTO> list2 = clockInOutConfigDao.getTimeReports(currentWeek,loginOrgLocation,user);
			
			int sec2 = 0;
	    	int min2 = 0;
	    	int hr2 = 0;
	    	Time sum2 = null;
	    	
	    	int brksec2 = 0;
	    	int brkmin2 = 0;
	    	int brkhr2 = 0;
	    	Time brksum2 = null;
			
			for (ClockInOutUserTimeEntryDTO data : list2) {
				
				List<Breaks> breakList = clockInOutConfigDao.getBreakListByClockInId(data.getId().longValue());
				for (Breaks breaks : breakList) {
					if(breaks.getTotalBreakTime() != null) {
						brksec2 += breaks.getTotalBreakTime().getSeconds();
						brkmin2 += breaks.getTotalBreakTime().getMinutes();
						brkhr2 += breaks.getTotalBreakTime().getHours();
						brksum2 = new Time(brkhr2, brkmin2, brksec2);
					}
				}
				
	    		sec2 += data.getTotalTime().getSeconds();
	    		min2 += data.getTotalTime().getMinutes();
	    		hr2 += data.getTotalTime().getHours();
	    		sum2 = new Time(hr2, min2, sec2);
	    	}
			
			Time totSum2 = null;
			if(brksum2 != null) {
				int totSec = sum2.getSeconds() - brksum2.getSeconds();
				int totMin = sum2.getMinutes() - brksum2.getMinutes();
				int totHr = sum2.getHours() - brksum2.getHours();
				totSum2 = new Time(totHr, totMin, totSec);
			}
			dto.setWeekTotTime(totSum2);
			
			
			Date[] currentMonth = clockInOutUtil.getClockFilterDate("current_month", loginOrgLocation);
			List<ClockInOutUserTimeEntryDTO> list3 = clockInOutConfigDao.getTimeReports(currentMonth,loginOrgLocation,user);
			
			
			int sec3 = 0;
	    	int min3 = 0;
	    	int hr3 = 0;
	    	Time sum3 = null;
	    	
	    	int brksec3 = 0;
	    	int brkmin3 = 0;
	    	int brkhr3 = 0;
	    	Time brksum3 = null;
			
			for (ClockInOutUserTimeEntryDTO data : list3) {
				
				List<Breaks> breakList = clockInOutConfigDao.getBreakListByClockInId(data.getId().longValue());
				for (Breaks breaks : breakList) {
					if(breaks.getTotalBreakTime() != null) {
						brksec3 += breaks.getTotalBreakTime().getSeconds();
						brkmin3 += breaks.getTotalBreakTime().getMinutes();
						brkhr3 += breaks.getTotalBreakTime().getHours();
						brksum3 = new Time(brkhr3, brkmin3, brksec3);
					}
				}
				
	    		sec3 += data.getTotalTime().getSeconds();
	    		min3 += data.getTotalTime().getMinutes();
	    		hr3 += data.getTotalTime().getHours();
	    		sum3 = new Time(hr3, min3, sec3);
	    	}
			
			Time totSum3 = null;
			if(brksum3 != null) {
				int totSec = sum3.getSeconds() - brksum3.getSeconds();
				int totMin = sum3.getMinutes() - brksum3.getMinutes();
				int totHr = sum3.getHours() - brksum3.getHours();
				totSum3 = new Time(totHr, totMin, totSec);
			}
			dto.setMonthTotTime(totSum3);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Time getWorkingTimeByClockId(OrgLocations loc,User user) {
		List<ClockInOut> list = clockInOutConfigDao.getWorkingTimeByClockId(loc,user);
		int sec = 0;
    	int min = 0;
    	int hr = 0;
    	Time sum = null;
    	
    	int brksec = 0;
    	int brkmin = 0;
    	int brkhr = 0;
    	Time brksum = null;
		for (ClockInOut clockInOut : list) {
			
			List<Breaks> breakList = clockInOutConfigDao.getBreakListByClockInId(clockInOut.getId());
			for (Breaks breaks : breakList) {
				if(breaks.getTotalBreakTime() != null) {
					brksec += breaks.getTotalBreakTime().getSeconds();
					brkmin += breaks.getTotalBreakTime().getMinutes();
					brkhr += breaks.getTotalBreakTime().getHours();
					brksum = new Time(brkhr, brkmin, brksec);
				}
			}
			
			if(clockInOut.getTotalClockTime() != null) {
				sec += clockInOut.getTotalClockTime().getSeconds();
	    		min += clockInOut.getTotalClockTime().getMinutes();
	    		hr += clockInOut.getTotalClockTime().getHours();
	    		sum = new Time(hr, min, sec);
			}else {
				
				int sec1 = new Date().getSeconds() - clockInOut.getClockInTime().getSeconds();
				int min1 = new Date().getMinutes() - clockInOut.getClockInTime().getMinutes();
				int hr1 = new Date().getHours() - clockInOut.getClockInTime().getHours();
				Time currentTime = new Time(hr1, min1, sec1);
				
				sec += currentTime.getSeconds();
	    		min += currentTime.getMinutes();
	    		hr += currentTime.getHours();
	    		sum = new Time(hr, min, sec);
			}
		}
		
		Time totSum = null;
		if(brksum != null) {
			int totSec = sum.getSeconds() - brksum.getSeconds();
			int totMin = sum.getMinutes() - brksum.getMinutes();
			int totHr = sum.getHours() - brksum.getHours();
			totSum = new Time(totHr, totMin, totSec);
		}else {
			totSum = sum;
		}
		
		
		return totSum;
	}
	

	@Override
	public List<ClockInOutAssociateReportSummaryDTO> getAssociateClockInOutReportSummary(ClockInOutFiltersDTO clockInOutFiltersDTO, OrgLocations orgLocation) {
		// TODO Auto-generated method stub
		 List<ClockInOutAssociateReportSummaryDTO> getAssociateReportList = clockInOutConfigDao.getAssociateClockInOutReportSummary(clockInOutFiltersDTO, orgLocation.getId());

		 /*
		 List<ClockInOutAssociateReportSummaryDTO> associateReportList = new ArrayList<>();
		 ClockInOutAssociateReportSummaryDTO associateReportDto = new ClockInOutAssociateReportSummaryDTO();
		 
		 for (ClockInOutAssociateReportSummaryDTO associateReport : getAssociateReportList) {
			 associateReportDto.setId(associateReport.getId());
			 associateReportDto.setUserId(associateReport.getUserId());
			 associateReportDto.setRoleId(associateReport.getRoleId());
			 associateReportDto.setDepId(associateReport.getDepId());
			 associateReportDto.setClockInTime(associateReport.getClockInTime());
			 associateReportDto.setClockOutTime(associateReport.getClockOutTime());
			 associateReportDto.setTotalTime(associateReport.getTotalTime());
			 associateReportDto.setClockInDate(associateReport.getClockInDate());
			 associateReportDto.setClockOutDate(associateReport.getClockOutDate());
			 associateReportDto.setYearDate(associateReport.getYearDate());
			 associateReportDto.setFirstName(associateReport.getFirstName());
			 associateReportDto.setLastName(associateReport.getLastName());
			 associateReportDto.setRoleType(associateReport.getRoleType());
			 associateReportDto.setComments(associateReport.getComments());
			 
			 if("Last_7_days".equals(clockInOutFiltersDTO.getType())) {
				 
			 }
			 
			  
			 associateReportList.add(associateReportDto);
		 }
		 */

		return getAssociateReportList;
	}

}
