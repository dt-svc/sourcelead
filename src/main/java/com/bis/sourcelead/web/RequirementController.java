package com.bis.sourcelead.web;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ContractsDTO;
import com.bis.sourcelead.dto.JobFairDTO;
import com.bis.sourcelead.dto.JobFairRequirementsDTO;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailReminder;
import com.bis.sourcelead.entity.JobFair;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementIncentive;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmailReminderService;
import com.bis.sourcelead.service.JobCustomFieldDataService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;
import com.bis.sourcelead.workflow.service.RequirementWorkFlowService;
//import org.apache.commons.collections.ListUtils;
import com.ibm.icu.util.Calendar;

@Controller
public class RequirementController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${atsUrl}")
	private String atsUrl;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
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
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	
	@Autowired
	private RequirementInterviewerService requirementInterviewerService;
	
	@Autowired

	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	private ProjectClientService projectClientService;
	

	TeamService teamService;
	
	@Autowired
	RolesService roleService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	@Autowired
	private JobCustomFieldDataService jobCustomFieldDataService;
	
	@Autowired
	private RequirementWorkFlowService requirementWorkFlowService;
	
	@Autowired
    EmailReminderService emailReminderService;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT', 'ROLE_VIEW_REQUIREMENT', 'ROLE_VIEW_EDIT_REQUIREMENT')")
	@RequestMapping(value = "/requirement")
	public ModelAndView newRequirement(HttpServletRequest request,@RequestParam(value="requirementUUID", required=false)String requirementUUID) throws Exception {
		Requirement requirement ;
		ModelAndView model = new ModelAndView();
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Requirement);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocationById(orgLocId);
				
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User loginUser = userService.getUserById(userId);
		
		model.addObject("loginUser",loginUser);
		model.addObject("orgLocationDateFormat", orgLocations.getDateFormat());
		 SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_REQUIREMENT);
	     model.addObject("actionId",action.getId());
		
	    List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocations.getId());
	    model.addObject("sourceleadSectionList",sections); 
	    
		if(null != requirementUUID && !requirementUUID.isEmpty()){
			requirement = this.requirementService.getRequirementByRequirementUUID(requirementUUID);
			model.addObject("requirementUUID", requirementUUID);
			model.addObject("requirementStatus", requirement.getRequirementSubmissionStatus());
			List<RequirementIncentive> requirementIncentiveList = requirementService.getRequirementIncentiveListByRequirementId(requirement.getId());
			model.addObject("requirementIncentiveList", requirementIncentiveList);
			model.addObject("requirementIncentiveListSize", requirementIncentiveList.size());
			
			if (requirement != null) {
				requirement.setJobFairList(requirementService.getJobFairsListByRequirementId(requirement.getId()));
				if (requirement.getJobFairList() != null) {
					String selectedJobFairsString = "";
					for (JobFair jobFair : requirement.getJobFairList()) {
						selectedJobFairsString += (selectedJobFairsString.isEmpty() ? "" : ",") + jobFair.getId() + ":" + jobFair.getName();
					}
					model.addObject("selectedJobFairsString", selectedJobFairsString);
				}
			}

			if (requirement != null && requirement.getOrgLocationId().getId().equals(orgLocations.getId())) {

				if (null != requirement.getEducation()) {
					String[] educationList = requirement.getEducation().split(",");
					requirement.setReqEducationList(educationList);
					requirement.setReqEducationListString(requirement.getEducation());
				}

				if (null != requirement.getSkills()) {
					String[] skillsList = requirement.getSkills().split(",");
					requirement.setReqSkillsList(skillsList);
					requirement.setReqSkillsListString(requirement.getSkills());
				}

			if(null != requirement.getIndustryName()){
				String selectedIndustryName = requirement.getIndustryName();
				requirement.setIndustrySavedName(selectedIndustryName);
				
			}
			
	      //  SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();

	 
			if(null !=requirement.getClosedDate()){
				
				requirement.setClosedDateStringFormat(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirement.getClosedDate()));
			}
			
			if(requirement != null && requirement.getId() != null){
				String storedNoticeperiodList = requirementService.getNoticePeriodByRequirementId(requirement.getId());
						model.addObject("noticePeriod", storedNoticeperiodList);
			}
			
			
			String requirementAssignedIds = "";
			if(null != requirement.getRequirementAssignTo()){
				List<RequirementAssignTo> requirementAssignToList = requirementAssignToService.getRequirementAssignToListByRequirementId(requirement.getId());
				for(RequirementAssignTo rat : requirementAssignToList){
					if(null != requirementAssignedIds && !requirementAssignedIds.isEmpty()  && "" != requirementAssignedIds ){
						StringBuilder assignUserIdsSB = new StringBuilder();
						requirementAssignedIds = assignUserIdsSB.append(requirementAssignedIds).append("&").append(rat.getAssignUserId().getId().toString()).toString();
					}else{
						requirementAssignedIds =   rat.getAssignUserId().getId().toString();
					}
					
				}
			}
			
			if(requirement.getSpocId() != null) {
				if (requirement.getSpocId().getClientLocationId().getId() == orgLocations.getId()) {
					requirement.setSpocName(requirement.getSpocId().getVendorUser().getFullName());
					requirement.setSpocOwnerName(requirement.getSpocId().getClientUser().getFullName());
				}
				else {
					requirement.setSpocName(requirement.getSpocId().getClientUser().getFullName());
					requirement.setSpocOwnerName(requirement.getSpocId().getVendorUser().getFullName());
				}
			}
			
			model.addObject("requirementAssignedIds",requirementAssignedIds);
			if(requirement.getJobType() != null && StringUtils.isNotBlank(requirement.getJobType())){
			requirement.setJobType(requirement.getJobType().replaceAll("\n", " ").replaceAll("\r", "").replaceAll("\t", ""));
			}
			model.addObject("requirement",requirement);
			model.addObject("requirementId",requirement.getId());
			if(null != requirement.getClientId()){
				long managerUserId = 0;
				if (requirement.getPrimaryRecruiterUserId() != null && requirement.getPrimaryRecruiterUserId().getId() == loginUser.getId())
					managerUserId = requirement.getManagerUserId().getId();
				else
					managerUserId = loginUser.getId();
				List<OrgSpoc> clientContactList  = orgSpocService.getAllSpocsByOrgRelationId(requirement.getClientId().getId(), managerUserId, orgLocations.getId(),"Client");
				/*commented by Prasad salina, here no need to iterate list, because in service impl we already itarated the list.
				 * if(null != clientContactList){
					for(OrgSpoc orgSpoc : clientContactList){
						orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName());
					}
				}*/
				if(clientContactList.size() == 0 && null != requirement.getSpocId()){
				OrgSpoc clientContactOrgSpoc = orgSpocService.getOrgSpocById(requirement.getSpocId().getId());
				StringBuilder clientContactWithOwner = new StringBuilder();
				if(null != clientContactOrgSpoc.getClientUser().getLastName() && !"".contentEquals(clientContactOrgSpoc.getClientUser().getLastName())){
				    clientContactWithOwner.append(clientContactOrgSpoc.getClientUser().getFirstName()).append(clientContactOrgSpoc.getClientUser().getLastName()).append("(").append(clientContactOrgSpoc.getVendorUser().getFirstName()).append(")");
				}else{
					clientContactWithOwner.append(clientContactOrgSpoc.getClientUser().getFirstName()).append("(").append(clientContactOrgSpoc.getVendorUser().getFirstName()).append(")");
				}
				model.addObject("clientContactWithOwner", clientContactWithOwner);
			   }
				model.addObject("clientContactList", clientContactList);
			}
	     	
			List<ProjectClients> projectList = projectClientService.getAllProjectsByOrgLocationIdAndStatusInProgressAndNotStarted(orgLocations.getId());
			for(ProjectClients projectClients : projectList){
				if(projectClients.getOrgRelationId() != null){
				if(projectClients.getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					projectClients.setClientName(projectClients.getOrgRelationId().getResponderLocationId().getOrgLocationName());
				}else if(projectClients.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					projectClients.setClientName(projectClients.getOrgRelationId().getLocationId().getOrgLocationName());
				}
			}
			}
			model.addObject("projectList", projectList);
		    //for requirement approvaers
			List<RequirementApproverHierarchy> requirementApproverHierarchyList =	requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirement.getId());
			String approverMode = "noApprovers";
			Long sequentialApprover1 = null;
			Long sequentialApprover2 = null;
			String parallelApproverIds = null ;
			Integer maxLevel =  requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirement.getId());
			if(null != maxLevel && maxLevel >1){
				approverMode = "Sequential";
				if(null != requirementApproverHierarchyList && !requirementApproverHierarchyList.isEmpty()){
					for(RequirementApproverHierarchy requirementApproverHierarchy :requirementApproverHierarchyList){
						if(requirementApproverHierarchy.getLevel() == 1){
							sequentialApprover1 = requirementApproverHierarchy.getApproverUserId().getId();
						}
						if(requirementApproverHierarchy.getLevel() == 2){
							sequentialApprover2 = requirementApproverHierarchy.getApproverUserId().getId();
						}
					}
				}
			}else if(null != maxLevel && maxLevel ==1){
				approverMode = "Parallel";
				int i = 0;
				if(null != requirementApproverHierarchyList && !requirementApproverHierarchyList.isEmpty()){
					for(RequirementApproverHierarchy requirementApproverHierarchy :requirementApproverHierarchyList){
						if(null != parallelApproverIds && "" != parallelApproverIds){
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
				model.setViewName("registration/persmissionDenied");
			}
			
		}else{
			requirement = new Requirement();
		}
		
		SourceLeadSessionManager.setSessionAttribute(request, "profilePercentage", orgLocations.getProfilePercentage());
		SourceLeadSessionManager.setSessionAttribute(request, "coolingOffPeriod", orgLocations.getCoolingOffPeriod());
		List<LookUpData> experienceLevelList = lookUpDataService.getLookUpDataByCategory(Constants.EXPERIENCELEVEL);
		model.addObject("experienceLevelList", experienceLevelList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("requirement/requirement");
		
		model.addObject("loginOrgLocCoolingOffPeriod", orgLocations.getCoolingOffPeriod());
		
		//OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
		/*if(null != orgLocations && StringUtils.isNotEmpty(orgLocations.getCountry())){
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
			model.addObject("loginCountryName", countries.getName());
			}
	        
		}*/
        
		// skills and education list<Pojo(lookupdata)> to list<string> because customized skills were not appending in edit mode.
		
		if(requirement != null && requirement.getId() != null){

			List<String> allReqSkills = requirementService.getAllRequirementSkillsByRequirementI(requirement.getId());
				List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
				String skill = allReqSkills.get(0);
				if(!"null".equalsIgnoreCase(skill) && null != skill){
				String arr[] = skill.split(",");
				for(String editSkillName : arr){
					if(!skillsList.contains(editSkillName)){
						skillsList.add(editSkillName);
					}
				}
			      model.addObject("skillsList", skillsList);
				}else{
				  model.addObject("skillsList", skillsList);
				} 
		}else{
			//List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
			model.addObject("skillsList", skillsList);
		}
		List<ProjectClients> projectList = projectClientService.getAllProjectsByOrgLocationIdAndStatusInProgressAndNotStarted(orgLocations.getId());
		for(ProjectClients projectClients : projectList){
			if(projectClients.getOrgRelationId() != null && projectClients.getOrgRelationId().getResponderLocationId() != null && projectClients.getOrgRelationId().getLocationId() != null  ){
				if(projectClients.getOrgRelationId().getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					projectClients.setClientName(projectClients.getOrgRelationId().getResponderLocationId().getOrgLocationName());
				}else if( projectClients.getOrgRelationId().getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					projectClients.setClientName(projectClients.getOrgRelationId().getLocationId().getOrgLocationName());
				}
			}
		}

		model.addObject("projectList", projectList);
		
		if(requirement != null && requirement.getId() != null){

			List<String> allReqVisaList = requirementService.getAllRequirementVisaByRequirementI(requirement.getId());
				List<String> visaList = lookUpDataService.getAllVisaByCategory();
				String visa = allReqVisaList.get(0);
				if(!"null".equalsIgnoreCase(visa) && null != visa){
				String arr[] = visa.split(",");
				for(String visaName : arr){
					if(!visaList.contains(visaName)){
						visaList.add(visaName);
					}
				}
			      model.addObject("visaList", visaList);
				}else{
				  model.addObject("visaList", visaList);
				} 
		}else{
			List<String> visaList = lookUpDataService.getAllVisaByCategory();
			model.addObject("visaList", visaList);
		}
		
		
		
		
		if(requirement != null && requirement.getId() != null){
		List<String> newlyAddededucationList = requirementService.getAllRequirementEducationByRequirementId(requirement.getId());
	       List<String> educationsList = lookUpDataService.getLookUpDataByCategoryEducation();
	       
	       String education = newlyAddededucationList.get(0);
			if(!"null".equalsIgnoreCase(education) && null != education){
			String arr[] = education.split(",");
			for(String editEducationName : arr){
				if(!educationsList.contains(editEducationName)){
					educationsList.add(editEducationName);
				}
			}
		      model.addObject("educationsList", educationsList);
			}else{
			  model.addObject("educationsList", educationsList);
			} 
		}else{
         // List<LookUpData> educationsListData = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
          List<String> educationsList = lookUpDataService.getLookUpDataByCategoryEducation();
		  model.addObject("educationsList", educationsList);
		}
		
		
		if(requirement != null && requirement.getId() != null){
			List<String> newlyAddedindustryList = requirementService.getAllRequirementIndustryByRequirementId(requirement.getId());
		       List<String> industryList = lookUpDataService.getAllSkillsByIndustryCategory();
		       String industry = newlyAddedindustryList.get(0);
				if(!"null".equalsIgnoreCase(industry) && null != industry){
				String arr = industry;
				if(null != arr && !"".equalsIgnoreCase(arr)){
			      model.addObject("industryList", industryList);
				}else{
				  model.addObject("industryList", industryList);
				} 
			}else
			{
				  model.addObject("industryList", industryList);
			}
				
			}else{
	         // List<LookUpData> educationsListData = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
	          List<String> industryListWithoutId = lookUpDataService.getAllSkillsByIndustryCategory();
			  model.addObject("industryList", industryListWithoutId);
			}
		
		/*List<LookUpData> industryList = lookUpDataService.getLookUpDataByCategory(Constants.INDUSTRIES);
		model.addObject("industryList", industryList);*/
		
		List<User> usersList = userService.getOwnersByOrgLocationId(orgLocations.getId());
		model.addObject("usersList", usersList);
		
		List<User> allusersList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		model.addObject("all_users_List", allusersList);
		
		List<Requirement> jobsTemplateList = requirementService.getJobTitleByJobTemplateStatusAndOrgLocationId(orgLocations.getId());
		model.addObject("jobsTemplateList", jobsTemplateList);	
		
		Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
		for(OrgRelations ocl :orgClientList){
			ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
			ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
		 }
	    model.addObject("add_clientList", orgClientList);
	    model.addObject("loginOrgLocId", orgLocations.getId());
	    
	    if(requirement.getClientId() != null && requirement.getClientId().getId() != null){
	    	OrgRelations orgClient =orgRelationsService.getOrRelationsById(requirement.getClientId().getId()); 
			if(null != orgClient){
				orgClient.setOrgName(orgClient.getResponderLocationId().getOrgLocationName());
				orgClient.setOrgid(orgClient.getResponderLocationId().getOrg().getId());
			 }
		    model.addObject("orgClient", orgClient);
	    }
	    
	    //for get the owners list for spocs
		List<User> userList = userService.getOwnersByOrgLocationId(orgLocations.getId());
		model.addObject("spocOwnersList", userList);
		
		//approver list
		List<User> approverList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_REQUIREMENT));
		model.addObject("approverList", approverList);
		
		//approver list
		List<SubmissionTemplate>   submissionTempletesList = submissionTemplateService.getOrgSubmissionTemplatesByOrgLocationIdAndTemplateType(orgLocations.getId(),"Submission");
		model.addObject("submissionTempletesList", submissionTempletesList);
		
		// workflow List commented by SRINIVAS SABBU.
		List<RequirementWorkFlow> workflowList = new ArrayList<RequirementWorkFlow>();
		workflowList = requirementWorkFlowService.getRequirementWorkFlowByOrgLocationId(orgLocations);
		if (workflowList != null && workflowList.size() > 0) {
			model.addObject("workflowList", workflowList);
		}
			
		//client submission template 
		if(null != requirement.getClientId()){
			List<SubmissionTemplate>   clientSubmissionTempletesList = submissionTemplateService.getSubmissionTemplateByClientLocationIdAndOrgLocationIdAndTemplateType(requirement.getClientId().getResponderLocationId().getId(), orgLocations.getId(),"Submission");
			model.addObject("clientSubmissionTempletesList", clientSubmissionTempletesList);
/*		}
		
		if(null != requirement.getClientId()){*/
			if(null != requirement.getParentRequirement() && null != requirement.getParentRequirement().getSubmissionTemplateId()){
				SubmissionTemplate   clientSubmissionTemplete = submissionTemplateService.getSubmissionTemplateById(requirement.getParentRequirement().getSubmissionTemplateId().getId());
				model.addObject("clientSubmissionTemplete", clientSubmissionTemplete);
			}else{
				if(null != requirement.getClientSubmissionTemplateId()){
					SubmissionTemplate   clientSubmissionTemplete = submissionTemplateService.getSubmissionTemplateById(requirement.getClientSubmissionTemplateId().getId());
					model.addObject("clientSubmissionTemplete", clientSubmissionTemplete);
				}
			}
		}
		
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		if(null != countries){
        model.addObject("currencyCode", countries.getCurrency());
		}
		
		/*CombinedList combinedList = new CombinedList();
		List<RequirementAssignTo> requirementAssignTo = null;
		Boolean isTeamViewAccess = commonUtil.hasRole("ROLE_VIEW_TEAM");
		if (isTeamViewAccess) {
			List<Team> teamsList = teamService.getTeamListByOrgLocationID(orgLocations.getId());
			if (null != teamsList) {
				for (Team team : teamsList) {
					if (null != requirementAssignTo) {
						if (requirementAssignTo.stream().anyMatch(t -> t.getTeamId() != null
								&& t.getTeamId().getId().equals(team.getId()))) {
							team.setIsTeamAssigned("Y");
						}
					}
				}
				combinedList.setTeams(teamsList);
			}
		}
		List<Roles> rolesList = roleService.getTabAccessRolesListByOrgLocationIdAndTabId(orgLocations.getId(), Long.valueOf(Constants.TAB_REQUIREMENT));
		if (null != rolesList) {
			for (Roles role : rolesList) {
				if (null != requirementAssignTo) {
					if (requirementAssignTo.stream().anyMatch(t -> t.getTeamId() == null
							&& t.getRoleId() != null
							&& t.getRoleId().getId().equals(role.getId()))) {
						role.setIsRoleAssigned("Y");
					}
				}
			}
			combinedList.setRoles(rolesList);
		}
		model.addObject("combinedList", combinedList);
		
		
		List<User> usersListForAssignTeam = userService.getUsersByTeamTypeAndTabId(orgLocations.getId(),"", Long.valueOf(Constants.TAB_REQUIREMENT));
		for(User user : usersListForAssignTeam){
	            List<RequirementAssignTo> assign = requirementService.getUserAssignJobCount(user.getId(),orgLocations.getId());
	            user.setAssignJobCount(assign.size());
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if(null != userRole){
					user.setUserType(userRole.getUserType());
				}
		   }
		model.addObject("usersListForAssignTeam", usersListForAssignTeam);*/
		return model;

	}
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT', 'ROLE_VIEW_REQUIREMENT', 'ROLE_VIEW_EDIT_REQUIREMENT')")
	@RequestMapping(value = { "/addJobFair", "/editJobFair" })
	public ModelAndView jobFair(HttpServletRequest request, @RequestParam(defaultValue = "") String uuid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (loginUser != null) {
				modelAndView.addObject("sourceleadUrl", sourceleadUrl);
				JobFairDTO jobFairDTO = new JobFairDTO();
				jobFairDTO.setPostedFrom("week");
				String requirementString = "";
				if (!uuid.isEmpty()) {
					jobFairDTO = requirementService.getJobFairByUUID(uuid);
					modelAndView.addObject("jobFair", jobFairDTO);

					for (JobFairRequirementsDTO jobFairRequirementsDTO : jobFairDTO.getJobFairRequirementList()) {
						String req = jobFairRequirementsDTO.getRequirementId() + ":" + jobFairRequirementsDTO.getRequirementTitle();
						if (!requirementString.contains(req)) {
							requirementString += (requirementString.isEmpty() ? "" : ",") + req;
						}
					}
				}
				
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

//				String reqString = dashboardRestController
//						.getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(jobFairDTO.getPostedFrom(), request);
				List<Requirement> requirementList = requirementService.getRequirementListByPostedDateAndSelectedClients(orgLocations.getId(), loginUser.getId(), jobFairDTO.getPostedFrom(), "");
				String reqString = commonUtil.toJSON(requirementList);
				if (reqString != null) {
					JSONArray jsonArray = new JSONArray(reqString);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String req = jsonObject.getString("id") + ":" + jsonObject.getString("jobTitle");
						if (!requirementString.contains(req)) {
							requirementString += (requirementString.isEmpty() ? "" : ",") + (req);
						}
					}
				}
				/*System.out.println("Requirement String : " + requirementString);*/
				modelAndView.addObject("requirementString", requirementString);
				
				Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId()); 
				for(OrgRelations ocl :orgClientList){
					ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
					ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
				 }
				modelAndView.addObject("add_clientList", orgClientList);
				modelAndView.addObject("loginOrgLocId", orgLocations.getId());
			} else {
				modelAndView.setViewName("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelAndView.setViewName("requirement/addJobFair");
		return modelAndView;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT', 'ROLE_VIEW_REQUIREMENT', 'ROLE_VIEW_EDIT_REQUIREMENT')")
	@RequestMapping(value = "/jobFairSummary")
	public ModelAndView jobFairSummary(HttpServletRequest request, @RequestParam(defaultValue = "") String uuid) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (loginUser != null) {
				modelAndView.addObject("sourceleadUrl", sourceleadUrl);
				modelAndView.addObject("jobFair", requirementService.getJobFairByUUID(uuid));
			} else {
				modelAndView.setViewName("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelAndView.setViewName("requirement/jobFairSummary");
		return modelAndView;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_REQUIREMENT','ROLE_VIEW_EDIT_REQUIREMENT')")
	@RequestMapping(value = "/requirementSummary")
	public ModelAndView requirementSummary(HttpServletRequest request,@RequestParam(value="requirementUUID", required=false)String requirementUUID,
			@RequestParam(value="type", required=false) String type,@RequestParam(value="candidateUUID", required=false) String candidateUUID,
			@RequestParam(value="submissionId", required=false) String submissionId,@RequestParam(value="candidateKeyword", required=false)String candidateKeyword,@RequestParam(value="submissionStatus", required=false)String submissionStatus) throws Exception {

		logger.info("EXECUTION START TIME>>>>>" + Calendar.getInstance().getTime());
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		// isAdmin value is set to true as 'SourceLeadSessionManager.getSessionAttribute' method is returning null.
		Boolean isAdmin = (Boolean) SourceLeadSessionManager.getSessionAttribute(request,"isAdmin");
		
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Requirement);
		//Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		Requirement requirement = null;
		if(isAdmin){
			requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
		}else {
			requirement = requirementService.getRequirementByAccessibleUserIdAndRequirementUUID(loginUser.getId(), requirementUUID);
		}
		
		if(null != requirement) {
			if(requirement.getProjectClientID() !=null && requirement.getProjectClientID().getProjectId() !=null && requirement.getProjectClientID().getProjectId().getProjectName() != null){
			requirement.setProjectName(requirement.getProjectClientID().getProjectId().getProjectName());
			}
			if(requirement.getCreatedDate()!=null){
				requirement.setRequirementCreatedDateToString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirement.getCreatedDate()));
			}
			if (null != requirement.getClosedDate()) {
				requirement.setClosedDateStringFormat(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirement.getClosedDate()));
			}
			if (requirement.getSpocId() != null) {
				if (requirement.getClientId() != null) {
					if (requirement.getClientId().getInitiatorType().equalsIgnoreCase("Client")) {
						requirement.setClientName(requirement.getClientId().getLocationId().getOrgLocationName());
						requirement.setOrgRelationUUID(requirement.getClientId().getUUID());
					}
					if (requirement.getClientId().getInitiatorType().equalsIgnoreCase("Vendor")) {
						requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrgLocationName());
					}
				}
				if (requirement.getSpocId().getClientUser().getFirstName() != null
						&& requirement.getSpocId().getClientUser().getFirstName() != ""
						&& requirement.getSpocId().getClientUser().getLastName() != null
						&& requirement.getSpocId().getClientUser().getLastName() != "") {
					StringBuilder spocSB = new StringBuilder();
					requirement.setSpocName(spocSB.append(requirement.getSpocId().getClientUser().getFirstName()).append(" ").append(requirement.getSpocId().getClientUser().getLastName()).toString());
				}
				else {
					requirement.setSpocName(requirement.getSpocId().getClientUser().getFirstName());
				}
				requirement.setSpocEmail(requirement.getSpocId().getClientUser().getEmail());
				requirement.setSpocMobilePhone(requirement.getSpocId().getClientUser().getMobilePhone());
				requirement.setSpocUUID(requirement.getSpocId().getUUID());
			}
			 Set<RequirementApproverHierarchy> approverList = requirement.getRequirementApprover();
			 Integer maxLevel =  requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirement.getId());
				if(null != maxLevel && maxLevel >1){
					if(null != approverList && !approverList.isEmpty()){
						for(RequirementApproverHierarchy reApprover :approverList){
							if((loginUser.getId().equals(reApprover.getApproverUserId().getId())) && reApprover.getLevel() == 1){
								 requirement.setRequirementApproverHierarchyStatus(reApprover.getRequirementApproverStatus().toString());
							}
							else if((isAdmin || loginUser.getId().equals(reApprover.getApproverUserId().getId())) && reApprover.getLevel() == 2){
								 requirement.setRequirementApproverHierarchyStatus(reApprover.getRequirementApproverStatus().toString());
							}
						}
					}
				}else if(null != maxLevel && maxLevel ==1){
					int i = 0;
					if(null != approverList && !approverList.isEmpty()){
						for(RequirementApproverHierarchy reApprover :approverList){
							if(null != reApprover.getRequirementApproverStatus().toString() && "" != reApprover.getRequirementApproverStatus().toString()){
								 requirement.setRequirementApproverHierarchyStatus(reApprover.getRequirementApproverStatus().toString());
							}
							i++;
						}
					}
				}
			Boolean isManager = false;
			if(requirement.getManagerUserId()!= null && requirement.getManagerUserId().getId().equals(loginUser.getId())) {
				isManager = true;
				//requirementService.checkLoginUserIsRequirementManagerByRequirementIdAndUserId(requirement.getId(), loginUser.getId());
			}
			Boolean isLoginUserAsApprover = false;
			Boolean approvedByLoginUser = false;
			RequirementApproverHierarchy requirementApproverHierarchy = null;
			if(isAdmin || isManager){
				requirementApproverHierarchy = requirementApproverHierarchyService.getRejectedApproverHierarchyByRequirementId(requirement.getId());
			}else{
				requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirement.getId(), loginUser.getId());
				if (requirementApproverHierarchy != null && requirementApproverHierarchy.getRequirementApproverStatus().getValue().equalsIgnoreCase("APPROVED")) {
					approvedByLoginUser = true;
				}
			}
			model.addObject("approvedByLoginUser",approvedByLoginUser);
			if (requirementApproverHierarchy != null) {
				isLoginUserAsApprover = true;
				requirement.setRejectReasonForRequirement(requirementApproverHierarchy.getRejectedReason());
			}
			requirement.setIsLoginUserAsApprover(isLoginUserAsApprover.toString());
			
			if (requirement.getCreatedBy().getId().equals(loginUser.getId())
					|| (requirement.getPrimaryRecruiterUserId() != null && requirement.getPrimaryRecruiterUserId().getId().equals(loginUser.getId()))) {
				requirement.setIsLoginUserAsInitiator("true");
			} else {
				requirement.setIsLoginUserAsInitiator("false");
			}
	
			
			if(requirement.getClientId() != null){
			requirement.setOrgRelationUUID(requirement.getClientId().getUUID());
			}
			
	
			Integer requirementAssignToListCount = requirementAssignToService.getRequirementAssignToListCountByRequirementId(requirement.getId());
			model.addObject("requirementAssignToListCount",requirementAssignToListCount);
			
			Boolean isPrimaryRecruiter = false;
			if(requirement.getPrimaryRecruiterUserId() != null && requirement.getPrimaryRecruiterUserId().getId().longValue() == loginUser.getId().longValue()) {
				isPrimaryRecruiter = true;
				model.addObject("isPrimaryRecruiter",isPrimaryRecruiter);
			}
			
			if(null != requirement && requirement.getOrgLocationId().getId().equals(orgLocations.getId())){
				Long requirementId = requirement.getId();
				RequirementWorkFlowDTO workflowDTO = requirementWorkFlowService.getRequirementDefaultWorkflow(orgLocations);
				System.out.println(" defalt workflow id is :>>>"+workflowDTO.getId());
				System.out.println("Workflow configure Type is >>>>> "+workflowDTO.getConfigureType());
				if(requirement.getWorkflowId() != null  && workflowDTO.getId() != requirement.getWorkflowId().getId()) {
					System.out.println("OTHER THAN DEFAULT WORKFLOW IS FOUND >>>>>>>>>>>>>");
					List<RequirementWorkFlowStages> stagesList = requirementWorkFlowService.getRequirementWorkflowStagesByWorkflowIdAndOrglocations(requirement.getWorkflowId().getId(), orgLocations);
					model.addObject("stagesList", stagesList);
					model.addObject("workflowId", requirement.getWorkflowId().getId());
					model.addObject("workflowConfigured", true);
					model.setViewName("requirement/requirementWorkflowSummary");
				}else {
					System.out.println("DEFAULT WORKFLOW IS FOUND >>>>>>>>>>>>>");
					model.addObject("workflowConfigured", false);
					model.setViewName("requirement/requirementSummary");
				}
				
				model.addObject("requirementId",requirementId);
				
				model.addObject("requirement",requirement);
				
				List<Countries> allCountryList = countriesService.getAllCountries();
				model.addObject("allCountryList", allCountryList);
				
				List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
				model.addObject("locationsList",locationsList);
				
				model.addObject("timezone",orgLocations.getTimeZoneId());
				
				Boolean isTeamMember = requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementId, loginUser.getId());
				model.addObject("isTeamMember",isTeamMember);
				
				model.addObject("isManager",isManager);
				
				model.addObject("isAdmin",isAdmin);
		
				model.addObject("sourceleadUrl", sourceleadUrl);
				
				List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
				model.addObject("skillsList", skillsList);
				
				List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
				model.addObject("educationsList", educationsList);
				
				List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
				model.addObject("documentsList", documentsList);
		
				// This code is commented as not in use 
		
				//to get time zone list
				/*Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
				if(countries != null){
					String[] timezonesList = com.ibm.icu.util.TimeZone.getAvailableIDs(countries.getCode2());
					model.addObject("timezonesList", timezonesList);
				}*/
				
				if(null != requirement.getIsClientJob() && requirement.getIsClientJob().equalsIgnoreCase("Y")){
					model.addObject("clientSubmissions","Y");
				}else{
					model.addObject("clientSubmissions","N");
				}
				
				Requirement parentRequirement = requirementService.getRequirementByParentRequirementId(requirementId);
		        if(null != parentRequirement){
		            model.addObject("parentRequirement","Y");
		        }else{
		            model.addObject("parentRequirement","N");
		        }
		        
		        if(null != requirement.getSpocId() && null != requirement.getSpocId().getId()){
					OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(requirement.getSpocId().getId());
					if(null != orgSpoc && null != orgSpoc.getSpocStatus() && "In-Active".equalsIgnoreCase(orgSpoc.getSpocStatus())){
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
				model.addObject("requirementUUID",requirementUUID);
				if(!isNullOrEmpty(type)){
					model.addObject("type",type);
				}
				
				if(!isNullOrEmpty(candidateUUID)){
					Candidate canidate = candidateService.getCandidateByUUID(candidateUUID);
					model.addObject("candidateId", canidate.getId());
				}
				
				if(!isNullOrEmpty(submissionId)){
					model.addObject("submissionId", submissionId);
				}
				if(submissionId != null && StringUtils.isNotEmpty(submissionId)){
				 RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId));
				 String subStatus  = requirementSubmission.getSubmissionStatus().toString();
				 model.addObject("subStatus", subStatus);
				}
				if(!isNullOrEmpty(submissionStatus)){
					model.addObject("submissionStatus", submissionStatus);
				}
				
			    
			    List<OrgRelationDTO> orgVendorsList = orgRelationsService.getAllVendorsListOrgLocationId(orgLocations.getId());
			    model.addObject("add_VendorList", orgVendorsList);
				
			}
			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "reqsummaryMsg");
			if(!isNullOrEmpty(msg)){
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "reqsummaryMsg");
			}
			model.addObject("requirementUUID",requirementUUID);
			if(!isNullOrEmpty(type)){
				model.addObject("type",type);
			}
			
			if(!isNullOrEmpty(candidateUUID)){
				Candidate canidate = candidateService.getCandidateByUUID(candidateUUID);
				model.addObject("candidateId", canidate.getId());
			}
			
			if(!isNullOrEmpty(submissionId)){
				model.addObject("submissionId", submissionId);
			}
			if(!isNullOrEmpty(candidateKeyword)){
				model.addObject("candidateKeyword", candidateKeyword);
			}
			 SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_REQUIREMENT);
		     model.addObject("actionId",action.getId());
		     
		    List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocations.getId());
		    model.addObject("sourceleadSectionList",sections);
		   
		    requirement.setJobFairList(requirementService.getJobFairsListByRequirementId(requirement.getId()));
		    
		    List<RequirementIncentive> requirementIncentiveList = requirementService.getRequirementIncentiveListByRequirementId(requirement.getId());
			model.addObject("requirementIncentiveList", requirementIncentiveList);
			model.addObject("requirementIncentiveListSize", requirementIncentiveList.size());
			model.addObject("requirementProfilePercentage",requirement.getProfilePercentage());
		}else{
			model.setViewName("registration/persmissionDenied");
		}
		
		logger.info("EXECUTION END TIME>>>>>" + Calendar.getInstance().getTime());
		return model;
	}
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_REQUIREMENT','ROLE_VIEW_EDIT_REQUIREMENT')")
	@RequestMapping(value = "/requirementSummaryView")
	public ModelAndView requirementSummaryView(HttpServletRequest request, @RequestParam(value="requirementUUID", required=false)String requirementUUID, @RequestParam(value="interviewId", required=false)Long interviewId) throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org loginOrg= (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ModelAndView model = new ModelAndView();
		Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocations.getTimeZoneId()));
		Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
		
		Boolean isInterviewer = requirementInterviewerService.isInterviwerForByRequirementIdInterviewIdAndUserId(requirementUUID, interviewId, loginUser.getId());
		model.addObject("isInterviewer",isInterviewer);
		
		if(null != requirement && requirement.getOrgLocationId().getId().equals(orgLocations.getId())){
			Long requirementId = requirement.getId();
			
			model.setViewName("requirement/requirementSummary");
			model.addObject("requirementId",requirementId);
			
			model.addObject("requirement",requirement);
			
			model.addObject("timezone",timezone.getTimeZoneId());
			
			Boolean isTeamMember = requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementId, loginUser.getId());
			model.addObject("isTeamMember",isTeamMember);
			
			Boolean isManager = requirementService.checkLoginUserIsRequirementManagerByRequirementIdAndUserId(requirementId, loginUser.getId());
			model.addObject("isManager",isManager);
			
			Boolean isAdmin = userRoleService.isUserAdminByOrgLocationId(loginUser.getId(),orgLocations.getId());
			model.addObject("isAdmin",isAdmin);
	
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillsList", skillsList);
			
			List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationsList", educationsList);
			
			List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
			model.addObject("documentsList", documentsList);
			
			if(null != requirement.getIsClientJob() && requirement.getIsClientJob().equalsIgnoreCase("Y")){
				model.addObject("clientSubmissions","Y");
			}else{
				model.addObject("clientSubmissions","N");
			}
			
			Requirement parentRequirement = requirementService.getRequirementByParentRequirementId(requirementId);
	        if(null != parentRequirement){
	            model.addObject("parentRequirement","Y");
	        }else{
	            model.addObject("parentRequirement","N");
	        }
	        
	        if(null != requirement.getSpocId() && null != requirement.getSpocId().getId()){
				OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(requirement.getSpocId().getId());
				if(null != orgSpoc && null != orgSpoc.getSpocStatus() && "In-Active".equalsIgnoreCase(orgSpoc.getSpocStatus())){
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
			model.addObject("requirementUUID",requirementUUID);
		}else{
			model.setViewName("registration/persmissionDenied");
		}
		return model;
	}
	
	
	
	
	
	@RequestMapping(value = "/reqPipelineSubmitToClientMail")
	public ModelAndView reqPipelineSubmitToClientMail(HttpServletRequest request,@RequestParam(value="requirementId", required=false)Long requirementId) throws Exception {
		ModelAndView model = new ModelAndView();
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String fullName = null;
   	      if(!"".equalsIgnoreCase(user.getLastName()) && null != user.getLastName()){
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
	    if(null != req && null != req.getParentRequirement()){
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
		
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "userCreateMsgTemplate");
		if(!isNullOrEmpty(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "userCreateMsgTemplate");
		}
		
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
			if(null != requirementId){
				requirement = requirementService.getRequirementTemplateByRequirementId(requirementId, orgLocations.getId());
			 model.addObject("requirement", requirement);
			 
			 if(null != requirement.getEducation()){
					String[] educationList = requirement.getEducation().split(",");
					requirement.setReqEducationList(educationList);
					requirement.setReqEducationListString(requirement.getEducation());
			}
			
			if(null != requirement.getSkills()){		
					String[] skillsList = requirement.getSkills().split(",");
					requirement.setReqSkillsList(skillsList);
					requirement.setReqSkillsListString(requirement.getSkills());
			}
			 
			}
		
			if(requirement != null && requirement.getId() != null){
				List<String> newlyAddededucationList = requirementService.getAllRequirementEducationByRequirementId(requirement.getId());
			       List<String> educationsList = lookUpDataService.getLookUpDataByCategoryEducation();
			       
			       String education = newlyAddededucationList.get(0);
					if(!"null".equalsIgnoreCase(education) && null != education){
					String arr[] = education.split(",");
					for(String editEducationName : arr){
						if(!educationsList.contains(editEducationName)){
							educationsList.add(editEducationName);
						}
					}
				      model.addObject("educationsList", educationsList);
					}else{
					  model.addObject("educationsList", educationsList);
					} 
				}else{
		         // List<LookUpData> educationsListData = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		          List<String> educationsList = lookUpDataService.getLookUpDataByCategoryEducation();
				  model.addObject("educationsList", educationsList);
				}
			
			/*List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillsList", skillsList);*/
			
			List<LookUpData> experienceLevelList = lookUpDataService.getLookUpDataByCategory(Constants.EXPERIENCELEVEL);
			 model.addObject("experienceLevelList", experienceLevelList);
			
			if(requirement != null && requirement.getId() != null){

				List<String> allReqSkills = requirementService.getAllRequirementSkillsByRequirementI(requirement.getId());
					List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
					String skill = allReqSkills.get(0);
					if(!"null".equalsIgnoreCase(skill) && null != skill){
					String arr[] = skill.split(",");
					for(String editSkillName : arr){
						if(!skillsList.contains(editSkillName)){
							skillsList.add(editSkillName);
						}
					}
				      model.addObject("skillsList", skillsList);
					}else{
					  model.addObject("skillsList", skillsList);
					} 
			}else{
				//List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
				List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
				model.addObject("skillsList", skillsList);
			}
			
			
		
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
	
	@RequestMapping(value = "/ws/acceptOrRejectRequirementOffer")
	public ModelAndView acceptOrRejectRequirementOffer(HttpServletRequest request,@RequestParam(value="offerStatus", required=false)String offerStatus,@RequestParam(value="offerId", required=false)Long offerId) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		RequirementOffers requirementOffers  = requirementOffersService.getRequirementOfferId(offerId);
		List<RequirementOfferHistory> requirementOfferHistories = requirementOfferHistoryService.getAllRequirementOfferHistoriesByOfferId(offerId);
		
		String permission = "NO";
		if(requirementOffers.getSubmissionId().getSubmissionStatus().equalsIgnoreCase(ApplicantSubmissionStatus.OFFERMADE.toString())){
			permission = "YES";
		}else if(requirementOffers.getSubmissionId().getSubmissionStatus().equalsIgnoreCase(ApplicantSubmissionStatus.CLIENT_OFFERMADE.toString())){
			permission = "YES";
		}
		
		ModelAndView model = new ModelAndView();
		model.setViewName("requirement/offerAcceptOrReject");
		model.addObject("offerStatus",offerStatus);
		model.addObject("offerId",offerId);
		model.addObject("requirementOffers",requirementOffers);
		model.addObject("SourceLeadWeb_atsUrl", atsUrl);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("permission",permission);
	
		
		if(offerStatus.equalsIgnoreCase("Accepted") && permission.equalsIgnoreCase("YES")){
			
		    if(null != requirementOfferHistories && !requirementOfferHistories.isEmpty() ){  
			    if(offerStatus.equalsIgnoreCase("Accepted")){
			    	for (RequirementOfferHistory requirementOfferHistory : requirementOfferHistories) {
			    		   RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementOfferHistory.getSubmissionId().getId());
				    		if(null != requirementOfferHistory.getOfferSourceType() && "OFFERMADE".equals(requirementOfferHistory.getOfferSourceType())){
				    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED.toString());
				    		}else{
				    			requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERACCEPTED.toString());
				    		}
							 requirementSubmission.setUpdatedDate(commonUtil.currentDate());
							 requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
							 
							//Strote RequirementSubmissionHistory
						    RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
							requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus());
							if(null != requirementOfferHistory.getOfferSourceType() && "OFFERMADE".equals(requirementOfferHistory.getOfferSourceType())){
							requirementSubmissionHistory.setFromStage("OFFERMADE");
							requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERACCEPTED.toString());
							}else{
								requirementSubmissionHistory.setFromStage("CLIENT_OFFERMADE");
								requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_OFFERACCEPTED.toString());	
							}
							requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
					  }
			    	
					
			    	  //Changing the status of offerTable 
				      if(null != requirementOffers){
				    	  if(requirementOffers.getSubmissionId().getSubmissionStatus() == ApplicantSubmissionStatus.OFFERMADE.toString()){
						    requirementOffers.setOfferStatus("OFFERACCEPTED");
				    	  }else{
				    		  requirementOffers.setOfferStatus("CLIENT_OFFERACCEPTED");  
				    	  }
						    requirementOffers.setUpdatedDate(commonUtil.currentDate());
						    requirementOffersService.saveOfferStatus(requirementOffers);
						    
							Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(requirementOffers.getSubmissionId().getCandidate().getId(),requirementOffers.getSubmissionId().getOrgLocations().getId());
							if(candidate != null){
								candidate.setIsAssociate(Constants.YES);
								candidate.setGeneratedId(candidateService.getCandidateId(requirementOffers.getSubmissionId().getOrgLocations()));
								candidateService.saveOrUpdateCandidate(candidate);
						    	candidateService.saveOrUpdateCandidateElkDocument(candidate);
							}else{
								requirementOffersService.addCandidateToClientAfterOfferAccepted(requirementOffers,requirementOffers.getSubmissionId().getOrgLocations());
							}
				     }
				      
				      if((null != requirementOffers.getSubmissionId().getVendorLocationId() && null != requirementOffers.getSubmissionId().getSourceSubmission()) &&
					      	  ("C2C".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getJobType()) || "C2H".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getJobType()) || "Contract".equalsIgnoreCase(requirementOffers.getSubmissionId().getRequirement().getJobType())) ){
					      		ContractsDTO contractsDTO = populateContractsDTO(requirementOffers,null,requirementOffers.getSubmissionId().getOrgLocations());
					      		contractsTemplateService.saveNewContract(contractsDTO, requirementOffers.getSubmissionId().getOrgLocations(), "DRAFT", user, requirementOffers.getSubmissionId().getOrgLocations().getOrg(), "NEW");
						      }
				      
				      requirementOffersService.sendApplicantOfferAcceptMail(requirementOffers);
			    }
			    
		    }
		}
		return model;
	}
	
	private ContractsDTO populateContractsDTO(RequirementOffers requirementOffers, User loginUser, OrgLocations orgLocations) throws Exception
	{
		ContractsDTO contractsDTO = new ContractsDTO();
		// SimpleDateFormat simpleDateFormat =
		// orgLocations.getLocFullFormDateFormatter();
		RequirementSubmissionTemplateFields candidate = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementOffers.getSubmissionId().getId());
		if (candidate != null) {
			contractsDTO.setContractType("Vendor SOW");
			if (null != candidate.getFirstName() && null != candidate.getLastName()) {
				StringBuilder candisb = new StringBuilder();
				contractsDTO.setContractTitle(candisb.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).append(" SOW").toString());
			}
			else {
				StringBuilder candisb = new StringBuilder();
				contractsDTO.setContractTitle(candisb.append(candidate.getFirstName()).append(" ").append(" SOW").toString());
			}
			contractsDTO.setSubmissionOffersList(requirementOffers.getSubmissionId().getId().toString());
			/*
			 * to allow default owner un-comment the below line --Pavan jampana
			 */
			// jsonObj.put("owner", loginUser.getId());
			contractsDTO.setOrgRelationId(requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getClientId().getId().toString());
			contractsDTO.setVendorContact(requirementOffers.getSubmissionId().getSourceSubmission().getRequirement().getSpocId().getVendorUser().getId().toString());
			contractsDTO.setFlatReward(requirementOffers.getAmount());
			contractsDTO.setBillingRatePer(requirementOffers.getPaymentMode());
			contractsDTO.setBillingRateType(requirementOffers.getPaymentType());
			// Date startTime =
			// commonUtil.getDateFormat(jsonObj.getString("startDate"));
			String currentTimeStr = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), requirementOffers.getDateOfJoining());
			contractsDTO.setStartDate(currentTimeStr);
		}
		return contractsDTO;
	}

	
	@RequestMapping(value = "/downloadRequestDocumentBySubmissionId", method = RequestMethod.GET)
    public void downloadRequestDocumentBySubmissionId(@RequestParam(value="submissionId", required=false) String submissionId ,HttpServletResponse response) throws Exception {
		
			RequirementSubmission requirementSubmission =  requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId));
	        StringBuilder filename = new StringBuilder();
	        
		    response.setContentType("application/octet-stream");
	        response.setHeader("content-disposition", filename.append("attachment; filename=\"").append(requirementSubmission.getSubmissionRequestFileName()).append("\"").toString());
	        
			InputStream inputStream = new ByteArrayInputStream(requirementSubmission.getSubmissionRequestDocFile());
			try {
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();

			} catch (FileNotFoundException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			} 
   }
	
	
	@RequestMapping(value = "/downloadRateDocumentBySubmissionId", method = RequestMethod.GET)
    public void downloadRateDocumentBySubmissionId(@RequestParam(value="submissionId", required=false) String submissionId ,HttpServletResponse response) throws Exception {
		
			RequirementSubmission requirementSubmission =  requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId));
	        StringBuilder filename = new StringBuilder();
	        
		    response.setContentType("application/octet-stream");
	        response.setHeader("content-disposition", filename.append("attachment; filename=\"").append(requirementSubmission.getSubmissionRateFileName()).append("\"").toString());
	        
			InputStream inputStream = new ByteArrayInputStream(requirementSubmission.getSubmissionRateDocFile());
			try {
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();

			} catch (FileNotFoundException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			} 
   }
	
	/**
	* @author David Raju 
	* 
	* This method is used to download OfferAttachment
	* 
	* @param offerId
	* @param request
	* 
	* @return resultJson
	* 
	* @throws Exception
	*/
	@RequestMapping(value = "/ws/downloadOfferAttachment/{offerId}", method = RequestMethod.GET)
    public void downloadOfferAttachment(@RequestParam(value="offerId", required=false) Long offerId ,HttpServletResponse response) throws Exception {
		
		
		   RequirementOffers requirementOffers  =requirementOffersService.getRequirementOfferId(offerId);
		
		    response.setContentType("application/octet-stream");
		    StringBuilder fileName = new StringBuilder();
	        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(requirementOffers.getFileName()).append("\"").toString());
		    
			InputStream inputStream = new ByteArrayInputStream(requirementOffers.getDocFile());
			try {
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();

			} catch (FileNotFoundException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			} 
		}


