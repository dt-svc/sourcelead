package com.bis.sourcelead.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.dto.CandidateEmployerDTO;
import com.bis.sourcelead.dto.CandidateFamilyDetailsDTO;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ImmigrationLcaDTO;
import com.bis.sourcelead.dto.ImmigrationPassportDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationVisaDTO;
import com.bis.sourcelead.dto.LcaDTO;
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
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationCandidateDocuments;
import com.bis.sourcelead.entity.ImmigrationCandidateDocumentsHistory;
import com.bis.sourcelead.entity.ImmigrationCandidateForms;
import com.bis.sourcelead.entity.Lca;
import com.bis.sourcelead.entity.LcaHistory;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.immigration.service.ImmigrationTemplateService;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.SourceleadNotesService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.ImmigrationActivityCommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/rest/immigration")
public class ImmigrationRestController {

	private static final Logger logger = LoggerFactory.getLogger(ImmigrationRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;

	@Value("${static.path}")
	private String staticPath;

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
	private SourceleadNotesService sourceleadNotesService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	
	// for mobile 
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
	
	
	// immigration summary for mobile
	@RequestMapping(value = { "/viewImmigration" })
	public  @ResponseBody  Response<ImmigrationDTO> viewImmigration(HttpServletRequest request,
			@RequestParam(value = "immiTemplateUUID", required = false) String immiTemplateUUID,
			@RequestParam(value = "candidateUUID", required = false) String candidateUUID,
			@RequestParam(value = "immigrationUUID", required = false) String immigrationUUID,
			@RequestParam(value = "myImmigrationPage", required = false) String myImmigrationPage
			) throws Exception {
		
		Response<ImmigrationDTO> response = new Response<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		ImmigrationDTO immigrationDTO = null;
		if (StringUtils.isNoneBlank(immigrationUUID)) {
			Long immigrationId = immigrationCommonService.getImmigrationIdByUUID(immigrationUUID);
			Long candidateId = immigrationCommonService.getCandidateIdByUUID(candidateUUID);
			Long templateId = immigrationTemplateService.getTemplateIdByUUID(immiTemplateUUID);
			
			immigrationDTO = new ImmigrationDTO();
			immigrationDTO.setImmigrationId(immigrationId.toString());
			immigrationDTO.setCandidateId(candidateId.toString());
			immigrationDTO.setTemplateId(templateId.toString());
			
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
			
			immigrationDTO.setTabRecordCountMap(tabRecordCountMap);
			immigrationDTO.setUscisStatusList(uscisStatusList);
			immigrationDTO.setMyImmigrationPage(myImmigrationPage);
		
		}
		response.setObject(immigrationDTO);
		return response;
	}

	
	private HashMap<String, Integer> getImmigrationViewTabStatusDetails(Long candidateId, Long immigrationId) {
		// result = "{errorMsg:'" + tabNameArray[i] + "'}";
		// logger.debug(" Record not found for the Tab Nme:::--> : " + tabNameArray[i]);
		HashMap<String, Integer> tabRecordCountMap = new HashMap<String, Integer>();
		try {
			String entityArray[] = new String[] { "CandidatePassportVisa", "CandidatePassportVisa",
					"CandidateEmployers", "Candidate", "CandidateEducation", "CandidateFamilyDetails", "Tasks" };
			String tabNameArray[] = new String[] { "Passport", "Visa", "Employers", "Residence", "Education", "Family",
					"Tasks" };
			Integer count = 0;
			for (int i = 0; i < entityArray.length; i++) {
				if (i == 0) { // CandidatePassportVisa for passport
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where candidate.id=:candidateId and idType='Passport'");
					tabRecordCountMap.put(tabNameArray[i], count);

				} else if (i == 1) {// CandidatePassportVisa for Visa
					count = immigrationCommonService.getTableRecordCountByCandidateId(entityArray[i], candidateId,
							"where candidate.id=:candidateId and idType='Visa'");
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
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tabRecordCountMap;

	}
	//PassPort for mobile
	
	@RequestMapping(value = { "/getCandidatePassportDetails" })
	public @ResponseBody ResponseList<CandidatePassport> getCandidatePassportVisaDetails(
			@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request,
			@RequestParam(value = "searchValue", required = true) String searchValue) throws Exception {
	
		
		ResponseList<CandidatePassport> responseList = new ResponseList<CandidatePassport>();
		
		logger.debug(" inisde getCandidatePassportDetails controller test -->");
		List<CandidatePassport> passportList = new ArrayList<CandidatePassport>();
		OrgLocations  orgLocation = (OrgLocations)  SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		OrgLocations orgLocations = orgLocationService.getOrgLocationById(orgLocation.getId());
		Date toDayDate = new Date();
		try {
			passportList = immigrationCommonService.getCandidatePassportDetails(candidateId,searchValue);
			for (CandidatePassport canPassport : passportList) {
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
			responseList.setData(passportList);
		} catch (Exception e) {
			logger.error("Exception caught in controller --->" + e.getMessage());
			e.printStackTrace();
		}
		logger.debug(" after getCandidatePassportDetails controller -->");
		
		return responseList;
		
	}
	
	/**
	 * 
	 * @param immigrationPassportDTO
	 * @param request
	 * @param candidateId
	 * @param immigrationId
	 * @return
	 * savaing for mobile
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
							immigrationId);
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
	
	  // passport edit for mobile
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
		
		//passport delete for mobile
		@RequestMapping(value = { "/deleteCandidatePassportDetails" })
		public Response<String> deleteCandidatePassportDetails(@RequestParam(value = "id", required = true) Long id,
				@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request
				) throws Exception {
			
			Response<String> response = new Response<String>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			immigrationCommonService.removeCandidatePassportDetails(candidateId, id, user, orgLocations);
			response.setObject("success");
			return response;
		}
		
		
		@RequestMapping(value = { "/getCandidateVisaDetails" })
		public @ResponseBody ResponseList<ImmigrationVisaDTO> getCandidateVisaDetails(
				@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request,
				@RequestParam(value = "searchValue", required = true) String searchValue) throws Exception {
			
			ResponseList<ImmigrationVisaDTO> responseList = new ResponseList<ImmigrationVisaDTO>();
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

				responseList.setData(visaList);
			} catch (Exception e) {
				logger.error("Exception caught in controller --->" + e.getMessage());
				e.printStackTrace();
			}
			logger.debug(" after getCandidateVisaDetails controller -->");
			
			return responseList;
		}
		
		/**
		 * 
		 * @param immigrationPassportVisaDTO
		 * @param request
		 * @param candidateId
		 * @param immigrationId
		 * @return
		 * saveVisaDetailsPage for mobile 
		 */
		@RequestMapping(value = { "/saveVisaDetailsPage" }, method = RequestMethod.POST)
		public Response<String> saveVisaDetailsPage(ImmigrationVisaDTO immigrationVisaDTO,
				HttpServletRequest request, @RequestParam(value = "candidateId", required = false) String candidateId,
				@RequestParam(value = "immigrationId", required = false) String immigrationId,
				@RequestParam(value = "visaHistoryId", required = false) Long visaHistoryId) {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			Response<String> response = new Response<>();
			logger.debug("Candidate ID ----> :  " + immigrationVisaDTO.getCandidateId());
	
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
		
		@RequestMapping(value = { "/deleteCandidateVisaDetails" })
		public Response<String> deleteCandidateVisaDetails(@RequestParam(value = "visaHistoryId", required = true) Long visaHistoryId, 
				@RequestParam(value = "candidateId", required = true) Long candidateId,HttpServletRequest request
				) throws Exception {
			Response<String> response = new Response<String>();
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			try {
			immigrationCommonService.removeCandidateVisaDetails(visaHistoryId,candidateId,user, orgLocations);
			response.setObject("success");
			}catch(Exception e) {
				e.printStackTrace();
				logger.error("Exception while deleting visa history record.");
			}
			return response;
		}
		// LCA List In immigration summary for mobile
		
		@RequestMapping(value = { "/getLcaDetails" })
		public ResponseList<LcaDTO> getLcaDetails(
				@RequestParam(value = "immigrationId", required = true) String immigrationId, HttpServletRequest request) throws Exception {
			
			ResponseList<LcaDTO> responseList = new ResponseList<LcaDTO>();
			logger.debug(" inisde getLcaDetails controller test -->");
			if(immigrationId != null) {
				 List<LcaDTO> lcaList = immigrationCommonService.getLcaHistoryDetailsInfo(Long.parseLong(immigrationId));
				 responseList.setData(lcaList);
			}
			return responseList;
		}
		
		// LCA From immigration 
		
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
		
		// Employers List for mobile
		@RequestMapping(value = { "/getCandidateEmployerList" })
		public ResponseList<CandidateEmployers> getCandidateEmployerList(@RequestParam(value = "candidateId", required = true) Long candidateId,
				@RequestParam(value = "searchVal", required = false) String searchVal, HttpServletRequest request)
				throws Exception {
			 ResponseList<CandidateEmployers> responseList = new ResponseList<CandidateEmployers>();
			
			List<CandidateEmployers> candidatesEmployersList = candidateEmployersService
					.getCandidatesEmployersListByCandidateIdAndOrgLocationIDImmigration(Long.valueOf(candidateId), searchVal);
			responseList.setData(candidatesEmployersList);
			return responseList;
		}
		
		@RequestMapping(value = { "/saveImmigrationEmployerDetails" }, method = RequestMethod.POST)
		public @ResponseBody  Response<String> saveImmigrationEmployerDetails(@RequestBody CandidateEmployerDTO candidateEmployerDTO,
				HttpServletRequest request) throws Exception {

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
					candidate = candidateService.getCandidateByCandidateId(candidateEmployerDTO.getCandidateId());
					immigration = immigrationCommonService.getImmigrationById(candidateEmployerDTO.getId());
					candidateEmployersService.storeCandidateEmployers(candidateEmployerDTO, user, loginOrglocation,candidateEmployerDTO.getCandidateId());
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
		
		@RequestMapping(value = { "/deleteCandidateEmployerDetails" })
		public Response<String> deleteCandidateEmployerDetails(@RequestParam(value = "id", required = true) Long id,
				@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request)
				throws Exception {
			Response<String> response = new Response<String>();
			candidateEmployersService.removeCandidateEmployerBycandidateIdandCandidateEmployerID(candidateId, id);
			response.setObject("success");
			return response;
		}
		
		// candidate education list for mobile
		
		@RequestMapping(value = { "/getCandidateEducationList" })
		public ResponseList<CandidateEducation> getCandidateEducationList(
				@RequestParam(value = "candidateId", required = true) Long candidateId,
				@RequestParam(value = "searchVal", required = false) String searchVal, HttpServletRequest request)
				throws Exception {
			
			ResponseList<CandidateEducation> responseList = new ResponseList<CandidateEducation>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			List<CandidateEducation> candidatesEducationList = candidateEducationService
					.getCandidateEducationListByCandidateIdAndOrgLocationIDImmigration(Long.valueOf(candidateId),
							orgLocations.getId(), searchVal);
			responseList.setData(candidatesEducationList);
			return responseList;
		}
		
		@RequestMapping(value = { "/getEducationLookUpdata" })
		public ResponseList<LookUpData> getEducationLookUpdata(
				 HttpServletRequest request)
				throws Exception {
			ResponseList<LookUpData> responseList = new ResponseList<LookUpData>();
			List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			responseList.setData(educationsList);
			return responseList;
		}
		// for saving education for mobile
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
		
		//edit candidate education for mobile
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
		
		//  delete candidate education for mobile
		
		@RequestMapping(value = { "/deleteCandidateEduDetails" })
		public Response<String> deleteCandidateEduDetails(@RequestParam(value = "id", required = true) Long id,
				@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request)
				throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			Response<String> response = new Response<String>();
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			candidateEducationService.removeCandidateEducationBycandidateIdandcandidateEducationId(candidateId, id, user,
					orgLocations);
			response.setObject("success");
			return response;
		}
		
		// candidate residence list for mobile
		
		@RequestMapping(value = { "/getCandidateResidenceList" })
		public ResponseList<CandidateResidence> getCandidateResidenceList(
				@RequestParam(value = "candidateId", required = true) Long candidateId,
				@RequestParam(value = "searchVal", required = false) String searchVal, HttpServletRequest request)
				throws Exception {
			ResponseList<CandidateResidence> responseList = new ResponseList<CandidateResidence>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			
			List<CandidateResidence> candidatesResidenceList = immigrationCommonService
					.getCandidatesResidenceListByCandidateIdAndOrgLocationID(candidateId, searchVal);
			for (CandidateResidence canRes : candidatesResidenceList) {
				canRes.setStartDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), canRes.getStartDate()));
				canRes.setEndDateStr(
						commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), canRes.getEndDate()));

			}
			responseList.setData(candidatesResidenceList);
		
			return responseList;
		}
		
		//candidate residence save for mobile
		
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
		
		//candidate residence edit for mobile
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
		
		//candidate residence delete for mobile 
		
		@RequestMapping(value = { "/deleteCandidateResidenceDetails" })
		public Response<String> deleteCandidateResidenceDetails(@RequestParam(value = "id", required = true) Long id,
				@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request)
				throws Exception {
			Response<String> response = new  Response<String>();
			immigrationCommonService.deleteCandidateResidenceDetails(id, candidateId);
			response.setObject("success");
			return response;
		}
		
		//Candidate Family list for mobile
		
		@RequestMapping(value = { "/getCandidateFamilyDetails" })
		public ResponseList<CandidateFamilyDetails> getCandidateFamilyDetails(
				@RequestParam(value = "candidateId", required = true) Long candidateId,
				@RequestParam(value = "searchVal", required = false) String searchVal, HttpServletRequest request) {
			ResponseList<CandidateFamilyDetails> responseList = new  ResponseList<CandidateFamilyDetails>();
			logger.debug(" inisde getCandidateFamilyDetails controller test -->");
			
			List<CandidateFamilyDetails> familyList = new ArrayList<CandidateFamilyDetails>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			try {
				
				familyList = immigrationCommonService.getCandidateFamilyDetails(candidateId, searchVal);
				for (CandidateFamilyDetails familyObj : familyList) {
					familyObj.setMarraigeDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
							familyObj.getMarraigeDate()));
					familyObj.setMarriageEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
							familyObj.getMarriageEndDate()));
					familyObj.setDateOfBirthStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
							familyObj.getDateOfBirth()));
				}
				responseList.setData(familyList);
			} catch (Exception e) {
				logger.error("Exception caught in controller --->" + e.getMessage());
				e.printStackTrace();
			}
			logger.debug(" after getCandidateFamilyDetails controller -->");
			return responseList;
		}
		
		// Candidate Family saving for mobile
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
		
		// Candidate Family edit for mobile
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
		
		// Candidate Family delete for mobile
		
		@RequestMapping(value = { "/deleteCandidateFamilyDetails" })
		public Response<String> deleteCandidateFamilyDetails(@RequestParam(value = "id", required = true) Long deleteId,
				HttpServletRequest request) {
			Response<String> response = new Response<>();
			String status = "";
			logger.debug(" in controller deleteId ---> " + deleteId);
			try {
				status = immigrationCommonService.deletetCandidateFamilyDetails(deleteId);
				response.setObject(status);
				logger.debug(" in controller status " + status);
			} catch (Exception e) {
				logger.error("Exception caught in controller --->" + e.getMessage());
				e.printStackTrace();
			}

			return response;
		}
		
		// candidate forms list for mobile
		@RequestMapping(value = { "/getCandidateImmigrationFormsList" })
		public ResponseList<ImmigrationCandidateForms> getCandidateImmigrationForms(HttpServletRequest request,
				@RequestParam(value = "candidateId", required = false) Long candidateId,
				@RequestParam(value = "templateId", defaultValue = "0") Long templateId,
				@RequestParam(value = "immigrationId", required = false) Long immigrationId,
				@RequestParam(value = "searchVal", required = false) String searchVal,
				@RequestParam(value = "myImmigration", required = false) String myImmigration) throws Exception {

			logger.debug("inside controller method getCandidateImmigrationForms--> ");
			ResponseList<ImmigrationCandidateForms> responseList = new ResponseList<ImmigrationCandidateForms>();
			
			List<ImmigrationCandidateForms> candidateFormsLst = immigrationCommonService
					.getCandidateImmigartionFormsDetails(candidateId, templateId, immigrationId, searchVal,myImmigration);
			logger.debug("Candidate forms Details updated succesfully  candidateFormsLst size  is --> "
					+ candidateFormsLst.size());
			responseList.setData(candidateFormsLst);
			
			return responseList;
		}
		
     // candidate forms save for mobile
		
	@RequestMapping(value = { "/saveCandidateImmigrationForms" }, method = RequestMethod.POST)
	public Response<String> saveCandidateImmigrationForms(ImmigrationTemplateFormDTO immigrationTemplateFormDTO,
			@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "immigrationId", required = false) String immigrationId,
			@RequestParam(value = "templateId", required = false) String templateId, HttpServletRequest request)
			throws Exception {
		 Response<String> response = new Response<String>();
		immigrationTemplateFormDTO.setImmigrationId(immigrationId);
		immigrationTemplateFormDTO.setImmigrationcandidateId(candidateId);
		immigrationTemplateFormDTO.setImmigrationTempplateId(templateId);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrglocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		immigrationTemplateFormDTO.setStaticPath(staticPath);
		immigrationCommonService.saveImmigrationCandidateForms(immigrationTemplateFormDTO, user, loginOrglocation);
		response.setObject("Success");
		logger.debug("Candidate Form  saved succesfully --> ");

		return response;
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
	
	@RequestMapping(value = { "/deleteImmigrationCandidateFormDetails" })
	public Response<String> deleteImmigrationCandidateFormDetails(@RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request) {
		Response<String> response =new Response<String>();
		String status = "";
		try {
			status = immigrationCommonService.deletetImmigrationCandidateFormsById(id);
			response.setObject(status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping(value = { "/getDocumentLookUpdata" })
	public ResponseList<LookUpData> getDocumentLookUpdata(
			 HttpServletRequest request)
			throws Exception {
		ResponseList<LookUpData> responseList = new ResponseList<LookUpData>();
		List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
		responseList.setData(documentsList);
		return responseList;
	}
	
	// immigration candidate documents(show only candidate documents not show petitionerDocs,attorneyDocs ) for mobile 
	
	@RequestMapping(value = { "/getCandidateImmigrationCandidateDocsList" })
	public ResponseList<ImmigrationCandidateDocuments> getCandidateImmigrationCandidateDocsList(
			@RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "templateid", required = true) Long templateid,
			@RequestParam(value = "immigrationId", required = true) Long immigrationId,
			@RequestParam(value = "searchVal", required = false) String searchVal,
			@RequestParam(value = "myImmigration", required = false) String myImmigration, HttpServletRequest request)
			throws Exception {
		/*ModelAndView model = new ModelAndView();*/
		ResponseList<ImmigrationCandidateDocuments> responseList = new ResponseList<ImmigrationCandidateDocuments>();
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
		}
		responseList.setData(immigrationCanDocList);
		
		return responseList;
	}
	
	@RequestMapping(value = { "/saveCandidateImmigrationDocuments" }, method = RequestMethod.POST)
	public Response<String> saveCandidateImmigrationDocuments(ImmigrationTemplateDocDTO immigrationTemplateDocDTO,
			@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "immigrationId", required = false) String immigrationId,
			@RequestParam(value = "templateId", required = false) String templateId, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<String>();
		try {
			
			immigrationTemplateDocDTO.setImmigrationId(immigrationId);
			immigrationTemplateDocDTO.setImmigrationcandidateId(candidateId);
			immigrationTemplateDocDTO.setImmigrationTempplateId(templateId);
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrglocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			immigrationTemplateDocDTO.setStaticPath(staticPath);
			immigrationCommonService.saveImmigrationCandidateDocs(immigrationTemplateDocDTO, user, loginOrglocation);
			logger.debug("Template Task Details saved succesfully --> ");
			response.setObject("success");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = { "/editDocumentDetailsByRowId" })
	public Response<ImmigrationCandidateDocuments>  editDocumentDetailsByRowId(
			@RequestParam(value = "id", required = true) Long id, HttpServletRequest request) {
		Response<ImmigrationCandidateDocuments> response = new Response<ImmigrationCandidateDocuments>();
		logger.debug(" in controller method getCandidateResidenceDetailsById id ---> " + id);
		ImmigrationCandidateDocuments immigDocObj = null;
		try {
			immigDocObj = immigrationCommonService.getImmigrationCandidateDocumentById(id);
			response.setObject(immigDocObj);
		} catch (Exception e) {
			logger.error("Exception caught in controller test --->" + e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	@RequestMapping(value = { "/deleteDocumentDetailsByRowId" })
	public Response<String> deleteDocumentDetailsByRowId(@RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request) {
		Response<String> response = new Response<String>();
		String status = "";
		try {
			status = immigrationCommonService.deletetImmigrationCandidateDocsById(id);
			response.setObject(status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
	
	@RequestMapping(value = { "/deleteImmigrationEmployerDocumentById" })
	public Response<String> deleteImmigrationEmployerDocumentById(@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<String>();
		String status = "";
		try {
		 status = candidateEmployersService.removeCandidateEmployerDocById(id);
		response.setObject(status);
		}catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
	
	@RequestMapping(value = { "/deleteImmigrationEducationDocumentById" })
	public Response<String> deleteImmigrationEducationDocumentById(@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request) throws Exception {
		
		Response<String> response = new Response<String>();
		String status = "";
		try {
			 status = candidateEducationService.removeCandidateEucationrDocById(id);
			response.setObject(status);
			}catch (Exception e) {
				e.printStackTrace();
			}

		return response;
	}
	
	
	//Task List for mobile
	@RequestMapping(value = { "/getImmigrationTasksList" })
	public ResponseList<TaskDTO> getImmigrationTasksList(
			@RequestParam(value = "immigrationId", required = false) String immigrationId,
			@RequestParam(value = "taskStatus", required = false) String taskStatus,
			@RequestParam(value = "searchVal", required = false) String searchVal, HttpServletRequest request)

			throws Exception {
		ResponseList<TaskDTO> responseList= new ResponseList<TaskDTO>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<TaskDTO> tasksList = null;
		try {
			 tasksList = taskService.getTasksListByImmigrationIdAndCandidateId(immigrationId, user, taskStatus,
					searchVal);
			 responseList.setData(tasksList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseList;
	}
	
	// Notes list for immigration candidate for mobile
	
	@RequestMapping(value = { "/getNotesListByImmigrationId" })
	public ResponseList<SourceleadNotes> getNotesListByImmigrationId(
			@RequestParam(value = "candidateId", required = true) Long candidateId,
			@RequestParam(value = "immigrationId", required = true) Long immigrationId, HttpServletRequest request)
			throws Exception {
		ResponseList<SourceleadNotes> responseList = new ResponseList<SourceleadNotes>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
	
		List<SourceleadNotes> candidatesNotesList = sourceleadNotesService.getNotesListByImmigrationId(immigrationId,
				orgLocations.getId());
		responseList.setData(candidatesNotesList);
		return responseList;
	}
	
	// Notes saving for mobile
	@RequestMapping(value = { "/saveImmigrationNotesDetails" })
	public Response<String> saveImmigrationNotesDetails(SourceleadNotesDTO sourceleadNotesDTO, HttpServletRequest request,
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "immigrationId", required = false) String immigrationId) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> response = new Response<String>();
		try {
			sourceleadNotesService.storeComments(sourceleadNotesDTO, user, orgLocations);
			response.setObject("success");
			logger.debug("Template Task Details saved succesfully --> ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	//Visa Type from lookupdata
		@RequestMapping(value = { "/getVisaTypeLookUpdata" })
		public ResponseList<String> getVisaTypeLookUpdata(
				 HttpServletRequest request)
				throws Exception {
			ResponseList<String> responseList = new ResponseList<String>();
			List<String> canVisaList = lookUpDataService.getAllVisaByCategory();
			responseList.setData(canVisaList);
			return responseList;
		}
		
		//get document Types from lookup data
		@RequestMapping(value = { "/getDocumentTypeLookUpdata" })
		public ResponseList<LookUpData> getDocumentTypeLookUpdata(
				 HttpServletRequest request)
				throws Exception {
			ResponseList<LookUpData> responseList = new ResponseList<LookUpData>();
			List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
			responseList.setData(documentsList);
			return responseList;
		}
}
