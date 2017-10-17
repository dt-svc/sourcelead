
package com.bis.sourcelead.service.impl;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesApprovalDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.ibm.icu.math.BigDecimal;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


@Service
public class ExpensesApprovalServiceImpl implements ExpensesApprovalService{

	@Autowired
	private ExpensesApprovalDao expensesApprovalDao;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private ExpensesService expensesService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	ProjectMembersDao projectMembersDao;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;

	private static final Logger logger = LoggerFactory.getLogger(ExpensesApprovalServiceImpl.class);
	
	
	@Override
	public List<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(User loginUser, OrgLocations orgLoc, String expenseStatus, String startDate, String endDate) throws Exception {
		Date endDateUTC = null;
		Date startDateUTC = null;
	//	SimpleDateFormat dateFormat=orgLoc.getLocFullFormDateFormatter();
		Timezone timezone = timeZoneService.getTimezone(orgLoc.getTimezone().getId());
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
		DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
		if(StringUtils.isNotEmpty(startDate) && !startDate.equalsIgnoreCase("undefined")){
			Date startTime = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),startDate);
			String customStartTimeStr = TZ_DF_CSD.format(startTime);
			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
			startDateUTC = TZ_DF_TS.parse(startDateString);
		}
		
		if(StringUtils.isNotEmpty(endDate) && !startDate.equalsIgnoreCase("undefined")){
			Date endTime =  commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(),endDate);
			String customEndTimeStr = TZ_DF_CED.format(endTime);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
			endDateUTC = TZ_DF_TS.parse(endDateString);
		}
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesApprovalDao.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser.getId(),orgLoc.getId(), expenseStatus, startDateUTC, endDateUTC);
		for(ExpensesApproverHierarchy expensesApproverHierarchy : expApperoverHierarchy){
			Expenses expenses = expensesApproverHierarchy.getExpenseId();
			expenses.setLocationsCode(expensesApproverHierarchy.getExpenseId().getOrgLocationId().getLocationCode());
			if(null != expensesApproverHierarchy.getExpenseId().getEndDate()){
				expensesApproverHierarchy.setExpenseStartDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getStartDate()));
				expensesApproverHierarchy.setExpenseEndDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getEndDate()));
			}else{
				expensesApproverHierarchy.setExpenseStartDateString(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expensesApproverHierarchy.getExpenseId().getStartDate()));
			}
			
			if (null != expensesApproverHierarchy.getExpenseId()) {
				
				if(expensesApproverHierarchy.getExpenseId().getPurpose()!=null){
					expensesApproverHierarchy.setExpensesApproverHierarchyPurpose(expensesApproverHierarchy.getExpenseId().getPurpose());
				}else{
					expensesApproverHierarchy.setExpensesApproverHierarchyPurpose("--");
				}
				if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers()) {
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects()){
								expensesApproverHierarchy.setExpensesProjectName(expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects().getProjectName());
							}else{
								expensesApproverHierarchy.setExpensesProjectName("--");
							}
							
					 ProjectMembers projectMember = expensesApproverHierarchy.getExpenseId().getProjectMembers();
					 ProjectMembers pmWithUser =projectMembersDao.getConsultantUserProjectMemberByProjectMemberId(expensesApproverHierarchy.getExpenseId().getProjectMembers().getId());
					if (null !=projectMember.getProjectMemberCandidteId()) {
							if(null != projectMember.getProjectMemberCandidteId().getDesignationType() && !"".equalsIgnoreCase(projectMember.getProjectMemberCandidteId().getDesignationType())){
								String empType = projectMember.getProjectMemberCandidteId().getDesignationType();
								if(empType.equalsIgnoreCase("C2C") || empType.equalsIgnoreCase("C2C-1099")){
									OrgRelations orgRelation = projectMember.getProjectMemberCandidteId().getOrgRelationId();
									expensesApproverHierarchy.setUserTypeVendorName(orgRelation.getResponderLocationId().getOrgLocationName());
									expensesApproverHierarchy.setExpenseEmpUserType(empType);
								 }else{
									 expensesApproverHierarchy.setExpenseEmpUserType(empType);
								 }
							}else{
								expensesApproverHierarchy.setExpenseEmpUserType("--");
							}
						
						if (null != projectMember.getProjectMemberCandidteId()) {
							expensesApproverHierarchy.setExpenseEmpUserFirstNameLastName(projectMember.getProjectMemberCandidteId().getFullName());
							expensesApproverHierarchy.setExpenseEmpUserEmail(projectMember.getProjectMemberCandidteId().getEmail());
							if(null !=  pmWithUser.getProjectMemberCandidteId().getConsultantUser()){
								expensesApproverHierarchy.setExpensesUserid(pmWithUser.getProjectMemberCandidteId().getConsultantUser().getId());
								expensesApproverHierarchy.setUserFileUrl(pmWithUser.getProjectMemberCandidteId().getConsultantUser().getFileUrl());
							}
						}
						
						if (null != expensesApproverHierarchy.getExpenseId()) {
							if(null != expensesApproverHierarchy.getExpenseId().getPurpose()){
								expensesApproverHierarchy.setExpensesApproverHierarchyPurpose(expensesApproverHierarchy.getExpenseId().getPurpose());
							}else{
								expensesApproverHierarchy.setExpensesApproverHierarchyPurpose("-----");
							}
						}
					}
				}
			}

		 }
		return expApperoverHierarchy;
	}




	@Override
	public ExpensesApproverHierarchy updateSubmissionApproverHierarchy(
			ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception {
		return expensesApprovalDao.updateSubmissionApproverHierarchy(expensesApproverHierarchy);
	}




	@Override
	public Integer updateApprovalStatusByApproverForExpense(Long expenseId, Long approverUserId, String status,
			String rejectedReason) throws Exception {
		return expensesApprovalDao.updateApprovalStatusByApproverForExpense(expenseId, approverUserId, status, rejectedReason);
	}


	@Override
	public File getExpensesApproverHierarchyListForPdf(List<ExpensesApproverHierarchy> expApperoverHierarchy, OrgLocations orgLoc,User loginUser) throws Exception {
		String[] headers = Constants.EXPENSES_APPROVAL_lIST_HEADERS;
	    Document document = new Document(PageSize.LETTER.rotate());
	    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	    StringBuilder sbUser =  new StringBuilder();
	    sbUser.append("ExpensesApproval_").append(dateFormat.format(new Date()));

	    StringBuilder fileSB = new StringBuilder();
	    File file = File.createTempFile(fileSB.append("_ExpensesApproval_").append(dateFormat.format(new Date())).toString(),".pdf");
	    file.setReadable(true);
	    try {
	        // Get an instance of PdfWriter and create a Table.pdf file as an output.
	        PdfWriter.getInstance(document, new FileOutputStream(file));
	        document.open();

	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	        BaseColor myColor = WebColors.getRGBColor("#000000");
	        boldFont.setColor(myColor);
	        StringBuilder userStyle = new StringBuilder();
	        userStyle.append(orgLoc.getOrgLocationName()).append(" ").append("- Employee Expenses");
	       
	        // Create an instance of PdfPTable. After that we transform the header and
	        PdfPTable table = new PdfPTable(headers.length);
	        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
	        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
	        cell1.setColspan(18);
	        headerCellStyle(cell1);
	        table.setWidthPercentage(100);
            table.setWidths(new int[]{7,7,7,4,5,5,5,7,6,6,4,5,5,5});
            table.addCell(cell1);
	        for (int i = 0; i < headers.length; i++) {
	            String header = headers[i];
	            PdfPCell cell = new PdfPCell();
	            cell.setGrayFill(0.9f);
	            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
	            cell.setBackgroundColor(myBorderColor);
	            table.addCell(cell);
	        }
	        table.completeRow();
	        java.util.List<String[]> expenseList = prepareExpenseApprovalListStringArray(expApperoverHierarchy,headers,orgLoc,loginUser);
	        if(expenseList != null && expenseList.size() >0){
	        	for (String[] expenseArray: expenseList) {
	        		for (int i = 0; i < expenseArray.length; i++) {
	        			String datum = expenseArray[i];
	        			PdfPCell cell = new PdfPCell();
	        			cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
	        			table.addCell(cell);
	        		}
	        		table.completeRow();
	        	}
	        }
	        document.addTitle("Expenses Approval Table");
	        table.setSpacingBefore(15f);
	        document.add(table);
	        logger.info("Pdf table created !!");
	    } catch (DocumentException e) {
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    } catch (FileNotFoundException e) {
	    	logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
	    } finally {
	        document.close();
	    }
		return file;
	}
private List<String[]> prepareExpenseApprovalListStringArray(List<ExpensesApproverHierarchy> expApperoverHierarchy, String[] headers,OrgLocations orgLoc,User loginUser) throws Exception {
	
	List<String[]> arrayList = new ArrayList<>();
	for(ExpensesApproverHierarchy ex:expApperoverHierarchy){
		String expenseArray[] = new String[headers.length];
		if(ex.getExpenseId() !=null){
			Expenses expenseId =expensesService.getExpenseById(ex.getExpenseId().getId());
			if(null != expenseId.getProjectMembers()){
				if(null != expenseId.getProjectMembers().getEmployee()){
					if(null != expenseId.getProjectMembers().getEmployee().getUser()){
						if(null != expenseId.getProjectMembers().getEmployee().getUser().getFirstName() && null != expenseId.getProjectMembers().getEmployee().getUser().getLastName()){
							StringBuffer sb = new StringBuffer();
							expenseArray[0] = sb.append(expenseId.getProjectMembers().getEmployee().getUser().getFirstName()).append(" ").append(expenseId.getProjectMembers().getEmployee().getUser().getLastName()).toString();
						}else if(null != expenseId.getProjectMembers().getEmployee().getUser().getFirstName()){
							expenseArray[0] = expenseId.getProjectMembers().getEmployee().getUser().getFirstName();
						}
						else
						{
							expenseArray[0] = "--";
						}
						if(null != expenseId.getProjectMembers().getEmployee().getUser().getEmail()){

							expenseArray[1] = expenseId.getProjectMembers().getEmployee().getUser().getEmail();
						}else{
							expenseArray[1] = "--";
						}
						if(null != expenseId.getProjectMembers().getEmployee().getUser().getMobilePhone() && !"".equalsIgnoreCase(expenseId.getProjectMembers().getEmployee().getUser().getMobilePhone())){

							expenseArray[2] = expenseId.getProjectMembers().getEmployee().getUser().getMobilePhone();
						}else{
							expenseArray[2] = "--";
						}
						
					}
					if(null != expenseId.getProjectMembers().getEmployee().getUserEmploymentType() && !"".equalsIgnoreCase(expenseId.getProjectMembers().getEmployee().getUserEmploymentType())){

						expenseArray[3] = expenseId.getProjectMembers().getEmployee().getUserEmploymentType();
					}else{
						expenseArray[3] = "--";
					}
					if((null != expenseId.getProjectMembers().getEmployee().getUserEmploymentType())
							&& ((expenseId.getProjectMembers().getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C") ||
									(expenseId.getProjectMembers().getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C-1099")) ))){
						 OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(expenseId.getProjectMembers().getEmployee().getOrgLocations().getId(), expenseId.getProjectMembers().getEmployee().getOrgRelationId().getId());
						expenseArray[4] = orgRelation.getResponderLocationId().getOrgLocationName();
					}else{
						expenseArray[4] = "--";
					}
					
				}
				if(null != expenseId.getProjectMembers().getProjects()){
					if(null != expenseId.getProjectMembers().getProjects().getProjectName()){
						expenseArray[5] = expenseId.getProjectMembers().getProjects().getProjectName();
					}
				}else{
					expenseArray[5] = "--";
				}
				
			}
			if(null != expenseId.getClientName()){

				expenseArray[6] = expenseId.getClientName();
			}else{
				expenseArray[6] = "--";
			}
			if(null != expenseId.getPurpose()){

				expenseArray[7] = expenseId.getPurpose();
			}else{
				expenseArray[7] = "--";
			}
			
			if(null != expenseId.getStartDate()){
				expenseId.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenseId.getStartDate()));
				expenseArray[8] = expenseId.getStartDateStr();
			}else{
				expenseArray[8] = "--";
			}
			
			if(null != expenseId.getEndDate()){
				expenseId.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenseId.getEndDate()));
				expenseArray[9] = expenseId.getEndDateStr();
			}else{
				expenseArray[9] = "--";
			}
			if(null != expenseId.getNoOfExpenses()){

				expenseArray[10] = expenseId.getNoOfExpenses();
			}else{
				expenseArray[10] = "--";
			}
			if(expenseId.getTotalAmount() != 0){
				StringBuilder str = new StringBuilder();
				BigDecimal bd = new BigDecimal(Double.toString(expenseId.getTotalAmount()));
				bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP );
				expenseArray[11] = str.append(bd).toString();
			}else{
				expenseArray[11] = "--";
			}
			if(null != expenseId.getStatus()){
				
				ExpensesApproverHierarchy  eah = getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId.getId(),orgLoc.getId());
			    Submission_Status submissionStatus = eah.getExpenseStatus();
				expenseArray[12] = submissionStatus.getValue();
			}else{
				expenseArray[12] = "--";
			}
			if(null != ex.getActionPerformedBy()){
				expenseArray[13] = ex.getActionPerformedBy();
			}else{
				expenseArray[13] = "--";
			}
		}
		arrayList.add(expenseArray);
	}
	return arrayList;
}

	public List<ExpensesApproverHierarchy> getExpensesApproverHierarchyListByExpenseId(Long expenseId)
			throws Exception {
		return expensesApprovalDao.getExpensesApproverHierarchyListByExpenseId(expenseId);
	}




	@Override
	public ExpensesApproverHierarchy isThereAnyApproverApprovedTheExpense(Long expenseId) {
		return expensesApprovalDao.isThereAnyApproverApprovedTheExpense(expenseId);
	}

	private static void headerCellStyle(PdfPCell cell){
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setPaddingTop(0f);
    cell.setPaddingBottom(7f);
    cell.setBackgroundColor(new BaseColor(0,121,182));
    cell.setBorder(0);
    cell.setBorderWidthBottom(2f);
    }




