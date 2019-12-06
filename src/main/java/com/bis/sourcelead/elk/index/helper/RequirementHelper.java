package com.bis.sourcelead.elk.index.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Component
public class RequirementHelper {
	private static final Logger logger = LoggerFactory.getLogger(RequirementHelper.class);
	@Autowired
	private RequirementService requirementService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private RequirementApproverHierarchyService requirementApproverHierarchyService;
	
	@Autowired
	private CommonUtil commonUtil; 
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	/**
	 * Prepares Requirement Index to store data into Elastic Search
	 * 
	 * @param requirement
	 * @param jsonObj
	 * @return RequirementIndex
	 * @throws Exception
	 */
	protected boolean isNullOrEmpty(String str)
	{
		return (str == null || "".equals(str.trim()));
	}
	
	public RequirementIndex populateRequirementIndex(Requirement requirement,RequirementDTO requirementDTO) throws Exception
	{
		RequirementIndex requirementIndex = new RequirementIndex();
		if (null != requirement) {
			requirementIndex.setRequirementId(requirement.getId());
			requirementIndex.setOrgLocationId(requirement.getOrgLocationId().getId());
			requirementIndex.setJobTitle(commonUtil.firstLetterCapitalizedString(requirement.getJobTitle()));
			requirementIndex.setJobType(requirement.getJobType());
			requirementIndex.setOtherJobType(requirement.getOtherJobType());
			requirementIndex.setIndustryName(requirement.getIndustryName());
			requirementIndex.setTravelAllownace(requirement.getTravelAllowance());
			requirementIndex.setReqCoolingOffPeriod(requirement.getReqCoolingOffPeriod());
			requirementIndex.setReleaseJobToAllUsers(requirement.getReleaseJobToAllUsers());
			requirementIndex.setProjectDurationMonths(requirement.getProjectDurationMonths());
			requirementIndex.setProjectDurationYears(requirement.getProjectDurationYears());
			requirementIndex.setIsreceivingApplicants(requirement.getIsreceivingApplicants());
			requirementIndex.setUrl(requirement.getUrl());
			/*if(requirement.getWorkflowId()!= null) {
			requirementIndex.setWorkflowId(requirement.getWorkflowId().getId());
			}*/
			//WORKFLOWID added by SRINIVAS SABBU on 24-May-2019.
			// below lines is commented for to move in next release build.
			//requirementIndex.setWorkflowId(requirement.getWorkflowId().getId());
			requirementIndex.setGeneratedId(requirement.getGeneratedId());
			if(requirement.getIsClientJob() !=null){
				requirementIndex.setIsClientJob(requirement.getIsClientJob());
			}
			if(requirement.getProcess() !=null){
				requirementIndex.setProcess(requirement.getProcess());
			}
			if(requirement.getMedicalClearence() != null){
			requirementIndex.setMedicalClearence(requirement.getMedicalClearence());
			}
			if(requirement.getSecurityClearance() != null){
			requirementIndex.setSecurityClearance(requirement.getSecurityClearance());
			}
			if(requirement.getOverTime() != null){
			requirementIndex.setOverTime(requirement.getOverTime());
			}
			if(requirement.getBackgroundCheck() != null){
			requirementIndex.setBackgroundCheck(requirement.getBackgroundCheck());
			}
			if(requirement.getGender() != null){
			requirementIndex.setGender(requirement.getGender());
			}
			if(requirement.getExperienceLevel() != null){
			requirementIndex.setExperienceLevel(requirement.getExperienceLevel().toString());
			}
			
			if(requirement.getProjectClientID() != null){
				requirementIndex.setProjectClientID(requirement.getProjectClientID().getId().toString());
				}
				if(requirement.getClientId() != null && requirement.getClientId().getId() != null){
			requirementIndex.setOrgRelationId(requirement.getClientId().getId().toString());
				}

			if(requirement.getClientId()!= null && requirement.getClientId().getId()!= null){
			requirementIndex.setOrgRelationId(requirement.getClientId().getId().toString());
			}

			if (null != requirement.getDescription() && !"".equals(requirement.getDescription())) {
				requirementIndex.setDescription(requirement.getDescription());
			}
			if (null != requirement.getMaxRate() && !"".equals(requirement.getMaxRate())) {
				requirementIndex.setMaxRate(Float.parseFloat(requirement.getMaxRate()));
			}
			if (null != requirement.getRate() && !"".equals(requirement.getRate())) {
				requirementIndex.setRate(Float.parseFloat(requirement.getRate()));
			}
			if (null != requirement.getRateType() && !"".equals(requirement.getRateType())) {
				requirementIndex.setRateType(requirement.getRateType());
			}
			if (StringUtils.isNotEmpty(requirement.getMinExp())) {
				requirementIndex.setMinExperience(Float.parseFloat(requirement.getMinExp()));
			}
			if (StringUtils.isNotEmpty(requirement.getMaxExp())) {
				requirementIndex.setMaxExperience(Float.parseFloat(requirement.getMaxExp()));
			}
			if(null != requirement.getCurrencyCode() && !"".equalsIgnoreCase(requirement.getCurrencyCode())){
				requirementIndex.setCurrentRateCurrency(requirement.getCurrencyCode());
			}
			if(null != requirement.getClientCurrencyCode() && !"".equalsIgnoreCase(requirement.getClientCurrencyCode())){
				requirementIndex.setClientCurrencyCode(requirement.getClientCurrencyCode());
			}
			if(null != requirement.getClientMaxRate() && !"".equalsIgnoreCase(requirement.getClientMaxRate())){
				requirementIndex.setClientMaxRate(Float.parseFloat(requirement.getClientMaxRate()));
			}
			if(null != requirement.getClientRate() && !"".equalsIgnoreCase(requirement.getClientRate())){
				requirementIndex.setClientRate(Float.parseFloat(requirement.getClientRate()));
			}
			if(null != requirement.getClientRateType() && !"".equalsIgnoreCase(requirement.getClientRateType())){
				requirementIndex.setClientRateType(requirement.getClientRateType());
			}
			
			if(requirement.getJobsTags() != null && !requirement.getJobsTags().isEmpty()) {
				requirementIndex.setTags(requirement.getJobsTags());
			}
			
			if(requirement.getRequirementSubmissionStatus() != null) {
			requirementIndex.setRequirementStatus(requirement.getRequirementSubmissionStatus().toString());
			}
			// requirementIndex.setRequirementSubmissionPreviousStatus(requirement.getRequirementSubmissionPreviousStatus().toString());
			requirementIndex.setRequirementSubmissionPreviousStatus(requirement.getRequirementSubmissionPreviousStatus() != null ? requirement.getRequirementSubmissionPreviousStatus().name() : "");
			
			
			StringBuilder country = new StringBuilder();
			StringBuilder state = new StringBuilder();
			StringBuilder city = new StringBuilder();
			
			Set<String> countrySet = new HashSet<>();
			Set<String> stateSet = new HashSet<>();
			Set<String> citySet = new HashSet<>();
			
			List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
			if(locationsList != null && !locationsList.isEmpty()) {
				for(RequirementLocations locations : locationsList) {
					if(StringUtils.isNotBlank(locations.getCountry())) {
						countrySet.add(locations.getCountry());
					}
					
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
				requirementIndex.setCountry(countryStr);
			}
			
			if(state.toString() != null && !state.toString().isEmpty()) {
				String stateStr = state.toString();
				stateStr = stateStr.substring(0, stateStr.length() - 1);
				requirementIndex.setState(stateStr);
			}
			
			if(city.toString() != null && !city.toString().isEmpty()) {
				String cityStr = city.toString();
				cityStr = cityStr.substring(0, cityStr.length() - 1);
				requirementIndex.setCity(cityStr);
			}
			
			requirementIndex.setLocation(requirement.getLocation());
			requirementIndex.setZipCode(requirement.getZipCode());
			requirementIndex.setReqCreatedBy(requirement.getCreatedBy().getId());
			requirementIndex.setReqUpdatedBy(requirement.getUpdatedBy());
			if (requirement.getManagerUserId() != null) {
				requirementIndex.setManagerUserId(requirement.getManagerUserId().getId().toString());
			}
			if (requirement.getPrimaryRecruiterUserId() != null) {
				requirementIndex.setPrimaryRecruiterUserId(requirement.getPrimaryRecruiterUserId().getId().toString());
			} else {
				requirementIndex.setPrimaryRecruiterUserId("");
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (null != requirement.getClosedDate()) {
				String reqclosedDate = dateFormat.format(requirement.getClosedDate());
				requirementIndex.setClosedDate(reqclosedDate);
			}
			String reqcreatedDate = dateFormat.format(requirement.getCreatedDate());
			requirementIndex.setCreatedDate(reqcreatedDate);
			String reqUpDatedDate = dateFormat.format(requirement.getCreatedDate());
			requirementIndex.setUpdatedDate(reqUpDatedDate);
			User ownerUser = new User();
			if(null != requirement && null != requirement.getManagerUserId() && null != requirement.getManagerUserId().getId()) {
			Long userId = requirement.getManagerUserId().getId();
			ownerUser = userService.getUserById(userId);
			}

			StringBuilder sbilder = new StringBuilder();
			if (ownerUser != null) {
				if (ownerUser.getFirstName() != null && ownerUser.getLastName() != null) {
					sbilder.append((ownerUser.getFirstName())).append(" ").append(ownerUser.getLastName());
				}
				else {
					sbilder.append((ownerUser.getFirstName()));
				}
				requirementIndex.setManager(sbilder.toString());
			}
			// to store approver of the req in elk
		if(requirementDTO != null) {
			if (requirementDTO.getParallelApprover() != null && !requirementDTO.getParallelApprover().isEmpty()) {
				String[] reqApproverUserId = requirementDTO.getParallelApprover().toArray(new String[requirementDTO.getParallelApprover().size()]);
				requirementIndex.setReqLevel1ApproverUserId(reqApproverUserId);
			}
			if (StringUtils.isNotBlank(requirementDTO.getSequentialApprover1())) {
				String sequentialApprovers1 = requirementDTO.getSequentialApprover1();
				String sequentialApprovers2 = requirementDTO.getSequentialApprover2();
				requirementIndex.setReqLevel1ApproverUserId(sequentialApprovers1.split(","));
				requirementIndex.setReqLevel1ApproverStatus(requirementDTO.getReqLevel1ApproverStatus());
				requirementIndex.setReqLevel2ApproverUserId(sequentialApprovers2.split(","));
				requirementIndex.setReqLevel2ApproverStatus(requirementDTO.getReqLevel2ApproverStatus());
			}
		}
			HashSet<String> teamOwnerUserId = new HashSet<>();
			HashSet<String> teamAssignUserId = new HashSet<>();
			if(requirement.getRequirementAssignTo() != null &&  !requirement.getRequirementAssignTo().isEmpty()){
				
				for(RequirementAssignTo assignTo: requirement.getRequirementAssignTo()){
					if(assignTo.getAssignUserId() != null && assignTo.getAssignUserId().getId() != null)
						teamAssignUserId.add(assignTo.getAssignUserId().getId().toString());
					
					if(assignTo.getTeamId() != null && assignTo.getTeamId().getId() != null){
						Team team = teamService.getTeamByTeamId(assignTo.getTeamId().getId());
						if(team.getOwnerUserId() != null && team.getOwnerUserId().getId() != null)
						teamOwnerUserId.add(team.getOwnerUserId().getId().toString());
					}
						
				}
				
				
			requirementIndex.setTeamOwnerUserId(teamOwnerUserId.toArray(new String[teamOwnerUserId.size()]));
			requirementIndex.setTeamAssignUserId(teamAssignUserId.toArray(new String[teamAssignUserId.size()]));
		     }else {
		    	 requirementIndex.setTeamOwnerUserId(null);
					requirementIndex.setTeamAssignUserId(null); 
		     }
		   
			// requirementIndex.setRequirementSubmissionStatus(requirement.getRequirementSubmissionStatus().toString());
			requirementIndex.setIsTemplate(requirement.getIsTemplate());
			// education
			if (null != requirement.getEducation()) {
				String[] education = requirement.getEducation().split(",");
				requirementIndex.setEducation(education);
			}
			if (requirement.getClientId() != null) {
				Requirement requirement1 = requirementService.getRequirementByRequirementId(requirement.getId());
				StringBuilder sb = new StringBuilder();
				if (Constants.CLIENT.equalsIgnoreCase(requirement1.getClientId().getResponderType())) {
					if (requirement1.getClientId().getResponderLocationId() != null) {
						requirementIndex.setClient(requirement1.getClientId().getResponderLocationId().getOrgLocationName());
					}
				}
				if (Constants.VENDOR.equalsIgnoreCase(requirement1.getClientId().getResponderType())) {
					requirementIndex.setClient(requirement1.getClientId().getLocationId().getOrgLocationName());
				}
				// Prasad salina
				if (null != requirement1.getSpocId()) {
					if (requirement1.getSpocId().getClientUser() != null
							&& requirement1.getSpocId().getClientUser().getLastName() != null
							&& !"".equals(requirement1.getSpocId().getClientUser().getLastName())) {
						sb.append(requirement1.getSpocId().getClientUser().getFirstName()).append(" ").append(requirement1.getSpocId().getClientUser().getLastName());
					}
					else {
						sb.append(requirement1.getSpocId().getClientUser().getFirstName());
					}
					requirementIndex.setClientContactName(sb.toString());
					requirementIndex.setClientContactEmail(requirement1.getSpocId().getClientUser().getEmail());
				}
				// requirementIndex.setClientContactEmail(requirement1.getClientId().getLocationId().getUser().getEmail());
			}
			// requirementIndex.setClient(requirement.getClientName());
			// skill pending
			if (null != requirement.getSkills()) {
				String[] skills = requirement.getSkills().split(",");
				requirementIndex.setSkills(skills);
			}
			if (null != requirement.getNoticePeriod()) {
				String[] noticePeriod = requirement.getNoticePeriod().split(",");
				requirementIndex.setNoticePeriod(noticePeriod);
			}
			requirementIndex.setJobId(requirement.getJobId());
			requirementIndex.setPriority(requirement.getPriority());
			requirementIndex.setCurrentRateCurrency(requirement.getCurrencyCode());
			requirementIndex.setNumberOfPositions(requirement.getPositions());
			if (null != requirement.getTotalApplicants()) {
				requirementIndex.setNumberOfApplicants(requirement.getTotalApplicants().toString());
			}
			requirementIndex.setReleasedBy(requirement.getReleasedBy());
			requirementIndex.setRequirementUUID(requirement.getRequirementUUID());
			if (null != requirement.getIsPublic()) {
				requirementIndex.setIsPublicJob(requirement.getIsPublic());
			}
			else {
				requirementIndex.setIsPublicJob("N");
			}
			if (null != requirement.getIsCrowdSourcing()) {
				requirementIndex.setIsCrowdSourcingJob(requirement.getIsCrowdSourcing());
			}
			else {
				requirementIndex.setIsCrowdSourcingJob("N");
			}
			if (null != requirement.getPostedOrgName()
					&& !requirement.getPostedOrgName().isEmpty()) {
				requirementIndex.setPostedOrg(requirement.getPostedOrgName());
			}
			if (null != requirement.getAppliedProfessionals()
					&& !"".equalsIgnoreCase(requirement.getAppliedProfessionals())) {
				String[] appliedProfessionals = requirement.getAppliedProfessionals().split(",");
				requirementIndex.setAppliedProfessionals(appliedProfessionals);
			}
			if (null != requirement.getAppliedRecruiters()
					&& !"".equalsIgnoreCase(requirement.getAppliedRecruiters())) {
				String[] appliedRecruiters = requirement.getAppliedRecruiters().split(",");
				requirementIndex.setAppliedRecruiters(appliedRecruiters);
			}
			if (null != requirement.getReqlevel1ApproverStatus()
					&& !"".equalsIgnoreCase(requirement.getReqlevel1ApproverStatus())) {
				requirementIndex.setReqLevel1ApproverStatus(requirement.getReqlevel1ApproverStatus());
			}
			if (null != requirement.getReqlevel2ApproverStatus()
					&& !"".equalsIgnoreCase(requirement.getReqlevel1ApproverStatus())) {
				requirementIndex.setReqLevel2ApproverStatus(requirement.getReqlevel2ApproverStatus());
			}
			if (!isNullOrEmpty(requirement.getRewardType())) {
				requirementIndex.setRewardType(requirement.getRewardType());
			}
			if (!isNullOrEmpty(requirement.getRewardPercentage())) {
				requirementIndex.setRewardPercentage(requirement.getRewardPercentage());
			}
			if (!isNullOrEmpty(requirement.getRewardFlat())) {
				requirementIndex.setRewardFlat(requirement.getRewardFlat());
			}
			if (!isNullOrEmpty(requirement.getRewardFlatRateType())) {
				requirementIndex.setRewardFlatRateType(requirement.getRewardFlatRateType());
			}
			if (!isNullOrEmpty(requirement.getRewardGuaranteePeriod())) {
				requirementIndex.setRewardGuaranteePeriod(requirement.getRewardGuaranteePeriod());
			}
			if (null != requirement.getVisaType()) {
				String[] visaTypes = requirement.getVisaType().split(",");
				requirementIndex.setVisaTypes(visaTypes);
			}
			if (null != requirement.getGeneratedId()
					&& !"".equalsIgnoreCase(requirement.getGeneratedId())) {
				requirementIndex.setGeneratedId(requirement.getGeneratedId());
			}
		}
		return requirementIndex;
	}
}
