package com.bis.sourcelead.clockInOutServiceImpl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.json.JSONObject;
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
import com.bis.sourcelead.geoLocation.GeoIPv4;
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
	
	@Autowired
	private GeoIPv4 geoIPv4;

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

	@SuppressWarnings({ "deprecation", "static-access" })
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
			
			
			clockInOut.setOrgLocations(loginOrgLocation);
			clockInOut.setUserId(loginUser);
			
			
			ZoneId zoneId = ZoneId.of(loginOrgLocation.getTimezone().getTimeZoneId());
			LocalDateTime ldt = LocalDateTime.now(zoneId);
			Date currentDate = Date.from( ldt.atZone( ZoneId.systemDefault()).toInstant());
			
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			String s = formatter.format(currentDate);
			java.sql.Time timeValue =  new java.sql.Time(formatter.parse(s).getTime());
			
			if(Constants.CLOCK_IN.equalsIgnoreCase(clockInOutDTO.getType())) {
				
				clockInOut.setClockInDate(currentDate);
				clockInOut.setClockInDateTime(currentDate);
				clockInOut.setClockInTime(timeValue);
				clockInOut.setClockInDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), currentDate));
		    	clockInOut.setGeoLocationData(geoIPv4.getLocation(clockInOutDTO.getIpAddress()).toString());
			}
			
			if(Constants.CLOCK_OUT.equalsIgnoreCase(clockInOutDTO.getType())) {
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(clockInOut.getClockInDateTime());
				Date date1 = calendar.getTime();
				Date date2 = currentDate;
				
				
				long diff = date2.getTime() - date1.getTime();
				long diffDays = diff / (24 * 60 * 60 * 1000);
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;

				if(diffDays>0) {
					diffHours = diffDays * 24;
				}
				
				
				clockInOut.setClockOutTime(timeValue);
				clockInOut.setClockOutDate(currentDate);
				clockInOut.setClockOutDateTime(currentDate);
				clockInOut.setClockOutDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), currentDate));
				DecimalFormat df = new DecimalFormat("00");
				clockInOut.setTotalTime(df.format(diffHours)+":"+df.format(diffMinutes)+":"+df.format(diffSeconds));

				
			}
			
			clockInOut.setJobsTaskId(clockInOutDTO.getJobs());
			clockInOut.setProjectId(clockInOutDTO.getProjects());
			clockInOut.setProjectsTaskId(clockInOutDTO.getProjectTasks());
			clockInOut.setGeneralTaskId(clockInOutDTO.getGeneralTasks());
			clockInOut.setComments(clockInOutDTO.getComments());
			
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
	public ClockInOut getExistedClockTime(Long orgLocationId, Long userId,String dateFormat) {
		ClockInOut clk = clockInOutConfigDao.getExistedClockTime(orgLocationId,userId);
		if(clk != null) {
			try {
				clk.setClockInDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat, clk.getClockInDate()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return clk;
	}

	@Override
	public void updateClockInOutTime(ClockInOutDTO clockInOutDTO, OrgLocations loginOrgLocation, User user) {
		ClockInOut clk =  getExistedClockTime(loginOrgLocation.getId(),user.getId(),loginOrgLocation.getDateFormat());
		if(clk != null) {
			saveClockInOutTime(clockInOutDTO,loginOrgLocation,user);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public Breaks saveBreakTime(ClockInOutDTO clockInOutDTO,Long userId,OrgLocations loginOrgLocation) {
		
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
		
			ZoneId zoneId = ZoneId.of(loginOrgLocation.getTimezone().getTimeZoneId());
			LocalDateTime ldt = LocalDateTime.now(zoneId);
			Date currentDate = Date.from( ldt.atZone( ZoneId.systemDefault()).toInstant());
			
			DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
			String s = formatter.format(currentDate);
			java.sql.Time timeValue =  new java.sql.Time(formatter.parse(s).getTime());
			
			if(Constants.PAUSE.equalsIgnoreCase(clockInOutDTO.getType())) {
				breaks.setBreakStartTime(timeValue);
				breaks.setBreakStartDate(currentDate);
				breaks.setBreakStartDateTime(currentDate);
			}
			
			if(Constants.PLAY.equalsIgnoreCase(clockInOutDTO.getType())) {
				
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(breaks.getBreakStartDateTime());
				Date date1 = calendar.getTime();
				Date date2 = currentDate;
				
				
				long diff = date2.getTime() - date1.getTime();
				long diffDays = diff / (24 * 60 * 60 * 1000);
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;

				if(diffDays>0) {
					diffHours = diffDays * 24;
				}
				
				DecimalFormat df = new DecimalFormat("00");
				breaks.setTotalTime(df.format(diffHours)+":"+df.format(diffMinutes)+":"+df.format(diffSeconds));
				breaks.setBreakEndDate(currentDate);
				breaks.setBreakEndDateTime(currentDate);
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
		
		DecimalFormat df = new DecimalFormat("00");

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
				    	String sum = null;
				    	
				    	int brksec = 0;
				    	int brkmin = 0;
				    	int brkhr = 0;
				    	String brksum = null;
				    	
				    	for (ClockInOutUserTimeEntryDTO data : uniqueData.getValue()) {
				    		 if(data.getGeoLocationData() != null) {
				    			JSONObject jsonObj = new JSONObject(data.getGeoLocationData());
				    			if(jsonObj.has("city") && jsonObj.getString("city") != ""){
				    			main.setGeoLocationData(jsonObj.getString("city")+", "+jsonObj.getString("countryName"));
				    			}else {
				    			main.setGeoLocationData(jsonObj.getString("countryName"));
				    			}
				    			}else {
				    			main.setGeoLocationData("--");
				    			}
				    		int indBrksec = 0;
					    	int indBrkmin = 0;
					    	int indBrkhr = 0;
				    		String indBrksum = null;
				    		ClockInOutSubDTO subDto = new ClockInOutSubDTO();
				    		
							List<Breaks> breakList = clockInOutConfigDao.getBreakListByClockInId(data.getId().longValue());
							for (Breaks breaks : breakList) {
								
								String[] str = breaks.getTotalTime().split(":");
								brksec += Integer.parseInt(str[2]);
								indBrksec += Integer.parseInt(str[2]);
								if(brksec>=60) {
									brkmin++;
									brksec = brksec - 60; 
								}
								if(indBrksec>=60) {
									indBrkmin++;
									indBrksec = indBrksec - 60; 
								}
								brkmin += Integer.parseInt(str[1]);
								indBrkmin += Integer.parseInt(str[1]);
								if(brkmin>=60) {
									brkhr++;
									brkmin = brkmin - 60;
								}
								if(indBrkmin>=60) {
									indBrkhr++;
									indBrkmin = indBrkmin - 60;
								}
								brkhr += Integer.parseInt(str[0]);
								indBrkhr += Integer.parseInt(str[0]);
					    		brksum = df.format(brkhr)+":"+df.format(brkmin)+":"+df.format(brksec);
					    		indBrksum = df.format(indBrkhr)+":"+df.format(indBrkmin)+":"+df.format(indBrksec);
							}

							subDto.setIndividualTotBreakTime(indBrksum);
				    		subDto.setClockInTime(data.getClockInTime());
				    		subDto.setClockOutTime(data.getClockOutTime());
				    		subDto.setIndividualNoOfHours(data.getTotalTime());
				    		subDto.setTaskName(data.getProjectTaskName());
				    		subDto.setLocation(null);
				    		subDto.setComments(data.getComments());
				    		subDto.setClockOutDate(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockInDate()));
				    		subListDto.add(subDto);
				    		
				    		main.setComments(data.getComments());
				    		main.setProjectTaskName(data.getProjectTaskName());
				    		main.setClockInDate(data.getClockInDate());
				    		main.setClockInDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockInDate()));
				    		main.setGraphForamatDate(commonUtil.dateStrFromOrgDateFormatter("yyyy-MM-dd", data.getClockInDate()));
				    		main.setClockOutDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockOutDate()));

				    		
				    		String[] str = data.getTotalTime().split(":");
				    		sec += Integer.parseInt(str[2]);
							if(sec>=60) {
								min++;
								sec = sec - 60; 
							}
							min += Integer.parseInt(str[1]);
							if(min>=60) {
								hr++;
								min = min - 60;
							}
							hr += Integer.parseInt(str[0]);
				    		sum = df.format(hr)+":"+df.format(min)+":"+df.format(sec);
				    	}
				    	
				    	main.setTotalTime(sum);
				    	main.setTotalBreakTime(brksum);
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
				    	String sum = null;
				    	
				    	int brksec = 0;
				    	int brkmin = 0;
				    	int brkhr = 0;
				    	String brksum = null;
				    	
				    	for (ClockInOutUserTimeEntryDTO data : uniqueData.getValue()) {
				    		
			    		 if(data.getGeoLocationData() != null) {
			    			JSONObject jsonObj = new JSONObject(data.getGeoLocationData());
			    			if(jsonObj.has("city") && jsonObj.getString("city") != ""){
			    			main.setGeoLocationData(jsonObj.getString("city")+", "+jsonObj.getString("countryName"));
			    			}else {
			    			main.setGeoLocationData(jsonObj.getString("countryName"));
			    			}
			    			}else {
			    			main.setGeoLocationData("--");
			    			}
				    		int indBrksec = 0;
					    	int indBrkmin = 0;
					    	int indBrkhr = 0;
				    		String indBrksum = null;
				    		
				    		ClockInOutSubDTO subDto = new ClockInOutSubDTO();
				    		
				    		
				    		List<Breaks> breakList = clockInOutConfigDao.getBreakListByClockInId(data.getId().longValue());
							for (Breaks breaks : breakList) {
								if(breaks.getTotalTime() != null) {
								String[] str = breaks.getTotalTime().split(":");
								brksec += Integer.parseInt(str[2]);
								indBrksec += Integer.parseInt(str[2]);
								if(brksec>=60) {
									brkmin++;
									brksec = brksec - 60; 
								}
								if(indBrksec>=60) {
									indBrkmin++;
									indBrksec = indBrksec - 60; 
								}
								brkmin += Integer.parseInt(str[1]);
								indBrkmin += Integer.parseInt(str[1]);
								if(brkmin>=60) {
									brkhr++;
									brkmin = brkmin - 60;
								}
								if(indBrkmin>=60) {
									indBrkhr++;
									indBrkmin = indBrkmin - 60;
								}
								brkhr += Integer.parseInt(str[0]);
								indBrkhr += Integer.parseInt(str[0]);
					    		brksum = df.format(brkhr)+":"+df.format(brkmin)+":"+df.format(brksec);
					    		indBrksum = df.format(indBrkhr)+":"+df.format(indBrkmin)+":"+df.format(indBrksec);
							}
							}
				    		
							subDto.setIndividualTotBreakTime(indBrksum);
				    		subDto.setClockInTime(data.getClockInTime());
				    		subDto.setClockOutTime(data.getClockOutTime());
				    		subDto.setIndividualNoOfHours(data.getTotalTime());
				    		subDto.setTaskName(data.getProjectTaskName());
				    		subDto.setLocation(null);
				    		subDto.setComments(data.getComments());
				    		subDto.setClockOutDate(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockInDate()));
				    		subListDto.add(subDto);
				    		
				    		main.setComments(data.getComments());
				    		main.setProjectTaskName(data.getProjectTaskName());
				    		main.setClockInDateStr(uniqueData.getKey());
				    		main.setGraphForamatDate(uniqueData.getKey());
				    		main.setClockOutDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat, data.getClockOutDate()));
						
				    		
				    		String[] str = data.getTotalTime().split(":");
				    		sec += Integer.parseInt(str[2]);
							if(sec>=60) {
								min++;
								sec = sec - 60; 
							}
							min += Integer.parseInt(str[1]);
							if(min>=60) {
								hr++;
								min = min - 60;
							}
							hr += Integer.parseInt(str[0]);
				    		sum = df.format(hr)+":"+df.format(min)+":"+df.format(sec);
				    	}
				    	
				    	main.setTotalTime(sum);
				    	main.setTotalBreakTime(brksum);
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
		
		DecimalFormat df = new DecimalFormat("00");

			ClockInOutUserTimeEntryDTO dto = new ClockInOutUserTimeEntryDTO();
		
		try {
			
			Date[] currentDates = clockInOutUtil.getClockFilterDate("today", loginOrgLocation);
			List<ClockInOutUserTimeEntryDTO> list1 = clockInOutConfigDao.getTimeReports(currentDates,loginOrgLocation,user);
			
			int sec = 0;
	    	int min = 0;
	    	int hr = 0;
	    	String sum = null;
	    	
	    	int brksec = 0;
	    	int brkmin = 0;
	    	int brkhr = 0;
	    	String brksum = null;
			
			for (ClockInOutUserTimeEntryDTO data : list1) {
				
				List<Breaks> breakList = clockInOutConfigDao.getBreakListByClockInId(data.getId().longValue());
				for (Breaks breaks : breakList) {
					if(breaks.getTotalTime() != null) {
						
						
						String[] str = breaks.getTotalTime().split(":");
						brksec += Integer.parseInt(str[2]);
						if(brksec>=60) {
							brkmin++;
							brksec = brksec - 60; 
						}
						brkmin += Integer.parseInt(str[1]);
						if(brkmin>=60) {
							brkhr++;
							brkmin = brkmin - 60;
						}
						brkhr += Integer.parseInt(str[0]);
			    		brksum = df.format(brkhr)+":"+df.format(brkmin)+":"+df.format(brksec);
						
					}
				}
				String[] str = data.getTotalTime().split(":");
	    		sec += Integer.parseInt(str[2]);
	    		if(sec>=60) {
					min++;
					sec = sec - 60; 
				}
	    		min += Integer.parseInt(str[1]);
	    		if(min>=60) {
					hr++;
					min = min - 60;
				}
	    		hr += Integer.parseInt(str[0]);
	    		sum = df.format(hr)+":"+df.format(min)+":"+df.format(sec);

	    	}
			
			String totSum = null;
			if(brksum != null) {
				
				int totMin = 0;
				int totHr = 0;
				String[] str = sum.split(":");
				String[] brkStr = brksum.split(":");
				if(str[2].equals("00")) {
					str[2] = "60";
				}if(brkStr[2].equals("00")) {
					brkStr[2] = "60";
				}
				
				int minSec = 0;
				if(Integer.parseInt(str[2])<Integer.parseInt(brkStr[2])) {
					minSec = Integer.parseInt(str[2])+60;
				}else {
					minSec = Integer.parseInt(str[2]);
				}
				int totSec = minSec - Integer.parseInt(brkStr[2]);
				if(totSec<0) {
					totSec = Math.abs(totSec);
				}
				if(totSec>=60) {
					totMin++;
				}
				int minMin = 0;
				if(str[2].equals("60") || Integer.parseInt(str[2])<Integer.parseInt(brkStr[2])) {
					minMin = Integer.parseInt(str[1]) - 1;
				}else {
					minMin = Integer.parseInt(str[1]);
				}
				 totMin = minMin - Integer.parseInt(brkStr[1]);
				 if(totMin<0) {
					 totMin = Math.abs(totMin);
					}
				 if(totMin>=60) {
					 totHr++;
					}
				totHr = Integer.parseInt(str[0]) - Integer.parseInt(brkStr[0]);
				totSum = df.format(totHr)+":"+df.format(totMin)+":"+df.format(totSec);
			
			
			}else {
				totSum = sum;
			}
			dto.setCurrentTotTime(totSum);
			
			Date[] currentWeek = clockInOutUtil.getClockFilterDate("Last_7_days", loginOrgLocation);
			List<ClockInOutUserTimeEntryDTO> list2 = clockInOutConfigDao.getTimeReports(currentWeek,loginOrgLocation,user);
			
			int sec2 = 0;
	    	int min2 = 0;
	    	int hr2 = 0;
	    	String sum2 = null;
	    	
	    	int brksec2 = 0;
	    	int brkmin2 = 0;
	    	int brkhr2 = 0;
	    	String brksum2 = null;
			
			for (ClockInOutUserTimeEntryDTO data : list2) {
				
				List<Breaks> breakList = clockInOutConfigDao.getBreakListByClockInId(data.getId().longValue());
				for (Breaks breaks : breakList) {
					if(breaks.getTotalTime() != null) {
						
						String[] str = breaks.getTotalTime().split(":");
						brksec2 += Integer.parseInt(str[2]);
						if(brksec2>=60) {
							brkmin2++;
							brksec2 = brksec2 - 60; 
						}
						brkmin2 += Integer.parseInt(str[1]);
						if(brkmin2>=60) {
							brkhr2++;
							brkmin2 = brkmin2 - 60;
						}
						brkhr2 += Integer.parseInt(str[0]);
			    		brksum2 = df.format(brkhr2)+":"+df.format(brkmin2)+":"+df.format(brksec2);
					}
				}
				
	    		
	    		String[] str = data.getTotalTime().split(":");
	    		sec2 += Integer.parseInt(str[2]);
	    		if(sec2>=60) {
					min2++;
					sec2 = sec2 - 60; 
				}
	    		min2 += Integer.parseInt(str[1]);
	    		if(min2>=60) {
					hr2++;
					min2 = min2 - 60;
				}
	    		hr2 += Integer.parseInt(str[0]);
	    		sum2 = df.format(hr2)+":"+df.format(min2)+":"+df.format(sec2);
	    		
	    		
	    	}
			
			String totSum2 = null;
			if(brksum2 != null) {
				
				int totMin = 0;
				int totHr = 0;
				String[] str = sum2.split(":");
				String[] brkStr = brksum2.split(":");
				if(str[2].equals("00")) {
					str[2] = "60";
				}if(brkStr[2].equals("00")) {
					brkStr[2] = "60";
				}
				
				int minSec = 0;
				if(Integer.parseInt(str[2])<Integer.parseInt(brkStr[2])) {
					minSec = Integer.parseInt(str[2])+60;
				}else {
					minSec = Integer.parseInt(str[2]);
				}
				int totSec = minSec - Integer.parseInt(brkStr[2]);
				if(totSec<0) {
					totSec = Math.abs(totSec);
				}
				if(totSec>=60) {
					totMin++;
				}
				int minMin = 0;
				if(str[2].equals("60") || Integer.parseInt(str[2])<Integer.parseInt(brkStr[2])) {
					minMin = Integer.parseInt(str[1]) - 1;
				}else {
					minMin = Integer.parseInt(str[1]);
				}
				 totMin = minMin - Integer.parseInt(brkStr[1]);
				 if(totMin<0) {
					 totMin = Math.abs(totMin);
					}
				 if(totMin>=60) {
					 totHr++;
					}
				totHr = Integer.parseInt(str[0]) - Integer.parseInt(brkStr[0]);
				totSum2 = df.format(totHr)+":"+df.format(totMin)+":"+df.format(totSec);
				
				
			}else {
				totSum2 = sum2;
			}
			dto.setWeekTotTime(totSum2);
			
			
			Date[] currentMonth = clockInOutUtil.getClockFilterDate("current_month", loginOrgLocation);
			List<ClockInOutUserTimeEntryDTO> list3 = clockInOutConfigDao.getTimeReports(currentMonth,loginOrgLocation,user);
			
			
			int sec3 = 0;
	    	int min3 = 0;
	    	int hr3 = 0;
	    	String sum3 = null;
	    	
	    	int brksec3 = 0;
	    	int brkmin3 = 0;
	    	int brkhr3 = 0;
	    	String brksum3 = null;
			
			for (ClockInOutUserTimeEntryDTO data : list3) {
				
				List<Breaks> breakList = clockInOutConfigDao.getBreakListByClockInId(data.getId().longValue());
				for (Breaks breaks : breakList) {
					if(breaks.getTotalTime() != null) {
						String[] str = breaks.getTotalTime().split(":");
						brksec3 += Integer.parseInt(str[2]);
						if(brksec3>=60) {
							brkmin3++;
							brksec3 = brksec3 - 60; 
						}
						brkmin3 += Integer.parseInt(str[1]);
						if(brkmin3>=60) {
							brkhr3++;
							brkmin3 = brkmin3 - 60;
						}
						brkhr3 += Integer.parseInt(str[0]);
			    		brksum3 = df.format(brkhr3)+":"+df.format(brkmin3)+":"+df.format(brksec3);
					}
				}
				
	    		
	    		String[] str = data.getTotalTime().split(":");
	    		sec3 += Integer.parseInt(str[2]);
	    		if(sec3>=60) {
					min3++;
					sec3 = sec3 - 60; 
				}
	    		min3 += Integer.parseInt(str[1]);
	    		if(min3>=60) {
					hr3++;
					min3 = min3 - 60;
				}
	    		hr3 += Integer.parseInt(str[0]);
	    		sum3 = df.format(hr3)+":"+df.format(min3)+":"+df.format(sec3);
	    	}
			
			String totSum3 = null;
			if(brksum3 != null) {

				int totMin = 0;
				int totHr = 0;
				String[] str = sum3.split(":");
				String[] brkStr = brksum3.split(":");
				if(str[2].equals("00")) {
					str[2] = "60";
				}if(brkStr[2].equals("00")) {
					brkStr[2] = "60";
				}
				
				int minSec = 0;
				if(Integer.parseInt(str[2])<Integer.parseInt(brkStr[2])) {
					minSec = Integer.parseInt(str[2])+60;
				}else {
					minSec = Integer.parseInt(str[2]);
				}
				int totSec = minSec - Integer.parseInt(brkStr[2]);
				if(totSec<0) {
					totSec = Math.abs(totSec);
				}
				if(totSec>=60) {
					totMin++;
				}
				int minMin = 0;
				if(str[2].equals("60") || Integer.parseInt(str[2])<Integer.parseInt(brkStr[2])) {
					minMin = Integer.parseInt(str[1]) - 1;
				}else {
					minMin = Integer.parseInt(str[1]);
				}
				 totMin = minMin - Integer.parseInt(brkStr[1]);
				 if(totMin<0) {
					 totMin = Math.abs(totMin);
					}
				 if(totMin>=60) {
					 totHr++;
					}
				totHr = Integer.parseInt(str[0]) - Integer.parseInt(brkStr[0]);
				totSum3 = df.format(totHr)+":"+df.format(totMin)+":"+df.format(totSec);
				

			}else {
				totSum3 = sum3;
			}
			dto.setMonthTotTime(totSum3);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getWorkingTimeByClockId(OrgLocations loc,User user) {
		DecimalFormat df = new DecimalFormat("00");
		
		List<ClockInOut> list = clockInOutConfigDao.getWorkingTimeByClockId(loc,user);
		int sec = 0;
    	int min = 0;
    	int hr = 0;
    	String sum = null;
    	
    	int brksec = 0;
    	int brkmin = 0;
    	int brkhr = 0;
    	String brksum = null;
		for (ClockInOut clockInOut : list) {
			
			List<Breaks> breakList = clockInOutConfigDao.getBreakListByClockInId(clockInOut.getId());
			for (Breaks breaks : breakList) {
				if(breaks.getTotalTime() != null) {
					
					String[] str = breaks.getTotalTime().split(":");
					brksec += Integer.parseInt(str[2]);
					if(brksec>=60) {
						brkmin++;
						brksec = brksec - 60; 
					}
					brkmin += Integer.parseInt(str[1]);
					if(brkmin>=60) {
						brkhr++;
						brkmin = brkmin - 60;
					}
					brkhr += Integer.parseInt(str[0]);
		    		brksum = df.format(brkhr)+":"+df.format(brkmin)+":"+df.format(brksec);

				}
			}
				
			if(clockInOut.getTotalTime() != null) {
				String[] str = clockInOut.getTotalTime().split(":");
				sec += Integer.parseInt(str[2]);
				if(sec>=60) {
					min++;
					sec = sec - 60;
				}
	    		min += Integer.parseInt(str[1]);
	    		if(min>=60) {
	    			hr++;
	    			min = min - 60;
				}
	    		hr += Integer.parseInt(str[0]);
	    		sum = df.format(hr)+":"+df.format(min)+":"+df.format(sec);

			}else {
				
				ZoneId zoneId = ZoneId.of(loc.getTimezone().getTimeZoneId());
				LocalDateTime ldt = LocalDateTime.now(zoneId);
				Date currentDate = Date.from( ldt.atZone( ZoneId.systemDefault()).toInstant());
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(clockInOut.getClockInDateTime());
				Date date1 = calendar.getTime();
				Date date2 = currentDate;
				
				
				long diff = date2.getTime() - date1.getTime();
				long diffDays = diff / (24 * 60 * 60 * 1000);
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;

				if(diffDays>0) {
					diffHours = diffDays * 24;
				}
				
				sec += diffSeconds;
				if(sec>=60) {
					min++;
					sec = sec - 60;
				}
	    		min += diffMinutes;
	    		if(min>=60) {
	    			hr++;
	    			min = min - 60;
				}
	    		hr += diffHours;
//	    		sum = new Time(hr, min, sec);
	    		
	    		sum = df.format(hr)+":"+df.format(min)+":"+df.format(sec);
			}
		}
		
		String totSum = null;
		if(brksum != null) {
			int totMin = 0;
			int totHr = 0;
			String[] str = sum.split(":");
			String[] brkStr = brksum.split(":");
			if(str[2].equals("00")) {
				str[2] = "60";
			}if(brkStr[2].equals("00")) {
				brkStr[2] = "60";
			}
			
			int minSec = 0;
			if(Integer.parseInt(str[2])<Integer.parseInt(brkStr[2])) {
				minSec = Integer.parseInt(str[2])+60;
			}else {
				minSec = Integer.parseInt(str[2]);
			}
			int totSec = minSec - Integer.parseInt(brkStr[2]);
			if(totSec<0) {
				totSec = Math.abs(totSec);
			}
			if(totSec>=60) {
				totMin++;
			}
			int minMin = 0;
			if(str[2].equals("60") || Integer.parseInt(str[2])<Integer.parseInt(brkStr[2])) {
				minMin = Integer.parseInt(str[1]) - 1;
			}else {
				minMin = Integer.parseInt(str[1]);
			}
			 totMin = minMin - Integer.parseInt(brkStr[1]);
			 if(totMin<0) {
				 totMin = Math.abs(totMin);
				}
			 if(totMin>=60) {
				 totHr++;
				}
			totHr = Integer.parseInt(str[0]) - Integer.parseInt(brkStr[0]);
//			totSum = new Time(totHr, totMin, totSec);
			totSum = df.format(totHr)+":"+df.format(totMin)+":"+df.format(totSec);
		}else {
			totSum = sum;
		}
		
		
		return totSum;
	}

	@Override
	public ClockInOutDTO getPreviousClockInData(OrgLocations loginOrgLocation, User user) {
		
		ClockInOutDTO clkDto = new ClockInOutDTO();
		ClockInOut clk = clockInOutConfigDao.getPreviousClockInData(loginOrgLocation,user);
		if(clk != null) {
			clkDto.setPreviousClockInStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), clk.getClockInDate()));
			clkDto.setPreviousClockOutStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), clk.getClockOutDate()));
			clkDto.setPreviousClockInTime(clk.getClockInTime());
			clkDto.setPreviousClockOutTime(clk.getClockOutTime());
		}
		return clkDto;
	}
	

	@Override
	public List<ClockInOutAssociateReportSummaryDTO> getAssociateClockInOutReportSummary(ClockInOutFiltersDTO clockInOutFiltersDTO, Date empJoiningDate, OrgLocations orgLocation) {
		// TODO Auto-generated method stub

		List<ClockInOutAssociateReportSummaryDTO> getAssociateReportList = clockInOutConfigDao.getAssociateClockInOutReportSummary(clockInOutFiltersDTO, orgLocation, null);
		List<ClockInOutAssociateReportSummaryDTO> mainList = new ArrayList<>();
		
		try {
			
			List<String> dates = new ArrayList<>();
			dates.add("Employee Name");
			Date[] dateArray = null;
			if("previous".equalsIgnoreCase(clockInOutFiltersDTO.getType())) {
				dateArray = clockInOutUtil.getClockFilterPreviousDates(clockInOutFiltersDTO.getCurrentDate(),null,orgLocation,clockInOutFiltersDTO.getDayWisetype());
				dates = clockInOutUtil.getAllDatesBetweenTwoDates(orgLocation,dateArray);

			}else if("next".equalsIgnoreCase(clockInOutFiltersDTO.getType())) {
				dateArray = clockInOutUtil.getClockFilterNextDates(clockInOutFiltersDTO.getCurrentDate(),null,orgLocation,clockInOutFiltersDTO.getDayWisetype());
				dates = clockInOutUtil.getAllDatesBetweenTwoDates(orgLocation,dateArray);
			}else {
				if(Constants.TO_DAY.equalsIgnoreCase(clockInOutFiltersDTO.getDayWisetype())) {
					
					dates.add(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date()));
					
				}else if(Constants.REPORT_FILTER_LAST_7_DAYS.equalsIgnoreCase(clockInOutFiltersDTO.getDayWisetype())) {
					
					dateArray = clockInOutUtil.currentWeek(orgLocation);
					dates = clockInOutUtil.getAllDatesBetweenTwoDates(orgLocation,dateArray);
			
				}else if(Constants.REPORT_FILTER_CURRENT_MONTH.equalsIgnoreCase(clockInOutFiltersDTO.getDayWisetype())) {
					
					dateArray = clockInOutUtil.currentMonthDates(orgLocation);
					dates = clockInOutUtil.getAllDatesBetweenTwoDates(orgLocation,dateArray);
			
				}else if(Constants.REPORT_FILTER_CURRENT_YEAR.equalsIgnoreCase(clockInOutFiltersDTO.getDayWisetype())) {
					
					dateArray = clockInOutUtil.currentYearDates();
					dates = clockInOutUtil.getAllMonthsOfCurrentYear(orgLocation,dateArray);
			
				}
			}
			
			ClockInOutAssociateReportSummaryDTO mainDto = null;
			
				Map<BigInteger, List<ClockInOutAssociateReportSummaryDTO>> result = getAssociateReportList.stream().collect(Collectors.groupingBy(ClockInOutAssociateReportSummaryDTO::getUserId));
				
				if(result != null && result.size() > 0) {
					
					for (Entry<BigInteger, List<ClockInOutAssociateReportSummaryDTO>> entry : result.entrySet()) {
						mainDto = new ClockInOutAssociateReportSummaryDTO();
						Map<String, String> map = new HashMap<>();
						
						String sum = null;
						
						for (ClockInOutAssociateReportSummaryDTO clock : entry.getValue()) {
							StringBuilder sb = new StringBuilder();
							
							for(String s : dates) {
								List<ClockInOutAssociateReportSummaryDTO> dayClockInList = null;
								if(!s.equalsIgnoreCase("Employee Name")) {
									if(!"Current_Year".equalsIgnoreCase(clockInOutFiltersDTO.getDayWisetype())) {
										dayClockInList = entry.getValue().stream()
												.filter(clco->clco.getClockInDate().equals(commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), s))).collect(Collectors.toList());
								}else {
										dayClockInList = entry.getValue().stream()
												.filter(clco->clco.getYearDate().equals(s)).collect(Collectors.toList());
								}
								
								}
								
						    	if(dayClockInList != null && dayClockInList.size() > 0) {
						    		sum = getTotalTimeSumForSummaryReport(dayClockInList);
						    		map.put(s, sum);
						    	}else {
									map.put(s, "--");
						    	}
								
							}
							
							
							if(clock.getLastName() != null) {
								map.put("Employee Name", sb.append(clock.getFirstName()).append(" ").append(clock.getLastName()).toString());
							}else {
								map.put("Employee Name", clock.getFirstName());
							}
							
							
							if(!"Current_Year".equalsIgnoreCase(clockInOutFiltersDTO.getDayWisetype())) {
								mainDto.setGraphFormatDate(commonUtil.dateStrFromOrgDateFormatter("yyyy-MM-dd", clock.getClockInDate()));
							}else {
								mainDto.setGraphFormatDate(commonUtil.dateStrFromOrgDateFormatter("yyyy-MM", clock.getClockInDate()));

							}
						
		
						}
						mainDto.setHeaderDates(dates);
						mainDto.setMapData(map);
						mainDto.setUserId(entry.getKey());
						
						if(sum != null) {
							String tTime [] = sum.toString().split(":");
			                StringBuilder sb = new StringBuilder();
			                sb.append(tTime[0]).append(".").append(tTime[1]);
			                mainDto.setGraphTotalTime(sb.toString());
						}
						
						mainList.add(mainDto);
					}
					
				}else {
					mainDto = new ClockInOutAssociateReportSummaryDTO();
					mainDto.setHeaderDates(dates);
					mainDto.setIsDataAvailable("NO");
					mainList.add(mainDto);
				}
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mainList;
	}

	private String getTotalTimeSumForSummaryReport(List<ClockInOutAssociateReportSummaryDTO> dayClockInList) {
		
		int sec = 0;
    	int min = 0;
    	int hr = 0;
    	String sum = null;
    	DecimalFormat df = new DecimalFormat("00");
		for (ClockInOutAssociateReportSummaryDTO clk : dayClockInList) {
			
		    String[] str = clk.getTotalTime().split(":");
    		sec += Integer.parseInt(str[2]);
			if(sec>=60) {
				min++;
				sec = sec - 60; 
			}
			min += Integer.parseInt(str[1]);
			if(min>=60) {
				hr++;
				min = min - 60;
			}
			hr += Integer.parseInt(str[0]);
    		sum = df.format(hr)+":"+df.format(min)+":"+df.format(sec);
    		
		   }
		return sum;
	}

	@Override
	public List<ClockInOut> getListOfClockDataBasedOnDate(String selectedDate, OrgLocations loginOrgLocation, Long userId) {
		return clockInOutConfigDao.getListOfClockDataBasedOnDate(selectedDate,loginOrgLocation,userId);
	}
}
