package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.bis.sourcelead.util.CalendarUtil;
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
	private TimeSheetDayEntriesService timeSheetDayEntriesService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ProjectTasksService projecttasksservice;
	
	@Autowired
	private ProjectMembersService projectMemberService;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private TimesheetActivityDao timesheetActivityDao;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DayEntryReportsDao dayEntryReportsDao;
	
	@Override
	public File exportDayEntryReportsWithoutProjectExcel(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc, User loginUser) throws Exception {
		 File file = null;
		 	Long[]  countTotal = new Long[2];
		 	 countTotal[0] = 0l;
			 countTotal[1] = 0l;
			if(dayWiseTimesheetReoprtsDto.getStartDate() != null && dayWiseTimesheetReoprtsDto.getEndDate()!= null) {
				List<TimeSheetDayEntries> dayEntryList = null;
				Workbook createExcelworkbook = new XSSFWorkbook();
				Row writeRow = null;
				int rowCount = 0;
				Sheet createExcelsheet = createExcelworkbook.createSheet("Day wise Project wise Time sheet Report");
				 Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
				 Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
				//createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,dayWiseTimesheetReoprtsDto);
				if(dayWiseTimesheetReoprtsDto.getProjectMemberListId() != null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
						for(Long projectMemberId:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
							 ProjectMembers projectMember = projectMemberService.getProjectMemberById(projectMemberId);
							 if(projectMember!=null) {
								 List<ProjectMembers> projectMemberList = projectMemberService.getProjectMembersListByOrgIdAndMasterCandidateId(orgloc.getOrg().getId(),projectMember.getMasterCandidateId().getId());
										for(ProjectMembers projectMembers:projectMemberList) {
								 		 dayEntryList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDateTest(projectMembers.getProjects().getId(),startDate,endDate,projectMembers.getId()); 
										 if(dayEntryList != null && !dayEntryList.isEmpty()) {
												    Date loopdate = null;
												    for(TimeSheetDayEntries dayEntries:dayEntryList) {
												    	writeRow = createExcelsheet.createRow(rowCount + 6);
												    	 Map<Sheet,Long[]> dataWithTotal = createXlsSheet(createExcelsheet, writeRow,dayEntries,projectMember,loopdate,orgloc,countTotal[0],countTotal[1]);
													    	 for (Long[] totalHours : dataWithTotal.values()) {
													    		 countTotal[0] = totalHours[0];
													    		 countTotal[1] = totalHours[1];
													 			
													 		 }
												    	 	 rowCount++;
															 loopdate = dayEntries.getTimesheetDate();
															}
														}
							 						}
												}
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
		return file;
	}

		@Override
		public File exportDayEntryReportsWithProjectExcel(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc,User loginUser) throws Exception {
		 File file = null;
		 Boolean flag = false;
		 if(dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
			 flag = true;
		 }
		 
		if(dayWiseTimesheetReoprtsDto.getStartDate() != null && dayWiseTimesheetReoprtsDto.getEndDate()!= null) {
			List<TimeSheetDayEntries> dayEntryList = null;
			Workbook createExcelworkbook = new XSSFWorkbook();
			Row writeRow = null;
			Long[]  countTotal = new Long[2];
			countTotal[0] = 0l;
		    countTotal[1] = 0l; 
			Sheet createExcelsheet = createExcelworkbook.createSheet("Day wise Project wise Time sheet Report");
			int rowCount = 0;
			if(dayWiseTimesheetReoprtsDto.getProjectIds() != null && !dayWiseTimesheetReoprtsDto.getProjectIds().isEmpty()) {
				 List<Long> newProjectmemberList = new ArrayList<>();
				for(Long projectId:dayWiseTimesheetReoprtsDto.getProjectIds()) {
					Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
					Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
					if(flag== true) {
						if(dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty() && projectId!= null) {
							 List<ProjectMembers> projectMember = projectMemberService.getAllProjectMembersByProjectId(projectId);
							 for(ProjectMembers pro:projectMember) {
								 newProjectmemberList.add(pro.getId());
							 }
							 dayWiseTimesheetReoprtsDto.setProjectMemberListId(newProjectmemberList);
						}
					}
					
					
					if(dayWiseTimesheetReoprtsDto.getProjectMemberListId()!= null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
						for(Long projectMembers:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
							ProjectMembers projectMember = projectMemberService.getProjectMemberById(projectMembers);
									dayEntryList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDateTest(projectId,startDate,endDate,projectMembers); 
									if(dayEntryList != null && !dayEntryList.isEmpty()) {
										 		/*main table data row count is 9 as data will start appending from 9th row*/
											    Date loopdate = null;
											    for(TimeSheetDayEntries dayEntries:dayEntryList) {
											    	 writeRow = createExcelsheet.createRow(rowCount+6);
											    	 Map<Sheet,Long[]> dataWithTotal = createXlsSheet(createExcelsheet, writeRow,dayEntries,projectMember,loopdate,orgloc,countTotal[0],countTotal[1]);
											    	 	 for (Long[] totalHours : dataWithTotal.values()) {
												    		 countTotal[0] = totalHours[0];
												    		 countTotal[1] = totalHours[1];
												 			
												 		}
											    	 	rowCount++;														  
														loopdate = dayEntries.getTimesheetDate();
														}
													}
												}
											if(flag== true) {
												dayWiseTimesheetReoprtsDto.setProjectMemberListId(new ArrayList<>());
											}
										}
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
	return file;
}

		private Map<Sheet,Long[]> createXlsSheet(Sheet createExcelsheet, Row writeRow, TimeSheetDayEntries dayEntries, ProjectMembers projectMember, Date loopdate, OrgLocations orgloc, long totalBillable, long totalNonbillable) throws Exception {
			Map<Sheet,Long[]> dataMap =  new HashMap<>();
			Long[]  countTotal = new Long[2];
			Date loopStartDate = dayEntries.getTimesheetDate();
		    	if(!loopStartDate.equals(loopdate)) {
		    		String weekDayName;
					SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
					weekDayName = dayFormat.format(dayEntries.getTimesheetDate());
					if (dayEntries.getTimesheetDate() != null) {
						writeRow.createCell(0).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dayEntries.getTimesheetDate()));
						createExcelsheet.autoSizeColumn(0);
					}
					if (weekDayName != null && !"".equalsIgnoreCase(weekDayName)) {
						writeRow.createCell(1).setCellValue(weekDayName);
						createExcelsheet.autoSizeColumn(1);
					}
		    	}
				if (projectMember != null && projectMember.getMasterCandidateId() != null) {
					if(projectMember.getProjectMemberCandidteId().getLastName()!=null) {
						StringBuilder sb = new StringBuilder();
						writeRow.createCell(2).setCellValue(sb.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(" ").append(projectMember.getProjectMemberCandidteId().getLastName()).toString());
					}else {
						writeRow.createCell(2).setCellValue(projectMember.getProjectMemberCandidteId().getFirstName());
					}
					createExcelsheet.autoSizeColumn(2);
				}
				
				if (projectMember != null && projectMember.getProjects() != null) {
					writeRow.createCell(3).setCellValue(projectMember.getProjectMemberCandidteId().getEmail());
					createExcelsheet.autoSizeColumn(3);
				}
				
				if (dayEntries!= null) {
					if(projectMember.getEmployee()!= null && projectMember.getEmployee().getDepartmentId()!= null) {
					Department department = departmentService.getDepartmentById(projectMember.getEmployee().getDepartmentId().getId());
						if(department!= null) {
							writeRow.createCell(4).setCellValue(department.getDepartmentName());
							createExcelsheet.autoSizeColumn(4);
						}
					}else {
						writeRow.createCell(4).setCellValue("");
					}
				}
				
				if (dayEntries!= null) {
					writeRow.createCell(5).setCellValue(dayEntries.getTicketNumber());
					createExcelsheet.autoSizeColumn(5);
				}
				
				if (dayEntries.getTimesheetSubmissions() != null && dayEntries.getTimesheetSubmissions().getProjects() != null) {
					writeRow.createCell(6).setCellValue(dayEntries.getTimesheetSubmissions().getProjects().getProjectName());
					createExcelsheet.autoSizeColumn(6);
				}
				
				if (dayEntries.getProjectTask() != null && dayEntries.getProjectTask().getId() != null) {
					ProjectTasks projectTask = projecttasksservice.getTasksId(dayEntries.getProjectTask().getId().toString());
					writeRow.createCell(7).setCellValue(projectTask.getTaskName());
					createExcelsheet.autoSizeColumn(7);
				}
				if (dayEntries.getBillableHours() != null) {
					writeRow.createCell(8).setCellValue(dayEntries.getBillableHours());
					totalBillable = getTotalHoursFormDayHours(dayEntries.getBillableHours(), totalBillable);
					createExcelsheet.autoSizeColumn(8);
				}
				if (dayEntries.getNonBillableHours() != null) {
					writeRow.createCell(9).setCellValue(dayEntries.getNonBillableHours());
					totalNonbillable = getTotalHoursFormDayHours(dayEntries.getNonBillableHours(), totalNonbillable);
					createExcelsheet.autoSizeColumn(9);
				}
				  SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeBySubmissionId(dayEntries.getTimesheetSubmissions().getId(), projectMember.getMasterCandidateId().getId());
				if (sah != null) {
					writeRow.createCell(10).setCellValue(sah.getSubmissionApprovalStatus().toString());
					createExcelsheet.autoSizeColumn(10);
				}
				if (dayEntries.getComments() != null) {
					writeRow.createCell(11).setCellValue(dayEntries.getComments());
					createExcelsheet.autoSizeColumn(11);
				}
				String status = null;
				if(sah.getSubmissionApprovalStatus().getValue().equalsIgnoreCase("submitted for approval")) {
					status =  "submitted";
				}else {
					status = sah.getSubmissionApprovalStatus().getValue();
				}
				
				TimesheetActivities timesheetActivity = getSubmittedAndApprovedDate(projectMember.getProjects().getId(), projectMember.getMasterCandidateId().getId(), sah.getFrequencyStartDate(),sah.getFrequencyEndDate(),status);
				String submittedDate = null;
				if(timesheetActivity!= null) {
					if(timesheetActivity.getActivityStatus().equalsIgnoreCase("submitted")) {
						submittedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), timesheetActivity.getCreatedDate());
						writeRow.createCell(12).setCellValue(submittedDate);
						createExcelsheet.autoSizeColumn(12);
					}else if(timesheetActivity.getActivityStatus().equalsIgnoreCase("approved") || timesheetActivity.getActivityStatus().equalsIgnoreCase("rejected")) {
						writeRow.createCell(12).setCellValue(submittedDate);
						createExcelsheet.autoSizeColumn(12);
						writeRow.createCell(13).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), timesheetActivity.getCreatedDate()));
						createExcelsheet.autoSizeColumn(13);
					}
				 }
				
				
				countTotal[0] = totalBillable;
				countTotal[1] = totalNonbillable;
				dataMap.put(createExcelsheet, countTotal);
			return dataMap;
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
			 
			 createExcelsheet.setColumnWidth(3, 10000);
			 createExcelsheet.setColumnWidth(4, 10000);
			 
			 Cell cell11 = writeRow.createCell(3);
			 cell11.setCellValue("Billable Total Hours :");
			 cell11.setCellStyle(style);
			 
			 Cell cell21 = writeRow.createCell(4);
			 cell21.setCellValue(billable);
			 cell21.setCellStyle(style);
			 
			 
			 writeRow = createExcelsheet.createRow(2);
			 createExcelsheet.setColumnWidth(0, 10000);
			 createExcelsheet.setColumnWidth(1, 10000);
			 
			 Cell cell3 = writeRow.createCell(0);
			 cell3.setCellValue("To Date :");
			 cell3.setCellStyle(style);
			 
			 Cell cell4 = writeRow.createCell(1);
			 cell4.setCellValue(dayWiseTimesheetReoprtsDto.getEndDate());
			 cell4.setCellStyle(style);
			 
			 createExcelsheet.setColumnWidth(3, 10000);
			 createExcelsheet.setColumnWidth(4, 10000);
			 
			 Cell cell31 = writeRow.createCell(3);
			 cell31.setCellValue("Non Billable Total Hours :");
			 cell31.setCellStyle(style);
			 
			 Cell cell41 = writeRow.createCell(4);
			 cell41.setCellValue(nonBillable);
			 cell41.setCellStyle(style);
			 String headers[] = Constants.DAYWISE_TIMESHEET_REPORTS;
			 createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 5);
			return createExcelsheet;
			
		}
		

		@Override
		public File exportDayEntryReportsWithProjectPdf(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc, User loginUser)throws Exception {

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
		        cell1.setColspan(15);
		        headerCellStyle(cell1);
		        table.setWidthPercentage(100);
	            table.setWidths(new int[]{5, 3, 3,5,3, 5, 5,5,5,5,3,5,3,3});
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
		        
		        Boolean flag = false;
				 if(dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
					 flag = true;
				 }
				 
				if(dayWiseTimesheetReoprtsDto.getStartDate() != null && dayWiseTimesheetReoprtsDto.getEndDate()!= null) {
					List<TimeSheetDayEntries> dayEntryList = null;
					if(dayWiseTimesheetReoprtsDto.getProjectIds() != null && !dayWiseTimesheetReoprtsDto.getProjectIds().isEmpty()) {
						 List<Long> newProjectmemberList = new ArrayList<>();
						 Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
						 Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());

						for(Long projectId:dayWiseTimesheetReoprtsDto.getProjectIds()) {
							if(flag== true) {
								if(dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty() && projectId!= null) {
									 List<ProjectMembers> projectMember = projectMemberService.getAllProjectMembersByProjectId(projectId);
									 for(ProjectMembers pro:projectMember) {
										 newProjectmemberList.add(pro.getId());
									 }
									 dayWiseTimesheetReoprtsDto.setProjectMemberListId(newProjectmemberList);
								}
							}
							
							
							if(dayWiseTimesheetReoprtsDto.getProjectMemberListId()!= null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
								for(Long projectMembers:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
									ProjectMembers projectMember = projectMemberService.getProjectMemberById(projectMembers);
											 dayEntryList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDateTest(projectId,startDate,endDate,projectMembers);
											 if(dayEntryList != null && !dayEntryList.isEmpty()) {
												 for(TimeSheetDayEntries dayEntries:dayEntryList) {
													//dayList.add(prepareUserStringArray(dayEntries,headers,projectMember,orgloc));
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
															if (projectMember != null && projectMember.getMasterCandidateId() != null) {
																if(projectMember.getProjectMemberCandidteId().getLastName()!=null) {
																	StringBuilder sb = new StringBuilder();
																	dayArray[2]=(sb.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(" ").append(projectMember.getProjectMemberCandidteId().getLastName()).toString());
																}else {
																	dayArray[2]=(projectMember.getProjectMemberCandidteId().getFirstName());
																}
															}
															
															if (projectMember != null && projectMember.getProjects() != null) {
																dayArray[3]=(projectMember.getProjectMemberCandidteId().getEmail());
															}
															
															if (dayEntries!= null) {
																if(projectMember.getEmployee()!= null && projectMember.getEmployee().getDepartmentId()!= null) {
																Department department = departmentService.getDepartmentById(projectMember.getEmployee().getDepartmentId().getId());
																	if(department!= null) {
																		dayArray[4]=(department.getDepartmentName());
																	}
																}else {
																	dayArray[4]=("");
																}
															}
															
															if (dayEntries!= null) {
																dayArray[5]=(dayEntries.getTicketNumber());
															}
															
															if (dayEntries.getTimesheetSubmissions() != null && dayEntries.getTimesheetSubmissions().getProjects() != null) {
																dayArray[6]=(dayEntries.getTimesheetSubmissions().getProjects().getProjectName());
															}
															
															if (dayEntries.getProjectTask() != null && dayEntries.getProjectTask().getId() != null) {
																ProjectTasks projectTask = projecttasksservice.getTasksId(dayEntries.getProjectTask().getId().toString());
																dayArray[7]=(projectTask.getTaskName());
															}
															if (dayEntries.getBillableHours() != null) {
																dayArray[8]=(dayEntries.getBillableHours());
																totalBillable = getTotalHoursFormDayHours(dayEntries.getBillableHours(), totalBillable);
																
															}
															if (dayEntries.getNonBillableHours() != null) {
																dayArray[9]=(dayEntries.getNonBillableHours());
																totalNonbillable = getTotalHoursFormDayHours(dayEntries.getNonBillableHours(), totalNonbillable);
															}
															  SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeBySubmissionId(dayEntries.getTimesheetSubmissions().getId(), projectMember.getMasterCandidateId().getId());
															if (sah != null) {
																dayArray[10]=(sah.getSubmissionApprovalStatus().toString());
															}
															String status = null;
															if(sah.getSubmissionApprovalStatus().getValue().equalsIgnoreCase("submitted for approval")) {
																status =  "submitted";
															}else {
																status = sah.getSubmissionApprovalStatus().getValue();
															}
															if (dayEntries.getComments() != null) {
																dayArray[11]=(dayEntries.getComments());
															}
															TimesheetActivities timesheetActivity = getSubmittedAndApprovedDate(projectMember.getProjects().getId(), projectMember.getMasterCandidateId().getId(), sah.getFrequencyStartDate(),sah.getFrequencyEndDate(),status);
															String submittedDate = null;
															if(timesheetActivity!= null) {
																if(timesheetActivity.getActivityStatus().equalsIgnoreCase("submitted")) {
																	submittedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), timesheetActivity.getCreatedDate());
																	dayArray[12] =(submittedDate);
																}else if(timesheetActivity.getActivityStatus().equalsIgnoreCase("approved") || timesheetActivity.getActivityStatus().equalsIgnoreCase("rejected")) {
																	dayArray[12] =(submittedDate);
																	dayArray[13] =(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), timesheetActivity.getCreatedDate())); 
																}
															 }
															
															dayList.add(dayArray);
												}
											}
										}
											if(flag== true) {
												dayWiseTimesheetReoprtsDto.setProjectMemberListId(new ArrayList<>());
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
		
		@Override
		public File exportDayEntryReportsWithoutProjectPdf(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc, User loginUser) throws Exception {
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
				if(dayWiseTimesheetReoprtsDto.getProjectMemberListId() != null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
					for(Long userId:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
						 ProjectMembers projectMember = projectMemberService.getProjectMemberById(userId);
						 if(projectMember!=null) {
							 Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
							 Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
							 List<Date> dateList = CalendarUtil.getListOfDaysBetweenTwoDates(startDate,endDate);
							 List<ProjectMembers> projectMemberList = projectMemberService.getProjectMembersListByOrgIdAndMasterCandidateId(orgloc.getOrg().getId(),projectMember.getMasterCandidateId().getId());
							 if(projectMemberList!= null && !projectMemberList.isEmpty()) {
							 for(ProjectMembers projectMembers:projectMemberList) {
							 if(dateList != null && !dateList.isEmpty()) {
									List<TimeSheetDayEntries> dayEntryList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDateTest(projectMembers.getProjects().getId(),startDate,endDate,projectMembers.getId());
									 if(dayEntryList != null && !dayEntryList.isEmpty()) {
											    for(TimeSheetDayEntries dayEntries:dayEntryList) {
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
														if (projectMember != null && projectMember.getMasterCandidateId() != null) {
															if(projectMember.getProjectMemberCandidteId().getLastName()!=null) {
																StringBuilder sb = new StringBuilder();
																dayArray[2]=(sb.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(" ").append(projectMember.getProjectMemberCandidteId().getLastName()).toString());
															}else {
																dayArray[2]=(projectMember.getProjectMemberCandidteId().getFirstName());
															}
														}
														
														if (projectMember != null && projectMember.getProjects() != null) {
															dayArray[3]=(projectMember.getProjectMemberCandidteId().getEmail());
														}
														
														if (dayEntries!= null) {
															if(projectMember.getEmployee()!= null && projectMember.getEmployee().getDepartmentId()!= null) {
															Department department = departmentService.getDepartmentById(projectMember.getEmployee().getDepartmentId().getId());
																if(department!= null) {
																	dayArray[4]=(department.getDepartmentName());
																}
															}else {
																dayArray[4]=("");
															}
														}
														
														if (dayEntries!= null) {
															dayArray[5]=(dayEntries.getTicketNumber());
														}
														
														if (dayEntries.getTimesheetSubmissions() != null && dayEntries.getTimesheetSubmissions().getProjects() != null) {
															dayArray[6]=(dayEntries.getTimesheetSubmissions().getProjects().getProjectName());
														}
														
														if (dayEntries.getProjectTask() != null && dayEntries.getProjectTask().getId() != null) {
															ProjectTasks projectTask = projecttasksservice.getTasksId(dayEntries.getProjectTask().getId().toString());
															dayArray[7]=(projectTask.getTaskName());
														}
														if (dayEntries.getBillableHours() != null) {
															dayArray[8]=(dayEntries.getBillableHours());
															totalBillable = getTotalHoursFormDayHours(dayEntries.getBillableHours(), totalBillable);
														}
														if (dayEntries.getNonBillableHours() != null) {
															dayArray[9]=(dayEntries.getNonBillableHours());
															totalNonbillable = getTotalHoursFormDayHours(dayEntries.getNonBillableHours(), totalNonbillable);
														}
														  SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeBySubmissionId(dayEntries.getTimesheetSubmissions().getId(), projectMember.getMasterCandidateId().getId());
														if (sah != null) {
															dayArray[10]=(sah.getSubmissionApprovalStatus().toString());
														}
														
														String status = null;
														if(sah.getSubmissionApprovalStatus().getValue().equalsIgnoreCase("submitted for approval")) {
															status =  "submitted";
														}else {
															status = sah.getSubmissionApprovalStatus().getValue();
														}
														
														TimesheetActivities timesheetActivity = getSubmittedAndApprovedDate(projectMember.getProjects().getId(), projectMember.getMasterCandidateId().getId(), sah.getFrequencyStartDate(),sah.getFrequencyEndDate(),status);
														String submittedDate = null;
														if(timesheetActivity!= null) {
															if(timesheetActivity.getActivityStatus().equalsIgnoreCase("submitted")) {
																submittedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), timesheetActivity.getCreatedDate());
																dayArray[11] =(submittedDate);
															}else if(timesheetActivity.getActivityStatus().equalsIgnoreCase("approved") || timesheetActivity.getActivityStatus().equalsIgnoreCase("rejected")) {
																dayArray[11] =(submittedDate);
																dayArray[12] =(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), timesheetActivity.getCreatedDate())); 
															}
														 }
														
														if (dayEntries.getComments() != null) {
															dayArray[13]=(dayEntries.getComments());
														}
														dayList.add(dayArray);
													}
												}
											}
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
			Long totalNonbillable = 0l;
			Long totalBillable = 0l;
			Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
			Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
			
			ResponseList<DayWiseTimesheetReoprtsListDTO> timeEntryDataMap = new ResponseList<DayWiseTimesheetReoprtsListDTO>();
			List<DayWiseTimesheetReoprtsListDTO> dayEntriesListDto = new ArrayList<DayWiseTimesheetReoprtsListDTO>();
			
			if(dayWiseTimesheetReoprtsDto.getProjectMemberListId()!= null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty() && (dayWiseTimesheetReoprtsDto.getProjectIds() == null || dayWiseTimesheetReoprtsDto.getProjectIds().isEmpty())) {
			for(Long userId:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
				 ProjectMembers projectMember = projectMemberService.getProjectMemberById(userId);
				 if(projectMember!=null) {
					 
					 List<ProjectMembers> projectMemberList = projectMemberService.getProjectMembersListByOrgIdAndMasterCandidateId(orgloc.getOrg().getId(),projectMember.getMasterCandidateId().getId());
					 if(projectMemberList!= null && !projectMemberList.isEmpty()) {
					 for(ProjectMembers projectMembers:projectMemberList) {
							List<TimeSheetDayEntries> dayEntryList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDateTest(projectMembers.getProjects().getId(),startDate,endDate,projectMembers.getId());
							 if(dayEntryList != null && !dayEntryList.isEmpty()) {
								 for(TimeSheetDayEntries dayEntries:dayEntryList) {
									    DayWiseTimesheetReoprtsListDTO	listDto = new DayWiseTimesheetReoprtsListDTO();										
									 	//listDto.setTimesheetDate(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), dayEntries.getTimesheetDate()));		
									 	if(projectMember.getProjectMemberCandidteId().getLastName()!=null) {
											StringBuilder sb = new StringBuilder();
											listDto.setEmployeeName(sb.append(projectMember.getProjectMemberCandidteId().getFirstName()).append(" ").append(projectMember.getProjectMemberCandidteId().getLastName()).toString());
										}else {
											listDto.setEmployeeName(projectMember.getProjectMemberCandidteId().getFirstName());
										}
									 	if (projectMember != null && projectMember.getProjects() != null) {
									 		listDto.setEmployeeEmail(projectMember.getProjectMemberCandidteId().getEmail());
										}
									 	
									 	if (dayEntries!= null) {
									 		listDto.setTicketNo(dayEntries.getTicketNumber());
										}
										
									 	if (dayEntries.getTimesheetSubmissions() != null && dayEntries.getTimesheetSubmissions().getProjects() != null) {
									 		listDto.setProjectName(dayEntries.getTimesheetSubmissions().getProjects().getProjectName());
										}
									 	
										if (dayEntries.getProjectTask() != null && dayEntries.getProjectTask().getId() != null) {
											ProjectTasks projectTask = projecttasksservice.getTasksId(dayEntries.getProjectTask().getId().toString());
											listDto.setTask(projectTask.getTaskName());
										}
										if (dayEntries.getBillableHours() != null) {
											listDto.setBillableHrs(dayEntries.getBillableHours());
											totalBillable = getTotalHoursFormDayHours(dayEntries.getBillableHours(), totalBillable);
											
										}
										if (dayEntries.getNonBillableHours() != null) {
											listDto.setNonBillableHrs(dayEntries.getNonBillableHours());
											totalNonbillable = getTotalHoursFormDayHours(dayEntries.getNonBillableHours(), totalNonbillable);
										}
										  SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeBySubmissionId(dayEntries.getTimesheetSubmissions().getId(), projectMember.getMasterCandidateId().getId());
										if (sah != null) {
											listDto.setStatus(sah.getSubmissionApprovalStatus().toString());
										}
										
										String status = null;
										if(sah.getSubmissionApprovalStatus().getValue().equalsIgnoreCase("submitted for approval")) {
											status =  "submitted";
										}else {
											status = sah.getSubmissionApprovalStatus().getValue();
										}
										
										TimesheetActivities timesheetActivity = getSubmittedAndApprovedDate(projectMember.getProjects().getId(), projectMember.getMasterCandidateId().getId(), sah.getFrequencyStartDate(),sah.getFrequencyEndDate(),status);
										String submittedDate = null;
										if(timesheetActivity!= null) {
											if(timesheetActivity.getActivityStatus().equalsIgnoreCase("submitted")) {
												submittedDate = commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), timesheetActivity.getCreatedDate());
												listDto.setSubmittedOn(submittedDate);
											}else if(timesheetActivity.getActivityStatus().equalsIgnoreCase("approved") || timesheetActivity.getActivityStatus().equalsIgnoreCase("rejected")) {
												listDto.setSubmittedOn(submittedDate);
												listDto.setApprovedOn(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), timesheetActivity.getCreatedDate())); 
											}
										 }
											dayEntriesListDto.add(listDto);
										}
								 
								         
								 
							}
					}
				}
			}
		}
			timeEntryDataMap.setData(dayEntriesListDto);	
		}else {
			 timeEntryDataMap = dayEntryReportsDao.getDayWiseReportListBasedOnProjectListIdStartDateEndDate(dayWiseTimesheetReoprtsDto.getProjectIds(), startDate, endDate,dayWiseTimesheetReoprtsDto.getSearchValue(),dayWiseTimesheetReoprtsDto.getStart(),dayWiseTimesheetReoprtsDto.getLength(),orgloc,loginUser,isGlobalUser);
			}
		return timeEntryDataMap;
	}



	private TimesheetActivities getSubmittedAndApprovedDate(Long projectMemberId, Long masterCandidateId, Date frequencyStartDate, Date frequencyEndDate,String status) {
		Long timesheetActivityId = timesheetActivityDao.getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDateAndStatus(projectMemberId, masterCandidateId, frequencyStartDate,frequencyEndDate,status);
		TimesheetActivities timesheetActivity = null;
		if(timesheetActivityId != null) {
			 timesheetActivity = timesheetActivityDao.getTimesheetActivityById(timesheetActivityId);
		}
	return timesheetActivity;
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
			String[] parts = dayHours.split(":");
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
