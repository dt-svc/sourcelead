package com.bis.sourcelead.rest.reportsRestController;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
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
	
	@RequestMapping(value = "/getApplicantBySourceDetails", method = RequestMethod.GET)
	public List<ApplicantsBySourceDTO> getApplicantBySourceDetails(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ApplicantsBySourceDTO> applicantBySourceList = new ArrayList<>();
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
	    	    searchDates[0] = fromDate;
	    	    searchDates[1] = toDate;
			}
		}
		
		
		if(jobIdList != null && jobIdList.size() > 0) {
		ApplicantsBySourceDTO crowdSourcingApplicantsDTO = new ApplicantsBySourceDTO();
		
		Integer crowdsourcingApplicantsCount =  applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates,"SUBMISSION");
		Integer crowdsourcingSortlistedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "SHORTLIST");
		Integer crowdsourcingSubmitToClientCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "SUBMIT_TO_CLIENT");
		
		Integer crowdsourcingInterviewCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "INTERVIEW");
		Integer crowdsourcingClientInterviewCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "CLIENT_INTERVIEW");
		crowdsourcingInterviewCount = crowdsourcingInterviewCount+crowdsourcingClientInterviewCount;
		
		Integer crowdsourcingRejectedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "REJECTED");
		
		Integer crowdsourcingOfferedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "OFFERMADE");
		Integer crowdsourcingClientOfferedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "CLIENT_OFFERMADE");
		crowdsourcingOfferedCount = crowdsourcingOfferedCount+crowdsourcingClientOfferedCount;
		
		Integer crowdsourcingOfferAcceptedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "OFFERACCEPTED");
		Integer crowdsourcingClientOfferAcceptedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "CLIENT_OFFERACCEPTED");
		crowdsourcingOfferAcceptedCount = crowdsourcingOfferAcceptedCount+crowdsourcingClientOfferAcceptedCount;
		
		Integer crowdsourcingOfferRejectedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "OFFERREJECTED");
		Integer crowdsourcingOfferBackoutCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "BACKOUT");
		crowdSourcingApplicantsDTO.setCategory("Crowd Sourcing");
		crowdSourcingApplicantsDTO.setApplicantsCount(crowdsourcingApplicantsCount);
		crowdSourcingApplicantsDTO.setShortlistedCount(crowdsourcingSortlistedCount);
		crowdSourcingApplicantsDTO.setSubmitToClientCount(crowdsourcingSubmitToClientCount);
		crowdSourcingApplicantsDTO.setInterviewsCount(crowdsourcingInterviewCount);
		crowdSourcingApplicantsDTO.setRejectedCount(crowdsourcingRejectedCount);
		crowdSourcingApplicantsDTO.setOfferedCount(crowdsourcingOfferedCount);
		crowdSourcingApplicantsDTO.setOfferAcceptedCount(crowdsourcingOfferAcceptedCount);
		crowdSourcingApplicantsDTO.setOfferRejectedCount(crowdsourcingOfferRejectedCount);
		crowdSourcingApplicantsDTO.setBackoutCount(crowdsourcingOfferBackoutCount);
		
		
		
		ApplicantsBySourceDTO vendorSourcingApplicantsDTO = new ApplicantsBySourceDTO();
		
		Integer vendorSourcingApplicantCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "SUBMISSION");
		Integer vendorSourcingShortlistCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "SHORTLIST");
		Integer vendorSourcingSubmitToClientCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "SUBMIT_TO_CLIENT");
		
		Integer vendorSourcingInterviewCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "INTERVIEW");
		Integer vendorSourcingClientInterviewCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "CLIENT_INTERVIEW");
		vendorSourcingInterviewCount = vendorSourcingInterviewCount+vendorSourcingClientInterviewCount;
		
		Integer vendorSourcingRejectedCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "REJECTED");
		
		Integer vendorSourcingOfferedCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "OFFERMADE");
		Integer vendorSourcingClientOfferedCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "CLIENT_OFFERMADE");
		vendorSourcingOfferedCount = vendorSourcingOfferedCount+vendorSourcingClientOfferedCount;
		
		Integer vendorSourcingOfferAcceptedCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "OFFERACCEPTED");
		Integer vendorSourcingClientOfferAcceptedCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "CLIENT_OFFERACCEPTED");
		vendorSourcingOfferAcceptedCount = vendorSourcingOfferAcceptedCount+vendorSourcingClientOfferAcceptedCount;
		
		Integer vendorSourcingOfferRejectedCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "CLIENT_OFFERREJECTED");
		Integer vendorSourcingOfferBackoutCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "BACKOUT");
		
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
		
		
		
		ApplicantsBySourceDTO recruiterSourcingApplicantsDTO = new ApplicantsBySourceDTO();
		Integer recruiterSourcingApplicantCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "SUBMISSION");
		Integer recruiterSourcingShortlistCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "SHORTLIST");
		Integer recruiterSourcingSubmitToClientCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "SUBMIT_TO_CLIENT");
		
		Integer recruiterSourcingInterviewCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "INTERVIEW");
		Integer recruiterSourcingClientInterviewCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "CLIENT_INTERVIEW");
		recruiterSourcingInterviewCount = recruiterSourcingInterviewCount+recruiterSourcingClientInterviewCount;
		
		Integer recruiterSourcingRejectedCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "REJECTED");
		
		Integer recruiterrSourcingOfferedCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "OFFERMADE");
		Integer recruiterrSourcingClientOfferedCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "CLIENT_OFFERMADE");
		recruiterrSourcingOfferedCount = recruiterrSourcingOfferedCount+recruiterrSourcingClientOfferedCount;
		
		Integer recruiterSourcingOfferAcceptedCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "OFFERACCEPTED");
		Integer recruiterSourcingClientOfferAcceptedCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "CLIENT_OFFERACCEPTED");
		recruiterSourcingOfferAcceptedCount = recruiterSourcingOfferAcceptedCount+recruiterSourcingClientOfferAcceptedCount;
		
		Integer recruiterSourcingOfferRejectedCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "OFFERREJECTED");
		Integer recruiterSourcingOfferBackoutCount = applicantsBySourceService.getFreelanceRecruiterSourceApplicantsCountByStage(jobIdList, searchDates, "BACKOUT");
		
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
	
		
		
		ApplicantsBySourceDTO internalSourcingApplicantsDTO = new ApplicantsBySourceDTO();
		Integer internalSourcingApplicantCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "SUBMISSION",orgLocation.getId());
		Integer internalSourcingShortlistCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "SHORTLIST",orgLocation.getId());
		Integer internalSourcingSubmitToClientCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "SUBMIT_TO_CLIENT",orgLocation.getId());
		Integer internalSourcingInterviewCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "INTERVIEW",orgLocation.getId());
		Integer internalSourcingClientInterviewCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "CLIENT_INTERVIEW",orgLocation.getId());
		internalSourcingInterviewCount = internalSourcingInterviewCount+internalSourcingClientInterviewCount;
		
		Integer internalSourcingRejectedCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "REJECTED",orgLocation.getId());
		
		Integer internalSourcingOfferedCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "OFFERMADE",orgLocation.getId());
		Integer internalSourcingClientOfferedCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "CLIENT_OFFERMADE",orgLocation.getId());
		internalSourcingOfferedCount = internalSourcingOfferedCount+internalSourcingClientOfferedCount;
		
		Integer internalSourcingOfferAcceptedCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "OFFERACCEPTED",orgLocation.getId());
		Integer internalSourcingClientOfferAcceptedCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "CLIENT_OFFERACCEPTED",orgLocation.getId());
		internalSourcingOfferAcceptedCount = internalSourcingOfferAcceptedCount+internalSourcingClientOfferAcceptedCount;
		
		Integer internalSourcingOfferRejectedCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "OFFERREJECTED",orgLocation.getId());
		Integer internalSourcingBackoutCount = applicantsBySourceService.getInternalSourceApplicantsCountByStage(jobIdList, searchDates, "BACKOUT",orgLocation.getId());
		
		internalSourcingApplicantsDTO.setCategory("Internal DB");
		internalSourcingApplicantsDTO.setApplicantsCount(internalSourcingApplicantCount);
		internalSourcingApplicantsDTO.setShortlistedCount(internalSourcingShortlistCount);
		internalSourcingApplicantsDTO.setSubmitToClientCount(internalSourcingSubmitToClientCount);
		internalSourcingApplicantsDTO.setInterviewsCount(internalSourcingInterviewCount);
		internalSourcingApplicantsDTO.setRejectedCount(internalSourcingRejectedCount);
		internalSourcingApplicantsDTO.setOfferedCount(internalSourcingOfferedCount);
		internalSourcingApplicantsDTO.setOfferAcceptedCount(internalSourcingOfferAcceptedCount);
		internalSourcingApplicantsDTO.setOfferRejectedCount(internalSourcingOfferRejectedCount);
		internalSourcingApplicantsDTO.setBackoutCount(internalSourcingBackoutCount);
		
		
		 Double crowdSoruceApplicantCount = ((double)(crowdsourcingApplicantsCount)/((double)crowdsourcingApplicantsCount+(double)vendorSourcingApplicantCount+(double)recruiterSourcingApplicantCount+(double)internalSourcingApplicantCount))*100;
		 Double crowdSoruceShortlistedCount = ((double)(crowdsourcingSortlistedCount)/((double)crowdsourcingSortlistedCount+(double)vendorSourcingShortlistCount+(double)recruiterSourcingShortlistCount+(double)internalSourcingShortlistCount))*100;
		 Double crowdSoruceSubmitToClientCount = ((double)(crowdsourcingSubmitToClientCount)/((double)crowdsourcingSubmitToClientCount+(double)vendorSourcingSubmitToClientCount+(double)recruiterSourcingSubmitToClientCount+(double)internalSourcingSubmitToClientCount))*100;
		 Double crowdSoruceInterviewsCount = ((double)(crowdsourcingInterviewCount)/((double)crowdsourcingInterviewCount+(double)vendorSourcingInterviewCount+(double)recruiterSourcingInterviewCount+(double)internalSourcingInterviewCount))*100;
		 Double crowdSoruceRejectedCount = ((double)(crowdsourcingRejectedCount)/((double)crowdsourcingRejectedCount+(double)vendorSourcingRejectedCount+(double)recruiterSourcingRejectedCount+(double)internalSourcingInterviewCount))*100;
		 Double crowdSoruceOfferedCount = ((double)(crowdsourcingOfferedCount)/((double)crowdsourcingOfferedCount+(double)vendorSourcingOfferedCount+(double)recruiterrSourcingOfferedCount+(double)internalSourcingOfferedCount))*100;
		 Double crowdSoruceOfferAcceptedCount = ((double)(crowdsourcingOfferAcceptedCount)/((double)crowdsourcingOfferAcceptedCount+(double)vendorSourcingOfferAcceptedCount+(double)recruiterSourcingOfferAcceptedCount+(double)internalSourcingOfferAcceptedCount))*100;
		 Double crowdSoruceOfferRejectedCount = ((double)(crowdsourcingOfferRejectedCount)/((double)crowdsourcingOfferRejectedCount+(double)vendorSourcingOfferRejectedCount+(double)recruiterSourcingOfferRejectedCount+(double)internalSourcingOfferRejectedCount))*100;
		 Double crowdSoruceOfferBackoutCount = ((double)(crowdsourcingOfferBackoutCount)/((double)crowdsourcingOfferBackoutCount+(double)vendorSourcingOfferBackoutCount+(double)recruiterSourcingOfferBackoutCount+(double)internalSourcingBackoutCount))*100;
		 
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
		
		return applicantBySourceList;
	}

}
