package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementSubmissionHistoryDao;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;

@Service
public class RequirementSubmissionHistoryServiceImpl implements RequirementSubmissionHistoryService {

	@Autowired
	RequirementSubmissionHistoryDao requirementSubmissionHistoryDao;

	/**
	 * updating the Requirement Submission Hostory
	 * @param requirementSubmission
	 * @param user
	 * @param requirementSubmissionHistory
	 * @return
	 * @throws Exception
	 */
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	public RequirementSubmissionHistory updateRequirementHistory(RequirementSubmission requirementSubmission,User user,RequirementSubmissionHistory requirementSubmissionHistory)
			throws Exception {
		requirementSubmissionHistory.setSubmissionId(requirementSubmission);
		requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().getValue());
		if(user != null){
			requirementSubmissionHistory.setCreatedBy(user.getUserCode());
			requirementSubmissionHistory.setUpdatedBy(user.getUserCode());	
		}
		requirementSubmissionHistory.setCreatedDate(new Date());
		requirementSubmissionHistory.setUpdatedDate(new Date());
		
		StringBuilder userName = new StringBuilder();
		
		if(!isNullOrEmpty(user.getLastName())){
			userName = userName.append(user.getFirstName()).append(" ").append(user.getLastName());
		}else{
			userName = userName.append(user.getFirstName());
		}
		requirementSubmissionHistory.setCreatedUser(userName.toString());
		
		return requirementSubmissionHistoryDao.updateRequirementHistory(requirementSubmissionHistory);
	}

	/**
	 * Read Requirement history based on locationid and submission type
	 * @param orgLocationID
	 * @param submissionType
	 * @throws Exception 
	 */
	@Override
	public List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndStage(Long orgLocationID,String submissionType,Long submissionId) throws Exception {
		return requirementSubmissionHistoryDao.getRequirementHistoryByLocationIdAndStage(orgLocationID,submissionType,submissionId);
	}

	@Override
	public RequirementSubmissionHistory getRequirementSubmissionHistoryBySubmissionId(Long submissionId) {
		return requirementSubmissionHistoryDao.getRequirementSubmissionHistoryBySubmissionId(submissionId);
	}

	@Override
	public List<RequirementSubmissionHistory> getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(
			Long candidateId, Long orgLocationId, Long requirementId, Long submissionId) throws Exception {
		return requirementSubmissionHistoryDao.getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(candidateId,orgLocationId,requirementId,submissionId);
	}

	@Override
	public List<RequirementSubmissionHistory> getAllRequirementSubmissionHistoryBySubmissionId(List<Long> submissionIds)
			throws Exception {
		return requirementSubmissionHistoryDao.getAllRequirementSubmissionHistoryBySubmissionId(submissionIds);
	}

}