/*@RequestMapping(value = "/viewJobSummaryDocumentDocView/{title}", method = RequestMethod.GET)
	public void viewJobSummaeyDocumentDocView(@RequestParam(value = "jobId", required = false) String jobId,
			@PathVariable("title") String title, HttpServletResponse response)
			throws Exception {

		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(jobId));

		byte[] pdfBytes = null;
		if ("application/pdf".equalsIgnoreCase(requirement.getFileContentType())) {
			pdfBytes = requirement.getDocFile();
		} else {
			pdfBytes = pdfConversionUtil.convertDocToPdf(requirement.getDocFile());
		}
		response.setHeader("Content-disposition", "inline; filename=contractPdf.pdf");
		response.setContentType("application/pdf");
		ServletOutputStream sos = response.getOutputStream();
		if (null != pdfBytes) {
			response.setContentLength(pdfBytes.length);
			sos.write(pdfBytes);
		}
		sos.flush();
		sos.close();

	}*/	


@RequestMapping(value = "/viewOfferDocumentDocView/{title}", method = RequestMethod.GET)
	public void viewOfferDocumentDocView(@RequestParam(value = "offerID", required = false) String offerID,
			@PathVariable("title") String title, HttpServletResponse response)
		throws Exception {

	RequirementOffers offer = requirementOffersService.getRequirementOfferId(Long.parseLong(offerID));

	byte[] pdfBytes = null;
	if ("application/pdf".equalsIgnoreCase(offer.getFileContentType())) {
		pdfBytes = offer.getDocFile();
	} else {
		pdfBytes = pdfConversionUtil.convertDocToPdf(offer.getDocFile());
	}
	response.setHeader("Content-disposition", "inline; filename=contractPdf.pdf");
	response.setContentType("application/pdf");
	ServletOutputStream sos = response.getOutputStream();
	if (null != pdfBytes) {
		response.setContentLength(pdfBytes.length);
		sos.write(pdfBytes);
	}
	sos.flush();
	sos.close();

}	

