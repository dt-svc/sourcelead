package com.bis.sourcelead.elk.index.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;

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
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;

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
			requirementIndex.setJobTitle(requirement.getJobTitle());
			requirementIndex.setJobType(requirement.getJobType());
			requirementIndex.setOtherJobType(requirement.getOtherJobType());
			requirementIndex.setIndustryName(requirement.getIndustryName());
			requirementIndex.setTravelAllownace(requirement.getTravelAllowance());
			requirementIndex.setReqCoolingOffPeriod(requirement.getReqCoolingOffPeriod());
			requirementIndex.setReleaseJobToAllUsers(requirement.getReleaseJobToAllUsers());
			requirementIndex.setProjectDurationMonths(requirement.getProjectDurationMonths());
			requirementIndex.setProjectDurationYears(requirement.getProjectDurationYears());
			requirementIndex.setIsreceivingApplicants(requirement.getIsreceivingApplicants());
			requirementIndex.setGeneratedId(requirement.getGeneratedId());
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
			if (null != requirement.getMinExp()) {
				requirementIndex.setMinExperience(requirement.getMinExp());
			}
			if (null != requirement.getMaxExp()) {
				requirementIndex.setMaxExperience(requirement.getMaxExp());
			}
			requirementIndex.setRequirementStatus(requirement.getRequirementSubmissionStatus().toString());
			// requirementIndex.setRequirementSubmissionPreviousStatus(requirement.getRequirementSubmissionPreviousStatus().toString());
			requirementIndex.setCountry(requirement.getCountry());
			requirementIndex.setState(requirement.getState());
			requirementIndex.setCity(requirement.getCity());
			requirementIndex.setLocation(requirement.getLocation());
			requirementIndex.setZipCode(requirement.getZipCode());
			requirementIndex.setReqCreatedBy(requirement.getCreatedBy().getId());
			requirementIndex.setReqUpdatedBy(requirement.getUpdatedBy());
			if (requirement.getManagerUserId() != null) {
				requirementIndex.setManagerUserId(requirement.getManagerUserId().getId().toString());
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
			Long userId = requirement.getManagerUserId().getId();
			User ownerUser = userService.getUserById(userId);
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
			if (null != requirementDTO.getParallelApprover()) {
				String[] reqApproverUserId = requirementDTO.getParallelApprover().toArray(new String[requirementDTO.getParallelApprover().size()]);
				requirementIndex.setReqLevel1ApproverUserId(reqApproverUserId);
			}
			if (StringUtils.isNotBlank(requirementDTO.getSequentialApprover1())) {
				String sequentialApprovers1 = requirementDTO.getSequentialApprover1();
				String sequentialApprovers2 = requirementDTO.getSequentialApprover2();
				requirementIndex.setReqLevel1ApproverUserId(sequentialApprovers1.split(","));
				requirementIndex.setReqLevel2ApproverUserId(sequentialApprovers2.split(","));
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
		     }
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
