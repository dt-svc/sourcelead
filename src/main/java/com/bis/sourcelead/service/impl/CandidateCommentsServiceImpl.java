package com.bis.sourcelead.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateCommentsDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateComments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateCommentsService;
import com.bis.sourcelead.service.UserService;
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
	public CandidateComments storeCandidateComments(JSONObject jsonObj, User user, Candidate candidate, OrgLocations orgLocations) throws Exception {
		CandidateComments candidateComments = new CandidateComments();
			if(jsonObj.has("comment")){
				candidateComments.setComment(jsonObj.getString("comment"));
			}
			if(candidate != null){
				candidateComments.setCandidateId(candidate);
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
	public List<CandidateComments> getAllCommentsOnCandidateByCandidateIdAndOrgLocation(Candidate candidate, OrgLocations orgLocation) throws Exception {
		
		List<CandidateComments> candidateCommentsList = candidateCommentsDao.getAllCommentsOnCandidateByCandidateIdAndOrgLocation(candidate.getId(), orgLocation.getId());
		for(CandidateComments candidateComments: candidateCommentsList){
			
			User user = userService.getUserByUserCode(candidateComments.getUpdatedBy());
			if(null != user){
				String authorLastName = "";
				if(StringUtils.isNotEmpty(user.getFirstName())){
					authorLastName = user.getLastName();
				}
				candidateComments.setCommentAuthor(user.getFirstName()+" "+authorLastName);
				candidateComments.setCommentAuthorUserId(user.getId());
			}
			
			candidateComments.setCommentTime(candidateComments.getUpdatedDate().toString());
		}
		
		return candidateCommentsList;
	}
	
}
