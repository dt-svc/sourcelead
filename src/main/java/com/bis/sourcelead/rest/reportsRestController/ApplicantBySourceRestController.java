package com.bis.sourcelead.rest.reportsRestController;

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
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reports.service.ApplicantsBySourceService;
import com.bis.sourcelead.reportsDTO.ApplicantsBySourceDTO;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class ApplicantBySourceRestController {
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	ApplicantsBySourceService applicantsBySourceService;
	
	@RequestMapping(value = "/getApplicantBySourceDetails", method = RequestMethod.GET)
	public List<ApplicantsBySourceDTO> getApplicantBySourceDetails(JobFilterDTO jobFilterDTO, HttpServletRequest request) throws Exception {
		
		List<ApplicantsBySourceDTO> applicantBySourceList = new ArrayList<>();
		List<Long> jobIdList = new ArrayList<>();
		
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
			searchDates = reportsUtil.getJobFilterDate(jobFilterDTO.getDateSelection());
		}
		
		ApplicantsBySourceDTO crowdSourcingApplicantsDTO = new ApplicantsBySourceDTO();
		
		Integer crowdsourcingApplicantsCount =  applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates,"SUBMISSION");
		Integer crowdsourcingSortlistedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "SHORTLIST");
		Integer crowdsourcingInterviewCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "INTERVIEW");
		Integer crowdsourcingRejectedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "REJECTED");
		Integer crowdsourcingOfferedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "OFFERMADE");
		Integer crowdsourcingOfferAcceptedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "OFFERACCEPTED");
		Integer crowdsourcingOfferRejectedCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "OFFERREJECTED");
		Integer crowdsourcingOfferBackoutCount = applicantsBySourceService.getCrowdsourcingApplicantsCountByStage(jobIdList, searchDates, "BACKOUT");
		crowdSourcingApplicantsDTO.setCategory("Crowd Sourcing");
		crowdSourcingApplicantsDTO.setApplicantsCount(crowdsourcingApplicantsCount);
		crowdSourcingApplicantsDTO.setShortlistedCount(crowdsourcingSortlistedCount);
		crowdSourcingApplicantsDTO.setInterviewsCount(crowdsourcingInterviewCount);
		crowdSourcingApplicantsDTO.setRejectedCount(crowdsourcingRejectedCount);
		crowdSourcingApplicantsDTO.setOfferedCount(crowdsourcingOfferedCount);
		crowdSourcingApplicantsDTO.setOfferAcceptedCount(crowdsourcingOfferAcceptedCount);
		crowdSourcingApplicantsDTO.setOfferRejectedCount(crowdsourcingOfferRejectedCount);
		crowdSourcingApplicantsDTO.setBackoutCount(crowdsourcingOfferBackoutCount);
		applicantBySourceList.add(crowdSourcingApplicantsDTO);
		
		
		ApplicantsBySourceDTO vendorSourcingApplicantsDTO = new ApplicantsBySourceDTO();
		
		Integer vendorSourcingApplicantCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "SUBMISSION");
		Integer vendorSourcingShortlistCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "SHORTLIST");
		Integer vendorSourcingInterviewCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "INTERVIEW");
		Integer vendorSourcingRejectedCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "REJECTED");
		Integer vendorSourcingOfferedCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "OFFERMADE");
		Integer vendorSourcingOfferAcceptedCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "OFFERACCEPTED");
		Integer vendorSourcingOfferRejectedCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "CLIENT_OFFERREJECTED");
		Integer vendorSourcingOfferBackoutCount = applicantsBySourceService.getVendorSourceApplicantsCountByStage(jobIdList, searchDates, "BACKOUT");
		
		vendorSourcingApplicantsDTO.setCategory("Vendors");
		vendorSourcingApplicantsDTO.setApplicantsCount(vendorSourcingApplicantCount);
		vendorSourcingApplicantsDTO.setShortlistedCount(vendorSourcingShortlistCount);
		vendorSourcingApplicantsDTO.setInterviewsCount(vendorSourcingInterviewCount);
		vendorSourcingApplicantsDTO.setRejectedCount(vendorSourcingRejectedCount);
		vendorSourcingApplicantsDTO.setOfferedCount(vendorSourcingOfferedCount);
		vendorSourcingApplicantsDTO.setOfferAcceptedCount(vendorSourcingOfferAcceptedCount);
		vendorSourcingApplicantsDTO.setOfferRejectedCount(vendorSourcingOfferRejectedCount);
		vendorSourcingApplicantsDTO.setBackoutCount(vendorSourcingOfferBackoutCount);
		applicantBySourceList.add(vendorSourcingApplicantsDTO);
		
		return applicantBySourceList;
	}

}
