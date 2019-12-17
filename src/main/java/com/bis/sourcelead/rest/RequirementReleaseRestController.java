package com.bis.sourcelead.rest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.OrgSpocDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.workflow.dao.RequirementWorkFlowDao;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
/**
 * 
 * @author Rajashekar
 * Handers all Vendor actions
 *
 */

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class RequirementReleaseRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
        
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	RequirementAssignToService requirementAssignToService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RecommendedCustomizeScoreService recommendedCustomizeScoreService;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	@Autowired
	RequirementWorkFlowDao requirementWorkFlowDao;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	

	/**
	 * @author Venkat Kesav
	 * this is method is using contacts List
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllContactsbyOrgLocationIdAndUserIdForRequirement")
	public @ResponseBody ResponseList<OrgSpocDTO> getAllContactsbyOrgLocationIdAndUserIdForRequirement(OrgSpocDTO orgSpocDTO, String vendorSelectedIds, HttpServletRequest request) throws Exception {
	    
		ResponseList<OrgSpocDTO> response = new ResponseList<OrgSpocDTO>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   
	    Requirement req = requirementService.getRequirementByRequirementId(Long.parseLong(orgSpocDTO.getRequirementId()));
	    List<OrgRelations> vandorsListByOrgLocationId = orgRelationsService.getAllVendorsFilterByOrgLocationId(orgLocations.getId(),"", vendorSelectedIds);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ResponseList<OrgSpoc> contactList = null;
		Long clientLocationId = null;
	    if(null != req.getClientId() && "Client".equals(req.getClientId().getInitiatorType())){
	    	if(req.getClientId().getResponderLocationId().getId().equals(orgLocations.getId())){
	    		clientLocationId = req.getClientId().getLocationId().getId();
	    	}
	    }else if(null != req.getClientId() && "Vendor".equals(req.getClientId().getInitiatorType())){
	    	if(req.getClientId().getLocationId().getId().equals(orgLocations.getId())){
	    		clientLocationId = req.getClientId().getResponderLocationId().getId();
	    	}
	    }
		List<Long> vendorOrgLocationsIds = new ArrayList<Long>();
		   for (OrgRelations orgRelation : vandorsListByOrgLocationId) {
			    Boolean isCyclicRelease = false;
			    if(orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
				    /*this check is for the vendor org is owner for parent requirement or not (@Venkat Kesav)*/
				    if((null != req.getParentRequirement() && null != req.getParentRequirement().getOrgLocationId() && req.getParentRequirement().getOrgLocationId().getId() == orgRelation.getLocationId().getId()) || ("Y".equalsIgnoreCase(req.getIsClientJob()) && clientLocationId.equals(orgRelation.getLocationId().getId()))){
			    		isCyclicRelease = true;
			    	}else{
				    	vendorOrgLocationsIds.add(orgRelation.getLocationId().getId());
				    }
			    }else if(orgRelation.getLocationId().getId().equals(orgLocations.getId())){
				    /*this check is for the vendor org is owner for parent requirement or not (@Venkat Kesav)*/
				    if((null != req.getParentRequirement() && null != req.getParentRequirement().getOrgLocationId() && req.getParentRequirement().getOrgLocationId().getId() == orgRelation.getResponderLocationId().getId()) || ("Y".equalsIgnoreCase(req.getIsClientJob()) && clientLocationId.equals(orgRelation.getResponderLocationId().getId()))){
			    		isCyclicRelease = true;
			    	}else{
				    	vendorOrgLocationsIds.add(orgRelation.getResponderLocationId().getId());
				    }
			    }
		   }
		    	if(vendorOrgLocationsIds.size() > 0){
		    		 contactList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationIdAndTabAccessAndDTO(orgSpocDTO,user.getId(),orgLocations.getId(), "Client","Active,New,Contracted", vendorOrgLocationsIds);
		    	}
		    	List<OrgSpocDTO> orgSpoclist = new ArrayList<>();
		      if(contactList != null && contactList.getData() != null){
		    	for(OrgSpoc contact : contactList.getData()){
		    		OrgSpocDTO orgSpocDto = new OrgSpocDTO();
			    	List<Requirement>  reqList = requirementService.getRequirementListByParentRequirementIdAndSpocId(Long.parseLong(orgSpocDTO.getRequirementId()), contact.getId());
						if(reqList.size()>0){
							orgSpocDto.setReleasedRequirement("true");
				    	for(Requirement req11 : reqList){
				    		orgSpocDto.setStringDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),req11.getCreatedDate()));
						}
						}else{
							orgSpocDto.setReleasedRequirement("false");	
						}
						orgSpocDto.setId(contact.getId());
						orgSpocDto.setSpocEmail(contact.getVendorUser().getEmail());
						orgSpocDto.setSpocName(contact.getVendorUser().getFullName());
						orgSpocDto.setSpocMobilePhone(contact.getVendorUser().getMobilePhone());
						orgSpocDto.setOwnerName(contact.getClientUser().getFullName());
						orgSpocDto.setTempOwnerId(contact.getClientUser().getId());
						orgSpocDto.setUserId(contact.getVendorUser().getId());
						orgSpocDto.setSpocLocationId(contact.getVendorLocationId().getId());
						orgSpocDto.setOrgName(contact.getVendorLocationId().getOrgLocationName()); 
				   
					if(null != contact.getClientUser() && null != contact.getOwnerUser() && contact.getOwnerUser().getId().longValue() == contact.getClientUser().getId().longValue()){
						orgSpocDto.setSpocUserId(contact.getVendorUser().getId());
						orgSpocDto.setOwnerUserId(contact.getClientUser().getId().toString());
					}
					else{
						orgSpocDto.setSpocUserId(contact.getClientUser().getId());
						orgSpocDto.setOwnerUserId(contact.getVendorUser().getId().toString());
					}
					if(contact.getOrgRelations() != null && contact.getOrgRelations().getId() != null){
						orgSpocDto.setOrgRelationId(contact.getOrgRelations().getId());
					}
			    	orgSpoclist.add(orgSpocDto);
				}
		    	response.setRecordsFiltered(contactList.getRecordsFiltered());
				response.setRecordsTotal(contactList.getRecordsTotal());
		 }
		   response.setData(orgSpoclist);
			return response;
	}
	
	/**
	 * @author Venkat Kesav
	 * this is method is using Vendor List
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	 
	@RequestMapping(value = "/getVendorListsForReleaseRequirement")
	public @ResponseBody ResponseList<OrgRelationDTO>  getVendorListsForReleaseRequirement(OrgRelationDTO orgRelationDTO, HttpServletRequest request) throws Exception
	{
		ResponseList<OrgRelationDTO> response = new ResponseList<OrgRelationDTO>();
		String[] tableColumns = {"name", "location", "releasedOn", "noOfContacts","level","release"};
	    int sortColumnIndex = 1; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default descending order
		if(orgRelationDTO.getOrder() != null && !orgRelationDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(orgRelationDTO.getOrder().get(0).get("column"));
			sortColumnOrder = orgRelationDTO.getOrder().get(0).get("dir");
		}
		orgRelationDTO.setSortColumn(tableColumns[sortColumnIndex]);
		orgRelationDTO.setSortOrder(sortColumnOrder);
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Requirement req = requirementService.getRequirementByRequirementId(Long.parseLong(orgRelationDTO.getRequirementId()));
		ResponseList<OrgRelations> responseList = orgRelationsService.getAllVendorsFilterByOrgLocationIdAndOrgRelationDTO(orgRelationDTO,orgLocations.getId(), orgRelationDTO.getVendorStatus(), "");
		List<OrgRelations>  vandorsListByOrgLocationId = responseList.getData();
		CopyOnWriteArrayList<OrgRelations> vandorsListByOrgLocationIdDuplicate = new CopyOnWriteArrayList<>(vandorsListByOrgLocationId);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long clientLocationId = null;
		if (null != req.getClientId() && req.getClientId().getInitiatorType().equals("Client")) {
			if (req.getClientId().getResponderLocationId().getId().equals(orgLocations.getId())) {
				clientLocationId = req.getClientId().getLocationId().getId();
			}
		}
		else if (null != req.getClientId()
				&& req.getClientId().getInitiatorType().equals("Vendor")) {
			if (req.getClientId().getLocationId().getId().equals(orgLocations.getId())) {
				clientLocationId = req.getClientId().getResponderLocationId().getId();
			}
		}
	    List<OrgRelationDTO> orgRelationslist = new ArrayList<>();
		for (OrgRelations orgRelation : vandorsListByOrgLocationIdDuplicate) {
			OrgRelationDTO orgRelationsDTO = new OrgRelationDTO();
			/*
			 * isCyclicRelease variable is for to check the vendor org is owner
			 * for parent requirement or not (@Venkat Kesav)
			 */
			Boolean isCyclicRelease = false;
			List<Long> vendorOrgLocationsIds = new ArrayList<Long>();
			if (orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())) {
				vendorOrgLocationsIds.add(orgRelation.getLocationId().getId());
				/*
				 * this check is for the vendor org is owner for parent
				 * requirement or not (@Venkat Kesav)
				 */
				if ((null != req.getParentRequirement()
						&& null != req.getParentRequirement().getOrgLocationId()
						&& req.getParentRequirement().getOrgLocationId().getId() == orgRelation.getLocationId().getId())
						|| ("Y".equalsIgnoreCase(req.getIsClientJob())
								&& clientLocationId.equals(orgRelation.getLocationId().getId()))) {
					isCyclicRelease = true;
				}
			}
			else if (orgRelation.getLocationId().getId().equals(orgLocations.getId())) {
				vendorOrgLocationsIds.add(orgRelation.getResponderLocationId().getId());
				/*
				 * this check is for the vendor org is owner for parent
				 * requirement or not (@Venkat Kesav)
				 */
				if ((null != req.getParentRequirement()
						&& null != req.getParentRequirement().getOrgLocationId()
						&& req.getParentRequirement().getOrgLocationId().getId() == orgRelation.getResponderLocationId().getId())
						|| ("Y".equalsIgnoreCase(req.getIsClientJob())
								&& clientLocationId.equals(orgRelation.getResponderLocationId().getId()))) {
					isCyclicRelease = true;
				}
			}
			if (isCyclicRelease) {
				vandorsListByOrgLocationIdDuplicate.remove(orgRelation);
			}
			else {
				if (orgRelation.getLocationId().getId().equals(orgLocations.getId())) {
					orgRelationsDTO.setOrgRelationId(orgRelation.getId());
					orgRelationsDTO.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
					orgRelationsDTO.setOrgName(orgRelation.getResponderLocationId().getOrgLocationName());
					orgRelationsDTO.setCountry(orgRelation.getResponderLocationId().getCountry());
					orgRelationsDTO.setState(orgRelation.getResponderLocationId().getState());
					orgRelationsDTO.setCity(orgRelation.getResponderLocationId().getCity());
					orgRelationsDTO.setVendorlocationId(orgRelation.getResponderLocationId().getId());
					if (orgRelation.getInitiatorLevel() != null) {
						orgRelationsDTO.setLevel(orgRelation.getInitiatorLevel());
					}
					else {
						orgRelationsDTO.setLevel("");
					}
					List<OrgSpoc> contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(user.getId(), orgLocations.getId(), "Client", "Active,New,Contracted", orgRelation.getId().toString(), "", vendorOrgLocationsIds);
					int nonAssignedCount = 0;
					orgRelationsDTO.setNoOfContatcs(String.valueOf(0));
					if (!contactsList.isEmpty()) {
						orgRelationsDTO.setNoOfContatcs(String.valueOf(contactsList.size()));
						for (OrgSpoc orgSpoc : contactsList) {
							List<Requirement> reqList = requirementService.getRequirementListByParentRequirementIdAndSpocId(Long.parseLong(orgRelationDTO.getRequirementId()), orgSpoc.getId());
							if (reqList.size() > 0) {
								orgSpoc.setReleasedRequirement("true");
								Requirement requi = reqList.get(0);
								orgRelationsDTO.setStringDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), requi.getCreatedDate()));
							}
							else {
								orgSpoc.setReleasedRequirement("false");
								nonAssignedCount = nonAssignedCount + 1;
							}
						}
						orgRelationsDTO.setNoOfNonReleasedContacts(String.valueOf(nonAssignedCount));
						orgRelationslist.add(orgRelationsDTO);
					}
					else {
//						vandorsListByOrgLocationIdDuplicate.remove(orgRelation);
						orgRelationslist.remove(orgRelationsDTO);
					}
				}
				if (orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())) {
					orgRelationsDTO.setOrgRelationId(orgRelation.getId());
					orgRelationsDTO.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
					orgRelationsDTO.setOrgName(orgRelation.getLocationId().getOrgLocationName());
					orgRelationsDTO.setCountry(orgRelation.getLocationId().getCountry());
					orgRelationsDTO.setState(orgRelation.getLocationId().getState());
					orgRelationsDTO.setCity(orgRelation.getLocationId().getCity());
					orgRelationsDTO.setVendorlocationId(orgRelation.getLocationId().getId());
					if (orgRelation.getResponderLevel() != null) {
						orgRelationsDTO.setLevel(orgRelation.getResponderLevel());
					}
					else {
						orgRelationsDTO.setLevel("");
					}
					List<OrgSpoc> contactsList = orgRelationsService.getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(user.getId(), orgLocations.getId(), "Client", "Active,New,Contracted", orgRelation.getId().toString(), "", vendorOrgLocationsIds);
					int nonAssignedCount = 0;
					orgRelationsDTO.setNoOfContatcs(String.valueOf(0));
					if (!contactsList.isEmpty()) {
						orgRelationsDTO.setNoOfContatcs(String.valueOf(contactsList.size()));
						for (OrgSpoc orgSpoc : contactsList) {
							List<Requirement> reqList = requirementService.getRequirementListByParentRequirementIdAndSpocId(Long.parseLong(orgRelationDTO.getRequirementId()), orgSpoc.getId());
							if (reqList.size() > 0) {
								orgSpoc.setReleasedRequirement("true");
								Requirement requi = reqList.get(0);
								orgRelationsDTO.setStringDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), requi.getCreatedDate()));
							}
							else {
								orgSpoc.setReleasedRequirement("false");
								nonAssignedCount = nonAssignedCount + 1;
							}
						}
						orgRelationsDTO.setNoOfNonReleasedContacts(String.valueOf(nonAssignedCount));
						orgRelationslist.add(orgRelationsDTO);
					}
					else {
						orgRelationslist.remove(orgRelationsDTO);
					}
				}
			}
		}
		response.setRecordsFiltered(responseList.getRecordsFiltered());
		response.setRecordsTotal(responseList.getRecordsTotal());
		response.setData(orgRelationslist);
		return response;
	}
	
	
	/**
	 * Handles Vendor Contacts 
	 * @param json
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_RELEASE_REQUIREMENT')")
	@RequestMapping(value = "/releaseJobsToClientsOrVendors", method = RequestMethod.POST)
	@ResponseBody
	public Response<String> releaseJobsToClientsOrVendors(@RequestParam(value = "requirementId", required = false) Long requirementId,
												@RequestParam(value = "contactsIds", required = false) String contactsIds,
			                                    @RequestParam(value="vendorIds", required=false) String vendorIds,
			                                    HttpServletRequest request) throws Exception {
			Response<String> response =  new Response<>();
			
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 	if(loginUser != null) {
				try {
				 	String releasetype = null;
					Requirement parentRequirement = requirementService.getRequirementByRequirementId(requirementId);
					String[] contacts = contactsIds.split(",");
					if(contacts != null &&  contacts.length>0 && contacts[0] != null && contacts[0] != "" ){
						for(String contact   : contacts){
								releasetype = Constants.CONTACTS;
								OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(Long.parseLong(contact));
								Requirement requirement  = new Requirement();
								releaseJobToVendorOrContacts(requirement, parentRequirement, orgSpoc,null,loginOrgLocation);
								// for URL redirections from mail
								String redirectURL = "/requirementSummary-params-requirementUUID="+requirement.getRequirementUUID();
								Long redirectOrgLocationId = requirement.getOrgLocationId().getId();
								requirementService.releaseMailScenario(loginUser, requirement, orgSpoc, loginOrgLocation,releasetype,redirectURL,redirectOrgLocationId);
		
						}
					}	
					
					String[] vendors = vendorIds.split(",");
					if(vendors != null &&  vendors.length>0 && vendors[0] != null && vendors[0] != ""){
						for(String vendor   : vendors){
							
							
							OrgRelations orgRelations = orgRelationsService.getOrRelationsById(Long.parseLong(vendor));
							List<Long> vendorOrgLocationsIds = new ArrayList<Long>();
								   if(orgRelations.getResponderLocationId().getId().equals(loginOrgLocation.getId())){
									   vendorOrgLocationsIds.add(orgRelations.getLocationId().getId());
								   }else if(orgRelations.getLocationId().getId().equals(loginOrgLocation.getId())){
									   vendorOrgLocationsIds.add(orgRelations.getResponderLocationId().getId());
								   }
							
							 List<OrgSpoc> vandorsContactListByOrgLocationId = orgRelationsService.getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(loginUser.getId(),loginOrgLocation.getId(), "Client","Active",vendor,"", vendorOrgLocationsIds);
							
							for(OrgSpoc orgSpoc : vandorsContactListByOrgLocationId){
							    Boolean isJobReleased = requirementService.getRequirementByParentRequirementIdAndSpocId(requirementId, orgSpoc.getId());
							    if(isJobReleased.toString().equalsIgnoreCase("false")){
							    	Requirement requirement  = new Requirement();
							    	releasetype = Constants.VENDORSTYPE;
							    	
									releaseJobToVendorOrContacts(requirement, parentRequirement, orgSpoc, orgRelations.getInitiatorType(),loginOrgLocation);
									// for URL redirections from mail
									String redirectURL = "/requirementSummary-params-requirementUUID="+requirement.getRequirementUUID();
									Long redirectOrgLocationId = requirement.getOrgLocationId().getId();
									requirementService.releaseMailScenario(loginUser, requirement, orgSpoc, loginOrgLocation,releasetype,redirectURL,redirectOrgLocationId);
		
							    }
							}
						}
					}
					
					if(null == parentRequirement.getReleasedBy()){
						parentRequirement.setReleasedBy(loginUser.getId().toString());
					}else{
						List<Long> userId = new ArrayList<Long>();
						for (String s : parentRequirement.getReleasedBy().split(",")){
							userId.add(Long.parseLong(s));
						 }
						boolean flag = userId.contains(loginUser.getId());
		         		if(flag == false){
		         			StringBuilder releasedUser = new StringBuilder();
		    				releasedUser = releasedUser.append(",").append(loginUser.getId().toString());
		    				parentRequirement.setReleasedBy(parentRequirement.getReleasedBy()+releasedUser.toString());
		         		}
					}
					parentRequirement.setUpdatedDate(new Date());
					requirementService.addRequirement(parentRequirement);
					
					StringBuilder reqReleaseMsg = new StringBuilder();
					SourceLeadSessionManager.setSessionAttribute(request, "reqReleaseMessage", reqReleaseMsg.append("Success!! ").append(" job  released").toString());
					
					response.setObject("vendorsList");
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
		 	}else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		return response;
	}

	private void releaseJobToVendorOrContacts(Requirement requirement, Requirement parentRequirement, OrgSpoc orgSpoc, String initiator,OrgLocations loginOrgLocation) throws Exception {
		
		//requirement = parentRequirement;
		
		requirement.setParentRequirement(parentRequirement);
		/*if(StringUtils.equalsIgnoreCase(initiator, "Vendor") ){
			requirement.setOrgLocationId(orgSpoc.getInitiatorLocationId());
		}else{
			requirement.setOrgLocationId(orgSpoc.getResponderLocationId());
		}*/
		
		/*if(orgSpoc != null && orgSpoc.getResponderLocationId().getId().longValue() == loginOrgLocation.getId().longValue()){
			requirement.setOrgLocationId(orgSpoc.getInitiatorLocationId());
			requirement.setManagerUserId(orgSpoc.getOwnerUser());
			requirement.setUpdatedBy(orgSpoc.getOwnerUser().getUserCode());
			requirement.setCreatedBy(orgSpoc.getOwnerUser().getUserCode());
		}
		if(orgSpoc != null && orgSpoc.getInitiatorLocationId().getId().longValue() == loginOrgLocation.getId().longValue() ){
			requirement.setOrgLocationId(orgSpoc.getResponderLocationId());
			requirement.setManagerUserId(orgSpoc.getSpocUser());
			requirement.setUpdatedBy(orgSpoc.getSpocUser().getUserCode());
			requirement.setCreatedBy(orgSpoc.getSpocUser().getUserCode());
		}*/
		
		requirement.setOrgLocationId(orgSpoc.getVendorLocationId());
		requirement.setManagerUserId(orgSpoc.getVendorUser());
		requirement.setUpdatedBy(orgSpoc.getVendorUser().getUserCode());
		requirement.setCreatedBy(orgSpoc.getVendorUser());
		
		requirement.setJobTitle(parentRequirement.getJobTitle());
		requirement.setJobType(parentRequirement.getJobType());
		if(null != parentRequirement.getJobType() &&  parentRequirement.getJobType() != "" &&  parentRequirement.getJobType() != "undefined" && parentRequirement.getJobType().equalsIgnoreCase("Other")){
			requirement.setOtherJobType(parentRequirement.getOtherJobType());
		}
		
		requirement.setReqCoolingOffPeriod(parentRequirement.getReqCoolingOffPeriod());
		
