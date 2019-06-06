package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
//import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.dto.CandidateEmployerDTO;
import com.bis.sourcelead.dto.CandidateFamilyDetailsDTO;
import com.bis.sourcelead.dto.CandidateResumeDTO;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationLcaDTO;
import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.ImmigrationPassportDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateTaskDTO;
import com.bis.sourcelead.dto.ImmigrationVisaDTO;
import com.bis.sourcelead.dto.LcaDTO;
import com.bis.sourcelead.dto.LcaDetailsDTO;
import com.bis.sourcelead.dto.LcaListViewDTO;
import com.bis.sourcelead.dto.MyImmigrationViewDTO;
import com.bis.sourcelead.dto.ResidenceDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadNotesDTO;
import com.bis.sourcelead.dto.TaskDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.CandidatePassport;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationActivityHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationCandidateDocumentsHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateForms;
import com.bis.sourcelead.entity.ImmigrationDashboardView;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.Lca;
import com.bis.sourcelead.entity.LcaHistory;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.immigration.service.ImmigrationTemplateService;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SourceleadNotesService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.ImmigrationActivityCommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/immigration")
public class ImmigrationController {

	private static final Logger logger = LoggerFactory.getLogger(ImmigrationController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	private CountriesService countriesService;

	@Autowired
	private CandidateEducationService candidateEducationService;

	@Autowired
	private CandidateEmployersService candidateEmployersService;

	@Autowired
	private LookUpDataService lookUpDataService;

	@Autowired
	private ImmigrationTemplateService immigrationTemplateService;

	@Autowired
	private ImmigrationCommonService immigrationCommonService;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	CandidateResumesService candidateResumesService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	ImmigrationActivityCommonUtil immigrationActivityCommonUtil;

	@Autowired
	private RequirementService requirementService;

	@Autowired
	private SourceleadNotesService sourceleadNotesService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	
	@RequestMapping(value = { "/viewImmigration" })
	public ModelAndView viewImmigration(HttpServletRequest request,
			@RequestParam(value = "immiTemplateUUID", required = false) String immiTemplateUUID,
			@RequestParam(value = "candidateUUID", required = false) String candidateUUID,
			@RequestParam(value = "immigrationUUID", required = false) String immigrationUUID,
			@RequestParam(value = "myImmigrationPage", required = false) String myImmigrationPage
			) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		ImmigrationDTO immigrationDTO = null;
		if (StringUtils.isNoneBlank(immigrationUUID)) {
			Long immigrationId = immigrationCommonService.getImmigrationIdByUUID(immigrationUUID);
			Long candidateId = immigrationCommonService.getCandidateIdByUUID(candidateUUID);
			Long templateId = immigrationTemplateService.getTemplateIdByUUID(immiTemplateUUID);
			model.addObject("immigrationId", immigrationId);
			model.addObject("candidateId", candidateId);
			model.addObject("templateId", templateId);
			if (immigrationUUID != null && !StringUtils.isEmpty(immigrationUUID)) {
				immigrationDTO = immigrationCommonService.getImmigrationDetails(immigrationId.toString(), orgLocations);
			}
			List<LookUpData> uscisStatusList = immigrationTemplateService
					.getLookupDataByType(ImmigrationConstants.PETITION_STATUS);

			HashMap<String, Integer> tabRecordCountMap = null;
			if (candidateUUID != null) {
				candidateId = immigrationCommonService.getCandidateIdByUUID(candidateUUID);
				tabRecordCountMap = getImmigrationViewTabStatusDetails(candidateId, immigrationId);
			}
			model.addObject("tabRecordCountMap", tabRecordCountMap);
			model.addObject("uscisStatusList", uscisStatusList);
			model.addObject("immigration", immigrationDTO);
			model.addObject("myImmigrationPage", myImmigrationPage);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("/immigration/immigrationView");
		}
		return model;
	}

