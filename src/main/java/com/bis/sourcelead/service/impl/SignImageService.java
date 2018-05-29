package com.bis.sourcelead.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.model.SignedImageInfo;
import com.bis.sourcelead.util.DocuSignUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SignImageService {
	
	private static final Logger logger = LoggerFactory.getLogger(SignImageService.class);
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private DocuSignUtil docuSignUtil;

	public void signImage(SignedImageInfo signedImageInfo, String contextPath) throws JsonGenerationException, JsonMappingException, IOException, FontFormatException {
		String[] imageLocation =  docuSignUtil.getimageLocation();
		String dirPath = null;
		if(signedImageInfo.getUserId().contains("/unsigned.pdf")) {
		 dirPath = signedImageInfo.getUserId().replace("/unsigned.pdf", "");
		}
		String result = staticPath.substring(0,staticPath.lastIndexOf("/"));
		String filePath = result + dirPath+ "/signatures";
		logger.info("Signature folder path : "+filePath);
		new File(filePath).mkdir();
		for (int i = 0; i < imageLocation.length; i++) {
			BufferedImage image = null;

			Font font = null;
			try {
				String imageType = imageLocation[i];
				
				image = ImageIO.read(new File(contextPath + imageType));

				font = Font.createFont(Font.TRUETYPE_FONT,
						new FileInputStream(contextPath + docuSignUtil.FONT_DIR + docuSignUtil.getFont(signedImageInfo.getFontId()))); //commonUtils.getFont(i)
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String text =  i == 0 ? signedImageInfo.getSignatureText(): signedImageInfo.getInitialText();
			int charCount = text.length();

			int width = (charCount * 14) + 110; // image.getWidth()
			int imgWidth = image.getWidth();									// ;(char_count*13.5 + 100+ 10)
			int height = image.getHeight();
			int extraSpace = (int) ((charCount * 0.5) + 110);

			BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = newImage.createGraphics();
			try {
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				int alpha = 255; // 50% transparent
				Color myColour = new Color(255, 255, 255, alpha);
				g.setColor(myColour);
				g.fillRect(0, 0, width, height);
				g.drawImage(image, 0, 0, imgWidth, height, null);
				g.setFont(font.deriveFont(40f));
				g.setColor(Color.BLACK);
				g.drawString(text, extraSpace / 2, (height/2)+15);

				g.dispose();
				
				String imageName = i == 0
							? docuSignUtil.SIGNATURE_IMAGE_NAME : docuSignUtil.INITIALS_IMAGE_NAME;
               
				ImageIO.write(newImage, "png",
						new File(filePath+ "/"+imageName+".jpeg"));

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				g.dispose();
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(new File(result + dirPath+ "/signatureInfo.json"),
				signedImageInfo);
	}
}
