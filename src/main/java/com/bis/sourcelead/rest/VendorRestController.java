package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * 
 * @author Rajashekar
 * Handers all Vendor actions
 *
 */

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class VendorRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@RequestMapping(value = "/saveVendorContact", method = RequestMethod.POST)
	public @ResponseBody String saveVenodrContact(@RequestParam(value = "json", required = false) String json,HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	
		SourceLeadSessionManager.setSessionAttribute(request, "clientCreateMsg",
				"Successfully you have created a client");

		return "vendorsList";
	}

	
}
