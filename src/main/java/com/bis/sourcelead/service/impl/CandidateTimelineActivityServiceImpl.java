package com.bis.sourcelead.service.impl;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateTimelineActivityDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateTimelineActivityService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * 
 * @author Venkat Kesav
 * @date 17th July 2017
 *
 */
@Service
public class CandidateTimelineActivityServiceImpl implements CandidateTimelineActivityService {
	private static final Logger logger = LoggerFactory.getLogger(CandidateTimelineActivityServiceImpl.class);
	
	@Autowired
	CandidateTimelineActivityDao candidateTimelineActivityDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public List<CandidateTimelineActivity> getCandidateTimelineActivities(Long candidateId, Long orgLocationId) throws Exception
	{
		return candidateTimelineActivityDao.getCandidateTimelineActivities(candidateId, orgLocationId);
	}

	@Override
	public HashSet<CandidateTimelineActivity> getCandidateTimelineActivityDetails(User user, OrgLocations orgLocations,
			Long candidateId, String actionOn, String actionDesc, String actionSummary, String actionIcon)
			throws Exception {
		
		HashSet<CandidateTimelineActivity> candidateTimeLineList = new HashSet<CandidateTimelineActivity>(); 
		
		CandidateTimelineActivity candidateTimelineActivity = new CandidateTimelineActivity();
		CandidateGlobal candidateGlobal = new CandidateGlobal();
		candidateGlobal.setId(candidateId);
		candidateTimelineActivity.setCandidate(candidateGlobal);
		candidateTimelineActivity.setOrgLocationId(orgLocations);
		if(user !=null){
			candidateTimelineActivity.setActionBy(user.getFullName());
			candidateTimelineActivity.setCreatedUser(user);
			candidateTimelineActivity.setCreatedBy(user.getUserCode());
			candidateTimelineActivity.setUpdatedBy(user.getUserCode());
		}
		else{
			candidateTimelineActivity.setActionBy(null);
			candidateTimelineActivity.setCreatedUser(null);
			candidateTimelineActivity.setCreatedBy(null);
			candidateTimelineActivity.setUpdatedBy(null);
		}
		candidateTimelineActivity.setActionOn(actionOn);
		candidateTimelineActivity.setActionDesc(actionDesc);
		candidateTimelineActivity.setActionSummary(actionSummary);
		candidateTimelineActivity.setActionIcon(actionIcon);
		candidateTimelineActivity.setStatus(Constants.RECORD_ACTIVE);
		candidateTimelineActivity.setCreatedDate(commonUtil.currentDate());
		candidateTimelineActivity.setUpdatedDate(commonUtil.currentDate());
		
		return candidateTimeLineList;
	}
}
