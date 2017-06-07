package com.bis.sourcelead.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProposeTimeDao;
import com.bis.sourcelead.entity.ProposeTime;
import com.bis.sourcelead.service.ProposeTimeService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class ProposeTimeServiceImpl implements ProposeTimeService{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ProposeTimeDao proposeTimeDao;
	
	 @Autowired
	 private CommonUtil commonUtil;

	@Override
	public ProposeTime saveProposeTime(ProposeTime proposeTime) throws Exception {
		return proposeTimeDao.saveProposeTime(proposeTime);
	}

	@Override
	public ProposeTime getProposeTimeById(Long proposeTimetId) throws Exception {
		return proposeTimeDao.getProposeTimeById(proposeTimetId);
	}

	@Override
	public List<ProposeTime> getProposeTimeByInterViewId(Long interviewId) throws Exception {
		return proposeTimeDao.getProposeTimeByInterViewId(interviewId);
	}

	
}
