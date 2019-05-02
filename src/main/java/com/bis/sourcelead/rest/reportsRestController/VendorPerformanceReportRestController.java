package com.bis.sourcelead.rest.reportsRestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.beans.RequirementStagesReport;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.OrgSpocDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;
import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class VendorPerformanceReportRestController {

	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	OrgSpocService orgSpocService;

	@Autowired
	ClientService clientService;

	@Autowired
	RequirementService requirementService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private RequirementLocationsService requirementLocationsService;
	
	@Autowired
	private RequirementDao requirementDao;
	
	@Autowired
	private CommonUtil commonUtil;

	@RequestMapping(value = "/getOrgRelationList", method = RequestMethod.POST)
	public ResponseList<OrgRelationDTO> getVendorList(HttpServletRequest request) {
		ResponseList<OrgRelationDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				responseList.setData(orgRelationsService.getAllVendorsDTOListByOrgLocationId(orgLocations.getId()));
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}

	@RequestMapping(value = "/getContactListByOrgRelationId")
	public ResponseList<OrgSpocDTO> getVendorContactsByOrgRelationId(HttpServletRequest request, long orgRelationId) {
		ResponseList<OrgSpocDTO> responseList = new ResponseList<>();
		List<OrgSpocDTO> orgSpocDTOList = new LinkedList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(orgRelationId);
				if (orgSpocList != null && !orgSpocList.isEmpty()) {
					for (OrgSpoc orgSpoc : orgSpocList) {
						OrgSpocDTO orgSpocDTO = new OrgSpocDTO();
						orgSpocDTO.setId(orgSpoc.getId());
						orgSpocDTO.setUserId(orgSpoc.getClientUser().getId());
						if (null != orgSpoc.getClientUser().getFirstName() && null != orgSpoc.getClientUser().getLastName() && !"".equalsIgnoreCase(orgSpoc.getClientUser().getLastName())) {
							orgSpocDTO.setSpocName(orgSpoc.getClientUser().getFirstName() + " " + orgSpoc.getClientUser().getLastName());
						} else {
							orgSpocDTO.setSpocName(orgSpoc.getClientUser().getFirstName());
						}

						if (null != orgSpoc.getVendorUser().getFirstName() && null != orgSpoc.getVendorUser().getLastName()) {
							orgSpocDTO.setOwnerName(orgSpoc.getVendorUser().getFirstName() + " " + orgSpoc.getVendorUser().getLastName());
						} else {
							orgSpocDTO.setOwnerName(orgSpoc.getVendorUser().getFirstName());
						}
						orgSpocDTO.setSpocUserId(orgSpoc.getVendorUser().getId());

						orgSpocDTOList.add(orgSpocDTO);
					}
				}
				responseList.setData(orgSpocDTOList);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}

	@RequestMapping(value = "/getVendorJobsForReleasedJobReport")
	public Response<RequirementStagesReport> getAllVendorJobs(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long orgRelationId, @RequestParam(defaultValue = "0") long spocId) {
		Response<RequirementStagesReport> response = new Response<>();
		List<Requirement> requirementList = new LinkedList<>();
		long openJobsCount = 0, closedJobsCount = 0, cancelledJobsCount = 0, onHoldJobsCount = 0;
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			if (user != null) {
				if (orgRelationId > 0) {
					if (spocId > 0) {
						OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(spocId);
						if (orgSpoc != null) {
							userRole = orgSpoc.getClientUser().getUserRole().iterator().next().getRoles().getRoleName();
						}
					}
					requirementList = clientService.getReleasedJobsByOrgRelationId(orgRelationId, spocId, userRole);
				} else {
					OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					List<OrgRelationDTO> vendorList = orgRelationsService.getAllVendorsDTOListByOrgLocationId(orgLocations.getId());
					if (vendorList != null && !vendorList.isEmpty()) {
						for (OrgRelationDTO orgRelationDTO : vendorList) {
							List<Requirement> reqList = clientService
									.getReleasedJobsByOrgRelationId(orgRelationDTO.getId(), spocId, userRole);
							if (reqList != null && !reqList.isEmpty()) {
								requirementList.addAll(reqList);
							}
						}
					}
				}
				List<Long> requirementIdsList = new LinkedList<>();

				if (requirementList != null && !requirementList.isEmpty()) {
					Long tempId = 0L;
					for (Requirement requirement : requirementList) {
						requirementIdsList.add(requirement.getId());

						if (tempId != requirement.getParentRequirement().getId()) {
							Requirement parentRequirement = requirementService.getRequirementDetailsById(requirement.getParentRequirement().getId());
							if (parentRequirement != null) {
								switch (parentRequirement.getRequirementSubmissionStatus().toString()) {
								case "OPEN":
									openJobsCount++;
									break;
								case "CLOSED":
									closedJobsCount++;
									break;
								case "FILLEDCLOSED":
									closedJobsCount++;
									break;
								case "UNFILLEDCLOSED":
									closedJobsCount++;
									break;
								case "CANCEL":
									cancelledJobsCount++;
									break;
								case "ONHOLD":
									onHoldJobsCount++;
									break;
								default:
									break;
								}
							}
							tempId = requirement.getParentRequirement().getId();
						}
					}
				}
				SourceLeadSessionManager.setSessionAttribute(request, "requirementIdsList", requirementIdsList);
				RequirementStagesReport requirementStagesReport = new RequirementStagesReport();
				requirementStagesReport.setOpenRequirementsCount(new BigDecimal(openJobsCount));
				requirementStagesReport.setClosedRequirementsCount(new BigDecimal(closedJobsCount));
				requirementStagesReport.setCancelRequirementsCount(new BigDecimal(cancelledJobsCount));
				requirementStagesReport.setOnholdRequirementsCount(new BigDecimal(onHoldJobsCount));

				response.setObject(requirementStagesReport);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/getVendorJobsForReleasedJobReportForList", method = RequestMethod.GET)
	public ResponseList<Requirement> getVendorJobsForReleasedJobReportForList(HttpServletRequest request,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(defaultValue = "0") long orgRelationId, 
			@RequestParam(defaultValue = "0") long spocId) {
		ResponseList<Requirement> response = new ResponseList<>();
		List<Requirement> requirementList = new LinkedList<>();
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				List<Requirement> reqList = clientService.getReleasedJobsByOrgRelationId(orgRelationId, spocId, userRole);
				if (reqList != null && !reqList.isEmpty()) {
					Long tempId = 0L;
					
					for (Requirement requirement : reqList) {
						List<RequirementLocations> locationsList;
						String location = "";
						StringBuilder requirementBuilder = new StringBuilder();
						if (tempId != requirement.getParentRequirement().getId()) {
							try {
								Requirement parentRequirement = requirementService.getRequirementDetailsById(requirement.getParentRequirement().getId());
								requirement.setRequirementSubmissionStatus(parentRequirement.getRequirementSubmissionStatus());
								User user = userService.getUserById(parentRequirement.getManagerUserId().getId());
								//For Applicants Count
								long allCount = requirementService.getVendorSubmittedCandidates(parentRequirement.getId(), orgRelationId, spocId, "ALL");
							     requirement.setTotalApplicants((int)allCount);
								long joinedCount = requirementService.getVendorSubmittedCandidates(parentRequirement.getId(), orgRelationId, spocId, "JOINED");
							     requirement.setJoinedCount(String.valueOf(joinedCount));
							     // for requirement location
							     List<String> requirementLocationsList = new ArrayList<>();
									StringBuilder sb = new StringBuilder();
						    		List<RequirementLocations> locationList = requirementLocationsService.getRequirementLocationsListByRequirementId(parentRequirement.getId());
						    		locationList.forEach(loclist->{
						    			requirementLocationsList.add(loclist.getLocation());
						    			if(sb.length() > 0) 
						    				sb.append(",");
						    			sb.append(loclist.getLocation()).toString();
						    		});
						    		requirement.setLocation(sb.toString());
						    		requirement.setLocationsList(requirementLocationsList);
								requirement.setJobOwner(user.getFullName());
								requirement.setReleasedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), requirement.getCreatedDate()));
							} catch (Exception e) {
								e.printStackTrace();
							}

							requirementList.add(requirement);
							tempId = requirement.getParentRequirement().getId();
						}
					}
				}
				response.setData(requirementList);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
		return response;
	}

	@RequestMapping(value = "/getQualityCandidatesListForVendor")
	@SuppressWarnings("unchecked")
	public Response<String> getQualityCandidatesForVendors(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long orgRelationId, @RequestParam(defaultValue = "0") long spocId) {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				/*
				 * String userRole = (String)
				 * SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				 * if(orgRelationId > 0) { if(spocId > 0) { OrgSpoc orgSpoc =
				 * orgSpocService.getOrgSpocById(spocId); if(orgSpoc != null) { userRole =
				 * orgSpoc.getClientUser().getUserRole().iterator().next().getRoles().
				 * getRoleName(); } } }
				 */
				List<Long> requirementIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request,
						"requirementIdsList");
				if (requirementIdList != null && !requirementIdList.isEmpty()) {
					long joinedCount = requirementService.getQualityCandidatesForVendors(requirementIdList, orgRelationId, spocId, "JOINED");
					long rejectedCount = requirementService.getQualityCandidatesForVendors(requirementIdList, orgRelationId, spocId, "REJECTED");
					response.setObject("Joined:" + joinedCount + "<>Rejected:" + rejectedCount);
				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@RequestMapping(value = "/getApplicantDeclineCountsForVendor")
	@SuppressWarnings("unchecked")
	public Response<ApplicantDeclineDTO> getApplicantDeclineCountsForVendor(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long orgRelationId, @RequestParam(defaultValue = "0") long spocId) {
		Response<ApplicantDeclineDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				List<Long> requirementIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
				if (requirementIdList != null && !requirementIdList.isEmpty()) {
					response.setObject(requirementService.getApplicantDeclineCountsForVendor(requirementIdList, orgRelationId, spocId));
				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/getApplicantDeclineListForVendor")
	@SuppressWarnings("unchecked")
	public ResponseList<RequirementSubmission>getApplicantDeclineListForVendor(@RequestParam(defaultValue = "0") long orgRelationId, 
			@RequestParam(defaultValue = "0") long spocId,
			@RequestParam(value = "status", required = false) String status,
			HttpServletRequest request){
				
		ResponseList<RequirementSubmission> responseList = new ResponseList<>();
		List<RequirementSubmission> list = new ArrayList<RequirementSubmission>();
		String dateFormat = (String) SourceLeadSessionManager.getSessionAttribute(request, "dateFormat");
		List<Long> requirementIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
	 	List<RequirementSubmission> requirementSubmissionList = requirementDao.getApplicantDeclineCountsForVendor(requirementIdList, orgRelationId, spocId, status);
	 	requirementSubmissionList.forEach(submission->{
	 		submission.setSubmittedOn(commonUtil.dateStrFromOrgDateFormatter(dateFormat, submission.getCreatedDate()));
	 		if(submission.getJoinedDate() !=null){
	 		submission.setJoinedDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat, submission.getJoinedDate()));	
	 		}
	 		list.add(submission);
	 	});
	 	
	 	responseList.setData(list);
	 	responseList.setResponse(Result.SUCCESS.name());
	 	responseList.setResponseMessage(Result.SUCCESS.getValue());
		
		return responseList;
		
	}

	@RequestMapping(value = "/getApplicantBackoutCountsForVendor")
	@SuppressWarnings("unchecked")
	public Response<ApplicantsBackoutDTO> getApplicantBackoutCountsForVendor(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long orgRelationId, @RequestParam(defaultValue = "0") long spocId) {
		Response<ApplicantsBackoutDTO> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				List<Long> requirementIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
				if (requirementIdList != null && !requirementIdList.isEmpty()) {
					response.setObject(requirementService.getApplicantBackoutCountsForVendor(requirementIdList, orgRelationId, spocId));
				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
}
