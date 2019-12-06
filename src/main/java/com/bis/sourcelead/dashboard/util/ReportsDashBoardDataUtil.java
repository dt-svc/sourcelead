package com.bis.sourcelead.dashboard.util;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dashboard.dto.CommonDataDto;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.reportsDTO.ProjectReportDTO;
import com.bis.sourcelead.service.ProjectsService;

@Component
public class ReportsDashBoardDataUtil {
	@Autowired
	ProjectsService projectsService;
	
	@SuppressWarnings("unchecked")
	public String getProjectReports(CommonDataDto commonDataDto) {
		JSONArray jsonArray = new JSONArray();
		try {
			ProjectReportDTO projectReportDTO = new ProjectReportDTO();
			projectReportDTO.setRefreshFlag(true);
			projectReportDTO.setSearchValue("");
			ResponseList<ProjectReportDTO> projectReportList = projectsService.getProjectReportList(projectReportDTO,commonDataDto.getOrgLocation().getId());
			List<ProjectReportDTO> ProjectReportDtoList = projectReportList.getData();
			for(ProjectReportDTO ProjectReportDTOList : ProjectReportDtoList){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("projectMember", ProjectReportDTOList.getProjectMembers());
				jsonObj.put("projectName", ProjectReportDTOList.getProjectName());
				jsonObj.put("projectStatus", ProjectReportDTOList.getProjectStatus());
				jsonObj.put("projectManager", ProjectReportDTOList.getProjectManagerNames());
				jsonArray.add(jsonObj);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toString();
	}
}
