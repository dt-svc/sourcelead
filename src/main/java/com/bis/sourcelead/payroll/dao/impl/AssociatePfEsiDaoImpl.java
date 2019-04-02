package com.bis.sourcelead.payroll.dao.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.AssociatePfEsiDao;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.entity.AssociatePfEsiDetails;

@Repository
@Transactional
public class AssociatePfEsiDaoImpl implements AssociatePfEsiDao{

	private static final Logger logger = LoggerFactory.getLogger(AssociatePfEsiDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public AssociatePfEsiDetails saveAssociatePfEsi(AssociatePfEsiDetails associatePfEsiDetails) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associatePfEsiDetails != null) {
				session.saveOrUpdate(associatePfEsiDetails);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return associatePfEsiDetails;
	}

	@Override
	public AssociatePfEsiDetails findAssociatePfEsiByCandidateId(Long candidateId, OrgLocations orgLocation) {
		Session session = this.sessionFactory.getCurrentSession();
		AssociatePfEsiDetails associatePfEsi = null;
		try {
			List<AssociatePfEsiDetails>  associatePfEsiList = session.createQuery("from AssociatePfEsiDetails ape left join fetch ape.candidateId where "
					+ "ape.candidateId.id = :candidateId and ape.orgLocationId.id= :orgLocationId" ).setLong( "candidateId", candidateId).setLong("orgLocationId", orgLocation.getId()).list();
		    if(associatePfEsiList !=null && associatePfEsiList.size() >0) {
		    	associatePfEsi = associatePfEsiList.get(0);
		    }
		}catch (Exception e) {
		}
		return associatePfEsi;
	}

	@Override
	public AssociatePfEsiDetails findAssociatePfEsiId(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		AssociatePfEsiDetails associatePfEsi = null;
		try {
			List<AssociatePfEsiDetails>  associatePfEsiList = session.createQuery("from AssociatePfEsiDetails ape where ape.id=:id" ).setLong( "id", id).list();
		    if(associatePfEsiList !=null && associatePfEsiList.size() >0) {
		    	associatePfEsi = associatePfEsiList.get(0);
		    }
		
		}catch (Exception e) {
		}
		return associatePfEsi;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<AssociatePfEsiDetails> getAllPfEsiDetailsByOrgLocationid(Long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from AssociatePfEsiDetails pf  where pf.orgLocationId.id =:id";
			List<AssociatePfEsiDetails> pfList = session.createQuery(query).setLong("id", id).list();
			return pfList;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;	
	      }

	@Override
	public int saveAssociateInformation(Long id, List<AssociatePfEsiDetailsDTO> list, Map<String, Long> canMap) {
		List<Integer> listCount = new ArrayList<>();
		int totalCount = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call associateInfoDataProcedure(?,?,?,?,?,?,?,?)}");
					for (AssociatePfEsiDetailsDTO infoDto : list) {	
						Long candidateId = canMap.get(infoDto.getEmail().trim());
						stmt.setLong(1,   candidateId);
						stmt.setString(2, infoDto.getUanNumber());
						stmt.setString(3, infoDto.getPfNumber());
						stmt.setString(4, infoDto.getEsinumber());
						stmt.setString(5, id.toString());	//OrgLocationID						
						java.util.Date date = Calendar.getInstance().getTime();
						java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
						stmt.setDate(6,sqlDate);	//Created Date
						stmt.setString(7,infoDto.getEligibleForEsi());
						stmt.setString(8,infoDto.getEligibleForPf());
						stmt.addBatch();
					}
					int[] updateCount = stmt.executeBatch();
					listCount.add(updateCount.length);
					connection.commit();
					connection.setAutoCommit(initialAutocommitSetting);
				}
			});
			if(listCount.size() >0) {
				totalCount = 0;
			}
			return totalCount;
		} catch (Exception e) {
			e.printStackTrace();
			return totalCount;
		}
	}
} 
