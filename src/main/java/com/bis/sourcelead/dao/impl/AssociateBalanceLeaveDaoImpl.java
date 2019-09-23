package com.bis.sourcelead.dao.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateBalanceLeaveDao;
import com.bis.sourcelead.dto.AssociateBalanceLeaveDTO;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.dto.LeaveTypeEditDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TotalLeaveDTO;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.LeaveManagementUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Repository
public class AssociateBalanceLeaveDaoImpl implements AssociateBalanceLeaveDao{

	private static final Logger logger = LoggerFactory.getLogger(AssociateBalanceLeaveDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	LeaveTypeService leaveTypeService;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	private LeaveManagementUtil leaveManagementUtil;
	
	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Override
	@Transactional
	public int addAssociateLeave(AssociateBalanceLeaveDTO associateBalanceLeaveDTO,User loginUser, OrgLocations orgLocations)
	{

		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call associateBalanceLeave(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
					for (TotalLeaveDTO leave : associateBalanceLeaveDTO.getTotalLeavesDto()) {
						if(StringUtils.isNotBlank(leave.getUserId())) {
							stmt.setLong(1, Long.parseLong(leave.getUserId()));
						}else {
							stmt.setLong(1, Long.parseLong(associateBalanceLeaveDTO.getUserId()));
						}
						stmt.setLong(2, orgLocations.getId());
						stmt.setLong(3, Long.parseLong(leave.getLeaveTypeId()));
						stmt.setString(4, leave.getNoOfLeaves());
						stmt.setString(5, SourceLeadRandomCodeHelper.generateRandomUUID());
						if(leave.getLeavetypeRoles() != null) {
							stmt.setString(6, leave.getLeavetypeRoles());
						}else {
							stmt.setString(6, null);
						}
						if(leave.getLeavetypeDepartment() != null) {
							stmt.setString(7, leave.getLeavetypeDepartment());
						}else {
							stmt.setString(7, null);
						}
						if(leave.getLeavetypeEmpType() != null) {
							stmt.setString(8, leave.getLeavetypeEmpType());
						}else {
							stmt.setString(8, null);
						}
						stmt.setString(8, leave.getLeavetypeEmpType());
						stmt.setInt(9, 1);
						stmt.setLong(10, loginUser.getId());
						stmt.setDate(11, new java.sql.Date(new Date().getTime()));
						stmt.setLong(12, loginUser.getId());
						stmt.setDate(13, new java.sql.Date(new Date().getTime()));
						
						stmt.addBatch();
					}
					int[] updateCount = stmt.executeBatch();
					connection.commit();
					connection.setAutoCommit(initialAutocommitSetting);
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}


	@Override
	@Transactional
	public ResponseList<AssociateBalanceLeaveView> getAssociateBalanceLeavesByUserId(Long userId,Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Date[] yearDates = ConversionUtil.getCurrentYearStartAndEndDate();
//		java.sql.Date yearStartDate = new java.sql.Date(yearDates[0].getTime());
//		java.sql.Date yearEndDate = new java.sql.Date(yearDates[1].getTime());
		ResponseList<AssociateBalanceLeaveView> res = new ResponseList<>();
//		List<AssociateBalanceLeaveView> assoBalLeave = session.createQuery("from AssociateBalanceLeaveView balLeaveView where balLeaveView.userId =:userId and balLeaveView.orgLocationId = :orgLocationId and balLeaveView.status =1 and date(balLeaveView.createdDate) BETWEEN :yearStartDate AND :yearEndDate" ).setLong( "userId", userId ).setLong("orgLocationId", orgLocationId).setDate("yearStartDate", yearStartDate).setDate("yearEndDate", yearEndDate).list();
		List<AssociateBalanceLeaveView> assoBalLeave = session.createQuery("from AssociateBalanceLeaveView balLeaveView where balLeaveView.userId =:userId and balLeaveView.orgLocationId = :orgLocationId and balLeaveView.status =1" ).setLong( "userId", userId ).setLong("orgLocationId", orgLocationId).list();
		res.setData(assoBalLeave);
		return res;
		
	}


	@Override
	@Transactional
	public AssociateBalanceLeave getAssociateBalanceByUUID(String assoBalLeaveUUID)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateBalanceLeave associateBalanceLeave = null;
		List<AssociateBalanceLeave> assoBalLeave = session.createQuery("from AssociateBalanceLeave balLeave where balLeave.assoBalLeaveUUID =:assoBalLeaveUUID").setString("assoBalLeaveUUID", assoBalLeaveUUID).list();
		if (assoBalLeave != null && assoBalLeave.size() > 0) {
			associateBalanceLeave = assoBalLeave.get(0);
		}
		return associateBalanceLeave;
	}


	@Override
	@Transactional
	public int updateAssociateBalLeave(String assoBalLeaveUUID,String totalLeaves)
	{
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update AssociateBalanceLeave balleave set balleave.totalLeaves =:totalLeaves where balleave.assoBalLeaveUUID = :assoBalLeaveUUID ");
        query.setString("totalLeaves", totalLeaves);  
        query.setString("assoBalLeaveUUID", assoBalLeaveUUID);  
        int result = query.executeUpdate();
        return result;
	}


	@Override
	@Transactional
	public AssociateBalanceLeave getTotalLeavesByUserIdAndLeaveTypeId(Long leaveTypeId, Long userId,Long orgLocationId, Long orgId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateBalanceLeave associateBalanceLeave = null;
		List<AssociateBalanceLeave> assoBalLeave = session.createQuery("from AssociateBalanceLeave balLeave where balLeave.user.id =:userId and balLeave.leaveTypeId = :leaveTypeId and (balLeave.orgLocationId.id = :orgLocationId or balLeave.orgLocationId.org.id = :orgId)").setLong("userId", userId).setLong("orgLocationId", orgLocationId).setLong("leaveTypeId", leaveTypeId).setLong("orgId", orgId).list();
		if (assoBalLeave != null && assoBalLeave.size() > 0) {
			associateBalanceLeave = assoBalLeave.get(0);
		}
		return associateBalanceLeave;
	}


	@Override
	@Transactional
	public int updateAssociateBalanceLeaveType(List<User> userList, Long orgLocationId, LeaveType leaveType,int status,float diffLeaves,String actionType,String methodType)
	{
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call deleteAssociateBalLeave(?,?,?,?,?,?,?,?)}");
					for (User user : userList) {
						if(Constants.UPDATE.equalsIgnoreCase(methodType)) {
							OrgLocations orgLocations = new OrgLocations();
							orgLocations.setId(orgLocationId);
							/*boolean b = getOrgLevelLeaves(orgLocations, user.getId(), leaveType);
							if(!b) {
								stmt.setLong(1, 0);
							}else{*/
								stmt.setLong(1, status);
//							}
						}else {
							stmt.setLong(1, status);
						}
						OrgLocations orgLoc = null;
						try {
							orgLoc = orgLocationService.getOrgLocations(orgLocationId);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						AssociateBalanceLeave associateBalanceLeave = getTotalLeavesByUserIdAndLeaveTypeId(leaveType.getId(), user.getId(), orgLocationId,orgLoc.getOrg().getId());
						float assoBalLeaves = Float.parseFloat(associateBalanceLeave.getTotalLeaves());
						Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocationId);
						float days = 0;
								try{
								days =	leaveManagementUtil.getNumberofDaysPerPeriodForLeaveType(leaveType, emp, user);
								}catch (Exception e) {
									e.printStackTrace();
								}
								
						if(!"NONE".equalsIgnoreCase(actionType)) {
							float additionLeaves = 0;
							if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
								if("ADD".equalsIgnoreCase(actionType)) {
									 additionLeaves = assoBalLeaves + diffLeaves;
								}if("SUB".equalsIgnoreCase(actionType)) {
									additionLeaves = assoBalLeaves - diffLeaves;
								}if("IMPORT".equalsIgnoreCase(actionType)) {
									additionLeaves = diffLeaves;
								}
								
							}else{
								if("ADD".equalsIgnoreCase(leaveType.getActionExpType())) {
									 additionLeaves = assoBalLeaves + days;
								}if("SUB".equalsIgnoreCase(leaveType.getActionExpType())) {
									additionLeaves = assoBalLeaves - days;
								}if("IMPORT".equalsIgnoreCase(actionType)) {
									additionLeaves = diffLeaves;
								}
								
								if("ADD".equalsIgnoreCase(leaveType.getActionFresherType())) {
									 additionLeaves = assoBalLeaves + days;
								}if("SUB".equalsIgnoreCase(leaveType.getActionFresherType())) {
									additionLeaves = assoBalLeaves - days;
								}if("IMPORT".equalsIgnoreCase(actionType)) {
									additionLeaves = diffLeaves;
								}
							}
							stmt.setString(2, String.valueOf(additionLeaves));	
						}else {
							stmt.setString(2, String.valueOf(assoBalLeaves+days));
//							if(!Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod()) && !Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
//								if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
//									stmt.setString(2, String.valueOf(assoBalLeaves+days));
//								}else {
////									Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocationId);
////										float days = leaveManagementUtil.getNumberofDaysPerPeriodForLeaveType(leaveType, emp, user);
//										stmt.setString(2, String.valueOf(assoBalLeaves+days));
//								}
//							}else {
//								
//								if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
//									stmt.setString(2, String.valueOf(assoBalLeaves+days));
//								}else {
////									Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocationId);
////										float days = leaveManagementUtil.getNumberofDaysPerPeriodForLeaveType(leaveType, emp, user);
//										stmt.setString(2, String.valueOf(days));
//								}
//							}
						}
						stmt.setLong(3, orgLocationId);
						stmt.setLong(4, user.getId());
						stmt.setLong(5, leaveType.getId());
						if(leaveType.getLeaveTypeRolesSet() != null && !leaveType.getLeaveTypeRolesSet().isEmpty() && leaveType.getLeaveTypeRolesSet().size() > 0) {
							String s = leaveType.getLeaveTypeRolesSet().stream().map(r->r.getRoleId().getId().toString()).collect(Collectors.joining(","));
							stmt.setString(6, s);
						}else {
							stmt.setString(6, null);
						}
						
						if(leaveType.getDepartmentIds() != null) {
							stmt.setString(7, leaveType.getDepartmentIds());
						}else {
							stmt.setString(7, null);
						}
						if(!leaveType.getLeaveEmpType().isEmpty()) {
							stmt.setString(8, leaveType.getLeaveEmpType());
						}else {
							stmt.setString(8, null);
						}
						stmt.addBatch();
					}
					int[] updateCount = stmt.executeBatch();
					connection.commit();
					connection.setAutoCommit(initialAutocommitSetting);
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
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
	@Transactional
	public AssociateBalanceLeave saveOrUpdateAssocicateBalanceLeaves(AssociateBalanceLeave assBalLeave)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (assBalLeave != null) {
				session.saveOrUpdate(assBalLeave);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return assBalLeave;
		
	}


