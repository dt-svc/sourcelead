package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class SubmissionsRestController {
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
    
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
			   @RequestParam(value="con", required=false) String con,HttpServletRequest request) throws Exception {
			
    	    String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			requirementSubmissionService.saveRequirementSubmission(jsonObj, user, orgLocations,con);
    	    
    	    
			return "success";
	    
 }
    
    private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }

}
