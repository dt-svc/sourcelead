package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RestrictDayService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

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
	
	@Autowired 
	LeaveService leaveService;
	
	@Override
	public RestrictDay storeRestrictedDay(RestrictDayDTO restrictDayDTO) throws Exception {
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.valueOf(restrictDayDTO.getOrgLocationId()));
		User user = userService.getUserById(Long.valueOf(restrictDayDTO.getUserId()));
		RestrictDay restrictDay = null;
		
		if(restrictDayDTO.getRestrictDayId() > 0) {
			restrictDay = restrictDayDao.getRestrictDayById(restrictDayDTO.getRestrictDayId());
			restrictDay.setUpdatedBy(user.getUserCode());
			restrictDay.setUpdatedDate(new Date());
		}else{
			restrictDay = new RestrictDay();
			restrictDay.setCreatedBy(user.getUserCode());
			restrictDay.setCreatedDate(new Date());
			restrictDay.setRestrictUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
		restrictDay.setRestrictName(restrictDayDTO.getRestrictName());
		restrictDay.setStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),restrictDayDTO.getStartDate()));
		restrictDay.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),restrictDayDTO.getEndDate()));
		restrictDay.setReason(restrictDayDTO.getReason());
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
		
		restrictDay =  restrictDayDao.storeRestrictedDay(restrictDay);
		//to save all days of duration
		leaveService.storeHolidayOrRestrictDays(orgLocations,user,restrictDay);
		return restrictDay;
	}

	@Override
	public List<RestrictDay> getRestrictDayListByOrgLocationId(Long orgLocationId) throws Exception {
		List<RestrictDay> restrictDayList = restrictDayDao.getRestrictDayListByOrgLocationId(orgLocationId);
		return restrictDayList;
	}

	@Override
	public RestrictDay getRestrictDayById(Long restrictDayId) {
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
		boolean flag = leaveService.deleteAllLeaveRolesByParticularTableId(Constants.RESTRICTDAY,restrictDay.getId());
		roles.forEach(role -> {
			LeaveRole leaveRole = null;
			if(restrictDay.getRestrictDayRolesList() !=null && restrictDay.getRestrictDayRolesList().size() >0) {
				List<LeaveRole> list = restrictDay.getRestrictDayRolesList().stream().filter(r->r.getRoleId().getId().longValue() == Long.parseLong(role)).collect(Collectors.toList());
				if(!list.isEmpty()) {
					leaveRole = list.iterator().next();
				}else {
					leaveRole = new LeaveRole();
				}
			}else {
				leaveRole = new LeaveRole();
			}
			leaveRole.setRestrictDay(restrictDay);
			leaveRole.setType(Constants.RESTRICTDAY);
			try {
				Roles rol = rolesService.getRolesById(Long.parseLong(role));
				leaveRole.setRoleId(rol);
			} catch (Exception e) {
				e.printStackTrace();
			}
			leaveRole.setStatus("1");
			leaveRole.setCreatedDate(new Date());
			leaveRole.setUpdatedDate(new Date());
			leaveRole.setCreatedBy(user.getUserCode());
			leaveRole.setUpdatedBy(user.getUserCode());
			leaveRolesSet.add(leaveRole);
		});

		return leaveRolesSet;

	}

	@Override
	public List<RestrictDay> validateRestrictDayDates(String fromDate, String toDate, OrgLocations orgLocations) throws Exception {
		return restrictDayDao.validateRestrictDayDates(fromDate, toDate, orgLocations);
	}

	@Override
	public boolean checkDuplicateRestrictDay(RestrictDayDTO restrictDayDTO) throws Exception
	{
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(restrictDayDTO.getOrgLocationId()));
		List<RestrictDay> list = validateRestrictDayDates(restrictDayDTO.getStartDate(), restrictDayDTO.getEndDate(), orgLocations);
//		Date startDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),restrictDayDTO.getStartDate());
//		Date endDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),restrictDayDTO.getEndDate());
//		commonUtil.getNumberOfDaysBetweenTwoDates(startDate, endDate);
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public RestrictDay getRestrictDayByUUID(String restrictUUID)
	{
		return restrictDayDao.getRestrictDayByUUID(restrictUUID);
	}

}
