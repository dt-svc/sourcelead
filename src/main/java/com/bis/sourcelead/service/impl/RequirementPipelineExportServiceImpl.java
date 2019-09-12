package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionCounts;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementPipelineExportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlowStages;
import com.bis.sourcelead.workflow.service.RequirementWorkFlowService;

@Service
public class RequirementPipelineExportServiceImpl implements RequirementPipelineExportService {

	@Autowired
	PdfUtil pdfUtil;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	RequirementService requirementService;
	
	@Autowired
	private RequirementLocationsService requirementLocationService;

	@Autowired
	PdfUtilPipelineHelper pdfUtilPipelineHelper;
	
	@Autowired
	OrgLocationService orgLocationService;

	@Autowired
	RequirementWorkFlowService requirementWorkFlowService;
	
	/**
	 * @author Shiva and Rakshith
	 * prepares excel and pdf and retun it as File
	 * @return File
	 * @throws NumberFormatException
	 */
	@Override
	public File getExportRequirementFile(List<RequirementSubmission> requirementStatusList, OrgLocations loginOrgLocation,
			String exportType, String requirementId, String submissionType,RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {
		File file = null;
		if (!"pdf".equalsIgnoreCase(exportType)) {
			Workbook createExcelworkbook = new XSSFWorkbook();
			//DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			
			//SimpleDateFormat  simpleDateFormat=loginOrgLocation.getLocFullFormDateFormatter();
			
			StringBuilder excelStringBuilder=new StringBuilder();
			Sheet createExcelsheet = createExcelworkbook.createSheet(excelStringBuilder.append(loginOrgLocation.getOrgLocationName()).append("_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString());
			Row writeRow = null;
			if (submissionType.equals("SUBMIT_TO_CLIENT")) {
				/*createExcelsheet = getExcelSheetWithHeaderSubmitClient(createExcelsheet, writeRow, createExcelworkbook,requirementId,requirementSubmissionCounts);
				int column = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheetSubmitClient(createExcelsheet, writeRow, requirementsub,submissionType,loginOrgLocation);
					column++;
				}*/
				
				String[] headers = null;
				String[] extraHeaders = Constants.REQUIREMENT_PIPELINE_SUBMIT_TO_CLIENT_EXCEL_HEADERS_FOR_TEMPLATE_CONFIGURED;
				int row = 0;
				int count = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					int column = 0;
					JSONArray jsonArray = new JSONArray(requirementsub.getSubmissionTemplateJsonStringValue());
					writeRow = createExcelsheet.createRow(row + 8);
					if (jsonArray != null && jsonArray.length() > 0) {
						headers = new String[jsonArray.length() + extraHeaders.length];
						String[] values = new String[jsonArray.length()];

						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							headers[i] = jsonObject.getString("name");
							values[i] = jsonObject.getString("value");
//							values[i] = !jsonObject.getString("value").isEmpty() ? jsonObject.getString("value") : "NO DATA";
						}
						
						for(String value : values) {
							writeRow.createCell(column++).setCellValue(value);
							count++;
						}
						createExcelsheet = createXlsSheetSubmitClient(createExcelsheet, writeRow, requirementsub,
								submissionType, loginOrgLocation, values.length);
					}
					row++;
				}
				for(String head : extraHeaders) {
					headers[count++] = head;
				}
				createExcelsheet = getExcelSheetWithHeaderSubmitClient(createExcelsheet, writeRow, createExcelworkbook,
						requirementId, requirementSubmissionCounts, headers);
			}else if (submissionType.equals("REQUEST_SENT")) {
				createExcelsheet = getExcelSheetWithHeaderRequestSent(createExcelsheet, writeRow, createExcelworkbook,requirementId,requirementSubmissionCounts);
				int column = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheet(createExcelsheet, writeRow, requirementsub,submissionType);
					column++;
				}
			}else if (submissionType.equals("SHORTLIST")) {
				createExcelsheet = getExcelSheetWithHeaderShortListed(createExcelsheet, writeRow, createExcelworkbook,requirementId,requirementSubmissionCounts);
				int column = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheetShortListed(createExcelsheet, writeRow, requirementsub);
					column++;
				}
			} else if (submissionType.equals("INTERVIEW")) {
				createExcelsheet = getExcelSheetWithHeaderInterview(createExcelsheet, writeRow, createExcelworkbook,
						requirementId,requirementSubmissionCounts);
				int column = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheetInterview(createExcelsheet, writeRow, requirementsub);
					column++;
				}
			} else if (submissionType.equals("OFFERMADE")) {
				createExcelsheet = getExcelSheetWithHeaderOfferMade(createExcelsheet, writeRow, createExcelworkbook,
						requirementId,requirementSubmissionCounts);
				int column = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheetOfferMade(createExcelsheet, writeRow, requirementsub);
					column++;
				}
			} else if (submissionType.equals("REJECTED")) {
				createExcelsheet = getExcelSheetWithHeaderRejected(createExcelsheet, writeRow, createExcelworkbook,
						requirementId,requirementSubmissionCounts);
				int column = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheetRejected(createExcelsheet, writeRow, requirementsub);
					column++;
				}
			}

			else {
				createExcelsheet = getExcelSheetWithHeaderApplicants(createExcelsheet, writeRow, createExcelworkbook,
						requirementId,requirementSubmissionCounts);
				int column = 0;
				/*for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					JSONArray jsonArray = new JSONArray(requirementsub.getSubmissionTemplateJsonStringValue());
					if (jsonArray != null && jsonArray.length() > 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							writeRow.createCell(column).setCellValue(!jsonObject.getString("value").isEmpty() ? jsonObject.getString("value") : "NO DATA");
							column++;
						}
					}
				}*/
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheet(createExcelsheet, writeRow, requirementsub,submissionType);
					column++;
				}
			}
			/*file = new File(orgName + "_Requirements_" + dateFormat.format(new Date()) + ".xls");*/
			StringBuilder tempFileBuilder=new StringBuilder();
			file = File.createTempFile(tempFileBuilder.append(loginOrgLocation.getOrgLocationName()).append("_Jobs_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);

			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			file = pdfUtilPipelineHelper.getRequirementPipelenePdf(requirementStatusList, loginOrgLocation, requirementId,submissionType,requirementSubmissionCounts);
		}
		return file;
	}
	
	@Override
	public File getWorkflowExportRequirementFile(List<RequirementSubmission> requirementStatusList, OrgLocations loginOrgLocation,
			String exportType, String requirementId, Long workflowId, String submissionType,RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {
		File file = null;
		
		// submissionType ="SCRNG"; // testing purpose used need to remove after testing. SRINIVA SABBU
		 HashMap<String, String> stageColumnMap = requirementWorkFlowService.getColumnsMapbyWorkflowStage(submissionType,workflowId, loginOrgLocation.getId());
		 RequirementWorkFlowStages  workflowSatge =requirementWorkFlowService.getRequirementWorkFlowStagesDetails(submissionType,workflowId, loginOrgLocation.getId());
		 
		if (!"pdf".equalsIgnoreCase(exportType)) {
			Workbook createExcelworkbook = new XSSFWorkbook();
			//DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			
			//SimpleDateFormat  simpleDateFormat=loginOrgLocation.getLocFullFormDateFormatter();
			
			StringBuilder excelStringBuilder=new StringBuilder();
			Sheet createExcelsheet = createExcelworkbook.createSheet(excelStringBuilder.append(loginOrgLocation.getOrgLocationName()).append("_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString());
			Row writeRow = null;
			// below parameter is hard coded for testing purpose .
			
				createExcelsheet = getWorkflowExcelSheetWithStageHeaderApplicants(createExcelsheet, writeRow, createExcelworkbook,
						requirementId, stageColumnMap, workflowSatge.getStageName(), requirementSubmissionCounts);
				int column = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createWorkflowXlsSheet(createExcelsheet, writeRow, requirementsub, stageColumnMap);
					column++;
				}
			StringBuilder tempFileBuilder=new StringBuilder();
			file = File.createTempFile(tempFileBuilder.append(loginOrgLocation.getOrgLocationName()).append("_Jobs_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);

			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			file = pdfUtilPipelineHelper.getRequirementWorkflowPipelenePdf(requirementStatusList, loginOrgLocation, requirementId,workflowSatge.getStageName(), stageColumnMap, requirementSubmissionCounts);
		}
		return file;
	}
	private Sheet createXlsSheetSubmitClient(Sheet createExcelsheet, Row writeRow, RequirementSubmission requirementsub,
			String submissionType,OrgLocations loginOrgLocation, int columnCount) {
		
		if(columnCount == 0) {
			if (requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())) {
				writeRow.createCell(0).setCellValue(requirementsub.getCandidateName());
			}
	
			if (requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())) {
				writeRow.createCell(1).setCellValue(requirementsub.getCandidateEmail());
			}
			if (requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())) {
				writeRow.createCell(2).setCellValue(requirementsub.getPhoneNumber());
			}
		}
		if (requirementsub.getResumeName() != null && !"".equalsIgnoreCase(requirementsub.getResumeName())) {
			writeRow.createCell(columnCount++).setCellValue(requirementsub.getResumeName());
		} else {
			writeRow.createCell(columnCount++).setCellValue("");
		}
		if (requirementsub.getCreatedDate() != null) {
			//DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			//SimpleDateFormat simpleDateFormat=loginOrgLocation.getLocFullFormDateFormatter();
			String createdDate = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(),requirementsub.getCreatedDate());
			writeRow.createCell(columnCount++).setCellValue(createdDate);
		} else {
			writeRow.createCell(columnCount++).setCellValue("");
		}
		if (requirementsub.getCandidate().getExpectedRate() != null && !"".equalsIgnoreCase(requirementsub.getCandidate().getExpectedRate())) {
			writeRow.createCell(columnCount++).setCellValue(requirementsub.getCandidate().getExpectedRate());
		} else {
			writeRow.createCell(columnCount++).setCellValue("");
		}
		if (requirementsub.getProposedSubmissionRate() != null && !"".equalsIgnoreCase(requirementsub.getProposedSubmissionRate())) {
			writeRow.createCell(columnCount++).setCellValue(requirementsub.getProposedSubmissionRate());
		} else {
			writeRow.createCell(columnCount++).setCellValue("");
		}
		if (requirementsub.getUserName() != null && !"".equalsIgnoreCase(requirementsub.getUserName())) {
			writeRow.createCell(columnCount++).setCellValue(requirementsub.getUserName());
		} else {
			writeRow.createCell(columnCount++).setCellValue("");
		}
		if (requirementsub.getScore() != null && !"".equalsIgnoreCase(requirementsub.getScore())) {
			writeRow.createCell(columnCount++).setCellValue(requirementsub.getScore());
		} else {
			writeRow.createCell(columnCount++).setCellValue("");
		}
		if (requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())) {
			writeRow.createCell(columnCount++).setCellValue(requirementsub.getWorkExperience());
		} else {
			writeRow.createCell(columnCount++).setCellValue("");
		}
		if (requirementsub.getSubmissionStatus() != null
				&& !"".equalsIgnoreCase(requirementsub.getSubmissionStatus().toString())) {
			writeRow.createCell(columnCount++).setCellValue(requirementsub.getSubmissionStatus().toString());
		} else {
			writeRow.createCell(columnCount++).setCellValue("");
		}
		
		/*String submittedon = "--";*/
		/*String submittedBy = "--";*/
		if(!submissionType.equals("REQUEST_SENT")){
		 if(requirementsub.getSubmittedOn() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedOn())){
			writeRow.createCell(columnCount++).setCellValue(requirementsub.getSubmittedOn());
		} else {
			writeRow.createCell(columnCount++).setCellValue("");
		}
		 if(requirementsub.getSubmittedBy() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedBy())){
		writeRow.createCell(columnCount++).setCellValue(requirementsub.getSubmittedBy());
		 } else {
				writeRow.createCell(columnCount++).setCellValue("");
			}
		 
		 //client contact name related code start
		 if(requirementsub.getRequirement().getId() != null){
			 if(requirementsub.getRequirement().getSpocId().getId() != null){
				 if(requirementsub.getRequirement().getSpocId().getOwnerUser().getId() != null){
					 StringBuilder spocSB = new StringBuilder();
					 if(requirementsub.getRequirement().getSpocId().getClientUser().getLastName() != null ){
						 writeRow.createCell(columnCount++).setCellValue(spocSB.append(requirementsub.getRequirement().getSpocId().getClientUser().getFirstName()).append(" ").append(requirementsub.getRequirement().getSpocId().getClientUser().getLastName()).toString());
					 } else{
						 writeRow.createCell(columnCount++).setCellValue(spocSB.append(requirementsub.getRequirement().getSpocId().getClientUser().getFirstName()).toString());
					 }
				 }
			 }
		 }
		 
		 
		 /*if(requirementsub.getRequirement().getSpocId().getInitiatorLocationId().getId().longValue() == loginOrgLocation.getId().longValue() ){
			 if(requirementsub.getRequirement().getId() != null){
				 if(requirementsub.getRequirement().getSpocId().getId() != null){
					 if(requirementsub.getRequirement().getSpocId().getOwnerUser().getId() != null){
						 StringBuilder spocSB = new StringBuilder();
						 if(requirementsub.getRequirement().getSpocId().getSpocUser().getLastName() != null ){
							 writeRow.createCell(13).setCellValue(spocSB.append(requirementsub.getRequirement().getSpocId().getSpocUser().getFirstName()).append(" ").append(requirementsub.getRequirement().getSpocId().getSpocUser().getLastName()).toString());
						 }else{
							 writeRow.createCell(13).setCellValue(spocSB.append(requirementsub.getRequirement().getSpocId().getSpocUser().getFirstName()).toString());
						 }
					 }
				 }
			 }
		 }
		  
		 if(requirementsub.getRequirement().getSpocId().getResponderLocationId().getId().longValue() == loginOrgLocation.getId().longValue()){
			 if(requirementsub.getRequirement().getId() != null){
				 if(requirementsub.getRequirement().getSpocId().getId() != null){
					 if(requirementsub.getRequirement().getSpocId().getOwnerUser().getId() != null){
						 StringBuilder spocSB1 = new StringBuilder();
						 if(requirementsub.getRequirement().getSpocId().getOwnerUser().getLastName() != null){
							 writeRow.createCell(13).setCellValue(spocSB1.append(requirementsub.getRequirement().getSpocId().getOwnerUser().getFirstName()).append(" ").append(requirementsub.getRequirement().getSpocId().getOwnerUser().getLastName()).toString()); 
						 }else{
							 writeRow.createCell(13).setCellValue(spocSB1.append(requirementsub.getRequirement().getSpocId().getOwnerUser().getFirstName()).toString());
						 }
					 }
				 }
			 }
			
				
		}*/
		 //client contact name related code end
	}
		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeaderSubmitClient(Sheet createExcelsheet, Row writeRow,
			Workbook createExcelworkbook, String requirementId,
			RequirementSubmissionCounts requirementSubmissionCounts, String[] headers) throws NumberFormatException, Exception {
		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
        prepareHeaders(writeRow, createExcelsheet, style, requirement, Constants.SUBMIT_TO_CLIENT_COUNT, requirementSubmissionCounts.getNumOfSubmitToClient());
		if(headers == null)
			headers = Constants.REQUIREMENT_PIPELINE_SUBMIT_TO_CLIENT_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 7);

		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeaderRequestSent(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			String requirementId, RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {

		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
        prepareHeaders(writeRow, createExcelsheet, style, requirement, Constants.REQUEST_SENT_COUNT, requirementSubmissionCounts.getNumOfRequestSent());
		String headers[] = Constants.REQUIREMENT_PIPELINE_REQUEST_SENT_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 7);

		return createExcelsheet;
	}

	/**
	 * @author shivayogi
	 * @param createExcelsheet
	 * @param writeRow
	 * @param requirementsub
	 * @return
	 * @throws Exception 
	 * @description: this is for rejected pipeline requirement.
	 */
	private Sheet createXlsSheetRejected(Sheet createExcelsheet, Row writeRow, RequirementSubmission requirementsub) throws Exception {
		
		OrgLocations orgLocations=orgLocationService.getOrgLocations(requirementsub.getOrgLocations().getId());
		
		// SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		
		 
		if (requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())) {
			writeRow.createCell(0).setCellValue(requirementsub.getCandidateName());
		}

		if (requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())) {
			writeRow.createCell(1).setCellValue(requirementsub.getCandidateEmail());
		}
		if (requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())) {
			writeRow.createCell(2).setCellValue(requirementsub.getPhoneNumber());
		}
		if (requirementsub.getResumeName() != null && !"".equalsIgnoreCase(requirementsub.getResumeName())) {
			writeRow.createCell(3).setCellValue(requirementsub.getResumeName());
		}
		if (requirementsub.getCreatedDate() != null) {
			//DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementsub.getCreatedDate());
			writeRow.createCell(4).setCellValue(createdDate);
		}
		if (requirementsub.getExpectedCTC() != null && !"".equalsIgnoreCase(requirementsub.getExpectedCTC())) {
			writeRow.createCell(5).setCellValue(requirementsub.getExpectedCTC());
		}
		if (requirementsub.getUserName() != null && !"".equalsIgnoreCase(requirementsub.getUserName())) {
			writeRow.createCell(6).setCellValue(requirementsub.getUserName());
		}
		if (requirementsub.getUpDatedStr() != null && !"".equalsIgnoreCase(requirementsub.getUpDatedStr())) {
			writeRow.createCell(7).setCellValue(requirementsub.getUpDatedStr());
		}
		if (requirementsub.getSubmittedBy() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedBy())) {
			writeRow.createCell(8).setCellValue(requirementsub.getSubmittedBy());
		}
		if (requirementsub.getPreviousSubmissionStatus() != null) {
			writeRow.createCell(9).setCellValue(requirementsub.getPreviousSubmissionStatus().toString());
		}
		if (requirementsub.getSubmissionStatus() != null) {
			writeRow.createCell(10).setCellValue(requirementsub.getSubmissionStatus().toString());
		}

		return createExcelsheet;
	}

	/**
	 * @param createExcelsheet
	 * @param writeRow
	 * @param createExcelworkbook
	 * @param requirementId
	 * @param requirementSubmissionCounts 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private Sheet getExcelSheetWithHeaderRejected(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			String requirementId, RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {
		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));

		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
        prepareHeaders(writeRow, createExcelsheet, style, requirement, Constants.REJECTED_COUNT, requirementSubmissionCounts.getNumOfRejected());
		String headers[] = Constants.REQUIREMENT_PIPELINE_REJECTED_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 7);

		return createExcelsheet;
	}

	/**
	 * @author shivayogi
	 * @param createExcelsheet
	 * @param writeRow
	 * @param requirementsub
	 * @return
	 * @description : export for offer candidate
	 */
	private Sheet createXlsSheetOfferMade(Sheet createExcelsheet, Row writeRow, RequirementSubmission requirementsub) {
		if (requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())) {
			writeRow.createCell(0).setCellValue(requirementsub.getCandidateName());
		}

		if (requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())) {
			writeRow.createCell(1).setCellValue(requirementsub.getCandidateEmail());
		}
		if (requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())) {
			writeRow.createCell(2).setCellValue(requirementsub.getPhoneNumber());
		}
		if (requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())) {
			writeRow.createCell(3).setCellValue(requirementsub.getWorkExperience());
		}
		if (requirementsub.getOfferJoinDate() != null && !"".equalsIgnoreCase(requirementsub.getOfferJoinDate())) {
			writeRow.createCell(4).setCellValue(requirementsub.getOfferJoinDate());
		}

		if (requirementsub.getPositionType() != null && !"".equalsIgnoreCase(requirementsub.getPositionType())) {
			writeRow.createCell(5).setCellValue(requirementsub.getPositionType());
		}
		if (requirementsub.getOfferRate() != null && !"".equalsIgnoreCase(requirementsub.getOfferRate())) {
			writeRow.createCell(6).setCellValue(requirementsub.getOfferRate());
		}
		if (requirementsub.getSubmittedOn() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedOn())) {
			writeRow.createCell(7).setCellValue(requirementsub.getSubmittedOn());
		}
		if (requirementsub.getAttachment() != null && !"".equalsIgnoreCase(requirementsub.getAttachment())) {
			writeRow.createCell(8).setCellValue(requirementsub.getAttachment());
		}

		return createExcelsheet;
	}

	/**
	 * @author shivayogi
	 * @param createExcelsheet
	 * @param writeRow
	 * @param requirementSubmissionCounts 
	 * @param requirementsub
	 * @return
	 * @description : export for offer candidate
	 */
	private Sheet getExcelSheetWithHeaderOfferMade(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			String requirementId, RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {
		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));

		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		prepareHeaders(writeRow, createExcelsheet, style, requirement, Constants.OFFER_COUNT, requirementSubmissionCounts.getNumOfOffers());
		String headers[] = Constants.REQUIREMENT_PIPELINE_OFFERMADE_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 7);

		return createExcelsheet;
	}

	/**
	 * @author shivayogi
	 * @param createExcelsheet
	 * @param writeRow
	 * @param requirementsub
	 * @return
	 * @throws Exception 
	 * @Description : for interview candidates
	 */
	private Sheet createXlsSheetInterview(Sheet createExcelsheet, Row writeRow, RequirementSubmission requirementsub) throws Exception {
		if (requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())) {
			writeRow.createCell(0).setCellValue(requirementsub.getCandidateName());
		}

		if (requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())) {
			writeRow.createCell(1).setCellValue(requirementsub.getCandidateEmail());
		}
		if (requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())) {
			writeRow.createCell(2).setCellValue(requirementsub.getPhoneNumber());
		}
		if (requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())) {
			writeRow.createCell(3).setCellValue(requirementsub.getWorkExperience());
		}
		
		java.util.List<RequirementInterview> rquirementInterviews = requirementsub.getRequirementInterviewList();
		StringBuilder interviewStringBuilder = new StringBuilder();
		
		 OrgLocations orglo = orgLocationService.getOrgLocations(requirementsub.getOrgLocations().getId());
		for(RequirementInterview listInterviews:rquirementInterviews){
			if(listInterviews.getDateOfInterview() != null && !"".equalsIgnoreCase(listInterviews.getDateOfInterview().toString())){
				writeRow.createCell(4).setCellValue( commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),listInterviews.getDateOfInterview()));
			}
			if(listInterviews.getStartTimeStr() != null && !"".equalsIgnoreCase(listInterviews.getStartTimeStr()) && listInterviews.getEndTimeStr() != null && !"".equalsIgnoreCase(listInterviews.getEndTimeStr())){
				writeRow.createCell(5).setCellValue(  interviewStringBuilder.append(listInterviews.getStartTimeStr()).append("-").append(listInterviews.getEndTimeStr()).toString());
			}else if(listInterviews.getStartTimeStr() != null && !"".equalsIgnoreCase(listInterviews.getEndTimeStr())){
				writeRow.createCell(5).setCellValue(  interviewStringBuilder.append(listInterviews.getStartTimeStr()).toString());
			}
			if(listInterviews.getLocation() != null && !"".equalsIgnoreCase(listInterviews.getLocation())){
				writeRow.createCell(6).setCellValue( listInterviews.getLocation());
			}
	}
		return createExcelsheet;

	}

	/**
	 * @author shivayogi
	 * @param createExcelsheet
	 * @param writeRow
	 * @param createExcelworkbook
	 * @param requirementId
	 * @param requirementSubmissionCounts 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 * @Description : for interview candidates
	 */
	private Sheet getExcelSheetWithHeaderInterview(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			String requirementId, RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {
		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));

		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		prepareHeaders(writeRow, createExcelsheet, style, requirement, Constants.INTERVIEW_COUNT, requirementSubmissionCounts.getNumOfInterview());
		String headers[] = Constants.REQUIREMENT_PIPELINE_INTERVIEW_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 7);

		return createExcelsheet;
	}

	/**
	 * @author shivayogi
	 * @param createExcelsheet
	 * @param writeRow
	 * @param requirementsub
	 * @return
	 * @throws Exception 
	 * @description: for short listed candidates
	 */
	private Sheet createXlsSheetShortListed(Sheet createExcelsheet, Row writeRow,
			RequirementSubmission requirementsub) throws Exception {

		OrgLocations orgLocations=orgLocationService.getOrgLocations(requirementsub.getOrgLocations().getId());
		
		 //SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		
		if (requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())) {
			writeRow.createCell(0).setCellValue(requirementsub.getCandidateName());
		}

		if (requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())) {
			writeRow.createCell(1).setCellValue(requirementsub.getCandidateEmail());
		}
		if (requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())) {
			writeRow.createCell(2).setCellValue(requirementsub.getPhoneNumber());
		}
		if (requirementsub.getResumeName() != null && !"".equalsIgnoreCase(requirementsub.getResumeName())) {
			writeRow.createCell(3).setCellValue(requirementsub.getResumeName());
		}
		if (requirementsub.getCreatedDate() != null	&& !"".equalsIgnoreCase(requirementsub.getCreatedDate().toString())) {
			//DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementsub.getCreatedDate());
			writeRow.createCell(4).setCellValue(createdDate);
		}
		if (requirementsub.getExpectedCTC() != null && !"".equalsIgnoreCase(requirementsub.getExpectedCTC())) {
			writeRow.createCell(5).setCellValue(requirementsub.getExpectedCTC());
		}
		if (requirementsub.getUserName() != null && !"".equalsIgnoreCase(requirementsub.getUserName())) {
			writeRow.createCell(6).setCellValue(requirementsub.getUserName());
		}
		if (requirementsub.getScore() != null && !"".equalsIgnoreCase(requirementsub.getScore())) {
			writeRow.createCell(7).setCellValue(requirementsub.getScore());
		}
		if (requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())) {
			writeRow.createCell(8).setCellValue(requirementsub.getWorkExperience());
		}
		if (requirementsub.getSubmissionStatus() != null && !"".equalsIgnoreCase(requirementsub.getSubmissionStatus().toString())) {
			writeRow.createCell(9).setCellValue(requirementsub.getSubmissionStatus().toString());
		}
		if (requirementsub.getSubmittedBy() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedBy())) {
			writeRow.createCell(10).setCellValue(requirementsub.getSubmittedBy());
		}
		if (requirementsub.getSubmittedOn() != null) {
			writeRow.createCell(11).setCellValue(requirementsub.getSubmittedOn());
		}
		return createExcelsheet;
	}

	/**
	 * @author shivayogi
	 * @param createExcelsheet
	 * @param writeRow
	 * @param createExcelworkbook
	 * @param requirementId
	 * @param requirementSubmissionCounts 
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 * @Description : for short listed candidates
	 */
	private Sheet getExcelSheetWithHeaderShortListed(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			String requirementId, RequirementSubmissionCounts requirementSubmissionCounts)
			throws NumberFormatException, Exception {
		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		prepareHeaders(writeRow, createExcelsheet, style, requirement, Constants.SHORTLIST_COUNT,requirementSubmissionCounts.getNumOfShortList());
		String headers[] = Constants.REQUIREMENT_PIPELINE_SHORTLISTED_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 7);
		return createExcelsheet;
	}
