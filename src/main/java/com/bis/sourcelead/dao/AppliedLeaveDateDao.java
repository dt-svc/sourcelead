package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.AppliedLeaveDateView;

public interface AppliedLeaveDateDao {

	int deleteAppliedLeaveDatesByLeaveId(Long leaveId);

	List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(Long orgLocationid, Long leaveTypeId);

	List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(Long orgLocationId, Long leaveTypeId, Date sDate, Date eDate, Long userId);
}
