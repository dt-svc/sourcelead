package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dao.RequirementSubmissionHistoryDao;
import com.bis.sourcelead.dao.TimeZoneDao;
import com.bis.sourcelead.dto.OfferAcceptanceRateDTO;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OfferAcceptanceRateView;
import com.bis.sourcelead.entity.OfferAcceptanceRateViewForVendor;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RecruiterJobsReportView;
import com.bis.sourcelead.entity.RecruiterReportView;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class RequirementSubmissionHistoryServiceImpl implements RequirementSubmissionHistoryService {

	@Autowired
	RequirementSubmissionHistoryDao requirementSubmissionHistoryDao;
	
	@Autowired
	TimeZoneDao timeZoneDao;
	
	@Autowired
	private OrgRelationsDao orgRelationsDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	OrgSpocService orgSpocService;
	
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
	public RequirementSubmissionHistory updateRequirementHistory(RequirementSubmission requirementSubmission,User user,RequirementSubmissionHistory requirementSubmissionHistory, String masterUserCode)
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
		requirementSubmissionHistory.setMasterUserCode(masterUserCode);
		
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
	public Integer getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user,
			String submissionStage, Date startDateUTC, Date endDateUTC,String isGlobalUser) throws Exception {
		
		return requirementSubmissionHistoryDao.getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(orgLocationId, user, submissionStage, startDateUTC, endDateUTC,isGlobalUser);
	}

	@Override
	public List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndSubId(Long orgLocationID, Long submissionId)
	{
		return requirementSubmissionHistoryDao.getRequirementHistoryByLocationIdAndSubId(orgLocationID, submissionId);
	}

	@Override
	public RequirementSubmissionHistory addRequirementHistory(RequirementSubmissionHistory requirementSubmissionHistory)
			throws Exception {
		return requirementSubmissionHistoryDao.addRequirementHistory(requirementSubmissionHistory);
	}

	@Override
	public RecruiterReportView getSubmissionCountsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user,
			String submissionStage, String startDateUTC, String endDateUTC, String isGlobalUser,Long orgId) throws Exception {
		return requirementSubmissionHistoryDao.getSubmissionCountsByStageAndCreatedUserAndOrglocationId(orgLocationId, user, submissionStage, startDateUTC, endDateUTC, isGlobalUser,orgId);
	}

	@Override
	public List<RecruiterReportView> getSubmissionCountsByStageAndCreatedUserListAndOrglocationId(Long orgLocationId,
			List<String> userCodeList, String startDateUTC, String endDateUTC,
			String isGlobalUser, Long orgId) throws Exception {
		return requirementSubmissionHistoryDao.getSubmissionCountsByStageAndCreatedUserListAndOrglocationId(orgLocationId, userCodeList, startDateUTC, endDateUTC, isGlobalUser, orgId);
	}

	@Override
	public ResponseList<RequirementSubmissionHistory> getAllSubmissionHistoryApplicantsByOwner(RequirementSubmissionHistoryDTO sumissionHistoryDTO,String userCode, String stage,Date[] searchDates,Long locationId) throws Exception {
		
		return requirementSubmissionHistoryDao.getAllSubmissionHistoryApplicantsByOwner(sumissionHistoryDTO,userCode,stage,searchDates,locationId);
	}

	@Override
	public int updateSourceUserCodeForSubmissionHistory() {
		return requirementSubmissionHistoryDao.updateSourceUserCodeForSubmissionHistory();
	}
	
	@Override
	public ResponseList<OfferAcceptanceRateDTO> getOfferAcceptanceRateList(String userCode, long orgLocationId, String selectedClients, String filterType, String filteredDate) {
		ResponseList<OfferAcceptanceRateDTO> responseList = new ResponseList<>();
		try {
			int month = 0, year = 0;
			boolean compare = false;
			if(filterType != null && !filterType.isEmpty() && filteredDate != null && !filteredDate.isEmpty()) {
				switch (filterType) {
				case "month":
					month = Integer.parseInt(filteredDate);
					break;
				case "year":
					year = Integer.parseInt(filteredDate);
					break;
				case "compare":
					compare = true;
					break;
				default:
					break;
				}
			}
			List<OfferAcceptanceRateView> offerAcceptanceRateViewList = requirementSubmissionHistoryDao.getOfferAcceptanceRateList(userCode, orgLocationId, selectedClients, month, year, compare);
			responseList.setData(convertOfferAcceptanceRateListToDTOList(offerAcceptanceRateViewList, responseList, month, year, compare));
			responseList.setResponse(Result.SUCCESS.name());
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return responseList;
	}
	
	@Override
	public ResponseList<OfferAcceptanceRateDTO> getOfferAcceptanceRateListForVendor(List<Long> parentRequirementIdList, long orgRelationId, long spocId, String filterType, String filteredDate) {
		ResponseList<OfferAcceptanceRateDTO> responseList = new ResponseList<>();
		try {
			int month = 0, year = 0;
			boolean compare = false;
			if(filterType != null && !filterType.isEmpty() && filteredDate != null && !filteredDate.isEmpty()) {
				switch (filterType) {
				case "month":
					month = Integer.parseInt(filteredDate);
					break;
				case "year":
					year = Integer.parseInt(filteredDate);
					break;
				case "compare":
					compare = true;
					break;
				default:
					break;
				}
			}
			List<OfferAcceptanceRateViewForVendor> offerAcceptanceRateViewList = new LinkedList<>();
			String masterUserCode = "";
			if(orgRelationId > 0 && spocId > 0) {
				OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(spocId);
				if(orgSpoc != null) {
					masterUserCode = "'" + orgSpoc.getVendorUser().getUserCode() + "'";
				}
			} else if(orgRelationId > 0) {
				List<OrgSpoc> orgSpocList = orgSpocService.getAllContactsbyOrgId(orgRelationId);
				if(orgSpocList != null && !orgSpocList.isEmpty()) {
					for(OrgSpoc orgSpoc : orgSpocList) {
						masterUserCode += (masterUserCode.isEmpty() ? "" : ",") + "'" + orgSpoc.getVendorUser().getUserCode() + "'";
					}
				}
			}
			offerAcceptanceRateViewList = requirementSubmissionHistoryDao.getOfferAcceptanceRateListForVendor(parentRequirementIdList, masterUserCode, month, year, compare);
			responseList.setData(convertOfferAcceptanceRateForVendorListToDTOList(offerAcceptanceRateViewList, responseList, month, year, compare));
			responseList.setResponse(Result.SUCCESS.name());
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return responseList;
	}
	
	private List<OfferAcceptanceRateDTO> convertOfferAcceptanceRateListToDTOList(List<OfferAcceptanceRateView> offerAcceptanceRateViewList, ResponseList<OfferAcceptanceRateDTO> responseList, int month, int year, boolean compare) {
		List<OfferAcceptanceRateDTO> offerAcceptanceRateDTOList = new LinkedList<>();
		try {
			if(offerAcceptanceRateViewList != null && !offerAcceptanceRateViewList.isEmpty()) {
				//Grouping & Sorting OfferAcceptanceRateViewList by CreatedYearMonthDate if month > 0 otherwise by CreatedYearMonth
				Map<String, List<OfferAcceptanceRateView>> oarMap = offerAcceptanceRateViewList.stream()
						.collect(Collectors.groupingBy(compare ? OfferAcceptanceRateView::getCreatedYear : month > 0 ? OfferAcceptanceRateView::getCreatedYearMonthDate : OfferAcceptanceRateView::getCreatedYearMonth))
						.entrySet().stream().sorted(Map.Entry.comparingByKey())
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
								(oldValue, newValue) -> oldValue, LinkedHashMap::new));
				if((oarMap != null && oarMap.keySet().size() > 1) || compare) {
					for(String date : oarMap.keySet()) {
						offerAcceptanceRateDTOList.add(getOfferAcceptanceRateDTOByOfferAcceptanceRateViewList(oarMap.get(date), date));
					}
					responseList.setResponseMessage(compare ? "compare" : month > 0 ? "month" : "year");
				} else if(oarMap != null && oarMap.keySet().size() == 1) {
					Map<String, List<OfferAcceptanceRateView>> oarMap2 = oarMap.get(oarMap.keySet().iterator().next())
							.stream().collect(Collectors.groupingBy(OfferAcceptanceRateView::getCreatedYearMonthDate))
							.entrySet().stream().sorted(Map.Entry.comparingByKey())
							.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
									(oldValue, newValue) -> oldValue, LinkedHashMap::new));
					if (oarMap2 != null) {
						for (String date : oarMap2.keySet()) {
							offerAcceptanceRateDTOList.add(getOfferAcceptanceRateDTOByOfferAcceptanceRateViewList(oarMap2.get(date), date));
						}
						responseList.setResponseMessage("month");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offerAcceptanceRateDTOList;
	}
	
	private OfferAcceptanceRateDTO getOfferAcceptanceRateDTOByOfferAcceptanceRateViewList(List<OfferAcceptanceRateView> oarList, String date) {
		OfferAcceptanceRateDTO offerAcceptanceRateDTO = new OfferAcceptanceRateDTO();
		try {
			int offerCount = 0;
			int offerAcceptedCount = 0;
			int offerRejectedCount = 0;
			for(OfferAcceptanceRateView oarView : oarList) {
				offerCount += oarView.getOFFERMADE_COUNT().intValue();
				offerAcceptedCount += oarView.getOFFERACCEPTED_COUNT().intValue();
				offerRejectedCount += oarView.getOFFERREJECTED_COUNT().intValue();
			}
			offerAcceptanceRateDTO.setOfferDate(date);
			offerAcceptanceRateDTO.setOfferCount(offerCount);
			offerAcceptanceRateDTO.setAcceptedCount(offerAcceptedCount);
			offerAcceptanceRateDTO.setRejectedCount(offerRejectedCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offerAcceptanceRateDTO;
	}
	
	private List<OfferAcceptanceRateDTO> convertOfferAcceptanceRateForVendorListToDTOList(List<OfferAcceptanceRateViewForVendor> offerAcceptanceRateViewList, ResponseList<OfferAcceptanceRateDTO> responseList, int month, int year, boolean compare) {
		List<OfferAcceptanceRateDTO> offerAcceptanceRateDTOList = new LinkedList<>();
		try {
			if(offerAcceptanceRateViewList != null && !offerAcceptanceRateViewList.isEmpty()) {
				//Grouping & Sorting OfferAcceptanceRateViewList by CreatedYearMonthDate if month > 0 otherwise by CreatedYearMonth
				Map<String, List<OfferAcceptanceRateViewForVendor>> oarMap = offerAcceptanceRateViewList.stream()
						.collect(Collectors.groupingBy(compare ? OfferAcceptanceRateViewForVendor::getCreatedYear : month > 0 ? OfferAcceptanceRateViewForVendor::getCreatedYearMonthDate : OfferAcceptanceRateViewForVendor::getCreatedYearMonth))
						.entrySet().stream().sorted(Map.Entry.comparingByKey())
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
								(oldValue, newValue) -> oldValue, LinkedHashMap::new));
				if((oarMap != null && oarMap.keySet().size() > 1) || compare) {
					for(String date : oarMap.keySet()) {
						offerAcceptanceRateDTOList.add(getOfferAcceptanceRateDTOByOfferAcceptanceRateForVendorViewList(oarMap.get(date), date));
					}
					responseList.setResponseMessage(compare ? "compare" : month > 0 ? "month" : "year");
				} else if(oarMap != null && oarMap.keySet().size() == 1) {
					Map<String, List<OfferAcceptanceRateViewForVendor>> oarMap2 = oarMap.get(oarMap.keySet().iterator().next())
							.stream().collect(Collectors.groupingBy(OfferAcceptanceRateViewForVendor::getCreatedYearMonthDate))
							.entrySet().stream().sorted(Map.Entry.comparingByKey())
							.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
									(oldValue, newValue) -> oldValue, LinkedHashMap::new));
					if (oarMap2 != null) {
						for (String date : oarMap2.keySet()) {
							offerAcceptanceRateDTOList.add(getOfferAcceptanceRateDTOByOfferAcceptanceRateForVendorViewList(oarMap2.get(date), date));
						}
						responseList.setResponseMessage("month");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offerAcceptanceRateDTOList;
	}
	
	private OfferAcceptanceRateDTO getOfferAcceptanceRateDTOByOfferAcceptanceRateForVendorViewList(List<OfferAcceptanceRateViewForVendor> oarList, String date) {
		OfferAcceptanceRateDTO offerAcceptanceRateDTO = new OfferAcceptanceRateDTO();
		try {
			int offerCount = 0;
			int offerAcceptedCount = 0;
			int offerRejectedCount = 0;
			for(OfferAcceptanceRateViewForVendor oarView : oarList) {
				offerCount += oarView.getOFFERMADE_COUNT().intValue();
				offerAcceptedCount += oarView.getOFFERACCEPTED_COUNT().intValue();
				offerRejectedCount += oarView.getOFFERREJECTED_COUNT().intValue();
			}
			offerAcceptanceRateDTO.setOfferDate(date);
			offerAcceptanceRateDTO.setOfferCount(offerCount);
			offerAcceptanceRateDTO.setAcceptedCount(offerAcceptedCount);
			offerAcceptanceRateDTO.setRejectedCount(offerRejectedCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offerAcceptanceRateDTO;
	}

	@Override
	public List<RecruiterJobsReportView> getSubmissionApplicantCountsForRecruiterJobreports(Long orgLocationId, User user,
			Date[] searchDates, Long requirementId) throws Exception {
		List<RecruiterJobsReportView> rjrvList = requirementSubmissionHistoryDao.getSubmissionApplicantCountsForRecruiterJobreports(orgLocationId,user,searchDates,requirementId);
		return rjrvList;
	}

}
