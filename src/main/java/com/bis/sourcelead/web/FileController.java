package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class FileController {

	@Value("${static.path}")
	private String staticPath;

	@RequestMapping(value = "/upload/{type}", method = RequestMethod.POST)
	@ResponseBody
	public List<FileInfo> uploadFile(MultipartHttpServletRequest request, @PathVariable String type) {
		List<FileInfo> fileInfoList = new ArrayList<>();
		Iterator<String> itrator = request.getFileNames();
		while (itrator.hasNext()) {
			FileInfo fileInfo = new FileInfo();
			MultipartFile multiFile = request.getFile(itrator.next());
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			String fileURL = new FileUploadUtil().uploadFile(staticPath, org.getId(), loginOrgLocation.getId(),	type, multiFile);
			fileInfo.setFileName(multiFile.getOriginalFilename());
			fileInfo.setFileURL(fileURL);
			fileInfo.setContentType(multiFile.getContentType());
			fileInfoList.add(fileInfo);
		}
		return fileInfoList;
	}
}
