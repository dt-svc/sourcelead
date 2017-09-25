package com.bis.sourcelead.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.parser.Reader;
import com.bis.sourcelead.parser.ResumeParser;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.ResumeParserJsonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.SourceleadResumeParser;

@Controller
public class FileController {
	@Value("${static.path}")
	private String staticPath;
	@Autowired
	private SourceleadResumeParser sourceleadResumeParser;
	@Autowired
	private PdfConversionUtil pdfConversionUtil;
	@Autowired
	private CandidateService candidateService;
	
	@RequestMapping(value = "/upload/{type}", method = RequestMethod.POST)
	@ResponseBody
	public List<FileInfo> uploadFile(MultipartHttpServletRequest request, @PathVariable String type, @RequestParam(defaultValue = "0") long orgId, @RequestParam(defaultValue = "0") long orgLocationId)
	{
		List<FileInfo> fileInfoList = new ArrayList<>();
		Iterator<String> itrator = request.getFileNames();
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		while (itrator.hasNext()) {
			FileInfo fileInfo = new FileInfo();
			MultipartFile multiFile = request.getFile(itrator.next());
			if (orgId == 0 || orgLocationId == 0) {
				Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				orgId = org.getId();
				orgLocationId = loginOrgLocation.getId();
			}
			String fileName = multiFile.getOriginalFilename();
			String extenstion = fileName.substring(fileName.lastIndexOf("."));
			if (extenstion.equalsIgnoreCase(".zip")) {
				fileInfoList.addAll(fileUploadUtil.uploadZipFile(staticPath, orgId, orgLocationId, type, multiFile));
			}
			else {
				String fileURL = fileUploadUtil.uploadFile(staticPath, orgId, orgLocationId, type, multiFile);
				fileInfo.setFileName(multiFile.getOriginalFilename());
				fileInfo.setFileURL(fileURL);
				fileInfo.setContentType(multiFile.getContentType());
				fileInfoList.add(fileInfo);
			}
		}
		return fileInfoList;
	}
	
	@RequestMapping(value = "/upload-resumes", method = RequestMethod.POST)
	@ResponseBody
	public List<ResumeParserJsonUtil> uploadResumeFiles(MultipartHttpServletRequest request) throws Exception
	{
		List<ResumeParserJsonUtil> parsedList = new ArrayList<>();
		List<FileInfo> fileInfoList = uploadFile(request, "resumes", 0, 0);
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		for (FileInfo fileInfo : fileInfoList) {
			File file = new File(staticPath.substring(0, staticPath.lastIndexOf("/")), fileInfo.getFileURL().replace("resumes", StringUtils.isNotBlank(fileInfo.getTempFolderName())
					? fileInfo.getTempFolderName()
					: "temp"));
			ResumeParserJsonUtil resume = null;
			try {
				resume = sourceleadResumeParser.getCandidateDetailsFromResume1(file, request);
				resume.setFileInfo(fileInfo);
				Candidate c = candidateService.findByCandidateEmail(resume.getEmail(), loginOrgLocation.getId());
				if (c == null || c.getId() == 0) {
					continue;
				}
				else {
					CandidateDTO candidateDto = new CandidateDTO();
					candidateDto.setEmail(resume.getEmail());
					candidateDto.setCandidateSkills(resume.getSkills());
					Iterator<String> it = resume.getPhoneNumbers().iterator();
					while (it.hasNext()) {
						if (candidateDto.getPhoneNumber() == null)
							candidateDto.setPhoneNumber(it.next());
						else
							candidateDto.setAlternateMobilePhone(it.next());
					}
					candidateService.storeCandidate(candidateDto, loginOrgLocation, user);
					FileUploadUtil.moveFile(staticPath, fileInfo, "resumes");
				}
				parsedList.add(resume);
			} catch (Exception ex) {
				ex.printStackTrace();
				// FileUploadUtil.moveFile(staticPath, fileInfo,
				// "resumes-failed");
			}
		}
		return parsedList;
	}
	
	@RequestMapping(value = "/parse-resumes", method = RequestMethod.POST)
	@ResponseBody
	public List<ResumeParserJsonUtil> uploadResume(MultipartHttpServletRequest request)
	{
		List<ResumeParserJsonUtil> resumeContent = new ArrayList<>();
		List<FileInfo> fileInfoList = uploadFile(request, "resumes", 0, 0);
		for (FileInfo fileInfo : fileInfoList) {
			File file = new File(staticPath.substring(0, staticPath.lastIndexOf("/")), fileInfo.getFileURL().replace("resumes", "temp"));
			try (FileInputStream fis = new FileInputStream(file);) {
				byte[] b = new byte[fis.available()];
				fis.read(b);
				ResumeParserJsonUtil resume = sourceleadResumeParser.getCandidateDetailsFromResume(b, request);
				resume.setFileInfo(fileInfo);
				resumeContent.add(resume);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return resumeContent;
	}
	
	@RequestMapping(value = "/parse-resumes-external", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> uploadResumes(MultipartHttpServletRequest request)
	{
		List<Map<String, Object>> resumeContent = new ArrayList<>();
		List<FileInfo> fileInfoList = uploadFile(request, "resumes", 0, 0);
		Reader reader = new Reader();
		ResumeParser resumeParser = new ResumeParser();
		for (FileInfo fileInfo : fileInfoList) {
			System.out.println(fileInfo.getFileName());
			String fileContent = reader.readFileContent(new File(staticPath.substring(0, staticPath.lastIndexOf("/")), fileInfo.getFileURL().replace("resumes", "temp")).getAbsolutePath());
			if (StringUtils.isNotBlank(fileContent)) {
				Map<String, Object> parsedResume = resumeParser.parseResume(fileContent);
				if (parsedResume != null && !parsedResume.isEmpty()) {
					parsedResume.put("fileInfo", fileInfo);
					resumeContent.add(parsedResume);
				}
			}
		}
		return resumeContent;
	}
	
	@RequestMapping(value = "/viewFile")
	public void viewFile(HttpServletResponse response, String documentPath, String contentType)
	{
		File file = new File(staticPath.substring(0, staticPath.lastIndexOf("/")), documentPath);
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
