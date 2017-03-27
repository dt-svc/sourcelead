package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterviewer;

public interface RequirementInterviewerService {

	List<RequirementInterviewer> getInterviwersListByInterviewId(Long requirementInterviewId);
	
	RequirementInterviewer getInterviwerstByInterviewId(Long requirementInterviewerId);
	
	RequirementInterviewer getInterviwerByInterviewIdAndUserId(Long requirementInterviewerId,Long userId);

	List<RequirementInterviewer> getinterviewListByOrgLocationAndUserId(OrgLocations loginOrgLocations,
			List<Long> interviewersList, Date startUTCDate, Date endUTCDate);
}
