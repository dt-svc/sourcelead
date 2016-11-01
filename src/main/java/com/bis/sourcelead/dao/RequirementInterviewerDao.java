package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RequirementInterviewer;

public interface RequirementInterviewerDao {

	RequirementInterviewer storeRequirementInterviewer(RequirementInterviewer interviewer) throws Exception;

	List<RequirementInterviewer> getInterviwersListByInterviewId(Long requirementInterviewId);

}
