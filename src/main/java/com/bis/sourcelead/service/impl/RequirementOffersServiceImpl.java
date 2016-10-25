package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementOffersDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class RequirementOffersServiceImpl implements RequirementOffersService{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;

	
	@Autowired
	private RequirementOffersDao requirementOffersDao;
	
	@Override
	public void saveRequirementOffers(JSONObject jsonObj, User user, OrgLocations orgLocations,MultipartHttpServletRequest request) throws Exception {
		
		
		String maxAdminTimeTypeRowNum = jsonObj.getString("maxOfferRowNum");
		int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
		RequirementInterview requirementInterview  = null;
		
		RequirementOffers requirementOffers = null;
		
		
		for (long c = 1; c < maxCount; c++) {
				if(jsonObj.has(c+"applicant")){
						
						String applicantslist =null;
						try{
							JSONArray jsonArray = jsonObj.getJSONArray(c+"applicant");
							applicantslist = jsonArray.toString();
						}
						catch(JSONException e){
							applicantslist =jsonObj.getString(c+"applicant");
						}
						applicantslist = applicantslist.replaceAll("\\[", "").replaceAll("\\]","");
						applicantslist = applicantslist.replaceAll("\"", "");
						
					 	String[] applicants = applicantslist.split(",");
					
					     for(int i=0 ;i<applicants.length;i++ ){
					    	     requirementOffers  = new RequirementOffers();
								RequirementSubmission submission = new RequirementSubmission();
								submission.setId(Long.parseLong(applicants[i].replace("\"", "")));
								requirementOffers.setSubmissionId(submission);
								
								if(jsonObj.has(c+"joiningDate") && jsonObj.getString(c+"joiningDate") != null && !"".equalsIgnoreCase(jsonObj.getString(c+"joiningDate"))){
									requirementOffers.setDateOfJoining(commonUtil.getDateFormat(jsonObj.getString(c+"joiningDate")));
								}
								
								if(jsonObj.has(c+"PaymentType") && !jsonObj.getString(c+"PaymentType").isEmpty()){
									requirementOffers.setPaymentType(jsonObj.getString(c+"PaymentType"));
								}
								
								if(jsonObj.has(c+"paymentTypAmount") && !jsonObj.getString(c+"paymentTypAmount").isEmpty()){
									requirementOffers.setAmount(jsonObj.getString(c+"paymentTypAmount"));
								}
								
								if(jsonObj.has(c+"PaymentMode") && !jsonObj.getString(c+"PaymentMode").isEmpty()){
									requirementOffers.setPaymentMode(jsonObj.getString(c+"PaymentMode"));
								}
								
								if(jsonObj.has(c+"PositionType") && !jsonObj.getString(c+"PositionType").isEmpty()){
									requirementOffers.setPositionType(jsonObj.getString(c+"PositionType"));
								}
                               
                               final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; 
                   		       final Map files = multiRequest.getFileMap();
                              /* MultipartFile multiFile = null;*/
                               /*for (Iterator iter = files.values().iterator(); iter.hasNext();) {
                               	    multiFile = (MultipartFile) iter.next();
                                       String fileName = multiFile.getOriginalFilename();
                               }*/
                               MultipartFile  multiFile = (MultipartFile)files.get(c+"OfferAttachment");
                               requirementOffers.setDocFile(multiFile.getBytes());
							   requirementOffers.setFileName(multiFile.getOriginalFilename());
							   /*requirementOffers.setFileContentType(multiFile.getContentType());*/
							  
								requirementOffers.setStatus(Constants.RECORD_ACTIVE);
								requirementOffers.setCreatedDate(new Date());
								requirementOffers.setCreatedBy(user.getUserCode());
								requirementOffers.setUpdatedDate(new Date());
								requirementOffers.setUpdatedBy(user.getUserCode());
								
					            requirementOffersDao.storeRequirementOffers(requirementOffers);
					           
					           RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(applicants[i].replace("\"", "")));
					           requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.OFFERMADE);
					           requirementSubmissionService.storeRequirementSubmission(requirementSubmission);
					           
							}
				}
			continue;
		}

		
	}
}
