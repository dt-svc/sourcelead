package com.bis.sourcelead;

 
import com.itextpdf.text.BaseColor;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {
    public static final String DEST = "/home/rajasekhar/Desktop/PDF/simple_table.pdf";
 
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

    	PdfPTable table = new PdfPTable(50);
    	table.setTotalWidth(document.getPageSize().getWidth() - 80);
    	table.setLockedWidth(true);

    	PdfPCell cell0 = new PdfPCell();
    	cell0.addElement(new Paragraph("Aerra, Rahul"));
    	cell0.addElement(new Paragraph("Emp #: 25"));
    	cell0.setBorder(Rectangle.NO_BORDER);
    	cell0.setBackgroundColor(new BaseColor(255,255,45));
    	cell0.setColspan(5);
    	table.addCell(cell0);
    	PdfPCell cell1 = new PdfPCell();
    	cell1.addElement(new Paragraph(""));
    	cell1.addElement(new Paragraph("XXX-XX-5607"));
    	cell1.setBorder(Rectangle.NO_BORDER);
    	cell1.setBackgroundColor(new BaseColor(255,255,45));
    	cell1.setColspan(5);
    	table.addCell(cell1);
    	PdfPCell cell2 = new PdfPCell();
    	cell2.addElement(new Paragraph("2000 Walnut Ave FREMONT, CA 94538"));
    	cell2.addElement(new Paragraph(""));
    	cell2.setBorder(Rectangle.NO_BORDER);
    	cell2.setBackgroundColor(new BaseColor(255,255,45));
    	cell2.setColspan(5);
    	table.addCell(cell2);
    	PdfPCell cell3 = new PdfPCell();
    	cell3.addElement(new Paragraph("Fed Marital:"));
    	cell3.addElement(new Paragraph("State Marital:"));
    	cell3.setBorder(Rectangle.NO_BORDER);
    	cell3.setBackgroundColor(new BaseColor(255,255,45));
    	cell3.setColspan(10);
    	table.addCell(cell3);
    	PdfPCell cell4 = new PdfPCell();
    	cell4.addElement(new Paragraph("Exempts: 0"));
    	cell4.addElement(new Paragraph("Exempts: 0"));
    	cell4.setBorder(Rectangle.NO_BORDER);
    	cell4.setBackgroundColor(new BaseColor(255,255,45));
    	cell4.setColspan(5);
    	table.addCell(cell4);
    	PdfPCell cell5 = new PdfPCell();
    	cell5.addElement(new Paragraph("Addl Tax:"));
    	cell5.addElement(new Paragraph("Addl Tax:"));
    	cell5.setBorder(Rectangle.NO_BORDER);
    	cell5.setBackgroundColor(new BaseColor(255,255,45));
    	cell5.setColspan(5);
    	table.addCell(cell5);
    	PdfPCell cell6 = new PdfPCell();
    	cell6.addElement(new Paragraph("Res State: CA"));
    	cell6.addElement(new Paragraph("Work State: CA"));
    	cell6.setBorder(Rectangle.NO_BORDER);
    	cell6.setBackgroundColor(new BaseColor(255,255,45));
    	cell6.setColspan(5);
    	table.addCell(cell6);
    	PdfPCell cell7 = new PdfPCell();
    	cell7.addElement(new Paragraph("Per Pay Salary: 0.00"));
    	cell7.addElement(new Paragraph("Status:	ACTIVE"));
    	cell7.setBorder(Rectangle.NO_BORDER);
    	cell7.setBackgroundColor(new BaseColor(255,255,45));
    	cell7.setColspan(5);
    	table.addCell(cell7);
    	PdfPCell cell8 = new PdfPCell();
    	cell8.addElement(new Paragraph("Hire Date: 12/1/2017"));
    	cell8.addElement(new Paragraph("Birth Date: 12/1/XXXX"));
    	cell8.setBorder(Rectangle.NO_BORDER);
    	cell8.setBackgroundColor(new BaseColor(255,255,45));
    	cell8.setColspan(5);
    	table.addCell(cell8);


    	PdfPCell mCell0 = new PdfPCell();
    	mCell0.addElement(table);
    	mCell0.setBorderWidth(2f);
    	maintable.addCell(mCell0);

    	PdfPCell mCell1 = new PdfPCell();
    	mCell1.addElement(table);
    	mCell1.setBorderWidth(2f);
    	maintable.addCell(mCell1);

    	document.add(maintable);
    	document.close();
         
         System.out.println("Done.!!");
    }
 
}