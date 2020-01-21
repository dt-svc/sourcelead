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

import com.bis.sourcelead.bgc.dto.BgcCandidateDetailsDTO;
import com.bis.sourcelead.dto.MarketplacePurchaseSalesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.dynamicReport.Templates;

import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;

/**
 * This class contains methods to export background check list into pdf and excel.
 * 
 * @author: Vishnu
 * @date: 17-Jan-2020
 */

@Component
public class BackgroundCheckExportUtil {
	
	@Autowired
	CommonUtil commonUtil;

	public File exportBgcToPdf(ResponseList<BgcCandidateDetailsDTO> bgcCandidateDetailsDTOList,
			OrgLocations orgLocations) {
		File file = null;
		try{
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("BackgroundCheck_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);

			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocations.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Status", "currentBgcStatus", type.stringType()),
							col.column("Id", "generatedId", type.stringType()),
							col.column("Name", "fullName", type.stringType()),
							col.column("Updated Percentage", "updatedPercentage", type.stringType()),
							col.column("Personal Info", "personalInfoStatus", type.stringType()),
							col.column("Education", "educationCheckStaus", type.stringType()),
							col.column("Prior Employment", "priorEmployersCheckStatus", type.stringType()),
							col.column("Health", "healthCheckStatus", type.stringType()),
							col.column("Criminal", "crimeCheckStatus", type.stringType()),
							col.column("Others Info", "othersInfoStatus", type.stringType()),
							col.column("Comments", "comments", type.stringType()),
							col.column("Client/Vendor", "clinetOrVendorName", type.stringType()),
							col.column("Updated Date", "updatedDate", type.stringType())).pageFooter(Templates.footerComponent)
					.setDataSource(createSalesOrderDataSource(bgcCandidateDetailsDTOList)).toPdf(pdfExporter);
		}catch(Exception e){
			e.printStackTrace();
		}
		return file;
	}

	public File exportBgcToExcel(ResponseList<BgcCandidateDetailsDTO> bgcCandidateDetailsDTOList,
			OrgLocations orgLocations) {
		File file = null;
		try{
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("BackgroundCheck_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),".xls");
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
					.columns(col.column("Status", "currentBgcStatus", type.stringType()),
							col.column("Id", "generatedId", type.stringType()),
							col.column("Name", "fullName", type.stringType()),
							col.column("Updated Percentage", "updatedPercentage", type.stringType()),
							col.column("Personal Info", "personalInfoStatus", type.stringType()),
							col.column("Education", "educationCheckStaus", type.stringType()),
							col.column("Prior Employment", "priorEmployersCheckStatus", type.stringType()),
							col.column("Health", "healthCheckStatus", type.stringType()),
							col.column("Criminal", "crimeCheckStatus", type.stringType()),
							col.column("Others Info", "othersInfoStatus", type.stringType()),
							col.column("Comments", "comments", type.stringType()),
							col.column("Client/Vendor", "clinetOrVendorName", type.stringType()),
							col.column("Updated Date", "updatedDate", type.stringType())).setDataSource(createSalesOrderDataSource(bgcCandidateDetailsDTOList)).toXls(xlsExporter);
		}catch(Exception e){
			e.printStackTrace();
		}
		return file;
	}
	
	private DRDataSource createSalesOrderDataSource(ResponseList<BgcCandidateDetailsDTO> bgcCandidateDetailsDTOList) {
		DRDataSource dataSource = new DRDataSource("currentBgcStatus", "generatedId", "fullName", "updatedPercentage", "personalInfoStatus", "educationCheckStaus", 
				"priorEmployersCheckStatus", "healthCheckStatus", "crimeCheckStatus", "othersInfoStatus", "comments","clinetOrVendorName", "updatedDate");
			String currentBgcStatus= "---";
			String generatedId = "---";
			String fullName = "---";
			String updatedPercentage = "---";
			String personalInfoStatus= "---";
			String educationCheckStaus= "---";
			String priorEmployersCheckStatus = "---";
			String healthCheckStatus = "---";
			String crimeCheckStatus = "---";
			String othersInfoStatus = "---";
			String comments = "---";
			String clinetOrVendorName = "---";
			String updatedDate = "---";
		for(BgcCandidateDetailsDTO bgcDto :bgcCandidateDetailsDTOList.getData()) {
			if(bgcDto != null && bgcDto.getCurrentBgcStatus() != null) {
				currentBgcStatus = bgcDto.getCurrentBgcStatus();
			}
			if(bgcDto != null && bgcDto.getGeneratedId() != null){
				generatedId = bgcDto.getGeneratedId();
			}
			if(bgcDto != null && bgcDto.getFullName() != null){
				fullName = bgcDto.getFullName();
			}
			if(bgcDto != null && bgcDto.getUpdatedPercentage() != null){
				updatedPercentage = bgcDto.getUpdatedPercentage();
			}
			if(bgcDto != null && bgcDto.getPersonalInfoStatus() != null){
				personalInfoStatus = bgcDto.getPersonalInfoStatus();
			}
			if(bgcDto != null && bgcDto.getEducationCheckStaus() != null){
				educationCheckStaus = bgcDto.getEducationCheckStaus();
			}
			if(bgcDto != null && bgcDto.getPriorEmployersCheckStatus() != null){
				priorEmployersCheckStatus = bgcDto.getPriorEmployersCheckStatus();
			}
			if(bgcDto != null && bgcDto.getHealthCheckStatus() != null){
				healthCheckStatus = bgcDto.getHealthCheckStatus();
			}
			if(bgcDto != null && bgcDto.getCrimeCheckStatus() != null){
				crimeCheckStatus = bgcDto.getCrimeCheckStatus();
			}
			if(bgcDto != null && bgcDto.getOthersInfoStatus() != null){
				othersInfoStatus = bgcDto.getOthersInfoStatus();
			}
			if(bgcDto != null && bgcDto.getComments() != null){
				comments = bgcDto.getComments();
			}
			if(bgcDto != null && bgcDto.getClinetOrVendorName() != null){
				clinetOrVendorName = bgcDto.getClinetOrVendorName();
			}
			if(bgcDto != null && bgcDto.getUpdatedDate() != null){
				updatedDate = bgcDto.getUpdatedDate();
			}
			dataSource.add(currentBgcStatus, generatedId, fullName, updatedPercentage, personalInfoStatus, educationCheckStaus, priorEmployersCheckStatus, 
					healthCheckStatus, crimeCheckStatus, othersInfoStatus,comments, clinetOrVendorName, updatedDate);
		}
		return dataSource;
	}

}
