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
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.PdfHeaderDTO;
import com.bis.sourcelead.util.CommonUtil;
import java.math.BigDecimal;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
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
		    	headerDTO.setPeriodBeginDate(registerDTO.getFreqStartDate());
		    	headerDTO.setPeriodEndDate(registerDTO.getFreqEndDate());
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
			cell3.addElement(new Paragraph("Period Begin Date: "+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), headerData.getPeriodBeginDate()), boldFont));
			cell3.addElement(new Paragraph("Period End Date: "+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), headerData.getPeriodEndDate()), boldFont));
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
    	headerDTO.setPeriodBeginDate(registerDTO.getFreqStartDate());
    	headerDTO.setPeriodEndDate(registerDTO.getFreqEndDate());
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
		
		PdfPTable maintable = new PdfPTable(1);
		maintable.setTotalWidth(document.getPageSize().getWidth() - 50);
		maintable.setLockedWidth(true);
		
		PdfPTable childTable1 = new PdfPTable(9);
		childTable1.setTotalWidth(document.getPageSize().getWidth() - 50);
		childTable1.setLockedWidth(true);
		PdfPCell cell0 = new PdfPCell();
		cell0.addElement(new Paragraph("Aerra, Rahul", normalFont));
		cell0.addElement(new Paragraph("Emp #: 25", normalFont));
		cell0.setBorder(Rectangle.NO_BORDER);
		cell0.setBackgroundColor(new BaseColor(209, 210, 211));
		// cell0.setColspan(5);
		childTable1.addCell(cell0);
		PdfPCell cell1 = new PdfPCell();
		cell1.addElement(new Paragraph(""));
		cell1.addElement(new Paragraph("XXX-XX-5607", normalFont));
		cell1.setBorder(Rectangle.NO_BORDER);
		cell1.setBackgroundColor(new BaseColor(209, 210, 211));
		// cell1.setColspan(5);
		childTable1.addCell(cell1);
		PdfPCell cell2 = new PdfPCell();
		cell2.addElement(new Paragraph("2000 Walnut Ave FREMONT, CA 94538", normalFont));
		cell2.addElement(new Paragraph(""));
		cell2.setBorder(Rectangle.NO_BORDER);
		cell2.setBackgroundColor(new BaseColor(209, 210, 211));
		// cell2.setColspan(5);
		childTable1.addCell(cell2);
		PdfPCell cell3 = new PdfPCell();
		cell3.addElement(new Paragraph("Fed Marital:", normalFont));
		cell3.addElement(new Paragraph("State Marital:", normalFont));
		cell3.setBorder(Rectangle.NO_BORDER);
		cell3.setBackgroundColor(new BaseColor(209, 210, 211));
		// cell3.setColspan(10);
		childTable1.addCell(cell3);
		PdfPCell cell4 = new PdfPCell();
		cell4.addElement(new Paragraph("Exempts: 0", normalFont));
		cell4.addElement(new Paragraph("Exempts: 0", normalFont));
		cell4.setBorder(Rectangle.NO_BORDER);
		cell4.setBackgroundColor(new BaseColor(209, 210, 211));
		// cell4.setColspan(5);
		childTable1.addCell(cell4);
		PdfPCell cell5 = new PdfPCell();
		cell5.addElement(new Paragraph("Addl Tax:", normalFont));
		cell5.addElement(new Paragraph("Addl Tax:", normalFont));
		cell5.setBorder(Rectangle.NO_BORDER);
		cell5.setBackgroundColor(new BaseColor(209, 210, 211));
		// cell5.setColspan(5);
		childTable1.addCell(cell5);
		PdfPCell cell6 = new PdfPCell();
		cell6.addElement(new Paragraph("Res State:  CA", normalFont));
		cell6.addElement(new Paragraph("Work State: CA", normalFont));
		cell6.setBorder(Rectangle.NO_BORDER);
		cell6.setBackgroundColor(new BaseColor(209, 210, 211));
		// cell6.setColspan(5);
		childTable1.addCell(cell6);
		PdfPCell cell7 = new PdfPCell();
		cell7.addElement(new Paragraph("Per Pay Salary: 0.00", normalFont));
		cell7.addElement(new Paragraph("Status:			ACTIVE", normalFont));
		cell7.setBorder(Rectangle.NO_BORDER);
		cell7.setBackgroundColor(new BaseColor(209, 210, 211));
		// cell7.setColspan(5);
		childTable1.addCell(cell7);
		PdfPCell cell8 = new PdfPCell();
		cell8.addElement(new Paragraph("Hire Date:  12/1/2017", normalFont));
		cell8.addElement(new Paragraph("Birth Date: 12/1/XXXX", normalFont));
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
		ct2Cell0.addElement(new Paragraph("REGULAR CHECK", ctNormalFont));
		ct2Cell0.setBorder(Rectangle.NO_BORDER);
		// ct2Cell0.setBackgroundColor(new BaseColor(227,228,229));
		// ct2Cell0.setColspan(5);
		childTable2.addCell(ct2Cell0);
		PdfPCell ct2Cell1 = new PdfPCell();
		ct2Cell1.addElement(new Paragraph("Gross Wage: 7,351.83", ctNormalFont));
		ct2Cell1.setBorder(Rectangle.NO_BORDER);
		// ct2Cell1.setBackgroundColor(new BaseColor(255,255,45));
		// ct2Cell1.setColspan(5);
		childTable2.addCell(ct2Cell1);
		PdfPCell ct2Cell2 = new PdfPCell();
		ct2Cell2.addElement(new Paragraph("Paid Gross: 7,351.83", ctNormalFont));
		ct2Cell2.setBorder(Rectangle.NO_BORDER);
		// ct2Cell2.setBackgroundColor(new BaseColor(255,255,45));
		// ct2Cell2.setColspan(5);
		childTable2.addCell(ct2Cell2);
		PdfPCell ct2Cell3 = new PdfPCell();
		ct2Cell3.addElement(new Paragraph("Net Pay: 5,249.11", ctNormalFont));
		ct2Cell3.setBorder(Rectangle.NO_BORDER);
		// ct2Cell3.setBackgroundColor(new BaseColor(255,255,45));
		// ct2Cell3.setColspan(5);
		childTable2.addCell(ct2Cell3);
		PdfPCell ct2Cell4 = new PdfPCell();
		ct2Cell4.addElement(new Paragraph("Direct Deposit: Checking ####0888 5,249.11", ctNormalFont));
		ct2Cell4.setBorder(Rectangle.NO_BORDER);
		// ct2Cell4.setBackgroundColor(new BaseColor(255,255,45));
		// ct2Cell4.setColspan(5);
		childTable2.addCell(ct2Cell4);
		PdfPCell ct2Cell5 = new PdfPCell();
		ct2Cell5.addElement(new Paragraph("Check Amount: 0.00", ctNormalFont));
		ct2Cell5.setBorder(Rectangle.NO_BORDER);
		// ct2Cell5.setBackgroundColor(new BaseColor(255,255,45));
		// ct2Cell5.setColspan(5);
		childTable2.addCell(ct2Cell5);
		PdfPCell ct2Cell6 = new PdfPCell();
		ct2Cell6.addElement(new Paragraph("Voucher #: V3301198", ctNormalFont));
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
		tbl1.addCell(getPdfCell("", ctNormalFont, 0, ""));
		tbl1.addCell(getPdfCell("0.00", ctNormalFont, 0, "grey"));
		tbl1.addCell(getPdfCell("5000.00", ctNormalFont, 0, ""));
		tbl1.addCell(getPdfCell("173.33", ctNormalFont, 0, "grey"));
		tbl1.addCell(getPdfCell("15000.00", ctNormalFont, 0, ""));
		tbl1.addCell(getBottomAlignedPdfCell("", ctNormalFont, 0, ""));
		PdfPCell c1 = getBottomAlignedPdfCell("Totals:", boldFont, 0, "");
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl1.addCell(c1);
		PdfPCell c2 = getBottomAlignedPdfCell("0.00", ctNormalFont, 0, "grey");
		ct2Cell0.setBackgroundColor(new BaseColor(227, 228, 229));
		tbl1.addCell(c2);
		PdfPCell c3 = getBottomAlignedPdfCell("5000.00", ctNormalFont, 0, "");
		tbl1.addCell(c3);
		PdfPCell c4 = getBottomAlignedPdfCell("173.33", ctNormalFont, 0, "grey");
		tbl1.addCell(c4);
		PdfPCell c5 = getBottomAlignedPdfCell("15000.00", ctNormalFont, 0, "");
		tbl1.addCell(c5);
		
		
		// for deductions table
		PdfPTable tbl2 = new PdfPTable(3);
		PdfPCell tbl2cl0 = new PdfPCell();
		tbl2cl0.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl2cl0.addElement(new Paragraph("Deductions", ctNormalFont));
		tbl2cl0.setBorder(Rectangle.BOTTOM);
		tbl2cl0.setBackgroundColor(new BaseColor(227, 228, 229));
		tbl2.addCell(tbl2cl0);
		PdfPCell tbl2cl1 = new PdfPCell();
		tbl2cl1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl2cl1.addElement(new Paragraph("Current", ctNormalFont));
		tbl2cl1.setBorder(Rectangle.BOTTOM);
		tbl2cl1.setBackgroundColor(new BaseColor(227, 228, 229));
		tbl2.addCell(tbl2cl1);
		PdfPCell tbl2cl2 = new PdfPCell();
		tbl2cl2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl2cl2.addElement(new Paragraph("YTD", ctNormalFont));
		tbl2cl2.setBorder(Rectangle.BOTTOM);
		tbl2cl2.setBackgroundColor(new BaseColor(227, 228, 229));
		tbl2.addCell(tbl2cl2);
		PdfPCell tb2c1 = getBottomAlignedPdfCell("Totals:", boldFont, 0, "");
		tb2c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl2.addCell(tb2c1);
		PdfPCell tb2c2 = getBottomAlignedPdfCell("0.00", ctNormalFont, 0, "grey");
		tbl2.addCell(tb2c2);
		PdfPCell tb2c3 = getBottomAlignedPdfCell("0.00", ctNormalFont, 0, "");
		tbl2.addCell(tb2c3);
		
		
		// for taxes table
		PdfPTable tbl3 = new PdfPTable(5);
		PdfPCell tbl3cl0 = new PdfPCell();
		tbl3cl0.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl3cl0.addElement(new Paragraph("Taxes", ctNormalFont));
		tbl3cl0.setBorder(Rectangle.BOTTOM);
		tbl3cl0.setBackgroundColor(new BaseColor(227, 228, 229));
		tbl3.addCell(tbl3cl0);
		PdfPCell tbl3cl1 = new PdfPCell();
		tbl3cl1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl3cl1.addElement(new Paragraph("Wage", ctNormalFont));
		tbl3cl1.setBorder(Rectangle.BOTTOM);
		tbl3cl1.setBackgroundColor(new BaseColor(227, 228, 229));
		tbl3.addCell(tbl3cl1);
		PdfPCell tbl3cl2 = new PdfPCell();
		tbl3cl2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl3cl2.addElement(new Paragraph("Tax", ctNormalFont));
		tbl3cl2.setBorder(Rectangle.BOTTOM);
		tbl3cl2.setBackgroundColor(new BaseColor(227, 228, 229));
		tbl3.addCell(tbl3cl2);
		PdfPCell tbl3cl3 = new PdfPCell();
		tbl3cl3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl3cl3.addElement(new Paragraph("YTD Wage", ctNormalFont));
		tbl3cl3.setBorder(Rectangle.BOTTOM);
		tbl3cl3.setBackgroundColor(new BaseColor(227, 228, 229));
		tbl3.addCell(tbl3cl3);
		PdfPCell tbl3cl4 = new PdfPCell();
		tbl3cl4.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl3cl4.addElement(new Paragraph("YTD Tax", ctNormalFont));
		tbl3cl4.setBorder(Rectangle.BOTTOM);
		tbl3cl4.setBackgroundColor(new BaseColor(227, 228, 229));
		tbl3.addCell(tbl3cl4);
		tbl3.addCell(getPdfCell("SOC SEC EE", ctNormalFont, 0, ""));
		tbl3.addCell(getPdfCell("5,000.00", ctNormalFont, 0, "grey"));
		tbl3.addCell(getPdfCell("310.00", ctNormalFont, 0, ""));
		tbl3.addCell(getPdfCell("15,000.00", ctNormalFont, 0, "grey"));
		tbl3.addCell(getPdfCell("930.00", ctNormalFont, 0, ""));
		
		
		tbl3.addCell(getPdfCell("SOC SEC EE", ctNormalFont, 0, ""));
		tbl3.addCell(getPdfCell("5,000.00", ctNormalFont, 0, "grey"));
		tbl3.addCell(getPdfCell("310.00", ctNormalFont, 0, ""));
		tbl3.addCell(getPdfCell("15,000.00", ctNormalFont, 0, "grey"));
		tbl3.addCell(getPdfCell("930.00", ctNormalFont, 0, ""));
		
		tbl3.addCell(getPdfCell("SOC SEC EE", ctNormalFont, 0, ""));
		tbl3.addCell(getPdfCell("5,000.00", ctNormalFont, 0, "grey"));
		tbl3.addCell(getPdfCell("310.00", ctNormalFont, 0, ""));
		tbl3.addCell(getPdfCell("15,000.00", ctNormalFont, 0, "grey"));
		tbl3.addCell(getPdfCell("930.00", ctNormalFont, 0, ""));
		
		tbl3.addCell(getPdfCell("SOC SEC EE", ctNormalFont, 0, ""));
		tbl3.addCell(getPdfCell("5,000.00", ctNormalFont, 0, "grey"));
		tbl3.addCell(getPdfCell("310.00", ctNormalFont, 0, ""));
		tbl3.addCell(getPdfCell("15,000.00", ctNormalFont, 0, "grey"));
		tbl3.addCell(getPdfCell("930.00", ctNormalFont, 0, ""));
		
		PdfPCell tb3c1 = getBottomAlignedPdfCell("Totals:", boldFont, 0, "");
		tb3c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		tbl3.addCell(tb3c1);
		PdfPCell tb3c2 = getBottomAlignedPdfCell("0.00", ctNormalFont, 0, "grey");
		tbl3.addCell(tb3c2);
		PdfPCell tb3c3 = getBottomAlignedPdfCell("0.00", ctNormalFont, 0, "");
		tbl3.addCell(tb3c3);
		PdfPCell tb4c3 = getBottomAlignedPdfCell("0.00", ctNormalFont, 0, "grey");
		tbl3.addCell(tb4c3);
		PdfPCell tb5c3 = getBottomAlignedPdfCell("0.00", ctNormalFont, 0, "");
		tbl3.addCell(tb5c3);
		
		childTable3.addCell(tbl1);
		childTable3.addCell(tbl2);
		childTable3.addCell(tbl3);
		
		PdfPCell mCell2 = new PdfPCell();
		mCell2.addElement(childTable3);
		mCell2.setBorderWidth(1f);
		maintable.addCell(mCell2);
		
		document.add(maintable);
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
	
}
