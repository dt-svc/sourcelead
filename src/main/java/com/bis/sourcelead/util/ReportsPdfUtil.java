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

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.payroll.dto.AssociateWithHoldingDTO;
import com.bis.sourcelead.util.dynamicReport.Templates;
import com.itextpdf.text.Font;

import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;

@Component
public class ReportsPdfUtil {
	
	Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
	Font detailFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
	Font greyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	
	@Autowired
	CommonUtil commonUtil;

	public File jobsPerformanceExcelExport(ResponseList<Requirement> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("jobs_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".xls");
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
							col.column("Job Id", "id", type.stringType()),
							col.column("Job title", "jobTitle", type.stringType()),
							col.column("Client", "client", type.stringType()),
							col.column("Priority", "priority", type.stringType()),
							col.column("Location", "location", type.stringType()),
							col.column("Applicants", "applicants", type.stringType()),
							col.column("Shortlisted", "shortlisted", type.stringType()),
							col.column("Interview", "interview", type.stringType()),
							col.column("Offered", "offered", type.stringType()),
							col.column("Submit To Client", "submitToClient", type.stringType()),
							col.column("Backout", "backout", type.stringType()),
							col.column("Joined", "joined", type.stringType()),
							col.column("Rejected", "rejected", type.stringType()))
					.setDataSource(createAssociateWithholdingPdfDataSource(res)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	return file;
	}
	
	public File  jobsPerformancePdfExport(ResponseList<Requirement> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("jobs_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocation.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Job Id", "id", type.stringType()),
							col.column("Job title", "jobTitle", type.stringType()),
							col.column("Client", "client", type.stringType()),
							col.column("Priority", "priority", type.stringType()),
							col.column("Location", "location", type.stringType()),
							col.column("Applicants", "applicants", type.stringType()),
							col.column("Shortlisted", "shortlisted", type.stringType()),
							col.column("Interview", "interview", type.stringType()),
							col.column("Offered", "offered", type.stringType()),
							col.column("Submit To Client", "submitToClient", type.stringType()),
							col.column("Backout", "backout", type.stringType()),
							col.column("Joined", "joined", type.stringType()),
							col.column("Rejected", "rejected", type.stringType()))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createAssociateWithholdingPdfDataSource(res)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}
	
	private DRDataSource createAssociateWithholdingPdfDataSource(ResponseList<Requirement> res) {
		DRDataSource dataSource = new DRDataSource("id","jobTitle", "client", "priority","location","applicants",
				"shortlisted","interview","offered","submitToClient","backout","joined","rejected");
		for(Requirement job :res.getData()) {
			String  id= "--";
			String jobTitle = "--";
			String client = "--";
			String priority = "--";
			String location= "--";
			
			if(job != null && job.getJobId() != null) {
				id = job.getJobId();
			}
			if(job.getJobTitle() != null) {
				jobTitle = job.getJobTitle();
			}
			if(job.getClientName() != null) {
				client = job.getClientName();
			}
			if(job.getPriority() != null) {
				priority = job.getPriority();
			}
			location = job.getPriority();
			
			String applicants = job.getAssignedMembersCount();
			
			String shortlisted = job.getShortlistCount();
			
			String interview = job.getInterviewCount();
			
			String offered = job.getOffersCount();
			
			String submitToClient = job.getSubmitToClientCount();
			
			String backout = job.getBackoutCount();
			
			String joined = job.getJoinedCount();
			
			String rejected = job.getRejectedCount();

			dataSource.add(id,jobTitle,client,priority,location,applicants,shortlisted,interview,offered,submitToClient,backout,joined,rejected);
		}
		return dataSource;
	}
}

