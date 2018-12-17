package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ShiftDao;
import com.bis.sourcelead.dto.ShiftDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

import cucumber.deps.difflib.StringUtills;
@Service
public class ShiftServiceImpl implements ShiftService {
	
	@Autowired
	ShiftDao shiftDao;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	UserService userService;

	@Override
	public Shift saveShift(ShiftDTO shiftDTO) throws Exception
	{
		User user = userService.getUserById(Long.valueOf(shiftDTO.getUserId()));
		OrgLocations orgLocation = orgLocationService.getOrgLocations(Long.valueOf(shiftDTO.getOrgLocationId()));
		
		Shift shift = null;
		if(StringUtils.isNotBlank(shiftDTO.getShiftUUID())){
			shift = shiftDao.getShiftById(shiftDTO.getShiftUUID());
		}else{
			shift = new Shift();
			shift.setShiftUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			shift.setCreatedBy(user.getUserCode());
			shift.setCreatedDate(new Date());
		}
		shift.setOrgLocations(orgLocation);
		shift.setShiftName(shiftDTO.getShiftName());
		if(shiftDTO.getTimeIn() != null && !"".equalsIgnoreCase(shiftDTO.getTimeIn())){
		    shift.setTimeIn(shiftDTO.getTimeIn());
		}else{
			shift.setTimeIn("");
		}
		if(shiftDTO.getTimeOut() != null && !"".equalsIgnoreCase(shiftDTO.getTimeOut())){
		    shift.setTimeOut(shiftDTO.getTimeOut());
		}else{
			shift.setTimeOut("");
		} 
		
		if(StringUtils.isNotBlank(shiftDTO.getTotalHours())) {
			shift.setTotalHours(shiftDTO.getTotalHours());
		}
		if(StringUtils.isNotBlank(shiftDTO.getMinimumHours())){
			shift.setMinmumHours(shiftDTO.getMinimumHours());
		}

		if(StringUtils.isNotBlank(shiftDTO.getMondayInTime())){
			shift.setMondayInTime(shiftDTO.getMondayInTime());
		}else {
			shift.setMondayInTime(shiftDTO.getTimeIn());
		}
		if(StringUtils.isNotBlank(shiftDTO.getMondayOutTime())) {
			shift.setMondayOutTime(shiftDTO.getMondayOutTime());
		}else {
			shift.setMondayOutTime(shiftDTO.getTimeOut());
		}
				
		if(StringUtils.isNotBlank(shiftDTO.getTuesdayInTime())) {
			shift.setTuesdayInTime(shiftDTO.getTuesdayInTime());
		}else {
			shift.setTuesdayInTime(shiftDTO.getTimeIn());
		}
		if(StringUtils.isNotBlank(shiftDTO.getTuesdayOutTime())) {
			shift.setTuesdayOutTime(shiftDTO.getTuesdayOutTime());
		}else {
			shift.setTuesdayOutTime(shiftDTO.getTimeOut());
		}
		
		if(StringUtils.isNotBlank(shiftDTO.getWednesdayInTime())) {
			shift.setWednesdayInTime(shiftDTO.getWednesdayInTime());
		}else {
			shift.setWednesdayInTime(shiftDTO.getTimeIn());
		}
		if(StringUtils.isNotBlank(shiftDTO.getWednesdayOutTime())) {
			shift.setWednesdayOutTime(shiftDTO.getWednesdayOutTime());
		}else {
			shift.setWednesdayOutTime(shiftDTO.getTimeOut());
		}
		
		
		if(StringUtils.isNotBlank(shiftDTO.getThursdayInTime())) {
			shift.setThursdayInTime(shiftDTO.getThursdayInTime());
		}else {
			shift.setThursdayInTime(shiftDTO.getTimeIn());
		}
		if(StringUtils.isNotBlank(shiftDTO.getThursdayOutTime())) {
			shift.setThursdayOutTime(shiftDTO.getThursdayOutTime());
		}else {
			shift.setThursdayOutTime(shiftDTO.getTimeOut());
		}
		
		if(StringUtils.isNotBlank(shiftDTO.getFridayInTime())) {
			shift.setFridayInTime(shiftDTO.getFridayInTime());
		}else {
			shift.setFridayInTime(shiftDTO.getTimeIn());
		}
		if(StringUtils.isNotBlank(shiftDTO.getFridayOutTime())) {
			shift.setFridayOutTime(shiftDTO.getFridayOutTime());
		}else {
			shift.setFridayOutTime(shiftDTO.getTimeOut());
		}
		
		if(StringUtils.isNotBlank(shiftDTO.getSaturdayInTime())) {
			shift.setSaturdayInTime(shiftDTO.getSaturdayInTime());
		}else {
			shift.setSaturdayInTime(shiftDTO.getTimeIn());
		}
		if(StringUtils.isNotBlank(shiftDTO.getSaturdayOutTime())) {
			shift.setSaturdayOutTime(shiftDTO.getSaturdayOutTime());
		}else {
			shift.setSaturdayOutTime(shiftDTO.getTimeOut());
		}
		
		if(StringUtils.isNotBlank(shiftDTO.getSundayInTime())) {
			shift.setSundayInTime(shiftDTO.getSundayInTime());
		}else {
			shift.setSundayInTime(shiftDTO.getTimeIn());
		}
		if(StringUtils.isNotBlank(shiftDTO.getSundayOutTime())) {
			shift.setSundayOutTime(shiftDTO.getSundayOutTime());
		}else {
			shift.setSundayOutTime(shiftDTO.getSundayOutTime());
		}
		
		if(shiftDTO.getShiftDescription() != null && !"".equalsIgnoreCase(shiftDTO.getShiftDescription())){
		    shift.setShiftDescription(shiftDTO.getShiftDescription());
		}else{
			shift.setShiftDescription("");
		}
		
		shift.setStatus(Constants.RECORD_ACTIVE.toString());
		shift.setUpdatedBy(user.getUserCode());
		shift.setUpdatedDate(new Date());
		
		return shiftDao.saveShift(shift);
	}

	@Override
	public List<Shift> getShiftList(Long orgLocationId)
	{
		
		return shiftDao.getShiftList(orgLocationId);
	}

	@Override
	public Shift getShiftById(String shiftId)
	{
		return shiftDao.getShiftById(shiftId);
	}

	@Override
	public Shift getShiftListByShiftNameAndOrglocationId(String ShiftName, Long orgLocationId) {
		return shiftDao.getShiftListByShiftNameAndOrglocationId(ShiftName, orgLocationId);
	}

	@Override
	public List<String> getAssociatesShiftListByOrgLocationId(Long orgLocationId) {
		return shiftDao.getAssociatesShiftListByOrgLocationId(orgLocationId);
	}
	
	
}
