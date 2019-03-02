package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrglocationDepositTaxesDao;
import com.bis.sourcelead.dto.FederalAndStateTaxDTO;
import com.bis.sourcelead.dto.OrgLocationBankingDetailsDTO;
import com.bis.sourcelead.dto.OrgLocationIndiaBankingDetailsDTO;
import com.bis.sourcelead.dto.OrgLocationInformationDetailsDTO;
import com.bis.sourcelead.dto.OrglocationOnboardDocumentsDTO;
import com.bis.sourcelead.dto.OrglocationSignatoryDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.SingnatoryDTO;
import com.bis.sourcelead.dto.StateTaxesList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocationInformationDetails;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationBankingDetails;
import com.bis.sourcelead.entity.OrglocationDepositTaxes;
import com.bis.sourcelead.entity.OrglocationIndiaBankingDetails;
import com.bis.sourcelead.entity.OrglocationOnboardDocuments;
import com.bis.sourcelead.entity.OrglocationSignatory;
import com.bis.sourcelead.entity.OrglocationStateTax;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationIndiaBankingDetailsService;
import com.bis.sourcelead.service.OrgLocationInformationDetailsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrglocationBankingDetailsService;
import com.bis.sourcelead.service.OrglocationDepositTaxesService;
import com.bis.sourcelead.service.OrglocationOnboardDocumentsService;
import com.bis.sourcelead.service.OrglocationSignatoryService;
import com.bis.sourcelead.service.OrglocationStateTaxService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
@PropertySource("${propertyLocation:app.properties}")
public class CompanyOnboardRestController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyOnboardRestController.class);
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
    OrglocationStateTaxService orglocationStateTaxService;
	
	@Autowired
	OrglocationBankingDetailsService orglocationBankingDetailsService;
	
	@Autowired
	OrglocationOnboardDocumentsService orglocationOnboardDocumentsService;
	
	@Autowired
	OrglocationSignatoryService orglocationSignatoryService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private OrglocationDepositTaxesService orglocationDepositTaxesService;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	OrgLocationInformationDetailsService orgLocationInformationDetailsService;
	
	@Autowired
	OrgLocationIndiaBankingDetailsService orglocationIndiaBankingDetailsService;
	
	@Autowired
	OrglocationDepositTaxesDao orglocationDepositTaxesDao;
	
	@RequestMapping(value = "/saveOrgLocationsFederalAndStatesTax", method = RequestMethod.POST)
	@ResponseBody
	public Response<String> saveOrgLocationsFederalAndStatesTax(FederalAndStateTaxDTO federalAndStateTaxDTO, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		try {
			if (user != null) {
				if (Validator.validate(federalAndStateTaxDTO)) {
					loginOrgLocation.setFederalEin(federalAndStateTaxDTO.getFedralEIN());
					loginOrgLocation.setDepositSchedule(federalAndStateTaxDTO.getDepositeSchedule());
					loginOrgLocation.setFillingForm(federalAndStateTaxDTO.getFillingForm());
					loginOrgLocation.setUnemploymentTax(federalAndStateTaxDTO.getUnemploymentTax());
					orgLocationService.updateOrgLocations(loginOrgLocation);
					SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", loginOrgLocation);
					String result = orglocationStateTaxService.addOrglocationStateTax(federalAndStateTaxDTO, loginOrgLocation, user);
					
					OrglocationDepositTaxes orglocationDepositTaxes = (OrglocationDepositTaxes) SourceLeadSessionManager.getSessionAttribute(request, "orglocationDepositTaxes");
					if(orglocationDepositTaxes == null) {
						orglocationDepositTaxes = new OrglocationDepositTaxes();
					}
//					OrglocationDepositTaxes orglocationDepositTaxes = new OrglocationDepositTaxes();
					orglocationDepositTaxes.setOrgLocationId(loginOrgLocation);
					orglocationDepositTaxes.setFillerType(federalAndStateTaxDTO.getFillerType());
					orglocationDepositTaxes.setTaxDepositSchedule(federalAndStateTaxDTO.getTaxDepositSchedule());
					orglocationDepositTaxes.setFederalDeposit(federalAndStateTaxDTO.getFederalDeposit());
					orglocationDepositTaxes.setStatus(Constants.RECORD_ACTIVE);
					orglocationDepositTaxes.setCreatedBy(user);
					orglocationDepositTaxes.setUpdatedBy(user);
					orglocationDepositTaxes.setCreatedDate(commonUtil.currentDate());
					orglocationDepositTaxes.setUpdatedDate(commonUtil.currentDate());
					orglocationDepositTaxes = orglocationDepositTaxesDao.saveOrglocationDepositTaxes(orglocationDepositTaxes);
					
//					OrglocationDepositTaxes orglocationDepositTaxes = orglocationDepositTaxesService.saveOrglocationDepositTaxes(federalAndStateTaxDTO, loginOrgLocation, user);
					SourceLeadSessionManager.setSessionAttribute(request, "orglocationDepositTaxes", orglocationDepositTaxes);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}
			else {
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
	
	
	@RequestMapping(value = "/getOrgLocationsStatesTaxList", method = RequestMethod.GET)
	public @ResponseBody List<StateTaxesList> getOrgLocationsStatesTaxList(HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrglocationStateTax> orglocationStateTaxList = orglocationStateTaxService.getOrglocationStateTaxListByOrgLocationId(loginOrgLocation.getId());
		List<StateTaxesList>  stateTaxesDTOList = new ArrayList<>();
		if(orglocationStateTaxList != null && orglocationStateTaxList.size() > 0) {
			
			for(OrglocationStateTax stateTax : orglocationStateTaxList) {
				StateTaxesList stateTaxesList = new StateTaxesList();
				stateTaxesList.setStateName(stateTax.getState());
				stateTaxesList.setStateTaxAccountNumber(stateTax.getStateTaxAccountNumber());
				stateTaxesList.setStateUnemploymentTax(stateTax.getStateUnemploymentTax());
				stateTaxesList.setUuid(stateTax.getUuid());
				stateTaxesDTOList.add(stateTaxesList);
			}
			
		}
		return stateTaxesDTOList;
	}
	
	
	@RequestMapping(value = "/deleteOrgLocationStateTaxByDocumentUUID", method = RequestMethod.POST)
	public @ResponseBody String deleteOrgLocationStateTaxByDocumentUUID(@RequestParam(value = "uuid", required = true) String uuid) throws Exception
	{
		
		String result = "invalid";
		
		if(uuid != null && !uuid.isEmpty()) {
			OrglocationStateTax stateTax = orglocationStateTaxService.getOrglocationStateTaxByUUID(uuid);
			if(stateTax != null) {
				orglocationStateTaxService.deleteOrglocationStateTax(stateTax);
				result = "success";
			}
		}
		return result;
	}
	
	
	
	
	@RequestMapping(value = "/saveCompanyOnboardInformationDetails", method = RequestMethod.POST)
	@ResponseBody
	public  Response<String> saveCompanyOnboardInformationDetails(OrgLocationInformationDetailsDTO informationDetailsDTO, HttpServletRequest request) throws Exception
	{
		Response<String> response=new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		OrgLocationInformationDetails informationDetails = null;
		
		try {
			if(user !=null){
			if(Validator.validate(informationDetailsDTO)){
				informationDetails = orgLocationInformationDetailsService.getOrgLocationInformationDetailsByOrgLocationId(loginOrgLocation.getId());
			 if(informationDetails == null) {
				 informationDetails = new OrgLocationInformationDetails();
				 informationDetails.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		       }
			 informationDetails.setOrgLocationId(loginOrgLocation);
			 informationDetails.setCin(informationDetailsDTO.getCin());
			 informationDetails.setGstin(informationDetailsDTO.getGstin());
			 informationDetails.setIncorporationDate(commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),informationDetailsDTO.getIncorporationDate()));
			 informationDetails.setPan(informationDetailsDTO.getPan());
			 informationDetails.setTan(informationDetailsDTO.getTan());
			 informationDetails.setTds(informationDetailsDTO.getTds());
			 informationDetails.setPfNumber(informationDetailsDTO.getPfNumber());
			 informationDetails.setPfRegistrationDate(commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),informationDetailsDTO.getPfregistrationDate()));
			 informationDetails.setEsiNumber(informationDetailsDTO.getEsiNumber());
			 informationDetails.setEsiRegistrationDate(commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),informationDetailsDTO.getEsiregistrationDate()));
			 informationDetails.setPtNumber(informationDetailsDTO.getPtNumber());
			 informationDetails.setPtRegistrationDate(commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),informationDetailsDTO.getPtregistrationDate()));
			 informationDetails.setCreatedBy(user);
			 informationDetails.setUpdatedBy(user);
			 informationDetails.setCreatedDate(commonUtil.getOrgLocationCurrentDate(loginOrgLocation.getDateFormat(), new Date()));
			 informationDetails.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(loginOrgLocation.getDateFormat(), new Date()));
			 orgLocationInformationDetailsService.saveOrgLocationInformationDetails(informationDetails);
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
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
	
		return response;
	}
	
	
	@RequestMapping(value = "/saveOrgLocationsBankingDetails", method = RequestMethod.POST)
	@ResponseBody
	public  Response<String> saveOrgLocationsBankingDetails(OrgLocationBankingDetailsDTO bankingDetailsDTO, HttpServletRequest request) throws Exception
	{
		Response<String> response=new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		OrglocationBankingDetails bankingDetails = null;
		
		try {
			if(user !=null){
			if(Validator.validate(bankingDetailsDTO)){
				if(bankingDetailsDTO.getUuid() != null && !bankingDetailsDTO.getUuid().isEmpty()) {
				bankingDetails = orglocationBankingDetailsService.getOrglocationBankingDetailsByUUID(bankingDetailsDTO.getUuid());
			}else {
				bankingDetails = new OrglocationBankingDetails();
				bankingDetails.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			}
			bankingDetails.setOrgLocationId(loginOrgLocation);
			bankingDetails.setAccountType(bankingDetailsDTO.getAccountType());
			bankingDetails.setAccountName(bankingDetailsDTO.getAccountName());
			bankingDetails.setBankAddress(bankingDetailsDTO.getBankAddress());
			bankingDetails.setRoutingNumber(bankingDetailsDTO.getRoutingNumber());
			bankingDetails.setAccountNumber(bankingDetailsDTO.getAccountNumber());
			bankingDetails.setBankName(bankingDetailsDTO.getBankName());
			bankingDetails.setStatus(Constants.RECORD_ACTIVE);
			bankingDetails.setCreatedBy(user);
			bankingDetails.setUpdatedBy(user);
			bankingDetails.setCreatedDate(commonUtil.getOrgLocationCurrentDate(loginOrgLocation.getDateFormat(), new Date()));
			bankingDetails.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(loginOrgLocation.getDateFormat(), new Date()));
			orglocationBankingDetailsService.saveOrglocationBankingDetails(bankingDetails);
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
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
	
		return response;
	}
	
	
	
	
	
	@RequestMapping(value = "/saveOrgLocationsIndiaBankingDetails", method = RequestMethod.POST)
	@ResponseBody
	public  Response<String> saveOrgLocationsIndiaBankingDetails(OrgLocationIndiaBankingDetailsDTO bankingDetailsDTO, HttpServletRequest request) throws Exception
	{
		Response<String> response=new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		OrglocationIndiaBankingDetails bankingDetails = null;
		
		try {
			if(user !=null){
			if(Validator.validate(bankingDetailsDTO)){
				if(bankingDetailsDTO.getUuid() != null && !bankingDetailsDTO.getUuid().isEmpty()) {
				bankingDetails = orglocationIndiaBankingDetailsService.getOrglocationIndiaBankingDetailsByUUID(bankingDetailsDTO.getUuid());
			}else {
				bankingDetails = new OrglocationIndiaBankingDetails();
				bankingDetails.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			}
			bankingDetails.setOrgLocationId(loginOrgLocation);
			bankingDetails.setAccountName(bankingDetailsDTO.getAccountName());
			bankingDetails.setBankAddress(bankingDetailsDTO.getBankAddress());
			bankingDetails.setAccountNumber(bankingDetailsDTO.getAccountNumber());
			bankingDetails.setBankName(bankingDetailsDTO.getBankName());
			bankingDetails.setIfscCode(bankingDetailsDTO.getIfscCode());
			
			if(StringUtils.isNotBlank(bankingDetailsDTO.getBsrNumber())) {
				bankingDetails.setBsrNumber(bankingDetailsDTO.getBsrNumber());
			}
			if(StringUtils.isNotBlank(bankingDetailsDTO.getMicrCode())) {
				bankingDetails.setMicrCode(bankingDetailsDTO.getMicrCode());
			}
			
			bankingDetails.setStatus(Constants.RECORD_ACTIVE);
			bankingDetails.setCreatedBy(user);
			bankingDetails.setUpdatedBy(user);
			bankingDetails.setCreatedDate(commonUtil.getOrgLocationCurrentDate(loginOrgLocation.getDateFormat(), new Date()));
			bankingDetails.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(loginOrgLocation.getDateFormat(), new Date()));
			orglocationIndiaBankingDetailsService.saveOrgLocationIndiaBankingDetails(bankingDetails);
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
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
	
		return response;
	}
	
	
	
	
	@RequestMapping(value = "/getOrgLocationsBankingDetailsByUUId", method = RequestMethod.GET)
	@ResponseBody
	public  OrgLocationBankingDetailsDTO getOrgLocationsBankingDetailsByUUId(@RequestParam(value = "uuid", required = true) String uuid, HttpServletRequest request) throws Exception
	{
		OrglocationBankingDetails bankingDetails = null;
		OrgLocationBankingDetailsDTO bankingDetailsDTO = new OrgLocationBankingDetailsDTO();
		if(StringUtils.isNotBlank(uuid)) {
			bankingDetails = orglocationBankingDetailsService.getOrglocationBankingDetailsByUUID(uuid);
			if(bankingDetails != null) {
				bankingDetailsDTO.setBankName(bankingDetails.getBankName());
				bankingDetailsDTO.setAccountName(bankingDetails.getAccountName());
				bankingDetailsDTO.setAccountNumber(bankingDetails.getAccountNumber());
				bankingDetailsDTO.setAccountType(bankingDetails.getAccountType());
				bankingDetailsDTO.setBankAddress(bankingDetails.getBankAddress());
				bankingDetailsDTO.setRoutingNumber(bankingDetails.getRoutingNumber());
				bankingDetailsDTO.setUuid(bankingDetails.getUuid());
			}
		}
		
	
		return bankingDetailsDTO;
	}
	
	
	
	@RequestMapping(value = "/getOrgLocationsIndiaBankingDetailsByUUId", method = RequestMethod.GET)
	@ResponseBody
	public  OrgLocationIndiaBankingDetailsDTO getOrgLocationsIndiaBankingDetailsByUUId(@RequestParam(value = "uuid", required = true) String uuid, HttpServletRequest request) throws Exception
	{
		OrglocationIndiaBankingDetails bankingDetails = null;
		OrgLocationIndiaBankingDetailsDTO bankingDetailsDTO = new OrgLocationIndiaBankingDetailsDTO();
		if(StringUtils.isNotBlank(uuid)) {
			bankingDetails = orglocationIndiaBankingDetailsService.getOrglocationIndiaBankingDetailsByUUID(uuid);
			if(bankingDetails != null) {
				bankingDetailsDTO.setBankName(bankingDetails.getBankName());
				bankingDetailsDTO.setAccountName(bankingDetails.getAccountName());
				bankingDetailsDTO.setAccountNumber(bankingDetails.getAccountNumber());
				bankingDetailsDTO.setBankAddress(bankingDetails.getBankAddress());
				bankingDetailsDTO.setIfscCode(bankingDetails.getIfscCode());
				bankingDetailsDTO.setBsrNumber(bankingDetails.getBsrNumber());
				bankingDetailsDTO.setMicrCode(bankingDetails.getMicrCode());
				bankingDetailsDTO.setUuid(bankingDetails.getUuid());
			}
		}
		
	
		return bankingDetailsDTO;
	}
	
	
	@RequestMapping(value = "/deleteOnboardBankingDetails", method = RequestMethod.POST)
	public @ResponseBody Response<String> deleteOnboardBankingDetails(@RequestParam(value = "uuid", required = true) String uuid, HttpServletRequest request) throws Exception
	{
		
		Response<String> response=new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			if (user != null) {
				if(uuid != null && !uuid.isEmpty()) {
					OrglocationBankingDetails bankingDetails = orglocationBankingDetailsService.getOrglocationBankingDetailsByUUID(uuid);
					if(bankingDetails != null) {
						orglocationBankingDetailsService.deleteOrglocationBankingDetails(bankingDetails);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}
				}
			}
			else {
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
	
	@RequestMapping(value = "/deleteIndiaOnboardBankingDetails", method = RequestMethod.POST)
	public @ResponseBody Response<String> deleteIndiaOnboardBankingDetails(@RequestParam(value = "uuid", required = true) String uuid, HttpServletRequest request) throws Exception
	{
		
		Response<String> response=new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			if (user != null) {
				if(uuid != null && !uuid.isEmpty()) {
					OrglocationIndiaBankingDetails bankingDetails = orglocationIndiaBankingDetailsService.getOrglocationIndiaBankingDetailsByUUID(uuid);
					if(bankingDetails != null) {
						orglocationIndiaBankingDetailsService.deleteOrglocationIndiaBankingDetails(bankingDetails);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}
				}
			}
			else {
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
	
	
	@RequestMapping(value = "/saveOrgLocationDocuments", method = RequestMethod.POST)
	@ResponseBody
	public  Response<String> saveOrgLocationDocuments(OrglocationOnboardDocumentsDTO documentsDTO, HttpServletRequest request) throws Exception
	{
		Response<String> response=new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		try {
			if (user != null) {
				if (Validator.validate(documentsDTO)) {
					documentsDTO.setStaticPath(staticPath);
					String result = orglocationOnboardDocumentsService.addOrglocationOnboardDocuments(documentsDTO, loginOrgLocation, user);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}
			else {
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
	
	
	@RequestMapping(value = "/deleteOnboardDocument", method = RequestMethod.POST)
	public @ResponseBody String deleteOnboardDocument(@RequestParam(value = "uuid", required = true) String uuid) throws Exception
	{
		String result = "invalid";
		
		if(uuid != null && !uuid.isEmpty()) {
			OrglocationOnboardDocuments document = orglocationOnboardDocumentsService.getOrglocationOnboardDocumentsByUUId(uuid);
			if(document != null) {
				orglocationOnboardDocumentsService.deleteOrglocationOnboardDocuments(document);
				result = "success";
			}
		}
		return result;
	}
	
	
	@RequestMapping(value = "/saveOrgLocationSignatory", method = RequestMethod.POST)
	@ResponseBody
	public Response<String> saveOrgLocationSignatory(OrglocationSignatoryDTO orglocationSignatoryDTO,HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<String> response = new Response<>();
		try {
			if (user != null) {
				if (Validator.validate(orglocationSignatoryDTO)) {
					String result = orglocationSignatoryService.addOrglocationSignatory(orglocationSignatoryDTO, user, loginOrgLocation);
					if (!"orgSettings".equalsIgnoreCase(orglocationSignatoryDTO.getSignatoryFromType()))
						commonUtil.onboardCreationCandidateGeneratedIdUpdated(candidate, loginOrgLocation);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}
			else {
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
	
	
	
	
	@RequestMapping(value = "/saveLoginUserAsOrgLocationSignatory", method = RequestMethod.POST)
	@ResponseBody
	public Response<String> saveLoginUserAsOrgLocationSignatory(HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<String> response = new Response<>();
		try {
			if (user != null) {
				orglocationSignatoryService.addLoginUserAsCompanyOrglocationSignatory(user, loginOrgLocation);
				commonUtil.onboardCreationCandidateGeneratedIdUpdated(candidate, loginOrgLocation);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}
			else {
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
	
	
	@RequestMapping(value = "/saveCompanyOnBoardSignatory", method = RequestMethod.POST)
	@ResponseBody
	public Response<String> saveCompanyOnBoardSignatory(SingnatoryDTO singnatoryDTO,HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Candidate candidate = (Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<String> response = new Response<>();
		try {
			if (user != null) {
				if (Validator.validate(singnatoryDTO)) {
					String result = orglocationSignatoryService.addCompanyOrglocationSignatory(singnatoryDTO, user, loginOrgLocation);
					commonUtil.onboardCreationCandidateGeneratedIdUpdated(candidate, loginOrgLocation);
						
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}
			else {
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
	
	@RequestMapping(value = "/skipAndSaveOrglocationSignatory", method = RequestMethod.POST)
	public @ResponseBody String skipAndSaveOrglocationSignatory(HttpServletRequest request) throws Exception
	{
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(orgLocationId);
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
		loginOrgLocation.setIsCompanyOnboarded(Constants.YES);
		orgLocationService.updateOrgLocations(loginOrgLocation);
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", loginOrgLocation);
		commonUtil.onboardCreationCandidateGeneratedIdUpdated(candidate,loginOrgLocation);
		return "success";
	}
	
	@RequestMapping(value = "/getAllSignatoriesByLocationId", method = RequestMethod.GET)
	public @ResponseBody List<OrglocationSignatoryDTO> getAllSignatoriesByLocationId(HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<OrglocationSignatoryDTO> signatoryList = orglocationSignatoryService.getAllSignatoriesByLocationId(loginOrgLocation.getId());
		return signatoryList;
	}
	
	@RequestMapping(value = "/getSignatoriesByUUID", method = RequestMethod.GET)
	public @ResponseBody OrglocationSignatoryDTO getSignatoriesByUUID(OrglocationSignatoryDTO orglocationSignatoryDTO,HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		OrglocationSignatory signatory = orglocationSignatoryService.getOrglocationSignatoryByUUID(orglocationSignatoryDTO.getUuid());
		OrglocationSignatoryDTO signDto = new OrglocationSignatoryDTO();
		signDto.setUuid(signatory.getUuid());
		signDto.setFirstName(signatory.getUserId().getFullName());
		signDto.setAssociateId(signatory.getUserId().getId());
		if(StringUtils.isNotBlank(signatory.getAuthorizedType())){
			signDto.setAuthorizedType(Arrays.asList(signatory.getAuthorizedType().split("\\s*,\\s*")));
		}
		if(signatory.getAuthorizedAmount() != null){
			signDto.setAuthorizedAmount(signatory.getAuthorizedAmount().toString());
		}
		
		return signDto;
	}
	
	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/deactivateSignatory", method = RequestMethod.POST)
	public String deactivateSignatory(@RequestParam(value = "uuid", required = true) String uuid,
			@RequestParam(value = "status", required = true) String status,HttpServletRequest request) throws Exception
	{
		OrglocationSignatory signatory = orglocationSignatoryService.getOrglocationSignatoryByUUID(uuid);
		if("active".equalsIgnoreCase(status))
			signatory.setStatus(Constants.RECORD_ACTIVE);
		else
			signatory.setStatus(Constants.RECORD_INACTIVE);
		orglocationSignatoryService.saveOrglocationSignatory(signatory);
		return "success";
	}
	
}
