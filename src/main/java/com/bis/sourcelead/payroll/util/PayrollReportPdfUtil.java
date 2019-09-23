package com.bis.sourcelead.payroll.util;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.AssociateReportDTO;
import com.bis.sourcelead.payroll.dto.AssociateWithHoldingDTO;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.DeductionSummaryDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.PdfHeaderDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayrollTax;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.dynamicReport.Templates;

import java.math.BigDecimal;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import net.sf.dynamicreports.jasper.builder.export.JasperPdfExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;

@Component
public class PayrollReportPdfUtil {
	
	Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
	Font detailFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
	Font greyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	
	@Autowired
	CommonUtil commonUtil;

	public File getCheckRegisterReportPdf(List<CheckRegisterReportDTO> checkRegData, OrgLocations orgLocation) throws Exception
	{
		
		StringBuilder sbReq = new StringBuilder();
		sbReq.append(orgLocation.getOrgLocationName()).append(Constants.PAYROLL).append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date()));

		StringBuilder fileSB = new StringBuilder();
		File file = File.createTempFile(fileSB.append(orgLocation.getOrgLocationName()).append(Constants.PAYROLL).append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),
				".pdf");

		file.setReadable(true);
		
		try {
			if(checkRegData != null && checkRegData.size() >0) {
				Document document = new Document(PageSize.A4.rotate());
		    	PdfWriter.getInstance(document, new FileOutputStream(file));
		    	document.open();
		    	
		    	CheckRegisterReportDTO registerDTO = checkRegData.get(0);
		    	
		    	PdfHeaderDTO headerDTO = new PdfHeaderDTO();
		    	
		    	headerDTO.setCheckDate(registerDTO.getPayCheckDate());
		    	headerDTO.setGroupName(registerDTO.getPayGroupName());
		    	headerDTO.setOrgName(orgLocation.getOrgLocationName());
		    	headerDTO.setLocationAddress(orgLocation.getLocation());
		    	headerDTO.setPayPeriod(registerDTO.getPayFrequency());
		    	headerDTO.setPayrollType(registerDTO.getType());
		    	headerDTO.setPeriodBeginDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), registerDTO.getFreqStartDate()));
		    	headerDTO.setPeriodEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), registerDTO.getFreqEndDate()));
		    	headerDTO.setReportType("CHECK REGISTER");
		    	headerDTO.setRunDate(registerDTO.getRunDate());
