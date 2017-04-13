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
			String k = "<h5><b>Business Intelli Service aggrement</b></h5><br>THIS PROFESSIONAL SERVICES AGREEMENT (this 'Agreement') is "
					+ " Contractor which may render services to BIS or BIS's clients in the future. Contractor shall<br><div style='text-align: center;'><span style='font-weight: bold;'>Business Intelli Solutions Inc.</span><br>--------------------------------------------------------------------------------------------------------------------------------------------<br></div><div style='text-align: center;'><span style='color: rgb(61, 133, 198);'>3535 Victory Group Way, Building 4, Frisco, TX 75034.<br>PH: 214 295 5535 FAX: 972 559 3641<br>Email: info@businessintelli.com, www.businessintelli.com</span></div>";
        	    OutputStream file = new FileOutputStream(new File("C:\\Users\\BIS94\\Desktop\\Itext\\TestSample.pdf"));
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