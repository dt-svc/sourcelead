package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.AppliedLeaveDate;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.Leave;

public interface AppliedLeaveDateDao {

	int deleteAppliedLeaveDatesByLeaveId(Long leaveId);

	List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(Long orgLocationid, Long leaveTypeId, Date startDate, Date endDate);

	List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(Long orgLocationId, Long leaveTypeId, Date sDate, Date eDate, Long userId);

	AppliedLeaveDate saveAppliedLeaveDates(AppliedLeaveDate appliedLeaveDate);

	boolean checkForExistedLeave(Leave leave);

}
