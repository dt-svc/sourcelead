package com.bis.sourcelead.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateBalanceLeaveDao;
import com.bis.sourcelead.dto.AssociateBalanceLeaveDTO;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.dto.LeaveTypeEditDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TotalLeaveDTO;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.LeaveManagementUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.ibm.icu.text.DecimalFormat;

@Service
public class AssociateBalanceLeaveServiceImpl implements AssociateBalanceLeaveService {

	private static final Logger logger = LoggerFactory.getLogger(AssociateBalanceLeaveServiceImpl.class);
	
	DecimalFormat dec = new DecimalFormat("#0.00");
	
	@Autowired
	AssociateBalanceLeaveDao associateBalanceLeaveDao;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	LeaveTypeService leaveTypeService;
	
	@Autowired
	LeaveService leaveService;
	
	@Autowired
	LeaveManagementUtil leaveManagementUtil;
	
	@Autowired
	AppliedLeaveDateService appliedLeaveDateService;
	
	@Autowired
	UserService userService;

	@Autowired
	ConversionUtil conversionUtil;

	
	@Autowired
	HolidayService holidayService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	EmployeesService employeesService;
	
	@Override
	public Response<AssociateBalanceLeaveDTO> addAssociateLeave(AssociateBalanceLeaveDTO associateBalanceLeaveDTO, User loginUser, OrgLocations orgLocations)
	{
		Response<AssociateBalanceLeaveDTO>  resObj = new Response<>();
		try {
			if(associateBalanceLeaveDTO.getTotalLeavesDto() != null && associateBalanceLeaveDTO.getTotalLeavesDto().size() > 0) {
				int count = associateBalanceLeaveDao.addAssociateLeave(associateBalanceLeaveDTO,loginUser,orgLocations);
					if (count == 1) {
						resObj.setResponse(Result.SUCCESS.name());
						resObj.setResponseMessage(Result.SUCCESS.getValue());
						resObj.setObject(associateBalanceLeaveDTO);
					}
			}else {
				resObj.setResponse("DataMissing");
				resObj.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		}catch (Exception e) {
			resObj.setResponse(Result.AWKWARD.name());
			resObj.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
			logger.error("Add Association Balance Leave Failed Due To : "+e.getMessage());
		}
		
		
		return resObj;
	}

	@Override
	public List<AssociateBalanceLeaveDTO> getAssociateBalanceLeavesByUserId(Long userId, OrgLocations orgLocations, User loginUser)
	{
		ArrayList<AssociateBalanceLeaveDTO> list = new ArrayList<>();
		try {
			User user  = userService.getUserById(userId);
			ResponseList<AssociateBalanceLeaveView> finalResObj = associateBalanceLeaveDao.getAssociateBalanceLeavesByUserId(userId, orgLocations.getId());
			if (finalResObj.getData() != null && finalResObj.getData().size() > 0) {
				for (AssociateBalanceLeaveView l : finalResObj.getData()) {
					AssociateBalanceLeaveDTO associateBalanceLeaveDTO = new AssociateBalanceLeaveDTO();
					associateBalanceLeaveDTO.setId(String.valueOf(l.getId()));
					associateBalanceLeaveDTO.setAssoBalLeaveUUID(l.getAssoBalLeaveUUID());
					associateBalanceLeaveDTO.setLeaveTypeName(l.getLeaveTypeName());
					associateBalanceLeaveDTO.setNoofLeaves(l.getTotalLeaves());
					associateBalanceLeaveDTO.setLeaveTypeId(l.getLeaveTypeId());
					float appliedLeaveCount = 0;
					LocalDate todayDate = LocalDate.now();
					List<AppliedLeaveDateView> appliedLeaves = null;
					/*if (Constants.MONTHLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						Date[] monthDates = getMonthStartDateEndDate();
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), monthDates[0], monthDates[1], userId,orgLocations.getOrg().getId());
					    appliedLeaves = getExcludeHolidayLeaveCountList(appliedLeaves,orgLocations,l,monthDates[0],monthDates[1]);
					}
					else if (Constants.QUARTERLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						LocalDate[] currentMonthQuarter = conversionUtil.getThisMonthQuarterDates(todayDate);
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), currentMonthQuarter[0].toDate(), currentMonthQuarter[1].toDate(), userId,orgLocations.getOrg().getId());
					     appliedLeaves = getExcludeHolidayLeaveCountList(appliedLeaves,orgLocations,l,currentMonthQuarter[0].toDate(),currentMonthQuarter[1].toDate());
					}
					else if (Constants.HALFYEARLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						LocalDate[] currentHalfYear = conversionUtil.getThisMonthHalfyearyDates(todayDate);
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), currentHalfYear[0].toDate(), currentHalfYear[1].toDate(), userId,orgLocations.getOrg().getId());
						appliedLeaves = getExcludeHolidayLeaveCountList(appliedLeaves,orgLocations,l,currentHalfYear[0].toDate(),currentHalfYear[1].toDate());
					}
					else if (Constants.ANNUALLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						LocalDate[] thisYearDates = conversionUtil.getThisYearStartDateEndDate();
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), thisYearDates[0].toDate(), thisYearDates[1].toDate(), userId,orgLocations.getOrg().getId());
						appliedLeaves = getExcludeHolidayLeaveCountList(appliedLeaves,orgLocations,l,thisYearDates[0].toDate(),thisYearDates[1].toDate());
					}*/
					float appliedLeavesCount =  0;
					LeaveType leaveType = leaveTypeService.getLeaveTypeById(l.getLeaveTypeId());
					if (Constants.MONTHLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						Date[] monthDates = conversionUtil.getMonthStartDateEndDate();
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), monthDates[0], monthDates[1], userId,orgLocations.getOrg().getId());
						appliedLeavesCount = (float) leaveManagementUtil.getExcludeHolidayLeaveCount(appliedLeaves, orgLocations, l,monthDates[0],monthDates[1],leaveType);
					}
					else if (Constants.QUARTERLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						LocalDate[] currentMonthQuarter = conversionUtil.getThisMonthQuarterDates(todayDate);
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), currentMonthQuarter[0].toDate(), currentMonthQuarter[1].toDate(), userId,orgLocations.getOrg().getId());
						appliedLeavesCount = (float) leaveManagementUtil.getExcludeHolidayLeaveCount(appliedLeaves, orgLocations, l,currentMonthQuarter[0].toDate(),currentMonthQuarter[1].toDate(),leaveType);
					}
					else if (Constants.HALFYEARLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						LocalDate[] currentHalfYear = conversionUtil.getThisMonthHalfyearyDates(todayDate);
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), currentHalfYear[0].toDate(), currentHalfYear[1].toDate(), userId,orgLocations.getOrg().getId());
						appliedLeavesCount = (float) leaveManagementUtil.getExcludeHolidayLeaveCount(appliedLeaves, orgLocations, l,currentHalfYear[0].toDate(),currentHalfYear[1].toDate(),leaveType);
					}
					else if (Constants.ANNUALLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						LocalDate[] thisYearDates = conversionUtil.getThisYearStartDateEndDate();
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), thisYearDates[0].toDate(), thisYearDates[1].toDate(), userId,orgLocations.getOrg().getId());
						appliedLeavesCount = (float) leaveManagementUtil.getExcludeHolidayLeaveCount(appliedLeaves, orgLocations, l,thisYearDates[0].toDate(),thisYearDates[1].toDate(),leaveType);
					}
					if (appliedLeaves != null && appliedLeaves.size() > 0) {
						appliedLeaveCount = Float.parseFloat(l.getTotalLeaves()) - appliedLeavesCount;
						associateBalanceLeaveDTO.setBalanceLeaveCounts(Float.parseFloat(dec.format(appliedLeaveCount)));
					}
					else {
						associateBalanceLeaveDTO.setBalanceLeaveCounts(Float.parseFloat(l.getTotalLeaves()));
					}
					if(appliedLeaves != null) {
						associateBalanceLeaveDTO.setAppliedLeaveCount(appliedLeavesCount);
					}else {
						associateBalanceLeaveDTO.setAppliedLeaveCount(0);
					}
					
					if (l.getCreatedByFirstName() != null && !"".equalsIgnoreCase(l.getCreatedByFirstName())) {
						associateBalanceLeaveDTO.setCreatedBy(l.getCreatedByFirstName() + " " + l.getCreatedByLastName());
					}
					else {
						associateBalanceLeaveDTO.setCreatedBy(l.getCreatedByFirstName());
					}
					UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(),orgLocations.getId());
