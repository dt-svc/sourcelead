package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.ClientVendorExport;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;

/**
 * 
 * @author Rajashekar
 * @date 23-11-2016
 * 
 *
 */
@Service
public class ClientVendorExportImpl implements ClientVendorExport {
	private static final Logger logger = LoggerFactory.getLogger(ClientVendorExportImpl.class);

	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private PdfUtil pdfUtil;
	/**
	 * @author Rajashekar 
	 * write clients date to excel and pdf based exportType
	 * @return File
	 */
	@Override
	public File getClientExportFile(List<OrgRelations> clientsList, String exportType, String orgName)
			throws Exception {
		
		File file = null;
		String clientHeaders[] = Constants.CLIENT_EXCEL_HEADERS;
		String vendorType = Constants.CLIENT;
		if(!"pdf".equalsIgnoreCase(exportType)){
			Workbook wb = new XSSFWorkbook();
			CellStyle style = commonUtil.getCellStyle(wb);
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Sheet sheet = wb.createSheet(orgName + "_Clients_" + dateFormat.format(new Date()));
			Row writeRow = null;
			sheet = commonUtil.setExcelHeaders(sheet, style, clientHeaders);
			int row = 0;
			for (OrgRelations orgRelations : clientsList) {
				writeRow = sheet.createRow(row + 1);
				sheet = createXlsSheet(sheet, writeRow, orgRelations);
				row++;
			}
			 file = new File(orgName + "_Clients_" + dateFormat.format(new Date()) + ".xls");
			FileOutputStream fos = new FileOutputStream(file);
			try {
				wb.write(fos);
				fos.close();
			} catch (IOException e) {
				logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
			}
		}else{
			file = pdfUtil.getClientVendorPdfFile(clientsList,orgName,clientHeaders,vendorType);
		}
		
		return file;
	}

	/**
	 * @author Rajashekar writes data to xls file
	 * @param sheet
	 * @param writeRow
	 * @param orgRelations
	 * @return Sheet
	 */
	private Sheet createXlsSheet(Sheet sheet, Row writeRow, OrgRelations orgRelations) {
//		{"VendorName","Website","Location","Level","Number of Contacts"};
		if (orgRelations.getOrgName() != null && !"".equalsIgnoreCase(orgRelations.getOrgName())) {
			writeRow.createCell(0).setCellValue(orgRelations.getOrgName());
		}
		if (orgRelations.getWebSite() != null && !"".equalsIgnoreCase(orgRelations.getWebSite())) {
			writeRow.createCell(1).setCellValue(orgRelations.getWebSite());
		}
		if (orgRelations.getCity() != null && !"".equalsIgnoreCase(orgRelations.getCity())) {
			writeRow.createCell(2).setCellValue(orgRelations.getCity());
		}
		if (orgRelations.getLevel() != null && !"".equalsIgnoreCase(orgRelations.getLevel())) {
			writeRow.createCell(3).setCellValue(orgRelations.getLevel());
		}
		if (orgRelations.getNoOfContatcs() != null) {
			writeRow.createCell(4).setCellValue(orgRelations.getNoOfContatcs());
		}
		return sheet;
	}

	
		/**
		 * @author Rajashekar
		 * Writes Client Contacts to Excel and Pdf 
		 * @return File
		 */
	@Override
	public File getClientContactsExportFile(List<OrgSpoc> vandorsContactList, String exportType, String orgName)
			throws Exception {
		File file = null;
		String headers[] = Constants.CLIENT_CONTACTS_EXCEL_HEADERS;
		String vendorType = Constants.CLIENT;
		if(!"pdf".equalsIgnoreCase(exportType)){
			Workbook wb = new XSSFWorkbook();
			CellStyle style = commonUtil.getCellStyle(wb);
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Sheet sheet = wb.createSheet(orgName + "_Clients Contacts_" + dateFormat.format(new Date()));
			Row writeRow = null;
			sheet = commonUtil.setExcelHeaders(sheet, style, headers);
			int row = 0;
			for (OrgSpoc OrgSpoc : vandorsContactList) {
				writeRow = sheet.createRow(row + 1);
				sheet = createContactXlsSheet(sheet, writeRow, OrgSpoc);
				row++;
			}
			 file = new File(orgName + "_Clients Contact_" + dateFormat.format(new Date()) + ".xls");
			FileOutputStream fos = new FileOutputStream(file);
			try {
				wb.write(fos);
				fos.close();
			} catch (IOException e) {
				logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
			}
		}else{
			file = pdfUtil.getClientVendorContactsPdfFile(vandorsContactList,orgName,vendorType,headers);
		}
		
		return file;
	}
	/**
	 * @author Rajashekar
	 * prepares excel sheet for client contacts data and vendor contacts data
	 * @param sheet
	 * @param writeRow
	 * @param orgSpoc
	 * @return Sheet
	 */
	private Sheet createContactXlsSheet(Sheet sheet, Row writeRow, OrgSpoc orgSpoc) {
		
		if (orgSpoc.getOrgName() != null && !"".equalsIgnoreCase(orgSpoc.getOrgName())) {
			writeRow.createCell(0).setCellValue(orgSpoc.getOrgName());
		}
		if (orgSpoc.getSpocName() != null && !"".equalsIgnoreCase(orgSpoc.getSpocName())) {
			writeRow.createCell(1).setCellValue(orgSpoc.getSpocName());
		}
		if (orgSpoc.getSpocEmail() != null && !"".equalsIgnoreCase(orgSpoc.getSpocEmail())) {
			writeRow.createCell(2).setCellValue(orgSpoc.getSpocEmail());
		}
		if (orgSpoc.getSpocMobilePhone() != null && !"".equalsIgnoreCase(orgSpoc.getSpocMobilePhone())) {
			writeRow.createCell(3).setCellValue(orgSpoc.getSpocMobilePhone());
		}
		if (orgSpoc.getOwnerName() != null && !"".equalsIgnoreCase(orgSpoc.getOwnerName())) {
			writeRow.createCell(4).setCellValue(orgSpoc.getOwnerName());
		}
		writeRow.createCell(5).setCellValue("--");
		if (orgSpoc.getSpocStatus() != null && !"".equalsIgnoreCase(orgSpoc.getSpocStatus())) {
			writeRow.createCell(6).setCellValue(orgSpoc.getSpocStatus());
		}
		return sheet;
	}

