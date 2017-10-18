package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RestrictDayDao;
import com.bis.sourcelead.dto.RestrictDayDTO;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RestrictDay;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RestrictDayService;
import com.bis.sourcelead.service.RolesService;
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
	
	@Autowired
	private RolesService rolesService;
	
	@Override
	public RestrictDay storeRestrictedDay(RestrictDayDTO restrictDayDTO) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.valueOf(restrictDayDTO.getOrgLocationId()));
		User user = userService.getUserById(Long.valueOf(restrictDayDTO.getUserId()));
		RestrictDay restrictDay = null;
		
		if(StringUtils.isNotBlank(restrictDayDTO.getRestrictDayId())) {
			restrictDay = restrictDayDao.getRestrictDayById(restrictDayDTO.getRestrictDayId());
			restrictDay.setUpdatedBy(user.getUserCode());
			restrictDay.setUpdatedDate(new Date());
		}else{
			restrictDay = new RestrictDay();
			restrictDay.setCreatedBy(user.getUserCode());
			restrictDay.setCreatedDate(new Date());
		}
		
		restrictDay.setRestrictName(restrictDayDTO.getRestrictName());
		restrictDay.setStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),restrictDayDTO.getStartDate()));
		restrictDay.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),restrictDayDTO.getEndDate()));
 		if(StringUtils.isNotBlank(restrictDayDTO.getReason())){
			restrictDay.setReason(restrictDayDTO.getReason());
		}
		restrictDay.setOrgLocationId(orgLocations);
		restrictDay.setApplicableToAll(restrictDayDTO.getIsApllicable());
		
		if(restrictDayDTO.getRolesList() != null){
			Set<LeaveRole> leaveRolesSet = getLeaveRoles(restrictDayDTO.getRolesList(), restrictDay, user);
			restrictDay.setRestrictDayRolesList(leaveRolesSet);
		}
		restrictDay.setCreatedDate(commonUtil.currentDate());
		restrictDay.setCreatedBy(user.getUserCode());
		restrictDay.setStatus(Constants.RECORD_ACTIVE.toString());
		restrictDay.setUpdatedDate(commonUtil.currentDate());
		restrictDay.setUpdatedBy(user.getUserCode());
		return restrictDayDao.storeRestrictedDay(restrictDay);
	}

	@Override
	public List<RestrictDay> getRestrictDayListByOrgLocationId(Long orgLocationId) throws Exception {
		List<RestrictDay> restrictDayList = restrictDayDao.getRestrictDayListByOrgLocationId(orgLocationId);
		return restrictDayList;
	}

	@Override
	public RestrictDay getRestrictDayById(String restrictDayId) {
		return restrictDayDao.getRestrictDayById(restrictDayId);
	}
	

	/**
	 * 
	 * To save roles to give leave permission
	 *
	 * 
	 * 
	 * @param roles
	 * 
	 * @param options
	 * 
	 * @param user
	 * 
	 */

	private Set<LeaveRole> getLeaveRoles(List<String> roles, RestrictDay restrictDay, User user) throws Exception {
		Set<LeaveRole> leaveRolesSet = new HashSet<>();
		roles.forEach(role -> {
			LeaveRole leaveRoles = new LeaveRole();
			leaveRoles.setRestrictDay(restrictDay);
			leaveRoles.setType(Constants.RESTRICTDAY);
			try {
				Roles rol = rolesService.getRolesById(Long.parseLong(role));
				leaveRoles.setRoleId(rol);
			} catch (Exception e) {
				e.printStackTrace();
			}
			leaveRoles.setStatus("1");
			leaveRoles.setCreatedDate(new Date());
			leaveRoles.setUpdatedDate(new Date());
			leaveRoles.setCreatedBy(user.getUserCode());
			leaveRoles.setUpdatedBy(user.getUserCode());
			leaveRolesSet.add(leaveRoles);
		});

		return leaveRolesSet;

	}

	@Override
	public List<RestrictDay> validateRestrictDayDates(String fromDate, String toDate, OrgLocations orgLocations) throws Exception {
		return restrictDayDao.validateRestrictDayDates(fromDate, toDate, orgLocations);
	}

}