	@Override
	@Transactional
	public int updateAssociateAccuralBalanceLeaveCount(List<AssociateBalanceLeaveDTO> assBalList)
	{
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call updateAssociateBalLeave(?,?,?,?)}");
					for (AssociateBalanceLeaveDTO leave : assBalList) {
						stmt.setString(1, leave.getNoofLeaves());
						stmt.setLong(2, leave.getOrgLocationId());
						stmt.setLong(3, Long.parseLong(leave.getUserId()));
						stmt.setLong(4, leave.getLeaveTypeId());
						stmt.addBatch();
					}
					int[] updateCount = stmt.executeBatch();
					connection.commit();
					connection.setAutoCommit(initialAutocommitSetting);
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}


	@Override
	@Transactional
	public List<AssociateBalanceLeave> getAssoBalLeavesByUserId(Long userId, Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateBalanceLeave> assoBalLeave = session.createQuery("from AssociateBalanceLeave balLeave where balLeave.user.id =:userId and balLeave.orgLocationId.id= :orgLocationId").setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
		return assoBalLeave;
	}
	
	
	
	@Override
	@Transactional
	public int updateAssociateDateOfJoinBalanceLeaveType(Long orgLocationId, User user,int status,String methodType)
	{
		List<LeaveType> leavetypeList = leaveTypeService.getLeaveTypeListByLocationId(orgLocationId.toString());
		if(leavetypeList != null) {
			try {
				Session session = this.sessionFactory.getCurrentSession();
				session.doWork(new Work() {
					@Override
					public void execute(java.sql.Connection connection) throws SQLException {
						boolean initialAutocommitSetting = connection.getAutoCommit();
						connection.setAutoCommit(false);
						CallableStatement stmt = connection.prepareCall("{call deleteAssociateBalLeave(?,?,?,?,?,?,?,?)}");
						for (LeaveType leaveType : leavetypeList) {
							if(Constants.UPDATE.equalsIgnoreCase(methodType)) {
								OrgLocations orgLocations = new OrgLocations();
								orgLocations.setId(orgLocationId);
//								boolean b = getOrgLevelLeaves(orgLocations, user.getId(), leaveType);
//								if(!b) {
//									stmt.setLong(1, 0);
//								}else{
									stmt.setLong(1, status);
//								}
							}else {
								stmt.setLong(1, status);
							}
								if(!Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod()) && !Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
									if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
										stmt.setString(2, String.valueOf(leaveType.getNoOfDaysPerPeriod()));
									}else {
										Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocationId);
										 try {
											float days = leaveManagementUtil.getNumberofDaysPerPeriodForLeaveType(leaveType, emp, user);
											stmt.setString(2, String.valueOf(days));
										} catch (ParseException e) {
											e.printStackTrace();
										}
									}
								}else {
									
									if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
										stmt.setString(2, String.valueOf(leaveType.getNoOfDays()));
									}else {
										Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocationId);
										 try {
											float days = leaveManagementUtil.getNumberofDaysPerPeriodForLeaveType(leaveType, emp, user);
											stmt.setString(2, String.valueOf(days));
										} catch (ParseException e) {
											e.printStackTrace();
										}
									}
								}
							stmt.setLong(3, orgLocationId);
							stmt.setLong(4, user.getId());
							stmt.setLong(5, leaveType.getId());
							if(leaveType.getLeaveTypeRolesSet() != null && !leaveType.getLeaveTypeRolesSet().isEmpty() && leaveType.getLeaveTypeRolesSet().size() > 0) {
								String s = leaveType.getLeaveTypeRolesSet().stream().map(r->r.getRoleId().getId().toString()).collect(Collectors.joining(","));
								stmt.setString(6, s);
							}else {
								stmt.setString(6, null);
							}
							
							if(leaveType.getDepartmentIds() != null) {
								stmt.setString(7, leaveType.getDepartmentIds());
							}else {
								stmt.setString(7, null);
							}
							if(StringUtils.isNotBlank(leaveType.getLeaveEmpType())) {
								stmt.setString(8, leaveType.getLeaveEmpType());
							}else {
								stmt.setString(8, null);
							}
							stmt.execute();
							connection.commit();
						}
						connection.setAutoCommit(initialAutocommitSetting);
					}
				});
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}return 0;
	}
	
	
	@Override
	@Transactional
	public int addAssociateLeaveType(User user, Long orgLocationId,Employees employee, UserRole userRole)
	{
		List<LeaveType> leavetypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(orgLocationId.toString());
		List<LeaveType> roleLeaveType = new ArrayList<>();
		if (leavetypeList != null && !leavetypeList.isEmpty()) {
			for (LeaveType leaveType : leavetypeList) {
				List<Leave> takenLeavesList = leaveService.getLeavesListByTypeIdAndLocationIdAndUserId(leaveType.getLeaveTypeUUID(), orgLocationId, user.getId());
				List<Leave> pendingAndApprovedList = takenLeavesList.stream().filter(tl->Constants.PENDING.equalsIgnoreCase(tl.getStatus()) || Constants.APPROVED.equalsIgnoreCase(tl.getStatus())).collect(Collectors.toList());
				if(StringUtils.isNotBlank(leaveType.getAllowability())){
					if(StringUtils.isNotBlank(leaveType.getAllowability()) && pendingAndApprovedList.size() < Integer.parseInt(leaveType.getAllowability())){
						boolean flag = leaveManagementUtil.checkGenderAndDepartmentAndRoleAndEmpType(leaveType,employee,userRole);
						if(flag == true){
							roleLeaveType.add(leaveType);
						}
					}
				}else{
					boolean flag = leaveManagementUtil.checkGenderAndDepartmentAndRoleAndEmpType(leaveType,employee,userRole);
					if(flag == true){
						roleLeaveType.add(leaveType);
					}
				}
			}
			
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call associateBalanceLeave(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
					for(LeaveType leaveType : roleLeaveType) {
						stmt.setLong(1, user.getId());
						stmt.setLong(2, orgLocationId);
						stmt.setLong(3, leaveType.getId());
						if(!Constants.ANNUALLY.equalsIgnoreCase(leaveType.getPeriod()) && !Constants.NO.equalsIgnoreCase(leaveType.getAccuralPolicy())) {
							if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
								stmt.setString(4, String.valueOf(leaveType.getNoOfDaysPerPeriod()));
							}else {
								Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocationId);
								 try {
									float days = leaveManagementUtil.getNumberofDaysPerPeriodForLeaveType(leaveType, emp, user);
									stmt.setString(4, String.valueOf(days));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
						}else {
							
							if("All Employees".equalsIgnoreCase(leaveType.getLeaveEffective())) {
								stmt.setString(4, String.valueOf(leaveType.getNoOfDays()));
							}else {
								Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocationId);
								 try {
									float days = leaveManagementUtil.getNumberofDaysPerPeriodForLeaveType(leaveType, emp, user);
									stmt.setString(4, String.valueOf(days));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
						}
						
						stmt.setString(5, SourceLeadRandomCodeHelper.generateRandomUUID());
						
						if(leaveType.getLeaveTypeRolesSet() != null && !leaveType.getLeaveTypeRolesSet().isEmpty() && leaveType.getLeaveTypeRolesSet().size() > 0) {
							stmt.setString(6, leaveType.getLeaveTypeRolesSet().stream().map(l-> String.valueOf(l.getRoleId().getId())).collect(Collectors.joining(",")));
						}else {
							stmt.setString(6, null);
						}
						if(StringUtils.isNotBlank(leaveType.getDepartmentIds())) {
							stmt.setString(7, leaveType.getDepartmentIds());
						}else {
							stmt.setString(7, null);
						}
						if(StringUtils.isNotBlank(leaveType.getLeaveEmpType())) {
							stmt.setString(8, leaveType.getLeaveEmpType());
						}else {
							stmt.setString(8, null);
						}
						stmt.setInt(9, 1);
						stmt.setLong(10, user.getId());
						stmt.setDate(11, new java.sql.Date(new Date().getTime()));
						stmt.setLong(12, user.getId());
						stmt.setDate(13, new java.sql.Date(new Date().getTime()));
						stmt.addBatch();
					}
					int[] updateCount = stmt.executeBatch();
					connection.commit();
					connection.setAutoCommit(initialAutocommitSetting);
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	  }return 0;
	}


	@Override
	@Transactional
	public int updateLeaveTypeAssociateBalances(List<LeaveTypeEditDTO> list)
	{
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call deleteAssociateBalLeave(?,?,?,?,?,?,?,?)}");
					for (LeaveTypeEditDTO dto : list) {
						stmt.setLong(1, dto.getStatus());
						stmt.setString(2, String.valueOf(dto.getTotalLeaves()));	
						stmt.setLong(3,  dto.getLocationId());
						stmt.setLong(4,  dto.getUserId());
						stmt.setLong(5,  dto.getLeaveTypeId());
						stmt.setString(6, dto.getLeavetypeRoles());
						stmt.setString(7, dto.getLeavetypeDepartment());
						stmt.setString(8, dto.getLeavetypeEmpType());
						stmt.addBatch();
					}
					int[] updateCount = stmt.executeBatch();
					connection.commit();
					connection.setAutoCommit(initialAutocommitSetting);
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}


	@Override
	@Transactional
	public int saveAssociateBalLeaveHistory(List<AssociateBalanceLeaveDTO> assBalList,Date startDate,Date endDate) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
			int year = cal.get(Calendar.YEAR);
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call associateBalanceLeaveHistoryProcedure(?,?,?,?,?,?,?,?)}");
					for (AssociateBalanceLeaveDTO leave : assBalList) {
						stmt.setLong(1, Long.parseLong(leave.getUserId()));
						stmt.setLong(2, leave.getOrgLocationId());
						stmt.setDate(3, new java.sql.Date(startDate.getTime()));
						stmt.setDate(4, new java.sql.Date(endDate.getTime()));
						stmt.setInt(5, year);
						stmt.setString(6, month+ "-" +year);
						stmt.setFloat(7, leave.getAssBalLeaveHis());
						stmt.setLong(8, leave.getLeaveTypeId());
						
						stmt.addBatch();
					}
					int[] count = stmt.executeBatch();
					connection.commit();
					connection.setAutoCommit(initialAutocommitSetting);
				}
			});
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
