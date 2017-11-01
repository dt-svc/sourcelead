package com.bis.sourcelead.dao;


import java.text.ParseException;

import java.util.Date;

import java.util.List;

import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.OrgLocations;

public interface LeaveDao {

	Leave saveLeave(Leave leave) throws Exception;

	ResponseList<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationId(LeaveDTO leaveDTO, OrgLocations orgLocations) throws ParseException;

	List<Leave> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status, Long userId);

	Leave getLeaveById(String leaveId) throws Exception;

	List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationIdAndDuration(Long locationId, String userId, String status, Date currentWeekStarDate, Date currentWeekEndDate, String department, Date leaveDate);

	List<Leave> getLeavesListByTypeIdAndLocationIdAndUserId(String leaveTypeId, Long orgLocationId, Long userId);
	
	int getNumberOfLeavesTakenByUser(Long userId, String leaveTypeId) throws Exception;

	int getThisMonthLeavesTakenByUser(Long userId, String leaveTypeId);

	ResponseList<Leave> getTopLeavetakers(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception;

	List<LeaveApproveHierarchy> getAllEmployeesLeavesCounts(Long userId,Long orgLocationId);

	List<Leave> getAllSelfLeavesCounts(Long orgLocationId, Long userId);
	
	List<Leave> getLeaveTakersBarChat(OrgLocations orgLocation, LeaveDTO leaveDTO)throws Exception;

	List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationIdAndUserIdAndStatus(Long locationId, String userId, String status);

	boolean validateNameByOrgLocationIdandFromTable(String name, Long orgLocationId, String fromTable, String id);

	boolean deleteAllLeaveRolesByParticularTableId(String from, String fromId);

	void storeHolidayAndRestrictDays(HolidayAndRestrictday day);

	List<HolidayAndRestrictday> getAllHolidayAndRestrictDaysInDuration(String fromDate, String toDate, OrgLocations orgLocations);

}
