package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.model.Coordinates;
import com.bis.sourcelead.model.CoordinatesInfo;
import com.bis.sourcelead.model.SignedDocumentInfo;
import com.bis.sourcelead.util.DocuSignUtil;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

@Service
public class SignDocumentService {

	@Autowired 
	private DocuSignUtil docuSignUtil;
	
	
    
	private Image getSignedImage(PdfDocument pdfDoc, Image img, String watermark, Coordinates coordinate, PdfFont font) {
		float x = coordinate.getX();
		float y = coordinate.getY();
		int pageNumber = coordinate.getPageNumber();
		
		float width = img.getImageScaledWidth();
		float height = img.getImageScaledHeight();
		float negY = y*-1;
		PdfFormXObject template = new PdfFormXObject(new Rectangle(x, negY, width, height));
		
		float pageheight = pdfDoc.getFirstPage().getPageSize().getHeight();

		PdfCanvas pdfCanvas = new PdfCanvas(pdfDoc,pageNumber);
		new Canvas(pdfCanvas, pdfDoc,new Rectangle(x, pageheight-y-(height)+(height/10), width, height)).
		add(img).setFontColor(DeviceGray.BLACK).setFontSize((float) 12.0).setFont(font).
		showTextAligned(watermark, x+(width/2), pageheight-y-(height)+(height/10)  + (height/2),
		TextAlignment.RIGHT, VerticalAlignment.MIDDLE, (float) 0.0);
		
		return new Image(template);
	}
/**
 * 
 * @param signedDocumentInfo
 * @param contextPath
 * This method is used to sign and attach the signature to the document.
 */
	public void createSignatureAndAddToPdf(SignedDocumentInfo signedDocumentInfo, String contextPath, String userPath)  {
		PdfDocument pdfDoc;
		try {
			String dirPath = userPath+ signedDocumentInfo.getCoordinatesInfo().getUserId()+"/"+ signedDocumentInfo.getCoordinatesInfo().getDocId();
			pdfDoc = new PdfDocument(new PdfReader(dirPath+"/unsigned.pdf"), new PdfWriter(dirPath+"/signed1.pdf"));
			Document doc = new Document(pdfDoc);

			Image imageSignature = new Image(ImageDataFactory.create(contextPath +docuSignUtil.getimageLocation()[0]));
			imageSignature.scaleAbsolute(signedDocumentInfo.getCoordinatesInfo().getSignatureWidth(),
					signedDocumentInfo.getCoordinatesInfo().getSignatureHeight());
			
			Image imageInitials = new Image(ImageDataFactory.create(contextPath +docuSignUtil.getimageLocation()[1]));
			imageInitials.scaleAbsolute(signedDocumentInfo.getCoordinatesInfo().getInitialsWidth(),
					signedDocumentInfo.getCoordinatesInfo().getInitialsHeight());
	        PdfFont font = PdfFontFactory.createFont(contextPath+docuSignUtil.FONT_DIR+docuSignUtil.getFont(signedDocumentInfo.getFontId()), PdfEncodings.CP1250, true);
	       
			for(Coordinates coordinate:signedDocumentInfo.getCoordinatesInfo().getSignatures())
				doc.add(getSignedImage(pdfDoc, imageSignature, signedDocumentInfo.getSignatureText(), 
						coordinate, font));
			for(Coordinates coordinate:signedDocumentInfo.getCoordinatesInfo().getInitials())
				doc.add(getSignedImage(pdfDoc, imageInitials, signedDocumentInfo.getInitialsText() , 
						coordinate, font));
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	/*
	 * @param signedDocumentInfo
	 * @param contextPath
	 * This method is  attach already signed Image  to the document.
	 */
		public String addSignaturetoPdf(CoordinatesInfo coordinatesInfo, String userPath)  {
			PdfDocument pdfDoc;
			String dirPath =null;
			try {
				dirPath = userPath+ coordinatesInfo.getUserId();
				if(dirPath.contains("/unsigned.pdf")) {
					dirPath = dirPath.replace("/unsigned.pdf", "");
				}
				
				String signDirPath = dirPath+"/signatures";
				pdfDoc = new PdfDocument(new PdfReader(dirPath+"/unsigned.pdf"),new PdfWriter(dirPath+"/signed.pdf"));
				Document doc = new Document(pdfDoc);
				
				String signatureImagePath = signDirPath + "/"+ (coordinatesInfo.getFontId() == 0 ?docuSignUtil.SIGNATURE_IMAGE_NAME_CUSTOM : docuSignUtil.SIGNATURE_IMAGE_NAME+".jpeg");
				String initialsImagePath = signDirPath + "/"+  (coordinatesInfo.getFontId() == 0 ?docuSignUtil.INITIALS_IMAGE_NAME_CUSTOM : docuSignUtil.INITIALS_IMAGE_NAME+".jpeg");
				
				boolean signatureFieExists = new File(signatureImagePath).exists() ;
				boolean initialsFieExists = new File(initialsImagePath).exists() ;
				if(signatureFieExists) {
					Image signatureImage = new Image(ImageDataFactory.create(signatureImagePath));
					signatureImage.scaleAbsolute(coordinatesInfo.getSignatureWidth(),
							coordinatesInfo.getSignatureHeight());
					for(Coordinates coordinate : coordinatesInfo.getSignatures())
						doc.add(getSignedImage(pdfDoc, signatureImage, coordinate));
				}
				if(initialsFieExists) {
					Image initialsImage = new Image(ImageDataFactory.create(initialsImagePath));
					initialsImage.scaleAbsolute(coordinatesInfo.getInitialsWidth(),
							coordinatesInfo.getInitialsHeight());
					for(Coordinates coordinate : coordinatesInfo.getInitials())
						doc.add(getSignedImage(pdfDoc, initialsImage,coordinate));
				}
				
				doc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return dirPath+"/signed.pdf";
		}
		

	private Image getSignedImage(PdfDocument pdfDoc, Image img, Coordinates coordinate) {
		float x = coordinate.getX();
		float y = coordinate.getY();
		int pageNumber = coordinate.getPageNumber();
		if(pageNumber == -1) {
			pageNumber =+1;
		}
		float pageheight = pdfDoc.getFirstPage().getPageSize().getHeight();
		
		float width = img.getImageScaledWidth();
		float height = img.getImageScaledHeight();
		float negY = y*-1;
		PdfFormXObject template = new PdfFormXObject(new Rectangle(x, negY, width, height));
		PdfCanvas pdfCanvas = new PdfCanvas(pdfDoc,pageNumber);
		new Canvas(pdfCanvas, pdfDoc,new Rectangle(x, pageheight-y -(height)+(height/10), width, height)).
		add(img);
		
		return new Image(template);
	}
	
}
