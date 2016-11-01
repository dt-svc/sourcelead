package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RequirementInterviewer;

public interface RequirementInterviewerService {

	List<RequirementInterviewer> getInterviwersListByInterviewId(Long requirementInterviewId);
	
	

}
