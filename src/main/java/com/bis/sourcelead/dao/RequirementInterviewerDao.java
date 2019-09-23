package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.RequirementInterviewerDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterviewer;

public interface RequirementInterviewerDao {

	RequirementInterviewer storeRequirementInterviewer(RequirementInterviewer interviewer) throws Exception;

	List<RequirementInterviewer> getInterviwersListByInterviewId(Long requirementInterviewId);
	
	RequirementInterviewer getInterviwerstByInterviewId(Long requirementInterviewerId);
	
	RequirementInterviewer getInterviwerByInterviewIdAndUserId(Long requirementInterviewerId,Long userId);

	List<RequirementInterviewer> getinterviewListByOrgLocationAndUserId(OrgLocations loginOrgLocations,
			List<Long> interviewerIds,Date startUTCDate, Date endUTCDate);
	
	public Boolean isInterviwerForByRequirementIdInterviewIdAndUserId(String reqUUID, Long requirementInterviewerId, Long userId);

	List<RequirementInterviewerDTO> getInterviwersListByInterviewIdFromDTO(Long requirementInterviewId);
	
	List<RequirementInterviewerDTO> getInterviwersListByInterviewIdAndSubmissionIdFromDTO(Long interviewId,Long submissionId);
	
	RequirementInterviewer getRequirementInterviewerByUUID(String uuid) throws Exception;
	
	RequirementInterviewer getRequirementInterviewerByInterviewIdAndExternalInterviewerEmail(Long requirementInterviewId,String email) throws Exception;
	
	List<RequirementInterviewer> getInterviwersListByInterviewIdAndUserID(Long requirementInterviewId,Long userId)throws Exception;

}