/**
 * @author Rajashekar
 * 
 * @param writeRow
 * @param createExcelsheet
 * @param style
 * @param requirement
 * @param headerName
 * @param count
 */
	private void prepareHeaders(Row writeRow, Sheet createExcelsheet, CellStyle style, Requirement requirement, String headerName, int count) {
		writeRow = createExcelsheet.createRow(1);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell1 = writeRow.createCell(0);
		cell1.setCellValue("Job Name :");
		cell1.setCellStyle(style);
		Cell cell2 = writeRow.createCell(1);
		cell2.setCellValue(requirement.getJobTitle());
		cell2.setCellStyle(style);

		writeRow = createExcelsheet.createRow(2);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell3 = writeRow.createCell(0);
		cell3.setCellValue("Location :");
		cell3.setCellStyle(style);
		
		

		List<String> requirementLocationsList = new ArrayList<>();
		List<RequirementLocations> locationsList;
		String location = "";
		StringBuilder requirementBuilder=new StringBuilder();
		StringBuilder experienceBuilder=new StringBuilder();
		try {
			locationsList = requirementLocationService.getRequirementLocationsListByRequirementId(requirement.getId());
			if(locationsList != null && !locationsList.isEmpty()) {
				for(RequirementLocations locationsJob : locationsList) {
					requirementLocationsList.add(locationsJob.getLocation());
					if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
						location = requirementBuilder.append(locationsJob.getLocation()).toString();
					}else{
						location = "--";
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//reqListDTO.add(RequirementDTO);

		/*String locations = "";
		StringBuilder requirementBuilder=new StringBuilder();
		if(requirement.getCountry() !=null && requirement.getState() !=null){
			location = requirementBuilder.append(requirement.getCountry()).append(" ").append(requirement.getState()).toString();
		}else{
			location = requirement.getCountry();
		}*/
		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(location);
		cell4.setCellStyle(style);

		writeRow = createExcelsheet.createRow(3);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("Experience : ");
		cell5.setCellStyle(style);

		Cell cell6 = writeRow.createCell(1);
		if(requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()) && requirement.getMaxExp() !=null && !"".equalsIgnoreCase(requirement.getMaxExp())){
			
			cell6.setCellValue(experienceBuilder.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).append(" Years").toString());
			cell6.setCellStyle(style);
			
		}else if(requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())){
			
			cell6.setCellValue(experienceBuilder.append("0 -").append(requirement.getMinExp()).append(" Years").toString());
			cell6.setCellStyle(style);	
			
			}else if(requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())){
				
				cell6.setCellValue(experienceBuilder.append("0 -").append(requirement.getMaxExp()).append(" Years").toString());
				cell6.setCellStyle(style);
				
			}else{
					cell6.setCellValue("--");
					cell6.setCellStyle(style);
				}

		writeRow = createExcelsheet.createRow(4);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell7 = writeRow.createCell(0);
		cell7.setCellValue(headerName);
		cell7.setCellStyle(style);

		Cell cell12 = writeRow.createCell(1);
		cell12.setCellValue(count);
		cell12.setCellStyle(style);
		
	}

	/**
	 * @author shivayogi
	 * @param createExcelsheet
	 * @param writeRow
	 * @param requirementsub
	 * @param submissionType 
	 * @return
	 * @throws Exception 
	 * @description: for applied candidates(All candidates)
	 */
	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, RequirementSubmission requirementsub, String submissionType) throws Exception {
		
		OrgLocations orgLocations=orgLocationService.getOrgLocations(requirementsub.getOrgLocations().getId());
		
		// SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		
		if (requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())) {
			writeRow.createCell(0).setCellValue(requirementsub.getCandidateName());
		}

		if (requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())) {
			writeRow.createCell(1).setCellValue(requirementsub.getCandidateEmail());
		}
		if (requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())) {
			writeRow.createCell(2).setCellValue(requirementsub.getPhoneNumber());
		}
		if (requirementsub.getResumeName() != null && !"".equalsIgnoreCase(requirementsub.getResumeName())) {
			writeRow.createCell(3).setCellValue(requirementsub.getResumeName());
		}
		if (requirementsub.getCreatedDate() != null) {
			//DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			String createdDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),requirementsub.getCreatedDate());
			writeRow.createCell(4).setCellValue(createdDate);
		}
		if (requirementsub.getExpectedCTC() != null && !"".equalsIgnoreCase(requirementsub.getExpectedCTC())) {
			writeRow.createCell(5).setCellValue(requirementsub.getExpectedCTC());
		}
		if (requirementsub.getUserName() != null && !"".equalsIgnoreCase(requirementsub.getUserName())) {
			writeRow.createCell(6).setCellValue(requirementsub.getUserName());
		}
		if (requirementsub.getScore() != null && !"".equalsIgnoreCase(requirementsub.getScore())) {
			writeRow.createCell(7).setCellValue(requirementsub.getScore());
		}
		if (requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())) {
			writeRow.createCell(8).setCellValue(requirementsub.getWorkExperience());
		}
		if (requirementsub.getSubmissionStatus() != null
				&& !"".equalsIgnoreCase(requirementsub.getSubmissionStatus().toString())) {
			writeRow.createCell(9).setCellValue(requirementsub.getSubmissionStatus().toString());
		}
		
		/*String submittedon = "--";*/
		/*String submittedBy = "--";*/
		if(!submissionType.equals("REQUEST_SENT")){
		 if(requirementsub.getSubmittedOn() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedOn())){
			writeRow.createCell(10).setCellValue(requirementsub.getSubmittedOn());
		}
		 if(requirementsub.getSubmittedBy() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedBy())){
		writeRow.createCell(11).setCellValue(requirementsub.getSubmittedBy());
		}
		}
		return createExcelsheet;
		
	}

	/**
	 * @author shivayogi
	 * @param createExcelsheet
	 * @param writeRow
	 * @param createExcelworkbook
	 * @param requirementId
	 * @param requirementSubmissionCounts 
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 * @description: for applied candidates(ALL)
	 */
	private Sheet getExcelSheetWithHeaderApplicants(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			String requirementId, RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {

		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
        prepareHeaders(writeRow, createExcelsheet, style, requirement, Constants.SUBMITTED_APPLICANTS_COUNT, requirementSubmissionCounts.getSubmittedCount());
		String headers[] = Constants.REQUIREMENT_PIPELINE_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 7);

		return createExcelsheet;
	}
	/**
	 * @author SRINIVAS SABBU
	 * @param createExcelsheet
	 * @param writeRow
	 * @param requirementsub
	 * @param submissionType 
	 * @return
	 * @throws Exception 
	 * @description: for stage wise candidate list 
	 */
	private Sheet createWorkflowXlsSheet(Sheet createExcelsheet, Row writeRow, RequirementSubmission requirementsub, HashMap<String, String> stageColumnMap ) throws Exception {
		
		//OrgLocations orgLocations=orgLocationService.getOrgLocations(requirementsub.getOrgLocations().getId());
		
		ArrayList<String> colPropLst =  new ArrayList(stageColumnMap.values());
		
		int colIndex = 0;
		Class mainClass = RequirementSubmission.class;
		// Here we are using JAVA 8 Reflection API to get dynamic object properties  of an object below.
		Field field = null;
		for(String getColumn : colPropLst) {
			 field= mainClass.getDeclaredField(getColumn);
			 field.setAccessible(true);
			 writeRow.createCell(colIndex++).setCellValue(field.get(requirementsub) == null ?"":field.get(requirementsub).toString());
		}
		
		return createExcelsheet;
		
	}
	/**
	 * @author SRINIVAS SABBU
	 * @param createExcelsheet
	 * @param writeRow
	 * @param requirementsub
	 * @param submissionType 
	 * @return
	 * @throws Exception 
	 * @description: for stage wise column list by the work flow id and stage.
	 */
	private Sheet getWorkflowExcelSheetWithStageHeaderApplicants(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			String requirementId, HashMap<String, String> stageColMap, String submissionType,  RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {

		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		
        prepareHeaders(writeRow, createExcelsheet, style, requirement, submissionType, requirementSubmissionCounts.getWorkflowStageCount());
		String headers[] = Arrays.copyOf(stageColMap.keySet().toArray(), stageColMap.size(), String[].class); 
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 7);

		return createExcelsheet;
	}
}
