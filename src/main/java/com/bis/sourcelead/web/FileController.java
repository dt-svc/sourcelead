package com.bis.sourcelead.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
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

import com.bis.sourcelead.constants.Constants;
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
@RequestMapping("/app")
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
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;

	@RequestMapping(value = {"/ws/upload/{type}","/rest/upload/{type}"}, method = RequestMethod.POST)
	@ResponseBody
	public List<FileInfo> uploadFile(MultipartHttpServletRequest request, @PathVariable String type,
			@RequestParam(defaultValue = "0") long orgId, @RequestParam(defaultValue = "0") long orgLocationId,
			@RequestParam(defaultValue = "0") int x, @RequestParam(defaultValue = "0") int y,
			@RequestParam(defaultValue = "0") int w, @RequestParam(defaultValue = "0") int h,
			@RequestParam(defaultValue = "0") int chunk, @RequestParam(defaultValue = "0") int chunks, String name,
			String uploadid) {

		List<FileInfo> fileInfoList = new ArrayList<>();
		Iterator<String> itrator = request.getFileNames();
		while (itrator.hasNext()) {
			FileInfo fileInfo = new FileInfo();
			boolean chunkDataFlag = false;
			MultipartFile multiFile = request.getFile(itrator.next());
			if (name == null)
				name = multiFile.getOriginalFilename();
			File file = new File(staticPath, "temp"+ System.getProperty("file.separator") + (uploadid != null ? uploadid + "-" : "") + name);
			try (FileOutputStream fis = new FileOutputStream(file, true);) {
				fis.write(multiFile.getBytes());
				chunkDataFlag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (chunks > chunk + 1) {
				return fileInfoList;
			}
			if (!type.equalsIgnoreCase("profile") && (orgId == 0 || orgLocationId == 0)) {
				Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
						"orgLocations");
				if (org != null) {
					orgId = org.getId();
				}
				if (loginOrgLocation != null) {
					orgLocationId = loginOrgLocation.getId();
				}

			}
			FileUploadUtil fileUploadUtil = new FileUploadUtil();
			if (type.equals("profile")) {
				fileInfo.setFileURL(fileUploadUtil.cropImage(staticPath, file, x, y, w, h));
				fileInfo.setFileName(multiFile.getOriginalFilename());
				fileInfo.setContentType(multiFile.getContentType());
				fileInfoList.add(fileInfo);
			} else {
				String fileName = chunkDataFlag ? name : multiFile.getOriginalFilename();
				String extenstion = fileName.substring(fileName.lastIndexOf("."));
				if (extenstion.equalsIgnoreCase(".rar"))
					continue;
				if (extenstion.equalsIgnoreCase(".zip")) {
					fileInfoList.addAll(fileUploadUtil.uploadZipFile(staticPath, orgId, orgLocationId, type, file));
				} else {
					String fileURL = fileUploadUtil.uploadFile(staticPath, orgId, orgLocationId, type, file);
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
	public Map<String, List<ResumeParserJsonUtil>> uploadResumeFiles(MultipartHttpServletRequest request,
			String uploadid, int chunk, int chunks, String name) throws Exception {
		HttpSession session = request.getSession();
		Map<String, List<ResumeParserJsonUtil>> parsedList = new HashMap<>();
		if (chunks <= chunk + 1) {
			saveLogMessage(uploadid, "<br/>File uploading completed");
			saveLogMessage(uploadid, "<br/>Processing files...");
		}
		List<FileInfo> fileInfoList = uploadFile(request, "resumes", 0, 0, 0, 0, 0, 0, chunk, chunks, name, uploadid);
		if (chunks <= chunk + 1) {
			saveLogMessage(uploadid, "<br/>Initial process completed " + fileInfoList.size() + " file(s) found!");
		} else
			return parsedList;
		//long fileName = new Date().getTime();
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
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
			session.setAttribute("uploadstatus" + uploadid,
					session.getAttribute("uploadstatus" + uploadid) + "<br/>Processing " + fileInfo.getFileName());
			File file = new File(staticPath.substring(0, staticPath.lastIndexOf("/")), fileInfo.getFileURL().replace(
					"resumes",
					StringUtils.isNotBlank(fileInfo.getTempFolderName()) ? fileInfo.getTempFolderName() : "temp"));
			ResumeParserJsonUtil resume = null;
			try {
				resume = sourceleadResumeParser.getCandidateDetailsFromResume1(file, request);
				resume.setFileInfo(fileInfo);
				if (resume.getEmail() == null || resume.getEmail().isEmpty() || resume.getEmail().equals("null")) {
					throw new Exception("Email ID missing");
				}
				Candidate c = candidateService.findByCandidateEmail(resume.getEmail(), loginOrgLocation.getId());
				boolean flag = false;
				if (c != null && c.getId() > 0) {
					CandidateResumes cr = candidateResumeService.getCandidateResumeByCandidateId(c.getId());
					if (cr == null || cr.getFileURL() == null || cr.getFileURL().isEmpty())
						flag = true;
				}
				if (c != null && c.getId() > 0 && !flag) {
					fileInfo.setFileURL(fileInfo.getFileURL().replace("resumes", "resumes-existed-" + uploadid));
					FileUploadUtil.moveFile(staticPath, fileInfo, "resumes-existed-" + uploadid);
					addToResponse(0, session, uploadid, resume, fileInfo, count, i++);
					existedList.add(resume);
					continue;
				} else if (flag) {
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
					String name1 = resume.getName();
					candidateDto.setFirstName(name1.contains(".") ? name1.substring(0, name1.lastIndexOf(".")) : name1);
					candidateDto.setLastName(name1.contains(".") ? name1.substring(name1.lastIndexOf(".") + 1) : "");
					candidateDto.setCandidateCreationType(Constants.IMPORT_CANDIDATE);
					candidateService.storeCandidate(candidateDto, loginOrgLocation, user);
					addToResponse(1, session, uploadid, resume, fileInfo, count, i++);
					successList.add(resume);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				fileInfo.setFileURL(fileInfo.getFileURL().replace("resumes", "resumes-failed-" + uploadid));
				FileUploadUtil.moveFile(staticPath, fileInfo, "resumes-failed-" + uploadid);
				addToResponse(-1, session, uploadid, resume, fileInfo, count, i++);
				failedList.add(resume);
			}
		}
		session.setAttribute("uploadstats" + uploadid, "Completed!");
		saveLogMessage(uploadid, "<br/>Completed!<br/><br/>");
		// zip the existing and failed folders and send the urls for download if
		// required.
		return parsedList;
	}

	private void saveLogMessage(String uploadId, String message) {
		CandidateImportLog c = new CandidateImportLog();
		c.setMessage(message);
		c.setUploadId(uploadId);
		candidateService.storeCandidateImportLog(c);
	}

	@RequestMapping(value = "/parse-resumes", method = RequestMethod.POST)
	@ResponseBody
	public List<ResumeParserJsonUtil> uploadResume(MultipartHttpServletRequest request) {
		List<ResumeParserJsonUtil> resumeContent = new ArrayList<>();
		List<FileInfo> fileInfoList = uploadFile(request, "resumes", 0, 0, 0, 0, 0, 0, 0, 0, null, null);
		for (FileInfo fileInfo : fileInfoList) {
			File file = new File(staticPath.substring(0, staticPath.lastIndexOf("/")),
					fileInfo.getFileURL().replace("resumes", "temp"));
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
		// String ac= staticPath.Remove(staticPath.lastIndexOf('/'));
		String ac = StringUtils.removeEnd(staticPath, staticPath.substring(staticPath.lastIndexOf("/")));
		File file = new File(ac, documentPath);
		try (FileInputStream fis = new FileInputStream(file);) {

			byte[] pdfBytes = new byte[fis.available()];
			fis.read(pdfBytes);
			if (!"application/pdf".equalsIgnoreCase(contentType)) {
				pdfBytes = pdfConversionUtil.convertDocToPdf(pdfBytes);
			}
			response.setHeader("Content-disposition", "inline; filename=document-" + (new Date().getTime()) + ".pdf");
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

	@RequestMapping("/check-session")
	@ResponseBody
	public String checkSession(HttpServletRequest request) {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		if (loginOrgLocation != null)
			return "YES";
		else
			return "NO";
	}

	@RequestMapping("/bulk-resume-import-log")
	@ResponseBody
	public String uploadStatus(HttpServletRequest request, String uploadid) {
		StringBuilder message = new StringBuilder();
		List<CandidateImportLog> list = candidateService.getCandidateImportLog(uploadid, false);
		for (CandidateImportLog c : list) {
			message.append(c.getMessage());
		}
		return message.toString();
	}

	@RequestMapping("/bulk-resume-import-counts")
	@ResponseBody
	public String uploadStats(HttpServletRequest request, String uploadid) {
		String message = "";
		if (request.getSession().getAttribute("uploadstats" + uploadid) != null) {
			message = request.getSession().getAttribute("uploadstats" + uploadid).toString();
			request.getSession().removeAttribute("uploadstats" + uploadid);
		}
		return message;
	}

	@RequestMapping("/bulk-resume-import-export-log")
	@ResponseBody
	public String exportLog(HttpServletRequest request, String uploadid) {
		PrintWriter out = null;
		File file = null;
		try {
			file = new File(staticPath, "temp/log-" + uploadid + ".html");
			
			out = new PrintWriter(new FileOutputStream(file), true);
			List<CandidateImportLog> list = candidateService.getCandidateImportLog(uploadid, true);
			for (CandidateImportLog c : list) {
				out.write(c.getMessage());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(out != null) 
				out.close();
		}
		return file == null ? "" : file.getAbsolutePath();
	}
	
	@RequestMapping("/bulk-resume-import-export-failed")
	@ResponseBody
	public String exportFailedResumes(HttpServletRequest request, String uploadid) {
		return FileUploadUtil.getResumeZipFileName(staticPath, request, uploadid, true);
	}
	
	@RequestMapping("/bulk-resume-import-export-duplicate")
	@ResponseBody
	public String exportDuplicateResumes(HttpServletRequest request, String uploadid) {
		return FileUploadUtil.getResumeZipFileName(staticPath, request, uploadid, false);
	}

	private void addToResponse(int type, HttpSession session, String uploadid, ResumeParserJsonUtil resume,
			FileInfo fileInfo, int count, int i) {
		saveLogMessage(uploadid, "<br/><br/>"
				+ (type == 1 ? "<span class='a'>Candidate (Resume) added"
						: type == 2 ? "<span class='b'>Candidate Existed, resume attached "
								: type == 0 ? "<span class='c'>Candidate Existed"
										: "<span class='b'>Resume parsing failed")
				+ "</span><br/>File Name: " + fileInfo.getFileName() + "<br/>Name: " + resume.getName() + "<br/>Email: "
				+ (resume.getEmail() == null || resume.getEmail().isEmpty() || resume.getEmail().equals("null") ? "" : resume.getEmail()));
		saveLogMessage(uploadid,
				"<br/><a href='"+ (sourceleadUrl.replace("/app", "")) + fileInfo.getFileURL() + "' target='_blank'>Click here to download the Resume</a>");
		session.setAttribute("uploadstats" + uploadid, i + " of " + count + " Parsed...");
	}
	
	@RequestMapping(value = "/downloadFile.html")
    public void downloadFile(HttpServletResponse response, String file) {
        try {
            File file1 = new File(URLDecoder.decode(file, "utf-8"));
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + file1.getName());

            OutputStream outStream;
            try (FileInputStream inputStream = new FileInputStream(file1)) {
                outStream = response.getOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            }
            outStream.close();

        } catch (Exception ex) {

        }
    }


}
