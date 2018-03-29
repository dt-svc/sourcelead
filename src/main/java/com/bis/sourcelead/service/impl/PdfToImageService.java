package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.stereotype.Service;

@Service
public class PdfToImageService {
	public int convertPdfToImage(String pdfFilename) {
		
		
		try {
			PDDocument document = PDDocument.loadNonSeq(new File(pdfFilename), null);
			List<PDPage> pdPages = document.getDocumentCatalog().getAllPages();
			PdfToImageThread pdfToImageThread = new PdfToImageThread(pdPages, pdfFilename,document);
			(new Thread(pdfToImageThread)).start();
			
			return pdPages.size();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return -1;
	}
}