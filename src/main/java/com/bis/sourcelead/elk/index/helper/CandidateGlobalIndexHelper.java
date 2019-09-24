package com.bis.sourcelead.elk.index.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateResumesDao;
import com.bis.sourcelead.dto.CandidateResumesDTO;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Component
public class CandidateGlobalIndexHelper {
	
	@Autowired
    CandidateResumesDao candidateResumesDao;
	
	@Autowired
	UserService userService;
	

	public CandidateIndex getCandidateGlobalIndexFromCandidate(CandidateGlobal candidate) throws Exception{
		CandidateIndex candidateIndex = null;
		if(null != candidate && null != candidate.getId()){
			candidateIndex = new CandidateIndex();
			candidateIndex.setCandidateGlobalId(candidate.getId());
			if(null != candidate.getOrgLocationId()){
				candidateIndex.setOrgLocationId(candidate.getOrgLocationId().getId());
			}else {
				candidateIndex.setOrgLocationId(0L);
			}
			
			candidateIndex.setFirstName(firstLetterCapitalizedString(candidate.getFirstName()));
			candidateIndex.setLastName(firstLetterCapitalizedString(candidate.getLastName()));
			candidateIndex.setMiddleName(candidate.getMiddelName());
			candidateIndex.setGender(candidate.getGender());
			candidateIndex.setMaritalStatus(candidate.getMaritalStatus());
			if(StringUtils.isNoneBlank(candidate.getLastName())) {
				StringBuilder fullName = new StringBuilder();
				fullName = fullName.append(firstLetterCapitalizedString(candidate.getFirstName())).append(" ").append(firstLetterCapitalizedString(candidate.getLastName()));
				candidateIndex.setFullName(fullName.toString());
			}else {
				candidateIndex.setFullName(firstLetterCapitalizedString(candidate.getFirstName()));
			}
			
			candidateIndex.setEmail(candidate.getEmail().toLowerCase());
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
			
			if(StringUtils.isNotBlank(candidate.getPanNumber())){
				candidateIndex.setPanNumber(candidate.getPanNumber());
			}
			if(StringUtils.isNotBlank(candidate.getAadharNumber())){
				candidateIndex.setAadharNumber(candidate.getAadharNumber());
			}
			
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
			
			if(null != candidate.getSkillsName()){
				candidateIndex.setSkillsName(candidate.getSkillsName());
			}
			
			if(null != candidate.getPrimarySkill()){
				candidateIndex.setPrimarySkill(candidate.getPrimarySkill());
			}
			
			if(null != candidate.getVisaType() && !candidate.getVisaType().isEmpty()){
				candidateIndex.setVisaType(candidate.getVisaType().split(","));
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
			
			CandidateResumesDTO candidateResumes = candidateResumesDao.getCandidateGlobalLatesResumeDetails(candidate.getId());
			if(null != candidateResumes){
				candidateIndex.setCurrentResumeId(candidateResumes.getResumeId().longValue());
				candidateIndex.setCurrentResumeName(candidateResumes.getResumeName());
				candidateIndex.setFileContentType(candidateResumes.getFileContentType());
				candidateIndex.setFileUrl(candidateResumes.getFileUrl());
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
			
			if(null != candidate.getIsSharedInMarketplace()){
				candidateIndex.setIsSharedInMarketplace(candidate.getIsSharedInMarketplace());	
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
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if(candidate.getCreatedDate() != null){
				candidateIndex.setCreatedDate(df.format(new Date(candidate.getCreatedDate().getTime())));
			}
			
			if(null != candidate.getDateOfBirth()){
				String dateofBirth  = df.format(candidate.getDateOfBirth());
				candidateIndex.setDateofBirth(dateofBirth);
			}
			if(candidate.getCreatedBy() != null && !candidate.getCreatedBy().isEmpty()) {
				User user = userService.getUserByUserCode(candidate.getCreatedBy());
				StringBuilder sb = new StringBuilder();
				if(user.getLastName() != null){
					candidateIndex.setCreatedBy(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}else{
					candidateIndex.setCreatedBy(sb.append(user.getFirstName()).append(" ").toString());
				}
				candidateIndex.setCreatedByUserCode(user.getUserCode());
			}
			
			
			if(candidate.getUpdatedDate() != null){
				candidateIndex.setUpdatedDate(df.format(new Date(candidate.getUpdatedDate().getTime())));
			}
			
			if(null != candidate.getIsProfilePublic()){
				candidateIndex.setIsProfilePublic(candidate.getIsProfilePublic());
			}
			
			if(candidate.getCandidateTags() != null && !candidate.getCandidateTags().isEmpty()) {
				candidateIndex.setTags(candidate.getCandidateTags());
			}
			
			
			candidateIndex.setUpdatedBy(candidate.getUpdatedBy());
			
			candidateIndex.setCandidateUUID(candidate.getCandidateUUID());
			
		}
		
		return candidateIndex;
	}
	
	public String firstLetterCapitalizedString(String name){
		 String finalString = WordUtils.capitalizeFully(name);
		return finalString;
	}
}
