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

import com.bis.sourcelead.dao.performance.PerformanceGoalsCommentsDao;
import com.bis.sourcelead.dto.XlsBulkUploadHistroyDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsCommentsDTO;
import com.bis.sourcelead.entity.performance.PerformanceGoalsComments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
public class PerformanceGoalsCommentsDaoImpl implements PerformanceGoalsCommentsDao{
	
	private static final Logger logger = LoggerFactory.getLogger(PerformanceGoalsCommentsDaoImpl.class);

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
	public PerformanceGoalsComments savePerformanceGoalsComments(PerformanceGoalsComments performanceGoalsComments)
			throws Exception {
		

		logger.debug("Adding savePerformanceGoalsComments");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceGoalsComments != null) {
				session.saveOrUpdate(performanceGoalsComments);
			}
			logger.debug("savePerformanceGoalsComments Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_GOALS_COMMENTS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceGoalsComments;
		
		
	}

	@Override
	public List<PerformanceGoalsCommentsDTO> getPerformanceGoalsCommentsList(Long goalId, String dateFormat) throws Exception {
		
		List<PerformanceGoalsCommentsDTO> commentsDTOList = null;
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
				+" COMMENTS.COMMENT AS goalsComment, "
				+" DATE_FORMAT(COMMENTS.CREATED_DATE, '"+locationDateFormat+"') AS createdDate, "
				+" DATE_FORMAT(COMMENTS.CREATED_DATE, '%h:%i:%s %p') AS createdTime, "
				+" UR.FILE_URL AS imgUrl, "
				+" CONCAT (UR.FIRST_NAME,' ',IFNULL(UR.MIDDLE_NAME,''),' ',IFNULL(UR.LAST_NAME,'')) AS createdUserName "
				+" FROM PERFORMANCE_GOALS_COMMENTS COMMENTS, "
				+" USER UR "
				+" WHERE "
				+" COMMENTS.CREATED_USER_ID = UR.ID AND COMMENTS.GOAL_ID = :goalId "
				+" ORDER BY COMMENTS.ID DESC";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("goalId", goalId);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceGoalsCommentsDTO.class));
		commentsDTOList = queryObj.list();
		return commentsDTOList;
	}

}
