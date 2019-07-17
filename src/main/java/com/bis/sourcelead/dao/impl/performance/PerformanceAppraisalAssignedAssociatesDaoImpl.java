package com.bis.sourcelead.dao.impl.performance;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.performance.PerformanceAppraisalAssignedAssociatesDao;
import com.bis.sourcelead.dao.performance.PerformanceAppraisalTemplateDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.AppraisalAssignedAssociatesDTO;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalAssignedAssociates;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class PerformanceAppraisalAssignedAssociatesDaoImpl implements PerformanceAppraisalAssignedAssociatesDao{

	private static final Logger logger = LoggerFactory.getLogger(PerformanceAppraisalAssignedAssociatesDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	PerformanceAppraisalTemplateDao PerformanceAppraisalTemplateDao;
	
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

	@Value("${jdbc.schema}")
	private String schema;

	@Override
	public PerformanceAppraisalAssignedAssociates savePerformanceAppraisalAssignedAssociates(
			PerformanceAppraisalAssignedAssociates performanceAppraisalAssignedAssociates) throws Exception {
		
		logger.debug("Adding savePerformanceAppraisalAssignedAssociates");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceAppraisalAssignedAssociates != null) {
				session.saveOrUpdate(performanceAppraisalAssignedAssociates);
			}
			logger.debug("savePerformanceAppraisalAssignedAssociates Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceAppraisalAssignedAssociates;
	}

	@Override
	public ResponseList<AppraisalAssignedAssociatesDTO> getAppraisalAssignedAssociatesList(
			AppraisalAssignedAssociatesDTO appraisalAssignedAssociatesDTO) throws Exception {
		
		PerformanceAppraisalTemplate appriasalTemplate = PerformanceAppraisalTemplateDao.getPerformanceAppraisalTemplateByUUID(appraisalAssignedAssociatesDTO.getAppraisalTemplateUUId());
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		Query queryObj = null;
		List<AppraisalAssignedAssociatesDTO> assignToList = null;
		
		List<BigInteger> countlist = null;
		Integer filterCount = 0;
		ResponseList<AppraisalAssignedAssociatesDTO> response = new ResponseList<>();
		String querySeachStr = "";
		
		StringBuilder query1 = new StringBuilder("");
		if(appraisalAssignedAssociatesDTO.getSearchValue() != null && !"".equalsIgnoreCase(appraisalAssignedAssociatesDTO.getSearchValue())) {
			 query1.append(" Q1.associateId like '%" + appraisalAssignedAssociatesDTO.getSearchValue() + "%' ")
				.append("OR Q1.associateName like '%" + appraisalAssignedAssociatesDTO.getSearchValue() + "%' ")
				.append("OR Q1.associateEmail like '%" + appraisalAssignedAssociatesDTO.getSearchValue() + "%' ")
				.append("OR Q1.reportingManager like '%" + appraisalAssignedAssociatesDTO.getSearchValue() + "%' ");
			 
		}
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(appraisalAssignedAssociatesDTO.getSortColumn()))
		{
			sortingQry = " order by Q1."+ appraisalAssignedAssociatesDTO.getSortColumn() +" "+appraisalAssignedAssociatesDTO.getSortOrder();
		}
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			querySeachStr = "where "+"("+query1.toString()+")";
		}
		
		try {
			

			query = "SELECT Q1.* " + 
			 		 "FROM (" + 
					 "SELECT " + 
					"ASSIGNED.UUID AS uuid, " + 
					"CAND.GENERATED_ID AS associateId, " + 
					"U.FILE_URL AS userImgUrl, " + 
					"CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS associateName, " +
					"CONCAT (U.FIRST_NAME,' ',IFNULL(U.LAST_NAME,'')) AS reportingManager, " +
					"DATEDIFF(CURRENT_DATE(),'"+appriasalTemplate.getAppraisalStartDate()+"') AS daysDifference, " + 
					"R.DISPLAY_NAME AS associateRole, " + 
					 "(SELECT " + 
					 "user.EMAIL " + 
					 "FROM " + 
					 "USER user  " + 
					 "WHERE " + 
					 "user.ID = ASSIGNED.USER_ID) AS associateEmail, " + 
					 
					 "(SELECT " + 
					 "user.ID " + 
					 "FROM " + 
					 "USER user  " + 
					 "WHERE " + 
					 "user.ID = ASSIGNED.USER_ID) AS assignedUserId, " + 
					 
					 "(SELECT " + 
					 "userrole.ROLE_ID " + 
					 "FROM " + 
					 "USER_ROLE userrole  " + 
					 "WHERE " + 
					 "userrole.USER_ID = ASSIGNED.USER_ID AND userrole.ORG_LOCATIONS_ID = :orgLocationId) AS assignedUserRoleId " +
							             
					"FROM " + 
					"USER_ROLE UR ," + 
					"CANDIDATE CAND, " + 
					"USER U ," + 
					"PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, " + 
					"ROLES R " + 
					"WHERE ASSIGNED.USER_ID = CAND.CONSULTANT_USER_ID AND ASSIGNED.USER_ID = UR.USER_ID " + 
					"AND UR.REPORTING_MANAGER_ID = U.ID AND UR.ROLE_ID = R.ID " + 
					"AND ASSIGNED.APPRAISAL_TEMPLATE_ID = :appriasalTemplateId GROUP BY UR.USER_ID )Q1 "+querySeachStr+sortingQry;
			
			String  countQuery = "SELECT COUNT(*) AS TOTAL" + 
					" FROM" + 
					"(" + query +
					")Q2;" ;
		
		
		queryObj = session.createSQLQuery(query);
		queryObj.setParameter("appriasalTemplateId", appriasalTemplate.getId());
		queryObj.setParameter("orgLocationId", appraisalAssignedAssociatesDTO.getOrgLocationId());
		
		queryObj.setResultTransformer(Transformers.aliasToBean(AppraisalAssignedAssociatesDTO.class));
		queryObj.setFirstResult(appraisalAssignedAssociatesDTO.getStart());
		queryObj.setMaxResults(appraisalAssignedAssociatesDTO.getLength());
		assignToList = queryObj.list();
		
		countlist = session.createSQLQuery(countQuery).setLong("appriasalTemplateId", appriasalTemplate.getId()).setLong("orgLocationId", appraisalAssignedAssociatesDTO.getOrgLocationId()).list();
		filterCount = countlist.iterator().next().intValue();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	int totalCount = 0;

		
		if (appraisalAssignedAssociatesDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		
		response.setData(assignToList);
		return response;
	}

	@Override
	public void deleteAppraisalAssignedAssociate(
			PerformanceAppraisalAssignedAssociates performanceAppraisalAssignedAssociates) throws Exception {
		
		logger.debug("Adding deleteAppraisalAssignedAssociate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceAppraisalAssignedAssociates != null) {
				session.delete(performanceAppraisalAssignedAssociates);
			}
			logger.debug("deleteAppraisalAssignedAssociate Deleted Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}

	@Override
	public PerformanceAppraisalAssignedAssociates getPerformanceAppraisalAssignedAssociatesByUUID(String uuid)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceAppraisalAssignedAssociates> assignedAssociates = new ArrayList<PerformanceAppraisalAssignedAssociates>();
		logger.debug("getPerformanceAppraisalAssignedAssociatesByUUID  start");
		assignedAssociates = session.createQuery("from PerformanceAppraisalAssignedAssociates assigned where assigned.uuid = :uuid " ).setString( "uuid", uuid).list();
		logger.debug("getPerformanceAppraisalAssignedAssociatesByUUID  end");
		if (assignedAssociates.size() > 0) {
			return assignedAssociates.get(0);
		} else {
			return null;
		}
	 }

	@Override
	public boolean isGoalsConfiguredForAssignedPerformanceApprisal(String uuid) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = true;
		String query =  "SELECT "
				+" TEMPLATE.IS_GOALS_CONFIGURED "           
				+" FROM PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, "
				+" PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE "
				+" WHERE ASSIGNED.APPRAISAL_TEMPLATE_ID = TEMPLATE.ID   "
				+" AND ASSIGNED.UUID = :assignedUUID ";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("assignedUUID", uuid);
		String isGoalsConfigured = (String) queryObj.uniqueResult();
		if(isGoalsConfigured == null || isGoalsConfigured.equalsIgnoreCase("N")) {
			flag = false;
		}
		
		return flag;
	}

	@Override
	public Long getAppraisalAssignedUserByUUID(String uuid) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query =  "SELECT "
				+" ASSIGNED.USER_ID "           
				+" FROM PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED "
				+" WHERE ASSIGNED.UUID = :assignedUUID ";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("assignedUUID", uuid);
		BigInteger userId = (BigInteger) queryObj.uniqueResult();
		
		return userId.longValue();
	
	}

	@Override
	public List<PerformanceAppraisalAssignedAssociates> getPerformanceAppraisalAssignedAssociatesListByTemplateId(
			Long templateId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

	    String query = "from PerformanceAppraisalAssignedAssociates assigned where assigned.appraisalTemplateId = :templateId  ";

	    List<PerformanceAppraisalAssignedAssociates> assignedList = session.createQuery(query.toString()).setLong( "templateId", templateId).list();
        return assignedList;
	}

	@Override
	public void deletePerformanceAppraisalAssignedAssociates(
			PerformanceAppraisalAssignedAssociates performanceAppraisalAssignedAssociates) throws Exception {
		
		logger.debug("Adding deletePerformanceAppraisalAssignedAssociates");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceAppraisalAssignedAssociates != null) {
				session.delete(performanceAppraisalAssignedAssociates);
			}
			logger.debug("deletePerformanceAppraisalAssignedAssociates Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}
}
