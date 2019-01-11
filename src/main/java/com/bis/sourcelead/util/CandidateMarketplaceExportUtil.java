package com.bis.sourcelead.util;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dto.MarketplacePurchaseSalesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.ImmigrationReportViewDTO;
import com.bis.sourcelead.util.dynamicReport.Templates;

import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * 
 * Util class for exported Pdf and Excel files in Candidates.
 * 
 * @author Vishnu
 * @date 18th December 2018
 * 
 */

@Component
public class CandidateMarketplaceExportUtil {
	
	@Autowired
	CommonUtil commonUtil;


	public File  exportSalesOrderToPdf(ResponseList<MarketplacePurchaseSalesDTO> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("SalesOrder_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);

			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocation.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("orderNo", "orderNo", type.stringType()),
							col.column("orderDate", "orderDate", type.stringType()),
							col.column("companyName", "companyName", type.stringType()),
							col.column("noOfResumes", "noOfResumes", type.stringType()),
							col.column("totalResumePrice", "totalResumePrice", type.stringType()),
							col.column("paymentStatus", "paymentStatus", type.stringType()))
					       .pageFooter(Templates.footerComponent)
					.setDataSource(createSalesOrderDataSource(res)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}
	
	public File exportSalesOrderToExcel(ResponseList<MarketplacePurchaseSalesDTO> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("SalesOrder_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".xls");
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
							col.column("orderNo", "orderNo", type.stringType()),
							col.column("orderDate", "orderDate", type.stringType()),
							col.column("companyName", "companyName", type.stringType()),
							col.column("noOfResumes", "noOfResumes", type.stringType()),
							col.column("totalResumePrice", "totalResumePrice", type.stringType()),
							col.column("paymentStatus", "paymentStatus", type.stringType()))
					.setDataSource(createSalesOrderDataSource(res)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	return file;
	}
	
	private DRDataSource createSalesOrderDataSource(ResponseList<MarketplacePurchaseSalesDTO> res) {
		DRDataSource dataSource = new DRDataSource("orderNo", "orderDate", "companyName", "noOfResumes", "totalResumePrice", "paymentStatus");
			String  orderNo= "---";
			String orderDate = "---";
			String companyName = "---";
			String noOfResumes = "---";
			String totalResumePrice= "---";
			String  paymentStatus= "---";
		for(MarketplacePurchaseSalesDTO salesDto :res.getData()) {
			if(salesDto != null && salesDto.getOrderNo() != null) {
				orderNo = salesDto.getOrderNo();
			}
			if(salesDto != null && salesDto.getOrderDate() != null){
				orderDate = salesDto.getOrderDate();
			}
			if(salesDto != null && salesDto.getCompanyName() != null){
				companyName = salesDto.getCompanyName();
			}
			if(salesDto != null && salesDto.getTotalresume() != null){
				noOfResumes = salesDto.getTotalresume();
			}
			if(salesDto != null && salesDto.getTotalPrice() != null){
				totalResumePrice = salesDto.getTotalPrice().toString();
			}
			if(salesDto != null && salesDto.getPaymentStatus() != null){
				paymentStatus = salesDto.getPaymentStatus();
			}
			dataSource.add(orderNo, orderDate, companyName, noOfResumes, totalResumePrice, paymentStatus);
		}
		return dataSource;
	}
	
}
