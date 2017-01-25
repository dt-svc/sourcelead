package com.bis.sourcelead.elk.index.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;

@Component
public class ProjectsIndexHelper {
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	public ProjectsIndex getProjectsIndexFromProjects(Projects projects, List<ProjectApproverHierarchy> projectApproverHierarchyList, Collection<ProjectMembers> projectMembersList) throws Exception{
		
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
			
			if(null != projects.getOrgRelationId() && projects.getOrgRelationId().getLocationId().getId() == projects.getOrgLocationsId().getId()){
				projectsIndex.setClientName(projects.getOrgRelationId().getResponderLocationId().getOrg().getOrgName());
			}else if(null != projects.getOrgRelationId() && projects.getOrgRelationId().getResponderLocationId().getId() == projects.getOrgLocationsId().getId()){
				projectsIndex.setClientName(projects.getOrgRelationId().getLocationId().getOrg().getOrgName());
			}
			
			if(projectApproverHierarchyList.size()>0){
				for(ProjectApproverHierarchy projectApproverHierarchy : projectApproverHierarchyList){
					if(projectApproverHierarchy.getLevel() == 1){
						projectsIndex.setLevel1ManagerName(projectApproverHierarchy.getApproverUserId().getFirstName());
					}
					if(projectApproverHierarchy.getLevel() == 2){
						projectsIndex.setLevel2ManagerName(projectApproverHierarchy.getApproverUserId().getFirstName());
					}
				}
			}
			
			if(projectMembersList.size()>0){
				ArrayList<String> projectMembers = new ArrayList<>();
				for(ProjectMembers member : projectMembersList){
					projectMembers.add(member.getEmployee().getUser().getFirstName());
				}
				if(null != projectMembers && !projectMembers.isEmpty()){
					projectsIndex.setProjectMembers(projectMembers);
				}
			}
			
		}
		return projectsIndex;
		
	}

}
