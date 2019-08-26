package com.bis.sourcelead.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.sun.star.beans.PropertyValue;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.util.XCloseable;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import ooo.connector.BootstrapSocketConnector;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

@Component
public class PdfConversionUtil {
	
	@Value("${oooExecutableFolder}")
	private String oooExecutableFolder;
	
	public byte[] convertDocToPdf(byte[] docFile)
	{
	        // Create OOoOutputStream
	        OOoOutputStream outputStream = new OOoOutputStream();
	        try {
	            // Connect to OOo server
	            XComponentContext xComponentContext = BootstrapSocketConnector.bootstrap(oooExecutableFolder);
	            // Create OOoInputStream
	            InputStream inputFile = new ByteArrayInputStream(docFile);
	            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	            byte[] byteBuffer = new byte[4096];
	            int byteBufferLength = 0;
	            while ((byteBufferLength = inputFile.read(byteBuffer)) > 0) {
	                    bytes.write(byteBuffer,0,byteBufferLength);
	            }
	            inputFile.close();
	            OOoInputStream inputStream = new OOoInputStream(bytes.toByteArray());
	            // Convert document to PDF
	            convert(inputStream, outputStream, "writer_pdf_Export",xComponentContext);
	        } catch (com.sun.star.comp.helper.BootstrapException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			return outputStream.toByteArray();	
	}
	
	 public void convert(OOoInputStream input, OOoOutputStream output, String filterName,XComponentContext xComponentContext)  {
		 
	        XMultiComponentFactory xMultiComponentFactory = xComponentContext.getServiceManager();
	        Object desktopService;
	        
			try {
				desktopService = xMultiComponentFactory.createInstanceWithContext("com.sun.star.frame.Desktop", xComponentContext);
			
		        XComponentLoader xComponentLoader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class, desktopService);
		       
		        PropertyValue[] conversionProperties = new PropertyValue[2];
		        conversionProperties[0] = new PropertyValue();
		        conversionProperties[1] = new PropertyValue();
	
		        conversionProperties[0].Name = "InputStream";
		        conversionProperties[0].Value = input;
		        conversionProperties[1].Name = "Hidden";
		        conversionProperties[1].Value = new Boolean(true);
	
		        XComponent document = xComponentLoader.loadComponentFromURL("private:stream", "_blank", 0, conversionProperties);
	
		        conversionProperties[0].Name = "OutputStream";
		        conversionProperties[0].Value = output;
		        conversionProperties[1].Name = "FilterName";
		        conversionProperties[1].Value = filterName;
	
		        XStorable xstorable = (XStorable) UnoRuntime.queryInterface(XStorable.class,document);
		        xstorable.storeToURL("private:stream", conversionProperties);
	
		        XCloseable xclosable = (XCloseable) UnoRuntime.queryInterface(XCloseable.class,document);
		        xclosable.close(true);
			} catch (com.sun.star.uno.Exception e) {
				e.printStackTrace();
			}
	    }

	 /**
	  * Convert Doc File to Pdf Bytes
	  * @param docFile
	  * @return byte[]
	  */
	public byte[] convertDocFileToPdf(File docFile)
	{
		
		  POIFSFileSystem fs = null;  
	        Document pdfDoc = new Document();

	         try {  
	             fs = new POIFSFileSystem(docFile);  

	             HWPFDocument doc = new HWPFDocument(fs);  
	             WordExtractor we = new WordExtractor(doc);  

	             ByteArrayOutputStream file = new ByteArrayOutputStream();

	             PdfWriter writer = PdfWriter.getInstance(pdfDoc, file);  

	             Range range = doc.getRange();
	             pdfDoc.open();  
	             writer.setPageEmpty(true);  
	             pdfDoc.newPage();  
	             writer.setPageEmpty(true);  

	             String[] paragraphs = we.getParagraphText();  
	             for (int i = 0; i < paragraphs.length; i++) {  
	                 org.apache.poi.hwpf.usermodel.Paragraph pr = range.getParagraph(i);
	                 paragraphs[i] = paragraphs[i].replaceAll("\\cM?\r?\n", "");  
	                pdfDoc.add(new Paragraph(paragraphs[i]));  
	             }  
	             pdfDoc.close();
	             return  file.toByteArray();
	         } catch (Exception e) {  
	             e.printStackTrace();  
	         } 
		
		return null;
	}

	/**
	 * convert Docx file to Pdf byte
	 * @param file
	 * @return byte[]
	 */
	public byte[] convertDocxFileToPdf(File file)
	{
		try {
			InputStream doc = new FileInputStream(file);
			XWPFDocument document = new XWPFDocument(doc);
			PdfOptions options = PdfOptions.create();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PdfConverter.getInstance().convert(document, out, options);
			document.close();
			return out.toByteArray();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
}
