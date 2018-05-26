package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DayEntryReportsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimeSheetDayEntriesService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class DayEntryReportsServiceImpl implements DayEntryReportsService{

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
	
	
	/*@Override
	public File exportDayEntryReportsTemplateOne(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc,User loginUser) throws Exception {
  		 File file = null;
			if(dayWiseTimesheetReoprtsDto.getStartDate() != null && dayWiseTimesheetReoprtsDto.getEndDate()!= null) {
				List<TimeSheetDayEntries> dayEntryList = null;
				Long projectsId = null;
				Workbook createExcelworkbook = new XSSFWorkbook();
				Row writeRow = null;
				Sheet createExcelsheet = createExcelworkbook.createSheet("Day wise Project wise Time sheet Report");
				
				if(dayWiseTimesheetReoprtsDto.getProjectIds() != null && !dayWiseTimesheetReoprtsDto.getProjectIds().isEmpty()) {
					for(Long projectId:dayWiseTimesheetReoprtsDto.getProjectIds()) {
						get individual project from project list
						projectsId = projectId;
					}
						prepare header
						createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook, dayWiseTimesheetReoprtsDto,orgloc,projectsId);
						To get all dates between given start date and end date
						Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
						Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
						List<Date> dateList = CalendarUtil.getListOfDaysBetweenTwoDates(startDate,endDate);
						int rowCount = 0;
						int innerHeadderCount = 7;
						int headCount = 0;
						if(dayWiseTimesheetReoprtsDto.getProjectMemberListId()!= null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
							for(Long projectMembers:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
								createExcelsheet = getExcelSheetWithHeaderForInnerBlock(createExcelsheet, writeRow, createExcelworkbook, projectMembers,orgloc,projectsId,innerRowCount);
								if(innerHeadderCount == 7) {
									headCount = 7;
								}else if(innerHeadderCount > 7){
									headCount = innerHeadderCount;
								}
								ProjectMembers projectMember = projectMemberService.getProjectMemberById(projectMembers);
								CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
								writeRow = createExcelsheet.createRow(headCount);
								createExcelsheet.setColumnWidth(0, 10000);
								createExcelsheet.setColumnWidth(1, 10000);
								Cell cell9 = writeRow.createCell(0);
								cell9.setCellValue("Associate Name: ");
								cell9.setCellStyle(style);
								Cell cell10 = writeRow.createCell(1);
								cell10.setCellValue(projectMember.getCandidateName());
								cell10.setCellStyle(style);

								writeRow = createExcelsheet.createRow(headCount+1);
								createExcelsheet.setColumnWidth(0, 10000);
								createExcelsheet.setColumnWidth(1, 10000);
								Cell cell11 = writeRow.createCell(0);
								cell11.setCellValue("Location: ");
								cell11.setCellStyle(style);
								Cell cell12 = writeRow.createCell(1);
								cell12.setCellValue(projectMember.getProjectMemberCandidteId().getCandidateLocation());
								cell12.setCellStyle(style);
								
								if(dateList != null && !dateList.isEmpty()) {
									for(Date timesheetDate:dateList) {
										dayEntryList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(projectsId,timesheetDate,projectMembers);
										 Map<Date,List<TimeSheetDayEntries>> mapDataList = new HashMap<Date, List<TimeSheetDayEntries>>();
										 if(dayEntryList != null && !dayEntryList.isEmpty()) {
											 
											 		main table data row count is 9 as data will start appending from 9th row
												    writeRow = createExcelsheet.createRow(rowCount + 8);
												    mapDataList.put(timesheetDate, dayEntryList);
												    for(Entry<Date, List<TimeSheetDayEntries>> tableData:mapDataList.entrySet()) {
														String weekDayName;
														SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
														weekDayName = dayFormat.format(tableData.getKey().getTime());
														if (tableData.getKey() != null) {
															writeRow.createCell(0).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), tableData.getKey()));
															createExcelsheet.autoSizeColumn(0);
														}
														if (weekDayName != null && !"".equalsIgnoreCase(weekDayName)) {
															writeRow.createCell(1).setCellValue(weekDayName);
															createExcelsheet.autoSizeColumn(1);
														}
													 	List<TimeSheetDayEntries> timeEntryList =  tableData.getValue();
													 	Long totalHours = 0l;
													 	String actualTimeString = null;
														for(TimeSheetDayEntries dayEntries:timeEntryList) {
															if (dayEntries.getProjectTask() != null && dayEntries.getProjectTask().getId() != null) {
																ProjectTasks projectTask = projecttasksservice.getTasksId(dayEntries.getProjectTask().getId().toString());
																writeRow.createCell(2).setCellValue(projectTask.getTaskName());
																createExcelsheet.autoSizeColumn(2);
															}
															if (dayEntries.getComments() != null) {
																writeRow.createCell(3).setCellValue(dayEntries.getComments());
																createExcelsheet.autoSizeColumn(3);
															}
															if (dayEntries.getBillableHours() != null) {
																writeRow.createCell(4).setCellValue(dayEntries.getBillableHours());
																createExcelsheet.autoSizeColumn(4);
															}
															if (dayEntries.getNonBillableHours() != null) {
																writeRow.createCell(5).setCellValue(dayEntries.getNonBillableHours());
																createExcelsheet.autoSizeColumn(5);
															}
																to calculate total min from given string
															 	totalHours = getTotalHoursFormDayHours(dayEntries.getTotalHours(), totalHours);
															 	
															 	convert minute to hours in string format
															 	actualTimeString = convertMinuteToHours(totalHours,actualTimeString);
																
																to create new row for inner loop data as it has sane date and dayOftheDate
															    writeRow = createExcelsheet.createRow(rowCount+9);
															 
															 inner loop increment by one	
															  rowCount++;
															 
														}
															writeRow.createCell(6).setCellValue(actualTimeString);
															createExcelsheet.autoSizeColumn(6);
															
													main table data row count increment by one		
													rowCount++;
													innerHeadderCount = rowCount+7;
											}
										}
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
							}
						}
					}
				}
		return file;
	}*/
	
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
	
	
	
	/*private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto, OrgLocations orgloc, Long projectId) throws Exception
	{
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
		writeRow = createExcelsheet.createRow(2);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
		Cell cell3 = writeRow.createCell(0);
		cell3.setCellValue("To Date :");
		cell3.setCellStyle(style);
		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(dayWiseTimesheetReoprtsDto.getEndDate());
		cell4.setCellStyle(style);
		writeRow = createExcelsheet.createRow(3);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
		
		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("Project Name :");
		cell5.setCellStyle(style);
		Cell cell6 = writeRow.createCell(1);
		 Projects project = projectService.getProjectById(projectId);
		cell6.setCellValue(project.getProjectName());
		cell6.setCellStyle(style);
		writeRow = createExcelsheet.createRow(4);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
		
		Cell cell7 = writeRow.createCell(0);
		cell7.setCellValue("Client Name :");
		cell7.setCellStyle(style);
		Cell cell8 = writeRow.createCell(1);
		cell8.setCellValue("Client Name");
		cell8.setCellStyle(style);
		
		prepare table header
		String headers[] = Constants.DAYWISE_TIMESHEET_REPORTS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 6);
		return createExcelsheet;
	}
*/
	
	/*
	@Override
	public File exportDayEntryReportsWithoutProject(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc, User loginUser) throws Exception {
		 File file = null;
			if(dayWiseTimesheetReoprtsDto.getStartDate() != null && dayWiseTimesheetReoprtsDto.getEndDate()!= null) {
				List<TimeSheetDayEntries> dayEntryList = null;
				Workbook createExcelworkbook = new XSSFWorkbook();
				Row writeRow = null;
				Sheet createExcelsheet = createExcelworkbook.createSheet("Day wise Project wise Time sheet Report");
				
				if(dayWiseTimesheetReoprtsDto.getProjectMemberListId() != null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
						for(Long userId:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
							 ProjectMembers projectMember = projectMemberService.getProjectMemberByUserIdAndLoginOrglocationId(userId, orgloc.getId());
							 if(projectMember!=null) {
								 
								 Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
								 Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
								 List<Date> dateList = CalendarUtil.getListOfDaysBetweenTwoDates(startDate,endDate);
								 int rowCount = 0;
								 int innerHeadderCount = 7;
								 int outerCount = 0;
								 List<ProjectMembers> projectMemberList = projectMemberService.getProjectMembersListByOrgIdAndMasterCandidateId(orgloc.getOrg().getId(),projectMember.getMasterCandidateId().getId());
								 if(dayWiseTimesheetReoprtsDto.getProjectMemberListId()!= null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
								 for(ProjectMembers projectMembers:projectMemberList) {
								//createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook, dayWiseTimesheetReoprtsDto,orgloc,projectMember.getProjects().getId());
									 
								createExcelsheet = getExcelSheetWithHeaderWithoutProjectIds(createExcelsheet, writeRow, createExcelworkbook,innerHeadderCount);
								
								if(dateList != null && !dateList.isEmpty()) {
									for(Date timesheetDate:dateList) {
										dayEntryList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(projectMembers.getProjects().getId(),timesheetDate,projectMembers.getId());
										 Map<Date,List<TimeSheetDayEntries>> mapDataList = new HashMap<Date, List<TimeSheetDayEntries>>();
										 if(dayEntryList != null && !dayEntryList.isEmpty()) {
												    writeRow = createExcelsheet.createRow(rowCount + 8);
												    mapDataList.put(timesheetDate, dayEntryList);
												    for(Entry<Date, List<TimeSheetDayEntries>> tableData:mapDataList.entrySet()) {
														String weekDayName;
														SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
														weekDayName = dayFormat.format(tableData.getKey().getTime());
														if (tableData.getKey() != null) {
															writeRow.createCell(0).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), tableData.getKey()));
															createExcelsheet.autoSizeColumn(0);
														}
														if (weekDayName != null && !"".equalsIgnoreCase(weekDayName)) {
															writeRow.createCell(1).setCellValue(weekDayName);
															createExcelsheet.autoSizeColumn(1);
														}
													 	List<TimeSheetDayEntries> timeEntryList =  tableData.getValue();
													 	Long totalHours = 0l;
													 	String actualTimeString = null;
														for(TimeSheetDayEntries dayEntries:timeEntryList) {
															if (dayEntries.getProjectTask() != null && dayEntries.getProjectTask().getId() != null) {
																ProjectTasks projectTask = projecttasksservice.getTasksId(dayEntries.getProjectTask().getId().toString());
																writeRow.createCell(2).setCellValue(projectTask.getTaskName());
																createExcelsheet.autoSizeColumn(2);
															}
															if (dayEntries.getComments() != null) {
																writeRow.createCell(3).setCellValue(dayEntries.getComments());
																createExcelsheet.autoSizeColumn(3);
															}
															if (dayEntries.getBillableHours() != null) {
																writeRow.createCell(4).setCellValue(dayEntries.getBillableHours());
																createExcelsheet.autoSizeColumn(4);
															}
															if (dayEntries.getNonBillableHours() != null) {
																writeRow.createCell(5).setCellValue(dayEntries.getNonBillableHours());
																createExcelsheet.autoSizeColumn(5);
															}
															 	totalHours = getTotalHoursFormDayHours(dayEntries.getTotalHours(), totalHours);
															 	actualTimeString = convertMinuteToHours(totalHours,actualTimeString);
															    writeRow = createExcelsheet.createRow(rowCount+9);
															    rowCount++;
														}
															writeRow.createCell(6).setCellValue(actualTimeString);
															createExcelsheet.autoSizeColumn(6);
															
													 outerCount = rowCount++;
													innerHeadderCount = outerCount+7;
											}
										}
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
							}
						}
					}
				}
				 }
			}
		return file;
	}

	private Sheet getExcelSheetWithHeaderWithoutProjectIds(Sheet createExcelsheet, Row writeRow,Workbook createExcelworkbook, int rowCount) {
		String headers[] = Constants.DAYWISE_TIMESHEET_REPORTS;
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, rowCount);
		return createExcelsheet;
	}
	*/
	
	
		@Override
		public File exportDayEntryReportsTemplateOne(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc,User loginUser) throws Exception {
		 File file = null;
		if(dayWiseTimesheetReoprtsDto.getStartDate() != null && dayWiseTimesheetReoprtsDto.getEndDate()!= null) {
			List<TimeSheetDayEntries> dayEntryList = null;
			Workbook createExcelworkbook = new XSSFWorkbook();
			Row writeRow = null;
			Sheet createExcelsheet = createExcelworkbook.createSheet("Day wise Project wise Time sheet Report");
			int rowCount = 0;
			int fixedCount = 7;
			int headRowCount = 0;
			if(dayWiseTimesheetReoprtsDto.getProjectIds() != null && !dayWiseTimesheetReoprtsDto.getProjectIds().isEmpty()) {
				 List<Long> newProjectmemberList = new ArrayList<>();
				for(Long projectId:dayWiseTimesheetReoprtsDto.getProjectIds()) {
					/*prepare header*/
					createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook, dayWiseTimesheetReoprtsDto,orgloc,projectId);
					/*To get all dates between given start date and end date*/
					Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
					Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
					List<Date> dateList = CalendarUtil.getListOfDaysBetweenTwoDates(startDate,endDate);
					
					if(dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty() && projectId!= null) {
						 List<ProjectMembers> projectMember = projectMemberService.getAllProjectMembersByProjectId(projectId);
						 for(ProjectMembers pro:projectMember) {
							 newProjectmemberList.add(pro.getId());
						 }
						 dayWiseTimesheetReoprtsDto.setProjectMemberListId(newProjectmemberList);
					}
					
					
					if(dayWiseTimesheetReoprtsDto.getProjectMemberListId()!= null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
						for(Long projectMembers:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
							if(dateList != null && !dateList.isEmpty()) {
								for(Date timesheetDate:dateList) {
									ProjectMembers projectMember = projectMemberService.getProjectMemberById(projectMembers);
									System.out.println("***********************************************"+projectMember.getProjects().getProjectName());
									dayEntryList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(projectId,timesheetDate,projectMembers);
									 Map<Date,List<TimeSheetDayEntries>> mapDataList = new HashMap<Date, List<TimeSheetDayEntries>>();
									 if(dayEntryList != null && !dayEntryList.isEmpty()) {
										 
										 		/*main table data row count is 9 as data will start appending from 9th row*/
											    writeRow = createExcelsheet.createRow(rowCount+6);
											    mapDataList.put(timesheetDate, dayEntryList);
											    for(Entry<Date, List<TimeSheetDayEntries>> tableData:mapDataList.entrySet()) {
													String weekDayName;
													SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
													weekDayName = dayFormat.format(tableData.getKey().getTime());
													if (tableData.getKey() != null) {
														writeRow.createCell(0).setCellValue(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), tableData.getKey()));
														createExcelsheet.autoSizeColumn(0);
													}
													if (weekDayName != null && !"".equalsIgnoreCase(weekDayName)) {
														writeRow.createCell(1).setCellValue(weekDayName);
														createExcelsheet.autoSizeColumn(1);
													}
												 	List<TimeSheetDayEntries> timeEntryList =  tableData.getValue();
													for(TimeSheetDayEntries dayEntries:timeEntryList) {
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
															writeRow.createCell(3).setCellValue(projectMember.getProjects().getProjectName());
															System.out.println(projectMember.getProjects().getProjectName());
															createExcelsheet.autoSizeColumn(3);
														}
														
														if (dayEntries.getProjectTask() != null && dayEntries.getProjectTask().getId() != null) {
															ProjectTasks projectTask = projecttasksservice.getTasksId(dayEntries.getProjectTask().getId().toString());
															writeRow.createCell(4).setCellValue(projectTask.getTaskName());
															createExcelsheet.autoSizeColumn(4);
														}
														if (dayEntries.getBillableHours() != null) {
															writeRow.createCell(5).setCellValue(dayEntries.getBillableHours());
															createExcelsheet.autoSizeColumn(5);
														}
														if (dayEntries.getNonBillableHours() != null) {
															writeRow.createCell(6).setCellValue(dayEntries.getNonBillableHours());
															createExcelsheet.autoSizeColumn(6);
														}
														  SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeBySubmissionId(dayEntries.getTimesheetSubmissions().getId(), projectMember.getMasterCandidateId().getId());
														if (sah != null) {
															writeRow.createCell(7).setCellValue(sah.getSubmissionApprovalStatus().toString());
															createExcelsheet.autoSizeColumn(7);
														}
														
														if (dayEntries.getComments() != null) {
															writeRow.createCell(8).setCellValue(dayEntries.getComments());
															createExcelsheet.autoSizeColumn(8);
														}
														   writeRow = createExcelsheet.createRow(rowCount+fixedCount);
														   System.out.println(rowCount);
														  rowCount++;
														 
													}
													dayWiseTimesheetReoprtsDto.setProjectMemberListId(new ArrayList<>());
										}
											  
									}
								}
							}
						}
						  fixedCount  = fixedCount+ rowCount;
						  System.out.println(fixedCount);
					}
				  }
//				rowCount++;
				}
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

		
		private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto, OrgLocations orgloc, Long projectId) throws Exception
		{
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
			writeRow = createExcelsheet.createRow(2);
			createExcelsheet.setColumnWidth(0, 10000);
			createExcelsheet.setColumnWidth(1, 10000);
			Cell cell3 = writeRow.createCell(0);
			cell3.setCellValue("To Date :");
			cell3.setCellStyle(style);
			Cell cell4 = writeRow.createCell(1);
			cell4.setCellValue(dayWiseTimesheetReoprtsDto.getEndDate());
			cell4.setCellStyle(style);
			
			/*prepare table header*/
			String headers[] = Constants.DAYWISE_TIMESHEET_REPORTS;
			createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 5);
			return createExcelsheet;
		}

	

}
