package com.bis.sourcelead.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateBalanceLeaveDao;
import com.bis.sourcelead.dto.AssociateBalanceLeaveDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TotalLeaveDTO;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.LeaveRole;
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
					if (Constants.MONTHLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						Date[] monthDates = getMonthStartDateEndDate();
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), monthDates[0], monthDates[1], userId);
					    appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,l,monthDates[0],monthDates[1]);
					}
					else if (Constants.QUARTERLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						LocalDate[] currentMonthQuarter = conversionUtil.getThisMonthQuarterDates(todayDate);
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), currentMonthQuarter[0].toDate(), currentMonthQuarter[1].toDate(), userId);
					     appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,l,currentMonthQuarter[0].toDate(),currentMonthQuarter[1].toDate());
					}
					else if (Constants.HALFYEARLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						LocalDate[] currentHalfYear = conversionUtil.getThisMonthHalfyearyDates(todayDate);
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), currentHalfYear[0].toDate(), currentHalfYear[1].toDate(), userId);
						appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,l,currentHalfYear[0].toDate(),currentHalfYear[1].toDate());
					}
					else if (Constants.ANNUALLY.equalsIgnoreCase(l.getPeriod()) && Constants.YES.equalsIgnoreCase(l.getVisibleInBalanceLeaves())) {
						LocalDate[] thisYearDates = conversionUtil.getThisYearStartDateEndDate();
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), l.getLeaveTypeId(), thisYearDates[0].toDate(), thisYearDates[1].toDate(), userId);
						appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,l,thisYearDates[0].toDate(),thisYearDates[1].toDate());
					}
					if (appliedLeaves != null && appliedLeaves.size() > 0) {
						appliedLeaveCount = Float.parseFloat(l.getTotalLeaves()) - appliedLeaves.size();
						associateBalanceLeaveDTO.setBalanceLeaveCounts(Float.parseFloat(dec.format(appliedLeaveCount)));
					}
					else {
						associateBalanceLeaveDTO.setBalanceLeaveCounts(Float.parseFloat(l.getTotalLeaves()));
					}
					if(appliedLeaves != null) {
						associateBalanceLeaveDTO.setAppliedLeaveCount(appliedLeaves.size());
					}else {
						associateBalanceLeaveDTO.setAppliedLeaveCount(0);
					}
					
					if (l.getCreatedByFirstName() != null && !"".equalsIgnoreCase(l.getCreatedByFirstName())) {
						associateBalanceLeaveDTO.setCreatedBy(l.getCreatedByFirstName() + " " + l.getCreatedByLastName());
					}
					else {
						associateBalanceLeaveDTO.setCreatedBy(l.getCreatedByFirstName());
					}
					if("All".equalsIgnoreCase(l.getEffectiveGender()) || user.getGender().equalsIgnoreCase(l.getEffectiveGender())){
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
     * @return List<AppliedLeaveDateView>
     */
	public List<AppliedLeaveDateView> getExcludeHolidayLeaveCount(List<AppliedLeaveDateView> appliedLeaves, OrgLocations orgLocations, AssociateBalanceLeaveView l, Date startDate, Date endDaet)
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
	public float getTotalLeavesByUserIdAndLeaveTypeId(Long leaveTypeId, Long userId,Long orgLocationId)
	{
		float totalLeaves = 0;
		try {
			AssociateBalanceLeave associateBalanceLeave = associateBalanceLeaveDao.getTotalLeavesByUserIdAndLeaveTypeId(leaveTypeId,userId,orgLocationId,1);
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
	public int addLeaveTypeToAssociate(List<User> userList, OrgLocations orgLocation,LeaveType leaveType,User loginUser)
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
					if(!Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod())  && !Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
						if("All Employees".equalsIgnoreCase(leaveType.getApplicableToUsers())) {
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
						if("All Employees".equalsIgnoreCase(leaveType.getApplicableToUsers())) {
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
//		List<LeaveType> roleLeaveType = new ArrayList<>();
		try {
			UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocation.getId());
			List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocation.getId()));
//			if (leaveTypeList != null && !leaveTypeList.isEmpty()) {
//				for (LeaveType leaveType : leaveTypeList) {
			Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocation.getId());
				
			isLeaveApplicable = leaveManagementUtil.checkGenderAndDepartmentAndRoleAndEmpType(leaveType, emp, userRole);
			
			/*Set<Long> roleIds = new HashSet<>();
					if(leaveType.getLeaveTypeRolesSet() != null) {
						for (LeaveRole leaveRole : leaveType.getLeaveTypeRolesSet()) {
							roleIds.add(leaveRole.getRoleId().getId());
						}
						if (roleIds.contains(userRole.getRoles().getId()) || "Y".equalsIgnoreCase(leaveType.getApplicableToUsers())) {
							if(leaveType.getDepartmentId() != null && emp.getDepartmentId() != null){
								if(leaveType.getDepartmentId().getId().longValue() == emp.getDepartmentId().getId().longValue()){
									isLeaveApplicable = true;
								}
							}else{
								isLeaveApplicable = true;
							}
							//						roleLeaveType.add(leaveType);
						}	
					}else {
						if("Y".equalsIgnoreCase(leaveType.getApplicableToUsers())) {
							isLeaveApplicable = true;
						}
					}*/
				
//				}
//			}
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
	public AssociateBalanceLeave getTotalLeavesByUserIdAndLeaveTypeId(Long leaveTypeId, Long userId, Long orgLocationId, Integer status)
	{
		return associateBalanceLeaveDao.getTotalLeavesByUserIdAndLeaveTypeId(leaveTypeId, userId, orgLocationId, status);
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
		List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		List<AppliedLeaveDateView> appliedLeavesView = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(orgLocations.getId(),leaveType.getId(),startDate,endDate);
		  try{
//				   Map<Integer,Integer> map = appliedLeaves.stream().collect(Collectors.groupingBy(apl->AppliedLeaveDateView::getUserId,counting(),Map::size));
			 Map<Long, Long> map = appliedLeavesView.stream().collect(Collectors.groupingBy(AppliedLeaveDateView::getUserId,Collectors.counting()));
			 List<AssociateBalanceLeaveDTO> assBalList = new ArrayList<>();
			 for(User user : userList){
				 if(map.keySet().contains(user.getId())){
					 
					 float associateBalanceLeaves = 0;
					 if(Constants.YES.equalsIgnoreCase(leaveType.getVisibleInBalanceLeaves())) {
						 associateBalanceLeaves = getTotalLeavesByUserIdAndLeaveTypeId(leaveType.getId(), user.getId(), orgLocations.getId());
					 }
					float totalBal = 0f;
					
					AssociateBalanceLeaveView ablv = new AssociateBalanceLeaveView();
					ablv.setIncludeWeekoff(leaveType.getIncludeWeekoff());
					ablv.setIncludeHolidays(leaveType.getIncludeHolidays());
					float appliedLeaveCount = 0;
					LocalDate todayDate = LocalDate.now();
					List<AppliedLeaveDateView> appliedLeaves = null;
					if (Constants.MONTHLY.equalsIgnoreCase(leaveType.getPeriod()) && Constants.YES.equalsIgnoreCase(leaveType.getVisibleInBalanceLeaves())) {
						Date[] monthDates = getMonthStartDateEndDate();
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), leaveType.getId(), monthDates[0], monthDates[1], user.getId());
					    appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,ablv,monthDates[0],monthDates[1]);
					}
					else if (Constants.QUARTERLY.equalsIgnoreCase(leaveType.getPeriod()) && Constants.YES.equalsIgnoreCase(leaveType.getVisibleInBalanceLeaves())) {
						LocalDate[] currentMonthQuarter = conversionUtil.getThisMonthQuarterDates(todayDate);
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), leaveType.getId(), currentMonthQuarter[0].toDate(), currentMonthQuarter[1].toDate(), user.getId());
					     appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,ablv,currentMonthQuarter[0].toDate(),currentMonthQuarter[1].toDate());
					}
					else if (Constants.HALFYEARLY.equalsIgnoreCase(leaveType.getPeriod()) && Constants.YES.equalsIgnoreCase(leaveType.getVisibleInBalanceLeaves())) {
						LocalDate[] currentHalfYear = conversionUtil.getThisMonthHalfyearyDates(todayDate);
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), leaveType.getId(), currentHalfYear[0].toDate(), currentHalfYear[1].toDate(), user.getId());
						appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,ablv,currentHalfYear[0].toDate(),currentHalfYear[1].toDate());
					}
					else if (Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod()) && Constants.YES.equalsIgnoreCase(leaveType.getVisibleInBalanceLeaves())) {
						LocalDate[] thisYearDates = conversionUtil.getThisYearStartDateEndDate();
						appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(), leaveType.getId(), thisYearDates[0].toDate(), thisYearDates[1].toDate(), user.getId());
						appliedLeaves = getExcludeHolidayLeaveCount(appliedLeaves,orgLocations,ablv,thisYearDates[0].toDate(),thisYearDates[1].toDate());
					}
					
					if(Constants.YES.equalsIgnoreCase(leaveType.getAccuralPolicy())){
						if(Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
							totalBal = 0;
						}else if(Constants.YES.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
							if (appliedLeaves != null && appliedLeaves.size() > 0) {
								totalBal = associateBalanceLeaves - appliedLeaves.size();
								if(Constants.YES.equalsIgnoreCase(leaveType.getIncludeInPayroll())){
									if(totalBal < 0){
										totalBal = 0;
									}
								}else if(Constants.NO.equalsIgnoreCase(leaveType.getIncludeInPayroll())){
									totalBal = associateBalanceLeaves - appliedLeaves.size();
								} 
							}
						}
						totalBal += leaveType.getNoOfDaysPerPeriod();
					}else if(Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())){
						if(Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
							totalBal = associateBalanceLeaves - leaveType.getNoOfDaysPerPeriod();
						}else if(Constants.YES.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
							float remainingBal = 0f;
							if (appliedLeaves != null && appliedLeaves.size() > 0) {
								remainingBal = leaveType.getNoOfDaysPerPeriod() - appliedLeaves.size();
								if(Constants.YES.equalsIgnoreCase(leaveType.getIncludeInPayroll())){
									if(remainingBal < 0){
										totalBal = associateBalanceLeaves - leaveType.getNoOfDaysPerPeriod();
									}else{
										totalBal = associateBalanceLeaves - appliedLeaves.size();
									}
								}else if(Constants.NO.equalsIgnoreCase(leaveType.getIncludeInPayroll())){
									totalBal = associateBalanceLeaves - appliedLeaves.size();
								}
							}else{
								totalBal = associateBalanceLeaves;
							}
						}
					}
					 
					
