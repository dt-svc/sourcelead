package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateResumeDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateGlobalService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CandidateGlobalUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@PropertySource("${propertyLocation:app.properties}")
/**
 * 
 * @author root Handles client requests
 */
public class CandidateResumeRestController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateResumeRestController.class);
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Value("${static.path}")
	private String staticPath;
	@Autowired
	CandidateService candidateService;
	@Autowired
	CandidateResumesService candidateResumesService;
	@Autowired
	PdfConversionUtil pdfConversionUtil;
	@Autowired
	CommonUtil commonUtil;
	@Autowired
	UserService userService;
	@Autowired
	OrgLocationService orgLocationService;
	@Autowired
	RequirementOffersService requirementOffersService;
	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	@Autowired
	CandidateActivityCommonUtil candidateActivityCommonUtil;
	
	@Autowired
	CandidateIndexHelper candidateIndexHelper;
	
	@Autowired
	CandidateGlobalService candidateGlobalService;
	
	@Autowired
	CandidateGlobalUtil candidateGlobalUtil;

	/**
	 * @author Venkat Kesav
	 * 
	 *         Handles Resume saving for Candidate
	 * 
	 * @param json,
	 *            candidateId
	 * @param request
	 * 
	 * @return ResponseBody
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/saveCandidateResume" }, method = RequestMethod.POST)
	public @ResponseBody Response<Integer> saveCandidateResume(CandidateResumeDTO candidateResumeDTO, HttpServletRequest request)
			throws Exception {
		Response<Integer> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (user != null) {
			candidateResumeDTO.setStaticPath(staticPath);
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (StringUtils.isNotEmpty(candidateResumeDTO.getCandidateId())) {
				
				CandidateGlobal candidateGlobal = candidateGlobalService.getCandidateGlobalByCandidateId(Long.parseLong(candidateResumeDTO.getCandidateId()));
				candidateResumesService.storeGlobalCandidateResume(candidateResumeDTO, user, candidateGlobal, candidateResumeDTO.getResumeId(),
						orgLocation);
				Integer percentage = candidateGlobalUtil.getCandidatePercentage(candidateGlobal, orgLocation);
				response.setObject(percentage);
				
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}

	/**
	 * @author Mahaveer Manoj,
	 * 
	 *         Request for 'Manager' Role is working fine, but for 'Employee' login
	 *         the particular request is not getting hit here So,
	 *         commented @PreAuthorize annotation
	 **/
	// @PreAuthorize("hasAnyRole('ROLE_ADD_CANDIDATE_RESUMES','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_ADD_ASSOCIATE_RESUMES')")
	@RequestMapping(value = { "rest/saveCandidateResume" }, method = RequestMethod.POST)
	public @ResponseBody Response<String> saveCandidateResumeRest(@RequestBody CandidateResumeDTO candidateResumeDTO,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				candidateResumeDTO.setStaticPath(staticPath);
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (StringUtils.isNotEmpty(candidateResumeDTO.getCandidateId())) {
					CandidateGlobal candidateGlobal = candidateGlobalService.getCandidateGlobalByCandidateId(Long.parseLong(candidateResumeDTO.getCandidateId()));
					candidateResumesService.storeGlobalCandidateResume(candidateResumeDTO, user, candidateGlobal, candidateResumeDTO.getResumeId(),
							orgLocation);
					Integer percentage = candidateGlobalUtil.getCandidatePercentage(candidateGlobal, orgLocation);
					response.setObject(percentage.toString());
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
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
	 * @author Venkat Kesav
	 * 
	 *         This method is used to get candidate resumes list
	 * 
	 * @param candidateId
	 * 
	 * @return resultJson
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/candidateResumesListByCandidateId", "rest/candidateResumesListByCandidateId" }, method = RequestMethod.POST)
	public @ResponseBody ResponseList<CandidateResumeDTO> candidateResumesListByCandidateId(
			@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "candidateType", required = false) String candidateType,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<CandidateResumeDTO> resumeList = new ResponseList<>();
		Integer status = Constants.CANDIDATE_ACTIVE;
		List<CandidateResumeDTO> candidateResumesDTOList = new ArrayList<>();
		if (candidateId != null) {
			List<CandidateResumes> candidateResumesList = null;
			candidateResumesList = candidateResumesService
					.getCandidateGlobalResumesListByCandidateIdAndStatus(Long.parseLong(candidateId), status);
			
			if(candidateResumesList != null && !candidateResumesList.isEmpty()) {
				for (CandidateResumes candidateResume : candidateResumesList) {
					CandidateResumeDTO candidateResumeDTO = new CandidateResumeDTO();
					User createdUser = userService.getUserByUserCode(candidateResume.getCreatedBy());
					if (createdUser != null) {
						StringBuilder name = new StringBuilder();
						candidateResume
								.setCreatedBy(name.append(createdUser.getFirstName()).append(" ").append(createdUser.getLastName()).toString());
					}
					candidateResumeDTO.setId(candidateResume.getId());
					candidateResumeDTO.setTitle(candidateResume.getTitle());
					candidateResumeDTO.setDescription(candidateResume.getDescription());
					candidateResumeDTO.setCreatedBy(candidateResume.getCreatedBy());
					candidateResumeDTO.setFileContentType(candidateResume.getFileContentType());
					candidateResumeDTO.setFileName(candidateResume.getFileName());
					candidateResumeDTO.setFileURL(candidateResume.getFileURL());
					candidateResumeDTO.setResumeId(String.valueOf(candidateResume.getId()));
					candidateResumeDTO.setCandidateId(candidateId);
					candidateResumeDTO.setCandidateResumeUpdateDateToString(
							commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), candidateResume.getUpdatedDate()));
					candidateResumesDTOList.add(candidateResumeDTO);

				}
			}
			
		}
		resumeList.setData(candidateResumesDTOList);
		return resumeList;
	}

	/**
	 * @author Venkat Kesav
	 * 
	 *         This method is used to update Resume
	 * 
	 * @param resumeId
	 * @param request
	 * 
	 * @return resultJson
	 * 
	 * @throws Exception
	 */
	// @PreAuthorize("hasAnyRole('ROLE_VIEW_DELETE_CANDIDATE_RESUMES','ROLE_CROWDSOURCING_PROFILE','ROLE_VIEW_DELETE_ASSOCIATE_RESUMES','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/terminateResume", method = RequestMethod.POST)
	@ResponseBody
	public Response<String> terminateResume(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "resumeId", required = false) String resumeId,
			@RequestParam(value = "candidateType", required = false) String candidateType) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
				OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
				StringBuffer activitySummary = new StringBuffer();
				CandidateResumes candidateResume = candidateResumesService.getCandidateResumeByResumeId(Long.parseLong(resumeId));
				if (candidateResume != null) {
					candidateResume.setStatus(Constants.CANDIDATE_IN_ACTIVE);
					candidateResume.setUpdatedBy(user.getUserCode());
					candidateResume.setUpdatedDate(commonUtil.currentDate());
					candidateResume = candidateResumesService.updateCandidateResume(candidateResume);
					if (candidateResume.getTitle() != null && candidateResume.getTitle() != ""
							&& candidateResume.getTitle() != "undefined") {
						activitySummary.append("Resume").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION)
								.append(candidateResume.getTitle()).append(Constants.NEW_VALUE_TAG_END).append(" ");
					} else {
						activitySummary.append("Resume").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION)
								.append(candidateResume.getFileName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateActivityCommonUtil.storeCandidateResumesHistory(candidateResume, user);
				/*	if(StringUtils.isNotBlank(candidateType) && candidateType.equalsIgnoreCase(Constants.CANDIDATE_GLOBAL)) {
						candidateActivityCommonUtil.storeCandidateGlobalActivity(user, orgLocations, candidateResume.getCandidateId()(), "",
								Constants.CANDIDATE_DELETED_RESUME, activitySummary.toString(), "CANDIDATE");
					}else {
						candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidateResume.getCandidateId(), "",
								Constants.CANDIDATE_DELETED_RESUME, activitySummary.toString(), "CANDIDATE");
					}*/
					
					candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidateResume.getCandidateId(), "",
							Constants.CANDIDATE_DELETED_RESUME, activitySummary.toString(), "CANDIDATE");
					
				}
				Integer percentage = candidateService.getCandidatePercentage(candidate, orgLocations);
				response.setObject(percentage.toString());
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

	/**
	 * Resume Delete rest call
	 * 
	 * @param loginUserId
	 * @param orgLocationId
	 * @param candidateId
	 * @param resumeId
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "rest/terminateResume", method = RequestMethod.POST)
	public @ResponseBody Response<String> terminateResumeRest(@RequestBody CandidateResumeDTO candidateResumeDTO) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(Long.parseLong(candidateResumeDTO.getLoginUserId()));
			if (user != null) {
				Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(candidateResumeDTO.getCandidateId()));
				OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(candidateResumeDTO.getLoginUserId()));
				StringBuffer activitySummary = new StringBuffer();
				CandidateResumes candidateResume = candidateResumesService
						.getCandidateResumeByResumeId(Long.parseLong(candidateResumeDTO.getResumeId()));
				if (candidateResume != null) {
					candidateResume.setStatus(Constants.CANDIDATE_IN_ACTIVE);
					candidateResume.setUpdatedBy(user.getUserCode());
					candidateResume.setUpdatedDate(commonUtil.currentDate());
					candidateResume = candidateResumesService.updateCandidateResume(candidateResume);
					if (candidateResume.getTitle() != null && candidateResume.getTitle() != ""
							&& candidateResume.getTitle() != "undefined") {
						activitySummary.append("Resume").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION)
								.append(candidateResume.getTitle()).append(Constants.NEW_VALUE_TAG_END).append(" ");
					} else {
						activitySummary.append("Resume").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION)
								.append(candidateResume.getFileName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
					}
					candidateActivityCommonUtil.storeCandidateResumesHistory(candidateResume, user);
					candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, candidateResume.getCandidateId(), "",
							Constants.CANDIDATE_DELETED_RESUME, activitySummary.toString(), "CANDIDATE");
					// update the candidate Resume on ELK
					if (null != candidate && null != candidate.getId()) {
						CandidateIndex candidateIndex = new CandidateIndex();
						candidateIndex.setCandidateId(candidate.getId());
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						candidateIndex.setUpdatedDate(dateFormat.format(new Date()));
						candidateIndex.setCurrentResumeId(Constants.CANDIATE_RESUME_IN_ACTIVE);

						sourceleadELKBaseIndexer.saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX,
								Constants.ELK_CANDIDATE_TYPE, candidate.getId().toString());
					}
				}
				Integer percentage = candidateService.getCandidatePercentage(candidate, orgLocations);
				response.setObject(percentage.toString());
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

	/**
	 * @author Venkat Kesav
	 * 
	 *         This method is used to get Resume
	 * 
	 * @param resumeId
	 * @param request
	 * 
	 * @return resultJson
	 * 
	 * @throws Exception
	 */
	// @PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_CANDIDATE_RESUMES','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_PROFILE','ROLE_VIEW_EDIT_ASSOCIATE_RESUMES')")
	@RequestMapping(value = "/getResume", method = RequestMethod.POST)
	public @ResponseBody Response<CandidateResumes> getResume(@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "resumeId", required = false) String resumeId, HttpServletRequest request) throws Exception {
		Response<CandidateResumes> response = new Response<>();
		try {
			CandidateResumes candidateResume = candidateResumesService.getCandidateResumeByResumeId(Long.parseLong(resumeId));
			if (candidateResume != null) {
				response.setObject(candidateResume);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.FAILED.name());
				response.setResponseMessage(Result.FAILED.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * @author Venkat Kesav
	 * 
	 *         This method is used to download Resume
	 * 
	 * @param resumeId
	 * @param request
	 * 
	 * @return resultJson
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadResume", method = RequestMethod.GET)
	public void downloadResume(@RequestParam(value = "resumeUUID", required = false) String resumeUUID, HttpServletResponse response)
			throws Exception {
		CandidateResumes candidateResume = candidateResumesService.getCandidateResumeByResumeUUID(resumeUUID);
		if (null != candidateResume) {
			response.setContentType("application/octet-stream");
			StringBuilder fileName = new StringBuilder();
			response.setHeader("Content-Disposition",
					fileName.append("attachment; filename=\"").append(candidateResume.getFileName()).append("\"").toString());
			// InputStream inputStream = new
			// ByteArrayInputStream(candidateResume.getDocFile());
			try {
				OutputStream outStream = response.getOutputStream();
				byte[] buffer = new byte[4096];
				int bytesRead = -1;
				/*
				 * while ((bytesRead = inputStream.read(buffer)) != -1) {
				 * outStream.write(buffer, 0, bytesRead); } inputStream.close();
				 */
				outStream.close();
			} catch (FileNotFoundException e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		}
	}

	@RequestMapping(value = "/viewDocumentsForCan/{title}", method = RequestMethod.GET)
	public void viewResume(@PathVariable("title") String title, @RequestParam(value = "file", required = false) String docFile,
			HttpServletResponse response) throws Exception {
		docFile = URLDecoder.decode(docFile, "utf-8");
		File file = new File(staticPath, docFile);
		try (FileInputStream fis = new FileInputStream(file)) {
			byte[] pdfBytes = new byte[fis.available()];
			fis.read(pdfBytes);
			if (!file.getName().endsWith(".pdf")) {
				pdfBytes = pdfConversionUtil.convertDocToPdf(pdfBytes);
			}
			response.setHeader("Content-disposition", "inline; filename=contractPdf.pdf");
			response.setContentType("application/pdf");
			ServletOutputStream sos = response.getOutputStream();
			if (null != pdfBytes) {
				response.setContentLength(pdfBytes.length);
				sos.write(pdfBytes);
			}
			sos.flush();
			sos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/offerDownloadDocument", method = RequestMethod.GET)
	public void candidateAndOfferDownloadDocument(@RequestParam(value = "resumeId", required = false) Long resumeId,
			HttpServletResponse response) throws Exception {
		RequirementOffers requirementOffers = requirementOffersService.getRequirementOfferId(resumeId);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + requirementOffers.getFileName());
		InputStream inputStream = new ByteArrayInputStream(requirementOffers.getDocFile());
		try {
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outStream.close();
		} catch (FileNotFoundException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
	}
}