package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.ClientVendorExport;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
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
	
	@Autowired
	UserRoleService userRoleService;
	
	private static final Logger logger = LoggerFactory.getLogger(ClientVendorExportImpl.class);

	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private PdfUtil pdfUtil;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
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
			StringBuilder nameBuilder=new StringBuilder();
			Sheet sheet = wb.createSheet(nameBuilder.append(orgName).append("_Clients_").append(dateFormat.format(new Date())).toString());
			Row writeRow = null;
			sheet = commonUtil.setExcelHeaders(sheet, style, clientHeaders,0);
			int row = 0;
			for (OrgRelations orgRelations : clientsList) {
				writeRow = sheet.createRow(row + 1);
				sheet = createXlsSheet(sheet, writeRow, orgRelations);
				row++;
			}
			StringBuilder orgNameBuilder=new StringBuilder();
			 file = File.createTempFile(orgNameBuilder.append(orgName).append("_Clients_").append(dateFormat.format(new Date())).toString(),".xls");
			 file.setReadable(true);
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
		if (StringUtils.isNotEmpty(orgRelations.getOrgName())) {
			writeRow.createCell(0).setCellValue(orgRelations.getOrgName());
		}
		if (StringUtils.isNotEmpty(orgRelations.getOrgName())) {
			StringBuilder sb = new StringBuilder();
			if(orgRelations.getWebSite() != null && orgRelations.getWebSite() != "" && orgRelations.getWebSite() != "undefined"){
				writeRow.createCell(1).setCellValue(sb.append("www.").append(orgRelations.getWebSite()).toString());
			}
		}
		StringBuilder sb = new StringBuilder();
		if ((StringUtils.isNotEmpty(orgRelations.getCity())) && (StringUtils.isNotEmpty(orgRelations.getState())) && (StringUtils.isNotEmpty(orgRelations.getCountry()))){
			sb.append(orgRelations.getCity()).append(",").append(orgRelations.getState()).append(",").append(orgRelations.getCountry());
			writeRow.createCell(2).setCellValue(sb.toString());
		}else if ((StringUtils.isNotEmpty(orgRelations.getCity())) && (StringUtils.isNotEmpty(orgRelations.getState()))){
			sb.append(orgRelations.getCity()).append(",").append(orgRelations.getState());
			writeRow.createCell(2).setCellValue(sb.toString());
		}else if((StringUtils.isNotEmpty(orgRelations.getState())) && (StringUtils.isNotEmpty(orgRelations.getCountry()))){
			sb.append(orgRelations.getState()).append(",").append(orgRelations.getCountry());
			writeRow.createCell(2).setCellValue(sb.toString());
		}else if ((StringUtils.isNotEmpty(orgRelations.getCity())) && (StringUtils.isNotEmpty(orgRelations.getCountry()))){
			sb.append(orgRelations.getCity()).append(",").append(orgRelations.getCountry());
			writeRow.createCell(2).setCellValue(sb.toString());
		}else if(StringUtils.isNotEmpty(orgRelations.getCity())){
			writeRow.createCell(2).setCellValue(orgRelations.getCity());
		}else if(StringUtils.isNotEmpty(orgRelations.getState())){
			writeRow.createCell(2).setCellValue(orgRelations.getState());
		}else if(StringUtils.isNotEmpty(orgRelations.getCountry())){
			writeRow.createCell(2).setCellValue(orgRelations.getCountry());
		}
		if (StringUtils.isNotEmpty(orgRelations.getLevel())){
			writeRow.createCell(3).setCellValue(orgRelations.getLevel());
		}
		if (orgRelations.getNoOfContatcs() != null){
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
			StringBuilder clientContact=new StringBuilder();
			Sheet sheet = wb.createSheet(clientContact.append(orgName).append("_Clients Contacts_" ).append(dateFormat.format(new Date())).toString());
			Row writeRow = null;
			sheet = commonUtil.setExcelHeaders(sheet, style, headers,0);
			int row = 0;
			for (OrgSpoc OrgSpoc : vandorsContactList) {
				writeRow = sheet.createRow(row + 1);
				sheet = createContactXlsSheet(sheet, writeRow, OrgSpoc);
				row++;
			}
			/* file = new File(orgName + "_Clients Contact_" + dateFormat.format(new Date()) + ".xls");*/
			StringBuilder orgNameSB=new StringBuilder();
			file = File.createTempFile(orgNameSB.append(orgName).append("_Clients Contacts_" ).append(dateFormat.format(new Date())).toString(),".xls");
			file.setReadable(true);
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
	 * @throws Exception 
	 */
	private Sheet createContactXlsSheet(Sheet sheet, Row writeRow, OrgSpoc orgSpoc) throws Exception {
		
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
		
		/*UserRole userRole = userRoleService.getUserRoleByUserId(orgSpoc.getVendorUser().getId());
		if(null != userRole){
			if (userRole.getRoles().getDisplayName() != null && !"".equalsIgnoreCase(userRole.getRoles().getDisplayName())) {
				writeRow.createCell(5).setCellValue(userRole.getRoles().getDisplayName());
			}	
		}*/
		
		if (orgSpoc.getSpocStatus() != null && !"".equalsIgnoreCase(orgSpoc.getSpocStatus())) {
			writeRow.createCell(5).setCellValue(orgSpoc.getSpocStatus());
			
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
			StringBuilder vendor=new StringBuilder();
			Sheet sheet = wb.createSheet(vendor.append(orgName).append("_Vendor_").append(dateFormat.format(new Date())).toString());
			Row writeRow = null;
			sheet = commonUtil.setExcelHeaders(sheet, style, vendorHeaders,0);
			int row = 0;
			for (OrgRelations orgRelations : vandorsList) {
				writeRow = sheet.createRow(row + 1);
				sheet = createXlsSheet(sheet, writeRow, orgRelations);
				row++;
			}
			  StringBuilder orgNameSB=new StringBuilder();
			 file = File.createTempFile(orgNameSB.append(orgName).append("_Vendor_").append(dateFormat.format(new Date())).toString(),".xls");
			 file.setReadable(true);
			/* file = new File(orgName + "_Vendor_" + dateFormat.format(new Date()) + ".xls");*/
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
			StringBuilder vendorContacts=new StringBuilder();
			Sheet sheet = wb.createSheet(vendorContacts.append(orgName).append("_Vendor Contacts_").append(dateFormat.format(new Date())).toString());
			Row writeRow = null;
			sheet = commonUtil.setExcelHeaders(sheet, style, headers,0);
			int row = 0;
			for (OrgSpoc OrgSpoc : vandorsContactList) {
				writeRow = sheet.createRow(row + 1);
				sheet = createContactXlsSheet(sheet, writeRow, OrgSpoc);
				row++;
			}
			 StringBuilder orgNameSB=new StringBuilder();
			 file = File.createTempFile(orgNameSB.append(orgName).append("_Vendor Contacts_").append(dateFormat.format(new Date())).toString(),".xls");
			 file.setReadable(true);
			 /*file = new File(orgName + "_Vendor Contact_" + dateFormat.format(new Date()) + ".xls");*/
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
	public File getexportProjectEmployeeFile(List<ProjectMembers> employeeList,String exportType,Projects project,ProjectClients projectClients)throws Exception {
		String headers[] = Constants.PROJECT_ASSIGNED_EMPLOYEES;

		File file =null;
		
		Set<ProjectApproverHierarchy> projectApproverHierarchySet = projectClients.getProjectApproverHierarchyList();
		 StringBuilder sb = new StringBuilder();
		 for(ProjectApproverHierarchy ProjectApproverHierarchy:projectApproverHierarchySet ){
			 sb.append(ProjectApproverHierarchy.getApproverUserId().getFullName()).append(",");
		 }
		 String projectManager = sb.substring(0, sb.length()-1);
		 
	//	SimpleDateFormat dateFormat=project.getOrgLocationsId().getLocFullFormDateFormatter();
		if(!"pdf".equalsIgnoreCase(exportType)){
			Workbook wb = new XSSFWorkbook();
			CellStyle style = commonUtil.getCellStyle(wb);
			//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			StringBuilder projName=new StringBuilder();
			Sheet sheet = wb.createSheet(projName.append(project.getProjectName()).append("_").append(commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),new Date())).toString());
			Row writeRow = null;
			setExcelTitle(sheet,project.getProjectName(),project,wb,writeRow,projectManager);
			int rowCount = 6;
			sheet = commonUtil.setExcelHeaders(sheet, style, headers,rowCount);
			int row = 7;
			for (ProjectMembers projectMembers : employeeList) {
				writeRow = sheet.createRow(row + 1);
				sheet = createprojectMembersXlsSheet(sheet, writeRow, projectMembers);
				row++;
			}
			 StringBuilder projNameSB=new StringBuilder();
			 file = File.createTempFile(projNameSB.append(project.getProjectName()).append("_").append(commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),new Date())).toString(),".xls");
			 file.setReadable(true);
			 /*file = new File(projectName +"_" + dateFormat.format(new Date()) + ".xls");*/
			FileOutputStream fos = new FileOutputStream(file);
			try {
				wb.write(fos);
				fos.close();
			} catch (IOException e) {
				logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));
			}
		}else{
			file = pdfUtil.getProjectEmployeesExportPdf(employeeList,project.getProjectName(),project,projectManager);
		}
		
		return file;
	}

