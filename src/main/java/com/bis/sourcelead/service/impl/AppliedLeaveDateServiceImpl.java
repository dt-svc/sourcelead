package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AppliedLeaveDateDao;
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
}
