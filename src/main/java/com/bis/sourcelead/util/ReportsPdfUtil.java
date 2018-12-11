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

import com.bis.sourcelead.dto.LcaReportViewDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.payroll.dto.ImmigrationReportViewDTO;
import com.bis.sourcelead.util.dynamicReport.Templates;
import com.itextpdf.text.Font;

import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
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
			
			TextColumnBuilder<String> locationColumn = col.column("Location", "location", type.stringType())
					.setFixedWidth(100)
					.setStretchWithOverflow(true)
					.addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocation.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Job Id", "id", type.stringType()),
							col.column("Job title", "jobTitle", type.stringType()),
							col.column("Client", "client", type.stringType()),
							col.column("Priority", "priority", type.stringType()),
							locationColumn,
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
			location = job.getLocation();
			
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
	
	/**
	 * Immigration Report Export Excel
	 * 
	 */
	public File immigrationPerformanceExcelExport(ResponseList<ImmigrationReportViewDTO> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("petition_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".xls");
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
							col.column("ID", "id", type.stringType()),
							col.column("Name", "firstName", type.stringType()),
							col.column("Email", "email", type.stringType()),
							col.column("Phone", "phone", type.stringType()),
							col.column("Type", "petitionType", type.stringType()),
							col.column("Country", "country", type.stringType()),
							col.column("On Date", "openDateStr", type.stringType()),
							col.column("Attorney(Vendor Name)", "orgName", type.stringType()),
							col.column("LCA Start Date", "lcaStartDateStr", type.stringType()),
							col.column("LCA End Date", "lcaEndDateStr", type.stringType()),
							col.column("Status", "currUscisStatus", type.stringType()))
					.setDataSource(createImmigrationPdfDataSource(res)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	return file;
	}
	
	/**
	 * Immigration Report Export PDF
	 * @param res
	 * @param orgLocation
	 * @return
	 * @throws Exception
	 */
	public File  immigrationPerformancePdfExport(ResponseList<ImmigrationReportViewDTO> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("petition_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			
			TextColumnBuilder<String> locationColumn = col.column("Location", "location", type.stringType())
					.setFixedWidth(100)
					.setStretchWithOverflow(true)
					.addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocation.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("ID", "id", type.stringType()),
							col.column("Name", "firstName", type.stringType()),
							col.column("Email", "email", type.stringType()),
							col.column("Phone", "phone", type.stringType()),
							col.column("Type", "petitionType", type.stringType()),
							col.column("Country", "country", type.stringType()),
							col.column("On Date", "openDateStr", type.stringType()),
							col.column("Attorney(Vendor Name)", "orgName", type.stringType()),
							col.column("LCA Start Date", "lcaStartDateStr", type.stringType()),
							col.column("LCA End Date", "lcaEndDateStr", type.stringType()),
							col.column("Status", "currUscisStatus", type.stringType()))
					       .pageFooter(Templates.footerComponent)
					.setDataSource(createImmigrationPdfDataSource(res)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}
	
	private DRDataSource createImmigrationPdfDataSource(ResponseList<ImmigrationReportViewDTO> res) {
		DRDataSource dataSource = new DRDataSource("id","firstName", "email", "phone","petitionType","country",
				"openDateStr","orgName","lcaStartDateStr","lcaEndDateStr","currUscisStatus");
		for(ImmigrationReportViewDTO immig :res.getData()) {
			String  id= "--";
			String firstName = "--";
			String email = "--";
			String phone = "--";
			String petitionType= "--";
			
			String  country= "--";
			String openDateStr = null;
			String orgName = "--";
			String lcaStartDateStr = null;
			String lcaEndDateStr= null;
			String currUscisStatus= "--";
			
			if(immig != null && immig.getGeneratedId() != null) {
				id = immig.getGeneratedId();
			}
			if(immig.getName() != null) {
				firstName = immig.getName();
			}
			if(immig.getEmail() != null) {
				email = immig.getEmail();
			}
			if(immig.getPhoneNumber() != null) {
				phone = immig.getPhoneNumber();
			}
			
			if(immig.getPetitionTypeName() != null) {
				petitionType = immig.getPetitionTypeName();
		     }
			if(immig.getOrgName() != null) {
				orgName = immig.getOrgName();
			}
			
			if(immig.getCountry() != null) {
				country = immig.getCountry();
			}
	
            if(immig.getOpenDateStr() != null) {
            	openDateStr = immig.getOpenDateStr();
            }
            
            if(immig.getLcaStartDateStr() != null) {
            	lcaStartDateStr = immig.getLcaStartDateStr();
            }
            
            if(immig.getLcaEndDateStr() != null) {
            	lcaEndDateStr = immig.getLcaEndDateStr();
            }
            
            if(immig.getCurrUscisStatus() != null) {
            	currUscisStatus = immig.getCurrUscisStatus();
            }
            
			dataSource.add(id,firstName,email,phone,petitionType,country,openDateStr,orgName,lcaStartDateStr,lcaEndDateStr,currUscisStatus);
		}
		return dataSource;
	}
	
	/**
	 * LCA Report Export Excel
	 * 
	 */
	public File lcaPerformanceExcelExport(ResponseList<LcaReportViewDTO> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("petition_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".xls");
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
							col.column("LCA Number", "lcaNo", type.stringType()),
							col.column("LCA Title", "lcaTitle", type.stringType()),
							col.column("No. of positions", "noOfPositions", type.stringType()),
							col.column("Location", "location", type.stringType()),
							col.column("SOC Code", "socCode", type.stringType()),
							col.column("SOC Occupation Title", "socOccupationTitle", type.stringType()),
							col.column("Job Title", "jobTitle", type.stringType()),
							col.column("Wage Level", "wagesLevel", type.stringType()),
							col.column("Prevailing Wage Currency", "privilingWageCurrency", type.stringType()),
							col.column("Prevailing Wage Rate", "prevailingWage", type.stringType()),
							col.column("Prevailing Wage rate type", "privilingWageRateType", type.stringType()),
							col.column("LCA Start Date", "startDateStr", type.stringType()),
							col.column("LCA End Date", "endDateStr", type.stringType()),
							col.column("LCA Status", "lcaStatus", type.stringType()),
							col.column("Status", "status", type.stringType()),
					        col.column("Expiration", "expiredOn", type.stringType()))
					        
					.setDataSource(createLcaPdfDataSource(res)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	return file;
	}
	
	/**
	 * 
	 * @param res
	 * @param orgLocation
	 * @return
	 * @throws Exception
	 */
	public File  lcaPerformancePdfExport(ResponseList<LcaReportViewDTO> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("petition_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			
			TextColumnBuilder<String> locationColumn = col.column("Location", "location", type.stringType())
					.setFixedWidth(100)
					.setStretchWithOverflow(true)
					.addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocation.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("LCA Number", "lcaNo", type.stringType()),
							col.column("LCA Title", "lcaTitle", type.stringType()),
							col.column("No. of positions", "noOfPositions", type.stringType()),
							col.column("Location", "location", type.stringType()),
							col.column("SOC Code", "socCode", type.stringType()),
							col.column("SOC Occupation Title", "socOccupationTitle", type.stringType()),
							col.column("Job Title", "jobTitle", type.stringType()),
							col.column("Wage Level", "wagesLevel", type.stringType()),
							col.column("Prevailing Wage Currency", "privilingWageCurrency", type.stringType()),
							col.column("Prevailing Wage Rate", "prevailingWage", type.stringType()),
							col.column("Prevailing Wage rate type", "privilingWageRateType", type.stringType()),
							col.column("LCA Start Date", "startDateStr", type.stringType()),
							col.column("LCA End Date", "endDateStr", type.stringType()),
							col.column("LCA Status", "lcaStatus", type.stringType()),
							col.column("Status", "status", type.stringType()),
					        col.column("Expiration", "expiredOn", type.stringType()))
					       .pageFooter(Templates.footerComponent)
					.setDataSource(createLcaPdfDataSource(res)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}
	
	private DRDataSource createLcaPdfDataSource(ResponseList<LcaReportViewDTO> res) {
		DRDataSource dataSource = new DRDataSource("lcaNo","lcaTitle", "noOfPositions", "location","socCode","socOccupationTitle",
				"jobTitle","wagesLevel","privilingWageCurrency","prevailingWage","privilingWageRateType","startDateStr","endDateStr","lcaStatus","status","expiredOn");
		for(LcaReportViewDTO lca :res.getData()) {
			
			String  lcaNo= "--";
			String lcaTitle = "--";
			String noOfPositions = "--";
			String location = "--";
			String socCode= "--";
			String privilingWageCurrency ="--";
			String prevailingWage ="--";
			String privilingWageRateType ="--";
			String  socOccupationTitle= "--";
			String jobTitle = null;
			String wagesLevel = "--";
			String startDateStr = null;
			String endDateStr= null;
			String lcaStatus= "--";
			String status= "--";
			String expiredOn= "--";
			
			if(lca != null && lca.getLcaNo() != null) {
				lcaNo = lca.getLcaNo();
			}
			if(lca.getLcaTitle() != null) {
				lcaTitle = lca.getLcaTitle();
			}
			if(lca.getNoOfPositions() != null) {
				noOfPositions = lca.getNoOfPositions().toString();
			}
			if(lca.getLocation() != null) {
				location = lca.getLocation();
			}
			if(lca.getSocCode() != null) {
				socCode = lca.getSocCode();
		     }
			
			if(lca.getPrivilingWageCurrency() != null) {
				privilingWageCurrency = lca.getPrivilingWageCurrency();
			}
			
			if(lca.getPrevailingWage() != null) {
				prevailingWage = lca.getPrevailingWage();
			}
			
			if(lca.getPrivilingWageRateType() != null) {
				privilingWageRateType = lca.getPrivilingWageRateType();
			}
	
            if(lca.getSocOccupationTitle() != null) {
            	socOccupationTitle = lca.getSocOccupationTitle();
            }
            
            if(lca.getJobTitle() != null) {
            	jobTitle = lca.getJobTitle();
            }
            
            if(lca.getWagesLevel() != null) {
            	wagesLevel = lca.getWagesLevel();
            }
            
            if(lca.getStartDateStr() != null) {
            	startDateStr = lca.getStartDateStr();
            }
            
            if(lca.getEndDateStr() != null) {
            	endDateStr = lca.getEndDateStr();
            }
            
            if(lca.getLcaStatus() != null) {
            	lcaStatus = lca.getLcaStatus();
            }
            
            if(lca.getStatus() != null) {
            	status = lca.getStatus();
            }
            
            if(lca.getExpired() != null) {
            	expiredOn = lca.getExpired();
            }
            
			dataSource.add(lcaNo,lcaTitle,noOfPositions,location,socCode,socOccupationTitle,jobTitle,wagesLevel,privilingWageCurrency,prevailingWage,privilingWageRateType,startDateStr,endDateStr,lcaStatus,status,expiredOn);
		}
		return dataSource;
	}
	
	/**
	 * Immigration Attorney Report PDF And Excel
	 * 
	 */
	
	
	public File immigrationAttorneyPerformanceExcelExport(ResponseList<ImmigrationReportViewDTO> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("petition_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".xls");
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
							col.column("ID", "id", type.stringType()),
							col.column("Name", "firstName", type.stringType()),
							col.column("Email", "email", type.stringType()),
							col.column("Phone", "phone", type.stringType()),
							col.column("Type", "petitionType", type.stringType()),
							col.column("Country", "country", type.stringType()),
							col.column("On Date", "openDateStr", type.stringType()),
							col.column("Attorney(Vendor Name)", "orgName", type.stringType()),
							col.column("LCA Start Date", "lcaStartDateStr", type.stringType()),
							col.column("LCA End Date", "lcaEndDateStr", type.stringType()),
							col.column("Status", "currUscisStatus", type.stringType()))
					.setDataSource(createImmigrationAttorneyPdfDataSource(res)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	return file;
	}
	
	/**
	 * Immigration Report Export PDF
	 * @param res
	 * @param orgLocation
	 * @return
	 * @throws Exception
	 */
	public File  immigrationAttorneyPerformancePdfExport(ResponseList<ImmigrationReportViewDTO> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("petition_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			
			TextColumnBuilder<String> locationColumn = col.column("Location", "location", type.stringType())
					.setFixedWidth(100)
					.setStretchWithOverflow(true)
					.addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true");
			
			
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(orgLocation.getOrgLocationName()).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("ID", "id", type.stringType()),
							col.column("Name", "firstName", type.stringType()),
							col.column("Email", "email", type.stringType()),
							col.column("Phone", "phone", type.stringType()),
							col.column("Type", "petitionType", type.stringType()),
							col.column("Country", "country", type.stringType()),
							col.column("On Date", "openDateStr", type.stringType()),
							col.column("Attorney(Vendor Name)", "orgName", type.stringType()),
							col.column("LCA Start Date", "lcaStartDateStr", type.stringType()),
							col.column("LCA End Date", "lcaEndDateStr", type.stringType()),
							col.column("Status", "currUscisStatus", type.stringType()))
					       .pageFooter(Templates.footerComponent)
					.setDataSource(createImmigrationAttorneyPdfDataSource(res)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}
	
	private DRDataSource createImmigrationAttorneyPdfDataSource(ResponseList<ImmigrationReportViewDTO> res) {
		DRDataSource dataSource = new DRDataSource("id","firstName", "email", "phone","petitionType","country",
				"openDateStr","orgName","lcaStartDateStr","lcaEndDateStr","currUscisStatus");
		for(ImmigrationReportViewDTO immig :res.getData()) {
			String  id= "--";
			String firstName = "--";
			String email = "--";
			String phone = "--";
			String petitionType= "--";
			
			String  country= "--";
			String openDateStr = null;
			String orgName = "--";
			String lcaStartDateStr = null;
			String lcaEndDateStr= null;
			String currUscisStatus= "--";
			
			if(immig != null && immig.getGeneratedId() != null) {
				id = immig.getGeneratedId();
			}
			if(immig.getName() != null) {
				firstName = immig.getName();
			}
			if(immig.getEmail() != null) {
				email = immig.getEmail();
			}
			if(immig.getPhoneNumber() != null) {
				phone = immig.getPhoneNumber();
			}
			
			if(immig.getPetitionTypeName() != null) {
				petitionType = immig.getPetitionTypeName();
		     }
			if(immig.getOrgName() != null) {
				orgName = immig.getOrgName();
			}
			if(immig.getCountry() != null) {
				country = immig.getCountry();
			}
			
			if(immig.getCountry() != null) {
				country = immig.getCountry();
			}
	
            if(immig.getOpenDateStr() != null) {
            	openDateStr = immig.getOpenDateStr();
            }
            
            if(immig.getLcaStartDate() != null) {
            	lcaStartDateStr = immig.getLcaStartDate();
            }
            
            if(immig.getLcaEndDate() != null) {
            	lcaEndDateStr = immig.getLcaEndDate();
            }
            
            if(immig.getCurrUscisStatus() != null) {
            	currUscisStatus = immig.getCurrUscisStatus();
            }
            
			dataSource.add(id,firstName,email,phone,petitionType,country,openDateStr,orgName,lcaStartDateStr,lcaEndDateStr,currUscisStatus);
		}
		return dataSource;
	}
	
}

