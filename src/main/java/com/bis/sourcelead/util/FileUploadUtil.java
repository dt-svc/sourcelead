package com.bis.sourcelead.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OrgStorageDetailDTO;

public class FileUploadUtil {

	public String uploadFile(String staticPath, long orgId, long locationId, String fileType,
			MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();
		String extenstion = fileName.substring(fileName.lastIndexOf("."));
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		String url = "";
		long time = new Date().getTime();
		if (orgId > 0) {
			url = "/slead-static/" + orgId + "/" + locationId + "/" + fileType + "/" + fileName + time + extenstion;
			fileName = "/slead-static/" + orgId + "/" + locationId + "/temp/" + fileName + time + extenstion;
		} else {
			url = "/slead-static/" + fileType + "/" + fileName + time + extenstion;
			fileName = "/slead-static/temp/" + fileName + time + extenstion;
		}
		File folderFile = new File(staticPath, "/slead-static/" + orgId + "/" + locationId + "/" + fileType);
		if (!folderFile.exists())
			folderFile.mkdirs();
		folderFile = new File(staticPath, "/slead-static/" + orgId + "/" + locationId + "/temp");
		if (!folderFile.exists())
			folderFile.mkdirs();
		File file = new File(staticPath, fileName);
		try {

			multipartFile.transferTo(file);
			return url;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public OrgStorageDetailDTO getOrgStorageDetails(String staticPath, long orgId, String orgName) {
		File file = new File(staticPath, orgId + "");

		return null;
	}

	public static void moveFile(String staticPath, FileInfo fileInfo, String type) throws IOException {
		File file = new File(staticPath, fileInfo.getFileURL());
		File fromFile = new File(staticPath, fileInfo.getFileURL().replaceFirst(type, "temp"));
		Files.move(fromFile.toPath(), file.toPath());
	}
	
	public static String moveFile(String staticPath, long orgId, long orgLocationId, String type, File file) throws IOException {
		String url = "/slead-static/"+orgId+"/"+orgLocationId+"/"+type+"/"+file.getPath();
		File saveFileFolder = new File(staticPath, "/slead-static/"+orgId+"/"+orgLocationId+"/"+type);
		if(!saveFileFolder.exists())
		saveFileFolder.mkdirs();
		try {
			Files.move(file.toPath(), new File(saveFileFolder, file.getName()).toPath());
			return url;
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return "";
		}

	public static void fillUploadedFileInfo(Object object, List<Map<String, String>> fileInfo) throws Exception {
		List<FileInfo> uploadedFileInfo = new ArrayList<>();
		if (fileInfo != null) {
			for (Map<String, String> map : fileInfo) {
				Iterator<String> it = map.keySet().iterator();
				FileInfo f = new FileInfo();
				while (it.hasNext()) {
					String key = it.next();
					String value = map.get(key);
					if (key.equalsIgnoreCase("fileName"))
						f.setFileName(value);
					else if (key.equalsIgnoreCase("fileURL"))
						f.setFileURL(value);
					else
						f.setContentType(value);
				}
				uploadedFileInfo.add(f);
			}
		}
		object.getClass().getMethod("setUploadedFileInfo", List.class).invoke(object, uploadedFileInfo);
	}
	
	
	

}
