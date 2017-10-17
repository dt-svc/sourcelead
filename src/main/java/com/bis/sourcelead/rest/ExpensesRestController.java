package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AddExpensesDao;
import com.bis.sourcelead.dao.ExpensesEntriesDao;
import com.bis.sourcelead.dao.ExpensesEntryDocumentsDao;
import com.bis.sourcelead.dto.ExpenseEntryDetailsDTO;
import com.bis.sourcelead.dto.ExpensesCategoryListDTO;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesListUtilDTO;
import com.bis.sourcelead.dto.ProjectMembersDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.TimesheetExpenseEntryDetailsUtilDTO;
import com.bis.sourcelead.dto.helper.ExpenseCategoryDTOConverter;
import com.bis.sourcelead.dto.helper.ExpensesDTOConverter;
import com.bis.sourcelead.dto.helper.ProjectDTOConverter;
import com.bis.sourcelead.dto.helper.ProjectMemberDTOConverter;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.CurrencyCodes;
import com.bis.sourcelead.enums.PaymentModes;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.LoginSessionController;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ExpensesRestController {
	private static final Logger logger = LoggerFactory.getLogger(ExpensesRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	private ProjectClientService projectClientService;
	
	@Autowired
	private AddExpensesService addExpensesService;
	@Autowired
	private CountriesService countriesService;
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	ProjectMembersService projectMembersService;
	@Autowired
	ExpensesCategoryService expensesCategoryService;
	@Autowired
	private OrgRelationsService orgRelationsService;
	@Autowired
	private OrgLocationService orgLocationService;
	@Autowired
	private ExpensesApprovalService expensesApprovalService;
	@Autowired
	private UserService userService;
	@Autowired
	private ExpensesEntriesDao expensesEntriesDao;
	@Autowired
	private ExpensesEntryDocumentsDao expensesEntryDocumentsDao;
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	private AddExpensesDao addExpensesDao;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private LoginSessionController loginSessionController;
	
	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	@RequestMapping(value = {"/getExpensesList","/rest/getExpensesList"})
    public List<ExpensesDTO> getExpensesList( @RequestBody ExpensesListUtilDTO elDTO) throws Exception
    {
        
        OrgLocations orgLoc = orgLocationService.getOrgLocationsByLocationCode(elDTO.getLocationCode());
        User loginUser = userService.getUserById(elDTO.getUserId());
        String expenseStatus = "";
        if(elDTO.getExpenseStatus() != null && !elDTO.getExpenseStatus().isEmpty()){
            expenseStatus = elDTO.getExpenseStatus().stream ().map (i -> i.toString ()).collect (Collectors.joining (","));
        }
        List<Expenses> expensesList = expensesService.getAllExpenses(orgLoc, loginUser,expenseStatus, elDTO.getStartDate(), elDTO.getEndDate());
        List<ExpensesDTO> expensesDTOList = new ArrayList<>();
        for(Expenses expenses :expensesList){
            Set<ExpensesApproverHierarchy> hierarchyList = expenses.getApproverHierarchyList();
            for(ExpensesApproverHierarchy eah :hierarchyList){
                if(eah.getHierarchyId() != null && eah.getHierarchyId().getProjectClientsId() != null){
                    ProjectClients pc = eah.getHierarchyId().getProjectClientsId();
                    if(pc.getOrgLocationsId() != null && (pc.getOrgLocationsId().getId() == orgLoc.getId())){
                        if(pc.getOrgRelationId() != null && "Vendor".equalsIgnoreCase(pc.getOrgRelationId().getInitiatorType())){
                            expenses.setClientName(pc.getOrgRelationId().getResponderLocationId().getOrgLocationName());
                         }else if(pc.getOrgRelationId() != null && "Client".equalsIgnoreCase(pc.getOrgRelationId().getInitiatorType())){
                             expenses.setClientName(pc.getOrgRelationId().getLocationId().getOrgLocationName());
                         }
                    }
                }
                
            }
            expensesDTOList.add(ExpensesDTOConverter.getExpensesDTOFromExpenses(expenses,new ExpensesDTO()));
        }
        
        return expensesDTOList;
    }	
	
	/**
	 *  This method serves request for New Expense Sreen
	 * @param eedDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rest/getExpenseEntryDetails")
	public ExpenseEntryDetailsDTO getExpenseEntryDetails(@RequestBody TimesheetExpenseEntryDetailsUtilDTO eedDTO) throws Exception
	{
		
		ExpenseEntryDetailsDTO expenseEntryDetailsDTO = new ExpenseEntryDetailsDTO();
		OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getLocationCode());
		User loginUser = userService.getUserById(eedDTO.getUserId());
		if(StringUtils.isNotEmpty(eedDTO.getExpenseFrom()) && eedDTO.getExpenseFrom().equalsIgnoreCase("approver")){
			List<ProjectClients> projectClientsList = projectClientService.getAllProjectClientsByApproverUserAndOrgLocationId(loginUser.getId(),orgLocations.getId());
			ArrayList<ProjectsDTO> projectsList = new ArrayList<>();
			for(ProjectClients pc : projectClientsList){
				Projects projects = pc.getProjectId();
				List<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(projects.getId(),orgLocations.getId());
				List<ExpensesCategory>  expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndStatus(projects.getOrgLocationsId().getId(), Constants.MEMBER_ACTIVE);
				ProjectsDTO projectDTO =ProjectDTOConverter.getProjectsDTOFromProjects(projects);
				if (expensesCategoryList != null && !expensesCategoryList.isEmpty()) {
					ArrayList<ExpensesCategoryListDTO> ecDTOList = new ArrayList<>();
					for (ExpensesCategory ec : expensesCategoryList) {
						ecDTOList.add(ExpenseCategoryDTOConverter.getExpensesCategoryDTOFromExpensesCategory(ec));
					}
					projectDTO.setExpenseCategoryList(ecDTOList);
				}
				if(StringUtils.isNotEmpty(eedDTO.getExpenseId())){
					projectDTO.setExpenseId(eedDTO.getExpenseId());
				}
				else{
					String expenseId = addExpensesService.getExpenseId(pc.getProjectId().getOrgLocationsId());
					projectDTO.setExpenseId(expenseId);
				}
				ArrayList<ProjectMembersDTO> pmDTOList = new ArrayList<>();
				for (ProjectMembers pm : projectMembersList) {
					ProjectMembersDTO pmDTO =ProjectMemberDTOConverter.getProjectMembersDTOFromProjectMembers(pm);
					 HashMap<String, Date> dateMap = projectMembersHistoryService.getProjectMembersStartDateAndEndDateByProjectMemberId(pm.getId());
					 if(dateMap != null && !dateMap.isEmpty()) {
						 Date pmStartDate = dateMap.get("STARTDATE");
						 Date pmEndDate =  dateMap.get("ENDDATE");
						   pmDTO.setMemberStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmStartDate));
						 if(null != pmEndDate){
							 pmDTO.setMemberEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmEndDate));
						  }
					 }
					pmDTOList.add(pmDTO);
					
				}
				projectDTO.setProjectMembersList(pmDTOList);
			    if(pc.getOrgRelationId() != null){
			    	 if("Vendor".equalsIgnoreCase(pc.getOrgRelationId().getInitiatorType())){
			    		 projectDTO.setClient(pc.getOrgRelationId().getResponderLocationId().getOrgLocationName());
					 }else if("Client".equalsIgnoreCase(pc.getOrgRelationId().getInitiatorType())){
						 projectDTO.setClient(pc.getOrgRelationId().getLocationId().getOrgLocationName());
					 }
			    }
				projectsList.add(projectDTO);
			}
			expenseEntryDetailsDTO.setProjectsList(projectsList);
		}
		else{
			Candidate candidate = candidateService.getCandidateByConsultantUserId(eedDTO.getUserId(), orgLocations.getId());
			List<ProjectMembers> projectMembersList = projectMembersService.getProjectMembersListByOrgLocationsIdAndCandidateId(orgLocations.getId(), candidate.getId());
			if (projectMembersList != null && !projectMembersList.isEmpty()) {
				ArrayList<ProjectMembersDTO> pmDTOList = new ArrayList<>();
				for (ProjectMembers pm : projectMembersList) {
					ProjectMembersDTO pmDTO =ProjectMemberDTOConverter.getProjectMembersDTOFromProjectMembers(pm);
					List<ExpensesCategory> expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndStatus(pm.getProjects().getOrgLocationsId().getId(), "1");
					if (expensesCategoryList != null && !expensesCategoryList.isEmpty()) {
						ArrayList<ExpensesCategoryListDTO> ecDTOList = new ArrayList<>();
						for (ExpensesCategory ec : expensesCategoryList) {
							ecDTOList.add(ExpenseCategoryDTOConverter.getExpensesCategoryDTOFromExpensesCategory(ec));
						}
						pmDTO.setExpenseCategoryList(ecDTOList);
					}
					
					if(StringUtils.isNotEmpty(eedDTO.getExpenseId())){
						pmDTO.setExpenseId(eedDTO.getExpenseId());
					}
					else{
						String expenseId = addExpensesService.getExpenseId(pm.getProjects().getOrgLocationsId());
						pmDTO.setExpenseId(expenseId);
					}
					 if(pm.getProjectClientsId() != null &&  pm.getProjectClientsId().getOrgRelationId() != null){
				    	 if("Vendor".equalsIgnoreCase(pm.getProjectClientsId().getOrgRelationId().getInitiatorType())){
				    		 pmDTO.setClientName(pm.getProjectClientsId().getOrgRelationId().getResponderLocationId().getOrgLocationName());
						 }else if("Client".equalsIgnoreCase(pm.getProjectClientsId().getOrgRelationId().getInitiatorType())){
							 pmDTO.setClientName(pm.getProjectClientsId().getOrgRelationId().getLocationId().getOrgLocationName());
						 }
				    }
					 HashMap<String, Date> dateMap = projectMembersHistoryService.getProjectMembersStartDateAndEndDateByProjectMemberId(pm.getId());
					 if(dateMap != null && !dateMap.isEmpty()) {
						 Date pmStartDate = dateMap.get("STARTDATE");
						 Date pmEndDate =  dateMap.get("ENDDATE");
						   pmDTO.setMemberStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmStartDate));
						 if(null != pmEndDate){
							 pmDTO.setMemberEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmEndDate));
						  }
					 }
					pmDTOList.add(pmDTO);
				}
				expenseEntryDetailsDTO.setProjectMembersList(pmDTOList);
			}
		}
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		if (null != countries) {
			expenseEntryDetailsDTO.setCurrencyCode(countries.getCurrency());
		}
		List<String> paymentModes = new LinkedList<>();
		paymentModes.add(PaymentModes.CREDITCARD.getValue());
		paymentModes.add(PaymentModes.DEBITCARD.getValue());
		paymentModes.add(PaymentModes.CASH.getValue());
		paymentModes.add(PaymentModes.CHEQUE.getValue());
		paymentModes.add(PaymentModes.EWALLET.getValue());
		expenseEntryDetailsDTO.setPaymentModes(paymentModes);
		List<String> currencyCodeList = new LinkedList<>();
		currencyCodeList.add(CurrencyCodes.INR.getValue());
		currencyCodeList.add(CurrencyCodes.AUD.getValue());
		currencyCodeList.add(CurrencyCodes.USD.getValue());
		currencyCodeList.add(CurrencyCodes.GBP.getValue());
		currencyCodeList.add(CurrencyCodes.SGD.getValue());
		currencyCodeList.add(CurrencyCodes.EUR.getValue());
		expenseEntryDetailsDTO.setCurrencyCodeList(currencyCodeList);
		List<String> paidBy = new LinkedList<>();
		paidBy.add("Self");
		paidBy.add("Comapny");
		expenseEntryDetailsDTO.setPaidByType(paidBy);
		return expenseEntryDetailsDTO;
	}
	/*
	@RequestMapping(value = "/rest/storeDocumentForIOS")
	public @ResponseBody String storeDocumentForIOS(MultipartHttpServletRequest request,@RequestParam(value = "docName") Long docName) throws Exception
	{
		  final Map files = request.getFileMap();
           MultipartFile  multiFile = (MultipartFile)files.get(0);
           System.out.println(multiFile.getOriginalFilename());
           return multiFile.getOriginalFilename();
	}*/
	/*@RequestMapping(value = "/rest/storeExpenseEntryDetails")
	public @ResponseBody String storeExpenseEntryDetails(@RequestParam("entryForm") String entryForm, 
			MultipartHttpServletRequest request) throws Exception
	{
		String currencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		String result = "failure";
		Expenses expenses = null;
		ProjectMembers projMembers = null;
		ExpensesEntriesStoringDTO eesDTO = null;
		if(StringUtils.isNotEmpty(entryForm)){
			Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	   	    eesDTO =  gson.fromJson(entryForm,ExpensesEntriesStoringDTO.class);
		}
		if(eesDTO != null){
			OrgLocations orgLoc = orgLocationService.getOrgLocationsByLocationCode(eesDTO.getLocationsCode());
			//loginSessionController.DATEFORMAT= new SimpleDateFormat(orgLoc.getDateFormat());
			User loginUser = userService.getUserById(eesDTO.getUserId());
			String status = eesDTO.getStatus();
			expenses = new Expenses();
			if(eesDTO.getId() != null){
				expenses = expensesService.getExpenseById(eesDTO.getId());
			}
			if (StringUtils.isNotEmpty(eesDTO.getExpenseId())) {
				expenses.setExpenseId(eesDTO.getExpenseId());
			}
			if (StringUtils.isNotEmpty(eesDTO.getPurpose())) {
				expenses.setPurpose(eesDTO.getPurpose());
			}
			if (eesDTO.getProjectMemberId() != null) {
				projMembers = projectMembersService.getProjectMemberById(eesDTO.getProjectMemberId());
				expenses.setProjectMembers(projMembers);
			}
			if (StringUtils.isNotEmpty(eesDTO.getClientName())) {
				expenses.setClientName(eesDTO.getClientName());
			}
			if (StringUtils.isNotEmpty(eesDTO.getStartDate())) {
				expenses.setStartDate(commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),eesDTO.getStartDate()));
			}
			if (StringUtils.isNotEmpty(eesDTO.getEndDate())) {
				expenses.setEndDate(commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),eesDTO.getEndDate()));
			}
			if (null != orgLoc) {
				expenses.setOrgLocationId(orgLoc);
			}
			if (null == expenses.getId()) {
				expenses.setCreatedBy(loginUser.getUserCode());
			}
			expenses.setUpdatedBy(loginUser.getUserCode());
			if (null == expenses.getId()) {
				expenses.setCreatedDate(CommonUtil.currentDate());
			}
			expenses.setUpdatedDate(CommonUtil.currentDate());
			if (status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue())) {
				expenses.setExpenseStatus(Submission_Status.SUBMITTED);
			}
			else if (status.equalsIgnoreCase(Submission_Status.DRAFT.getValue())) {
				expenses.setExpenseStatus(Submission_Status.DRAFT);
			}
			
			expenses.setStatus(Constants.EXPENSES_STATUS);
			Set<ExpensesEntries> expensesEntriesList = prepareExpensesEntriesList(eesDTO,orgLoc,request, expenses);
			expenses.setExpensesEntries(expensesEntriesList);
			expenses.setNoOfExpenses(String.valueOf(expensesEntriesList.size()));
			float totalAmount = 0;
			for (ExpensesEntries expensesEntry : expensesEntriesList) {
				totalAmount = Float.sum(totalAmount, expensesEntry.getAmount());
			}
			expenses.setTotalAmount(totalAmount);
			if (projMembers != null) {
				ArrayList<User> managers = new ArrayList<User>();
				HashSet<ExpensesApproverHierarchy> eaHierarchyList = new HashSet<ExpensesApproverHierarchy>();
				List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getApproverUsersByProjectId(projMembers.getProjects().getId());
				if (hierarchyList != null && !hierarchyList.isEmpty()) {
						for (ProjectApproverHierarchy hierarchy : hierarchyList) {
							ExpensesApproverHierarchy eaHierarchy = new ExpensesApproverHierarchy();
							eaHierarchy.setHierarchyId(hierarchy);
							eaHierarchy.setExpenseId(expenses);
							if (status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue())
									|| status.equalsIgnoreCase(Submission_Status.APPROVED.getValue())) {
								eaHierarchy.setExpenseStatus(Submission_Status.SUBMITTED);
								eaHierarchy.setExpenseApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
							}
							else if (status.equalsIgnoreCase(Submission_Status.DRAFT.getValue())) {
								eaHierarchy.setExpenseStatus(Submission_Status.DRAFT);
								eaHierarchy.setExpenseApprovalStatus(Submission_ApprovalStatus.NEW);
							}
							eaHierarchy.setCreatedDate(expenses.getCreatedDate());
							eaHierarchy.setCreatedBy(expenses.getCreatedBy());
							eaHierarchy.setUpdatedDate(expenses.getUpdatedDate());
							eaHierarchy.setUpdatedBy(expenses.getUpdatedBy());
							eaHierarchy.setStatus(Constants.RECORD_ACTIVE + "");
							managers.add(hierarchy.getApproverUserId());
							eaHierarchyList.add(eaHierarchy);
						}
				}
				if (eaHierarchyList != null && !eaHierarchyList.isEmpty()) {
					expenses.setApproverHierarchyList(eaHierarchyList);
				}
				if (managers != null && !managers.isEmpty()) {
					expenses.setProjectManagerUserList(managers);
				}
			}
			if(eesDTO.getRemovedCategoryIds() != null && !eesDTO.getRemovedCategoryIds().isEmpty()){
		    		expensesEntriesDao.removeAllExpenseEntries(eesDTO.getRemovedCategoryIds());
		    }
			
			
			removeExpensesEntryDocoumentList
			expenses = addExpensesDao.storeAddExpensesData(expenses);
			if (status.equalsIgnoreCase(Submission_Status.APPROVED.getValue())) {
				expensesApprovalService.updateApprovalStatusByApproverForExpense(expenses.getId(), loginUser.getId(), "APPROVED", "");
			}
			if (status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue())) {
				addExpensesService.sendExpensesEmailToManager(loginUser, expenses, orgLoc,currencyCode);
			}
			result = "success";
		}
		return result;
	}
	
	private Set<ExpensesEntries> prepareExpensesEntriesList(ExpensesEntriesStoringDTO eesDTO,OrgLocations orgLocation,MultipartRequest request,Expenses expenses) throws Exception{
	    HashSet<ExpensesEntries> expensesEntriesList = new HashSet<ExpensesEntries>(); 
	    OrgLocations orgLoc = orgLocationService.getOrgLocationsByLocationCode(eesDTO.getLocationsCode());
	    if(eesDTO.getCategoryList() != null && !eesDTO.getCategoryList().isEmpty()){
	    	for(ExpensesCategoryDTO edDTO : eesDTO.getCategoryList()){
	    		ExpensesEntries  entries  = new ExpensesEntries();
	    		if(edDTO.getId() != null){
	    			entries.setId(edDTO.getId());
	    		}
	    		if(edDTO.getCategoryTypeId() != null){
					ExpensesCategory ec = new ExpensesCategory();
					ec.setId(edDTO.getCategoryTypeId());
					entries.setExpensesCategoryId(ec);
				
				if(StringUtils.isNotEmpty(edDTO.getExpenseDate())){
					Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date date = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),edDTO.getExpenseDate());
					String currentTimeStr = Constants.TZ_DF.format(date);  
					String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					entries.setExpenseDate(converteDate);
				}
				
				if(StringUtils.isNotEmpty(edDTO.getReceiptIssuedBy())){
					entries.setReceiptIssuedBy(edDTO.getReceiptIssuedBy());
				}
				if(StringUtils.isNotEmpty(edDTO.getDescription())){
					entries.setDescription(edDTO.getDescription());
				}
				
				if(StringUtils.isNotEmpty(edDTO.getPaymentMode())){
					entries.setPaymentMode(PaymentModes.getNameByValue(edDTO.getPaymentMode()));
				}
				if(StringUtils.isNotEmpty(edDTO.getCurrencyCode()) && edDTO.getExpenseAmount() != null){
					Countries country = countriesService.getCountriesByCountryName(orgLocation.getCountry());
					if(!country.getCurrency().equalsIgnoreCase(edDTO.getCurrencyCode())){
						 Float amount = conversionUtil.convert(edDTO.getCurrencyCode(),country.getCurrency(),edDTO.getExpenseAmount().floatValue());
						 entries.setCurrencyType(country.getCurrency());
						 entries.setAmount(amount);
						}else{
							entries.setCurrencyType(edDTO.getCurrencyCode());
							entries.setAmount(edDTO.getExpenseAmount().floatValue());
						}
				}
				if(edDTO.getFileNames() != null && !edDTO.getFileNames().isEmpty()){
					HashSet<ExpensesEntryDocuments> eeDocList = new HashSet<>();
					for(String fileName : edDTO.getFileNames()){
						 ExpensesEntryDocuments eedoc = new ExpensesEntryDocuments();
		       		      final Map files = request.getFileMap();
		       		      
		       		      eedoc.setExpensesEntriesId(entries);
		                   MultipartFile  multiFile = (MultipartFile)files.get(fileName);
		                   if(multiFile != null && !multiFile.isEmpty()){
		                	   eedoc.setDocFile(multiFile.getBytes());
		                	   eedoc.setFileName(multiFile.getOriginalFilename());
		                       
		                       if(multiFile.getContentType()!=null){
		                    	   eedoc.setFileContentType(multiFile.getContentType());
		                       }
		                       eedoc.setStatus(Constants.CANDIDATE_ACTIVE);
		                       eedoc.setCreatedDate(CommonUtil.currentDate());
		                       eedoc.setCreatedBy(entries.getCreatedBy());
		                       eedoc.setUpdatedDate(CommonUtil.currentDate());
		                       eedoc.setUpdatedBy(entries.getUpdatedBy());
			                   eeDocList.add(eedoc);
		                   }
					}
					 entries.setExpensesEntryDocuments(eeDocList);
					
				}
				entries.setCreatedDate(CommonUtil.currentDate());
				entries.setCreatedBy(expenses.getCreatedBy());
				entries.setUpdatedDate(CommonUtil.currentDate());
				entries.setExpensesId(expenses);
				entries.setUpdatedBy(expenses.getUpdatedBy());
				entries.setStatus(Constants.RECORD_ACTIVE.toString());
				expensesEntriesList.add(entries);
	    	 }
	    		if(edDTO.getRemovedFilesIds() != null && !edDTO.getRemovedFilesIds().isEmpty()){
		    		expensesEntryDocumentsDao.removeExpensesEntryDocoumentList(edDTO.getRemovedFilesIds());
		        }
	    }
	 }
	    return expensesEntriesList;
	}*/
	
	/**
	 *  This method serves request for Edit Expense Sreen
	 * @param eedDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rest/getExpenseDetailsByExpenseId")
	public ExpensesDTO getExpenseDetailsByExpenseId(@RequestBody ExpensesListUtilDTO elDTO) throws Exception
	{
		ExpensesDTO expensesDTO = null;
		OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(elDTO.getExpenseLocationCode());
		Expenses expenses = expensesService.getExpenseByExpenseUniqueId(elDTO.getExpenseId(), orgLocations.getId());
		//loginSessionController.DATEFORMAT= new SimpleDateFormat(orgLocations.getDateFormat());
		if (expenses != null) {
			expensesDTO = ExpensesDTOConverter.getExpensesDTOFromExpenses(expenses, expensesDTO);
			expensesDTO.setMasterCandidateId(expenses.getProjectMembers().getMasterCandidateId().getId());
			ExpensesApproverHierarchy eah = expensesApprovalService.getexpensesApproverHierarchyForEmployeeByExpenseId(expenses.getId());
			Submission_Status submissionStatus = eah.getExpenseStatus();
			expensesDTO.setCombinedSubmissionStatus(submissionStatus.getValue().toUpperCase());
			ExpensesApproverHierarchy isSah = expensesApprovalService.isThereAnyApproverApprovedTheExpense(expenses.getId());
			boolean isSahApproved = false;
			if (isSah != null) {
				isSahApproved = true;
			}
			if (expenses.getCreatedBy() != null) {
				User createduser = userService.getUserByUserCode(expenses.getCreatedBy());
				StringBuilder createdName = new StringBuilder();
				if (createduser.getFirstName() != null && createduser.getLastName() != null) {
					createdName.append(createduser.getFirstName()).append(" ").append(createduser.getLastName());
				}
				else {
					createdName.append(createduser.getFirstName());
				}
				expensesDTO.setCreatedBy(createdName.toString());
			}
			expensesDTO.setSahApproved(isSahApproved);
		}
		return expensesDTO;
	}
	
	@RequestMapping(value = "/getIndividualExpense")
	public String getIndividualExpense(HttpServletRequest request, String expenseId) throws Exception
	{
		String resultJson;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ExpensesEntries> expensesEntriesList = expensesService.getExpensesEntries(expenseId);
		for (ExpensesEntries exEnt : expensesEntriesList) {
			if (null != exEnt.getExpenseDate()) {
				exEnt.setExpenseDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),exEnt.getExpenseDate()));
			}
			if (null == exEnt.getExpenseIndList()) {
				List<Expenses> expensesList = expensesService.getIndividualExpense(orgLoc, loginUser, expenseId);
				for (Expenses ex : expensesList) {
					if (null != ex.getProjectMembers()) {
						ex.setProjectName(ex.getProjectMembers().getProjects().getProjectName());
					}
					if (null != ex.getStartDate()) {
						ex.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),ex.getStartDate()));
					}
					if (null != ex.getEndDate()) {
						ex.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),ex.getEndDate()));
					}
				}
				exEnt.setExpenseIndList(expensesList);
			}
		}
		resultJson = commonUtil.toJSON(expensesEntriesList);
		return resultJson;
	}
	
	@RequestMapping(value = "/exportMyExpensesList")
	public void exportMyExpensesList(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "Expid", required = false) String Expid, @RequestParam(value = "ApproverType", required = false) String ApproverType) throws Exception
	{
		File file = null;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Expenses ExpenseDetails = expensesService.getExpenseById(Long.parseLong(Expid));
		if (ExpenseDetails != null) {
			if (type.equalsIgnoreCase("PDF")) {
				file = expensesService.exportMyExpensesToPdf(ExpenseDetails, orgLoc, loginUser, ApproverType);
			}
			else {
				file = expensesService.exportMyExpensesToExcel(ExpenseDetails, orgLoc, loginUser, ApproverType);
			}
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
		if (file != null) {
			file.delete();
		}
	}
	
	@RequestMapping(value = "/exportAllMyExpensesList")
	public void exportAllMyExpensesList(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "expenseStatus", required = false) String expenseStatus, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate) throws Exception
	{
		File file = null;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Expenses> expensesList = expensesService.getAllExpenses(orgLoc, loginUser, expenseStatus, startDate, endDate);
		if (expensesList != null && expensesList.size() > 0) {
			for (Expenses ex : expensesList) {
				if (null != ex.getProjectMembers()) {
					ex.setProjectName(ex.getProjectMembers().getProjects().getProjectName());
				}
				if (null != ex.getStartDate()) {
					ex.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),ex.getStartDate()));
				}
				if (null != ex.getEndDate()) {
					ex.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),ex.getEndDate()));
				}
			}
			if (type.equalsIgnoreCase("PDF")) {
				file = expensesService.exportAllMyExpensesToPdf(expensesList, orgLoc, loginUser);
			}
			else {
				file = expensesService.exportAllMyExpensesToExcel(expensesList, orgLoc);
			}
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
		if (file != null) {
			file.delete();
		}
	}
	
	@RequestMapping(value = "/myExpensesListByStage")
	public String myExpensesListByStage(HttpServletRequest request, String expenseStatus) throws Exception
	{
		String resultJson = "";
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		List<Expenses> expensesList = expensesService.getTotalMyExpensesListByMasterCandidateIdForEmployeeLogin(candidate.getMasterCandidateId().getId(), expenseStatus);
		for (Expenses ex : expensesList) {
			if (null != ex.getProjectMembers()) {
				ex.setProjectName(ex.getProjectMembers().getProjects().getProjectName());
			}
			if (null != ex.getStartDate()) {
				ex.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),ex.getStartDate()));
			}
			if (null != ex.getEndDate()) {
				ex.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),ex.getEndDate()));
			}
		}
		resultJson = commonUtil.toJSON(expensesList);
		return resultJson;
	}
	
	@RequestMapping(value = "/employeeExpensesListByStage")
	public List<ExpensesApproverHierarchy> employeeExpensesListByStage(HttpServletRequest request, String expenseStatus) throws Exception
	{
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesService.getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(loginUser.getId(), orgLoc.getId(), expenseStatus);
		for (ExpensesApproverHierarchy expensesApproverHierarchy : expApperoverHierarchy) {
			if (null != expensesApproverHierarchy.getExpenseId().getEndDate()) {
				expensesApproverHierarchy.setExpenseStartDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getStartDate()));
				expensesApproverHierarchy.setExpenseEndDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getEndDate()));
			}
			else {
				expensesApproverHierarchy.setExpenseStartDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getStartDate()));
			}
			if (null != expensesApproverHierarchy.getExpenseId()) {
				if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers()) {
					if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects()) {
						expensesApproverHierarchy.setExpensesProjectName(expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects().getProjectName());
					}
					else {
						expensesApproverHierarchy.setExpensesProjectName("--");
					}
					if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee()) {
						if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId()
								&& !"".equalsIgnoreCase(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId())) {
							expensesApproverHierarchy.setExpensesEmployeeUserId(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId());
						}
						else {
							expensesApproverHierarchy.setExpensesEmployeeUserId("--");
						}
						if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjectMemberCandidteId()
								&& !"".equalsIgnoreCase(expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjectMemberCandidteId().getDesignationType())) {
							String empType = expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjectMemberCandidteId().getDesignationType();
							if (empType.equalsIgnoreCase("C2C")
									|| empType.equalsIgnoreCase("C2C-1099")) {
								OrgRelations orgRelation = expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjectMemberCandidteId().getOrgRelationId();
								expensesApproverHierarchy.setUserTypeVendorName(orgRelation.getResponderLocationId().getOrg().getOrgName());
								expensesApproverHierarchy.setExpenseEmpUserType(empType);
							}
							else {
								expensesApproverHierarchy.setExpenseEmpUserType(empType);
							}
						}
						else {
							expensesApproverHierarchy.setExpenseEmpUserType("--");
						}
						if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjectMemberCandidteId()) {
							expensesApproverHierarchy.setExpenseEmpUserFirstNameLastName(expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjectMemberCandidteId().getFullName());
							expensesApproverHierarchy.setExpenseEmpUserEmail(expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjectMemberCandidteId().getEmail());
							expensesApproverHierarchy.setExpensesUserid(expensesApproverHierarchy.getExpenseId().getId());
						}
					}
				}
			}
		}
		return expApperoverHierarchy;
	}
}