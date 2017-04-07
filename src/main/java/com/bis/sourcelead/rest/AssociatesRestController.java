package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PrimarySkillUtil;
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
public class AssociatesRestController {

	private static final Logger logger = LoggerFactory.getLogger(AssociatesRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private CandidateResumesService  candidateResumesService;
	
	/*@Autowired
	private CandidateSkillsService candidateSkillsService;
	*/
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	RequirementOffersService requirementOffersService;
	
	@Autowired
	RequirementInterviewService requirementInterviewService;
	
	@Autowired
	RequirementInterviewerService requirementInterviewerService; 
	
	@Autowired
	RequirementInterviewHistoryService requirementInterviewHistoryService;
	
	@Autowired
	RequirementOfferHistoryService requirementOfferHistoryService; 
	
	@Autowired
	private SourceleadResumeParser sourceleadResumeParser;
	
    @Autowired
    private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
    
    @Autowired
    RequirementService requirementService;
    
    @Autowired
    OrgRelationsService orgRelationsService;
    
    protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	
	@RequestMapping(value = "/storeAssociate", method = RequestMethod.POST)
	public @ResponseBody String storeAssociate(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {
		
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
		
		if(candidate.getCandidateType().equalsIgnoreCase("Candidate")){
			StringBuilder candidateCreateMsg = new StringBuilder();
		    SourceLeadSessionManager.setSessionAttribute(request, "candidateCreateMsg", candidateCreateMsg.append("Success !! ").append(candidate.getFirstName()).append(" Candidate Added").toString());
		}
		else{
			StringBuilder consultantCreateMsg = new StringBuilder();
			SourceLeadSessionManager.setSessionAttribute(request, "consultantCreateMsg", consultantCreateMsg.append("Success !! ").append(candidate.getFirstName()).append(" Consultant Added").toString());
		}
		return "success";
	}
	

	 
	 
	   @RequestMapping(value = "/getAssociateList", method = RequestMethod.POST)
		public @ResponseBody String getCandidatesList(
			   @RequestParam(value = "candidateType", required = false) String candidateType, HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<Candidate> candidateIdsList = candidateService.getAllCandidatesByLocation(orgLocations.getId());
			resultJson = toJSON(candidateIdsList);
			return resultJson;
		}
	 
	   @RequestMapping(value = "/getAssociateResumes")
		public String getCandidateResumes(@RequestParam(value = "candidateId", required = false) Long candidateId,
				HttpServletRequest request) throws Exception {

			String resultJson = null;
			List<CandidateResumes> resumesList = candidateResumesService
					.getCandidateResumesListByCandidateIdAndStatus(candidateId, Constants.CANDIDATE_ACTIVE);
			resultJson = toJSON(resumesList);
			return resultJson;

		}
	   
	   @RequestMapping(value = "/getVendorsByOrgLocation")
		public String getVendorsByOrgLocation(@RequestParam(value = "candidateId", required = false) Long candidateId,
				HttpServletRequest request) throws Exception {
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			String resultJson = null;
			List<OrgRelations> vendorList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
			for(OrgRelations vendors:vendorList){
				vendors.setOrgName(vendors.getResponderLocationId().getOrg().getOrgName());
			}
			
			resultJson = toJSON(vendorList);
			return resultJson;

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