	/**
	 * @author Rajashekar
	 * writes vendor data to excel and pdf based on exportType
	 * @return file
	 * @date 23-11-2016
	 */
	@Override
	public File getVendorDetailsFile(List<OrgRelations> vandorsList, String orgName, String exportType)
			throws Exception {
		File file = null;
		String vendorHeaders[] = Constants.VENDOR_EXCEL_HEADERS;
		String vendorType = Constants.VENDOR;
		if(!"pdf".equalsIgnoreCase(exportType)){
			Workbook wb = new XSSFWorkbook();
			CellStyle style = commonUtil.getCellStyle(wb);
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Sheet sheet = wb.createSheet(orgName + "_Vendor_" + dateFormat.format(new Date()));
			Row writeRow = null;
			sheet = commonUtil.setExcelHeaders(sheet, style, vendorHeaders);
			int row = 0;
			for (OrgRelations orgRelations : vandorsList) {
				writeRow = sheet.createRow(row + 1);
				sheet = createXlsSheet(sheet, writeRow, orgRelations);
				row++;
			}
			 file = new File(orgName + "_Vendor_" + dateFormat.format(new Date()) + ".xls");
			FileOutputStream fos = new FileOutputStream(file);
			try {
				wb.write(fos);
				fos.close();
			} catch (IOException e) {
				logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
			}
		}else{
			file = pdfUtil.getClientVendorPdfFile(vandorsList,orgName,vendorHeaders,vendorType);
		}
		
		return file;
	}

	/**
	 * @author Rajashekar
	 * writes vendor contacts details to excel and pdf
	 * @return File
	 */
	
	@Override
	public File getVendorContactDetailsFile(List<OrgSpoc> vandorsContactList, String orgName, String exportType)
			throws Exception {
		File file = null;
		String headers[] = Constants.VENDOR_CONTACTS_EXCEL_HEADERS;
		String vendorType = Constants.VENDOR;
		if(!"pdf".equalsIgnoreCase(exportType)){
			Workbook wb = new XSSFWorkbook();
			CellStyle style = commonUtil.getCellStyle(wb);
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Sheet sheet = wb.createSheet(orgName + "_Vendor Contacts_" + dateFormat.format(new Date()));
			Row writeRow = null;
			sheet = commonUtil.setExcelHeaders(sheet, style, headers);
			int row = 0;
			for (OrgSpoc OrgSpoc : vandorsContactList) {
				writeRow = sheet.createRow(row + 1);
				sheet = createContactXlsSheet(sheet, writeRow, OrgSpoc);
				row++;
			}
			 file = new File(orgName + "_Vendor Contact_" + dateFormat.format(new Date()) + ".xls");
			FileOutputStream fos = new FileOutputStream(file);
			try {
				wb.write(fos);
				fos.close();
			} catch (IOException e) {
				logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
			}
		}else{
			file = pdfUtil.getClientVendorContactsPdfFile(vandorsContactList,orgName,vendorType,headers);
		}
		
		return file;
	}


