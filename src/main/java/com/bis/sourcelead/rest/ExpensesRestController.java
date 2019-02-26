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
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ExpenseEntryDetailsDTO;
import com.bis.sourcelead.dto.ExpensesCategoryListDTO;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesListUtilDTO;
import com.bis.sourcelead.dto.ProjectMembersDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
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
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.CurrencyCodes;
import com.bis.sourcelead.enums.PaymentModes;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class ExpensesRestController {
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
	private CandidateService candidateService;
	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;

	
	
	@RequestMapping(value = "/rest/getExpensesList")
    public ResponseList<ExpensesDTO> getExpensesListForMobile(@RequestBody ExpensesListUtilDTO elDTO,HttpServletRequest request) throws Exception{
		return getExpensesList( elDTO, request);
    }
	
	@RequestMapping(value = "/getExpensesList" , method=RequestMethod.POST)
    public ModelAndView getExpensesListForWeb( ExpensesListUtilDTO elDTO,HttpServletRequest request){
		 ModelAndView model = new ModelAndView();
		 try {
		 OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			int start = elDTO.getStart();
			int length = 0;
			if(elDTO.getPageNo()<1) {
				elDTO.setPageNo(1);
			}
			if(elDTO.getPageSize()<1) {
				length = 10;
				elDTO.setPageSize(10);
			}else {
				length = elDTO.getPageSize();
			}
			elDTO.setStart(start);
			elDTO.setLength(length);
			if(elDTO.getExpenseDashboardStatus() !=null && elDTO.getExpenseDashboardStatus().equalsIgnoreCase("dashboard")) {
				 model.addObject("expenseDashBoardStatus",elDTO.getExpenseStatus().get(0));
			}
			ResponseList<ExpensesDTO> expenseEmployeeList = getExpensesList( elDTO, request);
		     model.addObject("expensesDTOList",expenseEmployeeList.getData());
		     model.addObject("sourceleadUrl",sourceleadUrl);
		     model.addObject("orgLocationDateFormat",orgLoc.getDateFormat().toUpperCase());
		     model.addObject("totalRows",expenseEmployeeList.getRecordsFiltered());
			 model.addObject("pageCount",elDTO.getPageNo());
			 model.addObject("pageSize",elDTO.getLength());
			 model.addObject("searchValue",elDTO.getSearchValue());
			 model.setViewName("expenses/expensesEmployeeRedefined");
		 }catch(Exception e) {
			  model.addObject("AWKWARD", true);
			  model.setViewName("expenses/expensesEmployeeRedefined");
			  e.printStackTrace();
		 }
			return model;
	}
	
    public ResponseList<ExpensesDTO> getExpensesList( ExpensesListUtilDTO elDTO,HttpServletRequest request) throws Exception 
    {
        
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<ExpensesDTO> response = new ResponseList<ExpensesDTO>();
		if(orgLoc == null){
			orgLoc = orgLocationService.getOrgLocationsByLocationCode(elDTO.getLocationCode());
		}
		if(loginUser == null){
			loginUser = userService.getUserById(elDTO.getUserId());
		}
        String expenseStatus = "";
        if(elDTO.getExpenseStatus() != null && !elDTO.getExpenseStatus().isEmpty()){
            expenseStatus = elDTO.getExpenseStatus().stream ().map (i -> i.toString ()).collect (Collectors.joining (","));
            expenseStatus = expenseStatus.toUpperCase();
        }
        ResponseList<Expenses> expensesList = expensesService.getAllExpenses(orgLoc, loginUser,expenseStatus, elDTO.getStartDate(), elDTO.getEndDate(),elDTO.getSearchValue(),elDTO.getStart(),elDTO.getLength());
        List<ExpensesDTO> expensesDTOList = new ArrayList<>();
        for(Expenses expenses :expensesList.getData()){
            List<ExpensesApproverHierarchy> hierarchyList = expenses.getApproverHierarchyList();
            for(ExpensesApproverHierarchy eah :hierarchyList){
                if(eah.getHierarchyId() != null && eah.getHierarchyId().getProjectClientsId() != null){
                    ProjectClients pc = eah.getHierarchyId().getProjectClientsId();
                    if(pc.getOrgLocationsId() != null && (pc.getOrgLocationsId().getId() == orgLoc.getId())){
                        if(pc.getOrgRelationId() != null && "Vendor".equalsIgnoreCase(pc.getOrgRelationId().getInitiatorType())){
                            expenses.setClientName(pc.getOrgRelationId().getResponderLocationId().getOrgLocationName());
                         }else if(pc.getOrgRelationId() != null && "Client".equalsIgnoreCase(pc.getOrgRelationId().getInitiatorType())){
                             expenses.setClientName(pc.getOrgRelationId().getLocationId().getOrgLocationName());
                         }
                        if(pc.getOrgRelationId() != null && pc.getOrgRelationId().getId() != null) {
                        OrgRelations orgRelations = orgRelationsService.getOrgRelationById(pc.getOrgRelationId().getId());
            			expenses.setOrgRelationUUID(orgRelations.getUUID());
                        }
                        if(eah.getRejectedReason() != null && StringUtils.isNotEmpty(eah.getRejectedReason())) {
                        	expenses.setExpensesRejectedReason(eah.getRejectedReason());
                        }
                    }
                }
        		//Submission_Status submissionStatus = eah.getExpenseStatus();
        		//expenses.setExpenseStatus(submissionStatus);
            }
            /*if(expenses.getOrgRelationId() != null && !expenses.getOrgRelationId().isEmpty()){
    			OrgRelations orgRelations = orgRelationsService.getOrgRelationById(Long.parseLong(expenses.getOrgRelationId()));
    			expenses.setOrgRelationUUID(orgRelations.getUUID());
    		}*/
            ExpensesDTO expDate =  ExpensesDTOConverter.getExpensesDTOFromExpenses(orgLoc,expenses,new ExpensesDTO());
            expDate.setStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenses.getStartDate()));
            expDate.setEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenses.getEndDate()));	
            expensesDTOList.add(expDate);
        }
        response.setData(expensesDTOList);
        response.setRecordsFiltered(expensesList.getRecordsFiltered());
        response.setRecordsTotal(expensesList.getData().size());
        return response;
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
			String purpose = "newExpense";
			if(StringUtils.isNotEmpty(eedDTO.getExpenseId())) {
				purpose = "editExpense";
			}
			
			List<ProjectClients> projectClientsList = projectClientService.getAllProjectClientsForExpensesByApproverUserAndOrgLocationId(loginUser.getId(),orgLocations.getId(),eedDTO.getIsGlobalUser(),purpose);
			ArrayList<ProjectsDTO> projectsList = new ArrayList<>();
			for(ProjectClients pc : projectClientsList){
				Projects projects = pc.getProjectId();
				if(projects.getOrgLocationsId()== null)
				continue;
				List<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersWithMasterCandidateIdByProjectIdAndLocationId(projects.getId(),orgLocations.getId(),eedDTO.getIsGlobalUser());
				Long orgLocationId = null;
				String isGlobalAccess = eedDTO.getIsGlobalUser();
				if(StringUtils.isNotEmpty(eedDTO.getExpenseId())){
					OrgLocations expLoc = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getExpenseLocationCode());
					if(expLoc != null) {
						orgLocationId = expLoc.getId();
					}
					isGlobalAccess = "N";
				}else {
					if(projects.getOrgLocationsId() != null && projects.getOrgLocationsId().getOrg() != null && StringUtils.isNotEmpty(projects.getOrgLocationsId().getOrg().getOrgType()) && !projects.getOrgLocationsId().getOrg().getOrgType().equalsIgnoreCase(Constants.GUEST)) {
						orgLocationId = projects.getOrgLocationsId().getId();
						isGlobalAccess = "N";
					}else {
						orgLocationId = orgLocations.getId();
						isGlobalAccess = "N";
					}
				}
				
				List<ExpensesCategory>  expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndStatus(orgLocationId, Constants.MEMBER_ACTIVE,isGlobalAccess);
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
					//String expenseId = addExpensesService.getExpenseId(pc.getProjectId().getOrgLocationsId().getId());/*OLD CODE*/
					String expenseId = addExpensesService.getExpenseId(orgLocations);
					projectDTO.setExpenseId(expenseId);
				}
				ArrayList<ProjectMembersDTO> pmDTOList = new ArrayList<>();
				for (ProjectMembers pm : projectMembersList) {
					ProjectMembersDTO pmDTO =ProjectMemberDTOConverter.getProjectMembersDTOFromProjectMembers(pm);
					
					 ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm.getId());
					 if(pmh != null) {
						 Date pmStartDate = pmh.getProjectAssignedDate();
						 Date pmEndDate =  null;
						 if(pmh.getProjectTerminationDate() != null) {
							 pmEndDate = pmh.getProjectTerminationDate();
						 }else if(pmh.getProjectMemberId().getProjects().getEndDate() != null)	{
							 pmEndDate = pmh.getProjectMemberId().getProjects().getEndDate();
						 }
						   pmDTO.setMemberStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmStartDate));
						 if(null != pmEndDate){
							 pmDTO.setMemberEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmEndDate));
						  }
					 }
					
				/*	
					HashMap<String, Date> dateMap = projectMembersHistoryService.getProjectMembersStartDateAndEndDateByProjectMemberId(pm.getId(),"");
					 if(dateMap != null && !dateMap.isEmpty()) {
						 Date pmStartDate = dateMap.get("STARTDATE");
						 Date pmEndDate =  dateMap.get("ENDDATE");
						   pmDTO.setMemberStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmStartDate));
						 if(null != pmEndDate){
							 pmDTO.setMemberEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmEndDate));
						  }
					 }*/
					pmDTOList.add(pmDTO);
					
				}
				projectDTO.setProjectMembersList(pmDTOList);
			    if(pc.getOrgRelationId() != null){
			    	 if("Vendor".equalsIgnoreCase(pc.getOrgRelationId().getInitiatorType())){
			    		 projectDTO.setClient(pc.getOrgRelationId().getResponderLocationId().getOrgLocationName());
					 }else if("Client".equalsIgnoreCase(pc.getOrgRelationId().getInitiatorType())){
						 projectDTO.setClient(pc.getOrgRelationId().getLocationId().getOrgLocationName());
					 }
			    	 
			    	 OrgRelations orgRelations = orgRelationsService.getOrgRelationById(pc.getOrgRelationId().getId());
			    	 projectDTO.setOrgRelationUUID(orgRelations.getUUID());
			    }
				projectsList.add(projectDTO);
			}
 			expenseEntryDetailsDTO.setProjectsList(projectsList);
		}
		else{
			Candidate candidate = candidateService.getCandidateByConsultantUserId(eedDTO.getUserId(), orgLocations.getId());
			List<ProjectMembers> projectMembersList = projectMembersService.getProjectMembersListByOrgLocationsIdAndCandidateId(orgLocations.getId(),orgLocations.getOrg().getId(),candidate.getId());
			if (projectMembersList != null && !projectMembersList.isEmpty()) {
				
				ArrayList<ProjectMembersDTO> pmDTOList = new ArrayList<>();
				for (ProjectMembers pm : projectMembersList) {
					
					if(StringUtils.isEmpty(eedDTO.getExpenseId()) && StringUtils.isNotEmpty(pm.getProjects().getIsProjectForExpenses()) && pm.getProjects().getIsProjectForExpenses().equalsIgnoreCase("N")) 
						continue;
					
					ProjectMembersDTO pmDTO =ProjectMemberDTOConverter.getProjectMembersDTOFromProjectMembers(pm);
					
					if(pm.getProjects()!= null) {
						if(pm.getProjects().getStartDate()!= null){
							pmDTO.setProjectStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pm.getProjects().getStartDate()));
						}if(pm.getProjects().getEndDate()!= null){
							pmDTO.setProjectEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pm.getProjects().getEndDate()));
						}
					}
					Long orgLocationId = null;
					String isGlobalAccess = eedDTO.getIsGlobalUser();
					if(StringUtils.isNotEmpty(eedDTO.getExpenseId())){
						OrgLocations expLoc = orgLocationService.getOrgLocationsByLocationCode(eedDTO.getExpenseLocationCode());
						if(expLoc != null) {
							orgLocationId = expLoc.getId();
						}
						isGlobalAccess = "N";
					}else {
						if(pm.getProjects().getOrgLocationsId() != null && pm.getProjects().getOrgLocationsId().getOrg() != null && StringUtils.isNotEmpty(pm.getProjects().getOrgLocationsId().getOrg().getOrgType()) && !pm.getProjects().getOrgLocationsId().getOrg().getOrgType().equalsIgnoreCase(Constants.GUEST)) {
							orgLocationId = pm.getProjects().getOrgLocationsId().getId();
							isGlobalAccess = "N";
						}else {
							orgLocationId = orgLocations.getId();
							isGlobalAccess = "N";
						}
					}
					List<ExpensesCategory> expensesCategoryList = expensesCategoryService.getAllExpensesCategoryByLocationIdAndStatus(orgLocationId, "1",isGlobalAccess);
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
						OrgLocations projLoc = orgLocationService.getOrgLocations(orgLocationId);
						String expenseId = addExpensesService.getExpenseId(projLoc);
						pmDTO.setExpenseId(expenseId);
					}
					 if(pm.getProjectClientsId() != null &&  pm.getProjectClientsId().getOrgRelationId() != null){
				    	 if("Vendor".equalsIgnoreCase(pm.getProjectClientsId().getOrgRelationId().getInitiatorType())){
				    		 pmDTO.setClientName(pm.getProjectClientsId().getOrgRelationId().getResponderLocationId().getOrgLocationName());
						 }else if("Client".equalsIgnoreCase(pm.getProjectClientsId().getOrgRelationId().getInitiatorType())){
							 pmDTO.setClientName(pm.getProjectClientsId().getOrgRelationId().getLocationId().getOrgLocationName());
						 }
				    	 
				    	 OrgRelations orgRelations = orgRelationsService.getOrgRelationById(pm.getProjectClientsId().getOrgRelationId().getId());
				    	 pmDTO.setOrgRelationUUID(orgRelations.getUUID());
				    }
					 ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pm.getId());
					 if(pmh != null) {
						 Date pmStartDate = pmh.getProjectAssignedDate();
						 Date pmEndDate =  null;
						 if(pmh.getProjectTerminationDate() != null) {
							 pmEndDate = pmh.getProjectTerminationDate();
						 }else if(pmh.getProjectMemberId().getProjects().getEndDate() != null)	{
							 pmEndDate = pmh.getProjectMemberId().getProjects().getEndDate();
						 }
						   pmDTO.setMemberStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmStartDate));
						 if(null != pmEndDate){
							 pmDTO.setMemberEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pmEndDate));
						  }
					 }
					 pmDTO.setProjectId(pm.getProjects().getId());
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
		paidBy.add("Company");
		expenseEntryDetailsDTO.setPaidByType(paidBy);
		return expenseEntryDetailsDTO;
	}
	/**
	 *  This method serves request for Edit Expense Sreen
	 * @param eedDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/rest/getExpenseDetailsByExpenseId", method = { RequestMethod.GET, RequestMethod.POST })
	public ExpensesDTO getExpenseDetailsByExpenseId(@RequestBody ExpensesListUtilDTO elDTO) throws Exception
	{
		ExpensesDTO expensesDTO = null;
		OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(elDTO.getExpenseLocationCode());
		if(orgLocations != null) {
			 Expenses expenses=  expensesService.getExpenseByExpenseUniqueId(elDTO.getExpenseId(), orgLocations.getId(),elDTO.getIsGlobalAccess());
			//loginSessionController.DATEFORMAT= new SimpleDateFormat(orgLocations.getDateFormat());
			if (expenses != null) {
				 OrgLocations loginLoc = orgLocationService.getOrgLocationsByLocationCode(elDTO.getLocationCode());
				expensesDTO = ExpensesDTOConverter.getExpensesDTOFromExpenses(loginLoc,expenses, expensesDTO);
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
		try{
		File file = null;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Expenses ExpenseDetails = expensesService.getExpenseById(Long.parseLong(Expid));
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		if (ExpenseDetails != null) {
			if (type.equalsIgnoreCase("PDF")) {
				file = expensesService.exportMyExpensesToPdf(ExpenseDetails, orgLoc, loginUser, ApproverType,isGlobalAccess);
			}
			else {
				file = expensesService.exportMyExpensesToExcel(ExpenseDetails, orgLoc, loginUser, ApproverType,isGlobalAccess);
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/exportAllMyExpensesList")
	public void exportAllMyExpensesList(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "expenseStatus", required = false) String expenseStatus, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate) throws Exception
	{
		try{
		File file = null;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		ResponseList<Expenses> expensesList = expensesService.getAllExpenses(orgLoc, loginUser, expenseStatus, startDate, endDate,null,0,0);
		if (expensesList != null) {
			for (Expenses ex : expensesList.getData()) {
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
				file = expensesService.exportAllMyExpensesToPdf(expensesList.getData(), orgLoc, loginUser,isGlobalAccess);
			}
			else {
				file = expensesService.exportAllMyExpensesToExcel(expensesList.getData(), orgLoc,isGlobalAccess);
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//unusedMethod
	@RequestMapping(value = "/myExpensesListByStage",method= RequestMethod.GET)
	public ResponseList<Expenses> myExpensesListByStage(HttpServletRequest request, String expenseStatus) throws Exception
	{
		ResponseList<Expenses> responceExpenses = new ResponseList<>();
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		List<Expenses> expensesList = expensesService.getTotalMyExpensesListByMasterCandidateIdForEmployeeLogin(candidate.getMasterCandidateId().getId(), expenseStatus);
		for (Expenses ex : expensesList) {
			ProjectMembers pm= projectMembersService.getProjectMembersByMasterCandidateIdAndProjectIdLocationId(ex.getMasterCandidateId().getId(),ex.getProjects().getId(),orgLoc.getId(),isGlobalAccess);
			if(pm != null) {
				if (null != ex.getProjectMembers()) {
					ex.setProjectName(pm.getProjects().getProjectName());
				}
				if(pm.getProjectClientsId()!=null && pm.getProjectClientsId().getOrgRelationId()!= null){
					ex.setClientName(pm.getProjectClientsId().getOrgRelationId().getClientName());	
	            }
			}
			if (null != ex.getStartDate()) {
				ex.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),ex.getStartDate()));
			}
			if (null != ex.getEndDate()) {
				ex.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),ex.getEndDate()));
			}
			if (null != orgLoc.getLocationCode()) {
				ex.setExpenseLocationCode(orgLoc.getLocationCode());
			}
		}
		responceExpenses.setData(expensesList);
		return responceExpenses;
	}
	
	//unused method
	@RequestMapping(value = "/employeeExpensesListByStage")
	public List<ExpensesApproverHierarchy> employeeExpensesListByStage(HttpServletRequest request, String expenseStatus) throws Exception
	{
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesService.getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(loginUser.getId(), orgLoc.getId(), expenseStatus,isGlobalAccess);
		for (ExpensesApproverHierarchy expensesApproverHierarchy : expApperoverHierarchy) {
			if (null != expensesApproverHierarchy.getExpenseId().getEndDate()) {
				expensesApproverHierarchy.setExpenseStartDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getStartDate()));
				expensesApproverHierarchy.setExpenseEndDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getEndDate()));
			}
			else {
				expensesApproverHierarchy.setExpenseStartDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getStartDate()));
			}
			if (null != expensesApproverHierarchy.getExpenseId()) {
				
				if(expensesApproverHierarchy.getExpenseId().getPurpose()!=null){
					expensesApproverHierarchy.setExpensesApproverHierarchyPurpose(expensesApproverHierarchy.getExpenseId().getPurpose());
				}else{
					expensesApproverHierarchy.setExpensesApproverHierarchyPurpose("--");
				}
				if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers()) {
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects()){
								expensesApproverHierarchy.setExpensesProjectName(expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects().getProjectName());
							}else{
								expensesApproverHierarchy.setExpensesProjectName("--");
							}
							
					 ProjectMembers projectMember = expensesApproverHierarchy.getExpenseId().getProjectMembers();
					 ProjectMembers pmWithUser = projectMembersService.getConsultantUserProjectMemberByProjectMemberId(projectMember.getId());
					if (null !=projectMember.getProjectMemberCandidteId()) {
							if(null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType())){
								String empType = projectMember.getProjectMemberCandidteId().getDesignationType();
								if(empType.equalsIgnoreCase("C2C") || empType.equalsIgnoreCase("C2C-1099")){
									OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
									expensesApproverHierarchy.setUserTypeVendorName(orgRelation.getResponderLocationId().getOrgLocationName());
									expensesApproverHierarchy.setExpenseEmpUserType(empType);
								 }else{
									 expensesApproverHierarchy.setExpenseEmpUserType(empType);
								 }
							}else{
								expensesApproverHierarchy.setExpenseEmpUserType("--");
							}
						
						if (null != projectMember.getProjectMemberCandidteId()) {
							expensesApproverHierarchy.setExpenseEmpUserFirstNameLastName(projectMember.getProjectMemberCandidteId().getFullName());
							expensesApproverHierarchy.setExpenseEmpUserEmail(projectMember.getProjectMemberCandidteId().getEmail());
							if(pmWithUser != null && null !=  pmWithUser.getProjectMemberCandidteId().getConsultantUser()){
								expensesApproverHierarchy.setExpensesUserid(pmWithUser.getProjectMemberCandidteId().getConsultantUser().getId());
								expensesApproverHierarchy.setUserFileUrl(pmWithUser.getProjectMemberCandidteId().getConsultantUser().getFileUrl());
							}
						}
						
						if (null != expensesApproverHierarchy.getExpenseId()) {
							if(null != expensesApproverHierarchy.getExpenseId().getPurpose()){
								expensesApproverHierarchy.setExpensesApproverHierarchyPurpose(expensesApproverHierarchy.getExpenseId().getPurpose());
							}else{
								expensesApproverHierarchy.setExpensesApproverHierarchyPurpose("-----");
							}
						}
					}
				}
			}
			expensesApproverHierarchy.setExpenseLocationCode(orgLoc.getLocationCode());
		}
		return expApperoverHierarchy;
	}
	
	

	@RequestMapping(value = "/updateBillingStatus")
	@ResponseBody
	public Response<Boolean> changeExpenseBillingStatus(HttpServletRequest request,@RequestParam(value = "expenseId", required = false) long expenseId, @RequestParam(value = "billingStatus", required = false) String billingStatus)
		{
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				response.setObject((Boolean) expensesService.changeExpenseBillingStatus(expenseId,billingStatus));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	
	
}