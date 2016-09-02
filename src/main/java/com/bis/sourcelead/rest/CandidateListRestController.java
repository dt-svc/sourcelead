package com.bis.sourcelead.rest;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateSkillsService;
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
	 
	 
	 @Autowired
	 CandidateSkillsService candidateSkillsService;
	 
		
	 /**
	   * @author David and sarath kumar
	   * @since  aug 30 2016
	   * @version 0.0.1
	   * 
	   * This method is used to for getting CandidatesList By OrgLocationID
	   * this method is called at onload when click on timesheet left menu
	   * 
	   * @param user This is the first parameter to getCandidatesListByOrgLocationID method
	   * @param navigation This is the second parameter to getCandidatesListByOrgLocationID method
	   * @param startDate This is the third parameter to getCandidatesListByOrgLocationID method
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
	   * This Method is for getting candidate by candidate ID
	   * @author David Raju
	   * 
	   * @param candidateId This is the first parameter to getCandidateById method
	   * @param request This is the second parameter to getCandidateById method
	   * 
	   * @return Candidate This is the third parameter to getCandidateById method
	   * 
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
			 

	 /**
	   *  This Method is for deleting CandidateImage
	   *  
	   * @author sarath kumar
	   * 
	   * @param candidateId This is the first parameter to deleteCandidateImage method
	   * @param request    This is the second parameter to deleteCandidateImage method
	   * 
	   * @return String
	   * 
	   * @throws NullPointerException
	   */
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
	   
	  /**
		*  This Method is for getting CandidatesSkills By candidateID
		*  
		* @author sarath kumar
		* 
		* @param candidateId  This is the first parameter to getCandidatesSkillsBycandidateID method
		* @param request      This is the second parameter to getCandidatesSkillsBycandidateID method
		* 
		* @return String
		* 
		* @throws NullPointerException
		*/
	  @RequestMapping(value = "/getCandidatesSkillsBycandidateID")
	  public String getCandidatesSkillsBycandidateID(@RequestParam(value="candidateId", required=false) String  candidateId,HttpServletRequest request) throws Exception {
	
			String resultJson;
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<CandidateSkills>  candidateSkillsList =  candidateSkillsService.getCandidateSkillsByCandidateId(candidateId);
			resultJson = commonUtil.toJSON(candidateSkillsList);
			return resultJson;
			
	   }
		 
	   /**
		 *  This Method is for saving Skils By candidateID
		 *  
		 * @author sarath kumar
		 * @param candidateId This is the first parameter to saveSkilsBycandidateID method
		 * @param skillName   This is the second parameter to saveSkilsBycandidateID method
		 * @param request     This is the third parameter to saveSkilsBycandidateID method
		 * 
		 * @return String
		 * 
		 * @throws NullPointerException
		 */
	   @RequestMapping(value = "/saveSkilsBycandidateID")
	   public String saveSkilsBycandidateID(@RequestParam(value="candidateId", required=false) String  candidateId,@RequestParam(value="skillName", required=false) String  skillName,HttpServletRequest request) throws Exception {

		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			String resultJson;
			CandidateSkills cs = new CandidateSkills();
			
			Candidate candidate = new Candidate();
			candidate.setId(Long.parseLong(candidateId));
			cs.setCandidateId(candidate);
			cs.setOrgLocationID(loginOrgLocation);
			cs.setName(skillName);
			candidateSkillsService.storeCandidateSkills(cs);
			return "success";
			
		 }
	   
	   /**
		 * This Method is for saving  Candidate Profile
		 * 
		 * @author sarath kumar
		 * 
		 * @param candidateId  This is the first parameter to saveCandidateProfile method
		 * @param request      This is the second parameter to saveCandidateProfile method
		 * 
		 * @return String 
		 * 
		 * @throws NullPointerException
	     */
	   @RequestMapping(value = "/saveCandidateProfile" , method = RequestMethod.POST)
	   public String saveCandidateProfile(@RequestParam(value="candidateId", required=false) String candidateId ,MultipartHttpServletRequest request) throws Exception {
			
		   
		    Iterator<String> itrator = request.getFileNames();
	        MultipartFile multiFile = request.getFile(itrator.next());
		
	        OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.parseLong(candidateId), loginOrgLocation.getId());
		    if(null != multiFile.getContentType() && !multiFile.getContentType().equalsIgnoreCase("application/octet-stream")){
		    	  try {
		    		  candidate.setFileContentType(multiFile.getContentType());
		    		  candidate.setDocFile(multiFile.getBytes());
		    		  candidate.setFileName(multiFile.getOriginalFilename());
			       }catch (Exception e) {
		    	      	 e.printStackTrace();
				   }
		    }
		    candidateService.addCandidate(candidate);
		    return "success";
	   }
	   
	   
	   /**
		 * This Method is for displaying  Candidate Image
		 * 
		 * @author sarath kumar
		 * 
		 * @param candidateId   This is the first parameter to displayCandidateImage method
		 * @param response      This is the second parameter to displayCandidateImage method
		 * @param session       This is the third parameter to displayCandidateImage method
		 * @param request       This is the fourth parameter to displayCandidateImage method
		 * 
		 * @return void
		 * 
		 * @throws NullPointerException
		 */ 
	   @RequestMapping(value = "/displayCandidateImage/{candidateId}" , method = RequestMethod.GET)
	     public void displayUserImage(@PathVariable Long candidateId,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
	    	 
		   Candidate candidate = null;
				try {
					 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
					 candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(candidateId, loginOrgLocation.getId());
					
					if(candidate != null && candidate.getDocFile() != null)
					{
						candidate.setFileContentType("image/jpeg");
						InputStream in = new ByteArrayInputStream(candidate.getDocFile());
						BufferedImage bi = ImageIO.read(in);
						OutputStream out = response.getOutputStream();
						ImageIO.write(bi, "jpg", out);
						out.close();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
				
				catch (Exception e1) {
					e1.printStackTrace();
				}
				
	  }
	   
	  /**
		 * This Method is for update Candidate details
		 * 
		 * @author David Raju
		 * 
		 * @param json   This is the first parameter to updateCandidate method
		 * @param  request    This is the second parameter to updateCandidate method
		 * 
		 * @return String
		 * 
		 * @throws NullPointerException
		 */ 
	   @RequestMapping(value = "/updateCandidate")
	   public String updateCandidate(@RequestParam(value="json", required=false) String  json,HttpServletRequest request) throws Exception {

		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			MultipartFile multiFile = null;
			Candidate candidate ;
			String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);
			 candidate = candidateService.storeCandidate(jsonObj,loginOrgLocation,user,multiFile);
			
			return "success";
			
		 }
		 
	   
	   /**
		 *  This Method is for update Candidate details
		 * @author David Raju
		 * @param json   This is the first parameter to updateCandidate method
		 * @param  request    This is the second parameter to updateCandidate method
		 * @return String
		 * @throws NullPointerException
		 */ 
	   @RequestMapping(value = "/deleteCandidateByCandidateId")
	   public @ResponseBody  String deleteCandidateByCandidateId(@RequestParam(value="candidateId", required=false) String  candidateId,HttpServletRequest request) throws Exception {
			String resultJson;
		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 
			Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.valueOf(candidateId),loginOrgLocation.getId());
			if(candidate != null){
				candidate.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				candidate.setUpdatedBy(user.getUserCode());
				candidate.setUpdatedDate(commonUtil.currentDate());
				candidateService.saveOrUpdateCandidate(candidate);
			}
			return "success";
		 }
		
	}
