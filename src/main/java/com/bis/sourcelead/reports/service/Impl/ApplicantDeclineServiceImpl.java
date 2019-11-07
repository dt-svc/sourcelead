package com.bis.sourcelead.reports.service.Impl;

import java.util.Date;
import java.util.List;

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

/*	public ApplicantDeclineDTO getDeclineApplicantSkillCount(List<Long> jobIds, Date[] searchDates,OrgLocations orgLocation,int offset)throws Exception {
		Integer count= 0;
		ApplicantDeclineDTO applicantDeclineDTO=new ApplicantDeclineDTO();
		
		List<ApplicantDeclineDTO>typeCount=applicantDeclineDao.getDeclineApplicantSkillCount(jobIds, searchDates,orgLocation, count);
		Iterator rejectedCount = typeCount.entrySet().iterator();
	    while (rejectedCount.hasNext()) {
	        Map.Entry rejectValue = (Map.Entry)rejectedCount.next();
	        System.out.println(rejectValue.getKey() + " = " + rejectValue.getValue());
	        if(rejectValue.getKey().equals("Wrong Skill Sets")){
	        	applicantDeclineDTO.setSkillsCount((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Unsuitable Personality")){
	        	applicantDeclineDTO.setUnsuitableCount((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Bad Fit")){
	        	applicantDeclineDTO.setBadfitCount((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Interview Rejection")){
	        	applicantDeclineDTO.setInterviewCount((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Salary")){
	        	applicantDeclineDTO.setSalaryCount((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Other")){
	        	applicantDeclineDTO.setOtherCount((int)rejectValue.getValue());
	        }
	       }
           applicantDeclineDTO.setTotalCount(applicantDeclineDTO.getSkillsCount() + applicantDeclineDTO.getBadfitCount() + applicantDeclineDTO.getUnsuitableCount() + applicantDeclineDTO.getInterviewCount() + applicantDeclineDTO.getSalaryCount() + applicantDeclineDTO.getOtherCount());
	    return typeCount.get(0);

}*/

	@Override
	public ApplicantDeclineDTO getDeclineApplicantSkillCount(List<Long> jobIds, Date[] searchDates,	OrgLocations orgLocation) throws Exception {
		// TODO Auto-generated method stub
		
		Integer count= 0;
		ApplicantDeclineDTO applicantDeclineDTO=new ApplicantDeclineDTO();
		
		List<ApplicantDeclineDTO>typeCount=applicantDeclineDao.getDeclineApplicantSkillCount(jobIds, searchDates,orgLocation, count);
		
	    return typeCount.get(0);
	}
	
}	
	
