package com.bis.sourcelead.util.dynamicReport;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.util.CommonUtil;

import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
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
  
}