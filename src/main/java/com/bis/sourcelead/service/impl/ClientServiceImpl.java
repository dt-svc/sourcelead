package com.bis.sourcelead.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.index.helper.ClientVendorIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadRoles;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RoleHierarchyService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadRolesService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.UniversalSearchHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author : Venkat Idapalapati
 *
 */

@Service
public class ClientServiceImpl implements ClientService {

	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;

	@Autowired
	private OrgService orgService;

	@Autowired
	private OrgLocationService orgLocationService;

	@Autowired
	private OrgRelationsService orgRelationsService;

	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	ProjectMembersService projectMembersService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private UserService userService;

	@Autowired
	OrgSpocService orgSpocService;

	@Autowired
	EmailScenarioService emailScenarioService;

	@Autowired
	EmailScenerioUtil emailScenerioUtil;

	@Autowired
	SubmissionTemplateService submissionTemplateService;

	@Autowired
	RolesService rolesService;

	@Autowired
	RoleHierarchyService roleHierarchyService;

	@Autowired
	SourceleadActionsService sourceleadActionsService;

	@Autowired
	private SourceleadRolesService sourceleadRolesService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private EmployeesService employeesService;

	@Autowired
	ClientVendorIndexHelper clientVendorIndexHelper;

	@Autowired
	private SourceleadELKBaseIndexer<ClientVendorIndex> sourceleadELKBaseIndexer;

	@Autowired
	private UniversalSearchHelper universalSearchHelper;
	
	@Autowired
	private RequirementService requirementService;

	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	CountriesService countriesService;