//					LeaveType leaveType = leaveTypeService.getLeaveTypeById(l.getLeaveTypeId());
					Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocations.getId());
				    boolean flag = leaveManagementUtil.checkGenderAndDepartmentAndRoleAndEmpType(leaveType, emp, userRole);
				    if(flag){
				    	list.add(associateBalanceLeaveDTO);
				    }
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
    /**
     * Exclude Week Offs and Holidays from leaves
     * @param holidayList
     * @param appliedLeaves
     * @param orgLocations
     * @param l 
     * @param endDaet 
     * @param startDate 
     * @param leaveType 
     * @return List<AppliedLeaveDateView>
     */
	public double getExcludeHolidayLeaveCount(List<AppliedLeaveDateView> appliedLeaves, OrgLocations orgLocations, AssociateBalanceLeaveView l, Date startDate, Date endDaet, LeaveType leaveType)
	{
		 SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
		 if(l.getIncludeHolidays() != null && !Constants.YES.equalsIgnoreCase(l.getIncludeHolidays())) {
			    List<HolidayAndRestrictday> holidayList = holidayService.getHolidayByFromToDate(startDate,endDaet,orgLocations);
			    for (HolidayAndRestrictday holiday : holidayList) {
					Date holidayDate = commonUtil.orgLocationCommonDate(holiday.getDay(), orgLocations);
					boolean s = appliedLeaves.removeIf(x -> commonUtil.orgLocationCommonDate(x.getApplyLeaveDate(), orgLocations).equals(holidayDate));
				}
		 }
		
		if(l.getIncludeWeekoff() != null && !Constants.YES.equalsIgnoreCase(l.getIncludeWeekoff())) {
			if(orgLocations.getWeekOffDays() != null) {
				String[] weekOoffs = orgLocations.getWeekOffDays().split(",");
				for(String weekEndDay : weekOoffs) {
					appliedLeaves.removeIf(x -> simpleDateformat.format(x.getApplyLeaveDate()).toUpperCase().equals(weekEndDay.trim().toUpperCase()));
				}
			}	
		}
		
		
		double fromHours = 0f;
		double toHours = 0f;
		int appLeaves = 0;
		double actualLeaves = 0;
		
		for(AppliedLeaveDateView ald : appliedLeaves){
			if (Constants.STATUS_YES.equalsIgnoreCase(ald.getFromHalfDay()) && ald.getFromDate().equals(ald.getApplyLeaveDate())) {
				appLeaves += 1;
				fromHours += leaveManagementUtil.calculateActualHoursForHalfDay(ald.getFromDateFromHalfTime(), ald.getFromDateToHalfTime(), orgLocations.getAverageWorkingHours(), leaveType);
			}
			if (Constants.STATUS_YES.equalsIgnoreCase(ald.getToHalfDay()) && ald.getToDate().equals(ald.getApplyLeaveDate())) {
				appLeaves += 1;
				toHours += leaveManagementUtil.calculateActualHoursForHalfDay(ald.getToDateFromHalfTime(), ald.getToDateToHalfTime(), orgLocations.getAverageWorkingHours(), leaveType);
			}
		}
		int orgHours = 0;
		if("Half Day".equalsIgnoreCase(leaveType.getDuration())){
			orgHours = orgLocations.getAverageWorkingHours() / 2;
		}else{
			orgHours = orgLocations.getAverageWorkingHours();
		}
		float hourDays = (float) ((fromHours + toHours) / orgHours);
		actualLeaves = (appliedLeaves.size() - appLeaves) + hourDays;
		
		return actualLeaves;
		
//		return appliedLeaves;
	}
	
	public List<AppliedLeaveDateView> getExcludeHolidayLeaveCountList(List<AppliedLeaveDateView> appliedLeaves, OrgLocations orgLocations, AssociateBalanceLeaveView l, Date startDate, Date endDaet)
	{
		 SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
		 if(l.getIncludeHolidays() != null && !Constants.YES.equalsIgnoreCase(l.getIncludeHolidays())) {
			    List<HolidayAndRestrictday> holidayList = holidayService.getHolidayByFromToDate(startDate,endDaet,orgLocations);
			    for (HolidayAndRestrictday holiday : holidayList) {
					Date holidayDate = commonUtil.orgLocationCommonDate(holiday.getDay(), orgLocations);
					boolean s = appliedLeaves.removeIf(x -> commonUtil.orgLocationCommonDate(x.getApplyLeaveDate(), orgLocations).equals(holidayDate));
				}
		 }
		
		if(l.getIncludeWeekoff() != null && !Constants.YES.equalsIgnoreCase(l.getIncludeWeekoff())) {
			if(orgLocations.getWeekOffDays() != null) {
				String[] weekOoffs = orgLocations.getWeekOffDays().split(",");
				for(String weekEndDay : weekOoffs) {
					appliedLeaves.removeIf(x -> simpleDateformat.format(x.getApplyLeaveDate()).toUpperCase().equals(weekEndDay.trim().toUpperCase()));
				}
			}	
		}
		
		return appliedLeaves;
	}
	
	/**
	 * Get Month startDate and endDate
	 * 
	 * @return Date[]
	 */
	private Date[] getMonthStartDateEndDate()
	{
		Date[] dates = new Date[2];
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);
		dates[0] = c.getTime();
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		dates[1] = c.getTime();
		return dates;
	}

	@Override
	public boolean getAssociateBalanceByUUID(String assoBalLeaveUUID,String totalLeaves)
	{ 
		boolean isUpdated = false;
		int result = associateBalanceLeaveDao.updateAssociateBalLeave(assoBalLeaveUUID,totalLeaves);
		if(result > 0) {
			isUpdated = true;	
		}
		return isUpdated;
	}

	@Override
	public float getTotalLeavesCountByUserIdAndLeaveTypeId(Long leaveTypeId, Long userId,Long orgLocationId, Long orgId)
	{
		float totalLeaves = 0;
		try {
			AssociateBalanceLeave associateBalanceLeave = associateBalanceLeaveDao.getTotalLeavesByUserIdAndLeaveTypeId(leaveTypeId,userId,orgLocationId,orgId);
			if(associateBalanceLeave != null) {
				totalLeaves = Float.parseFloat(associateBalanceLeave.getTotalLeaves());
			}/*else {
				LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeId);
				totalLeaves = leaveType.getNoOfDays();
			}*/
		}catch (Exception e) {
			logger.error("Get Total Leaves By User Id And LeaveType Id "+e.getMessage());
		}
		
		return totalLeaves;
	}

	@Override
	public int addLeaveTypeToAssociate(List<User> userList, OrgLocations orgLocation,LeaveType leaveType,User loginUser,float noOfLeaves)
	{
		int isAdded = 0;
		if(userList != null && userList.size() >0) {
			AssociateBalanceLeaveDTO associateBalanceLeaveDTO = new AssociateBalanceLeaveDTO();
			List<TotalLeaveDTO> list = new ArrayList<>();
			for(User user : userList) {
				boolean isApplicableToUser = getOrgLevelLeaves(orgLocation,user.getId(),leaveType);
				if(isApplicableToUser) {
					TotalLeaveDTO totalLeaveDTO = new TotalLeaveDTO();
					totalLeaveDTO.setUserId(String.valueOf(user.getId()));
					totalLeaveDTO.setLeaveTypeId(String.valueOf(leaveType.getId()));
					if(leaveType.getLeaveTypeRolesSet() != null && !leaveType.getLeaveTypeRolesSet().isEmpty() && leaveType.getLeaveTypeRolesSet().size() > 0) {
						totalLeaveDTO.setLeavetypeRoles(leaveType.getLeaveTypeRolesSet().stream().map(l-> String.valueOf(l.getRoleId().getId())).collect(Collectors.joining(",")));
					} 
					if(leaveType.getDepartmentIds() != null) {
						totalLeaveDTO.setLeavetypeDepartment(leaveType.getDepartmentIds());
					}else {
						totalLeaveDTO.setLeavetypeDepartment(null);
					}
					if(!leaveType.getLeaveEmpType().isEmpty()) {
						totalLeaveDTO.setLeavetypeEmpType(leaveType.getLeaveEmpType());
					}else {
						totalLeaveDTO.setLeavetypeEmpType(null);
					}
					
					if(noOfLeaves != 0) {
						totalLeaveDTO.setNoOfLeaves(String.valueOf(noOfLeaves));
					}else {
						if(!Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod())  && !Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
							if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
								totalLeaveDTO.setNoOfLeaves(String.valueOf(leaveType.getNoOfDaysPerPeriod()));
							}else {
								Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocation.getId());
								 try {
									float days = leaveManagementUtil.getNumberofDaysPerPeriodForLeaveType(leaveType, emp, user);
									totalLeaveDTO.setNoOfLeaves(String.valueOf(days));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
						}else {
							if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
								totalLeaveDTO.setNoOfLeaves(String.valueOf(leaveType.getNoOfDays()));
							}else {
								Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocation.getId());
								 try {
									float days = leaveManagementUtil.getNumberofDaysPerPeriodForLeaveType(leaveType, emp, user);
									totalLeaveDTO.setNoOfLeaves(String.valueOf(days));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
						}
					}
					

//					if(user.getGender() != null) {
//						if("All".equalsIgnoreCase(leaveType.getEffectiveGender()) || user.getGender().equalsIgnoreCase(leaveType.getEffectiveGender())){
							list.add(totalLeaveDTO);
//						}	
//					}

				}
			}
			associateBalanceLeaveDTO.setTotalLeavesDto(list);
			isAdded = associateBalanceLeaveDao.addAssociateLeave(associateBalanceLeaveDTO, loginUser, orgLocation);
		}
		return isAdded;
	}

	
	private boolean getOrgLevelLeaves(OrgLocations orgLocation, Long userId, LeaveType leaveType)
	{
		boolean isLeaveApplicable = false;
		try {
			UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocation.getId());
//			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
			Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocation.getId());
			isLeaveApplicable = leaveManagementUtil.checkGenderAndDepartmentAndRoleAndEmpType(leaveType, emp, userRole);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isLeaveApplicable;
	}
	
	
	@Override
	public int updateAssociateBalanceLeaveType(List<User> userList, Long orgLocationId, LeaveType leaveType,int status)
	{
		return associateBalanceLeaveDao.updateAssociateBalanceLeaveType(userList,orgLocationId,leaveType,status,0,Constants.NONE,Constants.DELETE);
		
	}

	@Override
	public int addOrSubAssociateBalanceLeaveType(List<User> userList, Long orgLocationId, LeaveType leaveType, int status, float diffLeaves, String actionType,String methodType)
	{
		return associateBalanceLeaveDao.updateAssociateBalanceLeaveType(userList,orgLocationId,leaveType,status,diffLeaves,actionType,methodType);
	}

	@Override
	public AssociateBalanceLeave getTotalLeavesByUserIdAndLeaveTypeId(Long leaveTypeId, Long userId, Long orgLocationId,Long orgId)
	{
		return associateBalanceLeaveDao.getTotalLeavesByUserIdAndLeaveTypeId(leaveTypeId, userId, orgLocationId,orgId);
	}

	@Override
	public AssociateBalanceLeave saveOrUpdateAssocicateBalanceLeaves(AssociateBalanceLeave assBalLeave)
	{
		return associateBalanceLeaveDao.saveOrUpdateAssocicateBalanceLeaves(assBalLeave);		
	}

	@Override
	public int updateAssociateAccuralBalanceLeaveCount(List<AssociateBalanceLeaveDTO> assBalList)
	{
		return associateBalanceLeaveDao.updateAssociateAccuralBalanceLeaveCount(assBalList);		
	}

	@Override
	public int calculateAndUpdateAssociateBalanceLeaves(OrgLocations orgLocations, LeaveType leaveType, Date startDate, Date endDate) throws Exception
	{
		int stat = 0;
		int val = 0;
		List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		List<AppliedLeaveDateView> appliedLeavesView = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(orgLocations.getId(),leaveType.getId(),startDate,endDate);
		  try{
//				   Map<Integer,Integer> map = appliedLeaves.stream().collect(Collectors.groupingBy(apl->AppliedLeaveDateView::getUserId,counting(),Map::size));
			 Map<Long, Long> map = appliedLeavesView.stream().collect(Collectors.groupingBy(AppliedLeaveDateView::getUserId,Collectors.counting()));
			 List<AssociateBalanceLeaveDTO> assBalList = new ArrayList<>();
			 Calendar cal = Calendar.getInstance();
			 String currentMonthName = new SimpleDateFormat("MMMM").format(cal.getTime());
			 for(User user : userList){
				 if(map.keySet().contains(user.getId())){
					 
					 float associateBalanceLeaves = 0;
					 if(Constants.YES.equalsIgnoreCase(leaveType.getVisibleInBalanceLeaves())) {
						 associateBalanceLeaves = getTotalLeavesCountByUserIdAndLeaveTypeId(leaveType.getId(), user.getId(), orgLocations.getId(),orgLocations.getOrg().getId());
					 }
					float totalBal = 0f;
					float totalBalForHis = 0f;
					boolean flag = true;
					AssociateBalanceLeaveView ablv = new AssociateBalanceLeaveView();
					ablv.setIncludeWeekoff(leaveType.getIncludeWeekoff());
					ablv.setIncludeHolidays(leaveType.getIncludeHolidays());
					float appliedLeaveCount = 0;
					LocalDate todayDate = LocalDate.now();
					List<AppliedLeaveDateView> appliedLeaves = null;
					float appliedNoofLeaves = 0;
					if (Constants.MONTHLY.equalsIgnoreCase(leaveType.getPeriod()) && Constants.YES.equalsIgnoreCase(leaveType.getVisibleInBalanceLeaves())) {
						Date[] monthDates = getMonthStartDateEndDate();
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), leaveType.getId(), startDate, endDate, user.getId(),orgLocations.getOrg().getId());
						appliedNoofLeaves = (float) getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,ablv,startDate,endDate,leaveType);
						if("January".equalsIgnoreCase(currentMonthName)) {
							totalBal = calculateMonthlyLeaves(appliedNoofLeaves,leaveType,associateBalanceLeaves,orgLocations,user);
							flag = false;
						}
					}
					else if (Constants.QUARTERLY.equalsIgnoreCase(leaveType.getPeriod()) && Constants.YES.equalsIgnoreCase(leaveType.getVisibleInBalanceLeaves())) {
						LocalDate[] currentMonthQuarter = conversionUtil.getThisMonthQuarterDates(todayDate.minusDays(1));
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), leaveType.getId(), currentMonthQuarter[0].toDate(), currentMonthQuarter[1].toDate(), user.getId(),orgLocations.getOrg().getId());
						appliedNoofLeaves = (float) getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,ablv,currentMonthQuarter[0].toDate(),currentMonthQuarter[1].toDate(),leaveType);
					}
					else if (Constants.HALFYEARLY.equalsIgnoreCase(leaveType.getPeriod()) && Constants.YES.equalsIgnoreCase(leaveType.getVisibleInBalanceLeaves())) {
						LocalDate[] currentHalfYear = conversionUtil.getThisMonthHalfyearyDates(todayDate.minusDays(1));
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), leaveType.getId(), currentHalfYear[0].toDate(), currentHalfYear[1].toDate(), user.getId(),orgLocations.getOrg().getId());
						appliedNoofLeaves = (float) getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,ablv,currentHalfYear[0].toDate(),currentHalfYear[1].toDate(),leaveType);
					}
					else if (Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod()) && Constants.YES.equalsIgnoreCase(leaveType.getVisibleInBalanceLeaves())) {
						Date[] thisYearDates = ConversionUtil.getPreviousYearStartAndEndDate();
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), leaveType.getId(), thisYearDates[0], thisYearDates[1], user.getId(),orgLocations.getOrg().getId());
						appliedNoofLeaves = (float) getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,ablv,thisYearDates[0],thisYearDates[1],leaveType);
						totalBal = calculateAnnualLeaves(appliedNoofLeaves,leaveType,associateBalanceLeaves,orgLocations,user);
					}
					
					if (!Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod()) && flag) {
						if(Constants.YES.equalsIgnoreCase(leaveType.getAccuralPolicy())){
							if(Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
								totalBal = 0;
							}else if(Constants.YES.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
								if (appliedNoofLeaves > 0) {
									totalBal = associateBalanceLeaves - appliedNoofLeaves;
									if(Constants.YES.equalsIgnoreCase(leaveType.getIncludeInPayroll())){
										if(totalBal < 0){
											totalBal = 0;
										}
									}else if(Constants.NO.equalsIgnoreCase(leaveType.getIncludeInPayroll())){
										totalBal = associateBalanceLeaves - appliedNoofLeaves;
										totalBalForHis = totalBal;
									} 
								}
							}
							
							if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
								totalBal += leaveType.getNoOfDaysPerPeriod();
							}else {
								Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocations.getId());
								String expType = leaveManagementUtil.getExperienceType(leaveType, emp, user);
								if("EXP".equalsIgnoreCase(expType)){
									totalBal += leaveType.getExperiencedPerPeriod();
								}else if("FRESHER".equalsIgnoreCase(expType)){
									totalBal += leaveType.getNewJoinPerPeriod();
								}
							}
							
						}else if(Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())){
							if(Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
								
								if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
									totalBal = associateBalanceLeaves - leaveType.getNoOfDaysPerPeriod();
								}else {
									Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocations.getId());
									String expType = leaveManagementUtil.getExperienceType(leaveType, emp, user);
									if("EXP".equalsIgnoreCase(expType)){
										totalBal = associateBalanceLeaves - leaveType.getExperiencedPerPeriod();
									}else if("FRESHER".equalsIgnoreCase(expType)){
										totalBal = associateBalanceLeaves - leaveType.getNewJoinPerPeriod();
									}
								}
								