	// ====================immigration view startviewImmigration if you don't
	// required comment below code start here==============================
	@RequestMapping(value = { "/viewImmigrationList" })
	public ModelAndView loadImmigrationList(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = new SessionMaintainanceData();
		sessionData.setNav(Constants.Immigrations);

		// CandidatePassportVisa candidatePassportVisa =
		// immigrationCommonService.getCandidatePassportVisaByIdAndIdNumber(id,
		// idNumber)

		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/immigration/immigrationList");
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		OrgLocations orgLocation =orgLocationService.getOrgLocations(orgLocations.getId());
		model.addObject("orgLocationInfo", orgLocation);
		
		try {
			ImmigrationDashboardView immDashboardView = immigrationCommonService
					.getImmigrationDashBoardDetails(orgLocations);
			model.addObject("dashboardResult", immDashboardView);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = { "/myImmigration" })
	public ModelAndView loadMyImmigration(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		SessionMaintainanceData sessionData = new SessionMaintainanceData();
		sessionData.setNav(Constants.My_Immigration);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/immigration/myImmigration");
		return model;
	}

	@RequestMapping(value = { "/loadNewPetition" })
	public ModelAndView loadNewPetition(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/immigration/newPetition");
		return model;
	}

	@RequestMapping(value = { "/loadNewPetitionPage" })
	public ModelAndView loagNewPetitionPageView(HttpServletRequest request,
			@RequestParam(value = "forward", required = false) String forward,
			@RequestParam(value = "immigrationUUID", required = false) String immigrationUUID) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		if (forward.equalsIgnoreCase("petitionDetails")) {
			model.setViewName("/immigration/petitionDetails");
			Long orgLocationsId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"orgLocationId");
			OrgLocations orgLocation =orgLocationService.getOrgLocations(orgLocationsId);
			SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocation);
			model.addObject("orgLocationInfo", orgLocation);
			if (!StringUtils.isEmpty(immigrationUUID)) {
				Long immigrationId = immigrationCommonService.getImmigrationIdByUUID(immigrationUUID);
				ImmigrationDTO immigrationDTO = immigrationCommonService.editImmigrationDetails(immigrationId,
						orgLocation);
				model.addObject("immigrationDTO", immigrationDTO);
				List<ImmigrationTemplate> templateList = immigrationCommonService.getImmigrationTemplateList(orgLocation,null);
				model.addObject("templateList", templateList);
			}else {
				List<ImmigrationTemplate> templateList = immigrationCommonService.getImmigrationTemplateList(orgLocation, 1L);
				model.addObject("templateList", templateList);
			}
			
		} else if (forward.equalsIgnoreCase("two")) {
			model.setViewName("/immigration/newPetitionPage2");
		} else if (forward.equalsIgnoreCase("three")) {
			model.setViewName("/immigration/newPetitionPage3");
		} else if (forward.equalsIgnoreCase("four")) {
			model.setViewName("/immigration/newPetitionPage4");
		} else if (forward.equalsIgnoreCase("five")) {
			model.setViewName("/immigration/newPetitionPage5");
		} else if (forward.equalsIgnoreCase("six")) {
			model.setViewName("/immigration/newPetitionPage6");
		} else if (forward.equalsIgnoreCase("seven")) {
			model.setViewName("/immigration/newPetitionPage7");
		}
		return model;
	}

	@RequestMapping(value = { "/getAllActiveCandidatesByLocation" }, method = RequestMethod.GET)
	public @ResponseBody List<Map<String, BigInteger>> getAllActiveCandidatesByLocation(
			@RequestParam(value = "locationIdVal", required = false) Long locationId, @RequestParam(value = "name", required = false) String name,  HttpServletRequest request) {
		List<Map<String, BigInteger>> candidateList = null;
		try {
			if("".equals(locationId) || locationId == null) {
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
						"orgLocations");
				locationId = orgLocations.getId();
			}
			candidateList = candidateService.getAllActiveCandidatesByLocationName(locationId, name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Gson gson=new Gson();
		// gsondata=gson.toJson(candidateList);//.replace("\\", "\\\\");
		return candidateList;
	}

	@RequestMapping(value = "/getImmigrationViewList")
	public @ResponseBody ResponseList<ImmigrationListViewDTO> getAllImmigartionList(
			ImmigrationListViewDTO immigrationListDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "filterAttorneyVal", required = false) Long[] filterAttorneyVal,
			@RequestParam(value = "filterTypeVal", required = false) Long[] filterTypeVal,
			@RequestParam(value = "filterStatusVal", required = false) Long[] filterStatusVal,
			@RequestParam(value = "filterDurationVal", required = false) Long[] filterDurationVal,
			@RequestParam(value = "orgTypeVal", required = false) String orgTypeVal, HttpServletRequest request) {
		ResponseList<ImmigrationListViewDTO> immigrationListDTOList = null;
		logger.debug("inisde controller  getAllImmigartionList  --> ");
		try {
			String[] tableColumns = { "currUscisStatus", "generatedId", "country", "petitionType", "firstName", "email",
					"phone", "openDate", "orgName", "lcaStartDate", "lcaEndDate" };// should be
			// entity names
			int sortColumnIndex = -1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default ascending order
			if (immigrationListDTO.getOrder() != null && !immigrationListDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(immigrationListDTO.getOrder().get(0).get("column"));
				sortColumnOrder = immigrationListDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means
				immigrationListDTO.setSortOrder(sortColumnOrder); // direction
				// of the sorting.
			}
			Map<String, Long[]> filterVal = new HashMap<String, Long[]>();
			if (filterAttorneyVal.length != 0) {
				filterVal.put("attorney", filterAttorneyVal);
			}
			if (filterTypeVal.length != 0) {
				filterVal.put("type", filterTypeVal);
			}
			if (filterStatusVal.length != 0) {
				filterVal.put("status", filterStatusVal);
			}
			if (filterDurationVal.length != 0) {
				filterVal.put("duration", filterDurationVal);
			}
			if (sortColumnIndex != -1)
				immigrationListDTO.setSortColumn(tableColumns[sortColumnIndex]);

			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			immigrationListDTO.setOrgLocationId(orgLocations.getId());
			immigrationListDTOList = immigrationCommonService.getImmigrationsList(immigrationListDTO, searchValue,
					orgLocations, filterVal, orgTypeVal);
			// commonUtil.highLightMenu(Constants.Immigration, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("after controller  getAllImmigartionList  --> ");
		return immigrationListDTOList;
	}

	@RequestMapping(value = "/getImmigrationDashBoardFilterList")
	public @ResponseBody ResponseList<ImmigrationListViewDTO> getImmigrationDashBoardFilterList(
			ImmigrationListViewDTO immigrationListDTO,
			@RequestParam(value = "filterStatus", required = false) String filterStatus, HttpServletRequest request) {
		ResponseList<ImmigrationListViewDTO> immigrationListDTOList = null;
		logger.debug("inisde controller  getMyImmigrationList  --> ");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			String[] tableColumns = { "currUscisStatus", "generatedId", "petitionType", "country", "firstName", "email",
					"phone", "openDate", "lcaStartDate", "lcaEndDate" };// should be
			// entity names
			int sortColumnIndex = 0; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default ascending order
			if (immigrationListDTO.getOrder() != null && !immigrationListDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(immigrationListDTO.getOrder().get(0).get("column"));
				sortColumnOrder = immigrationListDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means
																					// direction
				// of the sorting.
			}
			immigrationListDTO.setSortColumn(tableColumns[sortColumnIndex]);
			immigrationListDTO.setSortOrder(sortColumnOrder);
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			immigrationListDTO.setOrgLocationId(orgLocations.getId());
			immigrationListDTOList = immigrationCommonService.getImmigrationsListByCountsLink(immigrationListDTO,
					filterStatus, orgLocations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("after controller  getAllImmigartionList  --> ");
		return immigrationListDTOList;
	}

	@RequestMapping(value = "/getMyImmigrationList")
	public @ResponseBody ResponseList<MyImmigrationViewDTO> getMyImmigrationList(
			MyImmigrationViewDTO immigrationListDTO, HttpServletRequest request) {
		ResponseList<MyImmigrationViewDTO> immigrationListDTOList = null;
		logger.debug("inisde controller  getMyImmigrationList  --> ");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			String[] tableColumns = { "currUscisStatus", "generatedId", "petitionType", "country", "firstName", "email",
					"phone", "openDate", "lcaStartDate", "lcaEndDate" };// should be
			// entity names
			int sortColumnIndex = 0; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default ascending order
			if (immigrationListDTO.getOrder() != null && !immigrationListDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(immigrationListDTO.getOrder().get(0).get("column"));
				sortColumnOrder = immigrationListDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means
																					// direction
				// of the sorting.
			}
			immigrationListDTO.setSortColumn(tableColumns[sortColumnIndex]);
			immigrationListDTO.setSortOrder(sortColumnOrder);
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			immigrationListDTO.setOrgLocationId(orgLocations.getId());
			immigrationListDTOList = immigrationCommonService.getMyImmigrationsList(immigrationListDTO, orgLocations,
					loginUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("after controller  getAllImmigartionList  --> ");
		return immigrationListDTOList;
	}

	@RequestMapping(value = { "/getPetitionTemplateEmployers" })
	public List<CandidateEmployerDTO> getPetitionTemplateEmployers(HttpServletRequest request) {
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		List<CandidateEmployerDTO> caEmployerDTOs = immigrationTemplateService
				.getPetitionTemplateEmployers(candidate.getId());
		return caEmployerDTOs;
	}

	@RequestMapping(value = { "/getEmploymentTypeList" })
	public List<LookUpData> getEmploymentTypeList(HttpServletRequest request) {
		List<LookUpData> caEmployerDTOs = immigrationTemplateService
				.getLookupDataByType(ImmigrationConstants.EMPLOYMENT_TYPE);
		return caEmployerDTOs;
	}

	
	
	@RequestMapping(value = { "/uploadRequstedDoc" }, method = RequestMethod.POST)
	public String uploadRequstedDoc(ImmigrationTemplateDocDTO immigrationTemplateDocDTO,
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "immigrationId", required = true) Long immigrationId, HttpServletRequest request) {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		immigrationTemplateDocDTO.setStaticPath(staticPath);
		try {
			immigrationCommonService.uploadRequstedDocForCandidate(immigrationTemplateDocDTO, id, candidateId,
					immigrationId, user, orgLocations);

		} catch (Exception e) {
			logger.error("Exception caught in controller --->" + e.getMessage());
			e.printStackTrace();
		}

		return "success";
	}

	@RequestMapping(value = { "/uploadRequstedForm" }, method = RequestMethod.POST)
	public String uploadRequstedForm(ImmigrationTemplateFormDTO immigrationTemplateFormDTO,
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "immigrationId", required = true) Long immigrationId, HttpServletRequest request) {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		immigrationTemplateFormDTO.setStaticPath(staticPath);
		try {
			immigrationCommonService.uploadRequstedFormForCandidate(immigrationTemplateFormDTO, id, candidateId,
					immigrationId, user, orgLocations);

		} catch (Exception e) {
			logger.error("Exception caught in controller --->" + e.getMessage());
			e.printStackTrace();
		}

		return "success";
	}

	@RequestMapping(value = { "/viewImmigrationById" })
	public ModelAndView getImmigrationDeatilsById(HttpServletRequest request,
			@RequestParam(value = "immigrationId", required = true) String immigrationId) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		ImmigrationDTO immigrationDTO = null;
		if (immigrationId != null && !StringUtils.isEmpty(immigrationId)) {
			immigrationDTO = immigrationCommonService.getImmigrationDetails(immigrationId, orgLocations);
		}
		model.addObject("immigration", immigrationDTO);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/immigration/petitionDetailsTab");
		return model;
	}

	// ============= immigration view End. if you don't required comment above code
	// end here============================
	@RequestMapping(value = { "/gotoTemplatePreviousPage" })
	public ModelAndView loagNewPetitionPageOne(HttpServletRequest request,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "templateId", required = false) Long templateId) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		try {
			if (status.equalsIgnoreCase("templateDetails")) {
				if (templateId != null) { // this code for edit mode.
				  ImmigrationTemplateDTO templateDTO = immigrationTemplateService.getPetitionTemplateDetails(templateId);
				   model.addObject("templateDTO", templateDTO);
				   request.getSession().setAttribute("templateDTO", templateDTO);
				    model.setViewName("/immigration/petitionTemplateDetails");
				}
			}
			if (status.equalsIgnoreCase("tempDetails")) {
				model.setViewName("/immigration/petitionTemplateDetails");
			} else if (status.equalsIgnoreCase("docDetails")) {
				
				List<LookUpData> educationsList = null;
				educationsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE); // for populating
				
				List<LookUpData> attorneyDocsList = lookUpDataService.getLookUpDataByCategory(Constants.ATTORNEY_CATEGORY);
				
				model.addObject("attorneyDocsList", attorneyDocsList);
				
				List<LookUpData> petitionerDocsList = lookUpDataService.getLookUpDataByCategory(Constants.PETITIONER_CATEGORY);
				model.addObject("petitionerDocsList", petitionerDocsList);
				if(templateId != null) { // this code for edit mode.
					List<ImmigrationTemplateDocDTO> docsList = immigrationTemplateService.getImmigrationTemplateDocsListById(templateId);
				
					request.getSession().setAttribute("templateDocList", docsList);
				}
				model.addObject("educationsList", educationsList);
				
				model.setViewName("/immigration/petitionTemplateDocDetails");
			} else if (status.equalsIgnoreCase("formDetails")) {
				if (templateId != null) { // this code for edit mode.
					List<ImmigrationTemplateFormDTO> formsList = immigrationTemplateService.getImmigrationTemplateformsListById(templateId);
					request.getSession().setAttribute("templateFormsList", formsList);
				}
				model.setViewName("/immigration/petitionTemplateFormDetails");
			} else if (status.equalsIgnoreCase("taskList")) {
				model.setViewName("/immigration/immigrationTemplateTaskList");
			}
		} catch (Exception e) {
			logger.error("Exception occured in controller while navigating-->");
			e.printStackTrace();
		}
		return model;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADD_PETITION_TEMPLATE','ROLE_EDIT_VIEW_PETITION_TEMPLATE')")
	@RequestMapping(value = { "/addNewPetitionTemplate" })
	public ModelAndView loadAddPetitionTemplate(HttpServletRequest request) throws ServletRequestBindingException {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		request.getSession().removeAttribute("templateDTO");
		request.getSession().removeAttribute("templateDocList");
		request.getSession().removeAttribute("templateFormsList");
		model.setViewName("/immigration/addPetitionTemplate");

		String temlateUuid = ServletRequestUtils.getStringParameter(request, "templateUuid");
		Long templateId = immigrationTemplateService.getTemplateIdByUUID(temlateUuid);

		if (templateId != null) { // below code is used for edit template name, docs, forms when opened in edit
			// mode.
			try {
				ImmigrationTemplateDTO templateDTO = immigrationTemplateService.getPetitionTemplateDetails(templateId);
		/*		List<ImmigrationTemplateDocDTO> docsList = immigrationTemplateService.getImmigrationTemplateDocsListById(templateId);
				for(ImmigrationTemplateDocDTO  itd:docsList) {
					itd.setDocumentCategory(itd.getDocumentCategory());
				}
				logger.debug(
						" Edit mode templateId templateId -->" + templateId + ", docsList size is " + docsList.size());
				List<ImmigrationTemplateFormDTO> formsList = immigrationTemplateService.getImmigrationTemplateformsListById(templateId);
				logger.debug(
						" Edit mode templateId templateId -->" + templateId + ", formsList size i " + formsList.size());
				 templateDTO.setImmigrationDocList(docsList);*/
				/*request.getSession().setAttribute("templateDocList", docsList);
				request.getSession().setAttribute("templateFormsList", formsList);*/
				request.getSession().setAttribute("templateDTO", templateDTO);
			} catch (Exception e) {
				logger.error(" Exception caught in edit mode in controller -->" + templateId);
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = { "/viewImmigrationTemplateList" })
	public ModelAndView loadViewPetitionTemplate(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/immigration/immigrationTemplateList");
		/*
		 * try { // List<ImmigrationTemplateView> templateList=
		 * immigrationTemplateService.getAllTemplateDetails();
		 * model.addObject("templateList", templateList); } catch (Exception e) {
		 * logger.error("Exception occured while getting template list-->"+e.getMessage(
		 * )); e.printStackTrace(); }
		 */
		return model;
	}

	@RequestMapping(value = "/getImmigrationTemplateList")
	public @ResponseBody ResponseList<ImmigrationTemplateListDTO> getAlltemplateList(
			ImmigrationTemplateListDTO templateListDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "countriesList", required = false) String[] countriesList,
			HttpServletRequest request) {
		ResponseList<ImmigrationTemplateListDTO> templateListDTOList = null;
		try {
			String[] tableColumns = { "name", "country", "petitionType", "description", "docsCount", "formsCount",
					"tasksCount", "status", "id" };// should be
			// entity names
			int sortColumnIndex = -1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default ascending order
			if (templateListDTO.getOrder() != null && !templateListDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(templateListDTO.getOrder().get(0).get("column"));
				sortColumnOrder = templateListDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means direction
				templateListDTO.setSortOrder(sortColumnOrder);
				// of the sorting.
			}
			if (sortColumnIndex != -1) {
				templateListDTO.setSortColumn(tableColumns[sortColumnIndex]);
			}
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");

			templateListDTOList = immigrationTemplateService.getAllTemplateDetails(templateListDTO, searchValue,
					orgLocations.getId(), countriesList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return templateListDTOList;
	}

	@PreAuthorize("hasRole('ROLE_DELETE_PETITION_TEMPLATE')")
	@RequestMapping(value = "/deleteImmigrationTemplateDetatils")
	public String deleteTemplateDetailsById(@RequestParam(value = "temlateUuid", required = true) String temlateUuid,
			@RequestParam(value = "reason", required = true) String reason, HttpServletRequest request) {
		Long deleteId = immigrationTemplateService.getTemplateIdByUUID(temlateUuid);
		try {

			immigrationTemplateService.deleteAllPetitionTemplateDetailsById(deleteId, reason);// only soft delete
																								// operation in
			// Database.
			// "+deleteId);
			logger.debug("inside delete controller method calling ----->>>>>>>: " + deleteId);
		} catch (Exception e) {
			logger.error(" Exception while deleting template deitails in controller -->" + deleteId);
			e.printStackTrace();
		}
		return "success";
	}

	@RequestMapping(value = "/reactiveImmigrationTemplateDetailsById")
	public String reactiveImmigrationTemplateDetailsById(
			@RequestParam(value = "templateUuid", required = true) String templateUuid, HttpServletRequest request) {

		Long templateId = immigrationTemplateService.getTemplateIdByUUID(templateUuid);

		try {
			immigrationTemplateService.reactiveImmigrationTemplateById(templateId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	@RequestMapping(value = { "/addPetitionTemplate" })
	public ModelAndView addPetitionTemplatePage(HttpServletRequest request,
			@RequestParam(value = "forwardTo", required = false) String forwardTo) {
		ModelAndView model = new ModelAndView();
		try {
			model.addObject("sourceleadUrl", sourceleadUrl);
			if (forwardTo.equalsIgnoreCase("tempDetails")) {
				model.setViewName("/immigration/petitionTemplateDetails");
				// List<Countries> countryList = countriesService.getAllCountries();
				// List<LookUpData> petitionTypeList =
				// immigrationTemplateService.getLookupDataByType(Constants.PETITION_TYPE);
				// model.addObject("countriesList", countryList);
				// model.addObject("petitionTypeList", petitionTypeList);
			} else if (forwardTo.equalsIgnoreCase("docDetails")) {
				List<LookUpData> educationsList = null;
				educationsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE); // for populating
				// dropdown
				// values in UI.
				model.addObject("educationsList", educationsList);
				model.setViewName("/immigration/petitionTemplateDocDetails");
			} else if (forwardTo.equalsIgnoreCase("formDetails")) {
				model.setViewName("/immigration/petitionTemplateFormDetails");
			} else if (forwardTo.equalsIgnoreCase("taskList")) {
				model.setViewName("/immigration/immigrationTemplateTaskList");
			}

		} catch (Exception e) {
			logger.error("Exception occured in controller while navigating-->");
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = { "/saveImmigrationEmployerDetails" }, method = RequestMethod.POST)
	public Response<String> saveImmigrationEmployerDetails(CandidateEmployerDTO candidateEmployerDTO,
			HttpServletRequest request, @RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "immigrationId", required = false) String immigrationId) throws Exception {

		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrglocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		// immigrationTemplateService.savePetitionTemplateTaskDetails(templateDTO,
		// user);
		Candidate candidate = null;
		Immigration immigration = null;
		try {
			if (user != null && Validator.validate(candidateEmployerDTO)) {
				candidateEmployerDTO.setStaticPath(staticPath);
				candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candidateId));
				immigration = immigrationCommonService.getImmigrationById(Long.parseLong(immigrationId));
				candidateEmployersService.storeCandidateEmployers(candidateEmployerDTO, user, loginOrglocation,
						Long.parseLong(candidateId));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				StringBuilder actionDesc = new StringBuilder();
				if (StringUtils.isNotBlank(candidateEmployerDTO.getCandidateEmployeeId())) {
					actionDesc.append("Employer Details Updated").toString();
				} else {
					actionDesc.append("New Employer Details Added").toString();
				}
				immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, loginOrglocation, candidate,
						immigration, actionDesc.toString());
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = { "/saveCandidateImmigrationDocuments" }, method = RequestMethod.POST)
	public String saveCandidateImmigrationDocuments(ImmigrationTemplateDocDTO immigrationTemplateDocDTO,
			@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "immigrationId", required = false) String immigrationId,
			@RequestParam(value = "templateId", required = false) String templateId, HttpServletRequest request)
			throws Exception {
		immigrationTemplateDocDTO.setImmigrationId(immigrationId);
		immigrationTemplateDocDTO.setImmigrationcandidateId(candidateId);
		immigrationTemplateDocDTO.setImmigrationTempplateId(templateId);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrglocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		immigrationTemplateDocDTO.setStaticPath(staticPath);
		immigrationCommonService.saveImmigrationCandidateDocs(immigrationTemplateDocDTO, user, loginOrglocation);
		logger.debug("Template Task Details saved succesfully --> ");

		return "success";
	}

	@RequestMapping(value = { "/deleteDocumentDetailsByRowId" })
	public String deleteDocumentDetailsByRowId(@RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request) {
		String status = "";
		try {
			status = immigrationCommonService.deletetImmigrationCandidateDocsById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	@RequestMapping(value = { "/editDocumentDetailsByRowId" })
	public @ResponseBody ImmigrationCandidateDocuments editDocumentDetailsByRowId(
			@RequestParam(value = "id", required = true) Long id, HttpServletRequest request) {
		logger.debug(" in controller method getCandidateResidenceDetailsById id ---> " + id);
		ImmigrationCandidateDocuments immigDocObj = null;
		try {
			immigDocObj = immigrationCommonService.getImmigrationCandidateDocumentById(id);
		} catch (Exception e) {
			logger.error("Exception caught in controller test --->" + e.getMessage());
			e.printStackTrace();
		}

		return immigDocObj;
	}

	@RequestMapping(value = { "/saveCandidateImmigrationForms" }, method = RequestMethod.POST)
	public String saveCandidateImmigrationForms(ImmigrationTemplateFormDTO immigrationTemplateFormDTO,
			@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "immigrationId", required = false) String immigrationId,
			@RequestParam(value = "templateId", required = false) String templateId, HttpServletRequest request)
			throws Exception {
		immigrationTemplateFormDTO.setImmigrationId(immigrationId);
		immigrationTemplateFormDTO.setImmigrationcandidateId(candidateId);
		immigrationTemplateFormDTO.setImmigrationTempplateId(templateId);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrglocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		immigrationTemplateFormDTO.setStaticPath(staticPath);
		immigrationCommonService.saveImmigrationCandidateForms(immigrationTemplateFormDTO, user, loginOrglocation);
		logger.debug("Candidate Form  saved succesfully --> ");

		return "success";
	}

	@RequestMapping(value = { "/deleteImmigrationCandidateFormDetails" })
	public String deleteImmigrationCandidateFormDetails(@RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request) {
		String status = "";
		try {
			status = immigrationCommonService.deletetImmigrationCandidateFormsById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	@RequestMapping(value = { "/editImmigrationCandidateFormDetailsById" })
	public @ResponseBody ImmigrationCandidateForms editImmigrationCandidateFormDetailsById(
			@RequestParam(value = "id", required = true) Long id, HttpServletRequest request) {
		logger.debug(" in controller method getCandidateResidenceDetailsById id ---> " + id);
		ImmigrationCandidateForms immigFormObj = null;
		try {
			immigFormObj = immigrationCommonService.getImmigrationCandidateFormById(id);
		} catch (Exception e) {
			logger.error("Exception caught in controller test --->" + e.getMessage());
			e.printStackTrace();
		}

		return immigFormObj;
	}

	@RequestMapping(value = { "/deleteDocumentHistoryRecordById" })
	public @ResponseBody String deleteDocumentHistoryRecordById(
			@RequestParam(value = "recordDelete", required = true) Long recordDelete, HttpServletRequest request) {
		String status = "";
		try {
			status = immigrationCommonService.deleteDocumentHistoryRecordById(recordDelete);
		} catch (Exception e) {
			logger.error("Exception caught in controller test --->" + e.getMessage());
			e.printStackTrace();
		}

		return status;
	}

	@RequestMapping(value = { "/getCandidateImmigrationCandidateDocsList" })
	public ModelAndView getCandidateImmigrationCandidateDocsList(
			@RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "templateid", required = true) Long templateid,
			@RequestParam(value = "immigrationId", required = true) Long immigrationId,
			@RequestParam(value = "searchVal", required = false) String searchVal,
			@RequestParam(value = "myImmigration", required = false) String myImmigration, 
			@RequestParam(value = "myImmigrationPage" , required = true) String myImmigrationPage,	HttpServletRequest request)
			throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<ImmigrationCandidateDocuments> immigrationCanDocList = immigrationCommonService
				.getImmigrationCandidateDocs(immigrationId, templateid, candidateId, user, orgLocations, searchVal, myImmigration);
		for (ImmigrationCandidateDocuments docs : immigrationCanDocList) {
			LookUpData data = lookUpDataService.getTasksById(docs.getDocumentType().getId());
			docs.setDocumentTypeName(data.getName());
			HashMap<Long, String> fileIdNameMap = new HashMap<Long, String>();
			JSONArray jsonArray = new JSONArray();
			List<ImmigrationCandidateDocumentsHistory> docHistoryList = immigrationCommonService
					.getImmigrationCandidateDocumentHistoryList(immigrationId, candidateId, docs.getId());
			for (ImmigrationCandidateDocumentsHistory historyObj : docHistoryList) {

				fileIdNameMap.put(historyObj.getId(), historyObj.getUpldedFileName());
			}
			docs.setDocsHistoryList(jsonArray.put(fileIdNameMap));
			logger.debug("jsonArray to String  ===========" + jsonArray.toString());
			
	   Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
		if(docs.getDocCategory().equalsIgnoreCase("Document Type")){
			if(candidate.getConsultantUser().getId().equals(user.getId())) {
			model.addObject("beneficiaryDocs", docs.getDocCategory());
			}
		}else {
			model.addObject("beneficiaryDocs", docs.getDocCategory());
			if(docs.getDocCategory().equalsIgnoreCase("PetitionerCategory")){
				model.addObject("petitionerDocs", docs.getDocCategory());
			}
			
		if(docs.getDocCategory().equalsIgnoreCase("AttorneyCategory")){
				model.addObject("attorneyDocs", docs.getDocCategory());
			}
			
		}
			
		}
		
		model.setViewName("/immigration/documentDetailsTab");
		List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
		model.addObject("documentsList", documentsList);
		model.addObject("immigrationCanDocList", immigrationCanDocList);
		model.addObject("myImmigrationPage", myImmigrationPage);
		return model;
	}

	@RequestMapping(value = {
			"/savePetitionTemplateDetails" }, method = RequestMethod.POST, produces = "application/json")
	public Response<String> savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request,
			@RequestParam(value = "templateId", required = false) String templateId) {

		Response<String> response = new Response<>();
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		Long immigrationId = null;

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (user != null) {

			try {
				if (Validator.validate(templateDTO)) {
					immigrationId = immigrationTemplateService.savePetitionTemplateDetails(templateDTO, user,
							orgLocations, templateId);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
					response.setObject(immigrationId.toString());
					templateDTO.setId(immigrationId);
				} else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());

				}
			} catch (Exception e) {
				logger.error("Exception occured while saving Petition Template Deatils" + e.getMessage());
				e.printStackTrace();
			}
			/* return "{status:'success', id:" + immigrationId + "}"; */
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		request.getSession().setAttribute("templateDTO", templateDTO);
		return response;
	}

	@RequestMapping(value = { "/savePetitionTemplateDocDetails" }, method = RequestMethod.POST)
	public Response<String> savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO,
			HttpServletRequest request, @RequestParam(value = "templateId", required = false) String templateId) {
		Response<String> response = new Response<>();
		ModelAndView model = new ModelAndView();

		model.addObject("sourceleadUrl", sourceleadUrl);
		List<ImmigrationTemplateDocDTO> templateDocList = null;
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Boolean flag = false;
		if (user != null) {
			try {
				for (ImmigrationTemplateDocDTO doc : templateDTO.getImmigrationDocList()) {

					if (Validator.validate(doc)) {
						flag = true;
					} else {
						flag = false;
					}
				}
			} catch (Exception e) {
				logger.error("Exception occured while saving Petition Template Doc Details" + e.getMessage());
				e.printStackTrace();
			}
			if (flag == true) {
				templateDocList = immigrationTemplateService.savePetitionTemplateDocDetails(templateDTO, user,
						orgLocations);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setObject("DataMissing");
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		request.getSession().setAttribute("templateDocList", templateDocList);
		return response;
	}

	@RequestMapping(value = { "/savePetitionTemplateFormDetails" }, method = RequestMethod.POST)
	public Response<String> savePetitionTemplateFormDetails(ImmigrationTemplateDTO templateDTO,
			HttpServletRequest request) {
		Response<String> response = new Response<>();
		ModelAndView model = new ModelAndView();
		List<ImmigrationTemplateFormDTO> templateFormsList = null;
		model.addObject("sourceleadUrl", sourceleadUrl);
		templateDTO.setStaticPath(staticPath);
		Boolean flag = false;
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (user != null) {
			try {
				for (ImmigrationTemplateFormDTO form : templateDTO.getImmigrationFormList()) {

					if (Validator.validate(form)) {
						flag = true;
					} else {
						flag = false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flag == true) {
				templateFormsList = immigrationTemplateService.savePetitionTemplateFormDetails(templateDTO, user,
						orgLocations);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setObject("DataMissing");
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		request.getSession().setAttribute("templateFormsList", templateFormsList);
		return response;
	}

	@RequestMapping(value = { "/savePetitionTemplateTaskDetails" }, method = RequestMethod.POST)
	public Response<String> savePetitionTemplateTaskDetails(ImmigrationTemplateTaskDTO taskTemplateDTO,
			HttpServletRequest request) {
		Response<String> response = new Response<>();
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (user != null) {
			try {
				if (Validator.validate(taskTemplateDTO)) {
					immigrationTemplateService.savePetitionTemplateTaskDetails(taskTemplateDTO, user, orgLocations);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				logger.error("Exception occured while saving Template Task Details" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}

	@RequestMapping(value = { "/getTemplateTaskList" }, method = RequestMethod.POST)
	public @ResponseBody ResponseList<ImmigrationTemplateTaskDTO> getTemplateTaskList(
			ImmigrationTemplateTaskDTO templateDTO, HttpServletRequest request,
			@RequestParam(value = "templateId", required = false) String templateId,
			@RequestParam(value = "searchValue", required = false) String searchValue) throws Exception {
		ResponseList<ImmigrationTemplateTaskDTO> immiTemplateList = new ResponseList<>();
		String[] tableColumns = { "taskName", "assignedTo", "priority", "taskRepeat" };
		int sortColumnIndex = 3; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		templateDTO.setTemplateId(Long.parseLong(templateId));
		if (templateDTO.getOrder() != null && !templateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(templateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = templateDTO.getOrder().get(0).get("dir");
		}
		templateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		templateDTO.setSortOrder(sortColumnOrder);
		immiTemplateList = immigrationTemplateService.getTemplateTaskList(templateDTO, searchValue);
		return immiTemplateList;
	}

	@RequestMapping(value = { "/editImmigrationTaskList" })
	public ImmigrationTemplateTaskDTO editImmigrationTaskList(HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id) throws NumberFormatException, Exception {
		ImmigrationTemplateTaskDTO templateDTO = immigrationTemplateService.editImmigrationTaskList(id);
		return templateDTO;
	}

	@RequestMapping(value = { "/deleteImmigrationTaskList" })
	public void deleteImmigrationTaskList(HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "taskName", required = false) String taskName) {
		immigrationTemplateService.deleteImmigrationTaskList(id, taskName);
	}

	@RequestMapping(value = { "/savePetitionTemplateDetailsValues" })
	public String savePetitionTemplateDetailsValues(HttpServletRequest request,
			@RequestParam(value = "templateId", required = false) String templateId) {
		int value = immigrationTemplateService.savePetitionTemplateDetailsValues(templateId);
		String message = "failed";
		if (value != 0) {
			message = "success";
			request.getSession().setAttribute("success", message);
		}
		return message;
	}

	@RequestMapping(value = { "/getPetitionTypeDropDown" })
	public List<LookUpData> getPetitionTypeDropDown(HttpServletRequest request) {
		List<LookUpData> caEmployerDTOs = immigrationTemplateService.getLookupDataByType(Constants.VISA);
		return caEmployerDTOs;
	}

	@RequestMapping(value = { "/getUscisTypeDropDown" })
	public List<LookUpData> getUscisTypeDropDown(HttpServletRequest request) {
		List<LookUpData> caEmployerDTOs = immigrationTemplateService
				.getLookupDataByType(ImmigrationConstants.PETITION_STATUS);
		return caEmployerDTOs;
	}

	@RequestMapping(value = { "/getEmploymentLevelDropDown" })
	public List<LookUpData> getEmploymentLevelDropDown(HttpServletRequest request) {
		List<LookUpData> caEmployerDTOs = immigrationTemplateService
				.getLookupDataByType(ImmigrationConstants.EMPLOYMENT_LEVEL);
		return caEmployerDTOs;
	}

	@RequestMapping(value = { "/getJobTitleDropDown" })
	public List<Requirement> getJobTitleDropDown(@RequestParam(value = "locationId", required = false) Long locationId,
			HttpServletRequest request) throws Exception {
		List<Requirement> jobList = requirementService.getAllRequirementsByOrglocationId(locationId);
		return jobList;
	}

	@RequestMapping(value = { "/savePetitionDetailsPage" }, method = RequestMethod.POST)
	public ResponseList<String> savePetitionDetailsPage(ImmigrationDTO immigrationDTO,
			@RequestParam(value = "removedResidenceIds", required = false) String[] ids,
			@RequestParam(value = "status", required = false) String status, HttpServletRequest request) {
		ResponseList<String> response = new ResponseList<>();
		List<String> requiredIds = new LinkedList<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Org org =(Org) SourceLeadSessionManager.getSessionAttribute(request,"org");	
		orgLocations.setOrg(org);
		immigrationDTO.setStaticPath(staticPath);
		Immigration immigration = null;
		try {
			if (user != null && Validator.validate(immigrationDTO)) {
				immigration = immigrationCommonService.savePetitionDetails(immigrationDTO, user, orgLocations, ids);
				if (status.equalsIgnoreCase(ImmigrationConstants.SEND_REQUEST)) {
					immigrationCommonService.sendEmailToImmigrationBeneficiary(immigration, user, orgLocations);
					
				}
				
				
				requiredIds.add(immigration.getId().toString());
				requiredIds.add(immigration.getCandidate().getId().toString());
				requiredIds.add(immigration.getImmigrationTemplateId().getId().toString());
				requiredIds.add(immigration.getImmigrationUuid());
				requiredIds.add(immigration.getCandidate().getCandidateUUID());
				requiredIds.add(immigration.getImmigrationTemplateId().getUuid());
				response.setData(requiredIds);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		/*
		 * return "{result:'success',id:'" + immigration.getId() + "',candidateId:'" +
		 * immigration.getCandidate().getId() + "',immTemplateId:'" +
		 * immigration.getImmigrationTemplateId().getId() + "'}";
		 */
		return response;
	}

	@RequestMapping(value = "/validateTemplateName", method = RequestMethod.POST)
	@ResponseBody
	public boolean validateTemplateName(HttpServletRequest request,	@RequestParam(value = "templateName") String templateName,	@RequestParam(value = "templateId") Long templateId) {
		
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 ImmigrationTemplate template = null;
	       if(templateId != null){
	    	   template = immigrationTemplateService.getImmigrationTemplateByIdAndLocationId(templateName, templateId, orgLocation.getId());
	       }
	       Boolean flag = false;
		    if(template == null){
		    	flag =  immigrationTemplateService.validateTemplateNameExistance(templateName, orgLocation.getId());
		    }
			return flag;
		
	}

	@RequestMapping(value = { "/getCandidatesResumeDetailsById" })
	public @ResponseBody CandidateResumeDTO getCandidatesResumeDetailsById(
			@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request)
			throws Exception {
		Integer status = Constants.CANDIDATE_ACTIVE;
		CandidateResumeDTO candidateResumeDTO = new CandidateResumeDTO();
		if (candidateId != null) {
			List<CandidateResumes> candidateResumesList = candidateResumesService
					.getCandidateResumesListByCandidateIdAndStatus(candidateId, status);
			if (candidateResumesList != null && candidateResumesList.size() > 0) {
				CandidateResumes candidateResume = candidateResumesList.get(0);
				candidateResumeDTO = new CandidateResumeDTO();
				candidateResumeDTO.setId(candidateResume.getId());
				candidateResumeDTO.setTitle(candidateResume.getTitle());
				candidateResumeDTO.setDescription(candidateResume.getDescription());
				candidateResumeDTO.setCreatedBy(candidateResume.getCreatedBy());
				candidateResumeDTO.setFileContentType(candidateResume.getFileContentType());
				candidateResumeDTO.setFileName(candidateResume.getFileName());
				candidateResumeDTO.setFileURL(candidateResume.getFileURL());
			}
		}
		return candidateResumeDTO;
	}

	@RequestMapping(value = { "/saveCandidateFamilyDetails" })
	public Response<String> saveCandidateFamilyMemberDetails(CandidateFamilyDetailsDTO familyDetailsDTO,
			HttpServletRequest request, @RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "memberId", required = false) Long id,
			@RequestParam(value = "immigrationId", required = true) Long immigrationId) { // memberId used for edit
																							// process.
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");

		try {
			if (user != null && Validator.validate(familyDetailsDTO)) {
				familyDetailsDTO.setCandidateId(candidateId);
				if (id != null) { // edit mode we will get the value.

					familyDetailsDTO.setId(id);
					logger.debug("familyDetailsDTO.getId()--->" + familyDetailsDTO.getId());
				}

				logger.debug(" inside conroller -->relationType :: " + familyDetailsDTO.getRelationType());
				if (familyDetailsDTO.getRelationType().trim().equalsIgnoreCase(Constants.RELATION_TYPE_SPOUSE)) {
					familyDetailsDTO.setRelation(Constants.RELATION_TYPE_SPOUSE);
				} else if (familyDetailsDTO.getRelationType().trim().equalsIgnoreCase(Constants.RELATION_TYPE_CHILD)) {
					familyDetailsDTO.setRelation(Constants.RELATION_TYPE_CHILD);
				} else if (familyDetailsDTO.getRelationType().trim().equalsIgnoreCase(Constants.RELATION_TYPE_MOTHER)) {
					familyDetailsDTO.setRelation(Constants.RELATION_TYPE_MOTHER);
				} else if (familyDetailsDTO.getRelationType().trim().equalsIgnoreCase(Constants.RELATION_TYPE_FATHER)) {
					familyDetailsDTO.setRelation(Constants.RELATION_TYPE_FATHER);
				}

				immigrationCommonService.saveCandidateFamilyMemberDetails(familyDetailsDTO, orgLocations, user,
						immigrationId);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return response;
	}

	
	/**
	 * 
	 * @param immigrationPassportVisaDTO
	 * @param request
	 * @param candidateId
	 * @param immigrationId
	 * @return
	 */
	@RequestMapping(value = { "/saveVisaDetailsPage" }, method = RequestMethod.POST)
	public Response<String> saveVisaDetailsPage(ImmigrationVisaDTO immigrationVisaDTO,
			HttpServletRequest request, @RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "immigrationId", required = false) Long immigrationId,
			@RequestParam(value = "visaHistoryId", required = false) Long visaHistoryId) {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Response<String> response = new Response<>();
		logger.debug("Candidate ID ----> :  " + immigrationVisaDTO.getCandidateId());
/*		if(immigrationVisaDTO.getIsCurrent()== null || immigrationVisaDTO.getIsCurrent().isEmpty()) {
			immigrationVisaDTO.setIsCurrent(ImmigrationConstants.IS_CURRENT_NO);// setting value as "N"
		}*/
		if(visaHistoryId != null) {
			immigrationVisaDTO.setId(visaHistoryId);// setting value as "N"
		}
		if (user != null) {
			try {
				if (Validator.validate(immigrationVisaDTO)) {
					immigrationCommonService.saveVisaDetails(immigrationVisaDTO, user, orgLocations,
							  immigrationId, visaHistoryId); // in edit mode we will get visaHistoryId from the UI as a request parameter.
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				logger.error("Exception caugth in savePassportVisaDetailsPage controller" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;

	}
	
	/**
	 * 
	 * @param immigrationPassportDTO
	 * @param request
	 * @param candidateId
	 * @param immigrationId
	 * @return
	 */
	@RequestMapping(value = { "/savePassportDetailsPage" }, method = RequestMethod.POST)
	public Response<String> savePassportDetailsPage(ImmigrationPassportDTO immigrationPassportDTO,
			HttpServletRequest request, @RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "immigrationId", required = false) String immigrationId) {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Response<String> response = new Response<>();
		logger.debug("Candidate ID ----> :  " + immigrationPassportDTO.getCandidateId());
		if (user != null) {
			try {
				if (Validator.validate(immigrationPassportDTO)) {
					
					immigrationCommonService.savePassportDetails(immigrationPassportDTO, user, orgLocations,
							Long.parseLong(immigrationId));
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				logger.error("Exception caugth in savePassportVisaDetailsPage controller" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;

	}

	@RequestMapping(value = { "/saveEducationImmigrationDedtails" }, method = RequestMethod.POST)
	public Response<String> saveEducationImmigrationDedtails(CandidateEducationDTO candidateEducationDTO,
			HttpServletRequest request, @RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "immigrationId", required = false) String immigrationId) {
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Candidate candidate = null;
		Immigration immigration = null;
		try {
			if (user != null && Validator.validate(candidateEducationDTO)) {
				candidateEducationDTO.setStaticPath(staticPath);
				candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candidateId));
				immigration = immigrationCommonService.getImmigrationById(Long.parseLong(immigrationId));
				candidateEducationService.storeCandidateEducation(candidateEducationDTO, Long.parseLong(candidateId),
						orgLocations.getId(), user);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				StringBuilder actionDesc = new StringBuilder();
				if (StringUtils.isNotBlank(candidateEducationDTO.getCandidateEduId())) {
					actionDesc.append("Education Details Updated").toString();
				} else {
					actionDesc.append("New Education Details Added").toString();
				}
				immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate,
						immigration, actionDesc.toString());
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = { "/saveLCADetails" }, method = RequestMethod.POST)
	public Response<String> saveLCADetails(ImmigrationLcaDTO immLcaDTO,
			HttpServletRequest request, @RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "immigrationId", required = false) String immigrationId,
			@RequestParam(value = "lcaId", required = false) String lcaId) {
		
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		Candidate candidate = null;
		Immigration immigration = null;
		Lca lca = null;
		
		try {
			if (user != null && Validator.validate(immLcaDTO)) {
				Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
				candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candidateId));
				immigration = immigrationCommonService.getImmigrationById(Long.parseLong(immigrationId));
				lca=immigrationCommonService.getLcaByLcaId(Long.parseLong(lcaId));
				lca.setAvailablePositions(lca.getAvailablePositions()-1); // Decreasing no of available position when assigned to candidate.
				lca.setUpdatedBy(user);
				lca.setUpdatedDate(currentDate);
				immigrationCommonService.updateLcaAvailablePositionsById(lca);
				LcaHistory lh= new LcaHistory();
				
				lh.setLcaId(lca);
				lh.setCandidate(candidate);
				lh.setImmigrationId(immigration);
				lh.setIsCurrent(Constants.YES);
				lh.setStatus(Constants.RECORD_ACTIVE);
				lh.setCreatedBy(user);
				lh.setUpdatedBy(user);
				lh.setUpdatedDate(currentDate);
				lh.setCreatedDate(currentDate);
				
				LcaHistory lcaHistory= immigrationCommonService.saveLcaHistory(lh);
				immigrationCommonService.updateLcaHistoryStatusByLcaNumber(lcaHistory.getId(), lh.getImmigrationId().getId(), lh.getCandidate().getId());
				
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				StringBuilder actionDesc = new StringBuilder();
				 if (lcaHistory.getId() !=null ) {
					actionDesc.append("LCA Details Updated").toString();
				} else {
					actionDesc.append("New LCA Details Added").toString();
				}
				immigrationActivityCommonUtil.saveImmigrationCandidateActivities(user, orgLocations, candidate,immigration, actionDesc.toString());
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = { "/saveImmigrationResidenceDetails" })
	public Response<String> saveCandidateResidence(ResidenceDTO residenceDTO, HttpServletRequest request,
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "immigrationId", required = false) String immigrationId) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> response = new Response<>();
		// logger.debug("isCurrResi value is ---> "+residenceDTO.getIsCurrResi());
		if (user != null) {
			try {
				if (Validator.validate(residenceDTO)) {
					if (residenceDTO.getIsCurrResi().equalsIgnoreCase(Constants.YES)) {
						Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
						candidate.setLocation(residenceDTO.getLocation());
						candidate.setCountry(residenceDTO.getCountry());
						candidate.setState(residenceDTO.getState());
						candidate.setCity(residenceDTO.getCity());
						candidate.setZipCode(residenceDTO.getZipcode());
						candidate.setAddress(residenceDTO.getStreetAddress());
						candidate.setResidenceStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
								residenceDTO.getResidenceStartDate()));

						candidateService.saveOrUpdateCandidate(candidate); // update the master candidate table.

					} else {
						immigrationCommonService.saveResidenceDetails(residenceDTO, orgLocations, user, candidateId,
								Long.parseLong(immigrationId));
					}
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				logger.error("Exception caugth in controller" + e.getMessage());
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}

	@RequestMapping(value = { "/getCandidateFamilyDetails" })
	public ModelAndView getCandidateFamilyDetails(
			@RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "searchVal", required = false) String searchVal,
			@RequestParam(value = "myImmigrationPage", required = true) String myImmigrationPage,
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		logger.debug(" inisde getCandidateFamilyDetails controller test -->");
		model.setViewName("/immigration/familyDetailsTab");
		List<CandidateFamilyDetails> familyList = new ArrayList<CandidateFamilyDetails>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		try {
			// String searchVal = ServletRequestUtils.getStringParameter(request,
			// "searchVal");
			familyList = immigrationCommonService.getCandidateFamilyDetails(candidateId, searchVal);
			for (CandidateFamilyDetails familyObj : familyList) {
				familyObj.setMarraigeDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
						familyObj.getMarraigeDate()));
				familyObj.setMarriageEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
						familyObj.getMarriageEndDate()));
				familyObj.setDateOfBirthStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
						familyObj.getDateOfBirth()));
			}
			model.addObject("myImmigrationPage", myImmigrationPage);
			model.addObject("familyList", familyList);
		} catch (Exception e) {
			logger.error("Exception caught in controller --->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug(" after getCandidateFamilyDetails controller -->");
		return model;
	}

	@RequestMapping(value = { "/deleteCandidateFamilyDetails" })
	public String deleteCandidateFamilyDetails(@RequestParam(value = "id", required = true) Long deleteId,
			HttpServletRequest request) {
		String status = "";
		logger.debug(" in controller deleteId ---> " + deleteId);
		try {
			status = immigrationCommonService.deletetCandidateFamilyDetails(deleteId);
			logger.debug(" in controller status " + status);
		} catch (Exception e) {
			logger.error("Exception caught in controller --->" + e.getMessage());
			e.printStackTrace();
		}

		return status;
	}

	@RequestMapping(value = { "/getCandidateFamilyDetailsById" })
	public @ResponseBody CandidateFamilyDetails getCandidateFamilyDetailsById(
			@RequestParam(value = "id", required = true) Long id, HttpServletRequest request) {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		String status = "";
		logger.debug(" in controller method getCandidateFamilyDetailsById id ---> " + id);
		CandidateFamilyDetails familyObj = null;
		try {
			familyObj = immigrationCommonService.getCandidateFamilyDetailsById(id);
			familyObj.setMarraigeDateStr(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), familyObj.getMarraigeDate()));
			familyObj.setMarriageEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					familyObj.getMarriageEndDate()));
			familyObj.setDateOfBirthStr(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), familyObj.getDateOfBirth()));
			logger.debug(" in controller status " + status);
		} catch (Exception e) {
			logger.error("Exception caught in controller test --->" + e.getMessage());
			e.printStackTrace();
		}

		return familyObj;
	}

	@RequestMapping(value = { "/getCandidateEducationDetailsById" })
	public @ResponseBody CandidateEducation getCandidateEducationDetailsById(
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request) {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		logger.debug(" in controller method getCandidateFamilyDetailsById id ---> " + id);
		CandidateEducation canEdu = null;
		try {
			canEdu = candidateEducationService.getCandidateEducationBycandidateIdandEducationId(candidateId, id);
			canEdu.setStartDateStr(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), canEdu.getStartDate()));
			canEdu.setEndDateStr(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), canEdu.getEndDate()));
		} catch (Exception e) {
			logger.error("Exception caught in controller test --->" + e.getMessage());
			e.printStackTrace();
		}

		return canEdu;
	}

	@RequestMapping(value = { "/getCandidateEmpDetailsById" })
	public @ResponseBody CandidateEmployers getCandidateEmpDetailsById(
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request) {
		logger.debug(" in controller method getCandidateFamilyDetailsById id ---> " + id);
		CandidateEmployers canEmp = null;
		try {
			canEmp = candidateEmployersService.getCandidateEmployerBycandidateIdandCandidateEmployerID(candidateId, id);

		} catch (Exception e) {
			logger.error("Exception caught in controller test --->" + e.getMessage());
			e.printStackTrace();
		}

		return canEmp;
	}

	@RequestMapping(value = { "/deleteCandidateEduDetails" })
	public String deleteCandidateEduDetails(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		candidateEducationService.removeCandidateEducationBycandidateIdandcandidateEducationId(candidateId, id, user,
				orgLocations);

		return "success";
	}

	@RequestMapping(value = { "/deleteCandidateEmployerDetails" })
	public String deleteCandidateEmployerDetails(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request)
			throws Exception {
		candidateEmployersService.removeCandidateEmployerBycandidateIdandCandidateEmployerID(candidateId, id);

		return "success";
	}

	@RequestMapping(value = { "/getCandidateEducationList" })
	public ModelAndView getCandidateEducationList(
			@RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "searchVal", required = false) String searchVal, 
			@RequestParam(value = "myImmigrationPage", required = true) String myImmigrationPage,
			HttpServletRequest request)
			throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		model.setViewName("/immigration/educationDetailsTab");
		List<CandidateEducation> candidatesEducationList = candidateEducationService
				.getCandidateEducationListByCandidateIdAndOrgLocationIDImmigration(Long.valueOf(candidateId),
						orgLocations.getId(), searchVal);
		List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		model.addObject("educationsDropList", educationsList);
		model.addObject("candidatesEducationList", candidatesEducationList);
		model.addObject("myImmigrationPage", myImmigrationPage);
		return model;
	}

	@RequestMapping(value = { "/getCandidateEmployerList" })
	public ModelAndView getCandidateEmployerList(@RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "searchVal", required = false) String searchVal,
			@RequestParam(value = "myImmigrationPage", required = true) String myImmigrationPage,
			HttpServletRequest request)
			throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		model.setViewName("/immigration/employerDetailsTab");
		List<CandidateEmployers> candidatesEmployersList = candidateEmployersService
				.getCandidatesEmployersListByCandidateIdAndOrgLocationIDImmigration(Long.valueOf(candidateId), searchVal);
		model.addObject("myImmigrationPage", myImmigrationPage);
		model.addObject("candidatesEmployersList", candidatesEmployersList);
		return model;
	}

	@RequestMapping(value = { "/getCandidateResidenceList" })
	public ModelAndView getCandidateResidenceList(
			@RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "searchVal", required = false) String searchVal,
			@RequestParam(value = "myImmigrationPage" , required = true) String myImmigrationPage, HttpServletRequest request)
			throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		model.setViewName("/immigration/residenceDetailsTab");
		List<CandidateResidence> candidatesResidenceList = immigrationCommonService
				.getCandidatesResidenceListByCandidateIdAndOrgLocationID(candidateId, searchVal);
		for (CandidateResidence canRes : candidatesResidenceList) {
			canRes.setStartDateStr(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), canRes.getStartDate()));
			canRes.setEndDateStr(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), canRes.getEndDate()));

		}
		model.addObject("myImmigrationPage", myImmigrationPage);
		model.addObject("candidatesResidenceList", candidatesResidenceList);
		return model;
	}

	
	//Visa List
	@RequestMapping(value = { "/getCandidateVisaDetails" })
	public ModelAndView getCandidateVisaDetails(
			@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request,
			@RequestParam(value = "searchValue", required = true) String searchValue,
			@RequestParam(value = "myImmigrationPage", required = true) String myImmigrationPage) throws Exception {
		ModelAndView model = new ModelAndView();
		logger.debug(" inisde getCandidateVisaDetails controller test -->");
		List<ImmigrationVisaDTO> visaList = new ArrayList<ImmigrationVisaDTO>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Date toDayDate = new Date();
		List<Long> expiredVisaIdList= new ArrayList<Long>();
		try {
			visaList = immigrationCommonService.getCandidateVisaDetails(candidateId,searchValue);
			for (ImmigrationVisaDTO canVisa : visaList) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String currentDateStr = dateFormat.format(toDayDate);
				Date newDate = dateFormat.parse(currentDateStr);
				Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
			

				if (StringUtils.isNotBlank(canVisa.getValidTo()) && currentDate.before(commonUtil.stringToDate(canVisa.getValidTo()))) {
					canVisa.setCurrentStatus("Active");
				} else {
					canVisa.setCurrentStatus("Expired");
					if(canVisa.getStatus() == 1)
					expiredVisaIdList.add(canVisa.getCandidateVisaId());
				}
				canVisa.setValidFrom(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),  commonUtil.stringToDate(canVisa.getValidFrom())));
				canVisa.setValidTo(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),  commonUtil.stringToDate(canVisa.getValidTo())));
			}

			List<Countries> countryList = countriesService.getAllCountries();
			List<String> canVisaList = lookUpDataService.getAllVisaByCategory();
			model.addObject("canVisaList", canVisaList);
			model.addObject("visaList", visaList);
			model.addObject("countryList", countryList);
			model.addObject("expiredVisaIdList", expiredVisaIdList);
			model.addObject("myImmigrationPage", myImmigrationPage);
			model.setViewName("/immigration/visaDetailsTab");
		} catch (Exception e) {
			logger.error("Exception caught in controller --->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug(" after getCandidateVisaDetails controller -->");
		
		return model;
	}
	//Visa List
	@RequestMapping(value = { "/addVisaExtensionDate" })
	public void addCandidateExtensionDatebyVisaId(
			@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request,
			@RequestParam(value = "immigrationId", required = true) long immigrationId,
			@RequestParam(value = "extensionDate", required = true) String extensionDate,
			@RequestParam(value = "visaId", required = true) long visaId) throws Exception {
		logger.debug(" inisde getCandidateVisaDetailsByVisaNuimebr controller test -->");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			ImmigrationVisaDTO immigrationVisaDTO = new ImmigrationVisaDTO();
			immigrationVisaDTO.setValidTo(extensionDate);
			immigrationVisaDTO.setIsCurrent(ImmigrationConstants.IS_CURRENT); // setting value as "Y" for updated record.
			immigrationVisaDTO.setCandidateVisaId(visaId);
			immigrationCommonService.saveVisaHistoryDetails(immigrationVisaDTO, user, orgLocations, immigrationId);
		} catch (Exception e) {
			logger.error("Exception caught in controller --->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug(" after getCandidateVisaDetails controller -->");
		
	}
	//passport list
	@RequestMapping(value = { "/getCandidatePassportDetails" })
	public ModelAndView getCandidatePassportVisaDetails(
			@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request,
			@RequestParam(value = "searchValue", required = true) String searchValue,
			@RequestParam(value = "myImmigrationPage", required = true) String myImmigrationPage) throws Exception {
		ModelAndView model = new ModelAndView();
		logger.debug(" inisde getCandidatePassportDetails controller test -->");
		List<CandidatePassport> PassportList = new ArrayList<CandidatePassport>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Date toDayDate = new Date();
		try {
			PassportList = immigrationCommonService.getCandidatePassportDetails(candidateId,searchValue);
			for (CandidatePassport canPassport : PassportList) {
				canPassport.setValidFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
						canPassport.getValidFromDate()));
				canPassport.setValidToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
						canPassport.getValidToDate()));

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String currentDateStr = dateFormat.format(toDayDate);
				Date newDate = dateFormat.parse(currentDateStr);
				Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);

				if (currentDate.before(canPassport.getValidToDate())) {
					canPassport.setCurrentStatus("Active");
				} else {
					canPassport.setCurrentStatus("Expired");
				}
			}

			List<Countries> countryList = countriesService.getAllCountries();
			model.addObject("PassportList", PassportList);
			model.addObject("countryList", countryList);
			model.addObject("myImmigrationPage", myImmigrationPage);
			model.setViewName("/immigration/passportDetailsTab");
			
			
		} catch (Exception e) {
			logger.error("Exception caught in controller --->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug(" after getCandidatePassportDetails controller -->");
		
		return model;
		
	}
	
	@RequestMapping(value = { "/getLcaDetails" })
	public ModelAndView getLcaDetails(
			@RequestParam(value = "immigrationId", required = true) String immigrationId, HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		logger.debug(" inisde getLcaDetails controller test -->");
		if(immigrationId != null) {
			 List<LcaDTO> lcaList = immigrationCommonService.getLcaHistoryDetailsInfo(Long.parseLong(immigrationId));
			    model.addObject("lcaList", lcaList);
		}
		model.setViewName("/immigration/lcaDetailsTab");
		return model;
	}

	@RequestMapping(value = { "/updateImmigrationStatus" })
	public String checkDataAndUpdateImmigrationStatus(@RequestParam(value = "id", required = true) Long immigrationId,
			@RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "templateId", required = true) Long templateId, HttpServletRequest request)
			throws Exception {
		String result = "{status:'success'}";

		logger.debug(" inisde updateImmigrationStatus controller -->");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			String entityArray[] = new String[] { "CandidatePassportVisa", "CandidateEmployers", "Candidate",
					"CandidateEducation" };
			String tabNameArray[] = new String[] { "Passport", "Employers", "Residence", "Education" };
			Integer count = 0;
			List<String> missedTabList = new ArrayList<String>();
			for (int i = 0; i < entityArray.length; i++) {
				if (i == 0) { // CandidatePassportVisa for passport
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where candidate.id=:candidateId and idType='Passport'");
					if (count == null) {
						missedTabList.add(tabNameArray[i]);
					}
				} else if (i == 1) { // CandidateEmployers
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where candidateId.id=:candidateId and status=1");
					if (count == null) {
						missedTabList.add(tabNameArray[i]);
					}
				} else if (i == 2) { // CandidateResidence pulling current residence record from Candidate Table.
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where id=:candidateId");
					if (count == null) {
						missedTabList.add(tabNameArray[i]);
					}
				} else if (i == 3) {// CandidateEducation
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where candidateId.id=:candidateId and status=1");
					if (count == null) {
						missedTabList.add(tabNameArray[i]);
					}
				}
			}

			// check required column value in Forms tab.
			List<ImmigrationCandidateForms> formsList = immigrationCommonService
					.getCandidateImmigartionFormsDetails(candidateId, templateId, immigrationId, "","Y");
			logger.debug(" formsList:::size --> : " + formsList.size());
			for (ImmigrationCandidateForms form : formsList) {
				if (form.getIsRequired().equalsIgnoreCase(Constants.YES)) {
					if (!form.getFormStatus().equalsIgnoreCase(ImmigrationConstants.FILE_UPLOADED)
							|| StringUtils.isEmpty(form.getUploadedFileUrl())) {
						// logger.debug("Required found for the Form Tab for the Form ID :::--> : " +
						// form.getId());
						missedTabList.add("Forms");
						break;
					}
				}
			}
			// check is required in in DocumentsTab
			List<ImmigrationCandidateDocuments> docsList = immigrationCommonService
					.getImmigrationCandidateDocs(immigrationId, templateId, candidateId, user, orgLocations, "","Y");
			logger.debug(" docsList:::size --> : " + formsList.size());
			for (ImmigrationCandidateDocuments doc : docsList) {
				if (doc.getIsRequired().equalsIgnoreCase(Constants.YES)) {
					if (!doc.getDocumentStatus().equalsIgnoreCase(ImmigrationConstants.FILE_UPLOADED)
							|| StringUtils.isEmpty(doc.getUplodedFileUrl())) {
						// result = "{errorMsg:'Documents'}";
						// logger.debug("Required found for the Documents Tab for the Doc ID :::--> : "
						// + doc.getId());
						missedTabList.add("Documents");
						break;
					}
				}
			}

			if (missedTabList.size() > 0) {
				String commaSeparatedString = String.join(",", missedTabList);
				// logger.debug(" comma separated String is "+commaSeparatedString);
				result = "{errorMsg:'" + commaSeparatedString + "'}";

				return result;
			}

			// if no error message means all required information is filled.
			// then update immigration petition status to request to fill.

			LookUpData data = lookUpDataService.getLookUpdataByCategoryAndName(ImmigrationConstants.PETITION_STATUS,
					ImmigrationConstants.PETITION_STATUS_FILLED);
			Immigration immigration = new Immigration();
			if (immigrationId != null) {
				immigration = immigrationCommonService.getImmigrationById(immigrationId);
			}
			immigration.setCurrUscisStauts(data); // updating with Filled status.
			immigrationCommonService.updateImigrationDetatils(immigration);
			StringBuilder actionDesc = new StringBuilder();
			actionDesc.append("Changed Status").append(" ").append("Request to fill").append(" ").append("to")
					.append(" ").append(data.getName()).toString();
			immigrationActivityCommonUtil.saveImmigrationActivities(user, orgLocations, immigration, null,
					actionDesc.toString());

			logger.debug("Immigartion details udated successfully for the immigration id ID :::--> : "
					+ immigration.getId());
		} catch (Exception e) {
			logger.error("Exception caught in controller --->" + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	private HashMap<String, Integer> getImmigrationViewTabStatusDetails(Long candidateId, Long immigrationId) {
		// result = "{errorMsg:'" + tabNameArray[i] + "'}";
		// logger.debug(" Record not found for the Tab Nme:::--> : " + tabNameArray[i]);
		HashMap<String, Integer> tabRecordCountMap = new HashMap<String, Integer>();
		try {
			String entityArray[] = new String[] { "CandidatePassport", "CandidateVisa",
					"CandidateEmployers", "Candidate", "CandidateEducation", "CandidateFamilyDetails", "Tasks" ,"LcaHistory"};
			String tabNameArray[] = new String[] { "Passport", "Visa", "Employers", "Residence", "Education", "Family",
					"Tasks","Lca" };
			Integer count = 0;
			for (int i = 0; i < entityArray.length; i++) {
				if (i == 0) { // CandidatePassportVisa for passport
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where candidate.id=:candidateId ");
					tabRecordCountMap.put(tabNameArray[i], count);

				} else if (i == 1) {// CandidatePassportVisa for Visa
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where candidate.id=:candidateId ");
					tabRecordCountMap.put(tabNameArray[i], count);
				} else if (i == 2) { // CandidateEmployers
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where candidateId.id=:candidateId and status=1");
					tabRecordCountMap.put(tabNameArray[i], count);
				} else if (i == 3) { // CandidateResidence
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where id=:candidateId");
					tabRecordCountMap.put(tabNameArray[i], count);
				} else if (i == 4) {// CandidateEducation
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where candidateId.id=:candidateId and status=1");
					tabRecordCountMap.put(tabNameArray[i], count);
				} else if (i == 5) {// CandidateFamilyDetails
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where candidate.id=:candidateId");
					tabRecordCountMap.put(tabNameArray[i], count);
				} else if (i == 6) {// CandidateFamilyDetails
					count = immigrationCommonService.getTableRecordCountByImmigrationId(entityArray[i], immigrationId,
							"where immigrationId.id=:immigrationId and taskStatus='Open'");
					tabRecordCountMap.put(tabNameArray[i], count);
				} else if (i == 7) {// CandidateLcaDetails
					count = immigrationCommonService.getTableRecordCountByImmigrationId(entityArray[i], immigrationId,
							"where immigrationId.id=:immigrationId ");
					tabRecordCountMap.put(tabNameArray[i], count);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tabRecordCountMap;

	}

	//visa
	@RequestMapping(value = { "/deleteCandidateVisaDetails" })
	public String deleteCandidateVisaDetails(@RequestParam(value = "visaHistoryId", required = true) Long visaHistoryId, HttpServletRequest request
			) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
		immigrationCommonService.removeCandidateVisaDetails(visaHistoryId);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Exception while deleting visa history record.");
		}
		return "success";
	}
	
	//passport
	@RequestMapping(value = { "/deleteCandidatePassportDetails" })
	public String deleteCandidatePassportDetails(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request
			) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		immigrationCommonService.removeCandidatePassportDetails(candidateId, id, user, orgLocations);

		return "success";
	}

	@RequestMapping(value = { "/deleteCandidateResidenceDetails" })
	public String deleteCandidateResidenceDetails(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request)
			throws Exception {
		immigrationCommonService.deleteCandidateResidenceDetails(id, candidateId);

		return "success";
	}

	@RequestMapping(value = { "/getCandidateResidenceDetailsById" })
	public @ResponseBody CandidateResidence getCandidateResidenceDetailsById(
			@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "isCurr", required = false) String isCurr, HttpServletRequest request) {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		String status = "";
		logger.debug(" in controller method getCandidateResidenceDetailsById id ---> " + id);
		CandidateResidence residenceObj = null;
		try {

			if (isCurr.equalsIgnoreCase("Y")) {
				CandidateResidence currResidence = new CandidateResidence();
				Candidate canCurrResiCand = candidateService.getCandidateById(id);
				if (canCurrResiCand != null) {
					currResidence.setLocation(canCurrResiCand.getLocation());
					currResidence.setCountry(canCurrResiCand.getCountry());
					currResidence.setState(canCurrResiCand.getState());
					currResidence.setCity(canCurrResiCand.getCity());
					currResidence.setStreetAddress(canCurrResiCand.getAddress());
					currResidence.setZipcode(canCurrResiCand.getZipCode());
					currResidence.setStartDate(canCurrResiCand.getResidenceStartDate());
					currResidence.setIsCurrAddress("Y");// used for edit functionality.
					currResidence.setId(canCurrResiCand.getId());
				}
				residenceObj = currResidence;
			} else {
				residenceObj = immigrationCommonService.getCandidateResidenceDetailsById(id);
			}
			residenceObj.setStartDateStr(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), residenceObj.getStartDate()));
			residenceObj.setEndDateStr(
					commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), residenceObj.getEndDate()));
			logger.debug(" in controller status " + status);
		} catch (Exception e) {
			logger.error("Exception caught in controller test --->" + e.getMessage());
			e.printStackTrace();
		}

		return residenceObj;
	}

	// visa
	@RequestMapping(value = { "/getCandidateVisaDetailsByVisaHistoryId" })
	public @ResponseBody ImmigrationVisaDTO getCandidateVisaDetailsByVisaHistoryId(
			@RequestParam(value = "id", required = true) Long id, HttpServletRequest request) {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		String status = "";
		logger.debug(" in controller method getCandidateVisaDetailsByVisaHistoryId id ---> " + id);
		ImmigrationVisaDTO visaObj = null;
		try {
			
			visaObj = immigrationCommonService.getCandidateVisaDetailsByVisaHistoryId(id);
			visaObj.setValidFrom(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),  commonUtil.stringToDate(visaObj.getValidFrom())));
			visaObj.setValidTo(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),  commonUtil.stringToDate(visaObj.getValidTo())));
		} catch (Exception e) {
			logger.error("Exception caught in controller test --->" + e.getMessage());
			e.printStackTrace();
		}

		return visaObj;
	}
	
	// passport
	@RequestMapping(value = { "/getCandidatePassportDetailsById" })
	public @ResponseBody CandidatePassport getCandidatePassportDetailsById(
			@RequestParam(value = "id", required = true) Long id, HttpServletRequest request) {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		String status = "";
		logger.debug(" in controller method getCandidatePassportDetailsById id ---> " + id);
		CandidatePassport passportObj = null;
		try {
			passportObj = immigrationCommonService.getCandidatePassportDetailsById(id);

			passportObj.setValidFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					passportObj.getValidFromDate()));
			passportObj.setValidToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
					passportObj.getValidToDate()));

			logger.debug(" in controller status " + status);
		} catch (Exception e) {
			logger.error("Exception caught in controller test --->" + e.getMessage());
			e.printStackTrace();
		}

		return passportObj;
	}

	@RequestMapping(value = { "/saveImmigrationNotesDetails" })
	public String saveImmigrationNotesDetails(SourceleadNotesDTO sourceleadNotesDTO, HttpServletRequest request,
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "immigrationId", required = false) String immigrationId) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		sourceleadNotesService.storeComments(sourceleadNotesDTO, user, orgLocations);
		logger.debug("Template Task Details saved succesfully --> ");

		return "success";
	}

	@RequestMapping(value = "/validateImmiEduname")
	public Boolean validateEduname(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request)
			throws Exception {
		Boolean immiCanEdu = candidateEducationService.validateEducationName(candidateId, name);
		return immiCanEdu;
	}

	@RequestMapping(value = { "/getPetitionTypeOnLocation" })
	public List<LookUpData> getPetitionTypeOnLocation(
			@RequestParam(value = "locationVal", required = false) String locationVal, HttpServletRequest request) {
		List<LookUpData> petitionTypeDTOs = immigrationTemplateService.getLookupDataByTypeAndLocation(Constants.VISA,
				locationVal);
		return petitionTypeDTOs;
	}

	@RequestMapping(value = { "/getNotesListByImmigrationId" })
	public ModelAndView getNotesListByImmigrationId(
			@RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "immigrationId", required = true) Long immigrationId, HttpServletRequest request)
			throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		model.setViewName("/immigration/noteDetailsTab");
		List<SourceleadNotes> candidatesNotesList = sourceleadNotesService.getNotesListByImmigrationId(immigrationId,
				orgLocations.getId());
		model.addObject("candidatesNotesList", candidatesNotesList);
		return model;
	}

	@RequestMapping(value = { "/getImmigrationActiviesList" })
	public ModelAndView getImmigrationActiviesList(
			@RequestParam(value = "immigrationId", required = true) Long immigrationId, HttpServletRequest request)
			throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		model.setViewName("/immigration/activityDetailsTab");
		List<ImmigrationActivityHistory> immigrationActivitiesList = immigrationCommonService
				.getImmigrationActivitiesList(immigrationId, orgLocations.getId());
		model.addObject("immigrationActivitiesList", immigrationActivitiesList);
		return model;
	}

	@RequestMapping(value = { "/getCandidateImmigrationFormsList" })
	public ModelAndView getCandidateImmigrationForms(HttpServletRequest request,
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "templateId", defaultValue = "0") Long templateId,
			@RequestParam(value = "immigrationId", required = false) Long immigrationId,
			@RequestParam(value = "searchVal", required = false) String searchVal,
			@RequestParam(value = "myImmigration", required = false) String myImmigration,
			@RequestParam(value = "myImmigrationPage", required = true) String myImmigrationPage) throws Exception {

		logger.debug("inside controller method getCandidateImmigrationForms--> ");
		ModelAndView model = new ModelAndView();
		model.setViewName("/immigration/formDetailsTab");
		List<ImmigrationCandidateForms> candidateFormsLst = immigrationCommonService
				.getCandidateImmigartionFormsDetails(candidateId, templateId, immigrationId, searchVal,myImmigration);
		logger.debug("Candidate forms Details updated succesfully  candidateFormsLst size  is --> "
				+ candidateFormsLst.size());
		model.addObject("myImmigrationPage", myImmigrationPage);
		model.addObject("formsList", candidateFormsLst);
		return model;
	}

	@RequestMapping(value = { "/updateUscisTypeDropDown" })
	public String updateUscisTypeDropDown(@RequestParam(value = "templateId", required = false) Long templateId,
			@RequestParam(value = "uscisId", required = false) Long uscisId,
			@RequestParam(value = "uscisOldId", required = false) Long uscisOldId, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		String resultVal = immigrationCommonService.updateUscisTypeDropDown(templateId, uscisId, uscisOldId, user,
				loginOrgLocation);
		return resultVal;
	}

	@RequestMapping(value = { "/editImmigrationDetails" })
	public ModelAndView editImmigrationDetails(
			@RequestParam(value = "immigrationUUID", required = false) String immigrationUUID,
			HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("immigrationUUID", immigrationUUID);
		model.setViewName("/immigration/newPetition");
		return model;
	}

	@RequestMapping("/downloadImmigrationDocsZip")
	@ResponseBody
	public String exportImmigrationDocuments(HttpServletRequest request, Long immigrationId, Long docId)
			throws Exception {
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		long orgId = 0, orgLocationId = 0;
		if (org != null) {
			orgId = org.getId();
		}
		if (loginOrgLocation != null) {
			orgLocationId = loginOrgLocation.getId();
		}
		List<ImmigrationCandidateDocumentsHistory> immigrationCanididateDocHistoryList = immigrationCommonService
				.getImmigrationCandidateDocumentHistoryListByImmigrationId(immigrationId, docId);
		Map<String, String> fileNameParameters = new HashMap<>();
		Immigration immigration = immigrationCommonService.getImmigrationById(immigrationId);
		fileNameParameters.put("immiUUID", immigration.getGeneratedId());
		fileNameParameters.put("firstName", immigration.getCandidate().getFirstName());
		fileNameParameters.put("lastName", immigration.getCandidate().getLastName());
		if (docId != null) {
			ImmigrationCandidateDocuments docObj = immigrationCommonService.getImmigrationCandidateDocumentById(docId);
			fileNameParameters.put("docName", docObj.getDocumentType().getName());
		}
		return FileUploadUtil.getImmigrationZipFiles(staticPath, orgId, orgLocationId, immigrationId, docId,
				immigrationCanididateDocHistoryList, fileNameParameters);
	}

	@RequestMapping("/downloadAllImmigrationDocsZip") // used for downloading all immigration documents.
	@ResponseBody
	public String exportAllImmigrationDocuments(HttpServletRequest request, Long immigrationId) throws Exception {
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		long orgId = 0, orgLocationId = 0;
		if (org != null) {
			orgId = org.getId();
		}
		if (loginOrgLocation != null) {
			orgLocationId = loginOrgLocation.getId();
		}
		Map<String, String> fileNameParameters = new HashMap<>();
		Immigration immigration = immigrationCommonService.getImmigrationById(immigrationId);
		fileNameParameters.put("immiUUID", immigration.getGeneratedId());
		fileNameParameters.put("firstName", immigration.getCandidate().getFirstName());
		fileNameParameters.put("lastName", immigration.getCandidate().getLastName());

		HashMap<String, Object> docsListMap = new HashMap<String, Object>();
		List<ImmigrationCandidateForms> immigrationCandidateForms = immigrationCommonService
				.getImmigrationCandidateFormsByImmigrationId(immigrationId);
		docsListMap.put("immForms", immigrationCandidateForms);

		List<ImmigrationCandidateDocumentsHistory> immigrationCanididateDocHistoryList = immigrationCommonService
				.getImmigrationCandidateDocumentHistoryListByImmigrationId(immigrationId, null);

		// below code for adding candidate resume to downloading package.
		CandidateResumes candidateResume = candidateResumesService
				.getCandidateLatesResumes(immigration.getCandidate().getId(), Constants.CANDIDATE_ACTIVE);
		if(candidateResume != null) {
			if (StringUtils.isNotBlank(candidateResume.getFileURL())) {
				// logger.debug("cadidateResume path "+candidateResume.getFileURL());
				docsListMap.put("cadidateResume", candidateResume);
			}
		}

		docsListMap.put("immDocs", immigrationCanididateDocHistoryList);

		Long candidateId = immigration.getCandidate().getId();

		List<CandidateEmployers> employersList = candidateEmployersService
				.getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
		docsListMap.put("employerDocs", employersList);

		List<CandidateEducation> educationList = candidateEducationService
				.getCandidateEducationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
		docsListMap.put("educationDocs", educationList);

		return FileUploadUtil.getAllImmigrationZipFiles(staticPath, orgId, orgLocationId, immigrationId, docsListMap,
				fileNameParameters);
	}

	@RequestMapping(value = { "/deleteImmigrationDetailsById" })
	public String deleteImmigrationDetailsById(
			@RequestParam(value = "immigrationUUID", required = false) String immigrationUUID,
			HttpServletRequest request) throws Exception {
		Long immigrationId = immigrationCommonService.getImmigrationIdByUUID(immigrationUUID);
		String result = immigrationCommonService.deleteImmigrationDetailsById(immigrationId);
		return result;

	}

	@RequestMapping(value = { "/deleteImmigrationEmployerDocumentById" })
	public String deleteImmigrationEmployerDocumentById(@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request) throws Exception {
		String status = candidateEmployersService.removeCandidateEmployerDocById(id);
		return status;

	}

	@RequestMapping(value = { "/deleteImmigrationEducationDocumentById" })
	public String deleteImmigrationEducationDocumentById(@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request) throws Exception {
		String status = candidateEducationService.removeCandidateEucationrDocById(id);
		return status;

	}

	@RequestMapping("/downloadImmigrationFormZip")
	@ResponseBody
	public String exportImmigrationForms(HttpServletRequest request, Long immigrationId) throws Exception {
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		long orgId = 0, orgLocationId = 0;
		if (org != null) {
			orgId = org.getId();
		}
		if (loginOrgLocation != null) {
			orgLocationId = loginOrgLocation.getId();
		}
		List<ImmigrationCandidateForms> immigrationCandidateForms = immigrationCommonService
				.getImmigrationCandidateFormsByImmigrationId(immigrationId);
		Map<String, String> fileNameParameters = new HashMap<>();
		Immigration immigration = immigrationCommonService.getImmigrationById(immigrationId);
		fileNameParameters.put("immiUUID", immigration.getGeneratedId());
		fileNameParameters.put("firstName", immigration.getCandidate().getFirstName());
		fileNameParameters.put("lastName", immigration.getCandidate().getLastName());
		return FileUploadUtil.getImmigrationFormZipFiles(staticPath, orgId, orgLocationId, immigrationId,
				immigrationCandidateForms, fileNameParameters);
	}

	@RequestMapping(value = { "/getImmigrationTasksList" })
	public ModelAndView getImmigrationTasksList(
			@RequestParam(value = "immigrationId", required = false) String immigrationId,
			@RequestParam(value = "taskStatus", required = false) String taskStatus,
			@RequestParam(value = "searchVal", required = false) String searchVal, HttpServletRequest request)

			throws Exception {
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		model.setViewName("/immigration/taskDetailsTab");
		List<TaskDTO> tasksList = taskService.getTasksListByImmigrationIdAndCandidateId(immigrationId, user, taskStatus,
				searchVal);
		model.addObject("tasksList", tasksList);
		return model;
	}

	@RequestMapping(value = { "/validateCandidateVisa" })
	public Boolean validateCandidateVisa(
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "visaNumber", required = false) String visaNmuber,
			@RequestParam(value = "country", required = false) String country,
			@RequestParam(value = "id", required = false) Long id, HttpServletRequest request) throws Exception {
		//CandidateVisa candidateVisa = null;
		ImmigrationVisaDTO visaDto= null;
		if (null != id) {
		//	candidateVisa = immigrationCommonService.getCandidateVisaDetailsByIdNumberAndId(visaNmuber, candidateId, id);
			visaDto = immigrationCommonService.getCandidateActiveVisaDetailsByVisaNumberAndCountry(candidateId, visaNmuber, country);
		}
		Boolean flag = false;
		if (visaDto == null) {
			visaDto = immigrationCommonService.getCandidateActiveVisaDetailsByVisaNumberAndCountry(candidateId,visaNmuber, country);
			if(visaDto != null) {
				flag = true;
			}
		}
		return flag;
	}
	
	@RequestMapping(value = { "/validateCandidatePassport" })
	public Boolean validateCandidatePassport(
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "idNumberVal", required = false) String idNumberVal,
			
			@RequestParam(value = "id", required = false) Long id, HttpServletRequest request) throws Exception {

		CandidatePassport candidatePassport = null;
		if (null != id) {
			candidatePassport = immigrationCommonService.getCandidatePassportDetailsByIdNumberAndId(idNumberVal,candidateId, id);
		}
		Boolean flag = false;
		if (candidatePassport == null) {
			flag = immigrationCommonService.validateCandidatePassport(candidateId,idNumberVal);
		}
		return flag;
	}
	@RequestMapping(value = { "/validateImmigrationCandidateForms" })
	public Boolean validateImmigrationCandidateForms(
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "immigrationId", required = false) Long immigrationId,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "formName", required = false) String formName, HttpServletRequest request)
			throws Exception {

		ImmigrationCandidateForms ImmigrationCandidateForms = null;
		if (null != id) {
			ImmigrationCandidateForms = immigrationCommonService
					.getImmigrationCandidateFormsDetailsByFormNameAndId(formName, candidateId, immigrationId, id);
		}
		Boolean flag = false;
		if (ImmigrationCandidateForms == null) {
			flag = immigrationCommonService.validateImmigrationCandidateForms(formName, candidateId, immigrationId);
		}
		return flag;
	}

	@RequestMapping(value = { "/getImmigrationListExport" })
	public void getImmigrationListExport(@RequestParam(value = "exportType", required = false) String exportType,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		StringBuilder excelSB = new StringBuilder();
		Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("Immigration List ")
				.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date())).toString());
		if (!"pdf".equalsIgnoreCase(exportType)) {
			createExcelsheet = immigrationCommonService.getExcelFile(createExcelsheet, createExcelworkbook,
					orgLocations);
			StringBuilder templateSB = new StringBuilder();
			file = File.createTempFile(
					templateSB.append(orgLocations.getOrgLocationName() + "_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date())).toString(), ".xls");
			file.setReadable(true);

			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileNameSB = new StringBuilder();
		response.setHeader("Content-Disposition",
				fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
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
	
	
	@RequestMapping(value = "/validateTemplateTaskName", method = RequestMethod.POST)
	@ResponseBody
	public boolean validateTemplateTaskName(HttpServletRequest request,
			@RequestParam(value = "templateTaskName") String templateTaskName,
			@RequestParam(value = "templateId", defaultValue = "0") Long templateId,
			@RequestParam(value = "immiTaskId", defaultValue = "0") Long immiTaskId) {
		return immigrationTemplateService.validateTemplateTaskNameExistance(templateTaskName, templateId,immiTaskId);
	}
	
	@RequestMapping(value = "/getDcoumentType", method = RequestMethod.POST)
	@ResponseBody
	public List<LookUpData> getDcoumentType(HttpServletRequest request, @RequestParam(value = "docType") String docType) throws Exception {
		List<LookUpData> docTypeList = null;
		if(docType.equalsIgnoreCase("Document Type")) {
			docTypeList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
		}else if(docType.equalsIgnoreCase("AttorneyCategory")) {
			docTypeList = lookUpDataService.getLookUpDataByCategory(Constants.ATTORNEY_CATEGORY);
		}else if(docType.equalsIgnoreCase("PetitionerCategory")) {
			docTypeList = lookUpDataService.getLookUpDataByCategory(Constants.PETITIONER_CATEGORY);
		}
         
		 
		return docTypeList;
	}
	
	@RequestMapping(value = { "/getCandidateById" })
	public @ResponseBody Candidate getCandidateById(
			@RequestParam(value = "id", required = false) Long id,  HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Candidate candidate = candidateService.getCandidateByCandidateId(id);
		candidate.setDateofBirthString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), candidate.getDateOfBirth()));
		return candidate;
	}
	
	// to auto populate  lca start date and end date with LCA number in petition Details
	@RequestMapping(value = { "/getLcaByLcaId" })
	public @ResponseBody Lca getLcaByLcaId(
			@RequestParam(value = "id", required = false) Long id,  HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
	   Lca lca=immigrationCommonService.getLcaByLcaId(id);
	   lca.setLcaStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), lca.getStartDate()));
	   lca.setLcaEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), lca.getEndDate()));
		
		return lca;
	}
	
	/*
	 * LCA Code Starts Here
	 */
	@PreAuthorize("hasRole('ROLE_ADD_LCA')")
	@RequestMapping(value = { "/loadNewLCA" })
	public ModelAndView loadNewLCA(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		if(null != countries){
        model.addObject("loginCountryName", countries.getName());
        model.addObject("offeredWageCurrency", countries.getCurrency());
        model.addObject("privilingWageCurrency", countries.getCurrency());
		}
		
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/immigration/newLca");
		return model;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADD_LCA','ROLE_VIEW_LCA','ROLE_VIEW_EDIT_LCA','ROLE_VIEW_DELETE_LCA','ROLE_IMPORT_LCA','ROLE_EXPORT_LCA')")
	@RequestMapping(value = { "/viewLCAList" })
	public ModelAndView loadLCAList(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/immigration/lcaList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@RequestMapping(value = "/getLcaViewList")
	public @ResponseBody ResponseList<LcaListViewDTO> getAllLcaList(
			LcaListViewDTO lcaDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "filterByDays", required = false) String filterByDays,
            HttpServletRequest request) {
		ResponseList<LcaListViewDTO> lcaList = null;
		logger.debug("inisde controller  getLcaList  --> ");
		try {
			String[] tableColumns = { "lcaNo", "lcaTitle", "location", "prevailingWage","startDate", "endDate", "lcaStatus","status"};// should be
             
			// entity names
			int sortColumnIndex = -1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default ascending order
			if (lcaDTO.getOrder() != null && !lcaDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(lcaDTO.getOrder().get(0).get("column"));
				sortColumnOrder = lcaDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means
				lcaDTO.setSortOrder(sortColumnOrder); // direction
				// of the sorting.
			}
			Map<String, Long[]> filterVal = new HashMap<String, Long[]>();
			
			if (sortColumnIndex != -1)
				lcaDTO.setSortColumn(tableColumns[sortColumnIndex]);

			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			lcaList = immigrationCommonService.getLcasList(lcaDTO,lcaDTO.getMainStatus(),lcaDTO.getLcaStatus(), searchValue, orgLocations, filterVal,filterByDays);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("after controller  getLcaList  --> ");
		return lcaList;
	}
	
	
	@RequestMapping(value = "/saveLcaDetailsPage", method = RequestMethod.POST)
	public ResponseList<String> saveLcaDetailsPage(
			LcaDTO lcaDTO,HttpServletRequest request, @RequestParam(value = "plsempmntId", required = false) String plsempmntId) {
		
		ResponseList<String> response = new ResponseList<String>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Lca lca = null;
	
		try {
			if (user != null && Validator.validate(lcaDTO)) {
				lca = immigrationCommonService.saveLcaDetails(lcaDTO, user, orgLocations);
				
				
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		
		return response;
	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_LCA')")
	@RequestMapping(value = { "/editLcaDetails" })
	public ModelAndView editLcaDetails(
			@RequestParam(value = "lcaUuid", required = false) String lcaUuid,
			HttpServletRequest request) throws Exception {
		
			ModelAndView model = new ModelAndView();
			model.addObject("sourceleadUrl", sourceleadUrl);
				model.setViewName("/immigration/newLca");
				if (!StringUtils.isEmpty(lcaUuid)) {
					OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					Long lcaId = immigrationCommonService.getLcaIdByUUID(lcaUuid);
					LcaDTO lcaDTO = immigrationCommonService.editLcaDetails(lcaId,orgLocations);
					model.addObject("privilingWageCurrency",lcaDTO.getPrivilingWageCurrency());
					model.addObject("offeredWageCurrency",lcaDTO.getOfferedWageCurrency());
					model.addObject("lcaDTO", lcaDTO);
				}
			
		return model;
	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_DELETE_LCA')")
	@RequestMapping(value = { "/deleteLcaDetailsById" })
	public String deleteLcaDetailsById(
			@RequestParam(value = "lcaUuid", required = false) String lcaUuid,
			HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long lcaId = immigrationCommonService.getLcaIdByUUID(lcaUuid);
		
		Lca lca= immigrationCommonService.getLcaByLcaId(lcaId);
		StringBuilder actionDesc = new StringBuilder();
		if (lca != null) {
			actionDesc.append(ImmigrationConstants.LCA_DELETED).toString();
		} 
		
	//	lcaActivityCommonUtil.saveLcaActivities(user, orgLocations, lca, null, actionDesc.toString());
		
		String result = immigrationCommonService.deleteLcaDetailsById(lcaId);
		
		 
		return result;

	}
	
	@PreAuthorize("hasRole('ROLE_RE_ACTIVE_LCA')")
	@RequestMapping(value = { "/reActivateLca" })
	public String reActivateLca(
			@RequestParam(value = "lcaUuid", required = false) String lcaUuid,
			HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long lcaId = immigrationCommonService.getLcaIdByUUID(lcaUuid);
		Lca lca= immigrationCommonService.getLcaByLcaId(lcaId);
		StringBuilder actionDesc = new StringBuilder();
		if (lca != null) {
			actionDesc.append(ImmigrationConstants.LCA_RE_ACTIVE).toString();
		} 
		//lcaActivityCommonUtil.saveLcaActivities(user, orgLocations, lca, null, actionDesc.toString());
		
		String result = immigrationCommonService.reActiveLcaById(lcaId);
		return result;

	}
	
	@RequestMapping(value = "/getSocCodeWithSOCOccupationTitleList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> getSocCodeWithSOCOccupationTitleList(HttpServletRequest request,@RequestParam(value = "name", required = false) String name,@RequestParam(value = "socType", required = false) String socType) throws Exception {
		List<Map<String, String>> socDetailsTypeList = null;
		
		socDetailsTypeList = immigrationCommonService.getSocCodeWithSOCOccupationTitleList(name,ImmigrationConstants.SOC_TYPE,socType);
		
		return socDetailsTypeList;
	}
	
	@RequestMapping(value = "/getLcaNumbersList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> getLcaNumbersList(HttpServletRequest request,@RequestParam(value = "lcaNum", required = false) String lcaNum,
			@RequestParam(value = "expiryStatus", required = false) String expiryStatus,
			@RequestParam(value = "candidateId", required = false) String candidateId) throws Exception {
		
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 List<Map<String, String>> lcaNumbersList = immigrationCommonService.getLcaNumbersList(lcaNum,orgLocations.getId().toString(),expiryStatus,candidateId);
		 return lcaNumbersList;
	}
	

	@RequestMapping(value = { "/getOesSocCodeById" })
	public String getOesSocCodeById(
			@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "socType", required = false) String socType, HttpServletRequest request) throws Exception {
		return immigrationCommonService.getOesSocCodeById(id, socType);
	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_LCA')")
	@RequestMapping(value = { "/viewLca" })
	public ModelAndView viewLCA(HttpServletRequest request,
			@RequestParam(value = "lcaUuid", required = false) String lcaUuid,
			@RequestParam(value = "myLcaPage", required = false) String myLcaPage
			) throws Exception {
		
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		LcaDTO lcaDTO = null;
		if (StringUtils.isNoneBlank(lcaUuid)) {
			Long lcaId = immigrationCommonService.getLcaIdByUUID(lcaUuid);
			model.addObject("lcaId", lcaId);
			if (lcaUuid != null && !StringUtils.isEmpty(lcaUuid)) {
				 lcaDTO = immigrationCommonService.editLcaDetails(lcaId,orgLocations);
			}
            
            if(lcaDTO.getLcaDocumentList() != null && !lcaDTO.getLcaDocumentList().isEmpty()) {
            model.addObject("docList",lcaDTO.getLcaDocumentList());
            }
            model.addObject("locList",lcaDTO.getLcaPlaceOfEmploymentList());
			model.addObject("lcaDTO", lcaDTO);
			model.addObject("myLcaPage", myLcaPage);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("/immigration/lcaView");
		}
		return model;
	}
	
	@PreAuthorize("hasRole('ROLE_EXPORT_LCA')")
	@RequestMapping(value = { "/getLcaListExport" })
	public void getLCAListExport(@RequestParam(value = "exportType", required = false) String exportType,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		StringBuilder excelSB = new StringBuilder();
		Sheet createExcelsheet = createExcelworkbook.createSheet(excelSB.append("LCA List")
				.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date())).toString());
		
		if (!"pdf".equalsIgnoreCase(exportType)) {
			createExcelsheet = immigrationCommonService.getLCAExcelFile(createExcelsheet, createExcelworkbook,
					orgLocations);
			StringBuilder templateSB = new StringBuilder();
			file = File.createTempFile(
					templateSB.append(orgLocations.getOrgLocationName() + "_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date())).toString(), ".xls");
			file.setReadable(true);

			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileNameSB = new StringBuilder();
		response.setHeader("Content-Disposition",
				fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
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
	
	@RequestMapping(value = { "/validateLcaNumber" })
	public Boolean validateLcaNumber(
			@RequestParam(value = "lcaNo", required = false) String lcaNo,@RequestParam(value = "lcaId", required = false) Long lcaId,
			HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Lca lca = null;
		if (null != lcaId) {
			lca = immigrationCommonService.getLcaDetailsByLcaNoAndId(lcaNo, lcaId);
		}
		Boolean flag = false;
		if (lca == null) {
			flag = immigrationCommonService.validateLcaNumber(lcaNo, orgLocations.getId());
		}
		return flag;
	}
	
	@RequestMapping(value = { "/validateLcaTitle" })
	public Boolean validateLcaTitle(
			@RequestParam(value = "lcaTitle", required = false) String lcaTitle,@RequestParam(value = "lcaId", required = false) Long lcaId,
			HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Lca lca = null;
		if (null != lcaId) {
			lca = immigrationCommonService.getLcaDetailsByLcaTitleAndId(lcaTitle, lcaId);
		}
		Boolean flag = false;
		if (lca == null) {
			flag = immigrationCommonService.validateLcaTitle(lcaTitle, orgLocations.getId());
		}
		return flag;
	}
	
	@RequestMapping(value = "/getEmployeeByLcaId", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getEmployeeByLcaId(HttpServletRequest request,@RequestParam(value = "lcaId", required = false) Long lcaId) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<LcaDetailsDTO> empList = immigrationCommonService.getEmployeeByLcaId(lcaId ,orgLocations);
		model.addObject("empList",empList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/immigration/employeeDetailsTab");
		return model;
	}
	
	/*
	 * LCA Code Ends Here
	 */
}
