package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AppliedLeaveDateDao;
import com.bis.sourcelead.entity.AppliedLeaveDate;
import com.bis.sourcelead.service.AppliedLeaveDateService;

@Service
public class AppliedLeaveDateServiceImpl implements AppliedLeaveDateService{

	@Autowired
	AppliedLeaveDateDao appliedLeaveDateDao;
	
	@Override
	public int deleteAppliedLeaveDatesByLeaveId(Long leaveId)
	{
		return appliedLeaveDateDao.deleteAppliedLeaveDatesByLeaveId(leaveId);
	}

	@Override
	public List<AppliedLeaveDate> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(Long orgLocationid, Long leaveTypeId)
	{
		return appliedLeaveDateDao.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(orgLocationid, leaveTypeId);
	}
}