//								totalBal = associateBalanceLeaves - leaveType.getNoOfDaysPerPeriod();
							}else if(Constants.YES.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
								float remainingBal = 0f;
								if (appliedNoofLeaves > 0) {
									remainingBal = leaveType.getNoOfDaysPerPeriod() - appliedNoofLeaves;
									if(Constants.YES.equalsIgnoreCase(leaveType.getIncludeInPayroll())){
										if(remainingBal < 0){
											
											if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
												totalBal = associateBalanceLeaves - leaveType.getNoOfDaysPerPeriod();
											}else {
												Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocations.getId());
												String expType = leaveManagementUtil.getExperienceType(leaveType, emp, user);
												if("EXP".equalsIgnoreCase(expType)){
													totalBal = associateBalanceLeaves - leaveType.getExperiencedPerPeriod();
												}else if("FRESHER".equalsIgnoreCase(expType)){
													totalBal = associateBalanceLeaves - leaveType.getNewJoinPerPeriod();
												}
											}
											
//											totalBal = associateBalanceLeaves - leaveType.getNoOfDaysPerPeriod();
										}else{
											totalBal = associateBalanceLeaves - appliedNoofLeaves;
										}
									}else if(Constants.NO.equalsIgnoreCase(leaveType.getIncludeInPayroll())){
										totalBal = associateBalanceLeaves - appliedNoofLeaves;
									}
								}else{
									totalBal = associateBalanceLeaves;
								}
							}
						}
					}
					
