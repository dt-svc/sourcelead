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

import com.bis.sourcelead.dto.StateTaxDTO;
import com.bis.sourcelead.entity.ProfessionalTax;

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
	public List<StateTax> getStateTaxList(String yearFrequency, long stateId, long taxTypeId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from StateTax st";
			if (yearFrequency != null && !yearFrequency.isEmpty()) {
				query += " where st.financialYear=:yearFrequency";
			}
			if (stateId > 0) {
				query += (query.contains("where") ? " and " : " where ") + "st.states.id=:stateId";
			}
			if (taxTypeId > 0) {
				query += (query.contains("where") ? " and " : " where ") + "st.sourceleadTaxType.id=:taxTypeId";
			}
			Query queryObj = session.createQuery(query);
			if (yearFrequency != null && !yearFrequency.isEmpty()) {
				queryObj.setString("yearFrequency", yearFrequency);
			}
			if (stateId > 0) {
				queryObj.setLong("stateId", stateId);
			}
			if (taxTypeId > 0) {
				queryObj.setLong("taxTypeId", taxTypeId);
			}
			List<StateTax> stateTaxList = queryObj.list();
			return stateTaxList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

	@Override
	public List<CentralTax> getCentralTaxList(String yearFrequency) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CentralTax ct";
			if (yearFrequency != null && !yearFrequency.isEmpty()) {
				query += " where ct.financialYear=:yearFrequency";
			}
		
			Query queryObj = session.createQuery(query);
			if (yearFrequency != null && !yearFrequency.isEmpty()) {
				queryObj.setString("yearFrequency", yearFrequency);
			}
			
			List<CentralTax> centralTaxList = queryObj.list();
			return centralTaxList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
	@Override
	public long storeCentralTax(CentralTax centralTax) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(centralTax);
			return centralTax.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public StateTax getStateTax(long stateTaxId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(StateTax.class, stateTaxId);
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
			return session.get(SourceleadTaxType.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ProfessionalTax getProfessionalTaxById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(ProfessionalTax.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
