package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementInterviewerDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.service.RequirementInterviewerService;

@Service
public class RequirementInterviewerServiceImpl implements RequirementInterviewerService{
	

	@Autowired
	RequirementInterviewerDao requirementInterviewerDao;
	
	@Override
	public List<RequirementInterviewer> getInterviwersListByInterviewId(Long requirementInterviewId) {
		return requirementInterviewerDao.getInterviwersListByInterviewId(requirementInterviewId);
	}
	
	@Override
	public RequirementInterviewer getInterviwerstByInterviewId(Long requirementInterviewerId) {
		return requirementInterviewerDao.getInterviwerstByInterviewId(requirementInterviewerId);
	}

	@Override
	public RequirementInterviewer getInterviwerByInterviewIdAndUserId(Long requirementInterviewerId, Long userId) {
		return requirementInterviewerDao.getInterviwerByInterviewIdAndUserId(requirementInterviewerId,userId);
	}

	@Override
	public List<RequirementInterviewer> getinterviewListByOrgLocationAndUserId(OrgLocations loginOrgLocations,List<Long> interviewerIds,Date startUTCDate, Date endUTCDate) {
		return requirementInterviewerDao.getinterviewListByOrgLocationAndUserId(loginOrgLocations,interviewerIds,startUTCDate,endUTCDate);
	}
}
