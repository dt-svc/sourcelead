package com.bis.sourcelead.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.OrgStorageDetailDTO;

public class FileUploadUtil {
	 
	public String uploadFile(String staticPath, long orgId, long locationId, String fileType, MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();
		String extenstion = fileName.substring(fileName.lastIndexOf("."));
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		fileName = "/slead-static/"+orgId+"/"+locationId+"/"+fileType+"/"+fileName+new Date().getTime()+extenstion;
		File folderFile = new File(staticPath, "/slead-static"+orgId+"/"+locationId+"/"+fileType);
		if(!folderFile.exists())
			folderFile.mkdirs();
		File file = new File(staticPath, fileName);
		try {
			multipartFile.transferTo(file);
			return fileName;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public OrgStorageDetailDTO getOrgStorageDetails(String staticPath, long orgId, String orgName) {
		File file = new File(staticPath, orgId+"");
		
		return null;
	}
	
}
