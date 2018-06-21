package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.AppliedLeaveDate;

public interface AppliedLeaveDateDao {

	int deleteAppliedLeaveDatesByLeaveId(Long leaveId);

	List<AppliedLeaveDate> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(Long orgLocationid, Long leaveTypeId);
}