//		    	headerDTO.setLiveChecks(checkRegData.size());
		    	if(StringUtils.isNotBlank(registerDTO.getRunNo())) {
		    		headerDTO.setRunNumber(Integer.parseInt(registerDTO.getRunNo()));
		    	}else {
		    		headerDTO.setRunNumber(0);
		    	}
		    	
		    	
		    	createHeaderTable(document,headerDTO,orgLocation);
		    	
		    	PdfPTable empTable = new PdfPTable(7);
		    	empTable.setTotalWidth(document.getPageSize().getWidth() - 80);
		    	empTable.setLockedWidth(true);
		    	empTable.setSpacingBefore(20);  
		    	
		    	PdfPCell cell = new PdfPCell(new Phrase("Department", boldFont));
		    	cell.setFixedHeight(20);
		    	empTable.addCell(cell);
		    	empTable.addCell(new Phrase("Emp Id #", boldFont));
		    	empTable.addCell(new Phrase("Employee/Payee", boldFont));
		    	empTable.addCell(new Phrase("Date", boldFont));
		    	empTable.addCell("");
		    	empTable.addCell(new Phrase("Check Number", boldFont));
		    	empTable.addCell(new Phrase("Amount", boldFont));
		    	document.add(empTable);
		    	
		    	
		    	PdfPTable empDataTable = getPdfTable(7,document);
		    	empDataTable.setSpacingBefore(2);  
		    	BigDecimal checksTotal = BigDecimal.ZERO;
		    	for(CheckRegisterReportDTO checkRegisterData : checkRegData) {
		    		empDataTable.addCell(getPdfCell(checkRegisterData.getDepartmentId(),normalFont,5));
		    		if(StringUtils.isNotBlank(checkRegisterData.getEmployeeId())) {
		    			empDataTable.addCell(getPdfCell(checkRegisterData.getEmployeeId(),normalFont,0));
		    		}else {
		    			empDataTable.addCell(getPdfCell("--",normalFont,0));	
		    		}
		    		StringBuilder sb = new StringBuilder();
		    		if(StringUtils.isNotBlank(checkRegisterData.getLastName())) {
		    			sb.append(checkRegisterData.getFirstName()).append(" ").append(checkRegisterData.getLastName());
		    		}else {
		    			sb.append(checkRegisterData.getFirstName());
		    		}
			    	empDataTable.addCell(getPdfCell(sb.toString(),normalFont,0));
			    	empDataTable.addCell(getPdfCell(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), checkRegisterData.getPayCheckDate()),normalFont,0));
			    	empDataTable.addCell(getPdfCell("",normalFont,0));
			    	empDataTable.addCell(getPdfCell("XXXXX",normalFont,0));
			    	empDataTable.addCell(getPdfCell("$ "+checkRegisterData.getNetPay(),normalFont,0));
			    	checksTotal = checksTotal.add(checkRegisterData.getNetPay());
		    	}
		    	empDataTable.setSpacingAfter(8);
		    	document.add(empDataTable);
		    	
		    	PdfPTable lineTable = getPdfTable(1,document);
		    	
		    	
		    	PdfPTable lineChildTable = new PdfPTable(6);
		    	lineChildTable.addCell(getPdfCell("",boldFont,0));
		    	lineChildTable.addCell(getPdfCell("",boldFont,0));
		    	lineChildTable.addCell(getPdfCell("",boldFont,0));
		    	lineChildTable.addCell(getPdfCell("",boldFont,0));
		    	lineChildTable.addCell(getPdfCell("Total Number of Checks: "+checkRegData.size(),boldFont,0));
		    	lineChildTable.addCell(getPdfCell("$ "+checksTotal.toString(),boldFont,0));
		    	
		    	PdfPCell lineTableCell = new PdfPCell(lineChildTable);
		    	lineTableCell.setFixedHeight(30);
		    	lineTableCell.setBorderWidth(1f);
		    	lineTableCell.setBackgroundColor(new BaseColor(211,211,211));
		    	lineTableCell.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
		    	lineTable.addCell(lineTableCell);
		    	document.add(lineTable);
		    	
		    	PdfPTable checksTable = getPdfTable(1,document);
		    	checksTable.setTotalWidth(document.getPageSize().getWidth() - 80);
		    	checksTable.setLockedWidth(true);
		    	
		    	PdfPTable checksChildTable = new PdfPTable(6);
		    	
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("Total Employee Live Checks: "+headerDTO.getLiveChecks(),boldFont,0));
		    	checksChildTable.addCell(getPdfCell("$ "+checksTotal.toString(),boldFont,0));
		    	
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("Total Manual Checks: "+checkRegData.size(),boldFont,0));
		    	checksChildTable.addCell(getPdfCell("0.00",boldFont,0));
		    	
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("Total Void Checks: 0",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("0.00",boldFont,0));
		    	
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("Total Third Party Checks: 0",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("0.00",boldFont,0));
		    	
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("Total Third Party Void Checks: 0",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("0.00",boldFont,0));
		    	
		    	checksChildTable.setSpacingAfter(10);
		    	
		    	PdfPCell checksCell = new PdfPCell(checksChildTable);
		    	checksCell.setBorder(Rectangle.BOTTOM);
		    	checksTable.addCell(checksCell);
		    	
		    	document.add(checksTable);
		    	
		    	document.close();
			}
		
	        System.out.println("Done.!!");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return file;
	}
	
	/**
	 * Get Header Table 
	 * @param document
	 * @param orgLocation 
	 * @param headerDTO 
	 * @return PdfPTable
	 */
	 private PdfPTable createHeaderTable(Document document, PdfHeaderDTO headerData, OrgLocations orgLocation)
	{
		 PdfPTable headerTable = null;
		 if("PAYROLL REGISTER".equalsIgnoreCase(headerData.getReportType())) {
			  headerTable = new PdfPTable(1);
			 headerTable.setTotalWidth(document.getPageSize().getWidth() - 50);
			 headerTable.setLockedWidth(true);
		 }else {
			  headerTable = getPdfTable(1, document);
		 }
		
		try {
			PdfPTable table = getPdfTable(14, document);
			PdfPCell cell0 = new PdfPCell();
//			cell0.addElement(new Paragraph("Client ID: OVATYW56 - MI Softech Inc", boldFont));
			cell0.addElement(new Paragraph("Pay Group: "+headerData.getGroupName(), boldFont));
			cell0.addElement(new Paragraph("Check Date: "+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), headerData.getCheckDate()), boldFont));
			cell0.addElement(new Paragraph("Run Date: "+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), headerData.getRunDate()), boldFont));
			cell0.addElement(new Paragraph("Run Number : "+headerData.getRunNumber(), boldFont));
			cell0.setBorder(Rectangle.NO_BORDER);
			cell0.setPaddingLeft(20f);
			cell0.setColspan(5);
			table.addCell(cell0);
			PdfPCell cell2 = new PdfPCell();
			Chunk chunk = new Chunk(headerData.getReportType(), greyFont);
			chunk.setBackground(new BaseColor(220, 220, 220));
			cell2.addElement(new Paragraph(chunk));
			cell2.addElement(new Paragraph(headerData.getOrgName(), boldFont));
			cell2.addElement(new Paragraph(headerData.getLocationAddress(), boldFont));
			cell2.addElement(new Paragraph(""));
			cell2.setBorder(Rectangle.NO_BORDER);
			cell2.setColspan(6);
			table.addCell(cell2);
			PdfPCell cell3 = new PdfPCell();
			cell3.addElement(new Paragraph("Period Begin Date: "+headerData.getPeriodBeginDate(), boldFont));
			cell3.addElement(new Paragraph("Period End Date: "+headerData.getPeriodEndDate(), boldFont));
			cell3.addElement(new Paragraph("Pay Period: "+headerData.getPayPeriod(), boldFont));
			if(headerData.getPayrollType() != null)
				cell3.addElement(new Paragraph("Payroll Type: "+headerData.getPayrollType(), boldFont));
			else
				cell3.addElement(new Paragraph("Payroll Type: --", boldFont));
			cell3.setBorder(Rectangle.NO_BORDER);
			cell3.setColspan(4);
			table.addCell(cell3);
			PdfPCell mCell0 = new PdfPCell();
			mCell0.addElement(table);
			mCell0.setBorderWidth(1);
			headerTable.setSpacingAfter(15f);
			headerTable.addCell(mCell0);
			document.add(headerTable);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
			document.add(Chunk.NEWLINE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return headerTable;
	}


	/**
     * Get PdfTabele  
     * @param coulmns
     * @param document
     * @return PdfPTable
     */
	private PdfPTable getPdfTable(int coulmns, Document document)
	{
		PdfPTable table = new PdfPTable(coulmns);
		table.setTotalWidth(document.getPageSize().getWidth() - 80);
		table.setLockedWidth(true);
		return table;
	}
	
	/**
	 * Get Cell for PdfTable 
	 * @param str
	 * @param boldFont
	 * @param fixedHieght
	 * @return PdfPCell
	 */
	private PdfPCell getPdfCell(String str, Font boldFont, int fixedHieght)
	{
		PdfPCell cell = new PdfPCell(new Paragraph(str,boldFont));
		cell.setFixedHeight(fixedHieght);
    	cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	
	public File getDirectDepositExportFile(List<CheckRegisterReportDTO> checkRegData, OrgLocations orgLocation,
			PayrollReportDTO payrollReportDTO) throws Exception {
		
		StringBuilder sbReq = new StringBuilder();
		sbReq.append(orgLocation.getOrgLocationName()).append(Constants.PAYROLL).append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date()));

		StringBuilder fileSB = new StringBuilder();
		File file = File.createTempFile(fileSB.append(orgLocation.getOrgLocationName()).append(Constants.PAYROLL).append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),
				".pdf");

		file.setReadable(true);
		
		try {
			
			if(checkRegData != null && checkRegData.size() >0) {
			Document document = new Document(PageSize.A4.rotate());
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			
			
			CheckRegisterReportDTO registerDTO = checkRegData.get(0);
	    	
	    	PdfHeaderDTO headerDTO = new PdfHeaderDTO();
	    	
	    	headerDTO.setCheckDate(registerDTO.getPayCheckDate());
	    	headerDTO.setGroupName(registerDTO.getPayGroupName());
	    	headerDTO.setOrgName(orgLocation.getOrgLocationName());
	    	headerDTO.setPayPeriod(registerDTO.getPayFrequency());
	    	headerDTO.setPayrollType(registerDTO.getType());
	    	headerDTO.setPeriodBeginDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), registerDTO.getFreqStartDate()));
	    	headerDTO.setPeriodEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), registerDTO.getFreqEndDate()));
	    	headerDTO.setReportType("DIRECT DEPOSIT REGISTER");
	    	headerDTO.setRunDate(registerDTO.getRunDate());
	    	headerDTO.setRunNumber(Integer.parseInt(registerDTO.getRunNo()));
	    	
	    	createHeaderTable(document,headerDTO,orgLocation);
			
			PdfPTable bodyTable = new PdfPTable(1);
			Font greyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
			bodyTable.setTotalWidth(document.getPageSize().getWidth() - 80);
			bodyTable.setLockedWidth(true);
			PdfPCell bodyCell = new PdfPCell();
			bodyCell.setPhrase(new Paragraph("Employee Direct Deposits",greyFont));
			bodyCell.setBackgroundColor(new BaseColor(220,220,220));
			bodyCell.setPaddingTop(5f);
			bodyCell.setPaddingBottom(5f);
			bodyCell.setBorderWidth(1);
			bodyCell.setBorder(Rectangle.BOTTOM);
			bodyCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			bodyTable.addCell(bodyCell);
			
			/*PdfPTable bodyTable1 = new PdfPTable(3);
			bodyTable1.setTotalWidth(document.getPageSize().getWidth() - 80);
			bodyTable1.setLockedWidth(true);
			Stream.of("Bank: BANK OF AMERICA, N.A.", "Routing Transit #: 064000020", "Account #: 444006063283")
		      .forEach(columnTitle -> {
		        PdfPCell header = new PdfPCell();
		        header.setBackgroundColor(new BaseColor(246,246,246));
		        header.setBorderWidth(1);
		        header.setBorder(Rectangle.BOTTOM);
		        header.setPaddingTop(5f);
		        header.setPaddingBottom(5f);
		        header.setPhrase(new Paragraph(columnTitle,boldFont));
		        header.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		        bodyTable1.addCell(header);
		    });*/
			
			PdfPTable bodyTable2 = new PdfPTable(7);
			bodyTable2.setTotalWidth(document.getPageSize().getWidth() - 80);
			bodyTable2.setLockedWidth(true);
			Stream.of(Constants.DIRECT_DEPOSIT_HEADER)
		      .forEach(columnTitle -> {
		        PdfPCell header = new PdfPCell();
		        header.setPhrase(new Paragraph(columnTitle,boldFont));
		        header.setBorder(Rectangle.BOTTOM);
		        header.setPaddingTop(5f);
		        header.setPaddingBottom(5f);
		        header.setBorderWidth(1);
		        header.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		        bodyTable2.addCell(header);
		    });
			bodyTable2.setSpacingAfter(10f);
			
			PdfPTable detailTable = new PdfPTable(7);
			detailTable.setTotalWidth(document.getPageSize().getWidth() - 80);
			detailTable.setLockedWidth(true);
			BigDecimal totalDeposit = BigDecimal.ZERO;
			for (CheckRegisterReportDTO checkRegisterReportDTO : checkRegData) {
				PdfPCell detailCell0 = new PdfPCell();
				detailCell0.addElement(new Paragraph(checkRegisterReportDTO.getEmpId().toString(),detailFont));
				detailCell0.setBorder(Rectangle.NO_BORDER);
				detailCell0.setColspan(1);
				detailCell0.setPaddingLeft(45f);
				detailTable.addCell(detailCell0);
				
				PdfPCell detailCell1 = new PdfPCell();
				StringBuilder name = new StringBuilder();
				if(checkRegisterReportDTO.getLastName() != null) {
					name.append(checkRegisterReportDTO.getFirstName()).append(" ").append(checkRegisterReportDTO.getLastName());
				}else {
					name.append(checkRegisterReportDTO.getFirstName());
				}
				detailCell1.addElement(new Paragraph(name.toString(),detailFont));
				detailCell1.setBorder(Rectangle.NO_BORDER);
				detailCell1.setColspan(1);
				detailCell1.setPaddingLeft(30f);
				detailTable.addCell(detailCell1);
				
				PdfPCell detailCell2 = new PdfPCell();
				if(checkRegisterReportDTO.getDepartmentId() != null)
					detailCell2.addElement(new Paragraph(checkRegisterReportDTO.getDepartmentId(),detailFont));
				else
					detailCell2.addElement(new Paragraph("--",detailFont));
				detailCell2.setBorder(Rectangle.NO_BORDER);
				detailCell2.setColspan(1);
				detailCell2.setPaddingLeft(35f);
				detailTable.addCell(detailCell2);
				
				PdfPCell detailCell3 = new PdfPCell();
				if(checkRegisterReportDTO.getRoutingNum() != null)
					detailCell3.addElement(new Paragraph(checkRegisterReportDTO.getRoutingNum(),detailFont));
				else
					detailCell3.addElement(new Paragraph("--",detailFont));
				detailCell3.setBorder(Rectangle.NO_BORDER);
				detailCell3.setColspan(1);
				detailCell3.setPaddingLeft(35f);
				detailTable.addCell(detailCell3);
				
				PdfPCell detailCell4 = new PdfPCell();
				if(checkRegisterReportDTO.getAccountNum() != null)
					detailCell4.addElement(new Paragraph(checkRegisterReportDTO.getAccountNum(),detailFont));
				else
					detailCell4.addElement(new Paragraph("--",detailFont));
				detailCell4.setBorder(Rectangle.NO_BORDER);
				detailCell4.setColspan(1);
				detailCell4.setPaddingLeft(35f);
				detailTable.addCell(detailCell4);
				
				PdfPCell detailCell5 = new PdfPCell();
				detailCell5.addElement(new Paragraph("$ "+String.valueOf(checkRegisterReportDTO.getNetPay()),detailFont));
				detailCell5.setBorder(Rectangle.NO_BORDER);
				detailCell5.setColspan(1);
				detailCell5.setPaddingLeft(45f);
				detailTable.addCell(detailCell5);
				
				PdfPCell detailCell6 = new PdfPCell();
				detailCell6.addElement(new Paragraph("--",detailFont));
				detailCell6.setBorder(Rectangle.NO_BORDER);
				detailCell6.setColspan(1);
				detailCell6.setPaddingLeft(40f);
				detailTable.addCell(detailCell6);
				totalDeposit = totalDeposit.add(checkRegisterReportDTO.getNetPay());
			}
			
			PdfPTable detailTable1 = new PdfPTable(1);
			detailTable1.setTotalWidth(document.getPageSize().getWidth() - 350);
			detailTable1.setLockedWidth(true);
			PdfPCell footerCell = new PdfPCell();
			
			Chunk glue = new Chunk(new VerticalPositionMark());
			Paragraph p = new Paragraph(orgLocation.getOrgLocationName(),boldFont);
			p.add(new Chunk(glue));
			p.add(String.valueOf("$ "+totalDeposit));
			footerCell.addElement(p);
			
			footerCell.setBackgroundColor(new BaseColor(220,220,220));
			footerCell.setPaddingTop(5f);
			footerCell.setPaddingBottom(5f);
			footerCell.setBorderWidth(1);
			footerCell.setBorder(Rectangle.NO_BORDER);
			
			
			detailTable1.addCell(footerCell);
			
			
			PdfPTable detailTable2 = new PdfPTable(1);
			detailTable2.setTotalWidth(document.getPageSize().getWidth() - 350);
			detailTable2.setLockedWidth(true);
			PdfPCell footerCell1 = new PdfPCell();
			
			Chunk glue1 = new Chunk(new VerticalPositionMark());
			/*Paragraph p1 = new Paragraph("Number of Employees",boldFont);
			p1.add(new Chunk(glue1));
			p1.add("--");
			footerCell1.addElement(p1);*/
			
			Paragraph p2 = new Paragraph("Number of Direct Deposits",boldFont);
			p2.add(new Chunk(glue1));
			p2.add(String.valueOf(checkRegData.size()));
			footerCell1.addElement(p2);
			
		/*	Paragraph p3 = new Paragraph("Number of Prenote Accounts",boldFont);
			p3.add(new Chunk(glue1));
			p3.add("9");
			footerCell1.addElement(p3);*/
			
			footerCell1.setPaddingTop(5f);
			footerCell1.setPaddingBottom(5f);
			footerCell1.setBorderWidth(1);
			footerCell1.setBorder(Rectangle.NO_BORDER);
			
			detailTable2.addCell(footerCell1);
			
			
			
			PdfPTable detailTable3 = new PdfPTable(2);
			detailTable3.setTotalWidth(document.getPageSize().getWidth() - 80);
			detailTable3.setLockedWidth(true);
			Stream.of("", "")
		      .forEach(columnTitle -> {
		        PdfPCell header = new PdfPCell();
		        header.setPhrase(new Paragraph(columnTitle,boldFont));
		        header.setBorder(Rectangle.BOTTOM);
		        header.setPaddingTop(5f);
		        header.setPaddingBottom(5f);
		        header.setBorderWidth(1);
		        header.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		        detailTable3.addCell(header);
		    });
			
			document.add(bodyTable);
