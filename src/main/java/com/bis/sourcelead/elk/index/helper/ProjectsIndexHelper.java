package com.bis.sourcelead.elk.index.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.ProjectsIndex;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.util.CommonUtil;

@Component
public class ProjectsIndexHelper {

	@Autowired
	private CommonUtil commonUtil;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	public ProjectsIndex getProjectsIndexFromProjects(Projects projects, List<ProjectApproverHierarchy> projectApproverHierarchyList, Collection<ProjectMembers> projectMembersList) throws Exception{
		
		ProjectsIndex projectsIndex = null;
		
		if(null != projects && null != projects.getId()){
			projectsIndex = new ProjectsIndex();
			
			projectsIndex.setId(projects.getId());
			if(!isNullOrEmpty(projects.getProjectSequeneNumber())){
				projectsIndex.setProjectSequeneNumber(projects.getProjectSequeneNumber());
			}
			projectsIndex.setProjectName(projects.getProjectName());
			projectsIndex.setOrgLocationId(projects.getOrgLocationsId().getId());

			if(null != projects.getStartDate()){
				projectsIndex.setStartDate(commonUtil.elkDateFormatter(projects.getStartDate()));
			}
			if(null != projects.getEndDate()){
				projectsIndex.setEndDate(commonUtil.elkDateFormatter(projects.getEndDate()));
			}
			if(!isNullOrEmpty(projects.getInvoiceFrequency())){
				projectsIndex.setInvoiceFrequency(projects.getInvoiceFrequency());
			}
			if(!isNullOrEmpty(projects.getProjectStatus().toString())){
				projectsIndex.setProjectStatus(projects.getProjectStatus().toString());
			}
			
			if(null != projects.getCreatedDate()){
				projectsIndex.setCreatedDate(commonUtil.elkDateFormatter(projects.getCreatedDate()));
			}
			
			if(null != projects.getUpdatedDate()){
				projectsIndex.setUpdatedDate(commonUtil.elkDateFormatter(projects.getUpdatedDate()));
			}
			projectsIndex.setProjectUUID(projects.getProjectUUID());
			
			if(null != projects.getOrgRelationId() && null != projects.getOrgRelationId().getLocationId() && projects.getOrgRelationId().getLocationId().getId() == projects.getOrgLocationsId().getId()){
				projectsIndex.setClientName(projects.getOrgRelationId().getResponderLocationId().getOrgLocationName());
			}else if(null != projects.getOrgRelationId() && null != projects.getOrgRelationId().getResponderLocationId() && projects.getOrgRelationId().getResponderLocationId().getId() == projects.getOrgLocationsId().getId()){
				projectsIndex.setClientName(projects.getOrgRelationId().getLocationId().getOrgLocationName());
			}
			
			if(null !=projectApproverHierarchyList && projectApproverHierarchyList.size()>0){
				for(ProjectApproverHierarchy projectApproverHierarchy : projectApproverHierarchyList){
					if(projectApproverHierarchy.getLevel() == 1){
						if(null != projectApproverHierarchy.getApproverUserId()){
							StringBuilder manager1Name = new StringBuilder();
							projectsIndex.setLevel1ManagerName(manager1Name.append(projectApproverHierarchy.getApproverUserId().getFirstName()).append(" ").append(projectApproverHierarchy.getApproverUserId().getLastName()).toString());
						}
					}
					if(projectApproverHierarchy.getLevel() == 2){
						if(null != projectApproverHierarchy.getApproverUserId()){
							StringBuilder manager2Name = new StringBuilder();
							projectsIndex.setLevel2ManagerName(manager2Name.append(projectApproverHierarchy.getApproverUserId().getFirstName()).append(" ").append(projectApproverHierarchy.getApproverUserId().getLastName()).toString());
						}
					}
				}
			}
			
			if(projectMembersList.size()>0){
				ArrayList<String> projectMembers = new ArrayList<>();
				ArrayList<String> projectMemberEmail = new ArrayList<>();
				for(ProjectMembers member : projectMembersList){
					if(null != member.getEmployee() && null != member.getEmployee().getUser()){
						if(null != member.getEmployee().getUser()){
							StringBuilder memberName = new StringBuilder();
							projectMembers.add(memberName.append(member.getEmployee().getUser().getFirstName()).append(" ").append(member.getEmployee().getUser().getLastName()).toString());
						}
						projectMemberEmail.add(member.getEmployee().getUser().getEmail());
					}
				}
				if(null != projectMembers && !projectMembers.isEmpty()){
					projectsIndex.setProjectMembers(projectMembers);
				}
				if(null != projectMemberEmail && !projectMemberEmail.isEmpty()){
					projectsIndex.setProjectMemberEmail(projectMemberEmail);
				}
			}
		}
		return projectsIndex;
		
	}

}
