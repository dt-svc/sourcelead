package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AppliedLeaveDateDao;
import com.bis.sourcelead.entity.AppliedLeaveDate;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.service.AppliedLeaveDateService;

@Service
public class AppliedLeaveDateServiceImpl implements AppliedLeaveDateService{

	@Autowired
	AppliedLeaveDateDao appliedLeaveDateDao;
	
	@Override
	public List<AppliedLeaveDate> getAppliedLeaveDateByUserId(Long userId, Date sDate, Date eDate) {
		return appliedLeaveDateDao.getAppliedLeaveDateByUserId(userId, sDate, eDate);
	}
	
	@Override
	public int deleteAppliedLeaveDatesByLeaveId(Long leaveId)
	{
		return appliedLeaveDateDao.deleteAppliedLeaveDatesByLeaveId(leaveId);
	}

	@Override
	public List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(Long orgLocationid, Long leaveTypeId,Date startDate,Date endDate)
	{
		return appliedLeaveDateDao.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(orgLocationid, leaveTypeId, startDate, endDate);
	}

	@Override
	public List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(Long orgLocationId, Long leaveTypeId, Date sDate, Date eDate,Long userId, Long orgId)
	{
		return appliedLeaveDateDao.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocationId,leaveTypeId,sDate,eDate,userId,orgId);
	}

	@Override
	public List<AppliedLeaveDateView> getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserIdAndStatus(Long orgLocationId, Long leaveTypeId, Date sDate, Date eDate, Long userId, String leaveStatus,Long orgId)
	{
		return appliedLeaveDateDao.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserIdAndStatus(orgLocationId,leaveTypeId,sDate,eDate,userId,leaveStatus,orgId);
	}

	@Override
	public Map<String,Float> getAppliedLeaveDateAndHolidayHoursByUserId(Long userId, Date sDate, Date eDate,Long worklocationId) {
		return appliedLeaveDateDao.getAppliedLeaveDateAndHolidayHoursByUserId(userId, sDate, eDate,worklocationId);
	}

	@Override
	public List<AppliedLeaveDateView> getAppliedLeaveByUserId(Long orgLocationId, Long leaveTypeId, Date startDate, Date endDate, Long userId,Long orgId)
	{
		return appliedLeaveDateDao.getAppliedLeaveByUserId(orgLocationId, leaveTypeId, startDate, endDate, userId,orgId);
	}
	
}
