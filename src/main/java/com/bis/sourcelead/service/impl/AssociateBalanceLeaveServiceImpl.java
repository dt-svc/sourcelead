package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.LeaveManagementUtil;
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
			logger.error("Add Association Balance Leave Failed Due To : "+e.getMessage());
		}
		
		
		return resObj;
	}

	@Override
	public List<AssociateBalanceLeaveDTO> getAssociateBalanceLeavesByUserId(Long userId,OrgLocations orgLocations,User loginUser)
	{
		
		ResponseList<AssociateBalanceLeaveView> finalResObj = associateBalanceLeaveDao.getAssociateBalanceLeavesByUserId(userId, orgLocations.getId());
		ArrayList<AssociateBalanceLeaveDTO> list = new ArrayList<>();
		if (finalResObj.getData() != null && finalResObj.getData().size() > 0) {
			for (AssociateBalanceLeaveView l : finalResObj.getData()) {
				AssociateBalanceLeaveDTO associateBalanceLeaveDTO = new AssociateBalanceLeaveDTO();
				associateBalanceLeaveDTO.setId(String.valueOf(l.getId()));
				associateBalanceLeaveDTO.setAssoBalLeaveUUID(l.getAssoBalLeaveUUID());
				associateBalanceLeaveDTO.setLeaveTypeName(l.getLeaveTypeName());
				associateBalanceLeaveDTO.setNoofLeaves(l.getTotalLeaves());
				associateBalanceLeaveDTO.setLeaveTypeId(l.getLeaveTypeId());
				float appliedLeaveCount = 0;
//				float balanceLeaveCounts = 0;
//				float takenLeaveCounts = 0;
//				float totalLeaves = getTotalLeavesByUserIdAndLeaveTypeId(l.getLeaveTypeId(), userId, orgLocations.getId());
				Date [] monthDates = getMonthStartDateEndDate();
                List<AppliedLeaveDateView> appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationIdAndUserId(orgLocations.getId(),l.getLeaveTypeId(),monthDates[0],monthDates[1],userId);
                if(appliedLeaves != null && appliedLeaves.size() >0) {
                	appliedLeaveCount = Float.parseFloat(l.getTotalLeaves()) - appliedLeaves.size();
                	associateBalanceLeaveDTO.setBalanceLeaveCounts(Float.parseFloat(dec.format(appliedLeaveCount)));
                }else {
                	associateBalanceLeaveDTO.setBalanceLeaveCounts(Float.parseFloat(l.getTotalLeaves()));
                }
                 
                /*float totalbalanceLeaveCounts = totalLeaves;
				float calculatedTotalNonCarryforwardLeaves = 0;
				LeaveType leaveType = null;
				try {
					leaveType = leaveTypeService.getLeaveTypeById(l.getLeaveTypeId());
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			
				
				
				
			/*	List<Leave> totalleavesTaken = leaveService.getLeavesListByTypeIdAndLocationIdAndUserId(leaveType.getLeaveTypeUUID(), orgLocations.getId(), userId);
				for (Leave leave : totalleavesTaken) {
					float appliedLeaveCount = leaveManagementUtil.calculateAppliedLeavesCountWithHalfDays(leave);
					if (Constants.APPROVED.equalsIgnoreCase(leave.getStatus())
							|| Constants.PENDING.equalsIgnoreCase(leave.getStatus())) {
						totalbalanceLeaveCounts = totalbalanceLeaveCounts
								- (appliedLeaveCount - leave.getExtraLeavesTaken());
						takenLeaveCounts = takenLeaveCounts + appliedLeaveCount;
					}
				}
				if ("Monthly".equalsIgnoreCase(leaveType.getPeriod())
						&& Constants.NO.equalsIgnoreCase(leaveType.getLeavesCarryforward())) {
					try {
						calculatedTotalNonCarryforwardLeaves = leaveManagementUtil.getTotalBalanceLeavesForNonCarryforwardLeaveType(leaveType, totalbalanceLeaveCounts, null);
					} catch (Exception e) {
						e.printStackTrace();
					}
					totalbalanceLeaveCounts = calculatedTotalNonCarryforwardLeaves;
					balanceLeaveCounts += totalbalanceLeaveCounts;
				}
				else {
					balanceLeaveCounts += totalbalanceLeaveCounts;
				}*/
				associateBalanceLeaveDTO.setAppliedLeaveCount(appliedLeaves.size());
				if (l.getCreatedByFirstName() != null && !"".equalsIgnoreCase(l.getCreatedByFirstName())) {
					associateBalanceLeaveDTO.setCreatedBy(l.getCreatedByFirstName() + " "+ l.getCreatedByLastName());
				}
				else {
					associateBalanceLeaveDTO.setCreatedBy(l.getCreatedByFirstName());
				}
				list.add(associateBalanceLeaveDTO);
			}
		}
		return list;
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
					if(!Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod())  && !Constants.YES.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
						totalLeaveDTO.setNoOfLeaves(String.valueOf(leaveType.getNoOfDaysPerPeriod()));
					}else {
						totalLeaveDTO.setNoOfLeaves(String.valueOf(leaveType.getNoOfDays()));	
					}
				
					list.add(totalLeaveDTO);
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
					Set<Long> roleIds = new HashSet<>();
					if(leaveType.getLeaveTypeRolesSet() != null) {
						for (LeaveRole leaveRole : leaveType.getLeaveTypeRolesSet()) {
							roleIds.add(leaveRole.getRoleId().getId());
						}
						if (roleIds.contains(userRole.getRoles().getId()) || "Y".equalsIgnoreCase(leaveType.getApplicableToUsers())) {
							isLeaveApplicable = true;
							//						roleLeaveType.add(leaveType);
						}	
					}else {
						if("Y".equalsIgnoreCase(leaveType.getApplicableToUsers())) {
							isLeaveApplicable = true;
						}
					}
				
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
//		for(User user : userList) {
//			associateBalanceLeaveDao.getTotalLeavesByUserIdAndLeaveTypeId(leaveType.getId(), user.getId(), orgLocationId);
//		}
//		return 0;
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
	public int calculateAndUpdateAssociateBalanceLeaves(Long orgLocationId, LeaveType leaveType, Date startDate, Date endDate)
	{
		int stat = 0;
		List<AppliedLeaveDateView> appliedLeaves = appliedLeaveDateService.getAppliedLeaveDatesByLeaveTypeIdAndOrgLocationId(orgLocationId,leaveType.getId(),startDate,endDate);
		  try{
//				   Map<Integer,Integer> map = appliedLeaves.stream().collect(Collectors.groupingBy(apl->AppliedLeaveDateView::getUserId,counting(),Map::size));
			 Map<Long, Long> map = appliedLeaves.stream().collect(Collectors.groupingBy(AppliedLeaveDateView::getUserId,Collectors.counting()));
			 System.out.println(map);
			 List<AssociateBalanceLeaveDTO> assBalList = new ArrayList<>();
			 for(Long userId : map.keySet()){
				 float associateBalanceLeaves = getTotalLeavesByUserIdAndLeaveTypeId(leaveType.getId(), userId, orgLocationId);
				 float balLeaves = 0;
				 float totalBal = 0f;
			     if(map.get(userId) > leaveType.getNoOfDaysPerPeriod()){
			    	balLeaves = map.get(userId) - leaveType.getNoOfDaysPerPeriod();
			    	totalBal = associateBalanceLeaves - balLeaves;
			     }else if(map.get(userId) < leaveType.getNoOfDaysPerPeriod()){
			    	balLeaves = leaveType.getNoOfDaysPerPeriod() - map.get(userId);
			    	totalBal = associateBalanceLeaves + balLeaves;
			     }
			    totalBal += leaveType.getNoOfDaysPerPeriod();
			    AssociateBalanceLeaveDTO assBal = new AssociateBalanceLeaveDTO();
			    assBal.setUserId(String.valueOf(userId));
			    assBal.setLeaveTypeId(leaveType.getId());
			    assBal.setNoofLeaves(String.valueOf(totalBal));
			    assBal.setOrgLocationId(orgLocationId);
			    assBalList.add(assBal);
			 }
			 
			 stat = updateAssociateAccuralBalanceLeaveCount(assBalList);
			 
		   }catch (Exception e) {
			// TODO: handle exception
		   }
		return stat;
	}
	
}
