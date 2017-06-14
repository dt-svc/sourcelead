
package com.bis.sourcelead.service.impl;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ExpensesApprovalDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesApprovalService;
import com.bis.sourcelead.service.ExpensesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
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
	private CommonUtil commonUtil;
	
	@Autowired
	private TimeZoneService timeZoneService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExpensesApprovalServiceImpl.class);
	
	
	@Override
	public List<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(User loginUser, OrgLocations orgLoc, String expenseStatus, String startDate, String endDate) throws Exception {
		Date endDateUTC = null;
		Date startDateUTC = null;
		
		Timezone timezone = timeZoneService.getTimezone(orgLoc.getTimezone().getId());
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
		DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
		if(StringUtils.isNotEmpty(startDate)){
			Date startTime = commonUtil.getDateFormat(startDate);
			String customStartTimeStr = TZ_DF_CSD.format(startTime);
			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
			startDateUTC = TZ_DF_TS.parse(startDateString);
		}
		
		if(StringUtils.isNotEmpty(endDate)){
			Date endTime = commonUtil.getDateFormat(endDate);
			String customEndTimeStr = TZ_DF_CED.format(endTime);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
			endDateUTC = TZ_DF_TS.parse(endDateString);
		}
		List<ExpensesApproverHierarchy> expApperoverHierarchy = expensesApprovalDao.getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(loginUser.getId(),orgLoc.getId(), expenseStatus, startDateUTC, endDateUTC);
		for(ExpensesApproverHierarchy expensesApproverHierarchy : expApperoverHierarchy){
			
			if(null != expensesApproverHierarchy.getExpenseId().getEndDate()){
				expensesApproverHierarchy.setExpenseStartDateString(Constants.DMY.format(expensesApproverHierarchy.getExpenseId().getStartDate()));
				expensesApproverHierarchy.setExpenseEndDateString(Constants.DMY.format(expensesApproverHierarchy.getExpenseId().getEndDate()));
			}else{
				expensesApproverHierarchy.setExpenseStartDateString(Constants.DMY.format(expensesApproverHierarchy.getExpenseId().getStartDate()));
			}
			
			if (null != expensesApproverHierarchy.getExpenseId()) {
				if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers()) {
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects()){
								expensesApproverHierarchy.setExpensesProjectName(expensesApproverHierarchy.getExpenseId().getProjectMembers().getProjects().getProjectName());
							}else{
								expensesApproverHierarchy.setExpensesProjectName("--");
							}
					
					if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee()) {
						
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId() && !"".equalsIgnoreCase(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId())){
								expensesApproverHierarchy.setExpensesEmployeeUserId(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getEmployeeId());
							}else{
								expensesApproverHierarchy.setExpensesEmployeeUserId("--");
							}
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUserEmploymentType() && !"".equalsIgnoreCase(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUserEmploymentType())){
								String empType = expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUserEmploymentType();
								if(empType.equalsIgnoreCase("C2C") || empType.equalsIgnoreCase("C2C-1099")){
									OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getOrgLocations().getId(), expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getOrgRelationId().getId());
									expensesApproverHierarchy.setUserTypeVendorName(orgRelation.getResponderLocationId().getOrg().getOrgName());
									expensesApproverHierarchy.setExpenseEmpUserType(empType);
								 }else{
									 expensesApproverHierarchy.setExpenseEmpUserType(empType);
								 }
							}else{
								expensesApproverHierarchy.setExpenseEmpUserType("--");
							}
						
						if (null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser()) {
							if(null != expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName()){
								StringBuilder firstLastName = new StringBuilder();
								expensesApproverHierarchy.setExpenseEmpUserFirstNameLastName(firstLastName.append(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getFirstName()).append("").append(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getLastName()).toString());
							}else{
								expensesApproverHierarchy.setExpenseEmpUserFirstNameLastName(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getFirstName());	
							}
								expensesApproverHierarchy.setExpenseEmpUserEmail(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getEmail());
								expensesApproverHierarchy.setExpensesUserid(expensesApproverHierarchy.getExpenseId().getProjectMembers().getEmployee().getUser().getId());
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
	public File getExpensesApproverHierarchyListForPdf(List<ExpensesApproverHierarchy> expApperoverHierarchy, OrgLocations orgLoc) throws IOException {
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
	        userStyle.append(orgLoc.getOrg().getOrgName()).append(" ").append("- Employee Expenses");
	       
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
	        java.util.List<String[]> expenseList = prepareExpenseApprovalListStringArray(expApperoverHierarchy,headers);
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
private List<String[]> prepareExpenseApprovalListStringArray(List<ExpensesApproverHierarchy> expApperoverHierarchy, String[] headers) {
	
	List<String[]> arrayList = new ArrayList<>();
	for(ExpensesApproverHierarchy ex:expApperoverHierarchy){
		String expenseArray[] = new String[headers.length];
		if(ex.getExpenseId() !=null){
			Expenses expenseId =expensesService.getExpenseByExpenseId(ex.getExpenseId().getId());
			if(null != expenseId.getProjectMembers()){
				if(null != expenseId.getProjectMembers().getEmployee()){
					if(null != expenseId.getProjectMembers().getEmployee().getUser()){
						if(null != expenseId.getProjectMembers().getEmployee().getUser().getFirstName()){
							expenseArray[0] = expenseId.getProjectMembers().getEmployee().getUser().getFirstName();
						}else{
							expenseArray[0] = "--";
						}
						if(null != expenseId.getProjectMembers().getEmployee().getUser().getEmail()){

							expenseArray[1] = expenseId.getProjectMembers().getEmployee().getUser().getEmail();
						}else{
							expenseArray[1] = "--";
						}
						if(null != expenseId.getProjectMembers().getEmployee().getUser().getMobilePhone()){

							expenseArray[2] = expenseId.getProjectMembers().getEmployee().getUser().getMobilePhone();
						}else{
							expenseArray[2] = "--";
						}
						
					}
					if(null != expenseId.getProjectMembers().getEmployee().getUserEmploymentType()){

						expenseArray[3] = expenseId.getProjectMembers().getEmployee().getUserEmploymentType();
					}else{
						expenseArray[3] = "--";
					}
					if((null != expenseId.getProjectMembers().getEmployee().getUserEmploymentType())
							&& ((expenseId.getProjectMembers().getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C") ||
									(expenseId.getProjectMembers().getEmployee().getUserEmploymentType().equalsIgnoreCase("C2C-1099")) ))){
						 OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(expenseId.getProjectMembers().getEmployee().getOrgLocations().getId(), expenseId.getProjectMembers().getEmployee().getOrgRelationId().getId());
						expenseArray[4] = orgRelation.getResponderLocationId().getOrg().getOrgName();
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
				expenseId.setStartDateStr(Constants.DF_DMY.format(expenseId.getStartDate()));
				expenseArray[8] = expenseId.getStartDateStr();
			}else{
				expenseArray[8] = "--";
			}
			
			if(null != expenseId.getEndDate()){
				expenseId.setEndDateStr(Constants.DF_DMY.format(expenseId.getEndDate()));
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
				expenseArray[11] = str.append(expenseId.getTotalAmount()).toString();
			}else{
				expenseArray[11] = "--";
			}
			if(null != expenseId.getStatus()){
				expenseArray[12] = expenseId.getExpenseStatus().getValue();
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
		OrgLocations orgLoc) throws IOException {
	
	File file = null;
		Workbook createExcelworkbook = new XSSFWorkbook();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Sheet createExcelsheet = createExcelworkbook.createSheet(orgLoc.getOrg().getOrgName()+ "_" + dateFormat.format(new Date()));
		Row writeRow = null;
		TimeSheetSubmissionCount requirementSubCount=null;
		createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,requirementSubCount);
		int column = 0;
		for (ExpensesApproverHierarchy expensesList : expApperoverHierarchy) {
			writeRow = createExcelsheet.createRow(column+1);
			createExcelsheet = createXlsSheet(createExcelsheet, writeRow, expensesList);
			column++;
		}
		 try {
			file = File.createTempFile(orgLoc.getOrg().getOrgName() + "_ExpensesApproval_" + dateFormat.format(new Date()),".xls");
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

private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, ExpensesApproverHierarchy ex) {
		if(ex.getExpenseId() !=null){
			Expenses expenseId =expensesService.getExpenseByExpenseId(ex.getExpenseId().getId());
			if(null != expenseId.getProjectMembers()){
				if(null != expenseId.getProjectMembers().getEmployee()){
					if(null != expenseId.getProjectMembers().getEmployee().getUser()){
						if(null != expenseId.getProjectMembers().getEmployee().getUser().getFirstName()){
							writeRow.createCell(0).setCellValue(expenseId.getProjectMembers().getEmployee().getUser().getFirstName());
							createExcelsheet.autoSizeColumn(0);
						}
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
						 writeRow.createCell(4).setCellValue(orgRelation.getResponderLocationId().getOrg().getOrgName());
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
				expenseId.setStartDateStr(Constants.DF_DMY.format(expenseId.getStartDate()));
				writeRow.createCell(8).setCellValue(expenseId.getStartDateStr());
				createExcelsheet.autoSizeColumn(8);
			}
			if(null != expenseId.getEndDate()){
				expenseId.setEndDateStr(Constants.DF_DMY.format(expenseId.getEndDate()));
				writeRow.createCell(9).setCellValue(expenseId.getEndDateStr());
				createExcelsheet.autoSizeColumn(9);
			}
			if(null != expenseId.getNoOfExpenses()){
				writeRow.createCell(10).setCellValue(expenseId.getNoOfExpenses());
				createExcelsheet.autoSizeColumn(10);
			}
			if(expenseId.getTotalAmount() != 0){
                StringBuilder str = new StringBuilder();
                writeRow.createCell(11).setCellValue(str.append(expenseId.getTotalAmount()).toString());
                createExcelsheet.autoSizeColumn(11);
			}
			if(null != expenseId.getExpenseStatus()){
				writeRow.createCell(12).setCellValue(expenseId.getExpenseStatus().getValue());
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
			Long expenseId) throws Exception {
		return expensesApprovalDao.getExpensesApproverHierarchyByApproverUserIdAndExpenseId(approverUserId, expenseId);
	}


	@Override
	public User updateExpenseStatusByExpenseIdAndReOpenReason(Long expenseId, String reason, User loginUser,
			String type, String isSuperUser) {
		return expensesApprovalDao.updateExpenseStatusByExpenseIdAndReOpenReason(expenseId, reason, loginUser, type, isSuperUser);
	}

	
}