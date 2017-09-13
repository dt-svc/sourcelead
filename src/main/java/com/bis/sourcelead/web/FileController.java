package com.bis.sourcelead.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class FileController {

	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private PdfConversionUtil pdfConversionUtil;

	@RequestMapping(value = "/upload/{type}", method = RequestMethod.POST)
	@ResponseBody
	public List<FileInfo> uploadFile(MultipartHttpServletRequest request, @PathVariable String type,
			@RequestParam (defaultValue = "0") long orgId, @RequestParam (defaultValue = "0") long orgLocationId) {
		List<FileInfo> fileInfoList = new ArrayList<>();
		Iterator<String> itrator = request.getFileNames();
		while (itrator.hasNext()) {	
			FileInfo fileInfo = new FileInfo();
			MultipartFile multiFile = request.getFile(itrator.next());
			if(!type.equalsIgnoreCase("profile") && (orgId == 0 || orgLocationId == 0)) {
				Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				orgId = org.getId();
				orgLocationId = loginOrgLocation.getId();
			}
			String fileURL = new FileUploadUtil().uploadFile(staticPath, orgId, orgLocationId, type, multiFile);
			fileInfo.setFileName(multiFile.getOriginalFilename());
			fileInfo.setFileURL(fileURL);
			fileInfo.setContentType(multiFile.getContentType());
			fileInfoList.add(fileInfo);
		}
		
		return fileInfoList;
	}
		
	@RequestMapping(value = "/viewFile")
	public void viewFile(HttpServletResponse response, String documentPath, String contentType) {
		File file = new File(staticPath, documentPath);
		try (FileInputStream fis = new FileInputStream(file);) {

			byte[] pdfBytes = new byte[fis.available()];
			fis.read(pdfBytes);
			if (!"application/pdf".equalsIgnoreCase(contentType)) {
				pdfBytes = pdfConversionUtil.convertDocToPdf(pdfBytes);
			}
			response.setHeader("Content-disposition", "inline; filename=contractPdf.pdf");
			response.setContentType("application/pdf");
			ServletOutputStream sos = response.getOutputStream();
			if (null != pdfBytes) {
				response.setContentLength(pdfBytes.length);
				sos.write(pdfBytes);
			}
			sos.flush();
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}




