package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ExpensesApproverHierarchyDao;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;

/**
*
* @author  Pradeep Reddy
*          
*          |Ver_Number|  |Modified By|   |Modified On|  |Comments|
*          -----------   -------------   -------------  ----------
* @version    1.0        Pradeep Reddy     New_File       New_File
* 
* @since   2017-06-02
*/

@Repository
public class ExpensesApproverHierarchyDaoImpl implements ExpensesApproverHierarchyDao {
	private static final Logger logger = LoggerFactory.getLogger(ExpensesApproverHierarchyDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getTotalDraftListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,
			Long orgLocationId, String submissionStatus) {
		return null;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getTotalSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,
			String status, Long orgLocationId) throws Exception {
		
		return null;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getTotalApprovedListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,
			Long orgLocationId, String submissionStatus) throws Exception {
		return null;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getTotalRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,
			Long orgLocationId, String submissionStatus) throws Exception {
		return null;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<ExpensesApproverHierarchy> getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus) throws Exception {
		return null;
	}
	
	

}

