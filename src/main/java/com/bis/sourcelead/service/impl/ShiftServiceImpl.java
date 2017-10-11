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
		if(StringUtils.isNotBlank(shiftDTO.getShiftId())){
			shift = shiftDao.getShiftById(shiftDTO.getShiftId());
			shift.setUpdatedBy(user.getUserCode());
			shift.setUpdatedDate(new Date());
		}else{
			shift = new Shift();
			shift.setCreatedBy(user.getUserCode());
			shift.setCreatedDate(new Date());
		}
		shift.setOrgLocationId(orgLocation);
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
		if(shiftDTO.getShiftDescription() != null && !"".equalsIgnoreCase(shiftDTO.getShiftDescription())){
		    shift.setShiftDescription(shiftDTO.getShiftDescription());
		}else{
			shift.setShiftDescription("");
		}
		shift.setStatus(Constants.RECORD_ACTIVE.toString());
		
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
	
	
}
