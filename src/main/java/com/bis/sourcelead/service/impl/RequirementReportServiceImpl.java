package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementReportDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class RequirementReportServiceImpl implements RequirementReportService {
	
	@Autowired
	RequirementReportDao requirementReportDao;
	@Autowired
	private TimeZoneService timeZoneService;
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public List<Requirement> getRequirementReports(String userRoleName, OrgLocations orgLocation, User user, String reportType, String customStartDate, String customEndDate) throws Exception
	{
		
		Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDateUTC = null;
		String filterDate = null;
		Date startDateUTC = null;
		if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)) {
			String endDate = outputFormatter1.print(zoned);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, endDate);
			endDateUTC = TZ_DF_TS.parse(endDateString);
		}
		if (null != reportType && !"".equals(reportType)) {
			if (Constants.TO_DAY.equals(reportType)) {
				String startDate = outputFormatter.print(zoned);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
				filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, startDate);
				startDateUTC = TZ_DF_TS.parse(startDateString);
			}
			else if (Constants.YESTER_DAY.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
			}
			else if (Constants.ONE_WEEK.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
			}
			else if (Constants.TWO_WEEKS.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
			}
			else if (Constants.ONE_MONTH.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
			}
			else if (Constants.TWO_MONTHS.equals(reportType)) {
				startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
			}
			else {
				if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)) {
					DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
					DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
					Date startTime = commonUtil.getDateFormat(customStartDate);
					Date endTime = commonUtil.getDateFormat(customEndDate);
					String customStartTimeStr = TZ_DF_CSD.format(startTime);
					String customEndTimeStr = TZ_DF_CED.format(endTime);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
					String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
					startDateUTC = TZ_DF_TS.parse(startDateString);
					endDateUTC = TZ_DF_TS.parse(endDateString);
				}
			}
		}
		List<Requirement> requirementList = requirementReportDao.getRequirementReports(userRoleName, orgLocation, user, filterDate, startDateUTC, endDateUTC);
		for (Requirement requirement : requirementList) {
			String postedDate = getDateStr(requirement.getCreatedDate(), timezone);
			requirement.setPostedDateStr(postedDate);
		}
		return requirementList;
	}
	
	@Override
	public List<RequirementSubmissionHistory> getApplicantsCountsPerReq(Long requirementId)
	{
		return requirementReportDao.getRequirementReports(requirementId);
	}
	
	@Override
	public Integer getApplicantsCountsForReports(Long orgLocationID, String applicantStatus) throws Exception
	{
		return requirementReportDao.getApplicantsCountsForReports(orgLocationID, applicantStatus);
	}
	
	@Override
	public List<RequirementSubmission> getReceivedApplicantsCountsPerReq(Long requirementId)
	{
		return requirementReportDao.getReceivedApplicantsCountsPerReq(requirementId);
	}
	
	@Override
	public List<RequirementSubmissionHistory> getReceivedApplicantsCountForReports(Long orgLocationId, Long requirementId)
	{
		return requirementReportDao.getReceivedApplicantsCountForReports(orgLocationId, requirementId);
	}
	
	@Override
	public List<RequirementSubmissionHistory> getApplicantsCountForChart(Long orgLocationId)
	{
		return requirementReportDao.getApplicantsCountForChart(orgLocationId);
	}
	
	@Override
	public Integer getRequirementStagesCountsForReports(Long orgLocId, String requirementStatus) throws Exception
	{
		return requirementReportDao.getRequirementStagesCountsForReports(orgLocId, requirementStatus);
	}
	
	@Override
	public List<Requirement> getRequirementStageReports(OrgLocations orgLocation, String Status) throws Exception
	{
		return requirementReportDao.getRequirementStageReports(orgLocation, Status);
	}
	
	@Override
	public List<RequirementSubmissionHistory> getReceivedApplicantsCountForReportsByVendorLocationId(Long orgLocationId, Long vendorLocationId, Date startDateUTC, Date endDateUTC)
	{
		return requirementReportDao.getReceivedApplicantsCountForReportsByVendorLocationId(orgLocationId, vendorLocationId, startDateUTC, endDateUTC);
	}
	
	@Override
	public List<RequirementSubmission> getReceivedApplicantsCountsByVendorLocationId(Long orgLocationId, Long vendorLocationId, Date startDateUTC, Date endDateUTC)
	{
		return requirementReportDao.getReceivedApplicantsCountsByVendorLocationId(orgLocationId, vendorLocationId, startDateUTC, endDateUTC);
	}
	
	@Override
	public List<RequirementSubmission> getTotalApplicants(OrgLocations orgLocation, Date startDateUTC, Date endDateUTC) throws Exception
	{
		return requirementReportDao.getTotalApplicants(orgLocation, startDateUTC, endDateUTC);
	}
	
	@Override
	public List<RequirementSubmissionHistory> getApplicantsCountsByDateRange(Long submissionId, Date startDateUTC, Date endDateUTC) throws Exception
	{
		return requirementReportDao.getApplicantsCountsByDateRange(submissionId, startDateUTC, endDateUTC);
	}
	
	@Override
	public List<RequirementSubmission> getReceivedApplicantsCountsPerOrgLocId(Long orgLocationId, Date startDateUTC, Date endDateUTC)
	{
		
		return requirementReportDao.getReceivedApplicantsCountsPerOrgLocId(orgLocationId, startDateUTC, endDateUTC);
	}
	
	@Override
	public List<RequirementSubmission> getAllSubmissionsByAssignTeamMember(Long orgLocationId, Set<String> userCodesList, Date startDateUTC, Date endDateUTC)
	{
		return requirementReportDao.getAllSubmissionsByAssignTeamMember(orgLocationId, userCodesList, startDateUTC, endDateUTC);
	}
	
	/**
	 * used to get yesterday,week ago etc.dates
	 * 
	 * @param DateFormat
	 *            TZ_DF_TS
	 * @param DateTimeFormatter
	 *            outputFormatter
	 * @param DateTime
	 *            zoned
	 * @param numberofDays
	 * @param Timezone
	 *            timezone
	 * @return Date
	 * @throws ParseException
	 */
	private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS, Timezone timezone, int numberOfDays) throws ParseException
	{
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, yesterDayDate);
		return TZ_DF_TS.parse(startDateString);
	}
	
	/**
	 * Returns date format as string
	 * 
	 * @param date
	 * @param timezone
	 * @return String
	 * @throws ParseException
	 */
	private String getDateStr(Date date, Timezone timezone) throws ParseException
	{
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat standardFormat = new SimpleDateFormat("dd-MM-yyyy");
		String currentTimeStr = Constants.TZ_DF.format(date);
		String currentDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(), currentTimeStr);
		Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
		return currentDateInTimezone = standardFormat.format(converteDate);
	}
}
