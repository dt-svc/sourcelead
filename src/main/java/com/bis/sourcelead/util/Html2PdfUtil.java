package com.bis.sourcelead.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class Html2PdfUtil {
	
	public void convertHtml2PdfUsingHtmlWorker(){
		try {
			//TODO replace the string with actual HTML content.
			String k = "<html><body> This is from iText PDF HTML Worker </body></html>";
			OutputStream file = new FileOutputStream(new File("C:\\SourceLead\\Html2PdfUsingHtmlWorker.pdf"));
			Document document = new Document();
			PdfWriter.getInstance(document, file);
			document.open();
			//TODO use the latest XMLWorkerHelper class in below method instead of deprectaed HTMLWorker class
			HTMLWorker htmlWorker = new HTMLWorker(document);
			htmlWorker.parse(new StringReader(k));
			document.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void convertHtml2PdfUsingXMLWorker(){
		try {
			//TODO replace the string with actual HTML content.
		    String k = "<html><body> This is from iText PDF XML Worker   </body></html>";
		    OutputStream file = new FileOutputStream(new File("C:\\SourceLead\\Html2PdfUsingXMLWorker.pdf"));
			Document document = new Document();
		    PdfWriter writer = PdfWriter.getInstance(document, file);
		    document.open();
		    InputStream is = new ByteArrayInputStream(k.getBytes());
		    XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
		    document.close();
		    file.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

}
