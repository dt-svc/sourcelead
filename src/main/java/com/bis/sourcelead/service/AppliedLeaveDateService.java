package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.AppliedLeaveDate;

public interface AppliedLeaveDateService {

	int deleteAppliedLeaveDatesByLeaveId(Long leaveId);

	List<AppliedLeaveDate> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(Long orgLocationid, Long leaveTypeId);
}
