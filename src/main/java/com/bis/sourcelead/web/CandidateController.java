package com.bis.sourcelead.web;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.ImmigrationPassportDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ImmigrationVisaDTO;
import com.bis.sourcelead.dto.MarketplacePurchaseSalesDTO;
import com.bis.sourcelead.dto.OrgRelationDTO;

import com.bis.sourcelead.dto.ResponseList;

import com.bis.sourcelead.dto.RequirementSubmissionsDTO;

import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.CandidateMarketPlaceCartView;
import com.bis.sourcelead.entity.CandidatePassport;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrglocationStateTaxService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class CandidateController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	 CandidateService candidateService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	@Autowired
	StatesService statesService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	
	@Autowired
	private RolesService roleService;
	
	@Autowired
	 TimeZoneService timeZoneService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CandidateDocumentsService candidateDocumentsService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	SourceleadTagsService sourceleadTagsService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private ImmigrationCommonService immigrationCommonService;
	
	/**
	 * Handles to display candidate jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	
	//Note this method is Global Maarketplace to get all the all the candidate list.
	@PreAuthorize("hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')")
	@RequestMapping(value = "/getGlobalMarketplaceList")
	public @ResponseBody ResponseList<CandidateMarketPlaceDTO> getGlobalMarketplaceCandList(
			CandidateMarketPlaceDTO candidateMarketPlaceDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue, HttpServletRequest request) {
		ResponseList<CandidateMarketPlaceDTO> candidateMarketPlaceDTOList = null;
		logger.debug("inisde getGlobalMarketplaceCandList  conroller =========> --> ");
		try {
			String[] tableColumns = { "fullName", "location", "skillsName", "currentRate", "expectedRate", "profilePercentage" };// should
																														// be
			// entity names
			int sortColumnIndex = -1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default ascending order
			if (candidateMarketPlaceDTO.getOrder() != null && !candidateMarketPlaceDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(candidateMarketPlaceDTO.getOrder().get(0).get("column"));
				sortColumnOrder = candidateMarketPlaceDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means
				candidateMarketPlaceDTO.setSortOrder(sortColumnOrder); // direction
				// of the sorting.candidateMarketPlaceDTO
			}

			if (sortColumnIndex != -1)
				candidateMarketPlaceDTO.setSortColumn(tableColumns[sortColumnIndex]);

			candidateMarketPlaceDTOList = candidateService.getGlobalMarketCandidateList(candidateMarketPlaceDTO,
					searchValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug(
				"after controller  getGlobalMarketplaceCandList  --> " + candidateMarketPlaceDTOList.getRecordsTotal());
		return candidateMarketPlaceDTOList;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')")
			@RequestMapping(value = "/getGlobalMarketplaceListGridCard")
			public @ResponseBody List<CandidateMarketPlaceDTO> getGlobalMarketplaceCandListGridCard(
					@RequestParam(value = "searchValue", required = true)String searchValue,
					@RequestParam(value = "candidateLocation", required = true)String candidateLocation,
					@RequestParam(value = "searchCandidateIds", required = true)String[] searchCandidateIds,
					CandidateMarketPlaceDTO candidateMarketPlaceDTO,
					HttpServletRequest request) {
				List<CandidateMarketPlaceDTO> candidateMarketPlaceDTOs = null;
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				candidateMarketPlaceDTOs = candidateService.getGlobalMarketplaceCandListGridCard(searchCandidateIds, orgLocations, candidateLocation, searchValue, candidateMarketPlaceDTO);
				return candidateMarketPlaceDTOs;
			}
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/getOrgMarketplaceList")
	public @ResponseBody ResponseList<CandidateMarketPlaceDTO> getOrgMarketplaceCandList(
			CandidateMarketPlaceDTO candidateMarketPlaceDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue, 
			HttpServletRequest request) {
		ResponseList<CandidateMarketPlaceDTO> candidateMarketPlaceDTOList = null;
		logger.debug("inisde getOrgMarketplaceList  conroller =========> --> ");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		candidateMarketPlaceDTO.setOrgLocationId(orgLocations.getId().toString());
		try {
			String[] tableColumns = { "", "firstName", "location", "skillsName", "currentRate", "expectedRate", "candidateProfilePercentage", "marketPrice" };// should
																														// be
			// entity names
			int sortColumnIndex = -1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default ascending order
			if (candidateMarketPlaceDTO.getOrder() != null && !candidateMarketPlaceDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(candidateMarketPlaceDTO.getOrder().get(0).get("column"));
				sortColumnOrder = candidateMarketPlaceDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means
				candidateMarketPlaceDTO.setSortOrder(sortColumnOrder); // direction
				// of the sorting.candidateMarketPlaceDTO
			}

			if (sortColumnIndex != -1)
				candidateMarketPlaceDTO.setSortColumn(tableColumns[sortColumnIndex]);

			candidateMarketPlaceDTOList = candidateService.getGlobalMarketCandidateList(candidateMarketPlaceDTO,
					searchValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug(
				"after controller  getOrgMarketplaceList  --> " + candidateMarketPlaceDTOList.getRecordsTotal());
		return candidateMarketPlaceDTOList;
	}
	//Note this method is ResumeParsing Candidate method
	
	 @PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_CROWDSOURCING_CANDIDATES')")
	 @RequestMapping(value = "/candidate")
		public ModelAndView candidate(HttpServletRequest request) throws Exception {
		 	logger.debug("Display the candiade page");
		 	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			ModelAndView model = new ModelAndView();
			model.addObject("sourceCandidateList", candidateService.getCandidateSourceListByOrgLocationIdAndStatus(orgLocations.getId(), 1));
			model.setViewName("candidates/candidate");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
            model.addObject("loginCountryName", countries.getName());
            model.addObject("currencyCode", countries.getCurrency());
			}
            List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationsList", educationsList);
			
			
			List<String> visaList = lookUpDataService.getAllVisaByCategory();
			model.addObject("visaList", visaList);	
			
			
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillsList", skillsList);
			
			return model;
	    }
	 
	   //Note this method is ResumeParsing Candidate method
		
		 @PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_CROWDSOURCING_CANDIDATES')")
		 @RequestMapping(value = "/addCandidate")
			public ModelAndView addCandidate(HttpServletRequest request) throws Exception {
			 	logger.debug("Display the candiade page");
			 	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				ModelAndView model = new ModelAndView();
				model.setViewName("candidates/addCandidate");
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.addObject("sourceCandidateList", candidateService.getCandidateSourceListByOrgLocationIdAndStatus(orgLocations.getId(), 1));
				Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
				if(null != countries){
	            model.addObject("loginCountryName", countries.getName());
	            model.addObject("currencyCode", countries.getCurrency());
				}
	           
				List<String> visaList = lookUpDataService.getAllVisaByCategory();
				model.addObject("visaList", visaList);
	            
	            List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
				model.addObject("educationsList", educationsList);
				
				List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
				model.addObject("skillsList", skillsList);
				
				SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_CANDIDATE);
			     model.addObject("actionId",action.getId());
			     List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocations.getId());
				 model.addObject("sourceleadSectionList",sections);
				
				return model;
		    }
		 
		 
	
	
	@RequestMapping(value = "/candidatesList")
	public ModelAndView usersList(@RequestParam(value="candidateStatus", required=false) String  candidateStatus,
			@RequestParam(value="searchResult", required=false)String  searchResult
			,@RequestParam(value="searchValue", required=false)String searchValue,
			HttpServletRequest request,
			@RequestParam(value="candidateKeyword", required=false)String candidateKeyword,
			@RequestParam(value="candidateIdSearchId", required=false)String candidateIdSearchId,
			@RequestParam(value="candidateUUID", required=false)String  candidateUUID) throws Exception {
		
		logger.debug("--------candidatesList------");
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
	    String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	    Long candidateCount = 0L;
		 
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("candidates/candidatesList");	
			if(candidateKeyword != null && !candidateKeyword.equalsIgnoreCase("undefined") && candidateKeyword.equalsIgnoreCase("candidateSearch")){
			    model.addObject("candidateKeyword", candidateKeyword);
			    model.addObject("candidateIdSearchId",candidateIdSearchId);
			}
			
			if(candidateKeyword != null && !candidateKeyword.equalsIgnoreCase("undefined") && candidateKeyword.equalsIgnoreCase("requestedDocumentNotification")){
			    model.addObject("candidateKeyword", candidateKeyword);
			    model.addObject("candidateIdSearchId",candidateIdSearchId);
			}
			
			if(searchResult != null && !searchResult.equalsIgnoreCase("undefined") && searchResult.equalsIgnoreCase("universalCanddateList")){
			    model.addObject("universalCanddateList", "universalCanddateList");
			    model.addObject("serachValue",searchValue);
			    model.addObject("searchType", "Candidate");
			}
			
			
			 if(!isNullOrEmpty(userRole) && userRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
				 candidateCount = candidateService.getCandidatesCountByCandidateTypeOrgLocationIDAndUserId(orgLocations.getId(), user.getId(), "Candidate");
			 }else{
				 candidateCount = candidateService.getCandidatesCountByCandidateTypeOrgLocationID(user.getId(),orgLocations.getId(), "Candidate");
			 }
		  
		   model.addObject("candidateCount", candidateCount);
		   
			List<Countries> countryList = countriesService.getAllCountries();
			model.addObject("countryList", countryList);
			
			if(null != orgLocations && StringUtils.isNotEmpty(orgLocations.getCountry())){
			
				Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
				if(null != countries){
				   model.addObject("loginCountryName", countries.getName());
				   model.addObject("currency", countries.getCurrency());
				   
				}
		        List<States> statesList = statesService.getAllStatesByCountry(countries.getName());
		        model.addObject("statesList", statesList);
			}
			
			//approver list
			List<User> requirementApproverList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_CANDIATE));
			model.addObject("requirementApproverList", requirementApproverList);
					
			
		/*	List<Candidate>   candidatesList = candidateService.getCandidatesListByOrgLocationID(orgLocations.getId(),org.getId(),Constants.CANDIDATE_ACTIVE,"");
			model.addObject("candidatesList", candidatesList);*/
			
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillsList", skillsList);
			
			List<LookUpData> visaList = lookUpDataService.getLookUpDataByCategory(Constants.VISA);
			model.addObject("visaList", visaList);
			
			
			Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(orgLocations.getId());
			SourceLeadSessionManager.setSessionAttribute(request, "tagsList", tagsList);
			
			List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationsList", educationsList);
			
			List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
			model.addObject("documentsList", documentsList);
			
			List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(orgLocations.getId());
		    model.addObject("clientsList", orgClientList);
			  
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(null == sessionData)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Candidates);
			model.addObject("candidateType", "Candidates");
			request.getSession().setAttribute("sessionData",sessionData);
			
			//this block is for success messages --@Yashoda
					String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "candidateCreateMsg");
					if(null != msg && !"".equals(msg)){
						model.addObject("msg", msg);
						SourceLeadSessionManager.removeSessionAttribute(request, "candidateCreateMsg");
					}
					else{
						msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "consultantCreateMsg");
						model.addObject("msg", msg);
						SourceLeadSessionManager.removeSessionAttribute(request, "consultantCreateMsg");
					}	
		if(StringUtils.isNotBlank(candidateUUID)) {
			Candidate candidate = candidateService.getCandidateByUUID(candidateUUID);	
			model.addObject("candidateId", candidate.getId());
			model.addObject("candidateUUID", candidateUUID);
		}
		return model;

	}
	
	
	
	@PreAuthorize("hasRole('ROLE_ASSIGN_HOTLIST_TO_TEAM')")
	@RequestMapping(value = "/assignHotListToTeam")
	public ModelAndView assignHotListToTeam(HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("candidates/assignHotListToTeam");
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Associates);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		
		return model;

	}
	
	
	@PreAuthorize("hasRole('ROLE_CREATE_CANDIDATE')")
	@RequestMapping(value = "/addConsultant")
	public ModelAndView addConsultant(@RequestParam(value="type", required=false) String  type,HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		
		ModelAndView model = new ModelAndView();
		
		List<Roles> rolesList = roleService.getOrgEnrolledRolesByOrgLocationId(org, orgLocations.getId());
		model.addObject("rolesList", rolesList);
		
		model.setViewName("candidates/addConsultant");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("type", type);
	
		/* Based on Country Currency code will be set into Rate field */
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		if(null != countries){
        model.addObject("loginCountryName", countries.getName());
        model.addObject("currencyCode", countries.getCurrency());
		}
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(null == sessionData)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Candidates);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		/* educationsList,skillsList are used for autocompletes for education and skills in add consultant creation */
		
		 List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
		 model.addObject("educationsList", educationsList);
		 
		 List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
		 model.addObject("skillsList", skillsList);
		 
		 List<String> visaList = lookUpDataService.getAllVisaByCategory();
		 model.addObject("visaList", visaList);
		
		return model;
		
	}
	
	 
	 /*
	  * author PrasadK
	  * redirect to candidateReports page
	  */
	 @RequestMapping(value = "/candidateListForReports")
		public ModelAndView candidateListForReports(@RequestParam(value="serachValue", required=false)String serachValue,HttpServletRequest request) throws Exception {
		 
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("candidates/candidatesReports");
		 
		 Long candidateCount = candidateService.getCandidatesCountByCandidateTypeOrgLocationID(user.getId(),orgLocations.getId(), "Candidate");
//		 Long consultantCount = candidateService.getCandidatesCountByCandidateTypeOrgLocationID(orgLocations.getId(), "Consultant");
         
		 List<User> ownersList = candidateService.getAllCandidateOwnersByLocationIdAndCandidateType(orgLocations.getId(), "");
		 model.addObject("ownersList", ownersList);
		 
//		 model.addObject("consultantCount", consultantCount);
		 model.addObject("candidateCount", candidateCount);
		 
		 return model;
		
	 }	 
	 
	 @RequestMapping(value = "/candidateMarketplaceCart")
	 public ModelAndView candidateMarketplaceCart()throws Exception
	 { 
		 ModelAndView model = new ModelAndView();
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 model.setViewName("candidates/candidateMarketplaceCart");
		 return model;
	 }
	 
	
	 @RequestMapping(value = "/viewProfessionalCandidateResume/{title}", method = RequestMethod.GET)
	    public void viewProfessionalCandidateResume(@RequestParam(value="resumeUUID", required=false) String resumeUUID,
	    		@PathVariable(value="title") String title,
	    		HttpServletResponse response) throws Exception {
		 
		 CandidateResumes candidateResume =  candidateResumesService.getCandidateResumeByResumeUUID(resumeUUID);	
			    
		 /*byte[] pdfBytes = null;
			if("application/pdf".equalsIgnoreCase(candidateResume.getFileContentType())){
				pdfBytes = candidateResume.getDocFile();
			}
			else{
				 pdfBytes = pdfConversionUtil.convertDocToPdf(candidateResume.getDocFile());
			}*/
			 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
		  	    response.setContentType("application/pdf");
		  	    ServletOutputStream sos = response.getOutputStream();
		  	    /*if(null != pdfBytes){
		  	    	response.setContentLength(pdfBytes.length);
		  	    	sos.write(pdfBytes);
		  	    }*/
		          sos.flush();
		          sos.close();

		} 
	 
	 
	 @RequestMapping(value = "/viewProfessionalCandidateDocument/{title}", method = RequestMethod.GET)
	    public void viewProfessionalCandidateDocument(@RequestParam(value="documentUUID", required=false) String documentUUID,
	    		@PathVariable(value="title") String title,
	    		HttpServletResponse response) throws Exception {
		 
		 CandidateDocuments candidateDocuments =  candidateDocumentsService.getCandidateDocumentByDocumentUUID(documentUUID);	
			    
		 byte[] pdfBytes = null;
			/*if("application/pdf".equalsIgnoreCase(candidateDocuments.getFileContentType())){
				pdfBytes = candidateDocuments.getDocFile();
			}
			else{
				 pdfBytes = pdfConversionUtil.convertDocToPdf(candidateDocuments.getDocFile());
			}*/
			 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
		  	    response.setContentType("application/pdf");
		  	    ServletOutputStream sos = response.getOutputStream();
		  	    if(null != pdfBytes){
		  	    	response.setContentLength(pdfBytes.length);
		  	    	sos.write(pdfBytes);
		  	    }
		          sos.flush();
		          sos.close();

		} 
	 
	 
	 @RequestMapping(value = "/candidateResumeDownload", method = RequestMethod.GET)
	    public void candidateResumeDownload(@RequestParam(value="candidateID", required=false) String candidateID ,HttpServletResponse response) throws Exception {
			
		 CandidateResumes candidateResumes = candidateResumesService.getCandidateResumesListByCandidateId(Long.parseLong(candidateID));
			    
			    response.setContentType("application/octet-stream");
			    StringBuilder fileName = new StringBuilder();
		        response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(candidateResumes.getFileName()).append("\"").toString());
			    
				//InputStream inputStream = new ByteArrayInputStream(candidateResumes.getDocFile());
				try {
					OutputStream outStream = response.getOutputStream();

					byte[] buffer = new byte[4096];
					int bytesRead = -1;

					/*while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}

					inputStream.close();
					outStream.close();*/

				} catch (FileNotFoundException e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				} 
			}
	 
	 @RequestMapping(value = "/viewAssociateCandidateResumeDocView/{title}", method = RequestMethod.GET)
	    public void viewAssociateCandidateResumeDocView(@RequestParam(value="candidateId", required=false) String candidateId,
	    		@PathVariable(value="title") String title
	    		,HttpServletResponse response) throws Exception {
		 
		 CandidateResumes candidateResumes =  candidateResumesService.getCandidateResumesListByCandidateId(Long.parseLong(candidateId));	
			    
		 byte[] pdfBytes = null;
			/*if("application/pdf".equalsIgnoreCase(candidateResumes.getFileContentType())){
				pdfBytes = candidateResumes.getDocFile();
			}
			else{
				 pdfBytes = pdfConversionUtil.convertDocToPdf(candidateResumes.getDocFile());
			}*/
			 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
		  	    response.setContentType("application/pdf");
		  	    ServletOutputStream sos = response.getOutputStream();
		  	    /*if(null != pdfBytes){
		  	    	response.setContentLength(pdfBytes.length);
		  	    	sos.write(pdfBytes);
		  	    }*/
		          sos.flush();
		          sos.close();

		} 
	 
	 
	 
	 @PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_CROWDSOURCING_CANDIDATES')")
	 @RequestMapping(value = "/candidateSummary")
		public ModelAndView candidateSummary(@RequestParam(value="candidateId", required=false) String candidateId,HttpServletRequest request) throws Exception {
		 	logger.debug("Display the candiade page");
		 	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			ModelAndView model = new ModelAndView();
			model.setViewName("candidates/addCandidate");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			/*Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
            model.addObject("loginCountryName", countries.getName());
            model.addObject("currencyCode", countries.getCurrency());
			}
           
			List<String> visaList = lookUpDataService.getAllVisaByCategory();
			model.addObject("visaList", visaList);
            
            List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationsList", educationsList);
			
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillsList", skillsList);*/
			
			return model;
	    }
	 
	 
	 @RequestMapping(value = "/viewInterviewListResumes/{title}", method = RequestMethod.GET)
	 public void viewInterviewListResumes(@RequestParam(value="resumeUUID", required=false) String resumeUUID,
	 		@PathVariable(value="title") String title,
	 		HttpServletResponse response) throws Exception {
	  
	 	  CandidateResumes candidateResume = candidateResumesService.getCandidateResumeByResumeUUID(resumeUUID);
	 	
	 	 byte[] pdfBytes = null;
	 		/*if("application/pdf".equalsIgnoreCase(candidateResume.getFileContentType())){
	 			pdfBytes = candidateResume.getDocFile();
	 		}
	 		else{
	 			 pdfBytes = pdfConversionUtil.convertDocToPdf(candidateResume.getDocFile());
	 		}*/
	 		 response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
	 	  	    response.setContentType("application/pdf");
	 	  	    ServletOutputStream sos = response.getOutputStream();
	 	  	    /*if(null != pdfBytes){
	 	  	    	response.setContentLength(pdfBytes.length);
	 	  	    	sos.write(pdfBytes);
	 	  	    }*/
	 	          sos.flush();
	 	          sos.close();

	 } 
	 
	 
	 @RequestMapping(value = "/getCandidateSubmittedJobsList", method = RequestMethod.GET)
		public ModelAndView getCandidateSubmittedJobsList(@RequestParam(value = "candidateId", required = true) Long candidateId,HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			List<RequirementSubmissionsDTO> responseList = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			try {
				responseList = requirementSubmissionService.getCandidateSubmittedRequirementsListByCandidateId(candidateId, orgLocations.getId(),orgLocations.getDateFormat());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String locationList = orgLocations.getDateFormat();	
			String orglocationDateFormat = null;
			if(locationList.equalsIgnoreCase("yyyy-MM-dd")){
				  orglocationDateFormat = "YYYY-MM-DD";
			}if(locationList.equalsIgnoreCase("dd-MM-yyyy")){
				orglocationDateFormat = "DD-MM-YYYY"; 
			}if(locationList.equalsIgnoreCase("MM-dd-yyyy")){
				orglocationDateFormat = "MM-DD-YYYY"; 
			}if(locationList.equalsIgnoreCase("yyyy-dd-MM")){
				orglocationDateFormat = "YYYY-DD-MM"; 
			}
			
			 model.addObject("jobsList", responseList);
			 model.addObject("jobsListSize", responseList.size());
			 model.addObject("orglocationDateFormat", orglocationDateFormat);
			 model.setViewName("candidates/candidateSubmittedJobsList");
			 model.addObject("sourceleadUrl", sourceleadUrl);
			 return model;
		}
	 
	 
	 
	private boolean isNullOrEmpty(String msg) {
		// TODO Auto-generated method stub
		return false;
	}

	// Note this method is Global marketplace Candidate method
		// @PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_CROWDSOURCING_CANDIDATES')")
		@RequestMapping(value = "/globalMarketplacePage", method = RequestMethod.GET)
		public ModelAndView goToGlobalMarketplace(HttpServletRequest request) throws Exception {
			logger.debug("inisde goToGlobalMarketplace  conroller =========>");
			ModelAndView model = new ModelAndView();
			List<LookUpData> educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationsList", educationsList);
			List<LookUpData> visaList = lookUpDataService.getLookUpDataByCategory(Constants.VISA);
			model.addObject("visaList", visaList);	
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillsList", skillsList);
			List<Countries> countryList = countriesService.getAllCountries();
			model.addObject("countryList", countryList);
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if(null != orgLocations && StringUtils.isNotEmpty(orgLocations.getCountry())){
			
				Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
				if(null != countries){
				   model.addObject("loginCountryName", countries.getName());
				   model.addObject("currency", countries.getCurrency());
				   
				}
		        List<States> statesList = statesService.getAllStatesByCountry(countries.getName());
		        model.addObject("statesList", statesList);
			}
			model.setViewName("candidates/candidateGlobalMarketPlaceList");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
		}
		
		@RequestMapping(value = "/getCandidateDetailsFromMarketcart", method = RequestMethod.POST)
		public @ResponseBody List<CandidateMarketPlaceCartView> getCandidateDetailsFromMarketcart(
				@RequestParam(value = "searchValue", required = true)String searchValue,
				@RequestParam(value = "creationStartDate", required = false)String creationStartDate,
				@RequestParam(value = "creationEndDate", required = false)String creationEndDate,
				@RequestParam(value = "checkedDate", required = false)String checkedDate,
				HttpServletRequest request) throws Exception{
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<CandidateMarketPlaceCartView> candidatesFromMarketplace = null;
			if(user != null){
				if(StringUtils.isNotBlank(userRoleName)) {
					
					candidatesFromMarketplace = candidateService.getCandidateDetailsFromMarketcart(user, userRoleName, orgLocation, creationStartDate, creationEndDate, checkedDate, searchValue);
			}}
			return candidatesFromMarketplace;
		}
		
		@RequestMapping(value = "/globalCandidateSummary")
		public ModelAndView globalCandidateSummary(@RequestParam(value = "marketplaceUUID", required = false) String marketplaceUUID, HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
	        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	        CandidateMarketPlaceDTO marketPlaceDTO = candidateService.getCandidateMarketPlaceDTODetailsByUUId(marketplaceUUID,orgLocations);
	        model.addObject("marketplaceDTO", marketPlaceDTO);
	        model.setViewName("candidates/globalCandidateProfileSummary");
	        model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
		}
		
		// Sales Order Controller.
	    @PreAuthorize("hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')")
     	@RequestMapping(value = "/SalesOrder")
	    public ModelAndView goToSalesOrder(
	    		MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO,
	    		@RequestParam(value = "salesUuid", required = true) String salesUuid,
	    		HttpServletRequest request) {
	    	try{
	    		ModelAndView model = new ModelAndView();
	    		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    		MarketplacePurchaseSalesDTO salesOrder = candidateService.getMarketplaceSalesOrderBySalesUuid(orgLocations, salesUuid);
	    		model.addObject("marketplaceSalesOrder", salesOrder);
	    		model.setViewName("candidates/salesOrder");
	    		model.addObject("sourceleadUrl", sourceleadUrl);
	    		return model;
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		return null;
	    	}
		  
	}
	    
	    @RequestMapping(value = "/candidateDuplicateVerification")
		public ModelAndView redirect(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			model.addObject("sourceleadUrl", sourceleadUrl);
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if(null != countries){
            model.addObject("loginCountryName", countries.getName());
            model.addObject("currencyCode", countries.getCurrency());
			
			model.setViewName("requirement/candidateDuplicateVerification");
			}
			return model;
			
			}
			
			
	    @RequestMapping(value = "/candidateSourceList")
		public ModelAndView candidateSourceList(HttpServletRequest request, HttpServletResponse response)
		{
			ModelAndView model = new ModelAndView();
			 
			model.setViewName("candidates/candidateSourceList");
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;
		}
	/* Below method is used to get the passport details for the Associate.
	 *  Table Name: CANDIDATE_PASSPORT
	 */
	@RequestMapping(value = { "/getAssociatePassportDetails" })
		public  @ResponseBody ResponseList<ImmigrationPassportDTO> getAssociatePassportDetails(HttpServletRequest request,
				@RequestParam(value = "candidateId", required = true) Long candidateId) throws Exception {
	    	ResponseList<ImmigrationPassportDTO> reponseList = new ResponseList<>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
		try {
			if (orgLocations != null) {
				if (candidateId != null) {
					reponseList = immigrationCommonService.getAssociatePassportDetails(candidateId, orgLocations);
					reponseList.setResponse(Result.SUCCESS.name());
					reponseList.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					reponseList.setResponse(Result.INVALID_USER.name());
					reponseList.setResponseMessage(Result.INVALID_USER.getValue());
				}
			} else {
				reponseList.setResponse(Result.SESSION_TIMEDOUT.name());
				reponseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			reponseList.setResponse(Result.AWKWARD.name());
			reponseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return reponseList;
	}
	/* Below method is used to get the passport details for the Associate.
	 *  Table Names: CANDIDATE_VISA, CANDIDATE_VISA_HISTORY
	 */
	@RequestMapping(value = { "/getAssociateVisaDetails" })
	public  @ResponseBody ResponseList<ImmigrationVisaDTO> getAssociateVisaDetails(HttpServletRequest request,
			@RequestParam(value = "candidateId", required = true) Long candidateId) throws Exception {
    	ResponseList<ImmigrationVisaDTO> reponseList = new ResponseList<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
	try {
		Date toDayDate = new Date();
		List<Long> expiredVisaIdList= new ArrayList<Long>();
		if (orgLocations != null) {
			if (candidateId != null) {
				List<ImmigrationVisaDTO> visaList = new ArrayList<ImmigrationVisaDTO>();
				visaList = immigrationCommonService.getCandidateVisaDetails(candidateId,"");
				for (ImmigrationVisaDTO canVisa : visaList) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String currentDateStr = dateFormat.format(toDayDate);
					Date newDate = dateFormat.parse(currentDateStr);
					Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), newDate);
				

					if (StringUtils.isNotBlank(canVisa.getValidTo()) && currentDate.before(commonUtil.stringToDate(canVisa.getValidTo()))) {
						canVisa.setCurrentStatus("Active");
					} else {
						canVisa.setCurrentStatus("Expired");
						if(canVisa.getStatus() == 1)
						expiredVisaIdList.add(canVisa.getCandidateVisaId());
					}
					canVisa.setValidFrom(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),  commonUtil.stringToDate(canVisa.getValidFrom())));
					canVisa.setValidTo(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),  commonUtil.stringToDate(canVisa.getValidTo())));
				}
				reponseList.setData(visaList);
				reponseList.setResponse(Result.SUCCESS.name());
				reponseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				reponseList.setResponse(Result.INVALID_USER.name());
				reponseList.setResponseMessage(Result.INVALID_USER.getValue());
			}
		} else {
			reponseList.setResponse(Result.SESSION_TIMEDOUT.name());
			reponseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
	} catch (Exception e) {
		reponseList.setResponse(Result.AWKWARD.name());
		reponseList.setResponseMessage(Result.AWKWARD.getValue());
		logger.error(ExceptionUtils.getFullStackTrace(e));
	}
	return reponseList;
}
}
