package com.bis.sourcelead.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateOnBoardingDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateOnBoardingService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class CandidateOnBoardingServiceImpl implements CandidateOnBoardingService{
	
	@Autowired
	CandidateOnBoardingDao candidateOnBoardingDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public CandidateOnBoarding saveCandidateOnBoarding(CandidateOnBoarding candidateOnBoarding) throws Exception {
		return candidateOnBoardingDao.saveCandidateOnBoarding(candidateOnBoarding);
	}

	@Override
	public CandidateOnBoarding saveOnBoardingCandidatesDetails(JSONObject jsonObj, OrgLocations orgLocations, User user)
			throws Exception {
		
		CandidateOnBoarding candidateOnBoarding = null;
		
		candidateOnBoarding = new CandidateOnBoarding();
		
		String[] onboardCandiate = jsonObj.getString("onboardCandiate").split(",");
		
		Candidate candidate = new Candidate();
		candidate.setId(Long.valueOf(onboardCandiate[0]));
		
		RequirementOffers offer = new RequirementOffers();
		offer.setId(Long.valueOf(onboardCandiate[1]));
		
		candidateOnBoarding.setCandidateId(candidate);
		candidateOnBoarding.setOfferId(offer);
		candidateOnBoarding.setFirstName(jsonObj.getString("firstName"));
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			candidateOnBoarding.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("address1") && !isNullOrEmpty(jsonObj.getString("address1"))){
			candidateOnBoarding.setAddress1(jsonObj.getString("address1"));
		}
		
		if(jsonObj.has("address2") && !isNullOrEmpty(jsonObj.getString("address2"))){
			candidateOnBoarding.setAddress2(jsonObj.getString("address2"));
		}
		
		if(jsonObj.has("country") && !isNullOrEmpty(jsonObj.getString("country"))){
			candidateOnBoarding.setCountry(jsonObj.getString("country"));
		}
		
		if(jsonObj.has("state") && !isNullOrEmpty(jsonObj.getString("state"))){
			candidateOnBoarding.setState(jsonObj.getString("state"));
		}
		
		if(jsonObj.has("city") && !isNullOrEmpty(jsonObj.getString("city"))){
			candidateOnBoarding.setCity(jsonObj.getString("city"));
		}
		
		if(jsonObj.has("zipCode") && !isNullOrEmpty(jsonObj.getString("zipCode"))){
			candidateOnBoarding.setZipCode(jsonObj.getString("zipCode"));
		}
		
		if(jsonObj.has("mobileNumber") && !isNullOrEmpty(jsonObj.getString("mobileNumber"))){
			candidateOnBoarding.setMobileNumber(jsonObj.getString("mobileNumber"));
		}
		
		if(jsonObj.has("personalEmail") && !isNullOrEmpty(jsonObj.getString("personalEmail"))){
			candidateOnBoarding.setPersonalEmail(jsonObj.getString("personalEmail"));
		}
		
		if(jsonObj.has("companyEmail") && !isNullOrEmpty(jsonObj.getString("companyEmail"))){
			candidateOnBoarding.setCompanyEmail(jsonObj.getString("companyEmail"));
		}
		
		
		if(jsonObj.has("onboardCandidateRole") && !isNullOrEmpty(jsonObj.getString("onboardCandidateRole"))){
			Roles roles = new Roles();
			roles.setId(Long.valueOf(jsonObj.getString("onboardCandidateRole")));
			candidateOnBoarding.setRoleId(roles);
		}
		
		if(jsonObj.has("startDate") && !isNullOrEmpty(jsonObj.getString("startDate"))){
			candidateOnBoarding.setStartDate(commonUtil.getDateFormat(jsonObj.getString("startDate")));
		}
		
		if(jsonObj.has("endDate") && !isNullOrEmpty(jsonObj.getString("endDate"))){
			candidateOnBoarding.setEndDate(commonUtil.getDateFormat(jsonObj.getString("endDate")));
		}
		
		if(jsonObj.has("salaryRate") && !isNullOrEmpty(jsonObj.getString("salaryRate"))){
			candidateOnBoarding.setSalaryRate(jsonObj.getString("salaryRate"));
		}
		
		if(jsonObj.has("reportingManager") && !isNullOrEmpty(jsonObj.getString("reportingManager"))){
			User reportingManager = new User();
			reportingManager.setId(Long.valueOf(jsonObj.getString("reportingManager")));
			candidateOnBoarding.setManagerUserId(reportingManager);
		}
		
		if(jsonObj.has("team") && !isNullOrEmpty(jsonObj.getString("team"))){
			Team team = new Team();
			team.setId(Long.valueOf(jsonObj.getString("team")));
			candidateOnBoarding.setTeamId(team);
		}
		
		if(jsonObj.has("employmentType") && !isNullOrEmpty(jsonObj.getString("employmentType"))){
			candidateOnBoarding.setEmploymentType(jsonObj.getString("employmentType"));
		}
		
		if(jsonObj.has("employmentType") && !isNullOrEmpty(jsonObj.getString("employmentType"))){
			candidateOnBoarding.setEmploymentType(jsonObj.getString("employmentType"));
		}
		
		if(jsonObj.has("probationNotice") && !isNullOrEmpty(jsonObj.getString("probationNotice"))){
			candidateOnBoarding.setProbationNotice(jsonObj.getString("probationNotice"));
		}
		
		if(jsonObj.has("terminationNotice") && !isNullOrEmpty(jsonObj.getString("terminationNotice"))){
			candidateOnBoarding.setTerminationNotice(jsonObj.getString("terminationNotice"));
		}
		
		if(jsonObj.has("dueDate") && !isNullOrEmpty(jsonObj.getString("dueDate"))){
			candidateOnBoarding.setDueDate(commonUtil.getDateFormat(jsonObj.getString("dueDate")));
		}
		
		
		if(jsonObj.has("specialConditions") && !isNullOrEmpty(jsonObj.getString("specialConditions"))){
			candidateOnBoarding.setSpecialConditions(jsonObj.getString("specialConditions"));
		}
		
		if(jsonObj.has("onboardingTemplate") && !isNullOrEmpty(jsonObj.getString("onboardingTemplate"))){
			
			String templateIds = jsonObj.getString("onboardingTemplate");
			templateIds = templateIds.replaceAll("\\[", "").replaceAll("\\]","");
			templateIds = templateIds.replaceAll("\"", "");
			candidateOnBoarding.setOnboardTemplateId(templateIds);
		}
		
		candidateOnBoarding.setOrgLocationId(orgLocations);
		candidateOnBoarding.setStatus(Constants.CANDIDATE_ACTIVE);
		candidateOnBoarding.setOnboardCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		candidateOnBoarding.setCandidateOnBoardStatus("Draft");
		candidateOnBoarding.setCreatedDate(CommonUtil.currentDate());
		candidateOnBoarding.setCreatedBy(user.getUserCode());
		candidateOnBoarding.setUpdatedDate(CommonUtil.currentDate());
		candidateOnBoarding.setUpdatedBy(user.getUserCode());
		
			
		candidateOnBoardingDao.saveCandidateOnBoarding(candidateOnBoarding);
		
		return candidateOnBoarding;
	}

}
