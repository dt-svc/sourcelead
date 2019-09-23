package com.bis.sourcelead.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;


@Component
public class DocuSignUtil {

	private Map<Integer, String> fontFileMap = null;
	public final String userDirPath = "";
	private String contextPath = null; 

	
	private String[] imageLocation ;
	
	public final String FONT_DIR = "/resources/fonts/";
	
	public final String SIGNATURE_IMAGE_ID = "signature";
	public final String INITIALS_IMAGE_ID = "initials";
	
	public final String SIGNATURE_IMAGE_NAME = "signedImage";
	public final String INITIALS_IMAGE_NAME = "initialsImage";
	
	public final String SIGNATURE_IMAGE_NAME_CUSTOM = "signedImage_Custom.jpeg";
	public final String INITIALS_IMAGE_NAME_CUSTOM = "initialImage_Custom.jpeg";
	
	public DocuSignUtil() {
		fontFileMap = new HashMap<Integer, String>();
		imageLocation = new String[2];
	}

	@PostConstruct
	public void initializeFontFileMap() {
		fontFileMap.put(1, "Arizonia-Regular.ttf");
		fontFileMap.put(2, "GreatVibes-Regular.ttf");
		fontFileMap.put(3, "MrsSaintDelafield-Regular.ttf");
		fontFileMap.put(4, "Pacifico-Regular.ttf");
		
		imageLocation[0] = "/resources/images/signature.png";
		imageLocation[1] = "/resources/images/initial.png";
	}
	
	public int getFontFileMapSize() {
		return fontFileMap.size();
	}
	public String[] getimageLocation() {
		return imageLocation;
	}

	public String getFont(Integer fontId) {
		return fontFileMap.get(fontId);
	}

	public String getRealPath(String path) {
		return path + "../";
	}
	public String getRealPath(HttpServletRequest req) {
		if (contextPath == null) {
			String path = req.getRealPath("/");
			return path ;
		}
		return contextPath;
	}

	public String getUserPath(HttpServletRequest req) {
		ServletContext contextPath = req.getServletContext().getContext("/"+userDirPath);
		String path = contextPath.getRealPath("/");
		return path;
			 
		
	}
	
	public String getFolderName() {
		long currTimeMS = System.currentTimeMillis();
		return String.valueOf(currTimeMS) + "user";
	}

	public String createDir(String staticPath, HttpServletRequest req, String fileName,String orLocId,String orgId, boolean appendTimeStamp) {
		
		String url = staticPath+ "/" + orgId + "/" + orLocId + "/" + Constants.Contract;
		fileName = "/" + orgId + "/" + orLocId + "/temp";
		File folderFile = new File(staticPath, "/" + orgId + "/" + orLocId + "/" + Constants.Contract);
		if (!folderFile.exists())
			folderFile.mkdirs();
		folderFile = new File(staticPath, "/" + orgId + "/" + orLocId + "/temp");
		if (!folderFile.exists())
			folderFile.mkdirs();

		try {

			return url;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return "";
		
	}

	public String[] getDirectories(File[] paths) {
		// TODO Auto-generated method stub
		String[] directories = new String[paths.length];
		for( int i = 0 ; i<  paths.length ;i++) {
			String pathStr = paths[i].toString();
			pathStr = pathStr.substring(pathStr.lastIndexOf("/")+1, pathStr.length());
			if (!pathStr.equals("signatures"))
			    directories[i] = pathStr;
		}
		return directories;
	}

}