	/**
	 * @author VenkatKesav
	 * @since aug 19 2016
	 * @version 0.0.1
	 * 
	 *          This method is for client saving.
	 * 
	 * @param jsonObj,user,
	 *            orgLocations
	 * 
	 * @return void
	 * 
	 * @throws NullPointer
	 */
	@SuppressWarnings("unused")
	@Override
	public OrgLocations storeClient(ClientDTO clientDTO,AddUserDTO addUserDTO,CandidateDTO candidateDTO, User user, OrgLocations orgLocations, String responderType,
			Org loginOrg) throws Exception {
		HashSet<OrgLocations> orgLocationsList = new HashSet<OrgLocations>();
		OrgLocations clientOrgLocations = null;
		Set<OrgRelations> orgRelationsList = null;
		Org org = null;
		User spocUser = null;
		OrgSpoc orgSpoc = null;
		SubmissionTemplate submissionTemplate = null;
		SubmissionTemplate sourceLeadDefaultTemplate = null;

		String newlyRegistred = "N";
	
		if (StringUtils.isNotBlank(clientDTO.getOrgLocationId())) {
			clientOrgLocations = orgLocationService.getOrgLocations(Long.parseLong(clientDTO.getOrgLocationId()));
			if (StringUtils.isNotBlank(clientDTO.getOrgCountry())) {
				clientOrgLocations.setCountry(clientDTO.getOrgCountry());
			} else if (StringUtils.isNotBlank(clientDTO.getCountry())) {
				clientOrgLocations.setCountry(clientDTO.getCountry());
			}else
			{
				clientOrgLocations.setCountry("--");
			}
			if (StringUtils.isNotBlank(clientDTO.getOrgState())) {
				clientOrgLocations.setState(clientDTO.getOrgState());
			} else {
				clientOrgLocations.setState("--");
			}
			if (StringUtils.isNotBlank(clientDTO.getOrgCity())) {
				clientOrgLocations.setCity(clientDTO.getOrgCity());
			} else if (StringUtils.isNotBlank(clientDTO.getCity())) {
				clientOrgLocations.setCity(clientDTO.getCity());
			}else
			{
				clientOrgLocations.setCity("--");
			}

			if (StringUtils.isNotBlank(clientDTO.getOrgZipcode())) {
				clientOrgLocations.setZipCode(clientDTO.getOrgZipcode());
			} else if (StringUtils.isNotBlank(clientDTO.getZipcode())) {
				clientOrgLocations.setZipCode(clientDTO.getZipcode());
			}else
			{
				clientOrgLocations.setZipCode("--");
			}

			if (StringUtils.isNotBlank(clientDTO.getOrgLocation())) {
				clientOrgLocations.setLocation(clientDTO.getOrgLocation());
			} else if (StringUtils.isNotBlank(clientDTO.getLocation())) {
				clientOrgLocations.setLocation(clientDTO.getLocation());
			}else{
				clientOrgLocations.setLocation("--");
			}

			if (StringUtils.isNotBlank(clientDTO.getLatitude())) {
				clientOrgLocations.setLatitude(clientDTO.getLatitude());
			}else
			{
				clientOrgLocations.setLatitude("--");
			}

			if (StringUtils.isNotBlank(clientDTO.getLongitude())) {
				clientOrgLocations.setLongitude(clientDTO.getLongitude());
			}else{
				clientOrgLocations.setLongitude("--");
			}

		}

		if (clientOrgLocations == null) {
			org = populateOrg(clientDTO, user.getUserCode(), responderType);
			clientOrgLocations = orgLocationService.orgLocationsListByJson(clientDTO, org, orgLocations,
					user.getUserCode(), responderType);

			orgRelationsList = orgRelationsService.orgRelationsListByJson(clientDTO, clientOrgLocations, orgLocations,
					user.getUserCode(), responderType);
			clientOrgLocations.setOrgRelationsList(orgRelationsList);

			if (StringUtils.isNotBlank(clientDTO.getOrgName())) {
				clientOrgLocations.setOrgLocationName(clientDTO.getOrgName());
			}else{
				clientOrgLocations.setOrgLocationName("--");
			}
			if (responderType.equalsIgnoreCase("Client")) {
				clientOrgLocations.setModulesId(Integer.toString(Constants.MODULE_VMS));
			} else if (responderType.equalsIgnoreCase("Vendor")) {
				clientOrgLocations.setModulesId(Integer.toString(Constants.MODULE_ATS));
			}
			if (StringUtils.isNotBlank(clientDTO.getCompanyWebsite())) {
				org.setWebSite(convertWebsite(clientDTO.getCompanyWebsite()));
			}
			if (StringUtils.isNotBlank(clientDTO.getWebSite())) {
				org.setWebSite(convertWebsite(clientDTO.getWebSite()));
			}
			clientOrgLocations.setIsPrimary(Constants.YES);
			clientOrgLocations.setCoolingOffPeriod("3 Months");
			orgLocationsList.add(clientOrgLocations);
			org.setOrgLocationsList(orgLocationsList);
			orgService.addOrganization(org);
			saveOrgRoleHierarchy(clientOrgLocations, user);
			saveOrgBasedRoles(org, clientOrgLocations, user);
			newlyRegistred = "Y";
		} else {
			orgRelationsList = orgRelationsService.orgRelationsListByJson(clientDTO, clientOrgLocations, orgLocations,
					user.getUserCode(), responderType);
			clientOrgLocations.setOrgRelationsList(orgRelationsList);
			orgLocationsList.add(clientOrgLocations);
			org = clientOrgLocations.getOrg();
			if (StringUtils.isNotBlank(clientDTO.getCompanyWebsite())) {
				org.setWebSite(convertWebsite(clientDTO.getCompanyWebsite()));
			}
			if (StringUtils.isNotBlank(clientDTO.getWebSite())) {
				org.setWebSite(convertWebsite(clientDTO.getWebSite()));
			}
			if (StringUtils.isNotBlank(clientDTO.getAddress())) {
				org.setClientAddress(clientDTO.getAddress());
			}else{
				org.setClientAddress("--");
			}
			if (StringUtils.isNotBlank(clientDTO.getBillingAddress())) {
				org.setClientBillingAddress(clientDTO.getBillingAddress());
			}else{
				org.setClientBillingAddress("--");
			}
			if (StringUtils.isNotBlank(clientDTO.getPaymentTerms())) {
				org.setPaymentTerm(clientDTO.getPaymentTerms());
			}else{
				org.setPaymentTerm("--");
			}
			org.setId(org.getId());
			org.setOrgLocationsList(orgLocationsList);
			orgService.addOrganization(org);
		}
		
		if (StringUtils.isNotBlank(clientDTO.getEmail())) {
			spocUser = userService.storeUser(addUserDTO,candidateDTO, user, clientOrgLocations, "CREATEUSER", responderType, loginOrg);

		}

		if(clientDTO.getOwner() != null && !clientDTO.getOwner().isEmpty()){
			for(String owners :clientDTO.getOwner()){
				orgSpoc = new OrgSpoc();
				for (OrgLocations orl : orgLocationsList) {
					Set<OrgRelations> orgr = orl.getOrgRelationsList();
					for (OrgRelations orgRelations : orgr) {
						orgSpoc.setOrgRelations(orgRelations);
					}
				}

				// to differentiate client and vendor in ORG_SPOC Table
				User ownerUser = userService.getUserById(Long.parseLong(owners));
				if (responderType.equalsIgnoreCase("Client")) {
					orgSpoc.setClientLocationId(clientOrgLocations);
					orgSpoc.setVendorLocationId(orgLocations);
					orgSpoc.setClientUser(spocUser);
					orgSpoc.setVendorUser(ownerUser);
					orgSpoc.setOwnerUser(ownerUser);
				} else if (responderType.equalsIgnoreCase("Vendor")) {
					orgSpoc.setClientLocationId(orgLocations);
					orgSpoc.setVendorLocationId(clientOrgLocations);
					orgSpoc.setClientUser(ownerUser);
					orgSpoc.setVendorUser(spocUser);
					orgSpoc.setOwnerUser(ownerUser);
				}

				if (StringUtils.isNotBlank(clientDTO.getSpocStatus())) {
					orgSpoc.setSpocStatus(clientDTO.getSpocStatus());
				} else {
					orgSpoc.setSpocStatus(Constants.ACTIVE);
				}
				if (StringUtils.isNotBlank(clientDTO.getIsMailSent())) {
					orgSpoc.setInvitationStatus(Constants.MAIL_SENT);
				} else {
					orgSpoc.setInvitationStatus(Constants.MAIL_NEW);
				}
				String UUID = SourceLeadRandomCodeHelper.generateUUID();
				orgSpoc.setUUID(UUID);
				orgSpoc.setStatus(Constants.MEMBER_ACTIVE);
				orgSpoc.setCreatedDate(commonUtil.currentDate());
				orgSpoc.setCreatedBy(user.getUserCode());
				orgSpoc.setUpdatedDate(commonUtil.currentDate());
				orgSpoc.setUpdatedBy(user.getUserCode());
				orgSpocService.addOrgSpoc(orgSpoc);

				if (("Client".equals(responderType)) && (StringUtils.isNotBlank(clientDTO.getIsMailSent()) && ("Y".equals(clientDTO.getIsMailSent())))) {
					userService.sendEmailToClientOrVendor(orgSpoc.getUUID(), spocUser, ownerUser, user,
							spocUser.getTempPwd(), responderType, loginOrg, clientOrgLocations.getOrg(),
							clientOrgLocations, orgLocations);
				}
			}
		}

		// store default submission template in guest
		if (Constants.GUEST.equalsIgnoreCase(org.getOrgType())) {
			submissionTemplate = new SubmissionTemplate();
			sourceLeadDefaultTemplate = submissionTemplateService.getSubmissionTemplateById(1L);
			StringBuilder nameBuilder = new StringBuilder();
			StringBuilder descBuilder = new StringBuilder();
			submissionTemplate
					.setTemplateName(nameBuilder.append(org.getOrgName()).append(" Default Template").toString());
			submissionTemplate
					.setDescription(descBuilder.append(org.getOrgName()).append(" Default Template").toString());
			submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
			submissionTemplate.setOrgLocationId(clientOrgLocations);
			submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
			submissionTemplate.setTemplateType("Submission");
			submissionTemplate.setCreatedBy(user.getUserCode());
			submissionTemplate.setUpdatedBy(user.getUserCode());
			submissionTemplate.setCreatedDate(new Date());
			submissionTemplate.setUpdatedDate(new Date());
			submissionTemplateService.addSubmissionTemplate(submissionTemplate);

			// store default interview feedback template in guest
			sourceLeadDefaultTemplate = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(1L,
					Constants.DEFAULT_INTERVIEW_FEEDBACK_TEMAPLATE);
			StringBuilder templateStringBuilder = new StringBuilder();
			submissionTemplate = new SubmissionTemplate();
			submissionTemplate.setTemplateName(
					templateStringBuilder.append(org.getOrgName()).append("Interview Feedback Template").toString());
			submissionTemplate.setDescription(
					templateStringBuilder.append(org.getOrgName()).append("Interview Feedback Template").toString());
			submissionTemplate.setOrgLocationId(clientOrgLocations);
			submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
			submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
			submissionTemplate.setUpdatedBy(user.getUserCode());
			submissionTemplate.setTemplateType("Feedback");
			submissionTemplate.setUpdatedDate(new Date());
			submissionTemplate.setCreatedBy(user.getUserCode());
			submissionTemplate.setCreatedDate(new Date());
			submissionTemplateService.addSubmissionTemplate(submissionTemplate);

		}

		submissionTemplate = submissionTemplateService.getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId(
				clientOrgLocations.getId(), orgLocations.getId());
		// store default submission template in client & vedor
		if (submissionTemplate == null) {
			submissionTemplate = new SubmissionTemplate();
			submissionTemplate.setTemplateName("Default Template");
			sourceLeadDefaultTemplate = submissionTemplateService.getSubmissionTemplateById(1L);
			submissionTemplate.setDescription("Default Template Description");
			submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
			submissionTemplate.setIsDefault("Y");
			if ("Client".equals(responderType)) {
				submissionTemplate.setClientLocationId(clientOrgLocations);
				submissionTemplate.setOrgLocationId(orgLocations);
			} else if ("Vendor".equals(responderType)) {
				submissionTemplate.setClientLocationId(orgLocations);
				submissionTemplate.setOrgLocationId(clientOrgLocations);
			}
			submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
			submissionTemplate.setTemplateType("Submission");
			submissionTemplate.setCreatedBy(user.getUserCode());
			submissionTemplate.setCreatedDate(new Date());
			submissionTemplate.setUpdatedBy(user.getUserCode());
			submissionTemplate.setUpdatedDate(new Date());
			submissionTemplateService.addSubmissionTemplate(submissionTemplate);

			// store default interview feedback template in client & vedor
			sourceLeadDefaultTemplate = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(1L,
					Constants.DEFAULT_INTERVIEW_FEEDBACK_TEMAPLATE);
			StringBuilder templateStringBuilder = new StringBuilder();
			submissionTemplate = new SubmissionTemplate();
			submissionTemplate.setTemplateName(
					templateStringBuilder.append(org.getOrgName()).append("Interview Feedback Template").toString());
			submissionTemplate.setDescription(
					templateStringBuilder.append(org.getOrgName()).append("Interview Feedback Template").toString());
			submissionTemplate.setIsDefault("Y");
			submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
			submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
			submissionTemplate.setUpdatedBy(user.getUserCode());
			submissionTemplate.setTemplateType("Feedback");
			submissionTemplate.setUpdatedDate(new Date());
			submissionTemplate.setCreatedBy(user.getUserCode());
			submissionTemplate.setCreatedDate(new Date());
			submissionTemplateService.addSubmissionTemplate(submissionTemplate);

		}
		
		if(newlyRegistred.equalsIgnoreCase(Constants.YES)){
			saveOrgLocationDefaultProject(clientOrgLocations, spocUser);
			commonUtil.storeVendorOnboardDefaultTemplate(spocUser, clientOrgLocations);
		}

		for (OrgRelations orgRelation : orgRelationsList) {
			clientOrgLocations.setVendorOrgRelationId(orgRelation.getId());
			saveOrUpdateClientVendorElkDocument(org, clientOrgLocations, responderType, orgSpoc, orgRelation,
					submissionTemplate, user, orgLocations);

		}
		clientOrgLocations.setVendorSpocId(spocUser.getId());
		
		return clientOrgLocations;
	}
	
	
	
