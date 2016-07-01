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
      * @param id
      * @return
      * @throws Exception
      */
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Org getAllOrgLocations(@PathVariable String id) throws Exception {
		logger.info("Orgnanization by Id");
		Org org = orgService.findOrgByID(Long.parseLong(id));
		return org;
	}

	/*--------------Retrieve All Orgs---------------------------------------*/
	/**
	 * Handles request for getting all orgs
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody List<Org> listAllOrgs() throws Exception {
		List<Org> org = orgService.getOrgs();
		return org;

	}

	/*------------Create a Org--------------------------------*/
	/**
	 * Handles request for storing org into Database
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrg", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Org createUser() throws Exception {
		Org org = new Org();
		org.setId(4L);
		org.setCreatedDate(new Date());
		org.setOrgName("Srinivas Borra");
		Org org1 = orgService.addOrganization(org);
		return org1;
	}

	// ------------ Update a Org-----------
	/**
	 * Handles request for update on org
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateOrg", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Org updateOrg() throws Exception {
		Org org = new Org();
		org.setId(4L);
		org.setCreatedDate(new Date());
		org.setOrgName("Munnii");
		Org org1 = orgService.updateOrg(org);
		return org1;

	}

	// ------------------- Delete an Org ------------------------
	/**
	 * Handles request for delete an Org
	 * @param id
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/deleteOrg/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Boolean deleteOrg(@PathVariable String id) throws Exception {

		Boolean isDeleted = orgService.deleteOrg(Long.parseLong(id));

		return isDeleted;

	}
}
