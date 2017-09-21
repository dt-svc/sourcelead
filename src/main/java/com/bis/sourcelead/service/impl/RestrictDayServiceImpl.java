package com.bis.sourcelead.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RestrictDayDao;
import com.bis.sourcelead.dto.RestrictDayDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RestrictDay;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RestrictDayService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class RestrictDayServiceImpl implements RestrictDayService {

	@Autowired
	private RestrictDayDao restrictDayDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public RestrictDay storeRestrictedDay(RestrictDayDTO restrictDayDTO) throws Exception {
		
		User user = userService.getUserById(Long.valueOf(restrictDayDTO.getUserId()));
		RestrictDay restrictDay = new RestrictDay();
		restrictDay.setRestrictName(restrictDayDTO.getRestrictName());
		restrictDay.setStartDate(commonUtil.getDateFormat(restrictDayDTO.getStartDate()));
		restrictDay.setEndDate(commonUtil.getDateFormat(restrictDayDTO.getEndDate()));
 		if(StringUtils.isNotBlank(restrictDayDTO.getReason())){
			restrictDay.setReason(restrictDayDTO.getReason());
		}
		restrictDay.setOrgLocationId(Long.valueOf(restrictDayDTO.getOrgLocationId()));
		restrictDay.setApplicableToAll(restrictDayDTO.getIsApllicable());
		
		restrictDay.setCreatedDate(commonUtil.currentDate());
		restrictDay.setCreatedBy(user.getUserCode());
		restrictDay.setStatus(Constants.RECORD_ACTIVE.toString());
		restrictDay.setUpdatedBy(user.getUserCode());
		
		return restrictDayDao.storeRestrictedDay(restrictDay);
	}

	@Override
	public List<RestrictDay> getRestrictDayListByOrgLocationId(Long orgLocationId) throws Exception {
		OrgLocations orgloc = orgLocationService.getOrgLocations(orgLocationId);
		List<RestrictDay> restrictDayList = restrictDayDao.getRestrictDayListByOrgLocationId(orgLocationId);
		for (RestrictDay restrictDay : restrictDayList) {
			restrictDay.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), restrictDay.getStartDate()));
			restrictDay.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), restrictDay.getEndDate()));
		}
		return restrictDayList;
	}
	
}
