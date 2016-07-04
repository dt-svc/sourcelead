package com.bis.sourcelead.web;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.service.OrgService;
/**
 * Handles requests for the Organization service.
 * 
 * @author Rajashekar
 * @date 30th June 2016
 *
 */

@RestController
@RequestMapping(value = "/org")
public class OrgController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private OrgService orgService;

	/*----------------Retrieve Single Org-----------------------------------------*/
     /**
      * Handles request to get Org by using Id
      * @param String id
      * @return Org
      * @throws NullPointerException
      */
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Org getOrgById(@PathVariable String id) throws Exception {
		logger.info("Orgnanization by Id is "+id);
		return orgService.findOrgByID(Long.parseLong(id));
	}

	/*--------------Retrieve All Orgs---------------------------------------*/
	/**
	 * Handles request for getting all orgs
	 * @return org
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Org> listAllOrgs() throws Exception {
	  return  orgService.getOrgs();
	}

	/*------------Create a Org--------------------------------*/
	/**
	 * Handles request for storing org into Database
	 * @return org
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/saveOrg", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Org createOrg() throws Exception {
		Org org = new Org();
		org.setId(4L);
		org.setCreatedDate(new Date());
		org.setOrgName("Srinivas Borra");
		return  orgService.addOrganization(org);
	}

	// ------------ Update a Org-----------
	/**
	 * Handles request for update on org
	 * @return org
	 * @throws NullPointerException
	 */

	@RequestMapping(value = "/updateOrg", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Org updateOrg() throws Exception {
		Org org = new Org();
		org.setId(4L);
		org.setCreatedDate(new Date());
		org.setOrgName("Munnii");
		return orgService.updateOrg(org);

	}

	// ------------------- Delete an Org ------------------------
	/**
	 * Handles request for delete an Org
	 * @param String id
	 * @return boolean
	 * @throws NullPointerException
	 */

	@RequestMapping(value = "/deleteOrg/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Boolean deleteOrg(@PathVariable String id) throws Exception {

		return orgService.deleteOrg(Long.parseLong(id));


	}
}
