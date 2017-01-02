package com.bis.sourcelead.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RoleHierarchy;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadSignature;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RoleHierarchyService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadSignatureService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

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
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	RoleHierarchyService roleHierarchyService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	SourceleadSignatureService sourceleadSignatureService;
	
   @PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
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
		    SourceLeadSessionManager.setSessionAttribute(request, "address", org.getOrgAddress());
			
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
				//org.getDocFile() is not used 
				//change the org.getDocFile() to org.getUser().getDocFile() because not store in OrgDocFile
				if(org != null)
				{
					if(org.getUser() != null){
						if(org.getUser().getDocFile() != null){
							org.setFileContentType("image/jpeg");
							InputStream in = new ByteArrayInputStream(org.getUser().getDocFile());
							BufferedImage bi = ImageIO.read(in);
							OutputStream out = response.getOutputStream();
							ImageIO.write(bi, "jpg", out);
							out.close();
						}
						
					}
					
				}
				
			} catch (IOException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e)); 
			} 
			
			catch (Exception e1) {
				logger.error(ExceptionUtils.getFullStackTrace(e1)); 
			}
   }
   
   
   
   @RequestMapping(value = "/displayOrgImageInMail/{orgCode}" , method = RequestMethod.GET)
   public void displayOrgImageInMail(@PathVariable String orgCode,HttpServletResponse response) {
  	 
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
   
   
   
   
   
   @RequestMapping(value = "/getOrgEnrolledList")
	@ResponseBody
	public String getOrgEnrolledList(HttpServletRequest request) throws Exception {
		
	 	String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ArrayList<Roles> rolesList = new ArrayList<Roles>();
		List<Roles> orgRolesList = new ArrayList<Roles>();
		
		orgRolesList = rolesService.getOrgEnrolledRolesByOrgLocationId(org, orgLocations.getId());
		for(Roles roles : orgRolesList){
			roles.setHierarchyName(roles.getRoleHierarchy().getHierarchyName());
			List<Long> roleActonsList = new ArrayList<Long>();
			if(null != roles.getActionId()){
				for (String s : roles.getActionId().split(",")){
					 roleActonsList.add(Long.parseLong(s));
				 }
			}
			roles.setActionsCount(roleActonsList.size());
			rolesList.add(roles);
		}
	
		 resultJson = toJSON(rolesList);
		 return resultJson;

	}
   
   
   @RequestMapping(value = "/roleActions/{roleId}" , method = RequestMethod.GET)
	 public @ResponseBody void roleActions(@PathVariable Long roleId,HttpServletRequest request,HttpServletResponse response) throws Exception {
			StringBuilder redirectUrl = new StringBuilder();
			response.sendRedirect(redirectUrl.append("/sourcelead/enrollRoleActions?roleId=").append(roleId).toString());
			
	   }
   
   
   @PreAuthorize("hasRole('ROLE_ENROLL_NEW_ROLE')")
   @RequestMapping(value = "/saveRoleActions" , method = RequestMethod.POST)
   public String saveRoleActions(@RequestParam(value="roleId", required=false) Long roleId,
		   @RequestParam(value="actionIds", required=false) String actionIds,HttpServletRequest request) throws Exception {
	
	   Roles roles = rolesService.getRolesById(roleId);
	   if(null != roles){
		   roles.setActionId(actionIds);
		   rolesService.storeRoles(roles);
		   SourceLeadSessionManager.setSessionAttribute(request, "actionRolesMsg", "Action Roles Modified Successfully");
	   }
	   
	return "success";
   }
   
   
   @RequestMapping(value = "/validateRoleName" , method = RequestMethod.GET)
   public String validateRoleName(@RequestParam(value="roleName", required=false) String roleName,HttpServletRequest request) throws Exception {
	
	   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   Roles roles = rolesService.getRolesByRoleDisplayNameAndOrgLocationId(roleName,orgLocations.getId());
	   if(null != roles){
		   return "invalid";
	   }else{
		   return "valid";
	   }
	
   }
   
   
   
   @RequestMapping(value = "/addNewRole" , method = RequestMethod.POST)
	 public  String addNewRole(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		 	
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	 	
	 	 Roles roles = new Roles();
	 	if(jsonObj.has("roleName") && !isNullOrEmpty(jsonObj.getString("roleName"))){
	 		StringBuilder role = new StringBuilder();
	 		roles.setRoleName(role.append("ROLE_").append(jsonObj.getString("roleName")).toString().replaceAll(" ", "_").toUpperCase());
	 		roles.setDisplayName(jsonObj.getString("roleName"));
	 		roles.setOrg(org);
	 		roles.setOrgLocations(orgLocations);
	 		roles.setStatus("1");
	 		roles.setDefaultRole(0);
	 		RoleHierarchy roleHierarchy = roleHierarchyService.getRoleHierarchyById(Long.parseLong(jsonObj.getString("level")));
	 		roles.setRoleHierarchy(roleHierarchy);
	 		roles.setCreatedBy(user.getUserCode());
	 		roles.setCreatedDate(new Date());
	 		roles = rolesService.storeRoles(roles);
	 	}
	 	
	 	ArrayList<String> rolesList = new ArrayList<String>();
	 	rolesList.add(roles.getId().toString());
	 	return commonUtil.toJSON(rolesList);
	   }
   
   
   
   
    @RequestMapping(value = "/getOrgSignature")
	public String getOrgSignature(HttpServletRequest request) throws Exception {
		
	 	String resultJson = null;
	 	SourceleadSignature signature = new SourceleadSignature();
	 	
	 	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	List<SourceleadSignature> signatureList = null;
	 	 signatureList = sourceleadSignatureService.getSourceLeadSignatureByLocationId(orgLocations.getId());
	 	 if(signatureList.size() == 0){
	 		 signatureList = sourceleadSignatureService.getSourceLeadSignature();
	 	 }
	   
		String[] fontFamily = Constants.FONT_FAMILY;
		String[] fontSize = Constants.FONT_SIZE;
		
		List<String> fontFamilyList = new ArrayList<String>();
		List<String> fontSizeList = new ArrayList<String>();
		
		for(String fFamily : fontFamily){
			fontFamilyList.add(fFamily);
		}
		
		for(String fSize : fontSize){
			fontSizeList.add(fSize);
		}
		
		signature.setFontFamilyList(fontFamilyList);
		signature.setFontSizeList(fontSizeList);
		
		signature.setSignatureList(signatureList);
		
		resultJson = toJSON(signature);
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
   
   
   
   
   
   
   protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
   
  
	   
}
