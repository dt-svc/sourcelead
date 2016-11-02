package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementInterviewerDao;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.service.RequirementInterviewerService;

@Service
public class RequirementInterviewerServiceImpl implements RequirementInterviewerService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	RequirementInterviewerDao requirementInterviewerDao;
	
	@Override
	public List<RequirementInterviewer> getInterviwersListByInterviewId(Long requirementInterviewId) {
		return requirementInterviewerDao.getInterviwersListByInterviewId(requirementInterviewId);
	}
	
	

}
