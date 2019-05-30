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
import com.bis.sourcelead.entity.CentralTax;
import com.bis.sourcelead.entity.ProfessionalTax;
import com.bis.sourcelead.entity.SourceleadTaxChallan;
import com.bis.sourcelead.entity.SourceleadTaxChallanItem;
import com.bis.sourcelead.entity.SourceleadTaxChallanItemSubFormField;
import com.bis.sourcelead.entity.SourceleadTaxForm;
import com.bis.sourcelead.entity.SourceleadTaxITDeclaration;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationItem;
import com.bis.sourcelead.entity.SourceleadTaxITDeclarationSubmission;
import com.bis.sourcelead.entity.SourceleadTaxSubmissionDocument;
import com.bis.sourcelead.entity.SourceleadTaxSubmissionDocumentAttachment;
import com.bis.sourcelead.entity.SourceleadTaxSubmissionMaster;
import com.bis.sourcelead.entity.SourceleadTaxType;
import com.bis.sourcelead.entity.StateTax;
import com.bis.sourcelead.entity.Tds;
import com.bis.sourcelead.entity.User;

@Repository
@Transactional
public class TaxDaoImpl implements TaxDao {

	// private static final Logger logger =
	// LoggerFactory.getLogger(TaxDaoImpl.class);

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

