package com.bis.sourcelead.dao.impl.performance;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

import com.bis.sourcelead.dao.performance.PerformanceAppraisalPeersDao;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalPeersDTO;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalPeers;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class PerformanceAppraisalPeersDaoImpl implements PerformanceAppraisalPeersDao{

	private static final Logger logger = LoggerFactory.getLogger(PerformanceAppraisalPeersDaoImpl.class);

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
	public PerformanceAppraisalPeers savePerformanceAppraisalPeers(PerformanceAppraisalPeers performanceAppraisalPeers)
			throws Exception {
		logger.debug("Adding savePerformanceAppraisalPeers");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceAppraisalPeers != null) {
				session.saveOrUpdate(performanceAppraisalPeers);
			}
			logger.debug("savePerformanceAppraisalPeers Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_PEERS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceAppraisalPeers;
	}

	@Override
	public PerformanceAppraisalPeers getPerformanceAppraisalPeersByUserIdAndOrgLocationId(Long userId,
			Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceAppraisalPeers> performanceAppraisalPeersList = new ArrayList<PerformanceAppraisalPeers>();
		logger.debug("getPerformanceAppraisalPeersByUserIdAndOrgLocationId  start");
		performanceAppraisalPeersList = session.createQuery("from PerformanceAppraisalPeers peers where peers.userId.id = :userId and peers.orgLocationId.id = :orgLocationId " ).setLong( "userId", userId).setLong( "orgLocationId", orgLocationId).list();
		logger.debug("getPerformanceAppraisalPeersByUserIdAndOrgLocationId  end");
		if (performanceAppraisalPeersList.size() > 0) {
			return performanceAppraisalPeersList.get(0);
		} else {
			return null;
		}
	 }

	@Override
	public List<PerformanceAppraisalPeersDTO> getAssociateAssignedPeersList(Long userId, Long orgLocationId)
			throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		Query queryObj = null;
		List<PerformanceAppraisalPeersDTO> assignedPeersList = null;
		
		String peersQuery =  "SELECT " + 
				 "PEERS.PEER_USER_ID " + 
				 "FROM " + 
				 "PERFORMANCE_APPRAISAL_PEERS PEERS  " + 
				 "WHERE " + 
				 "PEERS.ORG_LOCATION_ID = :orgLocationId AND PEERS.USER_ID = :userId ";

					Query peersQueryObj = session.createSQLQuery(peersQuery);
					peersQueryObj.setParameter("orgLocationId", orgLocationId);
					peersQueryObj.setParameter("userId", userId);
					String peersUsers = (String) peersQueryObj.uniqueResult();
					
					if(peersUsers != null) {
					
					String peerUserIds [] = peersUsers.split(",");
					String peerUserIdStr = "'" + StringUtils.join(peerUserIds,"','") + "'";
					
					
					
					try {
						query = "SELECT Q1.* " + 
						 		 "FROM (" + 
								 "SELECT " + 
								 "CAND.GENERATED_ID AS associateId, " + 
								 "CONCAT (U.FIRST_NAME,' ',IFNULL(U.LAST_NAME,'')) AS associateName, " +
								 "U.FILE_URL AS userImgUrl, " + 
								 "R.DISPLAY_NAME AS associateRole, " + 
								 "U.EMAIL AS associateEmail " + 
										             
								"FROM " + 
								"USER U ," + 
								"USER_ROLE UR ," + 
								"ROLES R, " + 
								"CANDIDATE CAND " + 
								"WHERE U.ID = CAND.CONSULTANT_USER_ID AND UR.ROLE_ID = R.ID AND CAND.CONSULTANT_USER_ID = UR.USER_ID " + 
								"AND U.ID IN ("+peerUserIdStr+") GROUP BY CAND.CONSULTANT_USER_ID)Q1 ";
						
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					queryObj = session.createSQLQuery(query);
					queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalPeersDTO.class));
					assignedPeersList = queryObj.list();
				}
					

		return assignedPeersList;
	}

	@Override
	public List<PerformanceAppraisalPeersDTO> getAssociateAssignedPeersListForAppraisal(String assignedUUID)
			throws Exception {
		

		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		Query queryObj = null;
		List<PerformanceAppraisalPeersDTO> assignedPeersList = null;
		
		String peersQuery =  "SELECT " + 
				 "ASSIGNED.PEER_USER_ID " + 
				 "FROM " + 
				 "PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED  " + 
				 "WHERE " + 
				 "ASSIGNED.UUID = :assignedUUID ";

					Query peersQueryObj = session.createSQLQuery(peersQuery);
					peersQueryObj.setParameter("assignedUUID", assignedUUID);
					String peersUsers = (String) peersQueryObj.uniqueResult();
					
					if(peersUsers != null) {
					
					String peerUserIds [] = peersUsers.split(",");
					String peerUserIdStr = "'" + StringUtils.join(peerUserIds,"','") + "'";
					
					try {
						query = "SELECT Q1.* " + 
						 		 "FROM (" + 
								 "SELECT " + 
								 "CAND.GENERATED_ID AS associateId, " + 
								 "CONCAT (U.FIRST_NAME,' ',IFNULL(U.LAST_NAME,'')) AS associateName, " +
								 "U.FILE_URL AS userImgUrl, " + 
								 "R.DISPLAY_NAME AS associateRole, " + 
								 "U.EMAIL AS associateEmail " + 
										             
								"FROM " + 
								"USER U ," + 
								"USER_ROLE UR ," + 
								"ROLES R, " + 
								"CANDIDATE CAND " + 
								"WHERE U.ID = CAND.CONSULTANT_USER_ID AND UR.ROLE_ID = R.ID AND CAND.CONSULTANT_USER_ID = UR.USER_ID " + 
								"AND U.ID IN ("+peerUserIdStr+") GROUP BY CAND.CONSULTANT_USER_ID)Q1 ";
						
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					queryObj = session.createSQLQuery(query);
					queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalPeersDTO.class));
					assignedPeersList = queryObj.list();
				}
					

		return assignedPeersList;
	
	}
}
