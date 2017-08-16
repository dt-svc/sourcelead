package com.bis.sourcelead.elk.index.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.UserService;

@Component
public class RequirementHelper {

	private static final Logger logger = LoggerFactory.getLogger(RequirementHelper.class);

	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequirementApproverHierarchyService requirementApproverHierarchyService;
		/**
		 * Prepares Requirement Index to store data into Elastic Search
		 * @param requirement
		 * @param jsonObj 
		 * @return RequirementIndex
		 * @throws Exception
		 */
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	public RequirementIndex populateRequirementIndex(Requirement requirement, JSONObject jsonObj) throws Exception {
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
			requirementIndex.setProjectDurationMonths(requirement.getProjectDurationMonths());
			requirementIndex.setProjectDurationYears(requirement.getProjectDurationYears());
			requirementIndex.setIsreceivingApplicants(requirement.getIsreceivingApplicants());
			
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
			//requirementIndex.setRequirementSubmissionPreviousStatus(requirement.getRequirementSubmissionPreviousStatus().toString());
			requirementIndex.setCountry(requirement.getCountry());
			requirementIndex.setState(requirement.getState());
			requirementIndex.setCity(requirement.getCity());
			requirementIndex.setLocation(requirement.getLocation());
			requirementIndex.setZipCode(requirement.getZipCode());
			requirementIndex.setReqCreatedBy(requirement.getCreatedBy().getId());
			requirementIndex.setReqUpdatedBy(requirement.getUpdatedBy());
			if(requirement.getManagerUserId() != null){
				requirementIndex.setManagerUserId(requirement.getManagerUserId().getId().toString());
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if(null != requirement.getClosedDate()){
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
			if(ownerUser != null){
				if(ownerUser.getFirstName() != null && ownerUser.getLastName()!= null ){
					sbilder.append((ownerUser.getFirstName())).append(" ").append(ownerUser.getLastName());
				}else{
					sbilder.append((ownerUser.getFirstName()));
				}
				requirementIndex.setManager(sbilder.toString());
			}
			
			// to store approver of the req in elk
			if(jsonObj.has("parallelApprover") && null !=jsonObj){
				String parallelApprovers =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("parallelApprover");
					parallelApprovers = jsonArray.toString();
				}
				catch(JSONException e){
					parallelApprovers =jsonObj.getString("parallelApprover");
				}
				parallelApprovers = parallelApprovers.replaceAll("\\[", "").replaceAll("\\]","");
				parallelApprovers = parallelApprovers.replaceAll("\"", "");
			    String[] reqApproverUserId = parallelApprovers.split(",");
			    requirementIndex.setReqLevel1ApproverUserId(reqApproverUserId);
			}
			 if(null !=jsonObj && jsonObj.has("sequentialApprover1")&& jsonObj.getString("sequentialApprover1")!=null && !"".equalsIgnoreCase(jsonObj.getString("sequentialApprover1")) ){
				 String sequentialApprovers1 = jsonObj.getString("sequentialApprover1");
				 String sequentialApprovers2 =jsonObj.getString("sequentialApprover2");
				 requirementIndex.setReqLevel1ApproverUserId(sequentialApprovers1.split(","));
				 requirementIndex.setReqLevel2ApproverUserId(sequentialApprovers2.split(","));
			 }
			 
			 	if (null !=jsonObj && jsonObj.has("assignedToMaxCount")&& !"".equalsIgnoreCase(jsonObj.getString("assignedToMaxCount"))){
					String assignedToMaxCount = jsonObj.getString("assignedToMaxCount");
							if(jsonObj.has("assignedTeamId") && StringUtils.isNotEmpty(jsonObj.getString("assignedTeamId")) && !"undefined".equalsIgnoreCase(jsonObj.getString("assignedTeamId"))){
								String assignedTeamId = jsonObj.getString("assignedTeamId");
								String[] teamOwnerUserId = assignedTeamId.split(",");
								requirementIndex.setTeamOwnerUserId(teamOwnerUserId);
							}   
							String teamAssignUserStr = "";
							if(StringUtils.isNotEmpty(assignedToMaxCount)){
							int maxCount = Integer.parseInt(assignedToMaxCount);
								for (int c = 1; c < maxCount; c++) {
									if(jsonObj.has("assignUserId"+c)&& !"".equalsIgnoreCase(jsonObj.getString("assignUserId"+c))){
											if(c==1){
												teamAssignUserStr = jsonObj.getString("assignUserId"+c);
											}else{
												teamAssignUserStr = teamAssignUserStr + ","+jsonObj.getString("assignUserId"+c);
											}
									}
								}
							}
							if(!"".equalsIgnoreCase(teamAssignUserStr)){
								String[] teamAssignUserId = teamAssignUserStr.split(",");	
								requirementIndex.setTeamAssignUserId(teamAssignUserId);
							}
				}
			
			//requirementIndex.setRequirementSubmissionStatus(requirement.getRequirementSubmissionStatus().toString());
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
					if (requirement1.getClientId().getResponderLocationId() !=null) {
						requirementIndex
								.setClient(requirement1.getClientId().getResponderLocationId().getOrgLocationName());
						/*if (requirement1.getSpocId().getSpocUser() !=null &&requirement1.getSpocId().getSpocUser() !=null) {
							sb.append(requirement1.getSpocId().getSpocUser().getFirstName()).append(" ")
									.append(requirement1.getSpocId().getSpocUser().getLastName());
						} else {
							sb.append(requirement1.getSpocId().getSpocUser().getFirstName());
						}
						requirementIndex.setClientContactName(sb.toString());
						requirementIndex.setClientContactEmail(requirement1.getSpocId().getSpocUser().getEmail());*/
					}
				}
				if (Constants.VENDOR.equalsIgnoreCase(requirement1.getClientId().getResponderType())) {
					requirementIndex.setClient(requirement1.getClientId().getLocationId().getOrgLocationName());
					/*if (requirement1.getSpocId().getSpocUser() !=null && requirement1.getSpocId().getSpocUser()!=null) {
						sb.append(requirement1.getSpocId().getSpocUser().getFirstName()).append(" ")
								.append(requirement1.getSpocId().getSpocUser().getLastName());
					} else {
						sb.append(requirement1.getSpocId().getSpocUser().getFirstName());
					}
					requirementIndex.setClientContactName(sb.toString());
					requirementIndex
							.setClientContactEmail(requirement1.getClientId().getLocationId().getUser().getEmail());
				}*/
			}
				
			//Prasad salina	
			if(null != requirement1.getSpocId()){
				if (requirement1.getSpocId().getClientUser() !=null && requirement1.getSpocId().getClientUser().getLastName()!=null && !"".equals(requirement1.getSpocId().getClientUser().getLastName())) {
					sb.append(requirement1.getSpocId().getClientUser().getFirstName()).append(" ")
							.append(requirement1.getSpocId().getClientUser().getLastName());
				} else {
					sb.append(requirement1.getSpocId().getClientUser().getFirstName());
				}
				requirementIndex.setClientContactName(sb.toString());
				requirementIndex.setClientContactEmail(requirement1.getSpocId().getClientUser().getEmail());
			}
			//requirementIndex.setClientContactEmail(requirement1.getClientId().getLocationId().getUser().getEmail());
		}
				
