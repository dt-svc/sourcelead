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
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.service.RequirementPipelineExportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;

@Service
public class RequirementPipelineExportServiceImpl implements RequirementPipelineExportService  {
	
	@Autowired
	PdfUtil pdfUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	PdfUtilPipelineHelper pdfUtilPipelineHelper;

	@Override
	public File getExportRequirementFile(List<RequirementSubmission> requirementStatusList, String orgName,
			String exportType,String requirementId,String submissionType) throws NumberFormatException, Exception {
		File file = null;
		if (!"pdf".equalsIgnoreCase(exportType)) {
			Workbook createExcelworkbook = new XSSFWorkbook();
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Sheet createExcelsheet = createExcelworkbook.createSheet(orgName + "_" + dateFormat.format(new Date()));
			Row writeRow = null;
			if(submissionType.equals("SHORTLIST")){
				createExcelsheet = getExcelSheetWithHeaderShortListed(createExcelsheet, writeRow, createExcelworkbook,requirementId);
				int column = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheetShortListed(createExcelsheet, writeRow, requirementsub);
					column++;
				}
			}
			else if(submissionType.equals("INTERVIEW")){
				createExcelsheet = getExcelSheetWithHeaderInterview(createExcelsheet, writeRow, createExcelworkbook,requirementId);
				int column = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheetInterview(createExcelsheet, writeRow, requirementsub);
					column++;
				}
			}
			else if(submissionType.equals("OFFERMADE")){
				createExcelsheet = getExcelSheetWithHeaderOfferMade(createExcelsheet, writeRow, createExcelworkbook,requirementId);
				int column = 0;
				for (RequirementSubmission requirementsub : requirementStatusList) {
					writeRow = createExcelsheet.createRow(column + 8);
					createExcelsheet = createXlsSheetOfferMade(createExcelsheet, writeRow, requirementsub);
					column++;
				}
			}
			
