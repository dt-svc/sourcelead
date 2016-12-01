package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateCertificationsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * @author Shivayogi
 *
 */
@RestController
public class CandidateCertificationsRestController {
	
	@Autowired
	private CandidateCertificationsService candidateCertificationsService;
	
	@Autowired
	CommonUtil commonUtil;
	 
	
	/**
	 * Handles CandidateCartifications  store
	 * 
	 * @author Ajith Matta
	 * 
	 * @param json
	 * 
	 * @param request
	 * 
	 * @return ResponseBody
	 * 
	 * @throws Exception
	 */
	 @RequestMapping(value = "/storeCandidateCertifications", method = RequestMethod.POST)
     public @ResponseBody String storeCandidateCertifications(@RequestParam(value = "json", required = false) String json,@RequestParam(value="candidateId", required=false) Long candidateId ,HttpServletRequest request) throws Exception {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		String json1 = "{"+json+"}";
		JSONObject jsonObject = new JSONObject(json1);
		candidateCertificationsService.storeCandidateCertifications(jsonObject,users,loginOrgLocation,candidateId);
		
		SourceLeadSessionManager.setSessionAttribute(request, "addCertification", "New Certification added for Candidate ");
		
		return json;
	 }
	
	

	 /**
	   * This Method is to getCandidateCertificationListByCandidateIdAndOrgLocationID
	   * 
	   * @author Sammeta David Raju
	   * 
	   * @param candidateId
	   * 
	   * @return String
	   * 
	   * @throws Exception
	   */
	 @RequestMapping(value = "/getCandidateCertificationListByCandidateIdAndOrgLocationID") 
	 public String getCandidateCertificationListByCandidateIdAndOrgLocationID(@RequestParam(value = "candidateId", required = false) String candidateId, HttpServletRequest request) throws Exception {

		 String msg = "";
		String resultJson;
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<CandidateCertifications>   candidateCertificationsList = candidateCertificationsService.getCandidateCertificationListByCandidateIdAndOrgLocationID(Long.parseLong(candidateId) , loginOrgLocation.getId());
		
		for(CandidateCertifications candidateCertifications : candidateCertificationsList){

			msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "addCertification");
			if(msg == null){
				msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "editCertification");
			}
			if(msg == null){
				msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "deleteCertification");
			}
			if(msg != null && msg != ""){
				candidateCertifications.setMsg(msg);
			}
				 SourceLeadSessionManager.removeSessionAttribute(request, "deleteCertification");
			    SourceLeadSessionManager.removeSessionAttribute(request, "addCertification");
			    SourceLeadSessionManager.removeSessionAttribute(request, "editCertification");
			
		}
		resultJson = commonUtil.toJSON(candidateCertificationsList);
		return resultJson;
	 }
	 
	 
	 /**
	   * This Method is to remove CandidateEmployer By candidateId and CandidateEmployerID 
	   * @author Sammeta David Raju
	   * 
	   * @param candidateemployersId
	   * @param candidateId
	   * @param request
	   * 
	   * @return String
	   * 
	   * @throws Exception
	   */
	   @RequestMapping(value = "/removeCandidateCertificateBycandidateIdandcandidateCertificateId")
	   public String removeCandidateCertificateBycandidateIdandcandidateCertificateId(@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateCertificationId", required = false) Long candidateCertificationId,HttpServletRequest request) throws Exception {

			String resultJson;

			 CandidateCertifications  candidatesCertificate = candidateCertificationsService.removeCandidateCertificateBycandidateIdandcandidateCertificateId(candidateId, candidateCertificationId);
			 resultJson = commonUtil.toJSON(candidatesCertificate);
				SourceLeadSessionManager.setSessionAttribute(request, "deleteCertification", "Certfification deleted");

			 return resultJson;
		}
	   
	   /**
		   * This Method is to remove CandidateEmployer By candidateId and CandidateEmployerID 
		   * @author Sammeta David Raju
		   * 
		   * @param candidateemployersId
		   * @param candidateId
		   * @param request
		   * 
		   * @return String
		   * 
		   * @throws Exception
		   */
	   
	   @RequestMapping(value = "/getCandidateCertificateBycandidateIdandCertificateId")
		public String getCandidateCertificateBycandidateIdandCertificateId(@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateCertificationId", required = false) Long candidateCertificationId,HttpServletRequest request) throws Exception {

			String resultJson;

			CandidateCertifications  candidateCertificationsList = candidateCertificationsService.getCandidateCertificateBycandidateIdandCertificateId(candidateId, candidateCertificationId);
			
			if(candidateCertificationsList.getValidFrom() != null){
				candidateCertificationsList.setCandidatevalidFromDateToString(Constants.DF_YMD.format(candidateCertificationsList.getValidFrom())); 
			 }
			 if(candidateCertificationsList.getValidTo() != null){
				 candidateCertificationsList.setCandidatevalidToDateToString(Constants.DF_YMD.format(candidateCertificationsList.getValidTo())); 
			 }
			resultJson = commonUtil.toJSON(candidateCertificationsList);
			 return resultJson;
		}
	   
	   /**
		 * Handles CandidateCartifications  update
		 * 
		 * @author Ajith Matta
		 * 
		 * @param json
		 * 
		 * @param request
		 * 
		 * @return ResponseBody
		 * 
		 * @throws Exception
		 */
		 @RequestMapping(value = "/updateCandidateCertificationsBycandidateIdandcandidateCertificateId", method = RequestMethod.POST)
	     public @ResponseBody String updateCandidateCertificationsBycandidateIdandcandidateCertificateId(@RequestParam(value = "json", required = false) String json,@RequestParam(value="candidateId", required=false) Long candidateId ,HttpServletRequest request) throws Exception {

			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			String json1 = "{"+json+"}";
			JSONObject jsonObject = new JSONObject(json1);
			candidateCertificationsService.storeCandidateCertifications(jsonObject,users,loginOrgLocation,candidateId);
			SourceLeadSessionManager.setSessionAttribute(request, "editCertification", "Certification details updated ");

			return json;
		 }

}