@RequestMapping(value = "/viewOfferDocument/{title}", method = RequestMethod.GET)
public void viewOfferDocument(@RequestParam(value="offerId", required=false) String offerId,
		@PathVariable(value="title") String title,
		HttpServletResponse response) throws Exception {
 
	 RequirementOffers requirementOffers = requirementOffersService.getRequirementOfferId(Long.parseLong(offerId));
	 byte[] pdfBytes = null;
		if("application/pdf".equalsIgnoreCase(requirementOffers.getFileContentType())){
			pdfBytes = requirementOffers.getDocFile();
		}
		else{
			 pdfBytes = pdfConversionUtil.convertDocToPdf(requirementOffers.getDocFile());
		}
		 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
	  	    response.setContentType("application/pdf");
	  	    ServletOutputStream sos = response.getOutputStream();
	  	    if(null != pdfBytes){
	  	    	response.setContentLength(pdfBytes.length);
	  	    	sos.write(pdfBytes);
	  	    }
	          sos.flush();
	          sos.close();

} 


@RequestMapping(value = "/viewOffersListAttachment/{title}", method = RequestMethod.GET)
public void viewOffersListAttachment(@RequestParam(value="offerId", required=false) String offerId,
		@PathVariable(value="title") String title,
		HttpServletResponse response) throws Exception {
	
	 RequirementOffers requirementOffers = requirementOffersService.getRequirementOfferId(Long.parseLong(offerId));
	 byte[] pdfBytes = null;
		if("application/pdf".equalsIgnoreCase(requirementOffers.getFileContentType())){
			pdfBytes = requirementOffers.getDocFile();
		}
		else{
			 pdfBytes = pdfConversionUtil.convertDocToPdf(requirementOffers.getDocFile());
		}
		 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
	  	    response.setContentType("application/pdf");
	  	    ServletOutputStream sos = response.getOutputStream();
	  	    if(null != pdfBytes){
	  	    	response.setContentLength(pdfBytes.length);
	  	    	sos.write(pdfBytes);
	  	    }
	          sos.flush();
	          sos.close();

} 



