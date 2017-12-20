package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.service.RequirementService;

@Component
public class ReportsUtil {

	@Autowired
	RequirementService requirementService;
	
	public List<RequirementDTO> getAllRequirementsByOrgLocationId(Long orgLocationId){
		
		List<RequirementDTO> requirementDTOList = new ArrayList<>();
		try {
			List<Requirement> requirementList = requirementService.getAllRequirementsByOrglocationId(orgLocationId);
			if(requirementList != null && requirementList.size() > 0) {
				for(Requirement requirement : requirementList) {
					RequirementDTO requirementDTO = new RequirementDTO();
					requirementDTO.setRequirementId(String.valueOf(requirement.getId()));
					requirementDTO.setJobTitle(requirement.getJobTitle());
					requirementDTOList.add(requirementDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return requirementDTOList;
	}
}
