package com.bis.sourcelead.payroll.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.PdfHeaderDTO;
import com.bis.sourcelead.util.CommonUtil;
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

@Component
public class PayrollReportPdfUtil {
	
	Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
	
	public static final String DEST = "/home/rajasekhar/Desktop/PDF/simple_table.pdf";
	
	@Autowired
	CommonUtil commonUtil;

	public File getCheckRegisterReportPdf(List<CheckRegisterReportDTO> checkRegData, OrgLocations orgLocation)
	{
		try {
			if(checkRegData != null && checkRegData.size() >0) {
				Document document = new Document(PageSize.A4.rotate());
		    	PdfWriter.getInstance(document, new FileOutputStream(DEST));
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
		    	headerDTO.setReportType("CHECK REGISTER");
		    	headerDTO.setRunDate(registerDTO.getRunDate());
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
		    	
		    	empDataTable.addCell(getPdfCell("Total Number of Checks: 1",normalFont,5));
		    	empDataTable.addCell(getPdfCell("2",normalFont,0));
		    	empDataTable.addCell(getPdfCell("Kakula Varam, Kunila",normalFont,0));
		    	empDataTable.addCell(getPdfCell("4/5/2018",normalFont,0));
		    	empDataTable.addCell(getPdfCell("",normalFont,0));
		    	empDataTable.addCell(getPdfCell("15221458",normalFont,0));
		    	empDataTable.addCell(getPdfCell("$ 930.00",normalFont,0));
		    	empDataTable.setSpacingAfter(8);
		    	document.add(empDataTable);
		    	
		    	PdfPTable lineTable = getPdfTable(1,document);
		    	
		    	
		    	PdfPTable lineChildTable = new PdfPTable(4);
		    	lineChildTable.addCell(getPdfCell("",boldFont,0));
		    	lineChildTable.addCell(getPdfCell("",boldFont,0));
		    	lineChildTable.addCell(getPdfCell("Total Number of Checks: 1",boldFont,0));
		    	lineChildTable.addCell(getPdfCell("930.80",boldFont,0));
		    	
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
		    	
		    	PdfPTable checksChildTable = new PdfPTable(4);
		    	
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("Total Employee Live Checks: 1",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("930.83",boldFont,0));
		    	
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("Total Manual Checks: 0",boldFont,0));
		    	checksChildTable.addCell(getPdfCell("0.00",boldFont,0));
		    	
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
		
		return null;
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
			Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
//			cell0.addElement(new Paragraph("Client ID: OVATYW56 - MI Softech Inc", boldFont));
			cell0.addElement(new Paragraph("Pay Group: "+headerData.getGroupName(), boldFont));
			cell0.addElement(new Paragraph("Check Date: "+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), headerData.getCheckDate()), boldFont));
			cell0.addElement(new Paragraph("Run Date: "+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), headerData.getRunDate()), boldFont));
			cell0.addElement(new Paragraph(" Run Number : "+headerData.getRunNumber(), boldFont));
			cell0.setBorder(Rectangle.NO_BORDER);
			cell0.setPaddingLeft(20f);
			cell0.setColspan(5);
			table.addCell(cell0);
			PdfPCell cell2 = new PdfPCell();
			Font greyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
			Chunk chunk = new Chunk(headerData.getReportType(), greyFont);
			chunk.setBackground(new BaseColor(220, 220, 220));
			cell2.addElement(new Paragraph(chunk));
			cell2.addElement(new Paragraph(headerData.getOrgName(), boldFont));
//			cell2.addElement(new Paragraph("DBA: MI Softech Inc", boldFont));
			cell2.addElement(new Paragraph(""));
			cell2.setBorder(Rectangle.NO_BORDER);
			cell2.setColspan(6);
			table.addCell(cell2);
			PdfPCell cell3 = new PdfPCell();
			cell3.addElement(new Paragraph("Period Begin Date: "+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), headerData.getPeriodBeginDate()), boldFont));
			cell3.addElement(new Paragraph("Period End Date: "+commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), headerData.getPeriodEndDate()), boldFont));
			cell3.addElement(new Paragraph("Pay Period: "+headerData.getPayPeriod(), boldFont));
			cell3.addElement(new Paragraph("Payroll Type: "+headerData.getPayrollType(), boldFont));
			cell3.setBorder(Rectangle.NO_BORDER);
			cell3.setColspan(4);
			table.addCell(cell3);
			PdfPCell mCell0 = new PdfPCell();
			mCell0.addElement(table);
			mCell0.setBorderWidth(1);
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
}
