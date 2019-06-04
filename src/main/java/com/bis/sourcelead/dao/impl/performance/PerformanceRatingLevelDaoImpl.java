package com.bis.sourcelead.dao.impl.performance;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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

import com.bis.sourcelead.dao.performance.PerformanceRatingLevelDao;
import com.bis.sourcelead.dto.UploadXlsHistoryDTO;
import com.bis.sourcelead.dto.performance.PerformanceRatingLevelDTO;
import com.bis.sourcelead.entity.performance.PerformanceCompetencies;
import com.bis.sourcelead.entity.performance.PerformanceRatingLevel;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
public class PerformanceRatingLevelDaoImpl implements PerformanceRatingLevelDao {
	

	private static final Logger logger = LoggerFactory.getLogger(PerformanceRatingLevelDaoImpl.class);

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
	public PerformanceRatingLevel savePerformanceRatingLevel(PerformanceRatingLevel PerformanceRatingLevel)
			throws Exception {
		logger.debug("Adding PerformanceRatingScale");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (PerformanceRatingLevel != null) {
				session.saveOrUpdate(PerformanceRatingLevel);
			}
			logger.debug("PerformanceRatingScale Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return PerformanceRatingLevel;
	}

	@Override
	public List<PerformanceRatingLevelDTO> getPerformanceRatingLevelsListByOrgLocationId(Long orgLocationId,
			String dateFormat) throws Exception {
		
		
		List<PerformanceRatingLevelDTO> performanceRatingLevelDTO = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		
		 String locationDateFormat = null;
		 if(dateFormat.equalsIgnoreCase("yyyy-MM-dd")){
			 locationDateFormat = "%Y-%m-%d";
		  }if(dateFormat.equalsIgnoreCase("dd-MM-yyyy")){
			  locationDateFormat = "%d-%m-%Y";
		  }if(dateFormat.equalsIgnoreCase("MM-dd-yyyy")){
			  locationDateFormat = "%m-%d-%Y";
		  }if(dateFormat.equalsIgnoreCase("yyyy-dd-MM")){
			  locationDateFormat = "%Y-%d-%m";
		  }
		
		query =  "SELECT "
				+" RATING_LEVEL.ID AS id, "
				+" RATING_LEVEL.LEVEL_NAME AS name, "
				+" RATING_LEVEL.DESCRIPTION AS description, "
				+" RATING_LEVEL.UUID AS uuid, "
				+" DATE_FORMAT(RATING_LEVEL.CREATED_DATE, '"+locationDateFormat+"') AS date, "
				+" DATE_FORMAT(RATING_LEVEL.CREATED_DATE, '%h:%i:%s %p') AS time "
				+" FROM PERFORMANCE_RATING_LEVEL RATING_LEVEL "
				+" WHERE "
				+" RATING_LEVEL.ORG_LOCATION_ID = :orgLocationId AND RATING_LEVEL.STATUS = '1' "
				+" ORDER BY RATING_LEVEL.ID DESC";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceRatingLevelDTO.class));
		performanceRatingLevelDTO = queryObj.list();
		return performanceRatingLevelDTO;
	}

	@Override
	public boolean isRatingLevelNameExisted(Long orgLocationId, String ratingLevelName) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		boolean flag = false;
		query =  "SELECT "
				+" LOWER(RATING_LEVEL.LEVEL_NAME) AS name "
				+" FROM PERFORMANCE_RATING_LEVEL RATING_LEVEL "
				+" WHERE "
				+" RATING_LEVEL.ORG_LOCATION_ID = :orgLocationId AND RATING_LEVEL.STATUS = '1' ";
		
		List<String> levelNamesList = session.createSQLQuery(query).setLong("orgLocationId", orgLocationId).list();
		    if(levelNamesList != null &&  !levelNamesList.isEmpty() ) {
			  if(levelNamesList.contains(ratingLevelName.toLowerCase()) == true) {
				  flag = true;
			  }
		  }
		
		return flag;
	}

	@Override
	public PerformanceRatingLevelDTO getPerformanceRatingLevelDetailsById(Long ratingLevelId) throws Exception {
		
		List<PerformanceRatingLevelDTO> performanceRatingLevelDTO = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		
		query =  "SELECT "
				+" RATING_LEVEL.LEVEL_NAME AS name, "
				+" RATING_LEVEL.DESCRIPTION AS description, "
				+" RATING_LEVEL.UUID AS uuid "
				+" FROM PERFORMANCE_RATING_LEVEL RATING_LEVEL "
				+" WHERE "
				+" RATING_LEVEL.ID = :ratingLevelId  ";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("ratingLevelId", ratingLevelId);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceRatingLevelDTO.class));
		performanceRatingLevelDTO = queryObj.list();
		return performanceRatingLevelDTO.get(0);
	
	}

	@Override
	public PerformanceRatingLevel getPerformanceRatingLevelById(Long ratingLevelId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceRatingLevel> performanceRatingLevel = new ArrayList<PerformanceRatingLevel>();
		logger.debug("getPerformanceRatingLevelById  start");
		performanceRatingLevel = session.createQuery("from PerformanceRatingLevel ratingLevel where ratingLevel.id = :ratingLevelId " ).setLong( "ratingLevelId", ratingLevelId).list();
		logger.debug("getPerformanceRatingLevelById  end");
		if (performanceRatingLevel.size() > 0) {
			return performanceRatingLevel.get(0);
		} else {
			return null;
		}
	}

	
}
