package com.bis.sourcelead.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ClientRestController {

	private static final Logger logger = LoggerFactory.getLogger(ClientRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private OrgService orgService;

	@Autowired
	private OrgLocationService orgLocationService;

	@Autowired
	private OrgRelationsService orgRelationsService;

	@Autowired
	private ProjectsService projectsService;

	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	ProjectMembersService projectMembersService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	ClientService clientService;
	
	/**
	 * Handles client as well as project save
	 * 
	 * @param json
	 * @param request
	 * @return ResponseBody
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClient", method = RequestMethod.POST)
	public @ResponseBody String saveClient(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		json = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json);

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		clientService.saveClient(jsonObj, user, orgLocation);

		SourceLeadSessionManager.setSessionAttribute(request, "clientCreateMsg",
				"Successfully you have created a client");

		return "clientDashboard";
	}

	
	

	/**
	 * Returns Date
	 * 
	 * @param startDateString
	 * @return
	 */
	private Date getDateFormat(String startDateString) {

		Date formatedDate = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);
			formatedDate = df.parse(startDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatedDate;
	}

	/**
	 * validate duplicate projects by project sequence number
	 * 
	 * @param ProjectSequeneNumber
	 * @param request
	 * @return boolean
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateProjectSequeneNumberId", method = RequestMethod.POST)
	public @ResponseBody Boolean validateProjectSequeneNumberById(
			@RequestParam(value = "ProjectSequeneNumber", required = false) String ProjectSequeneNumber,
			HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Boolean flag = projectsService.validateProjectSequeneNumber(ProjectSequeneNumber, orgLocations.getId());

		return flag;
	}

	/**
	 * Handles client Details by OrgRelationId
	 * 
	 * @param orgLocationId
	 * @param request
	 * @return JSON
	 * @throws Exception
	 */

	@RequestMapping(value = "/getClientDeatilsById", method = RequestMethod.POST)
	public @ResponseBody String getProjectById(@RequestParam(value = "clientId", required = false) String orgLocationId,
			HttpServletRequest request) throws Exception {
		String resultJson = "";
		OrgRelations orgRelation = orgRelationsService.getClientByOrgLocationId(Long.parseLong(orgLocationId));
		Long projectCount = orgRelationsService.getNumberOfProjectsPerClient(orgRelation.getId());
		orgRelation.setProjectsCont(projectCount);
		resultJson = commonUtil.toJSON(orgRelation);
		return resultJson;
	}

	/**
	 * Read total number of projects
	 * 
	 * @param orgRelationId
	 * @param locationId
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/getAllClientProjectsByOrgRelationId", method = RequestMethod.POST)
	public @ResponseBody String getAllClientProjectsByOrgRelationId(
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,
			@RequestParam(value = "locationId", required = false) Long locationId, HttpServletRequest request)
			throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Collection<Projects> clientProjectsList = projectsService.getAllClientProjectsByOrgRelationId(orgRelationId,
				orgLocations.getId());
		ArrayList<Projects> clientProjectsArrList = new ArrayList<Projects>();

		for (Projects project : clientProjectsList) {
			project.setProjectName(project.getProjectName());

			if (project.getStartDate() != null && !isNullOrEmpty(project.getStartDate().toString())) {
				project.setStartDateStr(Constants.DATEFORMAT.format(project.getStartDate()));
			}
			if (project.getEndDate() != null && !isNullOrEmpty(project.getEndDate().toString())) {
				project.setEndDateStr(Constants.DATEFORMAT.format(project.getEndDate()));
			}

			// get team members count(assigned employees for projects)
			if (project.getOrgRelationId().getId() != null) {
				Integer assignedEmpSize = projectMembersService.getEmployeeAssignedProjectsCount(
						project.getOrgRelationId().getId(), orgLocations.getId(), project.getId());
				project.setAssignedEmpCount(assignedEmpSize);
			}
			if (project.getProjectStatus() != null) {
				project.setProjectStatus(project.getProjectStatus());
			}

			
			 * Add managers to project list(shows in select box) in client tab
			 
			Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService
					.getApproverUsersByProjectId(project.getId());
			String[] approvers = new String[projectApproverHierarchies.size()];
			int i = 0;
			for (ProjectApproverHierarchy php : projectApproverHierarchies) {
				approvers[i] = php.getApproverUserId().getFirstName() + "&" + php.getApproverUserId().getId();
				i++;
				if (php.getLevel() == 1) {
					project.setRelationId(php.getApproverUserId().getId());
				}
			}
			project.setProjectManagerName(approvers);

		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(clientProjectsList);

		return jsonInString;
	}*/

	/**
	 * Handles project inline editing
	 * @param orgRelationId
	 * @param fieldName
	 * @param fieldValue
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	@RequestMapping(value = "/clientDetailsUpdateUsingId", method = RequestMethod.POST)
	public String clientDetailsUpdateUsingId(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId, 
			HttpServletRequest request) throws Exception {
		
	       json = "{" + json + "}";
	       JSONObject jsonObj = new JSONObject(json);
	
	       clientService.updateClientDetails(jsonObj, orgRelationId);

		return "success";
	}

	
	
	
	
	

	/**
	 * Handles project inline editing
	 * 
	 * @param orgRelationId
	 * @param fieldName
	 * @param fieldValue
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/updateClientProjectById", method = RequestMethod.POST)
	public String updateClientProjectById(@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,
			@RequestParam(value = "fieldName", required = false) String fieldName,
			@RequestParam(value = "fieldValue", required = false) String fieldValue, HttpServletRequest request)
			throws Exception {
		OrgRelations orgRelation = orgRelationsService.getClientByOrgLocationId(orgRelationId);
		Org org = orgRelation.getClientLocationId().getOrg();
		if ("clientName".equalsIgnoreCase(fieldName)) {
			org.setOrgName(fieldValue);
			orgService.updateOrg(org);
		} else if ("clientWebsite".equalsIgnoreCase(fieldName)) {
			org.setWebSite(fieldValue);
			orgService.updateOrg(org);
		} else if ("clientCity".equalsIgnoreCase(fieldName)) {
			OrgLocations orgLocations = orgRelation.getClientLocationId();
			orgLocations.setCity(fieldValue);
			orgLocationService.updateOrgLocations(orgLocations);
		} else if ("clientSate".equalsIgnoreCase(fieldName)) {
			OrgLocations orgLocations = orgRelation.getClientLocationId();
			orgLocations.setState(fieldValue);
			orgLocationService.updateOrgLocations(orgLocations);
		} else if ("clientCountry".equalsIgnoreCase(fieldName)) {
			OrgLocations orgLocations = orgRelation.getClientLocationId();
			orgLocations.setCountry(fieldValue);
			orgLocationService.updateOrgLocations(orgLocations);
		} else if ("clientEmail".equalsIgnoreCase(fieldName)) {
			OrgLocations orgLocations = orgRelation.getClientLocationId();
			orgLocations.setEmail(fieldValue);
			orgLocationService.updateOrgLocations(orgLocations);
		} else {
			OrgLocations orgLocations = orgRelation.getClientLocationId();
			orgLocations.setPhone(fieldValue);
			orgLocationService.updateOrgLocations(orgLocations);
		}

		return "success";
	}*/


	/**
	 * Handles Inline edit for client projects
	 * 
	 * @param projectId
	 * @param fieldName
	 * @param fieldValue
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/inlineEditClientProjects", method = RequestMethod.POST)
	public String inlineEditClientProjects(@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "fieldName", required = false) String fieldName,
			@RequestParam(value = "fieldValue", required = false) String fieldValue, HttpServletRequest request)
			throws Exception {

		Projects projects = projectsService.getProjectById(projectId);
		Set<ProjectApproverHierarchy> pahSet = new HashSet<ProjectApproverHierarchy>();
		logger.info("Project name before Edit " + projects.getProjectName());
		if ("projectName".equalsIgnoreCase(fieldName)) {
			projects.setProjectName(fieldValue);
		} else if ("endDate".equalsIgnoreCase(fieldName)) {
			if (fieldValue != null && !"".equalsIgnoreCase(fieldValue)) {
				Date endDate = getDateFormat(fieldValue);
				projects.setEndDate(endDate);
			}
		} else if ("clientId".equalsIgnoreCase(fieldName)) {
			Long l1 = Long.parseLong(fieldValue);
			Collection<ProjectApproverHierarchy> pah = projectApproverHierarchyService
					.getApproverUsersByProjectId(projectId);
			for (ProjectApproverHierarchy p1 : pah) {
				if (l1 == p1.getApproverUserId().getId()) {
					if (p1.getLevel() == 1) {
						p1.setLevel(2);
					} else {
						p1.setLevel(1);
					}
				} else {
					if (p1.getLevel() == 1) {
						p1.setLevel(2);
					} else {
						p1.setLevel(1);
					}
				}
				pahSet.add(p1);
			}
			projects.setProjectApprovers(pahSet);
		} else {
			projects.setProjectStatus(Project_Status.getNameByValue(fieldValue));
		}
		projectsService.updateProject(projects);
		return fieldValue;

	}*/

	/**
	 * Handles ClientName duplicate or not
	 * 
	 * @param clientName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ws/isClientNameValid", method = RequestMethod.GET)
	public @ResponseBody boolean isClientNameValidated(
			@RequestParam(value = "clientName", required = false) String clientName,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return orgService.isClientNameValid(clientName,orgLocation.getId());
	}
/**
 * Verifies Client Email
 * @param EmailId
 * @return
 * @throws Exception
 */
	@RequestMapping(value = "/getClientEmailVerification", method = RequestMethod.POST)
	public @ResponseBody String validateClientEmail(@RequestParam(value = "EmailId", required = false) String EmailId)
			throws Exception {

		String result = null;
		OrgLocations orgLocations = orgLocationService.findByEmail(EmailId);

		if (orgLocations != null) {
			result = "true";
		} else {
			result = "false";
		}
		return result;
	}

	/**
	 * check given string empty or null
	 * @param str
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

}