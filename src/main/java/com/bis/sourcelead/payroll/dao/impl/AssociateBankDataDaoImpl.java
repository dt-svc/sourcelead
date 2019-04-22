package com.bis.sourcelead.payroll.dao.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PaymentAccount;
import com.bis.sourcelead.payroll.dao.AssociateBankDataDao;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.entity.AssociateBankData;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Repository
public class AssociateBankDataDaoImpl implements AssociateBankDataDao{

	private static final Logger logger = LoggerFactory.getLogger(AssociateBankDataDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public AssociateBankData saveAssociateBankData(AssociateBankData associateBankData)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateBankData != null) {
				session.saveOrUpdate(associateBankData);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return associateBankData;
	}

	@Override
	@Transactional
	public AssociateBankData findAssociateBankDataByCandidateId(Long candidateId, OrgLocations orgLocation)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateBankData associateBankData = null;
		try {
			List<AssociateBankData>  associateBankDataList = session.createQuery("from AssociateBankData bankData where bankData.candidate.id=:candidateId and bankData.orgLocationId.id= :orgLocationId" ).setLong( "candidateId", candidateId).setLong("orgLocationId", orgLocation.getId()).list();
		    if(associateBankDataList !=null && associateBankDataList.size() >0) {
		    	associateBankData = associateBankDataList.get(0);
		    }
		
		}catch (Exception e) {
		}
		return associateBankData;
	}

	@Override
	@Transactional
	public AssociateBankData findAssociateBankDataById(long id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateBankData associateBankData = null;
		try {
			List<AssociateBankData>  associateBankDataList = session.createQuery("from AssociateBankData bankData where bankData.id=:id" ).setLong( "id", id).list();
		    if(associateBankDataList !=null && associateBankDataList.size() >0) {
		    	associateBankData = associateBankDataList.get(0);
		    }
		
		}catch (Exception e) {
		}
		return associateBankData;
	}

	@Override
	@Transactional
	public AssociateBankData getAssociateBankDataByOrgLocationId(Long locationId) {
		Session session = this.sessionFactory.getCurrentSession();
		
		AssociateBankData assBankData = null;
		try {
			List<AssociateBankData> assBankDataList = session.createQuery("from AssociateBankData assBankData where assBankData.orgLocationId.id =:orgLocationId and assBankData.defaultAccount = :defaultAccount").setBoolean("defaultAccount", true).setLong("orgLocationId", locationId).list();
		    if(assBankDataList != null && assBankDataList.size() >0) {
		    	assBankData = assBankDataList.get(0);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assBankData;
	}

	@Override
	public boolean getisActivePaymentAccountExistedByCandidateId(Long candidateId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateBankData> paymentAccount = new ArrayList<AssociateBankData>();
		boolean  flag = false;
		paymentAccount = session.createQuery("from AssociateBankData ab left join fetch ab.candidate where ab.candidate.id = :candidateId and ab.defaultAccount = :flag " ).setLong( "candidateId", candidateId ).setBoolean("flag", true).list();
		if (paymentAccount.size() > 0) {
			flag = true;
		} 
		return flag;
	}


	@Override
	@Transactional
	public int saveAssociateBankDetails(Long orgLocationId, List<AssociateBankDataDTO> list, Map<String, Long> map)
	{
		List<Integer> listCount = new ArrayList<>();
		int totalCount = 0;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.doWork(new Work() {
				@Override
				public void execute(java.sql.Connection connection) throws SQLException {
					boolean initialAutocommitSetting = connection.getAutoCommit();
					connection.setAutoCommit(false);
					CallableStatement stmt = connection.prepareCall("{call associateBankDataProcedure(?,?,?,?,?,?,?,?,?)}");
					for (AssociateBankDataDTO bankDto : list) {
						if(bankDto.getEmailId() !=null && !"".equalsIgnoreCase(bankDto.getEmailId()) && map.get(bankDto.getEmailId()) != null) {
							stmt.setLong(1, map.get(bankDto.getEmailId()));
						}else {
							stmt.setLong(1, 0);							
						}
						stmt.setString(2, bankDto.getAccountType());
						stmt.setString(3, bankDto.getBankName());
						stmt.setString(4, bankDto.getAccountNumber());
						stmt.setString(5, bankDto.getRoutingNumber());
						stmt.setString(6, orgLocationId.toString());	//OrgLocationID
						
						java.util.Date date = Calendar.getInstance().getTime();
						java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
						stmt.setDate(7,sqlDate);	//Created Date
						stmt.setDate(8, sqlDate);	//Updated Date
						stmt.setString(9, SourceLeadRandomCodeHelper.generateRandomUUID());	//associateBankUUID
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AssociateBankData> findAllAssociateBankDataByCandidateId(Long candidateId, Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateBankData> assBankDataList = null;
		try {
			assBankDataList = session.createQuery("from AssociateBankData assBankData where assBankData.orgLocationId.id =:orgLocationId and assBankData.candidate.id = :candidateId").setLong("orgLocationId", orgLocationId).setLong("candidateId", candidateId).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assBankDataList;
	}

	@Override
	@Transactional
	public AssociateBankData findAssociateBankDataByUUID(String associateBankUUID, Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateBankData assBankData = null;
		try {
			List<AssociateBankData> dataList = session.createQuery("from AssociateBankData assBankData where assBankData.orgLocationId.id =:orgLocationId and assBankData.associateBankUuid = :associateBankUUID").setLong("orgLocationId", orgLocationId).setString("associateBankUUID", associateBankUUID).list();
			if(dataList != null && dataList.size() >0) {
				assBankData = dataList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assBankData;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean checkDeafaultAccountActiveOrNot(Long candidateId, Long orgLocationId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		boolean isActiveAccount = false;
		try {
			List<AssociateBankData> dataList = session.createQuery("from AssociateBankData assBankData where assBankData.orgLocationId.id =:orgLocationId and assBankData.defaultAccount = :defaultAccount").setLong("orgLocationId", orgLocationId).setBoolean("defaultAccount", true).list();
			if(dataList != null && dataList.size() >0) {
				isActiveAccount = true;;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isActiveAccount;
	}

	@Override
	@Transactional
	public boolean changeActiveAccount(Long associateBankId, Long candidateId)
	{
		boolean isChanged = false;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			AssociateBankData associateBankData = session.get(AssociateBankData.class, associateBankId);
			associateBankData.setDefaultAccount(true);
			session.saveOrUpdate(associateBankData);
			// Setting false to remaining accoungts
			String query = "from AssociateBankData assoBank where assoBank.candidate.id = :candidateId and assoBank.id != :id";
			Query queryObj = session.createQuery(query).setLong("id", associateBankId).setLong("candidateId", candidateId);
			List<AssociateBankData> list = queryObj.list();
			for (AssociateBankData b : list) {
				b.setDefaultAccount(false);
				session.saveOrUpdate(b);
			}
			if (associateBankData != null) {
				isChanged = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isChanged;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AssociateBankDataDTO> getAssociateBankDataListByOrgLocationId(Long locationId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<AssociateBankDataDTO> dtolist = new ArrayList<AssociateBankDataDTO>();
		try {
			List<AssociateBankData> assBankDataList = session.createQuery("from AssociateBankData assBankData where assBankData.orgLocationId.id =:orgLocationId").setLong("orgLocationId", locationId).list();
		    for(AssociateBankData b : assBankDataList) {
		    	AssociateBankDataDTO dto = new AssociateBankDataDTO();
		    	dto.setCandidateId(b.getCandidate().getId().toString());
		    	dto.setAccountType(b.getAccountType());
		    	dto.setAccountNumber(b.getAccountNumber());
		    	dtolist.add(dto);
		    }
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtolist;
	}

}
