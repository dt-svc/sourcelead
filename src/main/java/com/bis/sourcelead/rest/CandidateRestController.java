package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateSkillsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.SourceleadResumeParser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
/**
 * Handles requests for the Candidate service.
 * 
 * @author Prasad K
 * @date 30th June 2016
 *
 */

@RestController
public class CandidateRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private CandidateResumesService  candidateResumesService;
	
	@Autowired
	
	private CandidateSkillsService candidateSkillsService;
	
	/**
	 * @author prasad k
	 * @since  sep 1 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save candidate in candidate table.
	 * 1.internalley store/save in candidateEducation in candidateEducation table .
	 * 2.internalley store/save in candidateSkills in candidateSkills table .
	 * 3.internalley store/save in candidateResumes in candidateResumes table .
	 * 4.internalley store/save in candidateEmployers in candidateEmployers table .
	 *  
	 *  
	 * 
	 * @param jsonObj This is the first paramter to storeCandidate method
	 * @param orgLocations This is the second paramter to storeCandidate method
	 * @param user This is the third paramter to storeCandidate method
	 * @param multiFile This is the fourth paramter to storeCandidate method
	 * 
	 * @return Candidate 
	 * 
	 * @throws NullPointer
	*/
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_VIEW_CANDIDATE','ROLE_VIEW_EDIT_CANDIDATE_EDUCATION')") 
	@RequestMapping(value = "/storeCandidate", method = RequestMethod.POST)
	public @ResponseBody String storeCandidate(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Candidate candidate = candidateService.storeCandidate(jsonObj,orgLocation,user,multiFile);
		SourceLeadSessionManager.setSessionAttribute(request, "candidateCreateMsg", "Success !!"+candidate.getFirstName()+" "+candidate.getLastName()+" Candidate Added");
		return "success";
	}
	
	@RequestMapping(value = "/parseResume", method = RequestMethod.POST)
	public @ResponseBody String parseResume(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request,
			 @RequestParam(value="locationMapFile", required=false) CommonsMultipartFile locationMapFileData) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		SourceleadResumeParser resumeParser = new SourceleadResumeParser();
		String resumeDetails = null;
		if(multiFile != null){
			resumeDetails = resumeParser.getCandidateDetailsFromResume(multiFile.getBytes(),request);
		}
		return resumeDetails;
	}
	 /**
	  * To  validate user email
	  * @param EmailId
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/validateCandidateEmail" , method = RequestMethod.POST)
	 public @ResponseBody  String validateCandidateEmail(@RequestParam(value="email", required=false) String email,HttpServletRequest request) throws Exception {
		 	
		 	String result = null;
		 	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	Candidate candidate = candidateService.findByCandidateEmail(email,orgLocation.getId());
		 	
		 	if (candidate != null) {
				result = "true";
			}else{
				result = "false";
			}
			return result;
	   }
	 
	 @RequestMapping(value = "/validateSkillName" , method = RequestMethod.POST)
	 public @ResponseBody  Boolean validateSkillName(@RequestParam(value="skillName", required=false) String skillName, @RequestParam(value="candidateId", required=false) String candidateId, HttpServletRequest request) throws Exception {
		 	Boolean flag = false;
		 	CandidateSkills candidateSkills = candidateSkillsService.getSkillsByCandidateIdSkillName(candidateId,skillName);
		    if(candidateSkills != null){
		    	flag = candidateSkillsService.validateSkillName(skillName);
		    }
			return flag;
	   }
	 
	 
	 @RequestMapping(value = "/getCandidatesList", method = RequestMethod.POST)
		public @ResponseBody String getCandidatesList(
				@RequestParam(value = "requirementId", required = false) Long requirementId, HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			List<Candidate> candidatesList = candidateService.getCandidatesByOrgLocationID(orgLocations.getId(),requirementId);
			
			resultJson = toJSON(candidatesList);
			return resultJson;
		}
	 
	 /**
		 * @author sarath kumar
		 * @since  Dec 1 2016
		 * @version 0.0.1
		 * 
		 * 
		 * @throws NullPointer
		*/
	 @RequestMapping(value = "/checkCandidateState", method = RequestMethod.POST)
	 public @ResponseBody String checkCandidateState(
				@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request) throws Exception {
			
		    String resultJson = null;
			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			Boolean isCandidateActive = false;
			if(candidate != null){
				 if(candidate.getStatus() == 1){
					 isCandidateActive = true;
				 }
			}
			resultJson = toJSON(isCandidateActive);
			return resultJson;
		}
	 
	 
	 /**
	  * @author Rajashekar
	  * @param candidateId
	  * @param request
	  * @return string
	  * @throws Exception
	  */
	 @RequestMapping(value = "/downloadCandidateResume" , method = RequestMethod.GET)
	 public @ResponseBody  void downloadCandidateResume(@RequestParam(value="candidateId", required=false) Long candidateId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		 	CandidateResumes candidateResume = candidateResumesService.getCandidateLatesResumes(candidateId, 1);
		 	
		 	 response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition",
		                "attachment;filename="+candidateResume.getFileName());
			    
				InputStream inputStream = new ByteArrayInputStream(candidateResume.getDocFile());
				try {
					OutputStream outStream = response.getOutputStream();

					byte[] buffer = new byte[4096];
					int bytesRead = -1;

					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}

					inputStream.close();
					outStream.close();

				} catch (FileNotFoundException e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				} 
			/*byte[] pdfData = candidateResume.getDocFile();
			FileOutputStream fileOuputStream =null;
			try { 
			     fileOuputStream = new FileOutputStream(candidateResume.getFileName()); 
			    fileOuputStream.write(pdfData);
			 } finally {
			    fileOuputStream.close();
			 }
			String mimeType = "application/msword";
	        if (mimeType == null) {        
	            mimeType = "application/octet-stream";
	        }              
	        response.setContentType(mimeType);
	        String headerKey = "Content-Disposition";
	         String headerValue = String.format("attachment; filename=\"%s\"", candidateResume.getFileName());
	         response.setHeader(headerKey, headerValue);
	        InputStream inputStream = new ByteArrayInputStream(candidateResume.getDocFile());
			try {
				OutputStream outStream = response.getOutputStream();
	            
	            byte[] buffer = new byte[500];
	            int bytesRead = -1;
	             
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outStream.write(buffer, 0, bytesRead);
	            }
	             
	            inputStream.close();
	            outStream.close();   
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
	   }
	 
	 
	 public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
}
