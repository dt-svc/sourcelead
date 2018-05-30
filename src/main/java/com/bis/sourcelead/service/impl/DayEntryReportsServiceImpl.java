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
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsListDTO;
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
	
	
	@Override
	public File exportDayEntryReportsWithoutProjectExcel(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc, User loginUser) throws Exception {
		 File file = null;
			if(dayWiseTimesheetReoprtsDto.getStartDate() != null && dayWiseTimesheetReoprtsDto.getEndDate()!= null) {
				List<TimeSheetDayEntries> dayEntryList = null;
				Workbook createExcelworkbook = new XSSFWorkbook();
				Row writeRow = null;
				Sheet createExcelsheet = createExcelworkbook.createSheet("Day wise Project wise Time sheet Report");
				
				createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,dayWiseTimesheetReoprtsDto);
				int rowCount = 0;
				 int fixedCount = 7;
				if(dayWiseTimesheetReoprtsDto.getProjectMemberListId() != null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
						for(Long userId:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
							 ProjectMembers projectMember = projectMemberService.getProjectMemberByUserIdAndLoginOrglocationId(userId, orgloc.getId());
							 if(projectMember!=null) {
								 Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
								 Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
								 List<Date> dateList = CalendarUtil.getListOfDaysBetweenTwoDates(startDate,endDate);
								 List<ProjectMembers> projectMemberList = projectMemberService.getProjectMembersListByOrgIdAndMasterCandidateId(orgloc.getOrg().getId(),projectMember.getMasterCandidateId().getId());
								 if(projectMemberList!= null && !projectMemberList.isEmpty()) {
								 for(ProjectMembers projectMembers:projectMemberList) {
								 if(dateList != null && !dateList.isEmpty()) {
									for(Date timesheetDate:dateList) {
										dayEntryList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(projectMembers.getProjects().getId(),timesheetDate,projectMembers.getId());
										 Map<Date,List<TimeSheetDayEntries>> mapDataList = new HashMap<Date, List<TimeSheetDayEntries>>();
										 if(dayEntryList != null && !dayEntryList.isEmpty()) {
												    writeRow = createExcelsheet.createRow(rowCount + 6);
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
																writeRow.createCell(3).setCellValue(projectMember.getProjectMemberCandidteId().getEmail());
																createExcelsheet.autoSizeColumn(3);
															}
															
															if (dayEntries!= null) {
																writeRow.createCell(4).setCellValue(dayEntries.getTicketNumber());
																createExcelsheet.autoSizeColumn(4);
															}
															
															if (dayEntries.getTimesheetSubmissions() != null && dayEntries.getTimesheetSubmissions().getProjects() != null) {
																writeRow.createCell(5).setCellValue(dayEntries.getTimesheetSubmissions().getProjects().getProjectName());
																createExcelsheet.autoSizeColumn(5);
															}
															
															if (dayEntries.getProjectTask() != null && dayEntries.getProjectTask().getId() != null) {
																ProjectTasks projectTask = projecttasksservice.getTasksId(dayEntries.getProjectTask().getId().toString());
																writeRow.createCell(6).setCellValue(projectTask.getTaskName());
																createExcelsheet.autoSizeColumn(6);
															}
															if (dayEntries.getBillableHours() != null) {
																writeRow.createCell(7).setCellValue(dayEntries.getBillableHours());
																createExcelsheet.autoSizeColumn(7);
															}
															if (dayEntries.getNonBillableHours() != null) {
																writeRow.createCell(8).setCellValue(dayEntries.getNonBillableHours());
																createExcelsheet.autoSizeColumn(8);
															}
															  SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeBySubmissionId(dayEntries.getTimesheetSubmissions().getId(), projectMember.getMasterCandidateId().getId());
															if (sah != null) {
																writeRow.createCell(9).setCellValue(sah.getSubmissionApprovalStatus().toString());
																createExcelsheet.autoSizeColumn(9);
															}
															
															if (dayEntries.getComments() != null) {
																writeRow.createCell(10).setCellValue(dayEntries.getComments());
																createExcelsheet.autoSizeColumn(10);
															}
															   writeRow = createExcelsheet.createRow(rowCount+fixedCount);
															  rowCount++;
															 
														}
											}
										}
									}
								}
								
							}
								 fixedCount = fixedCount + rowCount;
						}
					}
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
			Sheet createExcelsheet = createExcelworkbook.createSheet("Day wise Project wise Time sheet Report");
			int rowCount = 0;
			int fixedCount = 7;
			if(dayWiseTimesheetReoprtsDto.getProjectIds() != null && !dayWiseTimesheetReoprtsDto.getProjectIds().isEmpty()) {
				 List<Long> newProjectmemberList = new ArrayList<>();
				for(Long projectId:dayWiseTimesheetReoprtsDto.getProjectIds()) {
					/*prepare header*/
					createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook, dayWiseTimesheetReoprtsDto);
					/*To get all dates between given start date and end date*/
					Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
					Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
					List<Date> dateList = CalendarUtil.getListOfDaysBetweenTwoDates(startDate,endDate);
					
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
							if(dateList != null && !dateList.isEmpty()) {
								for(Date timesheetDate:dateList) {
									ProjectMembers projectMember = projectMemberService.getProjectMemberById(projectMembers);
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
															writeRow.createCell(3).setCellValue(projectMember.getProjectMemberCandidteId().getEmail());
															createExcelsheet.autoSizeColumn(3);
														}
														
														if (dayEntries!= null) {
															writeRow.createCell(4).setCellValue(dayEntries.getTicketNumber());
															createExcelsheet.autoSizeColumn(4);
														}
														
														if (dayEntries.getTimesheetSubmissions() != null && dayEntries.getTimesheetSubmissions().getProjects() != null) {
															writeRow.createCell(5).setCellValue(dayEntries.getTimesheetSubmissions().getProjects().getProjectName());
															createExcelsheet.autoSizeColumn(5);
														}
														
														if (dayEntries.getProjectTask() != null && dayEntries.getProjectTask().getId() != null) {
															ProjectTasks projectTask = projecttasksservice.getTasksId(dayEntries.getProjectTask().getId().toString());
															writeRow.createCell(6).setCellValue(projectTask.getTaskName());
															createExcelsheet.autoSizeColumn(6);
														}
														if (dayEntries.getBillableHours() != null) {
															writeRow.createCell(7).setCellValue(dayEntries.getBillableHours());
															createExcelsheet.autoSizeColumn(7);
														}
														if (dayEntries.getNonBillableHours() != null) {
															writeRow.createCell(8).setCellValue(dayEntries.getNonBillableHours());
															createExcelsheet.autoSizeColumn(8);
														}
														  SubmissionApproverHierarchy sah = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeBySubmissionId(dayEntries.getTimesheetSubmissions().getId(), projectMember.getMasterCandidateId().getId());
														if (sah != null) {
															writeRow.createCell(9).setCellValue(sah.getSubmissionApprovalStatus().toString());
															createExcelsheet.autoSizeColumn(9);
														}
														
														if (dayEntries.getComments() != null) {
															writeRow.createCell(10).setCellValue(dayEntries.getComments());
															createExcelsheet.autoSizeColumn(10);
														}
														   writeRow = createExcelsheet.createRow(rowCount+fixedCount);
														  rowCount++;
														 
													}
													if(flag== true) {
														dayWiseTimesheetReoprtsDto.setProjectMemberListId(new ArrayList<>());
													}
										}
											  
									}
								}
							}
						}
						  fixedCount  = fixedCount+ rowCount;
					}
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
	return file;
}

		
		private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto) throws Exception
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


		@Override
		public File exportDayEntryReportsWithProjectPdf(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgLocations, User loginUser)throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public File exportDayEntryReportsWithoutProjectPdf(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,
				OrgLocations orgLocations, User loginUser) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<List<DayWiseTimesheetReoprtsListDTO>,String[]> getDayEntriesList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgloc, User loginUser) throws Exception {
			Long totalNonbillable = 0l;
			Long totalBillable = 0l;
			String[]  value = new String[2];
			Date startDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getStartDate());
			Date endDate =  commonUtil.orgLocationDateFormat(orgloc.getDateFormat(), dayWiseTimesheetReoprtsDto.getEndDate());
			List<Date> dateList = CalendarUtil.getListOfDaysBetweenTwoDates(startDate,endDate);
			
			Map<List<DayWiseTimesheetReoprtsListDTO>,String[]> timeEntryDataMap = new HashMap<List<DayWiseTimesheetReoprtsListDTO>,String[]>();
			List<DayWiseTimesheetReoprtsListDTO> dayEntriesListDto = new ArrayList<DayWiseTimesheetReoprtsListDTO>();
			if(dayWiseTimesheetReoprtsDto.getProjectMemberListId()!= null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
			for(Long userId:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
				 ProjectMembers projectMember = projectMemberService.getProjectMemberByUserIdAndLoginOrglocationId(userId, orgloc.getId());
				 if(projectMember!=null) {
					 
					 List<ProjectMembers> projectMemberList = projectMemberService.getProjectMembersListByOrgIdAndMasterCandidateId(orgloc.getOrg().getId(),projectMember.getMasterCandidateId().getId());
					 if(projectMemberList!= null && !projectMemberList.isEmpty()) {
					 for(ProjectMembers projectMembers:projectMemberList) {
					 if(dateList != null && !dateList.isEmpty()) {
						for(Date timesheetDate:dateList) {
							List<TimeSheetDayEntries> dayEntryList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(projectMembers.getProjects().getId(),timesheetDate,projectMembers.getId());
							 if(dayEntryList != null && !dayEntryList.isEmpty()) {
								 dayEntriesListDto = dataForDayEntriesListDTO(dayEntryList,orgloc,projectMember,totalNonbillable,totalBillable,timesheetDate,dayEntriesListDto, value);
								}
							}
						}
					}
				}
			}
		}
		}else {
			 Boolean flag = false;
			 if(dayWiseTimesheetReoprtsDto.getProjectMemberListId() == null || dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
				 flag = true;
			 }
			List<Long> newProjectmemberList = new ArrayList<>();
			for(Long projectId:dayWiseTimesheetReoprtsDto.getProjectIds()) {
				
				if(flag== true) {
					if((dayWiseTimesheetReoprtsDto.getProjectMemberListId() == null ||dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) && projectId!= null) {
						 List<ProjectMembers> projectMember = projectMemberService.getAllProjectMembersByProjectId(projectId);
						 for(ProjectMembers pro:projectMember) {
							 newProjectmemberList.add(pro.getId());
						 }
						 dayWiseTimesheetReoprtsDto.setProjectMemberListId(newProjectmemberList);
					}
				}
				if(dayWiseTimesheetReoprtsDto.getProjectMemberListId()!= null && !dayWiseTimesheetReoprtsDto.getProjectMemberListId().isEmpty()) {
					for(Long projectMembers:dayWiseTimesheetReoprtsDto.getProjectMemberListId()) {
						if(dateList != null && !dateList.isEmpty()) {
							for(Date timesheetDate:dateList) {
								ProjectMembers projectMember = projectMemberService.getProjectMemberById(projectMembers);
								List<TimeSheetDayEntries> dayEntryList = timeSheetDayEntriesService.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(projectId,timesheetDate,projectMembers);
								 if(dayEntryList != null && !dayEntryList.isEmpty()) {
									 	dayEntriesListDto = dataForDayEntriesListDTO(dayEntryList,orgloc,projectMember,totalNonbillable,totalBillable,timesheetDate,dayEntriesListDto,value);
									 	if(flag== true) {
											dayWiseTimesheetReoprtsDto.setProjectMemberListId(new ArrayList<>());
										}
								 	}
								}
							}
						}
					}
				}
	
			}
			timeEntryDataMap.put(dayEntriesListDto, value);
		return timeEntryDataMap;
	}

		
	private List<DayWiseTimesheetReoprtsListDTO> dataForDayEntriesListDTO(List<TimeSheetDayEntries> dayEntryList, OrgLocations orgloc, ProjectMembers projectMember, Long totalNonbillable, Long totalBillable, Date timesheetDate, List<DayWiseTimesheetReoprtsListDTO> dayEntriesListDto, String[] value) throws Exception {
		 for(TimeSheetDayEntries dayEntries:dayEntryList) {
			    DayWiseTimesheetReoprtsListDTO	listDto = new DayWiseTimesheetReoprtsListDTO();										
			 	listDto.setTimesheetDate(commonUtil.dateStrFromOrgDateFormatter(orgloc.getDateFormat(), timesheetDate));		
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
					dayEntriesListDto.add(listDto);
				}
		value[0] = (convertMinuteToHours(totalBillable, null));
		value[1] = (convertMinuteToHours(totalNonbillable, null));
		return dayEntriesListDto;
			
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
