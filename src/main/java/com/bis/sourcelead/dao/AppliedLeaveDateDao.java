package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.AppliedLeaveDate;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.Leave;

public interface AppliedLeaveDateDao {
	
	public List<AppliedLeaveDate> getAppliedLeaveDateByUserId(Long userId,Date sDate,Date eDate);
	
	public Map<String,Float> getAppliedLeaveDateAndHolidayHoursByUserId(Long userId,Date sDate,Date eDate,Long worklocationId);

	int deleteAppliedLeaveDatesByLeaveId(Long leaveId);

	List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(Long orgLocationid, Long leaveTypeId, Date startDate, Date endDate);

	List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(Long orgLocationId, Long leaveTypeId, Date sDate, Date eDate, Long userId, Long orgId);

	AppliedLeaveDate saveAppliedLeaveDates(AppliedLeaveDate appliedLeaveDate);

	boolean checkForExistedLeave(Leave leave);
	
    AppliedLeaveDate getAppliedLeaveDateByleaveIdAndAppliedLeaveDate(Date date,Long leaveId);

	List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserIdAndStatus(Long orgLocationId, Long leaveTypeId, Date sDate, Date eDate, Long userId, String leaveStatus, Long orgId);

	List<AppliedLeaveDateView> getAppliedLeaveByUserId(Long orgLocationId, Long leaveTypeId, Date startDate, Date endDate, Long userId, Long orgId);

}
