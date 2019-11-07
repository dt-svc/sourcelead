package com.bis.sourcelead.reports.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reports.dao.ApplicantsBackoutReportDao;
import com.bis.sourcelead.reports.service.ApplicantsBackoutReportService;
import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;
@Service
public class ApplicantsBackoutReportServiceImpl implements ApplicantsBackoutReportService {

	
	@Autowired
	private ApplicantsBackoutReportDao applicantsBackoutReportDao;
	
	@Override
	public List<ApplicantsBackoutDTO> getApplicantBackoutReport(List<Long> jobIds, Date[] searchDates) {
		return applicantsBackoutReportDao.getApplicantBackoutReport(jobIds, searchDates, 0);
	}

	@Override
	public void getApplicantBackoutReportForinduvidalJobs(List<Long> jobIdList, Date[] searchDates) {
		// applicantsBackoutReportDao.getApplicantBackoutReportForinduvidalJobs(jobIdList, searchDates);		
	}

	@Override
	public ApplicantsBackoutDTO getBackoutApplicantCount(List<Long> jobIds, Date[] searchDates,
			OrgLocations orgLocation) throws Exception {
		Integer count= 0;
		//List<ApplicantsBackoutDTO> typeCount = new ArrayList<>();
		@SuppressWarnings("unused")
		ApplicantsBackoutDTO applicantsBackoutDTO=new ApplicantsBackoutDTO();
		
		List<ApplicantsBackoutDTO>countList=applicantsBackoutReportDao.getApplicantBackoutReport(jobIds, searchDates, count);
		ApplicantsBackoutDTO applicantDtoList = new ApplicantsBackoutDTO();
		/*for(ApplicantsBackoutDTO ApplicantsBackList : countList){
			ApplicantsBackoutDTO applicantDtoList = new ApplicantsBackoutDTO();
			ApplicantsBackList.setBetterOffer(ApplicantsBackList.get));
			
			
			
		}*/
			
		/*Iterator rejectedCount = typeCount.entrySet().iterator();
	    while (rejectedCount.hasNext()) {
	        Map.Entry rejectValue = (Map.Entry)rejectedCount.next();
	        System.out.println(rejectValue.getKey() + " = " + rejectValue.getValue());
	        if(rejectValue.getKey().equals("Better Offer")){
	        	applicantsBackoutDTO.setBetterOffer((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Current Company")){
	        	applicantsBackoutDTO.setCurrentCompany((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Compensation")){
	        	applicantsBackoutDTO.setCompensation((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Cold Feet")){
	        	applicantsBackoutDTO.setColdFeet((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("Personal")){
	        	applicantsBackoutDTO.setPersonal((int)rejectValue.getValue());
	        }
	        if(rejectValue.getKey().equals("other")){
	        	applicantsBackoutDTO.setOther((int)rejectValue.getValue());
	        }
	       }*/
	    //applicantsBackoutDTO.setTotalCount(applicantsBackoutDTO.getBetterOffer() + applicantsBackoutDTO.getColdFeet() + applicantsBackoutDTO.getCompensation() + applicantsBackoutDTO.getCurrentCompany() + applicantsBackoutDTO.getOther() + applicantsBackoutDTO.getPersonal());
	    return countList.get(0);
	}

}