//					totalBal =  associateBalanceLeaves - map.get(user.getId()) ;
				    
				    AssociateBalanceLeaveDTO assBal = new AssociateBalanceLeaveDTO();
				    assBal.setUserId(String.valueOf(user.getId()));
				    assBal.setLeaveTypeId(leaveType.getId());
				    assBal.setNoofLeaves(String.valueOf(totalBal));
				    assBal.setOrgLocationId(orgLocations.getId());
				    assBal.setAssBalLeaveHis(totalBalForHis);
				    assBalList.add(assBal);
				 }else{
					 float associateBalanceLeaves = getTotalLeavesCountByUserIdAndLeaveTypeId(leaveType.getId(), user.getId(), orgLocations.getId(),orgLocations.getOrg().getId());
//					 float totalBal = associateBalanceLeaves + leaveType.getNoOfDaysPerPeriod();
					 float totalBal = 0;
					 float periodDays = 0;
					 if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
						 if(Constants.YES.equalsIgnoreCase(leaveType.getAccuralPolicy())){
						 	periodDays += leaveType.getNoOfDaysPerPeriod();
						 }else {
							periodDays += leaveType.getNoOfDays();
						 }
//						 periodDays += leaveType.getNoOfDaysPerPeriod();
						}else {
							Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocations.getId());
							String expType = leaveManagementUtil.getExperienceType(leaveType, emp, user);
							if("EXP".equalsIgnoreCase(expType)){
								periodDays += leaveType.getExperiencedPerPeriod();
							}else if("FRESHER".equalsIgnoreCase(expType)){
								periodDays += leaveType.getNewJoinPerPeriod();
							}
						}
					if (Constants.YES.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
						if (Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())) {
							totalBal += periodDays;
						}
						else if (Constants.YES.equalsIgnoreCase(leaveType.getLeavesCarryforward())) {
							if("January".equalsIgnoreCase(currentMonthName)) {
								if(leaveType.getMaxDaysToCarryforward() != null && leaveType.getMaxDaysToCarryforward() >0) {
									if(associateBalanceLeaves > leaveType.getMaxDaysToCarryforward()) {
										totalBal = leaveType.getMaxDaysToCarryforward() + periodDays;
									}else {
										totalBal = associateBalanceLeaves + periodDays;
									}
								}else {
									totalBal = associateBalanceLeaves + periodDays;
								}
							}else {
								totalBal = associateBalanceLeaves + periodDays;
							}
						}
					}
					else if (Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
						if (Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())) {
							if("January".equalsIgnoreCase(currentMonthName)) {
								totalBal = periodDays;
							}else {
								totalBal = associateBalanceLeaves - periodDays;
							}
						}
						else if (Constants.YES.equalsIgnoreCase(leaveType.getLeavesCarryforward())) {
							if("January".equalsIgnoreCase(currentMonthName)) {
								if(leaveType.getMaxDaysToCarryforward() != null && leaveType.getMaxDaysToCarryforward() >0) {
									if(associateBalanceLeaves > leaveType.getMaxDaysToCarryforward()) {
										totalBal = leaveType.getMaxDaysToCarryforward() + periodDays;
									}else {
										totalBal = associateBalanceLeaves + periodDays;
									}
								}else {
									totalBal = associateBalanceLeaves + periodDays;
								}
								
							}else {
								totalBal = associateBalanceLeaves;
							}
						}
					}
					 AssociateBalanceLeaveDTO assBal = new AssociateBalanceLeaveDTO();
				     assBal.setUserId(String.valueOf(user.getId()));
				     assBal.setLeaveTypeId(leaveType.getId());
				     assBal.setNoofLeaves(String.valueOf(totalBal));
				     assBal.setOrgLocationId(orgLocations.getId());
				     assBal.setAssBalLeaveHis(associateBalanceLeaves);
				     assBalList.add(assBal);
				 }
			 }
			 stat = updateAssociateAccuralBalanceLeaveCount(assBalList);
			 val = associateBalanceLeaveDao.saveAssociateBalLeaveHistory(assBalList,startDate,endDate);
			 
			 
		   }catch (Exception e) {
			   e.printStackTrace();
		   }
		return stat;
	}

	private float calculateMonthlyLeaves(float appliedNoofLeaves, LeaveType leaveType, float associateBalanceLeaves, OrgLocations orgLocations, User user)
	{
		float totalBal = 0f, noOfCFDays = 0f, totalBalanceLeaves = 0f;
		float perMnthDays = 0f;
		try {
			if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
				if(Constants.YES.equalsIgnoreCase(leaveType.getAccuralPolicy())){
					perMnthDays += leaveType.getNoOfDaysPerPeriod();
				}else {
					perMnthDays += leaveType.getNoOfDays();
				}
			}else {
				Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocations.getId());
				String expType = leaveManagementUtil.getExperienceType(leaveType, emp, user);
				if("EXP".equalsIgnoreCase(expType)){
					perMnthDays = leaveType.getExperiencedPerPeriod();
				}else if("FRESHER".equalsIgnoreCase(expType)){
					perMnthDays = leaveType.getNewJoinPerPeriod();
				}
			}
