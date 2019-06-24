package com.bis.sourcelead.web;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.ContractOwnerHierarchy;
import com.bis.sourcelead.entity.ContractRequestDocuments;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.TagEntites;
import com.bis.sourcelead.rest.ContractRestController;
import com.bis.sourcelead.service.ContractRequestDocumentsService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
public class ContractController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${vmsUrl}")
	private String vmsUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	ContractRequestDocumentsService contractRequestDocumentsService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;

	@Autowired
	private ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	RequirementOffersService requirementOffersService;
	
	@Autowired
	CountriesService countriesService;
	
	@Autowired
	PdfConversionUtil pdfConversionUtil;
	
	@Autowired
	ContractRestController contractRestController;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	@Autowired
	private SourceleadTagsService sourceleadTagsService;
	
	@Autowired
	private LetterHeadService letterHeadService;
	
	/**
	 * display contract template list page
	 * @return model
	 */
	@RequestMapping(value = "/orgContractTemplate")
	public ModelAndView orgContractTemplate() {
		logger.debug("display contract template page");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/contractsTemplate");
		return model;
	}
	
	/**
	 * 	Display add template page
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADD_CONTRACT_TEMPLATE')")
	@RequestMapping(value = "/addContractTemplate")
	public ModelAndView addContractTemplate(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<LetterHead> letterHeadList = letterHeadService.getLetterheadBylocationId(orgLocation.getId());
		
		model.addObject("letterHeadList", letterHeadList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/addContractTemplate");
		return model;
	}
	/**
	 * display contract template list page
	 * @return model
	 * @throws Exception 
	 */
	@PreAuthorize("hasAnyRole('ROLE_VIEW_CONTRACT','ROLE_ADD_NEW_CONTRACT','ROLE_ADD_EXISTING_CONTRACT')")
	@RequestMapping(value = "/contractListbyStatus")
	public ModelAndView contractListbyStatus(HttpServletRequest request,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "contractType", required = false) String contractType) throws Exception {
		logger.debug("display contract template page");

		SessionMaintainanceData sessionData = (SessionMaintainanceData) request.getSession()
				.getAttribute("sessionData");
		if (sessionData == null) {
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Contract);

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		Contracts contractsCount = contractsTemplateService.getAllContractsCountByLocationIdAndStatus(orgLocation, "",
				"", "", "", user, userRoleName);
		ModelAndView model = new ModelAndView();
		if (!isNullOrEmpty(status)) {
			model.addObject("status", status);
		}
		if (!isNullOrEmpty(contractType)) {
			model.addObject("contractType", contractType);
		}
		List<String> contractTagsList = sourceleadTagsService.sourceleadTagsListByEntitypeAndOrgloactionId(TagEntites.PROJECTS.toString(), orgLocation.getId());
		String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "userCreateMsg");
		if(null != msg && !"".equals(msg)){
			model.addObject("msg", msg);
			SourceLeadSessionManager.removeSessionAttribute(request, "userCreateMsg");
		}
		model.addObject("contractTagsList", contractTagsList);
        
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("staticPath", staticPath);
		model.addObject("contractsCount", contractsCount);
		model.setViewName("contracts/contractList");
		return model;
	}
	
	
	private boolean isNullOrEmpty(String status) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * display add new contract  page
	 * @return model
	 * @throws Exception 
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADD_NEW_CONTRACT')")
	@RequestMapping(value = "/addNewContract")
	public ModelAndView addNewContract(HttpServletRequest request, @RequestParam(value="contractUUID", required=false)String contractUUID,@RequestParam(value="spocUserCode", required=false)String spocUserCode,@RequestParam(value="orgRelationId", required=false) Long orgRelationId,@RequestParam(value="type", required=false)String type, HttpServletResponse response) throws Exception {
		
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		Contracts contract = null;
		if(null != contractUUID && !contractUUID.isEmpty() && !"null".equalsIgnoreCase(contractUUID)){
			contract = contractsTemplateService.getContractByContractUUID(contractUUID);
			
			List<ContractRequestDocuments> contractRequestDocumentsList  = new ArrayList<>();
    		if(null != contract.getParentContractId()){
    			Contracts parentcontracts  = contractsTemplateService.getContractById(contract.getParentContractId().getId());
    			contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(parentcontracts.getId());
    		}else{
    			contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(contract.getId());
    		} 
    		
    		if(contract != null){
    			List<Long> parallelOwnerIds = new ArrayList<>();
    			for (ContractOwnerHierarchy coh : contract.getOwnerHierarchyList()) {
    				if(Constants.PARALLEL.equalsIgnoreCase(coh.getHierarchyType().getValue())){
    					contract.setOwnersType(coh.getHierarchyType().getValue());
    					parallelOwnerIds.add(coh.getOwnerUserId().getId());
    				}else{
    					contract.setOwnersType(coh.getHierarchyType().getValue());
    					if(coh.getLevel() == 1)
    						contract.setSequentialOwner1(coh.getOwnerUserId().getId());
    					else if(coh.getLevel() == 2)
    						contract.setSequentialOwner2(coh.getOwnerUserId().getId());
    				}
    			}
    			contract.setParallelOwnerId(parallelOwnerIds);
    		}
			
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
			//SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
			if(null != contract && null != contract.getStartDate()){
				String currentTimeStr = Constants.TZ_DF.format(contract.getStartDate());  
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
				contract.setStartDateStr(currentDateInTimezone);
			}
			if(null != contract && null != contract.getEndDate()){
				String endTimeStr = Constants.TZ_DF.format(contract.getEndDate());  
				String endDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), endTimeStr);
				Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
				endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),endconverteDate);
				contract.setEndDateStr(endDateInTimezone);
			}
			if(null != contract && null != contract.getJoinDate()){
				String joinDateStr = Constants.TZ_DF.format(contract.getJoinDate());  
				String endDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), joinDateStr);
				Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
				endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),endconverteDate);
				contract.setJoinedDateStr(endDateInTimezone);
			}
			if(null != contract){
				List<ContractsTemplate> contractsTemplateList = contractsTemplateService.getAllContractsTemplatesByContractTypeLandLocationId(orgLocation.getId(),contract.getContractType());
				model.addObject("contractsTemplateList", contractsTemplateList);
				
				List<Requirement> requirementList = null;
				List<RequirementSubmission> requirementSubmissionsList = null;
				RequirementOffers requirementOffer = null;
				if(contract.getContractType().contains("SOW") || contract.getContractType().contains("Full time offer")){
					requirementList = requirementService.getRequirementsForSOWByOrgLocationId(orgLocation.getId(), contract.getContractType());
					if(requirementList.size() > 0){
						for(Requirement requirement : requirementList){
							if(null != requirement.getSpocId() && (requirement.getSpocId().getClientUser().getFirstName() != null && requirement.getSpocId().getClientUser().getFirstName() != "" && requirement.getSpocId().getClientUser().getLastName() != null && requirement.getSpocId().getClientUser().getLastName() != "" )){
								StringBuilder spocSB = new StringBuilder();
								requirement.setSpocName(spocSB.append(" (").append(requirement.getSpocId().getClientUser().getFirstName()).append(" ").append(requirement.getSpocId().getClientUser().getLastName()).append(")").toString());
							}else if(null != requirement.getSpocId()){
								requirement.setSpocName(" ("+requirement.getSpocId().getClientUser().getFirstName()+")");
							}else{
								requirement.setSpocName("");
							}
						}
					}
					
					requirementSubmissionsList = requirementSubmissionService.getSubmissionsForSOWByRequirementId(contract.getRequirementSubmission().getRequirement().getId(), contract.getContractType(),orgLocation);
					// requirementSubmissionsList = requirementSubmissionService.getSubmissionsForSOWByOrgLocationId(contract.getContractType(),orgLocation);
					if(requirementSubmissionsList.size() > 0){
						for(RequirementSubmission requirementSubmission : requirementSubmissionsList){
							StringBuffer candidateName = new StringBuffer(); 
							if(null != requirementSubmission.getCandidate() && StringUtils.isNotBlank(requirementSubmission.getCandidate().getLastName())){
								requirementSubmission.setCandidateName(candidateName.append(requirementSubmission.getCandidate().getFirstName()).append(" ").append(requirementSubmission.getCandidate().getLastName()).toString());
							}else{
								requirementSubmission.setCandidateName(candidateName.append(requirementSubmission.getCandidate().getFirstName()).toString());
							}
						}
					}
					
					if(null != contract.getRequirementSubmission()){
						requirementOffer = requirementOffersService.getRequirementOfferSubmissionById(contract.getRequirementSubmission().getId());
					}
				}
				if(null != contract.getOrgRelations()){
				List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(contract.getOrgRelations().getId());
				
				for(OrgSpoc orgSpoc : orgSpocList){
					StringBuilder sb = new StringBuilder();
					StringBuilder clientSb = new StringBuilder();
					if(null != orgSpoc.getVendorUser().getFirstName() && null != orgSpoc.getVendorUser().getLastName()){
					sb.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName());
					}else{
					sb.append(orgSpoc.getVendorUser().getFirstName());
					}
					if(null != orgSpoc.getClientUser().getFirstName() && null != orgSpoc.getClientUser().getLastName()){
					clientSb.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName());
					}else{
					clientSb.append(orgSpoc.getClientUser().getFirstName());
					}
					orgSpoc.setSpocName(sb.toString());
					orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
					orgSpoc.setTempOwnerId(orgSpoc.getVendorUser().getId());
					orgSpoc.setOwnerName(clientSb.toString());
					/*if(orgSpoc.getVendorUser().getId().longValue()==orgSpoc.getOwnerUser().getId().longValue()){
		                orgSpoc.setOwnerUserName(clientSb.toString());
		            }
		            if(orgSpoc.getClientUser().getId().longValue()==orgSpoc.getOwnerUser().getId().longValue()){
		                orgSpoc.setOwnerUserName(sb.toString());
		            }*/
					if(null != orgSpoc.getOwnerUser()){
						orgSpoc.setOwnerUserName(orgSpoc.getOwnerUser().getFirstName());
					}else{
						User user = userService.getUserByUserCode(orgSpoc.getCreatedBy());
						StringBuilder sb1 = new StringBuilder();
						if(null != user.getFirstName() && null != user.getLastName()){
						sb1.append(user.getFirstName()).append(" ").append(user.getLastName());
						}else{
						sb1.append(user.getFirstName());
						}
						orgSpoc.setOwnerUserName(sb1.toString());
					}
				}
				model.addObject("orgSpocList", orgSpocList);
				}
				
				model.addObject("docList",contractRequestDocumentsList);
				model.addObject("requirementList", requirementList);
				model.addObject("requirementSubmissionsList", requirementSubmissionsList);
				model.addObject("requirementOffer", requirementOffer);
			}
			
		}else if("Msa".equalsIgnoreCase(type)){
			model.addObject("orgRelationId", orgRelationId);
			model.addObject("contractType", Constants.VENDOR_MSA);
		}else if("Sow".equalsIgnoreCase(type)){
			model.addObject("orgRelationId", orgRelationId);
			model.addObject("contractType", Constants.Vendor_SOW);
		}
		
