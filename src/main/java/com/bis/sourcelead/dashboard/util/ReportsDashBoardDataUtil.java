package com.bis.sourcelead.dashboard.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dashboard.dto.CommonDataDto;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.reportsDTO.ProjectReportDTO;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectsService;

@Component
public class ReportsDashBoardDataUtil {
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	ProjectAssignTasksService projectAssignTasksService;
	
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

	@SuppressWarnings("unchecked")
	public String getEmployeeTasks(CommonDataDto commonDataDto) {
		JSONArray JSONarray = new JSONArray();
		try {
			List<ProjectassignTasksDTO> projectAssignTaskList = projectAssignTasksService.getProjectAssignTaskListByOrglocationId(commonDataDto.getOrgLocation().getId());
		JSONObject jsonobj = null;
			for(ProjectassignTasksDTO abc : projectAssignTaskList) {
				jsonobj = new JSONObject();
			jsonobj.put( "Name", abc.getFirstName());
			Map<BigInteger, List<ProjectassignTasksDTO>> canMap = projectAssignTaskList.stream().collect(Collectors.groupingBy(ProjectassignTasksDTO::getCandidateId,Collectors.toList()));
			  List<ProjectassignTasksDTO> mp = new ArrayList<>();
				  for (Map.Entry<BigInteger, List<ProjectassignTasksDTO>> entry : canMap.entrySet())  {
					  ProjectassignTasksDTO dty = new ProjectassignTasksDTO();
					  Map<String,String> innerMap = new HashMap<>();
					  ProjectassignTasksDTO dto = entry.getValue().get(0);
					  dty.setFirstName(dto.getFirstName());
					  dty.setLastName(dto.getLastName());
					  dty.setFileUrl(dto.getFileUrl());
					  dty.setMemberId(dto.getMemberId());
					  dty.setCandidateId(dto.getCandidateId());
			Map<String, List<ProjectassignTasksDTO>> map = projectAssignTaskList.stream().collect(Collectors.groupingBy(ProjectassignTasksDTO::getTaskStatus,Collectors.toList()));
			for (Map.Entry<String, List<ProjectassignTasksDTO>> canEntry : map.entrySet())  {
			List<ProjectassignTasksDTO> s = entry.getValue().stream().filter(pd->canEntry.getKey().equalsIgnoreCase(pd.getTaskStatus())).collect(Collectors.toList());
			  jsonobj.put(canEntry.getKey(), String.valueOf(s.size()));
			  }
			  JSONarray.add(jsonobj);
				  }
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return JSONarray.toString();
	}
}
