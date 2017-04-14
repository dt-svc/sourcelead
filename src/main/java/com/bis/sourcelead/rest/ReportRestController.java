package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.elk.index.helper.ClientVendorContactsIndexHelper;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class ReportRestController {

	private static final Logger logger = LoggerFactory.getLogger(ReportRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	EmployeesService employeesService;

	@Autowired
	UserService userService;

	@Autowired
	ProjectsService projectsService;

	@Autowired
	CommonUtil commonUtil;



	@Autowired
	OrgSpocService orgSpocService;

	@Autowired
	RequirementService requirementService;

	@Autowired
	TeamService teamService;

	@Autowired
	RequirementApproverHierarchyService requirementApproverHierarchyService;
    
	@Autowired
	CandidateService candidateService;

	@Autowired
	RolesService rolesService;

	@Autowired
	TabsService tabsService;

	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	ClientVendorContactsIndexHelper clientVendorContactsIndexHelper;
	
	/**
	 * To get All the Users by filtering
	 * 
	 * @param request
	 * @param userRole
	 * @param searchByName
	 * @param userState
	 * @param loginUserRole
	 * @return
	 */

	@RequestMapping(value = "/getAllUsersByOrgLocationsId")
	public @ResponseBody String getAllUsersByOrgLocationsId(
			HttpServletRequest request) throws Exception {

	    String resultJson;
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		List<User> usersList = userService.getUsersListByOrglocationId(orgLocations.getId());
		List<User> orgUsersList = new ArrayList<User>(); 
		StringBuilder userName = new StringBuilder();
		if(null != usersList){
			for(User user : usersList){
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				user.setUserState(userRole.getUserState());
				user.setRole(userRole.getRoles().getDisplayName());
				if(!isNullOrEmpty(userRole.getUser().getLastName())){
					userName = userName.append(userRole.getUser().getFirstName()).append(" ").append(userRole.getUser().getLastName());
					userRole.setUserName(userName.toString());
					
				}else{
					userRole.setUserName(userRole.getUser().getFirstName());
				}
				
				long totalRequirements = requirementService.getCountOfAllRequirementsByCreatedUserAndOrglocationId(orgLocations.getId(), user, "");
				user.setUserRequirementsCount(totalRequirements);
				
				long totalCandidates = candidateService.getCountOfAllCandidatesByCreatedUserAndOrglocationId(orgLocations.getId(), user, "");
				user.setUserCandidatesCount(totalCandidates);
				
				long totalApplicants = requirementSubmissionService.getCountOfAllApplicantsByCreatedUserAndOrglocationId(orgLocations.getId(), user,"", "");
				user.setUserApplicantsCount(totalApplicants);
				
				orgUsersList.add(user);
			}
		}
	    resultJson = commonUtil.toJSON(orgUsersList);
		return resultJson;
	}
	

	private String toJSON(Object obj) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

}
