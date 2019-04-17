package com.bis.sourcelead.clockInOutServiceImpl;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.clockInOutDTO.ClockInOutConfigDTO;
import com.bis.sourcelead.clockInOutDTO.ClockInOutDTO;
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
			
			if(Constants.CLOCK_IN.equalsIgnoreCase(clockInOutDTO.getType()))
				clockInOut.setClockInTime(timeValue);
				clockInOut.setClockInDate(new Date());
			
			if(Constants.CLOCK_OUT.equalsIgnoreCase(clockInOutDTO.getType())) {
				
				java.sql.Time totalTime =  new java.sql.Time(formatter.parse(clockInOutDTO.getTotalTime()).getTime());
				clockInOut.setTotalClockTime(totalTime);
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
					
				breaks.setBreakEndTime(timeValue);
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
		    		
		    		main.setClockInDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockInDate()));
		    		main.setClockOutDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockOutDate()));
				
		    		sec += data.getTotalTime().getSeconds();
		    		min += data.getTotalTime().getMinutes();
		    		hr += data.getTotalTime().getHours();
		    		sum = new Time(hr, min, sec);
		    	}
		    	main.setTotalTime(sum);
		    	main.setSubList(subListDto);
		    	mainList.add(main);
				
			}
		    resMainList.setData(mainList);
		    
			return resMainList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	

}
