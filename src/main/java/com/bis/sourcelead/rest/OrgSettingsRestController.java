package com.bis.sourcelead.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dao.OfferLetterTemplateDao;
import com.bis.sourcelead.dao.WhiteListIpAddressDao;
import com.bis.sourcelead.dto.AddNewRoleDTO;
import com.bis.sourcelead.dto.AddNewRoleHierarcyDTO;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.AssociateGradesDTO;
import com.bis.sourcelead.dto.CompanyRegistrationDTO;
import com.bis.sourcelead.dto.OfferLetterTemplateDTO;
import com.bis.sourcelead.dto.OrgLocationDTO;
import com.bis.sourcelead.dto.OrgSettingsDTO;
import com.bis.sourcelead.dto.ProbationDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.RoleDTO;
import com.bis.sourcelead.dto.SourceleadRolesDTO;
import com.bis.sourcelead.dto.TabsDTO;
import com.bis.sourcelead.dto.WhiteListIPAddressDTO;
import com.bis.sourcelead.entity.AssociateGrades;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.Modules;
import com.bis.sourcelead.entity.OfferLetterTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionTypes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrglocationSignatory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadRoles;
import com.bis.sourcelead.entity.SourceleadSignature;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.WhiteListIPAddress;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.AssociateGradesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.OfferLetterTemplateService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionTypesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrglocationSignatoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RoleHierarchyService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadRolesService;
import com.bis.sourcelead.service.SourceleadSignatureService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.WhiteListIpAddressService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.Html2PdfUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.util.Calendar;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class OrgSettingsRestController {
	private static final Logger logger = LoggerFactory.getLogger(OrgSettingsRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Autowired
	private OrgService orgService;
	@Autowired
	private CandidateService candidateService;
	@Autowired
	private OrgRelationsService orgRelationsService;
	@Autowired
	private OrglocationSignatoryService orglocationSignatoryService;
	@Autowired
	private OrgLocationService orgLocationService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private RoleHierarchyService roleHierarchyService;
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private SourceleadSignatureService sourceleadSignatureService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ModulesService modulesService;
	@Autowired
	private WhiteListIpAddressService whiteListIpAddressService;
	@Autowired
	private WhiteListIpAddressDao whiteListIpAddressDao;
	@Autowired
	private ExpensesService expensesService;
	@Autowired
	private AddExpensesService addExpensesService;
	@Autowired
	OrgLicenseSubscriptionService orgLicenseSubscriptionService;
	@Autowired
	TabsService tabsService;
	@Autowired
	UserService userService;
	@Autowired
	OrgLicenseSubscriptionTypesService orgLicenseSubscriptionTypesService;
	@Autowired
	TimeZoneService timeZoneService;
	@Autowired
	AssociateGradesService associateGradesService;

	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	SourceleadRolesService sourceleadRolesService;
	
	@Autowired
	SubmissionTemplateService submissionTemplateService;
	
	@Autowired
	UserStatsCacheHelper userStatsCacheHelper;
	
	@Value("${isCouchbaseEnabled}")
	private String isCouchbaseEnabled;
	
	@Autowired
	private OfferLetterTemplateService offerLetterTemplateService;
	
	@Autowired
	private ReportsUtil reportsUtil;
	
	@Autowired
	private OfferLetterTemplateDao offerLetterTemplateDao;
	
	@Autowired
	Html2PdfUtil html2PdfUtil;
	
	@Autowired
	RequirementOffersService requirementOffersService;
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/app/saveOrgImage", method = RequestMethod.POST)
	public String saveOrgImage(CompanyRegistrationDTO companyRegistrationDTO, HttpServletRequest request) throws Exception
	{
		Long orgId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgId");
		companyRegistrationDTO.setStaticPath(staticPath);
		Org org = orgService.findOrgByID(orgId);
		org = orgService.saveOrgSettings(companyRegistrationDTO, org);
		
		if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
			List<OrgLocations> orgLocationsList = orgLocationService.getAllRegisteredWorkLocationsByOrgId(orgId);
			for(OrgLocations orgLocations : orgLocationsList) {
					userStatsCacheHelper.saveOrgLocationDetailsToCouchbase(orgLocations);
			}
		}
		
		
		return "success";
	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/app/saveOrgSettings", method = RequestMethod.POST)
	public Response<String> saveOrgSettings(OrgSettingsDTO orgSettingsDTO, HttpServletRequest request) throws Exception
	{
		/*
		 * Iterator<String> itrator = request.getFileNames(); MultipartFile
		 * multiFile = request.getFile(itrator.next());
		 */
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> response = new Response<>();
	
		try {
			if(user != null){
			if (Validator.validate(orgSettingsDTO)) {
				Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
				ModelMapper mapper1 = new ModelMapper();
				OrgSettings orgSettings = mapper1.map(orgSettingsDTO, OrgSettings.class);
				OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
				orgLocations = orgLocationService.saveOrgLocations(orgSettings, orgLocations);
					if (orgLocations.getGeneratedUserId() != null) {
						changeShortCodeForExpenses(orgLocations.getGeneratedUserId(), orgLocations.getId());
					}
		
					if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
						userStatsCacheHelper.saveOrgLocationDetailsToCouchbase(orgLocations);
					}
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
			}else{
				response.setObject("DataMissing");
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		}else{
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}

	} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}

		return response;
	}
	
	private void changeShortCodeForExpenses(String generatedUserId, Long orgLocationId) throws Exception
	{
		List<Expenses> expenses = expensesService.getAllExpensesByOrgLocationId(orgLocationId);
		if (!expenses.isEmpty()) {
			for (Expenses exp : expenses) {
				String expId = exp.getExpenseId();
				int twoGenId = expId.lastIndexOf("EP", expId.length());
				String genId = expId.substring(0, twoGenId);
				if (exp.getExpenseId() != null && StringUtils.isNotBlank(exp.getExpenseId())
						&& !genId.equalsIgnoreCase(generatedUserId)) {
					int two = expId.lastIndexOf("EP", expId.length());
					String finalExpId = expId.substring(two, expId.length());
					StringBuilder expensesId = new StringBuilder();
					expensesId.append(generatedUserId).append(finalExpId);
					exp.setExpenseId(expensesId.toString());
					addExpensesService.storeAddExpensesData(exp);
				}
			}
		}
	}
	
	@RequestMapping(value = { "/app/displayOrgImage/{orgCode}",
			"/app/ws/displayOrgImage/{orgCode}" }, method = RequestMethod.GET)
	public void displayOrgImage(@PathVariable String orgCode, HttpServletResponse response)
	{
		Org org = null;
		try {
			org = orgService.getOrgByOrgCode(orgCode);
			if (org != null) {
				if (org.getDocFile() != null) {
					org.setFileContentType("image/jpeg");
					InputStream in = new ByteArrayInputStream(org.getDocFile());
					BufferedImage bi = ImageIO.read(in);
					OutputStream out = response.getOutputStream();
					ImageIO.write(bi, "jpg", out);
					out.close();
				}
			}
		} catch (IllegalArgumentException e2) {
			logger.error(ExceptionUtils.getFullStackTrace(e2));
		} catch (IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		} catch (Exception e1) {
			logger.error(ExceptionUtils.getFullStackTrace(e1));
		}
	}
	
	@RequestMapping(value = "/app/displayClientImage/{orgRelationId}", method = RequestMethod.GET)
	public void displayClientImage(@PathVariable String orgRelationId, HttpServletResponse response)
	{
		Org org = null;
		try {
			OrgRelations orgRelation = orgRelationsService.getOrgRelationById(Long.parseLong(orgRelationId));
			org = orgService.getOrgByOrgCode(orgRelation.getResponderLocationId().getOrg().getOrgCode());
			if (org != null) {
				if (org.getDocFile() != null) {
					org.setFileContentType("image/jpeg");
					InputStream in = new ByteArrayInputStream(org.getDocFile());
					BufferedImage bi = ImageIO.read(in);
					OutputStream out = response.getOutputStream();
					ImageIO.write(bi, "jpg", out);
					out.close();
				}
			}
		} catch (IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		} catch (Exception e1) {
			logger.error(ExceptionUtils.getFullStackTrace(e1));
		}
	}
	
	@RequestMapping(value = { "/ws/displayOrgImageInMail/{orgCode}",
			"/app/ws/displayOrgImageInMail/{orgCode}" }, method = RequestMethod.GET)
	public void displayOrgImageInMail(@PathVariable String orgCode, HttpServletResponse response)
	{
		Org org = null;
		try {
			org = orgService.getOrgByOrgCode(orgCode);
			if (org != null && org.getDocFile() != null) {
				org.setFileContentType("image/jpeg");
				InputStream in = new ByteArrayInputStream(org.getDocFile());
				BufferedImage bi = ImageIO.read(in);
				OutputStream out = response.getOutputStream();
				ImageIO.write(bi, "jpg", out);
				out.close();
			}
		} catch (IOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		} catch (Exception e1) {
			logger.error(ExceptionUtils.getFullStackTrace(e1));
		}
	}
	
	@RequestMapping(value = "/app/deleteOrgImage", method = RequestMethod.GET)
	public String deleteOrgImage(HttpServletRequest request) throws Exception
	{
		Long orgId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgId");
		Org org = orgService.findOrgByID(orgId);
		org.setDocFile(null);
		org.setFileName(null);
		org.setFileUrl(null);
		org.setFileContentType(null);
		orgService.updateOrg(org);
		if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
			List<OrgLocations> orgLocationsList = orgLocationService.getAllRegisteredWorkLocationsByOrgId(orgId);
			for(OrgLocations orgLocations : orgLocationsList) {
					userStatsCacheHelper.saveOrgLocationDetailsToCouchbase(orgLocations);
			}
		}
		
		
		return "success";
	}
	
	@RequestMapping(value = "/app/changeHierarchyLevels", method = RequestMethod.POST)
	public String changeHierarchyLevels(@RequestParam(value = "hierarchyOrder", required = false) String hierarchyOrder, HttpServletRequest request) throws Exception
	{
		String[] hierarchyids = hierarchyOrder.split(",");
		int val = 1;
		for (int i = 0; i < hierarchyids.length; i++) {
			RoleHierarchy roleHierarchy = roleHierarchyService.getRoleHierarchyById(Long.valueOf(hierarchyids[i]));
			roleHierarchy.setHierarchyLevel(val);
			roleHierarchyService.saveRoleHierarchy(roleHierarchy);
			val = val + 1;
		}
		return "success";
	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/app/addNewRoleHierarcy", method = RequestMethod.POST)
	public Response<String> addNewRoleHierarcy(AddNewRoleHierarcyDTO addNewRoleHierarcyDTO, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		RoleHierarchy hierarchy = null;
		Response<String> response=new Response<>();
		
		try{
			if(user !=null){
				if (Validator.validate(addNewRoleHierarcyDTO)) {	
					if (addNewRoleHierarcyDTO.getHierarchyId()!= null) {
					hierarchy = roleHierarchyService.getRoleHierarchyById(addNewRoleHierarcyDTO.getHierarchyId());
					hierarchy.setHierarchyName(addNewRoleHierarcyDTO.getLevel());
					hierarchy.setOrgLocations(orgLocations);
					hierarchy.setUpdatedBy(user.getUserCode());
					hierarchy.setUpdatedDate(new Date());
					}else{
						Integer maxHirearchyLevel = roleHierarchyService.getMaxHierarchyValueByOrgLocationId(orgLocations.getId());
						hierarchy = new RoleHierarchy();
						hierarchy.setHierarchyName(addNewRoleHierarcyDTO.getLevel());
						hierarchy.setHierarchyLevel(maxHirearchyLevel + 1);
						hierarchy.setOrgLocations(orgLocations);
						hierarchy.setStatus(Constants.RECORD_ACTIVE);
						hierarchy.setCreatedDate(new Date());
						hierarchy.setCreatedBy(user.getUserCode());
						hierarchy.setUpdatedBy(user.getUserCode());
						hierarchy.setUpdatedDate(new Date());
					}
					roleHierarchyService.saveRoleHierarchy(hierarchy);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
					
			}
				else{
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				}
			else{
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				
			}
			
			
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/app/getOrgEnrolledList")
	@ResponseBody
	public List<RoleDTO> getOrgEnrolledList(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		List<Roles> orgRolesList = new ArrayList<Roles>();
		orgRolesList = rolesService.getOrgEnrolledRolesByOrgLocationId(org, orgLocations.getId());
		List<RoleDTO> orgRolesDTOList = new ArrayList<>();
		
		for (Roles roles : orgRolesList) {
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setId(roles.getId());
			roleDTO.setDisplayName(roles.getDisplayName());
			List<Long> roleActonsList = new ArrayList<Long>();
			if (null != roles.getActionId()) {
				/*for (String s : roles.getActionId().split(",")) {
					roleActonsList.add(Long.parseLong(s));
				}*/
				for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActonsList.add(Long.parseLong(s));
	        	 }
			}
			roleDTO.setUuid(roles.getUuid());
			roleDTO.setActionsCount(roleActonsList.size());
			roleDTO.setHierarchyId(roles.getRoleHierarchy().getId());
			roleDTO.setHierarchyName(roles.getRoleHierarchy().getHierarchyName());
			orgRolesDTOList.add(roleDTO);
		}
		return orgRolesDTOList;
	}
	
	
	@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	@RequestMapping(value = "/app/getGuestOrgEnrolledList")
	public List<SourceleadRolesDTO> getGuestOrgEnrolledList(HttpServletRequest request) throws Exception
	{
		List<SourceleadRolesDTO> rolesDTOList = new ArrayList<>();
		
		List<SourceleadRoles> orgRolesList = sourceleadRolesService.getGuestOrgSourceleadRoles();
		for (SourceleadRoles roles : orgRolesList) {
			
			SourceleadRolesDTO rolesDTO = new SourceleadRolesDTO();
			rolesDTO.setDisplayName(roles.getDisplayName());
			rolesDTO.setUuid(roles.getUuid());
			
			List<Long> roleActonsList = new ArrayList<Long>();
			if (null != roles.getActionId()) {
				/*for (String s : roles.getActionId().split(",")) {
					roleActonsList.add(Long.parseLong(s));
				}*/
				for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	    			 roleActonsList.add(Long.parseLong(s));
	        	 }
			}
			rolesDTO.setActionsCount(roleActonsList.size());
			rolesDTOList.add(rolesDTO);
		}
		return rolesDTOList;
	}
	
	@RequestMapping(value = "/app/roleActions/{uuid}", method = RequestMethod.GET)
	public @ResponseBody void roleActions(@PathVariable String uuid, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		StringBuilder redirectUrl = new StringBuilder();
		response.sendRedirect(redirectUrl.append("/app/enrollRoleActions?uuid=").append(uuid).toString());
	}
	
	
	@RequestMapping(value = "/app/guestRoleActions/{uuid}", method = RequestMethod.GET)
	public @ResponseBody void guestRoleActions(@PathVariable String uuid, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		StringBuilder redirectUrl = new StringBuilder();
		response.sendRedirect(redirectUrl.append("/app/guestEnrollRoleActions?uuid=").append(uuid).toString());
	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/app/saveRoleActions", method = RequestMethod.POST)
	public String saveRoleActions(@RequestParam(value = "uuid", required = false) String uuid, @RequestParam(value = "actionIds", required = false) String actionIds, HttpServletRequest request) throws Exception
	{
		Roles roles = rolesService.getRolesByUUID(uuid);
		
		if (null != roles) {
			if(roles.getRoleName().equalsIgnoreCase("ROLE_PRODUCTION_SUPPORT")) {
				actionIds = actionIds+",115";
			}
			roles.setActionId(actionIds);
			rolesService.storeRoles(roles);
			
			List<UserRole> userRolesList = userRoleService.getAllUserRolesByRoleId(roles.getId());
			if(userRolesList != null && !userRolesList.isEmpty()) {
				for(UserRole userRole : userRolesList) {
					userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
				}
			}
			
			SourceLeadSessionManager.setSessionAttribute(request, "actionRolesMsg", "Action Roles Modified Successfully");
		}
		return "success";
	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/app/validateRoleName", method = RequestMethod.GET)
	public String validateRoleName(@RequestParam(value = "roleName", required = false) String roleName, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Roles roles = rolesService.getRolesByRoleDisplayNameAndOrgLocationId(roleName, orgLocations.getId());
		if (null != roles) {
			return "invalid";
		}
		else {
			return "valid";
		}
	}
	
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/app/validateExistingRoleName", method = RequestMethod.GET)
	public String validateExistingRoleName(@RequestParam(value = "roleName", required = true) String roleName,@RequestParam(value = "roleId", required = true) Long roleId,HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Roles roles = rolesService.getRolesById(roleId);
		
		String result = null;
		
		if(roles != null) {
			if(roles.getDisplayName().equalsIgnoreCase(roleName)) {
				result = "valid";
			}else {
				Roles orgRole = rolesService.getRolesByRoleDisplayNameAndOrgLocationId(roleName, orgLocations.getId());
				if(orgRole != null) {
					result = "invalid";
				}
			}
		}else {
			result = "valid";
		}
		
		return result;
		
	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/app/validateRoleHierarchyLevelName", method = RequestMethod.GET)
	public String validateRoleHierarchyLevelName(@RequestParam(value = "levelName", required = false) String levelName, @RequestParam(value = "hierarchyId", required = false) String hierarchyId, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		RoleHierarchy hierarchy = null;
		String result = null;
		if (!isNullOrEmpty(hierarchyId)) {
			hierarchy = roleHierarchyService.getRoleHierarchyById(Long.parseLong(hierarchyId));
			if (hierarchy.getHierarchyName().equalsIgnoreCase(levelName)) {
				result = "valid";
			}
			else {
				hierarchy = roleHierarchyService.getRoleHierarchyByHierarchyLevelNameAndOrgLocationId(levelName, orgLocations.getId());
				if (null != hierarchy) {
					result = "invalid";
				}
				else {
					result = "valid";
				}
			}
		}
		else {
			hierarchy = roleHierarchyService.getRoleHierarchyByHierarchyLevelNameAndOrgLocationId(levelName, orgLocations.getId());
			if (null != hierarchy) {
				result = "invalid";
			}
			else {
				result = "valid";
			}
		}
		return result;
	}
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/app/addNewRole", method = RequestMethod.POST)
	public String addNewRole(AddNewRoleDTO addNewRoleDTO, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		Roles roles = new Roles();
		String response = null;
		try{
			if(user != null){
				if (Validator.validate(addNewRoleDTO)) {
					StringBuilder role = new StringBuilder();
					roles.setRoleName(role.append("ROLE_").append(addNewRoleDTO.getRoleName()).toString().replaceAll(" ", "_").toUpperCase());
					roles.setDisplayName(addNewRoleDTO.getRoleName());
					roles.setOrg(org);
					roles.setOrgLocations(orgLocations);
					roles.setStatus("1");
					roles.setDefaultRole(0);
					RoleHierarchy roleHierarchy = roleHierarchyService.getRoleHierarchyById(Long.parseLong(addNewRoleDTO.getLevel()));
					roles.setRoleHierarchy(roleHierarchy);
					roles.setCreatedBy(user.getUserCode());
					roles.setCreatedDate(new Date());
					roles = rolesService.storeRoles(roles);
					response = roles.getUuid();
				}else{
					response = "INVALID_DATA";
				}
			}else{
				response = "SESSION_TIMEDOUT";
			}
		}catch (Exception e) {
			response = "AWKWARD";
		}
		return response;
	}
	
	
	@PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
	@RequestMapping(value = "/app/updateOrgLocationRole", method = RequestMethod.POST)
	public String updateOrgLocationRole(RoleDTO roleDTO, HttpServletRequest request) throws Exception
	{
		Roles roles = rolesService.getRolesById(roleDTO.getRoleId());
		if(roles != null) {
			roles.setDisplayName(roleDTO.getDisplayName());
			RoleHierarchy roleHierarchy = new RoleHierarchy();
			roleHierarchy.setId(roleDTO.getHierarchyId());
			roles.setRoleHierarchy(roleHierarchy);
			rolesService.storeRoles(roles);
			SourceLeadSessionManager.setSessionAttribute(request, "displayName",roleDTO.getDisplayName());
		}

		return "success";
	}
	
	
	
	@RequestMapping(value = "/app/getOrgSignature")
	public String getOrgSignature(HttpServletRequest request) throws Exception
	{
		String resultJson = null;
		SourceleadSignature signature = new SourceleadSignature();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<SourceleadSignature> signatureList = null;
		signatureList = sourceleadSignatureService.getSourceLeadSignatureByLocationId(orgLocations.getId());
		if (signatureList.size() == 0) {
			signatureList = sourceleadSignatureService.getSourceLeadSignature();
		}
		String[] fontFamily = Constants.FONT_FAMILY;
		String[] fontSize = Constants.FONT_SIZE;
		List<String> fontFamilyList = new ArrayList<String>();
		List<String> fontSizeList = new ArrayList<String>();
		for (String fFamily : fontFamily) {
			fontFamilyList.add(fFamily);
		}
		for (String fSize : fontSize) {
			fontSizeList.add(fSize);
		}
		signature.setFontFamilyList(fontFamilyList);
		signature.setFontSizeList(fontSizeList);
		signature.setSignatureList(signatureList);
		resultJson = toJSON(signature);
		return resultJson;
	}
	
	/**
	 * 
	 * Handles requests for the Cooling Off Period service.
	 * 
	 * @author Venkat Kesav
	 * @date 15th Mar 2017
	 *
	 */
	@RequestMapping(value = "/app/getCoolingOffPeriod")
	public String getCoolingOffPeriod(HttpServletRequest request) throws Exception
	{
		String coolingOffPeriod = (String) SourceLeadSessionManager.getSessionAttribute(request, "coolingOffPeriod");
		return coolingOffPeriod;
	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/app/saveCoolingOffPeriod", method = RequestMethod.POST)
	public String saveCoolingOffPeriod(@RequestParam(value = "json", required = false) String json, HttpServletRequest request) throws Exception
	{
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
		if (jsonObj.has("coolingOff")) {
			orgLocation.setCoolingOffPeriod(jsonObj.getString("coolingOff"));
		}
		orgLocationService.updateOrgLocations(orgLocation);
		SourceLeadSessionManager.setSessionAttribute(request, "coolingOffPeriod", orgLocation.getCoolingOffPeriod());
		return "success";
	}
	
	@RequestMapping(value = "/app/getOrgLocationDetails")
	public String getOrgLocationDetails(HttpServletRequest request, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception
	{
		String resultJson = null;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		// SimpleDateFormat simpleDateFormat =
		// orgLocations.getLocFullFormDateFormatter();
		if (null != orgLocations && null != orgLocations.getUser()) {
			StringBuilder userName = new StringBuilder();
			if (!isNullOrEmpty(orgLocations.getUser().getLastName())) {
				userName = userName.append(orgLocations.getUser().getFirstName()).append(" ").append(orgLocations.getUser().getLastName());
			}
			else {
				userName = userName.append(orgLocations.getUser().getFirstName());
			}
			orgLocations.setOrglocationUserName(userName.toString());
			orgLocations.setOrglocationUserEmail(orgLocations.getUser().getEmail());
			orgLocations.setOrglocationUserPhone(orgLocations.getUser().getMobilePhone());
			orgLocations.setOrglocationUserId(orgLocations.getUser().getId());
			orgLocations.setOrgCode(org.getOrgCode());
			orgLocations.setTimeZoneName(orgLocations.getTimezone().getTimeZoneId());
			orgLocations.setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), orgLocations.getCreatedDate()));
			UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(orgLocations.getUser().getId(), orgLocations.getId());
			if (null != userRole) {
				orgLocations.setOrglocationUserStatus(userRole.getUserState().toString());
			}
		}
		List<Modules> modulesList = null;
		List<Modules> requirementSubModulesList = null;
		if (null != orgLocations.getModulesId()) {
			ArrayList<Modules> registeredModulesList = new ArrayList<Modules>();
			ArrayList<Modules> registeredSubModulesList = new ArrayList<Modules>();
			modulesList = modulesService.getSourceLeadModules();
			requirementSubModulesList = modulesService.getRequirementSubModules();
			List<Long> orgModulesList = new ArrayList<Long>();
			for (String s : orgLocations.getModulesId().split(",")) {
				orgModulesList.add(Long.parseLong(s));
			}
			for (Modules modules : modulesList) {
				if ((orgModulesList.contains(modules.getId()))) {
					modules.setIsModuleConfigured(Constants.YES);
				}
				else {
					modules.setIsModuleConfigured(Constants.NO);
				}
				registeredModulesList.add(modules);
			}
			for (Modules modules : requirementSubModulesList) {
				if (orgModulesList.contains(modules.getId())) {
					modules.setIsModuleConfigured(Constants.YES);
				}
				else {
					modules.setIsModuleConfigured(Constants.NO);
				}
				registeredSubModulesList.add(modules);
			}
			orgLocations.setModulesList(registeredModulesList);
			orgLocations.setSubModulesList(requirementSubModulesList);
		}
		resultJson = toJSON(orgLocations);
		return resultJson;
	}
	
	@RequestMapping(value = "/app/getPrimaryUserContactDetailsByOrgLocationId", method = RequestMethod.GET)
	public String getPrimaryUserContactDetailsByOrgLocationId(HttpServletRequest request, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception
	{
		String resultJson = null;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		if (null != orgLocations && null != orgLocations.getUser()) {
			orgLocations.setOrgLocationUserFirstName(orgLocations.getUser().getFirstName());
			orgLocations.setOrgLocationUserLastName(orgLocations.getUser().getLastName());
			orgLocations.setOrglocationUserEmail(orgLocations.getUser().getEmail());
			if (orgLocations.getUser().getMobilePhone() != null) {
				orgLocations.setOrglocationUserPhone(orgLocations.getUser().getMobilePhone());
			}
			else {
				orgLocations.setOrglocationUserPhone("");
			}
			orgLocations.setOrglocationUserId(orgLocations.getUser().getId());
		}
		resultJson = toJSON(orgLocations);
		return resultJson;
	}
	
	@RequestMapping(value = "/app/saveOrgShortCodeform", method = RequestMethod.POST)
	public String saveOrgShortCodeform(@RequestParam(value = "json", required = false) String json, @RequestParam(value = "locationCode", required = false) String locationCode, HttpServletRequest request) throws Exception
	{
		JSONObject jsonObj = new JSONObject(json);
		OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		OrgLocations orgLocationsSession = orgLocationService.saveOrgLocationsOrgShortCode(orgLocations, jsonObj);
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocationsSession);
		
		if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
			userStatsCacheHelper.saveOrgLocationDetailsToCouchbase(orgLocationsSession);
		}
		
		return "success";
	}
	
	@RequestMapping(value = "/app/validateOrgShortCode", method = RequestMethod.POST)
	public boolean validateOrgShortCode(@RequestParam(value = "orgShortId") String orgShortId) throws Exception
	{
		boolean validateCode = orgLocationService.validateOrgShortCode(orgShortId);
		return validateCode;
	}
	
	@RequestMapping(value = "/app/getDateFormatByOrgLocation", method = RequestMethod.POST)
	public String getDateFormatByOrgLocation(@RequestParam(value = "locationCode") String locationCode) throws Exception
	{
		OrgLocations orgLocations1 = orgLocationService.getOrgLocationsByLocationCode(locationCode);
		String orgLocatDateFormat = orgLocationService.getDateFormatByOrgLocation(orgLocations1);
		return orgLocatDateFormat;
	}
	
	@RequestMapping(value = "/app/updateLoginSecurity", method = RequestMethod.GET)
	public String updateLoginSecurity(@RequestParam(value = "security") String security, @RequestParam(value = "locationId") Long locationId, HttpServletRequest request) throws Exception
	{
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		if (Constants.YES.equalsIgnoreCase(security)) {
			orgLocations.setLoginSecurity(Constants.YES);
			orgLocationService.updateOrgLocations(orgLocations);
		}
		else {
			orgLocations.setLoginSecurity(Constants.NO);
			orgLocationService.updateOrgLocations(orgLocations);
		}
		
		if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
			userStatsCacheHelper.saveOrgLocationDetailsToCouchbase(orgLocations);
		}
		return "Success";
	}
	
	@RequestMapping(value = "/app/saveWhiteListIps", method = RequestMethod.GET)
	public Response<String> saveWhiteListIps(WhiteListIPAddressDTO whiteListIPAddressDTO, HttpServletRequest request) throws Exception
	{
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user =  userService.getUserById(userId);
		
		Response<String> response = new Response<>();
		if (user != null) {
			try {
				if (Validator.validate(whiteListIPAddressDTO)) {
					whiteListIpAddressService.addWhiteListIps(whiteListIPAddressDTO, orgLocations, user);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
				e.printStackTrace();
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
 
		return response;
	}
	
	@RequestMapping(value = "/app/getWhiteListIps", method = RequestMethod.GET)
	public List<WhiteListIPAddressDTO> getWhiteListIps(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<WhiteListIPAddressDTO> ipList = whiteListIpAddressService.getWhiteListIpsByLocationId(orgLocations.getId(), null);
		return ipList;
	}
	
	private String toJSON(Object obj)
	{
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer)
			{
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}
	
	protected boolean isNullOrEmpty(String str)
	{
		return str == null || "".equals(str.trim());
	}
	
	@RequestMapping(value = "/app/deleteIP", method = RequestMethod.POST)
	public Boolean deleteWhiteListIpById(HttpServletRequest request, @RequestParam(value = "id", required = false) Long whiteListIpId) throws Exception
	{
		boolean isDeleted = false;
		WhiteListIPAddress deleteWhiteListIPAddress = whiteListIpAddressService.getWhiteListIpsById(whiteListIpId);
		if (deleteWhiteListIPAddress != null) {
			deleteWhiteListIPAddress.setStatus(Constants.INACTIVE);
			whiteListIpAddressDao.addWhiteListIps(deleteWhiteListIPAddress);
			isDeleted = true;
		}
		return isDeleted;
	}
	
	@RequestMapping(value = "/app/editWhiteListIp", method = RequestMethod.GET)
	public WhiteListIPAddress editWhiteListIp(@RequestParam(value = "whiteListId", required = false) long whiteListId, HttpServletRequest request) throws Exception
	{
		WhiteListIPAddress whiteListIp = whiteListIpAddressService.getWhiteListIpsById(whiteListId);
		return whiteListIp;
	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/app/saveFinanacialYear", method = RequestMethod.GET)
	public String saveFinanacialYear(@RequestParam(value = "locationId", required = false) Long locationId, @RequestParam(value = "startMonth", required = false) String startMonth, @RequestParam(value = "endMonth", required = false) String endMonth, HttpServletRequest request) throws Exception
	{
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		if (startMonth != null && endMonth != null) {
			orgLocations.setStartMonth(startMonth);
			orgLocations.setEndMonth(endMonth);
			orgLocationService.updateOrgLocations(orgLocations);
		}
		return "Success";
	}
	

	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/app/saveCustomRecommendation", method = RequestMethod.POST)
	public String saveCustomRecommendation(OrgLocationDTO locationDto, HttpServletRequest request) throws Exception
	{
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		orgLocations.setRecommendedSkillPercent(locationDto.getRecommSkill());
		orgLocations.setRecommendedEducationPercent(locationDto.getRecommEducation());
		orgLocations.setRecommendedLocationPercent(locationDto.getRecommLocation());
		orgLocations.setRecommendedNoticePercent(locationDto.getRecommNoticePeriod());
		orgLocations.setRecommendedExpPercent(locationDto.getRecommExp());
		orgLocations.setRecommendedVisatypePercent(locationDto.getRecommVisaType());
		orgLocations.setRecommendedRatePercent(locationDto.getRecommRate());
		orgLocations.setRecommendedKeywordsPercent(locationDto.getRecommKeywords());
		orgLocationService.updateOrgLocations(orgLocations);
		
		return "Success";
	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/app/getAssociatesForSignatory", method = RequestMethod.GET)
	public List<AddUserDTO> getAssociatesForSignatory(HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<User> associatesList = userService.getUsersListForSignatoryByOrglocationId(orgLocations.getId());
		List<AddUserDTO> userDtoList = new ArrayList<>();
		for(User user : associatesList){
			AddUserDTO userdto = new AddUserDTO();
			userdto.setUserId(user.getId().toString());
			userdto.setFirstName(user.getFirstName());
			if(StringUtils.isNotBlank(user.getLastName()))
				userdto.setLastName(user.getLastName());
			userDtoList.add(userdto);
		}
		return userDtoList;
	}
	
	

	@PreAuthorize("hasRole('ROLE_ENROLL_ORG')")
	@RequestMapping(value = "/app/saveGuestRoleActions", method = RequestMethod.POST)
	public String saveGuestRoleActions(@RequestParam(value = "uuid", required = false) String uuid, @RequestParam(value = "actionIds", required = false) String actionIds, HttpServletRequest request) throws Exception
	{
		SourceleadRoles roles = sourceleadRolesService.getSourceleadRolesByUUID(uuid);
		if (null != roles) {
			roles.setActionId(actionIds);
			sourceleadRolesService.saveSourceleadRoles(roles);
			SourceLeadSessionManager.setSessionAttribute(request, "actionRolesMsg", "Action Roles Modified Successfully");
		}
		return "success";
	}

	@RequestMapping(value = "/app/validateAssociate",  method = RequestMethod.POST)
    public @ResponseBody boolean validateAssociate(@RequestParam(value="userId", required=false) Long userId, @RequestParam(value="id", required=false) String uuid, HttpServletRequest request) throws Exception {
	       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	       OrglocationSignatory signatory=null;
	       if(null != uuid){
	        signatory = orglocationSignatoryService.getUserBySignatoryUuId(userId, uuid, orgLocation.getId());
	       }
	       
	       Boolean flag = false;
	       if(signatory == null){

	    	   flag = orglocationSignatoryService.getUserByUserId(userId, orgLocation.getId());
	       }
	      return flag;
		 
	    }

	

	@RequestMapping(value = "/app/generate-career-page-key", method = RequestMethod.POST)
	public String generateCareerPageKey(HttpServletRequest request) throws Exception
	{
		try {
			Long orgId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgId");
			Org org = orgService.findOrgByID(orgId);
			if (org == null) {
				return "logout";
			}
			String key = orgService.generateCareerPageKey(org.getId(), org.getOrgName());
			SourceLeadSessionManager.setSessionAttribute(request, "careerPageKey", key);
			SourceLeadSessionManager.setSessionAttribute(request, "careerPageKeyActive", true);
			
			return "key;" + key;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "failed";
	}
	
	@RequestMapping(value = "/app/career-page-key-status", method = RequestMethod.POST)
	public String careerPageKeyStatus(HttpServletRequest request) throws Exception
	{
		try {
			Long orgId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgId");
			Org org = orgService.findOrgByID(orgId);
			if (org == null) {
				return "logout";
			}
			int status = orgService.changeCareerPageKeyStatus(org.getId());
			org.setCareerPageKeyActive(status == 1);
			SourceLeadSessionManager.setSessionAttribute(request, "careerPageKeyActive", org.getCareerPageKeyActive());
			return "status;" + status;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "failed";
	}
	
	
	
	@RequestMapping(value = "/app/orgLicenseSubscriptionList")
	public ModelAndView orgLicenseSubscriptionList(HttpServletRequest request) throws Exception
	{
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		List<OrgLicenseSubscription> orgLicenseSubscriptionList = new ArrayList<>();
		List<OrgLicenseSubscription> licenseSubscriptionList =  orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgId(org.getId());
		if(licenseSubscriptionList != null && !licenseSubscriptionList.isEmpty()) {
			
			for(OrgLicenseSubscription subscription :licenseSubscriptionList ) {
				List<Long> tabIdsList = new ArrayList<>();
				String[] subscribedTabsList = subscription.getSubscriptionTabs().split(",");
				for(int i=0; i<subscribedTabsList.length; i++) {
			    	tabIdsList.add(Long.parseLong(subscribedTabsList[i]));
			    }
				
				List<Tabs> tabsList = tabsService.getTabsListByIds(tabIdsList);
				List<String> registeredTabs = new ArrayList<>();
				if(tabsList != null && !tabsList.isEmpty()) {
					for(Tabs tabs : tabsList) {
						registeredTabs.add(tabs.getTabName());
					}
				}
				subscription.setModulesList(registeredTabs);
				
				
				if(subscription.getFromDate() != null) {
					subscription.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),subscription.getFromDate()));
				}
				
				if(subscription.getToDate() != null) {
					subscription.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),subscription.getToDate()));
				}
				
				Date currentDate = CalendarUtil.getDateTimeForGivenTimeZone(subscription.getTimezone().getTimeZoneId(), new Date(), "dd-MM-yyyy");
				Calendar toDate = Calendar.getInstance();
				toDate.setTime(currentDate); 
				toDate.add(Calendar.DATE, +15); 
				Date upcommingDate = toDate.getTime();
				Date subscriptionDate = CalendarUtil.getDateTimeForGivenTimeZone(subscription.getTimezone().getTimeZoneId(), subscription.getToDate(), "dd-MM-yyyy");
				
				if(upcommingDate.after(subscriptionDate) || upcommingDate.equals(subscriptionDate)) {
					subscription.setShowRenewal(Constants.YES);
				}
				
				orgLicenseSubscriptionList.add(subscription);
			}
	
		}
		
		model.setViewName("orgLicenseSubscriptionList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("orgLicenseSubscriptionList", orgLicenseSubscriptionList);
		
		return model;
	}
	
	
	 @RequestMapping(value = "/app/upgradeOrgLicenseSubscription/{orgSubscriptionUUID}" , method = RequestMethod.GET)
	 public @ResponseBody void upgradeOrgLicenseSubscription(@PathVariable String orgSubscriptionUUID,HttpServletRequest request,HttpServletResponse response) throws Exception {
			StringBuilder redirectUrl = new StringBuilder();
			response.sendRedirect(redirectUrl.append("/app/upgradeLicenseSubscription?licenseSubscriptionUUID=").append(orgSubscriptionUUID).toString());
			
	   }
	
	@RequestMapping(value = "/app/upgradeLicenseSubscription")
	public ModelAndView upgradeLicenseSubscription(@RequestParam(value="licenseSubscriptionUUID", required=true) String orgSubscriptionUUID,HttpServletRequest request) throws Exception
	{
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		commonUtil.highLightMenu(Constants.OrgSettings, request);
		OrgLicenseSubscription orgLicenseSubscription = null;
		if(orgSubscriptionUUID != null && !orgSubscriptionUUID.isEmpty()) {
			orgLicenseSubscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByUUID(orgSubscriptionUUID);
			
			OrgLicenseSubscriptionTypes subscriptionType = orgLicenseSubscription.getSubscriptionType();
			List<OrgLicenseSubscriptionTypes> subscriptionTypeList = orgLicenseSubscriptionTypesService.getUpgradeLicenseSubscriptionTypesByCategoryAndSubscriptionTypeOrderId(Constants.SUBSCRIPTION_TYPE,subscriptionType.getSubscriptionTypeOrder());
			model.addObject("subscriptionTypeList", subscriptionTypeList);
			
			OrgLicenseSubscriptionTypes userSubscriptionType = orgLicenseSubscription.getUserSubscriptionType();
			List<OrgLicenseSubscriptionTypes> userSubscriptionTypeList = orgLicenseSubscriptionTypesService.getUpgradeLicenseSubscriptionTypesByCategoryAndSubscriptionTypeOrderId(Constants.USER_SUBSCRIPTION,userSubscriptionType.getSubscriptionTypeOrder());
			model.addObject("userSubscriptionTypeList", userSubscriptionTypeList);
			
			OrgLicenseSubscriptionTypes storageSubscriptionType = orgLicenseSubscription.getStorageSubscriptionType();
			List<OrgLicenseSubscriptionTypes> dataStorageTypeList = orgLicenseSubscriptionTypesService.getUpgradeLicenseSubscriptionTypesByCategoryAndSubscriptionTypeOrderId(Constants.DATA_STORAGE,storageSubscriptionType.getSubscriptionTypeOrder());
			model.addObject("dataStorageTypeList", dataStorageTypeList);
			
			orgLicenseSubscription.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),orgLicenseSubscription.getFromDate()));
			
			String[] subscribedTabsList = orgLicenseSubscription.getSubscriptionTabs().split(",");
			List<Long> tabIdsList = new ArrayList<>();
		    for(int i=0; i<subscribedTabsList.length; i++) {
		    	tabIdsList.add(Long.parseLong(subscribedTabsList[i]));
		    }
		    orgLicenseSubscription.setTabIdsList(tabIdsList);
		
		
		List<TabsDTO> tabsDTOList = new ArrayList<>();
		Collection<Tabs> tabsList = tabsService.getAllTabsList();
		List<Long> tabIs = new ArrayList<>();
		tabIs.add(14L);
		tabIs.add(11L);
		List<Tabs> otherTabsList = tabsService.getTabsListByIds(tabIs);
		tabsList.addAll(otherTabsList);
		if(tabsList != null) {
			for(Tabs tabs : tabsList) {
				TabsDTO tabsDTO = new TabsDTO();
				tabsDTO.setId(tabs.getId());
				if(tabs.getTabName().equalsIgnoreCase("Billing")) {
					tabsDTO.setTabName("Finance");
				}else {
					tabsDTO.setTabName(tabs.getTabName());
				}
				tabsDTO.setTabIcon(tabs.getTabIcon());
				if(tabIdsList.contains(tabs.getId())) {
					tabsDTO.setIsTabSubscribed(Constants.YES);
				}else {
					tabsDTO.setIsTabSubscribed(Constants.NO);
				}
				tabsDTOList.add(tabsDTO);
			}
		}
		orgLicenseSubscription.setTabsDTOList(tabsDTOList);
		}
		
		List<Timezone> timezoneList = timeZoneService.getAllTimeZones();
		
		model.setViewName("upgradeOrgLicenseSubscription");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("orgSubscriptionUUID", orgSubscriptionUUID);
		model.addObject("orgLicenseSubscription", orgLicenseSubscription);
		model.addObject("timezoneList", timezoneList);
		return model;
	}
	
	
	@RequestMapping(value = "/app/saveProfilePercentage")
	public Response<String> saveProfilePercentage(@RequestParam(value = "profilePercentage", required = true) Integer profilePercentage,HttpServletRequest request) throws Exception
	{
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> response = new Response<>();
		try {
				if (user != null) {
					orgLocations.setProfilePercentage(profilePercentage);
					orgLocationService.updateOrgLocations(orgLocations);
					SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
					
				}else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
		
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	@RequestMapping(value = "/app/configShowCompanyNameInMarkeplace")
	public Response<String> configShowCompanyNameInMarkeplace(@RequestParam(value = "showCompnayName", required = true) String showCompnayName,HttpServletRequest request) throws Exception
	{
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> response = new Response<>();
		try {
				if (user != null) {
					if(StringUtils.isNotBlank(showCompnayName)
							&&showCompnayName.equalsIgnoreCase("true")) {
					orgLocations.setShowCompanyNameInMarkeplace("Y");
					}else {
						orgLocations.setShowCompanyNameInMarkeplace("N");
					}
					orgLocationService.updateOrgLocations(orgLocations);
					candidateService.updateMarketplaceCandidates(orgLocations,orgLocations.getShowCompanyNameInMarkeplace());
					SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
					
				}else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
		
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	
	@RequestMapping(value = "/app/getAssociatesGradesList", method = RequestMethod.POST)
	public ResponseList<AssociateGradesDTO> getOnboardCandidateList(AssociateGradesDTO associateGradesDTO,
			HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String[] tableColumns = { "gradeName", "designation", "minSalary", "midSalary", "maxSalary", "actions" };
		int sortColumnIndex = 1; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		if (associateGradesDTO.getOrder() != null && !associateGradesDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(associateGradesDTO.getOrder().get(0).get("column"));
			sortColumnOrder = associateGradesDTO.getOrder().get(0).get("dir");
		}
		associateGradesDTO.setSortColumn(tableColumns[sortColumnIndex]);
		associateGradesDTO.setSortOrder(sortColumnOrder);
		ResponseList<AssociateGradesDTO> gradesOnBoardingDTOList = associateGradesService.getAssociateGradesDTOListByOrgLocationId(associateGradesDTO, orgLocations.getId());
		return gradesOnBoardingDTOList;
	}
	
	@RequestMapping(value = "/app/getGradeDetails", method = RequestMethod.POST)
	public AssociateGradesDTO getGradeDetails(@RequestParam(value="uuid", required=true) String uuid,
			HttpServletRequest request) throws Exception {
		AssociateGradesDTO gradesDTO = new AssociateGradesDTO();
		AssociateGrades grades = associateGradesService.getAssociateGradesByUUID(uuid);
		if(grades != null) {
			gradesDTO.setGradeName(grades.getGradeName());
			gradesDTO.setDesignation(grades.getDesignation());
			
			if(grades.getMinSalary() != null) {
				gradesDTO.setMinSalary(String.valueOf(grades.getMinSalary()));
				gradesDTO.setMinSalaryRateType(grades.getMinSalaryRateType());
				gradesDTO.setMinSalaryRatePer(grades.getMinSalaryRatePer());
				
			}
			
			if(grades.getMidSalary() != null) {
				gradesDTO.setMidSalary(String.valueOf(grades.getMidSalary()));
				gradesDTO.setMidSalaryRateType(grades.getMidSalaryRateType());
				gradesDTO.setMidSalaryRatePer(grades.getMidSalaryRatePer());
			}
			
			if(grades.getMaxSalary() != null) {
				gradesDTO.setMaxSalary(String.valueOf(grades.getMaxSalary()));
				gradesDTO.setMaxSalaryRateType(grades.getMaxSalaryRateType());
				gradesDTO.setMaxSalaryRatePer(grades.getMaxSalaryRatePer());
			}
			
			
			gradesDTO.setUuid(grades.getUuid());
		}
		return gradesDTO;
	}
	
	
	@RequestMapping(value = "/app/refreshGradesList", method = RequestMethod.POST)
	public List<AssociateGradesDTO> refreshGradesList(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<AssociateGradesDTO> gradesDTOList = new ArrayList<>();
		List<AssociateGrades> gradesList = associateGradesService.getAssociateGradesListByOrgLocationId(orgLocations.getId());
		if(gradesList != null && !gradesList.isEmpty()) {
			for(AssociateGrades grades : gradesList) {
				AssociateGradesDTO gradesDTO = new AssociateGradesDTO();
				gradesDTO.setId(grades.getId());
				gradesDTO.setGradeName(grades.getGradeName());	
				gradesDTOList.add(gradesDTO);
			}
		}
		return gradesDTOList;
	}
	
	
	@RequestMapping(value = "/app/deleteGradeDetails", method = RequestMethod.POST)
	public AssociateGradesDTO deleteGradeDetails(@RequestParam(value="uuid", required=true) String uuid,
			HttpServletRequest request) throws Exception {
		AssociateGradesDTO gradesDTO = new AssociateGradesDTO();
		AssociateGrades grades = associateGradesService.getAssociateGradesByUUID(uuid);
		if(grades != null) {
			grades.setStatus(Constants.CANDIDATE_IN_ACTIVE);
			associateGradesService.saveAssociateGrades(grades);
		}
		return gradesDTO;
	}
	
	@RequestMapping(value = "/app/validateGradeName", method = RequestMethod.POST)
	public String validateGradeName(@RequestParam(value="gradeName", required=true) String gradeName,@RequestParam(value="uuid", required=false) String uuid,
			HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String result = "true";
		List<AssociateGrades> gradesList = null;
		if(StringUtils.isNotBlank(uuid)) {
			AssociateGrades associateGrades = associateGradesService.getAssociateGradesByUUID(uuid);
			if(associateGrades != null && associateGrades.getGradeName().equalsIgnoreCase(gradeName)) {
				result = "true";
			}else {
				gradesList = associateGradesService.getAssociateGradesListByOrgLocationId(orgLocations.getId());
			}
		}else {
			  gradesList = associateGradesService.getAssociateGradesListByOrgLocationId(orgLocations.getId());
		}
		
		if(gradesList != null) {
			for(AssociateGrades grades : gradesList) {
				if(grades.getGradeName().equalsIgnoreCase(gradeName)) {
					result = "false";
				}
			}
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "/app/addNewGrade", method = RequestMethod.POST)
	public Response<String> addNewGrade(AssociateGradesDTO associateGradesDTO, HttpServletRequest request) throws Exception
	{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> response = new Response<>();
		AssociateGrades associateGrades = null;
		if (user != null) {
			try {
				if (Validator.validate(associateGradesDTO)) {
					if(StringUtils.isNotBlank(associateGradesDTO.getUuid())) {
						associateGrades = associateGradesService.getAssociateGradesByUUID(associateGradesDTO.getUuid());
					}else {
						associateGrades = new AssociateGrades();
						associateGrades.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
					}
					
					associateGrades.setGradeName(associateGradesDTO.getGradeName());
					
					if(StringUtils.isNotBlank(associateGradesDTO.getDesignation())) {
						associateGrades.setDesignation(associateGradesDTO.getDesignation());
					}else {
						associateGrades.setDesignation(null);
					}
					
					if(StringUtils.isNotBlank(associateGradesDTO.getMinSalary())) {
						associateGrades.setMinSalary(Double.valueOf(associateGradesDTO.getMinSalary()));
						associateGrades.setMinSalaryRateType(associateGradesDTO.getMinSalaryRateType());
						associateGrades.setMinSalaryRatePer(associateGradesDTO.getMinSalaryRatePer());
					}else {
						associateGrades.setMinSalary(null);
						associateGrades.setMinSalaryRateType(null);
						associateGrades.setMinSalaryRatePer(null);
					}
					
					

					if(StringUtils.isNotBlank(associateGradesDTO.getMidSalary())) {
						associateGrades.setMidSalary(Double.valueOf(associateGradesDTO.getMidSalary()));
						associateGrades.setMidSalaryRateType(associateGradesDTO.getMidSalaryRateType());
						associateGrades.setMidSalaryRatePer(associateGradesDTO.getMidSalaryRatePer());
					}else {
						associateGrades.setMidSalary(null);
						associateGrades.setMidSalaryRateType(null);
						associateGrades.setMidSalaryRatePer(null);
					}
					
					if(StringUtils.isNotBlank(associateGradesDTO.getMaxSalary())) {
						associateGrades.setMaxSalary(Double.valueOf(associateGradesDTO.getMaxSalary()));
						associateGrades.setMaxSalaryRateType(associateGradesDTO.getMaxSalaryRateType());
						associateGrades.setMaxSalaryRatePer(associateGradesDTO.getMaxSalaryRatePer());
					}else {
						associateGrades.setMaxSalary(null);
						associateGrades.setMaxSalaryRateType(null);
						associateGrades.setMaxSalaryRatePer(null);
					}
					
					associateGrades.setStatus(Constants.CANDIDATE_ACTIVE);
					associateGrades.setCreatedBy(user);
					associateGrades.setUpdatedBy(user);
					associateGrades.setOrgLocationId(orgLocations);
					associateGradesService.saveAssociateGrades(associateGrades);
					
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
				e.printStackTrace();
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
 
		return response;
	}
	
	
	@RequestMapping(value = "/app/getProbationData")
	public ProbationDTO getProbationData(HttpServletRequest request) throws Exception
	{
		ProbationDTO probation = new ProbationDTO();
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		if(orgLocations != null) {
			probation.setProbationPeriod(orgLocations.getProbationPeriod());
			if(orgLocations.getNoOfDays() != null) {
				probation.setNoOfDays(orgLocations.getNoOfDays());
			}else {
				probation.setNoOfDays(0);
			}
			
		}
		return probation;
	}
	
	
	
	
	@RequestMapping(value = "/app/loadSavedSubmissionTemplate" , method = RequestMethod.GET)
	   public String loadSavedSubmissionTemplate(@RequestParam(value="templateId", required=false) Long templateId,HttpServletRequest request) throws Exception {
			
		    SubmissionTemplate submissionTemplate = submissionTemplateService.getSubmissionTemplateById(templateId);
		    
			return submissionTemplate.getJsonString();
	    
	   }
	
	
	
	@RequestMapping(value = "/app/loadSubmissionTemplate" , method = RequestMethod.GET)
	   public String loadSubmissionTemplate() throws Exception {
			
		    SubmissionTemplate submissionTemplate = submissionTemplateService.getSubmissionTemplateById(1L);
		    
			return submissionTemplate.getJsonString();
	    
	   }
	
	
	
	@RequestMapping(value = "/app/validateSubmissionTemplateLabelName" , method = RequestMethod.GET)
	   public String validateSubmissionTemplateLabelName(@RequestParam(value="templateId", required=true) Long templateId,
			   @RequestParam(value="labelName", required=true) String labelName,HttpServletRequest request) throws Exception {
			
		    SubmissionTemplate submissionTemplate = submissionTemplateService.getSubmissionTemplateByLabelName(templateId, labelName);
		   
		    if(submissionTemplate == null){
		    	return "Success";
		    }else{
		    	return "Existed";
		    }
	    
	   }
	
	
	
	@RequestMapping(value = "/app/validateFeedbackTemplateLabelName" , method = RequestMethod.GET)
	   public String validateFeedbackTemplateLabelName(@RequestParam(value="templateId", required=false) Long templateId,
			   @RequestParam(value="labelName", required=false) String labelName,HttpServletRequest request) throws Exception {
			
		    SubmissionTemplate submissionTemplate = null;
		    if(templateId != null){
		    	submissionTemplate = submissionTemplateService.getSubmissionTemplateByLabelName(templateId, labelName);	
		    }
		    if(submissionTemplate == null){
		    	return "Success";
		    }else{
		    	return "Existed";
		    }
	    
	   }
	
	
	
	@RequestMapping(value = "/app/validateTemplateName", method = RequestMethod.POST)
	@ResponseBody String validateTemplateName(@RequestParam(value = "clientLocationId", required = false) Long clientLocationId,
			@RequestParam(value = "templateName", required = false) String templateName,
			@RequestParam(value = "templateId", required = false) String templateId,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		String result = null;
		SubmissionTemplate submissiontTemplate = null;
		
		if(!isNullOrEmpty(templateId)){
			submissiontTemplate  = submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
			if(submissiontTemplate.getTemplateName().equalsIgnoreCase(templateName)){
				result = "valid";
			}else{
				submissiontTemplate  = submissionTemplateService.getSubmissionTemplateByTemplateName(clientLocationId, orgLocations.getId(), templateName);
				if(null != submissiontTemplate){
					result = "invalid";
				}else{
					result = "valid";
				}
			}
		}
		
		else{
			submissiontTemplate  = submissionTemplateService.getSubmissionTemplateByTemplateName(clientLocationId, orgLocations.getId(), templateName);
			if(null != submissiontTemplate){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	

	@RequestMapping(value = "/app/validateTemplateNameByOrg", method = RequestMethod.POST)
	@ResponseBody String validateTemplateNameByOrg(@RequestParam(value = "templateName", required = false) String templateName,
			@RequestParam(value = "templateId", required = false) String templateId,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		SubmissionTemplate submissiontTemplate = null;
		String result = null;
		
		if(!isNullOrEmpty(templateId)){
			submissiontTemplate  = submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
			if(submissiontTemplate.getTemplateName().equalsIgnoreCase(templateName)){
				result = "valid";
			}else{
				submissiontTemplate  = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(orgLocations.getId(), templateName);
				if(null != submissiontTemplate){
					result = "invalid";
				}else{
					result = "valid";
				}
			}
		}
		
		else{
			submissiontTemplate  = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(orgLocations.getId(), templateName);
			if(null != submissiontTemplate){
				result = "invalid";
			}else{
				result = "valid";
			}
		}
		
		return result;
		
	}
	
	
	@RequestMapping(value = "/app/saveProbationData", method = RequestMethod.POST)
	public Response<String> saveProbationData(ProbationDTO probationDTO,HttpServletRequest request) throws Exception
	{
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> response = new Response<>();
		if(user != null) {
			try {
				if (Validator.validate(probationDTO)) {
				
					if(StringUtils.isNotBlank(probationDTO.getProbationPeriod())) {
						orgLocation.setProbationPeriod(probationDTO.getProbationPeriod());
					}
					
					orgLocation.setNoOfDays(probationDTO.getNoOfDays());
				
					orgLocationService.updateOrgLocations(orgLocation);
					
					SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocation);
					
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			
		} catch (Exception e) {
			
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
			
	}else {
		
		response.setResponse(Result.SESSION_TIMEDOUT.name());
		response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
	}
		return response;
		
	}
	
	
	
	@RequestMapping(value = "/app/saveOfferLetterTemplate", method = RequestMethod.POST)
	public @ResponseBody String saveContractTemplate(OfferLetterTemplateDTO offerLetterTemplateDTO, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		offerLetterTemplateDTO.setOrgLocationId(orgLocation.getId());
		offerLetterTemplateDTO.setUserCode(user.getUserCode());
		offerLetterTemplateService.saveOfferLetterTemplate(offerLetterTemplateDTO);
		return "success";
	}
	
	
	@RequestMapping(value = "/app/getAllOfferLetterTemplatesByOrgLocationId", method = RequestMethod.POST)
	public ResponseList<OfferLetterTemplate> getAllOfferLetterTemplatesByOrgLocationId(OfferLetterTemplateDTO offerLetterTemplateDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String[] tableColumns = { "templateName", "description","letterHead",  "" };
		int sortColumnIndex = 0; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		if (offerLetterTemplateDTO.getOrder() != null && !offerLetterTemplateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(offerLetterTemplateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = offerLetterTemplateDTO.getOrder().get(0).get("dir");
		}
		offerLetterTemplateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		offerLetterTemplateDTO.setSortOrder(sortColumnOrder);
		offerLetterTemplateDTO.setOrgLocationId(orgLocation.getId());
		return offerLetterTemplateService.getAllOfferLetterTemplatesByOrgLocationid(offerLetterTemplateDTO);
	}

	@RequestMapping(value = "/app/getOfferLetterTemplateContent", method = RequestMethod.POST)
	public @ResponseBody OfferLetterTemplate getOfferLetterTemplateContent(OfferLetterTemplateDTO offerLetterTemplateDTO, HttpServletRequest request) throws Exception
	{
		OfferLetterTemplate offerLetterTemplate = offerLetterTemplateService.getOfferLetterTemplateDetailsByTemplateUUID(offerLetterTemplateDTO.getTemplateUUID());
		return offerLetterTemplate;
	}
	
	
	@RequestMapping(value = "/app/offerLetterTempleateView/{templateUUID}/{tempName}", method = RequestMethod.GET)
	public @ResponseBody  void offerLetterTempleateView(@PathVariable("templateUUID") String templateUUID,@PathVariable("tempName") String tempName,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OfferLetterTemplate offerLetterTemplate = offerLetterTemplateService.getOfferLetterTemplateDetailsByTemplateUUID(templateUUID);
			String header = "<div style='text-align: center;'>"+offerLetterTemplate.getTemplateName()+"<br></br></div><br></br><br></br><br></br>";
			String totalBody = header+ offerLetterTemplate.getTemplateContent();
			reportsUtil.viewOfferLetterHeadData(totalBody, offerLetterTemplate, response);
	}
	
	 
	
	
	@RequestMapping(value = "/app/deleteOfferLetterTemplateByTemplateUUID", method = RequestMethod.POST)
	public @ResponseBody String deleteOfferLetterTemplateByTemplateUUID(@RequestParam(value = "templateUUID", required = false) String templateUUID, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OfferLetterTemplate offerLetterTemplate = offerLetterTemplateService.getOfferLetterTemplateDetailsByTemplateUUID(templateUUID);
		offerLetterTemplate.setTemplateStatus(Constants.RECORD_INACTIVE);
		offerLetterTemplate.setUpdatedBy(user.getUserCode());
		offerLetterTemplate.setUpdatedDate(new Date());
		offerLetterTemplateDao.saveOfferLetterTemplate(offerLetterTemplate);
		return "success";
	}
	
	
    @RequestMapping(value = "/app/validateOfferLetterTemplateName" , method = RequestMethod.GET)
	 public @ResponseBody  Boolean validateDepartmentname(@RequestParam(value="templateName", required=false) String templateName,
			@RequestParam(value="templateUUID", required=false) String templateUUID,HttpServletRequest request) throws Exception {
    	   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations"); 
    	   OfferLetterTemplate offerLetterTemplate = null;
    	   if(templateUUID != "" && templateUUID != null) {
    		   offerLetterTemplate = offerLetterTemplateService.validateOfferLetterTemplateByTemplateUUIDAndTemplateName(templateUUID, templateName);
    	   }
    	   Boolean flag = false;
    	   if(offerLetterTemplate == null) {
    		   flag = offerLetterTemplateService.validateOfferLetterTemplateByOrgLocationIdAndTemplateName(loginOrgLocation.getId(), templateName);
    	   }
		return flag;
	   }
    
    @RequestMapping(value = {"/app/candidateOfferLetterTempleateView","/app/ws/candidateOfferLetterTempleateView"})
   	public @ResponseBody  void candidateOfferLetterTempleateView(
   		   @RequestParam(value="offerUUID", required=true) String offerUUID,
   		   HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
       	RequirementOffers offer = requirementOffersService.getRequirementOffersByUUID(offerUUID) ;
   		byte[] pdfBytes = null;
   		if(offer.getOfferLetterTemplate() != null) {
   			pdfBytes = html2PdfUtil.getOfferLetterPdfBytes(offer);
   		}
   		response.setHeader("Content-disposition", "inline; filename=offerletterTemplatePdf.pdf");
   		response.setContentType("application/pdf");
   		ServletOutputStream sos = response.getOutputStream();
   		if (null != pdfBytes) {
   			response.setContentLength(pdfBytes.length);
   			sos.write(pdfBytes);
   		}
   		sos.flush();
   		sos.close();
   	} 
}