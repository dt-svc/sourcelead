package com.bis.sourcelead.service;

import java.io.File;


import java.io.FileNotFoundException;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionCounts;

/**
 * 
 * @author Shiva Yogi
 * For Export Execl and Pdf
 * Date 10-2016
 *
 */
public interface RequirementPipelineExportService {

	File getExportRequirementFile(List<RequirementSubmission> requirementStatusList, OrgLocations loginOrgLocation, String exportType,String requirementId, String submissionType, RequirementSubmissionCounts requirementSubmissionCounts)throws FileNotFoundException, NumberFormatException, Exception;

	File getWorkflowExportRequirementFile(List<RequirementSubmission> requirementStatusList,
			OrgLocations loginOrgLocation, String exportType, String requirementId, Long workflowId,
			String submissionType, RequirementSubmissionCounts requirementSubmissionCounts)	throws NumberFormatException, Exception;

}