//			if(Constants.YES.equalsIgnoreCase(leaveType.getAccuralPolicy())){
			if(leaveType.getMaxDaysToCarryforward() != null && leaveType.getMaxDaysToCarryforward() > 0) {
				if(Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
					if(associateBalanceLeaves > leaveType.getMaxDaysToCarryforward()) {
						noOfCFDays = leaveType.getMaxDaysToCarryforward();
					}else {
						noOfCFDays = associateBalanceLeaves;
					}
					totalBalanceLeaves = perMnthDays + noOfCFDays;
				}else {
					totalBal = associateBalanceLeaves - appliedNoofLeaves;
					if(totalBal > leaveType.getMaxDaysToCarryforward()) {
						noOfCFDays = leaveType.getMaxDaysToCarryforward();
					}else {
						noOfCFDays = totalBal;
					}
					totalBalanceLeaves = perMnthDays + noOfCFDays;
				}
			}else {
				if(Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
					noOfCFDays = associateBalanceLeaves;
					totalBalanceLeaves = perMnthDays + noOfCFDays;
				}else {
					totalBal = associateBalanceLeaves - appliedNoofLeaves;
					noOfCFDays = totalBal;
					totalBalanceLeaves = perMnthDays + noOfCFDays;
				}
			}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalBalanceLeaves;
	}

	private float calculateAnnualLeaves(float appliedNoofLeaves, LeaveType leaveType, float associateBalanceLeaves, OrgLocations orgLocations, User user) throws Exception
	{
		float totalBal = 0f, actualTotalBal = 0f;
		/*if(Constants.YES.equalsIgnoreCase(leaveType.getAccuralPolicy())){
			if(Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
				totalBal = 0;
			}else if(Constants.YES.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
				totalBal = associateBalanceLeaves - appliedNoofLeaves;
				actualTotalBal = 
			}
			
			if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
				totalBal += leaveType.getNoOfDaysPerPeriod();
			}else {
				Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocations.getId());
				String expType = leaveManagementUtil.getExperienceType(leaveType, emp, user);
				if("EXP".equalsIgnoreCase(expType)){
					totalBal += leaveType.getExperiencedPerPeriod();
				}else if("FRESHER".equalsIgnoreCase(expType)){
					totalBal += leaveType.getNewJoinPerPeriod();
				}
			}
			
		}else if(Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())){*/
			if(Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
				if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
					totalBal = leaveType.getNoOfDaysPerPeriod();
				}else {
					Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocations.getId());
					String expType = leaveManagementUtil.getExperienceType(leaveType, emp, user);
					if("EXP".equalsIgnoreCase(expType)){
						totalBal = leaveType.getExperiencedPerPeriod();
					}else if("FRESHER".equalsIgnoreCase(expType)){
						totalBal = leaveType.getNewJoinPerPeriod();
					}
				}
				
