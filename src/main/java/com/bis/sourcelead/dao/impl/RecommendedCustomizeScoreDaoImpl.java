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

import com.bis.sourcelead.dao.RecommendedCustomizeScoreDao;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Tasks;

@Repository
public class RecommendedCustomizeScoreDaoImpl implements RecommendedCustomizeScoreDao{
	
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
    
	
	private static final Logger logger = LoggerFactory.getLogger(RecommendedCustomizeScoreDaoImpl.class);


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RecommendedCustomizeScore saveCustomizeScore(RecommendedCustomizeScore recommendedCustomizeScore) throws Exception {
		logger.debug("In RecommendedCustomizeScore Save method");
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(recommendedCustomizeScore);
		return recommendedCustomizeScore;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RecommendedCustomizeScore getRecommendedCustomizeScoreByRequirementIdAndOrgLocationId(Long requirementId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RecommendedCustomizeScore rcs  where rcs.reqirementId.id =:requirementId and rcs.orgLocationId =:orgLocationId ";
		List<RecommendedCustomizeScore> recommendedCustomizeScoreLIst = session.createQuery(query).setLong("requirementId", requirementId).setLong("orgLocationId", orgLocationId).list();
		
		RecommendedCustomizeScore recommendedCustomizeScore = null;
		if(recommendedCustomizeScoreLIst != null && !recommendedCustomizeScoreLIst.isEmpty())
		{
			recommendedCustomizeScore = (RecommendedCustomizeScore) recommendedCustomizeScoreLIst.iterator().next();
		}
		return recommendedCustomizeScore;
		
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RecommendedCustomizeScore getRecommendedCustomizeScoreByRequirementId(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query ="from RecommendedCustomizeScore rcs where rcs.reqirementId.id = :requirementId";
		List<RecommendedCustomizeScore> recommendedCustomizeScoreList = session.createQuery(query).setLong("requirementId",requirementId).list();
				
		RecommendedCustomizeScore customizeScore = null;
		if (null != recommendedCustomizeScoreList && recommendedCustomizeScoreList.size() > 0) {
			customizeScore = (RecommendedCustomizeScore) recommendedCustomizeScoreList.iterator().next();
		}
		return customizeScore;
	}


}

