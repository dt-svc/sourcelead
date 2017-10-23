package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.CandidateCertificationDTO;
import com.bis.sourcelead.dto.CandidateResumeDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateCertificationsService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserService;
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
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	 
	
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
	 //@PreAuthorize("hasAnyRole('ROLE_ADD_CANDIDATE_CERTIFICATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_ADD_ASSOCIATE_CERTIFICATION_DETAILS')")
	 @RequestMapping(value = "/storeCandidateCertifications", method = RequestMethod.POST)
     public @ResponseBody String storeCandidateCertifications(CandidateCertificationDTO candidateCertificationDTO,@RequestParam(value="candidateId", required=false) Long candidateId ,HttpServletRequest request) throws Exception {
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		candidateCertificationDTO.setDateFormat(loginOrgLocation.getDateFormat());
		candidateCertificationsService.storeCandidateCertifications(candidateCertificationDTO,users,loginOrgLocation,candidateId);
		Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
		return "success";
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
	//@PreAuthorize("hasAnyRole('ROLE_VIEW_CANDIDATE_CERTIFICATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES')")
	 @RequestMapping(value = "/getCandidateCertificationListByCandidateIdAndOrgLocationID") 
	 public ResponseList<CandidateCertificationDTO> getCandidateCertificationListByCandidateIdAndOrgLocationID(@RequestParam(value = "candidateId", required = false) String candidateId, HttpServletRequest request) throws Exception {

		 ResponseList<CandidateCertificationDTO> certificationList = new ResponseList<>();
		 String msg = "";
		String resultJson;
		List<CandidateCertificationDTO> candidateCertificationDTOList = new ArrayList<>();
		
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<CandidateCertifications>   candidateCertificationsList = candidateCertificationsService.getCandidateCertificationListByCandidateIdAndOrgLocationID(Long.parseLong(candidateId) , loginOrgLocation.getId());
		
		for(CandidateCertifications candidateCertifications : candidateCertificationsList){
			CandidateCertificationDTO candidateCertificationDTO = new CandidateCertificationDTO();
			
            candidateCertificationDTO.setId(candidateCertifications.getId());
			candidateCertificationDTO.setName(candidateCertifications.getName());
			candidateCertificationDTO.setProvider(candidateCertifications.getProvider());
		    candidateCertificationDTO.setScore(candidateCertifications.getScore());
		    candidateCertificationDTO.setScoreType(candidateCertifications.getScoreType());
		    candidateCertificationDTO.setDescription(candidateCertifications.getDescription());
		    candidateCertificationDTO.setCandidatevalidFromDateToString(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), candidateCertifications.getValidFrom()));
		    candidateCertificationDTO.setCandidatevalidToDateToString(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), candidateCertifications.getValidTo()));
		    candidateCertificationDTOList.add(candidateCertificationDTO);
			
			/*msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "addCertification");
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
			*/
		}
		certificationList.setData(candidateCertificationDTOList);
		return certificationList;
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
	// @PreAuthorize("hasAnyRole('ROLE_VIEW_DELETE_CANDIDATE_CERTIFICATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_DELETE_ASSOCIATE_CERTIFICATION_DETAILS')")
	   @RequestMapping(value = "/removeCandidateCertificateBycandidateIdandcandidateCertificateId")
	   public String removeCandidateCertificateBycandidateIdandcandidateCertificateId(@RequestParam(value = "loginUserId", required = false) Long loginUserId, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			   																		@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateCertificationId", required = false) Long candidateCertificationId) throws Exception {

			String resultJson;
			User user = userService.getUserById(loginUserId);
			OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
			 CandidateCertifications  candidatesCertificate = candidateCertificationsService.removeCandidateCertificateBycandidateIdandcandidateCertificateId(candidateId, candidateCertificationId, user, orgLocations);
			 resultJson = commonUtil.toJSON(candidatesCertificate);
			 //SourceLeadSessionManager.setSessionAttribute(request, "deleteCertification", "Certfification deleted");

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
	  // @PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_CANDIDATE_CERTIFICATION','ROLE_CROWDSOURCING_PROFILE','ROLE_CROWDSOURCING_CANDIDATES','ROLE_VIEW_EDIT_ASSOCIATE_CERTIFICATION_DETAILS')")
	   @RequestMapping(value = "/getCandidateCertificateBycandidateIdandCertificateId")
		public String getCandidateCertificateBycandidateIdandCertificateId(@RequestParam(value = "candidateId", required = false) Long candidateId,@RequestParam(value = "candidateCertificationId", required = false) Long candidateCertificationId,HttpServletRequest request) throws Exception {

			String resultJson;
			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			OrgLocations orgLocations = orgLocationService.getOrgLocations(candidate.getOrgLocationId().getId());
			//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
			CandidateCertifications  candidateCertificationsList = candidateCertificationsService.getCandidateCertificateBycandidateIdandCertificateId(candidateId, candidateCertificationId);
			
			if(candidateCertificationsList.getValidFrom() != null){
				candidateCertificationsList.setCandidatevalidFromDateToString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidateCertificationsList.getValidFrom())); 
			 }
			 if(candidateCertificationsList.getValidTo() != null){
				 candidateCertificationsList.setCandidatevalidToDateToString(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),candidateCertificationsList.getValidTo())); 
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
	     public @ResponseBody String updateCandidateCertificationsBycandidateIdandcandidateCertificateId(CandidateCertificationDTO candidateCertificationDTO,@RequestParam(value="candidateId", required=false) Long candidateId ,HttpServletRequest request) throws Exception {

			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			candidateCertificationsService.storeCandidateCertifications(candidateCertificationDTO,users,loginOrgLocation,candidateId);
			//SourceLeadSessionManager.setSessionAttribute(request, "editCertification", "Certification details updated ");
			return "success";
		 }

}