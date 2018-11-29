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
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.util.CommonUtil;

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
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
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
			  if(pat.getProjectTaskId().getDueDate() != null) {
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
			  
			  List<ProjectAssignTask> assignUser = projectAssignTasksService.getProjectAssignTaskListByProjectTaskId(pt.getId());
			  List<String> assignedTaskUsersList =  new ArrayList<>();
				for(ProjectAssignTask pat:assignUser) {
					if(pat.getProjectMemberId() != null) {
						if(pat.getProjectMemberId().getProjectMemberCandidteId() != null) {
							assignedTaskUsersList.add(pat.getProjectMemberId().getProjectMemberCandidteId().getFullName());
						}
					}
				 }
				 String citiesCommaSeparated = String.join(",", assignedTaskUsersList);
			dataSource.add(pt.getTaskName(),priority,pt.getTaskType().getName() ,citiesCommaSeparated,pt.getProjectId().getProjectName(),dueDate,completion);
		}
		return dataSource;
	}
	
}