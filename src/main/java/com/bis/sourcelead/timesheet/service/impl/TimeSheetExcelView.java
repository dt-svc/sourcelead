package com.bis.sourcelead.timesheet.service.impl;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.timesheet.service.TimesheetEntriesService;

@Service
public class TimeSheetExcelView extends AbstractXlsView {
	
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;

	
	@Override
	public void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Sheet excelSheet = workbook.createSheet("timesheedata");
		excelSheet = setExcelHeader(excelSheet);
		
		@SuppressWarnings("unchecked")
		Collection<TimesheetEntries> timesheetEntriesList = (Collection<TimesheetEntries>) model.get("timesheetEntriesList");
		setExcelRow(excelSheet, timesheetEntriesList);
	}

	public Sheet setExcelHeader(Sheet excelSheet) {
		Row excelHeader = excelSheet.createRow(0);
			excelHeader.createCell(0).setCellValue("Projects");
			excelHeader.createCell(1).setCellValue("Tasks");
			excelHeader.createCell(2).setCellValue("Sun");
			excelHeader.createCell(3).setCellValue("Mon");
			excelHeader.createCell(4).setCellValue("Tue");
			excelHeader.createCell(5).setCellValue("Wed");
			excelHeader.createCell(6).setCellValue("Thu");
			excelHeader.createCell(7).setCellValue("Fri");
			excelHeader.createCell(8).setCellValue("Sat");
			excelHeader.createCell(9).setCellValue("TotalHours");
			return excelSheet;
	}
	
		public void setExcelRow(Sheet excelSheet, Collection<TimesheetEntries> timesheetEntriesList)
		{
			int record = 1;
			Row excelRow = excelSheet.createRow(record++);
			for(TimesheetEntries timesheet : timesheetEntriesList){
				if(timesheet.getTimesheetSubmissions().getProjectMembers().getProjects().getProjectName() != null){
					excelRow.createCell(0).setCellValue(timesheet.getTimesheetSubmissions().getProjectMembers().getProjects().getProjectName());
				}else{
					excelRow.createCell(0).setCellValue("Source");
				}
				if(timesheet.getProjectTask() != null){
					excelRow.createCell(1).setCellValue(timesheet.getProjectTask().toString());
				}else{
					excelRow.createCell(1).setCellValue("lead");
				}
				if(timesheet.getSunHours() != null){
					excelRow.createCell(2).setCellValue(timesheet.getSunHours());
				}else{
					excelRow.createCell(2).setCellValue("12");
				}
				if(timesheet.getMonHours() != null){
					excelRow.createCell(3).setCellValue(timesheet.getMonHours());
				}else{
					excelRow.createCell(3).setCellValue("12");
				}
				if(timesheet.getTueHours()!= null){
					excelRow.createCell(4).setCellValue(timesheet.getTueHours());
				}else{
					excelRow.createCell(4).setCellValue("12");
				}
				if(timesheet.getWedHours() != null){
					excelRow.createCell(5).setCellValue(timesheet.getWedHours());
				}else{
					excelRow.createCell(5).setCellValue("12");
				}
				if(timesheet.getThuHours() != null){
					excelRow.createCell(6).setCellValue(timesheet.getThuHours());
				}else{
					excelRow.createCell(6).setCellValue("12");
				}
				if(timesheet.getFriHours() != null){
					excelRow.createCell(7).setCellValue(timesheet.getFriHours());
				}else{
					excelRow.createCell(7).setCellValue("12");
				}
				if(timesheet.getSatHours() != null){
					excelRow.createCell(8).setCellValue(timesheet.getSatHours());
				}else{
					excelRow.createCell(8).setCellValue("12");
				}
				if(timesheet.getTotalHours() != null){
					excelRow.createCell(9).setCellValue(timesheet.getTotalHours());
				}else{
					excelRow.createCell(9).setCellValue("678");
				}
			}
		}
		
}