	@SuppressWarnings("unchecked")
	public StateTax getStateTax(String statetaxuuid) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from StateTax st";
			query += " where st.statetaxuuid = :statetaxuuid";
			Query queryObj = session.createQuery(query).setString("statetaxuuid", statetaxuuid);
			List<StateTax> stateTaxUUIDList = queryObj.list();
			if (stateTaxUUIDList != null && !stateTaxUUIDList.isEmpty()) {
				return stateTaxUUIDList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public StateTax getStateTaxById(long stateTaxId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			StateTax stateTax = session.get(StateTax.class, stateTaxId);
			return stateTax;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StateTax getStateTaxByStateNameAndFinancialYear(String stateName, String financialYear) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from StateTax st";
			query += " where st.states.stateName = :stateName and st.financialYear = :financialYear and st.active = :active";
			Query queryObj = session.createQuery(query).setString("stateName", stateName)
					.setString("financialYear", financialYear).setBoolean("active", true);
			List<StateTax> stateTaxList = queryObj.list();
			if (stateTaxList != null && !stateTaxList.isEmpty()) {
				return stateTaxList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CentralTax getCentralTax(String centraltaxuuid) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CentralTax ct";
			query += " where ct.centraltaxuuid = :centraltaxuuid";
			Query queryObj = session.createQuery(query).setString("centraltaxuuid", centraltaxuuid);
			List<CentralTax> centralTaxUUIDList = queryObj.list();
			if (centralTaxUUIDList != null && !centralTaxUUIDList.isEmpty()) {
				return centralTaxUUIDList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CentralTax getCentralTaxByTaxType(String taxType) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CentralTax ct where ct.sourceleadTaxType.description = :taxType";
			Query queryObj = session.createQuery(query).setString("taxType", taxType);
			List<CentralTax> centralTaxList = queryObj.list();
			if (centralTaxList != null && !centralTaxList.isEmpty()) {
				return centralTaxList.get(0);
			}
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

	@SuppressWarnings("unchecked")
	@Override
	public List<CentralTax> getCentralTaxList(String yearFrequency, long taxTypeId) {
		List<CentralTax> centralTaxList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CentralTax ct";
			if (yearFrequency != null && !yearFrequency.isEmpty()) {
				query += " where ct.ctfinancialYear = :yearFrequency";
			}
			if (taxTypeId > 0) {
				query += (query.contains("where") ? " and " : " where ") + "ct.sourceleadTaxType.id=:taxTypeId";
			}
			Query queryObj = session.createQuery(query);
			if (yearFrequency != null && !yearFrequency.isEmpty()) {
				queryObj.setString("yearFrequency", yearFrequency);
			}
			if (taxTypeId > 0) {
				queryObj.setLong("taxTypeId", taxTypeId);
			}
			centralTaxList = queryObj.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return centralTaxList;
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

	@SuppressWarnings("unchecked")
	@Override
	public SourceleadTaxForm getTaxFormType(String uuid) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SourceleadTaxForm st where st.uuid = :uuid";
			Query queryObj = session.createQuery(query);
			queryObj.setString("uuid", uuid);
			List<SourceleadTaxForm> stList = queryObj.list();
			if (stList != null && !stList.isEmpty())
				return stList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SourceleadTaxITDeclaration getTaxSection(long taxSectionId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(SourceleadTaxITDeclaration.class, taxSectionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SourceleadTaxChallan getTaxChallanSection(long taxSectionId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(SourceleadTaxChallan.class, taxSectionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public long addITDeclarationSection(SourceleadTaxITDeclaration taxSection) {
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
	public SourceleadTaxITDeclarationItem getTaxSectionItem(long taxSectionItemId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(SourceleadTaxITDeclarationItem.class, taxSectionItemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public long addITDeclarationSectionItem(SourceleadTaxITDeclarationItem taxSectionItem) {
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
	public long changeTaxFormStatus(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SourceleadTaxForm sourceleadTaxForm = session.get(SourceleadTaxForm.class, id);
			if (sourceleadTaxForm != null) {
				sourceleadTaxForm.setActive(!sourceleadTaxForm.isActive());
				return sourceleadTaxForm.getId();
			} else
				return -2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public long changeCentralTaxStatus(long centralTaxId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			CentralTax centralTax = session.get(CentralTax.class, centralTaxId);
			if (centralTax != null) {
				centralTax.setActive(!centralTax.isActive());
				return centralTax.getId();

			} else
				return -2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public long changeITDeclarationSectionStatus(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SourceleadTaxITDeclaration sourceleadTaxSection = session.get(SourceleadTaxITDeclaration.class, id);
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
			SourceleadTaxITDeclarationItem sourceleadTaxSectionItem = session.get(SourceleadTaxITDeclarationItem.class,
					id);
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

	public boolean removeTDSTax(Tds tds) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.delete(tds);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Tds getTDSById(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(Tds.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long submitITDeclaration(SourceleadTaxITDeclarationSubmission itDeclarationSubmission) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(itDeclarationSubmission);
			return itDeclarationSubmission.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getITDeclarationFormsCountByYear(String financialYear, String tableName, long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SourceleadTax" + tableName + "Submission it where it.orgLocations.id=:orgLocationId and it.financialYear=:financialYear";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setString("financialYear", financialYear + "");
			List<Object> submissionList = queryObj.list();
			if (submissionList != null && !submissionList.isEmpty())
				return submissionList.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SourceleadTaxITDeclarationSubmission> getITDeclarationSubmissionList(long orgLocationId, String uuid,
			String financialYear, String searchValue, Paginator paginator) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SourceleadTaxITDeclarationSubmission it where it.orgLocations.id=:orgLocationId";
			if (!uuid.isEmpty()) {
				query += (query.contains("where") ? " and " : " where ") + "it.sourceleadTaxForm.uuid=:uuid";
			}
			if (!financialYear.isEmpty()) {
				query += (query.contains("where") ? " and " : " where ") + "it.financialYear=:financialYear";
			}
			if (searchValue != null && !searchValue.isEmpty()) {
				query += (query.contains("where") ? " and " : " where ") + "(it.user.firstName like '%" + searchValue + "%' or it.user.lastName like '%" + searchValue + "%')";
			}
			query += " order by it.id desc";
			Query queryObj = session.createQuery(query);
			List<String> params = new LinkedList<>();
			queryObj.setLong("orgLocationId", orgLocationId);
			params.add("long;orgLocationId;" + orgLocationId);
			if (!uuid.isEmpty()) {
				queryObj.setString("uuid", uuid);
				params.add("String;uuid;" + uuid);
			}
			if (!financialYear.isEmpty()) {
				queryObj.setString("financialYear", financialYear);
				params.add("String;financialYear;" + financialYear);
			}
			if (paginator != null) {
				queryObj.setFirstResult(paginator.getPageNo() * paginator.getPageSize());
				queryObj.setMaxResults(paginator.getPageSize());
			}
			List<SourceleadTaxITDeclarationSubmission> itDeclarationSubmissionList = queryObj.list();
			if (paginator != null) {
				paginator.applyPaginationData(query, session, params);
			}
			return itDeclarationSubmissionList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmission(String taxFormUUID, String formUUID) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SourceleadTaxITDeclarationSubmission it where it.sourceleadTaxForm.uuid = :taxFormUUID and it.uuid = :formUUID";
			Query queryObj = session.createQuery(query);
			queryObj.setString("taxFormUUID", taxFormUUID);
			queryObj.setString("formUUID", formUUID);
			List<SourceleadTaxITDeclarationSubmission> itDeclarationSubmissionList = queryObj.list();
			if (itDeclarationSubmissionList != null && !itDeclarationSubmissionList.isEmpty()) {
				return itDeclarationSubmissionList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmission(long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(SourceleadTaxITDeclarationSubmission.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * @Override public SourceleadTaxITDeclarationSubmissionItem
	 * getITDeclarationSubmissionItem(long id) { try { Session session =
	 * this.sessionFactory.getCurrentSession(); return
	 * session.get(SourceleadTaxITDeclarationSubmissionItem.class, id); } catch
	 * (Exception e) { e.printStackTrace(); } return null; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmission(long userId, String financialYear) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SourceleadTaxITDeclarationSubmission itDeclarationSubmission where itDeclarationSubmission.user.id=:userId and itDeclarationSubmission.financialYear=:financialYear";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("userId", userId);
			queryObj.setString("financialYear", financialYear);
			List<SourceleadTaxITDeclarationSubmission> itDeclarationSubmissionList = queryObj.list();
			if (itDeclarationSubmissionList != null && !itDeclarationSubmissionList.isEmpty()) {
				return itDeclarationSubmissionList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean saveDocument(SourceleadTaxSubmissionDocument taxSubmissionDocument) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(taxSubmissionDocument);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	@Override
	public void deleteDocumentAttachment(long attachmentId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SourceleadTaxSubmissionDocumentAttachment documentAttachment = session
					.get(SourceleadTaxSubmissionDocumentAttachment.class, attachmentId);
			if (documentAttachment != null) {
				session.delete(documentAttachment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteDocument(long documentId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SourceleadTaxSubmissionDocument document = session
					.get(SourceleadTaxSubmissionDocument.class, documentId);
			if (document != null) {
				session.delete(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SourceleadTaxSubmissionDocument> getUploadedDocumentsBySubmissionId(long submissionId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SourceleadTaxSubmissionDocument doc where doc.sourceleadTaxITDeclarationSubmission.id = :submissionId";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("submissionId", submissionId);
			return queryObj.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SourceleadTaxSubmissionDocument getSourceleadTaxSubmissionDocumentById(long submissionDocumentId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(SourceleadTaxSubmissionDocument.class, submissionDocumentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void removeTaxSubmissionDocumentAttachment(SourceleadTaxSubmissionDocumentAttachment attachment) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.delete(attachment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public long addITChallanSectionItem(SourceleadTaxChallanItem sectionItem) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(sectionItem);
			return sectionItem.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	@Transactional
	public long addItChallanSection(SourceleadTaxChallan section) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(section);
			return section.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	@Transactional
	public SourceleadTaxChallanItem getSourceleadTaxChallanItemById(long addChallanSectionItemId) {
		SourceleadTaxChallanItem sourceleadTaxChallanItem = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			sourceleadTaxChallanItem = (SourceleadTaxChallanItem) session.get(SourceleadTaxChallanItem.class,
					addChallanSectionItemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceleadTaxChallanItem;
	}

	@Override
	public long addSourceleadTaxChallanItemSubFormField(SourceleadTaxChallanItemSubFormField challanSubField) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(challanSubField);
			return challanSubField.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public SourceleadTaxChallanItem getChallanItemDetails(Long itemRowId) {
		SourceleadTaxChallanItem ChallanItemDetails = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			ChallanItemDetails = (SourceleadTaxChallanItem) session.get(SourceleadTaxChallanItem.class, itemRowId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ChallanItemDetails;
	}

	@Override
	public SourceleadTaxChallanItem getITChallanSectionItem(Long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SourceleadTaxChallanItem sourceleadTaxChallanItem = (SourceleadTaxChallanItem) session
					.get(SourceleadTaxChallanItem.class, id);
			return sourceleadTaxChallanItem;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	

	
	
	@Override
	public long changeChallanItemStatus(long itemId) {
    try {
    	Session session = this.sessionFactory.getCurrentSession();
    	SourceleadTaxChallanItem sourceleadTaxChallanItem = session.get(SourceleadTaxChallanItem.class, itemId);
		if(sourceleadTaxChallanItem != null){
			sourceleadTaxChallanItem.setActive(!sourceleadTaxChallanItem.isActive());
			session.saveOrUpdate(sourceleadTaxChallanItem);
			return sourceleadTaxChallanItem.getId();
		}else
			return -2;
	} catch (Exception e) {
		e.printStackTrace();
		}
		return -1;
	}

	@Override
	public long changeSectionStatus(long sectionId) {
        try {
			Session session = this.sessionFactory.getCurrentSession();
			SourceleadTaxChallan sourceleadTaxChallan = session.get(SourceleadTaxChallan.class, sectionId);
			if(sourceleadTaxChallan != null){
				sourceleadTaxChallan.setActive(!sourceleadTaxChallan.isActive());
				session.saveOrUpdate(sourceleadTaxChallan);
				return sourceleadTaxChallan.getId();
			}else
				return -2;
		} catch (Exception e) {
			e.printStackTrace();
			}
			return -1;
		}

	@Override
	public long changeITDeclarationSubmissionStatus(long id, int status) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SourceleadTaxITDeclarationSubmission itDeclarationSubmission = session.get(SourceleadTaxITDeclarationSubmission.class, id);
			if (itDeclarationSubmission != null) {
				itDeclarationSubmission.setStatus(status);
				return itDeclarationSubmission.getId();
			} else
				return -2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmissionDetails(long formId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			SourceleadTaxITDeclarationSubmission itDeclarationSubmissionDetails = session.get(SourceleadTaxITDeclarationSubmission.class, formId);
			return itDeclarationSubmissionDetails;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	@Override
	public User getEmployeeDetails(Long id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			User userDetails = session.get(User.class, id);
			return userDetails;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<SourceleadTaxSubmissionMaster> getTaxSubmissionMasterList(Long userId, String financialYear,
			Paginator paginator) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from SourceleadTaxSubmissionMaster submissionMaster where submissionMaster.user.id=:userId";
			if(financialYear!=null && !financialYear.isEmpty()) {
				query += " and submissionMaster.financialYear=:financialYear";
			}
			query += " order by submissionMaster.id desc";
			List<String> params = new LinkedList<>();
			Query queryObj = session.createQuery(query);
			queryObj.setLong("userId", userId);
			params.add("long;userId;" + userId);
			if (financialYear != null && !financialYear.isEmpty()) {
				queryObj.setString("financialYear", financialYear);
				params.add("String;financialYear;" + financialYear);
			}
			if (paginator != null) {
				queryObj.setFirstResult(paginator.getPageNo() * paginator.getPageSize());
				queryObj.setMaxResults(paginator.getPageSize());
			}
			List<SourceleadTaxSubmissionMaster> taxSubmissionMasterList = queryObj.list();
			if (paginator != null) {
				paginator.applyPaginationData(query, session, params);
			}
			return taxSubmissionMasterList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveTaxSubmissionMaster(SourceleadTaxSubmissionMaster sourceleadTaxSubmissionMaster) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(sourceleadTaxSubmissionMaster);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tds> getTDSListByFinancialYear(String financialYear) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Tds tds where tds.centraltaxid.ctfinancialYear = :financialYear";
			Query queryObj = session.createQuery(query);
			queryObj.setString("financialYear", financialYear);
			List<Tds> tdsList = queryObj.list();
			if (tdsList != null && !tdsList.isEmpty() && tdsList.size()>0) {
				return tdsList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SourceleadTaxITDeclarationSubmission getITDeclarationSubmissionByUserIdOrgLocationAndFinancialYear(Long userId, Long orgLocationId, String financialYear) {
		try {
				Session session = this.sessionFactory.getCurrentSession();
				String query = "from SourceleadTaxITDeclarationSubmission it where it.orgLocations.id=:orgLocationId and it.financialYear=:financialYear and it.user.id=:userId ";
				Query queryObj = session.createQuery(query);
				queryObj.setLong("userId", userId);
				queryObj.setLong("orgLocationId", orgLocationId);
				queryObj.setString("financialYear", financialYear);
				List<SourceleadTaxITDeclarationSubmission> itDeclarationSubmissionList = queryObj.list();
				if (itDeclarationSubmissionList != null && !itDeclarationSubmissionList.isEmpty()) {
					return itDeclarationSubmissionList.get(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}


}
