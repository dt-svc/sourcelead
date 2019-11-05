package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.SourceleadTags;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.TagEntites;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.CandidateGlobalService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * @author Laxman
 */

@RestController
public class SourceLeadTagsRestController {
private static final Logger logger = LoggerFactory.getLogger(SourceLeadTagsRestController.class);
	

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
    @Autowired
    SourceleadTagsService sourceleadTagsService;
    
    @Autowired
	private UserService userService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private RequirementElkUtil requirementElkUtil;
	
	@Autowired
    private ProjectsService projectsService;
	
	@Autowired
	private ProjectTasksService projectTasksService;
	
	@Autowired
	private AssetService assetService;
     
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private ContractsTemplateService contractsTemplateService;
	
	@Autowired
	private CandidateService candidateService; 
	
	@Autowired
	private CandidateGlobalService candidateGlobalService;
	
	
    /**
     * @author Laxman
     * @param orgLocationId
     * @param tagName
     * @return success
     * @throws Exception
     */
    @RequestMapping(value = "/deleteTag", method = RequestMethod.POST)
	public @ResponseBody  String deleteTag(@RequestParam(value="orgLocationId", required=false) Long orgLocationId,
			@RequestParam(value="tagName", required=false) String tagName,
			@RequestParam(value="entityType", required=false) String entityType) throws Exception
	{
    	sourceleadTagsService.deleteTag(orgLocationId,tagName,entityType); 
		return "success";
	}
    