//	private Sheet setExcelHeaders(Sheet sheet, CellStyle style, String[] headers) {
//		Row excelHeader = sheet.createRow(7);
//		for (int i = 0; i < headers.length; i++) {
//			Cell cell = excelHeader.createCell(i);
//			cell.setCellValue(headers[i]);
//			cell.setCellStyle(style);
//		}
//		return sheet;
//	}

	private void setExcelTitle(Sheet sheet, String projectName, Projects project, Workbook wb, Row writeRow, String projectManager) {
		
		//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		//SimpleDateFormat dateFormat=project.getOrgLocationsId().getLocFullFormDateFormatter();
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
		 StringBuilder value=new StringBuilder();
		 if(project.getStartDate()!=null && project.getEndDate() !=null){
			 String convertedStartDate = commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),project.getStartDate());
			 String convertedEndDate = commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),project.getEndDate());
			 cell6.setCellValue(value.append(convertedStartDate).append(" to ").append(convertedEndDate).toString()); 
		 }else{
			 String convertedStartDate = commonUtil.dateStrFromOrgDateFormatter(project.getOrgLocationsId().getDateFormat(),project.getStartDate());
			 cell6.setCellValue(value.append(convertedStartDate).append(" to ").append("--").toString()); 
		 }
		 cell6.setCellStyle(style);
	}

	private Sheet createprojectMembersXlsSheet(Sheet sheet, Row writeRow, ProjectMembers projectMember) {
		if(projectMember.getProjectMemberCandidteId().getConsultantUser() != null){
			 UserRole userRole;
			try {
				userRole = userRoleService.getUserRoleByUserIdAndLocationId(projectMember.getProjectMemberCandidteId().getConsultantUser().getId(), projectMember.getProjects().getOrgLocationsId().getId());
				 if(userRole != null){
					 projectMember.setUserRoleType(userRole.getUserType());
				 } 
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }else{
			 projectMember.setUserRoleType("Candidate");
		 }
		
		if (projectMember.getEmpName() != null && !"".equalsIgnoreCase(projectMember.getEmpName())) {
			writeRow.createCell(0).setCellValue(projectMember.getEmpName());
		}
		
		if (projectMember.getStartDateStr() != null && !"".equalsIgnoreCase(projectMember.getStartDateStr())) {
			writeRow.createCell(1).setCellValue(projectMember.getStartDateStr());
		}
		if (projectMember.getEndDateStr() != null && !"".equalsIgnoreCase(projectMember.getEndDateStr())) {
			writeRow.createCell(2).setCellValue(projectMember.getEndDateStr());
		}else {
			writeRow.createCell(2).setCellValue("--");
		}
		
		if (projectMember.getCurrentBillRate() != null && projectMember.getCurrentBillRate() > 0 ) {
			writeRow.createCell(3).setCellValue(projectMember.getBillRateCurrency() +"  "+ projectMember.getCurrentBillRate() +"  "+projectMember.getRateType());
		}else {
			writeRow.createCell(3).setCellValue("--");
		}
		if (projectMember.getPayRate() != null && projectMember.getPayRate() > 0 ) {
			writeRow.createCell(4).setCellValue(projectMember.getPayRateCurrency() +"  "+ projectMember.getPayRate() +"  "+ projectMember.getPayRateType());
		}else {
			writeRow.createCell(4).setCellValue("--"); 
		}
		if (projectMember.getEstimatedHours() != null && projectMember.getEstimatedHours() > 0 ) {
			writeRow.createCell(5).setCellValue(projectMember.getEstimatedHours());
		}else {
			writeRow.createCell(5).setCellValue("--"); 
		}
		if (projectMember.getEstimatedBudget() != null && projectMember.getEstimatedBudget() > 0 ) {
			writeRow.createCell(6).setCellValue(projectMember.getEstimatedBudget());
		}else {
			writeRow.createCell(6).setCellValue("--");
		}
		if (projectMember.getPercentageOfWork() != null ) {
			writeRow.createCell(7).setCellValue(projectMember.getPercentageOfWork().doubleValue());
		}else {
			writeRow.createCell(7).setCellValue("--");
		}
		
		if(projectMember.getMasterCandidateId() != null){
			 Float approvalTimesheetsSubmissionsCountAsEmployee = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId(), "APPROVED");
			writeRow.createCell(8).setCellValue(approvalTimesheetsSubmissionsCountAsEmployee);
		}else {
			writeRow.createCell(8).setCellValue("--");
		}
		
	 
		return sheet;
	}

}
