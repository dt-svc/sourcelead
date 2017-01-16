package com.bis.sourcelead.elk.index.helper;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.entity.Requirement;

@Component
public class RequirementHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementHelper.class);
	
	public RequirementIndex populateRequirementIndex(Requirement requirement) {
		RequirementIndex requirementIndex = new RequirementIndex();
		if(null != requirement){
			requirementIndex.setRequirementId(requirement.getId());
			requirementIndex.setOrgLocationId(requirement.getOrgLocationId().getId());
			requirementIndex.setJobTitle(requirement.getJobTitle());
			requirementIndex.setRate(requirement.getRate());
			requirementIndex.setRateType(requirement.getRateType());
			if(null != requirement.getMinExp() && !"".equalsIgnoreCase(requirement.getMinExp())){
				requirementIndex.setMaxExperience(Integer.parseInt(requirement.getMinExp()));
			}
			if(null != requirement.getMaxExp() && !"".equalsIgnoreCase(requirement.getMaxExp())){
				requirementIndex.setMaxExperience(Integer.parseInt(requirement.getMaxExp()));
			}
			requirementIndex.setRequirementStatus(requirement.getRequirementSubmissionStatus().toString());
			requirementIndex.setCountry(requirement.getCountry());
			requirementIndex.setState(requirement.getState());
			requirementIndex.setCity(requirement.getCity());
//			education
			if(null != requirement.getEducation() && !"".equalsIgnoreCase(requirement.getEducation())){
             String [] education = requirement.getEducation().split(",");
             requirementIndex.setEducation( Arrays.asList(education));
			}
			requirementIndex.setClient(requirement.getClientRate());
//			skill pending
			if(null != requirement.getSkills() && !"".equalsIgnoreCase(requirement.getSkills())){
	             String [] skills = requirement.getSkills().split(",");
	             requirementIndex.setSkills( Arrays.asList(skills));
				}
			requirementIndex.setJobType(requirement.getJobType());
			requirementIndex.setJobId(requirement.getJobId());
			requirementIndex.setClientContactName(requirement.getClientContactName());
			requirementIndex.setClientContactEmail(requirement.getClientContactEmail());
			requirementIndex.setManager(requirement.getManager());
			requirementIndex.setPriority(requirement.getPriority());
			
		}
		return requirementIndex;
	}

}
