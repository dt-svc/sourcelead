package com.bis.sourcelead.background.web;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
//import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
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
import com.bis.sourcelead.bgc.dto.BgcComponentDTO;
import com.bis.sourcelead.bgc.dto.BgcStatusDTO;
import com.bis.sourcelead.bgc.dto.BgcTemplateDTO;
import com.bis.sourcelead.bgc.entity.BgcCandidateDetails;
import com.bis.sourcelead.bgc.entity.BgcComponent;
import com.bis.sourcelead.bgc.entity.BgcStatus;
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

/*	@RequestMapping(value = { "/completedBgcDetails" })
	public ModelAndView viewCompletedBgcDetails(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		SessionMaintainanceData sessionData = new SessionMaintainanceData();
		sessionData.setNav(Constants.COMPLETED);
		
			  model.addObject("sourceleadUrl", sourceleadUrl);
			  model.setViewName("/background/completedBgcDetails");
		return model;
	}*/
	
	@RequestMapping(value = { "/bgcCandidates" })
	public ModelAndView getBgcCandidates(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		SessionMaintainanceData sessionData = new SessionMaintainanceData();
		sessionData.setNav("BGC Candidates");
		
			  model.addObject("sourceleadUrl", sourceleadUrl);
			  model.setViewName("/background/bgcCandidates");
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
	@RequestMapping(value = {"/getBgcCandidageteList","/exportBgcToPdfOrExcel"})
	public @ResponseBody ResponseList<BgcCandidateDetailsDTO> getAllBgcList(
			BgcCandidateDetailsDTO bgcCandidateDetailsDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "filterAttorneyVal", required = false) Long[] filterAttorneyVal,
			@RequestParam(value = "filterTypeVal", required = false) Long[] filterTypeVal,
			@RequestParam(value = "filterStatusVal", required = false) Long[] filterStatusVal,
			@RequestParam(value = "filterDurationVal", required = false) Long[] filterDurationVal,
			@RequestParam(value = "orgTypeVal", required = false) String orgTypeVal, HttpServletRequest request, HttpServletResponse response) {
		ResponseList<BgcCandidateDetailsDTO> bgcCandidateDetailsDTOList = null;
		logger.debug("inisde controller  BgcCandidateDetailsDTO  --> ");
		try {
			String[] tableColumns = { "currentBgcStatus", "generatedId", "name", "updatedPercetage", "personalInfoStatus", "educationCheckStaus",
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
			
			//17-jan-2020
			//export bgc to pdf or excel.
			if(bgcCandidateDetailsDTO.getExportType() != null){
			if(bgcCandidateDetailsDTOList != null && bgcCandidateDetailsDTOList.getData().size() > 0){
				bgcCommonService.exportBgcToPdfOrExcel(bgcCandidateDetailsDTO, bgcCandidateDetailsDTOList, orgLocations, response);
			}
			}
			
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
	public @ResponseBody List<CandidateEducation> getBgcCandidateEducationSummary(@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request) {
		try {
			logger.debug("inside getbgcCandidateEducationSummary");
			List<CandidateEducation> candidateEducationList= bgcCommonService.getBgcCandidateEducationSummaryByCandidateId(candidateId);
			return candidateEducationList;
		} catch (Exception e) {
			logger.error(e+" in getbgcCandidateEducationSummary");
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/getBgcCandidateEmploymentSummary")
	public @ResponseBody List<CandidateEmployers> getBgcCandidateEmploymentSummary(@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request) {
		try {
			logger.debug("inside getBgcCandidateEmploymentSummary");
			String dateFormat = (String) SourceLeadSessionManager.getSessionAttribute(request, "dateFormat");
			List<CandidateEmployers> candidateEducationList= bgcCommonService.getBgcCandidateEmploymentSummary(dateFormat, candidateId);
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
	
	@RequestMapping(value = { "/getBgcCandidateSummary", "/getBgcCandidateLegalAndCriminalSummary" })
	public @ResponseBody BgcCandidateDetailsDTO getBgcCandidateLegalAndCriminalSummary(@RequestParam(value = "uuid", required = true) String uuid, HttpServletRequest request) {
		try {
			logger.debug("inside getBgcCandidateLegalAndCriminalSummary");
			BgcCandidateDetailsDTO bgcCandidateDetailsDTO= bgcCommonService.getBgcCandidateDetailsByBgcUuid(uuid);
			return bgcCandidateDetailsDTO;
		} catch (Exception e) {
			logger.error(e+" in getBgcCandidateLegalAndCriminalSummary");
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = { "/getBgcDynamicStatusList" }, method = RequestMethod.POST)
	public @ResponseBody ResponseList<BgcStatusDTO> getBgcDynamicStatusList(
			BgcStatusDTO bgcStatusDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseList<BgcStatusDTO> bgcStatusDTOList = null;
		logger.debug("inisde controller  Bgc Status DTO  --> ");
		try {
			String[] tableColumns = { "statusName", "color", "description"};// should be
			// entity names
			int sortColumnIndex = -1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default ascending order
			if (bgcStatusDTO.getOrder() != null && !bgcStatusDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(bgcStatusDTO.getOrder().get(0).get("column"));
				sortColumnOrder = bgcStatusDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means
				bgcStatusDTO.setSortOrder(sortColumnOrder); // direction
				// of the sorting.
			}
			
			
			if (sortColumnIndex != -1)
				bgcStatusDTO.setSortColumn(tableColumns[sortColumnIndex]);

			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			bgcStatusDTO.setOrgLocationId(orgLocations.getId());
			bgcStatusDTOList = (ResponseList<BgcStatusDTO>) bgcCommonService.getBgcStatusListByOrgLocationId(bgcStatusDTO, searchValue,
					orgLocations);
			System.out.println("bgcStatusDTOList size "+bgcStatusDTOList.getRecordsTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("after controller  getAllImmigartionList  --> ");
		return bgcStatusDTOList;
	}
	@RequestMapping(value = { "/bgcOrgStatusList"})
	public  ModelAndView getBgcStatusListByOrgLocationId(HttpServletRequest request)
	{
		try {
			logger.debug("inside getBgcStatusListByOrgLocationId");
			ModelAndView model = new ModelAndView();
			Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			List<BgcStatus> bgcStatusList = bgcCommonService.getBgcStatusListByOrgLocationId(orgLocationId);
			 model.addObject("bgcStatusLst",bgcStatusList);
			 model.setViewName("/background/bgcStatus");
			return model;
		} catch (Exception e) {
			logger.error(e + " in getBgcStatusListByOrgLocationId");
			e.printStackTrace();
			return null;
		}
	}

	// below method is for saving BGC status lists by ORG LOCATION id by SRINIVAS SABBU.
	@RequestMapping(value = { "/SaveOrUpdateBgcStatus"}, method = RequestMethod.POST)
	public  @ResponseBody Response<String>  saveOrUpdateBgcStatus(BgcStatusDTO bgcStatusDTO,
			HttpServletRequest request){
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (user != null) {
			try {
				if (Validator.validate(bgcStatusDTO)) {
					bgcCommonService.saveOrUpdateBgcStatusListByOrgLocationId(bgcStatusDTO, user , orgLocations);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				logger.error("Exception occured while saving Template Task Details" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	//Below method is used to get the bgc status details using by id by SRINIVAS SABBU.
	@RequestMapping(value = { "/getBgcStatusById"})
	public @ResponseBody BgcStatusDTO getBgcStatusById(@RequestParam(value = "id", required = true) Long statusId,
			HttpServletRequest request){
		BgcStatusDTO bgcStatusDto = null;
		try {
			User user =  (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(statusId != null &&  user != null) {
				bgcStatusDto = bgcCommonService.getBgcStatusDetailsById(statusId);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bgcStatusDto;
	}
	
	
	// Below method is for delete  BGC status record by ID .SRINIVA SABBU 
		@RequestMapping(value = { "/deleteBgcStatusById"})
		public @ResponseBody Response<String> deleteBgcStatusById(@RequestParam(value = "id", required = true) Long statusId,
				HttpServletRequest request){
			Response<String> response = new Response<String>();
			
			try {
				User user =  (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(statusId != null &&  user != null) {
					bgcCommonService.deleteBgcStatusListById(statusId);
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
			 logger.debug("after Deleted Bgc Status Record !!!!!!!");
			return response;
		}
		
	@RequestMapping(value = { "/bgcOrgComponentList"})
	public  ModelAndView getBgcComponentListByOrgLocationId(HttpServletRequest request)
	{
		try {
			logger.debug("inside getBgcComponentListByOrgLocationId");
			ModelAndView model = new ModelAndView();
		//	Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			//List<BgcComponent> bgcComponentList = bgcCommonService.getBgcComponentListByOrgLocationId(orgLocationId);
		//	 model.addObject("bgcComponentList",bgcComponentList);
			 model.setViewName("/background/bgcComponent");
			return model;
		} catch (Exception e) {
			logger.error(e + " in getBgcComponentListByOrgLocationId");
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping(value = { "/bgcOrgTemplateList"})
	public ModelAndView getBgcOrgcTemplateListByOrgLocationId(HttpServletRequest request)
	{
		try {
			logger.debug("inside getBgcOrgcTemplateListByOrgLocationId");
			ModelAndView model = new ModelAndView();
			//Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		//	List<BgcTemplateDTO> bgcTemplateDtoList = bgcCommonService.getBgcOrgcTemplateListByOrgLocationId(orgLocationId);
		//	model.addObject("bgcTemplateDtoList",bgcTemplateDtoList);
			 model.setViewName("/background/bgcTemplate");
			return model;
		} catch (Exception e) {
			logger.error(e + " in getBgcOrgcTemplateListByOrgLocationId");
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(value = { "/addBgcTemplate"})
	public ModelAndView addBgcTemplate(HttpServletRequest request)
	{
		try {
			logger.debug("inside getBgcOrgcTemplateListByOrgLocationId");
			ModelAndView model = new ModelAndView();
			Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			
			List<BgcStatus> statusList = bgcCommonService.getBgcUniqueStatusListByOrgLocationId(orgLocationId);
			System.out.println(" status list size is "+statusList.size());
			statusList.forEach(System.out::println);
			model.addObject("statusList",statusList);
			System.out.println(" status list size is "+statusList.size());
			List<BgcComponent> componentList = bgcCommonService.getBgcUniqueComponentListByOrgLocationId(orgLocationId);
			model.addObject("componentList",componentList);
			System.out.println(" componentList list size is "+componentList.size());
			model.setViewName("/background/addTemplate");
			 
			return model;
		} catch (Exception e) {
			logger.error(e + " in getBgcOrgcTemplateListByOrgLocationId");
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * used to refresh status list when clicked refresh icon at add template screen.
	 */
	@RequestMapping(value = { "/refreshBgcStatusList"})
	public ModelAndView refreshBgcStatusList(HttpServletRequest request)
	{
		try {
			logger.debug("inside getBgcOrgcTemplateListByOrgLocationId");
			ModelAndView model = new ModelAndView();
			Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			
			List<BgcStatus> statusList = bgcCommonService.getBgcUniqueStatusListByOrgLocationId(orgLocationId);
			System.out.println(" status list size is "+statusList.size());
			statusList.forEach(System.out::println);
			model.addObject("statusList",statusList);
			System.out.println(" status list size is "+statusList.size());
			List<BgcComponent> componentList = bgcCommonService.getBgcUniqueComponentListByOrgLocationId(orgLocationId);
			model.addObject("componentList",componentList);
			System.out.println(" componentList list size is "+componentList.size());
			model.setViewName("/background/bgcStatusList");
			 
			return model;
		} catch (Exception e) {
			logger.error(e + " in getBgcOrgcTemplateListByOrgLocationId");
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * used to refresh component list when clicked refresh icon at add template screen.
	 */
	@RequestMapping(value = { "/refreshBgcComponentList"})
	public ModelAndView refreshBgcComponentList(HttpServletRequest request)
	{
		try {
			logger.debug("inside getBgcOrgcTemplateListByOrgLocationId");
			ModelAndView model = new ModelAndView();
			Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			
			List<BgcComponent> componentList = bgcCommonService.getBgcUniqueComponentListByOrgLocationId(orgLocationId);
			model.addObject("componentList",componentList);
			System.out.println(" componentList list size is "+componentList.size());
			model.setViewName("/background/bgcComponentList");
			 
			return model;
		} catch (Exception e) {
			logger.error(e + " in getBgcOrgcTemplateListByOrgLocationId");
			e.printStackTrace();
			return null;
		}
	}
	/** 
	 * Below method is used to get the template list SRINIVAS 03-FEB-2020
	 * 
	 */
	@RequestMapping(value = { "/getBgcDynamicTemplateList" }, method = RequestMethod.POST)
	public @ResponseBody ResponseList<BgcTemplateDTO> getBgcDynamicTemplateList(
			BgcTemplateDTO bgcTemplateDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseList<BgcTemplateDTO> bgcTemplateDTOList = null;
		logger.debug("inisde controller  Bgc Status DTO  --> ");
		try {
			String[] tableColumns = { "name",  "description"};// should be
			// entity names
			int sortColumnIndex = -1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default ascending order
			if (bgcTemplateDTO.getOrder() != null && !bgcTemplateDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(bgcTemplateDTO.getOrder().get(0).get("column"));
				sortColumnOrder = bgcTemplateDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means
				bgcTemplateDTO.setSortOrder(sortColumnOrder); // direction
				// of the sorting.
			}
			
			
			if (sortColumnIndex != -1)
				bgcTemplateDTO.setSortColumn(tableColumns[sortColumnIndex]);

			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			bgcTemplateDTO.setOrgLocationId(orgLocations.getId());
			 bgcTemplateDTOList =  (ResponseList<BgcTemplateDTO>)bgcCommonService.getBgcOrgcTemplateListByOrgLocationId(bgcTemplateDTO, searchValue,
					orgLocations);
			System.out.println("BgcTemplateDTOList size "+bgcTemplateDTOList.getRecordsTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("after controller  getBgcDynamicComponentList  --> ");
		return bgcTemplateDTOList;
	}
	/** 
	 * Below methods are related component add/edit/delete functionality SRINIVAS 03-FEB-2020
	 * 
	 */
	@RequestMapping(value = { "/getBgcDynamicComponentList" }, method = RequestMethod.POST)
	public @ResponseBody ResponseList<BgcComponentDTO> getBgcDynamicComponentList(
			BgcComponentDTO bgcComponentDTO,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseList<BgcComponentDTO> bgcComponentDTOList = null;
		logger.debug("inisde controller  Bgc Status DTO  --> ");
		try {
			String[] tableColumns = { "name",  "description"};// should be
			// entity names
			int sortColumnIndex = -1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default ascending order
			if (bgcComponentDTO.getOrder() != null && !bgcComponentDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(bgcComponentDTO.getOrder().get(0).get("column"));
				sortColumnOrder = bgcComponentDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means
				bgcComponentDTO.setSortOrder(sortColumnOrder); // direction
				// of the sorting.
			}
			
			
			if (sortColumnIndex != -1)
				bgcComponentDTO.setSortColumn(tableColumns[sortColumnIndex]);

			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			bgcComponentDTO.setOrgLocationId(orgLocations.getId());
			bgcComponentDTOList = (ResponseList<BgcComponentDTO>) bgcCommonService.getBgcComponentListByOrgLocationId(bgcComponentDTO, searchValue,
					orgLocations);
			System.out.println("bgcComponentDTOList size "+bgcComponentDTOList.getRecordsTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("after controller  getBgcDynamicComponentList  --> ");
		return bgcComponentDTOList;
	}
	/**
	 * method is used for save or update existing record by SRINIVAS SABBU
	 * @param bgcStatusDTO
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = { "/SaveOrUpdateBgcComponent"}, method = RequestMethod.POST)
	public  @ResponseBody Response<String>  saveOrUpdateBgcComponent(BgcComponentDTO bgcComponentDTO,
			HttpServletRequest request){
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (user != null) {
			try {
				if (Validator.validate(bgcComponentDTO)) {
					bgcCommonService.saveOrUpdateBgcComponentListByOrgLocationId(bgcComponentDTO, user , orgLocations);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				logger.error("Exception occured while saving Template Task Details" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	// below method is used to get the BGC component details by ID by SRINIVAS SABBU.
		@RequestMapping(value = { "/getBgcComponentById"})
		public @ResponseBody BgcComponentDTO getBgcComponentById(@RequestParam(value = "id", required = true) Long componentId,
				HttpServletRequest request){
			BgcComponentDTO bgcComponentDto = null;
			try {
				User user =  (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(componentId != null &&  user != null) {
					bgcComponentDto = bgcCommonService.getBgcComponentDetailsById(componentId);
				}	
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return bgcComponentDto;
		}
     
		// Below method is for delete  BGC  Component by ID SRINIVAS SABBU.
		@RequestMapping(value = { "/deleteBgcComponentById"})
		public @ResponseBody Response<String> deleteBgcComponentById(@RequestParam(value = "id", required = true) Long componentId,
				HttpServletRequest request){
			Response<String> response = new Response<String>();
			
			try {
				User user =  (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				if(componentId != null &&  user != null) {
					bgcCommonService.deleteBgcComponentById(componentId);
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
			 logger.debug("after Deleted Bgc Component Record !!!!!!!");
			return response;
		}
	// below method is for saving BGC Template details by ORG LOCATION id by SRINIVAS SABBU.
	@RequestMapping(value = { "/SaveOrUpdateBgcTemplate"}, method = RequestMethod.POST)
	public  @ResponseBody Response<String>  saveOrUpdateBgcTemplate(BgcTemplateDTO bgcTemplateDTO,
			HttpServletRequest request){
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (user != null) {
			try {
				if (Validator.validate(bgcTemplateDTO)) {
					bgcCommonService.saveOrUpdateBgcTemplateByOrgLocationId(bgcTemplateDTO, user , orgLocations);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} catch (Exception e) {
				logger.error("Exception occured while saving Template Task Details" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	/*// below method is used to get the BGC Template details by UUID by SRINIVAS SABBU.
	@RequestMapping(value = { "/getBgcTemplateDetails"})
	public @ResponseBody BgcTemplateDTO getBgcTemplateDetailsByUuid(@RequestParam(value = "uuid", required = true) String uuid,
			HttpServletRequest request){
		BgcTemplateDTO bgcTemplateDTO = null;
		try {
			if(StringUtils.isNotBlank(uuid)) {
				bgcTemplateDTO = bgcCommonService.getBgcTemplateDetailsByUuid(uuid);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bgcTemplateDTO;
	}*/
	
	//editBgcTemplate
		@RequestMapping(value = { "/editBgcTemplate" })
		public ModelAndView editBgcTemplate(
				@RequestParam(value="uuid", required=true) String uuid,
				HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			BgcTemplateDTO bgcTemplateDTO = null;
			Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			try {
				if(StringUtils.isNotBlank(uuid)) {
					bgcTemplateDTO = bgcCommonService.getBgcTemplateDetailsByUuid(uuid);
					if(bgcTemplateDTO != null){
						model.addObject("bgcTemplateDto", bgcTemplateDTO);
					}
					
					List<BgcStatus> statusList = bgcCommonService.getBgcUniqueStatusListByOrgLocationId(orgLocationId);
					model.addObject("statusList",statusList);

					List<BgcComponent> componentList = bgcCommonService.getBgcUniqueComponentListByOrgLocationId(orgLocationId);
					model.addObject("componentList",componentList);
			}	
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			SessionMaintainanceData sessionData = new SessionMaintainanceData();
			sessionData.setNav(Constants.COMPLETED);
			      
				  model.addObject("sourceleadUrl", sourceleadUrl);
				  model.setViewName("/background/editTemplate");
			return model;
		}
}
