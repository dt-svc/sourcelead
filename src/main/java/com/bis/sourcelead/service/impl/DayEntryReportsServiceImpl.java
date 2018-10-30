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
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.DayEntryReportsDao;
import com.bis.sourcelead.dao.TimesheetActivityDao;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.TimesheetActivities;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DayEntryReportsService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimeSheetDayEntriesService;
import com.bis.sourcelead.util.CommonUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class DayEntryReportsServiceImpl implements DayEntryReportsService{

	private static final Logger logger = LoggerFactory.getLogger(DayEntryReportsServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ProjectMembersService projectMemberService;
	
	@Autowired
	private TimesheetActivityDao timesheetActivityDao;
	
	@Autowired
	private DayEntryReportsDao dayEntryReportsDao;
	
	@Override
	public File exportDayEntryReportsWithoutProjectExcel(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc, User loginUser,String isGlobalUser) throws Exception {
		 File file = null;
		 	Long[]  countTotal = new Long[2];
		 	 countTotal[0] = 0l;
			 countTotal[1] = 0l;
			if(dayWiseTimesheetReoprtsDto.getStartDate() != null && dayWiseTimesheetReoprtsDto.getEndDate()!= null) {
				Workbook createExcelworkbook = new XSSFWorkbook();
				DateFormat DF_YMD  = new SimpleDateFormat(orgloc.getDateFormat());
				SimpleDateFormat WEEK_DAY =  new SimpleDateFormat("EEEE"); 
				String weekDayName = "";
				String status ="";
				Row writeRow = null;
				int rowCount = 0;
				Sheet createExcelsheet = createExcelworkbook.createSheet("Day wise Project wise Time sheet Report");
				 Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
				 Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
							 ResponseList<DayWiseTimesheetReoprtsListDTO> dayEntryList = dayEntryReportsDao.getDayWiseReportListBasedOnProjectListIdStartDateEndDate(dayWiseTimesheetReoprtsDto.getProjectIds(), startDate, endDate,dayWiseTimesheetReoprtsDto.getSearchValue(),dayWiseTimesheetReoprtsDto.getStart(),dayWiseTimesheetReoprtsDto.getLength(),orgloc,loginUser,isGlobalUser,dayWiseTimesheetReoprtsDto.getProjectMemberListId());
							 if(dayEntryList != null && !dayEntryList.getData().isEmpty()) {
								 for(DayWiseTimesheetReoprtsListDTO dayEntries:dayEntryList.getData()) {
								    	 writeRow = createExcelsheet.createRow(rowCount+6);
								    	 		createExcelsheet =  createXlsSheet(createExcelworkbook,createExcelsheet,DF_YMD,WEEK_DAY,weekDayName,status,writeRow,dayEntries,orgloc,countTotal[0],countTotal[1]);
								    	 		rowCount++;
											}
										}
				createExcelsheet = billableAndNonBillabeTotalHeaderCount("0","0",createExcelsheet, writeRow, createExcelworkbook,dayWiseTimesheetReoprtsDto);
				file = File.createTempFile(orgloc.getOrg().getOrgName() + "dayWiseTimesheetReport" + commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), new Date()), ".xls");
				FileOutputStream fos = new FileOutputStream(file);
				try {
					createExcelworkbook.write(fos);
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return file;
	}

		/*@Override
		public File exportDayEntryReportsWithProjectExcel(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc,User loginUser,String isGlobalUser) throws Exception {
		 File file = null;
		if(dayWiseTimesheetReoprtsDto.getStartDate() != null && dayWiseTimesheetReoprtsDto.getEndDate()!= null) {
			Workbook createExcelworkbook = new XSSFWorkbook();
			Row writeRow = null;
			Long[]  countTotal = new Long[2];
			countTotal[0] = 0l;
		    countTotal[1] = 0l; 
			Sheet createExcelsheet = createExcelworkbook.createSheet("Day wise Project wise Time sheet Report");
			int rowCount = 0;
			if(dayWiseTimesheetReoprtsDto.getProjectIds() != null && !dayWiseTimesheetReoprtsDto.getProjectIds().isEmpty()) {
					Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
					Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
					ResponseList<DayWiseTimesheetReoprtsListDTO> dayEntryListData = dayEntryReportsDao.getDayWiseReportListBasedOnProjectListIdStartDateEndDate(dayWiseTimesheetReoprtsDto.getProjectIds(), startDate, endDate,dayWiseTimesheetReoprtsDto.getSearchValue(),dayWiseTimesheetReoprtsDto.getStart(),dayWiseTimesheetReoprtsDto.getLength(),orgloc,loginUser,isGlobalUser,null);
									if(dayEntryListData != null && !dayEntryListData.getData().isEmpty()) {
											    Date loopdate = null;
											    for(DayWiseTimesheetReoprtsListDTO dayEntries:dayEntryListData.getData()) {
											    	 writeRow = createExcelsheet.createRow(rowCount+6);
											    	 Map<Sheet,Long[]> dataWithTotal = createXlsSheet(createExcelsheet, writeRow,dayEntries,loopdate,orgloc,countTotal[0],countTotal[1]);
											    	 	 for (Long[] totalHours : dataWithTotal.values()) {
												    		 countTotal[0] = totalHours[0];
												    		 countTotal[1] = totalHours[1];
												 			
												 		}
											    	 	rowCount++;														  
														loopdate = dayEntries.getTimesheetDate();
														}
													}
			createExcelsheet = billableAndNonBillabeTotalHeaderCount(convertMinuteToHours(countTotal[0],null),convertMinuteToHours(countTotal[1],null),createExcelsheet, writeRow, createExcelworkbook,dayWiseTimesheetReoprtsDto);
			file = File.createTempFile(orgloc.getOrg().getOrgName() + "dayWiseTimesheetReport" + commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), new Date()), ".xls");
			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	return file;
}*/
		private Sheet createXlsSheet(Workbook workbook,Sheet createExcelsheet,DateFormat DF_YMD,SimpleDateFormat WEEK_DAY,String weekDayName,String status, Row writeRow, DayWiseTimesheetReoprtsListDTO dayEntries, OrgLocations orgloc, Long totalBillable, Long totalNonbillable) throws Exception {
				weekDayName = WEEK_DAY.format(dayEntries.getTimesheetDate());
				if (dayEntries.getTimesheetDate() != null) {
					writeRow.createCell(0).setCellValue(DF_YMD.format(dayEntries.getTimesheetDate()));
				}
				if (weekDayName != null && !"".equalsIgnoreCase(weekDayName)) {
					writeRow.createCell(1).setCellValue(weekDayName);
				}
				if (dayEntries.getEmployeeName() != null) {
						writeRow.createCell(2).setCellValue(dayEntries.getEmployeeName());
				}
				
				if (dayEntries.getEmployeeEmail() != null) {
					writeRow.createCell(3).setCellValue(dayEntries.getEmployeeEmail());
				}
				
				if (dayEntries.getDepartmentName()!= null) {
							writeRow.createCell(4).setCellValue(dayEntries.getDepartmentName());
				}else {
						writeRow.createCell(4).setCellValue("");
				}
				
				if (dayEntries.getTicketNo()!= null) {
					writeRow.createCell(5).setCellValue(dayEntries.getTicketNo());
				}
				
				if (dayEntries.getProjectName() != null) {
					writeRow.createCell(6).setCellValue(dayEntries.getProjectName());
				}
				
				if (dayEntries.getTask() != null) {
					writeRow.createCell(7).setCellValue(dayEntries.getTask());
				}
				if (dayEntries.getBillableHrs() != null) {
					CellStyle style = workbook.createCellStyle();
					style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
					writeRow.createCell(8).setCellStyle(style);
					if(StringUtils.isNotEmpty(dayEntries.getBillableHrs())) {
						String[] hours = dayEntries.getBillableHrs().split(":");
						if(hours != null && hours.length > 1) {
							String billableHrs = hours[0]+"."+Math.round((Integer.parseInt(hours[1])*5)/3);
							writeRow.createCell(8).setCellValue(Float.parseFloat(billableHrs));
						}else {
							writeRow.createCell(8).setCellValue(Float.parseFloat("0"));
						}
						
					}else {
						writeRow.createCell(8).setCellValue(Float.parseFloat("0"));
					}
					
				}
				if (dayEntries.getNonBillableHrs() != null) {
					CellStyle style = workbook.createCellStyle();
					style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
					writeRow.createCell(9).setCellStyle(style);
					if(StringUtils.isNotEmpty(dayEntries.getNonBillableHrs())) {
						String[] hours = dayEntries.getNonBillableHrs().split(":");
						if(hours != null && hours.length > 1) {
							String nonbillableHrs = hours[0]+"."+Math.round((Integer.parseInt(hours[1])*5)/3);
							writeRow.createCell(9).setCellValue(Float.parseFloat(nonbillableHrs));
						}else {
							writeRow.createCell(9).setCellValue(Float.parseFloat("0"));
						}
						
					}else {
						writeRow.createCell(9).setCellValue(Float.parseFloat("0"));
					}
					
			    }
				if (dayEntries.getStatus() != null) {
					writeRow.createCell(10).setCellValue(dayEntries.getStatus());
				}
				if (dayEntries.getComments() != null) {
					writeRow.createCell(11).setCellValue(dayEntries.getComments());
				}
				if(StringUtils.isNotEmpty(dayEntries.getStatus())) {
					if(dayEntries.getStatus().equalsIgnoreCase("submitted for approval")) {
						status =  "submitted";
					}else {
						status = dayEntries.getStatus();
					}
				}
				//Map<String,Date>dateMap = getSubmittedAndApprovedDate(dayEntries.getProjectId().longValue(),dayEntries.getMasterCandidateId().longValue(),dayEntries.getSahFrequenchStartDate(),dayEntries.getSahFrequenchEndDate(),status);
				String submittedDate =  null;
				String approvedDate =  null;
				/*if(dateMap != null && !dateMap.isEmpty()) {
					if(dateMap.get("SUBMITTED") != null) {
						submittedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dateMap.get("SUBMITTED"));
					}
					
                    if(dateMap.get("APPROVED") != null) {
                    	approvedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dateMap.get("APPROVED")); 
					}
				}else {*/
					if(StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("SUBMITTEDFORAPPROVAL")) {
						if(dayEntries.getSubmittedDate() != null)
						 submittedDate = DF_YMD.format(dayEntries.getSubmittedDate());
						//commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dayEntries.getSubmittedDate());
					}else if(StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("APPROVED")){
						if(dayEntries.getSubmittedDate() != null)
						  submittedDate = DF_YMD.format(dayEntries.getSubmittedDate());
						if(dayEntries.getApprovedDate() != null)
							approvedDate = DF_YMD.format(dayEntries.getApprovedDate());
					}
				//}
				writeRow.createCell(12).setCellValue(submittedDate);
				writeRow.createCell(13).setCellValue(approvedDate);
				if (dayEntries.getCandidateLocation() != null) {
					writeRow.createCell(14).setCellValue(dayEntries.getCandidateLocation());
				}
				
			return createExcelsheet;
		}
		
		private Sheet billableAndNonBillabeTotalHeaderCount(String billable, String nonBillable,Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto) {
			
			CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
			 writeRow = createExcelsheet.createRow(1);
			 createExcelsheet.setColumnWidth(0, 10000);
			 createExcelsheet.setColumnWidth(1, 10000);
			 
			 Cell cell1 = writeRow.createCell(0);
			 cell1.setCellValue("From Date :");
			 cell1.setCellStyle(style);
			 
			 Cell cell2 = writeRow.createCell(1);
			 cell2.setCellValue(dayWiseTimesheetReoprtsDto.getStartDate());
			 cell2.setCellStyle(style);
			 
			 /*createExcelsheet.setColumnWidth(3, 10000);
			 createExcelsheet.setColumnWidth(4, 10000);
			 
			 Cell cell11 = writeRow.createCell(3);
			 cell11.setCellValue("Billable Total Hours :");
			 cell11.setCellStyle(style);
			 
			 Cell cell21 = writeRow.createCell(4);
			 cell21.setCellValue(billable);
			 cell21.setCellStyle(style);*/
			 
			 
			 writeRow = createExcelsheet.createRow(2);
			 createExcelsheet.setColumnWidth(0, 10000);
			 createExcelsheet.setColumnWidth(1, 10000);
			 
			 Cell cell3 = writeRow.createCell(0);
			 cell3.setCellValue("To Date :");
			 cell3.setCellStyle(style);
			 
			 Cell cell4 = writeRow.createCell(1);
			 cell4.setCellValue(dayWiseTimesheetReoprtsDto.getEndDate());
			 cell4.setCellStyle(style);
			 
			 /*createExcelsheet.setColumnWidth(3, 10000);
			 createExcelsheet.setColumnWidth(4, 10000);
			 
			 Cell cell31 = writeRow.createCell(3);
			 cell31.setCellValue("Non Billable Total Hours :");
			 cell31.setCellStyle(style);
			 
			 Cell cell41 = writeRow.createCell(4);
			 cell41.setCellValue(nonBillable);
			 cell41.setCellStyle(style);*/
			 String headers[] = Constants.DAYWISE_TIMESHEET_REPORTS;
			 createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 5);
			return createExcelsheet;
			
		}
		

		@Override
		public File exportDayEntryReportsWithProjectPdf(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc, User loginUser,String isGlobalUser)throws Exception {

			String[] headers = Constants.DAYWISE_TIMESHEET_REPORTS;
		    Document document = new Document(PageSize.LETTER.rotate());

		    StringBuilder sbUser =  new StringBuilder();
		    sbUser.append("DayWiseTimeSheetReports_").append(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(),new Date()));

		    StringBuilder fileSB = new StringBuilder();
		    File file = File.createTempFile(fileSB.append("DayWiseTimeSheetReports_").append(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
		    long totalBillable = 0l;
		    long totalNonbillable = 0l; 
		    try {
		        // Get an instance of PdfWriter and create a Table.pdf file as an output.
		        PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();

		        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		        BaseColor myColor = WebColors.getRGBColor("#000000");
		        boldFont.setColor(myColor);
		        StringBuilder userStyle = new StringBuilder();
		        userStyle.append(orgloc.getOrgLocationName()).append(" ");
	          
	            com.itextpdf.text.List orderedList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
	            orderedList.add(new ListItem("From Date:"+dayWiseTimesheetReoprtsDto.getStartDate()));
		        orderedList.add(new ListItem("To Date:"+dayWiseTimesheetReoprtsDto.getEndDate()));
		        //document.add(orderedList);
		        // Create an instance of PdfPTable. After that we transform the header and
		        PdfPTable table = new PdfPTable(headers.length);
		        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
		        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
		        cell1.setColspan(17);
		        headerCellStyle(cell1);
		        table.setWidthPercentage(100);
	            table.setWidths(new int[]{5, 3, 3,5,3, 5, 5,5,5,5,3,5,3,3,5});
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
		        java.util.List<String[]> dayList = new ArrayList<>();
		        Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
				Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
		        ResponseList<DayWiseTimesheetReoprtsListDTO> dayEntryList = dayEntryReportsDao.getDayWiseReportListBasedOnProjectListIdStartDateEndDate(dayWiseTimesheetReoprtsDto.getProjectIds(), startDate, endDate,dayWiseTimesheetReoprtsDto.getSearchValue(),dayWiseTimesheetReoprtsDto.getStart(),dayWiseTimesheetReoprtsDto.getLength(),orgloc,loginUser,isGlobalUser,dayWiseTimesheetReoprtsDto.getProjectMemberListId());
							 if(dayEntryList != null && !dayEntryList.getData().isEmpty()) {
								 for(DayWiseTimesheetReoprtsListDTO dayEntries:dayEntryList.getData()) {
									 			dayList.add(prepareDaywiseReportPdfData(headers,dayEntries,orgloc,totalBillable,totalNonbillable));
									}
								}
				/*orderedList.add(new ListItem("Billable Total Hours: "+convertMinuteToHours(totalBillable,null)));
		        orderedList.add(new ListItem("Non Billable Total Hours: "+convertMinuteToHours(totalNonbillable,null)));*/
		        document.add(orderedList);
		        for (String[] datTimeArray: dayList) {
	                for (int i = 0; i < datTimeArray.length; i++) {
	                    String datum = datTimeArray[i];
	                    PdfPCell cell = new PdfPCell();
	                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
	                    table.addCell(cell);
	                }
	                table.completeRow();
	            }
		        document.addTitle("DayWiseTimeSheetReport Table");
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
		
		/*public File exportDayEntryReportsWithoutProjectPdf(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc, User loginUser,String isGlobalUser) throws Exception {
			long totalBillable = 0l;
		    long totalNonbillable = 0l; 
			String[] headers = Constants.DAYWISE_TIMESHEET_REPORTS;
		    Document document = new Document(PageSize.LETTER.rotate());

		    StringBuilder sbUser =  new StringBuilder();
		    sbUser.append("DayWiseTimeSheetReports_").append(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(),new Date()));

		    StringBuilder fileSB = new StringBuilder();
		    File file = File.createTempFile(fileSB.append("DayWiseTimeSheetReports_").append(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(),new Date())).toString(),".pdf");
		    file.setReadable(true);
		    
		    try {
		        // Get an instance of PdfWriter and create a Table.pdf file as an output.
		        PdfWriter.getInstance(document, new FileOutputStream(file));
		        document.open();

		        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		        BaseColor myColor = WebColors.getRGBColor("#000000");
		        boldFont.setColor(myColor);
		        StringBuilder userStyle = new StringBuilder();
		        userStyle.append(orgloc.getOrgLocationName());

	            com.itextpdf.text.List orderedList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
		        orderedList.add(new ListItem("From Date:"+dayWiseTimesheetReoprtsDto.getStartDate()));
		        orderedList.add(new ListItem("To Date:"+dayWiseTimesheetReoprtsDto.getEndDate()));
		        //document.add(orderedList);
		        // Create an instance of PdfPTable. After that we transform the header and
		        PdfPTable table = new PdfPTable(headers.length);
		        Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
		        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
		        cell1.setColspan(15);
		        headerCellStyle(cell1);
		        table.setWidthPercentage(100);
	            table.setWidths(new int[]{3, 5, 3,5,3, 5, 5,5,5,5,3,5,3,3});
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
		        java.util.List<String[]> dayList = new ArrayList<>();
		        List<Long> projectIds = new ArrayList<Long>();
		        
		        Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
				Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
		        for(Long projectMemberId:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
					 ProjectMembers projectMember = projectMemberService.getProjectMemberById(projectMemberId);
					 if(projectMember!=null) {
						 List<ProjectMembers> projectMemberList = projectMemberService.getProjectMembersListByOrgIdAndMasterCandidateId(orgloc.getOrg().getId(),projectMember.getMasterCandidateId().getId());
						 projectMemberList.forEach(pm ->projectIds.add(pm.getProjects().getId()));
						 if(projectMemberList!= null && !projectMemberList.isEmpty()) {
							 ResponseList<DayWiseTimesheetReoprtsListDTO> dayEntryList = dayEntryReportsDao.getDayWiseReportListBasedOnProjectListIdStartDateEndDate(projectIds, startDate, endDate,dayWiseTimesheetReoprtsDto.getSearchValue(),dayWiseTimesheetReoprtsDto.getStart(),dayWiseTimesheetReoprtsDto.getLength(),orgloc,loginUser,isGlobalUser,dayWiseTimesheetReoprtsDto.getProjectMemberListId());
							 if(dayEntryList != null && !dayEntryList.getData().isEmpty()) {
								 for(DayWiseTimesheetReoprtsListDTO dayEntries:dayEntryList.getData()) {
									 			dayList.add(prepareDaywiseReportPdfData(headers,dayEntries,orgloc,totalBillable,totalNonbillable));
									}
								}
						 }
					}
				}
				orderedList.add(new ListItem("Billable Total Hours: "+convertMinuteToHours(totalBillable,null)));
		        orderedList.add(new ListItem("Non Billable Total Hours: "+convertMinuteToHours(totalNonbillable,null)));
		        document.add(orderedList);
		        for (String[] datTimeArray: dayList) {
	                for (int i = 0; i < datTimeArray.length; i++) {
	                    String datum = datTimeArray[i];
	                    PdfPCell cell = new PdfPCell();
	                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
	                    table.addCell(cell);
	                }
	                table.completeRow();
	            }
		        document.addTitle("DayWiseTimeSheetReport Table");
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
		*/
		private String[] prepareDaywiseReportPdfData(String[] headers, DayWiseTimesheetReoprtsListDTO dayEntries, OrgLocations orgloc, long totalBillable, long totalNonbillable) {
			    String dayArray[] = new String[headers.length];
				String weekDayName;
				SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
				weekDayName = dayFormat.format(dayEntries.getTimesheetDate());
				if (dayEntries.getTimesheetDate() != null) {
					dayArray[0] = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dayEntries.getTimesheetDate());
				}
				if (weekDayName != null && !"".equalsIgnoreCase(weekDayName)) {
					dayArray[1]=(weekDayName);
				}
				if(dayEntries.getEmployeeName()!=null) {
					dayArray[2]= dayEntries.getEmployeeName();
				}
				if (dayEntries.getEmployeeEmail()!= null) {
					dayArray[3]= dayEntries.getEmployeeEmail();
				}
				if (dayEntries.getDepartmentName()!= null) {
					dayArray[4]=dayEntries.getDepartmentName();
				}
				if (dayEntries!= null) {
					dayArray[5]=dayEntries.getTicketNo();
				}
				if (dayEntries.getProjectName() != null) {
					dayArray[6]= dayEntries.getProjectName();
				}
				if (dayEntries.getTask() != null) {
					dayArray[7]=dayEntries.getTask();
				}
				if (dayEntries.getBillableHrs() != null) {
					dayArray[8]=dayEntries.getBillableHrs();
					 totalBillable = getTotalHoursFormDayHours(dayEntries.getBillableHrs(), totalBillable);
				}
				if (dayEntries.getNonBillableHrs() != null) {
					dayArray[9]= dayEntries.getNonBillableHrs();
					 totalNonbillable = getTotalHoursFormDayHours(dayEntries.getNonBillableHrs(), totalNonbillable);
				}
				if (dayEntries.getStatus() != null) {
					dayArray[10]= dayEntries.getStatus();
				}
				if (dayEntries.getComments() != null) {
					dayArray[11]=(dayEntries.getComments());
				}
				
				String status = null;
				if(StringUtils.isNotEmpty(dayEntries.getStatus()) && dayEntries.getStatus().equalsIgnoreCase("submitted for approval")) {
					status = "SUBMITTED";
				}else {
					status = dayEntries.getStatus();
				}
				
				String submittedDate =  null;
				String approvedDate =  null;
				
				if(StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("SUBMITTEDFORAPPROVAL")) {
					if(dayEntries.getSubmittedDate() != null)
					submittedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dayEntries.getSubmittedDate());
				}else if(StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("APPROVED")) {
					if(dayEntries.getSubmittedDate() != null)
					submittedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dayEntries.getSubmittedDate());
					if(dayEntries.getApprovedDate() != null)
					approvedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dayEntries.getApprovedDate()); 
				}
			    dayArray[12] = submittedDate;
				dayArray[13] = approvedDate;
				if(dayEntries.getCandidateLocation()!= null) {
					if(dayEntries.getCandidateLocation().endsWith(","))
					{
						dayArray[14]= dayEntries.getCandidateLocation().substring(0,dayEntries.getCandidateLocation().length() - 1);
					}else {
						dayArray[14]= dayEntries.getCandidateLocation();
					}
				}
				return dayArray;
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
	public ResponseList<DayWiseTimesheetReoprtsListDTO> getDayEntriesList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc, User loginUser,String isGlobalUser) throws Exception {
		Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
		Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
		ResponseList<DayWiseTimesheetReoprtsListDTO> timeEntryDataMap = new ResponseList<DayWiseTimesheetReoprtsListDTO>();
			timeEntryDataMap = dayEntryReportsDao.getDayWiseReportListBasedOnProjectListIdStartDateEndDate(dayWiseTimesheetReoprtsDto.getProjectIds(), startDate, endDate,dayWiseTimesheetReoprtsDto.getSearchValue(),dayWiseTimesheetReoprtsDto.getStart(),dayWiseTimesheetReoprtsDto.getLength(),orgloc,loginUser,isGlobalUser,dayWiseTimesheetReoprtsDto.getProjectMemberListId());
	   return timeEntryDataMap;
    }



	private Map<String,Date> getSubmittedAndApprovedDate(Long projectId, Long masterCandidateId, Date frequencyStartDate, Date frequencyEndDate,String status) {
	   return timesheetActivityDao.getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDateAndStatus(projectId, masterCandidateId, frequencyStartDate,frequencyEndDate,status);
	}

	private String convertMinuteToHours(Long totalHours, String actualTimeString) {
		int actualHours = (int) (totalHours / 60);
		int actualMinutes = (int) (totalHours % 60);
		String actualMinutesStr = "" + actualMinutes;
		String startWithZero = "";
		if (StringUtils.isNotEmpty(actualMinutesStr) && actualMinutesStr.length() == 1)
		startWithZero = "0";
		return actualTimeString = actualHours + "." + startWithZero + actualMinutes;
	}

	private Long getTotalHoursFormDayHours(String dayHours, Long total) {
		String hours = "00";
		String min = "00";
		if (StringUtils.isNotEmpty(dayHours)) {
			String[] parts = dayHours.split("\\:");
			hours = parts[0];
			min = parts[1];
		}
		Long localHours = Long.parseLong(hours) * 60;
		Long localHoursMinute = Long.parseLong(min);
		Long finalmin = localHours + localHoursMinute;
		total = total + finalmin;
		return total;
	}
	

}
