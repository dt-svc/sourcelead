package com.bis.sourcelead.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.ImageIOUtil;

public class PdfToImageThread implements Runnable {
	private List<PDPage> pdPages = null;
	private String pdfFilename;
	private PDDocument document;
	

	public PdfToImageThread(List<PDPage> pdPages, String pdfFilename, PDDocument document) {
		super();
		this.pdPages = pdPages;
		this.pdfFilename = pdfFilename;
		this.document = document;
	}

	@Override
	public void run() {
		int pageCount = 0;
		for (PDPage pdPage : pdPages) {
			++pageCount;
			BufferedImage bim;
			try {
				bim = pdPage.convertToImage(BufferedImage.TYPE_INT_RGB, 300);
				ImageIOUtil.writeImage(bim, pdfFilename + "-" + pageCount + ".png", 300);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