//				totalBal = associateBalanceLeaves - leaveType.getNoOfDaysPerPeriod();
			}else if(Constants.YES.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
				float remainingBal = 0f, actualBal = 0f, noOfDaysToCarryForward = 0f;
				if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
					actualBal = leaveType.getNoOfDaysPerPeriod();
				}else {
					Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocations.getId());
					String expType = leaveManagementUtil.getExperienceType(leaveType, emp, user);
					if("EXP".equalsIgnoreCase(expType)){
						actualBal = leaveType.getExperiencedPerPeriod();
					}else if("FRESHER".equalsIgnoreCase(expType)){
						actualBal = leaveType.getNewJoinPerPeriod();
					}
				}
				
				if (appliedNoofLeaves > 0) {
					remainingBal = associateBalanceLeaves - appliedNoofLeaves;
					if(leaveType.getMaxDaysToCarryforward() != null && leaveType.getMaxDaysToCarryforward() > 0){
						if(remainingBal > leaveType.getMaxDaysToCarryforward()) {
							noOfDaysToCarryForward = leaveType.getMaxDaysToCarryforward();
						}else {
							noOfDaysToCarryForward = remainingBal;
						}
					}else {
						noOfDaysToCarryForward = remainingBal;
					}
				}else {
					if(leaveType.getMaxDaysToCarryforward() != null && leaveType.getMaxDaysToCarryforward() > 0){
						if(associateBalanceLeaves > leaveType.getMaxDaysToCarryforward()) {
							noOfDaysToCarryForward = leaveType.getMaxDaysToCarryforward();
						}else {
							noOfDaysToCarryForward = associateBalanceLeaves;
						}
					}else {
						noOfDaysToCarryForward = associateBalanceLeaves;
					}
				}
				totalBal = actualBal + noOfDaysToCarryForward;
			}
