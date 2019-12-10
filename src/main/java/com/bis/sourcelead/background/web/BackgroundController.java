package com.bis.sourcelead.background.web;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.bgc.dto.BgcCandidateDetailsDTO;
import com.bis.sourcelead.bgc.entity.BgcCandidateDetails;
import com.bis.sourcelead.bgc.service.BgcCommonService;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.dto.ImmigrationListViewDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.workflow.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;

@Controller
@RequestMapping("/bgc")
public class BackgroundController {

	private static final Logger logger = LoggerFactory.getLogger(BackgroundController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	BgcCommonService bgcCommonService;
	
	
	@RequestMapping(value = { "/inProgressBgcDetails" })
	public ModelAndView viewInprogressBgcDetails(HttpServletRequest request	) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		SessionMaintainanceData sessionData = new SessionMaintainanceData();
		sessionData.setNav(Constants.IN_PROGRESS);
			  model.addObject("sourceleadUrl", sourceleadUrl);
			  model.setViewName("/background/inProgressBgcDetails");
		return model;
	}

	@RequestMapping(value = { "/completedBgcDetails" })
	public ModelAndView viewCompletedBgcDetails(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		SessionMaintainanceData sessionData = new SessionMaintainanceData();
		sessionData.setNav(Constants.COMPLETED);
		
			  model.addObject("sourceleadUrl", sourceleadUrl);
			  model.setViewName("/background/completedBgcDetails");
		return model;
	}
	
	/*
	 * below method is used to fetch bgc candidate details entity record by using bgcUuid.
	 * candidate entity is set as model and view object and details are displayed in summary.
	 */
	@RequestMapping(value = { "/bgcCandidateSummary" })
	public ModelAndView viewOnProgressViewDetails(
			@RequestParam(value="bgcUUID", required=true) String bgcUUID,
			HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		BgcCandidateDetailsDTO bgcCandidateDetailsDTO = bgcCommonService.getBgcCandidateDetailsByBgcUuid(bgcUUID);
		if(bgcCandidateDetailsDTO != null){
			model.addObject("bgcCandidate", bgcCandidateDetailsDTO);
		}
		SessionMaintainanceData sessionData = new SessionMaintainanceData();
		sessionData.setNav(Constants.COMPLETED);
		
			  model.addObject("sourceleadUrl", sourceleadUrl);
			  model.setViewName("/background/bgcCandidateSummary");
		return model;
	}
	
