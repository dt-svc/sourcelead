package com.bis.sourcelead;

 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.collection.PdfTargetDictionary;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {
    public static final String DEST = "/home/rajasekhar/Desktop/PDF/simple_table.pdf";
 
    Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
	
	
    public static void main(String[] args) throws IOException,
            DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Test().createPdf(DEST);
    }
    public void createPdf(String dest) throws IOException, DocumentException {
    	Document document = new Document(PageSize.A4.rotate());
    	PdfWriter.getInstance(document, new FileOutputStream(dest));
    	document.open();
    	PdfPTable maintable = new PdfPTable(1);
    	maintable.setTotalWidth(document.getPageSize().getWidth() - 80);
    	maintable.setLockedWidth(true);

    	PdfPTable table = new PdfPTable(3);
    	table.setTotalWidth(document.getPageSize().getWidth() - 80);
    	table.setLockedWidth(true);

    	StringBuilder sb = new StringBuilder();
    	sb.append("Client Id: OVATYW56 - MI Softech Inc \n");
    	sb.append("Pay Group: Monthly\n");
    	sb.append("Check Date: 2018-01-01\n");
    	sb.append("Run Date: 2018-01-01       Run Number: 10");
    	PdfPCell cell0 = new PdfPCell();
    	cell0.addElement(new Paragraph(sb.toString()));
//    	cell0.addElement(new Paragraph("Emp #: 25"));
    	cell0.setBorder(Rectangle.NO_BORDER);
    	cell0.setBackgroundColor(BaseColor.WHITE);
//    	cell0.setColspan(5);
    	table.addCell(cell0);
    	PdfPCell cell1 = new PdfPCell();
    	cell1.addElement(new Paragraph(""));
    	cell1.addElement(new Paragraph("XXX-XX-5607"));
    	cell1.setBorder(Rectangle.NO_BORDER);
    	cell1.setBackgroundColor(BaseColor.WHITE);
//    	cell1.setColspan(5);
    	table.addCell(cell1);
    	PdfPCell cell2 = new PdfPCell();
    	cell2.addElement(new Paragraph("2000 Walnut Ave FREMONT, CA 94538"));
    	cell2.addElement(new Paragraph(""));
    	cell2.setBorder(Rectangle.NO_BORDER);
    	cell2.setBackgroundColor(BaseColor.WHITE);
//    	cell2.setColspan(5);
    	table.addCell(cell2);

    	PdfPCell mCell1 = new PdfPCell();
    	mCell1.setBorderWidth(4f);
    	mCell1.addElement(table);
    	maintable.addCell(mCell1);
    	document.add(maintable);
    	document.add(Chunk.NEWLINE);
    	document.add(Chunk.NEWLINE);
    	document.add(Chunk.NEWLINE);
    	
    	
    	
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
//    	empTable.addCell(new Phrase("Cleared", boldFont));
    	document.add(empTable);
    	
    	
    	PdfPTable empDataTable = new PdfPTable(7);
    	empDataTable.setTotalWidth(document.getPageSize().getWidth() - 80);
    	empDataTable.setLockedWidth(true);
    	empDataTable.setSpacingBefore(2);  
    	
    	PdfPCell dataCell = new PdfPCell(new Phrase("100", normalFont));
    	dataCell.setBorder(Rectangle.NO_BORDER);
    	dataCell.setFixedHeight(5);
    	empDataTable.addCell(dataCell);
    	
    	PdfPCell empIdCell = new PdfPCell(new Phrase("2", normalFont));
    	empIdCell.setBorder(Rectangle.NO_BORDER);
    	empDataTable.addCell(empIdCell);
    	
    	PdfPCell empNameCell = new PdfPCell(new Phrase("Kakula Varam, Kunila", normalFont));
    	empNameCell.setBorder(Rectangle.NO_BORDER);
    	empDataTable.addCell(empNameCell);
    	
    	PdfPCell empDateCell = new PdfPCell(new Phrase("4/5/2018", normalFont));
    	empDateCell.setBorder(Rectangle.NO_BORDER);
    	empDataTable.addCell(empDateCell);
    	
    	PdfPCell empEmptyCell = new PdfPCell(new Phrase("", normalFont));
    	empEmptyCell.setBorder(Rectangle.NO_BORDER);
    	empDataTable.addCell(empEmptyCell);
    	
    	PdfPCell empCheckCell = new PdfPCell(new Phrase("15221458", normalFont));
    	empCheckCell.setBorder(Rectangle.NO_BORDER);
    	empDataTable.addCell(empCheckCell);
    	
    	PdfPCell empAmountCell = new PdfPCell(new Phrase("$ 930.00", normalFont));
    	empAmountCell.setBorder(Rectangle.NO_BORDER);
    	empDataTable.addCell(empAmountCell);
    	
//     	PdfPCell empBoxCell = new PdfPCell(new Paragraph(Rectangle.BOX));
//     	empBoxCell.setBorder(Rectangle.NO_BORDER);
//    	empDataTable.addCell(empBoxCell);
    	empDataTable.setSpacingAfter(8);
    	document.add(empDataTable);
    	
    	
    	PdfPTable lineTable = new PdfPTable(1);
    	lineTable.setTotalWidth(document.getPageSize().getWidth() - 80);
    	lineTable.setLockedWidth(true);
    	
    	PdfPTable lineChildTable = new PdfPTable(4);
//    	lineChildTable.setTotalWidth(document.getPageSize().getWidth() - 80);
//    	lineChildTable.setLockedWidth(true);
    	
    	PdfPCell leneCell1 = new PdfPCell(new Paragraph(""));
    	leneCell1.setBorder(Rectangle.NO_BORDER);
    	lineChildTable.addCell(leneCell1);
    	
    	PdfPCell leneCell2 = new PdfPCell(new Paragraph(""));
    	leneCell2.setBorder(Rectangle.NO_BORDER);
//    	leneCell2.setBorder(Rectangle.BOTTOM);
    	lineChildTable.addCell(leneCell2);
    	
    	PdfPCell leneCell3 = new PdfPCell(new Paragraph("Total Number of Checks: 1",boldFont));
    	leneCell3.setBorder(Rectangle.NO_BORDER);
//    	leneCell3.setBorder(Rectangle.BOTTOM);
    	lineChildTable.addCell(leneCell3);
    	
    	PdfPCell leneCell4 = new PdfPCell(new Paragraph("930.80",boldFont));
    	leneCell4.setBorder(Rectangle.NO_BORDER);
//    	leneCell4.setBorder(Rectangle.BOTTOM);
    	lineChildTable.addCell(leneCell4);
//    	LineSeparator ls = new LineSeparator();
//    	ls.setLineWidth(2f);
//    	document.add(new Chunk(ls));
//    	Chunk chunk = new Chunk("Total Number of Checks:", boldFont);
//    	chunk.setBackground(BaseColor.LIGHT_GRAY);
//    	Paragraph p = new Paragraph(chunk);
//    	document.add(p);
    	
    	PdfPCell p = new PdfPCell(lineChildTable);
    	p.setFixedHeight(30);
    	p.setBorderWidth(1f);
    	p.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
    	lineTable.addCell(p);
    	document.add(lineTable);
    	
    	document.close();
        System.out.println("Done.!!");
    }
 
}