package com.bis.sourcelead.util.dynamicReport;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dto.AssociateJoineReportDTO;
import com.bis.sourcelead.dto.LoanAndAdvanceReportDTO;
import com.bis.sourcelead.dto.MarginSheetReportDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TicketDTO;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.reportsDTO.MissingTimesheetRepotDTO;
import com.bis.sourcelead.reportsDTO.MissingTimesheetResultRepotDTO;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.mchange.v3.filecache.FileCache;

import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * @author Laxman
 */
@SuppressWarnings("unused")
@Component
public class DynamicReport {
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	ProjectAssignTasksService projectAssignTasksService;
	
	@Autowired
	private ProjectMembersDao  projectMembersDao;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	UserRoleService userRoleService;
	
	/**
	 * @author Laxman
	 * This method is used to assetmanagement pdf file
	 * @param responseList
	 * @param orgLocations
	 * @return file
	 * @throws IOException
	 */
	public File assetmanagementPdfExport(ResponseList<Asset> responseList, OrgLocations orgLocations) throws IOException {
		 File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("asset_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Name", "Name", type.stringType()),
							col.column("Serial Number", "Serial", type.stringType()),
							col.column("Category", "Category", type.stringType()),
							col.column("Model", "Model", type.stringType()),
							col.column("Manufactured Date", "IssueDate", type.stringType()),
							col.column("MAC Address", "Mac", type.stringType()),
							col.column("Purchase Date", "PDate", type.stringType()),
							col.column("Cost", "Cost", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.LEFT),
							col.column("Status", "Status", type.stringType()))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createPdfDataSource(responseList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	
	/**
	 * @author Laxman
	 * This method is used to assetmanagement xls file
	 * @param responseList
	 * @param orgLocations
	 * @return file
	 * @throws IOException
	 */
	public File assetmanagementExcelExport(ResponseList<Asset> responseList, OrgLocations orgLocations) throws IOException {
		 File file = null;
			try {
			    StringBuilder fileSB = new StringBuilder();
				file = File.createTempFile(fileSB.append("asset_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
				file.setReadable(true);
				JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                        .setIgnorePageMargins(true)
                        .setWhitePageBackground(false)
                        .setRemoveEmptySpaceBetweenColumns(true);

				report().setTemplate(Templates.reportTemplate)
						.setColumnTitleStyle(Templates.columnTitleStyle)
						.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
						.ignorePageWidth()
						.ignorePagination()
						.columns(
								col.column("Name", "Name", type.stringType()),
								col.column("Serial Number", "Serial", type.stringType()),
								col.column("Category", "Category", type.stringType()),
								col.column("Model", "Model", type.stringType()),
								col.column("Manufactured Date", "IssueDate", type.stringType()),
								col.column("MAC Address", "Mac", type.stringType()),
								col.column("Purchase Date", "PDate", type.stringType()),
								col.column("Cost", "Cost", type.bigDecimalType()).setHorizontalAlignment(HorizontalAlignment.LEFT),
								col.column("Status", "Status", type.stringType()))
						.setDataSource(createPdfDataSource(responseList)).toXls(xlsExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
    }
	
	/**
	 * @author Laxman
	 * @param responseList
	 * @return dataSource
	 */
	private DRDataSource createPdfDataSource(ResponseList<Asset> responseList) {
		DRDataSource dataSource = new DRDataSource("Name","Serial", "Category", "Model", "IssueDate", "Mac", "PDate", "Cost", "Status");
		for(Asset asset:responseList.getData()) {
			String modelName = "--";
			String issueDate = "--";
			String macAddress = "--";
			String name = "--";
			String manufacturedDate = "--";
			String purchaseDate = "--";
			BigDecimal cost = new BigDecimal(0);
			if(StringUtils.isNotEmpty(asset.getModel())) {
				modelName = asset.getModel();
			}
			if(asset.getIssueDate() != null) {
				manufacturedDate = commonUtil.dateStrFromOrgDateFormatter(asset.getOrgLocations().getDateFormat(), asset.getIssueDate());
			}
			if(StringUtils.isNotEmpty(asset.getName())) {
				name = asset.getName();
			}
			if(StringUtils.isNotEmpty(asset.getMacAddress())) {
				 macAddress = asset.getMacAddress();
			}
			if(asset.getPurchaseDate() != null) {
				purchaseDate = commonUtil.dateStrFromOrgDateFormatter(asset.getOrgLocations().getDateFormat(), asset.getPurchaseDate());
			}
			 if(asset.getCost() != null) {
				 cost = asset.getCost();
			 }
			dataSource.add(name,asset.getSlno(), asset.getAssetCategory().getCategoryName(), modelName,manufacturedDate,macAddress,purchaseDate,cost,asset.getWorkingStatus());
		}
		return dataSource;
	}


	public File assignAssetmanagementPdfExport(ResponseList<AssetAssign> responseList, OrgLocations orgLocations) throws Exception { 
		File file = null;
	try {
	    StringBuilder fileSB = new StringBuilder();
		file = File.createTempFile(fileSB.append("aaignAsset_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
		file.setReadable(true);
		JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
		report().setTemplate(Templates.reportTemplate)
				.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
				.columns(
						col.column("Emp Id", "empId", type.stringType()),
						col.column("Emp Name", "empName", type.stringType()),
						col.column("Asset Name", "assetName", type.stringType()),
						col.column("Serail Number", "serialNumber", type.stringType()),
						col.column("Assigne On", "assignedOn", type.stringType()),
						col.column("Retunend On", "returnedOn", type.stringType()),
						col.column("Shift Name", "shiftName", type.stringType()),
						col.column("Category Name", "category", type.stringType()))
				.pageFooter(Templates.footerComponent)
				.setDataSource(createAssignAssetPdfDataSource(responseList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	return file;
	}


	


	public File assignAssetmanagementExcelExport(ResponseList<AssetAssign> responseList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("aaignAsset_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(false)
                    .setRemoveEmptySpaceBetweenColumns(true);

			report().setTemplate(Templates.reportTemplate)
					.setColumnTitleStyle(Templates.columnTitleStyle)
					.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
					.ignorePageWidth()
					.ignorePagination()
					.columns(
							col.column("Emp Id", "empId", type.stringType()),
							col.column("Emp Name", "empName", type.stringType()),
							col.column("Asset Name", "assetName", type.stringType()),
							col.column("Serail Number", "serialNumber", type.stringType()),
							col.column("Assigne On", "assignedOn", type.stringType()),
							col.column("Retunend On", "returnedOn", type.stringType()),
							col.column("Shift Name", "shiftName", type.stringType()),
							col.column("Category Name", "category", type.stringType()))
					.setDataSource(createAssignAssetPdfDataSource(responseList)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	return file;
	}
	
	private DRDataSource createAssignAssetPdfDataSource(ResponseList<AssetAssign> responseList) throws Exception {
		DRDataSource dataSource = new DRDataSource("empId","empName", "assetName", "serialNumber", "assignedOn", "returnedOn", "shiftName", "category");
		for(AssetAssign aa:responseList.getData()) {
			String  employeeId= "--";
			String assetName = "--";
			String assignedOn = "--";
			String returnedOn = "--";
			Employees emp = employeesService.getEmployeeByUserId(aa.getUserRole().getUser().getId());
			if(emp != null && emp.getEmployeeId() != null) {
				employeeId = emp.getEmployeeId();
			}
			if(aa.getAsset().getName() != null) {
				assetName = aa.getAsset().getName();
			}
			if(aa.getAssignedOn() != null) {
				assignedOn = commonUtil.dateStrFromOrgDateFormatter(aa.getAsset().getOrgLocations().getDateFormat(), aa.getAssignedOn());
			}
			if(aa.getReturnedOn() != null) {
				returnedOn = commonUtil.dateStrFromOrgDateFormatter(aa.getAsset().getOrgLocations().getDateFormat(), aa.getReturnedOn());
			}

			dataSource.add(employeeId,aa.getUserRole().getUser().getFullName(),assetName,aa.getAsset().getSlno(),assignedOn,returnedOn,aa.getShift().getShiftName(),aa.getAsset().getAssetCategory().getCategoryName());
		}
		return dataSource;
	}


	public File payrollTaxAndWagesReportPdfExport(ResponseList<PayrollReportDTO> responseList, OrgLocations orgLocations) throws IOException
	{
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("taxableWages_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Emp Id", "empId", type.stringType()),
							col.column("Emp Name", "empName", type.stringType()),
							col.column("SSN", "ssn", type.stringType()),
							col.column("Gross Wages", "grossWages", type.bigDecimalType()),
							col.column("Federal", "federal", type.bigDecimalType()),
							col.column("SOS Sec", "sos", type.bigDecimalType()),
							col.column("Medicare", "medicare", type.bigDecimalType()),
							col.column("FUTA", "futa", type.bigDecimalType()),
					        col.column("State", "state", type.stringType()),
					        col.column("ST", "st", type.bigDecimalType()),
					        col.column("Local Tax", "localTax", type.bigDecimalType()),
					        col.column("Suta", "suta", type.bigDecimalType()),
					        col.column("YTD", "ytd", type.bigDecimalType()))
					
					.pageFooter(Templates.footerComponent)
					.setDataSource(createTaxableWagesDataSource(responseList)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}


	public File payrollTaxAndWagesReportExcelExport(ResponseList<PayrollReportDTO> responseList, OrgLocations orgLocations) throws IOException
	{
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("taxableWages_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(false)
                    .setRemoveEmptySpaceBetweenColumns(true);

			report().setTemplate(Templates.reportTemplate)
					.setColumnTitleStyle(Templates.columnTitleStyle)
					.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
					.ignorePageWidth()
					.ignorePagination()
					.columns(
							col.column("Emp Id", "empId", type.stringType()),
							col.column("Emp Name", "empName", type.stringType()),
							col.column("SSN", "ssn", type.stringType()),
							col.column("Gross Wages", "grossWages", type.bigDecimalType()),
							col.column("Federal", "federal", type.bigDecimalType()),
							col.column("SOS Sec", "sos", type.bigDecimalType()),
							col.column("Medicare", "medicare", type.bigDecimalType()),
							col.column("FUTA", "futa", type.bigDecimalType()),
					        col.column("State", "state", type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT),
					        col.column("ST", "st", type.bigDecimalType()),
					        col.column("Local Tax", "localTax", type.bigDecimalType()),
					        col.column("Suta", "suta", type.bigDecimalType()),
					        col.column("YTD", "ytd", type.bigDecimalType()))
					
					.setDataSource(createTaxableWagesDataSource(responseList)).toXls(xlsExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}
	
	
	
	
	private DRDataSource createTaxableWagesDataSource(ResponseList<PayrollReportDTO> responseList)
	{
		DRDataSource dataSource = new DRDataSource("empId","empName", "ssn", "grossWages", "federal", "sos", "medicare", "futa", "state", "st", "localTax", "suta", "ytd");
		for(PayrollReportDTO pr:responseList.getData()) {
			  String empId = "--";
			  BigDecimal grossWages = BigDecimal.ZERO;
			  BigDecimal federal = BigDecimal.ZERO;
			  BigDecimal sos = BigDecimal.ZERO;
			  BigDecimal medicare = BigDecimal.ZERO;
			  BigDecimal futa = BigDecimal.ZERO;
			  BigDecimal st = BigDecimal.ZERO;
			  BigDecimal localTax = BigDecimal.ZERO;
			  BigDecimal suta = BigDecimal.ZERO;
			  BigDecimal ytd = BigDecimal.ZERO;
			  
			  if(pr.getEmpId() != null) {
				  empId = pr.getEmpId();
			  }
			  if(pr.getGrossPay() != null) {
				  grossWages = pr.getGrossPay();
			  }
			  if(pr.getFederalTax() != null) {
				  federal =  pr.getFederalTax();
			  }
			  if(pr.getSocialSecTax() != null) {
				  sos = pr.getSocialSecTax();
			  }
			  if(pr.getMedicare() != null) {
				  medicare = pr.getMedicare();
			  }
			  if(pr.getFuta() != null) {
				  futa = pr.getFuta();
			  }
			  if(pr.getStateTax() != null) {
				  st = pr.getStateTax();
			  }
			  if(pr.getLocalTax() != null) {
				  localTax = pr.getLocalTax();
			  }
			  if(pr.getFuta() != null) {
				  suta = pr.getFuta();
			  }
			  if(pr.getYtdTaxes() != null) {
				  ytd  = pr.getYtdTaxes();
			  }
			  
			dataSource.add(empId,pr.getEmpName(),pr.getSsnNumber(),grossWages,federal,sos,medicare,futa,pr.getResState(),st,localTax,suta,ytd);
		}
		return dataSource;
	}


	public File contractEmployeeReportPDFExport(ResponseList<PayrollReportDTO> responseList, OrgLocations orgLocations) throws IOException
	{
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("ContractEmployeeReport_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(Constants.CONTRACT_EMPLOYEE_PAYROLL).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Associate Id", "assId", type.stringType()),
							col.column("Associate Name", "assName", type.stringType()),
							col.column("Vendor", "vendor", type.stringType()),
							col.column("Pay Date", "payDate", type.stringType()),
							col.column("InvoiceId", "invoiceId", type.stringType()),
							col.column("Amount", "amount", type.stringType()),
							col.column("Paid Amount", "paidAmount", type.stringType()))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createContractEmployeeDataSource(responseList)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}


	private DRDataSource createContractEmployeeDataSource(ResponseList<PayrollReportDTO> responseList)
	{
		DRDataSource dataSource = new DRDataSource("assId","assName", "vendor", "payDate", "invoiceId", "amount", "paidAmount");
		for(PayrollReportDTO pr:responseList.getData()) {
			  String assId = "--";
			  if(StringUtils.isNotBlank(pr.getEmpId())) {
				  assId = pr.getEmpId();
			  }
			dataSource.add(assId,pr.getEmpName(),pr.getVendorName(),pr.getPayCheckDate(),pr.getInvoiceId(),pr.getAssAmount(),pr.getInvPaidAmount());
		}
		return dataSource;
	}


	public File contractEmployeeReportExcelExport(ResponseList<PayrollReportDTO> responseList, OrgLocations orgLocations) throws IOException
	{
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("ContractEmployeeReport_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(false)
                    .setRemoveEmptySpaceBetweenColumns(true);

			report().setTemplate(Templates.reportTemplate)
					.setColumnTitleStyle(Templates.columnTitleStyle)
					.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
					.ignorePageWidth()
					.ignorePagination()
					.columns(
							col.column("Associate Id", "assId", type.stringType()),
							col.column("Associate Name", "assName", type.stringType()),
							col.column("Vendor", "vendor", type.stringType()),
							col.column("Pay Date", "payDate", type.stringType()),
							col.column("InvoiceId", "invoiceId", type.stringType()),
							col.column("Amount", "amount", type.stringType()),
							col.column("Paid Amount", "paidAmount", type.stringType()))
					
					.setDataSource(createContractEmployeeDataSource(responseList)).toXls(xlsExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}


	/**
	 * @author Laxman
	 *  this is method using for Employee task reports pdf
	 * @param projectResponseList
	 * @param orgLocations
	 * @return file
	 * @throws Exception
	 */
	public File employeeTaskReportPdfExport(ResponseList<ProjectAssignTask> projectResponseList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("EmployeeTaskReport_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			TextColumnBuilder<String> taskTitle = col.column("Task Name", "taskName", type.stringType())
                    .setWidth(300)
                    .setStretchWithOverflow(true).addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							taskTitle,
							col.column("Priority", "priority", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Project Name", "projectName", type.stringType()),
							col.column("Due Date", "dueDate", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Completion", "completion", type.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Status", "status", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createEmployeeTaskDataSource(projectResponseList)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}

	/**
	 * @author Laxman
	 * this is method using for Employee task reports excel
	 * @param projectResponseList
	 * @param orgLocations
	 * @return file
	 * @throws Exception
	 */

	public File employeeTaskReportExcelExport(ResponseList<ProjectAssignTask> projectResponseList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("EmployeeTaskReport_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(false)
                    .setRemoveEmptySpaceBetweenColumns(true);

			TextColumnBuilder<String> taskTitle = col.column("Task Name", "taskName", type.stringType())
                    .setFixedWidth(550)
                    .setStretchWithOverflow(true)
                    .addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			TextColumnBuilder<String> projectName = col.column("Project Name", "projectName", type.stringType())
                    .setFixedWidth(200)
                    .setStretchWithOverflow(true)
                    .addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			report().setTemplate(Templates.reportTemplate)
					.setColumnTitleStyle(Templates.columnTitleStyle)
					.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
					.ignorePageWidth()
					.ignorePagination()
					.columns(
							taskTitle,
							col.column("Priority", "priority", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							projectName,
							col.column("Due Date", "dueDate", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Completion", "completion", type.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Status", "status", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER))
					
					.setDataSource(createEmployeeTaskDataSource(projectResponseList)).toXls(xlsExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}
	
	/**
	 * @author Laxman
	 * this method is EmployeeTask report implementation
	 * @param projectResponseList
	 * @return dataSource
	 */
	private DRDataSource createEmployeeTaskDataSource(ResponseList<ProjectAssignTask> projectResponseList)
	{
		DRDataSource dataSource = new DRDataSource("taskName","priority", "projectName", "dueDate", "completion", "status");
		for(ProjectAssignTask pat:projectResponseList.getData()) {
			String  priority = "--";
			String dueDate = "--";
			Integer completion = 0;
			  if(pat.getProjectTaskId().getPriority() != null) {
				  priority  = pat.getProjectTaskId().getPriority().getName();
			  }
			  if(pat.getProjectTaskId().getDueDate() != null) {
				  dueDate = commonUtil.dateStrFromOrgDateFormatter(pat.getProjectTaskId().getProjectId().getOrgLocationsId().getDateFormat(), pat.getProjectTaskId().getDueDate());
			  }
			  if(pat.getProjectTaskId().getPercentage() != null) {
				  completion =   pat.getProjectTaskId().getPercentage();
			  }
			dataSource.add(pat.getProjectTaskId().getTaskName(),priority,pat.getProjectTaskId().getProjectId().getProjectName(),dueDate,completion,pat.getProjectTaskId().getTaskStatus().getName());
		}
		return dataSource;
	}


	/**
	 * @author Laxman
	 * this method is Within the org overdue tasks list report pdf
	 * @param overdueTaskReportList
	 * @param orgLocations
	 * @return file
	 * @throws Exception
	 */
	public File overdueTaskReportPdfExport(ResponseList<ProjectTasks> overdueTaskReportList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("OverdueTaskReport_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			TextColumnBuilder<String> taskTitle = col.column("Task Name", "taskName", type.stringType())
                    .setWidth(150)
                    .setStretchWithOverflow(true).addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			TextColumnBuilder<Integer> completion = col.column("Completion", "completion", type.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setWidth(50)
                    .setStretchWithOverflow(true).addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			TextColumnBuilder<String> priority = col.column("Priority", "priority", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER)
                    .setWidth(100)
                    .setStretchWithOverflow(true).addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			
			TextColumnBuilder<String> assignTo = col.column("Assign To", "assignTo", type.stringType())
                    .setWidth(200)
                    .setStretchWithOverflow(true).addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							taskTitle,
							priority,
							col.column("Task Type", "taskType", type.stringType()),
							assignTo,
							col.column("Project Name", "projectName", type.stringType()),
							col.column("Due Date", "dueDate", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							completion)
					.pageFooter(Templates.footerComponent)
					.setDataSource(createOverdueTaskDataSource(overdueTaskReportList)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}

	/**
	 * @author Laxman
	 * this method is Within the org overdue tasks list report xls
	 * @param overdueTaskReportList
	 * @param orgLocations
	 * @return file
	 * @throws Exception
	 */
	public File overdueTaskReportExcelExport(ResponseList<ProjectTasks> overdueTaskReportList, OrgLocations orgLocations) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("OverdueTaskReport_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(false)
                    .setRemoveEmptySpaceBetweenColumns(true);

			TextColumnBuilder<String> taskTitle = col.column("Task Name", "taskName", type.stringType())
                    .setFixedWidth(550)
                    .setStretchWithOverflow(true)
                    .addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			TextColumnBuilder<String> projectName = col.column("Project Name", "projectName", type.stringType())
                    .setFixedWidth(200)
                    .setStretchWithOverflow(true)
                    .addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			TextColumnBuilder<String> assignTo = col.column("Assign To", "assignTo", type.stringType())
                    .setFixedWidth(300)
                    .setStretchWithOverflow(true)
                    .addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			report().setTemplate(Templates.reportTemplate)
					.setColumnTitleStyle(Templates.columnTitleStyle)
					.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
					.ignorePageWidth()
					.ignorePagination()
					.columns(
							taskTitle,
							col.column("Priority", "priority", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Task Type", "taskType", type.stringType()),
							assignTo,
							projectName,
							col.column("Due Date", "dueDate", type.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
							col.column("Completion", "completion", type.integerType()).setHorizontalAlignment(HorizontalAlignment.CENTER))
					
					.setDataSource(createOverdueTaskDataSource(overdueTaskReportList)).toXls(xlsExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}
	

	/**
	 * @author Laxman
	 * this method is Within the org overdue tasks list report pdf & xls functionality
	 * @param overdueTaskReportList
	 * @return dataSource
	 * @throws Exception 
	 */
	private DRDataSource createOverdueTaskDataSource(ResponseList<ProjectTasks> overdueTaskReportList) throws Exception {
		DRDataSource dataSource = new DRDataSource("taskName","priority", "taskType","assignTo", "projectName", "dueDate", "completion");
		for(ProjectTasks pt:overdueTaskReportList.getData()) {
			String  priority = "--";
			String dueDate = "--";
			String assignTo = "--";
			Integer completion = 0;
			  if(pt.getPriority() != null) {
				  priority  = pt.getPriority().getName();
			  }
			  if(pt.getDueDate() != null) {
				  dueDate = commonUtil.dateStrFromOrgDateFormatter(pt.getProjectId().getOrgLocationsId().getDateFormat(), pt.getDueDate());
			  }
			  if(pt.getDueDate() != null) {
				  completion =   pt.getPercentage();
			  }
			  
			  List<Long> assignedMembersIds = projectMembersDao.getAllProjectMembersIdsByProjectIdAndOrgLocationId(pt.getProjectId().getId(), pt.getProjectId().getOrgLocationsId().getId());
			  
			  List<ProjectAssignTask> assignUser = projectAssignTasksService.getProjectAssignTaskListByProjectTaskId(pt.getId());
			  List<String> assignedTaskUsersList =  new ArrayList<>();
				for(ProjectAssignTask pat:assignUser) {
					if(pat.getProjectMemberId() != null) {
						if(pat.getProjectMemberId().getProjectMemberCandidteId() != null) {
							if(assignedMembersIds.contains(pat.getProjectMemberId().getId().longValue())) {
								assignedTaskUsersList.add(pat.getProjectMemberId().getProjectMemberCandidteId().getFullName());
							}
						}
					}
				 }
				 String citiesCommaSeparated = String.join(",", assignedTaskUsersList);
			dataSource.add(pt.getTaskName(),priority,pt.getTaskType().getName() ,citiesCommaSeparated,pt.getProjectId().getProjectName(),dueDate,completion);
		}
		return dataSource;
	}
	
	public File marginSheetReportPdfExport(ResponseList<MarginSheetReportDTO> projectMembersDTOList, String orgLocationDateFormat,String orglocationName) throws IOException {
		 File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("margin_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocationDateFormat,new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orglocationName).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Associate Id", "Associate Id", type.stringType()),
							col.column("Associate Name", "Associate Name", type.stringType()),
							col.column("Associate Join Date", "Associate Join Date", type.stringType()),
							col.column("Client Name", "Client Name", type.stringType()),
							col.column("Project Name", "Project Name", type.stringType()),
							col.column("Project Duration", "Project Duration", type.stringType()),
							col.column("Pay Rate", "Pay Rate", type.stringType()),
							col.column("Bill Rate", "Bill Rate", type.stringType()),
							col.column("Margin", "Margin", type.stringType()))
					.pageFooter(Templates.footerComponent)
					.setDataSource(marginSheetReportDataSource(projectMembersDTOList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	
	public File marginSheetReportExcelExport(ResponseList<MarginSheetReportDTO> projectMembersDTOList,String orgLocationDateFormat,String orglocationName) throws IOException {
		 File file = null;
			try {
			    StringBuilder fileSB = new StringBuilder();
				file = File.createTempFile(fileSB.append("margin_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocationDateFormat,new Date())).toString(),".xls");
				file.setReadable(true);
				JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                      .setIgnorePageMargins(true)
                      .setWhitePageBackground(false)
                      .setRemoveEmptySpaceBetweenColumns(true);

				report().setTemplate(Templates.reportTemplate)
						.setColumnTitleStyle(Templates.columnTitleStyle)
						.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
						.ignorePageWidth()
						.ignorePagination()
						.columns(
								col.column("Associate Id", "Associate Id", type.stringType()),
								col.column("Associate Name", "Associate Name", type.stringType()),
								col.column("Associate Join Date", "Associate Join Date", type.stringType()),
								col.column("Client Name", "Client Name", type.stringType()),
								col.column("Project Name", "Project Name", type.stringType()),
								col.column("Project Duration", "Project Duration", type.stringType()),
								col.column("Pay Rate", "Pay Rate", type.stringType()),
								col.column("Bill Rate", "Bill Rate", type.stringType()),
								col.column("Margin", "Margin", type.stringType()))
						.setDataSource(marginSheetReportDataSource(projectMembersDTOList)).toXls(xlsExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
  }
	
	private DRDataSource marginSheetReportDataSource(ResponseList<MarginSheetReportDTO> responseList) {
		DRDataSource dataSource = new DRDataSource("Associate Id","Associate Name","Associate Join Date", "Client Name","Project Name","Project Duration", "Pay Rate", "Bill Rate", "Margin");
		for(MarginSheetReportDTO margin:responseList.getData()) {
			String empId = "--";
			
			if(margin.getEmployeeId() !=null) {
			 empId = margin.getEmployeeId();
			}
			
			String	associateName = margin.getEmployeeName();
			
			String  associateJoinDate = margin.getAssociateJoinedDate();
		
			String	clientName = margin.getClinetName();
			
			String	projectName = margin.getProjectName();
			
			String  projectDuration = margin.getProjectDuration();
			
			String	 payRate = margin.getPayRateCurrency() +" "+String.valueOf(margin.getPayRate())+" "+margin.getPayRateType();
				 
			String	 billRate = margin.getBillRateTypeCurrency() +" "+String.valueOf(margin.getBillRate())+" "+margin.getBillRateType();
			 
			String marginRate = String.valueOf(margin.getMarginRate());
			
			dataSource.add(empId,associateName,associateJoinDate, clientName,projectName,projectDuration, payRate,billRate,marginRate);
		}
		return dataSource;
	}
	
	public File associateJoinedAndTerminatedReportPdfExport(ResponseList<AssociateJoineReportDTO> associateDTOList, String orgLocationsDateFormat,String orgLocationName,String isTerminated) throws IOException {
		 File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("associateJoined_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocationsDateFormat,new Date())).toString(),".pdf");
			file.setReadable(true);
			if(isTerminated.equalsIgnoreCase(Constants.YES)){
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocationName).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Associate Id", "Associate Id", type.stringType()),
							col.column("Associate Name", "Associate Name", type.stringType()),
							col.column("Gender", "Gender", type.stringType()),
							col.column("Emp Type", "Emp Type", type.stringType()),
							col.column("Department", "Department", type.stringType()),
							col.column("Role", "Role", type.stringType()),
							col.column("Rate", "Rate", type.stringType()),
							col.column("Date of Join", "Date of Join", type.stringType()),
							col.column("Manager", "Manager", type.stringType()),
							col.column("Terminated On", "Terminated On", type.stringType()),
							col.column("Terminate Reason", "Terminate Reason", type.stringType()),
							col.column("Shift", "Shift", type.stringType()),
							col.column("Probation Status", "Probation Status", type.stringType()))
					.pageFooter(Templates.footerComponent)
					.setDataSource(associateJoinedReportDataSource(associateDTOList,orgLocationsDateFormat,isTerminated)).toPdf(pdfExporter);
			}else{
				JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
				report().setTemplate(Templates.reportTemplate)
						.title(cmp.text(orgLocationName).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
						.columns(
								col.column("Associate Id", "Associate Id", type.stringType()),
								col.column("Associate Name", "Associate Name", type.stringType()),
								col.column("Gender", "Gender", type.stringType()),
								col.column("Emp Type", "Emp Type", type.stringType()),
								col.column("Department", "Department", type.stringType()),
								col.column("Role", "Role", type.stringType()),
								col.column("Rate", "Rate", type.stringType()),
								col.column("Date of Join", "Date of Join", type.stringType()),
								col.column("Manager", "Manager", type.stringType()),
								col.column("Shift", "Shift", type.stringType()),
								col.column("Probation Status", "Probation Status", type.stringType()))
						.pageFooter(Templates.footerComponent)
						.setDataSource(associateJoinedReportDataSource(associateDTOList,orgLocationsDateFormat,isTerminated)).toPdf(pdfExporter);
			}
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public File associateJoinedAndTerminatedReportExcelExport(ResponseList<AssociateJoineReportDTO> associateDTOList, String orgLocationsDateFormat,String orgLocationName,String isTerminated) throws IOException {
		 File file = null;
			try {
			    StringBuilder fileSB = new StringBuilder();
				file = File.createTempFile(fileSB.append("associateJoined_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocationsDateFormat,new Date())).toString(),".xls");
				file.setReadable(true);
				if(isTerminated.equalsIgnoreCase(Constants.YES)){
				JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                     .setIgnorePageMargins(true)
                     .setWhitePageBackground(false)
                     .setRemoveEmptySpaceBetweenColumns(true);

				report().setTemplate(Templates.reportTemplate)
						.setColumnTitleStyle(Templates.columnTitleStyle)
						.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
						.ignorePageWidth()
						.ignorePagination()
						.columns(
								col.column("Associate Id", "Associate Id", type.stringType()),
								col.column("Associate Name", "Associate Name", type.stringType()),
								col.column("Gender", "Gender", type.stringType()),
								col.column("Emp Type", "Emp Type", type.stringType()),
								col.column("Department", "Department", type.stringType()),
								col.column("Role", "Role", type.stringType()),
								col.column("Rate", "Rate", type.stringType()),
								col.column("Date of Join", "Date of Join", type.stringType()),
								col.column("Manager", "Manager", type.stringType()),
								col.column("Terminated On", "Terminated On", type.stringType()),
								col.column("Terminate Reason", "Terminate Reason", type.stringType()),
								col.column("Shift", "Shift", type.stringType()),
								col.column("Probation Status", "Probation Status", type.stringType()))
						.setDataSource(associateJoinedReportDataSource(associateDTOList,orgLocationsDateFormat,isTerminated)).toXls(xlsExporter);
				}else{
					JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
		                      .setIgnorePageMargins(true)
		                      .setWhitePageBackground(false)
		                      .setRemoveEmptySpaceBetweenColumns(true);

						report().setTemplate(Templates.reportTemplate)
								.setColumnTitleStyle(Templates.columnTitleStyle)
								.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
								.ignorePageWidth()
								.ignorePagination()
								.columns(
										col.column("Associate Id", "Associate Id", type.stringType()),
										col.column("Associate Name", "Associate Name", type.stringType()),
										col.column("Gender", "Gender", type.stringType()),
										col.column("Emp Type", "Emp Type", type.stringType()),
										col.column("Department", "Department", type.stringType()),
										col.column("Role", "Role", type.stringType()),
										col.column("Rate", "Rate", type.stringType()),
										col.column("Date of Join", "Date of Join", type.stringType()),
										col.column("Manager", "Manager", type.stringType()),
										col.column("Shift", "Shift", type.stringType()),
										col.column("Probation Status", "Probation Status", type.stringType()))
								.setDataSource(associateJoinedReportDataSource(associateDTOList,orgLocationsDateFormat,isTerminated)).toXls(xlsExporter);
				}
				} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
 }
	
	private DRDataSource associateJoinedReportDataSource(ResponseList<AssociateJoineReportDTO> associateDTOList,String dateFormat,String isTerminated) {
		DRDataSource dataSource = null;
		if(isTerminated.equalsIgnoreCase(Constants.YES)){
		 dataSource = new DRDataSource("Associate Id","Associate Name", "Gender", "Emp Type", "Department", "Role","Rate","Date of Join","Manager","Terminated On","Terminate Reason","Shift","Probation Status");
		}else{
		 dataSource = new DRDataSource("Associate Id","Associate Name", "Gender", "Emp Type", "Department", "Role","Rate","Date of Join","Manager","Shift","Probation Status");
		}
		for(AssociateJoineReportDTO associateData:associateDTOList.getData()) {
			String empId = "--";
			String rate = "--";
			String trminateReason = "--";
			String terminatedOn = "--";
			
			if(associateData.getEmployeeId() !=null) {
			 empId = associateData.getEmployeeId();
			}
			
			String	associateName = associateData.getEmpName();
		
			String	gender = associateData.getGender();
			
			String	empType = associateData.getEmpType();
				 
			String	departmentName = associateData.getDeptName();
			 
			String role = associateData.getUserRole();
			
			if(rate !=null){
			rate = associateData.getCurrency()+" "+associateData.getRate()+" "+associateData.getRatePer();
			}
			
			String	dateOfJoin = commonUtil.dateStrFromOrgDateFormatter(dateFormat,associateData.getHireDate());
			 
			String	manager = associateData.getReportingManager();
			 
			String shift = associateData.getShiftName();
			
			String probationStatus = associateData.getProbationStatus();
			
			if(isTerminated.equalsIgnoreCase(Constants.YES)){
			trminateReason = associateData.getTerminatedReason();
			
			terminatedOn = commonUtil.dateStrFromOrgDateFormatter(dateFormat,associateData.getTerminateDate());
			}
			
			if(isTerminated.equalsIgnoreCase(Constants.YES)){
			dataSource.add(empId,associateName, gender, empType,departmentName,role,rate,dateOfJoin,manager,terminatedOn,trminateReason,shift,probationStatus);
			}else{
			dataSource.add(empId,associateName, gender, empType,departmentName,role,rate,dateOfJoin,manager,shift,probationStatus);	
			}
		}
		return dataSource;
	}
	
	public File applicantsJoinedListExportPdf(ResponseList<RequirementSubmissionHistoryDTO> historyDTOList, String orgLocationDateFormat,String orglocationName) throws IOException {
		 File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("margin_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocationDateFormat,new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orglocationName).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Applicant Name", "Applicant Name", type.stringType()),
							col.column("Email Id", "Email Id", type.stringType()),
							col.column("Contact Number", "Contact Number", type.stringType()),
							col.column("Job Title", "Job Title", type.stringType()),
							col.column("Job Rating", "Job Rating", type.stringType()),
							col.column("Client", "Client", type.stringType()),
							col.column("Date Of Interview", "Date Of Interview", type.stringType()),
							col.column("Source", "Source", type.stringType()),
							col.column("Submitted On", "Submitted On", type.stringType()),
							col.column("Date Of Join", "Date Of Join", type.stringType()),
							col.column("Offer Rate", "Offer Rate", type.stringType()),
							col.column("Offer Rate Yearly", "Offer Rate Yearly", type.stringType()),
							col.column("Margin Rate Yearly", "Margin Rate Yearly", type.stringType()),
							col.column("Payment Term", "Payment Term", type.stringType()),
							col.column("Generate Invoice", "Generate Invoice", type.stringType()))
					.pageFooter(Templates.footerComponent)
					.setDataSource(applicantsJoinedSheetReportDataSource(historyDTOList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}
	

	
	public File applicantsJoinedListExportExcel(ResponseList<RequirementSubmissionHistoryDTO> historyDTOList,String orgLocationDateFormat,String orglocationName) throws IOException {
		 File file = null;
			try {
			    StringBuilder fileSB = new StringBuilder();
				file = File.createTempFile(fileSB.append("margin_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocationDateFormat,new Date())).toString(),".xls");
				file.setReadable(true);
				JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                     .setIgnorePageMargins(true)
                     .setWhitePageBackground(false)
                     .setRemoveEmptySpaceBetweenColumns(true);

				report().setTemplate(Templates.reportTemplate)
						.setColumnTitleStyle(Templates.columnTitleStyle)
						.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
						.ignorePageWidth()
						.ignorePagination()
						.columns(
								col.column("Applicant Name", "Applicant Name", type.stringType()),
								col.column("Email Id", "Email Id", type.stringType()),
								col.column("Contact Number", "Contact Number", type.stringType()),
								col.column("Job Title", "Job Title", type.stringType()),
								col.column("Job Rating", "Job Rating", type.stringType()),
								col.column("Client", "Client", type.stringType()),
								col.column("Date Of Interview", "Date Of Interview", type.stringType()),
								col.column("Source", "Source", type.stringType()),
								col.column("Submitted On", "Submitted On", type.stringType()),
								col.column("Date Of Join", "Date Of Join", type.stringType()),
								col.column("Offer Rate", "Offer Rate", type.stringType()),
								col.column("Offer Rate Yearly", "Offer Rate Yearly", type.stringType()),
								col.column("Margin Rate Yearly", "Margin Rate Yearly", type.stringType()),
								col.column("Payment Term", "Payment Term", type.stringType()),
								col.column("Generate Invoice", "Generate Invoice", type.stringType()))
						.setDataSource(applicantsJoinedSheetReportDataSource(historyDTOList)).toXls(xlsExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
 }
	
	private DRDataSource applicantsJoinedSheetReportDataSource(ResponseList<RequirementSubmissionHistoryDTO> responseList) {
		DRDataSource dataSource = new DRDataSource( "Applicant Name", "Email Id","Contact Number","Job Title", "Job Rating","Client", "Date Of Interview","Source","Submitted On","Date Of Join","Offer Rate","Offer Rate Yearly","Margin Rate Yearly", "Payment Term","Generate Invoice");
		for(RequirementSubmissionHistoryDTO history:responseList.getData()) {
			
			String jobRate = "---";
			
			String client = "---";
			
			String marginRateYearly = "---";
			
			String invoiceDate = "---";
			
			String applicantName = history.getCandidateName();
			
			String emailId = history.getEmail();
		
			String contactNumber = history.getMobilePhone();
			
			String jobTitle = history.getJobTitle();
			
			if(history.getJobRate() !=null){
			jobRate = history.getJobRate();
			}
			
			if(history.getOrgLocationName() !=null){
		    client = history.getOrgLocationName();
			}
			
			String dateOfInterview = history.getInterviewOn();
			
			String source = history.getCreatedUser();
			
			String submittedOn = history.getSubmittedOn();
			
			String joinedDate = history.getJoinDate();
			
			String offerRate = history.getOfferRate();
			
			String offerRateYearly = history.getOfferRateforYearly();
			
			if(history.getMarginRateYearly() !=null){
			
			marginRateYearly = history.getMarginRateYearly();
			}
			
			String paymentTerm = String.valueOf(history.getPayTerm());
			
			if(history.getGenerateInvoice() !=null){
			invoiceDate = history.getGenerateInvoice();
			}
			
			dataSource.add(applicantName,emailId,contactNumber,jobTitle,jobRate,client,dateOfInterview,source,submittedOn,joinedDate,offerRate,offerRateYearly,marginRateYearly,paymentTerm,invoiceDate);
		}
		return dataSource;
	}
	
	public File ticketManagementPdfExport(ResponseList<TicketDTO> responseList, OrgLocations orgLocations) throws IOException {
		File file = null;
		try {
			StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("ticket_")
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date()))
					.toString(), ".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle)
							.setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(col.column("Ticket Id", "Ticket Id", type.stringType()),
							col.column("Ticket Title", "Ticket Title", type.stringType()),
							col.column("Status", "Status", type.stringType()),
							col.column("Priority", "Priority", type.stringType()),
							col.column("Requested By", "Requested By", type.stringType()),
							col.column("AssignedTo", "AssignedTo", type.stringType()))
					.pageFooter(Templates.footerComponent).setDataSource(createTicketDataSource(responseList))
					.toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}

	public File ticketManagementExcelExport(ResponseList<TicketDTO> responseList, OrgLocations orgLocations) throws IOException {
		File file = null;
		try {
			StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("ticket_")
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date()))
					.toString(), ".xls");
			file.setReadable(true);
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file).setDetectCellType(true)
					.setIgnorePageMargins(true).setWhitePageBackground(false).setRemoveEmptySpaceBetweenColumns(true);

			report().setTemplate(Templates.reportTemplate).setColumnTitleStyle(Templates.columnTitleStyle)
					.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1").ignorePageWidth().ignorePagination()
					.columns(col.column("Ticket Id", "Ticket Id", type.stringType()),
							col.column("Ticket Title", "Ticket Title", type.stringType()),
							col.column("Status", "Status", type.stringType()),
							col.column("Priority", "Priority", type.stringType()),
							col.column("Requested By", "Requested By", type.stringType()),
							col.column("AssignedTo", "AssignedTo", type.stringType()))
					.setDataSource(createTicketDataSource(responseList)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}

	private DRDataSource createTicketDataSource(ResponseList<TicketDTO> responseList) {
		DRDataSource dataSource = new DRDataSource("Ticket Id", "Ticket Title", "Status", "Priority", "Requested By", "AssignedTo");
		try {
			for (TicketDTO history : responseList.getData()) {
				String ticketId = history.getGeneratedId();
				String ticketTitle = history.getTitle();
				String status = history.getTicketStatus();
				String priority = history.getTicketPriority();
				String requestedBy = history.getCreatedBy();
				String assignedTo = history.getAssignedTo().toString();
				assignedTo = assignedTo.replace("[", " ");
				assignedTo = assignedTo.replace("]", " ");
				dataSource.add(ticketId, ticketTitle, status, priority, requestedBy, assignedTo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}
	public File interviewListPdfExport(List<RequirementInterviewHistory> requirementInterviewList, String orgLocationDateFormat,String orglocationName) throws IOException {
		 File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("interviewPdfList_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocationDateFormat,new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orglocationName).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Applicant Name", "Applicant Name", type.stringType()),
							col.column("Job Title", "Job Title", type.stringType()),
							col.column("Client", "Client", type.stringType()),
							col.column("Date Of Interview", "Date Of Interview", type.stringType()),
							col.column("Interview Time", "Interview Time", type.stringType()),
							col.column("Interview Type", "Interview Type", type.stringType()),
							col.column("Time Zone", "Time Zone", type.stringType()),
							col.column("Details", "Details", type.stringType()),
							col.column("Interviewers", "Interviewers", type.stringType()))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createInterviewDataListataSource(requirementInterviewList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
		return file;
	}
	public File interviewListExcelExport(List<RequirementInterviewHistory> requirementInterviewList, String orgLocationDateFormat,String orglocationName) throws IOException {
		 File file = null;
			try {
			    StringBuilder fileSB = new StringBuilder();
				file = File.createTempFile(fileSB.append("interviewExcelList_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocationDateFormat,new Date())).toString(),".xls");
				file.setReadable(true);
				JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                   .setIgnorePageMargins(true)
                   .setWhitePageBackground(false)
                   .setRemoveEmptySpaceBetweenColumns(true);

				report().setTemplate(Templates.reportTemplate)
						.setColumnTitleStyle(Templates.columnTitleStyle)
						.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
						.ignorePageWidth()
						.ignorePagination()
						.columns(
								col.column("Applicant Name", "Applicant Name", type.stringType()),
								col.column("Job Title", "Job Title", type.stringType()),
								col.column("Client", "Client", type.stringType()),
								col.column("Date Of Interview", "Date Of Interview", type.stringType()),
								col.column("Interview Time", "Interview Time", type.stringType()),
								col.column("Interview Type", "Interview Type", type.stringType()),
								col.column("Time Zone", "Time Zone", type.stringType()),
								col.column("Details", "Details", type.stringType()),
								col.column("Interviewers", "Interviewers", type.stringType()))
						.setDataSource(createInterviewDataListataSource(requirementInterviewList)).toXls(xlsExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
}
	private DRDataSource createInterviewDataListataSource(List<RequirementInterviewHistory> requirementInterviewList) {
		DRDataSource dataSource = new DRDataSource( "Applicant Name", "Job Title","Client", "Date Of Interview","Interview Time", "Interview Type","Time Zone","Details","Interviewers");
		
		for(RequirementInterviewHistory interview:requirementInterviewList) {
			
			String applicantName = interview.getCandidateName();
			
			String jobTitle = interview.getSubmissionId().getRequirement().getJobTitle();
			
			String client = null;
			if(interview.getClientName() != null) {
			client = interview.getClientName();
			}else {
				client = "--";
			}
			
			String dateOfInterview = commonUtil.dateStrFromOrgDateFormatter(interview.getOrgLocationId().getDateFormat(), interview.getInterviewId().getDateOfInterview());
			
			String interviewTime = interview.getStartTimeStr();
			
			String interviewType = interview.getInterviewId().getInterviewType();
			
			String timeZone =  interview.getInterviewId().getTimezone().getTimeZoneId();
			
			List<String> interviewerList = new ArrayList<String>();
			for(RequirementInterviewer requirementInterviewer  : interview.getInterviewId().getRequirementInterviewers()) {
				if(requirementInterviewer.getInterviewerUser() !=null) {
				interviewerList.add(requirementInterviewer.getInterviewerUser().getFullName());
				}
				
			}
			
			String details = interview.getInterviewId().getLocation();
			
			String interviewer = StringUtils.join(interviewerList, ',');
			
			
			dataSource.add(applicantName,jobTitle,client,dateOfInterview,interviewTime,interviewType,timeZone,details,interviewer);
		}
		return dataSource;
	}
	
	
	public File loanAndAdvanceReportPDFExport(ResponseList<LoanAndAdvanceReportDTO> assLoanList,
			OrgLocations orgLocations) throws IOException {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("LoanAndAdvanceReport_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(Constants.LOAN_AND_ADVANCE).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Employee Id", "empId", type.stringType()),
							col.column("Employee Name", "empName", type.stringType()),
							col.column("Type", "loanAndAdvType", type.stringType()),
							col.column("Name", "loanName", type.stringType()),
							col.column("Start Date", "startDate", type.stringType()),
							col.column("End Date", "loanEndDate", type.stringType()),
							col.column("Claimed Amount", "loanTotalAmount", type.doubleType()),
				      	    col.column("Paid Amount", "loanPaidAmount", type.doubleType()),
							col.column("Peding Amount", "loanPendingAmount", type.doubleType()),
							col.column("No. of Schedules", "loanNoOfSchedules", type.integerType()),
							col.column("Deducted Amount", "loanDeductAmount", type.doubleType()),
							col.column("Status", "loanAndAdvStatus", type.stringType()))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createLoanAndAdvanceDataSource(assLoanList,orgLocations.getDateFormat())).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;	
		
	}


	private DRDataSource createLoanAndAdvanceDataSource(ResponseList<LoanAndAdvanceReportDTO> assLoanList, String dateFormat) {
		DRDataSource dataSource = new DRDataSource("empId","empName", "loanAndAdvType", "loanName", "startDate", "loanEndDate", "loanTotalAmount", "loanPaidAmount", "loanPendingAmount", "loanNoOfSchedules", "loanDeductAmount", "loanAndAdvStatus");
		for(LoanAndAdvanceReportDTO laa:assLoanList.getData()) {
			  String empId = "--";
			  String strFromDate = "";
			  String strToDate = "";
			  if(StringUtils.isNotBlank(laa.getEmployeeId())) {
				  empId = laa.getEmployeeId();
			  }
			  strFromDate = commonUtil.dateStrFromOrgDateFormatter(dateFormat, laa.getStartDate());
			  strToDate = commonUtil.dateStrFromOrgDateFormatter(dateFormat, laa.getLoanEndDate());
			dataSource.add(empId,laa.getFirstName(),laa.getLoanAndAdvType(),laa.getLoanName(), strFromDate, strToDate,laa.getLoanTotalAmount(),laa.getLoanPaidAmount(),laa.getLoanPendingAmount(),laa.getLoanNoOfSchedules(),laa.getLoanDeductAmount(),laa.getLoanAndAdvStatus());
		}
		return dataSource;
	}


	public File loanAndAdvanceReportExcelExport(ResponseList<LoanAndAdvanceReportDTO> assLoanList,
			OrgLocations orgLocations) throws IOException {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("LoanAndAdvanceReport_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
			file.setReadable(true);
			
			JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file) .setDetectCellType(true)
                    .setIgnorePageMargins(true)
                    .setWhitePageBackground(false)
                    .setRemoveEmptySpaceBetweenColumns(true);

			report().setTemplate(Templates.reportTemplate)
					.setColumnTitleStyle(Templates.columnTitleStyle)
					.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1")
					.ignorePageWidth()
					.ignorePagination()
					.columns(
							col.column("Employee Id", "empId", type.stringType()),
							col.column("Employee Name", "empName", type.stringType()),
							col.column("Type", "loanAndAdvType", type.stringType()),
							col.column("Name", "loanName", type.stringType()),
							col.column("Start Date", "startDate", type.stringType()),
							col.column("End Date", "loanEndDate", type.stringType()),
							col.column("Claimed Amount", "loanTotalAmount", type.doubleType()),
							col.column("Paid Amount", "loanPaidAmount", type.doubleType()),
							col.column("Peding Amount", "loanPendingAmount", type.doubleType()),
							col.column("No. of Schedules", "loanNoOfSchedules", type.integerType()),
							col.column("Deducted Amount", "loanDeductAmount", type.doubleType()),
							col.column("Status", "loanAndAdvStatus", type.stringType()))
					
					.setDataSource(createLoanAndAdvanceDataSourcexl(assLoanList,orgLocations.getDateFormat())).toXls(xlsExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}


	private DRDataSource createLoanAndAdvanceDataSourcexl(ResponseList<LoanAndAdvanceReportDTO> assLoanList, String dateFormat) {
		DRDataSource dataSource = new DRDataSource("empId","empName", "loanAndAdvType", "loanName", "startDate", "loanEndDate", "loanTotalAmount", "loanPaidAmount", "loanPendingAmount", "loanNoOfSchedules", "loanDeductAmount", "loanAndAdvStatus");
		for(LoanAndAdvanceReportDTO laad:assLoanList.getData()) {
			  String empId = "--";
			  String strFromDate = "";
			  String strToDate = "";
			  if(StringUtils.isNotBlank(laad.getEmployeeId())) {
				  empId = laad.getEmployeeId();
			  }
			  strFromDate = commonUtil.dateStrFromOrgDateFormatter(dateFormat, laad.getStartDate());
			  strToDate = commonUtil.dateStrFromOrgDateFormatter(dateFormat, laad.getLoanEndDate());
			dataSource.add(empId,laad.getFirstName(),laad.getLoanAndAdvType(),laad.getLoanName(), strFromDate, strToDate,laad.getLoanTotalAmount(),laad.getLoanPaidAmount(),laad.getLoanPendingAmount(),laad.getLoanNoOfSchedules(),laad.getLoanDeductAmount(),laad.getLoanAndAdvStatus());
		}
		return dataSource;
	}
	
	public File timesheetsPendingPdfExport(List<MissingTimesheetResultRepotDTO> missingTimesheetResultRepotList, OrgLocations orgLocations) {
		File file = null;
		try {
			StringBuilder tmSbPdf = new StringBuilder();
			file = File.createTempFile(tmSbPdf.append("TimesheetsPending").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date()))
					.toString(), ".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate).title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle)
					.setHorizontalAlignment(HorizontalAlignment.CENTER))
			.columns(col.column("Employee id", "Employee_id", type.stringType()),
					col.column("Employee Name", "Employee_Name", type.stringType()),
					col.column("Employee Type", "Employee_Type", type.stringType()),
					col.column("Project Name", "Project_Name", type.stringType()),
					col.column("Project Frequency", "Project_Frequency", type.stringType()),
					col.column("Approval Mechanism", "Approval_Mechanism", type.stringType()),
					col.column("Duration", "Duration", type.stringType()),
					col.column("Manager 1", "Manager_1", type.stringType()),
					col.column("Manager 2", "Manager_2", type.stringType()),
					col.column("No of Hours", "No_Of_Hours", type.stringType()),
					col.column("Submitted on", "Submitted_On", type.stringType()),
					col.column("Approved by", "Approved_By", type.stringType()),
					col.column("Approved on", "Approved_On", type.stringType()),
					col.column("No of days Pending", "No_Of_Days_Pending", type.stringType()))
					.pageFooter(Templates.footerComponent).setDataSource(createTimesheetsApprovePendingDataSource(missingTimesheetResultRepotList)).toPdf(pdfExporter);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	
	
	public File timesheetsPendingExcelExport(List<MissingTimesheetResultRepotDTO> missingTimesheetResultRepotList, OrgLocations orgLocations) {
		File file = null;
			try {
				StringBuilder fileExcelSb = new StringBuilder();
				file = File.createTempFile(fileExcelSb.append("TimesheetsPending").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date())).toString(), ".xls");
				file.setReadable(true);
				JasperXlsExporterBuilder xlsExporter = export.xlsExporter(file).setDetectCellType(true).setIgnorePageMargins(true).setWhitePageBackground(false).setRemoveEmptySpaceBetweenColumns(true);
				report().setTemplate(Templates.reportTemplate).setColumnTitleStyle(Templates.columnTitleStyle)
				.addProperty(JasperProperty.EXPORT_XLS_FREEZE_ROW, "1").ignorePageWidth().ignorePagination()
				.columns(col.column("Employee id", "Employee_id", type.stringType()),
						col.column("Employee Name", "Employee_Name", type.stringType()),
						col.column("Employee Type", "Employee_Type", type.stringType()),
						col.column("Project Name", "Project_Name", type.stringType()),
						col.column("Project Frequency", "Project_Frequency", type.stringType()),
						col.column("Approval Mechanism", "Approval_Mechanism", type.stringType()),
						col.column("Duration", "Duration", type.stringType()),
						col.column("Manager 1", "Manager_1", type.stringType()),
						col.column("Manager 2", "Manager_2", type.stringType()),
						col.column("No of Hours", "No_Of_Hours", type.stringType()),
						col.column("Submitted on", "Submitted_On", type.stringType()),
						col.column("Approved by", "Approved_By", type.stringType()),
						col.column("Approved on", "Approved_On", type.stringType()),
						col.column("No of days Pending", "No_Of_Days_Pending", type.stringType()))
						.setDataSource(createTimesheetsApprovePendingDataSource(missingTimesheetResultRepotList)).toXls(xlsExporter);
			}catch(Exception e) {
				e.printStackTrace();
			}
		return file;
	}
	
	private DRDataSource createTimesheetsApprovePendingDataSource(List<MissingTimesheetResultRepotDTO> missingTimesheetResultRepotList) {
		DRDataSource dataSource = new DRDataSource("Employee_id","Employee_Name","Employee_Type","Project_Name", "Project_Frequency","Approval_Mechanism","Duration","Manager_1","Manager_2","No_Of_Hours","Submitted_On","Approved_By","Approved_On","No_Of_Days_Pending");
		try {
			for (MissingTimesheetResultRepotDTO mtrd : missingTimesheetResultRepotList) {
				String Manager_1 = "";
				String Manager_2= "";
				String Employee_id       = mtrd.getEmpId();		
				String Employee_Name 	 = mtrd.getEmployeName();
				String Employee_Type 	 = mtrd.getEmployeType();
				String Project_Name  	 = mtrd.getProjectName();
				String Project_Frequency = mtrd.getProjectFrequency();
				String Approval_Mechanism = mtrd.getApprovalMechanism();
				String Duration          = mtrd.getFrequency();
				if(mtrd.getManager2() != null && !"".equalsIgnoreCase(mtrd.getManager2())) {
					 Manager_1 		 = mtrd.getManager1();
					 Manager_2 		 = mtrd.getManager2();
					}
					else{
						Manager_1 		 = mtrd.getManager1();
						Manager_2 = Manager_1;
						Manager_1 ="--";
					}
				String No_Of_Hours 		 = mtrd.getNoOfHours();
				String Submitted_On 	 = mtrd.getSubmittedOn();
				String Approved_By 		 = mtrd.getApproved_By();
				String Approved_On		 = mtrd.getApprovedDate();
				String No_Of_Days_Pending= mtrd.getDueDays().toString();
				dataSource.add(Employee_id,Employee_Name,Employee_Type,Project_Name,Project_Frequency,Approval_Mechanism,Duration,Manager_1,Manager_2,No_Of_Hours,Submitted_On,Approved_By,Approved_On,No_Of_Days_Pending);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}
	
	
}