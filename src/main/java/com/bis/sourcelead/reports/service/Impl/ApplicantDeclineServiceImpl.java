package com.bis.sourcelead.reports.service.Impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reports.dao.ApplicantDeclineDao;
import com.bis.sourcelead.reports.service.ApplicantDeclineService;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;

@Service
public class ApplicantDeclineServiceImpl implements ApplicantDeclineService {
	
	@Autowired
	ApplicantDeclineDao applicantDeclineDao;

	@Override
	public ApplicantDeclineDTO getDeclineApplicantSkillCount(List<Long> jobIds, Date[] searchDates,OrgLocations orgLocation)throws Exception {
		Integer count= 0;
		Map<String,Integer> typeCount = new LinkedHashMap<>();
		ApplicantDeclineDTO applicantDeclineDTO=new ApplicantDeclineDTO();
		
		typeCount=applicantDeclineDao.getDeclineApplicantSkillCount(jobIds, searchDates,orgLocation);
		Iterator rejectedCount = typeCount.entrySet().iterator();
	    while (rejectedCount.hasNext()) {
	        Map.Entry rejectValue = (Map.Entry)rejectedCount.next();
	        System.out.println(rejectValue.getKey() + " = " + rejectValue.getValue());
	        if(rejectValue.getKey().equals("Wrong Skill Sets")){
	        	applicantDeclineDTO.setSkillsCount((Integer)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Unsuitable Personality")){
	        	applicantDeclineDTO.setUnsuitableCount((Integer)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Bad Fit")){
	        	applicantDeclineDTO.setBadfitCount((Integer)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Interview Rejection")){
	        	applicantDeclineDTO.setInterviewCount((Integer)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Salary")){
	        	applicantDeclineDTO.setSalaryCount((Integer)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Other")){
	        	applicantDeclineDTO.setOtherCount((Integer)rejectValue.getValue());
	        }
	       }
           applicantDeclineDTO.setTotalCount(applicantDeclineDTO.getSkillsCount() + applicantDeclineDTO.getBadfitCount() + applicantDeclineDTO.getUnsuitableCount() + applicantDeclineDTO.getInterviewCount() + applicantDeclineDTO.getSalaryCount() + applicantDeclineDTO.getOtherCount());
	    return applicantDeclineDTO;

}
	
}	
	
