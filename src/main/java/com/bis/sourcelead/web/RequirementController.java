package com.bis.sourcelead.web;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class RequirementController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	RequirementAssignToService requirementAssignToService;

	@Autowired
	CandidateService candidateService;
	
	@Autowired
	RequirementApproverHierarchyService requirementApproverHierarchyService;
	
	@Autowired
	SubmissionTemplateService submissionTemplateService;
	
	
	
	@RequestMapping(value = "/requirement")
	public ModelAndView newRequirement(HttpServletRequest request,@RequestParam(value="requirementId", required=false)String requirementId) throws Exception {
		Requirement requirement ;
		RequirementAssignTo requirementAssignTo;
		ModelAndView model = new ModelAndView();
		
		Org loginOrg= (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		if(requirementId != null && !requirementId.isEmpty()){
			requirement = this.requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
			if(requirement.getEducation() != null){
					String[] educationList = requirement.getEducation().split(",");
					requirement.setReqEducationList(educationList);
			}
			if(requirement.getSkills() != null){		
					String[] skillsList = requirement.getSkills().split(",");
					requirement.setReqSkillsList(skillsList);
			}
			String requirementAssignedIds = "";
			if(requirement.getRequirementAssignTo() != null){
				List<RequirementAssignTo> requirementAssignToList = requirementAssignToService.getRequirementAssignToListByRequirementId(Long.parseLong(requirementId));
				for(RequirementAssignTo rat : requirementAssignToList){
					if(requirementAssignedIds != null && !requirementAssignedIds.isEmpty()  && requirementAssignedIds != "" ){
						requirementAssignedIds = requirementAssignedIds +"&"+rat.getAssignUserId().getId().toString();
					}else{
						requirementAssignedIds =   rat.getAssignUserId().getId().toString();
					}
					
				}
			}
			model.addObject("requirementAssignedIds",requirementAssignedIds);
			model.addObject("requirement",requirement);
			model.addObject("requirementId",requirementId);
			if(requirement.getClientId() != null){
				List<OrgSpoc> clientContactList  = orgSpocService.getAllSpocsByOrgRelationId(requirement.getClientId().getId(), loginUser.getId(),orgLocations.getId());
				if(clientContactList != null){
					for(OrgSpoc orgSpoc : clientContactList){
						orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName());
					}
				}
			    model.addObject("clientContactList", clientContactList);
			}
		    
		    
		    //for requirement approvaers
			List<RequirementApproverHierarchy> requirementApproverHierarchyList =	requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(Long.parseLong(requirementId));
			String approverMode = "noApprovers";
			Long sequentialApprover1 = null;
			Long sequentialApprover2 = null;
			String parallelApproverIds = null ;
			Integer maxLevel =  requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(Long.parseLong(requirementId));
			if(maxLevel != null && maxLevel >1){
				approverMode = "Sequential";
				if(requirementApproverHierarchyList != null && !requirementApproverHierarchyList.isEmpty()){
					for(RequirementApproverHierarchy requirementApproverHierarchy :requirementApproverHierarchyList){
						if(requirementApproverHierarchy.getLevel() == 1){
							sequentialApprover1 = requirementApproverHierarchy.getApproverUserId().getId();
						}
						if(requirementApproverHierarchy.getLevel() == 2){
							sequentialApprover2 = requirementApproverHierarchy.getApproverUserId().getId();
						}
					}
				}
			}else{
				approverMode = "Parallel";
				int i = 0;
				if(requirementApproverHierarchyList != null && !requirementApproverHierarchyList.isEmpty()){
					for(RequirementApproverHierarchy requirementApproverHierarchy :requirementApproverHierarchyList){
						if(parallelApproverIds != null && parallelApproverIds != ""){
							parallelApproverIds =  parallelApproverIds +","+ requirementApproverHierarchy.getApproverUserId().getId().toString();
						}else{
							parallelApproverIds =  requirementApproverHierarchy.getApproverUserId().getId().toString();
						}
						i++;
					}
				}
			}
			model.addObject("approverMode",approverMode);
			model.addObject("parallelApproverIds",parallelApproverIds);
			model.addObject("sequentialApprover1",sequentialApprover1);
			model.addObject("sequentialApprover2",sequentialApprover2);
			
		}else{
			requirement = new Requirement();
		}
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirement");
		
		//OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		
        model.addObject("loginCountryName", countries.getName());
        model.addObject("currencyCode", countries.getCurrency());
        
        List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		model.addObject("educationsList", educationsList);
		
		
		List<User> usersList = userService.getOwnersByOrgLocationId(orgLocations.getId());
		model.addObject("usersList", usersList);
		
		List<User> allusersList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		model.addObject("all_users_List", allusersList);
		
		List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		model.addObject("skillsList", skillsList);
		
		List<Requirement> jobsTemplateList = requirementService.getJobTitleByJobTemplateStatusAndOrgLocationId(orgLocations.getId());
		model.addObject("jobsTemplateList", jobsTemplateList);	
		
		Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
		for(OrgRelations ocl :orgClientList){
			ocl.setOrgName(ocl.getResponderLocationId().getOrg().getOrgName());
			ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
		 }
	    model.addObject("add_clientList", orgClientList);
	    model.addObject("loginOrgLocId", orgLocations.getId());
	    
	    //for get the owners list for spocs
		List<User> userList = userService.getOwnersByOrgLocationId(orgLocations.getId());
		model.addObject("spocOwnersList", userList);
		
		//approver list
		List<User> approverList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		model.addObject("approverList", approverList);
		
		//approver list
		List<SubmissionTemplate>   submissionTempletesList = submissionTemplateService.getOrgSubmissionTemplatesByOrgLocationId(orgLocations.getId());
				model.addObject("submissionTempletesList", submissionTempletesList);
		

		return model;

	}
	
	@RequestMapping(value = "/requirementSummary")
	public ModelAndView requirementSummary(HttpServletRequest request,@RequestParam(value="requirementId", required=false)Long requirementId) throws Exception {
		 
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<Candidate> candidatesList = candidateService.getCandidatesByOrgLocationID(orgLocations.getId(),requirementId);
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		
		ModelAndView model = new ModelAndView();
		model.setViewName("requirement/requirementSummary");
		model.addObject("candidatesList",candidatesList);
		model.addObject("requirementId",requirementId);
		
		//to get time zone list
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		if(countries != null){
			String[] timezonesList = com.ibm.icu.util.TimeZone.getAvailableIDs(countries.getCode2());
			model.addObject("timezonesList", timezonesList);
		}
		
		if(requirement.getIsClientJob() != null && requirement.getIsClientJob().equalsIgnoreCase("Y")){
			model.addObject("clientSubmissions","Y");
		}else{
			model.addObject("clientSubmissions","N");
		}
		
		Requirement parentRequirement = requirementService.getRequirementByParentRequirementId(requirementId);
        if(parentRequirement != null){
            model.addObject("parentRequirement","Y");
        }else{
            model.addObject("parentRequirement","N");
        }
		
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}

}
