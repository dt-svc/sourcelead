package com.bis.sourcelead.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dto.ImmigrationPassportDTO;
import com.bis.sourcelead.dto.ImmigrationVisaDTO;
import com.bis.sourcelead.dto.LcaDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.AssociateGrades;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.service.AssociateGradesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
    CountriesService countriesService;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;

	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	DepartmentService departmentService;

	@Autowired
	AssociateGradesService associateGradesService;
	
	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	 private SourceleadTagsService sourceleadTagsService;

	@Autowired
	UserGroupDao userGroupDao;
	
	@Autowired
	private ImmigrationCommonService immigrationCommonService;
	
	/**
	 * To forward user creation jsp page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_CREATE_ASSOCIATE')")
	@RequestMapping(value = "/userCreation")
	public ModelAndView userCreation(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		
		    Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		
		    Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
            model.addObject("loginCountryName", countries.getName());
            model.addObject("currencyCode", countries.getCurrency());
			}
		    
			List<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
		    model.addObject("org_projectsList", projectsList);
		    
		    List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
		    model.addObject("rolesList", rolesList);

			List<OrgLocations> locationList = orgLocationService.getAllRegisteredWorkLocationsByOrgId(org.getId());
		    model.addObject("locationList", locationList);
		    
		    List<AssociateGrades> gradesList = associateGradesService.getAssociateGradesListByOrgLocationId(orgLocations.getId());
		    model.addObject("gradesList", gradesList);
		    
		    List<Shift> shiftList = shiftService.getShiftList(orgLocations.getId());
		    model.addObject("shiftList", shiftList);

		     SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_ASSOCIATE);
		     model.addObject("actionId",action.getId());
		     List<SourceleadSectionDTO> sections = commonUtil.getSourceleadFormSectionsByActionId(request, action.getId());
		     model.addObject("sourceleadSectionList",sections); 
		     
		     model.addObject("averageWorkingHours",orgLocations.getAverageWorkingHours());
		   		    
		     StringBuilder sb = new StringBuilder();
		     sb.append(orgLocations.getState()).append(",").append(orgLocations.getCity());
		     model.addObject("location",sb.toString());
		    commonUtil.highLightMenu(Constants.Users, request);
		    
			model.setViewName("users/userCreation");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			model.addObject("probationPeriod",orgLocations.getProbationPeriod());
			
		return model;

	}
	
	
	
	
	@PreAuthorize("hasRole('ROLE_CREATE_ASSOCIATE')")
	@RequestMapping(value = "/addAssociateFromLocations")
	public ModelAndView addAssociateFromLocations(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		ModelAndView model = new ModelAndView();
		
		    Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
		
		    Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
            model.addObject("loginCountryName", countries.getName());
            model.addObject("currencyCode", countries.getCurrency());
			}
		    
		    List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
		    model.addObject("rolesList", rolesList);
		    
		    List<OrgLocations> orgLocationsList = orgLocationService.getAllRegisteredOrgLocationsExceptLoggedInLocation(orgLocations.getId(), org.getId());
		 	model.addObject("orgLocationsList", orgLocationsList);
		   		    
		    commonUtil.highLightMenu(Constants.Users, request);
		    
			model.setViewName("users/addAssociateFromLocations");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
		return model;

	}
	
	/**
	 * To forward user summary jsp with userId, and displays the user summary details
	 * @param request
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_ASSOCIATE','ROLE_VIEW_DELETE_ASSOCIATE','ROLE_VIEW_ASSOCIATE')")
	@RequestMapping(value = "/userSummaryDetails")
	public ModelAndView userSummaryDetails(HttpServletRequest request,
			@RequestParam(value = "userCode", required = false) String userCode,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "tabType", required = false) String tabType) throws Exception {
			ModelAndView model = new ModelAndView();
			commonUtil.highLightMenu(Constants.Users, request);
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
			String isTimeSheetEnrolled = (String) SourceLeadSessionManager.getSessionAttribute(request,"timesheet");
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
            model.addObject("loginCountryName", countries.getName());
            model.addObject("currencyCode", countries.getCurrency());
			}
			
			List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationsList", educationsList);
		   
			List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
		    model.addObject("documentsList", documentsList);
		    if(userId != null && StringUtils.isNotEmpty(userId)) {
		    	User user = userService.getUserById(Long.parseLong(userId));
		    	userCode = user.getUserCode();
		    }
		    
		    if(StringUtils.isNotBlank(tabType)) {
		    	model.addObject("tabType", tabType);
		    }
		    
			User user = userService.getUserByUserCodeAndOrgLocationsId(userCode, orgLocations.getId());
			if(user != null){
				Candidate candidate = candidateService.getCandidateByConsultantUserId(user.getId(), orgLocations.getId());
				Long candidateGloalId = candidateService.getGlobalCandidateIdByCandidateId(candidate.getId());
				if(!isNullOrEmpty(isTimeSheetEnrolled) && isTimeSheetEnrolled.equalsIgnoreCase("Y")){
					Integer assignedProjectsCount = projectsService.getUserAssignedProjectsCount(user.getId(), orgLocations.getId());
					if(candidate != null && candidate.getMasterCandidateId() != null) {
						TimeSheetSubmissionCount userdetails  = timesheetSubmissionsService.getTimeSheetSubmissionsCountByMasterCandidateIdAndOrgLocationId(candidate.getMasterCandidateId().getId(), orgLocations.getId(),org.getId(),"All");
						model.addObject("totalOpenTimeSheetCount", userdetails.getSavedTimesheetSubmissionCount().intValue());
						model.addObject("approvedTimeSheetsCount", userdetails.getApprovedTimesheetSubmissionCount().intValue());
						model.addObject("rejectedTimeSheetsCount", userdetails.getRejectedTimesheetSubmissionCount().intValue());
						model.addObject("waitingTimeSheetsCount", userdetails.getWaitingForApprovalTimesheetSubmissionCount().intValue());
						model.addObject("assignedProjectsCount", assignedProjectsCount);
					}
					
				}
				
				 SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_ASSOCIATE);
			     model.addObject("actionId",action.getId());
			     List<SourceleadSectionDTO> sections = commonUtil.getSourceleadFormSectionsByActionId(request, action.getId());
			     model.addObject("sourceleadSectionList",sections); 
			     
			    boolean isMulitplieOrgAccounts = userRoleService.isUserWithMultipleOrgsAccounts(org.getId(), user.getId());
			    model.addObject("isMulitplieOrgAccounts", isMulitplieOrgAccounts);
			    
				request.setAttribute("candidateId", candidate.getId());
				request.setAttribute("candidateGloalId", candidateGloalId);
				request.setAttribute("userId", user.getId());
				List<Countries> countryList = countriesService.getAllCountries(); // added for passport details.
				model.addObject("countryList", countryList); // added for passport countries.
				List<String> canVisaList = lookUpDataService.getAllVisaByCategory();// added for Visa Types.
				model.addObject("canVisaList", canVisaList);// added for Visa Types.
				model.setViewName("users/userSummary");
			}else{
				model.setViewName("registration/persmissionDenied");
			}
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("userCode",userCode);
		
		return model;

	}
	/**
	 * checks string is empty or null
	 * @param str
	 * @return
	 */
	 
	 protected static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
	 
		@RequestMapping(value = "/addAssociateDeduction")
		public ModelAndView addAssociateDeduction(HttpServletRequest request,@RequestParam(value = "userCode",required = false) String userCode,@RequestParam(value = "saveType",required = false) String saveType) throws Exception
		{
			ModelAndView model = new ModelAndView();
			model.addObject("sourceleadUrl", sourceleadUrl);
			User user = userService.getUserByUserCode(userCode);
			model.addObject("userCode", user.getUserCode());
			model.addObject("userId", user.getId());
			model.addObject("saveType", saveType);
			model.setViewName("users/addAssociateSummaryDeduction");
			return model;
		}
		
		@RequestMapping(value = "/addAssociateContribution")
		public ModelAndView addAssociateContribution(HttpServletRequest request,@RequestParam(value = "userCode",required = false) String userCode,@RequestParam(value = "saveType",required = false) String saveType) throws Exception
		{
			ModelAndView model = new ModelAndView();
			model.addObject("sourceleadUrl", sourceleadUrl);
			User user = userService.getUserByUserCode(userCode);
			model.addObject("userCode", user.getUserCode());
			model.addObject("userId", user.getId());
			model.addObject("saveType", saveType);
			model.setViewName("users/addAssociateSummaryContribution");
			return model;
		}
		/* Below method is used to get the passport details for the Associate.
		 *  Table Name: CANDIDATE_PASSPORT
		 *  Currently using in profile tab of associate summary.
		 */
		@RequestMapping(value = { "/getAssociatePassportDetails" })
			public  @ResponseBody ResponseList<ImmigrationPassportDTO> getAssociatePassportDetails(HttpServletRequest request,
					@RequestParam(value = "candidateId", required = true) Long candidateId) throws Exception {
		    	ResponseList<ImmigrationPassportDTO> reponseList = new ResponseList<>();
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
						"orgLocations");
			try {
				if (orgLocations != null) {
					if (candidateId != null) {
						reponseList = immigrationCommonService.getAssociatePassportDetails(candidateId, orgLocations);
						reponseList.setResponse(Result.SUCCESS.name());
						reponseList.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						reponseList.setResponse(Result.INVALID_USER.name());
						reponseList.setResponseMessage(Result.INVALID_USER.getValue());
					}
				} else {
					reponseList.setResponse(Result.SESSION_TIMEDOUT.name());
					reponseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			} catch (Exception e) {
				reponseList.setResponse(Result.AWKWARD.name());
				reponseList.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return reponseList;
		}
		/* Below method is used to get the VISA details for the Associate.
		 *  Table Names: CANDIDATE_VISA, CANDIDATE_VISA_HISTORY.
		 *  Currently using in profile tab of associate summary
		 */
		@RequestMapping(value = { "/getAssociateVisaDetails" })
		public  @ResponseBody ResponseList<ImmigrationVisaDTO> getAssociateVisaDetails(HttpServletRequest request,
				@RequestParam(value = "candidateId", required = true) Long candidateId) throws Exception {
	    	ResponseList<ImmigrationVisaDTO> reponseList = new ResponseList<>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			List<Long> expiredVisaIdList= new ArrayList<Long>();
		try {
			Date toDayDate = new Date();
			if (orgLocations != null) {
				if (candidateId != null) {
					List<ImmigrationVisaDTO> visaList = new ArrayList<ImmigrationVisaDTO>();
					visaList = immigrationCommonService.getCandidateVisaDetails(candidateId,"");
					for (ImmigrationVisaDTO canVisa : visaList) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String currentDateStr = dateFormat.format(toDayDate);
						Date newDate = dateFormat.parse(currentDateStr);
						Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
						if (StringUtils.isNotBlank(canVisa.getValidTo()) && currentDate.before(commonUtil.stringToDate(canVisa.getValidTo())) && !currentDate.equals(commonUtil.stringToDate(canVisa.getValidTo()))) {
							canVisa.setCurrentStatus("Active");
						}else if (currentDate.compareTo(commonUtil.stringToDate(canVisa.getValidTo())) == 0) {
							canVisa.setCurrentStatus("Active");
						} else {
							canVisa.setCurrentStatus("Expired");
							if(canVisa.getStatus() == Constants.RECORD_ACTIVE)
								expiredVisaIdList.add(canVisa.getCandidateVisaId());
						}
						canVisa.setValidFrom(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),  commonUtil.stringToDate(canVisa.getValidFrom())));
						canVisa.setValidTo(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),  commonUtil.stringToDate(canVisa.getValidTo())));
					}
					if(visaList != null && visaList.size()>0 ) { // here adding visalList to first object of visaList and will get it response.
						visaList.get(0).setExpiredVisaLst(expiredVisaIdList);// form the first object of the visaList
					}
					reponseList.setData(visaList);
					reponseList.setResponse(Result.SUCCESS.name());
					reponseList.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					reponseList.setResponse(Result.INVALID_USER.name());
					reponseList.setResponseMessage(Result.INVALID_USER.getValue());
				}
			} else {
				reponseList.setResponse(Result.SESSION_TIMEDOUT.name());
				reponseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			reponseList.setResponse(Result.AWKWARD.name());
			reponseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return reponseList;
	}
		/* Below method is used to get the LCA details for the Associate.
		 *  Table Names: LCA, LCA_HISTORY,LCA_PLACE_OF_EMPLOYMENT,LCA_VISA_CLARIFICATIONS.
		 *  Currently using in profile tab of associate summary.
		 */
		@RequestMapping(value = { "/getAssociateLcaDetails" })
		public  @ResponseBody ResponseList<LcaDTO> getAssociateLcaDetails(HttpServletRequest request,
				@RequestParam(value = "candidateId", required = true) Long candidateId) throws Exception {
	    	ResponseList<LcaDTO> reponseList = new ResponseList<>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
		try {
			Date toDayDate = new Date();
			if (orgLocations != null) {
				if (candidateId != null) {
					List<LcaDTO> lcaList = new ArrayList<LcaDTO>();
					lcaList = immigrationCommonService.getAssociateLcaDetails(candidateId);
					for (LcaDTO lcaDto : lcaList) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String currentDateStr = dateFormat.format(toDayDate);
						Date newDate = dateFormat.parse(currentDateStr);
						Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
						lcaDto.setEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),  commonUtil.stringToDate(lcaDto.getEndDate())));
						lcaDto.setStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),  commonUtil.stringToDate(lcaDto.getStartDate())));
					}
					reponseList.setData(lcaList);
					reponseList.setResponse(Result.SUCCESS.name());
					reponseList.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					reponseList.setResponse(Result.INVALID_USER.name());
					reponseList.setResponseMessage(Result.INVALID_USER.getValue());
				}
			} else {
				reponseList.setResponse(Result.SESSION_TIMEDOUT.name());
				reponseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			reponseList.setResponse(Result.AWKWARD.name());
			reponseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return reponseList;
	 }
}
