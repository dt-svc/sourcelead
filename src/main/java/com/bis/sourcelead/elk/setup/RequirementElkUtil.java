package com.bis.sourcelead.elk.setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.index.helper.RequirementHelper;
import com.bis.sourcelead.entity.Requirement;

@Component
public class RequirementElkUtil {
	private static final Logger logger = LoggerFactory.getLogger(RequirementElkUtil.class);
	@Autowired
	private SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;
	@Autowired
	private RequirementHelper requirementHelper;
	
	public void saveElkRequirement(Requirement requirement, RequirementDTO requirementDTO) throws Exception
	{
		RequirementIndex requirementIndex = requirementHelper.populateRequirementIndex(requirement, requirementDTO);
		sourceleadELKBaseIndexer.saveOrUpdateElkDocument(requirementIndex, Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE, requirement.getId().toString());
	}
}
