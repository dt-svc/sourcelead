package com.bis.sourcelead.rest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class SubmissionsRestController {
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private SubmissionTemplateService submissionTemplateService;
	
	@Autowired
	private RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
    
    @RequestMapping(value = "/getCandidateResumes")
    public String getCandidateResumes(@RequestParam(value="candidateId", required=false) Long candidateId,HttpServletRequest request) throws Exception {
    	
    	String resultJson = null;
    	List<CandidateResumes> resumesList = candidateResumesService.getCandidateResumesListByCandidateIdAndStatus(candidateId, Constants.CANDIDATE_ACTIVE);
    	resultJson = toJSON(resumesList);
		return resultJson;
		
	}
    
    
    @RequestMapping(value = "/getCandidateDetails")
    public String getCandidateDetails(@RequestParam(value="candidateId", required=false) Long candidateId,HttpServletRequest request) throws Exception {
    	
    	String resultJson = null;
    	Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
    	resultJson = toJSON(candidate);
		return resultJson;
		
	}
    
    
    @RequestMapping(value = "/getRequirmentOrgSubmissionTemplate" , method = RequestMethod.GET)
	   public String getRequirmentOrgSubmissionTemplate(@RequestParam(value="requirementId", required=false) Long requirementId) throws Exception {
			
		    Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		    
			return requirement.getSubmissionTemplateJsonString();
	    
    }
    
    
    @RequestMapping(value = "/saveAddApplicant" , method = RequestMethod.POST)
	   public String saveAddApplicant(@RequestParam(value="jsonData", required=false) String json,
			   @RequestParam(value="con", required=false) String con,MultipartHttpServletRequest request,
			   @RequestParam(value="locationMapFile", required=false) CommonsMultipartFile locationMapFileData) throws Exception {
			
    	    String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			
			
			MultipartFile multiFile = null;
			Iterator<String> itrator = request.getFileNames();
			if(itrator.hasNext()){
		        multiFile = request.getFile(itrator.next());
			}
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			requirementSubmissionService.saveRequirementSubmission(jsonObj, user, orgLocations,con,multiFile);
    	    
    	    
			return "success";
	    
 }
    
    @RequestMapping(value = "/getOrgSubmissionTemplatesList", method = RequestMethod.POST)
	@ResponseBody String getOrgSubmissionTemplatesList(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<SubmissionTemplate> submissiontTemplateList  = submissionTemplateService.getOrgSubmissionTemplatesByOrgLocationId(orgLocations.getId());
		
		return commonUtil.toJSON(submissiontTemplateList);
	}
    


    @RequestMapping(value = "/saveOrgSubmissionTemplate" , method = RequestMethod.POST)
	   public String saveClientSubmissionTemplate(@RequestParam(value="jsonData", required=false) String json,
			   @RequestParam(value="templateId", required=false) String templateId,
			   @RequestParam(value="jsonStringData", required=false) String jsonStringData,HttpServletRequest request) throws Exception {
			
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");		
		    
		    String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			
			
		   try {
			   SubmissionTemplate submissionTemplate = null;
			   if(!isNullOrEmpty(templateId)){
				   submissionTemplate = submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
				}else{
					submissionTemplate = new SubmissionTemplate();
				}
			   
			   submissionTemplate.setTemplateName(jsonObj.getString("templateName"));
			   submissionTemplate.setDescription(jsonObj.getString("description"));
			   submissionTemplate.setOrgLocationId(orgLocations);
			   submissionTemplate.setJsonString(jsonStringData);
			   submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
			   submissionTemplate.setCreatedBy(user.getUserCode());
			   submissionTemplate.setCreatedDate(new Date());
			   submissionTemplateService.addSubmissionTemplate(submissionTemplate);
				
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			   return "home";
	    
	   }
    
    
    @RequestMapping(value = "/getSubmissionCandidates" , method = RequestMethod.POST)
	   public String getSubmissionCandidates(@RequestParam(value="requirementId", required=false) Long requirementId,
			   @RequestParam(value="status", required=false) String status,HttpServletRequest request) throws Exception {
			
    	 String resultJson = null;
    	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
     	 List<RequirementSubmission> shortlistedList = requirementSubmissionService.getAllSubmissionListByRequirementIdAndSubmissionStage(orgLocations.getId(), requirementId,status);    
     	 ArrayList<RequirementSubmission> candidatesList = new ArrayList<RequirementSubmission>();
     	 
     	 for (RequirementSubmission requirementSubmission : shortlistedList) {
			CandidateResumes candidateResumes = candidateResumesService.getCandidateResumeByResumeId(requirementSubmission.getCandidateResumes().getId());
			RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
			requirementSubmission.setCandidateName(requirementSubmissionTemplateFields.getFirstName()+" "+requirementSubmissionTemplateFields.getLastName());
			requirementSubmission.setCandidateEmail(requirementSubmissionTemplateFields.getEmailId());
			requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
			requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
			requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
			if(requirementSubmission.getCreatedDate() != null){
				requirementSubmission.setCreatedDateStr(Constants.DF_YMD.format(requirementSubmission.getCreatedDate()));
			}
			User userVal = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
			requirementSubmission.setUserName(userVal.getFirstName()+" "+userVal.getLastName());
			requirementSubmission.setResumeName(candidateResumes.getFileName());
			candidatesList.add(requirementSubmission);
		  }
     	 resultJson = toJSON(candidatesList);
		 return resultJson;
	    
    }
    
    
    @RequestMapping(value = "/submitShortlistedToClient" , method = RequestMethod.POST)
	   public String submitShortlistedToClient(@RequestParam(value="requirementId", required=false) Long requirementId,
			   @RequestParam(value="submissionsList", required=false) String submissionsList,HttpServletRequest request) throws Exception {
    	
    	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
    	
    	 requirementSubmissionService.storeClientSubmission(requirementId, user, orgLocations, submissionsList);
    	 
    	/* Workbook createExcelworkbook =  new XSSFWorkbook();
 		 Sheet createExcelsheet = createExcelworkbook.createSheet("submissions");
 		 createExcelsheet = setExcelHeader(submissionsList,createExcelsheet);
 		
    	 String[] submissionId = submissionsList.split(",");
    	 for(int i=0; i < submissionId.length; i++){
    		 RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId[i]));
    		 String data = submission.getSubmissionTemplateJsonStringValue();
    		 try {
    		     // create the json array from String rules
    		     JSONArray jsonRules = new JSONArray(data);
    		     System.out.println(jsonRules.length());
    		     // iterate over the rules 
    		     Row writeRow = createExcelsheet.createRow(i+1);
    		     for (int j=0; j<jsonRules.length();j++){
    		         JSONObject obj = (JSONObject) jsonRules.get(j);
    		         createXlsSheet(createExcelsheet,writeRow,j,obj.getString("value"));
    		     }
    		     
    		 } catch (JSONException e){
    		     e.printStackTrace();
    		 }
    	 }
    	 
    	 
    	 DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    	 
    	 String data = "/home/bis88/xls/Submission"+SourceLeadRandomCodeHelper.generateSubmissionXlsRandomCode()+""+dateFormat.format(new Date())+".xls";
    	 FileOutputStream fos =  new FileOutputStream(data);
		 createExcelworkbook.write(fos);
		 fos.close();
		 
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setToEmail("ivenkat07@gmail.com");
		 emailMessage.setSubject("hiiiiiii");
		 
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipientList.add(emailRecipient);
		 
		
		 ArrayList<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
		 EmailAttachment emailAttachment = new EmailAttachment();
		 emailAttachment.setName(data);
		 attachmentList.add(emailAttachment);
		 
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,attachmentList);*/
    	
    	return "success";
    }
    
    
    @RequestMapping(value = "/candidateShortList" , method = RequestMethod.POST)
	   public String candidateShortList(@RequestParam(value="requirementId", required=false) Long requirementId,
			   @RequestParam(value="submissionsList", required=false) String submissionsList,HttpServletRequest request) throws Exception {
 	
 	 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
 	 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
 	
 	 
 	 String[] submissionId = submissionsList.split(",");
 	 
 	 for(int i=0; i < submissionId.length; i++){
 		 RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId[i]));
 		 submission.setSubmissionStatus(ApplicantSubmissionStatus.SHORTLIST);
 		 submission.setUpdatedBy(user.getUserCode());
 		 submission.setUpdatedDate(new Date());
 		 requirementSubmissionService.storeRequirementSubmission(submission);
 	 }
 	
 	return "success";
 }
    
    
    @RequestMapping(value = "/getSubmissionDetails" , method = RequestMethod.POST)
	   public String getSubmissionDetails(@RequestParam(value="submissionId", required=false) Long submissionId,HttpServletRequest request) throws Exception {
    	 
    	 String resultJson = null;
    	 
    	 RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
    	 
    	 resultJson = toJSON(submission);
		 return resultJson;
    	
    }
    
    
    
    private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }

	/**
	 * check given string empty or null
	 * @param str
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	
   private Sheet setExcelHeader(String submissionsList,Sheet createExcelsheet) throws Exception {
    	   
    	    Row excelHeader = createExcelsheet.createRow(0);
    	    String[] submissionId = submissionsList.split(",");
      	    for(int i=0; i < submissionId.length; i++){
      		 RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId[i]));
      		 String data = submission.getSubmissionTemplateJsonStringValue();
      		 try {
      		     JSONArray jsonRules = new JSONArray(data);
      		     System.out.println(jsonRules.length());
      		     for (int j=0; j<jsonRules.length();j++){
      		         JSONObject obj = (JSONObject) jsonRules.get(j);
      		         excelHeader.createCell(j).setCellValue(obj.getString("label"));
      		     }
      		 } catch (JSONException e){
      		     e.printStackTrace();
      		 }
      	 }
		
      	  return createExcelsheet;
	}
   
   private void createXlsSheet(Sheet createExcelsheet,Row writeRow,int column,String value) throws IOException {
	   createExcelsheet.setColumnWidth(column, 5000);
	   if(!isNullOrEmpty(value)){ 
			writeRow.createCell(column).setCellValue(value);
		} else {
			 writeRow.createCell(column).setCellValue("No Data");
		}
	   
   }
	
}