//		commonUtil.highLightMenu(Constants.List_by_status, request);
		List<User> userList = contractsTemplateService.getSigningAuthoritiesForContract(orgLocation.getId(),Long.valueOf(Constants.TAB_CONTRACTS));
//		List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocation.getId(),Long.valueOf(Constants.TAB_CONTRACTS));
		List<OrgRelations> orgRelationsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
		List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
		for (OrgRelations orgRelations : orgRelationsList) {
			String orgName = "";
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getLocationId().getOrgLocationName();
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getResponderLocationId().getOrgLocationName();
			}
			orgRelations.setOrgName(orgName);
		}
		for (OrgRelations orgRelations : clientList) {
			String orgName = "";
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getLocationId().getOrgLocationName();
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getResponderLocationId().getOrgLocationName();
			}
			orgRelations.setOrgName(orgName);
		}
		
		Countries countries = countriesService.getCountriesByCountryName(orgLocation.getCountry());
		if(null != countries){
        model.addObject("currencyCode", countries.getCurrency());
		}
		
		if(StringUtils.isNotBlank(spocUserCode))
			model.addObject("spocUserCode", spocUserCode);
		
//		if(orgRelationId != null && "null".equalsIgnoreCase(contractUUID)){
//			model.addObject("orgRelationId", orgRelationId);
//			model.addObject("contractType", Constants.VENDOR_MSA);
//		}else {
//			model.addObject("orgRelationId", orgRelationId);
//			model.addObject("contractType", Constants.Vendor_SOW);
//		}
		
		SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_ADD_NEW_CONTRACT);
	     model.addObject("actionId",action.getId());
		
	    List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocation.getId());
	    model.addObject("sourceleadSectionList",sections); 
		
		List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
		model.addObject("documentsList", documentsList);
		model.addObject("orgLocation", orgLocation);
		model.addObject("contract", contract);
		model.addObject("userList", userList);
		model.addObject("clientList", clientList);
		model.addObject("orgRelationsList", orgRelationsList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/addNewContract");
		return model;
	}
	
	/**
	 * display add new contract  page 
	 * @return model
	 * @throws Exception 
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADD_EXISTING_CONTRACT')")
	@RequestMapping(value = "/addExistingContract")
	public ModelAndView addExistingContract(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//		commonUtil.highLightMenu(Constants.List_by_status, request);
		List<User> userList = contractsTemplateService.getSigningAuthoritiesForContract(orgLocation.getId(),Long.valueOf(Constants.TAB_CONTRACTS));
		List<OrgRelations> orgRelationsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
		List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
		for (OrgRelations orgRelations : orgRelationsList) {
			String orgName = "";
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getLocationId().getOrgLocationName();
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getResponderLocationId().getOrgLocationName();
			}
			orgRelations.setOrgName(orgName);
		}
		for (OrgRelations orgRelations : clientList) {
			String orgName = "";
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getLocationId().getOrgLocationName();
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getResponderLocationId().getOrgLocationName();
			}
			orgRelations.setOrgName(orgName);
		}
		
		Countries countries = countriesService.getCountriesByCountryName(orgLocation.getCountry());
		if(null != countries){
        model.addObject("currencyCode", countries.getCurrency());
		}
		List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
		model.addObject("documentsList", documentsList);
		model.addObject("orgLocation", orgLocation);
		model.addObject("userList", userList);
		model.addObject("clientList", clientList);
		model.addObject("orgRelationsList", orgRelationsList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/addExistedContract");
		return model;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_CONTRACT_TEMPLATE')")
	@RequestMapping(value = "/editTemplate")
    public ModelAndView editTemplate(HttpServletRequest request,@RequestParam(value="templateId", required=false) String templateId ,HttpServletResponse response) throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ContractsTemplate contractsTemplate =	contractsTemplateService.getContractTemplateById(Long.parseLong(templateId));
		ModelAndView model = new ModelAndView();
		List<LetterHead> letterHeadList = letterHeadService.getLetterheadBylocationId(orgLocation.getId());
		model.addObject("letterHeadList", letterHeadList);
		
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("contractsTemplate", contractsTemplate);
		model.addObject("templateContent", contractsTemplate.getTemplateContent());
		model.addObject("templateId", templateId);
		model.setViewName("contracts/addContractTemplate");
		return model;
	}
	
	/**
	 * display add new contract  page
	 * @return model
	 * @throws Exception 
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADD_NEW_CONTRACT')")
	@RequestMapping(value = "/addCustomContract")
	public ModelAndView addCustomContract(HttpServletRequest request, @RequestParam(value="contractUUID", required=false)String contractUUID,@RequestParam(value="contractFromType", required=false)String contractFromType, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		Contracts contract = null;
		if(null != contractUUID && !contractUUID.isEmpty()){
			contract = contractsTemplateService.getContractByContractUUID(contractUUID);
			
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Timezone timezone = timeZoneService.getTimezone(Long.parseLong(orgLocation.getTimeZoneId()));
		        //SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();

		 
			if(null != contract && null != contract.getStartDate()){
				String currentTimeStr = Constants.TZ_DF.format(contract.getStartDate());  
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
				contract.setStartDateStr(currentDateInTimezone);
			}
			if(null != contract && null != contract.getJoinDate()){
				String currentTimeStr = Constants.TZ_DF.format(contract.getJoinDate());  
				String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),converteDate);
				contract.setJoinedDateStr(currentDateInTimezone);
			}
			if(null != contract && null != contract.getEndDate()){
				String endTimeStr = Constants.TZ_DF.format(contract.getEndDate());  
				String endDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), endTimeStr);
				Date endconverteDate = TZ_DF_TS.parse(endDateInTimezone);
				endDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),endconverteDate);
				contract.setEndDateStr(endDateInTimezone);
			}
			
			if(null != contract){
				
				// To get Signing Authority Owners  
				List<Long> parallelOwnerIds = new ArrayList<>();
    			for (ContractOwnerHierarchy coh : contract.getOwnerHierarchyList()) {
    				if(Constants.PARALLEL.equalsIgnoreCase(coh.getHierarchyType().getValue())){
    					contract.setOwnersType(coh.getHierarchyType().getValue());
    					parallelOwnerIds.add(coh.getOwnerUserId().getId());
    				}else{
    					contract.setOwnersType(coh.getHierarchyType().getValue());
    					if(coh.getLevel() == 1)
    						contract.setSequentialOwner1(coh.getOwnerUserId().getId());
    					else if(coh.getLevel() == 2)
    						contract.setSequentialOwner2(coh.getOwnerUserId().getId());
    				}
    			}
    			contract.setParallelOwnerId(parallelOwnerIds);
    			
				
				List<ContractsTemplate> contractsTemplateList = contractsTemplateService.getAllContractsTemplatesByContractTypeLandLocationId(orgLocation.getId(),contract.getContractType());
				model.addObject("contractsTemplateList", contractsTemplateList);
				
				List<Requirement> requirementList = null;
				List<RequirementSubmission> requirementSubmissionsList = null;
				RequirementOffers requirementOffer = null;
				if(contract.getContractType().contains("SOW") || contract.getContractType().contains(Constants.FULL_TIME_OFFER)){
					requirementList = requirementService.getRequirementsForSOWByOrgLocationId(orgLocation.getId(), contract.getContractType());
					if(requirementList.size() > 0){
						for(Requirement requirement : requirementList){
							if(null != requirement.getSpocId() && (requirement.getSpocId().getClientUser().getFirstName() != null && requirement.getSpocId().getClientUser().getFirstName() != "" && requirement.getSpocId().getClientUser().getLastName() != null && requirement.getSpocId().getClientUser().getLastName() != "" )){
								StringBuilder spocSB = new StringBuilder();
								requirement.setSpocName(spocSB.append(" (").append(requirement.getSpocId().getClientUser().getFirstName()).append(" ").append(requirement.getSpocId().getClientUser().getLastName()).append(")").toString());
							}else if(null != requirement.getSpocId()){
								requirement.setSpocName(" ("+requirement.getSpocId().getClientUser().getFirstName()+")");
							}else{
								requirement.setSpocName("");
							}
						}
					}
					
					requirementSubmissionsList = requirementSubmissionService.getSubmissionsForSOWByRequirementId(contract.getRequirementSubmission().getRequirement().getId(), contract.getContractType(),orgLocation);
					//requirementSubmissionsList = requirementSubmissionService.getSubmissionsForSOWByOrgLocationId(contract.getContractType(),orgLocation);
					if(requirementSubmissionsList.size() > 0){
						for(RequirementSubmission requirementSubmission : requirementSubmissionsList){
							StringBuffer candidateName = new StringBuffer(); 
							if(null != requirementSubmission.getCandidate() && StringUtils.isNotBlank(requirementSubmission.getCandidate().getLastName())){
								requirementSubmission.setCandidateName(candidateName.append(requirementSubmission.getCandidate().getFirstName()).append(" ").append(requirementSubmission.getCandidate().getLastName()).toString());
							}else{
								requirementSubmission.setCandidateName(candidateName.append(requirementSubmission.getCandidate().getFirstName()).toString());
							}
						}
					}
					
					if(null != contract.getRequirementSubmission()){
						requirementOffer = requirementOffersService.getRequirementOfferSubmissionById(contract.getRequirementSubmission().getId());
					
					}
				}
				if(null != contract){
					if(null != contract.getOrgRelations()){
					List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(contract.getOrgRelations().getId());
					
					for(OrgSpoc orgSpoc : orgSpocList){
						StringBuilder sb = new StringBuilder();
						StringBuilder clientSb = new StringBuilder();
						if(null != orgSpoc.getVendorUser().getFirstName() && null != orgSpoc.getVendorUser().getLastName()){
						sb.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName());
						}else{
						sb.append(orgSpoc.getVendorUser().getFirstName());
						}
						if(null != orgSpoc.getClientUser().getFirstName() && null != orgSpoc.getClientUser().getLastName()){
						clientSb.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName());
						}else{
						clientSb.append(orgSpoc.getClientUser().getFirstName());
						}
						orgSpoc.setSpocName(sb.toString());
						orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
						orgSpoc.setTempOwnerId(orgSpoc.getVendorUser().getId());
						orgSpoc.setOwnerName(clientSb.toString());
						}
					model.addObject("orgSpocList", orgSpocList);
					}
				}
				model.addObject("requirementList", requirementList);
				model.addObject("requirementSubmissionsList", requirementSubmissionsList);
				model.addObject("requirementOffer", requirementOffer);
			}
			
		}
		
//		commonUtil.highLightMenu(Constants.List_by_status, request);
//		List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocation.getId(),Long.valueOf(Constants.TAB_CONTRACTS));
		List<User> userList = contractsTemplateService.getSigningAuthoritiesForContract(orgLocation.getId(),Long.valueOf(Constants.TAB_CONTRACTS));
		List<OrgRelations> orgRelationsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
		List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
		for (OrgRelations orgRelations : orgRelationsList) {
			String orgName = "";
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getLocationId().getOrgLocationName();
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getResponderLocationId().getOrgLocationName();
			}
			orgRelations.setOrgName(orgName);
		}
		for (OrgRelations orgRelations : clientList) {
			String orgName = "";
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getLocationId().getOrgLocationName();
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getResponderLocationId().getOrgLocationName();
			}
			orgRelations.setOrgName(orgName);
		}
		List<ContractRequestDocuments> contractRequestDocumentsList  = new ArrayList<>();
		if(null != contract){
			if(null != contract.getParentContractId()){
				Contracts parentcontracts  = contractsTemplateService.getContractById(contract.getParentContractId().getId());
				contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(parentcontracts.getId());
			}else{
				contractRequestDocumentsList = contractRequestDocumentsService.getAllDocByContractId(contract.getId());
			} 
		}
		
		Countries countries = countriesService.getCountriesByCountryName(orgLocation.getCountry());
		if(null != countries){
        model.addObject("currencyCode", countries.getCurrency());
		}
		
		SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_ADD_NEW_CONTRACT);
	     model.addObject("actionId",action.getId());
		
	    List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocation.getId());
	    model.addObject("sourceleadSectionList",sections); 
		
		List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
		model.addObject("docList",contractRequestDocumentsList);
		model.addObject("documentsList", documentsList);
		model.addObject("orgLocation", orgLocation);
		model.addObject("contract", contract);
		model.addObject("contractFromType", contractFromType);
		model.addObject("userList", userList);
		model.addObject("clientList", clientList);
		model.addObject("orgRelationsList", orgRelationsList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/addCustomContract");
		return model;
	}
	
	
	@RequestMapping(value = "/viewContract")
	public ModelAndView viewContract(HttpServletRequest request,@RequestParam(value="contractUUID", required=false)String contractUUID) throws Exception
	{
		logger.debug("display contract view page");
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Contract);
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ModelAndView model = new ModelAndView();
		List<User> userList = contractsTemplateService.getSigningAuthoritiesForContract(orgLocation.getId(),Long.valueOf(Constants.TAB_CONTRACTS));
		model.addObject("userList", userList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("contractUUID", contractUUID);
		model.setViewName("contracts/viewContract");
		return model;
	}
	@RequestMapping(value = "/viewReqContractDocPdf/{docId}/{fileName}", method = RequestMethod.GET)
	public @ResponseBody  void viewReqContractDocPdf(@PathVariable("docId") String docId, @PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContractRequestDocuments contractRequestDocuments =  contractRestController.getContractRequestDocumentsById(Long.parseLong(docId));
		
		byte[] pdfBytes = null ;
		/*if("application/pdf".equalsIgnoreCase(contractRequestDocuments.getFileType())){
			pdfBytes = contractRequestDocuments.getDocFile();
		}
		else{
			 pdfBytes = pdfConversionUtil.convertDocToPdf(contractRequestDocuments.getDocFile());
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
	
	@RequestMapping(value = "/viewContractTemplatePdf/{uuid}/{templateName}", method = RequestMethod.GET)
	public @ResponseBody  void viewContractTemplatePdf(@PathVariable("uuid") String uuid, @PathVariable("templateName") String templateName, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContractsTemplate contractsTemplate =  contractRestController.getContractTemplateByTemplateUUID(uuid);
		
		String header = "<div style='text-align: center;'>"+contractsTemplate.getTemplateName()+"<br></div><br>";
		String totalBody = header + contractsTemplate.getTemplateContent();
    	ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
  	    Document document = new Document();
  	    PdfWriter.getInstance(document, byteArray);
  	    document.open();
  	    HTMLWorker htmlWorker = new HTMLWorker(document);
  	    htmlWorker.parse(new StringReader(totalBody));
  	    document.close();
  	    byte[] pdfBytes = byteArray.toByteArray();
  	    response.setHeader("Content-disposition","inline; filename=contractPdf.pdf");
  	    response.setContentType("application/pdf");
  	    response.setContentLength(pdfBytes.length);
          ServletOutputStream sos = response.getOutputStream();
          sos.write(pdfBytes);
          sos.flush();
          sos.close();
	}

	@RequestMapping(value = {"/docusign","/ws/docusign"})
	public ModelAndView docusignRedirect(@RequestParam(value="contractUUID", required=false)String contractUUID,@RequestParam(value="type", required=false) String type) throws Exception {
		ModelAndView model = new ModelAndView();
		Contracts contract = contractsTemplateService.getContractByContractUUID(contractUUID);
		String permission = "";
		if("public".equalsIgnoreCase(type) && (Constants.SEND_FOR_VERIFICATION.equalsIgnoreCase(contract.getTemplateSubmissionStatus()) || Constants.FINALIZED.equalsIgnoreCase(contract.getTemplateSubmissionStatus()))){
			permission = "No";
			model.addObject("permission", permission);
		}
		
		model.addObject("contract", contract);
		
		if(contract.getOwnerUserId() != null && !"public".equalsIgnoreCase(type)){
			if(StringUtils.isNotBlank(contract.getOwnerUserId().getLastName())) {
				StringBuilder sb = new StringBuilder();
				StringBuilder sbn = new StringBuilder();
				model.addObject("signatory",sb.append(contract.getOwnerUserId().getFirstName()).append(" ").append(contract.getOwnerUserId().getLastName()));
				model.addObject("initial",sbn.append(contract.getOwnerUserId().getFirstName().charAt(0)).append(contract.getOwnerUserId().getLastName().charAt(0)));
			}else {
				model.addObject("signatory",contract.getOwnerUserId().getFirstName());
				model.addObject("initial",contract.getOwnerUserId().getFirstName().charAt(0));
			}
		}

		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("type", type);
		model.addObject("vmsUrl", vmsUrl);
		model.setViewName("contracts/upload");
		return model;
	}
}