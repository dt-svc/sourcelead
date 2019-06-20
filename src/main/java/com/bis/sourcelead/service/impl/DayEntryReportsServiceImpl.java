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
import com.bis.sourcelead.timesheet.dao.TimesheetActivityDao;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimeSheetDayEntriesService;
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
							 ResponseList<DayWiseTimesheetReoprtsListDTO> dayEntryList = dayEntryReportsDao.getDayWiseReportListBasedOnProjectListIdStartDateEndDate(dayWiseTimesheetReoprtsDto.getProjectIds(), startDate, endDate,dayWiseTimesheetReoprtsDto.getSearchValue(),dayWiseTimesheetReoprtsDto.getStart(),dayWiseTimesheetReoprtsDto.getLength(),orgloc,loginUser,isGlobalUser,dayWiseTimesheetReoprtsDto.getProjectMemberListId(),orgloc.getDateFormat());
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
		private Sheet createXlsSheet(Workbook workbook,Sheet createExcelsheet,DateFormat DF_YMD,SimpleDateFormat WEEK_DAY,String weekDayName,String status, Row writeRow, DayWiseTimesheetReoprtsListDTO dayEntries, OrgLocations orgloc, Long totalBillable, Long totalNonbillable) throws Exception {
				Date tDate = commonUtil.stringToDateByOrgLocationFormat(dayEntries.getTimesheetDate(), orgloc.getDateFormat());
				weekDayName = WEEK_DAY.format(tDate);
				int columnCount = 0;
				String timesheetDate = "";
				if (dayEntries.getTimesheetDate() != null) {
					timesheetDate = dayEntries.getTimesheetDate();
				}
				writeRow.createCell(columnCount++).setCellValue(timesheetDate);

				writeRow.createCell(columnCount++).setCellValue(weekDayName != null ? weekDayName : "");
				
				writeRow.createCell(columnCount++).setCellValue(dayEntries.getEmployeeId() != null ? dayEntries.getEmployeeId() : "");
				
				writeRow.createCell(columnCount++).setCellValue(dayEntries.getEmployeeName() != null ? dayEntries.getEmployeeName() : "");
				
				writeRow.createCell(columnCount++).setCellValue(dayEntries.getEmployeeEmail() != null ? dayEntries.getEmployeeEmail() : "");
				
				writeRow.createCell(columnCount++).setCellValue(dayEntries.getDepartmentName()!= null ? dayEntries.getDepartmentName() : "");
				
				writeRow.createCell(columnCount++).setCellValue(dayEntries.getTicketNo()!= null ? dayEntries.getTicketNo() : "");
				
				writeRow.createCell(columnCount++).setCellValue(dayEntries.getProjectName() != null ? dayEntries.getProjectName() : "");
				
				writeRow.createCell(columnCount++).setCellValue(dayEntries.getTask() != null ? dayEntries.getTask() : "");
				
				String getBillableHrs = "0";
				int billableHrsColumn = columnCount++;
				if (dayEntries.getBillableHrs() != null && StringUtils.isNotEmpty(dayEntries.getBillableHrs())) {
					CellStyle style = workbook.createCellStyle();
					style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
					writeRow.createCell(billableHrsColumn).setCellStyle(style);
					String[] hours = dayEntries.getBillableHrs().split(":");
					if(hours != null && hours.length > 1) {
						String billableHrs = hours[0]+"."+Math.round((Integer.parseInt(hours[1])*5)/3);
						getBillableHrs = billableHrs;
					}
				}
				
				writeRow.createCell(billableHrsColumn).setCellValue(Float.parseFloat(getBillableHrs));
				
				String getNonBillableHrs = "0";
				int nonBillableHrsColumn = columnCount++;
				if (dayEntries.getNonBillableHrs() != null && StringUtils.isNotEmpty(dayEntries.getNonBillableHrs())) {
					CellStyle style = workbook.createCellStyle();
					style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
					writeRow.createCell(nonBillableHrsColumn).setCellStyle(style);
					String[] hours = dayEntries.getNonBillableHrs().split(":");
					if(hours != null && hours.length > 1) {
						String nonbillableHrs = hours[0]+"."+Math.round((Integer.parseInt(hours[1])*5)/3);
						getNonBillableHrs = nonbillableHrs;
					}
			    }

				writeRow.createCell(nonBillableHrsColumn).setCellValue(Float.parseFloat(getNonBillableHrs));

				writeRow.createCell(columnCount++).setCellValue(dayEntries.getStatus() != null ? dayEntries.getStatus() : "");

				writeRow.createCell(columnCount++).setCellValue(dayEntries.getComments() != null ? dayEntries.getComments() : "");
				
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
						 submittedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dayEntries.getSubmittedDate());
						
					}else if(StringUtils.isNotEmpty(status) && status.equalsIgnoreCase("APPROVED")){
						if(dayEntries.getSubmittedDate() != null)
						  submittedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dayEntries.getSubmittedDate());
						if(dayEntries.getApprovedDate() != null)
							approvedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dayEntries.getSubmittedDate());
					}
				//}
				writeRow.createCell(columnCount++).setCellValue(submittedDate);

				writeRow.createCell(columnCount++).setCellValue(approvedDate);
				
				writeRow.createCell(columnCount++).setCellValue(dayEntries.getCandidateLocation() != null ? dayEntries.getCandidateLocation() : "");
				
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
		    Document document = new Document(PageSize.A4.rotate());

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

		        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
		        BaseColor myColor = WebColors.getRGBColor("#000000");
		        boldFont.setColor(myColor);
		        StringBuilder userStyle = new StringBuilder();
		        userStyle.append(orgloc.getOrgLocationName()).append(" ");
		        Font orderedListFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL);
	            com.itextpdf.text.List orderedList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
	            orderedList.add(new ListItem("From Date:"+dayWiseTimesheetReoprtsDto.getStartDate(), orderedListFont));
		        orderedList.add(new ListItem("To Date:"+dayWiseTimesheetReoprtsDto.getEndDate(), orderedListFont));
		        //document.add(orderedList);
		        // Create an instance of PdfPTable. After that we transform the header and
		        PdfPTable table = new PdfPTable(headers.length);
		        Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
		        PdfPCell cell1 = new PdfPCell(new Phrase(userStyle.toString(),font));
		        cell1.setColspan(17);
		        headerCellStyle(cell1);
		        table.setWidthPercentage(100);
	            table.setWidths(new int[]{4,3,3,3,5,3,3,5,5,3,3,3,5,4,4,5});
	            table.addCell(cell1);
		        for (int i = 0; i < headers.length; i++) {
		            String header = headers[i];
		            PdfPCell cell = new PdfPCell();
		            cell.setGrayFill(0.9f);
		            cell.setPhrase(new Phrase(header.toUpperCase(), new Font(FontFamily.HELVETICA, 8, Font.BOLD)));
		            BaseColor myBorderColor = WebColors.getRGBColor("#808080");
		            cell.setBackgroundColor(myBorderColor);
		            table.addCell(cell);
		        }
		        table.completeRow();
		        java.util.List<String[]> dayList = new ArrayList<>();
		        Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
				Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
		        ResponseList<DayWiseTimesheetReoprtsListDTO> dayEntryList = dayEntryReportsDao.getDayWiseReportListBasedOnProjectListIdStartDateEndDate(dayWiseTimesheetReoprtsDto.getProjectIds(), startDate, endDate,dayWiseTimesheetReoprtsDto.getSearchValue(),dayWiseTimesheetReoprtsDto.getStart(),dayWiseTimesheetReoprtsDto.getLength(),orgloc,loginUser,isGlobalUser,dayWiseTimesheetReoprtsDto.getProjectMemberListId(),orgloc.getDateFormat());
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
	                    cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
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
		
		
		private String[] prepareDaywiseReportPdfData(String[] headers, DayWiseTimesheetReoprtsListDTO dayEntries, OrgLocations orgloc, long totalBillable, long totalNonbillable) {
			    String dayArray[] = new String[headers.length];
				String weekDayName;
				SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
				Date timesheetDate = commonUtil.stringToDateByOrgLocationFormat(dayEntries.getTimesheetDate(), orgloc.getDateFormat());
				weekDayName = dayFormat.format(timesheetDate);
				int columnCount = 0;

				dayArray[columnCount++] = StringUtils.isNotEmpty(dayEntries.getTimesheetDate()) ? dayEntries.getTimesheetDate() : "";

				dayArray[columnCount++] = weekDayName != null && !"".equalsIgnoreCase(weekDayName) ? weekDayName : "";
				
				dayArray[columnCount++] =  dayEntries.getEmployeeId() != null ? dayEntries.getEmployeeId() : "";
				
				dayArray[columnCount++] = dayEntries.getEmployeeName()!=null ? dayEntries.getEmployeeName() : "";

				dayArray[columnCount++] = dayEntries.getEmployeeEmail()!= null ? dayEntries.getEmployeeEmail() : "";

				dayArray[columnCount++] = dayEntries.getDepartmentName()!= null ? dayEntries.getDepartmentName() : "";
	
				dayArray[columnCount++] = dayEntries!= null ? dayEntries.getTicketNo() : "";

				dayArray[columnCount++] = dayEntries.getProjectName() != null ? dayEntries.getProjectName() : "";

				dayArray[columnCount++] = dayEntries.getTask() != null ? dayEntries.getTask() : "";
				
				String billableHrs = "";
				if (dayEntries.getBillableHrs() != null) {
					billableHrs = dayEntries.getBillableHrs();
					totalBillable = getTotalHoursFormDayHours(dayEntries.getBillableHrs(), totalBillable);
				}
				dayArray[columnCount++] = billableHrs;
				
				String nonBillableHrs = "";
				if (dayEntries.getNonBillableHrs() != null) {
					nonBillableHrs = dayEntries.getNonBillableHrs();
					totalNonbillable = getTotalHoursFormDayHours(dayEntries.getNonBillableHrs(), totalNonbillable);
				}
				dayArray[columnCount++] = nonBillableHrs;
				
				dayArray[columnCount++] = dayEntries.getStatus() != null ? dayEntries.getStatus() : "";

				dayArray[columnCount++]=  dayEntries.getComments() != null ? dayEntries.getComments() : "";
				
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
			    dayArray[columnCount++] = submittedDate;
				dayArray[columnCount++] = approvedDate;
				String candLoc = "";
				if(dayEntries.getCandidateLocation()!= null) {
					if(dayEntries.getCandidateLocation().endsWith(",")) {
						candLoc = dayEntries.getCandidateLocation().substring(0,dayEntries.getCandidateLocation().length() - 1);
					} else {
						candLoc = dayEntries.getCandidateLocation();
					}
				}
				dayArray[columnCount++] =  candLoc;
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
			timeEntryDataMap = dayEntryReportsDao.getDayWiseReportListBasedOnProjectListIdStartDateEndDate(dayWiseTimesheetReoprtsDto.getProjectIds(), startDate, endDate,dayWiseTimesheetReoprtsDto.getSearchValue(),dayWiseTimesheetReoprtsDto.getStart(),dayWiseTimesheetReoprtsDto.getLength(),orgloc,loginUser,isGlobalUser,dayWiseTimesheetReoprtsDto.getProjectMemberListId(),orgloc.getDateFormat());
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
