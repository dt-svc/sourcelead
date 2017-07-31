package com.bis.sourcelead.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.beans.CandidateCompareDTO;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RecommendedCustomizeScoreDao;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.service.RequirementService;
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
	
	@Autowired
	private CandidateResumesService candidateResumeSerivce;
	 
	@Autowired
	private CandidateService candidateService;
 
	@Autowired
	private RecommendationsService recommendationsService;
 
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
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
			res.setOrgLocationId(requirement.getOrgLocationId());	
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
		if("" != null && null != requirement.getNoticePeriod()){
			res.setCustomizeNoticeperiod(requirement.getNoticePeriod());	
		}
		
		if(null != requirement.getVisaType()){
			res.setCustomizeVisatype(requirement.getVisaType());
		}
		if(null != requirement.getEducation()){
			res.setCustomizeEducation(requirement.getEducation());
		}
		
		res.setCustomizeSkillScore(Constants.SCORE_20);
		res.setCustomizeRateScore(Constants.SCORE_10);
		res.setCustomizeExperienceScore(Constants.SCORE_10);
		res.setCustomizeLocationScore(Constants.SCORE_10);
		res.setCustomizeNoticeperiodScore(Constants.SCORE_10);
		res.setCustomizeKeywordsScore(Constants.SCORE_20);
		res.setCustomizeVisatypecore(Constants.SCORE_10);
		res.setCustomizeEducationScore(Constants.SCORE_10);
		
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

	@Override
	public CandidateCompareDTO getCandidateListToCompare(Long requirementId, Long orgLocationId) throws Exception
	{
		OrgLocations orglocation = orgLocationService.getOrgLocations(orgLocationId);
		CandidateCompareDTO candidateCompare = new CandidateCompareDTO();
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		List<CandidateIndex> candidateList = null;
		RecommendedCustomizeScore recommendedCustomizeScore = getRecommendedCustomizeScoreByRequirementId(requirementId);
		if (null != recommendedCustomizeScore) {
			candidateList = recommendationsService.getRecommendationsUpdateWithScoreByUsingElk(orgLocationId, recommendedCustomizeScore, requirementId);
		}
		if (requirement != null) {
			if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
				candidateCompare.setJobId(requirement.getJobId());
			}
			if (requirement.getJobTitle() != null
					&& !"".equalsIgnoreCase(requirement.getJobTitle())) {
				candidateCompare.setJobTitile(requirement.getJobTitle());
			}
			if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
				candidateCompare.setJobRate(requirement.getRate());
			}
			if (requirement.getRateType() != null
					&& !"".equalsIgnoreCase(requirement.getRateType())) {
				candidateCompare.setJobRatePer(requirement.getRateType());
			}
			if (requirement.getSkills() != null && !"".equalsIgnoreCase(requirement.getSkills())) {
				candidateCompare.setJobSkills(requirement.getSkills());
			}
			if (requirement.getVisaType() != null
					&& !"".equalsIgnoreCase(requirement.getVisaType())) {
				candidateCompare.setJobVisa(requirement.getVisaType());
			}
			if (requirement.getLocation() != null
					&& !"".equalsIgnoreCase(requirement.getLocation())) {
				candidateCompare.setJobLocation(requirement.getLocation());
			}
			if (requirement.getMinExp() != null
					&& !"".equalsIgnoreCase(requirement.getMinExp())) {
				candidateCompare.setJobMinExp(requirement.getMinExp());
			}
			if (requirement.getMaxExp() != null
					&& !"".equalsIgnoreCase(requirement.getMaxExp())) {
				candidateCompare.setJobMaxExp(requirement.getMaxExp());
			}
			if (requirement.getNoticePeriod() != null
					&& !"".equalsIgnoreCase(requirement.getNoticePeriod())) {
				candidateCompare.setJobNoticePeriod(requirement.getNoticePeriod());
			}
		}
		if (candidateList != null) {
			for(int i = 0; i<candidateList.size(); i++){
				CandidateIndex candidate = candidateList.get(i);
				Candidate can = candidateService.getCandidateByCandidateId(candidate.getCandidateId());
				CandidateResumes candidateResume =  candidateResumeSerivce.getCandidateLatesResumes(can.getId(), Constants.RECORD_ACTIVE);
				if(candidateResume != null){
					candidate.setCurrentResumeId(candidateResume.getId());
					candidate.setCurrentResumeName(candidateResume.getFileName());
					candidate.setCurrentResumeUUID(candidateResume.getResumeUUID());
				}
				int percentage = candidateService.getCandidatePercentage(can,orglocation);
				candidate.setProfilePercentage(percentage);
			}
			candidateCompare.setCandidateList(candidateList);
		}
		return candidateCompare;
	}

}
	



