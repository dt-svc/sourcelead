package com.bis.sourcelead.elk.index.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
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
	public RequirementIndex populateRequirementIndex(Requirement requirement, JSONObject jsonObj) throws Exception {
		RequirementIndex requirementIndex = new RequirementIndex();
		
		if (null != requirement) {
			requirementIndex.setRequirementId(requirement.getId());
			requirementIndex.setOrgLocationId(requirement.getOrgLocationId().getId());
			requirementIndex.setJobTitle(requirement.getJobTitle());
			requirementIndex.setJobType(requirement.getJobType());
			if (requirement.getRate() !=null && !"".equalsIgnoreCase(requirement.getRate())) {
				requirementIndex.setRate(Float.parseFloat(requirement.getRate()));
			}
			requirementIndex.setRateType(requirement.getRateType());
			if (requirement.getMinExp() !=null && !"".equalsIgnoreCase(requirement.getMinExp())) {
				requirementIndex.setMinExperience(Integer.parseInt(requirement.getMinExp()));
			}
			if (requirement.getMaxExp() !=null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
				requirementIndex.setMaxExperience(Integer.parseInt(requirement.getMaxExp()));
			}
			requirementIndex.setRequirementStatus(requirement.getRequirementSubmissionStatus().toString());
			requirementIndex.setCountry(requirement.getCountry());
			requirementIndex.setState(requirement.getState());
			requirementIndex.setCity(requirement.getCity());
			requirementIndex.setReqCreatedBy(requirement.getCreatedBy());
			requirementIndex.setReqUpdatedBy(requirement.getUpdatedBy());
			requirementIndex.setManagerUserId(requirement.getManagerUserId().getId().toString());
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			 String reqcreatedDate = dateFormat.format(requirement.getCreatedDate());
			requirementIndex.setReqCreatedDate(reqcreatedDate);
			 String reqUpDatedDate = dateFormat.format(requirement.getCreatedDate());
			requirementIndex.setReqUpdatedDate(reqUpDatedDate);
			Long userId = requirement.getManagerUserId().getId();
			User ownerUser = userService.getUserById(userId);
			StringBuilder sbilder = new StringBuilder();
			if(ownerUser != null && ownerUser.getFirstName() != null && ownerUser.getLastName()!= null ){
			requirementIndex.setManager(sbilder.append((ownerUser.getFirstName())).append(" ").append(ownerUser.getLastName()).toString());
			}else{
			requirementIndex.setManager(sbilder.append((ownerUser.getFirstName())).toString());
			}
			
			
			
			// to store approver of the req in elk
			if(jsonObj.has("parallelApprover")){
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
			 if(jsonObj.has("sequentialApprover1")&& jsonObj.getString("sequentialApprover1")!=null && !"".equalsIgnoreCase(jsonObj.getString("sequentialApprover1")) ){
				 String sequentialApprovers1 = jsonObj.getString("sequentialApprover1");
				 String sequentialApprovers2 =jsonObj.getString("sequentialApprover2");
				 requirementIndex.setReqLevel1ApproverUserId(sequentialApprovers1.split(","));
				 requirementIndex.setReqLevel2ApproverUserId(sequentialApprovers2.split(","));
			 }
			 
				if (jsonObj.has("assignedToMaxCount")){
					String assignedToMaxCount = jsonObj.getString("assignedToMaxCount");
							if(jsonObj.has("assignedTeamId") && StringUtils.isNotEmpty(jsonObj.getString("assignedTeamId")) && !"undefined".equalsIgnoreCase(jsonObj.getString("assignedTeamId"))){
								String assignedTeamId = jsonObj.getString("assignedTeamId");
								String[] teamOwnerUserId = assignedTeamId.split(",");
								requirementIndex.setTeamOwnerUserId(teamOwnerUserId);
							}   
							String teamAssignUserStr = null;
							if(StringUtils.isNotEmpty(assignedToMaxCount)){
							int maxCount = Integer.parseInt(assignedToMaxCount);
								for (int c = 1; c < maxCount; c++) {
									if(c==1){
										teamAssignUserStr = jsonObj.getString("assignUserId"+c);
									}else{
										teamAssignUserStr = teamAssignUserStr + ","+jsonObj.getString("assignUserId"+c);
									}
									
								}
							}
							String[] teamAssignUserId = teamAssignUserStr.split(",");	
							requirementIndex.setTeamAssignUserId(teamAssignUserId);
				}
			
			
			//requirementIndex.setRequirementSubmissionStatus(requirement.getRequirementSubmissionStatus().toString());
			requirementIndex.setIsTemplate(requirement.getIsTemplate());
			
			
			// education
			if (null != requirement.getEducation() && !"".equalsIgnoreCase(requirement.getEducation())) {
				String[] education = requirement.getEducation().split(",");
				requirementIndex.setEducation(education);
			}
			if (requirement.getClientId() != null) {
				Requirement requirement1 = requirementService.getRequirementByRequirementId(requirement.getId());
				StringBuilder sb = new StringBuilder();
				if (Constants.CLIENT.equalsIgnoreCase(requirement1.getClientId().getResponderType())) {
					if (requirement1.getClientId().getResponderLocationId() !=null) {
						requirementIndex
								.setClient(requirement1.getClientId().getResponderLocationId().getOrg().getOrgName());
						if (requirement1.getSpocId().getSpocUser() !=null &&requirement1.getSpocId().getSpocUser() !=null) {
							sb.append(requirement1.getSpocId().getSpocUser().getFirstName()).append(" ")
									.append(requirement1.getSpocId().getSpocUser().getLastName());
						} else {
							sb.append(requirement1.getSpocId().getSpocUser().getFirstName());
						}
						requirementIndex.setClientContactName(sb.toString());
						requirementIndex.setClientContactEmail(requirement1.getSpocId().getSpocUser().getEmail());
					}
				}
				if (Constants.VENDOR.equalsIgnoreCase(requirement1.getClientId().getResponderType())) {
					requirementIndex.setClient(requirement1.getClientId().getLocationId().getOrg().getOrgName());
					if (requirement1.getSpocId().getSpocUser() !=null && requirement1.getSpocId().getSpocUser()!=null) {
						sb.append(requirement1.getSpocId().getSpocUser().getFirstName()).append(" ")
								.append(requirement1.getSpocId().getSpocUser().getLastName());
					} else {
						sb.append(requirement1.getSpocId().getSpocUser().getFirstName());
					}
					requirementIndex.setClientContactName(sb.toString());
					requirementIndex
							.setClientContactEmail(requirement1.getClientId().getLocationId().getUser().getEmail());
				}
			}
			// requirementIndex.setClient(requirement.getClientName());
			// skill pending
			if (null != requirement.getSkills() && !"".equalsIgnoreCase(requirement.getSkills())) {
				String[] skills = requirement.getSkills().split(",");
				requirementIndex.setSkills(skills);
			}
			requirementIndex.setJobId(requirement.getJobId());
			// requirementIndex.setClientContactName(requirement.getClientContactName());
			// requirementIndex.setClientContactEmail(requirement.getClientContactName());
			requirementIndex.setPriority(requirement.getPriority());
			requirementIndex.setCurrentRateCurrency(requirement.getCurrencyCode());
			requirementIndex.setNumberOfPositions(requirement.getPositions());
			requirementIndex.setNumberOfApplicants(requirement.getNoOfApplicants());

		}
		return requirementIndex;
	}

}
