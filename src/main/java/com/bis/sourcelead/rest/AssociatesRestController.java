package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
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
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.SourceleadResumeParserNew;
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
	private OrgLocationService orgLocationService;
	
		
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
	private SourceleadResumeParserNew sourceleadResumeParser;
	
    @Autowired
    private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
    
    @Autowired
    RequirementService requirementService;
    
    @Autowired
    OrgRelationsService orgRelationsService;
    
    @Autowired
    CandidateEducationService candidateEducationService;
    
    @Autowired
    CandidateEmployersService candidateEmployersService;
    
    @Autowired
    LookUpDataService lookUpDataService;
    
    protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	
	@RequestMapping(value = "/storeAssociate", method = RequestMethod.POST)
	public @ResponseBody String storeAssociate(CandidateDTO candidateDto, HttpServletRequest request) throws Exception {
		
		
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		User user = userService.getUserById(userId);
		Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		OrgLocations orgLocation = orgLocationService.getOrgLocations(orgLocationId);
		
		
		Candidate candidate = candidateService.storeCandidate(candidateDto,orgLocation,user);
		
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
			List<Candidate> candidateIdsList = candidateService.getAllActiveCandidatesByLocation(orgLocations.getId());
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
	   
	   @RequestMapping(value = "/getVendorsByOrgLocation", method = RequestMethod.GET)
		public String getVendorsByOrgLocation(HttpServletRequest request) throws Exception {
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			String resultJson = null;
			List<OrgRelations> vendorList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocations.getId());
			for(OrgRelations vendors:vendorList){
				if(vendors.getLocationId().getId().equals(orgLocations.getId())){
					vendors.setOrgName(vendors.getResponderLocationId().getOrgLocationName());
				}
				if(vendors.getResponderLocationId().getId().equals(orgLocations.getId())){
					vendors.setOrgName(vendors.getLocationId().getOrgLocationName());
				}
				
			}
			
			resultJson = toJSON(vendorList);
			return resultJson;

		}
	   
	   @RequestMapping(value = "/getAllRelationsByOrgLocationAndType", method = RequestMethod.GET)
		public String getAllRelationsByOrgLocationAndType(@RequestParam(value = "type", required = false) String type,
				HttpServletRequest request) throws Exception {
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			String resultJson = null;
			List<OrgRelations> orgRelationsList = orgRelationsService.getClientOrVendorListByOrgLocationIdForImmigration(orgLocations.getId(),type);
			for(OrgRelations orgRelation:orgRelationsList){
				if(orgRelation.getLocationId().getId().equals(orgLocations.getId())){
					orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrgLocationName());
					orgRelation.setImmigrationRelatedId(orgRelation.getResponderLocationId().getId());
				}
				if(orgRelation.getResponderLocationId().getId().equals(orgLocations.getId())){
					orgRelation.setOrgName(orgRelation.getLocationId().getOrgLocationName());
					orgRelation.setImmigrationRelatedId(orgRelation.getLocationId().getId());
				}
				
			}
			
			resultJson = toJSON(orgRelationsList);
			return resultJson;

		}
	   
	   @RequestMapping(value = "/getCandidateDetailsInAssociate")
		public String getCandidateDetailsInAssociate(@RequestParam(value = "candidateId", required = false) Long candidateId,
				HttpServletRequest request) throws Exception {
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			String resultJson = null;
			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			List<String> educationList = candidateEducationService.getCandidateEducationNamesByCandidateId(candidateId);
			if(null != candidate.getOrgRelationId()){
				candidate.setVendorOrgRelationId(candidate.getOrgRelationId().getId().toString());

				if(candidate.getOrgRelationId().getResponderLocationId().getId().equals(orgLocations.getId())){
					candidate.setVendorName(candidate.getOrgRelationId().getLocationId().getOrgLocationName());	
				}
				if(candidate.getOrgRelationId().getLocationId().getId().equals(orgLocations.getId())){
					candidate.setVendorName(candidate.getOrgRelationId().getResponderLocationId().getOrgLocationName());	
				}
				
			}
			if(null != candidate.getSpocId()){
				StringBuilder contactName = new StringBuilder();
				candidate.setVendorSpocId(candidate.getSpocId().getId().toString());
				if(null != candidate.getSpocId().getClientUser().getLastName()){
					candidate.setVendorContactName(contactName.append(candidate.getSpocId().getVendorUser().getFirstName()).append(" ").append(candidate.getSpocId().getClientUser().getLastName()).toString());	
				}else{
					candidate.setVendorContactName(contactName.append(candidate.getSpocId().getVendorUser().getFirstName()).toString());
				}
				
			}
			
			
			
			LinkedHashSet<String> lhs = new LinkedHashSet<String>();
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			for(LookUpData lookUpData :skillsList){
				lhs.add(lookUpData.getName());
			}
			if(null != candidate.getSkillsName() && StringUtils.isNotEmpty(candidate.getSkillsName())){
				String [] csl = candidate.getSkillsName().split(",");
				for(String sl : csl){
					lhs.add(sl);
				}
			}
			candidate.setAllAndCandidateSkills(lhs);
			
			
			LinkedHashSet<String> lhs1 = new LinkedHashSet<String>();
			List<LookUpData> visaList = lookUpDataService.getLookUpDataByCategory(Constants.VISA);
			for(LookUpData lookUpData :visaList){
				lhs1.add(lookUpData.getName());
			}
			if(null != candidate.getVisaType() && StringUtils.isNotEmpty(candidate.getVisaType())){
				String [] csl = candidate.getVisaType().split(",");
				for(String sl : csl){
					lhs1.add(sl);
				}
			}
			candidate.setAllAndCandidateVisa(lhs1);
			
			
			LinkedHashSet<String> eduList = new LinkedHashSet<String>();
			List<LookUpData> eduLookupList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			for(LookUpData lookUpData :eduLookupList){
				eduList.add(lookUpData.getName());
			}
			if(educationList.size() > 0){
				for(String  ed: educationList){
					eduList.add(ed);
				}
			}
			candidate.setAllAndCandidateEducation(eduList);
			
			
		/*	List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
			LinkedHashSet<String> totSkils = new LinkedHashSet<String>();
			if(!educationList.contains(null)){
				totSkils.addAll(skillsList);
				totSkils.addAll(educationList);
				candidate.setAllAndCandidateEducation(totSkils);
			}else{
				candidate.setAllAndCandidateEducation((LinkedHashSet<String>) skillsList);
			}*/
			
			List<CandidateEmployers>   employeeList= candidateEmployersService.getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateId, orgLocations.getId());
			if(null != employeeList && employeeList.size() > 0){
				StringBuilder employees = new StringBuilder();
				for(CandidateEmployers employe : employeeList){
					candidate.setEmployeList(employees.append(employe.getName()).append(",").toString());
				}
				candidate.setEmployeList(candidate.getEmployeList().substring(0, candidate.getEmployeList().length()-1));
			}
			
			StringBuilder education = new StringBuilder();
			if (educationList != null && educationList.size() > 0) {
				for(String educationName : educationList){
					education.append(educationName).append(", ");
				}
				
				candidate.setCandidateEdu(education.substring(0,education.length()-2).toString());
				
			}
			resultJson = toJSON(candidate);
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
