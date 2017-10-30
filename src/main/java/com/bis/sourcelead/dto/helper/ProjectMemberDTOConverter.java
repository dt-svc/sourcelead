package com.bis.sourcelead.dto.helper;

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
			    pmDTO.setProjectStartDate(projectMembers.getProjects().getStartDateStr());
			    pmDTO.setProjectEndDate(projectMembers.getProjects().getEndDateStr());
		}
		pmDTO.setMasterCandidateId(projectMembers.getMasterCandidateId().getId());
		return pmDTO;
	}
}
