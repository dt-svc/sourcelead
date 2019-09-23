package com.bis.sourcelead.rest.reportsRestController;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ApplicantBySourceCountDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.reports.service.ApplicantsBySourceService;
import com.bis.sourcelead.reportsDTO.ApplicantsBySourceDTO;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class ApplicantBySourceRestController {
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	ApplicantsBySourceService applicantsBySourceService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicantDeclineRestController.class);
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/getApplicantBySourceDetails", method = RequestMethod.GET)
	public ResponseList<ApplicantsBySourceDTO> getApplicantBySourceDetails(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
		
		ResponseList<ApplicantsBySourceDTO> response = new ResponseList<>();
		List<ApplicantsBySourceDTO> applicantBySourceList = new ArrayList<>();
		try{
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(loginUser != null){
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				
				List<Long> jobIdList = new ArrayList<>();
				DecimalFormat df = new DecimalFormat("#.00");
				
				if(StringUtils.isNotBlank(jobFilterDTO.getJobIds())) {
					String[] jobIds = jobFilterDTO.getJobIds().split(",");
					for(int i=0; i<jobIds.length; i++) {
						jobIdList.add(Long.valueOf(jobIds[i]));
					}
				}else {
					jobIdList = (List<Long>) SourceLeadSessionManager.getSessionAttribute(request, "requirementIdsList");
				}
				
				Date[] searchDates = null;
				
				if(StringUtils.isNotBlank(jobFilterDTO.getDateSelection())) {
					if(!jobFilterDTO.getDateSelection().equalsIgnoreCase("custom")) {
						searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection(),orgLocation);
					}else {
						Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getFromDate());
			    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getToDate());
			    	   
			    	    Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
			    	    DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    	    DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
			    	    DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
			    	    String customStartTimeStr = TZ_DF_CSD.format(fromDate);
			    	    String customEndTimeStr = TZ_DF_CED.format(toDate);
			    	    String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
			    	    String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
			    	    fromDate = TZ_DF_TS.parse(startDateString);
			    	    toDate = TZ_DF_TS.parse(endDateString);
			    	    
			    	    searchDates = new Date[2];
			    	    //
			    	  
			    	    //
			    	    searchDates[0] = fromDate;
			    	    searchDates[1] = toDate;
					}
				}
				
				
				if(jobIdList != null && jobIdList.size() > 0) {
				
				// Crowd Sourcing Reports Start
					
				ApplicantsBySourceDTO crowdSourcingApplicantsDTO = new ApplicantsBySourceDTO();
				
				ApplicantBySourceCountDTO applicantBySourceCountDTO =  applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates);
				
				Integer crowdsourcingApplicantsCount = applicantBySourceCountDTO.getSubmissionCount() == null ? 0 :applicantBySourceCountDTO.getSubmissionCount().intValue();
				crowdSourcingApplicantsDTO.setApplicantsCount(crowdsourcingApplicantsCount);
				Integer crowdsourcingSortlistedCount = applicantBySourceCountDTO.getShortlistCount() == null ? 0 :applicantBySourceCountDTO.getShortlistCount().intValue();
				crowdSourcingApplicantsDTO.setShortlistedCount(crowdsourcingSortlistedCount);
				
				Integer crowdsourcingSubmitToClientCount = applicantBySourceCountDTO.getSubmitToClientCount() == null ? 0 :applicantBySourceCountDTO.getSubmitToClientCount().intValue();
				crowdSourcingApplicantsDTO.setSubmitToClientCount(crowdsourcingSubmitToClientCount);	
				
				Integer crowdsourcingInterviewCount = applicantBySourceCountDTO.getInterviewCount() == null ? 0 : applicantBySourceCountDTO.getInterviewCount().intValue();
				Integer crowdsourcingClientInterviewCount = applicantBySourceCountDTO.getClientInterviewCount() == null ? 0 : applicantBySourceCountDTO.getClientInterviewCount().intValue();
				crowdsourcingInterviewCount = crowdsourcingInterviewCount+crowdsourcingClientInterviewCount;
				crowdSourcingApplicantsDTO.setInterviewsCount(crowdsourcingInterviewCount);
				
				Integer crowdsourcingRejectedCount = applicantBySourceCountDTO.getRejectedCount() == null ? 0 : applicantBySourceCountDTO.getRejectedCount().intValue();
				crowdSourcingApplicantsDTO.setRejectedCount(crowdsourcingRejectedCount);
				
				Integer crowdsourcingOfferedCount = applicantBySourceCountDTO.getOffermadeCount() == null ? 0 : applicantBySourceCountDTO.getOffermadeCount().intValue();
				Integer crowdsourcingClientOfferedCount = applicantBySourceCountDTO.getClientOffermadeCount() == null ? 0 :applicantBySourceCountDTO.getClientOffermadeCount().intValue();
				crowdsourcingOfferedCount = crowdsourcingOfferedCount+crowdsourcingClientOfferedCount;
				crowdSourcingApplicantsDTO.setOfferedCount(crowdsourcingOfferedCount);
				
				Integer crowdsourcingOfferAcceptedCount =applicantBySourceCountDTO.getOfferAcceptedCount() == null ? 0 : applicantBySourceCountDTO.getOfferAcceptedCount().intValue();
				Integer crowdsourcingClientOfferAcceptedCount =applicantBySourceCountDTO.getClientOfferAcceptedCount() == null ? 0 :applicantBySourceCountDTO.getClientOfferAcceptedCount().intValue();
				crowdsourcingOfferAcceptedCount = crowdsourcingOfferAcceptedCount+crowdsourcingClientOfferAcceptedCount;
				crowdSourcingApplicantsDTO.setOfferAcceptedCount(crowdsourcingOfferAcceptedCount);
				
				Integer crowdsourcingOfferRejectedCount = applicantBySourceCountDTO.getOfferRejectedCount() == null ? 0 :applicantBySourceCountDTO.getOfferRejectedCount().intValue();
				crowdSourcingApplicantsDTO.setOfferRejectedCount(crowdsourcingOfferRejectedCount);
				
				Integer crowdsourcingOfferBackoutCount = applicantBySourceCountDTO.getBackoutCount() == null ? 0 :applicantBySourceCountDTO.getBackoutCount().intValue();
				crowdSourcingApplicantsDTO.setBackoutCount(crowdsourcingOfferBackoutCount);
				
				crowdSourcingApplicantsDTO.setCategory("Job Seeker");
				crowdSourcingApplicantsDTO.setApplicantsCount(crowdsourcingApplicantsCount);
				crowdSourcingApplicantsDTO.setShortlistedCount(crowdsourcingSortlistedCount);
				crowdSourcingApplicantsDTO.setSubmitToClientCount(crowdsourcingSubmitToClientCount);
				crowdSourcingApplicantsDTO.setInterviewsCount(crowdsourcingInterviewCount);
				crowdSourcingApplicantsDTO.setRejectedCount(crowdsourcingRejectedCount);
				crowdSourcingApplicantsDTO.setOfferedCount(crowdsourcingOfferedCount);
				crowdSourcingApplicantsDTO.setOfferAcceptedCount(crowdsourcingOfferAcceptedCount);
				crowdSourcingApplicantsDTO.setOfferRejectedCount(crowdsourcingOfferRejectedCount);
				crowdSourcingApplicantsDTO.setBackoutCount(crowdsourcingOfferBackoutCount);
				
				// // Crowd Sourcing Reports End.
				
				// vendors Reports
				
				ApplicantsBySourceDTO vendorSourcingApplicantsDTO = new ApplicantsBySourceDTO();
				
				ApplicantBySourceCountDTO vendorApplicantBySourceCountDTO = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates);
				
				Integer vendorSourcingApplicantCount = vendorApplicantBySourceCountDTO.getSubmissionCount() == null ? 0 : vendorApplicantBySourceCountDTO.getSubmissionCount().intValue();
				vendorSourcingApplicantsDTO.setApplicantsCount(vendorSourcingApplicantCount);		
				
				Integer vendorSourcingShortlistCount = vendorApplicantBySourceCountDTO.getShortlistCount() == null ? 0 : vendorApplicantBySourceCountDTO.getShortlistCount().intValue();
				vendorSourcingApplicantsDTO.setShortlistedCount(vendorSourcingShortlistCount);
				
				Integer vendorSourcingSubmitToClientCount = vendorApplicantBySourceCountDTO.getSubmitToClientCount() == null ? 0 : vendorApplicantBySourceCountDTO.getSubmitToClientCount().intValue();
				vendorSourcingApplicantsDTO.setSubmitToClientCount(vendorSourcingSubmitToClientCount);
				
				Integer vendorSourcingInterviewCount = vendorApplicantBySourceCountDTO.getInterviewCount() == null ? 0 : vendorApplicantBySourceCountDTO.getInterviewCount().intValue();
				Integer vendorSourcingClientInterviewCount = vendorApplicantBySourceCountDTO.getClientInterviewCount() == null ? 0 : vendorApplicantBySourceCountDTO.getClientInterviewCount().intValue();
				vendorSourcingInterviewCount = vendorSourcingInterviewCount+vendorSourcingClientInterviewCount;
				vendorSourcingApplicantsDTO.setInterviewsCount(vendorSourcingInterviewCount);	
				
				Integer vendorSourcingRejectedCount = vendorApplicantBySourceCountDTO.getRejectedCount() == null ? 0 :vendorApplicantBySourceCountDTO.getRejectedCount().intValue();
				vendorSourcingApplicantsDTO.setRejectedCount(vendorSourcingRejectedCount);
				
				Integer vendorSourcingOfferedCount = vendorApplicantBySourceCountDTO.getOffermadeCount() == null ? 0 : vendorApplicantBySourceCountDTO.getOffermadeCount().intValue();
				Integer vendorSourcingClientOfferedCount = vendorApplicantBySourceCountDTO.getClientOffermadeCount() == null ? 0 : vendorApplicantBySourceCountDTO.getClientOffermadeCount().intValue();
				vendorSourcingOfferedCount = vendorSourcingOfferedCount+vendorSourcingClientOfferedCount;
				vendorSourcingApplicantsDTO.setOfferedCount(vendorSourcingOfferedCount);
				
				Integer vendorSourcingOfferAcceptedCount = vendorApplicantBySourceCountDTO.getOfferAcceptedCount() == null ? 0 : vendorApplicantBySourceCountDTO.getOfferAcceptedCount().intValue();
				Integer vendorSourcingClientOfferAcceptedCount = vendorApplicantBySourceCountDTO.getClientOfferAcceptedCount() == null ? 0 : vendorApplicantBySourceCountDTO.getClientOfferAcceptedCount().intValue();
				vendorSourcingOfferAcceptedCount = vendorSourcingOfferAcceptedCount+vendorSourcingClientOfferAcceptedCount;
				vendorSourcingApplicantsDTO.setOfferAcceptedCount(vendorSourcingOfferAcceptedCount);
				
				Integer vendorSourcingOfferRejectedCount = vendorApplicantBySourceCountDTO.getOfferRejectedCount() == null ? 0 : vendorApplicantBySourceCountDTO.getOfferRejectedCount().intValue();
				vendorSourcingApplicantsDTO.setOfferRejectedCount(vendorSourcingOfferRejectedCount);
				
				Integer vendorSourcingOfferBackoutCount = vendorApplicantBySourceCountDTO.getBackoutCount() == null ? 0 :vendorApplicantBySourceCountDTO.getBackoutCount().intValue();
				vendorSourcingApplicantsDTO.setBackoutCount(vendorSourcingOfferBackoutCount);
				
				vendorSourcingApplicantsDTO.setCategory("Vendors");
				vendorSourcingApplicantsDTO.setApplicantsCount(vendorSourcingApplicantCount);
				vendorSourcingApplicantsDTO.setShortlistedCount(vendorSourcingShortlistCount);
				vendorSourcingApplicantsDTO.setSubmitToClientCount(vendorSourcingSubmitToClientCount);
				vendorSourcingApplicantsDTO.setInterviewsCount(vendorSourcingInterviewCount);
				vendorSourcingApplicantsDTO.setRejectedCount(vendorSourcingRejectedCount);
				vendorSourcingApplicantsDTO.setOfferedCount(vendorSourcingOfferedCount);
				vendorSourcingApplicantsDTO.setOfferAcceptedCount(vendorSourcingOfferAcceptedCount);
				vendorSourcingApplicantsDTO.setOfferRejectedCount(vendorSourcingOfferRejectedCount);
				vendorSourcingApplicantsDTO.setBackoutCount(vendorSourcingOfferBackoutCount);
				
				// vendors end
				
				// FreelanceRecruiter Report Start
				
				ApplicantsBySourceDTO recruiterSourcingApplicantsDTO = new ApplicantsBySourceDTO();
				
				ApplicantBySourceCountDTO freelanceRecruiterApplicantBySourceCountDTO = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates);
				
				Integer recruiterSourcingApplicantCount = freelanceRecruiterApplicantBySourceCountDTO.getSubmissionCount() == null ? 0 : freelanceRecruiterApplicantBySourceCountDTO.getSubmissionCount().intValue();
				recruiterSourcingApplicantsDTO.setApplicantsCount(recruiterSourcingApplicantCount);
				
				Integer recruiterSourcingShortlistCount = freelanceRecruiterApplicantBySourceCountDTO.getShortlistCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getShortlistCount().intValue();
				recruiterSourcingApplicantsDTO.setShortlistedCount(recruiterSourcingShortlistCount);
				
				Integer recruiterSourcingSubmitToClientCount =freelanceRecruiterApplicantBySourceCountDTO.getSubmitToClientCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getSubmitToClientCount().intValue();
				recruiterSourcingApplicantsDTO.setSubmitToClientCount(recruiterSourcingSubmitToClientCount);
				
				Integer recruiterSourcingInterviewCount = freelanceRecruiterApplicantBySourceCountDTO.getInterviewCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getInterviewCount().intValue();
				Integer recruiterSourcingClientInterviewCount =freelanceRecruiterApplicantBySourceCountDTO.getClientInterviewCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getClientInterviewCount().intValue();
				recruiterSourcingInterviewCount = recruiterSourcingInterviewCount+recruiterSourcingClientInterviewCount;
				recruiterSourcingApplicantsDTO.setInterviewsCount(recruiterSourcingInterviewCount);
				
				Integer recruiterSourcingRejectedCount =  freelanceRecruiterApplicantBySourceCountDTO.getRejectedCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getRejectedCount().intValue();
				recruiterSourcingApplicantsDTO.setRejectedCount(recruiterSourcingRejectedCount);
				
				Integer recruiterrSourcingOfferedCount = freelanceRecruiterApplicantBySourceCountDTO.getOffermadeCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getOffermadeCount().intValue();
				Integer recruiterrSourcingClientOfferedCount =freelanceRecruiterApplicantBySourceCountDTO.getClientOffermadeCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getClientOffermadeCount().intValue();
				recruiterrSourcingOfferedCount = recruiterrSourcingOfferedCount+recruiterrSourcingClientOfferedCount;
				recruiterSourcingApplicantsDTO.setOfferedCount(recruiterrSourcingOfferedCount);
				
				Integer recruiterSourcingOfferAcceptedCount =freelanceRecruiterApplicantBySourceCountDTO.getOfferAcceptedCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getOfferAcceptedCount().intValue();
				Integer recruiterSourcingClientOfferAcceptedCount =freelanceRecruiterApplicantBySourceCountDTO.getClientOfferAcceptedCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getClientOfferAcceptedCount().intValue();
				recruiterSourcingOfferAcceptedCount = recruiterSourcingOfferAcceptedCount+recruiterSourcingClientOfferAcceptedCount;
				recruiterSourcingApplicantsDTO.setOfferAcceptedCount(recruiterSourcingOfferAcceptedCount);
				
				Integer recruiterSourcingOfferRejectedCount =freelanceRecruiterApplicantBySourceCountDTO.getOfferRejectedCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getOfferRejectedCount().intValue();
				recruiterSourcingApplicantsDTO.setOfferRejectedCount(recruiterSourcingOfferRejectedCount);
						
				Integer recruiterSourcingOfferBackoutCount = freelanceRecruiterApplicantBySourceCountDTO.getBackoutCount() == null ? 0 :freelanceRecruiterApplicantBySourceCountDTO.getBackoutCount().intValue();
				recruiterSourcingApplicantsDTO.setBackoutCount(recruiterSourcingOfferBackoutCount);
				
				recruiterSourcingApplicantsDTO.setCategory("Freelance Recruiters");
				recruiterSourcingApplicantsDTO.setApplicantsCount(recruiterSourcingApplicantCount);
				recruiterSourcingApplicantsDTO.setShortlistedCount(recruiterSourcingShortlistCount);
				recruiterSourcingApplicantsDTO.setSubmitToClientCount(recruiterSourcingSubmitToClientCount);
				recruiterSourcingApplicantsDTO.setInterviewsCount(recruiterSourcingInterviewCount);
				recruiterSourcingApplicantsDTO.setRejectedCount(recruiterSourcingRejectedCount);
				recruiterSourcingApplicantsDTO.setOfferedCount(recruiterrSourcingOfferedCount);
				recruiterSourcingApplicantsDTO.setOfferAcceptedCount(recruiterSourcingOfferAcceptedCount);
				recruiterSourcingApplicantsDTO.setOfferRejectedCount(recruiterSourcingOfferRejectedCount);
				recruiterSourcingApplicantsDTO.setBackoutCount(recruiterSourcingOfferBackoutCount);
				
				// FreelanceRecruiter End 
				
				// Internal DB Stage Counts
				
				ApplicantsBySourceDTO internalSourcingApplicantsDTO = new ApplicantsBySourceDTO();
				ApplicantBySourceCountDTO internalSourcingApplicantCountDTO = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates);
				
				internalSourcingApplicantsDTO.setCategory("Internal DB");
				if(internalSourcingApplicantCountDTO != null) {
				Integer internalSourcingApplicantCount = internalSourcingApplicantCountDTO.getSubmissionCount()== null ? 0 : internalSourcingApplicantCountDTO.getSubmissionCount().intValue();
				
				internalSourcingApplicantsDTO.setApplicantsCount(internalSourcingApplicantCount);
				
				Integer internalSourcingShortlistCount = internalSourcingApplicantCountDTO.getShortlistCount() == null ? 0 : internalSourcingApplicantCountDTO.getShortlistCount().intValue();
				internalSourcingApplicantsDTO.setShortlistedCount(internalSourcingShortlistCount);
				
				Integer internalSourcingSubmitToClientCount = internalSourcingApplicantCountDTO.getSubmitToClientCount() == null ? 0 : internalSourcingApplicantCountDTO.getSubmitToClientCount().intValue();
				internalSourcingApplicantsDTO.setSubmitToClientCount(internalSourcingSubmitToClientCount);
				
				Integer internalSourcingInterviewCount = internalSourcingApplicantCountDTO.getInterviewCount() == null ? 0 : internalSourcingApplicantCountDTO.getInterviewCount().intValue();
				Integer internalSourcingClientInterviewCount = internalSourcingApplicantCountDTO.getClientInterviewCount() == null ? 0 : internalSourcingApplicantCountDTO.getClientInterviewCount().intValue();
				internalSourcingInterviewCount = internalSourcingInterviewCount + internalSourcingClientInterviewCount;
				internalSourcingApplicantsDTO.setInterviewsCount(internalSourcingInterviewCount);
				
				Integer internalSourcingRejectedCount = internalSourcingApplicantCountDTO.getRejectedCount() == null ? 0 : internalSourcingApplicantCountDTO.getRejectedCount().intValue();
				internalSourcingApplicantsDTO.setRejectedCount(internalSourcingRejectedCount);
				
				Integer internalSourcingOfferedCount =internalSourcingApplicantCountDTO.getOffermadeCount() == null ? 0 : internalSourcingApplicantCountDTO.getOffermadeCount().intValue();
				Integer internalSourcingClientOfferedCount = internalSourcingApplicantCountDTO.getClientOffermadeCount() == null ? 0 : internalSourcingApplicantCountDTO.getClientOffermadeCount().intValue();
				internalSourcingOfferedCount = internalSourcingOfferedCount+internalSourcingClientOfferedCount;
				internalSourcingApplicantsDTO.setOfferedCount(internalSourcingOfferedCount);
				
				Integer internalSourcingOfferAcceptedCount = internalSourcingApplicantCountDTO.getOfferAcceptedCount() == null ? 0 : internalSourcingApplicantCountDTO.getOfferAcceptedCount().intValue();
				Integer internalSourcingClientOfferAcceptedCount = internalSourcingApplicantCountDTO.getClientOfferAcceptedCount() == null ? 0 : internalSourcingApplicantCountDTO.getClientOfferAcceptedCount().intValue();
				internalSourcingOfferAcceptedCount = internalSourcingOfferAcceptedCount+internalSourcingClientOfferAcceptedCount;
				internalSourcingApplicantsDTO.setOfferAcceptedCount(internalSourcingOfferAcceptedCount);
				
				Integer internalSourcingOfferRejectedCount = internalSourcingApplicantCountDTO.getOfferRejectedCount() == null ? 0 : internalSourcingApplicantCountDTO.getOfferRejectedCount().intValue();
				internalSourcingApplicantsDTO.setOfferRejectedCount(internalSourcingOfferRejectedCount);
				
				Integer internalSourcingBackoutCount = internalSourcingApplicantCountDTO.getBackoutCount() == null ? 0 : internalSourcingApplicantCountDTO.getBackoutCount().intValue();
				internalSourcingApplicantsDTO.setBackoutCount(internalSourcingBackoutCount);
				
				 Double crowdSoruceApplicantCount = ((double)(crowdsourcingApplicantsCount)/((double)crowdsourcingApplicantsCount+(double)vendorSourcingApplicantCount+(double)recruiterSourcingApplicantCount+(double)internalSourcingApplicantCount))*100;
				 Double crowdSoruceShortlistedCount = ((double)(crowdsourcingSortlistedCount)/((double)crowdsourcingSortlistedCount+(double)vendorSourcingShortlistCount+(double)recruiterSourcingShortlistCount+(double)internalSourcingShortlistCount))*100;
				 Double crowdSoruceSubmitToClientCount = ((double)(crowdsourcingSubmitToClientCount)/((double)crowdsourcingSubmitToClientCount+(double)vendorSourcingSubmitToClientCount+(double)recruiterSourcingSubmitToClientCount+(double)internalSourcingSubmitToClientCount))*100;
				 Double crowdSoruceInterviewsCount = ((double)(crowdsourcingInterviewCount)/((double)crowdsourcingInterviewCount+(double)vendorSourcingInterviewCount+(double)recruiterSourcingInterviewCount+(double)internalSourcingInterviewCount))*100;
				 Double crowdSoruceRejectedCount = ((double)(crowdsourcingRejectedCount)/((double)crowdsourcingRejectedCount+(double)vendorSourcingRejectedCount+(double)recruiterSourcingRejectedCount+(double)internalSourcingRejectedCount))*100;
				 Double crowdSoruceOfferedCount = ((double)(crowdsourcingOfferedCount)/((double)crowdsourcingOfferedCount+(double)vendorSourcingOfferedCount+(double)recruiterrSourcingOfferedCount+(double)internalSourcingOfferedCount))*100;
				 Double crowdSoruceOfferAcceptedCount = ((double)(crowdsourcingOfferAcceptedCount)/((double)crowdsourcingOfferAcceptedCount+(double)vendorSourcingOfferAcceptedCount+(double)recruiterSourcingOfferAcceptedCount+(double)internalSourcingOfferAcceptedCount))*100;
				 Double crowdSoruceOfferRejectedCount = ((double)(crowdsourcingOfferRejectedCount)/((double)crowdsourcingOfferRejectedCount+(double)vendorSourcingOfferRejectedCount+(double)recruiterSourcingOfferRejectedCount+(double)internalSourcingOfferRejectedCount))*100;
				 Double crowdSoruceOfferBackoutCount = ((double)(crowdsourcingOfferBackoutCount)/((double)crowdsourcingOfferBackoutCount+(double)vendorSourcingOfferBackoutCount+(double)recruiterSourcingOfferBackoutCount+(double)internalSourcingBackoutCount))*100;
				 
				// Internal DB Stage Counts End
				 
				 crowdSourcingApplicantsDTO.setApplicantsAvgCount(df.format(crowdSoruceApplicantCount));
				 crowdSourcingApplicantsDTO.setShortlistedAvgCount(df.format(crowdSoruceShortlistedCount));
				 crowdSourcingApplicantsDTO.setSubmitToClientAvgCount(df.format(crowdSoruceSubmitToClientCount));
				 crowdSourcingApplicantsDTO.setInterviewsAvgCount(df.format(crowdSoruceInterviewsCount));
				 crowdSourcingApplicantsDTO.setRejectedAvgCount(df.format(crowdSoruceRejectedCount));
				 crowdSourcingApplicantsDTO.setOfferedAvgCount(df.format(crowdSoruceOfferedCount));
				 crowdSourcingApplicantsDTO.setOfferAcceptedAvgCount(df.format(crowdSoruceOfferAcceptedCount));
				 crowdSourcingApplicantsDTO.setOfferRejectedAvgCount(df.format(crowdSoruceOfferRejectedCount));
				 crowdSourcingApplicantsDTO.setBackoutAvgCount(df.format(crowdSoruceOfferBackoutCount));
				 applicantBySourceList.add(crowdSourcingApplicantsDTO);
				 
				 Double vendorApplicantCount = ((double)(vendorSourcingApplicantCount)/((double)crowdsourcingApplicantsCount+(double)vendorSourcingApplicantCount+(double)recruiterSourcingApplicantCount+(double)internalSourcingApplicantCount))*100;
				 Double vendorShortlistedCount = ((double)(vendorSourcingShortlistCount)/((double)crowdsourcingSortlistedCount+(double)vendorSourcingShortlistCount+(double)recruiterSourcingShortlistCount+(double)internalSourcingShortlistCount))*100;
				 Double vendorSubmitToClientCount = ((double)(vendorSourcingSubmitToClientCount)/((double)crowdsourcingSubmitToClientCount+(double)vendorSourcingSubmitToClientCount+(double)recruiterSourcingSubmitToClientCount+(double)internalSourcingSubmitToClientCount))*100;
				 Double vendorInterviewsCount = ((double)(vendorSourcingInterviewCount)/((double)crowdsourcingInterviewCount+(double)vendorSourcingInterviewCount+(double)recruiterSourcingInterviewCount+(double)internalSourcingInterviewCount))*100;
				 Double vendorRejectedCount = ((double)(vendorSourcingRejectedCount)/((double)crowdsourcingRejectedCount+(double)vendorSourcingRejectedCount+(double)recruiterSourcingRejectedCount+(double)internalSourcingInterviewCount))*100;
				 Double vendorOfferedCount = ((double)(vendorSourcingOfferedCount)/((double)crowdsourcingOfferedCount+(double)vendorSourcingOfferedCount+(double)recruiterrSourcingOfferedCount+(double)internalSourcingOfferedCount))*100;
				 Double vendorOfferAcceptedCount = ((double)(vendorSourcingOfferAcceptedCount)/((double)crowdsourcingOfferAcceptedCount+(double)vendorSourcingOfferAcceptedCount+(double)recruiterSourcingOfferAcceptedCount+(double)internalSourcingOfferAcceptedCount))*100;
				 Double vendorOfferRejectedCount = ((double)(vendorSourcingOfferRejectedCount)/((double)crowdsourcingOfferRejectedCount+(double)vendorSourcingOfferRejectedCount+(double)recruiterSourcingOfferRejectedCount+(double)internalSourcingOfferRejectedCount))*100;
				 Double vendorOfferBackoutCount = ((double)(vendorSourcingOfferBackoutCount)/((double)crowdsourcingOfferBackoutCount+(double)vendorSourcingOfferBackoutCount+(double)recruiterSourcingOfferBackoutCount+(double)internalSourcingBackoutCount))*100;
				 
				 
				 vendorSourcingApplicantsDTO.setApplicantsAvgCount(df.format(vendorApplicantCount));
				 vendorSourcingApplicantsDTO.setShortlistedAvgCount(df.format(vendorShortlistedCount));
				 vendorSourcingApplicantsDTO.setSubmitToClientAvgCount(df.format(vendorSubmitToClientCount));
				 vendorSourcingApplicantsDTO.setInterviewsAvgCount(df.format(vendorInterviewsCount));
				 vendorSourcingApplicantsDTO.setRejectedAvgCount(df.format(vendorRejectedCount));
				 vendorSourcingApplicantsDTO.setOfferedAvgCount(df.format(vendorOfferedCount));
				 vendorSourcingApplicantsDTO.setOfferAcceptedAvgCount(df.format(vendorOfferAcceptedCount));
				 vendorSourcingApplicantsDTO.setOfferRejectedAvgCount(df.format(vendorOfferRejectedCount));
				 vendorSourcingApplicantsDTO.setBackoutAvgCount(df.format(vendorOfferBackoutCount));
				 applicantBySourceList.add(vendorSourcingApplicantsDTO);
				 
				 
				 Double recruiterApplicantCount = ((double)(recruiterSourcingApplicantCount)/((double)crowdsourcingApplicantsCount+(double)vendorSourcingApplicantCount+(double)recruiterSourcingApplicantCount+(double)internalSourcingApplicantCount))*100;
				 Double recruiterShortlistedCount = ((double)(recruiterSourcingShortlistCount)/((double)crowdsourcingSortlistedCount+(double)vendorSourcingShortlistCount+(double)recruiterSourcingShortlistCount+(double)internalSourcingShortlistCount))*100;
				 Double recruiterSubmitToClientCount = ((double)(recruiterSourcingSubmitToClientCount)/((double)crowdsourcingSubmitToClientCount+(double)vendorSourcingSubmitToClientCount+(double)recruiterSourcingSubmitToClientCount+(double)internalSourcingSubmitToClientCount))*100;
				 Double recruiterInterviewsCount = ((double)(recruiterSourcingInterviewCount)/((double)crowdsourcingInterviewCount+(double)vendorSourcingInterviewCount+(double)recruiterSourcingInterviewCount+(double)internalSourcingInterviewCount))*100;
				 Double recruiterRejectedCount = ((double)(recruiterSourcingRejectedCount)/((double)crowdsourcingRejectedCount+(double)vendorSourcingRejectedCount+(double)recruiterSourcingRejectedCount+(double)internalSourcingInterviewCount))*100;
				 Double recruiterOfferedCount = ((double)(recruiterrSourcingOfferedCount)/((double)crowdsourcingOfferedCount+(double)vendorSourcingOfferedCount+(double)recruiterrSourcingOfferedCount+(double)internalSourcingOfferedCount))*100;
				 Double recruiterOfferAcceptedCount = ((double)(recruiterSourcingOfferAcceptedCount)/((double)crowdsourcingOfferAcceptedCount+(double)vendorSourcingOfferAcceptedCount+(double)recruiterSourcingOfferAcceptedCount+(double)internalSourcingOfferAcceptedCount))*100;
				 Double recruiterOfferRejectedCount = ((double)(recruiterSourcingOfferRejectedCount)/((double)crowdsourcingOfferRejectedCount+(double)vendorSourcingOfferRejectedCount+(double)recruiterSourcingOfferRejectedCount+(double)internalSourcingOfferRejectedCount))*100;
				 Double recruiterOfferBackoutCount = ((double)(recruiterSourcingOfferBackoutCount)/((double)crowdsourcingOfferBackoutCount+(double)vendorSourcingOfferBackoutCount+(double)recruiterSourcingOfferBackoutCount+(double)internalSourcingBackoutCount))*100;
				 
				 recruiterSourcingApplicantsDTO.setApplicantsAvgCount(df.format(recruiterApplicantCount));
				 recruiterSourcingApplicantsDTO.setShortlistedAvgCount(df.format(recruiterShortlistedCount));
				 recruiterSourcingApplicantsDTO.setSubmitToClientAvgCount(df.format(recruiterSubmitToClientCount));
				 recruiterSourcingApplicantsDTO.setInterviewsAvgCount(df.format(recruiterInterviewsCount));
				 recruiterSourcingApplicantsDTO.setRejectedAvgCount(df.format(recruiterRejectedCount));
				 recruiterSourcingApplicantsDTO.setOfferedAvgCount(df.format(recruiterOfferedCount));
				 recruiterSourcingApplicantsDTO.setOfferAcceptedAvgCount(df.format(recruiterOfferAcceptedCount));
				 recruiterSourcingApplicantsDTO.setOfferRejectedAvgCount(df.format(recruiterOfferRejectedCount));
				 recruiterSourcingApplicantsDTO.setBackoutAvgCount(df.format(recruiterOfferBackoutCount));
				 applicantBySourceList.add(recruiterSourcingApplicantsDTO);
				 
				 
				 Double internalApplicantCount = ((double)(internalSourcingApplicantCount)/((double)crowdsourcingApplicantsCount+(double)vendorSourcingApplicantCount+(double)recruiterSourcingApplicantCount+(double)internalSourcingApplicantCount))*100;
				 Double internalShortlistedCount = ((double)(internalSourcingShortlistCount)/((double)crowdsourcingSortlistedCount+(double)vendorSourcingShortlistCount+(double)recruiterSourcingShortlistCount+(double)internalSourcingShortlistCount))*100;
				 Double internalSubmitToClientCount = ((double)(internalSourcingSubmitToClientCount)/((double)crowdsourcingSubmitToClientCount+(double)vendorSourcingSubmitToClientCount+(double)recruiterSourcingSubmitToClientCount+(double)internalSourcingSubmitToClientCount))*100;
				 Double internalInterviewsCount = ((double)(internalSourcingInterviewCount)/((double)crowdsourcingInterviewCount+(double)vendorSourcingInterviewCount+(double)recruiterSourcingInterviewCount+(double)internalSourcingInterviewCount))*100;
				 Double internalRejectedCount = ((double)(internalSourcingRejectedCount)/((double)crowdsourcingRejectedCount+(double)vendorSourcingRejectedCount+(double)recruiterSourcingRejectedCount+(double)internalSourcingRejectedCount))*100;
				 Double internalOfferedCount = ((double)(internalSourcingOfferedCount)/((double)crowdsourcingOfferedCount+(double)vendorSourcingOfferedCount+(double)recruiterrSourcingOfferedCount+(double)internalSourcingOfferedCount))*100;
				 Double internalOfferAcceptedCount = ((double)(internalSourcingOfferAcceptedCount)/((double)crowdsourcingOfferAcceptedCount+(double)vendorSourcingOfferAcceptedCount+(double)recruiterSourcingOfferAcceptedCount+(double)internalSourcingOfferAcceptedCount))*100;
				 Double internalOfferRejectedCount = ((double)(internalSourcingOfferRejectedCount)/((double)crowdsourcingOfferRejectedCount+(double)vendorSourcingOfferRejectedCount+(double)recruiterSourcingOfferRejectedCount+(double)internalSourcingOfferRejectedCount))*100;
				 Double internalOfferBackoutCount = ((double)(internalSourcingBackoutCount)/((double)crowdsourcingOfferBackoutCount+(double)vendorSourcingOfferBackoutCount+(double)recruiterSourcingOfferBackoutCount+(double)internalSourcingBackoutCount))*100;
				 
				 internalSourcingApplicantsDTO.setApplicantsAvgCount(df.format(internalApplicantCount));
				 internalSourcingApplicantsDTO.setShortlistedAvgCount(df.format(internalShortlistedCount));
				 internalSourcingApplicantsDTO.setSubmitToClientAvgCount(df.format(internalSubmitToClientCount));
				 internalSourcingApplicantsDTO.setInterviewsAvgCount(df.format(internalInterviewsCount));
				 internalSourcingApplicantsDTO.setRejectedAvgCount(df.format(internalRejectedCount));
				 internalSourcingApplicantsDTO.setOfferedAvgCount(df.format(internalOfferedCount));
				 internalSourcingApplicantsDTO.setOfferAcceptedAvgCount(df.format(internalOfferAcceptedCount));
				 internalSourcingApplicantsDTO.setOfferRejectedAvgCount(df.format(internalOfferRejectedCount));
				 internalSourcingApplicantsDTO.setBackoutAvgCount(df.format(internalOfferBackoutCount));
				 
				 applicantBySourceList.add(internalSourcingApplicantsDTO);
				}
				 if(applicantBySourceList != null){
					 response.setData(applicantBySourceList);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setResponse(Result.FAILED.name());
						response.setResponseMessage(Result.FAILED.getValue());
					}
				 
				}else if(jobIdList == null || jobIdList.isEmpty()) {
				    	response.setData(applicantBySourceList); // returning empty data List if jobIdList is empty or null.
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
			    } 
			}
			else if (loginUser == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
			else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
				
		return response;
	}

}
