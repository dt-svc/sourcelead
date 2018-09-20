package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AssociateSalaryDao;
import com.bis.sourcelead.dto.AssociatePayItemDTO;
import com.bis.sourcelead.entity.AssociatePayItem;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.AssociateSalaryHistory;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.impl.AssociateBankDataDaoImpl;
import com.bis.sourcelead.payroll.dto.DeductionHistoryDTO;

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
	public List<AssociateSalary> getAssociateSalaryListByGroupId(OrgLocations orgLocation, long groupId) {
		Session session = this.sessionFactory.getCurrentSession();
		String qry = "from AssociateSalary ass where ass.orgLocations.id = :orgLocationId and ass.groupId = :groupId and ass.status = '1'";
		List<AssociateSalary> assList = session.createQuery(qry).setLong("orgLocationId", orgLocation.getId()).setLong("groupId", groupId).list();
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
}
