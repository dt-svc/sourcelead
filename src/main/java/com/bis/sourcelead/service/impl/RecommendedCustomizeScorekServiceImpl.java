package com.bis.sourcelead.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RecommendedCustomizeScoreDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class RecommendedCustomizeScorekServiceImpl implements RecommendedCustomizeScoreService{
	
	private static final Logger logger = LoggerFactory.getLogger(RecommendedCustomizeScorekServiceImpl.class);

	@Autowired
	RecommendedCustomizeScoreDao recommendedCustomizeScoreDao;
	
	@Autowired
	HttpServletRequest request;
 
	@Autowired
	 private CommonUtil commonUtil;
	
	@Override
	public RecommendedCustomizeScore getRecommendedCustomizeScoreByRequirementIdAndOrgLocationId(Long requirementId, Long orgLocationId) throws Exception {
		return recommendedCustomizeScoreDao.getRecommendedCustomizeScoreByRequirementIdAndOrgLocationId(requirementId,orgLocationId);
	}
	
	@Override
	public RecommendedCustomizeScore updateCustomizeScore(RecommendedCustomizeScore recommendedCustomizeScore) throws Exception {
		recommendedCustomizeScore.setUpdatedDate(commonUtil.currentDate());
		return recommendedCustomizeScoreDao.saveCustomizeScore(recommendedCustomizeScore);
	}
	
	@Override
	public RecommendedCustomizeScore getRecommendedCustomizeScoreByRequirementId(Long requirementId)throws Exception {
		return recommendedCustomizeScoreDao.getRecommendedCustomizeScoreByRequirementId(requirementId);
	}


	
	
	@Override
	public RecommendedCustomizeScore saveCustomizeScore(Requirement requirement) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		RecommendedCustomizeScore res;
		
		res = getRecommendedCustomizeScoreByRequirementId(requirement.getId());
		if(null != res && null != res.getId()){
			 if(null != user.getUserCode()){
				 res.setUpdatedBy(user.getUserCode());	
		    }
			res.setUpdatedDate(commonUtil.currentDate());
		}else{
			res = new RecommendedCustomizeScore();
		}
		if(null != requirement){
			res.setReqirementId(requirement);	
		}
		if(null != orgLocations){
			res.setOrgLocationId(orgLocations);	
		}
		if(null != requirement.getSkills()){
			res.setCustomizeSkills(requirement.getSkills());	
		}
		if("" != requirement.getRate() && null != requirement.getRate()){
			res.setCustomizeRate(requirement.getRate());
		}
		if("" != requirement.getMinExp() && null != requirement.getMinExp()){
			res.setCustomizeMinExperience(requirement.getMinExp());	
		}
		if("" != null && null != requirement.getRateType()){
			res.setCustomizeRatetype(requirement.getRateType());	
		}
		
		res.setCustomizeSkillScore(Constants.SCORE_20);
		res.setCustomizeRateScore(Constants.SCORE_20);
		res.setCustomizeExperienceScore(Constants.SCORE_20);
		res.setCustomizeLocationScore(Constants.SCORE_10);
		res.setCustomizeNoticeperiodScore(Constants.SCORE_10);
		res.setCustomizeKeywordsScore(Constants.SCORE_20);
		if("" != requirement.getMaxExp() && null != requirement.getMaxExp()){
			res.setCustomizeMaxExperience(requirement.getMaxExp());	
		}
		if("" != requirement.getCountry() && null != requirement.getCountry()){
			res.setCustomizeCountry(requirement.getCountry());	
		}
		if("" != requirement.getState() && null != requirement.getState()){
			res.setCustomizeState(requirement.getState());	
		}
		if("" != requirement.getCity() && null != requirement.getCity()){
			res.setCustomizeCity(requirement.getCity());	
		}
		 if(user.getUserCode() != null){
			 res.setCreatedBy(user.getUserCode());	
	    }
		res.setCreatedDate(commonUtil.currentDate());
		
		return recommendedCustomizeScoreDao.saveCustomizeScore(res);
	}

}
	



