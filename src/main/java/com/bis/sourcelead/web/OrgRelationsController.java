package com.bis.sourcelead.web;

import java.io.Writer;
import java.util.Collection;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
/**
 * Handles requests for the Organization service.
 * 
 * @author prasadK
 * @date 08th jul 2016
 *
 */

@RestController
public class OrgRelationsController {

	private static final Logger logger = LoggerFactory.getLogger(OrgRelationsController.class);

	
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;

	
	/*------------Create a OrgRelations--------------------------------*/
	/**
	 * Handles request for storing OrgRelations into Database
	 * @return orgRelations
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/saveOrgRelations", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody OrgRelations createOrgRelations() throws Exception {
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setId(4L);
		orgRelations.setRelationType("Customer");
		orgRelations.setCreatedDate(new Date());
		return  orgRelationsService.addOrgRelations(orgRelations);
	}

	
	@RequestMapping(value = "/saveOrgRelations", method = RequestMethod.GET)
	public @ResponseBody  String addModelDialogTasks(
			@RequestParam(value="json", required=false) String json ) throws Exception
			 
	{
		String resultJson = null;
		
		JSONObject jsonObj = new JSONObject(json);
		Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		Org org = new Org();
		org.setOrgName(jsonObj.getString("orgName"));
		org.setWebSite(jsonObj.getString("webSite"));
		
		Org addOrg = orgService.addOrganization(org);
		
		/*OrgLocations  orgLocations = new OrgLocations();
		orgLocations.setId(5l);;
		
		OrgLocations addOrgLocations=orgLocationService.addOrgLocations(orgLocations);
		
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setRelationType("Customer");
		orgRelations.setCreatedDate(new Date());
		orgRelations.setUpdatedDate(new Date());
		
		OrgRelations addRelations = orgRelationsService.addOrgRelations(orgRelations);*/
		
		Collection<Org> orgsList = orgService.getOrgs();
		
		resultJson = toJSON(orgsList);
		 return resultJson;
	}
	
	private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
	
	
}
