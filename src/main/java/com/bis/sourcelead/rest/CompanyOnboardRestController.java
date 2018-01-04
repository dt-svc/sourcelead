package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.FederalAndStateTaxDTO;
import com.bis.sourcelead.dto.OrgLocationBankingDetailsDTO;
import com.bis.sourcelead.dto.OrglocationOnboardDocumentsDTO;
import com.bis.sourcelead.dto.OrglocationSignatoryDTO;
import com.bis.sourcelead.dto.StateTaxesList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationBankingDetails;
import com.bis.sourcelead.entity.OrglocationOnboardDocuments;
import com.bis.sourcelead.entity.OrglocationStateTax;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrglocationBankingDetailsService;
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
	
	@Value("${static.path}")
	private String staticPath;
	
	@RequestMapping(value = "/saveOrgLocationsFederalAndStatesTax", method = RequestMethod.POST)
	public @ResponseBody String saveOrgLocationsFederalAndStatesTax(FederalAndStateTaxDTO federalAndStateTaxDTO, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		loginOrgLocation.setFederalEin(federalAndStateTaxDTO.getFedralEIN());
		loginOrgLocation.setDepositSchedule(federalAndStateTaxDTO.getDepositeSchedule());
		loginOrgLocation.setFillingForm(federalAndStateTaxDTO.getFillingForm());
		loginOrgLocation.setUnemploymentTax(federalAndStateTaxDTO.getUnemploymentTax());
		orgLocationService.updateOrgLocations(loginOrgLocation);
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", loginOrgLocation);
		String result = orglocationStateTaxService.addOrglocationStateTax(federalAndStateTaxDTO, loginOrgLocation, user);
		return result;
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
	
	
	@RequestMapping(value = "/saveOrgLocationsBankingDetails", method = RequestMethod.POST)
	public @ResponseBody String saveOrgLocationsBankingDetails(OrgLocationBankingDetailsDTO bankingDetailsDTO, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		OrglocationBankingDetails bankingDetails = null;
		String result = "invalid";
		
		try {
			
			if(bankingDetailsDTO.getUuid() != null && !bankingDetailsDTO.getUuid().isEmpty()) {
				bankingDetails = orglocationBankingDetailsService.getOrglocationBankingDetailsByUUID(bankingDetailsDTO.getUuid());
			}else {
				bankingDetails = new OrglocationBankingDetails();
				bankingDetails.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			}
			bankingDetails.setOrgLocationId(loginOrgLocation);
			bankingDetails.setAccountType(bankingDetailsDTO.getAccountType());
			bankingDetails.setAccountName(bankingDetailsDTO.getAccountName());
			bankingDetails.setRoutingNumber(bankingDetailsDTO.getRoutingNumber());
			bankingDetails.setAccountNumber(bankingDetailsDTO.getAccountNumber());
			bankingDetails.setStatus(Constants.RECORD_ACTIVE);
			bankingDetails.setCreatedBy(user);
			bankingDetails.setUpdatedBy(user);
			bankingDetails.setCreatedDate(commonUtil.getOrgLocationCurrentDate(loginOrgLocation.getDateFormat(), new Date()));
			bankingDetails.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(loginOrgLocation.getDateFormat(), new Date()));
			orglocationBankingDetailsService.saveOrglocationBankingDetails(bankingDetails);
			result = "success";
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	
		return result;
	}
	
	
	@RequestMapping(value = "/saveOrgLocationDocuments", method = RequestMethod.POST)
	public @ResponseBody String saveOrgLocationDocuments(OrglocationOnboardDocumentsDTO documentsDTO, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		documentsDTO.setStaticPath(staticPath);
		String result = orglocationOnboardDocumentsService.addOrglocationOnboardDocuments(documentsDTO, loginOrgLocation, user);
		return result;
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
	public @ResponseBody String saveOrgLocationSignatory(OrglocationSignatoryDTO orglocationSignatoryDTO,HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String result = orglocationSignatoryService.addOrglocationSignatory(orglocationSignatoryDTO, user, loginOrgLocation);
		return result;
	}
	
	@RequestMapping(value = "/skipAndSaveOrglocationSignatory", method = RequestMethod.POST)
	public @ResponseBody String skipAndSaveOrglocationSignatory(HttpServletRequest request) throws Exception
	{
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		loginOrgLocation.setIsCompanyOnboarded(Constants.YES);
		orgLocationService.updateOrgLocations(loginOrgLocation);
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", loginOrgLocation);
		return "success";
	}
	
	
	
	
}
