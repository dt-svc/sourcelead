package com.bis.sourcelead.web;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class RequirementController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);
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
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RequirementOffersService requirementOffersService;
	
	@Autowired
	RequirementOfferHistoryService requirementOfferHistoryService;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT', 'ROLE_VIEW_REQUIREMENT', 'ROLE_VIEW_EDIT_REQUIREMENT')")
	@RequestMapping(value = "/requirement")
	public ModelAndView newRequirement(HttpServletRequest request,@RequestParam(value="requirementId", required=false)String requirementId) throws Exception {
		Requirement requirement ;
		ModelAndView model = new ModelAndView();
		
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		if(requirementId != null && !requirementId.isEmpty()){
			requirement = this.requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
			if(requirement.getEducation() != null){
					String[] educationList = requirement.getEducation().split(",");
					requirement.setReqEducationList(educationList);
					requirement.setReqEducationListString(requirement.getEducation());
			}
			
			if(requirement.getSkills() != null){		
					String[] skillsList = requirement.getSkills().split(",");
					requirement.setReqSkillsList(skillsList);
					requirement.setReqSkillsListString(requirement.getSkills());
			}
			String requirementAssignedIds = "";
			if(requirement.getRequirementAssignTo() != null){
				List<RequirementAssignTo> requirementAssignToList = requirementAssignToService.getRequirementAssignToListByRequirementId(Long.parseLong(requirementId));
				for(RequirementAssignTo rat : requirementAssignToList){
					if(requirementAssignedIds != null && !requirementAssignedIds.isEmpty()  && requirementAssignedIds != "" ){
						StringBuilder assignUserIdsSB = new StringBuilder();
						requirementAssignedIds = assignUserIdsSB.append(requirementAssignedIds).append("&").append(rat.getAssignUserId().getId().toString()).toString();
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
			}else if(maxLevel != null && maxLevel ==1){
				approverMode = "Parallel";
				int i = 0;
				if(requirementApproverHierarchyList != null && !requirementApproverHierarchyList.isEmpty()){
					for(RequirementApproverHierarchy requirementApproverHierarchy :requirementApproverHierarchyList){
						if(parallelApproverIds != null && parallelApproverIds != ""){
							StringBuilder parallelApproverIdsSB = new StringBuilder();
							parallelApproverIds =  parallelApproverIdsSB.append(parallelApproverIds).append(",").append(requirementApproverHierarchy.getApproverUserId().getId().toString()).toString();
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
		
		
		if(orgLocations != null && StringUtils.isNotEmpty(orgLocations.getCountry())){
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			model.addObject("loginCountryName", countries.getName());
	        model.addObject("currencyCode", countries.getCurrency());
		}
        
        
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
		List<User> approverList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_REQUIREMENT));
		model.addObject("approverList", approverList);
		
		//approver list
		List<SubmissionTemplate>   submissionTempletesList = submissionTemplateService.getOrgSubmissionTemplatesByOrgLocationIdAndTemplateType(orgLocations.getId(),"Submission");
		model.addObject("submissionTempletesList", submissionTempletesList);
		
		
		//client submission template 
		if(requirement.getClientId() != null){
			List<SubmissionTemplate>   clientSubmissionTempletesList = submissionTemplateService.getSubmissionTemplateByClientLocationIdAndOrgLocationIdAndTemplateType(requirement.getClientId().getResponderLocationId().getId(), orgLocations.getId(),"Submission");
			model.addObject("clientSubmissionTempletesList", clientSubmissionTempletesList);
		}
		

		return model;

	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_REQUIREMENT')")
	@RequestMapping(value = "/requirementSummary")
	public ModelAndView requirementSummary(HttpServletRequest request,@RequestParam(value="requirementId", required=false)Long requirementId) throws Exception {
		 
		
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		
		ModelAndView model = new ModelAndView();
		model.setViewName("requirement/requirementSummary");
		model.addObject("requirementId",requirementId);
		model.addObject("sourceleadUrl", sourceleadUrl);

		// This code is commented as not in use 

		//to get time zone list
		/*Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		if(countries != null){
			String[] timezonesList = com.ibm.icu.util.TimeZone.getAvailableIDs(countries.getCode2());
			model.addObject("timezonesList", timezonesList);
		}*/
		
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
        
        
        if(requirement.getSpocId() != null && requirement.getSpocId().getId() != null){
			OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(requirement.getSpocId().getId());
			if(orgSpoc != null && orgSpoc.getSpocStatus() != null && "In-Active".equalsIgnoreCase(orgSpoc.getSpocStatus())){
				model.addObject("isSpocActive", Constants.NO);
			}else{
				model.addObject("isSpocActive", Constants.YES);
			}
		}
		
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "reqsummaryMsg");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "reqsummaryMsg");
		}
		return model;
	}
	
	@RequestMapping(value = "/reqPipelineSubmitToClientMail")
	public ModelAndView reqPipelineSubmitToClientMail(HttpServletRequest request,@RequestParam(value="requirementId", required=false)Long requirementId) throws Exception {
		ModelAndView model = new ModelAndView();
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String fullName = null;
   	      if(!"".equalsIgnoreCase(user.getLastName()) && user.getLastName() !=null){
   	    	     StringBuilder userNameSB = new StringBuilder();
   		         fullName = userNameSB.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
		   	 }else{
		   		 fullName = user.getFirstName();
		   	 }
         StringBuilder sb = new StringBuilder();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.addObject("loginUserEmail",user.getEmail());
		 model.setViewName("requirement/mail-compose");
		Requirement req =requirementService.getRequirementByRequirementId(requirementId);
	    if(req != null && req.getParentRequirement() !=null){
			 model.addObject("toEmailId",req.getParentRequirement().getManagerUserId().getEmail());
			 StringBuilder toFullNameSB = new StringBuilder();
			 model.addObject("toFullName",toFullNameSB.append(req.getParentRequirement().getManagerUserId().getFirstName()).append(" ").append(req.getParentRequirement().getManagerUserId().getLastName()).toString());
			 sb.append("Hi,").append(req.getParentRequirement().getManagerUserId().getFirstName()).append("").append(req.getParentRequirement().getManagerUserId().getLastName()).toString();
			 sb.append("\n");
			 sb.append(fullName).append(" submitted the applicants for the").append(req.getJobTitle()).append(".\n");
			 sb.append("Best Regards,\n");
			 sb.append(fullName);
			 model.addObject("body",sb.toString());
			 
	    }
		return model.addObject("requirement", req);
	
	}
	

//	/candiCount
	@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT_TEMPLATE','ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE','ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE')")
	@RequestMapping(value = "/template")
	public ModelAndView AllTemplates(HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/template");
		
		return model;

	}
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT_TEMPLATE','ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE')")
	@RequestMapping(value = "/addTemplate")
	public ModelAndView AddTemplates(HttpServletRequest request,@RequestParam(value="requirementId", required=false) Long requirementId) throws Exception {
		
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		Requirement requirement =null;
			ModelAndView model = new ModelAndView();
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("requirement/addTemplate");
			if(requirementId != null){
				requirement = requirementService.getRequirementTemplateByRequirementId(requirementId, orgLocations.getId());
			 model.addObject("requirement", requirement);
			 
			 if(requirement.getEducation() != null){
					String[] educationList = requirement.getEducation().split(",");
					requirement.setReqEducationList(educationList);
					requirement.setReqEducationListString(requirement.getEducation());
			}
			
			if(requirement.getSkills() != null){		
					String[] skillsList = requirement.getSkills().split(",");
					requirement.setReqSkillsList(skillsList);
					requirement.setReqSkillsListString(requirement.getSkills());
			}
			 
			}
		
		    List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationsList", educationsList);
			
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillsList", skillsList);
			
		
		   return model;

	}
	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE','ROLE_VIEW_DELETE_REQUIREMENT_TEMPLATE')")
	@RequestMapping(value = "/requirementTemplateSummary")
	public ModelAndView requirementTemplateSummary(HttpServletRequest request,@RequestParam(value="requirementId", required=false)Long requirementId) throws Exception {
		 
		ModelAndView model = new ModelAndView();
		model.setViewName("requirement/requirementTemplateSummary");
		model.addObject("requirementId",requirementId);
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	@RequestMapping(value = "/acceptOrRejectRequirementOffer")
	public ModelAndView acceptOrRejectRequirementOffer(HttpServletRequest request,@RequestParam(value="offerStatus", required=false)String offerStatus,@RequestParam(value="offerId", required=false)Long offerId) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		RequirementOffers requirementOffers  = requirementOffersService.getRequirementOfferId(offerId);
		List<RequirementOfferHistory> requirementOfferHistories = requirementOfferHistoryService.getAllRequirementOfferHistoriesByOfferId(offerId);
		
		ModelAndView model = new ModelAndView();
		model.setViewName("requirement/offerAcceptOrReject");
		model.addObject("offerStatus",offerStatus);
		model.addObject("offerId",offerId);
		model.addObject("requirementOffers",requirementOffers);
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		if(offerStatus.equalsIgnoreCase("Accepted")){
			
		    if(requirementOfferHistories != null && !requirementOfferHistories.isEmpty() ){  
			    if(offerStatus.equalsIgnoreCase("Accepted")){
			    	for (RequirementOfferHistory requirementOfferHistory : requirementOfferHistories) {
			    		   RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementOfferHistory.getSubmissionId().getId());
				    		if(requirementOfferHistory.getOfferSourceType() != null && "OFFERMADE".equals(requirementOfferHistory.getOfferSourceType())){
				    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
				    		}else{
				    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERACCEPTED);
				    		}
							 requirementSubmission.setUpdatedDate(commonUtil.currentDate());
							 requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
							 
							//Strote RequirementSubmissionHistory
						    RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
							requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().getValue());
							requirementSubmissionHistory.setFromStage("OFFERMADE");
							requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED);
							requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory);
					  }
			    	
					
			    	  //Changing the status of offerTable 
				      if(requirementOffers  != null){
						    requirementOffers.setOfferStatus("OFFERACCEPTED");
						    requirementOffers.setUpdatedDate(commonUtil.currentDate());
						    requirementOffersService.saveOfferStatus(requirementOffers);
				     }
			    }
		    }
		}
		return model;
	}
	

	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		return false;
	}

}
