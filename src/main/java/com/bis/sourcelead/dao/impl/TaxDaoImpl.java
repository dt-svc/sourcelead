package com.bis.sourcelead.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.TaxDao;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxForm;
import com.bis.sourcelead.entity.SourceleadTaxSection;
import com.bis.sourcelead.entity.SourceleadTaxSectionItem;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;

@Repository
@Transactional
public class TaxDaoImpl implements TaxDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<SourceleadTaxType> getAllTaxesByType(long type, boolean includeInactive) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SourceleadTaxType st where st.taxType=:taxType";
			if (!includeInactive) {
				query += " and st.isActive=:isActive";
			}
			Query queryObj = session.createQuery(query);
			queryObj.setLong("taxType", type);
			if (!includeInactive) {
				queryObj.setBoolean("isActive", true);
			}
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
	@SuppressWarnings("unchecked")
	public List<StateTax> getStateTaxList(String yearFrequency, long stateId, long taxTypeId, Paginator paginator) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from StateTax st";
			List<String> params = new LinkedList<>();
			if (yearFrequency != null && !yearFrequency.isEmpty()) {
				query += " where st.financialYear=:yearFrequency";
			}
			if (stateId > 0) {
				query += (query.contains("where") ? " and " : " where ") + "st.states.id=:stateId";
			}
			if (taxTypeId > 0) {
				query += (query.contains("where") ? " and " : " where ") + "st.sourceleadTaxType.id=:taxTypeId";
			}
			query += " order by st.id desc";
			Query queryObj = session.createQuery(query);
			if (yearFrequency != null && !yearFrequency.isEmpty()) {
				queryObj.setString("yearFrequency", yearFrequency);
				params.add("String;yearFrequency;" + yearFrequency);
			}
			if (stateId > 0) {
				queryObj.setLong("stateId", stateId);
				params.add("long;stateId;" + stateId);
			}
			if (taxTypeId > 0) {
				queryObj.setLong("taxTypeId", taxTypeId);
				params.add("long;taxTypeId;" + taxTypeId);
			}
			if (paginator != null) {
				queryObj.setFirstResult(paginator.getPageNo() * paginator.getPageSize());
				queryObj.setMaxResults(paginator.getPageSize());
			}
			List<StateTax> stateTaxList = queryObj.list();
			if (paginator != null) {
				paginator.applyPaginationData(query, session, params);
			}
			return stateTaxList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
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
	@Transactional
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
	public boolean removeProfesionalTax(ProfessionalTax professionalTax) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.delete(professionalTax);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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

	@Override
	public long changeStateTaxStatus(long stateTaxId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StateTax stateTax = session.get(StateTax.class, stateTaxId);
			if (stateTax != null) {
				stateTax.setActive(!stateTax.isActive());
				return stateTax.getId();
			} else
				return -2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SourceleadTaxForm> getTaxFormTypeList(boolean isAdminFlag) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SourceleadTaxForm st";
			if (!isAdminFlag) {
				query += " where st.active=:active";
			}
			Query queryObj = session.createQuery(query);
			if (!isAdminFlag) {
				queryObj.setBoolean("active", true);
			}
			List<SourceleadTaxForm> stList = queryObj.list();
			return stList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SourceleadTaxForm getTaxFormType(long taxFormId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(SourceleadTaxForm.class, taxFormId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SourceleadTaxSection getTaxSection(long taxSectionId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(SourceleadTaxSection.class, taxSectionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public long addITDeclarationSection(SourceleadTaxSection taxSection) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(taxSection);
			return taxSection.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public SourceleadTaxSectionItem getTaxSectionItem(long taxSectionItemId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(SourceleadTaxSectionItem.class, taxSectionItemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public long addITDeclarationSectionItem(SourceleadTaxSectionItem taxSectionItem) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(taxSectionItem);
			return taxSectionItem.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public long changeITDeclarationSectionStatus(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SourceleadTaxSection sourceleadTaxSection = session.get(SourceleadTaxSection.class, id);
			if (sourceleadTaxSection != null) {
				sourceleadTaxSection.setActive(!sourceleadTaxSection.isActive());
				return sourceleadTaxSection.getId();
			} else
				return -2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public long changeITDeclarationSectionItemStatus(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SourceleadTaxSectionItem sourceleadTaxSectionItem = session.get(SourceleadTaxSectionItem.class, id);
			if (sourceleadTaxSectionItem != null) {
				sourceleadTaxSectionItem.setActive(!sourceleadTaxSectionItem.isActive());
				return sourceleadTaxSectionItem.getId();
			} else
				return -2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