@Override
public File getExpensesApproverHierarchyListForExcel(List<ExpensesApproverHierarchy> expApperoverHierarchy,
		OrgLocations orgLoc,User loginUser) throws Exception {
	
	File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(orgLoc.getOrgLocationName()+ "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		TimeSheetSubmissionCount requirementSubCount=null;
		createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,requirementSubCount);
		int column = 0;
		for (ExpensesApproverHierarchy expensesList : expApperoverHierarchy) {
			writeRow = createExcelsheet.createRow(column+1);
			createExcelsheet = createXlsSheet(createExcelsheet, writeRow, expensesList,orgLoc,loginUser);
			column++;
		}
		 try {
			file = File.createTempFile(orgLoc.getOrgLocationName() + "_ExpensesApproval_" + dateFormat.format(new Date()),".xls");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		 file.setReadable(true);
		FileOutputStream fos = new FileOutputStream(file);
		try {
			createExcelworkbook.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return file;
}

private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, TimeSheetSubmissionCount requirementSubCount) {
	
	CellStyle style = commonUtil.getCellStyle(createExcelworkbook);

	String[] headers = Constants.EXPENSES_APPROVAL_lIST_HEADERS;;
	createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,0);

	return createExcelsheet;
}

private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, ExpensesApproverHierarchy ex,OrgLocations orgLoc,User loginUser) throws Exception {
		if(ex.getExpenseId() !=null){
			Expenses expenseId =expensesService.getExpenseById(ex.getExpenseId().getId());
			if(null != expenseId.getProjectMembers()){
				if(null != expenseId.getProjectMembers().getEmployee()){
					if(null != expenseId.getProjectMembers().getEmployee().getUser()){
						
						if(null != expenseId.getProjectMembers().getEmployee().getUser().getFirstName() && null != expenseId.getProjectMembers().getEmployee().getUser().getLastName()){
							StringBuffer sb = new StringBuffer();
							writeRow.createCell(0).setCellValue(sb.append(expenseId.getProjectMembers().getEmployee().getUser().getFirstName()).append(" ").append(expenseId.getProjectMembers().getEmployee().getUser().getLastName()).toString());
							createExcelsheet.autoSizeColumn(0);
						}else if(null != expenseId.getProjectMembers().getEmployee().getUser().getFirstName()){
							writeRow.createCell(0).setCellValue(expenseId.getProjectMembers().getEmployee().getUser().getFirstName());
							createExcelsheet.autoSizeColumn(0);
						}
						
						/*if(null != expenseId.getProjectMembers().getEmployee().getUser().getFirstName() && null != expenseId.getProjectMembers().getEmployee().getUser().getLastName()){
							writeRow.createCell(0).setCellValue(expenseId.getProjectMembers().getEmployee().getUser().getFirstName());
							createExcelsheet.autoSizeColumn(0);
						}*/
						if(null != expenseId.getProjectMembers().getEmployee().getUser().getEmail()){
							writeRow.createCell(1).setCellValue(expenseId.getProjectMembers().getEmployee().getUser().getEmail());
							createExcelsheet.autoSizeColumn(1);
						}
						if(null != expenseId.getProjectMembers().getEmployee().getUser().getMobilePhone()){
							writeRow.createCell(2).setCellValue(expenseId.getProjectMembers().getEmployee().getUser().getMobilePhone());
							createExcelsheet.autoSizeColumn(2);
						}
					}
					if(null != expenseId.getProjectMembers().getEmployee().getUserEmploymentType()){
						writeRow.createCell(3).setCellValue(expenseId.getProjectMembers().getEmployee().getUserEmploymentType());
						createExcelsheet.autoSizeColumn(3);
					}
					if((null != expenseId.getProjectMembers().getEmployee().getUserEmploymentType())
							&& ((expenseId.getProjectMembers().getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C") ||
									(expenseId.getProjectMembers().getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C-1099")) ))){
						 OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(expenseId.getProjectMembers().getEmployee().getOrgLocations().getId(), expenseId.getProjectMembers().getEmployee().getOrgRelationId().getId());
						 writeRow.createCell(4).setCellValue(orgRelation.getResponderLocationId().getOrgLocationName());
						 createExcelsheet.autoSizeColumn(4);
					}
				}
				if(null != expenseId.getProjectMembers().getProjects()){
					if(null != expenseId.getProjectMembers().getProjects().getProjectName()){
						writeRow.createCell(5).setCellValue(expenseId.getProjectMembers().getProjects().getProjectName());
						createExcelsheet.autoSizeColumn(5);
					}
				}
			}
			if(null != expenseId.getClientName()){
				writeRow.createCell(6).setCellValue(expenseId.getClientName());
				createExcelsheet.autoSizeColumn(6);
			
			}
			if(null != expenseId.getPurpose()){
				writeRow.createCell(7).setCellValue(expenseId.getPurpose());
				createExcelsheet.autoSizeColumn(7);
			}
			if(null != expenseId.getStartDate()){
				expenseId.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenseId.getStartDate()));
				writeRow.createCell(8).setCellValue(expenseId.getStartDateStr());
				createExcelsheet.autoSizeColumn(8);
			}
			if(null != expenseId.getEndDate()){
				expenseId.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLoc.getDateFormat(),expenseId.getEndDate()));
				writeRow.createCell(9).setCellValue(expenseId.getEndDateStr());
				createExcelsheet.autoSizeColumn(9);
			}
			if(null != expenseId.getNoOfExpenses()){
				writeRow.createCell(10).setCellValue(expenseId.getNoOfExpenses());
				createExcelsheet.autoSizeColumn(10);
			}
			if(expenseId.getTotalAmount() != 0){
				StringBuilder str = new StringBuilder();
				BigDecimal bd = new BigDecimal(Double.toString(expenseId.getTotalAmount()));
				bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP );                
                writeRow.createCell(11).setCellValue(str.append(bd).toString());
                createExcelsheet.autoSizeColumn(11);
			}
			if(null != expenseId.getExpenseStatus()){
				ExpensesApproverHierarchy  eah = getExpensesApproverHierarchyByApproverUserIdAndExpenseId(loginUser.getId(), expenseId.getId(),orgLoc.getId());
			    Submission_Status submissionStatus = eah.getExpenseStatus();
				writeRow.createCell(12).setCellValue(submissionStatus.getValue());
				createExcelsheet.autoSizeColumn(12);
			}
			if(null != ex.getActionPerformedBy()){
				writeRow.createCell(13).setCellValue(ex.getActionPerformedBy());
				createExcelsheet.autoSizeColumn(13);
			}
		}
	return createExcelsheet;
}

	@Override
	public  Boolean isTheExpenseTotallyApproved(Long expenseId){
		return expensesApprovalDao.isTheExpenseTotallyApproved(expenseId);
	}


	@Override
	public ExpensesApproverHierarchy getexpensesApproverHierarchyForEmployeeByExpenseId(Long expenseId) {
		return expensesApprovalDao.getexpensesApproverHierarchyForEmployeeByExpenseId(expenseId);
	}

	@Override
	public void updateExpenseStatusByExpenseId(Long expenseId, String status) {
		 expensesApprovalDao.updateExpenseStatusByExpenseId(expenseId, status);
		
	}
	
	@Override
	public ExpensesApproverHierarchy getExpensesApproverHierarchyByApproverUserIdAndExpenseId(Long approverUserId,
			Long expenseId,Long locationsId) throws Exception {
		return expensesApprovalDao.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(approverUserId, expenseId,locationsId);
	}


	@Override
	public User updateExpenseStatusByExpenseIdAndReOpenReason(Long expenseId, String reason, User loginUser,
			String type, String isSuperUser) {
		return expensesApprovalDao.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId, reason, loginUser, type, isSuperUser);
	}

	@Override
	public void sendReopenExpenseAcceptOrRejectMail(Expenses expenses, String reason, User loginUser, OrgLocations orgLoc, String type, User reopenedUser, String projectName) throws Exception {
		
				 EmailScenario emailScenario = null;
				 EmailScenario  notificationScenario = null;
				 EmailMessage emailMessage = new EmailMessage();
				 emailMessage.setFirstName(reopenedUser.getFirstName());
				 emailMessage.setLastName(reopenedUser.getLastName());
				 emailMessage.setToEmail(reopenedUser.getEmail());
				 
				 emailMessage.setFromEmail(orgLoc.getUser().getEmail());
				 emailMessage.setCreatedBy(loginUser.getUserCode());
				 emailMessage.setFromName(orgLoc.getUser().getFirstName());
				 
				 
				 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

				 //ProjectMembers projectMembers = projectMembersDao.getgetProjectMemberById(expenseId);
				 
				//setting the class filed values
				 
				 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 globalVarsMap.put("employeeUser",expenses.getProjectMembers().getEmployee().getUser().getFirstName());
				 globalVarsMap.put("approverUser",reopenedUser.getFirstName());
				 globalVarsMap.put("reopenUser",orgLoc.getUser().getFirstName());
				 globalVarsMap.put("projectName", projectName);
				 globalVarsMap.put("clientName",expenses.getClientName());
				 
				 if(null != expenses.getExpenseId()){
					 if(null != expenses.getStartDate()){
						 expenses.setStartDateStr(expenses.getStartDate().toString());
				 globalVarsMap.put("startDate",expenses.getStartDateStr());
					 }
				 }	 
				 if(null != expenses.getExpenseId()){
					 if(null != expenses.getEndDate()){
						 expenses.setEndDateStr(expenses.getEndDate().toString());
				 globalVarsMap.put("endDate",expenses.getEndDateStr());
					 }
				 }
				 globalVarsMap.put("amount",Float.toString(expenses.getTotalAmount()));
				 globalVarsMap.put("reason",reason);
				 
				 globalVarsMap.put("loginURL","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
			
				 //setting the Recipient information
				 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
				 
				 //getting the current EmailScenario by EmailScenario unique name and entity type
				 if(null != type && "accept".equalsIgnoreCase(type)){
					 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_REOPEN_APPROVED_EXPENSES_EMAIL_TO_APPROVERS,Constants.ENTITY_TYPE_EMAIL);
					 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_REOPEN_APPROVED_EXPENSES_EMAIL_TO_APPROVERS,Constants.ENTITY_TYPE_NOTIFICATION);
				 }if(null != type && "reject".equalsIgnoreCase(type)){
					 emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_REOPEN_REJECT_EXPENSES_EMAIL_TO_APPROVERS,Constants.ENTITY_TYPE_EMAIL);
					 notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_REOPEN_REJECT_EXPENSES_EMAIL_TO_APPROVERS,Constants.ENTITY_TYPE_NOTIFICATION); 
				 }
				 
				
				 //covert subject,body and signature
				 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
				 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
				 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
				 String url = sourceleadUrl;
				 
				 //construct HTML template using required data
				 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLoc.getOrg(), orgLoc);
				 emailMessage.setSubject(subject);
				 emailMessage.setMessageBody(emailTemplate);
				 
				 //calling the sendEmail method of emailScenerioUtil
				 emailScenerioUtil.sendEmail(emailMessage,orgLoc.getId(),iAdressArray,new ArrayList<EmailAttachment>());
				
				//calling the sendNotification method of emailScenerioUtil
				String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
				notificationScenario.setBody(notificationBody);
				String actionLink = "";
				emailScenerioUtil.sendNotification(actionLink, orgLoc.getUser(), reopenedUser, orgLoc, notificationScenario);

		}

	
}