	 public Projects  saveOrgLocationDefaultProject(OrgLocations orgLocations,User approverUser) throws Exception {
		 	
	    	Projects projects = new Projects();
	    	StringBuilder defaultProjectName = new  StringBuilder();
	    	projects.setProjectName(defaultProjectName.append(orgLocations.getOrgLocationName()).append(" ").append("Default Project").toString());
	    	StringBuilder defaultProjectNameDesc = new  StringBuilder();
	    	projects.setDescription(defaultProjectNameDesc.append(orgLocations.getOrgLocationName()).append(" ").append("Default Project").toString());
	    	projects.setStartDate(new Date());
	    	projects.setInvoiceFrequency(Constants.WEEKLY);
	    	projects.setProjectStatus(Project_Status.INPROGRESS);
	    	projects.setIsDefaultProject(Constants.YES);
	    	projects.setIsPrimary(1);
	    	projects.setCreatedDate(new Date());
	    	projects.setUpdatedDate(new Date());
	    	projects.setOrgLocationsId(orgLocations);
	    	Set<ProjectClients> projectClientsList = getProjectClientsForProject(projects,orgLocations,approverUser);
	    	projects.setProjectClients(projectClientsList);
			projects.setProjectUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			projects.setIsClientProject(Constants.NO);
			projects.setProjectMode("Finished");
			projects.setCreatedDate(new Date());
			projects.setUpdatedDate(new Date());
			projects.setProjectShortCode("DFP");
			Countries countires = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(countires != null) {
				projects.setCurrency(countires.getCurrency());
			}
			
			Projects updatedProjects = projectsService.saveProject(projects);
			projectsService.saveOrUpdateProjectElkDocument(updatedProjects);
			commonUtil.storeSourceleadTasks(approverUser, updatedProjects);
			return updatedProjects;
	}
	 
	 
		private Set<ProjectClients> getProjectClientsForProject(Projects projects,OrgLocations orglocations,User approverUser) throws Exception {
			Set<ProjectClients> projectClientsSet = new HashSet<>();
			
			ProjectClients projectClients = new ProjectClients();
			projectClients.setProjectId(projects);
			projectClients.setOrgLocationsId(orglocations);
			projectClients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			projectClients.setStatus(1);
			projectClients.setLevel(1);
			projectClients.setCreatedDate(new Date());
			projectClients.setUpdatedDate(new Date());
			projectClients.setCreatedBy(approverUser);
			projectClients.setUpdatedBy(approverUser);
			
			//prepare ProjectApproverHierarchy
			Set<ProjectApproverHierarchy> projectApproverHierarchyList = new HashSet<ProjectApproverHierarchy>();
			ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
			pah.setProjectId(projects);
			pah.setProjectClientsId(projectClients);
			pah.setApproverUserId(approverUser);
			pah.setLevel(1);
			pah.setStatus("1");
			projectApproverHierarchyList.add(pah);
			
			projectClients.setProjectApproverHierarchyList(projectApproverHierarchyList);
	    	projectClientsSet.add(projectClients);
			return projectClientsSet;
		}


	/**
	 * populating Org data
	 * 
	 * @param jsonObj
	 * @param loginUserCode
	 * @return Org
	 * @throws JSONException
	 */
	private Org populateOrg(ClientDTO clientDTO, String loginUserCode, String responderType) throws JSONException {
		Org org = new Org();

		if (StringUtils.isNotBlank(clientDTO.getOrgName())) {
			org.setOrgName(clientDTO.getOrgName());
		}
		if (StringUtils.isNotBlank(clientDTO.getCompanyWebsite())) {
			org.setWebSite(convertWebsite(clientDTO.getCompanyWebsite()));
		}
		if (StringUtils.isNotBlank(clientDTO.getWebSite())) {
			org.setWebSite(convertWebsite(clientDTO.getWebSite()));
		}
		org.setOrgType("Guest");

		/*
		 * if(responderType.equalsIgnoreCase("Client")){
		 * org.setModulesId(Integer.toString(Constants.MODULE_VMS)); } else
		 * if(responderType.equalsIgnoreCase("Vendor")){
		 * org.setModulesId(Integer.toString(Constants.MODULE_ATS)); }
		 */

		org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
		org.setCreatedDate(commonUtil.currentDate());
		org.setUpdatedBy(loginUserCode);
		org.setCreatedBy(loginUserCode);
		org.setUpdatedDate(commonUtil.currentDate());
		return org;

	}

	public void saveOrgRoleHierarchy(OrgLocations orgLocations, User user) throws Exception {
		List<RoleHierarchy> hierarchyList = roleHierarchyService.getRoleHierarchyByOrgLocationId(1L);
		if (null != hierarchyList) {
			for (RoleHierarchy roleHierarchy : hierarchyList) {
				RoleHierarchy orgRoleHierarchy = new RoleHierarchy();
				orgRoleHierarchy.setHierarchyName(roleHierarchy.getHierarchyName());
				orgRoleHierarchy.setHierarchyLevel(roleHierarchy.getHierarchyLevel());
				orgRoleHierarchy.setOrgLocations(orgLocations);
				orgRoleHierarchy.setStatus(roleHierarchy.getStatus());
				orgRoleHierarchy.setCreatedBy(user.getUserCode());
				orgRoleHierarchy.setCreatedDate(new Date());
				roleHierarchyService.saveRoleHierarchy(orgRoleHierarchy);
			}
		}

	}

	public void saveOrgBasedRoles(Org org, OrgLocations orgLocations, User user) throws Exception {
		List<SourceleadRoles> rolesList = sourceleadRolesService.getSourceleadRoles();
		if (null != rolesList) {
			for (SourceleadRoles roles : rolesList) {
				Roles orgRoles = new Roles();
				orgRoles.setRoleName(roles.getRoleName());
				orgRoles.setDisplayName(roles.getDisplayName());
				orgRoles.setOrgLocations(orgLocations);
				orgRoles.setOrg(orgLocations.getOrg());
				RoleHierarchy roleHierarchy = roleHierarchyService.getRoleHierarchyByHierarchyLevelAndOrgLocationId(
						roles.getRoleHierarchy().getHierarchyLevel(), orgLocations.getId());
				orgRoles.setRoleHierarchy(roleHierarchy);
				orgRoles.setStatus(roles.getStatus().toString());
				orgRoles.setDefaultRole(1);
				StringBuilder strbul = new StringBuilder();

				List<Long> roleActonsList = new ArrayList<Long>();
				if (null != roles.getActionId()) {
					for (String s : roles.getActionId().split(",")) {
						roleActonsList.add(Long.parseLong(s));
					}
				}

				List<SourceleadActions> sourceLeadActionsList = sourceleadActionsService
						.getAllSourceleadActionsByOrgLocationId(orgLocations);
				for (SourceleadActions actions : sourceLeadActionsList) {
					if (roleActonsList.contains(actions.getId())) {
						strbul.append(actions.getId()).append(",");
					}
				}

				String actionId = strbul.toString();

				if (!isNullOrEmpty(actionId)) {
					actionId = actionId.substring(0, actionId.length() - 1);
					orgRoles.setActionId(actionId);
				} else {
					orgRoles.setActionId(null);
				}

				orgRoles.setCreatedBy(user.getUserCode());
				orgRoles.setCreatedDate(new Date());
				rolesService.storeRoles(orgRoles);
			}
		}

	}

	// This method is not in use so commented by David S
	/**
	 * populating OrgLocations Data
	 * 
	 * @param jsonObj
	 * @param addOrg
	 * @param locationCode
	 * @param loginUserCode
	 * @return OrgLocations
	 * @throws JSONException
	 */
	/*
	 * private OrgLocations populateOrgLocations(JSONObject jsonObj, Org addOrg,
	 * String locationCode, String loginUserCode) throws JSONException {
	 * OrgLocations orgLocations = new OrgLocations(); orgLocations.setOrg(addOrg);
	 * if(jsonObj.has("phone")){ orgLocations.setPhone(jsonObj.getString("phone"));
	 * } if(jsonObj.has("emailId")){
	 * orgLocations.setEmail(jsonObj.getString("emailId")); }
	 * 
	 * if(jsonObj.has("country")){
	 * orgLocations.setCountry(jsonObj.getString("country")); }
	 * if(jsonObj.has("state")){ orgLocations.setState(jsonObj.getString("state"));
	 * }
	 * 
	 * if(jsonObj.has("city")){ orgLocations.setCity(jsonObj.getString("city")); }
	 * 
	 * String clientCountry = locationCode.substring(Math.max(locationCode.length()
	 * - 2, 0)); StringBuilder countryBuilder=new StringBuilder();
	 * orgLocations.setLocationCode(countryBuilder.append(addOrg.getOrgCode()).
	 * append(clientCountry).toString()); orgLocations.setCreatedDate(new Date());
	 * orgLocations.setCreatedBy(loginUserCode);
	 * orgLocations.setUpdatedBy(loginUserCode); orgLocations.setUpdatedDate(new
	 * Date()); return orgLocations; }
	 */

	// This method is not in use so commented by David S

	/**
	 * populate OrgRelations Data
	 * 
	 * @param orgLocations
	 * @param loginOrgLocations
	 * @param loginUserCode
	 * @return
	 */
	/*
	 * private OrgRelations populateOrgRelations(OrgLocations clientOrgLocation,
	 * OrgLocations orgLocations,String loginUserCode) {
	 * 
	 * OrgRelations orgRelations = new OrgRelations();
	 * orgRelations.setResponderLocationId(clientOrgLocation);
	 * orgRelations.setLocationId(orgLocations); orgRelations.setStatus("ACTIVE");
	 * orgRelations.setInitiatorType("Client"); orgRelations.setResponderType("");
	 * orgRelations.setCreatedBy(loginUserCode); orgRelations.setCreatedDate(new
	 * Date()); return orgRelations; }
	 */

