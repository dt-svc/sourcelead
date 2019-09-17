package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateCommentsDao;
import com.bis.sourcelead.dto.CandidateCommentsDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateComments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateCommentsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Venkat Kesav
 *
 */

@Service
public class CandidateCommentsServiceImpl implements CandidateCommentsService {
	
	private static final Logger logger = LoggerFactory.getLogger(CandidateCommentsServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private CandidateCommentsDao candidateCommentsDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
    CandidateActivityCommonUtil candidateActivityCommonUtil; 

	/**
	 * @author Venkat Kesav
	 * @since  Nov 10 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save the candidate comment in candidate_Comments table.
	 * 
	 * @param jsonObj This is the first parameter to storeCandidateComments method
	 * @param user This is the second parameter to storeCandidateComments method
	 * @param candidate This is the third parameter to storeCandidateComments method
	 * @param orgLocations This is the third parameter to storeCandidateComments method
	 * 
	 * @return CandidateComments
	 * 
	 * @throws NullPointer
	 */
	@Override
	public CandidateComments storeCandidateComments(CandidateCommentsDTO candidateCommentsDTO, User user, Candidate candidate, OrgLocations orgLocations, RequirementSubmission submission) throws Exception {
		CandidateComments candidateComments = new CandidateComments();
		StringBuffer activitySummary = new StringBuffer();
			if(candidateCommentsDTO.getComment() != null){
				activitySummary.append("Comment").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(candidateCommentsDTO.getComment()).append(Constants.NEW_VALUE_TAG_END).append(" ");
				candidateComments.setComment(candidateCommentsDTO.getComment());
			}
			if(candidate != null){
				candidateComments.setCandidateId(candidate);
			}
			if(submission != null){
				candidateComments.setSubmissionId(submission);
			}
			if(orgLocations != null){
				candidateComments.setOrgLocationId(orgLocations);
			}
			candidateComments.setStatus(Constants.CANDIDATE_ACTIVE);
			candidateComments.setCreatedDate(commonUtil.currentDate());
			candidateComments.setUpdatedBy(user.getUserCode());
			candidateComments.setCreatedBy(user.getUserCode());
			candidateComments.setUpdatedDate(commonUtil.currentDate());
		candidateCommentsDao.storeCandidateComments(candidateComments);
		
		candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidate, "", Constants.CANDIDATE_COMMENTS_NEW, activitySummary.toString(), "CANDIDATE");
		return candidateComments;
	}
	
	
	/**
	 * @author Venkat Kesav
	 * @since  Nov 10 2016
	 * @version 0.0.1
	 * 
	 * This method is used to getAllCommentsOnCandidateByCandidateIdAndOrgLocation from candidate_Comments table.
	 * 
	 * @param candidate This is the first parameter to getAllCommentsOnCandidateByCandidateIdAndOrgLocation method
	 * @param orgLocation This is the second parameter to getAllCommentsOnCandidateByCandidateIdAndOrgLocation method
	 * 
	 * @return CandidateComments
	 * 
	 * @throws NullPointer
	 */
	@Override
	public List<CandidateComments> getAllCommentsOnCandidateByCandidateIdAndOrgLocation(Long candidateId, OrgLocations orgLocation) throws Exception {
		
		List<CandidateComments> candidateCommentsList = candidateCommentsDao.getAllCommentsOnCandidateByCandidateIdAndOrgLocation(candidateId, orgLocation.getId());

		for(CandidateComments candidateComments: candidateCommentsList){
			
			User user = userService.getUserByUserCode(candidateComments.getUpdatedBy());
			if(null != user){
				String authorLastName = "";
				if(StringUtils.isNotEmpty(user.getFirstName())){
					authorLastName = user.getLastName();
				}
				StringBuilder nameBuilder = new StringBuilder();
				 candidateComments.setCommentAuthor(nameBuilder.append(user.getFirstName()).append(" ").append(authorLastName).toString());
				candidateComments.setCommentAuthorUserId(user.getId());
				candidateComments.setCommentAuthorUserImage(user.getFileUrl());
			}
			
			long diff = commonUtil.currentDate().getTime() - candidateComments.getUpdatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder timeBuilder = new StringBuilder();
			if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
				candidateComments.setCommentTime(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			}else if(diffDays == 0 && diffHours == 0){
				candidateComments.setCommentTime(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			}else if(diffDays == 0){
				candidateComments.setCommentTime(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
			}else if(diffDays < 7){
				candidateComments.setCommentTime(timeBuilder.append(diffDays).append( " days ").append(diffHours).append(" hours ago").toString());
			}else{
				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(candidateComments.getUpdatedDate());
				candidateComments.setCommentTime(commonDateFormat.toString());
			}
			//candidateComments.setCommentTime(candidateComments.getUpdatedDate().toString());
		}
		
		return candidateCommentsList;
	}


	@Override
	public List<CandidateComments> getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId(Long candidateId,
			Long submissionId, OrgLocations orgLocation) throws Exception {
		
		List<CandidateComments> candidateCommentsList = candidateCommentsDao.getAllCommentsOnCandidateByCandidateIdAndOrgLocationAndSubmissionId(candidateId, submissionId, orgLocation.getId());
		for(CandidateComments candidateComments: candidateCommentsList){
			
			User user = userService.getUserByUserCode(candidateComments.getUpdatedBy());
			if(null != user){
				String authorLastName = "";
				if(StringUtils.isNotEmpty(user.getFirstName())){
					authorLastName = user.getLastName();
				}
				StringBuilder commentBuilder = new StringBuilder();
				candidateComments.setCommentAuthor(commentBuilder.append(user.getFirstName()).append(" ").append(authorLastName).toString());
				candidateComments.setCommentAuthorUserId(user.getId());
				candidateComments.setCommentAuthorUserImage(user.getFileUrl());
			}
			
			long diff = commonUtil.currentDate().getTime() - candidateComments.getUpdatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);	
			StringBuilder timeBuilder = new StringBuilder();
			if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
				candidateComments.setCommentTime(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			}else if(diffDays == 0 && diffHours == 0){
				candidateComments.setCommentTime(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			}else if(diffDays == 0){
				candidateComments.setCommentTime(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
			}else if(diffDays < 7){
				candidateComments.setCommentTime(timeBuilder.append(diffDays).append( " days ").append(diffHours).append(" hours ago").toString());
		
			}else{
				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(candidateComments.getUpdatedDate());
				candidateComments.setCommentTime(commonDateFormat.toString());
			}
		}
		return candidateCommentsList;
	}


	@Override
	public List<CandidateComments> getAllCommentsOnCandidateGlobalByCandidateIdAndOrgLocation(Long candidateId,
			Long orgLocationId) throws Exception {
		
		List<CandidateComments> candidateCommentsList = candidateCommentsDao.getAllCommentsOnCandidateGlobalByCandidateIdAndOrgLocation(candidateId, orgLocationId);

		for(CandidateComments candidateComments: candidateCommentsList){
			
			User user = userService.getUserByUserCode(candidateComments.getUpdatedBy());
			if(null != user){
				String authorLastName = "";
				if(StringUtils.isNotEmpty(user.getFirstName())){
					authorLastName = user.getLastName();
				}
				StringBuilder nameBuilder = new StringBuilder();
				 candidateComments.setCommentAuthor(nameBuilder.append(user.getFirstName()).append(" ").append(authorLastName).toString());
				candidateComments.setCommentAuthorUserId(user.getId());
				candidateComments.setCommentAuthorUserImage(user.getFileUrl());
			}
			
			long diff = commonUtil.currentDate().getTime() - candidateComments.getUpdatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder timeBuilder = new StringBuilder();
			if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
				candidateComments.setCommentTime(timeBuilder.append(diffSeconds).append("seconds ago").toString());
			}else if(diffDays == 0 && diffHours == 0){
				candidateComments.setCommentTime(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
			}else if(diffDays == 0){
				candidateComments.setCommentTime(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
			}else if(diffDays < 7){
				candidateComments.setCommentTime(timeBuilder.append(diffDays).append( " days ").append(diffHours).append(" hours ago").toString());
			}else{
				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(candidateComments.getUpdatedDate());
				candidateComments.setCommentTime(commonDateFormat.toString());
			}
		}
		
		return candidateCommentsList;
	
	}
}
