package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RecommendationsDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.rest.RequirementRestController;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateSkillsService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RequirementSubmissionService;

@Service
public class RecommendationsServiceImpl implements RecommendationsService{
	
	private static final Logger logger = LoggerFactory.getLogger(RecommendationsServiceImpl.class);
	
	@Autowired
	private RecommendationsDao recommendationsDao;
	
	 @Autowired
	 private CandidateSkillsService candidateSkillsService;
	 
	 @Autowired
	 private CandidateResumesService candidateResumeSerivce;
	 
	 @Autowired
	 private  RequirementSubmissionService requirementSubmissionService;
	
	@Override
	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId) throws Exception {
		return recommendationsDao.getAllCandidatesByLocation(orgLocationId);
	}

	@Override
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception {
		Requirement requirement = recommendationsDao.getRequirementByRequirementId(requirementId);
		requirement.setRequirementCreatedDateToString(Constants.DF_YMD.format(requirement.getCreatedDate()));
		return requirement;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Candidate> getRecommendationsWithScore(List<Candidate> candidateList, Requirement requirement) throws Exception {
		float expPercentage = 0; //  experience = 20%
		float locationPercentage = 0; // location 10%
		float ratePercentage = 0;    // rate 20 %
		float skillPercentage = 0; // skill = 50%
		float percentPerSkill = 0;
		List<Candidate> CandidateListcopy = new ArrayList<>();
		String reqSkills = "";
		if(requirement.getSkills() != null && !"".equalsIgnoreCase(requirement.getSkills())){
		     reqSkills = requirement.getSkills();
			String [] reqMaxSkills = reqSkills.split(",");
			 percentPerSkill = 50/(float)reqMaxSkills.length;
		}
		int reqMinExp = 0;
		int reqMaxExp = 0;
		float percentPerMonthExp = 0;
		if(requirement.getMinExp() !=null && !"".equalsIgnoreCase(requirement.getMinExp()) && requirement.getMaxExp() !=null && !"".equalsIgnoreCase(requirement.getMaxExp())){
			 reqMinExp = Integer.parseInt(requirement.getMinExp());
			 reqMaxExp = Integer.parseInt(requirement.getMaxExp());
			int reqExp = reqMaxExp *12 ;
			 percentPerMonthExp = 20/(float)reqExp;
		}
		String reqCity = "";
		if(requirement.getCity() != null && !"".equalsIgnoreCase(requirement.getCity())){
			 reqCity = requirement.getCity();
		}
		float reqPerMonthSal = 0;
		if(requirement.getRate() !=null && !"".equalsIgnoreCase(requirement.getRate())){
			int reqRate = Integer.parseInt(requirement.getRate());
			String reqRateType = requirement.getRateType();
			if("Hourly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate *24;
				reqPerMonthSal = perDaySal * 30;
			}else if("Weekly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate /7;
				reqPerMonthSal = perDaySal * 30;
			}else if("Bi-weekly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate /14;
				reqPerMonthSal = perDaySal * 30;
			}else if("Semi-Monthly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate /15;
				reqPerMonthSal = perDaySal * 30;
			}else if("Monthly".equalsIgnoreCase(reqRateType)){
				reqPerMonthSal = reqRate;
			}else if("Bi-Monthly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate /60;
				reqPerMonthSal = perDaySal * 30;
			}else{
				reqPerMonthSal = reqRate /12;
			}
		}
		
		for(Candidate candidate : candidateList){
			boolean isCandidateAdded = false;
			int matchedSkillCount = 0;
			List<CandidateSkills> candidateSkillList  = candidateSkillsService.getSkillByCandidateId(candidate.getId());
			List<RequirementSubmission> reqList = requirementSubmissionService.getRequirementPerCandidate(candidate.getId());
			CandidateResumes candidateResume = candidateResumeSerivce.getCandidateLatesResumes(candidate.getId(), 1);
			
			if(candidateSkillList != null && candidateSkillList.size() >0){
				for(CandidateSkills candidateSkills : candidateSkillList){
					if(candidateSkills.getName() !=null && !"".equalsIgnoreCase(candidateSkills.getName())){
						if(reqSkills.contains(candidateSkills.getName())){
							matchedSkillCount++;
						}
						
					}
				}
			}
			skillPercentage = matchedSkillCount*percentPerSkill;
			if(candidate.getExperienceYears() != null && !"".equalsIgnoreCase(candidate.getExperienceYears()) && candidate.getExperienceMonths()!=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
				int  candidateExpMonths = Integer.parseInt(candidate.getExperienceYears())*12 + Integer.parseInt(candidate.getExperienceMonths());
				if(candidateExpMonths < reqMinExp *12){
					expPercentage = 0;
				}else if(candidateExpMonths >= reqMinExp *12 && candidateExpMonths <= reqMaxExp *12){
					expPercentage = percentPerMonthExp*(float)candidateExpMonths;
				}else{
					expPercentage = 20;
				}
			}
			if(candidate.getCity() != null && !"".equalsIgnoreCase(candidate.getCity())){
				if(candidate.getCity().equalsIgnoreCase(reqCity)){
					locationPercentage = 10;
				}
			}
			
			if(candidate.getExpectedRate() != null && !"".equalsIgnoreCase(candidate.getExpectedRate()) && candidate.getRateType() !=null && !"".equalsIgnoreCase(candidate.getRateType())){
				String candidateRateType = candidate.getRateType();
				String  expectedRate = candidate.getExpectedRate();
				
				int reqRate = Integer.parseInt(expectedRate);
				float perMonthCandidateSal = 0;
				if("Hourly".equalsIgnoreCase(candidateRateType)){
					int perDaySal = reqRate *24;
					perMonthCandidateSal = perDaySal * 30;
				}else if("Weekly".equalsIgnoreCase(candidateRateType)){
					int perDaySal = reqRate /7;
					perMonthCandidateSal = perDaySal * 30;
				}else if("Bi-weekly".equalsIgnoreCase(candidateRateType)){
					int perDaySal = reqRate /14;
					perMonthCandidateSal = perDaySal * 30;
				}else if("Semi-Monthly".equalsIgnoreCase(candidateRateType)){
					int perDaySal = reqRate /15;
					perMonthCandidateSal = perDaySal * 30;
				}else if("Monthly".equalsIgnoreCase(candidateRateType)){
					perMonthCandidateSal = reqRate;
				}else if("Bi-Monthly".equalsIgnoreCase(candidateRateType)){
					int perDaySal = reqRate /60;
					perMonthCandidateSal = perDaySal * 30;
				}else{
					perMonthCandidateSal = reqRate /12;
				}
				
				if(perMonthCandidateSal<=reqPerMonthSal){
					ratePercentage = 20;
				}
			}
			float totalPercentage = skillPercentage+expPercentage+locationPercentage+ratePercentage;
			
			double roundOff = Math.round(totalPercentage * 100.0) / 100.0;
			String candidateScore = String.valueOf(roundOff);
			candidate.setCandidateScore(candidateScore);
			candidate.setNoOfReq(reqList.size());
			if(candidateResume.getTitle() !=null && !"".equalsIgnoreCase(candidateResume.getTitle())){
				candidate.setCandidateResume(candidateResume.getTitle());
			}else{
				candidate.setCandidateResume(candidateResume.getFileName());
			}
			if(reqList.size() >0){
				
				for(RequirementSubmission reqSubmission : reqList){
					if(requirement.getId().equals(reqSubmission.getRequirement().getId())){
						isCandidateAdded = true;
					}
					reqSubmission.setRequirementName(reqSubmission.getRequirement().getJobTitle());
					reqSubmission.setRequirementStage(reqSubmission.getSubmissionStatus());
					candidate.setReqSubmissionId(reqSubmission.getId().toString());
				}
			}
			candidate.setRequirementSubmisstions(reqList);
			candidate.setIsCandidateAdedd(isCandidateAdded);
			
			if(skillPercentage !=0 && totalPercentage !=0 ){
				CandidateListcopy.add(candidate);
			}
			
			
		}
		 Collections.sort(CandidateListcopy, new Comparator() {  
	            @Override  
	            public int compare(Object obj1, Object obj2) {  
	            	Candidate candidate1 = (Candidate)obj1;  
	            	Candidate candidate2 = (Candidate)obj2; 
	            	return candidate2.getCandidateScore().compareToIgnoreCase(candidate1.getCandidateScore());  
	            }  
	        });
		return CandidateListcopy;
	
	}

}