			else{
			createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,requirementId);
			int column = 0;
			for (RequirementSubmission requirementsub : requirementStatusList) {
				writeRow = createExcelsheet.createRow(column + 8);
				createExcelsheet = createXlsSheet(createExcelsheet, writeRow, requirementsub);
				column++;
				}
			}
			file = new File(orgName + "_Requirements_" + dateFormat.format(new Date()) + ".xls");
			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			file = pdfUtilPipelineHelper.getRequirementPipelenePdf(requirementStatusList, orgName, requirementId,submissionType);
	}
		return file;
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
		if(requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())){
			 writeRow.createCell(0).setCellValue(requirementsub.getCandidateName()); 
		}
		
		if(requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())){
			 writeRow.createCell(1).setCellValue(requirementsub.getCandidateEmail()); 
		}
		if(requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())){
			 writeRow.createCell(2).setCellValue(requirementsub.getPhoneNumber()); 
		}
		if(requirementsub.getWorkExperience()!= null&& !"".equalsIgnoreCase(requirementsub.getWorkExperience())){
			 writeRow.createCell(3).setCellValue(requirementsub.getWorkExperience()); 
		}
		if(requirementsub.getOfferJoinDate()!= null&& !"".equalsIgnoreCase(requirementsub.getOfferJoinDate())){
			 writeRow.createCell(4).setCellValue(requirementsub.getOfferJoinDate()); 
		}
		
		if(requirementsub.getPositionType()!= null&& !"".equalsIgnoreCase(requirementsub.getPositionType())){
			 writeRow.createCell(5).setCellValue(requirementsub.getPositionType()); 
		}
		if(requirementsub.getOfferRate()!= null&& !"".equalsIgnoreCase(requirementsub.getOfferRate())){
			 writeRow.createCell(6).setCellValue(requirementsub.getOfferRate()); 
		}
		String offerDate="--";
		if(offerDate!= null){
			 writeRow.createCell(7).setCellValue(offerDate); 
		}
		if(requirementsub.getAttachment()!= null&& !"".equalsIgnoreCase(requirementsub.getAttachment())){
			 writeRow.createCell(8).setCellValue(requirementsub.getAttachment()); 
		}
		
		
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
	private Sheet getExcelSheetWithHeaderOfferMade(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			String requirementId) throws NumberFormatException, Exception {
		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId)); 
		
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);

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

		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(requirement.getCountry()+" "+requirement.getState());
		cell4.setCellStyle(style);

		writeRow = createExcelsheet.createRow(3);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("Experience : ");
		cell5.setCellStyle(style);

		Cell cell6 = writeRow.createCell(1);
		cell6.setCellValue(requirement.getMaxExp()+" "+requirement.getMinExp());
		cell6.setCellStyle(style);
		
		
		writeRow = createExcelsheet.createRow(4);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell7 = writeRow.createCell(0);
		cell7.setCellValue("Shortlisted Applicants : ");
		cell7.setCellStyle(style);
			

		Cell cell12 = writeRow.createCell(1);
		cell12.setCellValue("6-static");
		cell12.setCellStyle(style);
		String headers [] = Constants.REQUIREMENT_PIPELINE_OFFERMADE_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,7);

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
		if(requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())){
			 writeRow.createCell(0).setCellValue(requirementsub.getCandidateName()); 
		}
		
		if(requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())){
			 writeRow.createCell(1).setCellValue(requirementsub.getCandidateEmail()); 
		}
		if(requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())){
			 writeRow.createCell(2).setCellValue(requirementsub.getPhoneNumber()); 
		}
		if(requirementsub.getWorkExperience()!= null&& !"".equalsIgnoreCase(requirementsub.getWorkExperience())){
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
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 * @Description : for interview candidates
	 */
	private Sheet getExcelSheetWithHeaderInterview(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			String requirementId) throws NumberFormatException, Exception {
		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId)); 
		
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);

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

		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(requirement.getCountry()+" "+requirement.getState());
		cell4.setCellStyle(style);

		writeRow = createExcelsheet.createRow(3);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("Experience : ");
		cell5.setCellStyle(style);

		Cell cell6 = writeRow.createCell(1);
		cell6.setCellValue(requirement.getMaxExp()+" "+requirement.getMinExp());
		cell6.setCellStyle(style);
		
		
		writeRow = createExcelsheet.createRow(4);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell7 = writeRow.createCell(0);
		cell7.setCellValue("Shortlisted Applicants : ");
		cell7.setCellStyle(style);
			

		Cell cell12 = writeRow.createCell(1);
		cell12.setCellValue("6-static");
		cell12.setCellStyle(style);
		String headers [] = Constants.REQUIREMENT_PIPELINE_INTERVIEW_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,7);

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
		
		if(requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())){
			 writeRow.createCell(0).setCellValue(requirementsub.getCandidateName()); 
		}
		
		if(requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())){
			 writeRow.createCell(1).setCellValue(requirementsub.getCandidateEmail()); 
		}
		if(requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())){
			 writeRow.createCell(2).setCellValue(requirementsub.getPhoneNumber()); 
		}
		if(requirementsub.getResumeName() != null && !"".equalsIgnoreCase(requirementsub.getResumeName())){
			 writeRow.createCell(3).setCellValue(requirementsub.getResumeName()); 
		}
		if(requirementsub.getCreatedDate() != null && !"".equalsIgnoreCase(requirementsub.getCreatedDate().toString())){
			 writeRow.createCell(4).setCellValue(requirementsub.getCreatedDate()); 
		}
		if(requirementsub.getExpectedCTC() != null && !"".equalsIgnoreCase(requirementsub.getExpectedCTC())){
			 writeRow.createCell(5).setCellValue(requirementsub.getExpectedCTC()); 
		}
		if(requirementsub.getUserName() != null && !"".equalsIgnoreCase(requirementsub.getUserName())){
			 writeRow.createCell(6).setCellValue(requirementsub.getUserName()); 
		}
		if(requirementsub.getScore() != null && !"".equalsIgnoreCase(requirementsub.getScore())){
			 writeRow.createCell(7).setCellValue(requirementsub.getScore()); 
		}
		if(requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())){
			 writeRow.createCell(8).setCellValue(requirementsub.getWorkExperience()); 
		}
		if(requirementsub.getStatus() != null && !"".equalsIgnoreCase(requirementsub.getStatus().toString())){
			 writeRow.createCell(9).setCellValue(requirementsub.getStatus()); 
		}
		if(requirementsub.getSubmittedBy() != null && !"".equalsIgnoreCase(requirementsub.getSubmittedBy())){
			 writeRow.createCell(10).setCellValue(requirementsub.getSubmittedBy()); 
		}
		if(requirementsub.getSubmittedOn() != null){
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
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 * @Description : for short listed candidates
	 */
	private Sheet getExcelSheetWithHeaderShortListed(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			String requirementId) throws NumberFormatException, Exception {
		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId)); 
		
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);

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

		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(requirement.getCountry()+" "+requirement.getState());
		cell4.setCellStyle(style);

		writeRow = createExcelsheet.createRow(3);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("Experience : ");
		cell5.setCellStyle(style);

		Cell cell6 = writeRow.createCell(1);
		cell6.setCellValue(requirement.getMaxExp()+" "+requirement.getMinExp());
		cell6.setCellStyle(style);
		
		
		writeRow = createExcelsheet.createRow(4);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell7 = writeRow.createCell(0);
		cell7.setCellValue("Shortlisted Applicants : ");
		cell7.setCellStyle(style);
			

		Cell cell12 = writeRow.createCell(1);
		cell12.setCellValue("6-static");
		cell12.setCellStyle(style);
		String headers [] = Constants.REQUIREMENT_PIPELINE_SHORTLISTED_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,7);

		return createExcelsheet;
	}

	/**
	 * @author shivayogi
	 * @param createExcelsheet
	 * @param writeRow
	 * @param requirementsub
	 * @return
	 * @description: for applied candidates(All candidates)
	 */
	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, RequirementSubmission requirementsub) {
		if(requirementsub.getCandidateName() != null && !"".equalsIgnoreCase(requirementsub.getCandidateName())){
			 writeRow.createCell(0).setCellValue(requirementsub.getCandidateName()); 
		}
		
		if(requirementsub.getCandidateEmail() != null && !"".equalsIgnoreCase(requirementsub.getCandidateEmail())){
			 writeRow.createCell(1).setCellValue(requirementsub.getCandidateEmail()); 
		}
		if(requirementsub.getPhoneNumber() != null && !"".equalsIgnoreCase(requirementsub.getPhoneNumber())){
			 writeRow.createCell(2).setCellValue(requirementsub.getPhoneNumber()); 
		}
		if(requirementsub.getResumeName() != null && !"".equalsIgnoreCase(requirementsub.getResumeName())){
			 writeRow.createCell(3).setCellValue(requirementsub.getResumeName()); 
		}
		if(requirementsub.getCreatedDate() != null){
			 writeRow.createCell(4).setCellValue(requirementsub.getCreatedDate().toString()); 
		}
		if(requirementsub.getExpectedCTC() != null && !"".equalsIgnoreCase(requirementsub.getExpectedCTC())){
			 writeRow.createCell(5).setCellValue(requirementsub.getExpectedCTC()); 
		}
		if(requirementsub.getUserName() != null && !"".equalsIgnoreCase(requirementsub.getUserName())){
			 writeRow.createCell(6).setCellValue(requirementsub.getUserName()); 
		}
		if(requirementsub.getScore() != null && !"".equalsIgnoreCase(requirementsub.getScore())){
			 writeRow.createCell(7).setCellValue(requirementsub.getScore()); 
		}
		if(requirementsub.getWorkExperience() != null && !"".equalsIgnoreCase(requirementsub.getWorkExperience())){
			 writeRow.createCell(8).setCellValue(requirementsub.getWorkExperience()); 
		}
		if(requirementsub.getStatus() != null && !"".equalsIgnoreCase(requirementsub.getStatus().toString())){
			 writeRow.createCell(9).setCellValue(requirementsub.getStatus()); 
		}
		return createExcelsheet;
	}

	/**
	 * @author shivayogi
	 * @param createExcelsheet
	 * @param writeRow
	 * @param createExcelworkbook
	 * @param requirementId
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 * @description: for applied candidates(ALL)
	 */
	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			 String requirementId) throws NumberFormatException, Exception {
		
		Requirement requirement = requirementService.getRequirementByRequirementId(Long.parseLong(requirementId)); 
		
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);

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

		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(requirement.getCountry()+" "+requirement.getState());
		cell4.setCellStyle(style);

		writeRow = createExcelsheet.createRow(3);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("Experience : ");
		cell5.setCellStyle(style);

		Cell cell6 = writeRow.createCell(1);
		cell6.setCellValue(requirement.getMaxExp()+" "+requirement.getMinExp());
		cell6.setCellStyle(style);
		
		
		writeRow = createExcelsheet.createRow(4);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);

		Cell cell7 = writeRow.createCell(0);
		cell7.setCellValue("Shortlisted Applicants : ");
		cell7.setCellStyle(style);
			

		Cell cell12 = writeRow.createCell(1);
		cell12.setCellValue("6-static");
		cell12.setCellStyle(style);
		String headers [] = Constants.REQUIREMENT_PIPELINE_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,7);

		return createExcelsheet;
	}

}
