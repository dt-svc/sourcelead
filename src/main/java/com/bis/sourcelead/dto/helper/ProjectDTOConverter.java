package com.bis.sourcelead.dto.helper;

import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.entity.Projects;

public class ProjectDTOConverter {

	public static ProjectsDTO getProjectsDTOFromProjects(Projects projects){
		ProjectsDTO projectsDTO = new ProjectsDTO();
		projectsDTO.setId(projects.getId());
		projectsDTO.setClient(projects.getExpensesClientName());
		projectsDTO.setProjectName(projects.getProjectName());
		return projectsDTO;
	}
}
