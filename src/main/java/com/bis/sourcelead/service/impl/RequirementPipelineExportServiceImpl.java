package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionCounts;
import com.bis.sourcelead.service.RequirementPipelineExportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;

@Service
public class RequirementPipelineExportServiceImpl implements RequirementPipelineExportService {

	@Autowired
	PdfUtil pdfUtil;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	RequirementService requirementService;

	@Autowired
	PdfUtilPipelineHelper pdfUtilPipelineHelper;

	/**
	 * @author Shiva and Rakshith
	 * prepares excel and pdf and retun it as File
	 * @return File
	 * @throws NumberFormatException
	 */
	@Override
	public File getExportRequirementFile(List<RequirementSubmission> requirementStatusList, String orgName,
			String exportType, String requirementId, String submissionType,RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {
		File file = null;
		if (!"pdf".equalsIgnoreCase(exportType)) {
			Workbook createExcelworkbook = new XSSFWorkbook();
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			StringBuilder excelStringBuilder=new StringBuilder();
			Sheet createExcelsheet = createExcelworkbook.createSheet(excelStringBuilder.append(orgName).append("_").append(dateFormat.format(new Date())).toString());
			Row writeRow = null;
			if (submissionType.equals("SUBMIT_TO_CLIENT")) {
				createExcelsheet = getExcelSheetWithHeaderSubmitClient(createExcelsheet, writeRow, createExcelworkbook,requirementId,requirementSubmissionCounts);
				int column = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheetSubmitClient(createExcelsheet, writeRow, requirementsub,submissionType);
					column++;
				}
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
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheet(createExcelsheet, writeRow, requirementsub,submissionType);
					column++;
				}
			}
			/*file = new File(orgName + "_Requirements_" + dateFormat.format(new Date()) + ".xls");*/
			StringBuilder tempFileBuilder=new StringBuilder();
			file = File.createTempFile(tempFileBuilder.append(orgName).append("_Requirements_").append(dateFormat.format(new Date())).toString(),".xls");
			file.setReadable(true);

			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			file = pdfUtilPipelineHelper.getRequirementPipelenePdf(requirementStatusList, orgName, requirementId,submissionType,requirementSubmissionCounts);
		}
		return file;
	}

	private Sheet createXlsSheetSubmitClient(Sheet createExcelsheet, Row writeRow, RequirementSubmission requirementsub,
			String submissionType) {
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
			writeRow.createCell(4).setCellValue(requirementsub.getCreatedDate().toString());
		}
		if (requirementsub.getCandidate().getExpectedRate() != null && !"".equalsIgnoreCase(requirementsub.getCandidate().getExpectedRate())) {
			writeRow.createCell(5).setCellValue(requirementsub.getCandidate().getExpectedRate());
		}
		if (requirementsub.getProposedSubmissionRate() != null && !"".equalsIgnoreCase(requirementsub.getProposedSubmissionRate())) {
			writeRow.createCell(6).setCellValue(requirementsub.getProposedSubmissionRate());
		}
		if (requirementsub.getUserName() != null && !"".equalsIgnoreCase(requirementsub.getUserName())) {
			writeRow.createCell(7).setCellValue(requirementsub.getUserName());
		}
		if (requirementsub.getScore() != null && !"".equalsIgnoreCase(requirementsub.getScore())) {
			writeRow.createCell(8).setCellValue(requirementsub.getScore());
		}
		if (requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())) {
			writeRow.createCell(9).setCellValue(requirementsub.getWorkExperience());
		}
		if (requirementsub.getSubmissionStatus() != null
				&& !"".equalsIgnoreCase(requirementsub.getSubmissionStatus().toString())) {
			writeRow.createCell(10).setCellValue(requirementsub.getSubmissionStatus().toString());
		}
		
		/*String submittedon = "--";*/
		/*String submittedBy = "--";*/
		if(!submissionType.equals("REQUEST_SENT")){
		 if(requirementsub.getSubmittedOn() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedOn())){
			writeRow.createCell(11).setCellValue(requirementsub.getSubmittedOn());
		}
		 if(requirementsub.getSubmittedBy() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedBy())){
		writeRow.createCell(12).setCellValue(requirementsub.getSubmittedBy());
		 }
		 if(requirementsub.getRequirement().getSpocId().getSpocUser().getMobilePhone() != null && !"".equalsIgnoreCase(requirementsub.getRequirement().getSpocId().getSpocUser().getMobilePhone())){
				writeRow.createCell(13).setCellValue(requirementsub.getRequirement().getSpocId().getSpocUser().getMobilePhone());
		}
	}
		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeaderSubmitClient(Sheet createExcelsheet, Row writeRow,
			Workbook createExcelworkbook, String requirementId,
			RequirementSubmissionCounts requirementSubmissionCounts) throws NumberFormatException, Exception {
		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
        prepareHeaders(writeRow, createExcelsheet, style, requirement, Constants.SUBMIT_TO_CLIENT_COUNT, requirementSubmissionCounts.getNumOfSubmitToClient());
		String headers[] = Constants.REQUIREMENT_PIPELINE_SUBMIT_TO_CLIENT_EXCEL_HEADERS;
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
	 * @description: this is for rejected pipeline requirement.
	 */
	private Sheet createXlsSheetRejected(Sheet createExcelsheet, Row writeRow, RequirementSubmission requirementsub) {
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
			writeRow.createCell(4).setCellValue(requirementsub.getCreatedDate().toString());
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
	 * @Description : for interview candidates
	 */
	private Sheet createXlsSheetInterview(Sheet createExcelsheet, Row writeRow, RequirementSubmission requirementsub) {
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
	 * @description: for short listed candidates
	 */
	private Sheet createXlsSheetShortListed(Sheet createExcelsheet, Row writeRow,
			RequirementSubmission requirementsub) {

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
		if (requirementsub.getCreatedDate() != null
				&& !"".equalsIgnoreCase(requirementsub.getCreatedDate().toString())) {
			writeRow.createCell(4).setCellValue(requirementsub.getCreatedDate());
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
		if (requirementsub.getStatus() != null && !"".equalsIgnoreCase(requirementsub.getStatus().toString())) {
			writeRow.createCell(9).setCellValue(requirementsub.getStatus());
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
		cell1.setCellValue("Requirement Name :");
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

		String location = "";
		StringBuilder requirementBuilder=new StringBuilder();
		if(requirement.getCountry() !=null && requirement.getState() !=null){
			location = requirementBuilder.append(requirement.getCountry()).append(" ").append(requirement.getState()).toString();
		}else{
			location = requirement.getCountry();
		}
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
			
			cell6.setCellValue(requirementBuilder.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
			cell6.setCellStyle(style);
			
		}else if(requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())){
			
			cell6.setCellValue(requirementBuilder.append("0 -").append(requirement.getMinExp()).append("years").toString());
			cell6.setCellStyle(style);	
			
			}else if(requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())){
				
				cell6.setCellValue(requirementBuilder.append("0 -").append(requirement.getMaxExp()).append("years").toString());
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
	 * @description: for applied candidates(All candidates)
	 */
	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, RequirementSubmission requirementsub, String submissionType) {
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
			writeRow.createCell(4).setCellValue(requirementsub.getCreatedDate().toString());
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

}
