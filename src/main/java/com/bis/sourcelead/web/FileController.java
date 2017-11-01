package com.bis.sourcelead.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.bis.sourcelead.dto.CandidateResumeDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateImportLog;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
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
	private PdfConversionUtil pdfConversionUtil;
	
	@Autowired
	private SourceleadResumeParser sourceleadResumeParser;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private CandidateResumesService candidateResumeService;

	@RequestMapping(value = "/upload/{type}", method = RequestMethod.POST)
	@ResponseBody
	public List<FileInfo> uploadFile(MultipartHttpServletRequest request, @PathVariable String type,
			@RequestParam (defaultValue = "0") long orgId, 
			@RequestParam (defaultValue = "0") long orgLocationId,
			@RequestParam (defaultValue = "0") int x,
			@RequestParam (defaultValue = "0") int y,
			@RequestParam (defaultValue = "0") int w,
			@RequestParam (defaultValue = "0") int h) {
		List<FileInfo> fileInfoList = new ArrayList<>();
		Iterator<String> itrator = request.getFileNames();
		while (itrator.hasNext()) {	
			FileInfo fileInfo = new FileInfo();
			MultipartFile multiFile = request.getFile(itrator.next());
			if(!type.equalsIgnoreCase("profile") && (orgId == 0 || orgLocationId == 0)) {
				Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				if(org != null) {
					orgId = org.getId();
				} 
				if(loginOrgLocation != null) {
					orgLocationId = loginOrgLocation.getId();
				} 
				
			}
			FileUploadUtil fileUploadUtil = new FileUploadUtil();
			if(type.equals("profile")) {
				fileInfo.setFileURL(fileUploadUtil.cropImage(staticPath, multiFile, x, y, w, h));
				fileInfo.setFileName(multiFile.getOriginalFilename());
				fileInfo.setContentType(multiFile.getContentType());
				fileInfoList.add(fileInfo);
			} else {
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
		}
		
		return fileInfoList;
	}
		
	@RequestMapping(value = "/upload-resumes", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, List<ResumeParserJsonUtil>> uploadResumeFiles(MultipartHttpServletRequest request, String uploadid) throws Exception
	{
		
		HttpSession session = request.getSession();
		Map<String, List<ResumeParserJsonUtil>> parsedList = new HashMap<>();
		saveLogMessage(uploadid, "File uploading completed");
		saveLogMessage(uploadid, "<br/>Processing files...");
		List<FileInfo> fileInfoList = uploadFile(request, "resumes", 0, 0, 0, 0, 0, 0);
		long fileName = new Date().getTime();
		saveLogMessage(uploadid, "<br/>Initial process completed "+fileInfoList.size()+" file(s) found!");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<ResumeParserJsonUtil> successList = new ArrayList<>();
		List<ResumeParserJsonUtil> failedList = new ArrayList<>();
		List<ResumeParserJsonUtil> existedList = new ArrayList<>();
		parsedList.put("completed", successList);
		parsedList.put("failed", failedList);
		parsedList.put("existed", existedList);
		int i = 1;
		int count = fileInfoList.size();
		for (FileInfo fileInfo : fileInfoList) {
			session.setAttribute("uploadstatus"+uploadid, session.getAttribute("uploadstatus"+uploadid)+"<br/>Processing "+fileInfo.getFileName());
			File file = new File(staticPath.substring(0, staticPath.lastIndexOf("/")), fileInfo.getFileURL().replace("resumes", StringUtils.isNotBlank(fileInfo.getTempFolderName())
					? fileInfo.getTempFolderName()
					: "temp"));
			ResumeParserJsonUtil resume = null;
			try {
				resume = sourceleadResumeParser.getCandidateDetailsFromResume1(file, request);
				resume.setFileInfo(fileInfo);
				Candidate c = candidateService.findByCandidateEmail(resume.getEmail(), loginOrgLocation.getId());
				boolean flag = false;
				if(c != null && c.getId() > 0) {
					CandidateResumes cr = candidateResumeService.getCandidateResumeByCandidateId(c.getId());
					if(cr == null || cr.getFileURL() == null || cr.getFileURL().isEmpty())
						flag = true;
				}
				if (c != null && c.getId() > 0 && !flag) {
					fileInfo.setFileURL(fileInfo.getFileURL().replace("resumes", "resumes-existed-"+fileName));
					FileUploadUtil.moveFile(staticPath, fileInfo, "resumes-existed-"+fileName);
					addToResponse(0, session, uploadid, resume, fileInfo, count, i++);
					existedList.add(resume);
					continue;
				} else if(flag) {
					CandidateResumeDTO candidateResumeDTO = new CandidateResumeDTO();
					List<FileInfo> fileInfoList1 = new ArrayList<>();
					fileInfoList1.add(fileInfo);
					candidateResumeDTO.setStaticPath(staticPath);
					candidateResumeDTO.setFileInfo(fileInfoList1);
					candidateResumeService.storeCandidateResume(candidateResumeDTO, user, c, null, loginOrgLocation);
					addToResponse(2, session, uploadid, resume, fileInfo, count, i++);
					successList.add(resume);
				} else {
					CandidateDTO candidateDto = new CandidateDTO();
					candidateDto.setEmail(resume.getEmail());
					candidateDto.setCandidateSkills(resume.getSkills());
					Iterator<String> it = resume.getPhoneNumbers().iterator();
					while (it.hasNext()) {
						if (candidateDto.getMobilePhone() == null)
							candidateDto.setMobilePhone(it.next());
						else
							candidateDto.setAlternateMobilePhone(it.next());
					}
					candidateDto.setFileName(fileInfo.getFileName());
					candidateDto.setFileContentType(fileInfo.getContentType());
					candidateDto.setFileUrl(fileInfo.getFileURL());
					List<FileInfo> fileInfoList1 = new ArrayList<>();
					fileInfoList1.add(fileInfo);
					candidateDto.setFileInfo(fileInfoList1);
					candidateDto.setStaticPath(staticPath);
					String name = resume.getName();
					candidateDto.setFirstName(name.contains(".") ? name.substring(0, name.lastIndexOf(".")) : name);
					candidateDto.setLastName(name.contains(".") ? name.substring(name.lastIndexOf(".")+1) : "");
					candidateService.storeCandidate(candidateDto, loginOrgLocation, user);
					addToResponse(1, session, uploadid, resume, fileInfo, count, i++);
					successList.add(resume);
				}	
			} catch (Exception ex) {
				ex.printStackTrace();
				fileInfo.setFileURL(fileInfo.getFileURL().replace("resumes", "resumes-failed-"+fileName));
				FileUploadUtil.moveFile(staticPath, fileInfo, "resumes-failed-"+fileName);
				addToResponse(-1, session, uploadid, resume, fileInfo, count, i++);
				failedList.add(resume);
			}
		}
		session.setAttribute("uploadstats"+uploadid, "Completed!");
		saveLogMessage(uploadid, "<br/>Completed!");
		//zip the existing and failed folders and send the urls for download if required.
		return parsedList;
	}
	
	private void saveLogMessage(String uploadId, String message)
	{
		CandidateImportLog c = new CandidateImportLog();
		c.setMessage(message);
		c.setUploadId(uploadId);
		candidateService.storeCandidateImportLog(c);
	}

	@RequestMapping(value = "/parse-resumes", method = RequestMethod.POST)
	@ResponseBody
	public List<ResumeParserJsonUtil> uploadResume(MultipartHttpServletRequest request)
	{
		List<ResumeParserJsonUtil> resumeContent = new ArrayList<>();
		List<FileInfo> fileInfoList = uploadFile(request, "resumes", 0, 0, 0, 0, 0, 0);
		for (FileInfo fileInfo : fileInfoList) {
			File file = new File(staticPath.substring(0, staticPath.lastIndexOf("/")), fileInfo.getFileURL().replace("resumes", "temp"));
			try {
				ResumeParserJsonUtil resume = sourceleadResumeParser.getCandidateDetailsFromResume1(file, request);
				resume.setFileInfo(fileInfo);
				resumeContent.add(resume);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return resumeContent;
	}
	
	@RequestMapping(value = "/viewFile")
	public void viewFile(HttpServletResponse response, String documentPath, String contentType) {
		//String ac= staticPath.Remove(staticPath.lastIndexOf('/'));
		String ac =StringUtils.removeEnd(staticPath, staticPath.substring(staticPath.lastIndexOf("/")));
		File file = new File(ac, documentPath);
		try (FileInputStream fis = new FileInputStream(file);) {

			byte[] pdfBytes = new byte[fis.available()];
			fis.read(pdfBytes);
			if (!"application/pdf".equalsIgnoreCase(contentType)) {
				pdfBytes = pdfConversionUtil.convertDocToPdf(pdfBytes);
			}
			response.setHeader("Content-disposition", "inline; filename=document-"+(new Date().getTime())+".pdf");
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
	
	@RequestMapping("/crop.html")
	public String crop() {
		return "crop";
	}
	
	@RequestMapping("/bulk-resume-import.html")
	public String bulkResumeImport() {
		return "resume-upload";
	}
	
	@RequestMapping("/bulk-resume-import-log")
	@ResponseBody
	public String uploadStatus(HttpServletRequest request, String uploadid) {
		StringBuilder message = new StringBuilder();
		List<CandidateImportLog> list = candidateService.getCandidateImportLog(uploadid, false);
		for(CandidateImportLog c : list) {
			message.append(c.getMessage());
		}
		return message.toString();
	}
	
	@RequestMapping("/bulk-resume-import-counts")
	@ResponseBody
	public String uploadStats(HttpServletRequest request, String uploadid) {
		String message = "";
		if(request.getSession().getAttribute("uploadstats"+uploadid) != null) {
			message = request.getSession().getAttribute("uploadstats"+uploadid).toString();
			request.getSession().removeAttribute("uploadstats"+uploadid);
		}
		return message;
	}
	
	private void addToResponse(int type, HttpSession session, String uploadid, ResumeParserJsonUtil resume, FileInfo fileInfo, int count, int i) {
		saveLogMessage(uploadid, "<br/><br/>"+(type == 1 ? "<span class='a'>Candidate (Resume) added" : type == 2 ? "<span class='b'>Candidate Existed, resume attached " : type == 0 ? "<span class='c'>Candidate Existed" : "<span class='b'>Resume parsing failed")+"</span><br/>File Name: "+fileInfo.getFileName()+"<br/>Name: "+resume.getName() + "<br/>Email: "+resume.getEmail());
		saveLogMessage(uploadid, "<br/><a href='"+fileInfo.getFileURL()+"' target='_blank'>Click here to download the Resume</a>");
		session.setAttribute("uploadstats"+uploadid, i+ " of "+count+" processed...");
	}
	
}



