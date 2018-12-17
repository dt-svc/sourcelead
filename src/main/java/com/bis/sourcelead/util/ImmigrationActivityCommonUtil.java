package com.bis.sourcelead.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationActivityHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;
/**
 * This is the commonutil for saving Immigration activities throughout the
 * application
 * 
 * @author: Ajith Matta
 * 
 */
@Component
public class ImmigrationActivityCommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(ImmigrationActivityCommonUtil.class);
	
	@Autowired
	private ImmigrationCommonDao immigrationCommonDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CandidateDao candidateDao;
	
	
	
	public void saveImmigrationActivities(User user, OrgLocations orgLocations, Immigration immigration, String actionOn, String actionDesc) throws Exception
	{
		ImmigrationActivityHistory immigrationActivityHistory = new ImmigrationActivityHistory();
		
		immigrationActivityHistory.setActionDesc(actionDesc);
		immigrationActivityHistory.setActionBy(user.getFullName());
		immigrationActivityHistory.setImmigrationId(immigration);
		immigrationActivityHistory.setOrgLocationId(orgLocations);
		immigrationActivityHistory.setCandidate(immigration.getCandidate());
		Candidate candidate = candidateDao.getCandidateByCandidateId(immigration.getCandidate().getId());
		User canUser = userDao.getUserByIdAndOrgLocationsId(candidate.getConsultantUser().getId(), orgLocations.getId());
		immigrationActivityHistory.setUserId(canUser);
		immigrationActivityHistory.setCreatedBy(user);
		immigrationActivityHistory.setUpdatedBy(user);
		immigrationActivityHistory.setStatus(ImmigrationConstants.STATUS_ACTIVE);
		immigrationActivityHistory.setCreatedDate(new Date());
		immigrationActivityHistory.setUpdatedDate(new Date());
		immigrationCommonDao.storeImmigrationActivities(immigrationActivityHistory);
	}
	
	public void saveImmigrationCandidateActivities(User user, OrgLocations orgLocations, Candidate candidate,Immigration immigration, String actionDesc) throws Exception
	{
		ImmigrationActivityHistory immigrationActivityHistory = new ImmigrationActivityHistory();
		
		immigrationActivityHistory.setActionDesc(actionDesc);
		immigrationActivityHistory.setActionBy(user.getFullName());
		immigrationActivityHistory.setImmigrationId(immigration);
		immigrationActivityHistory.setOrgLocationId(orgLocations);
		immigrationActivityHistory.setCandidate(candidate);
		User canUser = userDao.getUserByIdAndOrgLocationsId(candidate.getConsultantUser().getId(), orgLocations.getId());
		immigrationActivityHistory.setUserId(canUser);
		immigrationActivityHistory.setCreatedBy(user);
		immigrationActivityHistory.setUpdatedBy(user);
		immigrationActivityHistory.setStatus(ImmigrationConstants.STATUS_ACTIVE);
		immigrationActivityHistory.setCreatedDate(new Date());
		immigrationActivityHistory.setUpdatedDate(new Date());
		immigrationCommonDao.storeImmigrationActivities(immigrationActivityHistory);
	}
	
	
}
