package com.bis.sourcelead.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.UploadedFile;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ClientImportsService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class VendorController {
	
	private static final Logger logger = LoggerFactory.getLogger(VendorController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	ClientImportsService clientImportsService;

	
	@RequestMapping(value = "/vendorsList")
	public ModelAndView vendorsList(HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
	
	/*	for(OrgRelations orgRelation : vandorsListByOrgLocationId){
			List<OrgSpoc> orgSpocs = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(), loginUser.getId(),orgLoc.getId());
			orgRelation.setNoOfContatcs(orgSpocs.size());
		}*/
		
		//for get the owners list for spocs
		List<User> userList = userService.getOwnersByOrgLocationId(orgLoc.getId());
		model.addObject("userList", userList);
	
		model.addObject("loginOrgLocId",orgLoc.getId());
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("vendors/vendorsList");
		Countries countries = countriesService.getCountriesByCountryName(orgLoc.getCountry());
		if(countries != null){
			model.addObject("loginCountryName", countries.getName());
		}
		List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLoc.getId());
		for(OrgRelations orgRelation :vendorsList){
			orgRelation.setClientName(orgRelation.getResponderLocationId().getOrg().getOrgName());
		}
		model.addObject("vendor_list", vendorsList);
        model.addObject(vendorsList);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Vendors);
		request.getSession().setAttribute("sessionData",sessionData);

		return model;

	}
/**
 * Diplay vendor jsp to enter vendor details to save
 * @author Rajashekar
 * @param request
 * @return ModelAndView
 * @throws Exception
 */
	
	 @PreAuthorize("hasRole('ROLE_CREATE_VENDOR')")
	@RequestMapping(value = "/addVendor")
	public ModelAndView addVendor(HttpServletRequest request)  {
		
		ModelAndView model = new ModelAndView();
		try{
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("vendors/vendor");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_VENDOR));
			model.addObject("userList", userList);
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Vendors);
			request.getSession().setAttribute("sessionData",sessionData);
			
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			model.addObject("loginCountryName", countries.getName());	
		}catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e)); 
		}
		return model;
	}
	/**
	 * Diplay vendor contact jsp
	 * @author Rajashekar
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_CREATE_VENDOR_CONTACT')")
	@RequestMapping(value = "/addVendorContact")
	public ModelAndView addVendorContac(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		try{
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("vendors/addVendorContact");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_VENDOR));
			List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
			model.addObject("userList", userList);
			model.addObject("loginOrgLocId", orgLocations.getId());
			model.addObject("vendorList", vandorsListByOrgLocationId);
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Vendors);
			request.getSession().setAttribute("sessionData",sessionData);
			
		}catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return model;
	}

	@PreAuthorize("hasAnyRole('ROLE_VENDOR_BULK_IMPORTS','ROLE_VENDOR_CONTACT_BULK_IMPORTS')")
	 @RequestMapping(value = "/uploadVendorForm")
		public ModelAndView uploadVendorForm(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Users);
			
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			model.setViewName("upload/uploadVendorForm");
			model.addObject("sourceleadUrl", sourceleadUrl);
			sessionData.setNav(Constants.Vendors);
			return model;

		}
	 
	 /**
		 * Handles saving for client uploads
		 * @param uploadedFile
		 * @param result
		 * @param model
		 * @param request
		 * @return
	 * @throws Exception 
		 */
		@RequestMapping("/vendorFileUpload")
		public String fileUploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,Model model, HttpServletRequest request,@RequestParam(value = "type", required = false) String responderType)throws Exception {
					
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			MultipartFile multipartFile = uploadedFile.getFile();
			if(multipartFile != null){
				String fileName = multipartFile.getOriginalFilename();
				
				
				String isFileCreated = clientImportsService.clientBulkSaving(multipartFile,user,orgLocation,org,uploadedFile.getEmail(),responderType);
				
				if ("FailureUsersForCheck.xlsx".equalsIgnoreCase(isFileCreated)) {
					model.addAttribute("errorMessage", "Please download Error file here");
		             model.addAttribute("fileName", "xlsx");
					return "upload/uploadVendorForm";
				}else if("FailureUsersForCheck.xls".equalsIgnoreCase(isFileCreated)){
					model.addAttribute("errorMessage", "Please download Error file here");
		            model.addAttribute("fileName", "xls");
					return "upload/uploadVendorForm";
				}else if("worngFileName".equalsIgnoreCase(isFileCreated)){
					model.addAttribute("fileNameWrong", "File name should follow as vendors_contacts_Import/vendors_import_template with .xls or .xlsx");
		            model.addAttribute("fileName", "xls");
					return "upload/uploadClientForm";
				}
			}else{
				return "upload/uploadClientForm";
			}
			
			
			return "redirect:/vendorsList";
			
		}
		/**
		 * to download error generated file for Vendor
		 * @param fileName
		 * @param response
		 * @throws IOException
		 */
		@RequestMapping(value = "/downloadVendorErrorFile/{fileName}", method = RequestMethod.GET)
		public void downloadFile(@PathVariable String fileName,HttpServletResponse response) throws IOException {
			
				File file;
				if("xlsx".equalsIgnoreCase(fileName)){
					file = new File("FailureUsersForCheck.xlsx");
				}else{
					 file = new File("FailureUsersForCheck.xls");
				}
				
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();	
		}
}
