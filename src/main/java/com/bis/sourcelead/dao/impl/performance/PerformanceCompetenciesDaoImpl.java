package com.bis.sourcelead.dao.impl.performance;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.bis.sourcelead.dao.performance.PerformanceCompetenciesDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceCompetenciesDTO;
import com.bis.sourcelead.entity.performance.PerformanceCompetencies;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
public class PerformanceCompetenciesDaoImpl implements PerformanceCompetenciesDao{
	
	private static final Logger logger = LoggerFactory.getLogger(PerformanceCompetenciesDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
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
	public PerformanceCompetencies savePerformanceCompetencies(PerformanceCompetencies performanceCompetencies)
			throws Exception {
		logger.debug("Adding PerformanceCompetencies");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceCompetencies != null) {
				session.saveOrUpdate(performanceCompetencies);
			}
			logger.debug("PerformanceCompetencies Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceCompetencies;
	}

	@Override
	public List<PerformanceCompetenciesDTO> getPerformanceCompetenciesListByOrgLocationId(Long orgLocationId)
			throws Exception {
		List<PerformanceCompetenciesDTO> competenciesDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT competencies.UUID AS uuid, "
				+ " competencies.ID AS id, "
				+ " competencies.COMPETENCIES_NAME AS name, "
				+ " competencies.DESCRIPTION AS description "
				+ " FROM PERFORMANCE_COMPETENCIES competencies WHERE competencies.ORG_LOCATION_ID = :orgLocationId AND competencies.STATUS = '1' ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceCompetenciesDTO.class));
		competenciesDTOList = queryObj.list();
		return competenciesDTOList;
	}

	
	@Override
	public ResponseList<PerformanceCompetenciesDTO> getPerformanceCompetenciesList(
			PerformanceCompetenciesDTO performanceCompetenciesDTO, String dateFormat, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> countlist = null;
		Integer filterCount = 0;
		ResponseList<PerformanceCompetenciesDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<PerformanceCompetencies> competenciesList = null;
		 java.sql.Date sqlDate = null;
		if(performanceCompetenciesDTO.getSearchValue() !=null && !"".equalsIgnoreCase(performanceCompetenciesDTO.getSearchValue())) {
		Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
		   Matcher matcher = pattern.matcher(performanceCompetenciesDTO.getSearchValue());
		    if(matcher.matches() == true) {
						if(performanceCompetenciesDTO.getSearchValue().contains("/")||(performanceCompetenciesDTO.getSearchValue().contains("-"))) {
							String replacedValue = performanceCompetenciesDTO.getSearchValue().replaceAll("/", "-");
								SimpleDateFormat format = new SimpleDateFormat(dateFormat);
						        java.util.Date utilDate = format.parse(replacedValue);
						        sqlDate = new java.sql.Date(utilDate.getTime());
						}
		    }
		}
		    
		    StringBuilder query1 = new StringBuilder("");
			if(performanceCompetenciesDTO.getSearchValue() !=null && !"".equalsIgnoreCase(performanceCompetenciesDTO.getSearchValue())) {
			 query1.append(" competencies.competenciesName like '" + performanceCompetenciesDTO.getSearchValue().replaceAll("[\\s]+", "").trim() + "%'  ");
			 
			     if(sqlDate != null) {
			    	query1.append("OR competencies.createdDate like '%" + sqlDate + "%' ");
				 }
						
			}
			
			String sortingQry = "";
			if(StringUtils.isNotBlank(performanceCompetenciesDTO.getSortColumn())) {
					sortingQry = " order by competencies."+ performanceCompetenciesDTO.getSortColumn() +" "+performanceCompetenciesDTO.getSortOrder();
			}
			
			query = "from PerformanceCompetencies competencies where competencies.orgLocationId.id = :orgLocationId and competencies.status = '1' ";
			if(query1.toString() != null && !query1.toString().isEmpty()){
				 queryObj = session.createQuery(query + "and "+"("+query1.toString()+")"+" "+sortingQry+" ");
			}else{
				 queryObj = session.createQuery(query + query1.toString()+" "+sortingQry+" ");
			}
			
			
			
			queryObj.setFirstResult(performanceCompetenciesDTO.getStart());
			queryObj.setMaxResults(performanceCompetenciesDTO.getLength());
			queryObj.setLong("orgLocationId",orgLocationId).list();
			competenciesList = queryObj.list();
			if(query1.toString() != null && !query1.toString().isEmpty()){
				countlist = session.createQuery("select count(*) " + query + "and "+"("+query1.toString()+") "+sortingQry+" ").setLong("orgLocationId", orgLocationId).list();
				filterCount = countlist.iterator().next().intValue();
			}else {
				countlist = session.createQuery("select count(*) " + query +sortingQry).setLong("orgLocationId", orgLocationId).list();
				filterCount = countlist.iterator().next().intValue();
			}
			
			
			int totalCount = 0;

			
			if (performanceCompetenciesDTO != null) {
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
			
			 List<PerformanceCompetenciesDTO> competenciesDTOList = new ArrayList<PerformanceCompetenciesDTO>();
			 
			if(competenciesList != null && !competenciesList.isEmpty()) {
				competenciesList.forEach(competencies -> {
					PerformanceCompetenciesDTO competenciesDTO = new PerformanceCompetenciesDTO();
					competenciesDTO.setCompetenceId(competencies.getId());
					competenciesDTO.setName(competencies.getCompetenciesName());
					competenciesDTO.setDescription(competencies.getDescription());
					competenciesDTO.setUuid(competencies.getUuid());
					competenciesDTO.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(dateFormat,competencies.getCreatedDate()));
					competenciesDTOList.add(competenciesDTO);
				});
				
			}
			
			response.setData(competenciesDTOList);
			return response;
		
		
	}

	@Override
	public PerformanceCompetencies getPerformanceCompetenciesById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceCompetencies> performanceCompetencies = new ArrayList<PerformanceCompetencies>();
		logger.debug("getPerformanceCompetenciesDTOById  start");
		performanceCompetencies = session.createQuery("from PerformanceCompetencies competencies where competencies.id = :id " ).setLong( "id", id).list();
		logger.debug("getPerformanceCompetenciesDTOById  end");
		if (performanceCompetencies.size() > 0) {
			return performanceCompetencies.get(0);
		} else {
			return null;
		}
	}

	@Override
	public boolean isCompetenceNameExisted(Long orgLocationId, String competenceName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceCompetencies> performanceCompetencies = new ArrayList<PerformanceCompetencies>();
		logger.debug("getPerformanceCompetenciesDTOById  start");
		performanceCompetencies = session.createQuery("from PerformanceCompetencies competencies where competencies.orgLocationId.id = :orgLocationId and "
				+ " competencies.competenciesName = :competenceName and competencies.status = '1' " ).setLong("orgLocationId", orgLocationId).setString("competenceName", competenceName).list();
		logger.debug("getPerformanceCompetenciesDTOById  end");
		if (performanceCompetencies.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
