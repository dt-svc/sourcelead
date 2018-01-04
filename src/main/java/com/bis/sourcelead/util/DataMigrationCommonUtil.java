package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dao.CandidateHistoryDao;
import com.bis.sourcelead.dao.CandidateTimelineActivityDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateCertificationsHistory;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.CandidateDocumentsHistory;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEducationHistory;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateEmployersHistory;
import com.bis.sourcelead.entity.CandidateHistory;
import com.bis.sourcelead.entity.CandidateReferences;
import com.bis.sourcelead.entity.CandidateReferencesHistory;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateResumesHistory;
import com.bis.sourcelead.entity.CandidateTimelineActivity;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.constants.Constants;
/**
 * This is the commonutil for validating data migration
 * application
 * 
 * @author: Venkat Kesav
 * 
 */
@Component
public class DataMigrationCommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(DataMigrationCommonUtil.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private SubmissionTemplateService submissionTemplateService;
	
	@SuppressWarnings("unchecked")
	public void dataValidationForJobs(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, FormulaEvaluator objFormulaEvaluator, DataFormatter objDefaultFormat, User user, OrgLocations orgLocation) throws Exception
	{
		logger.debug("In dataValidationForJobs method...");
		dataMigrationDTOJsonCopy.keys().forEachRemaining(k -> {
			try {
				Boolean flag;
				if((k.toString().equalsIgnoreCase("applicationFrom") || k.toString().equalsIgnoreCase("manager") || k.toString().equalsIgnoreCase("priority") || k.toString().equalsIgnoreCase("jobTitle") || k.toString().equalsIgnoreCase("location")) && (dataMigrationDTOJsonCopy.get(k.toString()) == null || dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") || dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null"))){
					errorSet.put(k.toString(), k.toString()+"  field is missing");
				}
				
				if(k.toString().equalsIgnoreCase("jobId") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					flag = false;
					flag = requirementService.validateJobid(dataMigrationDTOJsonCopy.get((String)k).toString(), orgLocation.getId());
					if(flag){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is Already Existed");
					}
				}
				
				if(k.toString().equalsIgnoreCase("applicationFrom") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					SubmissionTemplate submissionTemplate = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(orgLocation.getId(), dataMigrationDTOJsonCopy.get((String)k).toString());
					if(null != submissionTemplate){
						dataMigrationDTOJsonCopy.put((String) k, submissionTemplate.getId().toString());
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid");
					}
				}
				
				if(k.toString().equalsIgnoreCase("priority") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String jobPriority = dataMigrationDTOJsonCopy.get((String)k).toString().toLowerCase();
					if(jobPriority.equalsIgnoreCase("medium") || jobPriority.equalsIgnoreCase("low") || jobPriority.equalsIgnoreCase("high") || jobPriority.equalsIgnoreCase("urgent")){
						jobPriority = jobPriority.substring(0, 1).toUpperCase()+jobPriority.substring(1, jobPriority.length());
						dataMigrationDTOJsonCopy.put((String) k, jobPriority);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid");
					}
				}
				
				if(k.toString().equalsIgnoreCase("noticePeriod") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String noticePeriod = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(noticePeriod.equalsIgnoreCase("Immediately Available") || noticePeriod.equalsIgnoreCase("15 Days") || noticePeriod.equalsIgnoreCase("1 Month") || noticePeriod.equalsIgnoreCase("2 Months") || noticePeriod.equalsIgnoreCase("3 Months")){
						dataMigrationDTOJsonCopy.put((String) k, noticePeriod);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Notice Period");
					}
				}
				
				if(k.toString().equalsIgnoreCase("experienceLevel") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String experienceLevel = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(experienceLevel.equalsIgnoreCase("Level-1") || experienceLevel.equalsIgnoreCase("Level-2") || experienceLevel.equalsIgnoreCase("Level-3") || experienceLevel.equalsIgnoreCase("Level-4")){
						dataMigrationDTOJsonCopy.put((String) k, experienceLevel);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Experience Level");
					}
				}
				
				if(k.toString().equalsIgnoreCase("minExp") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String minExp = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(minExp)){
						if(Float.valueOf(minExp) <= 999){
							dataMigrationDTOJsonCopy.put((String) k, minExp);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid, should between 0 to 999");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Min Experience");
					}
				}
				
				if(k.toString().equalsIgnoreCase("maxExp") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String maxExp = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(maxExp)){
						if(Float.valueOf(maxExp) <= 999){
							dataMigrationDTOJsonCopy.put((String) k, maxExp);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid, should between 0 to 999");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Max Experience");
					}
				}
				
				if(k.toString().equalsIgnoreCase("rate") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String rate = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(rate)){
						if(Long.valueOf(rate) <= 99999999){
							dataMigrationDTOJsonCopy.put((String) k, rate);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Rate, should between 1 to 8 digits");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Rate");
					}
				}
				
				if(k.toString().equalsIgnoreCase("clientRate") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String clientRate = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(clientRate)){
						if(Long.valueOf(clientRate) <= 99999999){
							dataMigrationDTOJsonCopy.put((String) k, clientRate);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Rate, should between 1 to 8 digits");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Rate");
					}
				}
				
				if(k.toString().equalsIgnoreCase("coolingOff") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String coolingOff = dataMigrationDTOJsonCopy.get((String)k).toString();
					String[] coolingOffSet = {"0 Days","1 Week","2 Weeks","3 Weeks","1 Month","2 Months","3 Months","4 Months","5 Months","6 Months","7 Months","8 Months","9 Months","10 Months","11 Months","1 Year"};
					if(ArrayUtils.contains( coolingOffSet, coolingOff)){
						dataMigrationDTOJsonCopy.put((String) k, coolingOff);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid cooling-off period");
					}
				}
				
				if(k.toString().equalsIgnoreCase("closedDate") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					try {
						DateFormat formatter = new SimpleDateFormat("DD-MM-yyyy"); 
						Date date = (Date)formatter.parse(dataMigrationDTOJsonCopy.get((String)k).toString());
						String closedDate = formatter.format(date);
						dataMigrationDTOJsonCopy.put((String) k, closedDate);
					} catch (Exception e) {
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Close Date");
					}
				}
				
				if(k.toString().equalsIgnoreCase("manager") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					User approverList = userService.getTabAccessOwnersByOrgLocationIdEmailAndTabId(orgLocation.getId(), dataMigrationDTOJsonCopy.get(k.toString()).toString(), Long.valueOf(Constants.TAB_REQUIREMENT));
					if(approverList == null){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Email");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, approverList.getId().toString());
					}
				}
				
				if(k.toString().equalsIgnoreCase("selectClient") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(orgRelations == null){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, orgRelations.getId().toString());
					}
				}
				
				if(k.toString().equalsIgnoreCase("selectContact") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					if(dataMigrationDTOJsonCopy.get("selectClient") != null){
						OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.get("selectClient").toString());
						if(orgRelations != null){
							List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelations.getId(), user.getId(),orgLocation.getId(),"Client");
							if(orgSpocsList != null){
								for(OrgSpoc orgspoc:orgSpocsList){
									if(dataMigrationDTOJsonCopy.get(k.toString()).toString().toLowerCase().equalsIgnoreCase(orgspoc.getSpocEmail())){
										dataMigrationDTOJsonCopy.put((String) k, orgspoc.getId().toString());
									}else{
										errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Contact");
									}
								}
							}else{
								errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Contact");
							}
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Contact");
						}
						
					}
				}
				
				if(k.toString().equalsIgnoreCase("clientSubmissionForm") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					if(dataMigrationDTOJsonCopy.get("selectClient") != null && dataMigrationDTOJsonCopy.get("selectClient") != ""){
						OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.get("selectClient").toString());
						if(orgRelations != null){
							Long clientLocationId = null;
							if(orgRelations.getLocationId().getId() == orgLocation.getId()){
								clientLocationId = orgRelations.getResponderLocationId().getId();
							}
							if(orgRelations.getResponderLocationId().getId() == orgLocation.getId()){
								clientLocationId = orgRelations.getLocationId().getId();
							}
							List<SubmissionTemplate> submissiontTemplateList  = submissionTemplateService.getSubmissionTemplateByClientLocationIdAndOrgLocationIdAndTemplateType(clientLocationId, orgLocation.getId(),"Submission");
							if(submissiontTemplateList != null){
								for(SubmissionTemplate submissiontTemplate:submissiontTemplateList){
									if(dataMigrationDTOJsonCopy.get(k.toString()).toString().toLowerCase().equalsIgnoreCase(submissiontTemplate.getTemplateName())){
										dataMigrationDTOJsonCopy.put((String) k, submissiontTemplate.getId().toString());
									}else{
										errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Submission Form");
									}
								}
							}else{
								errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Submission Form");
							}
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Submission Form");
						}
					}
				}
				
				if(k.toString().equalsIgnoreCase("assignedTo") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					List<User> usersList = userService.getUsersByTeamTypeAndTabId(orgLocation.getId(),"", Long.valueOf(Constants.TAB_REQUIREMENT));
					List<String> validAssTo = new ArrayList<String>();
					StringBuffer invalidAssMembers = new StringBuffer();
					String[] assTo = dataMigrationDTOJsonCopy.get(k.toString()).toString().split(",");
					//int assToCount = assTo.length;
					//int matchedCount = 0;
					for(User assignUser : usersList){
						for(String userEmail : assTo){
							if(assignUser.getEmail().toLowerCase().equalsIgnoreCase(userEmail.trim().toLowerCase())){
								validAssTo.add(assignUser.getId().toString()+",undefined");
								//matchedCount++;
							}else{
								invalidAssMembers.append(userEmail).append(", ");
							}
						}
					}
					if(validAssTo == null){
						errorSet.put(k.toString(), "Asigned members are invalid");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, validAssTo);
					}
				}
				
				
				if(k.toString().equalsIgnoreCase("parallelApprover") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					List<User> usersList = userService.getUsersByTeamTypeAndTabId(orgLocation.getId(),"", Long.valueOf(Constants.TAB_REQUIREMENT));
					List<String> validApprovers = new ArrayList<String>();
					StringBuffer invalidApprovers = new StringBuffer();
					String[] apprs = dataMigrationDTOJsonCopy.get(k.toString()).toString().split(",");
					for(User appUser : usersList){
						for(String userEmail : apprs){
							if(appUser.getEmail().toLowerCase().equalsIgnoreCase(userEmail.trim().toLowerCase())){
								validApprovers.add(appUser.getId().toString());
							}else{
								invalidApprovers.append(userEmail).append(", ");
							}
						}
					}
					if(validApprovers == null){
						errorSet.put(k.toString(), "Approvers are invalid");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, validApprovers);
					}
				}
				
				if (k.toString().equalsIgnoreCase("applicationFrom") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					flag = false;
					List<SubmissionTemplate>   submissionTempletesList = submissionTemplateService.getOrgSubmissionTemplatesByOrgLocationIdAndTemplateType(orgLocation.getId(),"Submission");
					for(SubmissionTemplate submissionTemplete : submissionTempletesList){
						if(submissionTemplete.getTemplateName().toLowerCase().equalsIgnoreCase(k.toString().toLowerCase())){
							flag = true;
							dataMigrationDTOJsonCopy.put((String) k, submissionTemplete.getId().toString());
						}
					}
					if (!flag) {
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString()) + " is invalid Application Form");
					}
				}
								
				/*String keyStr = (String)k;
				System.out.println(keyStr);
		        Object keyvalue = dataMigrationDTOJsonCopy.get(keyStr);
		        System.out.println(keyvalue);*/
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	
	@SuppressWarnings("unchecked")
	public void dataValidationForTimesheets(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, FormulaEvaluator objFormulaEvaluator, DataFormatter objDefaultFormat, User user, OrgLocations orgLocation) throws Exception
	{
		logger.debug("In dataValidationForTimesheets method...");
		dataMigrationDTOJsonCopy.keys().forEachRemaining(k -> {
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