@RequestMapping(value = "/getRequirementSubmissionTemplateForBulkImprtApplicants")
public ModelAndView getRequirementSubmissionTemplateForBulkImprtApplicants(@RequestParam(value="requirementId", required=false) Long requirementId,
		HttpServletRequest request) throws Exception{
	ModelAndView model = new ModelAndView();
	 model.addObject("sourceleadUrl", sourceleadUrl);
	 model.addObject("requirementId", requirementId);
	 model.setViewName("requirement/importApplicantsForSubmission");
	 model.addObject("sourceleadUrl", sourceleadUrl);
	return model;
}


@RequestMapping(value = "/downloadRequirenetSubmissionTemplateByRequirementId/{requirementId}", method = RequestMethod.GET)
public void downloadRequirenetSubmissionTemplateByRequirementId(HttpServletRequest request,HttpServletResponse response,@PathVariable(value="requirementId") Long requirementId) throws IOException, URISyntaxException{
	
	try {
		Requirement requirement = requirementService.getRequirementDetailsById(requirementId);
		String jsonString = null;
		if(StringUtils.isNotBlank(requirement.getClientSubmissionTemplateJsonString())) {
			jsonString = requirement.getClientSubmissionTemplateJsonString();
		}else {
			jsonString = requirement.getSubmissionTemplateJsonString();
		}
		 JSONArray jsonarray = new JSONArray(jsonString);
		 HSSFWorkbook createExcelworkbook = new HSSFWorkbook();
		 String jobName = requirement.getJobTitle();
		// replace special characters 
		 jobName = jobName.replaceAll("[^a-zA-Z0-9]", "_"); 
		
		 DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		 HSSFSheet createExcelsheet = createExcelworkbook.createSheet(jobName + "_" + dateFormat.format(new Date()));
		 HSSFRow writeRow = null;
			createExcelsheet = (HSSFSheet) createXlsSheet(createExcelworkbook,createExcelsheet, writeRow,jsonarray);
			File file = File.createTempFile(jobName + "_Submissions_" + dateFormat.format(new Date()), ".xls");
			file.setReadable(true);
			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
				createExcelworkbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			StringBuilder fileName = new StringBuilder();
			response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();
			
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

private Sheet createXlsSheet(HSSFWorkbook createExcelworkbook,HSSFSheet createExcelsheet, HSSFRow writeRow, JSONArray jsonarray) throws JSONException {
	 writeRow = createExcelsheet.createRow(0);
	 HSSFSheet hidden = null;
	 for(int i=0; i<jsonarray.length(); i++){
	        JSONObject obj = jsonarray.getJSONObject(i);
	        if(obj.has("req") && obj.getString("req").equalsIgnoreCase("1")) {
	        	writeRow.createCell(i).setCellValue(obj.getString("label")+"*");
	        }else {
	        	writeRow.createCell(i).setCellValue(obj.getString("label"));
	        }
	     if(obj.has("choices")) {
	    	 hidden = createExcelworkbook.createSheet("hidden"+i);
	        	 String choices = obj.getString("choices");
	        	 JSONArray choiceJsonArray = new JSONArray(choices);
	        	 String[] choicesArray =  new String [choiceJsonArray.length()];
	        	 for(int j=0; j<choiceJsonArray.length(); j++){
	        		  JSONObject chioceObj = choiceJsonArray.getJSONObject(j);
	        		  String label = chioceObj.getString("label");
	        		  choicesArray[j] = label;
		     		  HSSFRow row = hidden.createRow(j);
		 			  HSSFCell cell = row.createCell(0);
		 			  cell.setCellValue(choicesArray[j]);
	        	  }
	        	
	        	 Name namedCell = createExcelworkbook.createName();
	        	 namedCell.setNameName("hidden"+i);
	        	 namedCell.setRefersToFormula("hidden"+i+"!$A$1:$A$" + choicesArray.length);
	 			 DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden"+i);
	 			 CellRangeAddressList choicesList = new CellRangeAddressList(1, 500, i, i);
	 			 HSSFDataValidation validation = new HSSFDataValidation(choicesList, constraint);
	 			 createExcelsheet.addValidationData(validation);
	        }
	    }
	
	return createExcelsheet;	
}

}