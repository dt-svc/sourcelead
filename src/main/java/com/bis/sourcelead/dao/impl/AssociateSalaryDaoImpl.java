package com.bis.sourcelead.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateSalaryDao;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SalaryDTO;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.AssociateSalaryHistory;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.impl.AssociateBankDataDaoImpl;

@Repository
public class AssociateSalaryDaoImpl implements AssociateSalaryDao{

	
private static final Logger logger = LoggerFactory.getLogger(AssociateBankDataDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	
	@Override
	@Transactional
	public AssociateSalary addAssociateSal(AssociateSalary associateSalary)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateSalary != null) {
				session.saveOrUpdate(associateSalary);
			}
		} catch (Exception exception) {
			logger.error("add associate salary failed due to : "+exception.getMessage());
		}
		return associateSalary;
	}


	@Override
	@Transactional
	public AssociateSalary findAssociateSalaryByCandidateId(Long candidateId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateSalary associateSalary = null;
		try {
			List<AssociateSalary> associateSalaryList = session.createQuery("from AssociateSalary associateSalary where associateSalary.candidate.id=:candidateId and associateSalary.status = 1" ).setLong("candidateId", candidateId).list();
		 if(associateSalaryList !=null && associateSalaryList.size() >0) {
			 associateSalary =  associateSalaryList.get(0);
		 }
		}catch (Exception e) {
			logger.error("findAssociateSalaryByUserId failed due to :"+e.getMessage());
		}
		return associateSalary;
	}


	@Override
	@Transactional
	public AssociateSalary findAssociateSalaryById(String id)
	{
		Session session = this.sessionFactory.getCurrentSession();
		AssociateSalary associateSalary = null;
		try {
			List<AssociateSalary> associateSalaryList = session.createQuery("from AssociateSalary associateSalary where associateSalary.id=:id and associateSalary.status = 1").setLong("id", Long.parseLong(id)).list();
			if (associateSalaryList != null && associateSalaryList.size() > 0) {
				associateSalary = associateSalaryList.get(0);
			}
		} catch (Exception e) {
			logger.error("findAssociateSalaryByUserId failed due to :" + e.getMessage());
		}
		return associateSalary;
	}


	@Override
	@Transactional
	public AssociateSalaryHistory updaetAssociateSalHis(AssociateSalaryHistory associateSalHis)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associateSalHis != null) {
				session.saveOrUpdate(associateSalHis);
			}
		} catch (Exception exception) {
			logger.error("add associate salary History failed due to : "+exception.getMessage());
		}
		return associateSalHis;
	}


	@Override
	@Transactional
	public AssociatePayItem findPayItemByPayItemId(Long payItemId) {
		Session session = this.sessionFactory.getCurrentSession();
		AssociatePayItem associatePayItem = null;
		try {
			List<AssociatePayItem> associatePayItemList = session.createQuery("from AssociatePayItem associatePayItem where associatePayItem.id=:id and associatePayItem.status = 1").setLong("id", payItemId).list();
			if (associatePayItemList != null && associatePayItemList.size() > 0) {
				associatePayItem = associatePayItemList.get(0);
			}
		} catch (Exception e) {
			logger.error("findPayItemByPayItemId failed due to :" + e.getMessage());
		}
		return associatePayItem;
	}
	
	@Override
	@Transactional
	public AssociatePayItem saveUpdatedPayItem(AssociatePayItem associatePayItem)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associatePayItem != null) {
				session.saveOrUpdate(associatePayItem);
			}
		} catch (Exception exception) {
			logger.error("add associate pay item failed due to : "+exception.getMessage());
		}
		return associatePayItem;
	}


	@Override
	@Transactional
	public List<AssociateSalary> getAssociateSalaryListByGroupId(OrgLocations orgLocation, long groupId,String payType) {
		
		String qry = "";
		List<AssociateSalary> assList = null;
		Session session = this.sessionFactory.getCurrentSession();
		if(Constants.ALL_RECORDS.equalsIgnoreCase(payType)) {
			qry = "from AssociateSalary ass where ass.orgLocations.id = :orgLocationId and ass.groupId = :groupId and ass.status = '1'";// and ass.candidate.isAssociate = 'Y'";
			 assList = session.createQuery(qry).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", groupId).list();
		}else {
			qry = "from AssociateSalary ass where ass.orgLocations.id = :orgLocationId and ass.groupId = :groupId and ass.payType = :payType and ass.status = '1'";// and ass.candidate.isAssociate = 'Y'";
			 assList = session.createQuery(qry).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", groupId).setParameter("payType", payType).list();
		}
		 
		if(assList != null)
			return assList;
		else
			return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AssociateSalaryInd> getIndAssociateSalaryListByGroupId(OrgLocations orgLocation, long groupId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String qry = "from AssociateSalaryInd ass where ass.orgLocations.id = :orgLocationId and ass.groupId = :groupId and ass.status = '1'";
			List<AssociateSalaryInd> assList = session.createQuery(qry).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", groupId).list();
			if (assList != null)
				return assList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	@Transactional
	public List<AssociatePayItemDTO> findAssociatePayItemByPayrollId(Long payrollId)
	{
		List<AssociatePayItemDTO> payItemHis = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT  assoPayItem.ID as payItemId,")
			.append("assoPayItem.PAY_ITEM_AMOUNT as amount,")
			.append("assoPayItem.PAY_ITEM_NAME as payItemName")
			.append(" FROM ASSOCIATE_PAY_ITEM assoPayItem,US_PAYITEM_HIS payItemHis")
			.append( " where assoPayItem.ID = payItemHis.PAY_ITEM_ID")
			.append(  " AND payItemHis.PAYROLL_ID = :payrollId");
			Query queryObj = session.createSQLQuery(sb.toString());
			queryObj.setLong("payrollId", payrollId);
			queryObj.setResultTransformer(Transformers.aliasToBean(AssociatePayItemDTO.class));
			payItemHis = queryObj.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return payItemHis;
	}


	@Override
	@Transactional
	public AssociateSalary getAssociateSalaryByCandidateIdAndOrgLocationId(Long candidateId, Long locationId)
	{
		List<AssociateSalary> assSal = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from AssociateSalary ass where ass.orgLocations.id = :locationId and ass.candidate.id = :candidateId";
			assSal = session.createQuery(query).setLong("locationId", locationId).setLong("candidateId", candidateId).list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(assSal != null) {
			return assSal.get(0);
		}else {
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<AssociateSalary> getAssociateSalaryListByLocationIdAndGroupId(OrgLocations orgLocation, Long groupId,CandidateDTO candidateDto) {
		
		List<AssociateSalary> assSalList = null;
		ResponseList<AssociateSalary> response = new ResponseList<>();
		
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from AssociateSalary ass where ass.orgLocations.id = :orgLocationId and ass.groupId = :groupId and ass.status = '1'";
		
		
		StringBuilder query1 = new StringBuilder("");
		if (StringUtils.isNotBlank(candidateDto.getSearchValue())) {
					query1.append(" and (ass.candidate.firstName like '%" + candidateDto.getSearchValue() + "%' ")
							.append("OR ass.candidate.lastName like '%"
									+ candidateDto.getSearchValue() + "%' ")
							.append("OR ass.candidate.email like '%" + candidateDto.getSearchValue()
									+ "%' ")
							.append("OR ass.candidate.designationType like '%" + candidateDto.getSearchValue()
									+ "%' ")
							.append("OR ass.candidate.mobilePhone like '%" + candidateDto.getSearchValue() + "%' )");

			}
		Query queryObj = session.createQuery(qry + query1.toString());
		
		if (candidateDto != null && candidateDto.getLength() > 0) {
			queryObj.setFirstResult(candidateDto.getStart());
			queryObj.setMaxResults(candidateDto.getLength());
		}
		assSalList = queryObj.setLong("orgLocationId", orgLocation.getId()).setLong("groupId", groupId).list();
		int count = 0;
		int filteredCount = 0;

		List<Long> countlist = session.createQuery("select count(*) " + qry)
				.setLong("orgLocationId", orgLocation.getId())
				.setLong("groupId", groupId).list();

		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (candidateDto != null) {
			List<Long> filteredCountlist = session.createQuery("select count(*) " + qry + query1.toString())
					.setLong("orgLocationId", orgLocation.getId())
					.setLong("groupId", groupId).list();
			if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		}
		response.setData(assSalList);
		return response;
		
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SalaryDTO> getSalaryByGroupId(OrgLocations orgLocation, Long candidateId,Date freqStartDate, Date freqEndDate)
	{
		List<SalaryDTO> salarylist = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			java.sql.Date startDate = new java.sql.Date(freqStartDate.getTime());
			java.sql.Date endDate = new java.sql.Date(freqEndDate.getTime());
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ")
			.append("sal.CANDIDATE_ID as candidateId,")
			.append("can.FIRST_NAME as firstName,")
			.append("can.LAST_NAME as lastName,")
			.append("can.IS_ASSOCIATE as isTerminated,") 
			.append("role.TERMINATE_DATE as terminatedDate ")
			.append(" FROM sourcelead.USER_ROLE role,USER usr,CANDIDATE can,ASSOCIATE_SALARY sal ")
			.append(" where usr.ID = role.USER_ID")
			.append(" AND  can.CONSULTANT_USER_ID = usr.ID")
			.append(" AND sal.CANDIDATE_ID = can.ID")
			.append(" AND can.ID =:candidateId AND sal.ORG_LOCATION_ID = :locationId ")
			.append(" AND role.TERMINATE_DATE >= :startDate AND role.TERMINATE_DATE <= :endDate");// OR (role.TERMINATE_DATE IS NULL OR role.TERMINATE_DATE IS NULL))");
			Query queryObj = session.createSQLQuery(sb.toString());
			queryObj.setLong("candidateId", candidateId)
			.setLong("locationId", orgLocation.getId())
			.setDate("startDate", startDate)
			.setDate("endDate", endDate);
			queryObj.setResultTransformer(Transformers.aliasToBean(SalaryDTO.class));
			salarylist = queryObj.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return salarylist;
	}
	
	@Override
	@Transactional
	public List<AssociateSalaryInd> getIndAssociateSalaryListByGroupId(OrgLocations orgLocation, long groupId,
			String payType) {
		String qry = "";
		List<AssociateSalaryInd> assList = null;
		Session session = this.sessionFactory.getCurrentSession();
		if(Constants.ALL_RECORDS.equalsIgnoreCase(payType)) {
			qry = "from AssociateSalaryInd ass where ass.orgLocations.id = :orgLocationId and ass.groupId = :groupId and ass.status = '1'";
			 assList = session.createQuery(qry).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", groupId).list();
		}else {
			qry = "from AssociateSalaryInd ass where ass.orgLocations.id = :orgLocationId and ass.groupId = :groupId and ass.payType = :payType and ass.status = '1'";
			 assList = session.createQuery(qry).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", groupId).setParameter("payType", payType).list();
		}
		 
		if(assList != null)
			return assList;
		else
			return null;
	}
}
