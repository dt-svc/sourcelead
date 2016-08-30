package com.bis.sourcelead.rest;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;



	/**
	 * Handles requests for the Candidate List Service.
	 * 
	 * @author Sammeta David 
	 * @date 26th August 2016
	 *
	 */
	
	@RestController
	public class CandidateListRestController {
	
	
		@Value("${sourceleadUrl}")
		private String sourceleadUrl;
		
		
		 @Autowired
		 private OrgService orgService;
		 
		 @Autowired
		 private OrgLocationService orgLocationService;
		
		 @Autowired
		 CommonUtil commonUtil;
		 

		 @Autowired
		 CandidateService candidateService;
		 
		
		 /**
		 * @author David and sarath kumar
		 * @since  aug 19 2016
		 * @version 0.0.1
		 * 
		 * This method is used to display employee Timesheet.
		 * this method is called at onload when click on timesheet left menu
		 * 
		 * @param user This is the first paramter to newTimesheet method
		 * @param navigation This is the second paramter to newTimesheet method
		 * @param startDate This is the third paramter to newTimesheet method
		 *  
		 * @return string 
		 * 
		 * @throws NullPointer
		 */
		 
		 @RequestMapping(value = "/getCandidatesListByOrgLocationID")
		 public String getCandidatesListByOrgLocationID(HttpServletRequest request) throws Exception {

			String resultJson;
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<Candidate>   candidatesList = candidateService.getCandidatesListByOrgLocationID(loginOrgLocation.getId());
			resultJson = commonUtil.toJSON(candidatesList);
			return resultJson;
		 }
		 
		 
				/**
				 *  This Method is for getting candidate by candidate ID
				 *   @author David Raju
				 * @param candidateId
				 * @param request
				 * @return Candidate
				 * @throws Exception
				 */
			 @RequestMapping(value = "/getCandidateById" , method = RequestMethod.POST)
			 public @ResponseBody  String getCandidateById(@RequestParam(value="candidateId", required=false) String  candidateId,HttpServletRequest request) throws Exception {
					String resultJson;
				    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
					User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
					Org loginOrgCode = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
					 
					Candidate can = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.valueOf(candidateId),loginOrgLocation.getId());
					
					String skillsList = "";
					Set<CandidateSkills> candidateSkills = can.getCandidateSkills();
					int i = 0;
					for(CandidateSkills skilss: candidateSkills){
						if(i <= 5){
							skillsList = skillsList+skilss.getName();
							if(i != candidateSkills.size()-1){
								skillsList = skillsList+",";
							}
						}
						i++;
					}
					
					can.setSkillsList(skillsList);
					resultJson = commonUtil.toJSON(can);
					return resultJson;
	  }
			 

	   @RequestMapping(value = "/deleteCandidateImage" , method = RequestMethod.GET)
	   public String deleteCandidateImage(@RequestParam(value="candidateId", required=false) String  candidateId,HttpServletRequest request) throws Exception {
		   
		   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		   Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.parseLong(candidateId), loginOrgLocation.getId());
		   candidate.setDocFile(null);
		   candidate.setFileContentType(null);
		   candidate.setFileName(null);
		   candidateService.addCandidate(candidate);
		   return "success";
		   
	   }
		 
		 
		 
		
	}
