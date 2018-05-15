package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TaxDao;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;

@Repository
public class TaxDaoImpl implements TaxDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
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
	@Transactional
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

}