//					totalBal =  associateBalanceLeaves - map.get(user.getId()) ;
				    
				    AssociateBalanceLeaveDTO assBal = new AssociateBalanceLeaveDTO();
				    assBal.setUserId(String.valueOf(user.getId()));
				    assBal.setLeaveTypeId(leaveType.getId());
				    assBal.setNoofLeaves(String.valueOf(totalBal));
				    assBal.setOrgLocationId(orgLocations.getId());
				    assBalList.add(assBal);
				 }else{
					 float associateBalanceLeaves = getTotalLeavesByUserIdAndLeaveTypeId(leaveType.getId(), user.getId(), orgLocations.getId());
//					 float totalBal = associateBalanceLeaves + leaveType.getNoOfDaysPerPeriod();
					 float totalBal = 0;
					if (Constants.YES.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
						if (Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())) {
							totalBal += leaveType.getNoOfDaysPerPeriod();
						}
						else if (Constants.YES.equalsIgnoreCase(leaveType.getLeavesCarryforward())) {
							totalBal = associateBalanceLeaves + leaveType.getNoOfDaysPerPeriod();
						}
					}
					else if (Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
						if (Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())) {
							totalBal = associateBalanceLeaves;
						}
						else if (Constants.YES.equalsIgnoreCase(leaveType.getLeavesCarryforward())) {
							totalBal = associateBalanceLeaves;
						}
					}
					 AssociateBalanceLeaveDTO assBal = new AssociateBalanceLeaveDTO();
				     assBal.setUserId(String.valueOf(user.getId()));
				     assBal.setLeaveTypeId(leaveType.getId());
				     assBal.setNoofLeaves(String.valueOf(totalBal));
				     assBal.setOrgLocationId(orgLocations.getId());
				     assBalList.add(assBal);
				 }
			 }
			 stat = updateAssociateAccuralBalanceLeaveCount(assBalList);
			 
		   }catch (Exception e) {
			   e.printStackTrace();
		   }
		return stat;
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
	
}
