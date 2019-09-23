package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.HolidayDAO;
import com.bis.sourcelead.dto.HolidayDTO;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.HolidayShift;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class HolidayServiceImpl implements HolidayService{

	@Autowired
	HolidayDAO holidayDAO;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LeaveService leaveService;


	@Override
	public Holiday storeHoliday(HolidayDTO holidayDTO) throws Exception {
		Holiday holiday = null;
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(holidayDTO.getOrgLocationId()));
		User user = userService.getUserById(Long.parseLong(holidayDTO.getUserId()));
		if(StringUtils.isNotBlank(holidayDTO.getHolidayId())){
			holiday = holidayDAO.getHolidaysById(holidayDTO.getHolidayId());
			holiday.setUpdatedBy(user.getUserCode());
			holiday.setUpdatedDate(new Date());
		}else{
			holiday = new Holiday();
			holiday.setCreatedDate(new Date());
			holiday.setCreatedBy(user.getUserCode());
			holiday.setHolidayUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		if(holidayDTO != null)
		{
			if(orgLocations.getAverageWorkingHours() != null) {
				holiday.setOrgAvgHrs(CommonUtil.getTimeFormat(Float.valueOf(orgLocations.getAverageWorkingHours()), null));
			}else {
				holiday.setOrgAvgHrs(CommonUtil.getTimeFormat(8f, null));
			}
			
			if(StringUtils.isNotBlank(holidayDTO.getOrgLocationId()))
			{
				holiday.setOrgLocations(orgLocations);
			}else{
				holiday.setHolidayName(null);
			}
			if(StringUtils.isNotBlank(holidayDTO.getHolidayName()))
			{
				holiday.setHolidayName(holidayDTO.getHolidayName());
			}else{
				holiday.setHolidayName(null);
			}
			if(StringUtils.isNotBlank(holidayDTO.getFromDate()))
			{
				holiday.setFromDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),holidayDTO.getFromDate()));
			}else{
				holiday.setFromDate(null);
			}
			if(StringUtils.isNotBlank(holidayDTO.getToDate()))
			{
				holiday.setToDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),holidayDTO.getToDate()));
			}else{
				holiday.setToDate(null);
			}
			if(StringUtils.isNotBlank(holidayDTO.getFromHalfDayCheckBox()))
			{
				holiday.setFromHalfDayCheckBox(holidayDTO.getFromHalfDayCheckBox());
				holiday.setFromHalfDayAmPm(holidayDTO.getFromHalfDayAmPm());
			}else{
				holiday.setFromHalfDayCheckBox(null);
				holiday.setFromHalfDayAmPm(null);
			}
			if(StringUtils.isNotBlank(holidayDTO.getToHalfDayCheckBox()))
			{
				holiday.setToHalfDayCheckBox(holidayDTO.getToHalfDayCheckBox());
				holiday.setToHalfDayAmPm(holidayDTO.getToHalfDayAmPm());
			}else{
				holiday.setToHalfDayCheckBox(null);
				holiday.setToHalfDayAmPm(null);
			}
			if(StringUtils.isNotBlank(holidayDTO.getDescription()))
			{
				holiday.setDescription(holidayDTO.getDescription());
			}else{
				holiday.setDescription(null);
			}
			if(StringUtils.isNotBlank(holidayDTO.getHolidayStatus()))
			{
				holiday.setHolidayStatus(holidayDTO.getHolidayStatus());
			}
			
			holiday.setStatus("1");
			holiday.setBillableType(holidayDTO.getHolidayBillableType());
			
			//Holiday Relates Shifts Storing
			List<Long> dbShifts = null;
			if(StringUtils.isNotBlank(holidayDTO.getHolidayId())){
				dbShifts = holidayDAO.getHolidayShiftCout(Long.valueOf(holidayDTO.getHolidayId()));
			}
		    HashSet<Long> freshShifts = new HashSet<>();
			Set<HolidayShift> holidayShiftDBList = new HashSet<>();
			if(holidayDTO.getShift() != null) {
				String[] shift = holidayDTO.getShift().split(",");
				for(String s:shift) {
					if(dbShifts == null || dbShifts.isEmpty() || (StringUtils.isNotEmpty(s) && dbShifts != null && !dbShifts.isEmpty() && !dbShifts.contains(Long.parseLong(s)))) {
						HolidayShift hs = new HolidayShift();
						hs.setHoliday(holiday);
						Shift sh = new Shift();
						sh.setId(Long.valueOf(s));
						hs.setShift(sh);
						holidayShiftDBList.add(hs);
					}else if(StringUtils.isNotEmpty(s) && dbShifts != null && !dbShifts.isEmpty() && dbShifts.contains(Long.parseLong(s))){
						freshShifts.add(Long.parseLong(s));
					}
				}
			}
			if(!freshShifts.isEmpty()) {
				dbShifts.removeAll(freshShifts);
				if(dbShifts != null && !dbShifts.isEmpty()) {
					holidayDAO.removeHolidayShiftList(dbShifts,Long.valueOf(holidayDTO.getHolidayId()));
				}
			}
			holiday.setHolidayShift(holidayShiftDBList);
		}
		
		
		
		holiday = holidayDAO.storeHoliday(holiday);
		//to save all days of duration
		leaveService.storeHolidayOrRestrictDays(orgLocations,user,holiday);
		return holiday;
	}

	private Set<HolidayShift> storeHolidayShift(HolidayDTO holidayDTO, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HolidayDTO> getAllHolidaysList(Long orgLocationId) throws Exception {
		List<HolidayDTO> holidayDTOList = new ArrayList<HolidayDTO>();
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<Holiday> holidaysList =  holidayDAO.getAllHolidaysList(orgLocationId);
		for(Holiday hlist : holidaysList)
		{ 
			HolidayDTO hDTO = new HolidayDTO();
		    hDTO.setId(hlist.getId());
			if(StringUtils.isNotBlank(hlist.getHolidayName()))
			hDTO.setHolidayName(hlist.getHolidayName());
			if(hlist.getFromDate() != null){
				hDTO.setFromDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), hlist.getFromDate()));
				hDTO.setDayOfTheWeek(Constants.WEEK_DAY.format(hlist.getFromDate()));
			}
			if(hlist.getToDate() != null)
			hDTO.setToDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), hlist.getToDate()));
			if(StringUtils.isNotBlank(hlist.getFromHalfDayCheckBox()))
			hDTO.setFromHalfDayCheckBox(hlist.getFromHalfDayCheckBox());
			if(StringUtils.isNotBlank(hlist.getFromHalfDayAmPm()))
			hDTO.setFromHalfDayAmPm(hlist.getFromHalfDayAmPm());
			if(StringUtils.isNotBlank(hlist.getToHalfDayCheckBox()))
			hDTO.setToHalfDayCheckBox(hlist.getToHalfDayCheckBox());
			if(StringUtils.isNotBlank(hlist.getToHalfDayAmPm()))
			hDTO.setToHalfDayAmPm(hlist.getToHalfDayAmPm());
			if(StringUtils.isNotBlank(hlist.getDescription()))
		    hDTO.setDescription(hlist.getDescription());
			
			hDTO.setHolidayUUID(hlist.getHolidayUUID());
			
			Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date());
			Date fromDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), hlist.getFromDate());
			if(fromDate.before(currentDate)){
				hDTO.setIsPast(Constants.YES);
			}else if(fromDate.after(currentDate)){
				hDTO.setIsFuture(Constants.YES);
			}else{
				hDTO.setIsCurrent(Constants.YES);
			}
			holidayDTOList.add(hDTO);
		}
		return holidayDTOList;
	}
	
	
	@Override
	public List<HolidayDTO> getAllHolidaysListBasedOnYear(Long orgLocationId,Long reportYear) throws Exception {
		List<HolidayDTO> holidayDTOList = new ArrayList<HolidayDTO>();
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<Holiday> holidaysList =  holidayDAO.getAllHolidaysListBasedOnYear(orgLocationId,reportYear);
		for(Holiday hlist : holidaysList)
		{ 
			HolidayDTO hDTO = new HolidayDTO();
		    hDTO.setId(hlist.getId());
			if(StringUtils.isNotBlank(hlist.getHolidayName()))
			hDTO.setHolidayName(hlist.getHolidayName());
			if(hlist.getFromDate() != null){
				hDTO.setFromDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), hlist.getFromDate()));
				hDTO.setDayOfTheWeek(Constants.WEEK_DAY.format(hlist.getFromDate()));
			}
			if(hlist.getToDate() != null)
			hDTO.setToDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), hlist.getToDate()));
			if(StringUtils.isNotBlank(hlist.getFromHalfDayCheckBox()))
			hDTO.setFromHalfDayCheckBox(hlist.getFromHalfDayCheckBox());
			if(StringUtils.isNotBlank(hlist.getFromHalfDayAmPm()))
			hDTO.setFromHalfDayAmPm(hlist.getFromHalfDayAmPm());
			if(StringUtils.isNotBlank(hlist.getToHalfDayCheckBox()))
			hDTO.setToHalfDayCheckBox(hlist.getToHalfDayCheckBox());
			if(StringUtils.isNotBlank(hlist.getToHalfDayAmPm()))
			hDTO.setToHalfDayAmPm(hlist.getToHalfDayAmPm());
			if(StringUtils.isNotBlank(hlist.getDescription()))
		    hDTO.setDescription(hlist.getDescription());
			
			Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date());
			Date fromDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), hlist.getFromDate());
			if(fromDate.before(currentDate)){
				hDTO.setIsPast(Constants.YES);
			}else if(fromDate.after(currentDate)){
				hDTO.setIsFuture(Constants.YES);
			}else{
				hDTO.setIsCurrent(Constants.YES);
			}
			holidayDTOList.add(hDTO);
		}
		return holidayDTOList;
	}
	
	
	@Override
	public Holiday getHolidaysById(String holidayId) throws Exception {
		return holidayDAO.getHolidaysById(holidayId);
	}

	@Override
	public List<Holiday> validateHoliDayDates(String fromDate, String toDate, OrgLocations orgLocations) throws Exception {
		return holidayDAO.validateHoliDayDates(fromDate,toDate,orgLocations);
	}
	
	
	public List<Holiday> getHolidaybyLocationIdAndFromdate(Date fromDate,Date endDate,Long orgLocationId)throws Exception {
		return holidayDAO.getHolidaybyLocationIdAndFromdate(fromDate,endDate,orgLocationId);
	}

	@Override
	public Holiday getHolidaysByUUID(String holidayUUID)
	{
		return holidayDAO.getHolidaysByUUID(holidayUUID);
	}

	@Override
	public List<ProjectTasks> getHolidayProjectTaskByorgLocationIdAndHolidayId(Long orglocationId, Long holidayId) {
		return holidayDAO.getHolidayProjectTaskByorgLocationIdAndHolidayId(orglocationId,holidayId);
	}

	@Override
	public List<Holiday> getAllHoliday() {
		return holidayDAO.getAllHoliday();
	}

	@Override
	public List<HolidayDTO> getAllHolidaysListByOrgloationIdAndShiftId(Long locationId, String shiftIds, String reportYear) throws Exception {
		List<HolidayDTO> holidayDTOList = new ArrayList<HolidayDTO>();
		OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);
		List<Holiday> holidaysList =  holidayDAO.getAllHolidaysListByOrgloationIdAndShiftId(locationId,shiftIds,reportYear);
		for(Holiday hlist : holidaysList)
		{ 
			HolidayDTO hDTO = new HolidayDTO();
		    hDTO.setId(hlist.getId());
			if(StringUtils.isNotBlank(hlist.getHolidayName()))
			hDTO.setHolidayName(hlist.getHolidayName());
			if(hlist.getFromDate() != null){
				hDTO.setFromDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), hlist.getFromDate()));
				hDTO.setDayOfTheWeek(Constants.WEEK_DAY.format(hlist.getFromDate()));
			}
			if(hlist.getToDate() != null)
			hDTO.setToDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), hlist.getToDate()));
			if(StringUtils.isNotBlank(hlist.getFromHalfDayCheckBox()))
			hDTO.setFromHalfDayCheckBox(hlist.getFromHalfDayCheckBox());
			if(StringUtils.isNotBlank(hlist.getFromHalfDayAmPm()))
			hDTO.setFromHalfDayAmPm(hlist.getFromHalfDayAmPm());
			if(StringUtils.isNotBlank(hlist.getToHalfDayCheckBox()))
			hDTO.setToHalfDayCheckBox(hlist.getToHalfDayCheckBox());
			if(StringUtils.isNotBlank(hlist.getToHalfDayAmPm()))
			hDTO.setToHalfDayAmPm(hlist.getToHalfDayAmPm());
			if(StringUtils.isNotBlank(hlist.getDescription()))
		    hDTO.setDescription(hlist.getDescription());
			
			hDTO.setHolidayUUID(hlist.getHolidayUUID());
			
			Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date());
			Date fromDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), hlist.getFromDate());
			if(fromDate.before(currentDate)){
				hDTO.setIsPast(Constants.YES);
			}else if(fromDate.after(currentDate)){
				hDTO.setIsFuture(Constants.YES);
			}else{
				hDTO.setIsCurrent(Constants.YES);
			}
			holidayDTOList.add(hDTO);
		}
		return holidayDTOList;
	}

	@Override
	public List<Long> getHolidayShiftCout(Long holidayId) throws Exception {
		return holidayDAO.getHolidayShiftCout(holidayId);
	}

	@Override
	public List<HolidayShift> getHolidayShiftListByHolidayId(Long holidayId) throws Exception {
		return holidayDAO.getHolidayShiftListByHolidayId(holidayId);
	}

	@Override
	public void deleteHolidayByHolidayId(Long holidayId) throws Exception {
		holidayDAO.deleteHolidayByHolidayId(holidayId);
	}

	@Override
	public List<HolidayAndRestrictday> getHolidayByFromToDate(Date startDate, Date endDate,OrgLocations orgLocations)
	{
		return holidayDAO.getHolidayByFromToDate(startDate,endDate,orgLocations);
	}

	
}
