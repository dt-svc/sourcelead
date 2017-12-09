package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.FederalAndStateTaxDTO;
import com.bis.sourcelead.dto.StateTaxesList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationStateTax;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrglocationStateTaxService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class CompanyOnboardRestController {

	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
    OrglocationStateTaxService orglocationStateTaxService;
	
	
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
				stateTaxesList.setUUID(stateTax.getUuid());
				stateTaxesDTOList.add(stateTaxesList);
			}
			
		}
		return stateTaxesDTOList;
	}
	
}
