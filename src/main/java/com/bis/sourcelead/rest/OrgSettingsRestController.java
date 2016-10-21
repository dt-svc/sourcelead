package com.bis.sourcelead.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class OrgSettingsRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgSettingsRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	
   @RequestMapping(value = "/saveOrgSettings" , method = RequestMethod.POST)
   public String saveOrgSettings(@RequestParam(value="json", required=false) String json,MultipartHttpServletRequest request) throws Exception {
		
	
    Iterator<String> itrator = request.getFileNames();
    MultipartFile multiFile = request.getFile(itrator.next());
	  
	    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    
	   String  json1 = "{"+json+"}";
		Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		OrgSettings orgSettings =  gson.fromJson(json1,OrgSettings.class);
		
		   try {
			org = orgService.saveOrgSettings(orgSettings, multiFile, org);
			orgLocations = orgLocationService.saveOrgLocations(orgSettings, orgLocations);
			SourceLeadSessionManager.setSessionAttribute(request, "org", org);
		    SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
		    SourceLeadSessionManager.setSessionAttribute(request, "orgName", org.getOrgName());
			
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e)); 
		}
		   return "home";
    
   }
   
   
   
   @RequestMapping(value = "/displayOrgImage/{orgCode}" , method = RequestMethod.GET)
   public void displayOrgImage(@PathVariable String orgCode,HttpServletResponse response) {
  	 
  	 Org org = null;
			try {
				org = orgService.getOrgByOrgCode(orgCode);
				
				if(org != null && org.getDocFile() != null)
				{
					org.setFileContentType("image/jpeg");
					InputStream in = new ByteArrayInputStream(org.getDocFile());
					BufferedImage bi = ImageIO.read(in);
					OutputStream out = response.getOutputStream();
					ImageIO.write(bi, "jpg", out);
					out.close();
				}
				
			} catch (IOException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e)); 
			} 
			
			catch (Exception e1) {
				logger.error(ExceptionUtils.getFullStackTrace(e1)); 
			}
   }
   @RequestMapping(value = "/deleteOrgImage" , method = RequestMethod.GET)
   public String deleteOrgImage(HttpServletRequest request) throws Exception {
	   
	   Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	   org.setDocFile(null);
	   org.setFileContentType(null);
	   orgService.updateOrg(org);
	   SourceLeadSessionManager.setSessionAttribute(request, "org", org);
	   return "success";
   }
   
   
   protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
   
  
	   
}