			// requirementIndex.setClient(requirement.getClientName());
			// skill pending
			if (null != requirement.getSkills()) {
				String[] skills = requirement.getSkills().split(",");
				requirementIndex.setSkills(skills);
			}
			if(null != requirement.getNoticePeriod()){
				String[] noticePeriod = requirement.getNoticePeriod().split(",");
				requirementIndex.setNoticePeriod(noticePeriod);
			}
			requirementIndex.setJobId(requirement.getJobId());
			requirementIndex.setPriority(requirement.getPriority());
			requirementIndex.setCurrentRateCurrency(requirement.getCurrencyCode());
			requirementIndex.setNumberOfPositions(requirement.getPositions());
			if(null != requirement.getTotalApplicants()){
				requirementIndex.setNumberOfApplicants(requirement.getTotalApplicants().toString());
			}
			requirementIndex.setReleasedBy(requirement.getReleasedBy());
			
			requirementIndex.setRequirementUUID(requirement.getRequirementUUID());
			
			if(null != requirement.getIsPublic()){
				requirementIndex.setIsPublicJob(requirement.getIsPublic());
			}else{
				requirementIndex.setIsPublicJob("N");
			}
			
			if(null != requirement.getIsCrowdSourcing()){
				requirementIndex.setIsCrowdSourcingJob(requirement.getIsCrowdSourcing());
			}else{
				requirementIndex.setIsCrowdSourcingJob("N");
			}
			
			if(null != requirement.getPostedOrgName() && !requirement.getPostedOrgName().isEmpty()){
				requirementIndex.setPostedOrg(requirement.getPostedOrgName());
			}
			
			if (null != requirement.getAppliedProfessionals() && !"".equalsIgnoreCase(requirement.getAppliedProfessionals())) {
				String[] appliedProfessionals = requirement.getAppliedProfessionals().split(",");
				requirementIndex.setAppliedProfessionals(appliedProfessionals);
			}
			
			
			if (null != requirement.getAppliedRecruiters() && !"".equalsIgnoreCase(requirement.getAppliedRecruiters())) {
				String[] appliedRecruiters = requirement.getAppliedRecruiters().split(",");
				requirementIndex.setAppliedRecruiters(appliedRecruiters);
			}
			
			if(null != requirement.getReqlevel1ApproverStatus() && !"".equalsIgnoreCase(requirement.getReqlevel1ApproverStatus())){
				requirementIndex.setReqLevel1ApproverStatus(requirement.getReqlevel1ApproverStatus());	
			}
			if(null != requirement.getReqlevel2ApproverStatus() && !"".equalsIgnoreCase(requirement.getReqlevel1ApproverStatus())){
				requirementIndex.setReqLevel2ApproverStatus(requirement.getReqlevel2ApproverStatus());	
			}
			
			if(!isNullOrEmpty(requirement.getRewardType())){
				requirementIndex.setRewardType(requirement.getRewardType());
			}
			
			if(!isNullOrEmpty(requirement.getRewardPercentage())){
				requirementIndex.setRewardPercentage(requirement.getRewardPercentage());
			}
			
			if(!isNullOrEmpty(requirement.getRewardFlat())){
				requirementIndex.setRewardFlat(requirement.getRewardFlat());
			}
			
			if(!isNullOrEmpty(requirement.getRewardFlatRateType())){
				requirementIndex.setRewardFlatRateType(requirement.getRewardFlatRateType());
			}
			
			if(!isNullOrEmpty(requirement.getRewardGuaranteePeriod())){
				requirementIndex.setRewardGuaranteePeriod(requirement.getRewardGuaranteePeriod());
			}
			
			if (null != requirement.getVisaType()) {
				String[] visaTypes = requirement.getVisaType().split(",");
				requirementIndex.setVisaTypes(visaTypes);
			}
			if (null != requirement.getGeneratedId() && !"".equalsIgnoreCase(requirement.getGeneratedId())) {
				requirementIndex.setGeneratedId(requirement.getGeneratedId());
			}
		}
		
		return requirementIndex;
	}

}
