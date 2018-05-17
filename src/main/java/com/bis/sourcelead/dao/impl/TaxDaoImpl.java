package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TaxDao;
import com.bis.sourcelead.entity.CentralTax;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;
import com.bis.sourcelead.entity.Tds;

@Repository
@Transactional
public class TaxDaoImpl implements TaxDao {
	private static final Logger logger = LoggerFactory.getLogger(TaxDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SourceleadTaxType> getAllTaxesByType(long type) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SourceleadTaxType st where st.taxType=:taxType";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("taxType", type);
			List<SourceleadTaxType> taxTypeList = queryObj.list();
			if (taxTypeList != null && !taxTypeList.isEmpty()) {
				return taxTypeList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<StateTax> getStateTaxList(String yearFrequency, long stateId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from StateTax st";
			if (yearFrequency != null && !yearFrequency.isEmpty()) {
				query += " where st.financialYear=:yearFrequency";
			}
			if (stateId > 0) {
				query += (query.contains("where") ? " and " : " where ") + "st.states.id=:stateId";
			}
			Query queryObj = session.createQuery(query);
			if (yearFrequency != null && !yearFrequency.isEmpty()) {
				queryObj.setString("yearFrequency", yearFrequency);
			}
			if (stateId > 0) {
				queryObj.setLong("stateId", stateId);
			}
			List<StateTax> stateTaxList = queryObj.list();
			return stateTaxList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override

	@Transactional
	public CentralTax storeCentralTax(CentralTax centralTax) {

		logger.debug("CentralTax");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (centralTax != null) {
				session.saveOrUpdate(centralTax);
			}
			logger.debug("Saving CentralTax");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return centralTax;
	}

	@Override
	@Transactional
	public Tds storeTds(Tds tds) {
		logger.debug("CentralTax");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (tds != null) {
				session.saveOrUpdate(tds);
			}
			logger.debug("Saving CentralTax");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return tds;
	}

	public StateTax getStateTax(long stateTaxId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from StateTax st where st.id=:stateTaxId";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("stateTaxId", stateTaxId);
			List<StateTax> stList = queryObj.list();
			if (stList != null && !stList.isEmpty()) {
				return stList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long addStateTax(StateTax stateTax) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(stateTax);
			return stateTax.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public SourceleadTaxType getTaxTypeById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SourceleadTaxType stt where stt.id=:id";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("id", id);
			List<SourceleadTaxType> sttList = queryObj.list();
			if (sttList != null && !sttList.isEmpty()) {
				return sttList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
