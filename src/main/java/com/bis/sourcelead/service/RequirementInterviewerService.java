package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.dto.RequirementInterviewerDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementSubmission;

public interface RequirementInterviewerService {

	List<RequirementInterviewer> getInterviwersListByInterviewId(Long requirementInterviewId);
	
	RequirementInterviewer getInterviwerstByInterviewId(Long requirementInterviewerId);
	
	RequirementInterviewer getInterviwerByInterviewIdAndUserId(Long requirementInterviewerId,Long userId);

	List<RequirementInterviewer> getinterviewListByOrgLocationAndUserId(OrgLocations loginOrgLocations,
			List<Long> interviewersList, Date startUTCDate, Date endUTCDate);

	void updatedFeedbackMailFromInterviewerToRequirementOwner(RequirementInterviewer requirementInterviewer, RequirementSubmission requirementSubmission, JSONObject jsonObj, String templateId) throws Exception;
	
	Boolean isInterviwerForByRequirementIdInterviewIdAndUserId(String reqUUID, Long requirementInterviewerId,Long userId);

	List<RequirementInterviewerDTO> getInterviwersListByInterviewIdFromDTO(Long interviewId);
	
	List<RequirementInterviewerDTO> getInterviwersListByInterviewIdAndSubmissionIdFromDTO(Long interviewId,Long submissionId);
	
	RequirementInterviewer getRequirementInterviewerByUUID(String uuid) throws Exception;
	
	RequirementInterviewer getRequirementInterviewerByInterviewIdAndExternalInterviewerEmail(Long requirementInterviewId,String email) throws Exception;
	
	List<RequirementInterviewer> getInterviwersListByInterviewIdAndUserID(Long requirementInterviewId,Long userId)throws Exception;
}
