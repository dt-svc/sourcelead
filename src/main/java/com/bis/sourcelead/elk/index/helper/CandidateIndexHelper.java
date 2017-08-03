package com.bis.sourcelead.elk.index.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.service.CandidateResumesService;

@Component
public class CandidateIndexHelper {
	
	@Autowired
    CandidateResumesService candidateResumesService;
	
	public CandidateIndex getCandidateIndexFromCandidate(Candidate candidate) throws Exception{
		CandidateIndex candidateIndex = null;
		if(null != candidate && null != candidate.getId()){
			candidateIndex = new CandidateIndex();
			candidateIndex.setCandidateId(candidate.getId());
			candidateIndex.setOrgLocationId(candidate.getOrgLocationId().getId());
			candidateIndex.setFirstName(candidate.getFirstName());
			candidateIndex.setLastName(candidate.getLastName());
			candidateIndex.setEmail(candidate.getEmail());
			candidateIndex.setAlternateEmail(candidate.getAlternateEmail());
			candidateIndex.setMobilePhone(candidate.getMobilePhone());
			candidateIndex.setAlternateMobilePhone(candidate.getAlternateMobilePhone());
			candidateIndex.setCountry(candidate.getCountry());
			candidateIndex.setState(candidate.getState());
			candidateIndex.setCity(candidate.getCity());
			candidateIndex.setAddress(candidate.getAddress());
			candidateIndex.setNoticePriod(candidate.getNoticePeriod());
			candidateIndex.setSummary(candidate.getSummary());
			candidateIndex.setCandidateType(candidate.getCandidateType());
			candidateIndex.setLocation(candidate.getLocation());
			candidateIndex.setZipCode(candidate.getZipCode());
			candidateIndex.setLatitude(candidate.getLatitude());
			candidateIndex.setLongitude(candidate.getLongitude());		
			candidateIndex.setGeneratedId(candidate.getGeneratedId());
			
		if(null != candidate.getConsultantUser()){
				candidateIndex.setUserId(candidate.getConsultantUser().getId());
			}
			
			if(null != candidate.getMasterCandidateId()){
				candidateIndex.setMasterCandidateId(candidate.getMasterCandidateId().getId());
			}
						
 
			if(null != candidate.getRecruiterUserId()){
				candidateIndex.setRecruiterUserId(candidate.getRecruiterUserId().getId());
			}else{
				candidateIndex.setRecruiterUserId(0L);
			}
			
			if(null != candidate.getSkillsName() && !candidate.getSkillsName().isEmpty()){
				candidateIndex.setSkillsName(candidate.getSkillsName());
			}
			
			if(null != candidate.getPrimarySkill()){
				candidateIndex.setPrimarySkill(candidate.getPrimarySkill());
			}
			
			if(null != candidate.getVisaType() && !candidate.getVisaType().isEmpty()){
				candidateIndex.setVisaType(candidate.getVisaType().split(","));
			}
			/*if(null != candidate.getCandidateSkills() && !candidate.getCandidateSkills().isEmpty()){
				ArrayList<String> skillList = new ArrayList<>();
				for(CandidateSkills skills : candidate.getCandidateSkills()){
					skillList.add(skills.getName());
				}
				if(null != skillList && !skillList.isEmpty()){
					candidateIndex.setSkills(skillList);
				}
				
			}*/
			if(null != candidate.getCandidateEmployers() && !candidate.getCandidateEmployers().isEmpty()){
				ArrayList<String> employerList = new ArrayList<>();
				for(CandidateEmployers employer : candidate.getCandidateEmployers()){
					employerList.add(employer.getName());
				}
				if(null != employerList && !employerList.isEmpty()){
					candidateIndex.setEmployer(employerList);
				}
				
			}
			if(null != candidate.getCandidateEducation() && !candidate.getCandidateEducation().isEmpty()){
				ArrayList<String> educationList = new ArrayList<>();
				for(CandidateEducation education : candidate.getCandidateEducation()){
					educationList.add(education.getName());
				}
				if(null != educationList && !educationList.isEmpty()){
					candidateIndex.setEducation(educationList);
				}
			}
			
			CandidateResumes candidateResumes = candidateResumesService.getCandidateLatesResumes(candidate.getId(),Constants.CANDIDATE_SKILL_ACTIVE);
			if(null != candidateResumes){
				candidateIndex.setCurrentResumeId(candidateResumes.getId());
				candidateIndex.setCurrentResumeName(candidateResumes.getFileName());
				candidateIndex.setResumeContent("");
			}
			if(StringUtils.isNotEmpty(candidate.getExperienceYears())){
				if(candidate.getExperienceYears().equalsIgnoreCase("25+")){
					candidateIndex.setExperienceYears(Constants.EXPERIENCE_26YEARS);
				}
				else{
					candidateIndex.setExperienceYears(Integer.parseInt(candidate.getExperienceYears()));
				}
				
			}
			if(StringUtils.isNotEmpty(candidate.getExperienceMonths())){
				candidateIndex.setExperienceMonths(Integer.parseInt(candidate.getExperienceMonths()));	
			}
			
			candidateIndex.setDesignation(candidate.getDesignation());
			candidateIndex.setDesignationType(candidate.getDesignationType());
			
			if(null != candidate.getDesignationType() && "Other".equalsIgnoreCase(candidate.getDesignationType())){
				candidateIndex.setDesignationOtherType(candidate.getDesignationOtherType());
			}
			if(StringUtils.isNotEmpty(candidate.getCurrentRate())){
				candidateIndex.setCurrentRate(candidate.getCurrentRate());
			}
			
			candidateIndex.setCurrentRateType(candidate.getCurrentRateType());
			candidateIndex.setCurrentRateCurrency(candidate.getCurrentRateCurrency());
			if(StringUtils.isNotEmpty(candidate.getExpectedRate())){
				candidateIndex.setExpectedRate(Float.parseFloat(candidate.getExpectedRate()));
			}
			candidateIndex.setRateType(candidate.getRateType());
			candidateIndex.setExpectedRateCurrencey(candidate.getExpectedRateCurrencey());
			candidateIndex.setInvitationstatus(candidate.getInvitationstatus());
			candidateIndex.setStatus(candidate.getStatus());
			
			if(null != candidate.getTeamId() && null != candidate.getTeamId().getId()){
				candidateIndex.setTeamId(candidate.getTeamId().getId());
			}else{
				candidateIndex.setTeamId(0L);
			}
			
			if(null != candidate.getIsFavourite()){
				candidateIndex.setIsFavourite(candidate.getIsFavourite());
			}else{
				candidateIndex.setIsFavourite(0);
			}
			
			if(null != candidate.getIsFavouriteAssociate()){
				candidateIndex.setIsFavouriteAssociate(candidate.getIsFavouriteAssociate());
			}else{
				candidateIndex.setIsFavouriteAssociate(0);
			}
			
			if(null != candidate.getIsAssociate()){
				candidateIndex.setIsAssociate(candidate.getIsAssociate());	
			}
			if(null != candidate.getSpocId()){
				candidateIndex.setSpocId(candidate.getSpocId().getId());	
			}else{
				candidateIndex.setSpocId(null);
			}
			
			if(null != candidate.getOrgRelationId()){
				candidateIndex.setOrgRelationId(candidate.getOrgRelationId().getId());	
			}else{
				candidateIndex.setOrgRelationId(null);
			}
			
			candidateIndex.setTerminateReason(candidate.getTerminateReason());
			if(candidate.getCreatedDate() != null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				candidateIndex.setCreatedDate(df.format(new Date(candidate.getCreatedDate().getTime())));
			}
			candidateIndex.setCreatedBy(candidate.getCreatedBy());
			if(candidate.getUpdatedDate() != null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				candidateIndex.setUpdatedDate(df.format(new Date(candidate.getUpdatedDate().getTime())));
			}
			
			if(null != candidate.getIsProfilePublic()){
				candidateIndex.setIsProfilePublic(candidate.getIsProfilePublic());
			}
			
			candidateIndex.setUpdatedBy(candidate.getUpdatedBy());
			
			candidateIndex.setCandidateUUID(candidate.getCandidateUUID());
			
		}
		
		return candidateIndex;
		
	}
	
	
}
