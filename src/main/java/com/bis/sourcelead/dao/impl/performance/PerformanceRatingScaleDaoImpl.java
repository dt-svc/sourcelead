package com.bis.sourcelead.dao.impl.performance;

import java.text.MessageFormat;
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

import com.bis.sourcelead.dao.performance.PerformanceRatingScaleDao;
import com.bis.sourcelead.dto.performance.PerformanceRatingLevelDTO;
import com.bis.sourcelead.dto.performance.PerformanceRatingScaleDTO;
import com.bis.sourcelead.entity.performance.PerformanceRatingScale;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
public class PerformanceRatingScaleDaoImpl implements PerformanceRatingScaleDao {
	
	

	private static final Logger logger = LoggerFactory.getLogger(PerformanceRatingScaleDaoImpl.class);

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
	public PerformanceRatingScale savePerformanceRatingScale(PerformanceRatingScale performanceRatingScale)
			throws Exception {
		logger.debug("Adding PerformanceRatingScale");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceRatingScale != null) {
				session.saveOrUpdate(performanceRatingScale);
			}
			logger.debug("PerformanceRatingScale Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_RATING_SCALE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceRatingScale;
	}
	

	@Override
	public List<PerformanceRatingScaleDTO> getPerformanceRatingScaleListByOrgLocationId(Long orgLocationId,
			String dateFormat) throws Exception {
		

		List<PerformanceRatingScaleDTO> performanceRatingScaleDTO = null;
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
					+" RATING_SCALE.ID AS id, "
					+" RATING_SCALE.RATING_NAME AS ratingName, "           
					+" RATING_SCALE.SHORT_CODE AS shortCode, "
					+" RATING_SCALE.DESCRIPTION AS description, "
					+" RATING_SCALE.RATING_SCALE_VALUE AS ratingScaleValue, "
					+" RATING_SCALE.UUID AS uuid, "
					+" RATING_LEVEL.LEVEL_NAME AS levelName, "
					+" DATE_FORMAT(RATING_SCALE.CREATED_DATE, '"+locationDateFormat+"') AS date, "
					+" DATE_FORMAT(RATING_SCALE.CREATED_DATE, '%h:%i:%s %p') AS time "
					+" FROM PERFORMANCE_RATING_SCALE RATING_SCALE, PERFORMANCE_RATING_LEVEL RATING_LEVEL "
					+" WHERE RATING_SCALE.RATING_LEVEL_ID = RATING_LEVEL.ID AND "
					+" RATING_SCALE.ORG_LOCATION_ID = :orgLocationId  "
					+" ORDER BY RATING_SCALE.ID DESC";
			

			Query queryObj = session.createSQLQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceRatingScaleDTO.class));
			performanceRatingScaleDTO = queryObj.list();
			return performanceRatingScaleDTO;
		
		
	}

}
