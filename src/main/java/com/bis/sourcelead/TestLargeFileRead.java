package com.bis.sourcelead;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;



import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
public class TestLargeFileRead {
	  /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        POIFSFileSystem fs = null;  
        Document document = new Document();

         try {  
             System.out.println("Starting the test");  
             fs = new POIFSFileSystem(new FileInputStream("/home/rajasekhar/Desktop/Resumes/doc1.doc"));  

             HWPFDocument doc = new HWPFDocument(fs);  
             WordExtractor we = new WordExtractor(doc);  

             OutputStream file = new FileOutputStream(new File("/home/rajasekhar/Desktop/Resumes/PDF/prasanna.pdf")); 

             PdfWriter writer = PdfWriter.getInstance(document, file);  

             Range range = doc.getRange();
             document.open();  
             writer.setPageEmpty(true);  
             document.newPage();  
             writer.setPageEmpty(true);  

             String[] paragraphs = we.getParagraphText();  
             for (int i = 0; i < paragraphs.length; i++) {  

                 org.apache.poi.hwpf.usermodel.Paragraph pr = range.getParagraph(i);
                 paragraphs[i] = paragraphs[i].replaceAll("\\cM?\r?\n", "");  
             System.out.println("Paragraph" + i + ": " + paragraphs[i].toString());  

             // add the paragraph to the document  
             document.add(new Paragraph(paragraphs[i]));  
             }  

             System.out.println("Document testing completed");  
         } catch (Exception e) {  
             System.out.println("Exception during test");  
             e.printStackTrace();  
         } finally {  
                         // close the document  
            document.close();  
                     }  
         }  
}
