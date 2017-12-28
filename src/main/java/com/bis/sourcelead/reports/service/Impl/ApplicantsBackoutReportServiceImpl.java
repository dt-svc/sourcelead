package com.bis.sourcelead.reports.service.Impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reports.dao.ApplicantsBackoutReportDao;
import com.bis.sourcelead.reports.service.ApplicantsBackoutReportService;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;
import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;
@Service
public class ApplicantsBackoutReportServiceImpl implements ApplicantsBackoutReportService {

	
	@Autowired
	private ApplicantsBackoutReportDao applicantsBackoutReportDao;
	
	@Override
	public Map<String, Integer> getApplicantBackoutReport(List<Long> jobIds, Date[] searchDates) {
		return applicantsBackoutReportDao.getApplicantBackoutReport(jobIds, searchDates);
	}

	@Override
	public void getApplicantBackoutReportForinduvidalJobs(List<Long> jobIdList, Date[] searchDates) {
		// applicantsBackoutReportDao.getApplicantBackoutReportForinduvidalJobs(jobIdList, searchDates);		
	}

	@Override
	public ApplicantsBackoutDTO getBackoutApplicantCount(List<Long> jobIds, Date[] searchDates,
			OrgLocations orgLocation) throws Exception {
		Integer count= 0;
		Map<String,Integer> typeCount = new LinkedHashMap<>();
		ApplicantsBackoutDTO applicantsBackoutDTO=new ApplicantsBackoutDTO();
		
		typeCount=applicantsBackoutReportDao.getApplicantBackoutReport(jobIds, searchDates);
		Iterator rejectedCount = typeCount.entrySet().iterator();
	    while (rejectedCount.hasNext()) {
	        Map.Entry rejectValue = (Map.Entry)rejectedCount.next();
	        System.out.println(rejectValue.getKey() + " = " + rejectValue.getValue());
	        if(rejectValue.getKey().equals("betterOffer")){
	        	applicantsBackoutDTO.setBetterOffer((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Current Company")){
	        	applicantsBackoutDTO.setCurrentCompany((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Compensation")){
	        	applicantsBackoutDTO.setCompensation((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("ICold Feet")){
	        	applicantsBackoutDTO.setColdFeet((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Personal")){
	        	applicantsBackoutDTO.setPersonal((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Other")){
	        	applicantsBackoutDTO.setOther((int)rejectValue.getValue());
	        }
	       }
	    applicantsBackoutDTO.setTotalCount(applicantsBackoutDTO.getBetterOffer() + applicantsBackoutDTO.getColdFeet() + applicantsBackoutDTO.getCompensation() + applicantsBackoutDTO.getCurrentCompany() + applicantsBackoutDTO.getOther() + applicantsBackoutDTO.getPersonal());
	    return applicantsBackoutDTO;
	}

}
