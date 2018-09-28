package com.bis.sourcelead.payroll.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.PdfHeaderDTO;
import com.bis.sourcelead.util.CommonUtil;
import java.math.BigDecimal;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

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
		    	headerDTO.setRunNumber(0);
		    	
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
		PdfPTable headerTable = getPdfTable(1, document);
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
    	headerDTO.setRunNumber(0);
    	
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
			detailCell2.addElement(new Paragraph(checkRegisterReportDTO.getDepartmentId(),detailFont));
			detailCell2.setBorder(Rectangle.NO_BORDER);
			detailCell2.setColspan(1);
			detailCell2.setPaddingLeft(25f);
			detailTable.addCell(detailCell2);
			
			PdfPCell detailCell3 = new PdfPCell();
			detailCell3.addElement(new Paragraph("Routing Number",detailFont));
			detailCell3.setBorder(Rectangle.NO_BORDER);
			detailCell3.setColspan(1);
			detailCell3.setPaddingLeft(25f);
			detailTable.addCell(detailCell3);
			
			PdfPCell detailCell4 = new PdfPCell();
			detailCell4.addElement(new Paragraph("Account Number",detailFont));
			detailCell4.setBorder(Rectangle.NO_BORDER);
			detailCell4.setColspan(1);
			detailCell4.setPaddingLeft(25f);
			detailTable.addCell(detailCell4);
			
			PdfPCell detailCell5 = new PdfPCell();
			detailCell5.addElement(new Paragraph("$ "+String.valueOf(checkRegisterReportDTO.getNetPay()),detailFont));
			detailCell5.setBorder(Rectangle.NO_BORDER);
			detailCell5.setColspan(1);
			detailCell5.setPaddingLeft(45f);
			detailTable.addCell(detailCell5);
			
			PdfPCell detailCell6 = new PdfPCell();
			detailCell6.addElement(new Paragraph("",detailFont));
			detailCell6.setBorder(Rectangle.NO_BORDER);
			detailCell6.setColspan(1);
			detailCell6.setPaddingLeft(25f);
			detailTable.addCell(detailCell6);
			totalDeposit = totalDeposit.add(checkRegisterReportDTO.getNetPay());
		}
		
		PdfPTable detailTable1 = new PdfPTable(1);
		detailTable1.setTotalWidth(document.getPageSize().getWidth() - 350);
		detailTable1.setLockedWidth(true);
		PdfPCell footerCell = new PdfPCell();
		
		Chunk glue = new Chunk(new VerticalPositionMark());
		Paragraph p = new Paragraph("MI Softech Inc",boldFont);
		p.add(new Chunk(glue));
		p.add(String.valueOf(totalDeposit));
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
		Paragraph p1 = new Paragraph("Number of Employees",boldFont);
		p1.add(new Chunk(glue1));
		p1.add("9");
		footerCell1.addElement(p1);
		
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
//		document.add(bodyTable1);
		document.add(bodyTable2);
		document.add(detailTable);
		document.add(detailTable1);
		document.add(detailTable2);
		document.add(detailTable3);
		document.close();
		return file;
	}

	public File getontributionReportPdf(OrgLocations orgLocation, List<PayrollReportDTO> deductionHisList)
	{
		try {
			Document document = new Document(PageSize.A4.rotate());
			String DEST = "/home/rajasekhar/Desktop/PDF/simple_deduction_table.pdf";
	    	PdfWriter.getInstance(document, new FileOutputStream(DEST));
	    	document.open();
	    	
	    	PayrollReportDTO payrollReportDTO = deductionHisList.get(0);
	    	PdfHeaderDTO headerDTO = new PdfHeaderDTO();
	    	
	    	headerDTO.setCheckDate(payrollReportDTO.getCheckDate());
	    	headerDTO.setGroupName(payrollReportDTO.getGroupName());
	    	headerDTO.setOrgName(orgLocation.getOrgLocationName());
	    	headerDTO.setPayPeriod(payrollReportDTO.getGroupFrequency());
	    	headerDTO.setPayrollType(payrollReportDTO.getType());
	    	headerDTO.setPeriodBeginDate(payrollReportDTO.getFreqStartDate());
	    	headerDTO.setPeriodEndDate(payrollReportDTO.getFreqEndDate());
	    	headerDTO.setReportType("DIRECT DEPOSIT REGISTER");
	    	headerDTO.setRunDate(payrollReportDTO.getRunDate());
	    	headerDTO.setRunNumber(0);
	    	createHeaderTable(document, headerDTO, orgLocation);
	    	
//	    	PdfPTable maintable = getPdfTable(1, document);
//
//	    	PdfPTable table = getPdfTable(14, document);
//
//	    	PdfPCell cell0 = new PdfPCell();
//	    	Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
//	    	cell0.addElement(new Paragraph("Client ID: OVATYW56 - MI Softech Inc",boldFont));
//	    	cell0.addElement(new Paragraph("Pay Group: Monthly",boldFont));
//	    	cell0.addElement(new Paragraph("Check Date: 4/5/2018",boldFont));
//	    	cell0.addElement(new Paragraph("Run Date: 4/5/2018",boldFont));
//	    	cell0.addElement(new Paragraph(" Run Number : "+10,boldFont));
//	    	cell0.setBorder(Rectangle.NO_BORDER);
//	    	cell0.setPaddingLeft(20f);
//	    	cell0.setColspan(5);
//	    	table.addCell(cell0);
//
//
//	    	PdfPCell cell2 = new PdfPCell();
//	    	Font greyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//	    	Chunk chunk = new Chunk("CONTRIBUTION REPORT", greyFont);
//	    	chunk.setBackground(new BaseColor(220,220,220));
//	    	cell2.addElement(new Paragraph(chunk));
//	    	cell2.addElement(new Paragraph("MI Softech Inc",boldFont));
//	    	cell2.addElement(new Paragraph("DBA: MI Softech Inc",boldFont));
//	    	cell2.addElement(new Paragraph(""));
//	    	cell2.setBorder(Rectangle.NO_BORDER);
//	    	cell2.setColspan(6);
//	    	table.addCell(cell2);
//	    	PdfPCell cell3 = new PdfPCell();
//	    	cell3.addElement(new Paragraph("Period Begin Date: 2/1/2018",boldFont));
//	    	cell3.addElement(new Paragraph("Period End Date: 2/28/2018",boldFont));
//	    	cell3.addElement(new Paragraph("Pay Period: 6",boldFont));
//	    	cell3.addElement(new Paragraph("Payroll Type: Regular",boldFont));
//	    	cell3.setBorder(Rectangle.NO_BORDER);
//	    	cell3.setColspan(4);
//	    	table.addCell(cell3);
//
//	    	PdfPCell mCell0 = new PdfPCell();
//	    	mCell0.addElement(table);
//	    	mCell0.setBorderWidth(1);
//	    	maintable.addCell(mCell0);
//	    	document.add(maintable);
//	    	
//	    	
//	    	document.add(Chunk.NEWLINE);
//	    	document.add(Chunk.NEWLINE);
//	    	document.add(Chunk.NEWLINE);
	    	
	    	
	    	
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
	    	for(PayrollReportDTO reportData : deductionHisList) {
	    		empDataTable.addCell(getPdfCell(reportData.getDepartmentName(),normalFont,5));
		    	empDataTable.addCell(getPdfCell(reportData.getEmpId(),normalFont,0));
		    	empDataTable.addCell(getPdfCell(reportData.getEmpName(),normalFont,0));
		    	empDataTable.addCell(getPdfCell(reportData.getContributionType(),normalFont,0));
		    	empDataTable.addCell(getPdfCell(reportData.getSchedule(),normalFont,0));
		    	empDataTable.addCell(getPdfCell("$ "+reportData.getContributionAmount(),normalFont,0));
		    	empDataTable.addCell(getPdfCell("$ xxxxx",normalFont,0));
		    	empDataTable.addCell(getPdfCell("$ xxxxx",normalFont,0));
		    	empDataTable.addCell(getPdfCell("$ "+reportData.getContributionYTD(),normalFont,0));	
		    	currentTotal = currentTotal.add(reportData.getContributionAmount());
		    	YTDTotal = YTDTotal.add(reportData.getContributionYTD());
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
	    	ytdTable.addCell(getPdfCell("Misc (no goal):",boldFont,0));
	    	ytdTable.addCell(getPdfCell("",boldFont,0));
	    	ytdTable.addCell(getPdfCell("$ "+currentTotal,boldFont,0));
	    	ytdTable.addCell(getPdfCell("$ xxxx",boldFont,0));
	    	ytdTable.addCell(getPdfCell("$ xxxx",boldFont,0));
	    	ytdTable.addCell(getPdfCell("$ "+YTDTotal,boldFont,0));
//	    	
	    	document.add(ytdTable);	
	    	document.close();
		}catch(Exception e) {
			
		}
		return null;
	}
	
}