//			document.add(bodyTable1);
			document.add(bodyTable2);
			document.add(detailTable);
			document.add(detailTable1);
			document.add(detailTable2);
			document.add(detailTable3);
			document.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return file;
	}

	public File getontributionReportPdf(OrgLocations orgLocation, List<PayrollReportDTO> deductionHisList) throws IOException
	{
		StringBuilder fileSB = new StringBuilder();
		File file = File.createTempFile(fileSB.append(orgLocation.getOrgLocationName()).append("_Contribution Report").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
		file.setReadable(true);
		
		try {
			Document document = new Document(PageSize.A4.rotate());
			
	    	PdfWriter.getInstance(document, new FileOutputStream(file));
	    	document.open();
	    	
	    	PdfHeaderDTO headerDTO = new PdfHeaderDTO();
	    	if(deductionHisList.size() > 0)
	    	{
		    	PayrollReportDTO payrollReportDTO = deductionHisList.get(0);
		    	headerDTO.setCheckDate(payrollReportDTO.getCheckDate());
		    	headerDTO.setGroupName(payrollReportDTO.getGroupName());
		    	headerDTO.setOrgName(orgLocation.getOrgLocationName());
		    	headerDTO.setPayPeriod(payrollReportDTO.getGroupFrequency());
		    	headerDTO.setPayrollType(payrollReportDTO.getType());
		    	headerDTO.setPeriodBeginDate(payrollReportDTO.getFreqStartDate());
		    	headerDTO.setPeriodEndDate(payrollReportDTO.getFreqEndDate());
		    	headerDTO.setReportType("CONTRIBUTION REPORTS");
		    	headerDTO.setRunDate(payrollReportDTO.getRunDate());
		    	headerDTO.setRunNumber(Integer.parseInt(payrollReportDTO.getRunNo()));
	    	}
	    	createHeaderTable(document, headerDTO, orgLocation);
	    	
	    	
	    	PdfPTable empTable = new PdfPTable(9);
	    	empTable.setTotalWidth(document.getPageSize().getWidth() - 80);
	    	empTable.setLockedWidth(true);
	    	empTable.setSpacingBefore(20);  
	    	
	    	PdfPCell cell = new PdfPCell(new Phrase("Department", boldFont));
	    	cell.setFixedHeight(20);
//	    	cell.setBackgroundColor(new BaseColor(209,210,211));
	    	empTable.addCell(cell);
	    	empTable.addCell(new Phrase("Emp Id #", boldFont));
	    	empTable.addCell(new Phrase("Employee/Payee", boldFont));
	    	empTable.addCell(new Phrase("Type", boldFont));
	    	empTable.addCell(new Phrase("Schedule", boldFont));
	    	empTable.addCell(new Phrase("Current", boldFont));
	    	empTable.addCell(new Phrase("MTD", boldFont));
	    	empTable.addCell(new Phrase("QTD", boldFont));
	    	empTable.addCell(new Phrase("YTD", boldFont));
	    	document.add(empTable);
	    	
	    	
	    	PdfPTable empDataTable = getPdfTable(9,document);
	    	empDataTable.setSpacingBefore(2);  
	    	
	    	BigDecimal currentTotal = BigDecimal.ZERO;
	    	BigDecimal YTDTotal = BigDecimal.ZERO;
	    	BigDecimal mTDTotal = BigDecimal.ZERO;
	    	BigDecimal qTDTotal = BigDecimal.ZERO;
	    	for(PayrollReportDTO reportData : deductionHisList) {
	    		empDataTable.addCell(getPdfCell(reportData.getDepartmentName(),normalFont,5));
		    	empDataTable.addCell(getPdfCell(reportData.getEmpId(),normalFont,0));
		    	empDataTable.addCell(getPdfCell(reportData.getEmpName(),normalFont,0));
		    	empDataTable.addCell(getPdfCell(reportData.getContributionType(),normalFont,0));
		    	empDataTable.addCell(getPdfCell(reportData.getSchedule(),normalFont,0));
		    	empDataTable.addCell(getPdfCell("$ "+reportData.getContributionAmount(),normalFont,0));
		    	empDataTable.addCell(getPdfCell("$ "+reportData.getContributionMTD(),normalFont,0));
		    	empDataTable.addCell(getPdfCell("$ "+reportData.getContributionQTD(),normalFont,0));
		    	empDataTable.addCell(getPdfCell("$ "+reportData.getContributionYTD(),normalFont,0));	
		    	currentTotal = currentTotal.add(reportData.getContributionAmount());
		    	YTDTotal = YTDTotal.add(reportData.getContributionYTD());
		    	mTDTotal = mTDTotal.add(reportData.getContributionMTD());
		    	qTDTotal = qTDTotal.add(reportData.getContributionMTD());
	    	}
	    
	    	empDataTable.setSpacingAfter(8);
	    	document.add(empDataTable);
	    	
	     	PdfPTable lineTable = getPdfTable(1,document);
	    	
	    	
	    	PdfPTable lineChildTable = new PdfPTable(9);
	    	lineChildTable.addCell(getPdfCell("",boldFont,0));
	    	lineChildTable.addCell(getPdfCell("",boldFont,0));
	    	lineChildTable.addCell(getPdfCell("",boldFont,0));
	    	lineChildTable.addCell(getPdfCell("REPORT TOTALS:",boldFont,0));
	    	lineChildTable.addCell(getPdfCell("",boldFont,0));
	    	lineChildTable.addCell(getPdfCell("Current",boldFont,0));
	    	lineChildTable.addCell(getPdfCell("MTD",boldFont,0));
	    	lineChildTable.addCell(getPdfCell("QTD",boldFont,0));
	    	lineChildTable.addCell(getPdfCell("YTD",boldFont,0));
//	    	
	    	PdfPCell lineTableCell = new PdfPCell(lineChildTable);
	    	lineTableCell.setFixedHeight(30);
	    	lineTableCell.setBorderWidth(1f);
	    	lineTableCell.setBackgroundColor(new BaseColor(209,210,211));
	    	lineTableCell.setBorder(Rectangle.BOTTOM);
	    	lineTable.addCell(lineTableCell);
	    	document.add(lineTable);
	    	
	    	
	    	PdfPTable ytdTable = new PdfPTable(9);
	    	ytdTable.addCell(getPdfCell("",boldFont,0));
	    	ytdTable.addCell(getPdfCell("",boldFont,0));
	    	ytdTable.addCell(getPdfCell("",boldFont,0));
	    	ytdTable.addCell(getPdfCell("",boldFont,0));
	    	ytdTable.addCell(getPdfCell("",boldFont,0));
	    	ytdTable.addCell(getPdfCell("$ "+currentTotal,boldFont,0));
	    	ytdTable.addCell(getPdfCell("$ "+mTDTotal,boldFont,0));
	    	ytdTable.addCell(getPdfCell("$ "+qTDTotal,boldFont,0));
	    	ytdTable.addCell(getPdfCell("$ "+YTDTotal,boldFont,0));
//	    	
	    	document.add(ytdTable);	
	    	document.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public File getDeductionReportPdf(OrgLocations orgLocation, List<PayrollReportDTO> deductionHisList) throws Exception {
	
		StringBuilder sbReq = new StringBuilder();
		sbReq.append(orgLocation.getOrgLocationName()).append(Constants.PAYROLL).append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date()));

		StringBuilder fileSB = new StringBuilder();
		File file = File.createTempFile(fileSB.append(orgLocation.getOrgLocationName()).append("Deduction Report").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),
				".pdf");

		file.setReadable(true);
		
		Document document = new Document(PageSize.A4.rotate());
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		try {
		
		PayrollReportDTO payrollReportDTO = deductionHisList.get(0);
    	PdfHeaderDTO headerDTO = new PdfHeaderDTO();
    	
    	headerDTO.setCheckDate(payrollReportDTO.getCheckDate());
    	headerDTO.setGroupName(payrollReportDTO.getGroupName());
    	headerDTO.setOrgName(orgLocation.getOrgLocationName());
    	headerDTO.setPayPeriod(payrollReportDTO.getGroupFrequency());
    	headerDTO.setPayrollType(payrollReportDTO.getType());
    	headerDTO.setPeriodBeginDate(payrollReportDTO.getFreqStartDate());
    	headerDTO.setPeriodEndDate(payrollReportDTO.getFreqEndDate());
    	headerDTO.setReportType("DEDUCTION REPORT");
    	headerDTO.setRunDate(payrollReportDTO.getRunDate());
    	headerDTO.setRunNumber(Integer.parseInt(payrollReportDTO.getRunNo()));
    	createHeaderTable(document, headerDTO, orgLocation);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);



		PdfPTable empTable = new PdfPTable(8);
		empTable.setTotalWidth(document.getPageSize().getWidth() - 80);
		empTable.setLockedWidth(true);
		empTable.setSpacingBefore(20); 

		PdfPCell cell = new PdfPCell(new Phrase("Emp Id #", boldFont));
		cell.setFixedHeight(20);
		// cell.setBackgroundColor(new BaseColor(209,210,211));
		empTable.addCell(cell);
		empTable.addCell(new Phrase("Employee Name", boldFont));
		empTable.addCell(new Phrase("Department", boldFont));
		empTable.addCell(new Phrase("Type", boldFont));
		empTable.addCell(new Phrase("Current", boldFont));
		empTable.addCell(new Phrase("MTD", boldFont));
		empTable.addCell(new Phrase("QTD", boldFont));
		empTable.addCell(new Phrase("YTD", boldFont));
		document.add(empTable);


		PdfPTable empDataTable = getPdfTable(8,document);
		empDataTable.setSpacingBefore(2); 

		BigDecimal currentTotal = BigDecimal.ZERO;
    	BigDecimal YTDTotal = BigDecimal.ZERO;
    	BigDecimal MTDTotal = BigDecimal.ZERO;
    	BigDecimal QTDTotal = BigDecimal.ZERO;
		for (PayrollReportDTO report : deductionHisList) {
			empDataTable.addCell(getPdfCell(report.getEmpId(),normalFont,5));
			empDataTable.addCell(getPdfCell(report.getEmpName(),normalFont,0));
			empDataTable.addCell(getPdfCell(report.getDepartmentName(),normalFont,0));
			empDataTable.addCell(getPdfCell(report.getDeductionType(),normalFont,0));
			empDataTable.addCell(getPdfCell("$ "+report.getDedAmount(),normalFont,0));
			empDataTable.addCell(getPdfCell("$ "+report.getDeductionMTD(),normalFont,0));
			empDataTable.addCell(getPdfCell("$ "+report.getDeductionQTD(),normalFont,0));
			empDataTable.addCell(getPdfCell("$ "+report.getDeductionYTD(),normalFont,0));
			currentTotal = currentTotal.add(report.getDedAmount());
	    	YTDTotal = YTDTotal.add(report.getDeductionYTD());
	    	MTDTotal = MTDTotal.add(report.getDeductionMTD());
	    	QTDTotal = QTDTotal.add(report.getDeductionQTD());
		}
		empDataTable.setSpacingAfter(8);
		document.add(empDataTable);
		PdfPTable lineTable = getPdfTable(1,document);


		PdfPTable lineChildTable = new PdfPTable(8);
		lineChildTable.addCell(getPdfCell("",boldFont,0));
		lineChildTable.addCell(getPdfCell("",boldFont,0));
		lineChildTable.addCell(getPdfCell("REPORT TOTALS:",boldFont,0));
		lineChildTable.addCell(getPdfCell("",boldFont,0));
		lineChildTable.addCell(getPdfCell("Current",boldFont,0));
		lineChildTable.addCell(getPdfCell("MTD",boldFont,0));
		lineChildTable.addCell(getPdfCell("QTD",boldFont,0));
		lineChildTable.addCell(getPdfCell("YTD",boldFont,0));
		// 
		PdfPCell lineTableCell = new PdfPCell(lineChildTable);
		lineTableCell.setFixedHeight(30);
		lineTableCell.setBorderWidth(1f);
		lineTableCell.setBackgroundColor(new BaseColor(209,210,211));
		lineTableCell.setBorder(Rectangle.BOTTOM);
		lineTable.addCell(lineTableCell);
		document.add(lineTable);


		PdfPTable ytdTable = new PdfPTable(8);
		ytdTable.addCell(getPdfCell("",boldFont,0));
		ytdTable.addCell(getPdfCell("",boldFont,0));
		ytdTable.addCell(getPdfCell("",boldFont,0));
		ytdTable.addCell(getPdfCell("",boldFont,0));
		ytdTable.addCell(getPdfCell("$ "+currentTotal,boldFont,0));
		ytdTable.addCell(getPdfCell("$ "+MTDTotal,boldFont,0));
		ytdTable.addCell(getPdfCell("$ "+QTDTotal,boldFont,0));
		ytdTable.addCell(getPdfCell("$ "+YTDTotal,boldFont,0));
		// 
		document.add(ytdTable);
		document.close();
		System.out.println("Done.!!");
	} catch (Exception e) {
		  e.printStackTrace();
	}
		return file;
		
}
	
	
	public File getPayrollRegisterReportsPdf(ResponseList<PayrollReportDTO> responseList, OrgLocations orgLocation) throws Exception
	{
		StringBuilder sbReq = new StringBuilder();
		sbReq.append(orgLocation.getOrgLocationName()).append(Constants.PAYROLL).append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date()));
		StringBuilder fileSB = new StringBuilder();
		File file = File.createTempFile(fileSB.append(orgLocation.getOrgLocationName()).append(Constants.PAYROLL).append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), new Date())).toString(), ".pdf");
		file.setReadable(true);
		
		
		Document document = new Document(PageSize.A4.rotate());
		Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD);
		Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
		Font ctNormalFont = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL);
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		
		PayrollReportDTO payrollReportDTO = responseList.getData().get(0);
    	PdfHeaderDTO headerDTO = new PdfHeaderDTO();
    	
    	headerDTO.setCheckDate(payrollReportDTO.getCheckDate());
    	headerDTO.setGroupName(payrollReportDTO.getGroupName());
    	headerDTO.setOrgName(orgLocation.getOrgLocationName());
    	headerDTO.setPayPeriod(payrollReportDTO.getGroupFrequency());
    	headerDTO.setPayrollType(payrollReportDTO.getType());
    	headerDTO.setPeriodBeginDate(payrollReportDTO.getFreqStartDate());
    	headerDTO.setPeriodEndDate(payrollReportDTO.getFreqEndDate());
    	headerDTO.setReportType("PAYROLL REGISTER");
    	headerDTO.setRunDate(payrollReportDTO.getRunDate());
    	headerDTO.setRunNumber(Integer.parseInt(payrollReportDTO.getRunNo()));
    	createHeaderTable(document, headerDTO, orgLocation);
    	BigDecimal totalTax = BigDecimal.ZERO;
		BigDecimal totalYtdTax = BigDecimal.ZERO;
		for(PayrollReportDTO payrollReportDto : responseList.getData()) {
			PdfPTable maintable = new PdfPTable(1);
			maintable.setTotalWidth(document.getPageSize().getWidth() - 50);
			maintable.setLockedWidth(true);
			
			PdfPTable childTable1 = new PdfPTable(9);
			childTable1.setTotalWidth(document.getPageSize().getWidth() - 50);
			childTable1.setLockedWidth(true);
			PdfPCell cell0 = new PdfPCell();
			cell0.addElement(new Paragraph(payrollReportDto.getEmpName(), normalFont));
			StringBuilder sb = new StringBuilder();
			sb.append("Emp #: ");
			if(StringUtils.isNotBlank(payrollReportDto.getEmpId()))
				sb.append(payrollReportDto.getEmpId());
			cell0.addElement(new Paragraph(sb.toString(), normalFont));
			cell0.setBorder(Rectangle.NO_BORDER);
			cell0.setBackgroundColor(new BaseColor(209, 210, 211));
			// cell0.setColspan(5);
			childTable1.addCell(cell0);
			PdfPCell cell1 = new PdfPCell();
			cell1.addElement(new Paragraph(""));
			String ssn = "";
			if(StringUtils.isNotBlank(payrollReportDto.getSsnNumber())){
				StringBuilder sb1 = new StringBuilder();
				sb1.append("xxx-xx-");
				try {
					String[] ssnNum = payrollReportDto.getSsnNumber().split("-");
					sb1.append(ssnNum[2]);
					ssn = sb1.toString();
				} catch (Exception e) {
					String ssnNum = payrollReportDto.getSsnNumber();
					if (ssnNum.length() == 3) {
		    		  sb1.append(ssnNum);
		    		} else if (ssnNum.length() > 3) {
		    			sb1.append(ssnNum.substring(ssnNum.length() - 4));
		    		} 
					ssn = sb1.toString();
				}
				/*String[] ssnNum = payrollReportDto.getSsnNumber().split("-");
				sb1.append(ssnNum[2]);
				ssn = sb1.toString();*/
			}else {
				ssn = "NA";
			}
			cell1.addElement(new Paragraph(ssn, normalFont));
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.setBackgroundColor(new BaseColor(209, 210, 211));
			// cell1.setColspan(5);
			childTable1.addCell(cell1);
			PdfPCell cell2 = new PdfPCell();
			if(StringUtils.isNotBlank(payrollReportDto.getCandidateWorkAddress()))
				cell2.addElement(new Paragraph(payrollReportDto.getCandidateWorkAddress(), normalFont));
			else
				cell2.addElement(new Paragraph("NA", normalFont));
			cell2.addElement(new Paragraph(""));
			cell2.setBorder(Rectangle.NO_BORDER);
			cell2.setBackgroundColor(new BaseColor(209, 210, 211));
			// cell2.setColspan(5);
			childTable1.addCell(cell2);
			PdfPCell cell3 = new PdfPCell();
			StringBuilder fedSbMar = new StringBuilder();
			if(StringUtils.isNotBlank(payrollReportDto.getFedMaritalStat())) 
				fedSbMar.append("Fed Marital: ").append(payrollReportDto.getFedMaritalStat());
			else
				fedSbMar.append("Fed Marital: ").append("NA");
			StringBuilder stateSbMar = new StringBuilder();
			if(StringUtils.isNotBlank(payrollReportDto.getStateMaritalStat())) 
				stateSbMar.append("State Marital: ").append(payrollReportDto.getStateMaritalStat());
			else
				stateSbMar.append("State Marital: ").append("NA");
			cell3.addElement(new Paragraph(fedSbMar.toString(), normalFont));
			cell3.addElement(new Paragraph(stateSbMar.toString(), normalFont));
			cell3.setBorder(Rectangle.NO_BORDER);
			cell3.setBackgroundColor(new BaseColor(209, 210, 211));
			// cell3.setColspan(10);
			childTable1.addCell(cell3);
			PdfPCell cell4 = new PdfPCell();
			if(StringUtils.isNotBlank(payrollReportDto.getFedExempt())) 
				cell4.addElement(new Paragraph("Exempts: "+payrollReportDto.getFedExempt()+"", normalFont));
			else
				cell4.addElement(new Paragraph("Exempts: NA", normalFont));
			
			cell4.addElement(new Paragraph("", normalFont));
			cell4.setBorder(Rectangle.NO_BORDER);
			cell4.setBackgroundColor(new BaseColor(209, 210, 211));
			// cell4.setColspan(5);
			childTable1.addCell(cell4);
			PdfPCell cell5 = new PdfPCell();
			StringBuilder fedAddTax = new StringBuilder();
			StringBuilder stateAddTax = new StringBuilder();
			if(StringUtils.isNotBlank(payrollReportDto.getFedAddTax())) 
				fedAddTax.append("Addl Tax: ").append(payrollReportDto.getFedAddTax());
			else
				fedAddTax.append("Addl Tax: ").append("NA");
				
			if(StringUtils.isNotBlank(payrollReportDto.getStateAddTax())) 
				stateAddTax.append("Addl Tax: ").append(payrollReportDto.getStateAddTax());
			else
				stateAddTax.append("Addl Tax: ").append("NA");
				
			cell5.addElement(new Paragraph(fedAddTax.toString(), normalFont));
			cell5.addElement(new Paragraph(stateAddTax.toString(), normalFont));
			cell5.setBorder(Rectangle.NO_BORDER);
			cell5.setBackgroundColor(new BaseColor(209, 210, 211));
			// cell5.setColspan(5);
			childTable1.addCell(cell5);
			PdfPCell cell6 = new PdfPCell();
			cell6.addElement(new Paragraph("Res State:  "+payrollReportDto.getResState()+"", normalFont));
			cell6.addElement(new Paragraph("", normalFont));
			cell6.setBorder(Rectangle.NO_BORDER);
			cell6.setBackgroundColor(new BaseColor(209, 210, 211));
			// cell6.setColspan(5);
			childTable1.addCell(cell6);
			PdfPCell cell7 = new PdfPCell();
//			cell7.addElement(new Paragraph("Per Pay Salary: 0.00", normalFont));
			cell7.addElement(new Paragraph("Status:			"+payrollReportDto.getEmpStatus()+"", normalFont));
			cell7.setBorder(Rectangle.NO_BORDER);
			cell7.setBackgroundColor(new BaseColor(209, 210, 211));
			// cell7.setColspan(5);
			childTable1.addCell(cell7);
			PdfPCell cell8 = new PdfPCell();
			cell8.addElement(new Paragraph("Hire Date:  "+payrollReportDto.getDateOfJoin()+"", normalFont));
//			cell8.addElement(new Paragraph("Birth Date: 12/1/XXXX", normalFont));
			cell8.setBorder(Rectangle.NO_BORDER);
			cell8.setBackgroundColor(new BaseColor(209, 210, 211));
			// cell8.setColspan(5);
			childTable1.addCell(cell8);
			PdfPCell mCell0 = new PdfPCell();
			mCell0.addElement(childTable1);
			mCell0.setBorderWidth(1f);
			maintable.addCell(mCell0);
			
			
			PdfPTable childTable2 = new PdfPTable(7);
			childTable2.setTotalWidth(document.getPageSize().getWidth() - 50);
			childTable2.setLockedWidth(true);
			PdfPCell ct2Cell0 = new PdfPCell();
			ct2Cell0.addElement(new Paragraph(payrollReportDto.getType(), ctNormalFont));
			ct2Cell0.setBorder(Rectangle.NO_BORDER);
			// ct2Cell0.setBackgroundColor(new BaseColor(227,228,229));
			// ct2Cell0.setColspan(5);
			childTable2.addCell(ct2Cell0);
			PdfPCell ct2Cell1 = new PdfPCell();
			StringBuilder grossSb = new StringBuilder();
			grossSb.append("Gross Pay: $ ").append(payrollReportDto.getGrossPay());
			ct2Cell1.addElement(new Paragraph(grossSb.toString(), ctNormalFont));
			ct2Cell1.setBorder(Rectangle.NO_BORDER);
			// ct2Cell1.setBackgroundColor(new BaseColor(255,255,45));
			// ct2Cell1.setColspan(5);
			childTable2.addCell(ct2Cell1);
			PdfPCell ct2Cell2 = new PdfPCell();
			ct2Cell2.addElement(new Paragraph("", ctNormalFont));
			ct2Cell2.setBorder(Rectangle.NO_BORDER);
			// ct2Cell2.setBackgroundColor(new BaseColor(255,255,45));
			// ct2Cell2.setColspan(5);
			childTable2.addCell(ct2Cell2);
			PdfPCell ct2Cell3 = new PdfPCell();
			StringBuilder netSb = new StringBuilder();
			netSb.append("Net Pay: $ ").append(payrollReportDto.getNetPay());
			ct2Cell3.addElement(new Paragraph(netSb.toString(), ctNormalFont));
			ct2Cell3.setBorder(Rectangle.NO_BORDER);
			// ct2Cell3.setBackgroundColor(new BaseColor(255,255,45));
			// ct2Cell3.setColspan(5);
			childTable2.addCell(ct2Cell3);
			PdfPCell ct2Cell4 = new PdfPCell();
			String amount1 = "", amount2 = "";
			if("Direct Deposit".equalsIgnoreCase(payrollReportDto.getDepositType())) {
				amount1 = payrollReportDto.getNetPay().toString();
				amount2 = "0.00";
			}else {
				amount2 = payrollReportDto.getNetPay().toString();
				amount1 = "0.00";
			}
			ct2Cell4.addElement(new Paragraph("Direct Deposit: $ "+amount1+"", ctNormalFont));
			ct2Cell4.setBorder(Rectangle.NO_BORDER);
			// ct2Cell4.setBackgroundColor(new BaseColor(255,255,45));
			// ct2Cell4.setColspan(5);
			childTable2.addCell(ct2Cell4);
			PdfPCell ct2Cell5 = new PdfPCell();
			ct2Cell5.addElement(new Paragraph("Check Amount: $ "+amount2+"", ctNormalFont));
			ct2Cell5.setBorder(Rectangle.NO_BORDER);
			// ct2Cell5.setBackgroundColor(new BaseColor(255,255,45));
			// ct2Cell5.setColspan(5);
			childTable2.addCell(ct2Cell5);
			PdfPCell ct2Cell6 = new PdfPCell();
			ct2Cell6.addElement(new Paragraph("", ctNormalFont));
			ct2Cell6.setBorder(Rectangle.NO_BORDER);
			// ct2Cell6.setBackgroundColor(new BaseColor(255,255,45));
			// ct2Cell6.setColspan(5);
			childTable2.addCell(ct2Cell6);
			PdfPCell mCell1 = new PdfPCell();
			mCell1.addElement(childTable2);
			mCell1.setBorderWidth(1f);
			maintable.addCell(mCell1);
			
			
			PdfPTable childTable3 = new PdfPTable(3);
			childTable3.setTotalWidth(document.getPageSize().getWidth() - 50);
			childTable3.setLockedWidth(true);
			
			
			// for earnings /salary table
			PdfPTable tbl1 = new PdfPTable(6);
			PdfPCell tbl1cl0 = new PdfPCell();
			tbl1cl0.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl1cl0.addElement(new Paragraph("Earnings", ctNormalFont));
			tbl1cl0.setBorder(Rectangle.BOTTOM);
			tbl1cl0.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl1.addCell(tbl1cl0);
			PdfPCell tbl1cl1 = new PdfPCell();
			tbl1cl1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl1cl1.addElement(new Paragraph("Rate", ctNormalFont));
			tbl1cl1.setBorder(Rectangle.BOTTOM);
			tbl1cl1.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl1.addCell(tbl1cl1);
			PdfPCell tbl1cl2 = new PdfPCell();
			tbl1cl2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl1cl2.addElement(new Paragraph("Hours", ctNormalFont));
			tbl1cl2.setBorder(Rectangle.BOTTOM);
			tbl1cl2.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl1.addCell(tbl1cl2);
			PdfPCell tbl1cl3 = new PdfPCell();
			tbl1cl3.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl1cl3.addElement(new Paragraph("Dollars", ctNormalFont));
			tbl1cl3.setBorder(Rectangle.BOTTOM);
			tbl1cl3.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl1.addCell(tbl1cl3);
			PdfPCell tbl1cl4 = new PdfPCell();
			tbl1cl4.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl1cl4.addElement(new Paragraph("YTD Hours", ctNormalFont));
			tbl1cl4.setBorder(Rectangle.BOTTOM);
			tbl1cl4.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl1.addCell(tbl1cl4);
			PdfPCell tbl1cl5 = new PdfPCell();
			tbl1cl5.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl1cl5.addElement(new Paragraph("YTD Dollars", ctNormalFont));
			tbl1cl5.setBorder(Rectangle.BOTTOM);
			tbl1cl5.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl1.addCell(tbl1cl5);
			
			tbl1.addCell(getPdfCell("Salary", ctNormalFont, 0, ""));
			tbl1.addCell(getPdfCell("$ "+payrollReportDto.getEmpPayRate().toString(), ctNormalFont, 0, ""));
			tbl1.addCell(getPdfCell(payrollReportDto.getRegularHours(), ctNormalFont, 0, "grey"));
			tbl1.addCell(getPdfCell("$ "+payrollReportDto.getEmpRegularAmount().toString(), ctNormalFont, 0, ""));
			tbl1.addCell(getPdfCell(payrollReportDto.getYtdHours(), ctNormalFont, 0, "grey"));
			tbl1.addCell(getPdfCell("$ "+payrollReportDto.getYtdNet().toString(), ctNormalFont, 0, ""));
			
			
			tbl1.addCell(getBottomAlignedPdfCell("", ctNormalFont, 0, ""));
			PdfPCell c1 = getBottomAlignedPdfCell("Totals:", boldFont, 0, "");
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl1.addCell(c1);
			PdfPCell c2 = getBottomAlignedPdfCell(payrollReportDto.getRegularHours(), ctNormalFont, 0, "grey");
			ct2Cell0.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl1.addCell(c2);
			PdfPCell c3 = getBottomAlignedPdfCell("$ "+payrollReportDto.getEmpRegularAmount().toString(), ctNormalFont, 0, "");
			tbl1.addCell(c3);
			PdfPCell c4 = getBottomAlignedPdfCell(payrollReportDto.getYtdHours(), ctNormalFont, 0, "grey");
			tbl1.addCell(c4);
			PdfPCell c5 = getBottomAlignedPdfCell("$ "+payrollReportDto.getYtdNet().toString(), ctNormalFont, 0, "");
			tbl1.addCell(c5);
			
			
			// for deductions table
			PdfPTable tbl2 = new PdfPTable(3);
			PdfPCell tbl2cl0 = new PdfPCell();
			tbl2cl0.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl2cl0.addElement(new Paragraph("Deductions ", ctNormalFont));
			tbl2cl0.setBorder(Rectangle.BOTTOM);
			tbl2cl0.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl2.addCell(tbl2cl0);
			PdfPCell tbl2cl1 = new PdfPCell();
			tbl2cl1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl2cl1.addElement(new Paragraph("Current ", ctNormalFont));
			tbl2cl1.setBorder(Rectangle.BOTTOM);
			tbl2cl1.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl2.addCell(tbl2cl1);
			PdfPCell tbl2cl2 = new PdfPCell();
			tbl2cl2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl2cl2.addElement(new Paragraph("YTD ", ctNormalFont));
			tbl2cl2.setBorder(Rectangle.BOTTOM);
			tbl2cl2.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl2.addCell(tbl2cl2);
			
			BigDecimal dedTotal = BigDecimal.ZERO, ytdDedTotal = BigDecimal.ZERO;
			for(PayrollReportDTO payDto : payrollReportDto.getDeductionsList()) {
				tbl2.addCell(getPdfCell("", ctNormalFont, 0, ""));
				PdfPCell tb2c2 = getBottomAlignedPdfCell("$ "+payDto.getDeductions().toString(), ctNormalFont, 0, "grey");
				dedTotal = dedTotal.add(payDto.getDeductions());
				tbl2.addCell(tb2c2);
				PdfPCell tb2c3 = getBottomAlignedPdfCell("$ "+payDto.getYtdDeductions().toString(), ctNormalFont, 0, "");
				ytdDedTotal = ytdDedTotal.add(payDto.getYtdDeductions());
				tbl2.addCell(tb2c3);
			}
			
			PdfPCell tb2c1 = getBottomAlignedPdfCell("Totals: ", boldFont, 0, "");
			tb2c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl2.addCell(tb2c1);
			PdfPCell tb2c2 = getBottomAlignedPdfCell("$ "+dedTotal.toString(), ctNormalFont, 0, "grey");
			tbl2.addCell(tb2c2);
			PdfPCell tb2c3 = getBottomAlignedPdfCell("$ "+ytdDedTotal.toString(), ctNormalFont, 0, "");
			tbl2.addCell(tb2c3);
			
			
			// for taxes table
			PdfPTable tbl3 = new PdfPTable(3);
			PdfPCell tbl3cl0 = new PdfPCell();
			tbl3cl0.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl3cl0.addElement(new Paragraph("Taxes", ctNormalFont));
			tbl3cl0.setBorder(Rectangle.BOTTOM);
			tbl3cl0.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl3.addCell(tbl3cl0);
			/*PdfPCell tbl3cl1 = new PdfPCell();
			tbl3cl1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl3cl1.addElement(new Paragraph("Wage", ctNormalFont));
			tbl3cl1.setBorder(Rectangle.BOTTOM);
			tbl3cl1.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl3.addCell(tbl3cl1);*/
			PdfPCell tbl3cl2 = new PdfPCell();
			tbl3cl2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl3cl2.addElement(new Paragraph("Tax", ctNormalFont));
			tbl3cl2.setBorder(Rectangle.BOTTOM);
			tbl3cl2.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl3.addCell(tbl3cl2);
			/*PdfPCell tbl3cl3 = new PdfPCell();
			tbl3cl3.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl3cl3.addElement(new Paragraph("YTD Wage", ctNormalFont));
			tbl3cl3.setBorder(Rectangle.BOTTOM);
			tbl3cl3.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl3.addCell(tbl3cl3);*/
			PdfPCell tbl3cl4 = new PdfPCell();
			tbl3cl4.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl3cl4.addElement(new Paragraph("YTD Tax", ctNormalFont));
			tbl3cl4.setBorder(Rectangle.BOTTOM);
			tbl3cl4.setBackgroundColor(new BaseColor(227, 228, 229));
			tbl3.addCell(tbl3cl4);
			
			
			YTDPayrollDTO ytdTax = payrollReportDto.getYtdPayrollDTO();
			for(EmployeePayrollTax empTax : payrollReportDto.getTaxHistory()) {
				
				if(empTax.getFicaWithholding() != null && !(BigDecimal.ZERO.compareTo(empTax.getFicaWithholding()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("FICA:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getFicaWithholding().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getFicaWithholding());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdFICA().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdFICA());
				}
				
				if(empTax.getMedicareWithholding() != null && !(BigDecimal.ZERO.compareTo(empTax.getMedicareWithholding()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("MEDICARE:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getMedicareWithholding().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getMedicareWithholding());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdMedicare().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdMedicare());
				}
				
				if(empTax.getFederalWithholding() != null && !(BigDecimal.ZERO.compareTo(empTax.getFederalWithholding()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("FEDERAL:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getFederalWithholding().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getFederalWithholding());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdFederal().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdFederal());
				}
				
				if(empTax.getEarnedIncCredit() != null && !(BigDecimal.ZERO.compareTo(empTax.getEarnedIncCredit()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("EARNED_INC:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getEarnedIncCredit().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getEarnedIncCredit());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdEarnedIncCredit().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdEarnedIncCredit());
				}
				
				if(empTax.getStateWithholding() != null && !(BigDecimal.ZERO.compareTo(empTax.getStateWithholding()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("STATE WithHolding:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getStateWithholding().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getStateWithholding());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdStateWithholding().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdStateWithholding());
				}
				
				if(empTax.getCountyWithholding() != null && !(BigDecimal.ZERO.compareTo(empTax.getCountyWithholding()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("COUNTY:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getCountyWithholding().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getCountyWithholding());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdCounty().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdCounty());
				}
				
				if(empTax.getSUI() != null && !(BigDecimal.ZERO.compareTo(empTax.getSUI()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("SUI:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getSUI().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getSUI());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdSUI().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdSUI());
				}
				
				if(empTax.getSDI() != null && !(BigDecimal.ZERO.compareTo(empTax.getSDI()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("SDI:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getSDI().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getSDI());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdSDI().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdSDI());
				}
				
				if(empTax.getFLI() != null && !(BigDecimal.ZERO.compareTo(empTax.getFLI()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("FLI:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getFLI().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getFLI());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdFLI().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdFLI());
				}
				
				if(empTax.getHCSF() != null && !(BigDecimal.ZERO.compareTo(empTax.getHCSF()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("HCSF:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getHCSF().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getHCSF());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdHCSF().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdHCSF());
				}
				
				if(empTax.getWC() != null && !(BigDecimal.ZERO.compareTo(empTax.getWC()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("WC:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getWC().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getWC());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdWC().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdWC());
				}
				
				if(empTax.getLocalTax() != null && !(BigDecimal.ZERO.compareTo(empTax.getLocalTax()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("LOCAL_TAX:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell("$ "+empTax.getLocalTax().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getLocalTax());
					tbl3.addCell(getBottomAlignedPdfCell("$ "+ytdTax.getYtdlocaltax().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getYtdlocaltax());
				}
				/*if(empTax.getStateWithholding() != null && !(BigDecimal.ZERO.compareTo(empTax.getStateWithholding()) == 0)) {
					tbl3.addCell(getBottomAlignedPdfCell("State WithHolding:", boldFont, 0, ""));
					tbl3.addCell(getBottomAlignedPdfCell(empTax.getStateWithholding().toString(), ctNormalFont, 0, "grey"));
					totalTax = totalTax.add(empTax.getStateWithholding());
					tbl3.addCell(getBottomAlignedPdfCell(ytdTax.getStateWithholding().toString(), ctNormalFont, 0, ""));
					totalYtdTax = totalYtdTax.add(ytdTax.getStateWithholding());
				}*/
			}
			
			
			PdfPCell tb3c1 = getBottomAlignedPdfCell("Totals:", boldFont, 0, "");
			tb3c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbl3.addCell(tb3c1);
			PdfPCell tb3c2 = getBottomAlignedPdfCell("$ "+totalTax.toString(), ctNormalFont, 0, "grey");
			tbl3.addCell(tb3c2);
			PdfPCell tb3c3 = getBottomAlignedPdfCell("$ "+totalYtdTax.toString(), ctNormalFont, 0, "");
			tbl3.addCell(tb3c3);
			
			childTable3.addCell(tbl1);
			childTable3.addCell(tbl2);
			childTable3.addCell(tbl3);
			
			PdfPCell mCell2 = new PdfPCell();
			mCell2.addElement(childTable3);
			mCell2.setBorderWidth(1f);
			maintable.addCell(mCell2);
			
			document.add(maintable);
		}
		document.close();
		
		return file;
	}
	
	private PdfPCell getPdfCell(String str, Font boldFont, int fixedHieght, String color)
	{
		PdfPCell cell = new PdfPCell(new Paragraph(str, boldFont));
		if ("grey".equalsIgnoreCase(color))
			cell.setBackgroundColor(new BaseColor(227, 228, 229));
		cell.setFixedHeight(fixedHieght);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	
	private PdfPCell getBottomAlignedPdfCell(String str, Font boldFont, int fixedHieght, String color)
	{
		PdfPCell cell = new PdfPCell(new Paragraph(40, str, boldFont));
		// cell.setFixedHeight(fixedHieght);
		if ("grey".equalsIgnoreCase(color))
			cell.setBackgroundColor(new BaseColor(227, 228, 229));
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}

	public File employeePayrollPdfExport(ResponseList<AssociateReportDTO> responseList, OrgLocations orgLocation) throws Exception {
		File file = null;
	try {
	    StringBuilder fileSB = new StringBuilder();
		file = File.createTempFile(fileSB.append("employeePayroll_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
		file.setReadable(true);
		JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
		report().setTemplate(Templates.reportTemplate)
				.title(cmp.text(Constants.EMPLOYEE_PAYROLL_REPORTS).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
				.columns(
						col.column("Employee Id", "empId", type.stringType()),
						col.column("Employee Name", "empName", type.stringType()),
						col.column("Gross Pay", "grossPay", type.stringType()),
						col.column("Pre Tax Deductions", "preTaxDed", type.stringType()),
						col.column("Federal Tax", "federalTax", type.stringType()),
						col.column("Social Security", "socialSec", type.stringType()),
						col.column("Medicare Withholding", "medicare", type.stringType()),
						col.column("State Withholding", "state", type.stringType()),
						col.column("SUI/SDI", "sui", type.stringType()),
						col.column("Post Tax Deductions", "postTaxDed", type.stringType()),
						col.column("Net Pay", "netPay", type.stringType()))
				.pageFooter(Templates.footerComponent)
				.setDataSource(createEmployeePayrollPdfDataSource(responseList)).toPdf(pdfExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	return file;
	}

	private DRDataSource createEmployeePayrollPdfDataSource(ResponseList<AssociateReportDTO> responseList) {
		DRDataSource dataSource = new DRDataSource("empId","empName", "grossPay", "preTaxDed", "federalTax", "socialSec", "medicare", "state", "sui", "postTaxDed", "netPay");
		for(AssociateReportDTO associate :responseList.getData()) {
			String  employeeId= "--";
			String employeeName = "--";
			String grossPay = "$ 0.0";
			String preTaxDed = "$ 0.0";
			String federalTax = "$ 0.0";
			String socialSec = "$ 0.0";
			String medicare = "$ 0.0";
			String state = "$ 0.0";
			String sui = "$ 0.0";
			String postTaxDed = "$ 0.0";
			String netPay = "$ 0.0";
			if(associate != null && associate.getEmpId() != null) {
				employeeId = associate.getEmpId();
			}
			if(associate.getEmpName() != null) {
				employeeName = associate.getEmpName();
			}
			
			if(associate.getGrossPay() != null) {                   // usefull for 0  amount condition && !(BigDecimal.ZERO.compareTo(associate.getGrossPay()) == 0)
				grossPay = "$ "+associate.getGrossPay().toString();
			}
			
			if(associate.getPreTaxDeduction() != null ) {
				preTaxDed = "$ "+associate.getPreTaxDeduction().toString();
			}
			
			if(associate.getTotalFederalWithholding() != null) {
				federalTax = "$ "+associate.getTotalFederalWithholding().toString();
			}
			
			if(associate.getTotalfica() != null) {
				socialSec = "$ "+associate.getTotalfica().toString();
			}
			
			if(associate.getTotalMedicareWithholding() != null) {
				medicare = "$ "+associate.getTotalMedicareWithholding().toString();
			}
			
			if(associate.getTotalStateWithholding() != null) {
				state = "$ "+associate.getTotalStateWithholding().toString();
			}
			
			if(associate.getTotalSui() != null) {
				sui = "$ "+associate.getTotalSui().toString();
			}
			
			if(associate.getPostTaxDeduction() != null) {
				postTaxDed = "$ "+associate.getPostTaxDeduction().toString();
			}
			
			if(associate.getNetPay() != null) {
				netPay = "$ "+associate.getNetPay().toString();
			}
			

			dataSource.add(employeeId,employeeName,grossPay,preTaxDed,federalTax,socialSec,medicare,state,sui,postTaxDed,netPay);
		}
		return dataSource;
	}

	public File employeePayrollExcelExport(ResponseList<AssociateReportDTO> responseList, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("employeePayroll_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".xls");
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
							col.column("Gross Pay", "grossPay", type.stringType()),
							col.column("Pre Tax Deductions", "preTaxDed", type.stringType()),
							col.column("Federal Tax", "federalTax", type.stringType()),
							col.column("Social Security", "socialSec", type.stringType()),
							col.column("Medicare Withholding", "medicare", type.stringType()),
							col.column("State Withholding", "state", type.stringType()),
							col.column("SUI/SDI", "sui", type.stringType()),
							col.column("Post Tax Deductions", "postTaxDed", type.stringType()),
							col.column("Net Pay", "netPay", type.stringType()))
					.setDataSource(createEmployeePayrollPdfDataSource(responseList)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	return file;
	}

	public File associateWithholdingPdfExport(ResponseList<AssociateWithHoldingDTO> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("associateWithholding_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
			file.setReadable(true);
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(Constants.ASSOCIATE_WITH_HOLDING_REPORT).setStyle(Templates.bold18CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER))
					.columns(
							col.column("Associate Id", "id", type.stringType()),
							col.column("Associate Name", "name", type.stringType()),
							col.column("SSN", "ssn", type.stringType()),
							col.column("Work Location", "location", type.stringType()),
							col.column("Federal Filling Status", "federalStatus", type.stringType()),
							col.column("Federal Allowances", "federalAllowances", type.stringType()),
							col.column("State", "state", type.stringType()),
							col.column("State Filling Status", "stateStatus", type.stringType()),
							col.column("State Allowances", "stateAllowances", type.stringType()))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createAssociateWithholdingPdfDataSource(res)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}

	private DRDataSource createAssociateWithholdingPdfDataSource(ResponseList<AssociateWithHoldingDTO> res) {
		DRDataSource dataSource = new DRDataSource("id","name", "ssn", "location", "federalStatus", "federalAllowances", "state", "stateStatus", "stateAllowances");
		for(AssociateWithHoldingDTO associate :res.getData()) {
			String  employeeId= "--";
			String employeeName = "--";
			String ssn = "--";
			String workLocation = "--";
			String federalStatus = "--";
			String federalAllowances = "--";
			String state = "--";
			String stateStatus = "--";
			String stateAllowances = "--";
			
			if(associate != null && associate.getEmployeeId() != null) {
				employeeId = associate.getEmployeeId();
			}
			if(associate.getLastName() != null) {
				StringBuilder sb = new StringBuilder();
				employeeName = sb.append(associate.getFirstName()).append(" ").append(associate.getLastName()).toString();
			}else {
				employeeName = associate.getFirstName();
			}
			
			if(associate.getSsnNumber() != null) {
				ssn = associate.getSsnNumber();
			}
			if(associate.getWorkLocation() != null) {
				workLocation = associate.getWorkLocation();
			}
			if(associate.getFederalFillingStatus() != null) {
				federalStatus = associate.getFederalFillingStatus();
			}
			if(associate.getFederalTaxAllowances() != null) {
				federalAllowances = associate.getFederalTaxAllowances();
			}
			if(associate.getResidenceStateCode() != null) {
				state = associate.getResidenceStateCode();
			}
			if(associate.getStateFillingStatus() != null) {
				stateStatus = associate.getStateFillingStatus();
			}
			if(associate.getStateTaxAllowances() != null) {
				stateAllowances = associate.getStateTaxAllowances();
			}
			

			

			dataSource.add(employeeId,employeeName,ssn,workLocation,federalStatus,federalAllowances,state,stateStatus,stateAllowances);
		}
		return dataSource;
	}

	public File associateWithholdingExcelExport(ResponseList<AssociateWithHoldingDTO> res, OrgLocations orgLocation) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
			file = File.createTempFile(fileSB.append("associateWithholding_").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".xls");
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
							col.column("Associate Id", "id", type.stringType()),
							col.column("Associate Name", "name", type.stringType()),
							col.column("SSN", "ssn", type.stringType()),
							col.column("Work Location", "location", type.stringType()),
							col.column("Federal Filling Status", "federalStatus", type.stringType()),
							col.column("Federal Allowances", "federalAllowances", type.stringType()),
							col.column("State", "state", type.stringType()),
							col.column("State Filling Status", "stateStatus", type.stringType()),
							col.column("State Allowances", "stateAllowances", type.stringType()))
					.setDataSource(createAssociateWithholdingPdfDataSource(res)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	return file;
	}

	public File getDeductionSummaryReportPdf(List<DeductionSummaryDTO> data, OrgLocations orgLocation, String reportType) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
		    String title = "";
		    if(Constants.DEDUCTION.equalsIgnoreCase(reportType)) {
		    	file = File.createTempFile(fileSB.append("deductionSummary").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
		    	title = Constants.DEDUCTION_SUMMARY_REPORTS;
		    }else {
		    	file = File.createTempFile(fileSB.append("contributionSummary").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".pdf");
		    	title = Constants.CONTRIBUTION_SUMMARY_REPORTS;
		    }
			
			file.setReadable(true);
			StyleBuilder tableHeadStyle = stl.style(Templates.columnStyle);
			StyleBuilder tableRowStyle = stl.style(Templates.columnStyle);
			
			JasperPdfExporterBuilder pdfExporter = export.pdfExporter(file);
			report().setTemplate(Templates.reportTemplate)
					.title(cmp.text(title).setStyle(Templates.bold18CenteredStyle))
					.setColumnStyle(tableRowStyle.setHorizontalAlignment(HorizontalAlignment.CENTER)).columns(
							col.column("Type", "type", type.stringType()),
							col.column("No of Employees", "noOfEmployees", type.integerType()),
							col.column("Current", "current", Templates.currencyType),
							col.column("QTD", "qtd", Templates.currencyType),
							col.column("YTD", "ytd", Templates.currencyType))
					.pageFooter(Templates.footerComponent)
					.setDataSource(createDeductionSummaryPdfDataSource(data)).toPdf(pdfExporter);
			} catch (DRException e) {
				e.printStackTrace();
			}
		return file;
	}

	private DRDataSource createDeductionSummaryPdfDataSource(List<DeductionSummaryDTO> data) {
		DRDataSource dataSource = new DRDataSource("type","noOfEmployees", "current", "qtd", "ytd");
		for(DeductionSummaryDTO ded :data) {
			String  type= "--";
			int noOfEmployees = 0;
			BigDecimal current = BigDecimal.ZERO;
			BigDecimal qtd = BigDecimal.ZERO;
			BigDecimal ytd = BigDecimal.ZERO;

			if(ded.getDeductionType() != null) {
				type = ded.getDeductionType();
			}
			
				noOfEmployees = ded.getCandidateCount();
				
			if(ded.getCurrentAmt() != null) {
				current = ded.getCurrentAmt();
			}
			
			if(ded.getQtd() != null) {
				qtd = ded.getQtd();
			}
			
			if(ded.getYtd() != null) {
				ytd = ded.getYtd();
			}
			

			

			dataSource.add(type,noOfEmployees,current,qtd,ytd);
		}
		return dataSource;
	}

	public File getDeductionSummaryReportXls(List<DeductionSummaryDTO> data, OrgLocations orgLocation, String reportType) throws Exception {
		File file = null;
		try {
		    StringBuilder fileSB = new StringBuilder();
		    
		    if(Constants.DEDUCTION.equalsIgnoreCase(reportType)) {
			     file = File.createTempFile(fileSB.append("deductionSummary").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".xls");
		    }else {
		    	 file = File.createTempFile(fileSB.append("contributionSummary").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),new Date())).toString(),".xls");
		    }
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
							col.column("Type", "type", type.stringType()),
							col.column("No of Employees", "noOfEmployees", type.integerType()),
							col.column("Current", "current", type.bigDecimalType()),
							col.column("QTD", "qtd", type.bigDecimalType()),
							col.column("YTD", "ytd", type.bigDecimalType()))
					.setDataSource(createDeductionSummaryPdfDataSource(data)).toXls(xlsExporter);
		} catch (DRException e) {
			e.printStackTrace();
		}
	return file;
	}
}