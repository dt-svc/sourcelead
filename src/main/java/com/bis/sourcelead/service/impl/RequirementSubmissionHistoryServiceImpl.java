package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementSubmissionHistoryDao;
import com.bis.sourcelead.dao.TimeZoneDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.web.LoginSessionController;

@Service
public class RequirementSubmissionHistoryServiceImpl implements RequirementSubmissionHistoryService {

	@Autowired
	RequirementSubmissionHistoryDao requirementSubmissionHistoryDao;
	
	@Autowired
	TimeZoneDao timeZoneDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	/**
	 * updating the Requirement Submission Hostory
	 * @param requirementSubmission
	 * @param user
	 * @param requirementSubmissionHistory
	 * @return
	 * @throws Exception
	 */
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	public RequirementSubmissionHistory updateRequirementHistory(RequirementSubmission requirementSubmission,User user,RequirementSubmissionHistory requirementSubmissionHistory)
			throws Exception {
		StringBuilder userName = new StringBuilder();
		requirementSubmissionHistory.setSubmissionId(requirementSubmission);
		requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().toString());
		if(user != null){
			requirementSubmissionHistory.setCreatedBy(user.getUserCode());
			requirementSubmissionHistory.setUpdatedBy(user.getUserCode());	
			if(null != user.getLastName() && !"".equalsIgnoreCase(user.getLastName())){
				userName = userName.append(user.getFirstName()).append(" ").append(user.getLastName());
			}else{
				userName = userName.append(user.getFirstName());
			}
		}
		requirementSubmissionHistory.setCreatedDate(new Date());
		requirementSubmissionHistory.setUpdatedDate(new Date());
		requirementSubmissionHistory.setCreatedUser(userName.toString());
		
		return requirementSubmissionHistoryDao.updateRequirementHistory(requirementSubmissionHistory);
	}

	/**
	 * Read Requirement history based on locationid and submission type
	 * @param orgLocationID
	 * @param submissionType
	 * @throws Exception 
	 */
	@Override
	public List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndStage(Long orgLocationID,String submissionType,Long submissionId) throws Exception {
		return requirementSubmissionHistoryDao.getRequirementHistoryByLocationIdAndStage(orgLocationID,submissionType,submissionId);
	}

	@Override
	public RequirementSubmissionHistory getRequirementSubmissionHistoryBySubmissionId(Long submissionId) {
		return requirementSubmissionHistoryDao.getRequirementSubmissionHistoryBySubmissionId(submissionId);
	}

	@Override
	public List<RequirementSubmissionHistory> getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(
			Long candidateId, Long orgLocationId, Long requirementId, Long submissionId) throws Exception {
		return requirementSubmissionHistoryDao.getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(candidateId,orgLocationId,requirementId,submissionId);
	}
	
	@Override
	public RequirementSubmissionHistory isInCoolingOffPeriodByEmailAndLocationsId(
			String email, OrgLocations orgLocations, String reqCoolingOffPeriod, Long requirementId) throws Exception {
		
		// This Code is for  getting the sql enddate based on range
	     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Calendar c = Calendar.getInstance();
	     c.setTime(new Date()); // Now use today date.
	     Integer dateRange = Integer.parseInt(reqCoolingOffPeriod);
        c.add(Calendar.DATE, -dateRange); // Subtracting dateRange days
        String updatedDate     = dateFormat.format(c.getTime());
        Date parsed = dateFormat.parse(updatedDate);
        java.sql.Timestamp coolingOffEndDate = new java.sql.Timestamp(parsed.getTime());
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		RequirementSubmissionHistory requirementSubmissionHistory1 = requirementSubmissionHistoryDao.isInCoolingOffPeriodByEmailAndLocationsId(email, orgLocations.getId(), reqCoolingOffPeriod, requirementId);
		
		boolean isInCoolingOffPeriod = false;
		if(null != requirementSubmissionHistory1){
			if(coolingOffEndDate.before(requirementSubmissionHistory1.getCreatedDate())){
				isInCoolingOffPeriod = true;
				requirementSubmissionHistory1.setIsInCoolingOffPeriod(isInCoolingOffPeriod);
	
				 Calendar cp = Calendar.getInstance();
				
			     cp.setTime(requirementSubmissionHistory1.getCreatedDate()); // Now use today date.
			     Integer coolingOffPeriodEndDate = Integer.parseInt(reqCoolingOffPeriod);
			     cp.add(Calendar.DATE, coolingOffPeriodEndDate);
		         String updatedCPDate     = dateFormat.format(cp.getTime());
		         Date parsedCPED = dateFormat.parse(updatedCPDate);
				
				Timezone timezone = null;
				if(null != orgLocations.getTimezone()){
			    	 timezone = timeZoneDao.getTimezone(orgLocations.getTimezone().getId());
				 }
				
				if(parsedCPED != null){
					String currentTimeStr = Constants.TZ_DF.format(parsedCPED);
					if(null != timezone && null != timezone.getTimeZoneId()){
						String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
						DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
						currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),converteDate);
						requirementSubmissionHistory1.setCoolingOffPeriodEndDateStr(currentDateInTimezone);
						
						/*SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
				        String time = localDateFormat.format(converteDate);
				        requirementSubmission.setCreatedTimeStr(time);*/
					}
			    }
			}else{
				requirementSubmissionHistory1.setIsInCoolingOffPeriod(isInCoolingOffPeriod);
			}
		}else{
			requirementSubmissionHistory1 = new RequirementSubmissionHistory();
			requirementSubmissionHistory1.setIsInCoolingOffPeriod(isInCoolingOffPeriod);
		}
		return requirementSubmissionHistory1;
	}

	@Override
	public List<RequirementSubmissionHistory> getAllRequirementSubmissionHistoryBySubmissionId(List<Long> submissionIds)
			throws Exception {
		return requirementSubmissionHistoryDao.getAllRequirementSubmissionHistoryBySubmissionId(submissionIds);
	}

	@Override
	public Long getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user,
			String submissionStage, Date startDateUTC, Date endDateUTC) throws Exception {
		
		return requirementSubmissionHistoryDao.getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(orgLocationId, user, submissionStage, startDateUTC, endDateUTC);
	}

	@Override
	public List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndSubId(Long orgLocationID, Long submissionId)
	{
		return requirementSubmissionHistoryDao.getRequirementHistoryByLocationIdAndSubId(orgLocationID, submissionId);
	}

}