//		}
		return totalBal;
	}

	@Override
	public int updateAssociateBalanceLeaves(User updatedUser,OrgLocations orgLocations,Employees employees, UserRole userRole)
	{
		int updated = 0;
		try {
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocations.getId()));
			List<LeaveType> roleLeaveType = new ArrayList<>();
			if (leaveTypeList != null && !leaveTypeList.isEmpty()) {
			   // Filter by Role,Department, Employee Type and Gender
				for (LeaveType leaveType : leaveTypeList) {
						boolean flag = leaveManagementUtil.checkGenderAndDepartmentAndRoleAndEmpType(leaveType,employees,userRole);
						if(flag){
							roleLeaveType.add(leaveType);
						}
				}
				List<AssociateBalanceLeave> associateBalLeave = associateBalanceLeaveDao.getAssoBalLeavesByUserId(updatedUser.getId(), orgLocations.getId());
				// remove Associate Balance if  Role,Department, Employee Type and Gender not matched 
				for(AssociateBalanceLeave balLeave : associateBalLeave) {
					 LeaveType leaveType = leaveTypeService.getLeaveTypeById(balLeave.getLeaveTypeId());
					 boolean flag = leaveManagementUtil.checkGenderAndDepartmentAndRoleAndEmpType(leaveType,employees,userRole);
					 if(!flag) {
						 balLeave.setStatus(1);
						 associateBalanceLeaveDao.saveOrUpdateAssocicateBalanceLeaves(balLeave);
					 }
				 }
				 // Adding Associate Balance if  Role,Department, Employee Type and Gender is matched 
				 for(LeaveType type : roleLeaveType) {
					 if(!containsName(associateBalLeave,type)) {
						 AssociateBalanceLeave associateBalanceLeave = new AssociateBalanceLeave();
						 associateBalanceLeave.setLeaveTypeId(type.getId());
						 associateBalanceLeave.setUser(updatedUser);
						 associateBalanceLeave.setAssoBalLeaveUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
						 associateBalanceLeave.setStatus(1);
						 associateBalanceLeave.setCreatedDate(new Date());
						 associateBalanceLeave.setUpdatedDate(new Date());
						 associateBalanceLeave.setTotalLeaves(String.valueOf(type.getNoOfDays()));
						AssociateBalanceLeave upDatedass  =  associateBalanceLeaveDao.saveOrUpdateAssocicateBalanceLeaves(associateBalanceLeave);
					    if(upDatedass != null) {
					    	updated = 1;
					    }
					 }
				 }
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return updated;
	}

	private boolean containsName(List<AssociateBalanceLeave> associateBalLeave, LeaveType type)
	{
		 return associateBalLeave.stream().filter(o -> o.getId().longValue()== type.getId().longValue()).findFirst().isPresent();
	}

	@Override
	public int updateAssociateDateOfJoinBalanceLeaveType(Long orgLocationId, User user,int status, String methodType) {
		return associateBalanceLeaveDao.updateAssociateDateOfJoinBalanceLeaveType(orgLocationId, user, status,  methodType);
	}

	@Override
	public int addAssociateLeaveType(User user, Long orgLocationId,Employees employee, UserRole userRole) {
		return associateBalanceLeaveDao.addAssociateLeaveType(user, orgLocationId,employee,userRole);
	}

	@Override
	public int updateLeaveTypeAssociateBalances(List<User> updatedUserList, LeaveType leaveType, LeaveTypeDTO leaveTypeDTO)
	{
		float previousNoOfDays = 0;
		float currentNofDays = 0;
		float previousFresherNoOfDays = 0;
		float currentFresherNofDays = 0;
		float previousExpNoOfDays = 0;
		float currentExpNofDays = 0;
		int status = 0;
		if(!Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod()) && !Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
			if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
				currentNofDays = leaveType.getNoOfDaysPerPeriod();
				previousNoOfDays = leaveTypeDTO.getPreviousNoOfDaysPerPeriod();
			}else{
				 previousFresherNoOfDays = leaveTypeDTO.getPreviousFresherPeriod();
				 currentFresherNofDays = leaveType.getNewJoinPerPeriod();
				
				 previousExpNoOfDays = leaveTypeDTO.getPreviousExpPeriodDays();
				 currentExpNofDays = leaveType.getExperiencedPerPeriod();
			}
		}else {
			if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
				currentNofDays   = leaveType.getNoOfDays();
				previousNoOfDays = Float.parseFloat(leaveTypeDTO.getPreviousNoOfdays());
			}else{
				 previousFresherNoOfDays = leaveTypeDTO.getPreviousNewJoinee();
				 currentFresherNofDays = leaveType.getNewJoinee();
				
				 previousExpNoOfDays = leaveTypeDTO.getPreviousExpApplicableDays();
				 currentExpNofDays = leaveType.getExperiencedApplicableDays();
			}
		}
			float diffLeaves = 0;
			float diffFresherLeaves = 0;
			float diffExpLeaves = 0;
			String actionType = "";
			String actionFresherType = "";
			String actionExpType = "";
			// All
			if(previousNoOfDays < currentNofDays) {
				diffLeaves = currentNofDays - previousNoOfDays;
				actionType = "ADD";
			}if(previousNoOfDays > currentNofDays) {
				 diffLeaves = previousNoOfDays - currentNofDays;
				 actionType = "SUB";
			}if(previousNoOfDays == currentNofDays) {
				 actionType = "NONE";
			}
			//Fresher calculation
			if(previousFresherNoOfDays < currentFresherNofDays) {
				diffFresherLeaves = currentFresherNofDays - previousFresherNoOfDays;
				actionFresherType = "ADD";
			}if(previousFresherNoOfDays > currentFresherNofDays) {
				diffFresherLeaves = currentFresherNofDays - previousFresherNoOfDays;
				 actionFresherType = "SUB";
			}if(previousFresherNoOfDays == currentFresherNofDays) {
				actionFresherType = "NONE";
			}
			// Experience Calculation 
			if(previousExpNoOfDays < currentExpNofDays) {
				diffExpLeaves = currentExpNofDays - previousExpNoOfDays;
				actionExpType = "ADD";
			}if(previousExpNoOfDays > currentExpNofDays) {
				diffExpLeaves = currentExpNofDays - previousExpNoOfDays;
				 actionExpType = "SUB";
			}if(previousExpNoOfDays == currentExpNofDays) {
				actionExpType = "NONE";
			}
			
			
			List<LeaveTypeEditDTO> list = new ArrayList<>();
			for(User user : updatedUserList){
				LeaveTypeEditDTO dto = new LeaveTypeEditDTO();
				dto.setStatus(Integer.parseInt(leaveType.getStatus()));
				AssociateBalanceLeave associateBalanceLeave = getTotalLeavesByUserIdAndLeaveTypeId(leaveType.getId(), user.getId(), leaveType.getOrgLocations().getId(),leaveType.getOrgLocations().getOrg().getId());
				float assoBalLeaves = Float.parseFloat(associateBalanceLeave.getTotalLeaves());
				float allowedLeaves = 0;
				String userType = "";
				Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), leaveType.getOrgLocations().getId());
				try {
					if(!"All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
						if(emp != null){
							userType = leaveManagementUtil.getExperienceType(leaveType, emp, user);
						}else{
							userType = "EXP";
						}
						if("EXP".equalsIgnoreCase(userType)){
							if("ADD".equalsIgnoreCase(actionExpType)){
								allowedLeaves = assoBalLeaves + diffExpLeaves;
							}else if("SUB".equalsIgnoreCase(actionExpType)){
								allowedLeaves = assoBalLeaves - diffExpLeaves;
							}else{
								allowedLeaves = assoBalLeaves;
							}
						}else if("FRESHER".equalsIgnoreCase(userType)){
							if("ADD".equalsIgnoreCase(actionFresherType)){
								allowedLeaves = assoBalLeaves + diffFresherLeaves;
							}else if("SUB".equalsIgnoreCase(actionFresherType)){
								allowedLeaves = assoBalLeaves - diffFresherLeaves;
							}else{
								allowedLeaves = assoBalLeaves;
							}
//							allowedLeaves = assoBalLeaves + diffFresherLeaves;
						}
					}else{
						if("ADD".equalsIgnoreCase(actionType)){
							allowedLeaves = assoBalLeaves + diffLeaves;
						}else if("SUB".equalsIgnoreCase(actionType)){
							allowedLeaves = assoBalLeaves - diffLeaves;
						}else{
							allowedLeaves = assoBalLeaves;
						}
//						allowedLeaves = assoBalLeaves + diffLeaves;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				 
				dto.setTotalLeaves(allowedLeaves);
				dto.setLocationId(leaveType.getOrgLocations().getId());
				dto.setUserId(user.getId());
				dto.setLeaveTypeId(leaveType.getId());
				if(leaveType.getLeaveTypeRolesSet() != null && !leaveType.getLeaveTypeRolesSet().isEmpty() && leaveType.getLeaveTypeRolesSet().size() > 0) {
					String s = leaveType.getLeaveTypeRolesSet().stream().map(r->r.getRoleId().getId().toString()).collect(Collectors.joining(","));
//					stmt.setString(6, s);
					dto.setLeavetypeRoles(s);
				}else {
					dto.setLeavetypeRoles(null);
				}
				
				if(leaveType.getDepartmentIds() != null) {
//					stmt.setString(7, leaveType.getDepartmentIds());
					dto.setLeavetypeDepartment(leaveType.getDepartmentIds());
				}else {
					dto.setLeavetypeDepartment(null);
				}
				if(!leaveType.getLeaveEmpType().isEmpty()) {
//					stmt.setString(8, leaveType.getLeaveEmpType());
					dto.setLeavetypeEmpType(leaveType.getLeaveEmpType());
				}else {
					dto.setLeavetypeEmpType(null);
				}
				list.add(dto);
			}
			status = associateBalanceLeaveDao.updateLeaveTypeAssociateBalances(list);
		return status;
	}
	
}
