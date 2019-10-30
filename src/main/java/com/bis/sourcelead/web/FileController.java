package com.bis.sourcelead.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.entity.CandidateImportLog;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.rest.RequirementRestControllerAdmin;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfConversionUtil;
import com.bis.sourcelead.util.ResumeParserJsonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.SourceleadResumeParserNew;
import com.google.gson.Gson;



@Controller
@RequestMapping("/app")
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Value("${static.path}")
	private String staticPath;
	
	@Value("${env}")
	private String environment;


	@Autowired
	private PdfConversionUtil pdfConversionUtil;

	@Autowired
	private SourceleadResumeParserNew sourceleadResumeParser;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	private CandidateResumesService candidateResumeService;
	
	@Autowired
	private RequirementRestControllerAdmin requirementRestController;
	
	@Autowired
	private RequirementService requirementService;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${resumeParserUrlLocal}")
	private String resumeParserUrlLocal;
	
	@Value("${resumeParserUrlQa}")
	private String resumeParserUrlQa;
	
	@Value("${isResumeparserEnabled}")
	private String isResumeparserEnabled;
	


	@RequestMapping(value = { "/ws/upload/{type}", "/rest/upload/{type}" }, method = RequestMethod.POST)
	@ResponseBody
	public List<FileInfo> uploadFile(MultipartHttpServletRequest request, @PathVariable String type,
			@RequestParam(defaultValue = "0") long orgId, @RequestParam(defaultValue = "0") long orgLocationId,
			@RequestParam(defaultValue = "0") int x, @RequestParam(defaultValue = "0") int y, @RequestParam(defaultValue = "0") int w,
			@RequestParam(defaultValue = "0") int h, @RequestParam(defaultValue = "0") int chunk,
			@RequestParam(defaultValue = "0") int chunks, String name, String uploadid) {

		List<FileInfo> fileInfoList = new ArrayList<>();
		Iterator<String> itrator = request.getFileNames();
		while (itrator.hasNext()) {
			FileInfo fileInfo = new FileInfo();
			boolean chunkDataFlag = false;
			MultipartFile multiFile = request.getFile(itrator.next());
			if (name == null)
				name = multiFile.getOriginalFilename();
			File file = new File(staticPath,
					"temp" + System.getProperty("file.separator") + (uploadid != null ? uploadid + "-" : "") + name.replaceAll(" ", "_"));
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
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Long organizationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgId");
				if (org != null) {
					orgId = org.getId();
				}
				if (loginOrgLocation != null) {
					orgId = organizationId;
					orgLocationId = loginOrgLocation.getId();
				}

			}
			FileUploadUtil fileUploadUtil = new FileUploadUtil();
			if (type.equals("profile")) {
				fileInfo.setFileURL(fileUploadUtil.cropImage(staticPath, file, x, y, w, h));
				fileInfo.setFileName(multiFile.getOriginalFilename().replaceAll(" ", "_"));
				fileInfo.setContentType(multiFile.getContentType());
				fileInfoList.add(fileInfo);
			} else {
				String fileName = chunkDataFlag ? name.replaceAll(" ", "_") : multiFile.getOriginalFilename().replaceAll(" ", "_");
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

	@RequestMapping(value = "/ws/uploadTaxDocs", method = RequestMethod.POST)
	@ResponseBody
	public List<FileInfo> uploadFile(MultipartHttpServletRequest request, @RequestParam String type) {
		List<FileInfo> fileInfoList = new LinkedList<>();
		try {
			List<MultipartFile> filesList = request.getFiles(type);
			if (filesList != null && filesList.size() > 0) {
				for (MultipartFile multipartFile : filesList) {
					long orgId = 0, orgLocationId = 0;
					FileInfo fileInfo = new FileInfo();
					String name = multipartFile.getOriginalFilename();
					File file = new File(staticPath, "temp" + System.getProperty("file.separator") + "" + name);
					try (FileOutputStream fis = new FileOutputStream(file, true);) {
						fis.write(multipartFile.getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
					Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
					OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					if (org != null) {
						orgId = org.getId();
					}
					if (loginOrgLocation != null) {
						orgLocationId = loginOrgLocation.getId();
					}

					FileUploadUtil fileUploadUtil = new FileUploadUtil();
					String fileName = multipartFile.getOriginalFilename();
					String extenstion = fileName.substring(fileName.lastIndexOf("."));
					if (extenstion.equalsIgnoreCase(".rar"))
						continue;
					if (extenstion.equalsIgnoreCase(".zip")) {
						fileInfoList.addAll(fileUploadUtil.uploadZipFile(staticPath, orgId, orgLocationId, type, file));
					} else {
						String fileURL = fileUploadUtil.uploadFile(staticPath, orgId, orgLocationId, type, file);
						fileInfo.setFileName(multipartFile.getOriginalFilename());
						fileInfo.setFileURL(fileURL);
						fileInfo.setContentType(multipartFile.getContentType());
						fileInfoList.add(fileInfo);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileInfoList;
	}

	@RequestMapping(value = "/upload-resumes", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, List<ResumeParserJsonUtil>> uploadResumeFiles(MultipartHttpServletRequest request, String uploadid, int chunk,
			int chunks, String name, String requirementUUID, String needApplicantPermit, String notifyToApplicant) throws Exception {
		HttpSession session = request.getSession();
		Map<String, List<ResumeParserJsonUtil>> parsedList = new HashMap<>();
		if (chunks <= chunk + 1) {
			FileUploadUtil.saveLogMessage(uploadid, "<br/>File uploading completed", candidateService);
			FileUploadUtil.saveLogMessage(uploadid, "<br/>Processing files...", candidateService);
		}
		List<FileInfo> fileInfoList = uploadFile(request, "resumes", 0, 0, 0, 0, 0, 0, chunk, chunks, name, uploadid);
		if (chunks <= chunk + 1) {
			FileUploadUtil.saveLogMessage(uploadid, "<br/>Initial process completed " + fileInfoList.size() + " file(s) found!",
					candidateService);
		} else
			return parsedList;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String country = (String) SourceLeadSessionManager.getSessionAttribute(request, "country");
		Long requirementId = 0L;
		if (requirementUUID != null && !requirementUUID.isEmpty()) {
			Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
			if (requirement != null) {
				requirementId = requirement.getId();
			}
		}
		String baseServicesUrl = "";
		if("qa".equalsIgnoreCase(environment)) {
			baseServicesUrl = resumeParserUrlQa;
		}else {
			baseServicesUrl = resumeParserUrlLocal;
		}
		String candidateIds = FileUploadUtil.processResumeFiles(parsedList, fileInfoList, session, uploadid, staticPath, sourceleadResumeParser, candidateService,
				candidateResumeService, country, loginOrgLocation, sourceleadUrl, user, requirementId, needApplicantPermit, notifyToApplicant,baseServicesUrl,isResumeparserEnabled);

		if (requirementId > 0 && requirementRestController != null && !candidateIds.isEmpty()) {
			requirementRestController.submitRecommendationCandidates(requirementId, candidateIds, "Y", needApplicantPermit, notifyToApplicant, request);
		}
		
		session.setAttribute("uploadstats" + uploadid, "Completed!");
		FileUploadUtil.saveLogMessage(uploadid, "<br/>Completed!<br/><br/>", candidateService);
		return parsedList;
	}

	
	@RequestMapping(value = "/parse-resumes", method = RequestMethod.POST)
	@ResponseBody
	public List<ResumeParserJsonUtil> uploadResume(MultipartHttpServletRequest request) {
		
		long startTime = System.currentTimeMillis();
		List<ResumeParserJsonUtil> resumeContent = new ArrayList<>();
		List<FileInfo> fileInfoList = uploadFile(request, "resumes", 0, 0, 0, 0, 0, 0, 0, 0, null, null);
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String country = (String) SourceLeadSessionManager.getSessionAttribute(request, "country");
		for (FileInfo fileInfo : fileInfoList) {
			File file = new File(staticPath.substring(0, staticPath.lastIndexOf("/")), fileInfo.getFileURL().replace("resumes", "temp"));
			ResumeParserJsonUtil resume = new ResumeParserJsonUtil();
			try {
				if("true".equalsIgnoreCase(isResumeparserEnabled)) {
					logger.info("*******Resume Parser Service calling..");
					 resume = sourceleadResumeParser.getCandidateDetailsFromResumeParser(file,fileInfo,country);
				}else {
					logger.info("*******Resume Parser without Service calling..");
					 resume = sourceleadResumeParser.getCandidateDetailsFromResume1(file, country, loginOrgLocation.getDateFormat(), file.getName());
				}
				
			      resumeContent.add(resume);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		logger.info("Total Response Time of resume parser is "+responseTime+" MS");
		return resumeContent;
	}


	@RequestMapping(value = "/process-uploaded-files", method = RequestMethod.POST)
	@ResponseBody
	public String processUploadedFiles(HttpServletRequest request, String path) {
		final File file = new File(path);
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		final OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String uploadid = String.valueOf(new Date().getTime());
		if (file.exists() && file.canRead() && org != null && loginOrgLocation != null) {
			long orgId = org.getId(), orgLocationId = loginOrgLocation.getId();
			final User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			final String country = (String) SourceLeadSessionManager.getSessionAttribute(request, "country");

			new Thread() {
				public void run() {
					FileUploadUtil.saveLogMessage(uploadid, "<br/>Processing files...", candidateService);
					FileUploadUtil fileUploadUtil = new FileUploadUtil();
					List<FileInfo> fileInfoList = new ArrayList<>();
					File[] fileList = file.listFiles();
					for (File f : fileList) {
						String name = f.getName().toLowerCase();
						if (name.endsWith(".zip")) {
							fileInfoList.addAll(fileUploadUtil.uploadZipFile(staticPath, orgId, orgLocationId, "resumes", f));
						} else if (name.endsWith(".doc") || name.endsWith(".docx") || name.endsWith(".pdf")) {
							String fileURL = fileUploadUtil.uploadFile(staticPath, orgId, orgLocationId, "resumes", f);
							FileInfo fileInfo = new FileInfo();
							fileInfo.setFileName(name);
							fileInfo.setFileURL(fileURL);
							fileInfo.setContentType(name.endsWith(".doc") || name.endsWith(".docx") ? "application/msword" : "");
							fileInfoList.add(fileInfo);
						}
					}
					FileUploadUtil.saveLogMessage(uploadid, "<br/>Initial process completed " + fileInfoList.size() + " file(s) found!",
							candidateService);
					if (!fileInfoList.isEmpty()) {
						Map<String, List<ResumeParserJsonUtil>> parsedList = new HashMap<>();

						try {
							String baseServicesUrl = "";
							if("qa".equalsIgnoreCase(environment)) {
								baseServicesUrl = resumeParserUrlQa;
							}else {
								baseServicesUrl = resumeParserUrlLocal;
							}
							FileUploadUtil.processResumeFiles(parsedList, fileInfoList, null, uploadid, staticPath, sourceleadResumeParser,
									candidateService, candidateResumeService, country, loginOrgLocation, sourceleadUrl, user, 0L, null, null,baseServicesUrl,isResumeparserEnabled);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						FileUploadUtil.saveLogMessage(uploadid, "<br/>Completed!<br/><br/>", candidateService);

						String existed = FileUploadUtil.getResumeZipFileName(staticPath, orgId, orgLocationId, uploadid, false);
						String failed = FileUploadUtil.getResumeZipFileName(staticPath, orgId, orgLocationId, uploadid, true);
						String logFile = exportLog(uploadid);
						File file1 = file;
						if (file.isFile()) {
							file1 = file.getParentFile();
						}
						if (existed.endsWith(".zip")) {
							File existedFile = new File(existed);
							File existedFile1 = new File(file1, existedFile.getName());
							try {
								Files.move(existedFile.toPath(), existedFile1.toPath());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						if (failed.endsWith(".zip")) {
							File existedFile = new File(failed);
							File existedFile1 = new File(file1, existedFile.getName());
							try {
								Files.move(existedFile.toPath(), existedFile1.toPath());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						if (logFile.endsWith(".html")) {
							File existedFile = new File(logFile);
							File existedFile1 = new File(file1, existedFile.getName());
							try {
								Files.move(existedFile.toPath(), existedFile1.toPath());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						// send mail...
					} else
						FileUploadUtil.saveLogMessage(uploadid, "<br/>Completed!<br/><br/>", candidateService);
				}
			}.start();

			return "Resume parsing started&nbsp;&nbsp;<a href='" + sourceleadUrl + "/process-log?uploadid=" + uploadid
					+ "' target='_blank'>View Process Log</a>";
		} else if (org == null || loginOrgLocation == null) {
			return "Session timedout, please login and try again";
		} else {
			return "Invalid File/Folder Path";
		}

	}

	@RequestMapping(value = "/viewFile")
	public void viewFile(HttpServletResponse response, String documentPath, String contentType) {
		// String ac= staticPath.Remove(staticPath.lastIndexOf('/'));

		if (contentType.startsWith("image/")) {
			try {
				response.sendRedirect(sourceleadUrl.replace("/app", "") + documentPath);
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String ac = StringUtils.removeEnd(staticPath, staticPath.substring(staticPath.lastIndexOf("/")));
		File file = new File(ac, documentPath);
		try (FileInputStream fis = new FileInputStream(file);) {
			String fileExt = file.getName().substring(file.getName().lastIndexOf("."));
			byte[] pdfBytes = new byte[fis.available()];
			fis.read(pdfBytes);
			if (!"application/pdf".equalsIgnoreCase(contentType)) {
				if(".doc".equalsIgnoreCase(fileExt)) {
					pdfBytes = pdfConversionUtil.convertDocFileToPdf(file);	
				}else if(".docx".equalsIgnoreCase(fileExt)) {
					pdfBytes = pdfConversionUtil.convertDocxFileToPdf(file);
				}else {
					// code for .rtf
				}
				 /*below line commented for open office code if something goes wrong you
				 enabel below line for open office*/ 
				//pdfBytes = pdfConversionUtil.convertDocToPdf(pdfBytes);
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
	public String bulkResumeImport(@RequestParam(defaultValue = "") String requirementUUID, ModelMap map) {
		map.addAttribute("requirementUUID", requirementUUID);
		return "resume-upload";
	}

	@RequestMapping("/check-session")
	@ResponseBody
	public String checkSession(HttpServletRequest request) {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if (loginOrgLocation != null)
			return "YES";
		else
			return "NO";
	}

	@RequestMapping("/process-log")
	public String fileProcessLog(HttpServletRequest request, String uploadid, ModelMap map) {
		StringBuilder message = new StringBuilder();
		List<CandidateImportLog> list = candidateService.getCandidateImportLog(uploadid, true);
		for (CandidateImportLog c : list) {
			message.append(c.getMessage());
		}
		map.addAttribute("message", message.toString());
		return "resume-file-process-log";
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
	public String exportLog(String uploadid) {
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
			if (out != null)
				out.close();
		}
		return file == null ? "" : file.getAbsolutePath();
	}

	@RequestMapping("/bulk-resume-import-export-failed")
	@ResponseBody
	public String exportFailedResumes(HttpServletRequest request, String uploadid) {
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		long orgId = 0, orgLocationId = 0;
		if (org != null) {
			orgId = org.getId();
		}
		if (loginOrgLocation != null) {
			orgLocationId = loginOrgLocation.getId();
		}

		return FileUploadUtil.getResumeZipFileName(staticPath, orgId, orgLocationId, uploadid, true);
	}

	@RequestMapping("/bulk-resume-import-export-duplicate")
	@ResponseBody
	public String exportDuplicateResumes(HttpServletRequest request, String uploadid) {
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		long orgId = 0, orgLocationId = 0;
		if (org != null) {
			orgId = org.getId();
		}
		if (loginOrgLocation != null) {
			orgLocationId = loginOrgLocation.getId();
		}

		return FileUploadUtil.getResumeZipFileName(staticPath, orgId, orgLocationId, uploadid, false);
	}

	@RequestMapping(value = "/downloadFile.html")
	public void downloadFile(HttpServletResponse response, String file) {
		try {
			File file1 = new File(URLDecoder.decode(file, "utf-8"));
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + file1.getName());

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
			ex.printStackTrace();
		}
	}

}