//		requirement.setJobId(parentRequirement.getJobId());
		requirement.setProfilePercentage(parentRequirement.getProfilePercentage());
		requirement.setPositions(parentRequirement.getPositions());
		requirement.setEducation(parentRequirement.getEducation());
		requirement.setMinExp(parentRequirement.getMinExp());
		requirement.setMaxExp(parentRequirement.getMaxExp());
		requirement.setCurrencyCode(parentRequirement.getCurrencyCode());
		requirement.setRate(parentRequirement.getRate());
		requirement.setPriority(parentRequirement.getPriority());
		requirement.setIndustryName(parentRequirement.getIndustryName());
		requirement.setMaxRate(parentRequirement.getMaxRate());
		requirement.setTravelAllowance(parentRequirement.getTravelAllowance());
		requirement.setProjectDurationMonths(parentRequirement.getProjectDurationMonths());
		requirement.setProjectDurationYears(parentRequirement.getProjectDurationYears());
		requirement.setRateType(parentRequirement.getRateType());
		requirement.setCountry(parentRequirement.getCountry());
		requirement.setState(parentRequirement.getState());
		requirement.setCity(parentRequirement.getCity());
		requirement.setLocation(parentRequirement.getLocation());
		requirement.setSkills(parentRequirement.getSkills());
		requirement.setDescription(parentRequirement.getDescription());
		requirement.setNoticePeriod(parentRequirement.getNoticePeriod());
		requirement.setFileContentType(parentRequirement.getFileContentType());
		requirement.setUrl(parentRequirement.getUrl());
		// Below Code is used to set default WORKFLOW id for the vendor job,  if job is mapped with custom WORKFLOW id of current login ORGLOCATION.
    	RequirementWorkFlow defaultWorkflow = requirementWorkFlowDao.getRequirementDefaultWorkflow();
    	requirement.setWorkflowId(defaultWorkflow);
    	// End of setting default WORKFLOW for the vendor job
		if(parentRequirement.getMedicalClearence() != null){
		requirement.setMedicalClearence(parentRequirement.getMedicalClearence());
		}
		if(parentRequirement.getSecurityClearance() != null){
		requirement.setSecurityClearance(parentRequirement.getSecurityClearance());
		}
		if(parentRequirement.getOverTime() !=null){
		requirement.setOverTime(parentRequirement.getOverTime());
		}
		if(parentRequirement.getBackgroundCheck() != null){
		requirement.setBackgroundCheck(parentRequirement.getBackgroundCheck());
		}
		if(parentRequirement.getGender() != null){
		requirement.setGender(parentRequirement.getGender());
		}
		if(parentRequirement.getExperienceLevel() != null){
		requirement.setExperienceLevel(parentRequirement.getExperienceLevel());
		}
		requirement.setFileName(parentRequirement.getFileName());
		requirement.setFileURL(parentRequirement.getFileURL());
		if(parentRequirement.getVisaType() != null){
			requirement.setVisaType(parentRequirement.getVisaType());	
		}
		requirement.setIsClientJob("Y");
		requirement.setIsTemplate("N");
		requirement.setSubmissionTemplateJsonString(parentRequirement.getSubmissionTemplateJsonString());
		requirement.setClientSubmissionTemplateJsonString(parentRequirement.getSubmissionTemplateJsonString());
		//requirement.setClientCurrencyCode(parentRequirement.getClientCurrencyCode());
		//requirement.setClientRate(parentRequirement.getClientRate());
		//requirement.setClientRateType(parentRequirement.getClientRateType());
		requirement.setClientCurrencyCode(parentRequirement.getCurrencyCode());
		requirement.setClientRate(parentRequirement.getClientRate());
		requirement.setClientMaxRate(parentRequirement.getClientMaxRate());
		requirement.setClientRateType(parentRequirement.getRateType());
		requirement.setTotalApplicants(0);
		requirement.setRequirementUUID(SourceLeadRandomCodeHelper.generateRandomUserCode());
		requirement.setGeneratedId(requirementService.getRequirementId(orgSpoc.getOrgRelations().getResponderLocationId()));
		if(parentRequirement.getClientSubmissionTemplateId()!=null){
			requirement.setClientSubmissionTemplateId(parentRequirement.getClientSubmissionTemplateId());
		}
		if(parentRequirement.getSubmissionTemplateId()!=null){
			requirement.setSubmissionTemplateId(parentRequirement.getSubmissionTemplateId());
		}
		
		
		requirement.setStatus(Constants.TEAM_ACTIVE);
		requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.NEW);
		
		if(orgSpoc != null){
			requirement.setSpocId(orgSpoc);
			requirement.setClientId(orgSpoc.getOrgRelations());
		}
		
		requirement.setCreatedDate(commonUtil.currentDate());
		requirement.setUpdatedDate(commonUtil.currentDate());
		
		requirement.setId(null);
        Requirement req= requirementService.addRequirement(requirement);
        
        
        
        List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(parentRequirement.getId());
        if(locationsList != null && !locationsList.isEmpty()) {
        	for(RequirementLocations locations : locationsList) {
        		RequirementLocations requirementLocations = new RequirementLocations();
				requirementLocations.setRequirementId(req);
				requirementLocations.setLocation(locations.getLocation());
				requirementLocations.setCountry(locations.getCountry());
				requirementLocations.setState(locations.getState());
				requirementLocations.setCity(locations.getCity());
				if(StringUtils.isNotBlank(locations.getZipCode())) {
					requirementLocations.setZipCode(locations.getZipCode());
				}
				
				requirementLocations.setLongitude(locations.getLongitude());
				requirementLocations.setLatitude(locations.getLatitude());
				requirementLocations.setStatus(Constants.RECORD_ACTIVE);
				requirementLocations.setLocationUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				requirementLocationsService.saveRequirementLocations(requirementLocations);
        		
        	}
        }
		
		//store recommended customizeScore
	   RecommendedCustomizeScore  recommendedCustomizeScore = recommendedCustomizeScoreService.saveCustomizeScore(req);

	}
	
	
	 @RequestMapping(value = "/releaseToCrowdSourcingNetwork" , method = RequestMethod.POST)
	 public  String releaseToCrowdSourcingNetwork(@RequestParam(value="json", required=false) String json,
			 @RequestParam(value = "requirementId", required = false) Long requirementId,
			 HttpServletRequest request,
                         @RequestParam(value = "jobBoardAccounts", required = false) ArrayList<Long> jobBoardAccounts) throws Exception {
		 	
		 json = "{"+json+"}";
		 
		 JSONObject jsonObj = new JSONObject(json);
		 	
	 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	 
	 	Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
//	 	System.out.println(jobBoardAccounts);
                requirement.setJobBoardAccountIdList(jobBoardAccounts);
                
	 	if(jsonObj.has("isPublic") && !isNullOrEmpty(jsonObj.getString("isPublic")) && jsonObj.getString("isPublic").equalsIgnoreCase("Y")){
	 		requirement.setIsPublic(jsonObj.getString("isPublic"));
	 		requirement.setPostedOrgName(orgLocations.getOrgLocationName());
	 	}else{
	 		requirement.setIsPublic(jsonObj.getString("isPublic"));
	 	}
	 	
	 	if(jsonObj.has("isCrowdScourcing") && !isNullOrEmpty("isCrowdScourcing") && jsonObj.getString("isCrowdScourcing").equalsIgnoreCase("Y")){
	 		requirement.setIsCrowdSourcing(jsonObj.getString("isCrowdScourcing"));
	 		requirement.setPostedOrgName(orgLocations.getOrgLocationName());
	 		
	 		if(jsonObj.has("rewardType") && !isNullOrEmpty("rewardType") && jsonObj.getString("rewardType").equalsIgnoreCase("Percentage")){
	 			if(jsonObj.has("percentageReward")){
	 				requirement.setRewardPercentage(jsonObj.getString("percentageReward"));
	 			}
	 			requirement.setRewardType(jsonObj.getString("rewardType"));
	 		}
	 		
	 		if(jsonObj.has("rewardType") && !isNullOrEmpty("rewardType") && jsonObj.getString("rewardType").equalsIgnoreCase("Flat")){
	 			
	 			if(jsonObj.has("flatReward")){
	 				requirement.setRewardFlat(jsonObj.getString("flatReward"));
	 			}
	 			
	 			if(jsonObj.has("flatRateType")){
	 				requirement.setRewardFlatRateType(jsonObj.getString("flatRateType"));
	 			}
	 			requirement.setRewardType(jsonObj.getString("rewardType"));
	 		}
	 		
	 		if(jsonObj.has("guaranteePeriod") && !isNullOrEmpty(jsonObj.getString("guaranteePeriod"))){
	 			requirement.setRewardGuaranteePeriod(jsonObj.getString("guaranteePeriod"));
	 		}
	 	}else{
	 		requirement.setIsCrowdSourcing(jsonObj.getString("isCrowdScourcing"));
	 	}
	 	 
		if(null == requirement.getReleasedBy()){
			requirement.setReleasedBy(user.getId().toString());
		}else{
			List<Long> userId = new ArrayList<Long>();
			for (String s : requirement.getReleasedBy().split(",")){
				userId.add(Long.parseLong(s));
			 }
			boolean flag = userId.contains(user.getId());
  		if(flag == false){
  			StringBuilder releasedUser = new StringBuilder();
				releasedUser = releasedUser.append(",").append(user.getId().toString());
				requirement.setReleasedBy(requirement.getReleasedBy()+releasedUser.toString());
  		}
		}
		requirement.setUpdatedDate(new Date());
		requirementService.addRequirement(requirement);
	 	
		 return "success";
	   }
}