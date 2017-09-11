package com.bis.sourcelead.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;


public class Reader
{
	private static boolean outputLog = false; 
	private static String readDOCX(InputStream fs)
	{
		String response ="";
		try {
			XWPFDocument doc = new XWPFDocument(OPCPackage.open(fs));
			XWPFWordExtractor we = new XWPFWordExtractor(doc);
			response = we.getText();
			//if(outputLog){
				System.out.println(response);
			//}
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
		 return response;
	}

	private static String readDOC(InputStream fs)
	{
		String response ="";
		try {
			  POIFSFileSystem poifs = null;
			  poifs = new POIFSFileSystem(fs);
			  HWPFDocument doc = new HWPFDocument(poifs);
			  WordExtractor we = new WordExtractor(doc);
			  String[] resp = we.getParagraphText();
			  response = String.join(",", resp);
			  response = URLEncoder.encode(response).replace("%07%2C", " ");
			  response = URLDecoder.decode(response);
			  response = response.replace(",", "");
			  //if(outputLog){
				System.out.println(response);
			  //}
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
		 return response;
	}

	private static String readRTF(InputStream fs)
	{
		String response ="";
		try {
			//FileInputStream stream = new FileInputStream(filePath);
			RTFEditorKit kit = new RTFEditorKit();
			Document doc = kit.createDefaultDocument();
			kit.read(fs, doc, 0);
			 
			response = doc.getText(0, doc.getLength());
			if(outputLog){
				System.out.println(response);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private static String readPDF(InputStream fs)
	{
		StringBuilder sb = new StringBuilder();
		try {
			//Create PdfReader instance.
			PdfReader pdfReader = new PdfReader(fs);	
			
			//Get the number of pages in pdf.
			int pages = pdfReader.getNumberOfPages(); 
			//Iterate the pdf through pages.
			for(int i=1; i<=pages; i++) {
				//Extract the page content using PdfTextExtractor.
				String pageContent = PdfTextExtractor.getTextFromPage(pdfReader, i);
				sb.append(pageContent);
			}
			
			//Close the PdfReader.
			pdfReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String response = sb.toString();
		if(outputLog){
			System.out.println(response);
		}
		return response;
	}

	private static String readTEXT(InputStream fs)
	{
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(fs));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		String response = sb.toString();
		if(outputLog){
			System.out.println(response);
		}
		return response;

	}
	
	public String readFileContent(String fileName){
		String parsedContent="";
		try (FileInputStream fs = new FileInputStream(fileName);) {
			if(fileName.endsWith("pdf")){
				parsedContent = readPDF(fs);
			}
			else if(fileName.endsWith("rtf")){
				parsedContent = readRTF(fs);
			}
			else if(fileName.endsWith("txt")){
				parsedContent = readTEXT(fs);
			}
			else if(fileName.endsWith("doc")){
				parsedContent = readDOC(fs);
			}
			else if(fileName.endsWith("docx")){
				parsedContent = readDOCX(fs);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	    
		return parsedContent;
	}
//	
//	public JSONObject parseResume(InputStream fs, String fileName){
//		JSONObject responseObj; 
//		String parsedContent = callParser(fs, fileName);
//		if(parsedContent.equals("")){
//			JSONObject jo = new JSONObject();
//			return jo;
//		}
//		HashMap<String, Object> parsedObj = pr.parseResume(parsedContent);
//	    responseObj = new JSONObject(parsedObj);
//		return responseObj;
//	}
//	
//	public JSONObject parseResume(InputStream fs, String fileName, String saveTo, String sourceLocation){
//		JSONObject thisRes; 
//		String parsedContent = callParser(fs, fileName);
//		if(parsedContent.equals("")){
//			JSONObject jo = new JSONObject();
//			return jo;
//		}
//		HashMap<String, Object> parsedObj = pr.parseResume(parsedContent);
//		thisRes = new JSONObject(parsedObj);
//	    JSONObject responseObj = new JSONObject();
//	    try {
//	    	responseObj.put("location", sourceLocation);
//	    	responseObj.put("resume", thisRes);
//		} catch (JSONException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		
//		if(!saveTo.equals("") && !parsedContent.equals("")){
//			try(  PrintWriter out = new PrintWriter(saveTo + fileName.replace('.', '_') + ".json")  ){
//			    out.println(responseObj.toString());
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		return responseObj;
//	}
}