	//editBgcCandidate
	@RequestMapping(value = { "/editBgcCandidate" })
	public ModelAndView editBgcCandidate(
			@RequestParam(value="bgcUUID", required=true) String bgcUUID,
			HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		BgcCandidateDetailsDTO bgcCandidateDetailsDTO = bgcCommonService.getBgcCandidateDetailsByBgcUuid(bgcUUID);
		if(bgcCandidateDetailsDTO != null){
			model.addObject("bgcCandidate", bgcCandidateDetailsDTO);
		}
		SessionMaintainanceData sessionData = new SessionMaintainanceData();
		sessionData.setNav(Constants.COMPLETED);
		
			  model.addObject("sourceleadUrl", sourceleadUrl);
			  model.setViewName("/background/editBgcCandidate");
		return model;
	}
	
	
	/*
	 * below method is used to fetch background candidates details for the 
	 * Data table is using for showing records.
	 */
	@RequestMapping(value = "/getBgcCandidageteList",  method = RequestMethod.POST)
	public @ResponseBody ResponseList<BgcCandidateDetailsDTO> getAllBgcList(
			BgcCandidateDetailsDTO bgcCandidateDetailsDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "filterAttorneyVal", required = false) Long[] filterAttorneyVal,
			@RequestParam(value = "filterTypeVal", required = false) Long[] filterTypeVal,
			@RequestParam(value = "filterStatusVal", required = false) Long[] filterStatusVal,
			@RequestParam(value = "filterDurationVal", required = false) Long[] filterDurationVal,
			@RequestParam(value = "orgTypeVal", required = false) String orgTypeVal, HttpServletRequest request) {
		ResponseList<BgcCandidateDetailsDTO> bgcCandidateDetailsDTOList = null;
		logger.debug("inisde controller  BgcCandidateDetailsDTO  --> ");
		try {
			String[] tableColumns = { "uuid", "name",  "currentBgcStatus","updatedPercetage", "personalInfoStatus", "educationCheckStaus",
					"priorEmployersCheckStatus", "healthCheckStatus", "healthCheckStatus", "crimeCheckStatus", "OthersInfoStatus" ,"comments","updatedDate"};// should be
			// entity names
			int sortColumnIndex = -1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default ascending order
			if (bgcCandidateDetailsDTO.getOrder() != null && !bgcCandidateDetailsDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(bgcCandidateDetailsDTO.getOrder().get(0).get("column"));
				sortColumnOrder = bgcCandidateDetailsDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means
				bgcCandidateDetailsDTO.setSortOrder(sortColumnOrder); // direction
				// of the sorting.
			}
			Map<String, Long[]> filterVal = new HashMap<String, Long[]>();
			if (filterAttorneyVal.length != 0) {
				filterVal.put("attorney", filterAttorneyVal);
			}
			if (filterTypeVal.length != 0) {
				filterVal.put("type", filterTypeVal);
			}
			if (filterStatusVal.length != 0) {
				filterVal.put("status", filterStatusVal);
			}
			if (filterDurationVal.length != 0) {
				filterVal.put("duration", filterDurationVal);
			}
			if (sortColumnIndex != -1)
				bgcCandidateDetailsDTO.setSortColumn(tableColumns[sortColumnIndex]);

			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			bgcCandidateDetailsDTO.setOrgLocationId(orgLocations.getId());
			bgcCandidateDetailsDTOList = (ResponseList<BgcCandidateDetailsDTO>) bgcCommonService.getBgcCadidateListByOrgLocationId(bgcCandidateDetailsDTO, searchValue,
					orgLocations, filterVal, orgTypeVal);
			System.out.println("bgcCandidateDetailsDTOList size "+bgcCandidateDetailsDTOList.getRecordsTotal());
			// commonUtil.highLightMenu(Constants.Immigration, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("after controller  getAllImmigartionList  --> ");
		return bgcCandidateDetailsDTOList;
	}
	
	
	//@PreAuthorize("hasRole('ROLE_DELETE_PETITION_TEMPLATE')")
	@RequestMapping(value = "/deleteBgcCandidateDetailsbyUuid")
	public @ResponseBody String deleteBgcCandidateDetailsbyUuiId(@RequestParam(value = "uuid", required = true) String uuid, HttpServletRequest request) {
		String status = null;		
		try {
			status = bgcCommonService.deleteBgcCandidateDetailsbyUuiId(uuid);
			logger.debug("inside delete controller method calling for the uuid----->>>>>>>: " + uuid);
		} catch (Exception e) {
			logger.error(" Exception while deleting bgc deitails in controller -->" + uuid);
			e.printStackTrace();
		}
		System.out.println("inisde controller status : "+status);
		return status;
	}
	
	@RequestMapping(value = "/getBgcCandidateEducationSummary")
	public @ResponseBody List<CandidateEducation> getBgcCandidateEducationSummary(@RequestParam(value = "uuid", required = true) String uuid, HttpServletRequest request) {
		try {
			logger.debug("inside getbgcCandidateEducationSummary");
			List<CandidateEducation> candidateEducationList= bgcCommonService.getBgcCandidateEducationSummaryByCandidateUuid(uuid);
			return candidateEducationList;
		} catch (Exception e) {
			logger.error(e+" in getbgcCandidateEducationSummary");
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/getBgcCandidateEmploymentSummary")
	public @ResponseBody List<CandidateEmployers> getBgcCandidateEmploymentSummary(@RequestParam(value = "uuid", required = true) String uuid, HttpServletRequest request) {
		try {
			logger.debug("inside getBgcCandidateEmploymentSummary");
			List<CandidateEmployers> candidateEducationList= bgcCommonService.getBgcCandidateEmploymentSummary(uuid);
			return candidateEducationList;
		} catch (Exception e) {
			logger.error(e+" in getBgcCandidateEmploymentSummary");
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/saveEditBgcCandidate", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveEditBgcCandidate(
			BgcCandidateDetailsDTO bgcCandidateDetailsDTO, HttpServletRequest request) {
		Response<String> response = new Response<String>();
		Long userId =  (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = new User();
		user.setId(userId);
		 logger.debug("inside saveEditBgcCandidate");
		try {
			if (user != null && Validator.validate(bgcCandidateDetailsDTO)) {
				bgcCommonService.saveEditBgcCandidate(bgcCandidateDetailsDTO);
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
			e.printStackTrace();
		}
		 logger.debug("after saveEditBgcCandidate");
		return response;
	}
}
