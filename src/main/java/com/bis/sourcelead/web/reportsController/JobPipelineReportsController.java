package com.bis.sourcelead.web.reportsController;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class JobPipelineReportsController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private TeamService teamService; 
	
	@RequestMapping(value = "/jobPipelineReports")
	public ModelAndView jobPipelineReports(HttpServletRequest request) throws Exception
	{
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 ModelAndView model = new ModelAndView();
		 List<RequirementDTO> requirementsDTOList = reportsUtil.getAllRequirementsByOrgLocationId(orgLocation.getId(), user, userRole,request);
		 model.addObject("requirementsList", requirementsDTOList);
		 
		 Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId()); 
			if(null != orgClientList && orgClientList.size() > 0){
				for(OrgRelations ocl :orgClientList){
					ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
					ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
				 }
			    model.addObject("orgClientList", orgClientList);
			}
			
		List<Team> teamsList = teamService.getTeamListByOrgLocationID(orgLocation.getId());
		model.addObject("teamsList",teamsList);	
		 
		 model.setViewName("reports/jobPipelineReports");
		 model.addObject("sourceleadUrl", sourceleadUrl);
		 return model;
	}
}