	private String convertWebsite(String website) {
		if (!website.startsWith("http") && !website.startsWith("https")) {
			website = "http://" + website;
		}
		URL netUrl = null;
		try {
			netUrl = new URL(website);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		String host = netUrl.getHost();
		if (host.startsWith("www")) {
			host = host.substring("www".length() + 1);
		}
		return host;
	}

	// This method is not in use so commented by David S
	/**
	 * Returns Date
	 * 
	 * @param startDateString
	 * @return
	 */
	/*
	 * private Date getDateFormat(String startDateString) {
	 * 
	 * Date formatedDate = null; try { SimpleDateFormat df = new
	 * SimpleDateFormat("yyyy-MM-dd"); df.setLenient(false); formatedDate =
	 * df.parse(startDateString); } catch (ParseException e) { e.printStackTrace();
	 * } return formatedDate; }
	 */

	/**
	 * check given string empty or null
	 * 
	 * @param str
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public void updateClientDetails(ClientDTO clientDTO, Long orgRelationId, Long clientLocationId, Long loginLocationId,
			User user) throws Exception {

		OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(clientLocationId,
				orgRelationId);
		String level = "level";
		OrgLocations loginOrgLocations = orgLocationService.getOrgLocations(loginLocationId);
		OrgLocations orgLocationForELk = null;
		Org orgForElk = null;
		OrgRelations orgRelationsForElk = null;
		if (null != orgRelation) {

			OrgLocations orgLocations = null;
			// if login user org id is equals to client location id then we get vendor list
			// of ResponderLocationId Org details
			if (orgRelation.getLocationId().getId().longValue() == loginLocationId.longValue()) {
				orgLocations = orgRelation.getResponderLocationId();
				if (StringUtils.isNotBlank(clientDTO.getLevel())) {
					orgRelation.setInitiatorLevel(clientDTO.getLevel());
				}
			}
			if (orgRelation.getResponderLocationId().getId().longValue() == loginLocationId.longValue()) {
				orgLocations = orgRelation.getLocationId();
				if (StringUtils.isNotBlank(clientDTO.getLevel())) {
					orgRelation.setResponderLevel(clientDTO.getLevel());
				}
			}

			orgRelationsForElk = orgRelationsService.addOrgRelations(orgRelation);

			Org org = orgLocations.getOrg();
			if (null != org) {
				if (StringUtils.isNotBlank(clientDTO.getOrgName())) {
					org.setOrgName(clientDTO.getOrgName());
					orgLocations.setOrgLocationName(clientDTO.getOrgName());
				}

				if (StringUtils.isNotBlank(clientDTO.getWebSite())) {
					org.setWebSite(clientDTO.getWebSite());
				}
				if (StringUtils.isNotBlank(clientDTO.getAddress())) {
					org.setClientAddress(clientDTO.getAddress());
				}
				if (StringUtils.isNotBlank(clientDTO.getBillingAddress())) {
					org.setClientBillingAddress(clientDTO.getBillingAddress());
				}
				
				orgForElk = orgService.updateOrg(org);
			}
			if (StringUtils.isNotBlank(clientDTO.getCity())) {
				orgLocations.setCity(clientDTO.getCity());
			} else {
				orgLocations.setCity("");
			}
			if (StringUtils.isNotBlank(clientDTO.getWebSite())) {
				orgLocations.setWebsite(clientDTO.getWebSite());
			}
			if (StringUtils.isNotBlank(clientDTO.getState())) {
				orgLocations.setState(clientDTO.getState());
			} else {

				orgLocations.setState("");
			}
			if (StringUtils.isNotBlank(clientDTO.getCountry())) {
				orgLocations.setCountry(clientDTO.getCountry());
			} else {

				orgLocations.setCountry("");
			}

			if (StringUtils.isNotBlank(clientDTO.getLocation())) {
				orgLocations.setLocation(clientDTO.getLocation());
			} else {

				orgLocations.setLocation("");
			}

			if (StringUtils.isNotBlank(clientDTO.getZipcode())) {
				orgLocations.setZipCode(clientDTO.getZipcode());
			} else {

				orgLocations.setZipCode("");
			}
			orgLocationForELk = orgLocationService.updateOrgLocations(orgLocations);
		} else {
			StringBuilder message = new StringBuilder(MessageFormat.format(statusUpdate, "ORG RELATIONS"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}

		saveOrUpdateClientVendorElkDocument(orgForElk, orgLocationForELk, null, null, orgRelationsForElk, null, user,
				loginOrgLocations);

		// This to update the client/vendor details in ELK

		List<OrgSpoc> updateOrgSpockDetails = orgSpocService.getOrgSpocByOrgRelationId(orgRelationsForElk.getId());
		if (updateOrgSpockDetails.size() > 0 && null != updateOrgSpockDetails) {
			updatingClientVendorDetails(updateOrgSpockDetails);
		}

	}

	@Override
	public void updateVendorDetails(ClientDTO clientDTO, Long orgRelationId, Long vendorLocationId, Long loginOrgId,
			User user) throws Exception {

		OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(vendorLocationId,
				orgRelationId);
		String vendorName = "vendorName";
		String companyWebsite = "companyWebsite";
		String vendorCity = "vendorCity";
		String vendorState = "vendorState";
		String vendorCountry = "vendorCountry";
		String vendorLocation = "vendorLocation";
		String vendorZipCode = "vendorZipCode";
		// This pojo's are for Storing the updated value in Elk

		OrgRelations orgRelationsForElk = null;
		Org orgForElk = null;
		OrgLocations orgLocationsForElk = null;
		OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(loginOrgId);

		// String emailId = "EmailId";
		String level = "level";

		if (null != orgRelation) {
			OrgLocations orgLocations = null;
			// if login user org id is equals to vendor location id then we get vendor list
			// of ResponderLocationId Org details
			if (orgRelation.getLocationId().getId().longValue() == loginOrgId.longValue()) {
				orgLocations = orgRelation.getResponderLocationId();
				if (StringUtils.isNotBlank(clientDTO.getLevel())) {
					orgRelation.setInitiatorLevel(clientDTO.getLevel());
				}
			}
			if (orgRelation.getResponderLocationId().getId().longValue() == loginOrgId.longValue()) {
				orgLocations = orgRelation.getLocationId();
				if (StringUtils.isNotBlank(clientDTO.getLevel())) {
					orgRelation.setResponderLevel(clientDTO.getLevel());
				}
			}
			
			if (StringUtils.isNotBlank(clientDTO.getPaymentTerms())) {
				orgRelation.setPaymentTerm(clientDTO.getPaymentTerms());
			}
			// orgRelation.setLevel(jsonObj.getString(level));
			orgRelationsForElk = orgRelationsService.addOrgRelations(orgRelation);

			Org org = orgLocations.getOrg();
			if (null != org) {
				if (StringUtils.isNotBlank(clientDTO.getVendorName())) {
					org.setOrgName(clientDTO.getVendorName());
					orgLocations.setOrgLocationName(clientDTO.getVendorName());
				}

				if (StringUtils.isNotBlank(clientDTO.getCompanyWebsite())) {
					org.setWebSite(clientDTO.getCompanyWebsite());
				}
				if (StringUtils.isNotBlank(clientDTO.getAddress())) {
					org.setClientAddress(clientDTO.getAddress());
				}else{
					org.setClientAddress(null);
				}
				if (StringUtils.isNotBlank(clientDTO.getBillingAddress())) {
					org.setClientBillingAddress(clientDTO.getBillingAddress());
				}else{
					org.setClientBillingAddress(null);
				}
				
				orgForElk = orgService.updateOrg(org);
			}
			if (StringUtils.isNotBlank(clientDTO.getCompanyWebsite())) {
				orgLocations.setWebsite(clientDTO.getCompanyWebsite());
			} else {
				orgLocations.setWebsite("");
			}
			if (StringUtils.isNotBlank(clientDTO.getCity())) {
				orgLocations.setCity(clientDTO.getCity());
			} else {
				orgLocations.setCity("");
			}
			if (StringUtils.isNotBlank(clientDTO.getState())) {
				orgLocations.setState(clientDTO.getState());
			} else {

				orgLocations.setState("");
			}
			if (StringUtils.isNotBlank(clientDTO.getCountry())) {
				orgLocations.setCountry(clientDTO.getCountry());
			} else {

				orgLocations.setCountry("");
			}
			if (StringUtils.isNotBlank(clientDTO.getLocation())) {
				orgLocations.setLocation(clientDTO.getLocation());
			}else if (StringUtils.isNotBlank(clientDTO.getOrgLocation())) {
				orgLocations.setLocation(clientDTO.getOrgLocation());
			}  
			else {
				orgLocations.setLocation("");
			}
			if (StringUtils.isNotBlank(clientDTO.getZipcode())) {
				orgLocations.setZipCode(clientDTO.getZipcode());
			} else {
				orgLocations.setZipCode("");
			}
			

			/*
			 * if(jsonObj.getString(emailId) !=null &&
			 * !jsonObj.getString(emailId).isEmpty()){
			 * orgLocations.setEmail(jsonObj.getString(emailId)); }else{
			 * orgLocations.setEmail(""); }
			 */
			orgLocationsForElk = orgLocationService.updateOrgLocations(orgLocations);
		} else {
			StringBuilder message = new StringBuilder(MessageFormat.format(statusUpdate, "ORG RELATIONS"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}

		saveOrUpdateClientVendorElkDocument(orgForElk, orgLocationsForElk, null, null, orgRelationsForElk, null, user,
				loginOrgLocation);

		// This to update the client/vendor details in ELK

		List<OrgSpoc> updateOrgSpockDetails = orgSpocService.getOrgSpocByOrgRelationId(orgRelationsForElk.getId());
		if (updateOrgSpockDetails.size() > 0 && null != updateOrgSpockDetails) {
			updatingClientVendorDetails(updateOrgSpockDetails);
		}

	}

	@Override
	public OrgSpoc updateVendorContactDetails(ClientDTO clientDTO, Long spocId, Long loginOrgId, User loginUser,
			Long ownerId, Long spocUserId, OrgLocations orgLocation, String type) throws Exception {

		// OrgRelations orgRelation =
		// orgRelationsService.getClientAndVendorOrgDeatilsById(vendorLocationId,orgRelationId);

		OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(spocId);

		String vendorContactFirstName = "vendorContactFirstName";
		String vendorContactLastName = "vendorContactLastName";
		String mobilePhone = "mobilePhone";
		String spocStatus = "spocStatus";
		String emailId = "email";
		String owner = "owner";

		if (null != orgSpoc) {
			User editUser = null;
			if (StringUtils.isNotBlank(clientDTO.getExistingSpocUserId())) {
				User existingUser = userService.getUserById(Long.parseLong(clientDTO.getExistingSpocUserId()));
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(loginUser.getId(),
						orgLocation.getId());

				if (StringUtils.isNotBlank(clientDTO.getFirstName())) {
					existingUser.setFirstName(clientDTO.getFirstName());
				} else {
					existingUser.setFirstName("");
				}
				if (StringUtils.isNotBlank(clientDTO.getLastName())){
					existingUser.setLastName(clientDTO.getLastName());
				} else {
					existingUser.setLastName("");
				}
				
				if (StringUtils.isNotBlank(clientDTO.getPhoneNumber())) {
					existingUser.setMobilePhone(clientDTO.getPhoneNumber());
				} else {
					existingUser.setMobilePhone("");
				}
				
				if(StringUtils.isNotBlank(clientDTO.getExtensionNumber())){
					existingUser.setExtensionNumber(clientDTO.getExtensionNumber());
				}else{
					existingUser.setExtensionNumber("");
				}
				
				if (StringUtils.isNotBlank(clientDTO.getEmail())) {
					existingUser.setEmail(clientDTO.getEmail());
				} else {
					existingUser.setEmail("");
				}
				editUser = userService.addUser(existingUser);

				Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(editUser.getId(),
						orgLocation.getId());
				// Update the user details for Elk search
				commonUtil.commonUserUpdate(editUser);
				userService.saveOrUpdateUserElkDocument(userRole, employee, editUser.getUserCode());

			}

			if (StringUtils.isNotBlank(clientDTO.getSpocStatus())) {
				orgSpoc.setSpocStatus(clientDTO.getSpocStatus());
			}

			if (null != type && !"".equalsIgnoreCase(type) && "Client".equalsIgnoreCase(type)) {
				User user = orgSpoc.getClientUser();
				for(String owners : clientDTO.getOwner()){
					orgSpoc.setTempOwnerId(orgSpoc.getVendorUser().getId());
					User spocUser = new User();
					spocUser.setId(Long.parseLong(owners));
					orgSpoc.setVendorUser(spocUser);
					// new owner filed updated when owner updated
					orgSpoc.setOwnerUser(spocUser);

				OrgSpoc orSpocElk = orgSpocService.addOrgSpoc(orgSpoc);

				if (null != orgSpoc.getTempOwnerId()&& orgSpoc.getTempOwnerId().longValue() != orgSpoc.getVendorUser().getId().longValue()) {
					user = userService.getUserById(Long.parseLong(owners));
					sendEmailToClientContact(user, editUser, orgLocation, loginUser, orgSpoc);
				}
				}
			} else if (null != type && !"".equalsIgnoreCase(type) && "Vendor".equalsIgnoreCase(type)) {
				User user = orgSpoc.getVendorUser();
				for(String owners : clientDTO.getOwner()){
					orgSpoc.setTempOwnerId(orgSpoc.getClientUser().getId());
					User spocUser = new User();
					spocUser.setId(Long.parseLong(owners));
					orgSpoc.setClientUser(spocUser);
					// new owner filed updated when owner updated
					orgSpoc.setOwnerUser(spocUser);
				OrgSpoc orSpocElk = orgSpocService.addOrgSpoc(orgSpoc);

				if (null != orgSpoc.getTempOwnerId()
						&& orgSpoc.getTempOwnerId().longValue() != orgSpoc.getClientUser().getId().longValue()) {
					user = userService.getUserById(Long.parseLong(owners));
					sendEmailToClientContact(user, editUser, orgLocation, loginUser, orgSpoc);
				}
			}
			}
		} else {
			StringBuilder message = new StringBuilder(MessageFormat.format(statusUpdate, "ORG RELATIONS"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		
		for(String owners : clientDTO.getOwner()){
		User changedUser = userService.getUserById(Long.parseLong(owners));
		if (!loginUser.getId().equals(changedUser.getId())) {
			sendupdatedownerMailTopocUser(loginUser, spocId, owners, orgLocation);
		}
		}
		return orgSpoc;

	}

	private void sendupdatedownerMailTopocUser(User loginUser, Long spocId, String owner, OrgLocations orgLocation)
			throws NumberFormatException, Exception {
		User changedUser = userService.getUserById(Long.parseLong(owner));
		OrgSpoc orgSpocLocation = orgSpocService.getOrgSpocById(spocId);
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(changedUser.getFirstName());
		emailMessage.setLastName(changedUser.getLastName());
		emailMessage.setToEmail(changedUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder fromName = new StringBuilder();
		emailMessage.setFromName(
				fromName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("orgContactName", orgSpocLocation.getClientUser().getFullName());
		globalVarsMap.put("clientOrgName", orgSpocLocation.getClientLocationId().getOrgLocationName());
		globalVarsMap.put("ownerName", loginUser.getFullName());
		globalVarsMap.put("changedOwnerName", changedUser.getFullName());
		globalVarsMap.put("loginUser", loginUser.getFirstName());
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("loginLink", loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
				.toString());
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

		// getting the current EmailScenario by EmailScenario unique name and entity
		// type
		EmailScenario emailScenario = null;
		emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CLIENT_OWNER_UPDATEMAIL,
				Constants.ENTITY_TYPE_EMAIL);

		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, orgLocation.getOrg(), orgLocation);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);

		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, orgLocation.getId(), iAdressArray, new ArrayList<EmailAttachment>());

		EmailScenario noificationEmailScenario = emailScenarioService
				.getEmailScenarioByUniqueName(Constants.CLIENT_OWNER_UPDATEMAIL, Constants.ENTITY_TYPE_NOTIFICATION);
		String actionLink = "";
		String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(), globalVarsMap);
		noificationEmailScenario.setBody(notificationbody);

		emailScenerioUtil.sendNotification(actionLink, loginUser, changedUser, orgLocation, noificationEmailScenario);

	}

	private void sendEmailToClientContact(User user, User editUser, OrgLocations orgLocation, User loginUser,
			OrgSpoc orgSpoc) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(editUser.getFirstName());
		emailMessage.setLastName(editUser.getLastName());
		emailMessage.setToEmail(editUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder fromName = new StringBuilder();
		emailMessage.setFromName(
				fromName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();

		if (orgSpoc.getOrgRelations().getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
			globalVarsMap.put("changedOwnerOrg", orgSpoc.getOrgRelations().getLocationId().getOrg().getOrgName());
		}
		if (orgSpoc.getOrgRelations().getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
			globalVarsMap.put("changedOwnerOrg",
					orgSpoc.getOrgRelations().getResponderLocationId().getOrg().getOrgName());
		}

		if (null != orgSpoc.getTempOwnerId()) {
			User previousOwner = userService.getUserById(orgSpoc.getTempOwnerId());
			StringBuilder changedOwnerBuilder = new StringBuilder();
			if (null != previousOwner.getLastName()) {
				globalVarsMap.put("changedOwnerName", changedOwnerBuilder.append(previousOwner.getFirstName())
						.append(" ").append(previousOwner.getLastName()).toString());
			} else {
				globalVarsMap.put("changedOwnerName",
						changedOwnerBuilder.append(previousOwner.getFirstName()).toString());
			}
		}

		globalVarsMap.put("clientFirstName", emailMessage.getFirstName());
		globalVarsMap.put("clientLastName", emailMessage.getLastName());
		globalVarsMap.put("loginUser", emailMessage.getFromName());
		globalVarsMap.put("loginOrgName", orgLocation.getOrgLocationName());
		globalVarsMap.put("ownerName", user.getFirstName());
		globalVarsMap.put("ownerEmail", user.getEmail());

		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

		// getting the current EmailScenario by EmailScenario unique name and entity
		// type
		EmailScenario emailScenario = null;
		emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.EDIT_CLIENTCONTACT,
				Constants.ENTITY_TYPE_EMAIL);

		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, orgLocation.getOrg(), orgLocation);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);

		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, orgLocation.getId(), iAdressArray, new ArrayList<EmailAttachment>());

		EmailScenario noificationEmailScenario = emailScenarioService
				.getEmailScenarioByUniqueName(Constants.EDIT_CLIENTCONTACT, Constants.ENTITY_TYPE_NOTIFICATION);
		String actionLink = "";
		String rejectActionLink = "";
		String spocUUID = "";
		String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(), globalVarsMap);
		noificationEmailScenario.setBody(notificationbody);

		OrgLocations clientOrgLocation = null;
		OrgRelations orgRelations = orgRelationsService.getOrgRelationById(orgSpoc.getOrgRelations().getId());
		if (orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()) {
			clientOrgLocation = orgRelations.getLocationId();
		} else if (orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()) {
			clientOrgLocation = orgRelations.getResponderLocationId();
		}

		emailScenerioUtil.sendNotificationToClientOrVendorWhenOwnerChange(actionLink, rejectActionLink, loginUser,
				loginUser, editUser, orgLocation, noificationEmailScenario, spocUUID, clientOrgLocation);

	}

	@Override
	public void saveOrUpdateClientVendorElkDocument(Org org, OrgLocations clientOrgLocations, String responderType,
			OrgSpoc orgSpoc, OrgRelations orgRelation, SubmissionTemplate submissionTemplate, User user,
			OrgLocations loginOrgLocation) throws Exception {

		// Elastic Search Document Saving
		ClientVendorIndex clientVendorIndex = clientVendorIndexHelper.getClientVendorIndexFromCandidate(org,
				clientOrgLocations, responderType, orgSpoc, orgRelation, submissionTemplate, user, loginOrgLocation);
		sourceleadELKBaseIndexer.saveOrUpdateElkDocument(clientVendorIndex, Constants.ELK_CLIENTVENDOR_INDEX,
				Constants.ELK_CLIENTVENDOR_TYPE, orgRelation.getId().toString());

	}

	@Override
	public void sendAcceptEmailToClientOrVendor(OrgSpoc orgSpoc, User loginUser, OrgLocations orgLocations)
			throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		if (null != orgSpoc.getOwnerUser()) {
			emailMessage.setFirstName(orgSpoc.getOwnerUser().getFirstName());
			emailMessage.setLastName(orgSpoc.getOwnerUser().getLastName());
		}
		emailMessage.setToEmail(orgSpoc.getOwnerUser().getEmail());
		emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder emailMsgBuilder = new StringBuilder();
		emailMessage.setFromName("Sourcelead");
		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		String UUID = SourceLeadRandomCodeHelper.generateUUID();
		if (Constants.CLIENT.equals(orgSpoc.getOrgRelations().getResponderType())) {
			globalVarsMap.put("firstName", emailMessage.getFirstName());
			globalVarsMap.put("lastName", emailMessage.getLastName());
			globalVarsMap.put("vendorFirstName", orgSpoc.getClientUser().getFirstName());
			globalVarsMap.put("vendorLastName", orgSpoc.getClientUser().getLastName());
			globalVarsMap.put("vendorOrgName", orgSpoc.getOrgRelations().getResponderLocationId().getOrgLocationName());
			globalVarsMap.put("signature", "Sourcelead Team");

			if (orgSpoc.getOrgRelations().getResponderLocationId().getId().longValue() == orgLocations.getId()
					.longValue()) {
				globalVarsMap.put("invitedOrg", orgSpoc.getOrgRelations().getLocationId().getOrg().getOrgName());
			}
			if (orgSpoc.getOrgRelations().getLocationId().getId().longValue() == orgLocations.getId().longValue()) {
				globalVarsMap.put("invitedOrg",
						orgSpoc.getOrgRelations().getResponderLocationId().getOrg().getOrgName());
			}

			StringBuilder linkBuilder = new StringBuilder();
			globalVarsMap.put("faqLink", linkBuilder.append(
					"<a href='http://support.sourcelead.com/faq' target='_blank' style='outline:none; border:none;'>Click Here</a>")
					.toString());

			StringBuilder loginBuilder = new StringBuilder();
			globalVarsMap.put("loginURL", loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append(
					"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
					.toString());

			StringBuilder fbLinkBuilder = new StringBuilder();
			globalVarsMap.put("facebookLink", fbLinkBuilder.append(
					"<a href='http://www.facebook.com' target='_blank' alt='Facebook' title='Facebook' style='margin:5px 5px 0px 0px;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/facebook-35x35.gif' width='30px' height='30px'></a>")
					.toString());

			StringBuilder linkedInLinkBuilder = new StringBuilder();
			globalVarsMap.put("linkedInLink", linkedInLinkBuilder.append(
					"<a href='http://in.linkedin.com' target='_blank' alt='LinkedIn' title='LinkedIn' style='margin:5px 5px 0px 0px;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/linkedin-35x35.gif' width='30px' height='30px'></a>")
					.toString());

			StringBuilder twitterLinkBuilder = new StringBuilder();
			globalVarsMap.put("twitterLink", twitterLinkBuilder.append(
					"<a href='http://twitter.com/login' target='_blank' alt='Twitter' title='Twitter' style='margin:5px 5px 0px 0px;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/twitter-35x35.gif' width='30px' height='30px'></a>")
					.toString());

			StringBuilder googleLinkBuilder = new StringBuilder();
			globalVarsMap.put("googleLink", googleLinkBuilder.append(
					"<a href='http://www.google.com' target='_blank' alt='Google+' title='Google+' style='margin:5px 5px 0px 0px;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/gplus-35x35.gif' width='30px' height='30px'></a>")
					.toString());

		} else if (Constants.VENDOR.equals(orgSpoc.getOrgRelations().getResponderType())) {
			globalVarsMap.put("firstName", emailMessage.getFirstName());
			globalVarsMap.put("lastName", emailMessage.getLastName());
			globalVarsMap.put("clientFirstName", orgSpoc.getVendorUser().getFirstName());
			globalVarsMap.put("clientLastName", orgSpoc.getVendorUser().getLastName());
			globalVarsMap.put("clientOrgName", orgSpoc.getOrgRelations().getResponderLocationId().getOrgLocationName());
			globalVarsMap.put("signature", "Sourcelead Team");

			if (orgSpoc.getOrgRelations().getResponderLocationId().getId().longValue() == orgLocations.getId()
					.longValue()) {
				globalVarsMap.put("invitedOrg", orgSpoc.getOrgRelations().getLocationId().getOrg().getOrgName());
			}
			if (orgSpoc.getOrgRelations().getLocationId().getId().longValue() == orgLocations.getId().longValue()) {
				globalVarsMap.put("invitedOrg",
						orgSpoc.getOrgRelations().getResponderLocationId().getOrg().getOrgName());
			}

			StringBuilder linkBuilder = new StringBuilder();
			globalVarsMap.put("faqLink", linkBuilder.append(
					"<a href='http://support.sourcelead.com/faq' target='_blank' style='outline:none; border:none;'>Click Here</a>")
					.toString());

			StringBuilder fbLinkBuilder = new StringBuilder();
			globalVarsMap.put("facebookLink", fbLinkBuilder.append(
					"<a href='http://www.facebook.com' target='_blank' alt='Facebook' title='Facebook' style='margin:5px 5px 0px 0px;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/facebook-35x35.gif' width='30px' height='30px'></a>")
					.toString());

			StringBuilder linkedInLinkBuilder = new StringBuilder();
			globalVarsMap.put("linkedInLink", linkedInLinkBuilder.append(
					"<a href='http://in.linkedin.com' target='_blank' alt='LinkedIn' title='LinkedIn' style='margin:5px 5px 0px 0px;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/linkedin-35x35.gif' width='30px' height='30px'></a>")
					.toString());

			StringBuilder twitterLinkBuilder = new StringBuilder();
			globalVarsMap.put("twitterLink", twitterLinkBuilder.append(
					"<a href='http://twitter.com/login' target='_blank' alt='Twitter' title='Twitter' style='margin:5px 5px 0px 0px;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/twitter-35x35.gif' width='30px' height='30px'></a>")
					.toString());

			StringBuilder googleLinkBuilder = new StringBuilder();
			globalVarsMap.put("googleLink", googleLinkBuilder.append(
					"<a href='http://www.google.com' target='_blank' alt='Google+' title='Google+' style='margin:5px 5px 0px 0px;'><img src='https://www.mail-signatures.com/articles/wp-content/themes/emailsignatures/images/gplus-35x35.gif' width='30px' height='30px'></a>")
					.toString());

			StringBuilder loginBuilder = new StringBuilder();
			globalVarsMap.put("loginURL", loginBuilder.append("<a href='").append(sourceleadUrl).append("/").append(
					"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
					.toString());
		}

		// changed the logic of adding to emails in InetAddress Array

		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

		// getting the current EmailScenario by EmailScenario unique name and entity
		// type
		EmailScenario emailScenario = null;

		if (Constants.CLIENT.equals(orgSpoc.getOrgRelations().getResponderType())) {
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_CLIENT,
					Constants.ENTITY_TYPE_EMAIL);
		} else if (Constants.VENDOR.equals(orgSpoc.getOrgRelations().getResponderType())) {
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR,
					Constants.ENTITY_TYPE_EMAIL);
		}

		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, orgLocations.getOrg(), orgLocations);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		emailMessage.setOrgLocationId(orgLocations.getId());

		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, orgLocations.getId(), iAdressArray, new ArrayList<EmailAttachment>());

	}

	@Override
	public void sendRejectEmailToClientOrVendor(OrgSpoc orgSpoc, User loginUser, OrgLocations orgLocations)
			throws Exception {

		EmailMessage emailMessage = new EmailMessage();
		if (null != orgSpoc.getOwnerUser()) {
			emailMessage.setFirstName(orgSpoc.getOwnerUser().getFirstName());
			emailMessage.setLastName(orgSpoc.getOwnerUser().getLastName());
		}
		emailMessage.setToEmail(orgSpoc.getOwnerUser().getEmail());
		emailMessage.setFromEmail(defaultFromEmailAddressForProd);
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder emailMsgBuilder = new StringBuilder();
		/*emailMessage.setFromName(emailMsgBuilder.append(loginUser.getFirstName()).append(" ")
				.append(loginUser.getLastName()).toString());*/

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		String UUID = SourceLeadRandomCodeHelper.generateUUID();
		StringBuilder orgSpocUser = new StringBuilder();
		
		if (Constants.CLIENT.equals(orgSpoc.getOrgRelations().getResponderType())) {
			if (null != orgSpoc.getVendorUser().getLastName()) {
				emailMessage.setFromName(orgSpocUser.append(orgSpoc.getVendorUser().getFirstName())
						.append(" ").append(orgSpoc.getVendorUser().getLastName()).toString());
			} else {
				emailMessage.setFromName(
						orgSpocUser.append(orgSpoc.getVendorUser().getFirstName()).toString());
			}
			globalVarsMap.put("firstName", emailMessage.getFirstName());
			globalVarsMap.put("lastName", emailMessage.getLastName());
			globalVarsMap.put("vendorFirstName", orgSpoc.getClientUser().getFirstName());
			globalVarsMap.put("vendorLastName", orgSpoc.getClientUser().getLastName());
			globalVarsMap.put("vendorOrgName", orgSpoc.getOrgRelations().getResponderLocationId().getOrgLocationName());
			globalVarsMap.put("signature", "Sourcelead Team");
		}
		
		else if (Constants.VENDOR.equals(orgSpoc.getOrgRelations().getResponderType())) {
			if (null != orgSpoc.getVendorUser().getLastName()) {
				emailMessage.setFromName(orgSpocUser.append(orgSpoc.getVendorUser().getFirstName())
						.append(" ").append(orgSpoc.getVendorUser().getLastName()).toString());
			} else {
				emailMessage.setFromName(
						orgSpocUser.append(orgSpoc.getVendorUser().getFirstName()).toString());
			}
			globalVarsMap.put("firstName", emailMessage.getFirstName());
			globalVarsMap.put("lastName", emailMessage.getLastName());
			globalVarsMap.put("clientFirstName", orgSpoc.getVendorUser().getFirstName());
			globalVarsMap.put("clientLastName", orgSpoc.getVendorUser().getLastName());
			globalVarsMap.put("clientOrgName", orgSpoc.getOrgRelations().getResponderLocationId().getOrgLocationName());
			globalVarsMap.put("signature", "Sourcelead Team");
		}

		// changed the logic of adding to emails in InetAddress Array
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

		// getting the current EmailScenario by EmailScenario unique name and entity
		// type
		EmailScenario emailScenario = null;

		if (Constants.CLIENT.equals(orgSpoc.getOrgRelations().getResponderType())) {
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_CLIENT,
					Constants.ENTITY_TYPE_EMAIL);
		} else if (Constants.VENDOR.equals(orgSpoc.getOrgRelations().getResponderType())) {
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_VENDOR,
					Constants.ENTITY_TYPE_EMAIL);
		}

		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, orgLocations.getOrg(), orgLocations);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		emailMessage.setOrgLocationId(orgLocations.getId());

		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, orgLocations.getId(), iAdressArray, new ArrayList<EmailAttachment>());
	}

	@Override
	public List<Object> getAllUniversalSearchVendorList(OrgLocations orgLocation, String searchValue, String searchType)
			throws Exception {

		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		String responderType = null;
		if ("Vendor".equalsIgnoreCase(searchType)) {
			responderType = "Client";
		} else if ("Client".equalsIgnoreCase(searchType)) {
			responderType = "Vendor";
		}

		BoolQueryBuilder combinedQueryBuilder = QueryBuilders.boolQuery();

		BoolQueryBuilder initiatorQueryBuilder = QueryBuilders.boolQuery();
		initiatorQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
		initiatorQueryBuilder.must(QueryBuilders.matchQuery("initiatorType", responderType));

		BoolQueryBuilder responderQueryBuilder = QueryBuilders.boolQuery();
		responderQueryBuilder.must(QueryBuilders.matchQuery("responderLocationId", orgLocation.getId()));
		responderQueryBuilder.must(QueryBuilders.matchQuery("responderType", responderType));

		combinedQueryBuilder.should(initiatorQueryBuilder);
		combinedQueryBuilder.should(responderQueryBuilder);

		boolQueryBuilder.must(combinedQueryBuilder);

		Integer maxResults = orgRelationsService.getAllVendorsCountByOrgLocationId(orgLocation.getId(), responderType);
		// All Keywords
		ArrayList<String> anyKeyWordArray = universalSearchHelper.getSearchValuesFromChosenSelectField(searchValue);
		if (anyKeyWordArray != null && !anyKeyWordArray.isEmpty()) {
			BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : anyKeyWordArray) {
				BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
				ArrayList<MatchQueryBuilder> matchQueryBuilderList = universalSearchHelper
						.getMatchQueryBuilderListForClientVendor(keywd);
				if (matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()) {
					for (MatchQueryBuilder matchQueryBuilder : matchQueryBuilderList) {
						anyBoolSubQueryBuilder.should(matchQueryBuilder);
					}
					anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
				}
			}
			boolQueryBuilder.must(anyBoolQueryBuilder);
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		List<Object> searchList = new ArrayList<>();
		SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX,
				Constants.ELK_CLIENTVENDOR_TYPE, 0, maxResults.intValue(), boolQueryBuilder).getHits();
		if (searchHits != null && searchHits.length > 0) {
			ObjectMapper mapper = new ObjectMapper();
			for (SearchHit hit : searchHits) {
				String json = hit.getSourceAsString();
				mapper.setDateFormat(df);
				ClientVendorIndex clientVendor = mapper.readValue(json, ClientVendorIndex.class);

				if (null != clientVendor.getOrgLocationId()
						&& clientVendor.getOrgLocationId().longValue() == orgLocation.getId().longValue()) {

					StringBuilder website = new StringBuilder();
					clientVendor.setWebsite(clientVendor.getResponderWebSite());
					clientVendor.setOrgName(clientVendor.getResponderOrgName());
					clientVendor.setCountry(clientVendor.getResponderCountry());
					clientVendor.setState(clientVendor.getResponderState());
					clientVendor.setCity(clientVendor.getResponderCity());

					if (clientVendor.getInitiatorLevel() != null) {
						clientVendor.setInitiatorLevel(clientVendor.getInitiatorLevel());
					}
				}
				if (null != clientVendor.getResponderLocationId()
						&& clientVendor.getResponderLocationId().longValue() == orgLocation.getId().longValue()) {

					StringBuilder website = new StringBuilder();
					clientVendor.setWebsite(clientVendor.getInitiatorWebSite());
					clientVendor.setOrgName(clientVendor.getInitiatorOrgName());
					clientVendor.setCountry(clientVendor.getInitiatorCountry());
					clientVendor.setState(clientVendor.getInitiatorState());
					clientVendor.setCity(clientVendor.getInitiatorCity());

					if (clientVendor.getResponderLevel() != null) {
						clientVendor.setInitiatorLevel(clientVendor.getResponderLevel());
					}
				}
				if (!responderType.isEmpty() && Constants.VENDOR.equalsIgnoreCase(responderType)) {
					if (!clientVendor.getCreatedBy().isEmpty()) {
						clientVendor.setCreatedBy(clientVendor.getCreatedBy());
					}

				}

				searchList.add(clientVendor);
			}
		}

		return searchList;
	}

	public List<Object> getAllUniversalSearchClientList(OrgLocations orgLocations, String serachValue,
			String searchType) throws Exception {
		List<Object> searchList = new ArrayList<>();
		// Integer maxResults =
		// orgRelationsService.getAllVendorsCountByOrgLocationId(orgLocations.getId(),"Client");
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		BoolQueryBuilder combinedQueryBuilder = QueryBuilders.boolQuery();

		String responderType = null;
		if ("Vendor".equalsIgnoreCase(searchType)) {
			responderType = "Client";
		} else if ("Client".equalsIgnoreCase(searchType)) {
			responderType = "Vendor";
		}

		BoolQueryBuilder initiatorQueryBuilder = QueryBuilders.boolQuery();
		initiatorQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocations.getId()));
		initiatorQueryBuilder.must(QueryBuilders.matchQuery("initiatorType", responderType));

		BoolQueryBuilder responderQueryBuilder = QueryBuilders.boolQuery();
		responderQueryBuilder.must(QueryBuilders.matchQuery("responderLocationId", orgLocations.getId()));
		responderQueryBuilder.must(QueryBuilders.matchQuery("responderType", responderType));

		combinedQueryBuilder.should(initiatorQueryBuilder);
		combinedQueryBuilder.should(responderQueryBuilder);

		boolQueryBuilder.must(combinedQueryBuilder);

		// to get the number of contacts
		Long maxResults = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX,
				Constants.ELK_CLIENTVENDOR_TYPE, 0, 0, boolQueryBuilder).getTotalHits();

		// Any Keyword
		ArrayList<String> anyKeyWordArray = universalSearchHelper.getSearchValuesFromChosenSelectField(serachValue);
		if (anyKeyWordArray != null && !anyKeyWordArray.isEmpty()) {
			BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : anyKeyWordArray) {
				BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
				// ArrayList<MatchQueryBuilder> matchQueryBuilderList =
				// getMatchQueryBuilderList(keywd);
				ArrayList<MatchQueryBuilder> matchQueryBuilderList = universalSearchHelper
						.getMatchQueryBuilderListForClientVendor(keywd);
				if (matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()) {
					for (MatchQueryBuilder matchQueryBuilder : matchQueryBuilderList) {
						anyBoolSubQueryBuilder.should(matchQueryBuilder);
					}
					anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
				}
			}
			boolQueryBuilder.must(anyBoolQueryBuilder);
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_INDEX,
				Constants.ELK_CLIENTVENDOR_TYPE, 0, maxResults.intValue(), boolQueryBuilder).getHits();
		if (searchHits != null && searchHits.length > 0) {
			ObjectMapper mapper = new ObjectMapper();
			for (SearchHit hit : searchHits) {
				String json = hit.getSourceAsString();
				mapper.setDateFormat(df);
				ClientVendorIndex clientVendor = mapper.readValue(json, ClientVendorIndex.class);

				// This is to get the number of Contacts based on Org Relation Id
				BoolQueryBuilder forNumberOfContacts = QueryBuilders.boolQuery();
				forNumberOfContacts.must(QueryBuilders.matchQuery("orgRelationId", clientVendor.getOrgRelationId()));

				Long numberOfContacts = sourceleadELKBaseIndexer
						.searchDocumentWithMaxResults(Constants.ELK_CLIENTVENDOR_CONTACT_INDEX,
								Constants.ELK_CLIENTVENDOR_CONTACT_TYPE, 0, 0, forNumberOfContacts)
						.getTotalHits();
				clientVendor.setNoContacts(numberOfContacts);

				if (null != clientVendor.getOrgLocationId()
						&& clientVendor.getOrgLocationId().longValue() == orgLocations.getId().longValue()) {

					clientVendor.setWebsite(clientVendor.getResponderWebSite());
					clientVendor.setOrgName(clientVendor.getResponderOrgName());
					clientVendor.setCountry(clientVendor.getResponderCountry());
					clientVendor.setState(clientVendor.getResponderState());
					clientVendor.setCity(clientVendor.getResponderCity());

					if (clientVendor.getInitiatorLevel() != null) {
						clientVendor.setInitiatorLevel(clientVendor.getInitiatorLevel());
					}
				}
				if (null != clientVendor.getResponderLocationId()
						&& clientVendor.getResponderLocationId().longValue() == orgLocations.getId().longValue()) {

					clientVendor.setWebsite(clientVendor.getInitiatorWebSite());
					clientVendor.setOrgName(clientVendor.getInitiatorOrgName());
					clientVendor.setCountry(clientVendor.getInitiatorCountry());
					clientVendor.setState(clientVendor.getInitiatorState());
					clientVendor.setCity(clientVendor.getInitiatorCity());

					if (clientVendor.getResponderLevel() != null) {
						clientVendor.setInitiatorLevel(clientVendor.getResponderLevel());
					}
				}
				if (!responderType.isEmpty() && Constants.VENDOR.equalsIgnoreCase(responderType)) {
					if (null != clientVendor.getCreatedBy() && !clientVendor.getCreatedBy().isEmpty()) {
						clientVendor.setCreatedBy(clientVendor.getCreatedBy());
					}

				}

				searchList.add(clientVendor);

			}
		}
		return searchList;
	}

	void updatingClientVendorDetails(List<OrgSpoc> updateOrgSpockDetails) throws Exception {
		if (updateOrgSpockDetails.size() > 0 && null != updateOrgSpockDetails) {
			for (OrgSpoc orgSpock : updateOrgSpockDetails) {
				orgSpocService.addOrgSpoc(orgSpock);
			}
		}
	}

	@Override
	public Integer getReleasedJobsCountByOrgRelationId(Long orgRelationId, OrgLocations orgLocations, String userRole,
			User user) throws Exception {
		 OrgRelations orgRelation =orgRelationsService.getOrRelationsById(orgRelationId);
		 Integer releasedJobsSize =0;
		    List<Requirement> vendorReleasedJobsList = null;
		    if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
		    	if(orgRelation !=null) {
		    		vendorReleasedJobsList = requirementService.getAllReleasedJobsForVendor(orgRelation.getLocationId().getId(),orgRelation.getId(),user,orgLocations);
		    	}
		    }else{
		    	List<Requirement> reqlist = requirementService.getAllReleasedJobsBasedOnUserIdAndLocationId(user,orgLocations);
			    List<Long> parentreqIds = new ArrayList<>();
			    for(Requirement req : reqlist){
			    	parentreqIds.add(req.getId());
			    }
			    if(orgRelation !=null) {
			    vendorReleasedJobsList = requirementService.getAllReleasedJobsBasedOnParetRequirementIdAndVendorLocationId(parentreqIds,orgRelation.getId());
			    }
			    }
			
			if(null != vendorReleasedJobsList ){
				
				releasedJobsSize = (Integer) vendorReleasedJobsList.size();
			}
				 return releasedJobsSize;
	}

}
