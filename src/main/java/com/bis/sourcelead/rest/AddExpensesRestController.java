package com.bis.sourcelead.rest;

import org.h2.schema.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.CommonUtil;

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
	
	@RequestMapping(value = "/getClientByProjectId", method = RequestMethod.GET)
	public @ResponseBody String storeAssociate(@RequestParam(value = "projectId", required = false) String projectId) throws Exception {
		if(null != projectId && !"".equalsIgnoreCase(projectId)){
		Projects project = projectsService.getProjectById(Long.parseLong(projectId));
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
		return projectId;
}
}
