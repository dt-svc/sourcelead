package com.bis.sourcelead.rest;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class AddExpensesRestController {
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private ProjectsService projectsService; 
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private AddExpensesService addExpensesService;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@RequestMapping(value = "/getClientByProjectMemberId", method = RequestMethod.GET)
	public @ResponseBody String getClientByProjectMemberId(@RequestParam(value = "projectMemberId", required = false) String projectMemberId) throws Exception {
		if(null != projectMemberId && !"".equalsIgnoreCase(projectMemberId)){
			ProjectMembers projectMembers =	projectMembersService.getProjectMemberById(Long.parseLong(projectMemberId));
			if(null != projectMembers.getProjects()){
				Projects project = projectMembers.getProjects();
				if(null != project.getOrgRelationId()){
					 OrgRelations orgRelation = orgRelationsService.getOrgRelationById(project.getOrgRelationId().getId());
					 project.setExpensesClientName(orgRelation.getResponderLocationId().getOrg().getOrgName());
					}
					 project.setStartDateStr(Constants.DMY.format(project.getStartDate()));
					 if(null != project.getEndDate() && !"".equalsIgnoreCase(project.getEndDate().toString())){
					 project.setEndDateStr(Constants.DMY.format(project.getEndDate()));
					 }
				 return commonUtil.toJSON(project);
			}
	    }
		return projectMemberId;
    }
	
	
	@RequestMapping(value = "/getClientByProjectId", method = RequestMethod.GET)
	public @ResponseBody String getClientByProjectId(@RequestParam(value = "projectId", required = false) String projectId) throws Exception {
		if(null != projectId && !"".equalsIgnoreCase(projectId)){
			Projects project =	projectsService.getProjectById(Long.parseLong(projectId));
			if(null != project){
				if(null != project.getOrgRelationId()){
					 OrgRelations orgRelation = orgRelationsService.getOrgRelationById(project.getOrgRelationId().getId());
					 project.setExpensesClientName(orgRelation.getResponderLocationId().getOrg().getOrgName());
					}
					 project.setStartDateStr(Constants.DMY.format(project.getStartDate()));
					 if(null != project.getEndDate() && !"".equalsIgnoreCase(project.getEndDate().toString())){
					 project.setEndDateStr(Constants.DMY.format(project.getEndDate()));
					 }
				 return commonUtil.toJSON(project);
			}
	    }
		return projectId;
    }
	
	
	@RequestMapping(value = "/storeAddExpensesData",method = RequestMethod.POST)
	public @ResponseBody String storeAddExpensesData(MultipartHttpServletRequest request,@RequestParam(value="status", required=false) String status) throws Exception {
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		JSONObject jsonObj = new JSONObject(request.getParameter("json"));
		addExpensesService.storeAddExpensesData(jsonObj,loginUser,orgLoc,request,status);
		return "DataSaved";
}
	
	 @RequestMapping(value = "/getMembersForSelectedProject",  method = RequestMethod.GET)
	 public @ResponseBody  String getMembersForSelectedProject(
			@RequestParam(value="projectId", required=false) Long projectId, HttpServletRequest request) throws Exception {
	 	
		 	Collection<ProjectMembers> projectMembers = null;
		 	if(projectId != null){
				projectMembers = projectMembersService.getAllProjectMembersByProjectId(projectId);
				
				if(null != projectMembers && projectMembers.size() > 0){
					for(ProjectMembers pm : projectMembers){
						if(null != pm.getEmployee().getUser()){
							StringBuilder eName = new StringBuilder();
							
							if(null != pm.getEmployee().getUser() && (pm.getEmployee().getUser().getFirstName() != null && pm.getEmployee().getUser().getFirstName() != "" && pm.getEmployee().getUser().getLastName() != null && pm.getEmployee().getUser().getLastName() != "" )){
								pm.setEmpName(eName.append(pm.getEmployee().getUser().getFirstName()).append(" ").append(pm.getEmployee().getUser().getLastName()).toString());
							}else{
								pm.setEmpName(eName.append(pm.getEmployee().getUser().getFirstName()).toString());
							}
							
						}
					}
				}
		 	}
		 	return commonUtil.toJSON(projectMembers);
	}
	
}
