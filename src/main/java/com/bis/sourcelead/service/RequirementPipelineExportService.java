package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import com.bis.sourcelead.entity.RequirementSubmission;

public interface RequirementPipelineExportService {

	File getExportRequirementFile(List<RequirementSubmission> requirementStatusList, String orgName, String exportType,String requirementId, String submissionType)throws FileNotFoundException, NumberFormatException, Exception;

}
