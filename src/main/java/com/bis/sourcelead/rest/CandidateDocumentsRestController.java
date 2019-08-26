package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateDocumentsDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequestedDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequestedDocumentsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles requests for the CandidateDocuments Service
 * 
 * @author Prasad K
 * @date 06th April 2017
 *
 */
@RestController
public class CandidateDocumentsRestController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateDocumentsRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	private CandidateDocumentsService candidateDocumentsService;
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	LookUpDataService lookUpDataService;
	@Autowired
	private CandidateService candidateService;
	@Autowired
	RequestedDocumentsService requestedDocumentsService;
	@Autowired
	UserService userService;
	@Autowired
	OrgLocationService orgLocationService;
	@Autowired
	CandidateActivityCommonUtil candidateActivityCommonUtil;

	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	CandidateIndexHelper candidateIndexHelper;

	/**
	 * @author Prasad K
	 * 
	 *         Handles Document saving for Candidate
	 * 
	 * @param json,
	 *            candidateId
	 * @param request
	 * 
	 * @return ResponseBody
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/saveCandidateDocument" }, method = RequestMethod.POST)
	public @ResponseBody Response<String> saveCandidateDocument(CandidateDocumentsDTO candidateDocumentsDTO, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				candidateDocumentsDTO.setStaticPath(staticPath);
				// FileUploadUtil.fillUploadedFileInfo(candidateDocumentsDTO,
				// candidateDocumentsDTO.getFileInfo());

				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (null != candidateDocumentsDTO.getCandidateId()) {
					candidateDocumentsService.storeCandidateDocuments(candidateDocumentsDTO,
							Long.parseLong(candidateDocumentsDTO.getCandidateId()), candidateDocumentsDTO.getDocumentUUID(),
							orgLocation.getId(), user);
					Candidate candidate = candidateService
							.getCandidateByCandidateId(Long.parseLong(candidateDocumentsDTO.getCandidateId()));
					Integer percentage = candidateService.getCandidatePercentage(candidate, orgLocation);
					// return percentage.toString();
					response.setObject(percentage.toString());
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * Rest Call for saveCandidateDocument
	 * 
	 * @param candidateDocumentsDTO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "rest/saveCandidateDocument" }, method = RequestMethod.POST)
	public @ResponseBody Response<String> saveCandidateDocumentRest(@RequestBody CandidateDocumentsDTO candidateDocumentsDTO,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				candidateDocumentsDTO.setStaticPath(staticPath);
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (null != candidateDocumentsDTO.getCandidateId()) {
					candidateDocumentsService.storeCandidateDocuments(candidateDocumentsDTO,
							Long.parseLong(candidateDocumentsDTO.getCandidateId()), candidateDocumentsDTO.getDocumentUUID(),
							orgLocation.getId(), user);
					Candidate candidate = candidateService
							.getCandidateByCandidateId(Long.parseLong(candidateDocumentsDTO.getCandidateId()));
					Integer percentage = candidateService.getCandidatePercentage(candidate, orgLocation);
					response.setObject(percentage.toString());
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_USER.name());
					response.setResponseMessage(Result.INVALID_USER.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * This Method is for getting candidate documents
	 * 
	 * @author PrasadK
	 * 
	 * @param candidateId
	 * @param request
	 * 
	 * @return CandidateEmployers list
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getCandidateDocumentsListByCandidateId",
			"rest/getCandidateDocumentsListByCandidateId" }, method = RequestMethod.POST)
	public ResponseList<CandidateDocumentsDTO> getCandidateDocumentsListByCandidateId(@RequestParam(defaultValue = "0") long candidateId,
			HttpServletRequest request) throws Exception {
		ResponseList<CandidateDocumentsDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && candidateId > 0) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				List<CandidateDocumentsDTO> candidateDocumentsList = candidateDocumentsService
						.getCandidateDocumentsDTOListByCandidateIdAndOrgLocationID(candidateId, loginOrgLocation.getId());
				responseList.setData(candidateDocumentsList);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else if (user == null) {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				responseList.setResponse(Result.INVALID_USER.name());
				responseList.setResponseMessage(Result.INVALID_USER.getValue());
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}

	/**
	 * @author Prasad K
	 * 
	 *         This method is used to get Document
	 * 
	 * @param documentId
	 * @param request
	 * 
	 * @return resultJson
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDocument", method = RequestMethod.POST)
	@ResponseBody
	public Response<CandidateDocuments> getDocument(@RequestParam(defaultValue = "0") Long candidateId,
			@RequestParam(defaultValue = "0") long documentId, HttpServletRequest request) throws Exception {
		Response<CandidateDocuments> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && candidateId > 0 && documentId > 0) {
				CandidateDocuments candidateDocuments = null;
				candidateDocuments = candidateDocumentsService.getCandidateDocumentByDocumentId(documentId);
				if (candidateDocuments != null) {
					response.setObject(candidateDocuments);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else if (candidateId <= 0) {
				response.setResponse(Result.INVALID_USER.name());
				response.setResponseMessage(Result.INVALID_USER.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * @author Prasad K
	 * 
	 *         This method is used to download Document
	 * 
	 * @param documentUUID
	 * @param request
	 * 
	 * @return resultJson
	 * 
	 * @throws Exception
	 */
	/*
	 * @RequestMapping(value = "/downloadDoc", method = RequestMethod.GET) public
	 * void downloadDoc(@RequestParam(value = "documentUUID", required = false)
	 * String documentUUID, HttpServletResponse response) throws Exception {
	 * CandidateDocuments candidateDocuments =
	 * candidateDocumentsService.getCandidateDocumentByDocumentUUID(documentUUID);
	 * if (null != candidateDocuments) {
	 * response.setContentType("application/octet-stream"); StringBuilder fileName =
	 * new StringBuilder(); response.setHeader("Content-Disposition",
	 * fileName.append("attachment; filename=\"").append(candidateDocuments.
	 * getFileName()).append("\"").toString()); InputStream inputStream = new
	 * ByteArrayInputStream(candidateDocuments.getDocFile()); try { OutputStream
	 * outStream = response.getOutputStream(); byte[] buffer = new byte[4096]; int
	 * bytesRead = -1; while ((bytesRead = inputStream.read(buffer)) != -1) {
	 * outStream.write(buffer, 0, bytesRead); } inputStream.close();
	 * outStream.close(); } catch (FileNotFoundException e) {
	 * logger.error(ExceptionUtils.getFullStackTrace(e)); } } }
	 */

	/**
	 * @author Prasad K
	 * 
	 *         This method is used to update Document
	 * 
	 * @param documentId
	 * @param request
	 * 
	 * @return resultJson
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/terminateDoc", "rest/terminateDoc" }, method = RequestMethod.POST)
	public @ResponseBody Response<String> terminateDoc(CandidateDocumentsDTO candidateDocumentsDTO) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(candidateDocumentsDTO.getLoginUserId());
			if (user != null) {
				OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(candidateDocumentsDTO.getOrgLocationId());
				CandidateDocuments candidateDocuments = candidateDocumentsService
						.getCandidateDocumentByDocumentId(Long.parseLong(candidateDocumentsDTO.getDocumentId()));
				if (candidateDocuments != null) {
					candidateDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
					candidateDocuments.setUpdatedBy(user.getUserCode());
					candidateDocuments.setUpdatedDate(commonUtil.currentDate());
					candidateDocumentsService.updateCandidateDocuments(candidateDocuments);
					StringBuffer activitySummary = new StringBuffer();
					if (candidateDocuments.getFileName() != null && !candidateDocuments.getFileName().equalsIgnoreCase("")) {
						activitySummary.append("Document").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION)
								.append(candidateDocuments.getFileName()).append(Constants.NEW_VALUE_TAG_END);
					} else if (candidateDocuments.getDocumentType().equalsIgnoreCase("Other")) {
						activitySummary.append("Document").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION)
								.append(candidateDocuments.getDocumentOtherType()).append(Constants.NEW_VALUE_TAG_END);
					} else {
						activitySummary.append("Document").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION)
								.append(candidateDocuments.getDocumentType()).append(Constants.NEW_VALUE_TAG_END);
					}
					candidateActivityCommonUtil.storeCandidateDocumentsHistory(candidateDocuments, user);
					candidateActivityCommonUtil.storeCandidateActivity(user, loginOrgLocation, candidateDocuments.getCandidateId(), "",
							Constants.CANDIDATE_DELETED_DOCUMENTS, activitySummary.toString(), "CANDIDATE");

				}
				Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candidateDocumentsDTO.getCandidateId()));
				if (null != candidate && null != candidate.getId()) {
					CandidateIndex candidateIndex = candidateIndexHelper.getCandidateIndexFromCandidate(candidate);
					candidateIndex.setCandidateId(candidate.getId());
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					candidateIndex.setUpdatedDate(dateFormat.format(new Date()));
					sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX,
							Constants.ELK_CANDIDATE_TYPE, candidate.getId().toString());

					// OrgLocations loginOrgLocation = (OrgLocations)
					// SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					Integer percentage = candidateService.getCandidatePercentage(candidate, loginOrgLocation);
					response.setObject(percentage.toString());
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/requestCandidateForDocuments", method = RequestMethod.POST)
	public @ResponseBody Response<String> requestCandidateForDocuments(CandidateDocumentsDTO candidateDocumentsDTO,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			Long userId=(Long)SourceLeadSessionManager.getSessionAttribute(request, "userId");
			Long orgLocationId =(Long)SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			User user = userService.getUserById(userId);
			if (user != null) {
//				OrgLocations orgLocation=(OrgLocations)SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//				Org org=(Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
				OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
//				loginOrgLocation.setOrg(org);
				requestedDocumentsService.storeRequestedDocumentsDetails(candidateDocumentsDTO, user, orgLocation);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
			
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/ws/uploadRequestedDocuments", method = RequestMethod.POST)
	public @ResponseBody Response<String> uploadRequestedDocuments(CandidateDocumentsDTO candidateDocumentsDTO, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		try {
			candidateDocumentsDTO.setStaticPath(staticPath);
			RequestedDocuments requestedDocuments = requestedDocumentsService
					.getRequestedDocumentsByRequestDocUUID(candidateDocumentsDTO.getRequestDocUUID());
			if (null != requestedDocuments) {
				List<CandidateDocuments> candidateDocumentsList = candidateDocumentsService
						.getCandidateDocumentsListByRequestedDocumentsId(requestedDocuments.getId());
				if (null != candidateDocumentsList && candidateDocumentsList.size() > 0) {
					for (CandidateDocuments candidateDocuments : candidateDocumentsList) {
						if (candidateDocumentsDTO.getFileInfo() != null && !candidateDocumentsDTO.getFileInfo().isEmpty()) {
							if (!candidateDocumentsDTO.getFileInfo().isEmpty()) {
								FileInfo fileInfo = candidateDocumentsDTO.getFileInfo().get(0);
								candidateDocuments.setFileName(fileInfo.getFileName());
								candidateDocuments.setFileContentType(fileInfo.getContentType());
								candidateDocuments.setFileURL(fileInfo.getFileURL());
								FileUploadUtil.moveFile(candidateDocumentsDTO.getStaticPath(), fileInfo, "requestedDocuments");
								candidateDocuments.setRequestedDocStatus(Constants.REQUESTED_DOCUMENT_SUBMITTED);
								candidateDocumentsService.updateCandidateDocuments(candidateDocuments);
								candidateDocumentsDTO.getFileInfo().remove(0);
							}
						}
					}
				}
				requestedDocuments.setIsDocsUploaded(Constants.YES);
				requestedDocumentsService.saveRequestedDocuments(requestedDocuments);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/rejectRequestedDocuments", method = RequestMethod.POST)
	public @ResponseBody Response<String> rejectRequestedDocuments(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "documentsIds", required = false) String documentsIds, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && documentsIds != null && !documentsIds.isEmpty()) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String json1 = "{" + json + "}";
				JSONObject jsonObj = new JSONObject(json1);
				requestedDocumentsService.rejectRequestedDocuments(jsonObj, user, loginOrgLocation, documentsIds);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/acceptRequestedDocuments", method = RequestMethod.POST)
	public @ResponseBody Response<String> acceptRequestedDocuments(
			@RequestParam(value = "documentsIds", required = false) String documentsIds,
			@RequestParam(value = "userId", required = false) Long userId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "candidateId", required = false) Long candidateId) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(userId);
			if (user != null && documentsIds != null && !documentsIds.isEmpty()) {
				OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(orgLocationId);
				Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
				requestedDocumentsService.acceptRequestedDocuments(user, documentsIds, loginOrgLocation, candidate);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/validateDocName")
	public Response<Boolean> validateDocName(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request) throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				String candidateDocument = name;
				Boolean canEdu = candidateDocumentsService.validateCandidateDocument(candidateId, candidateDocument);
				response.setObject(canEdu);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	@RequestMapping(value = "/rest/candidateDocuments", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<LookUpData> candidateDocuments(HttpServletRequest request) throws Exception
	{
		ResponseList<LookUpData> responseList = new ResponseList<LookUpData>();
		List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
		responseList.setData(documentsList);
		return responseList;
	}
	
	@RequestMapping(value = "/getUserDocumentList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<LookUpData> getUserDocumentList(HttpServletRequest request) throws Exception
	{
		ResponseList<LookUpData> respList = new ResponseList<LookUpData>();
		List<LookUpData> documentsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE);
		respList.setData(documentsList);
		return respList;
	}
	
}
