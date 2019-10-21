package com.bis.sourcelead.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dao.ExpensesCategoryDao;
import com.bis.sourcelead.dao.LookUpDataDao;
import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.index.helper.ClientVendorIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ClientCustomFieldData;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLicenseSubscription;
import com.bis.sourcelead.entity.OrgLicenseSubscriptionTypes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadRoles;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserCustomFieldData;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ClientCustomFieldDataService;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLicenseSubscriptionService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RoleHierarchyService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadRolesService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserCustomFieldDataService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.VendorOnboardService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.UniversalSearchHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.util.Calendar;

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
	private ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	private ProjectMembersService projectMembersService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private OrgSpocService orgSpocService;

	@Autowired
	private EmailScenarioService emailScenarioService;

	@Autowired
	private EmailScenerioUtil emailScenerioUtil;

	@Autowired
	private SubmissionTemplateService submissionTemplateService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	private RoleHierarchyService roleHierarchyService;

	@Autowired
	private SourceleadActionsService sourceleadActionsService;

	@Autowired
	private SourceleadRolesService sourceleadRolesService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private EmployeesService employeesService;

	@Autowired
	private ClientVendorIndexHelper clientVendorIndexHelper;

	@Autowired
	private SourceleadELKBaseIndexer<ClientVendorIndex> sourceleadELKBaseIndexer;

	@Autowired
	private UniversalSearchHelper universalSearchHelper;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private ClientCustomFieldDataService clientCustomFieldDataService;

	@Autowired
	private OrglocationFormFieldsService orglocationFormFieldsService;
	
	@Autowired
	ReferContactsDao referContactsDao;
	
	@Autowired
	private TabsService tabService;
	
	@Autowired
	private UserDao userDao;
	
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
	
	
	@Value("${vmsUrl}")
    private String vmsUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private CountriesService countriesService;
	
	@Autowired
	private ExpensesCategoryDao expensesCategoryDao;
	
	@Autowired
	private LookUpDataDao lookUpDataDao;
	
	@Autowired
	private OrgLicenseSubscriptionService orgLicenseSubscriptionService;
	
	@Autowired
	private TabsService tabsService;

	@Autowired
	private VendorOnboardService vendorOnboardService;
	
	@Value("${static.path}")
    private String staticPath;
	
	@Autowired
	private UserCustomFieldDataService userCustomFieldDataService;
	
    @Autowired
    UserStatsCacheHelper userStatsCacheHelper;
	 
    @Value("${isCouchbaseEnabled}")
    private String isCouchbaseEnabled;
	

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
		
		
		if (StringUtils.isBlank(clientDTO.getOrgLocationId())) {
			OrgLocations invitedOrgLocation = orgLocationService.getOrgLocationsByOrgLocationName(clientDTO.getOrgName());
			if(invitedOrgLocation != null) {
				clientDTO.setOrgLocationId(invitedOrgLocation.getId().toString());
			}
		}
		
	
		if (StringUtils.isNotBlank(clientDTO.getOrgLocationId())) {
			clientOrgLocations = orgLocationService.getOrgLocations(Long.parseLong(clientDTO.getOrgLocationId()));
			if (StringUtils.isNotBlank(clientDTO.getOrgCountry())) {
				clientOrgLocations.setCountry(clientDTO.getOrgCountry());
			} else if (StringUtils.isNotBlank(clientDTO.getCountry())) {
				clientOrgLocations.setCountry(clientDTO.getCountry());
			}else
			{
				clientOrgLocations.setCountry("");
			}
			if (StringUtils.isNotBlank(clientDTO.getOrgState())) {
				clientOrgLocations.setState(clientDTO.getOrgState());
			} else {
				clientOrgLocations.setState("");
			}
			if (StringUtils.isNotBlank(clientDTO.getOrgCity())) {
				clientOrgLocations.setCity(clientDTO.getOrgCity());
			} else if (StringUtils.isNotBlank(clientDTO.getCity())) {
				clientOrgLocations.setCity(clientDTO.getCity());
			}else
			{
				clientOrgLocations.setCity("");
			}

			if (StringUtils.isNotBlank(clientDTO.getOrgZipcode())) {
				clientOrgLocations.setZipCode(clientDTO.getOrgZipcode());
			} else if (StringUtils.isNotBlank(clientDTO.getZipcode())) {
				clientOrgLocations.setZipCode(clientDTO.getZipcode());
			}else
			{
				clientOrgLocations.setZipCode("");
			}

			if (StringUtils.isNotBlank(clientDTO.getOrgLocation())) {
				clientOrgLocations.setLocation(clientDTO.getOrgLocation());
			} else if (StringUtils.isNotBlank(clientDTO.getLocation())) {
				clientOrgLocations.setLocation(clientDTO.getLocation());
			}else{
				clientOrgLocations.setLocation("");
			}

			if (StringUtils.isNotBlank(clientDTO.getLatitude())) {
				clientOrgLocations.setLatitude(clientDTO.getLatitude());
			}else
			{
				clientOrgLocations.setLatitude("");
			}

			if (StringUtils.isNotBlank(clientDTO.getLongitude())) {
				clientOrgLocations.setLongitude(clientDTO.getLongitude());
			}else{
				clientOrgLocations.setLongitude("");
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
				clientOrgLocations.setOrgLocationName("");
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
			if(StringUtils.isNotBlank(clientDTO.getIsLawFirm()) && clientDTO.getIsLawFirm().equalsIgnoreCase("Y")){
				clientOrgLocations.setIsLawFirm(Constants.YES);	
			}else{
				clientOrgLocations.setIsLawFirm(Constants.NO);	
			}
			if(StringUtils.isNotBlank(clientDTO.getIsBackgroundCheck()) && clientDTO.getIsBackgroundCheck().equalsIgnoreCase("Y")){
				clientOrgLocations.setIsBackgroundCheck(Constants.YES);	
			}else{
				clientOrgLocations.setIsBackgroundCheck(Constants.NO);	
			}
			if(StringUtils.isNotBlank(clientDTO.getIsAccountingFirm()) && clientDTO.getIsAccountingFirm().equalsIgnoreCase("Y")){
					clientOrgLocations.setIsAccountingFirm(Constants.YES);	
				}else{
					clientOrgLocations.setIsAccountingFirm(Constants.NO);	
				}
			clientOrgLocations.setLoginSecurity(Constants.NO);
			clientOrgLocations.setIsPrimary(Constants.YES);
			clientOrgLocations.setIsCompanyOnboarded(Constants.NO);
			clientOrgLocations.setCoolingOffPeriod("3 Months");
			clientOrgLocations.setRegistrationStatus("Registered");
			clientOrgLocations.setProfilePercentage(Constants.PROFILE_PERCENTAGE);
			orgLocationsList.add(clientOrgLocations);
			org.setOrgLocationsList(orgLocationsList);
			Org savedClient = orgService.addOrganization(org);
			
			if(clientDTO.getCustomFormData() !=null) {
				for( CustomFormFieldDTO customFields:clientDTO.getCustomFormData()) {
					if(customFields!= null && customFields.getCustomFormFieldId() > 0) {
						ClientCustomFieldData clientCustomFields = null;
						if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
							clientCustomFields = clientCustomFieldDataService.getClientCustomFieldDataById(customFields.getEntityId());
						}else {
							clientCustomFields = new ClientCustomFieldData();
						}
					clientCustomFields.setOrg(savedClient);
					OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
					clientCustomFields.setOrglocationsFormFields(orgFormField);
					if(customFields.getCustomFormFieldData() == null || StringUtils.isEmpty(customFields.getCustomFormFieldData())) {
						clientCustomFields.setCustomFormFieldData("");
					}else {
						clientCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
					}
					clientCustomFieldDataService.saveClientCustomFieldData(clientCustomFields);
					}
					
				}	
			}
			
			List<LookUpData> lookUpExpCatList = lookUpDataDao.getLookUpDataByCategory(Constants.EXPENSESCATEGORY);
		    for(LookUpData lookUpData:lookUpExpCatList){
			    ExpensesCategory expensesCategory = new ExpensesCategory();
			    expensesCategory.setExpensesName(lookUpData.getName());
			    expensesCategory.setAmountType("Flat");
			    expensesCategory.setOrgLocationId(clientOrgLocations);
			    expensesCategory.setStatus(Constants.MEMBER_ACTIVE);
				expensesCategory.setCreatedBy(user.getUserCode());
				expensesCategory.setCreatedDate(commonUtil.currentDate());
				expensesCategory.setUpdatedBy(user.getUserCode());
				expensesCategory.setUpdatedDate(commonUtil.currentDate());
				expensesCategoryDao.storeExpenseCategory(expensesCategory); 
		    }
			saveOrgRoleHierarchy(clientOrgLocations, user);
			saveOrgBasedRoles(org, clientOrgLocations, user,responderType);
			newlyRegistred = "Y";
		} else {
			
			if (StringUtils.isNotBlank(clientDTO.getBillingAddress())) {
				clientOrgLocations.setBillingAddress(clientDTO.getBillingAddress());
			}
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
				org.setClientAddress("");
			}
			if (StringUtils.isNotBlank(clientDTO.getBillingAddress())) {
				org.setClientBillingAddress(clientDTO.getBillingAddress());
			}else{
				org.setClientBillingAddress("");
			}
			if (StringUtils.isNotBlank(clientDTO.getPaymentTerms())) {
				org.setPaymentTerm(clientDTO.getPaymentTerms());
			}else{
				org.setPaymentTerm("");
			}
			org.setId(org.getId());
			org.setOrgLocationsList(orgLocationsList);
			Org savedClient =  orgService.addOrganization(org);
			
			if(clientDTO.getCustomFormData() !=null) {
				for( CustomFormFieldDTO customFields:clientDTO.getCustomFormData()) {
					if(customFields!= null && customFields.getCustomFormFieldId() > 0) {
						ClientCustomFieldData clientCustomFields = null;
						if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
							clientCustomFields = clientCustomFieldDataService.getClientCustomFieldDataById(customFields.getEntityId());
						}else {
							clientCustomFields = new ClientCustomFieldData();
						}
					clientCustomFields.setOrg(savedClient);
					OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
					clientCustomFields.setOrglocationsFormFields(orgFormField);
					if(customFields.getCustomFormFieldData() == null || StringUtils.isEmpty(customFields.getCustomFormFieldData())) {
						clientCustomFields.setCustomFormFieldData("");
					}else {
						clientCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
					}
					clientCustomFieldDataService.saveClientCustomFieldData(clientCustomFields);
					}
					
				}	
			}
			
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
					if(StringUtils.isNotBlank(clientDTO.getInvoiceOrBillSent())){
					orgSpoc.setInvoiceEmailStatus(clientDTO.getInvoiceOrBillSent());
					}else{
					orgSpoc.setInvoiceEmailStatus(Constants.NO);	
					}
					if(StringUtils.isNotBlank(clientDTO.getApOrArSent())){
					orgSpoc.setApEmailStatus(clientDTO.getApOrArSent());
					}else{
					orgSpoc.setApEmailStatus(Constants.NO);
					}
				} else if (responderType.equalsIgnoreCase("Vendor")) {
					orgSpoc.setClientLocationId(orgLocations);
					orgSpoc.setVendorLocationId(clientOrgLocations);
					orgSpoc.setClientUser(ownerUser);
					orgSpoc.setVendorUser(spocUser);
					orgSpoc.setOwnerUser(ownerUser);
					if(StringUtils.isNotBlank(clientDTO.getInvoiceOrBillSent())){
					orgSpoc.setBillingEmailStatus(clientDTO.getInvoiceOrBillSent());
					}else{
					orgSpoc.setBillingEmailStatus(Constants.NO);	
					}
					if(StringUtils.isNotBlank(clientDTO.getApOrArSent())){
					orgSpoc.setArEmailStatus(clientDTO.getApOrArSent());
					}else{
					orgSpoc.setArEmailStatus(Constants.NO);
					}
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

		
		if ("BulkVendorSaving".equalsIgnoreCase(clientDTO.getClientSaveFromType())) {
			clientOrgLocations.setVendorOrgRelationId(orgSpoc.getOrgRelations().getId());
			clientOrgLocations.setVendorSpocId(spocUser.getId());
			User bulkUser = userService.getUserById(spocUser.getId());
			VendorOnboarding vendorOnboarding = vendorOnboardService.saveVendorOnboarding(clientOrgLocations, orgLocations, user);
			vendorOnboardService.saveVendorOnboardForVendorBulkImports(vendorOnboarding.getId(),user,bulkUser,orgLocations);
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
			submissionTemplate.setIsDefault(Constants.YES);
			submissionTemplate.setTemplateType("Submission");
			submissionTemplate.setCreatedBy(user.getUserCode());
			submissionTemplate.setUpdatedBy(user.getUserCode());
			submissionTemplate.setCreatedDate(new Date());
			submissionTemplate.setUpdatedDate(new Date());
			submissionTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			submissionTemplateService.addSubmissionTemplate(submissionTemplate);

			// store default interview feedback template in guest
			sourceLeadDefaultTemplate = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(1L,
					Constants.DEFAULT_INTERVIEW_FEEDBACK_TEMAPLATE);
			StringBuilder templateStringBuilder = new StringBuilder();
			submissionTemplate = new SubmissionTemplate();
			submissionTemplate.setTemplateName(
					templateStringBuilder.append(org.getOrgName()).append(" ").append("Interview Feedback Template").toString());
			submissionTemplate.setDescription(
					templateStringBuilder.append(org.getOrgName()).append(" ").append("Interview Feedback Template").toString());
			submissionTemplate.setOrgLocationId(clientOrgLocations);
			submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
			submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
			submissionTemplate.setIsDefault(Constants.YES);
			submissionTemplate.setUpdatedBy(user.getUserCode());
			submissionTemplate.setTemplateType("Feedback");
			submissionTemplate.setUpdatedDate(new Date());
			submissionTemplate.setCreatedBy(user.getUserCode());
			submissionTemplate.setCreatedDate(new Date());
			submissionTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
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
			submissionTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			submissionTemplateService.addSubmissionTemplate(submissionTemplate);

			// store default interview feedback template in client & vedor
			sourceLeadDefaultTemplate = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(1L,
					Constants.DEFAULT_INTERVIEW_FEEDBACK_TEMAPLATE);
			StringBuilder templateStringBuilder = new StringBuilder();
			submissionTemplate = new SubmissionTemplate();
			submissionTemplate.setTemplateName(
					templateStringBuilder.append(org.getOrgName()).append(" ").append("Interview Feedback Template").toString());
			submissionTemplate.setDescription(
					templateStringBuilder.append(org.getOrgName()).append(" ").append("Interview Feedback Template").toString());
			submissionTemplate.setIsDefault("Y");
			submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
			submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
			submissionTemplate.setUpdatedBy(user.getUserCode());
			submissionTemplate.setTemplateType("Feedback");
			submissionTemplate.setUpdatedDate(new Date());
			submissionTemplate.setCreatedBy(user.getUserCode());
			submissionTemplate.setCreatedDate(new Date());
			submissionTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			submissionTemplateService.addSubmissionTemplate(submissionTemplate);

		}
		
		if(Constants.GUEST.equalsIgnoreCase(org.getOrgType())) {
			commonUtil.saveDefaultContractTemplate(org,clientOrgLocations,spocUser," Default Client MSA Template",Constants.CLIENT_MSA);
			commonUtil.saveDefaultContractTemplate(org,clientOrgLocations,spocUser," Default Vendor MSA Template",Constants.VENDOR_MSA);
			commonUtil.saveDefaultContractTemplate(org,clientOrgLocations,spocUser," Default Client SOW Template",Constants.Client_SOW);
			commonUtil.saveDefaultContractTemplate(org,clientOrgLocations,spocUser," Default Vendor SOW Template",Constants.Vendor_SOW);
			commonUtil.saveDefaultContractTemplate(org,clientOrgLocations,spocUser," Default NDA Template",Constants.NDA);
			commonUtil.saveDefaultEmployeeVerificationTemplate(org, clientOrgLocations, spocUser, "Default Employee Verification Template");
         	commonUtil.saveDefaultGenaeralShift(clientOrgLocations, spocUser, Constants.GENERAL_SHIFT);
         	commonUtil.saveDefaultPayslipTemplate(clientOrgLocations, spocUser);

		}
		
		if(Constants.GUEST.equalsIgnoreCase(org.getOrgType())) {
			if(clientOrgLocations.getGeneratedUserId()== null || StringUtils.isBlank(clientOrgLocations.getGeneratedUserId())) {
			Boolean validateShortCode = true;
	 		while(validateShortCode){
	 			String orgShortCode = RandomStringUtils.randomAlphabetic(4);
	 			 boolean validateGeneratedUserId = orgLocationService.validateOrgShortCode(orgShortCode);
	 			 if(validateGeneratedUserId == true){
	 					validateShortCode = true;
	 				}else {
	 					validateShortCode = false;
	 					clientOrgLocations.setGeneratedUserId(orgShortCode.toUpperCase());
	 					orgLocationService.updateOrgLocations(clientOrgLocations);
	 				}
	 		 }
		   }
		}
		
		if(newlyRegistred.equalsIgnoreCase(Constants.YES)){
			saveOrgLocationDefaultProject(clientOrgLocations, spocUser,responderType);
			commonUtil.storeVendorOnboardDefaultTemplate(spocUser, clientOrgLocations);
//			commonUtil.storeDefaultPayheadsWithOrgLocation(user, orgLocations);
		}

		for (OrgRelations orgRelation : orgRelationsList) {
			clientOrgLocations.setVendorOrgRelationId(orgRelation.getId());
			saveOrUpdateClientVendorElkDocument(org, clientOrgLocations, responderType, orgSpoc, orgRelation,
					submissionTemplate, user, orgLocations);

		}
		clientOrgLocations.setVendorSpocId(spocUser.getId());
		 ReferContacts referContacts=referContactsDao.getReferContactsByEmails(user.getId(), clientDTO.getEmail());
		
			if(referContacts !=null){
				if(clientDTO.getFirstName() != null){
					referContacts.setFirstName(clientDTO.getFirstName());
				}
				if(clientDTO.getLastName() != null){
					referContacts.setLastName(clientDTO.getLastName());
				}
				if(clientDTO.getPhoneNumber() != null){
					referContacts.setContactUserPhone(clientDTO.getPhoneNumber());
				}
				referContacts.setUpdatedDate(new Date());
				referContacts.setUpdatedBy(user.getUserCode());
				if(clientDTO.getResponderType() != null){
					if(clientDTO.getResponderType().equalsIgnoreCase("vendor")){
						referContacts.setVendorType(1);
					}else{
						referContacts.setClientType(1);
					}
				}else{
					if(responderType !=null){
						if(responderType.equalsIgnoreCase("vendor")){
							referContacts.setVendorType(1);
						}else{
							referContacts.setClientType(1);
						}
					}
				}
			}else{
				 referContacts = new ReferContacts();
				
				if(clientDTO.getEmail() != null){
					referContacts.setContactUserEmail(clientDTO.getEmail());
				}
				if(clientDTO.getOrgName() != null){
					referContacts.setCompanyName(clientDTO.getOrgName());
				}
				if(clientDTO.getFirstName() != null){
					referContacts.setFirstName(clientDTO.getFirstName());
				}
				if(clientDTO.getLastName() != null){
					referContacts.setLastName(clientDTO.getLastName());
				}
				if(clientDTO.getPhoneNumber() != null){
					referContacts.setContactUserPhone(clientDTO.getPhoneNumber());
				}
				if(clientDTO.getOrgName() != null){
					referContacts.setCompanyName(clientDTO.getOrgName());
				}
				if(StringUtils.isNotBlank(clientDTO.getEmail())){
					referContacts.setContactUserEmail(clientDTO.getEmail());
					User contactUser = userDao.getUserByEmail(clientDTO.getEmail());
					if(null != contactUser){
						referContacts.setContactUser(contactUser);
					}
				}
				referContacts.setReferFromUser(user);
				String contactUUID = SourceLeadRandomCodeHelper.generateUUID();
				referContacts.setContactUserUUID(contactUUID);
				referContacts.setCreatedDate(new Date());
				if(clientDTO.getResponderType() != null){
					if(clientDTO.getResponderType().equalsIgnoreCase("vendor")){
						referContacts.setVendorType(1);
					}else{
						referContacts.setClientType(1);
					}
				}else{
					if(responderType !=null){
						if(responderType.equalsIgnoreCase("vendor")){
							referContacts.setVendorType(1);
						}else{
							referContacts.setClientType(1);
						}
					}
				}
				
				referContacts.setCreatedBy(user.getUserCode());
			}
		
		referContactsDao.addReferContacts(referContacts);
		
		if(isCouchbaseEnabled.equalsIgnoreCase("true")) {
			OrgLocations registeredOrgLocation = orgLocationService.getOrgLocations(clientOrgLocations.getId());
			userStatsCacheHelper.saveOrgLocationDetailsToCouchbase(registeredOrgLocation);
			UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(spocUser.getId(), clientOrgLocations.getId());
			userStatsCacheHelper.saveUserProfileDetailsToCouchbase(userRole);
		}
		
		return clientOrgLocations;
	}
	
	
	
	 public Projects  saveOrgLocationDefaultProject(OrgLocations orgLocations,User approverUser,String responderType) throws Exception {
		 	
	    	Projects projects = new Projects();
	    	StringBuilder defaultProjectName = new  StringBuilder();
	    	projects.setProjectName(defaultProjectName.append(orgLocations.getOrgLocationName().trim()).append(" ").append("Default Project").toString());
	    	StringBuilder defaultProjectNameDesc = new  StringBuilder();

	    	projects.setDescription(defaultProjectNameDesc.append(orgLocations.getOrgLocationName().trim()).append(" ").append("Default Project").toString());
	    	projects.setStartDate(new Date());

	    	Calendar cal = Calendar.getInstance();
	    	cal.add(Calendar.MONTH, -2);
	    	Date startDate = cal.getTime();
	    	Date projStartDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(),startDate);
	    	
	    	projects.setStartDate(projStartDate);
	    	
	    	if (responderType.equalsIgnoreCase("Client")) {
	    		projects.setIsTimesheetAttachmentRequired(1);
	    	}

	    	projects.setInvoiceFrequency(Constants.WEEKLY);
	    	projects.setTimesheetFrequency(Constants.MONTHLY);
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
			projects.setIsProjectForTimeSheets(Constants.YES);
			projects.setIsProjectForExpenses(Constants.YES);
			projects.setProjectApprovalType(Constants.PARALLEL);
			projects.setExpenseApprovalType(Constants.PARALLEL);
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
			projectClients.setOrgId(orglocations.getOrg());
			projectClients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			projectClients.setStatus(1);
			projectClients.setLevel(1);
			projectClients.setIsEndClient(Constants.YES);
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
			pah.setCreatedDate(new Date());
			pah.setCreatedBy(approverUser.getUserCode());
			pah.setLevel(1);
			pah.setStatus("1");
			projectApproverHierarchyList.add(pah);
			projectClients.setProjectApproverHierarchyList(projectApproverHierarchyList);
			
			//prepare ProjectApproverHierarchy
			Set<ExpenseProjectApproverHierarchy> projectExpenseApproverHierarchyList = new HashSet<ExpenseProjectApproverHierarchy>();
			ExpenseProjectApproverHierarchy eah = new ExpenseProjectApproverHierarchy();
			eah.setProjectId(projects);
			eah.setProjectClientsId(projectClients);
			eah.setApproverUserId(approverUser);
			eah.setLevel(1);
			eah.setStatus("1");
			eah.setCreatedDate(new Date());
			eah.setCreatedBy(approverUser.getUserCode());
			projectExpenseApproverHierarchyList.add(eah);
			projectClients.setExpenseProjectApproverHierarchyList(projectExpenseApproverHierarchyList);
			
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

	public void saveOrgBasedRoles(Org org, OrgLocations orgLocations, User user,String responderType) throws Exception {
		List<SourceleadRoles> rolesList = sourceleadRolesService.getSourceleadRoles();
		List<Long> soureleadActionIdList = new ArrayList<>();
		String[] actionsIds = null;
		SourceleadRoles sourceleadRoles = null;
		if(responderType.equalsIgnoreCase("Vendor")) {
			 sourceleadRoles = sourceleadRolesService.getSourceleadRolesByRoleName(Constants.ROLE_GUEST_VENDOR);
			 actionsIds = sourceleadRoles.getActionId().split(",");
			 for(int i=0 ; i < actionsIds.length; i++) {
				 soureleadActionIdList.add(Long.parseLong(actionsIds[i]));
			 }
		}else if(responderType.equalsIgnoreCase("Client")) {
			sourceleadRoles = sourceleadRolesService.getSourceleadRolesByRoleName(Constants.ROLE_GUEST_CLIENT);
			 actionsIds = sourceleadRoles.getActionId().split(",");
			 for(int i=0 ; i < actionsIds.length; i++) {
				 soureleadActionIdList.add(Long.parseLong(actionsIds[i]));
			 }
		}
		
		
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
					/*for (String s : roles.getActionId().split(",")) {
						roleActonsList.add(Long.parseLong(s));
					}*/
					for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
		    			 roleActonsList.add(Long.parseLong(s));
		        	 }
				}

				for (Long actionId : soureleadActionIdList) {
					if (roleActonsList.contains(actionId)) {
						strbul.append(actionId).append(",");
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

		saveLincenseSubscription(org,orgLocations,soureleadActionIdList);
	}
	
	
	public void saveLincenseSubscription(Org org,OrgLocations orgLocations,List<Long> soureleadActionIdList) throws Exception {
		OrgLicenseSubscription subscription = new OrgLicenseSubscription();
		subscription.setOrg(org);
		OrgLicenseSubscriptionTypes subscriptionTypeLookUpData = new OrgLicenseSubscriptionTypes();
		subscriptionTypeLookUpData.setId(6L);
		subscription.setSubscriptionType(subscriptionTypeLookUpData);
		subscription.setCountry(orgLocations.getCountry());
		subscription.setTimezone(orgLocations.getTimezone());
		
		OrgLicenseSubscriptionTypes userData = new OrgLicenseSubscriptionTypes();
		userData.setId(8L);
		subscription.setUserSubscriptionType(userData);
		
		OrgLicenseSubscriptionTypes storageLookUpData = new OrgLicenseSubscriptionTypes();
		storageLookUpData.setId(10L);
		subscription.setStorageSubscriptionType(storageLookUpData);
		subscription.setFromDate(new Date());
		
		Calendar toDate = Calendar.getInstance();
		toDate.setTime(new Date());
		toDate.add(Calendar.MONTH, +1); 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = dateFormat.parse(dateFormat.format(toDate.getTime()));
		subscription.setToDate(endDate);
		subscription.setCurrency("USD");
		subscription.setInvoiceAmount(0d);
		
		StringBuilder tabIds = new StringBuilder();
		List<Tabs> tabsList =  tabsService.getTabListByActionsIds(soureleadActionIdList);
		Set<Long> tabIdsList = new HashSet<>();
		if(tabsList != null && !tabsList.isEmpty()) {
			for(Tabs tabs : tabsList) {
				if(tabs.getTabs() != null) {
					tabIdsList.add(tabs.getTabs().getId());
				}else {
					tabIdsList.add(tabs.getId());
				}
			}
		}
		
		for(Long tab :tabIdsList) {
			tabIds = tabIds.append(tab).append(",");
		}
	
		String enrolledTabs = tabIds.toString();
		enrolledTabs = enrolledTabs.substring(0, enrolledTabs.length()-1);
		subscription.setSubscriptionTabs(enrolledTabs);
		subscription.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		orgLicenseSubscriptionService.saveOrgLicenseSubscription(subscription);
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
				}if(StringUtils.isNotBlank(clientDTO.getGenerateInvoice())){
				orgRelation.setGenerateInvoice(clientDTO.getGenerateInvoice());
				}
			}
			if (orgRelation.getResponderLocationId().getId().longValue() == loginLocationId.longValue()) {
				orgLocations = orgRelation.getLocationId();
				if (StringUtils.isNotBlank(clientDTO.getLevel())) {
					orgRelation.setResponderLevel(clientDTO.getLevel());
				}
				if(StringUtils.isNotBlank(clientDTO.getGenerateInvoice())){
					orgRelation.setGenerateInvoice(clientDTO.getGenerateInvoice());
					}
			}
			
			if (StringUtils.isNotBlank(clientDTO.getPaymentTerms())) {
				orgRelation.setPaymentTerm(clientDTO.getPaymentTerms());
			}else{
				orgRelation.setPaymentTerm(null);
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
				}else {
					org.setClientBillingAddress("");
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
			if(StringUtils.isNotBlank(clientDTO.getCountry())){
				orgLocations.setCountry(clientDTO.getCountry());
			}else{
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
			
			if (StringUtils.isNotBlank(clientDTO.getAddress())) {
				orgLocations.setOrgLocationAddress(clientDTO.getAddress());
			}
			if (StringUtils.isNotBlank(clientDTO.getBillingAddress())) {
				orgLocations.setBillingAddress(clientDTO.getBillingAddress());
			} else {

				orgLocations.setBillingAddress("");
			}
			orgLocationForELk = orgLocationService.updateOrgLocations(orgLocations);
			
			if(clientDTO.getCustomFormData() !=null) {
				for( CustomFormFieldDTO customFields:clientDTO.getCustomFormData()) {
					if(customFields!= null && customFields.getCustomFormFieldId() > 0) {
						ClientCustomFieldData clientCustomFields = null;
						if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
							clientCustomFields = clientCustomFieldDataService.getClientCustomFieldDataById(customFields.getEntityId());
						}else {
							clientCustomFields = new ClientCustomFieldData();
						}
					clientCustomFields.setOrg(orgLocationForELk.getOrg());
					OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
					clientCustomFields.setOrglocationsFormFields(orgFormField);
					if(customFields.getCustomFormFieldData() == null || StringUtils.isEmpty(customFields.getCustomFormFieldData())) {
						clientCustomFields.setCustomFormFieldData("");
					}else {
						clientCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
					}
					clientCustomFieldDataService.saveClientCustomFieldData(clientCustomFields);
					}
					
				}	
			}
			
			
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
				if(StringUtils.isNotBlank(clientDTO.getGenerateBill())){
				orgRelation.setGenerateBill(clientDTO.getGenerateBill());
				}
			}
			if (orgRelation.getResponderLocationId().getId().longValue() == loginOrgId.longValue()) {
				orgLocations = orgRelation.getLocationId();
				if (StringUtils.isNotBlank(clientDTO.getLevel())) {
					orgRelation.setResponderLevel(clientDTO.getLevel());
				}
				if(StringUtils.isNotBlank(clientDTO.getGenerateBill())){
					orgRelation.setGenerateBill(clientDTO.getGenerateBill());
					}
			}
			if (StringUtils.isNotBlank(clientDTO.getIsLawFirm()) && clientDTO.getIsLawFirm().equalsIgnoreCase("Y")) {
					orgRelation.setIsLawFirm(Constants.YES);
			} else {
				orgRelation.setIsLawFirm(Constants.NO);
			}
			if (StringUtils.isNotBlank(clientDTO.getIsAccountingFirm()) && clientDTO.getIsAccountingFirm().equalsIgnoreCase("Y")) {
					orgRelation.setIsAccountingFirm(Constants.YES);
			} else {
				orgRelation.setIsAccountingFirm(Constants.NO);
			}
			
			if (StringUtils.isNotBlank(clientDTO.getPaymentTerms())) {
				orgRelation.setPaymentTerm(clientDTO.getPaymentTerms());
			}else{
				orgRelation.setPaymentTerm(null);
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
			
			 if(StringUtils.isNotBlank(clientDTO.getAddress())){
				 orgLocations.setOrgLocationAddress(clientDTO.getAddress());
			 }
			 
			 if(StringUtils.isNotBlank(clientDTO.getBillingAddress())){
				 orgLocations.setBillingAddress(clientDTO.getBillingAddress());
			 }else{
				 orgLocations.setBillingAddress("");
			 }
			 
			if (StringUtils.isNotBlank(clientDTO.getIsLawFirm())) {
				if (clientDTO.getIsLawFirm().equalsIgnoreCase("Y")) {
					orgLocations.setIsLawFirm(Constants.YES);
				}
			} else {
				orgLocations.setIsLawFirm(Constants.NO);
			}
			if (StringUtils.isNotBlank(clientDTO.getIsBackgroundCheck())) {
				if (clientDTO.getIsBackgroundCheck().equalsIgnoreCase("Y")) {
					orgLocations.setIsBackgroundCheck(Constants.YES);
				}
			} else {
				orgLocations.setIsBackgroundCheck(Constants.NO);
			}
			if (StringUtils.isNotBlank(clientDTO.getIsAccountingFirm())) {
				if (clientDTO.getIsAccountingFirm().equalsIgnoreCase("Y")) {
					orgLocations.setIsAccountingFirm(Constants.YES);
				}
			} else {
				orgLocations.setIsAccountingFirm(Constants.NO);
			}
			 
			/*
			 * if(jsonObj.getString(emailId) !=null &&
			 * !jsonObj.getString(emailId).isEmpty()){
			 * orgLocations.setEmail(jsonObj.getString(emailId)); }else{
			 * orgLocations.setEmail(""); }
			 */
			orgLocationsForElk = orgLocationService.updateOrgLocations(orgLocations);
			
			if(clientDTO.getCustomFormData() !=null) {
				for( CustomFormFieldDTO customFields:clientDTO.getCustomFormData()) {
					if(customFields!= null && customFields.getCustomFormFieldId() > 0) {
						ClientCustomFieldData clientCustomFields = null;
						if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
							clientCustomFields = clientCustomFieldDataService.getClientCustomFieldDataById(customFields.getEntityId());
						}else {
							clientCustomFields = new ClientCustomFieldData();
						}
					clientCustomFields.setOrg(orgLocationsForElk.getOrg());
					OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
					clientCustomFields.setOrglocationsFormFields(orgFormField);
					if(customFields.getCustomFormFieldData() == null || StringUtils.isEmpty(customFields.getCustomFormFieldData())) {
						clientCustomFields.setCustomFormFieldData("");
					}else {
						clientCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
					}
					clientCustomFieldDataService.saveClientCustomFieldData(clientCustomFields);
					}
					
				}	
			}
			
			if(StringUtils.isNotBlank(orgRelation.getIsLawFirm()) && orgRelation.getIsLawFirm().equalsIgnoreCase(Constants.YES)){
				OrgLicenseSubscription subscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgIdAndCountry(orgLocations.getOrg().getId(), orgLocations.getCountry());
				String[] subscriptionTabs = subscription.getSubscriptionTabs().split(",");
				List<Long> subscriptionTabIdsList = new ArrayList<>();
				for(int i=0; i<subscriptionTabs.length;i ++){
					subscriptionTabIdsList.add(Long.valueOf(subscriptionTabs[i]));
				}
				
				if(!subscriptionTabIdsList.contains(Long.valueOf(Constants.TAB_IMMIGRATION))){
					List<Long> tabsIds = new ArrayList<>();
					List<Tabs> childTabsList = tabService.getClildTabsListByTabId(Long.valueOf(Constants.TAB_IMMIGRATION));
					if(childTabsList.size() > 0) {
						for(Tabs childTabs : childTabsList) {
							tabsIds.add(childTabs.getId());
						}
					}
					
					List<Long> sourceLeadActionsIdsList =  sourceleadActionsService.getAllSourceleadActionsIdsByTabsList(tabsIds);
					StringBuilder immigrationTabIds  = new StringBuilder();
					for(Long tabId : sourceLeadActionsIdsList){
						immigrationTabIds.append(tabId).append(",");
					}
					
					String tabIds = immigrationTabIds.toString();
					tabIds = tabIds.substring(0, tabIds.length()-1);
					
					Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
					String adminIds = adminrole.getActionId()+","+tabIds;
					adminrole.setActionId(adminIds);
					rolesService.storeRoles(adminrole);
					
					Roles managerrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER, orgLocations.getId());
					String managerIds = managerrole.getActionId()+","+tabIds;
					managerrole.setActionId(managerIds);
					rolesService.storeRoles(managerrole);
					
					String subscriptionRegisteredTabs = subscription.getSubscriptionTabs();
					subscriptionRegisteredTabs = subscriptionRegisteredTabs+","+Constants.TAB_IMMIGRATION;
					subscription.setSubscriptionTabs(subscriptionRegisteredTabs);
					orgLicenseSubscriptionService.saveOrgLicenseSubscription(subscription);
				}
			}
			// Below coded added for Background check vendor tags to be enabled
			if(StringUtils.isNotBlank(orgRelation.getIsBackgroundCheck()) && orgRelation.getIsBackgroundCheck().equalsIgnoreCase(Constants.YES)){
				OrgLicenseSubscription subscription = orgLicenseSubscriptionService.getOrgLicenseSubscriptionByOrgIdAndCountry(orgLocations.getOrg().getId(), orgLocations.getCountry());
				String[] subscriptionTabs = subscription.getSubscriptionTabs().split(",");
				List<Long> subscriptionTabIdsList = new ArrayList<>();
				for(int i=0; i<subscriptionTabs.length;i ++){
					subscriptionTabIdsList.add(Long.valueOf(subscriptionTabs[i]));
				}
				
				if(!subscriptionTabIdsList.contains(Long.valueOf(Constants.TAB_BACKGROUND_CHECK))){
					List<Long> tabsIds = new ArrayList<>();
					List<Tabs> childTabsList = tabService.getClildTabsListByTabId(Long.valueOf(Constants.TAB_BACKGROUND_CHECK));
					if(childTabsList.size() > 0) {
						for(Tabs childTabs : childTabsList) {
							tabsIds.add(childTabs.getId());
						}
					}
					
					List<Long> sourceLeadActionsIdsList =  sourceleadActionsService.getAllSourceleadActionsIdsByTabsList(tabsIds);
					StringBuilder immigrationTabIds  = new StringBuilder();
					for(Long tabId : sourceLeadActionsIdsList){
						immigrationTabIds.append(tabId).append(",");
					}
					
					String tabIds = immigrationTabIds.toString();
					tabIds = tabIds.substring(0, tabIds.length()-1);
					
					Roles adminrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_ADMIN, orgLocations.getId());
					String adminIds = adminrole.getActionId()+","+tabIds;
					adminrole.setActionId(adminIds);
					rolesService.storeRoles(adminrole);
					
					Roles managerrole = rolesService.getRolesByRoleNameAndOrgLocationId(Constants.ROLE_MANAGER, orgLocations.getId());
					String managerIds = managerrole.getActionId()+","+tabIds;
					managerrole.setActionId(managerIds);
					rolesService.storeRoles(managerrole);
					
					String subscriptionRegisteredTabs = subscription.getSubscriptionTabs();
					subscriptionRegisteredTabs = subscriptionRegisteredTabs+","+Constants.TAB_BACKGROUND_CHECK;
					subscription.setSubscriptionTabs(subscriptionRegisteredTabs);
					orgLicenseSubscriptionService.saveOrgLicenseSubscription(subscription);
				}
			}
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

		/*String vendorContactFirstName = "vendorContactFirstName";
		String vendorContactLastName = "vendorContactLastName";
		String mobilePhone = "mobilePhone";
		String spocStatus = "spocStatus";
		String emailId = "email";
		String owner = "owner";*/

		if (null != orgSpoc) {
			User editUser = null;
			if (StringUtils.isNotBlank(clientDTO.getExistingSpocUserId())) {
				User existingUser = userService.getUserById(Long.parseLong(clientDTO.getExistingSpocUserId()));
				UserRole userRole = null;
				Candidate candidate = null;
				Employees employee = null;
				
				if (null != type && !"".equalsIgnoreCase(type) && "Client".equalsIgnoreCase(type)) {
					userRole = userRoleService.getUserRoleByUserIdAndLocationId(existingUser.getId(),
							orgSpoc.getClientLocationId().getId());
					candidate = candidateService.getCandidateByConsultantUserId(existingUser.getId(), orgSpoc.getClientLocationId().getId());
					employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(existingUser.getId(), orgSpoc.getClientLocationId().getId());
				}else {
					userRole = userRoleService.getUserRoleByUserIdAndLocationId(existingUser.getId(),
							orgSpoc.getVendorLocationId().getId());
					candidate = candidateService.getCandidateByConsultantUserId(existingUser.getId(), orgSpoc.getVendorLocationId().getId());
					employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(existingUser.getId(), orgSpoc.getVendorLocationId().getId());
				}

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
				
				
				
				if(candidate != null) {
					if (StringUtils.isNotBlank(clientDTO.getFirstName())) {
						candidate.setFirstName(clientDTO.getFirstName());
					} else {
						candidate.setFirstName("");
					}
					if (StringUtils.isNotBlank(clientDTO.getLastName())){
						candidate.setLastName(clientDTO.getLastName());
					} else {
						candidate.setLastName("");
					}
					
					Candidate savedCandidate = candidateService.addCandidate(candidate);
					candidateService.getCandidatePercentage(savedCandidate, orgLocation);
					candidateService.saveOrUpdateCandidateElkDocument(savedCandidate);
				}
				
				
				// Update the user details for Elk search
				commonUtil.commonUserUpdate(editUser);
				userService.saveOrUpdateUserElkDocument(userRole, employee, editUser.getUserCode());

			}

			if (StringUtils.isNotBlank(clientDTO.getSpocStatus())) {
				orgSpoc.setSpocStatus(clientDTO.getSpocStatus());
			}

			if (null != type && !"".equalsIgnoreCase(type) && "Client".equalsIgnoreCase(type)) {
				User user = orgSpoc.getClientUser();
				if(StringUtils.isNotBlank(clientDTO.getInvoiceOrBillSent())){
					orgSpoc.setInvoiceEmailStatus(clientDTO.getInvoiceOrBillSent());
					}else{
					orgSpoc.setInvoiceEmailStatus(Constants.NO);	
					}
					if(StringUtils.isNotBlank(clientDTO.getApOrArSent())){
					orgSpoc.setApEmailStatus(clientDTO.getApOrArSent());
					}else{
					orgSpoc.setApEmailStatus(Constants.NO);
					}
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
					sendEmailToClientContact(user, editUser, orgLocation, loginUser, orgSpoc, type);
				}
				}
			} else if (null != type && !"".equalsIgnoreCase(type) && "Vendor".equalsIgnoreCase(type)) {
				User user = orgSpoc.getVendorUser();
				if(StringUtils.isNotBlank(clientDTO.getInvoiceOrBillSent())){
					orgSpoc.setBillingEmailStatus(clientDTO.getInvoiceOrBillSent());
					}else{
					orgSpoc.setBillingEmailStatus(Constants.NO);	
					}
					if(StringUtils.isNotBlank(clientDTO.getApOrArSent())){
					orgSpoc.setArEmailStatus(clientDTO.getApOrArSent());
					}else{
					orgSpoc.setArEmailStatus(Constants.NO);
					}
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
					sendEmailToClientContact(user, editUser, orgLocation, loginUser, orgSpoc, type);
				}
			}
			}
		} else {
			StringBuilder message = new StringBuilder(MessageFormat.format(statusUpdate, "ORG RELATIONS"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
		
		if(clientDTO.getCustomFormData() !=null) {
			for( CustomFormFieldDTO customFields:clientDTO.getCustomFormData()) {
				if(customFields.getCustomFormFieldId() > 0 &&StringUtils.isNotEmpty(customFields.getCustomFormFieldData())) {
					ClientCustomFieldData vendorUpdateCustomFields = null;
					if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
						vendorUpdateCustomFields = clientCustomFieldDataService.getClientCustomFieldDataById(customFields.getEntityId());
					}else {
						vendorUpdateCustomFields = new ClientCustomFieldData();
					}
					if(orgSpoc.getOrgRelations().getResponderLocationId().getId().longValue() != orgLocation.getId().longValue()) {
						vendorUpdateCustomFields.setOrg(orgSpoc.getOrgRelations().getResponderLocationId().getOrg());

		 			}else if(orgSpoc.getOrgRelations().getLocationId().getId().longValue() != orgLocation.getId().longValue()) {
						vendorUpdateCustomFields.setOrg(orgSpoc.getOrgRelations().getLocationId().getOrg());
		 			}
					
				OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
				vendorUpdateCustomFields.setOrglocationsFormFields(orgFormField);
				vendorUpdateCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
				clientCustomFieldDataService.saveClientCustomFieldData(vendorUpdateCustomFields);
				}
				
			}	
		}
		for(String owners : clientDTO.getOwner()){
		User changedUser = userService.getUserById(Long.parseLong(owners));
		if (!loginUser.getId().equals(changedUser.getId())) {
			sendupdatedownerMailTopocUser(loginUser, spocId, owners, orgLocation, type);
		}
		}
		return orgSpoc;

	}

	private void sendupdatedownerMailTopocUser(User loginUser, Long spocId, String owner, OrgLocations orgLocation,String type)
			throws NumberFormatException, Exception {
		User changedUser = userService.getUserById(Long.parseLong(owner));
		OrgSpoc orgSpocLocation = orgSpocService.getOrgSpocById(spocId);
		User spocUser = userService.getUserById(orgSpocLocation.getVendorUser().getId());
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
		globalVarsMap.put("loginLink", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
				.toString());
		

		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocation.getOrg(),orgLocation,Constants.CLIENT_OWNER_UPDATEMAIL,new ArrayList<EmailAttachment>());

		EmailScenario noificationEmailScenario = emailScenarioService
				.getEmailScenarioByUniqueName(Constants.CLIENT_OWNER_UPDATEMAIL, Constants.ENTITY_TYPE_NOTIFICATION);
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = "";
		if("Client".equalsIgnoreCase(type))
			actionLink = actionLinkBuilder.append("").append(vmsUrl).append("/").append("clientDashboard?contactsTab=contactsTab&orgRelationUUID="+orgSpocLocation.getOrgRelations().getUUID()).toString();
		else
			actionLink = actionLinkBuilder.append("").append(vmsUrl).append("/").append("vendorsList?contactsTab=contactsTab&orgRelationUUID="+orgSpocLocation.getOrgRelations().getUUID()).toString();
		String notificationbody = emailScenerioUtil.buildTemplateBody(noificationEmailScenario.getBody(), globalVarsMap,noificationEmailScenario);
		noificationEmailScenario.setBody(notificationbody);

		emailScenerioUtil.sendNotification(actionLink, loginUser, changedUser, orgLocation, noificationEmailScenario,new HashMap<String,String>());

	}

	private void sendEmailToClientContact(User user, User editUser, OrgLocations orgLocation, User loginUser,
			OrgSpoc orgSpoc,String type) throws Exception {
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

		globalVarsMap.put("ClientFirstName", emailMessage.getFirstName());
		globalVarsMap.put("ClientLastName", emailMessage.getLastName());
		globalVarsMap.put("LoginUser", emailMessage.getFromName());
		globalVarsMap.put("LoginOrgName", orgLocation.getOrgLocationName());
		globalVarsMap.put("OwnerName", user.getFirstName());
		globalVarsMap.put("ownerEmail", user.getEmail());



		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocation.getOrg(),orgLocation,Constants.EDIT_CLIENTCONTACT,new ArrayList<EmailAttachment>());

		EmailScenario noificationEmailScenario = emailScenarioService
				.getEmailScenarioByUniqueName(Constants.EDIT_CLIENTCONTACT, Constants.ENTITY_TYPE_NOTIFICATION);
		StringBuilder actionLinkBuilder = new StringBuilder();
		//&clientOrgLocationId="+orgSpoc.getOrgRelations().getResponderLocationId()+"&orgRelationId="+orgSpoc.getOrgRelations().getId()+"&clientName="+"reject client"
		String actionLink = "";
		if("Client".equalsIgnoreCase(type))
			  actionLink = actionLinkBuilder.append("").append(vmsUrl).append("/").append("vendorsList?contactsTab=contactsTab&orgRelationUUID="+orgSpoc.getOrgRelations().getUUID()).toString();
		else
			actionLink = actionLinkBuilder.append("").append(vmsUrl).append("/").append("clientDashboard?contactsTab=contactsTab&orgRelationUUID="+orgSpoc.getOrgRelations().getUUID()).toString();
		 String rejectActionLink = "";
		String spocUUID = "";
		String notificationbody = emailScenerioUtil.buildTemplateBody(noificationEmailScenario.getBody(), globalVarsMap,noificationEmailScenario);
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
			globalVarsMap.put("FirstName", emailMessage.getFirstName());
			globalVarsMap.put("LastName", emailMessage.getLastName());
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
					"<a href='http://www.sourcelead.com/contact.html' target='_blank' style='outline:none; border:none;'>Click Here</a>")
					.toString());

			StringBuilder loginBuilder = new StringBuilder();
			globalVarsMap.put("loginURL", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append(
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
			globalVarsMap.put("FirstName", emailMessage.getFirstName());
			globalVarsMap.put("LastName", emailMessage.getLastName());
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
					"<a href='http://www.sourcelead.com/contact.html' target='_blank' style='outline:none; border:none;'>Click Here</a>")
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
			globalVarsMap.put("loginURL", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append(
					"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
					.toString());
		}

		// changed the logic of adding to emails in InetAddress Array

		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

		// getting the current EmailScenario by EmailScenario unique name and entity
		// type
		EmailScenario emailScenario = null;

		if (Constants.CLIENT.equals(orgSpoc.getOrgRelations().getResponderType())) {
			 if(orgLocations != null)
				 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.ACCEPT_CLIENT,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
				  if(emailScenario == null){
						emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_CLIENT,Constants.ENTITY_TYPE_EMAIL);
				   }
		} else if (Constants.VENDOR.equals(orgSpoc.getOrgRelations().getResponderType())) {
			 if(orgLocations != null)
				 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.ACCEPT_VENDOR,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
				  if(emailScenario == null){
						emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_VENDOR,Constants.ENTITY_TYPE_EMAIL);
				   }
			
		}
		// covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, orgLocations.getOrg(), orgLocations);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		emailMessage.setOrgLocationId(orgLocations.getId());
		
		String fromMail=commonUtil.getConfiguredFromEmail(orgLocations.getId(), emailScenario);
		 if(StringUtils.isNotBlank(fromMail)){
			 emailMessage.setFromEmail(fromMail);
		 } 

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
			globalVarsMap.put("FirstName", emailMessage.getFirstName());
			globalVarsMap.put("LastName", emailMessage.getLastName());
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
			globalVarsMap.put("FirstName", emailMessage.getFirstName());
			globalVarsMap.put("LastName", emailMessage.getLastName());
			globalVarsMap.put("clientFirstName", orgSpoc.getVendorUser().getFirstName());
			globalVarsMap.put("clientLastName", orgSpoc.getVendorUser().getLastName());
			globalVarsMap.put("clientOrgName", orgSpoc.getOrgRelations().getResponderLocationId().getOrgLocationName());
			globalVarsMap.put("signature", "Sourcelead Team");
		}

		// changed the logic of adding to emails in InetAddress Array
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

		// getting the current EmailScenario by EmailScenario unique name and entity
		// type
		
		//getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = null;
		if (Constants.CLIENT.equals(orgSpoc.getOrgRelations().getResponderType())) {
			//getting the current EmailScenario by EmailScenario unique name and entity type
			 if(orgLocations != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.REJECT_CLIENT,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_CLIENT,Constants.ENTITY_TYPE_EMAIL);
			   }
		} else if (Constants.VENDOR.equals(orgSpoc.getOrgRelations().getResponderType())) {
			 if(orgLocations != null)
				 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.REJECT_VENDOR,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
				  if(emailScenario == null){
						emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECT_VENDOR,Constants.ENTITY_TYPE_EMAIL);
				   }
		}
		

		// covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(),globalVarsMap,emailScenario);
		 String url = sourceleadUrl;

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, orgLocations.getOrg(), orgLocations);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		emailMessage.setOrgLocationId(orgLocations.getId());
		
		String fromMail=commonUtil.getConfiguredFromEmail(orgLocations.getId(), emailScenario);
		 if(StringUtils.isNotBlank(fromMail)){
			 emailMessage.setFromEmail(fromMail);
		 } 

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
				ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper
						.getQueryBuilderListForClientVendor(keywd);
				if (QueryBuilderList != null && !QueryBuilderList.isEmpty()) {
					for (QueryBuilder QueryBuilder : QueryBuilderList) {
						anyBoolSubQueryBuilder.should(QueryBuilder);
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
				// ArrayList<QueryBuilder> QueryBuilderList =
				// getQueryBuilderList(keywd);
				ArrayList<QueryBuilder> QueryBuilderList = universalSearchHelper
						.getQueryBuilderListForClientVendor(keywd);
				if (QueryBuilderList != null && !QueryBuilderList.isEmpty()) {
					for (QueryBuilder QueryBuilder : QueryBuilderList) {
						anyBoolSubQueryBuilder.should(QueryBuilder);
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
	
	@Override
	public List<Requirement> getReleasedJobsByOrgRelationId(long orgRelationId, long spocId, String userRole) {
		List<Requirement> requirementList = new LinkedList<>();
		try {
			OrgRelations orgRelation = orgRelationsService.getOrRelationsById(orgRelationId);
			List<Requirement> vendorReleasedJobsList = null;
			if (spocId == 0 && Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)) {
				if (orgRelation != null) {
					vendorReleasedJobsList = requirementService.getAllReleasedJobsForVendorBasedOnOrgRelationId(orgRelation.getLocationId().getId(), orgRelation.getId());
				}
			} else if(spocId > 0) {
				vendorReleasedJobsList = requirementService.getAllReleasedJobsBasedOnSpocId(spocId);
			}
			return vendorReleasedJobsList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requirementList;
	}

}
