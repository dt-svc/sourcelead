package com.bis.sourcelead.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dao.RequirementLocationsDao;
import com.bis.sourcelead.dao.RequirementSubmissionHistoryDao;
import com.bis.sourcelead.dto.CandidateConfigurationDTO;
import com.bis.sourcelead.dto.CandidateVerificationConfigDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateVerificationConfig;
import com.bis.sourcelead.entity.OfferAcceptanceRateView;
import com.bis.sourcelead.entity.OfferAcceptanceRateViewForVendor;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RecruiterJobsReportView;
import com.bis.sourcelead.entity.RecruiterReportView;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class RequirementSubmissionHistoryDaoImpl implements RequirementSubmissionHistoryDao{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementSubmissionHistoryDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private RequirementLocationsDao requirementLocationsDao;
	
	@Autowired
	private OrgSpocDao orgSpocDao;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionHistory updateRequirementHistory(RequirementSubmissionHistory requirementSubmissionHistory)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.saveOrUpdate(requirementSubmissionHistory);
		}catch(Exception ex){
			logger.error(ExceptionUtils.getFullStackTrace(ex)); 
		}
		logger.info("updated requirement history..!!");
		return requirementSubmissionHistory;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndStage(Long orgLocationID,String submissionType,Long submissionId) throws Exception {
		logger.debug("In RequirementSubmissionHistoryDaoImpl class getRequirementHistoryByLocationIdAndStage method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory requirementSubmissionHistory where requirementSubmissionHistory.submissionId.id = :submissionId and requirementSubmissionHistory.toStage = :toStage and requirementSubmissionHistory.submissionId.orgLocations.id = :orgLocationID ";
		return  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationID", orgLocationID).setString("toStage", submissionType).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionHistory getRequirementSubmissionHistoryBySubmissionId(Long submissionId) {
		logger.debug("In RequirementSubmissionHistoryDaoImpl class getRequirementHistoryByLocationIdAndStage method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory requirementSubmissionHistory where requirementSubmissionHistory.submissionId.id = :submissionId";
		List submissionHistoryList = session.createQuery(query).setLong("submissionId", submissionId).list();
		
		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		if(submissionHistoryList != null && !submissionHistoryList.isEmpty())
		{
			requirementSubmissionHistory = (RequirementSubmissionHistory) submissionHistoryList.get(0);
		}
		return requirementSubmissionHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(
			Long candidateId, Long orgLocationId, Long requirementId, Long submissionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory rsh where rsh.submissionId.id = :submissionId and rsh.submissionId.candidate.id =:candidateId and rsh.submissionId.requirement.id =:requirementId and rsh.submissionId.orgLocations =:orgLocationId ORDER BY rsh.createdDate DESC";
	     List<RequirementSubmissionHistory> requirementHistoryList = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setLong("submissionId", submissionId).list();
	  return requirementHistoryList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionHistory isInCoolingOffPeriodByEmailAndLocationsId(String email, Long orgLocationId, String reqCoolingOffPeriod, Long requirementId) throws ParseException{
		 Session session = this.sessionFactory.getCurrentSession();
		
		
         String query = " FROM RequirementSubmissionHistory rsh where rsh.submissionId.candidate.email =:email AND rsh.submissionId.orgLocations.id =:orgLocationId  ORDER BY rsh.createdDate asc";
		
		List<RequirementSubmissionHistory> requirementHistoryList = session.createQuery(query).setString("email", email).setLong("orgLocationId", orgLocationId).list();
	  
		
		RequirementSubmissionHistory requirementSubmissionHistory1 = null;
		if (requirementHistoryList != null && !requirementHistoryList.isEmpty() ) {
			RequirementSubmissionHistory requirementSubmissionHistory = requirementHistoryList.get(0);
			
	         String query1 = " FROM RequirementSubmissionHistory rsh where rsh.submissionId.id =:submissionId ORDER BY rsh.createdDate DESC";
	 		 List<RequirementSubmissionHistory> requirementHistoryList1 = session.createQuery(query1).setLong("submissionId", requirementSubmissionHistory.getSubmissionId().getId()).list();
	 		
	 		 if (requirementHistoryList1 != null && !requirementHistoryList1.isEmpty() ) {
				requirementSubmissionHistory1 = requirementHistoryList1.get(0);
				
	 		}
         }
		
         return requirementSubmissionHistory1;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionHistory isInCoolingOffPeriodByLocationID(
			CandidateVerificationConfig candidateVerificationConfig,CandidateConfigurationDTO candidateConfigurationDTO, Long orgLocationId, String reqCoolingOffPeriod,
			Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String statusSubQuery1 = "";
		String statusSubQuery2 = "";
		String statusSubQuery3 = "";
		String statusSubQuery4 = "";
		String statusSubQuery5 = "";
		
		String query = " FROM RequirementSubmissionHistory rsh where rsh.submissionId.orgLocations.id =:orgLocationId ";
		
			statusSubQuery1 = " rsh.submissionId.candidate.email =:email";
		
		 if(candidateVerificationConfig.getMobilePhone() == true){
			statusSubQuery2 = " or rsh.submissionId.candidate.mobilePhone =:mobilePhone";
		}
		 else if(candidateVerificationConfig.getPanNumber() == true){
				statusSubQuery3 = " or rsh.submissionId.candidate.panNumber =:panNumber";
			}
		 else if(candidateVerificationConfig.getAadharNumber() == true){
				statusSubQuery4 = " or rsh.submissionId.candidate.aadharNumber =:aadharNumber";
			}
		 else if(candidateVerificationConfig.getSsnNumber() == true){
				statusSubQuery5 = " or rsh.submissionId.candidate.ssnNumber =:ssnNumber";
			}
		
		List<RequirementSubmissionHistory> requirementHistoryList = null;
		
		String finalQuery =query+" and ("+statusSubQuery1+statusSubQuery2+statusSubQuery3+statusSubQuery4+statusSubQuery5+") ORDER BY rsh.createdDate asc";
		if(candidateVerificationConfig.getMobilePhone() == true){
			 requirementHistoryList = session.createQuery(finalQuery).setString("email", candidateConfigurationDTO.getEmail()).setString("mobilePhone", candidateConfigurationDTO.getPhoneNumber()).setLong("orgLocationId", orgLocationId).list();
		}else if(candidateVerificationConfig.getPanNumber() == true) {
			 requirementHistoryList = session.createQuery(finalQuery).setString("email", candidateConfigurationDTO.getEmail()).setString("panNumber", candidateConfigurationDTO.getPanNumber()).setLong("orgLocationId", orgLocationId).list();
		}else if(candidateVerificationConfig.getAadharNumber() == true) {
			 requirementHistoryList = session.createQuery(finalQuery).setString("email", candidateConfigurationDTO.getEmail()).setString("aadharNumber", candidateConfigurationDTO.getAadharNumber()).setLong("orgLocationId", orgLocationId).list();
		}else if(candidateVerificationConfig.getSsnNumber() == true) {
			 requirementHistoryList = session.createQuery(finalQuery).setString("email", candidateConfigurationDTO.getEmail()).setString("ssnNumber", candidateConfigurationDTO.getSsnNumber()).setLong("orgLocationId", orgLocationId).list();
		}
		else {
			 requirementHistoryList = session.createQuery(finalQuery).setString("email", candidateConfigurationDTO.getEmail()).setLong("orgLocationId", orgLocationId).list();

		}
		
		RequirementSubmissionHistory requirementSubmissionHistory1 = null;
		if (requirementHistoryList != null && !requirementHistoryList.isEmpty() ) {
			RequirementSubmissionHistory requirementSubmissionHistory = requirementHistoryList.get(0);
			
	         String query1 = " FROM RequirementSubmissionHistory rsh where rsh.submissionId.id =:submissionId ORDER BY rsh.createdDate DESC";
	 		 List<RequirementSubmissionHistory> requirementHistoryList1 = session.createQuery(query1).setLong("submissionId", requirementSubmissionHistory.getSubmissionId().getId()).list();
	 		
	 		 if (requirementHistoryList1 != null && !requirementHistoryList1.isEmpty() ) {
				requirementSubmissionHistory1 = requirementHistoryList1.get(0);
				
	 		}
         }
		
         return requirementSubmissionHistory1;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getAllRequirementSubmissionHistoryBySubmissionId(List<Long> submissionIds)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory rsh where rsh.submissionId.id IN (:submissionIds)";
		return session.createQuery(query).setParameterList("submissionIds", submissionIds).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Integer getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user,
			String submissionStage, Date startDateUTC, Date endDateUTC,String isGlobalUser) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> requirementsCount = null;
		String globalAccessQuery = "";
		
		if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
			globalAccessQuery = " and rsh.submissionId.orgLocations.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :orgLocationId)";
		}else {
			globalAccessQuery = " and rsh.submissionId.orgLocations.id = :orgLocationId";
		}
		
		
		query ="select count(*) from RequirementSubmissionHistory rsh where rsh.createdBy =:userCode and rsh.toStage = :toStage "+globalAccessQuery+" "
				+ " and date(rsh.createdDate) BETWEEN :startDate and :endDate";
		
		requirementsCount =  session.createQuery(query.toString()).setString("userCode", user.getUserCode()).setString("toStage", submissionStage).setLong("orgLocationId", orgLocationId).setParameter("startDate", startDateUTC).setParameter("endDate", endDateUTC).list();
		Integer count= 0;
		if(requirementsCount != null && !requirementsCount.isEmpty()){
				count = requirementsCount.get(0).intValue();
		}
		return count;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndSubId(Long orgLocationID, Long submissionId)
	{
		logger.debug("In RequirementSubmissionHistoryDaoImpl class getRequirementHistoryByLocationIdAndSubId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory requirementSubmissionHistory where requirementSubmissionHistory.submissionId.id = :submissionId and requirementSubmissionHistory.submissionId.orgLocations.id = :orgLocationID";
		return  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationID", orgLocationID).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionHistory addRequirementHistory(RequirementSubmissionHistory requirementSubmissionHistory)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.saveOrUpdate(requirementSubmissionHistory);
		}catch(Exception ex){
			logger.error(ExceptionUtils.getFullStackTrace(ex)); 
		}
		logger.info("updated requirement history..!!");
		return requirementSubmissionHistory;
	}

	@Override
	@Transactional
	public RecruiterReportView getSubmissionCountsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user,
			String submissionStage, String startDateUTC, String endDateUTC, String isGlobalUser,Long orgId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		String globalAccessQuery = "";
		RecruiterReportView recuiterReportView = null;
		
		if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
			query = "from RecruiterReportView rv where rv.userCode = :userCode and rv.createdDate BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"' and  rv.orgId = :orgId ";
			recuiterReportView = (RecruiterReportView) session.createQuery(query).setString("userCode", user.getUserCode()).setLong("orgId", orgId).uniqueResult();
		}
		else {
			query = "from RecruiterReportView rv where rv.userCode = :userCode and rv.createdDate BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"' and rv.orgLocationId = :orgLocationId  ";
			recuiterReportView = (RecruiterReportView) session.createQuery(query).setString("userCode", user.getUserCode()).setLong("orgLocationId", orgLocationId).uniqueResult();
		}
		
		return  recuiterReportView;
		
	}

	@Override
	@Transactional
	public List<RecruiterReportView> getSubmissionCountsByStageAndCreatedUserListAndOrglocationId(Long orgLocationId,
			List<String> userCodeList, String startDateUTC, String endDateUTC,
			String isGlobalUser, Long orgId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<RecruiterReportView> recuiterReportViewList = null;
		try {
		
		if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
			query = "from RecruiterReportView rv where rv.userCode IN (:userCodeList) and rv.createdDate BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"' and  rv.orgId = :orgId ";
			recuiterReportViewList =  session.createQuery(query).setParameterList("userCodeList", userCodeList).setLong("orgId", orgId).list();
		}
		else {
			query = "from RecruiterReportView rv where rv.userCode IN (:userCodeList) and rv.createdDate BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"' and rv.orgLocationId = :orgLocationId  ";
			recuiterReportViewList =  session.createQuery(query).setParameterList("userCodeList", userCodeList).setLong("orgLocationId", orgLocationId).list();
		}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return  recuiterReportViewList;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<RequirementSubmissionHistory> getAllSubmissionHistoryApplicantsByOwner(RequirementSubmissionHistoryDTO sumissionHistoryDTO,	String userCode, String stage,Date[] searchDates,Long locationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		ResponseList<RequirementSubmissionHistory> response = new ResponseList<>();
		Query queryObj = null;
		List<Long> countlist = null;
		String stageQuery = null;
		if(stage.equals("SUBMISSION")){
			stageQuery = " rsh.toStage ='SUBMISSION' and ((rsh.fromStage = 'NEW') OR (rsh.fromStage ='request_sent'))";	
		}else if(stage.equalsIgnoreCase("SHORTLIST")){
			stageQuery = " rsh.toStage = 'SHORTLIST'";
		}else if(stage.equalsIgnoreCase("SUBMIT_TO_CLIENT")){
			stageQuery = " rsh.toStage = 'SUBMIT_TO_CLIENT'";
		}else if(stage.equalsIgnoreCase("INTERVIEW")){
			stageQuery = " (rsh.toStage = 'INTERVIEW')";
		}else if(stage.equalsIgnoreCase("OFFERMADE")){
			stageQuery = " (rsh.toStage = 'OFFERMADE') ";
		}else if(stage.equalsIgnoreCase("REJECTED")) {
			stageQuery = " rsh.toStage = 'REJECTED'";
		}else if(stage.equalsIgnoreCase("JOINED")) {
			stageQuery = " rsh.toStage = 'JOINED'";
		}else if(stage.equalsIgnoreCase("BACKOUT")) {
			stageQuery = " rsh.toStage = 'BACKOUT'";
		}else if(stage.equalsIgnoreCase("ONHOLD")) {
			stageQuery = " rsh.toStage = 'ONHOLD'";
		}else if(stage.equalsIgnoreCase("NORESPONSE")) {
			stageQuery = " rsh.toStage = 'NORESPONSE'";
		}
		
		
		StringBuilder query1 = new StringBuilder("");
		if(sumissionHistoryDTO.getSearchValue() != null && !"".equalsIgnoreCase(sumissionHistoryDTO.getSearchValue())) {
			 query1.append(" and (rsh.submissionId.candidate.email like '%" + sumissionHistoryDTO.getSearchValue() + "%' ")
				.append("OR rsh.submissionId.candidate.mobilePhone like '%" + sumissionHistoryDTO.getSearchValue() + "%' ")
			    .append("OR (concat(rsh.submissionId.candidate.firstName,rsh.submissionId.candidate.lastName) like '%" + sumissionHistoryDTO.getSearchValue().trim() + "%')" )
				.append("OR rsh.submissionId.submissionStatus like '%" + sumissionHistoryDTO.getSearchValue() + "%' ")
				.append("OR rsh.submissionId.requirement.jobTitle like '%" + sumissionHistoryDTO.getSearchValue() + "%' )");
			 
		}
		
		List<RequirementSubmissionHistory> submissionHistoryList = null;
		List<RequirementSubmissionHistory> submissionHistoryCountList = null;
		int totalCount = 0;
		
		String query = "from RequirementSubmissionHistory rsh left join fetch rsh.submissionId.candidate can left join fetch rsh.submissionId.requirement req left join fetch rsh.submissionId rs where rsh.masterUserCode =:userCode and "+stageQuery+" "+query1+" and (date(rsh.createdDate) BETWEEN :fromDate AND :toDate) and rs.orgLocations.id =:locationId";  
	    queryObj = session.createQuery(query);
	    queryObj.setParameter("userCode", userCode).setParameter("fromDate", searchDates[0]).setParameter("toDate", searchDates[1]).setParameter("locationId", locationId);
		
	    submissionHistoryCountList = queryObj.list();
	    if(submissionHistoryCountList != null && !submissionHistoryCountList.isEmpty()) {
	    	totalCount = submissionHistoryCountList.size();
	    }
	    
		queryObj.setFirstResult(sumissionHistoryDTO.getStart());
		queryObj.setMaxResults(sumissionHistoryDTO.getLength());

		submissionHistoryList = queryObj.list();
		
		submissionHistoryList.forEach(history->{
			history.setEmail(history.getSubmissionId().getCandidate().getEmail());
			history.setCandidateName(history.getSubmissionId().getCandidate().getFirstName());
			history.setJobTitle(history.getSubmissionId().getRequirement().getJobTitle());
			history.setMobilePhone(history.getSubmissionId().getCandidate().getMobilePhone());
			if (history.getSubmissionId().getFinalizedRate() !=null){
				history.setFinalizedRate(history.getSubmissionId().getFinalizedRate());	
			}
			StringBuilder sb = new StringBuilder();
			try {
				List<RequirementLocations> locationsList = requirementLocationsDao.getRequirementLocationsListByRequirementId(history.getSubmissionId().getRequirement().getId());
			for(RequirementLocations location :locationsList){
				sb = sb.append(location.getLocation()).append(",");
			}
			history.setOrgLocationName(sb.deleteCharAt(sb.length()-1).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(history.getSubmissionId().getRequirement().getClientId() != null) {
				if (history.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Client")) {
					if (history.getSubmissionId().getRequirement().getClientId().getResponderLocationId() != null) {
						history.setClientName(history.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrgLocationName());
					}
				}
				if (history.getSubmissionId().getRequirement().getClientId().getResponderType().equalsIgnoreCase("Vendor")) {
					if (history.getSubmissionId().getRequirement().getClientId().getLocationId() != null) {
						history.setClientName(history.getSubmissionId().getRequirement().getClientId().getLocationId().getOrgLocationName());
					}
				}
			}
			
			
		});
		
		
		response.setRecordsTotal(totalCount);
		response.setRecordsFiltered(totalCount);
		
		response.setData(submissionHistoryList);
		
		return response;
	}

	@Override
	@Transactional
	public int updateSourceUserCodeForSubmissionHistory() {
		int count = 0;
		try {
			System.out.println("-------------hi Start of updateSourceUserCodeForSubmissionHistory()-------------");
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select rsh.submissionId.id, rsh.createdBy from RequirementSubmissionHistory rsh group by rsh.submissionId.id order by rsh.id asc";
			List<Object[]> queryList = session.createQuery(query).list();
			if(queryList != null && !queryList.isEmpty()) {
				for(Object[] obj : queryList) {
					System.out.println("RSH ID : " + obj[0] + " MASTER USER CODE : " + obj[1]);
					RequirementSubmission requirementSubmission = session.get(RequirementSubmission.class, Long.valueOf(obj[0].toString()));
					
					String query2 = "from RequirementSubmissionHistory rsh where rsh.submissionId.id = " + obj[0];
					Query queryObj2 = session.createQuery(query2);
					List<RequirementSubmissionHistory> rshList = queryObj2.list();
					for(RequirementSubmissionHistory rsh : rshList) {
						rsh.setMasterUserCode(requirementSubmission.getCreatedBy());
						session.saveOrUpdate(rsh);
						count++;
					}
				}
			}
			System.out.println("-------------End of updateSourceUserCodeForSubmissionHistory()-------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<RecruiterJobsReportView> getSubmissionApplicantCountsForRecruiterJobreports(Long orgLocationId, User user,
			Date[] searchDates,Long requirementId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<RecruiterJobsReportView> recuiterReportViewList = new LinkedList<>();
		
		String	query = "from RecruiterJobsReportView rv where rv.userCode = :userCode and rv.createdDate BETWEEN :fromDate AND :toDate and rv.orgLocationId = :orgLocationId  and rv.requirementId =:requirementId";
		List<RecruiterJobsReportView> rrList = session.createQuery(query).setString("userCode", user.getUserCode()).setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setDate("fromDate", searchDates[0]).setDate("toDate",searchDates[1]).list();
		return rrList;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<OfferAcceptanceRateView> getOfferAcceptanceRateList(String userCode, long orgLocationId, String selectedClients, int month, int year, boolean compare) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from OfferAcceptanceRateView oarView where oarView.userCode=:userCode and oarView.orgLocationId=:orgLocationId";
			if(selectedClients != null && !selectedClients.isEmpty()) {
				query += " and oarView.orgRelationId in (" + selectedClients + ")";
			}
			if (month > 0 || year > 0) {
				Calendar c = Calendar.getInstance();
				if (month > 0) {
					c.set(Calendar.MONTH, month - 1);
					SimpleDateFormat YYYY_MM = new SimpleDateFormat("yyyy-MM");
					query += " and oarView.createdYearMonth='" + YYYY_MM.format(c.getTime()) + "'";
				} else if (year > 0) {
					c.set(Calendar.YEAR, year);
					SimpleDateFormat YYYY = new SimpleDateFormat("yyyy");
					query += " and oarView.createdYear='" + YYYY.format(c.getTime()) + "'";
				}
			}
			query += " order by oarView.createdYearMonthDate";
			Query queryObj = session.createQuery(query);
			queryObj.setString("userCode", userCode);
			queryObj.setLong("orgLocationId", orgLocationId);
			
			List<OfferAcceptanceRateView> offerAcceptanceRateViewList = queryObj.list();
			if(offerAcceptanceRateViewList != null && !offerAcceptanceRateViewList.isEmpty()) {
				return offerAcceptanceRateViewList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<OfferAcceptanceRateViewForVendor> getOfferAcceptanceRateListForVendor(List<Long> parentRequirementIdList, String masterUserCode, int month, int year, boolean compare) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from OfferAcceptanceRateViewForVendor oarView where oarView.requirementId in (:parentRequirementId) and oarView.userCode in (" + masterUserCode + ")";
			if (month > 0 || year > 0) {
				Calendar c = Calendar.getInstance();
				if (month > 0) {
					c.set(Calendar.MONTH, month - 1);
					SimpleDateFormat YYYY_MM = new SimpleDateFormat("yyyy-MM");
					query += " and oarView.createdYearMonth='" + YYYY_MM.format(c.getTime()) + "'";
				} else if (year > 0) {
					c.set(Calendar.YEAR, year);
					SimpleDateFormat YYYY = new SimpleDateFormat("yyyy");
					query += " and oarView.createdYear='" + YYYY.format(c.getTime()) + "'";
				}
			}
			query += " order by oarView.createdYearMonthDate";
			Query queryObj = session.createQuery(query);
			queryObj.setParameterList("parentRequirementId", parentRequirementIdList);
//			queryObj.setString("masterUserCode", masterUserCode);
			
			List<OfferAcceptanceRateViewForVendor> offerAcceptanceRateViewList = queryObj.list();
			if(offerAcceptanceRateViewList != null && !offerAcceptanceRateViewList.isEmpty()) {
				return offerAcceptanceRateViewList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
