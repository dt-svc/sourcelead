package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.beans.CandidateCompareDTO;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RecommendedCustomizeScoreDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.service.RequirementLocationsService;
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
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
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
		OrgLocations orgLocations = orgLocationService.getOrgLocations(requirement.getOrgLocationId().getId());
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
		if(null != requirement.getRate()){
			res.setCustomizeRate(requirement.getRate());
		}
		if(null != requirement.getMinExp()){
			res.setCustomizeMinExperience(requirement.getMinExp());	
		}else {
			res.setCustomizeMinExperience(null);	
		}
		if(null != requirement.getRateType()){
			res.setCustomizeRatetype(requirement.getRateType());	
		}
		if(null != requirement.getNoticePeriod()){
			res.setCustomizeNoticeperiod(requirement.getNoticePeriod());	
		}else {
			res.setCustomizeNoticeperiod(null);	
		}
		
		if(null != requirement.getVisaType()){
			res.setCustomizeVisatype(requirement.getVisaType());
		}else {
			res.setCustomizeVisatype(null);
		}
		if(null != requirement.getEducation()){
			res.setCustomizeEducation(requirement.getEducation());
		}else {
			res.setCustomizeEducation(null);
		}
		
		if(orgLocations.getRecommendedSkillPercent() != null) {
			res.setCustomizeSkillScore(String.valueOf(orgLocations.getRecommendedSkillPercent()));
		}else {
			res.setCustomizeSkillScore(Constants.SCORE_20);
		}
		
		if(orgLocations.getRecommendedRatePercent() != null) {
			res.setCustomizeRateScore(String.valueOf(orgLocations.getRecommendedRatePercent()));
		}else {
			res.setCustomizeRateScore(Constants.SCORE_10);
		}
		
		if(orgLocations.getRecommendedExpPercent() != null) {
			res.setCustomizeExperienceScore(String.valueOf(orgLocations.getRecommendedExpPercent()));
		}else {
			res.setCustomizeExperienceScore(Constants.SCORE_10);
		}
		
		if(orgLocations.getRecommendedLocationPercent() != null) {
			res.setCustomizeLocationScore(String.valueOf(orgLocations.getRecommendedLocationPercent()));
		}else {
			res.setCustomizeLocationScore(Constants.SCORE_10);
		}
		
		if(orgLocations.getRecommendedNoticePercent() != null) {
			res.setCustomizeNoticeperiodScore(String.valueOf(orgLocations.getRecommendedNoticePercent()));
		}else {
			res.setCustomizeNoticeperiodScore(Constants.SCORE_10);
		}
		
		if(orgLocations.getRecommendedKeywordsPercent() != null) {
			res.setCustomizeKeywordsScore(String.valueOf(orgLocations.getRecommendedKeywordsPercent()));
		}else {
			res.setCustomizeKeywordsScore(Constants.SCORE_20);
		}
		
		if(orgLocations.getRecommendedVisatypePercent() != null) {
			res.setCustomizeVisatypecore(String.valueOf(orgLocations.getRecommendedVisatypePercent()));
		}else {
			res.setCustomizeVisatypecore(Constants.SCORE_10);
		}
		
		if(orgLocations.getRecommendedEducationPercent() != null) {
			res.setCustomizeEducationScore(String.valueOf(orgLocations.getRecommendedEducationPercent()));
		}else {
			res.setCustomizeEducationScore(Constants.SCORE_10);
		}
		
		
		if(null != requirement.getMaxExp() && !requirement.getMaxExp().isEmpty()){
			res.setCustomizeMaxExperience(requirement.getMaxExp());	
		}else {
			res.setCustomizeMaxExperience(null);	
		}
		/*if("" != requirement.getCountry() && null != requirement.getCountry()){
			res.setCustomizeCountry(requirement.getCountry());	
		}
		if("" != requirement.getState() && null != requirement.getState()){
			res.setCustomizeState(requirement.getState());	
		}
		if("" != requirement.getCity() && null != requirement.getCity()){
			res.setCustomizeCity(requirement.getCity());	
		}*/
		
		
		StringBuilder country = new StringBuilder();
		StringBuilder state = new StringBuilder();
		StringBuilder city = new StringBuilder();
		
		Set<String> countrySet = new HashSet<>();
		Set<String> stateSet = new HashSet<>();
		Set<String> citySet = new HashSet<>();
		
		List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
		if(locationsList != null && !locationsList.isEmpty()) {
			for(RequirementLocations locations : locationsList) {
				if(StringUtils.isNotBlank(locations.getState())) {
					stateSet.add(locations.getState());
				}
				
				if(StringUtils.isNotBlank(locations.getCity())) {
					citySet.add(locations.getCity());
				}
			}
		}
		
		if(countrySet != null && !countrySet.isEmpty()) {
			for(String reqCountry : countrySet) {
				country = country.append(reqCountry).append(",");
			}
		}
		
		if(stateSet != null && !stateSet.isEmpty()) {
			for(String reqState : stateSet) {
				state = state.append(reqState).append(",");
			}
		}
		
		if(citySet != null && !citySet.isEmpty()) {
			for(String reqCity : citySet) {
				city = city.append(reqCity).append(",");
			}
		}
		
		if(country.toString() != null && !country.toString().isEmpty()) {
			String countryStr = country.toString();
			countryStr = countryStr.substring(0, countryStr.length() - 1);
			res.setCustomizeCountry(countryStr);
		}
		
		if(state.toString() != null && !state.toString().isEmpty()) {
			String stateStr = state.toString();
			stateStr = stateStr.substring(0, stateStr.length() - 1);
			res.setCustomizeState(stateStr);
		}
		
		if(city.toString() != null && !city.toString().isEmpty()) {
			String cityStr = city.toString();
			cityStr = cityStr.substring(0, cityStr.length() - 1);
			res.setCustomizeCity(cityStr);
		}
		
		
		 if(user.getUserCode() != null){
			 res.setCreatedBy(user.getUserCode());	
	    }
		res.setCreatedDate(commonUtil.currentDate());
		
		return recommendedCustomizeScoreDao.saveCustomizeScore(res);
	}

	@Override
	public CandidateCompareDTO getCandidateListToCompare(Long requirementId, Long orgLocationId,String candidatesSelected,CandidateDTO candidateDTO) throws Exception
	{
		OrgLocations orglocation = orgLocationService.getOrgLocations(orgLocationId);
		CandidateCompareDTO candidateCompare = new CandidateCompareDTO();
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		List<CandidateIndex> candidateList = null;
		List<CandidateIndex> totalCandidateList = null;
		RecommendedCustomizeScore recommendedCustomizeScore = getRecommendedCustomizeScoreByRequirementId(requirementId);
		if (null != recommendedCustomizeScore) {
			ResponseList<CandidateIndex> responseList = recommendationsService.getRecommendationsUpdateWithScoreByUsingElk(orgLocationId, recommendedCustomizeScore, requirementId,candidatesSelected,candidateDTO);
			candidateList = responseList.getData();
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
			
			if (requirement.getRequirementLocations() != null) {
				List<String> rls = new ArrayList<>();
				for(RequirementLocations rl : requirement.getRequirementLocations()) {
					rls.add(rl.getLocation());
				}
				candidateCompare.setJobLocationList(rls);
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
			if (requirement.getEducation() != null
					&& !"".equalsIgnoreCase(requirement.getEducation())) {
				candidateCompare.setJobEducation(requirement.getEducation());
			}
		}
		if (candidateList != null) {
			for(int i = 0; i<candidateList.size(); i++){
				CandidateIndex candidate = candidateList.get(i);
				Candidate can = candidateService.getCandidateByCandidateId(candidate.getCandidateId());
				CandidateResumes candidateResume =  candidateResumeSerivce.getCandidateLatesResumes(can.getId(), Constants.RECORD_ACTIVE);
				if(candidateResume != null){ 
					candidate.setCurrentResumeId(candidateResume.getId());
					candidate.setFileUrl(candidateResume.getFileURL());
					candidate.setCurrentResumeName(candidateResume.getFileName());
					candidate.setCurrentResumeUUID(candidateResume.getResumeUUID());
					candidate.setFileContentType(candidateResume.getFileContentType());
				}
				int percentage = candidateService.getCandidatePercentage(can,orglocation);
				candidate.setProfilePercentage(percentage);
			}
			candidateCompare.setCandidateList(candidateList);
			
		/*	ResponseList<CandidateIndex> responseList = recommendationsService.getRecommendationsUpdateWithScoreByUsingElk(orgLocationId, recommendedCustomizeScore, requirementId,null,candidateDTO);
			totalCandidateList = responseList.getData();
			candidateCompare.setTotalCandidateList(totalCandidateList);*/
		}
		return candidateCompare;
	}

}
	



