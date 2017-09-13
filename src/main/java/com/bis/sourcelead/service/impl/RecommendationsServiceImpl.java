package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;

@Service
public class RecommendationsServiceImpl implements RecommendationsService{
	
	private static final Logger logger = LoggerFactory.getLogger(RecommendationsServiceImpl.class);
	
	@Autowired
	private RecommendationsDao recommendationsDao;
	
	 @Autowired
	 private CandidateService candidateService;
	 
	 @Autowired
	 private CommonUtil commonUtil;
	 
	 @Autowired
	 private CandidateResumesService candidateResumeSerivce;
	 
	 @Autowired
	 private  RequirementSubmissionService requirementSubmissionService;
	
	 @Autowired
	 private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 private ConversionUtil conversionUtil;
	 
	 /*@Autowired
	 private CandidateSkillsService candidateSkillsService;*/
	 
	 
	 
	 
	 
	 
	@Override
	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId) throws Exception {
		return recommendationsDao.getAllCandidatesByLocation(orgLocationId);
	}

	@Override
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception {
		Requirement requirement = recommendationsDao.getRequirementByRequirementId(requirementId);
		requirement.setRequirementCreatedDateToString(commonUtil.elkDateFormatter(requirement.getCreatedDate()));
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
			
			
//			List<CandidateSkills> candidateSkillList = candidateSkillsService.getCandidateSkillsByCandidateId(candidate.getId().toString());
			
			String candidateSkills = candidate.getSkillsName();
			String[] skills = candidateSkills.split(",");
			//List<String> candidateSkillList = new ArrayList<String>(Arrays.asList(skills));
			 
			// Changed the below method by  bringing from  service as cache is implemented  for  the above method
			
			   // Set<CandidateSkills> candidateSkillList = candidate.getCandidateSkills();
			List<RequirementSubmission> reqList = requirementSubmissionService.getRequirementPerCandidate(candidate.getId());
			CandidateResumes candidateResume = candidateResumeSerivce.getCandidateLatesResumes(candidate.getId(), 1);
			
			if(null != skills && skills.length >0){
				for(String skill : skills){
					if(skill !=null && !"".equalsIgnoreCase(skill)){
						if(reqSkills.contains(skill)){
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
//					Set<CandidateSkills> candidateSkillList = candidate.getCandidateSkills();
					if(null != candidate.getSkillsName()){
						String[] candidateSkillList = candidate.getSkillsName().split(",");
						for(String skill : candidateSkillList){
							if(skill !=null && !"".equalsIgnoreCase(skill)){
								if(reqSkills.contains(skill)){
									matchedSkillCount++;
								}
								
							}
						}
					}
					
					List<RequirementSubmission> reqList = requirementSubmissionService.getRequirementPerCandidate(candidate.getId());
					CandidateResumes candidateResume = candidateResumeSerivce.getCandidateLatesResumes(candidate.getId(), 1);
					
//					if(candidateSkillList != null && candidateSkillList.size() >0){
					
//					}
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
	public String getCandidateScore(Candidate candidate, Requirement requirement,RecommendedCustomizeScore recommendedCustomizeScore) throws Exception {
		float locationPercentage = 0; // location 10%
		float ratePercentage = 0;    // rate 10 %
		float skillPercentage = 0; // skill = 20%
		float percentPerSkill = 0;
		float percentPerkeyword = 0; //keyword 20%
		float noticePeriod = 0; // notice 10%
		float expPercentage = 0; //  experience = 10%
		float eduPer = 0; // 10 %
		float visaPer = 0; // 10%
		String reqSkills = "";
		String candidateScore = null; 
		if(null != recommendedCustomizeScore && null != recommendedCustomizeScore.getCustomizeSkills() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeSkills())){
		     reqSkills = recommendedCustomizeScore.getCustomizeSkills();
		     String visaType = null;
		     if(recommendedCustomizeScore.getCustomizeVisatype() !=null){
		    	  visaType = recommendedCustomizeScore.getCustomizeVisatype();
		     }
		     String education = null;
		    if(recommendedCustomizeScore.getCustomizeEducation() !=null){
		    	education = recommendedCustomizeScore.getCustomizeEducation();	
		    }
		     
		     String [] numOfKeyWords = null;
		     if(null != recommendedCustomizeScore.getCustomizeKeywords()){
		    	 numOfKeyWords =  recommendedCustomizeScore.getCustomizeKeywords().split(",");
		    	 percentPerkeyword = Integer.parseInt(recommendedCustomizeScore.getCustomizeKeywordsScore())/(float)numOfKeyWords.length;
		     }
		     
		    	String [] reqMaxSkills = reqSkills.split(",");
		    	String [] visatypeArr = null;
		    	String [] educationsArr = null;
		    	if(visatypeArr !=null){
		    		visatypeArr = visaType.split(",");
    			}
		    	if(education !=null){
		    		educationsArr = education.split(",");
		    	}
		    	
				percentPerSkill = Integer.parseInt(recommendedCustomizeScore.getCustomizeSkillScore())/(float)reqMaxSkills.length;
				float visaTypePercentage = 0;
				float educationPercentage = 0;
				if(visatypeArr !=null){
					 visaTypePercentage = Integer.parseInt(recommendedCustomizeScore.getCustomizeVisatypecore())/(float)visatypeArr.length;
				}
				if(educationsArr !=null){
					 educationPercentage = Integer.parseInt(recommendedCustomizeScore.getCustomizeEducationScore())/(float)educationsArr.length;
				}
				 
				
				int reqMinExp = 0;
				int reqMaxExp = 0;
				float percentPerMonthExp = 0;
				String reqCity = "";
				if(null != recommendedCustomizeScore.getCustomizeCity() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeCity())){
					 reqCity = recommendedCustomizeScore.getCustomizeCity();
				}
				float requirementHourlyRate = 0;
				// changed rate logic
				if(null != recommendedCustomizeScore.getCustomizeRate() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeRate())){
					requirementHourlyRate = conversionUtil.getHourlyRate(recommendedCustomizeScore.getCustomizeRate(), recommendedCustomizeScore.getCustomizeRatetype());
				}
				// rate logic end
		    	    CandidateIndex candidateIndex = getCandidateIndex(candidate);
					int matchedSkillCount = 0;
					int matchedVisaTypeCount = 0;
					int matchedEducationCount = 0;
					if(null  != candidate.getSkillsName()){
						String[] skills = candidate.getSkillsName().split(",");
						Set<String> set = new HashSet<String>(Arrays.asList(skills));
						skillPercentage = getSkillPercentage(set,percentPerSkill,matchedSkillCount,reqSkills);
					}
					
					if(candidate.getVisaType() !=null && !"".equalsIgnoreCase(candidate.getVisaType())){
						String[] visa = candidate.getVisaType().split(",");
						Set<String> set = new HashSet<String>(Arrays.asList(visa));
						visaPer = getVisaTypePercentage(set,visaTypePercentage,matchedVisaTypeCount,visaType);
					}
					
					
					if(null  != candidate.getCandidateEducation()){
						Set<String> set = new HashSet<String>();
						Set<CandidateEducation> edu = candidate.getCandidateEducation();
						for(CandidateEducation can :edu){
							set.add(can.getName());
						}
						 eduPer = getEducationPercentage(set,educationPercentage,matchedEducationCount,education);
						
					}
					
					//get the ExperiencePercentage
					expPercentage = getExperiencePercentage(candidateIndex,recommendedCustomizeScore,reqMinExp,expPercentage,percentPerMonthExp,reqMaxExp);
					
					//get the RatePercentage
					ratePercentage = getRatePercentage(candidateIndex,ratePercentage,recommendedCustomizeScore,requirementHourlyRate);
					
					// get the NoticeperiodPercentage
					noticePeriod = getNoticeperiodPercentage(recommendedCustomizeScore,candidateIndex,noticePeriod);
					
					if(null != candidateIndex.getCity() && !"".equalsIgnoreCase(candidateIndex.getCity())){
						if(candidateIndex.getCity().equalsIgnoreCase(reqCity)){
							locationPercentage = Integer.parseInt(recommendedCustomizeScore.getCustomizeLocationScore());
						}
					}
					
					//keywords calculation
				    float totalKeyWordPer = 0;
					if(null != candidateIndex.getSummary() && null != numOfKeyWords){
						for(int i=0;i<numOfKeyWords.length;i++){
							if(candidateIndex.getSummary().contains(numOfKeyWords[i])){
								totalKeyWordPer = totalKeyWordPer + percentPerkeyword;	
							}
						}
					}
					
					float totalPercentage = skillPercentage+expPercentage+locationPercentage+ratePercentage+noticePeriod+totalKeyWordPer+eduPer+visaPer;
					
					double roundOff = Math.round(totalPercentage * 100.0) / 100.0;
					candidateScore = String.valueOf(roundOff);		
		 }
		logger.info("....getRecommendationsWithScoreByUsingElk Method Ended...");
		return candidateScore;
	}

	private CandidateIndex getCandidateIndex(Candidate candidate) {
		CandidateIndex canIndex = new CandidateIndex();
		if(StringUtils.isNotEmpty(candidate.getExperienceMonths())){
			canIndex.setExperienceMonths(Integer.parseInt(candidate.getExperienceMonths()));
		}
		if(StringUtils.isNotEmpty(candidate.getExperienceYears())){
			canIndex.setExperienceYears(Integer.parseInt(candidate.getExperienceYears()));
		}
		canIndex.setRateType(candidate.getRateType());
		if(null != candidate.getExpectedRate() && !"".equalsIgnoreCase(candidate.getExpectedRate())){
			canIndex.setExpectedRate(Float.parseFloat(candidate.getExpectedRate()));
		}
		canIndex.setNoticePriod(candidate.getNoticePeriod());
		canIndex.setCity(candidate.getCity());
		canIndex.setState(candidate.getState());
		canIndex.setCountry(candidate.getCountry());
		return canIndex;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateIndex> getRecommendationsUpdateWithScoreByUsingElk(Long orgLocationId, RecommendedCustomizeScore recommendedCustomizeScore,Long requirementId) throws Exception {
		float locationPercentage = 0; // location 10%
		float ratePercentage = 0;    // rate 10 %
		float skillPercentage = 0; // skill = 20%
		float percentPerSkill = 0;
		float percentPerkeyword = 0; //keyword 20%
		float noticePeriod = 0; // notice 10%
		float expPercentage = 0; //  experience = 10%
//		float visaTypePercentage = 0; //  visaType = 10%
//		float educationPercentage = 0; //  education = 10%
		float eduPer = 0;
		float visaPer = 0;
		List<CandidateIndex> CandidateListcopy = new ArrayList<>(); 
		String reqSkills = "";
		if(null != recommendedCustomizeScore.getCustomizeSkills() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeSkills())){
//			Long maxResults = candidateService.getCandidatesCountByOrgLocationID(orgLocationId,null);
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocationId));
			 boolQueryBuilder.must(QueryBuilders.matchQuery("status",1));
		     reqSkills = recommendedCustomizeScore.getCustomizeSkills();
		     String visaType = null;
		     if(recommendedCustomizeScore.getCustomizeVisatype() != null){
		    	 visaType = recommendedCustomizeScore.getCustomizeVisatype();
		     }
		    
		     String education = null;
		     if(recommendedCustomizeScore.getCustomizeEducation() != null){
		    	 education  = recommendedCustomizeScore.getCustomizeEducation();
		     }
		     
		     
		     
		     String [] numOfKeyWords = null;
		     if(null != recommendedCustomizeScore.getCustomizeKeywords()){
		    	 numOfKeyWords =  recommendedCustomizeScore.getCustomizeKeywords().split(",");
		    	 percentPerkeyword = Integer.parseInt(recommendedCustomizeScore.getCustomizeKeywordsScore())/(float)numOfKeyWords.length;
		     }
		     
			String [] reqMaxSkills = reqSkills.split(",");
			if(reqMaxSkills != null && reqMaxSkills.length >0){
				for(String skill : reqMaxSkills){
					boolQueryBuilder.should(QueryBuilders.matchQuery("skillsName", skill));
				}
			}
			
			String [] visatypeArr = null;
			if(visaType != null){
				visatypeArr = visaType.split(",");
				if(visatypeArr != null && visatypeArr.length >0){
					for(String visa : visatypeArr){
						boolQueryBuilder.should(QueryBuilders.matchQuery("visaType", visa));
					}
				}
			}
			
			
			String [] educationArr = null;
			if(education != null){
				educationArr = education.split(",");
				for(String edu : educationArr){
					boolQueryBuilder.should(QueryBuilders.matchQuery("education", edu));
				}
			}
			
			boolQueryBuilder.minimumShouldMatch("1");
			
			//Long  count = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,0,boolQueryBuilder).getTotalHits();
			
			SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,200,boolQueryBuilder).getHits();
			
			if(null != searchHits && searchHits.length >0){
				percentPerSkill = Integer.parseInt(recommendedCustomizeScore.getCustomizeSkillScore())/(float)reqMaxSkills.length;
				float visaTypePercentage = 0;
				float educationPercentage = 0;
				if(visatypeArr !=null && visatypeArr.length >0){
					 visaTypePercentage = Integer.parseInt(recommendedCustomizeScore.getCustomizeVisatypecore())/(float)visatypeArr.length;
				}
				if(educationArr != null && educationArr.length >0){
					 educationPercentage = Integer.parseInt(recommendedCustomizeScore.getCustomizeEducationScore())/(float)educationArr.length; 	
				}
				int reqMinExp = 0;
				int reqMaxExp = 0;
				float percentPerMonthExp = 0;
				/*if(null != recommendedCustomizeScore.getCustomizeMinExperience() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeMinExperience()) && null !=recommendedCustomizeScore.getCustomizeMaxExperience() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeMaxExperience())){
					 reqMinExp = Integer.parseInt(recommendedCustomizeScore.getCustomizeMinExperience());
					 reqMaxExp = Integer.parseInt(recommendedCustomizeScore.getCustomizeMaxExperience());
					int reqExp = reqMaxExp *12 ;
					 percentPerMonthExp = Integer.parseInt(recommendedCustomizeScore.getCustomizeExperienceScore())/(float)reqExp;
				}*/
				
				String reqCity = "";
				if(null != recommendedCustomizeScore.getCustomizeCity() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeCity())){
					 reqCity = recommendedCustomizeScore.getCustomizeCity();
				}
				float requirementHourlyRate = 0;
				// changed rate logic
				if(null != recommendedCustomizeScore.getCustomizeRate() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeRate())){
					requirementHourlyRate = conversionUtil.getHourlyRate(recommendedCustomizeScore.getCustomizeRate(), recommendedCustomizeScore.getCustomizeRatetype());
				}
				// rate logic end
				
				for (SearchHit hit : searchHits) {
					ObjectMapper mapper = new ObjectMapper();
					String json = hit.getSourceAsString();
		    	    CandidateIndex candidateIndex = mapper.readValue(json, CandidateIndex.class);
					boolean isCandidateAdded = false;
					
					int matchedSkillCount = 0;
					int matchedVisaTypeCount = 0;
					int matchedEducationCount = 0;
					
					
					//List<RequirementSubmission> reqList = requirementSubmissionService.getRequirementPerCandidate(candidateIndex.getCandidateId());
					Integer reqListCount = requirementSubmissionService.getRequirementPerCandidateCount(candidateIndex.getCandidateId(),candidateIndex.getOrgLocationId());
					 // TODO Don't delete until regression testing done Rajashekar
//					boolean isSumbitted = requirementSubmissionService.isCandidateSubmittedForRequirementByCandidateIdAndRequirementId(candidateIndex.getCandidateId(), requirementId);
//					if(isSumbitted == true){
//						candidateIndex.setIsCandidateSubmitted(Constants.YES);
//					}else{
//						candidateIndex.setIsCandidateSubmitted(Constants.NO);
//					}
					
					//get the skillPercentage method
					 				 
					if(null  != candidateIndex.getSkillsName()){
						String[] skills = candidateIndex.getSkillsName().split(",");
						Set<String> set = new HashSet<String>(Arrays.asList(skills));
						skillPercentage = getSkillPercentage(set,percentPerSkill,matchedSkillCount,reqSkills);
					}
					
					if(null  != candidateIndex.getVisaType()){
						String[] visa = candidateIndex.getVisaType();
						Set<String> set = new HashSet<String>(Arrays.asList(visa));
						visaPer = getVisaTypePercentage(set,visaTypePercentage,matchedVisaTypeCount,visaType);
					}
					
					
					if(null  != candidateIndex.getEducation()){
						ArrayList<String> edu = candidateIndex.getEducation();
						Set<String> set = new HashSet<String>(edu);
						 eduPer = getEducationPercentage(set,educationPercentage,matchedEducationCount,education);
						
					}
					
					//get the ExperiencePercentage
					expPercentage = getExperiencePercentage(candidateIndex,recommendedCustomizeScore,reqMinExp,expPercentage,percentPerMonthExp,reqMaxExp);
					
					//get the RatePercentage
					ratePercentage = getRatePercentage(candidateIndex,ratePercentage,recommendedCustomizeScore,requirementHourlyRate);
					
					// get the NoticeperiodPercentage
					noticePeriod = getNoticeperiodPercentage(recommendedCustomizeScore,candidateIndex,noticePeriod);
					
					if(null != candidateIndex.getCity() && !"".equalsIgnoreCase(candidateIndex.getCity())){
						if(candidateIndex.getCity().equalsIgnoreCase(reqCity)){
							locationPercentage = Integer.parseInt(recommendedCustomizeScore.getCustomizeLocationScore());
						}
					}
					
					//keywords calculation
				    float totalKeyWordPer = 0;
					if(null != candidateIndex.getSummary() && !"".equalsIgnoreCase(candidateIndex.getSummary()) && "undefined" != candidateIndex.getSummary() && null != numOfKeyWords){
						for(int i=0;i<numOfKeyWords.length;i++){
							if(candidateIndex.getSummary().contains(numOfKeyWords[i])){
								totalKeyWordPer = totalKeyWordPer + percentPerkeyword;	
							}
						}
					}
					
					float totalPercentage = skillPercentage+expPercentage+locationPercentage+ratePercentage+noticePeriod+totalKeyWordPer+visaPer+eduPer;
					
					double roundOff = Math.round(totalPercentage * 100.0) / 100.0;
					candidateIndex.setFinalScore(roundOff);
					candidateIndex.setNoOfReq(reqListCount);
					
					double skillRoundOff = Math.round(skillPercentage * 100.0) / 100.0;
					candidateIndex.setSkillPercentage((float)(skillRoundOff));
					candidateIndex.setTotalSkillPercentage(Integer.parseInt(recommendedCustomizeScore.getCustomizeSkillScore()));
					
					double visaTypeRoundOff = Math.round(visaPer * 100.0) / 100.0;
					candidateIndex.setVisaTypePercentage((float)(visaTypeRoundOff));
					if(recommendedCustomizeScore.getCustomizeVisatypecore() != null){
						candidateIndex.setTotalVisaTypePercentage(Integer.parseInt(recommendedCustomizeScore.getCustomizeVisatypecore()));	
					}else{
						candidateIndex.setTotalVisaTypePercentage(0);
					}
					
					double educationRoundOff = Math.round(eduPer * 100.0) / 100.0;
					candidateIndex.setEducationPercentage((float)(educationRoundOff));
					if(recommendedCustomizeScore.getCustomizeEducationScore() != null){
						candidateIndex.setTotalEducationPercentage(Integer.parseInt(recommendedCustomizeScore.getCustomizeEducationScore()));	
					}else{
						candidateIndex.setTotalEducationPercentage(0);
					}
					
					
					candidateIndex.setExpPercentage(expPercentage);
					candidateIndex.setTotalExpPercentage(Integer.parseInt(recommendedCustomizeScore.getCustomizeExperienceScore()));
					
					candidateIndex.setLocationPercentage(locationPercentage);
					candidateIndex.setTotalLocationPercentage(Integer.parseInt(recommendedCustomizeScore.getCustomizeLocationScore()));
					
					candidateIndex.setRatePercentage(ratePercentage);
					candidateIndex.setTotalRatePercentage(Integer.parseInt(recommendedCustomizeScore.getCustomizeRateScore()));
					
					candidateIndex.setNoticePeriodPercentage(noticePeriod);
					candidateIndex.setTotalNoticePeriodPercentage(Integer.parseInt(recommendedCustomizeScore.getCustomizeNoticeperiodScore()));
					
					candidateIndex.setTotalKeyWordPerPercentage(totalKeyWordPer);
					candidateIndex.setKeyWordPerPercentage(Integer.parseInt(recommendedCustomizeScore.getCustomizeKeywordsScore()));
					
					candidateIndex.setIsCandidateAdedd(isCandidateAdded);
					if(null != candidateIndex.getUpdatedDate()){
						Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(candidateIndex.getUpdatedDate());
						String  strDate = new SimpleDateFormat(recommendedCustomizeScore.getOrgLocationId().getDateFormat()).format(date1);
						candidateIndex.setUpdatedDate(strDate);	
					}
					
					if(skillPercentage !=0 && totalPercentage !=0 ){
						CandidateListcopy.add(candidateIndex);
					}
				}
				Collections.sort(CandidateListcopy, new Comparator() {  
		            @Override  
		            public int compare(Object obj1, Object obj2) {  
		            	CandidateIndex candidate1 = (CandidateIndex)obj1;  
		            	CandidateIndex candidate2 = (CandidateIndex)obj2; 
		            	if (candidate2.getFinalScore() < candidate1.getFinalScore()) return -1;
		                if (candidate2.getFinalScore() > candidate1.getFinalScore()) return 1;
		                return 0;
		            }  
		        });
			 }
		 }
		logger.info("....RecommendationsWithScoreByUsingElk Method Ended...");
		return CandidateListcopy;
	}
	
	
	/**
	 * this method is used to calculate the skillPercentage
	 * @param set
	 * @param percentPerSkill
	 * @param matchedSkillCount
	 * @param reqSkills
	 * @return float
	 */
	private float getSkillPercentage(Set<String> set, float percentPerSkill, int matchedSkillCount, String reqSkills) {
		if(set != null && set.size() >0){
			for(String skill : set){
					if(reqSkills.contains(skill)){
						matchedSkillCount++;
					}
			}
		}
		return matchedSkillCount*percentPerSkill;
		
	}

	/**
	 * this method is used to calculate the visaType
	 * @param set
	 * @param visaTypePercentage
	 * @param matchedSkillCount
	 * @param visaType
	 * @return float
	 */
	
	private float getVisaTypePercentage(Set<String> set, float visaTypePercentage, int matchedSkillCount, String visaType) {
		float visaPer = 0;       
       if(visaType !=null){
    	   if(set != null && set.size() >0){
   			for(String visa : set){
   					if(visaType.contains(visa)){
   						return visaPer = 10;
   					}
   			}
   		}
   
       }
	return visaPer;
		
	}
	
	/**
	 * this method is used to calculate the education
	 * @param set
	 * @param educationPercentage
	 * @param matchedSkillCount
	 * @param education
	 * @return float
	 */
	
	private float getEducationPercentage(Set<String> set, float educationPercentage, int matchedSkillCount, String education) {
		float eduPer = 0; 
		if(education !=null){
				if(set != null && set.size() >0){
					for(String edu : set){
							if(education.contains(edu)){
								return eduPer = 10;
							}
						}
					}
		 }
		return eduPer;
}
	
	
	/**
	 * this method is used to calculate the Experience Percentage
	 * @param candidateIndex
	 * @param recommendedCustomizeScore
	 * @param reqMinExp
	 * @param expPercentage
	 * @param percentPerMonthExp
	 * @param reqMaxExp
	 * @return float
	 */
	private float getExperiencePercentage(CandidateIndex candidateIndex, RecommendedCustomizeScore recommendedCustomizeScore, int reqMinExp, float expPercentage, float percentPerMonthExp, int reqMaxExp) {

		Integer Months = 0;
		Integer Years = 0;
		int recommendationExpMonthsMin = 0;
		int recommendationExpMonthsMax = 0;
		if(null != candidateIndex.getExperienceYears()){
			Years = candidateIndex.getExperienceYears();
			/*if(candidateIndex.getExperienceYears().equalsIgnoreCase("25+")){
				Years = Integer.valueOf(25);	
			}else{
				Years = Integer.parseInt(candidateIndex.getExperienceYears());
			}*/
			 	
		}
		if(null != candidateIndex.getExperienceMonths()){
			 Months = candidateIndex.getExperienceMonths();
		}
		
		if(null != candidateIndex.getExperienceYears()  && null != candidateIndex.getExperienceMonths() && Years > 0 && Months > 0 ){

			int  candidateExpMonths = (candidateIndex.getExperienceYears())*12 + (candidateIndex.getExperienceMonths());
			
			if(null != recommendedCustomizeScore.getCustomizeMinExperience() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeMinExperience()) &&  "undefined" != recommendedCustomizeScore.getCustomizeMinExperience() && null != recommendedCustomizeScore.getCustomizeMaxExperience() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeMaxExperience()) && "undefiend" != recommendedCustomizeScore.getCustomizeMaxExperience() ){
				
				 String recommendationYearAndMonth[] = recommendedCustomizeScore.getCustomizeMinExperience().split("\\.");
				 if(recommendationYearAndMonth.length == 2){
				   recommendationExpMonthsMin = (Integer.parseInt(recommendationYearAndMonth[0]))*12 + (Integer.parseInt(recommendationYearAndMonth[1]));
				 }else{
				   recommendationExpMonthsMin = (Integer.parseInt(recommendationYearAndMonth[0]))*12;	 
				 }
				 String recommendationYearAndMonthMax[] = recommendedCustomizeScore.getCustomizeMaxExperience().split("\\.");
				 if(recommendationYearAndMonthMax.length == 2){
				   recommendationExpMonthsMax = (Integer.parseInt(recommendationYearAndMonthMax[0]))*12 + (Integer.parseInt(recommendationYearAndMonthMax[1]));
				 }else{
				   recommendationExpMonthsMax = (Integer.parseInt(recommendationYearAndMonthMax[0]))*12 ;
				 }
			if(candidateExpMonths >= recommendationExpMonthsMin && candidateExpMonths <= recommendationExpMonthsMax){
					 expPercentage = Float.parseFloat(recommendedCustomizeScore.getCustomizeExperienceScore());
				 }
			}else if(null != recommendedCustomizeScore.getCustomizeMinExperience() && "undefined" != recommendedCustomizeScore.getCustomizeMinExperience() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeMinExperience())){
				 if(candidateExpMonths >= Integer.parseInt(recommendedCustomizeScore.getCustomizeMinExperience()) *12){
					 expPercentage = Float.parseFloat(recommendedCustomizeScore.getCustomizeExperienceScore());
				 }
			}else if(null != recommendedCustomizeScore.getCustomizeMaxExperience() && "undefined" != recommendedCustomizeScore.getCustomizeMaxExperience() && !"".equalsIgnoreCase(recommendedCustomizeScore.getCustomizeMaxExperience())){
				 if(candidateExpMonths <= Integer.parseInt(recommendedCustomizeScore.getCustomizeMaxExperience()) *12){
					 expPercentage = Float.parseFloat(recommendedCustomizeScore.getCustomizeExperienceScore());
				 }
			}else{
				expPercentage = 0;
			}
			/*if(candidateExpMonths < reqMinExp *12){
				expPercentage = 0;
			}else if(candidateExpMonths >= reqMinExp *12 && candidateExpMonths <= reqMaxExp *12){
				expPercentage = percentPerMonthExp*(float)candidateExpMonths;
			}else{
				expPercentage = Integer.parseInt(recommendedCustomizeScore.getCustomizeExperienceScore());
			}*/
		}else{
			expPercentage = 0;
		}
		return expPercentage;
	}

	/**
	 * this method is used to calculate the Rate Percentage
	 * @param candidateIndex
	 * @param ratePercentage
	 * @param recommendedCustomizeScore
	 * @param reqPerMonthSal
	 * @return float
	 */
	private float getRatePercentage(CandidateIndex candidateIndex, float ratePercentage, RecommendedCustomizeScore recommendedCustomizeScore, float requirementHourlyRate)
	{
		if (null != candidateIndex.getExpectedRate() && null != candidateIndex.getRateType()
				&& !"".equalsIgnoreCase(candidateIndex.getRateType())) {
			
			double candidateHourlyRate = conversionUtil.getHourlyRate(candidateIndex.getExpectedRate().toString(), candidateIndex.getRateType());
			if (candidateHourlyRate <= requirementHourlyRate) {
				ratePercentage = Integer.parseInt(recommendedCustomizeScore.getCustomizeRateScore());
			}
		}
		else {
			ratePercentage = 0;
		}
		return ratePercentage;
	}
	
	
	/**
	 * this method is used to calculate the Noticeperiod Percentage
	 * @param recommendedCustomizeScore
	 * @param candidateIndex
	 * @param noticePeriod
	 * @return float
	 */
	private float getNoticeperiodPercentage(RecommendedCustomizeScore recommendedCustomizeScore, CandidateIndex candidateIndex, float noticePeriod) {
		if(null !=recommendedCustomizeScore.getCustomizeNoticeperiod() && null !=candidateIndex.getNoticePriod() && !"".equalsIgnoreCase(candidateIndex.getNoticePriod()) && "undefined" != candidateIndex.getNoticePriod() ){
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
		
			if(null != candidateIndex.getNoticePriod() && !"".equalsIgnoreCase(candidateIndex.getNoticePriod())){
				if(Constants.NOTICE_PERIOD_15DAYS.equalsIgnoreCase(candidateIndex.getNoticePriod())){
					canTotalDays = canTotalDays + 15;
				}else if(Constants.NOTICE_PERIOD_1MONTH.equalsIgnoreCase(candidateIndex.getNoticePriod())){
					canTotalDays = canTotalDays + 30;
				}else if(Constants.NOTICE_PERIOD_2MONTH.equalsIgnoreCase(candidateIndex.getNoticePriod())){
					canTotalDays = canTotalDays + 60;
				}else if(Constants.NOTICE_PERIOD_3MONTH.equalsIgnoreCase(candidateIndex.getNoticePriod())){
					canTotalDays = canTotalDays + 90;
				}else if(Constants.NOTICE_PERIOD_IMMEDIATELY.equalsIgnoreCase(candidateIndex.getNoticePriod())){
					canTotalDays = 0;
				}
			}
			if(canTotalDays <= totalDays){
				noticePeriod = Integer.parseInt(recommendedCustomizeScore.getCustomizeNoticeperiodScore());
			}
		}else{
			noticePeriod = 0;
		}
		return noticePeriod;
	}
}
