package com.bis.sourcelead.dto.helper;

import org.apache.commons.lang3.StringUtils;

import com.bis.sourcelead.dto.ProjectMembersDTO;
import com.bis.sourcelead.entity.ProjectMembers;

public class ProjectMemberDTOConverter {

	public static ProjectMembersDTO getProjectMembersDTOFromProjectMembers(ProjectMembers projectMembers){
		ProjectMembersDTO pmDTO = new ProjectMembersDTO();
		pmDTO.setId(projectMembers.getId());
		pmDTO.setName(projectMembers.getEmpName());
		if(projectMembers.getProjects() != null){
			    if(projectMembers.getProjects().getOrgRelationId() != null){
				  pmDTO.setClientName(projectMembers.getProjects().getOrgRelationId().getClientName());
			    }
			    pmDTO.setProjectName(projectMembers.getProjects().getProjectName());
			    if(projectMembers.getProjects().getStartDateStr() != null) {
			    	pmDTO.setProjectStartDate(projectMembers.getProjects().getStartDateStr());
			    }
			    if(projectMembers.getProjects().getEndDateStr() != null) {
			    	pmDTO.setProjectEndDate(projectMembers.getProjects().getEndDateStr());
			    }
			    if(projectMembers.getProjectAssignedDateStr() != null) {
			    	pmDTO.setMemberStartDate(projectMembers.getProjectAssignedDateStr());
			    }
			    if(projectMembers.getProjectEndDateStr() != null) {
			    	pmDTO.setMemberEndDate(projectMembers.getProjectEndDateStr());
			    }
			    if(StringUtils.isNotEmpty(projectMembers.getProjects().getIsProjectForTimeSheets())) {
			    	pmDTO.setIsProjectForTimeSheets(projectMembers.getProjects().getIsProjectForTimeSheets());
			    }else {
			    	pmDTO.setIsProjectForTimeSheets("N");
			    }
			    if(StringUtils.isNotEmpty(projectMembers.getProjects().getIsProjectForExpenses())) {
			    	 pmDTO.setIsProjectForExpenses(projectMembers.getProjects().getIsProjectForExpenses());
			    }else {
			    	pmDTO.setIsProjectForExpenses("N");
			    }
			   
		}
		pmDTO.setMasterCandidateId(projectMembers.getMasterCandidateId().getId());
		return pmDTO;
	} 
}
