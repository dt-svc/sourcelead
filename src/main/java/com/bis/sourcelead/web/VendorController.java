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

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.UploadedFile;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.entity.VendorOnboardingTemplate;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.VendorImportService;
import com.bis.sourcelead.service.VendorOnboardService;
import com.bis.sourcelead.service.VendorOnboardTemplateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
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
	StatesService statesService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	VendorOnboardTemplateService vendorOnboardTemplateService;
	
//	@Autowired
//	ClientImportsService clientImportsService;
	
	@Autowired
	private VendorImportService vendorImportService;
	
	@Autowired
	VendorOnboardService vendorOnboardService;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;

	/* This method is used for landing page of vendors tab. So, who has vendors tab access they can see vendors list. No preAuthorize for this method */
	@RequestMapping(value = "/vendorsList")
	public ModelAndView vendorsList(HttpServletRequest request,@RequestParam(value="searchResult", required=false)String searchResult,
			@RequestParam(value="searchValue", required=false)String searchValue,
			@RequestParam(value="searchType", required=false)String searchType,
			@RequestParam(value="responderLocationId", required=false)String responderLocationId,
			@RequestParam(value="orgRelationId", required=false)String orgRelationId,
			@RequestParam(value="orgRelationUUID", required=false)String orgRelationUUID,
			@RequestParam(value="orgName", required=false)String orgName,
			@RequestParam(value="vendorKeyword", required=false)String vendorKeyword,
			@RequestParam(value="contactsTab", required=false)String contactsTab) throws Exception {
		
		
		ModelAndView model = new ModelAndView();
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	
	/*	for(OrgRelations orgRelation : vandorsListByOrgLocationId){
			List<OrgSpoc> orgSpocs = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(), loginUser.getId(),orgLoc.getId());
			orgRelation.setNoOfContatcs(orgSpocs.size());
		}*/
		
		//for get the owners list for spocs
		//List<User> userList = userService.getOwnersByOrgLocationId(orgLoc.getId());
		
		SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_VENDOR);
	     model.addObject("actionId",action.getId());
	     List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLoc.getId());
		 model.addObject("sourceleadSectionList",sections);
		
		List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLoc.getId(),Long.valueOf(Constants.TAB_VENDOR));
		model.addObject("userList", userList);
	
		model.addObject("loginOrgLocId",orgLoc.getId());
		model.addObject("contactsTab",contactsTab);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("vendors/vendorsList");
		List<Countries> countryList = countriesService.getAllCountries();
		model.addObject("countryList", countryList);
		
		if(null != vendorKeyword && !"".equalsIgnoreCase(vendorKeyword) &&  vendorKeyword.equalsIgnoreCase("vendorSearch")){
			model.addObject("vendorKeyword",vendorKeyword);
			model.addObject("responderLocationId",responderLocationId);
			model.addObject("orgRelationId",orgRelationId);
			model.addObject("orgName",orgName);
		}
		if(searchResult != null && !searchResult.equalsIgnoreCase("undefined") && searchResult.equalsIgnoreCase("universalVendorList")){
		    model.addObject("universalVendorList", "universalVendorList");
		    model.addObject("searchValue",searchValue);
		    model.addObject("searchType",searchType);
		    model.addObject("searchType","Vendor");
		}
		
		if(null != orgLoc && StringUtils.isNotEmpty(orgLoc.getCountry())){
		
			Countries countries = countriesService.getCountriesByCountryName(orgLoc.getCountry());
			if(null != countries){
			   model.addObject("loginCountryName", countries.getName());
			}
			if(countries!= null && countries.getName()!= null) {

	        List<States> statesList = statesService.getAllStatesByCountry(countries.getName());
	        model.addObject("statesList", statesList);
		    }
			
		}	
		
		List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLoc.getId());
		for(OrgRelations orgRelation :vendorsList){
			if(orgRelation.getLocationId() != null && null != orgRelation.getLocationId().getId() && orgRelation.getLocationId().getId().longValue() == orgLoc.getId().longValue()){
				if(null != orgRelation.getResponderLocationId()){
					orgRelation.setVendorNameForList(orgRelation.getResponderLocationId().getOrgLocationName());
				}
			}
			if(null != orgRelation.getResponderLocationId() && null != orgRelation.getResponderLocationId().getId() && orgRelation.getResponderLocationId().getId().longValue() == orgLoc.getId().longValue()){
				if(null != orgRelation.getLocationId()){
					orgRelation.setVendorNameForList(orgRelation.getLocationId().getOrgLocationName());
				}
			}
			
		}
		model.addObject("vendor_list", vendorsList);
        model.addObject(vendorsList);
        
        //this block is for success messages -@pavanjampana
        String msg = "";
		msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "vendorCreateMsg");
		if(msg != null && !"".equals(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "vendorCreateMsg");
		}else{
			msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "vendorContactCreateMsg");
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "vendorContactCreateMsg");
		}
		
		if(msg == null){
			msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "vendorImport");
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "vendorImport");
		}
		
		if(orgRelationUUID != null){
			OrgRelations orgRelations = orgRelationsService.getOrgRelationsByUUID(orgRelationUUID);
			if(orgRelations.getLocationId().getId().longValue() == orgLoc.getId().longValue()){
				model.addObject("vendorLocationId", orgRelations.getResponderLocationId().getId());
				model.addObject("vendorName", orgRelations.getResponderLocationId().getOrgLocationName());
			}else{
				model.addObject("vendorLocationId", orgRelations.getLocationId().getId());
				model.addObject("vendorName", orgRelations.getLocationId().getOrgLocationName());
			}
			
			model.addObject("orgRelationId", orgRelations.getId());
			model.addObject("orgRelationUUID", orgRelationUUID);
		}
		
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
	public ModelAndView addVendor(@RequestParam(value = "orgRelationUUID", required = false) String orgRelationUUID,HttpServletRequest request)  {
		
		ModelAndView model = new ModelAndView();      
		try{
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("vendors/vendor");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_VENDOR));
			model.addObject("userList", userList);
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			
			 SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_VENDOR);
		     model.addObject("actionId",action.getId());
		     List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocations.getId());
			 model.addObject("sourceleadSectionList",sections);
			
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Vendors);
			request.getSession().setAttribute("sessionData",sessionData);
			
			List<VendorOnboardingTemplate> templatesList = vendorOnboardTemplateService.getVendorOnBoardingFinishedTemplatesList(orgLocations.getId());
			model.addObject("templatesList", templatesList);
			
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(countries != null){
			model.addObject("loginCountryName", countries.getName());
			}
			
			if(StringUtils.isNotBlank(orgRelationUUID)){
				OrgRelations orgRelations = orgRelationsService.getOrgRelationsByUUID(orgRelationUUID);
				if(orgRelations != null){
					model.addObject("orgRelationId", orgRelations.getId());
					model.addObject("isDocumentsFilled", orgRelations.getIsVendorDocumentsFilled());
					model.addObject("isTasksFilled", orgRelations.getIsVendorTasksFilled());
					if(orgRelations.getLocationId().getId().equals(orgLocations.getId())){
						model.addObject("vendorOrgLocationId", orgRelations.getResponderLocationId().getId());
					}else{
						model.addObject("vendorOrgLocationId", orgRelations.getLocationId().getId());
					}
					VendorOnboarding vendorOnboarding = vendorOnboardService.getVendorOnboardingByOrgRelationId(orgRelations.getId());
					if(vendorOnboarding != null){
						model.addObject("vendorOnboardId", vendorOnboarding.getId());
					}
				}
				model.addObject("orgRelationUUID", orgRelationUUID);
			}
			
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
	public ModelAndView addVendorContac(HttpServletRequest request,@RequestParam(value = "vid", required = false) String vid) {
		ModelAndView model = new ModelAndView();
		try{
			model.addObject("vid",vid);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("vendors/addVendorContact");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_VENDOR));
			List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
			model.addObject("userList", userList);
			model.addObject("loginOrgLocId", orgLocations.getId());
			model.addObject("vendorList", vandorsListByOrgLocationId);
			
			/*List<VendorOnboardingTemplate> templatesList = vendorOnboardTemplateService.getVendorOnBoardingFinishedTemplatesList(orgLocations.getId());
			model.addObject("templatesList", templatesList);*/
			
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			
			SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_VENDOR);
		     model.addObject("actionId",action.getId());
		     List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocations.getId());
			 model.addObject("sourceleadSectionList",sections);
			 
			sessionData.setNav(Constants.Vendors);
			request.getSession().setAttribute("sessionData",sessionData);
			
		}catch(Exception e){
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return model;
	}

	@PreAuthorize("hasAnyRole('ROLE_VENDOR_BULK_IMPORTS','ROLE_VENDOR_CONTACT_BULK_IMPORTS')")
	 @RequestMapping(value = "/uploadVendorForm")
		public ModelAndView uploadVendorForm(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Users);
			
			request.getSession().setAttribute("sessionData",sessionData);
			ModelAndView model = new ModelAndView();
			List<VendorOnboardingTemplate> templatesList = vendorOnboardTemplateService.getVendorOnBoardingFinishedTemplatesList(orgLocations.getId());
			model.addObject("templatesList", templatesList);
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
	    @PreAuthorize("hasAnyRole('ROLE_VENDOR_BULK_IMPORTS','ROLE_VENDOR_CONTACT_BULK_IMPORTS')")
		@RequestMapping("/vendorFileUpload")
		public String fileUploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,Model model, HttpServletRequest request,@RequestParam(value = "type", required = false) String responderType)throws Exception {
					
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			MultipartFile multipartFile = uploadedFile.getFile();
			if(multipartFile != null){
				String fn = multipartFile.getOriginalFilename(); // vendors_contacts_Import vendors_import_template.xls
				String fileName = getFileName(fn);
//				if(!fileName.contains(Constants.CLIENTS_CONTACTS_IMPORT_XLS) && !fileName.contains(Constants.CLIENTS_CONTACTS_IMPORT_XLSX) && !fileName.contains(Constants.CLIENTS_IMPORT_TEMPLATE_XLS) && !fileName.contains(Constants.CLIENTS_IMPORT_TEMPLATE_XLSX)){
					String isFileCreated = vendorImportService.vendorBulkSaving(multipartFile,user,orgLocation,org,uploadedFile.getEmail(),responderType);
					
					if ("vendor_error.xls".equalsIgnoreCase(isFileCreated)) {
						model.addAttribute("errorMessage", "Please download Error file here");
			             model.addAttribute("fileName", "vendor_error.xls");
						return "upload/uploadVendorForm";
					}else if("vendor_error.xlsx".equalsIgnoreCase(isFileCreated)){
						model.addAttribute("errorMessage", "Please download Error file here");
			            model.addAttribute("fileName", "vendor_error.xlsx");
						return "upload/uploadVendorForm";
					}/*else if("worngFileName".equalsIgnoreCase(isFileCreated)){
						model.addAttribute("fileNameWrong", "File name should follow as vendors_contacts_Import/vendors_import_template with .xls or .xlsx");
			            model.addAttribute("fileName", "xls");
						return "upload/uploadVendorForm";
					}*/
//				}else{
//					model.addAttribute("fileNameWrong", "Oops.! It's Not Vendor template");
//		            model.addAttribute("fileName", "xls");
//					return "upload/uploadVendorForm";
//				}
			}
			SourceLeadSessionManager.setSessionAttribute(request, "vendorImport", "Success!! New vendors created ");
			return "redirect:/vendorsList";
			
		}
		/**
		 * to download error generated file for Vendor
		 * @param fileName
		 * @param response
		 * @throws IOException
		 */
	    
		@RequestMapping(value = "/downloadVendorErrorFile/{fileName:.+}", method = RequestMethod.GET)
		public void downloadFile(@PathVariable String fileName,HttpServletResponse response) throws IOException {
			
				File file;
				String fn = getFileName(fileName);
					if(fileName.endsWith("xls")){
						 file = new File("vendor_error.xls");
					}else{
						file = new File("vendor_error.xlsx");
					}
				
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileNameSB = new StringBuilder();
				response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();	
		}
		
		
		
		@RequestMapping(value = "/addNewVendorOnboardTemplate")
	 	public ModelAndView addNewVendorOnboardTemplate(HttpServletRequest request) {
			    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			    ModelAndView model = new ModelAndView();
	 			commonUtil.highLightMenu(Constants.OrgSettings, request);
	 			
	 			model.setViewName("vendors/addNewVendorOnboardTemplate");
	 			model.addObject("sourceleadUrl", sourceleadUrl);
	 			return model;
	 	}
		
		@RequestMapping(value = "/vendorOnboardTemplateList")
	 	public ModelAndView vendorOnboardTemplateList(HttpServletRequest request) {
			    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			    ModelAndView model = new ModelAndView();
	 			commonUtil.highLightMenu(Constants.OrgSettings, request);
	 			
	 			model.setViewName("vendors/vendorOnboardTemplateList");
	 			model.addObject("sourceleadUrl", sourceleadUrl);
	 			return model;
	 	}
		
		
		
		
		
	/**	
	 * removing special charactes from file name
	 * @param file
	 * @return String
	 */
		private String getFileName(String file) {
			String finalName;
			if(file.contains("(")){
					StringBuilder sb = new StringBuilder();
				    String fileArray [] = file.split("\\.");
				    String fileName1 = fileArray[0].substring(0, fileArray[0].indexOf("("));
				    sb.append(fileName1).append(".").append(fileArray[1]);
				    finalName = sb.toString().replaceAll("\\s", "");
			}else{
				finalName = file;
			}
			return finalName; 
			
		}
}
