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

import com.bis.sourcelead.dao.performance.PerformanceAppraisalTemplateDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalAssignToListDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalPeersDTO;
import com.bis.sourcelead.entity.SubmissionRequirementListView;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class PerformanceAppraisalTemplateDaoImpl implements PerformanceAppraisalTemplateDao{

	private static final Logger logger = LoggerFactory.getLogger(PerformanceAppraisalTemplateDaoImpl.class);

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

	@Value("${jdbc.schema}")
	private String schema;

	@Override
	public PerformanceAppraisalTemplate savePerformanceAppraisalTemplate(
			PerformanceAppraisalTemplate performanceAppraisalTemplate) throws Exception {
		logger.debug("Adding PerformanceAppraisalTemplate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceAppraisalTemplate != null) {
				session.saveOrUpdate(performanceAppraisalTemplate);
			}
			logger.debug("PerformanceAppraisalTemplate Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceAppraisalTemplate;
	}

	@Override
	public List<PerformanceAppraisalTemplate> getPerformanceAppraisalTemplateList(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PerformanceAppraisalTemplate template where template.orgLocationId.id =:orgLocationId and template.status ='1' ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}

	@Override
	public PerformanceAppraisalTemplate getPerformanceAppraisalTemplateById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceAppraisalTemplate> performanceAppraisalTemplate = new ArrayList<PerformanceAppraisalTemplate>();
		logger.debug("getPerformanceAppraisalTemplateById  start");
		performanceAppraisalTemplate = session.createQuery("from PerformanceAppraisalTemplate template where template.id = :id " ).setLong( "id", id).list();
		logger.debug("getPerformanceAppraisalTemplateById  end");
		if (performanceAppraisalTemplate.size() > 0) {
			return performanceAppraisalTemplate.get(0);
		} else {
			return null;
		}
	 }
	
	@Override
	public PerformanceAppraisalTemplate getPerformanceAppraisalTemplateByUUID(String uuid) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceAppraisalTemplate> performanceAppraisalTemplate = new ArrayList<PerformanceAppraisalTemplate>();
		logger.debug("getPerformanceAppraisalTemplateByUUID  start");
		performanceAppraisalTemplate = session.createQuery("from PerformanceAppraisalTemplate template where template.uuid = :uuid " ).setString( "uuid", uuid).list();
		logger.debug("getPerformanceAppraisalTemplateByUUID  end");
		if (performanceAppraisalTemplate.size() > 0) {
			return performanceAppraisalTemplate.get(0);
		} else {
			return null;
		}
	 }

	@Override
	public ResponseList<PerformanceAppraisalAssignToListDTO> getPerformanceAppraisalAssignToList(
			PerformanceAppraisalAssignToListDTO performanceAppraisalAssignToListDTO, Long orgLocationId)
			throws Exception {
		
		PerformanceAppraisalTemplate appriasalTemplate = getPerformanceAppraisalTemplateByUUID(performanceAppraisalAssignToListDTO.getAppraisalTemplateUUId());
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		Query queryObj = null;
		List<PerformanceAppraisalAssignToListDTO> assignToList = null;
		String departmentQuery = "";
		String rolesQuery = "";
		String employmentType = "";
		
		List<BigInteger> countlist = null;
		Integer filterCount = 0;
		ResponseList<PerformanceAppraisalAssignToListDTO> response = new ResponseList<>();
		String querySeachStr = "";
		
		StringBuilder query1 = new StringBuilder("");
		if(performanceAppraisalAssignToListDTO.getSearchValue() != null && !"".equalsIgnoreCase(performanceAppraisalAssignToListDTO.getSearchValue())) {
			 query1.append(" Q1.associateId like '%" + performanceAppraisalAssignToListDTO.getSearchValue() + "%' ")
				.append("OR Q1.associateName like '%" + performanceAppraisalAssignToListDTO.getSearchValue() + "%' ")
				.append("OR Q1.associateEmail like '%" + performanceAppraisalAssignToListDTO.getSearchValue() + "%' ")
				.append("OR Q1.reportingManager like '%" + performanceAppraisalAssignToListDTO.getSearchValue() + "%' ");
			 
		}
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(performanceAppraisalAssignToListDTO.getSortColumn()))
		{
			sortingQry = " order by Q1."+ performanceAppraisalAssignToListDTO.getSortColumn() +" "+performanceAppraisalAssignToListDTO.getSortOrder();
		}
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			querySeachStr = "where "+"("+query1.toString()+")";
		}
		
		if(StringUtils.isNotBlank(appriasalTemplate.getDepartment())) {
			String departmentIds [] = appriasalTemplate.getDepartment().split(",");
			String departmentIdsStr = "'" + StringUtils.join(departmentIds,"','") + "'";
			departmentQuery = " AND EMP.DEPARTMENT_ID IN ("+departmentIdsStr+") ";
		}
		
		if(StringUtils.isNotBlank(appriasalTemplate.getRoles())) {
			String rolesIds [] = appriasalTemplate.getRoles().split(",");
			String rolesIdsStr = "'" + StringUtils.join(rolesIds,"','") + "'";
			rolesQuery = " AND UR.ROLE_ID IN ("+rolesIdsStr+") ";
		}
		
		if(StringUtils.isNotBlank(appriasalTemplate.getEmployeeType())) {
			String empType [] = appriasalTemplate.getEmployeeType().split(",");
			String empTypeStr = "'" + StringUtils.join(empType,"','") + "'";
			employmentType = " AND CAND.DESIGNATION_TYPE IN ("+empTypeStr+") ";
		}
		
		try {
			
			query = "SELECT Q1.* " + 
			 		 "FROM (" + 
					 "SELECT " + 
					"CAND.GENERATED_ID AS associateId, " + 
					"CAND.CONSULTANT_USER_ID AS userId, " + 
					"CONCAT (CAND.FIRST_NAME,' ',IFNULL(CAND.LAST_NAME,'')) AS associateName, " +
					"CAND.EMAIL AS associateEmail, " + 
					"CONCAT (U.FIRST_NAME,' ',IFNULL(U.LAST_NAME,'')) AS reportingManager, " +
					"UR.ROLE_ID AS userRoleId, " + 
					"R.DISPLAY_NAME AS associateRole, " + 
					 "(SELECT " + 
					 "PEERS.PEER_COUNT " + 
					 "FROM " + 
					 "PERFORMANCE_APPRAISAL_PEERS PEERS  " + 
					 "WHERE " + 
					 "PEERS.ORG_LOCATION_ID = :orgLocationId AND PEERS.USER_ID = CAND.CONSULTANT_USER_ID) AS peersCount " + 
							             
					"FROM " + 
					"USER_ROLE UR ," + 
					"USER U ," + 
					"CANDIDATE CAND, " + 
					"EMPLOYEE EMP, " + 
					"ORG_LOCATIONS OL, " + 
					"ROLES R " + 
					"WHERE UR.REPORTING_MANAGER_ID = U.ID AND UR.ROLE_ID = R.ID " + 
					"AND CAND.CONSULTANT_USER_ID = UR.USER_ID AND CAND.ORG_LOCATION_ID = OL.ID AND EMP.USER_ID = UR.USER_ID " + departmentQuery + rolesQuery + employmentType+
					"AND CAND.ORG_LOCATION_ID = :orgLocationId AND CAND.STATUS = '1' AND UR.USER_STATE = 'ACTIVE' GROUP BY UR.ID )Q1 "+querySeachStr+sortingQry;
			
			
			  String  countQuery = "SELECT COUNT(*) AS TOTAL" + 
						" FROM" + 
						"(" + query +
						")Q2;" ;
			
			
			queryObj = session.createSQLQuery(query);
			queryObj.setParameter("orgLocationId", orgLocationId);
			queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalAssignToListDTO.class));
			queryObj.setFirstResult(performanceAppraisalAssignToListDTO.getStart());
			queryObj.setMaxResults(performanceAppraisalAssignToListDTO.getLength());
			assignToList = queryObj.list();
			
			countlist = session.createSQLQuery(countQuery).setLong("orgLocationId", orgLocationId).list();
			filterCount = countlist.iterator().next().intValue();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		int totalCount = 0;

		
		if (performanceAppraisalAssignToListDTO != null) {
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
	public ResponseList<PerformanceAppraisalPeersDTO> getPerformancePeersList(
			PerformanceAppraisalPeersDTO performanceAppraisalPeersDTO, Long orgLocationId) throws Exception {
		
		PerformanceAppraisalTemplate appriasalTemplate = getPerformanceAppraisalTemplateByUUID(performanceAppraisalPeersDTO.getAppraisalTemplateUUId());
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		Query queryObj = null;
		List<PerformanceAppraisalPeersDTO> peersList = null;
		String departmentQuery = "";
		
		List<BigInteger> countlist = null;
		Integer filterCount = 0;
		ResponseList<PerformanceAppraisalPeersDTO> response = new ResponseList<>();
		String querySeachStr = "";
		
		StringBuilder query1 = new StringBuilder("");
		if(performanceAppraisalPeersDTO.getSearchValue() != null && !"".equalsIgnoreCase(performanceAppraisalPeersDTO.getSearchValue())) {
			 query1.append(" Q1.associateId like '%" + performanceAppraisalPeersDTO.getSearchValue() + "%' ")
				.append("OR Q1.associateName like '%" + performanceAppraisalPeersDTO.getSearchValue() + "%' ")
				.append("OR Q1.associateEmail like '%" + performanceAppraisalPeersDTO.getSearchValue() + "%' ");
			 
		}
		
		if(StringUtils.isNotBlank(appriasalTemplate.getDepartment())) {
			String departmentIds [] = appriasalTemplate.getDepartment().split(",");
			String departmentIdsStr = "'" + StringUtils.join(departmentIds,"','") + "'";
			departmentQuery = " AND EMP.DEPARTMENT_ID IN ("+departmentIdsStr+") ";
		}
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(performanceAppraisalPeersDTO.getSortColumn()))
		{
			sortingQry = " order by Q1."+ performanceAppraisalPeersDTO.getSortColumn() +" "+performanceAppraisalPeersDTO.getSortOrder();
		}
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			querySeachStr = "where "+"("+query1.toString()+")";
		}
		
		
		try {
			
			query = "SELECT Q1.* " + 
			 		 "FROM (" + 
					 "SELECT " + 
					"CAND.GENERATED_ID AS associateId, " + 
					"UR.USER_ID AS peerUserId, " + 
					"CONCAT (U.FIRST_NAME,' ',IFNULL(U.LAST_NAME,'')) AS associateName, " +
					"U.FILE_URL AS userImgUrl, " + 
					"U.EMAIL AS associateEmail " + 
							             
					"FROM " + 
					"USER_ROLE UR ," + 
					"USER U ," + 
					"CANDIDATE CAND, " + 
					"EMPLOYEE EMP, " + 
					"ORG_LOCATIONS OL " + 
					"WHERE UR.USER_ID = U.ID  " + 
					"AND CAND.CONSULTANT_USER_ID = UR.USER_ID AND CAND.ORG_LOCATION_ID = OL.ID  " + 
					"AND UR.ORG_LOCATIONS_ID = :orgLocationId AND UR.ROLE_ID = :roleId " + departmentQuery+
					"AND UR.USER_ID != :userId AND UR.USER_STATE = 'ACTIVE'  GROUP BY UR.ID )Q1 "+querySeachStr+sortingQry;

			
			
			  String  countQuery = "SELECT COUNT(*) AS TOTAL" + 
						" FROM" + 
						"(" + query +
						")Q2;" ;
			
			
			queryObj = session.createSQLQuery(query);
			queryObj.setParameter("orgLocationId", orgLocationId);
			queryObj.setParameter("userId", performanceAppraisalPeersDTO.getUserId());
			queryObj.setParameter("roleId", performanceAppraisalPeersDTO.getUserRoleId());
			queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalPeersDTO.class));
			queryObj.setFirstResult(performanceAppraisalPeersDTO.getStart());
			queryObj.setMaxResults(performanceAppraisalPeersDTO.getLength());
			peersList = queryObj.list();
			
			countlist = session.createSQLQuery(countQuery).setLong("orgLocationId", orgLocationId).setBigInteger("userId", performanceAppraisalPeersDTO.getUserId()).setLong("roleId", performanceAppraisalPeersDTO.getUserRoleId()).list();
			filterCount = countlist.iterator().next().intValue();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		int totalCount = 0;

		
		if (performanceAppraisalPeersDTO != null) {
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
		
		response.setData(peersList);
		return response;
		
	}

	
}
