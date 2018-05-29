package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.SourceleadNotesDao;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.SourceleadNotes;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 * @author Ajith Matta
 *
 */
@Repository
public class SourceleadNotesDaoImpl implements SourceleadNotesDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	private static final Logger logger = LoggerFactory.getLogger(SourceleadNotesDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public SourceleadNotes storeComments(SourceleadNotes sourceleadNotes) throws Exception
	{
		logger.debug("Storing SourceleadNotes");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (sourceleadNotes != null) {
				session.saveOrUpdate(sourceleadNotes);
			}
			logger.debug("SourceleadNotes Stored Successfully");
		} catch (Exception exception) {
			StringBuilder message = new StringBuilder(MessageFormat.format(duplicateValue, "SourceleadNotes"));
			throw new SourceLeadConstraintViolationException(message.toString()
					+ exception.getCause().toString());
		}
		return sourceleadNotes;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadNotes> getNotesListByOrglocationIdAndType(Long orgrelation,Long orgLocationId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadNotes sn where  sn.orgRelationID.id =:orgrelation and sn.orgLocationID.id = :orgLocationId ORDER BY sn.id DESC";
		List<SourceleadNotes> notesList = session.createQuery(query).setLong("orgrelation", orgrelation).setLong("orgLocationId", orgLocationId).list();
		return notesList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadNotes> getNotesListByRequirementId(Long requirementId,Long orgLocationId) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadNotes sn where  sn.requirementId.id =:requirementId and sn.orgLocationID.id =:orgLocationId ORDER BY sn.id DESC";
		List<SourceleadNotes> notesList = session.createQuery(query).setLong("requirementId", requirementId).setLong("orgLocationId", orgLocationId).list();
		return notesList;
		
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadNotes> getNotesListByInvoiceID(Long invoiceId,Long orgLocationId) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadNotes sn where  sn.invoicdeId.id =:invoiceId and sn.orgLocationID.id =:orgLocationId ORDER BY sn.id DESC";
		List<SourceleadNotes> notesList = session.createQuery(query).setLong("invoiceId", invoiceId).setLong("orgLocationId", orgLocationId).list();
		return notesList;
		
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadNotes> getNotesListByContractId(Long contractId,Long orgLocationId) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadNotes sn where  sn.contractId.id =:contractId and sn.orgLocationID.id =:orgLocationId ORDER BY sn.id DESC";
		List<SourceleadNotes> notesList = session.createQuery(query).setLong("contractId", contractId).setLong("orgLocationId", orgLocationId).list();
		return notesList;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadNotes> getNotesListByAssetId(Long assetId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadNotes sn where  sn.assetId.id =:assetId and sn.orgLocationID.id =:orgLocationId ORDER BY sn.id DESC";
		List<SourceleadNotes> notesList = session.createQuery(query).setLong("assetId", assetId).setLong("orgLocationId", orgLocationId).list();
		return notesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<SourceleadNotes> getNotesListByImmigrationId(Long immigrationId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from SourceleadNotes sn where  sn.immigration.id =:immigrationId and sn.orgLocationID.id =:orgLocationId ORDER BY sn.id DESC";
		List<SourceleadNotes> notesList = session.createQuery(query).setLong("immigrationId", immigrationId).setLong("orgLocationId", orgLocationId).list();
		return notesList;
	}
}
