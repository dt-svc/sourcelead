package com.bis.sourcelead.dao.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TotalLeaveDTO;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.ConversionUtil;
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
					CallableStatement stmt = connection.prepareCall("{call associateBalanceLeave(?,?,?,?,?,?,?,?,?,?)}");
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
						stmt.setInt(6, 1);
						stmt.setLong(7, loginUser.getId());
						stmt.setDate(8, new java.sql.Date(new Date().getTime()));
						stmt.setLong(9, loginUser.getId());
						stmt.setDate(10, new java.sql.Date(new Date().getTime()));
						
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
		java.sql.Date yearStartDate = new java.sql.Date(yearDates[0].getTime());
		java.sql.Date yearEndDate = new java.sql.Date(yearDates[1].getTime());
		ResponseList<AssociateBalanceLeaveView> res = new ResponseList<>();
		List<AssociateBalanceLeaveView> assoBalLeave = session.createQuery("from AssociateBalanceLeaveView balLeaveView where balLeaveView.userId =:userId and balLeaveView.orgLocationId = :orgLocationId and balLeaveView.status =1 and balLeaveView.createdDate BETWEEN :yearStartDate AND :yearEndDate" ).setLong( "userId", userId ).setLong("orgLocationId", orgLocationId).setDate("yearStartDate", yearStartDate).setDate("yearEndDate", yearEndDate).list();
		
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
	public AssociateBalanceLeave getTotalLeavesByUserIdAndLeaveTypeId(Long leaveTypeId, Long userId,Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateBalanceLeave associateBalanceLeave = null;
		List<AssociateBalanceLeave> assoBalLeave = session.createQuery("from AssociateBalanceLeave balLeave where balLeave.user.id =:userId and balLeave.leaveTypeId = :leaveTypeId and balLeave.orgLocationId.id = :orgLocationId and balLeave.status= 1").setLong("userId", userId).setLong("orgLocationId", orgLocationId).setLong("leaveTypeId", leaveTypeId).list();
		if (assoBalLeave != null && assoBalLeave.size() > 0) {
			associateBalanceLeave = assoBalLeave.get(0);
		}
		return associateBalanceLeave;
	}


	@Override
	@Transactional
	public int updateAssociateBalanceLeaveType(List<User> userList, Long orgLocationId, LeaveType leaveType,int status,int diffLeaves,String actionType,String methodType)
	{
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call deleteAssociateBalLeave(?,?,?,?,?)}");
					for (User user : userList) {
						if(Constants.UPDATE.equalsIgnoreCase(methodType)) {
							OrgLocations orgLocations = new OrgLocations();
							orgLocations.setId(orgLocationId);
							boolean b = getOrgLevelLeaves(orgLocations, user.getId(), leaveType);
							if(!b) {
								stmt.setLong(1, 0);
							}else{
								stmt.setLong(1, status);
							}
						}else {
							stmt.setLong(1, status);
						}
						if(!"NONE".equalsIgnoreCase(actionType)) {
							AssociateBalanceLeave associateBalanceLeave = getTotalLeavesByUserIdAndLeaveTypeId(leaveType.getId(), user.getId(), orgLocationId);
							int assoBalLeaves = Integer.parseInt(associateBalanceLeave.getTotalLeaves());
							int additionLeaves = 0;
							if("ADD".equalsIgnoreCase(actionType)) {
								 additionLeaves = assoBalLeaves + diffLeaves;
							}if("SUB".equalsIgnoreCase(actionType)) {
								additionLeaves = assoBalLeaves - diffLeaves;
							}if("IMPORT".equalsIgnoreCase(actionType)) {
								additionLeaves = diffLeaves;
							}
							stmt.setString(2, String.valueOf(additionLeaves));
						}else {
							stmt.setString(2, String.valueOf(leaveType.getNoOfDays()));
						}
						stmt.setLong(3, orgLocationId);
						stmt.setLong(4, user.getId());
						stmt.setLong(5, leaveType.getId());
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

}
