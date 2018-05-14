package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TimesheetActivityDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimesheetActivities;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.TimesheetActivityService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class TimesheetActivityServiceImpl implements TimesheetActivityService {

	@Autowired
	private TimesheetActivityDao timesheetActivityDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<TimesheetActivities> getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDate(Long projectId,Long masterCAndidateId,String timesheetFrequency,OrgLocations orgLocations) throws Exception {
			String[]	timesheetFrequencyDates = timesheetFrequency.split("To");
			Date frequencyStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), timesheetFrequencyDates[0]);
			Date frequencyEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), timesheetFrequencyDates[1]);
			List<TimesheetActivities> submissionHistory= timesheetActivityDao.getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDate(projectId,masterCAndidateId,frequencyStartDate,frequencyEndDate);
			 for(TimesheetActivities activity:submissionHistory) {
				 activity.setDisplayDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), activity.getCreatedDate()));
				 SimpleDateFormat localtime = new SimpleDateFormat("HH:mm:ss");
			     String time = localtime.format(activity.getCreatedDate());
			     activity.setDisplayTime(time);
			     if(activity!= null &&  activity.getActionPerformedbyUserId() != null) {
				     User user = userService.getUserById(activity.getActionPerformedbyUserId());
				     StringBuilder userName = new StringBuilder();
				     if(user.getLastName() != null && StringUtils.isNotBlank(user.getLastName())) {
				    	 userName.append(user.getFirstName()).append(" ").append(user.getLastName());
				     }else {
				    	 userName.append(user.getFirstName());
				     }
				     activity.setUserFullName(userName.toString());
			     }
			 }
			return submissionHistory;
	}
	

	@Override
	public TimesheetActivities saveTimesheetActivity(TimesheetActivities ta) {
		return timesheetActivityDao.saveTimesheetActivity(ta);
		
	}

}
