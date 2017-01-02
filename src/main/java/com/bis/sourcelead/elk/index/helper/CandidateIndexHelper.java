package com.bis.sourcelead.elk.index.helper;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateSkills;
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
			candidateIndex.setSummary(candidate.getSummary());
			candidateIndex.setCandidateType(candidate.getCandidateType());
			if(null != candidate.getCandidateSkills() && !candidate.getCandidateSkills().isEmpty()){
				ArrayList<String> skillList = new ArrayList<>();
				for(CandidateSkills skills : candidate.getCandidateSkills()){
					skillList.add(skills.getName());
				}
				if(null != skillList && !skillList.isEmpty()){
					candidateIndex.setSkills(skillList);
				}
				
			}
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
			candidateIndex.setExperienceYears(candidate.getExperienceYears());
			candidateIndex.setExperienceMonths(candidate.getExperienceMonths());
			candidateIndex.setDesignation(candidate.getDesignation());
			candidateIndex.setDesignationType(candidate.getDesignationType());
			candidateIndex.setCurrentRate(candidate.getCurrentRate());
			candidateIndex.setCurrentRateType(candidate.getCurrentRateType());
			candidateIndex.setCurrentRateCurrency(candidate.getCurrentRateCurrency());
			candidateIndex.setExpectedRate(candidate.getExpectedRate());
			candidateIndex.setRateType(candidate.getRateType());
			candidateIndex.setExpectedRateCurrencey(candidate.getExpectedRateCurrencey());
			candidateIndex.setInvitationstatus(candidate.getInvitationstatus());
			candidateIndex.setStatus(candidate.getStatus());
			candidateIndex.setTerminateReason(candidate.getTerminateReason());
			candidateIndex.setCreatedDate(candidate.getCreatedDate());
			candidateIndex.setCreatedBy(candidate.getCreatedBy());
			candidateIndex.setUpdatedDate(candidate.getUpdatedDate());
			candidateIndex.setUpdatedBy(candidate.getUpdatedBy());
		}
		
		
		return candidateIndex;
		
	}
	
	
}