	@Override
	public File getexportProjectEmployeeFile(List<ProjectMembers> employeeList, String projectName, String exportType,Projects project)throws Exception {
		String headers[] = Constants.PROJECT_ASSIGNED_EMPLOYEES;

		File file =null;
		
		Set<ProjectApproverHierarchy> projectApproverHierarchySet = project.getProjectApprovers();
		 StringBuilder sb = new StringBuilder();
		 for(ProjectApproverHierarchy ProjectApproverHierarchy:projectApproverHierarchySet ){
			 sb.append(ProjectApproverHierarchy.getApproverUserId().getFirstName()+",");
		 }
		 String projectManager = sb.substring(0, sb.length()-1);
		if(!"pdf".equalsIgnoreCase(exportType)){
			Workbook wb = new XSSFWorkbook();
			CellStyle style = commonUtil.getCellStyle(wb);
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Sheet sheet = wb.createSheet(projectName +"_"+ dateFormat.format(new Date()));
			Row writeRow = null;
			setExcelTitle(sheet,projectName,project,wb,writeRow,projectManager);
			sheet = setExcelHeaders(sheet, style, headers);
			int row = 7;
			for (ProjectMembers projectMembers : employeeList) {
				writeRow = sheet.createRow(row + 1);
				sheet = createprojectMembersXlsSheet(sheet, writeRow, projectMembers);
				row++;
			}
			 file = new File(projectName +"_" + dateFormat.format(new Date()) + ".xls");
			FileOutputStream fos = new FileOutputStream(file);
			try {
				wb.write(fos);
				fos.close();
			} catch (IOException e) {
				logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
			}
		}else{
			file = pdfUtil.getProjectEmployeesExportPdf(employeeList,projectName,project,projectManager);
		}
		
		return file;
	}

	private Sheet setExcelHeaders(Sheet sheet, CellStyle style, String[] headers) {
		Row excelHeader = sheet.createRow(7);
		for (int i = 0; i < headers.length; i++) {
			Cell cell = excelHeader.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(style);
		}
		return sheet;
	}

	private void setExcelTitle(Sheet sheet, String projectName, Projects project, Workbook wb, Row writeRow, String projectManager) {
		
		CellStyle style = wb.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     Font font = wb.createFont();
        font.setColor(HSSFColor.BLUE.index);
        style.setFont(font);
		 
		 writeRow = sheet.createRow(1);
		 sheet.setColumnWidth(0, 10000);
		 sheet.setColumnWidth(1, 10000);
		 
		 Cell cell1 = writeRow.createCell(0);
		 cell1.setCellValue("Project Name :");
		 cell1.setCellStyle(style);
		 
		 Cell cell2 = writeRow.createCell(1);
		 cell2.setCellValue(projectName);
		 cell2.setCellStyle(style);
		 
		 writeRow = sheet.createRow(2);
		 sheet.setColumnWidth(0, 10000);
		 sheet.setColumnWidth(1, 10000);
		 
		 Cell cell3 = writeRow.createCell(0);
		 cell3.setCellValue("Manager : ");
		 cell3.setCellStyle(style);
		 
		 Cell cell4 = writeRow.createCell(1);
		 cell4.setCellValue(projectManager);
		 cell4.setCellStyle(style);
		 
		 writeRow = sheet.createRow(3);
		 sheet.setColumnWidth(0, 10000);
		 sheet.setColumnWidth(1, 10000);
		 
		 Cell cell5 = writeRow.createCell(0);
		 cell5.setCellValue("Date :");
		 cell5.setCellStyle(style);
		 
		 Cell cell6 = writeRow.createCell(1);
		 if(project.getStartDate()!=null && project.getEndDate() !=null){
			 cell6.setCellValue(project.getStartDate()+" to "+project.getEndDate()); 
		 }else{
			 cell6.setCellValue(project.getStartDate()+" to "+"--"); 
		 }
		 cell6.setCellStyle(style);
	}

	private Sheet createprojectMembersXlsSheet(Sheet sheet, Row writeRow, ProjectMembers projectMember) {
		if (projectMember.getEmpName() != null && !"".equalsIgnoreCase(projectMember.getEmpName())) {
			writeRow.createCell(0).setCellValue(projectMember.getEmpName());
		}
		if (projectMember.getEmployee().getUser().getEmail() != null && !"".equalsIgnoreCase(projectMember.getEmployee().getUser().getEmail())) {
			writeRow.createCell(1).setCellValue(projectMember.getEmployee().getUser().getEmail());
		}
		if (projectMember.getProjectAssignedDateStr() != null && !"".equalsIgnoreCase(projectMember.getProjectAssignedDateStr())) {
			writeRow.createCell(2).setCellValue(projectMember.getProjectAssignedDateStr());
		}
		if (projectMember.getProjectEndDateStr() != null && !"".equalsIgnoreCase(projectMember.getProjectEndDateStr())) {
			writeRow.createCell(3).setCellValue(projectMember.getProjectEndDateStr());
		}
		if (projectMember.getApprovedHrs() != null && !"".equalsIgnoreCase(projectMember.getApprovedHrs())) {
			writeRow.createCell(4).setCellValue(projectMember.getApprovedHrs());
		}
		if (projectMember.getRejectedHrs() != null && !"".equalsIgnoreCase(projectMember.getRejectedHrs())) {
			writeRow.createCell(5).setCellValue(projectMember.getRejectedHrs());
		}
		if (projectMember.getSubmittedHrs() != null && !"".equalsIgnoreCase(projectMember.getSubmittedHrs())) {
			writeRow.createCell(6).setCellValue(projectMember.getSubmittedHrs());
		}
		if (projectMember.getDraftHrs() != null && !"".equalsIgnoreCase(projectMember.getDraftHrs())) {
			writeRow.createCell(7).setCellValue(projectMember.getDraftHrs());
		}
		return sheet;
	}

}
