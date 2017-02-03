package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RecommendationsDao;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateSkillsService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RequirementSubmissionService;

@Service
public class RecommendationsServiceImpl implements RecommendationsService{
	
	private static final Logger logger = LoggerFactory.getLogger(RecommendationsServiceImpl.class);
	
	@Autowired
	private RecommendationsDao recommendationsDao;
	
	 @Autowired
	 private CandidateService candidateService;
	 
	 @Autowired
	 private CandidateResumesService candidateResumeSerivce;
	 
	 @Autowired
	 private  RequirementSubmissionService requirementSubmissionService;
	
	 @Autowired
	 private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 private CandidateSkillsService candidateSkillsService;
	 
	 
	 
	 
	 
	 
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
		
		float locationPercentage = 0; // location 10%
		float ratePercentage = 0;    // rate 20 %
		float skillPercentage = 0; // skill = 50%
		float percentPerSkill = 0;
		List<Candidate> CandidateListcopy = new ArrayList<>();
		String reqSkills = "";
		if(null != requirement.getSkills() && !"".equalsIgnoreCase(requirement.getSkills())){
		     reqSkills = requirement.getSkills();
			String [] reqMaxSkills = reqSkills.split(",");
			 percentPerSkill = 50/(float)reqMaxSkills.length;
		}
		int reqMinExp = 0;
		int reqMaxExp = 0;
		float percentPerMonthExp = 0;
		if(null != requirement.getMinExp() && !"".equalsIgnoreCase(requirement.getMinExp()) && null != requirement.getMaxExp() && !"".equalsIgnoreCase(requirement.getMaxExp())){
			 reqMinExp = Integer.parseInt(requirement.getMinExp());
			 reqMaxExp = Integer.parseInt(requirement.getMaxExp());
			int reqExp = reqMaxExp *12 ;
			 percentPerMonthExp = 20/(float)reqExp;
		}
		String reqCity = "";
		if(null != requirement.getCity() && !"".equalsIgnoreCase(requirement.getCity())){
			 reqCity = requirement.getCity();
		}
		float reqPerMonthSal = 0;
		if(null != requirement.getRate() && !"".equalsIgnoreCase(requirement.getRate())){
			int reqRate = Integer.parseInt(requirement.getRate());
			String reqRateType = requirement.getRateType();
			if("Hourly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate *9;
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
			float expPercentage = 0; //  experience = 20%
			int matchedSkillCount = 0;
    //		List<CandidateSkills> candidateSkillList  = candidateSkillsService.getSkillByCandidateId(candidate.getId());
			
			
			List<CandidateSkills> candidateSkillList = candidateSkillsService.getCandidateSkillsByCandidateId(candidate.getId().toString());
			
			  // Changed the below method by  bringing from  service as cache is implemented  for  the above method
			
			   // Set<CandidateSkills> candidateSkillList = candidate.getCandidateSkills();
			List<RequirementSubmission> reqList = requirementSubmissionService.getRequirementPerCandidate(candidate.getId());
			CandidateResumes candidateResume = candidateResumeSerivce.getCandidateLatesResumes(candidate.getId(), 1);
			
			if(null != candidateSkillList && candidateSkillList.size() >0){
				for(CandidateSkills candidateSkills : candidateSkillList){
					if(candidateSkills.getName() !=null && !"".equalsIgnoreCase(candidateSkills.getName())){
						if(reqSkills.contains(candidateSkills.getName())){
							matchedSkillCount++;
						}
						
					}
				}
			}
			skillPercentage = matchedSkillCount*percentPerSkill;
			if(null != candidate.getExperienceYears() && !"".equalsIgnoreCase(candidate.getExperienceYears()) && null != candidate.getExperienceMonths() && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
				int  candidateExpMonths = Integer.parseInt(candidate.getExperienceYears())*12 + Integer.parseInt(candidate.getExperienceMonths());
				if(candidateExpMonths < reqMinExp *12){
					expPercentage = 0;
				}else if(candidateExpMonths >= reqMinExp *12 && candidateExpMonths <= reqMaxExp *12){
					expPercentage = percentPerMonthExp*(float)candidateExpMonths;
				}else{
					expPercentage = 20;
				}
			}
			if(null != candidate.getCity() && !"".equalsIgnoreCase(candidate.getCity())){
				if(candidate.getCity().equalsIgnoreCase(reqCity)){
					locationPercentage = 10;
				}
			}
			
			if(null != candidate.getExpectedRate() && !"".equalsIgnoreCase(candidate.getExpectedRate()) && null != candidate.getRateType() && !"".equalsIgnoreCase(candidate.getRateType())){
				String candidateRateType = candidate.getRateType();
				String  expectedRate = candidate.getExpectedRate();
				
				int reqRate = Integer.parseInt(expectedRate);
				float perMonthCandidateSal = 0;
				if("Hourly".equalsIgnoreCase(candidateRateType)){
					int perDaySal = reqRate *9;
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
			if(null != candidateResume){
					if(candidateResume.getTitle() !=null && !"".equalsIgnoreCase(candidateResume.getTitle())){
						candidate.setCandidateResume(candidateResume.getTitle());
					}else{
						candidate.setCandidateResume(candidateResume.getFileName());
					}
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Candidate> getRecommendationsWithScoreByUsingElk(Long orgLocationId,Requirement requirement) throws Exception {
		logger.info("....getRecommendationsWithScoreByUsingElk Method Started...");
		float locationPercentage = 0; // location 10%
		float ratePercentage = 0;    // rate 20 %
		float skillPercentage = 0; // skill = 50%
		float percentPerSkill = 0;
		List<Candidate> CandidateListcopy = new ArrayList<>();
		String reqSkills = "";

		if(null != requirement.getSkills() && !"".equalsIgnoreCase(requirement.getSkills())){
			Long maxResults = candidateService.getCandidatesCountByOrgLocationID(orgLocationId,null);
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocationId));
			 boolQueryBuilder.must(QueryBuilders.matchQuery("status",1));
		     reqSkills = requirement.getSkills();
			String [] reqMaxSkills = reqSkills.split(",");
			if(reqMaxSkills != null && reqMaxSkills.length >0){
				for(String skill : reqMaxSkills){
					boolQueryBuilder.should(QueryBuilders.matchQuery("skills", skill));
				}
			}
			boolQueryBuilder.minimumShouldMatch("1");
			SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
			
			if(searchHits != null && searchHits.length >0){
				percentPerSkill = 50/(float)reqMaxSkills.length;
				int reqMinExp = 0;
				int reqMaxExp = 0;
				float percentPerMonthExp = 0;
				if(null != requirement.getMinExp() && !"".equalsIgnoreCase(requirement.getMinExp()) && null != requirement.getMaxExp()  && !"".equalsIgnoreCase(requirement.getMaxExp())){
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
				if(null != requirement.getRate() && !"".equalsIgnoreCase(requirement.getRate())){
					int reqRate = Integer.parseInt(requirement.getRate());
					String reqRateType = requirement.getRateType();
					if("Hourly".equalsIgnoreCase(reqRateType)){
						int perDaySal = reqRate *9;
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
				
				
				for (SearchHit hit : searchHits) {
					Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(hit.getId()));

					boolean isCandidateAdded = false;
					float expPercentage = 0; //  experience = 20%
					int matchedSkillCount = 0;
					Set<CandidateSkills> candidateSkillList = candidate.getCandidateSkills();
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
					if(null != candidate.getExperienceYears() && !"".equalsIgnoreCase(candidate.getExperienceYears()) && null != candidate.getExperienceMonths()  && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
						int  candidateExpMonths = Integer.parseInt(candidate.getExperienceYears())*12 + Integer.parseInt(candidate.getExperienceMonths());
						if(candidateExpMonths < reqMinExp *12){
							expPercentage = 0;
						}else if(candidateExpMonths >= reqMinExp *12 && candidateExpMonths <= reqMaxExp *12){
							expPercentage = percentPerMonthExp*(float)candidateExpMonths;
						}else{
							expPercentage = 20;
						}
					}
					if(null != candidate.getCity() && !"".equalsIgnoreCase(candidate.getCity())){
						if(candidate.getCity().equalsIgnoreCase(reqCity)){
							locationPercentage = 10;
						}
					}
					
					if(null != candidate.getExpectedRate() && !"".equalsIgnoreCase(candidate.getExpectedRate()) && null != candidate.getRateType() && !"".equalsIgnoreCase(candidate.getRateType())){
						String candidateRateType = candidate.getRateType();
						String  expectedRate = candidate.getExpectedRate();
						
						int reqRate = Integer.parseInt(expectedRate);
						float perMonthCandidateSal = 0;
						if("Hourly".equalsIgnoreCase(candidateRateType)){
							int perDaySal = reqRate *9;
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
					if(null != candidateResume){
							if(null != candidateResume.getTitle() && !"".equalsIgnoreCase(candidateResume.getTitle())){
								candidate.setCandidateResume(candidateResume.getTitle());
							}else{
								candidate.setCandidateResume(candidateResume.getFileName());
							}
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
				
			 }
		 }
		logger.info("....getRecommendationsWithScoreByUsingElk Method Ended...");
		return CandidateListcopy;
	}

	@Override
	public String getCandidateScore(Candidate candidate, Requirement requirement) throws Exception {
		
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
		if(null != requirement.getMinExp() && !"".equalsIgnoreCase(requirement.getMinExp()) && null != requirement.getMaxExp() && !"".equalsIgnoreCase(requirement.getMaxExp())){
			 reqMinExp = Integer.parseInt(requirement.getMinExp());
			 reqMaxExp = Integer.parseInt(requirement.getMaxExp());
			int reqExp = reqMaxExp *12 ;
			 percentPerMonthExp = 20/(float)reqExp;
		}
		String reqCity = "";
		if(null != requirement.getCity() && !"".equalsIgnoreCase(requirement.getCity())){
			 reqCity = requirement.getCity();
		}
		float reqPerMonthSal = 0;
		if(null != requirement.getRate() && !"".equalsIgnoreCase(requirement.getRate())){
			int reqRate = Integer.parseInt(requirement.getRate());
			String reqRateType = requirement.getRateType();
			if("Hourly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate *9;
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
		
			boolean isCandidateAdded = false;
			float expPercentage = 0; //  experience = 20%
			int matchedSkillCount = 0;
			
			List<CandidateSkills> candidateSkillList = candidateSkillsService.getCandidateSkillsByCandidateId(candidate.getId().toString());
			
			  // Changed the below method by  bringing from  service as cache is implemented  for  the above method
			
			   // Set<CandidateSkills> candidateSkillList = candidate.getCandidateSkills();
			List<RequirementSubmission> reqList = requirementSubmissionService.getRequirementPerCandidate(candidate.getId());
			CandidateResumes candidateResume = candidateResumeSerivce.getCandidateLatesResumes(candidate.getId(), 1);
			
			if(null != candidateSkillList && candidateSkillList.size() >0){
				for(CandidateSkills candidateSkills : candidateSkillList){
					if(null != candidateSkills.getName() && !"".equalsIgnoreCase(candidateSkills.getName())){
						if(reqSkills.contains(candidateSkills.getName())){
							matchedSkillCount++;
						}
						
					}
				}
			}
			skillPercentage = matchedSkillCount*percentPerSkill;
			if(null != candidate.getExperienceYears() && !"".equalsIgnoreCase(candidate.getExperienceYears()) && null != candidate.getExperienceMonths() && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
				int  candidateExpMonths = Integer.parseInt(candidate.getExperienceYears())*12 + Integer.parseInt(candidate.getExperienceMonths());
				if(candidateExpMonths < reqMinExp *12){
					expPercentage = 0;
				}else if(candidateExpMonths >= reqMinExp *12 && candidateExpMonths <= reqMaxExp *12){
					expPercentage = percentPerMonthExp*(float)candidateExpMonths;
				}else{
					expPercentage = 20;
				}
			}
			if(null != candidate.getCity() && !"".equalsIgnoreCase(candidate.getCity())){
				if(candidate.getCity().equalsIgnoreCase(reqCity)){
					locationPercentage = 10;
				}
			}
			
			if(null != candidate.getExpectedRate() && !"".equalsIgnoreCase(candidate.getExpectedRate()) && null != candidate.getRateType()  && !"".equalsIgnoreCase(candidate.getRateType())){
				String candidateRateType = candidate.getRateType();
				String  expectedRate = candidate.getExpectedRate();
				
				int reqRate = Integer.parseInt(expectedRate);
				float perMonthCandidateSal = 0;
				if("Hourly".equalsIgnoreCase(candidateRateType)){
					int perDaySal = reqRate *9;
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
			
			int roundOff = (int) (Math.round(totalPercentage * 100.0) / 100.0);
			String candidateScore = String.valueOf(roundOff);
			
			
		return candidateScore;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Candidate> getRecommendationsUpdateWithScoreByUsingElk(Long orgLocationId, RecommendedCustomizeScore recommendedCustomizeScore) throws Exception {
		logger.info("....getRecommendationsUpdateWithScoreByUsingElk Method Started...");
		float locationPercentage = 0; // location 10%
		float ratePercentage = 0;    // rate 20 %
		float skillPercentage = 0; // skill = 20%
		float percentPerSkill = 0;
		float percentPerkeyword = 0; //keyword 20%
		float noticePeriod = 0; // notice 10%
		float expPercentage = 0; //  experience = 20%
		
		List<Candidate> CandidateListcopy = new ArrayList<>();
		String reqSkills = "";
		if(null != recommendedCustomizeScore.getCustomizeSkills() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeSkills())){
			Long maxResults = candidateService.getCandidatesCountByOrgLocationID(orgLocationId,null);
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocationId));
			 boolQueryBuilder.must(QueryBuilders.matchQuery("status",1));
		     reqSkills = recommendedCustomizeScore.getCustomizeSkills();
		     String [] numOfKeyWords = null;
		     if(null != recommendedCustomizeScore.getCustomizeKeywords()){
		    	 numOfKeyWords =  recommendedCustomizeScore.getCustomizeKeywords().split(",");
		    	 percentPerkeyword = Integer.parseInt(recommendedCustomizeScore.getCustomizeKeywordsScore())/(float)numOfKeyWords.length;
		     }
		     
			String [] reqMaxSkills = reqSkills.split(",");
			if(reqMaxSkills != null && reqMaxSkills.length >0){
				for(String skill : reqMaxSkills){
					boolQueryBuilder.should(QueryBuilders.matchQuery("skills", skill));
				}
			}
			boolQueryBuilder.minimumShouldMatch("1");
			SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
			
			if(null != searchHits && searchHits.length >0){
				percentPerSkill = Integer.parseInt(recommendedCustomizeScore.getCustomizeSkillScore())/(float)reqMaxSkills.length;
				int reqMinExp = 0;
				int reqMaxExp = 0;
				float percentPerMonthExp = 0;
				if(null != recommendedCustomizeScore.getCustomizeMinExperience() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeMinExperience()) && null !=recommendedCustomizeScore.getCustomizeMaxExperience() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeMaxExperience())){
					 reqMinExp = Integer.parseInt(recommendedCustomizeScore.getCustomizeMinExperience());
					 reqMaxExp = Integer.parseInt(recommendedCustomizeScore.getCustomizeMaxExperience());
					int reqExp = reqMaxExp *12 ;
					 percentPerMonthExp = Integer.parseInt(recommendedCustomizeScore.getCustomizeExperienceScore())/(float)reqExp;
				}
				
				String reqCity = "";
				if(null != recommendedCustomizeScore.getCustomizeCity() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeCity())){
					 reqCity = recommendedCustomizeScore.getCustomizeCity();
				}
				float reqPerMonthSal = 0;
				if(null != recommendedCustomizeScore.getCustomizeRate() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeRate())){
					int reqRate = Integer.parseInt(recommendedCustomizeScore.getCustomizeRate());
					String reqRateType = recommendedCustomizeScore.getCustomizeRatetype();
					if("Hourly".equalsIgnoreCase(reqRateType)){
						int perDaySal = reqRate *9;
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
				
				
				for (SearchHit hit : searchHits) {
					//Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(hit.getId()));
					ObjectMapper mapper = new ObjectMapper();
					String json = hit.getSourceAsString();
		    	    CandidateIndex candidateIndex = mapper.readValue(json, CandidateIndex.class);
	    	        Candidate candidate =	populateCandidate(candidateIndex);
					boolean isCandidateAdded = false;
					
					int matchedSkillCount = 0;
					Set<CandidateSkills> candidateSkillList = candidate.getCandidateSkills();
					List<RequirementSubmission> reqList = requirementSubmissionService.getRequirementPerCandidate(candidate.getId());
					/*CandidateResumes candidateResume = candidateResumeSerivce.getCandidateLatesResumes(candidate.getId(), 1);*/
					
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
					if(null != candidate.getExperienceYears() && !"".equalsIgnoreCase(candidate.getExperienceYears()) && null != candidate.getExperienceMonths()  && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
						int  candidateExpMonths = Integer.parseInt(candidate.getExperienceYears())*12 + Integer.parseInt(candidate.getExperienceMonths());
						if(candidateExpMonths < reqMinExp *12){
							expPercentage = 0;
						}else if(candidateExpMonths >= reqMinExp *12 && candidateExpMonths <= reqMaxExp *12){
							expPercentage = percentPerMonthExp*(float)candidateExpMonths;
						}else{
							expPercentage = Integer.parseInt(recommendedCustomizeScore.getCustomizeExperienceScore());
						}
					}
					if(null != candidate.getCity() && !"".equalsIgnoreCase(candidate.getCity())){
						if(candidate.getCity().equalsIgnoreCase(reqCity)){
							locationPercentage = Integer.parseInt(recommendedCustomizeScore.getCustomizeLocationScore());
						}
					}
					
					if(null != candidate.getExpectedRate() && !"".equalsIgnoreCase(candidate.getExpectedRate()) && null != candidate.getRateType()  && !"".equalsIgnoreCase(candidate.getRateType())){
						String candidateRateType = candidate.getRateType();
						String  expectedRate = candidate.getExpectedRate();
						expectedRate = expectedRate.substring(0, expectedRate.length() - 2);
						
						int reqRate = Integer.parseInt(expectedRate.toString());
						float perMonthCandidateSal = 0;
						if(Constants.RATE_TYPE_HOURLY.equalsIgnoreCase(candidateRateType)){
							int perDaySal = reqRate *9;
							perMonthCandidateSal = perDaySal * 30;
						}else if(Constants.RATE_TYPE_WEEKLY.equalsIgnoreCase(candidateRateType)){
							int perDaySal = reqRate /7;
							perMonthCandidateSal = perDaySal * 30;
						}else if(Constants.RATE_TYPE_BI_WEEKLY.equalsIgnoreCase(candidateRateType)){
							int perDaySal = reqRate /14;
							perMonthCandidateSal = perDaySal * 30;
						}else if(Constants.RATE_TYPE_SEMI_MONTHLY.equalsIgnoreCase(candidateRateType)){
							int perDaySal = reqRate /15;
							perMonthCandidateSal = perDaySal * 30;
						}else if(Constants.RATE_TYPE_MONTHLY.equalsIgnoreCase(candidateRateType)){
							perMonthCandidateSal = reqRate;
						}else if(Constants.RATE_TYPE_BI_MONTHLY.equalsIgnoreCase(candidateRateType)){
							int perDaySal = reqRate /60;
							perMonthCandidateSal = perDaySal * 30;
						}else{
							perMonthCandidateSal = reqRate /12;
						}
						
						if(perMonthCandidateSal<=reqPerMonthSal){
							ratePercentage = Integer.parseInt(recommendedCustomizeScore.getCustomizeRateScore());
						}
					}
					
					if(recommendedCustomizeScore.getCustomizeNoticeperiod() !=null){
							String []  np = recommendedCustomizeScore.getCustomizeNoticeperiod().split(",");
							int totalDays = 0;
							int canTotalDays = 0;
							for(int i=0;i<np.length;i++){
								if(Constants.NOTICE_PERIOD_15DAYS.equalsIgnoreCase(np[i])){
									totalDays = totalDays + 15;
								}else if(Constants.NOTICE_PERIOD_1MONTH.equalsIgnoreCase(np[i])){
									totalDays = totalDays+30;
								}else if(Constants.NOTICE_PERIOD_2MONTH.equalsIgnoreCase(np[i])){
									totalDays = totalDays+60;
								}else if(Constants.NOTICE_PERIOD_3MONTH.equalsIgnoreCase(np[i])){
									totalDays = totalDays+90;
								}else if(Constants.NOTICE_PERIOD_IMMEDIATELY.equalsIgnoreCase(np[i])){
									totalDays =0;
								}
							}
						
						if(null != candidate.getNoticePeriod() && !"".equalsIgnoreCase(candidate.getNoticePeriod())){
							if(Constants.NOTICE_PERIOD_15DAYS.equalsIgnoreCase(candidate.getNoticePeriod())){
								canTotalDays = canTotalDays + 15;
							}else if(Constants.NOTICE_PERIOD_1MONTH.equalsIgnoreCase(candidate.getNoticePeriod())){
								canTotalDays = canTotalDays + 30;
							}else if(Constants.NOTICE_PERIOD_2MONTH.equalsIgnoreCase(candidate.getNoticePeriod())){
								canTotalDays = canTotalDays + 60;
							}else if(Constants.NOTICE_PERIOD_3MONTH.equalsIgnoreCase(candidate.getNoticePeriod())){
								canTotalDays = canTotalDays + 90;
							}else if(Constants.NOTICE_PERIOD_IMMEDIATELY.equalsIgnoreCase(candidate.getNoticePeriod())){
								canTotalDays = 0;
							}
						}
						if(totalDays <= canTotalDays){
							noticePeriod = Integer.parseInt(recommendedCustomizeScore.getCustomizeNoticeperiodScore());
						}
						
					}
					//keywords calculation
				    float totalKeyWordPer = 0;
					if(null != candidate.getSummary()){
						for(int i=0;i<numOfKeyWords.length;i++){
							if(candidate.getSummary().contains(numOfKeyWords[i])){
								totalKeyWordPer = totalKeyWordPer + percentPerkeyword;	
							}
						}
					}
					
					float totalPercentage = skillPercentage+expPercentage+locationPercentage+ratePercentage+noticePeriod+totalKeyWordPer;
					
					double roundOff = Math.round(totalPercentage * 100.0) / 100.0;
					String candidateScore = String.valueOf(roundOff);
					candidate.setCandidateScore(candidateScore);
					candidate.setNoOfReq(reqList.size());
					candidate.setSkillPercentage(skillPercentage);
					candidate.setExpPercentage(expPercentage);
					candidate.setLocationPercentage(locationPercentage);
					candidate.setRatePercentage(ratePercentage);
					candidate.setNoticePeriodPercentage(noticePeriod);
					candidate.setTotalKeyWordPerPercentage(totalKeyWordPer);
					
					/*if(candidateResume != null){
							if(candidateResume.getTitle() !=null && !"".equalsIgnoreCase(candidateResume.getTitle())){
								candidate.setCandidateResume(candidateResume.getTitle());
							}else{
								candidate.setCandidateResume(candidateResume.getFileName());
							}
					}*/
					if(reqList.size() >0){
						
						for(RequirementSubmission reqSubmission : reqList){
							if(recommendedCustomizeScore.getReqirementId().getId().equals(reqSubmission.getRequirement().getId())){
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
				
			 }
		 }
		logger.info("....getRecommendationsWithScoreByUsingElk Method Ended...");
		return CandidateListcopy;
	}

	private Candidate populateCandidate(CandidateIndex candidateIndex) {
		Candidate candidate = new Candidate();
		candidate.setId(candidateIndex.getCandidateId());
		 Set<CandidateSkills> set = new HashSet<CandidateSkills>();
		 List<String> skillList = candidateIndex.getSkills();
		 for(String skill :skillList){
			 CandidateSkills candSkill = new CandidateSkills();
			 candSkill.setName(skill);
			 set.add(candSkill);
		 }
		 candidate.setCandidateSkills(set);
		 candidate.setCandidateName(candidateIndex.getFirstName());
		 candidate.setMobilePhone(candidateIndex.getMobilePhone());
		 if(null != candidateIndex.getExperienceYears()){
			 candidate.setExperienceYears(candidateIndex.getExperienceYears().toString());	 
		 }
		 if(null != candidateIndex.getExperienceMonths()){
			 candidate.setExperienceMonths(candidateIndex.getExperienceMonths().toString());
		 }
		 if(null != candidateIndex.getNoticePriod() ){
			 candidate.setNoticePeriod(candidateIndex.getNoticePriod());	 
		 }
		 if(null != candidateIndex.getCity()){
			 candidate.setCity(candidateIndex.getCity());	 
		 }
		 if(null != candidateIndex.getCurrentResumeId()){
			 candidate.setCurrentResumeId(candidateIndex.getCurrentResumeId());	 
		 }
		 
		 if(null != candidateIndex.getExpectedRate() && null != candidateIndex.getRateType()){
			 candidate.setRateType(candidateIndex.getRateType());
			 candidate.setExpectedRate(String.valueOf(candidateIndex.getExpectedRate()));
			 	 
		 }
		 
		return candidate;
	}
}
