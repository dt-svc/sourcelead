package com.bis.sourcelead.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.OrgLocationService;

@RestController
@RequestMapping(value = "/orgLocation")
public class OrgLocationRestController {
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody List<OrgLocations> getAllOrgLocations() throws Exception {
		List<OrgLocations> orgLocationsList  = orgLocationService.getAllOrgLocations();
		return orgLocationsList;
	}
	
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	public OrgLocations getOrgLocations(@PathVariable Long id) throws Exception
	{
		OrgLocations orgLocations =	orgLocationService.getOrgLocations(id);
		return orgLocations;
	}

	@RequestMapping(value = "/addOrg", method = RequestMethod.GET,headers="Accept=application/json")
	public OrgLocations addOrgLocations(@PathVariable Long id) throws Exception
	{
		OrgLocations orgLocations = new OrgLocations();
		OrgLocations orgLocations1 = orgLocationService.addOrgLocations(orgLocations);
		return orgLocations1;
	}
	
	
	
}
