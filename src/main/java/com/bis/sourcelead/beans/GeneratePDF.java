package com.bis.sourcelead.beans;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

public class GeneratePDF {
    public static void main(String[] args) {
        try {
             String name = "Rajashekar";
			String k = "<html><body><h1 style='color:blue;'>This is a Blue Heading</h1><p><dfn id='html-def'>HTML</dfn> is the standard markup language for creating web pages.</p><p style='background-color:#FF0000;'>This is Rajashekar...</p><p>This is some text...</p><p>This is some text...</p><p>Learn <a href='#html-def'>HTML</a> now.</p></body></html>";
        	    OutputStream file = new FileOutputStream(new File("C:\\Users\\BIS94\\Desktop\\Itext\\Test.pdf"));
        	    Document document = new Document();
        	    PdfWriter.getInstance(document, file);
        	    document.open();
        	    HTMLWorker htmlWorker = new HTMLWorker(document);
        	    htmlWorker.parse(new StringReader(k));
        	    document.close();
        	    file.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}