package com.bis.sourcelead.elk.index.helper;

import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.entity.Projects;

@Component
public class ProjectsIndexHelper {
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	public ProjectsIndex getProjectsIndexFromProjects(Projects projects) throws Exception{
		
		ProjectsIndex projectsIndex = null;
		
		if(null != projects && null != projects.getId()){
			projectsIndex = new ProjectsIndex();
			
			projectsIndex.setId(projects.getId());
			projectsIndex.setProjectSequeneNumber(projects.getProjectSequeneNumber());
			projectsIndex.setProjectName(projects.getProjectName());
			projectsIndex.setOrgLocationId(projects.getOrgLocationsId().getId());
			projectsIndex.setStartDate(Constants.DF_DMY.format(projects.getStartDate()));
			projectsIndex.setEndDate(Constants.DF_DMY.format(projects.getEndDate()));
			projectsIndex.setInvoiceFrequency(projects.getInvoiceFrequency());
			projectsIndex.setProjectStatus(projects.getProjectStatus().toString());
			
			
		}
		
		return projectsIndex;
		
	}

}