	@RequestMapping(value = "/getTagsListByEntitype", method = RequestMethod.POST)
   	public @ResponseBody Response<Map<String, Long>>  getTagsListByEntitype(@RequestParam(value="orgLocationId", required=false) Long orgLocationId,
   			@RequestParam(value="entitype", required=false) String entityType) throws Exception
   	{
    	Response<Map<String, Long>> response = new Response<>();
    	List<SourceleadTags> tagsList = sourceleadTagsService.getAllSourceleadTagsListByEntitypeAndOrgloactionId(entityType, orgLocationId);
    	if(tagsList.size() > 0) {
    		Map<String, Long> counting = tagsList.stream().collect(Collectors.groupingBy(SourceleadTags::getTagName, Collectors.counting()));
    		Map<String, Long> treeMap = new TreeMap<String, Long>(counting);
    		response.setObject(treeMap);
            response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
    	}else {
    		response.setResponse(Result.DATA_NOT_FOUND.name());
			response.setResponseMessage(Result.DATA_NOT_FOUND.getValue());
    	}
    	return response;
   	}
    
    
    
    
	/**
	 * @author Laxman
	 * @param loginUserId
	 * @param requirementId
	 * @param tagName
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdateTagsByJobId")
	public Response<String> saveOrUpdateTagsByJobId(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "requirementId", required = false) long requirementId,
			@RequestParam(value = "tagName", required = false) String tagName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");

		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				 Requirement req = requirementService.getRequirementByRequirementId(requirementId);
					Set<String> jobsTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(orgLocations.getId(), TagEntites.JOBS.getValue(), req.getId());
					ArrayList<String> newTags = new ArrayList<>(Arrays.asList(tagName.split("\\s*,\\s*")));
					ArrayList<String> actualNewTags = new ArrayList<>();
					actualNewTags.addAll(newTags);
				    Set<String> newTagsSet = new HashSet<String>(actualNewTags);
				    newTagsSet.removeAll(jobsTagsList);
				    jobsTagsList.removeAll(actualNewTags);
				    req.setJobsTags(tagName);
				    
				    
				    if(newTagsSet != null && !newTagsSet.isEmpty() && newTagsSet.size() > 0) {
				    	for(String tag : newTagsSet) {
				    		if(StringUtils.isNotBlank(tag)) {
				    			SourceleadTags tags = new SourceleadTags();
					    		tags.setEntityType(TagEntites.JOBS);
					    		tags.setEntityId(req.getId());
					    		tags.setTagName(tag);
					    		tags.setOrgLocationId(orgLocations);
					    		tags.setStatus(Constants.CANDIDATE_ACTIVE);
					    		tags.setCreatedBy(user);
					    		tags.setCreatedDate(new Date());
					    		sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    	
				    }
				    
				    if(jobsTagsList != null && !jobsTagsList.isEmpty() && jobsTagsList.size() > 0) {
				    	for(String removedTags : jobsTagsList) {
				    		SourceleadTags tags = sourceleadTagsService.getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(orgLocations.getId(), TagEntites.JOBS.getValue(), req.getId(),removedTags);
				    		if(tags != null) {
				    			tags.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				    			tags.setUpdatedBy(user);
				    			tags.setUpdatedDate(new Date());
				    			sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    	
				    }
				 requirementElkUtil.saveElkRequirement(req, null);
			    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(orgLocations.getId());
			    SourceLeadSessionManager.removeSessionAttribute(request, "tagsList");
			    SourceLeadSessionManager.setSessionAttribute(request, "tagsList", tagsList);
				response.setResponse(Result.SUCCESS.name());

			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	
	/**
	 * @author Laxman
	 * @param requirementId
	 * @param request
	 * @param response
	 * @return requirement
	 * @throws Exception
	 */
	@RequestMapping(value = "/getJobsTags", method = RequestMethod.POST)
	public Requirement getJobsTags(@RequestParam(value = "requirementId", required = false) Long requirementId , HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 Requirement req = requirementService.getRequirementByRequirementId(requirementId);
	    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
	    request.getSession().setAttribute("tagsList", tagsList);
	    req.setTagsList(tagsList);
		Set<String> jobsTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.JOBS.getValue(), req.getId());
		if(jobsTagsList != null && !jobsTagsList.isEmpty()) {
			StringBuilder jobsTagsStr = new StringBuilder();
			for(String tags : jobsTagsList) {
				jobsTagsStr = jobsTagsStr.append(tags).append(",");
			}
			jobsTagsStr.toString();
			req.setJobsTags(jobsTagsStr.substring(0,jobsTagsStr.length()-1));
		}
		return req;

	}
	
	/**
	 * @author Laxman
	 * @param projectId
	 * @param request
	 * @param response
	 * @return project
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProjectsTags", method = RequestMethod.POST)
	public Projects getProjectsTags(@RequestParam(value = "projectId", required = false) Long projectId , HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 Projects project = projectsService.getProjectById(projectId);
		 System.out.println("projectId : "+projectId);
	    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
	    request.getSession().setAttribute("tagsList", tagsList);
	    project.setTagsList(tagsList);
		Set<String> projectTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.PROJECTS.getValue(), project.getId());
		if(projectTagsList != null && !projectTagsList.isEmpty()) {
			StringBuilder projectTagsStr = new StringBuilder();
			for(String tags : projectTagsList) {
				projectTagsStr = projectTagsStr.append(tags).append(",");
			}
			projectTagsStr.toString();
			project.setProjectsTags(projectTagsStr.substring(0,projectTagsStr.length()-1));
		}
		return project;

	}
	
	/**
	 * @author Laxman
	 * @param loginUserId
	 * @param projectId
	 * @param tagName
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdateTagsByProjectId")
	public Response<String> saveOrUpdateTagsByProjectId(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "projectId", required = false) long projectId,
			@RequestParam(value = "tagName", required = false) String tagName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");

		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				Projects project = projectsService.getProjectById(projectId);
					Set<String> projectTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(orgLocations.getId(), TagEntites.PROJECTS.getValue(), project.getId());
					ArrayList<String> newTags = new ArrayList<>(Arrays.asList(tagName.split("\\s*,\\s*")));
					ArrayList<String> actualNewTags = new ArrayList<>();
					actualNewTags.addAll(newTags);
				    Set<String> newTagsSet = new HashSet<String>(actualNewTags);
				    newTagsSet.removeAll(projectTagsList);
				    projectTagsList.removeAll(actualNewTags);
				    project.setProjectsTags(tagName);
				    
				    if(newTagsSet != null && !newTagsSet.isEmpty() && newTagsSet.size() > 0) {
				    	for(String tag : newTagsSet) {
				    		if(StringUtils.isNotBlank(tag)) {
				    			SourceleadTags tags = new SourceleadTags();
					    		tags.setEntityType(TagEntites.PROJECTS);
					    		tags.setEntityId(project.getId());
					    		tags.setTagName(tag);
					    		tags.setOrgLocationId(orgLocations);
					    		tags.setStatus(Constants.CANDIDATE_ACTIVE);
					    		tags.setCreatedBy(user);
					    		tags.setCreatedDate(new Date());
					    		sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
				    
				    if(projectTagsList != null && !projectTagsList.isEmpty() && projectTagsList.size() > 0) {
				    	for(String removedTags : projectTagsList) {
				    		SourceleadTags tags = sourceleadTagsService.getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(orgLocations.getId(), TagEntites.PROJECTS.getValue(), project.getId(),removedTags);
				    		if(tags != null) {
				    			tags.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				    			tags.setUpdatedBy(user);
				    			tags.setUpdatedDate(new Date());
				    			sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
			    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(orgLocations.getId());
			    SourceLeadSessionManager.removeSessionAttribute(request, "tagsList");
			    SourceLeadSessionManager.setSessionAttribute(request, "tagsList", tagsList);
				response.setResponse(Result.SUCCESS.name());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	
	@RequestMapping(value = "/getTaskTags", method = RequestMethod.POST)
	public ProjectTasks getProjectTaskIdTags(@RequestParam(value = "projectTaskId", required = false) Long projectTaskId , HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 ProjectTasks pt = projectTasksService.getTasksId(String.valueOf(projectTaskId));
			    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
			    request.getSession().setAttribute("tagsList", tagsList);
			    pt.setTagsList(tagsList);
				Set<String> tasksTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.TASKS.getValue(), pt.getId());
				if(tasksTagsList != null && !tasksTagsList.isEmpty()) {
					StringBuilder tasksTagsStr = new StringBuilder();
					for(String tags : tasksTagsList) {
						tasksTagsStr = tasksTagsStr.append(tags).append(",");
					}
					tasksTagsStr.toString();
					pt.setTaskTags(tasksTagsStr.substring(0,tasksTagsStr.length()-1));
				} 
		return pt;

	}
	
	@RequestMapping(value = "/saveOrUpdateTagsByProjectTaskId")
	public Response<String> saveOrUpdateTagsByProjectTaskId(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "projectTaskId", required = false) long projectTaskId,
			@RequestParam(value = "tagName", required = false) String tagName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				ProjectTasks pt = projectTasksService.getTasksId(String.valueOf(projectTaskId));
					Set<String> taskTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(orgLocations.getId(), TagEntites.TASKS.getValue(), pt.getId());
					ArrayList<String> newTags = new ArrayList<>(Arrays.asList(tagName.split("\\s*,\\s*")));
					ArrayList<String> actualNewTags = new ArrayList<>();
					actualNewTags.addAll(newTags);
				    Set<String> newTagsSet = new HashSet<String>(actualNewTags);
				    newTagsSet.removeAll(taskTagsList);
				    taskTagsList.removeAll(actualNewTags);
				    pt.setTaskTags(tagName);
				    
				    if(newTagsSet != null && !newTagsSet.isEmpty() && newTagsSet.size() > 0) {
				    	for(String tag : newTagsSet) {
				    		if(StringUtils.isNotBlank(tag)) {
				    			SourceleadTags tags = new SourceleadTags();
					    		tags.setEntityType(TagEntites.TASKS);
					    		tags.setEntityId(pt.getId());
					    		tags.setTagName(tag);
					    		tags.setOrgLocationId(orgLocations);
					    		tags.setStatus(Constants.CANDIDATE_ACTIVE);
					    		tags.setCreatedBy(user);
					    		tags.setCreatedDate(new Date());
					    		sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
				    
				    if(taskTagsList != null && !taskTagsList.isEmpty() && taskTagsList.size() > 0) {
				    	for(String removedTags : taskTagsList) {
				    		SourceleadTags tags = sourceleadTagsService.getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(orgLocations.getId(), TagEntites.TASKS.getValue(), pt.getId(),removedTags);
				    		if(tags != null) {
				    			tags.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				    			tags.setUpdatedBy(user);
				    			tags.setUpdatedDate(new Date());
				    			sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
			    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(orgLocations.getId());
			    SourceLeadSessionManager.removeSessionAttribute(request, "tagsList");
			    SourceLeadSessionManager.setSessionAttribute(request, "tagsList", tagsList);
				response.setResponse(Result.SUCCESS.name());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	/**
	 * @author Laxman
	 * @param assetId
	 * @param request
	 * @param response
	 * @return asset
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getAssetTags", method = RequestMethod.POST)
	public Asset getAssetTags(@RequestParam(value = "assetId", required = false) Long assetId , HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 Asset asset =  assetService.getAssetById(assetId);
		    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
		    request.getSession().setAttribute("tagsList", tagsList);
		    asset.setTagsList(tagsList);
			Set<String> assetTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.ASSETMANAGEMENT.getValue(), asset.getId());
			if(assetTagsList != null && !assetTagsList.isEmpty()) {
				StringBuilder assetTagsStr = new StringBuilder();
				for(String tags : assetTagsList) {
					assetTagsStr = assetTagsStr.append(tags).append(",");
				}
				assetTagsStr.toString();
				asset.setAssetTags(assetTagsStr.substring(0,assetTagsStr.length()-1));
			} 
		return asset;

	}
	
	
	
	/**
	 * @author Laxman
	 * @param loginUserId
	 * @param assetId
	 * @param tagName
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/saveOrUpdateTagsByAssetId")
	public Response<String> saveOrUpdateTagsByAssetId(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "assetId", required = false) long assetId,
			@RequestParam(value = "tagName", required = false) String tagName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				Asset asset =  assetService.getAssetById(assetId);
					Set<String> assetTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(orgLocations.getId(), TagEntites.ASSETMANAGEMENT.getValue(), asset.getId());
					ArrayList<String> newTags = new ArrayList<>(Arrays.asList(tagName.split("\\s*,\\s*")));
					ArrayList<String> actualNewTags = new ArrayList<>();
					actualNewTags.addAll(newTags);
				    Set<String> newTagsSet = new HashSet<String>(actualNewTags);
				    newTagsSet.removeAll(assetTagsList);
				    assetTagsList.removeAll(actualNewTags);
				    asset.setAssetTags(tagName);
				    
				    if(newTagsSet != null && !newTagsSet.isEmpty() && newTagsSet.size() > 0) {
				    	for(String tag : newTagsSet) {
				    		if(StringUtils.isNotBlank(tag)) {
				    			SourceleadTags tags = new SourceleadTags();
					    		tags.setEntityType(TagEntites.ASSETMANAGEMENT);
					    		tags.setEntityId(asset.getId());
					    		tags.setTagName(tag);
					    		tags.setOrgLocationId(orgLocations);
					    		tags.setStatus(Constants.CANDIDATE_ACTIVE);
					    		tags.setCreatedBy(user);
					    		tags.setCreatedDate(new Date());
					    		sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
				    
				    if(assetTagsList != null && !assetTagsList.isEmpty() && assetTagsList.size() > 0) {
				    	for(String removedTags : assetTagsList) {
				    		SourceleadTags tags = sourceleadTagsService.getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(orgLocations.getId(), TagEntites.ASSETMANAGEMENT.getValue(), asset.getId(),removedTags);
				    		if(tags != null) {
				    			tags.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				    			tags.setUpdatedBy(user);
				    			tags.setUpdatedDate(new Date());
				    			sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
			    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(orgLocations.getId());
			    SourceLeadSessionManager.removeSessionAttribute(request, "tagsList");
			    SourceLeadSessionManager.setSessionAttribute(request, "tagsList", tagsList);
				response.setResponse(Result.SUCCESS.name());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	
	/**
	 * @author Laxman
	 * @param invoiceUUID
	 * @param request
	 * @param response
	 * @return invoice
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getInvoiceTags", method = RequestMethod.POST)
	public Invoice getInvoiceTags(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID , HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
		    request.getSession().setAttribute("tagsList", tagsList);
		    invoice.setTagsList(tagsList);
			Set<String> invoiceTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.INVOICES.getValue(), invoice.getId());
			if(invoiceTagsList != null && !invoiceTagsList.isEmpty()) {
				StringBuilder invoiceTagsStr = new StringBuilder();
				for(String tags : invoiceTagsList) {
					invoiceTagsStr = invoiceTagsStr.append(tags).append(",");
				}
				invoiceTagsStr.toString();
				invoice.setInvoiceTags(invoiceTagsStr.substring(0,invoiceTagsStr.length()-1));
			} 
		return invoice;
	}
	
	
	/**
	 * @author Laxman
	 * @param loginUserId
	 * @param invoiceUUID
	 * @param tagName
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdateTagsByInvoiceUUID")
	public Response<String> saveOrUpdateTagsByInvoiceUUID(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID,
			@RequestParam(value = "tagName", required = false) String tagName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
					Set<String> invoiceTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(orgLocations.getId(), TagEntites.INVOICES.getValue(), invoice.getId());
					ArrayList<String> newTags = new ArrayList<>(Arrays.asList(tagName.split("\\s*,\\s*")));
					ArrayList<String> actualNewTags = new ArrayList<>();
					actualNewTags.addAll(newTags);
				    Set<String> newTagsSet = new HashSet<String>(actualNewTags);
				    newTagsSet.removeAll(invoiceTagsList);
				    invoiceTagsList.removeAll(actualNewTags);
				    invoice.setInvoiceTags(tagName);
				    
				    if(newTagsSet != null && !newTagsSet.isEmpty() && newTagsSet.size() > 0) {
				    	for(String tag : newTagsSet) {
				    		if(StringUtils.isNotBlank(tag)) {
				    			SourceleadTags tags = new SourceleadTags();
					    		tags.setEntityType(TagEntites.INVOICES);
					    		tags.setEntityId(invoice.getId());
					    		tags.setTagName(tag);
					    		tags.setOrgLocationId(orgLocations);
					    		tags.setStatus(Constants.CANDIDATE_ACTIVE);
					    		tags.setCreatedBy(user);
					    		tags.setCreatedDate(new Date());
					    		sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
				    
				    if(invoiceTagsList != null && !invoiceTagsList.isEmpty() && invoiceTagsList.size() > 0) {
				    	for(String removedTags : invoiceTagsList) {
				    		SourceleadTags tags = sourceleadTagsService.getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(orgLocations.getId(), TagEntites.INVOICES.getValue(), invoice.getId(),removedTags);
				    		if(tags != null) {
				    			tags.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				    			tags.setUpdatedBy(user);
				    			tags.setUpdatedDate(new Date());
				    			sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
			    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(orgLocations.getId());
			    SourceLeadSessionManager.removeSessionAttribute(request, "tagsList");
			    SourceLeadSessionManager.setSessionAttribute(request, "tagsList", tagsList);
				response.setResponse(Result.SUCCESS.name());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	/**
	 * @author Laxman
	 * @param invoiceUUID
	 * @param request
	 * @param response
	 * @return invoice
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBillsTags", method = RequestMethod.POST)
	public Invoice getBillsTags(@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID , HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
		    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
		    request.getSession().setAttribute("tagsList", tagsList);
		    invoice.setTagsList(tagsList);
			Set<String> billsTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.BILLS.getValue(), invoice.getId());
			if(billsTagsList != null && !billsTagsList.isEmpty()) {
				StringBuilder billsTagsStr = new StringBuilder();
				for(String tags : billsTagsList) {
					billsTagsStr = billsTagsStr.append(tags).append(",");
				}
				billsTagsStr.toString();
				invoice.setBillsTags(billsTagsStr.substring(0,billsTagsStr.length()-1));
			} 
		return invoice;
	}
	
	/**
	 * @author Laxman
	 * @param loginUserId
	 * @param invoiceUUID
	 * @param tagName
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdateTagsByBillsId")
	public Response<String> saveOrUpdateTagsByBillsId(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "invoiceUUID", required = false) String invoiceUUID,
			@RequestParam(value = "tagName", required = false) String tagName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				Invoice invoice = invoiceService.getInvoicetByInvoiceUUID(invoiceUUID);
					Set<String> billsTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(orgLocations.getId(), TagEntites.BILLS.getValue(), invoice.getId());
					ArrayList<String> newTags = new ArrayList<>(Arrays.asList(tagName.split("\\s*,\\s*")));
					ArrayList<String> actualNewTags = new ArrayList<>();
					actualNewTags.addAll(newTags);
				    Set<String> newTagsSet = new HashSet<String>(actualNewTags);
				    newTagsSet.removeAll(billsTagsList);
				    billsTagsList.removeAll(actualNewTags);
				    invoice.setInvoiceTags(tagName);
				    
				    if(newTagsSet != null && !newTagsSet.isEmpty() && newTagsSet.size() > 0) {
				    	for(String tag : newTagsSet) {
				    		if(StringUtils.isNotBlank(tag)) {
				    			SourceleadTags tags = new SourceleadTags();
					    		tags.setEntityType(TagEntites.BILLS);
					    		tags.setEntityId(invoice.getId());
					    		tags.setTagName(tag);
					    		tags.setOrgLocationId(orgLocations);
					    		tags.setStatus(Constants.CANDIDATE_ACTIVE);
					    		tags.setCreatedBy(user);
					    		tags.setCreatedDate(new Date());
					    		sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
				    
				    if(billsTagsList != null && !billsTagsList.isEmpty() && billsTagsList.size() > 0) {
				    	for(String removedTags : billsTagsList) {
				    		SourceleadTags tags = sourceleadTagsService.getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(orgLocations.getId(), TagEntites.BILLS.getValue(), invoice.getId(),removedTags);
				    		if(tags != null) {
				    			tags.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				    			tags.setUpdatedBy(user);
				    			tags.setUpdatedDate(new Date());
				    			sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
			    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(orgLocations.getId());
			    SourceLeadSessionManager.removeSessionAttribute(request, "tagsList");
			    SourceLeadSessionManager.setSessionAttribute(request, "tagsList", tagsList);
				response.setResponse(Result.SUCCESS.name());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	
	
	
	
	@RequestMapping(value = "/getContractTags", method = RequestMethod.POST)
	public Contracts getContractTags(@RequestParam(value = "contractUUID", required = false) String contractUUID , HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 Contracts contract = contractsTemplateService.getContractByContractUUID(contractUUID);
		    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
		    request.getSession().setAttribute("tagsList", tagsList);
		    contract.setTagsList(tagsList);
			Set<String> contractTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.CONTRACT.getValue(), contract.getId());
			if(contractTagsList != null && !contractTagsList.isEmpty()) {
				StringBuilder contractTagsStr = new StringBuilder();
				for(String tags : contractTagsList) {
					contractTagsStr = contractTagsStr.append(tags).append(",");
				}
				contractTagsStr.toString();
				contract.setContractTags(contractTagsStr.substring(0,contractTagsStr.length()-1));
			} 
		return contract;
	}
	
	
	@RequestMapping(value = "/saveOrUpdateTagsByContractUUID")
	public Response<String> saveOrUpdateTagsByContractUUID(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "contractUUID", required = false) String contractUUID,
			@RequestParam(value = "tagName", required = false) String tagName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				Contracts contract = contractsTemplateService.getContractByContractUUID(contractUUID);
					Set<String> contractTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(orgLocations.getId(), TagEntites.CONTRACT.getValue(), contract.getId());
					ArrayList<String> newTags = new ArrayList<>(Arrays.asList(tagName.split("\\s*,\\s*")));
					ArrayList<String> actualNewTags = new ArrayList<>();
					actualNewTags.addAll(newTags);
				    Set<String> newTagsSet = new HashSet<String>(actualNewTags);
				    newTagsSet.removeAll(contractTagsList);
				    contractTagsList.removeAll(actualNewTags);
				    contract.setContractTags(tagName);
				    
				    if(newTagsSet != null && !newTagsSet.isEmpty() && newTagsSet.size() > 0) {
				    	for(String tag : newTagsSet) {
				    		if(StringUtils.isNotBlank(tag)) {
				    			SourceleadTags tags = new SourceleadTags();
					    		tags.setEntityType(TagEntites.CONTRACT);
					    		tags.setEntityId(contract.getId());
					    		tags.setTagName(tag);
					    		tags.setOrgLocationId(orgLocations);
					    		tags.setStatus(Constants.CANDIDATE_ACTIVE);
					    		tags.setCreatedBy(user);
					    		tags.setCreatedDate(new Date());
					    		sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
				    
				    if(contractTagsList != null && !contractTagsList.isEmpty() && contractTagsList.size() > 0) {
				    	for(String removedTags : contractTagsList) {
				    		SourceleadTags tags = sourceleadTagsService.getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(orgLocations.getId(), TagEntites.CONTRACT.getValue(), contract.getId(),removedTags);
				    		if(tags != null) {
				    			tags.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				    			tags.setUpdatedBy(user);
				    			tags.setUpdatedDate(new Date());
				    			sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    }
			    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(orgLocations.getId());
			    SourceLeadSessionManager.removeSessionAttribute(request, "tagsList");
			    SourceLeadSessionManager.setSessionAttribute(request, "tagsList", tagsList);
				response.setResponse(Result.SUCCESS.name());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/getCandidatesTags", method = RequestMethod.POST)
	public Candidate getCandidatesTags(@RequestParam(value = "candidateId", required = false) Long candidateId , HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
		    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
		    request.getSession().setAttribute("tagsList", tagsList);
		    candidate.setTagsList(tagsList);
		    Set<String> candidateTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.CANDIDATES.getValue(), candidate.getId());
			if(candidateTagsList != null && !candidateTagsList.isEmpty()) {
				StringBuilder candidateTagsStr = new StringBuilder();
				for(String tags : candidateTagsList) {
					candidateTagsStr = candidateTagsStr.append(tags).append(",");
				}
				candidateTagsStr.toString();
				candidate.setCandidateTags(candidateTagsStr.substring(0,candidateTagsStr.length()-1));
			} 
		return candidate;
	}
	
	
	@RequestMapping(value = "/getCandidatesGlobalTags", method = RequestMethod.POST)
	public CandidateGlobal getCandidatesGlobalTags(@RequestParam(value = "candidateId", required = false) Long candidateId , HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 CandidateGlobal candidate = candidateGlobalService.getCandidateGlobalByCandidateId(candidateId);
		    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
		    request.getSession().setAttribute("tagsList", tagsList);
		    candidate.setTagsList(tagsList);
		    Set<String> candidateTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.CANDIDATES_GLOBAL.getValue(), candidate.getId());
			if(candidateTagsList != null && !candidateTagsList.isEmpty()) {
				StringBuilder candidateTagsStr = new StringBuilder();
				for(String tags : candidateTagsList) {
					candidateTagsStr = candidateTagsStr.append(tags).append(",");
				}
				candidateTagsStr.toString();
				candidate.setCandidateTags(candidateTagsStr.substring(0,candidateTagsStr.length()-1));
			} 
		return candidate;
	}
	
	
	
	/**
	 * @author Rohit
	 * @param loginUserId
	 * @param associateId
	 * @param tagName
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdateTagsByAssociateId")
	public Response<String> saveOrUpdateTagsBycandidateId(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "associateId", required = false) long associateId,
			@RequestParam(value = "tagName", required = false) String tagName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");

		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				User associate = userService.getUserById(associateId);
					Set<String> associateTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(orgLocations.getId(), TagEntites.ASSOCIATES.getValue(), associate.getId());
					ArrayList<String> newTags = new ArrayList<>(Arrays.asList(tagName.split("\\s*,\\s*")));
					ArrayList<String> actualNewTags = new ArrayList<>();
					actualNewTags.addAll(newTags);
				    Set<String> newTagsSet = new HashSet<String>(actualNewTags);
				    newTagsSet.removeAll(associateTagsList);
				    associateTagsList.removeAll(actualNewTags);
				    associate.setAssociateTags(tagName);
				    
				    
				    if(newTagsSet != null && !newTagsSet.isEmpty() && newTagsSet.size() > 0) {
				    	for(String tag : newTagsSet) {
				    		if(StringUtils.isNotBlank(tag)) {
				    			SourceleadTags tags = new SourceleadTags();
					    		tags.setEntityType(TagEntites.ASSOCIATES);
					    		tags.setEntityId(associate.getId());
					    		tags.setTagName(tag);
					    		tags.setOrgLocationId(orgLocations);
					    		tags.setStatus(Constants.CANDIDATE_ACTIVE);
					    		tags.setCreatedBy(user);
					    		tags.setCreatedDate(new Date());
					    		sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    	
				    }
				    
				    if(associateTagsList != null && !associateTagsList.isEmpty() && associateTagsList.size() > 0) {
				    	for(String removedTags : associateTagsList) {
				    		SourceleadTags tags = sourceleadTagsService.getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(orgLocations.getId(), TagEntites.ASSOCIATES.getValue(), associate.getId(),removedTags);
				    		if(tags != null) {
				    			tags.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				    			tags.setUpdatedBy(user);
				    			tags.setUpdatedDate(new Date());
				    			sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    	
				    }
				    
			    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(orgLocations.getId());
			    SourceLeadSessionManager.removeSessionAttribute(request, "tagsList");
			    SourceLeadSessionManager.setSessionAttribute(request, "tagsList", tagsList);
				response.setResponse(Result.SUCCESS.name());

			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	
	/**
	 * @author Rohit
	 * @param associateId
	 * @param request
	 * @param response
	 * @return user
	 * @throws Exception
	 */

	@RequestMapping(value = "/getAssociateTags", method = RequestMethod.POST)
	public User getAssociateTags(@RequestParam(value = "associateId", required = false) Long associateId , HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 User user = userService.getUserById(associateId);
		 
//	    System.out.println("associateId : "+associateId);
		Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
	    request.getSession().setAttribute("tagsList", tagsList);
	    user.setTagsList(tagsList);
		Set<String> associateTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.ASSOCIATES.getValue(), user.getId());
		if(associateTagsList != null && !associateTagsList.isEmpty()) {
			StringBuilder associateTagsStr = new StringBuilder();
			for(String tags : associateTagsList) {
				associateTagsStr = associateTagsStr.append(tags).append(",");
			}
			associateTagsStr.toString();
			user.setAssociateTags(associateTagsStr.substring(0,associateTagsStr.length()-1));
		}
		return user;

	}
	
}
