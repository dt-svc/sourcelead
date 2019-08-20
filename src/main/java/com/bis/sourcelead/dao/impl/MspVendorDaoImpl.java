package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.MspVendorDao;
import com.bis.sourcelead.dto.MspVendorDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.entity.MspVendor;
import com.bis.sourcelead.entity.MspVendorHistory;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class MspVendorDaoImpl implements MspVendorDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	private static final Logger logger = LoggerFactory.getLogger(MspVendorDaoImpl.class);


	@Override
	public MspVendor saveMspVendor(MspVendor mspVendor) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (mspVendor != null) {
				session.saveOrUpdate(mspVendor);
			}
			logger.debug("Saving MspVendor");
		} catch (Exception exception) {
			exception.printStackTrace();
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"MspVendor"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return mspVendor;
	}


	@Override
	public MspVendor getMspVendorListByOrgLocationIdAndMspRelationId(Long orgLocationId, Long orgRelationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from MspVendor mspVendor where (mspVendor.orgLocationId =:orgLocationId or mspVendor.mspOrgLocationId =:orgLocationId)  and mspVendor.mspRelationId =:orgRelationId ";
		List<MspVendor> mspVendorList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).list();
		MspVendor mspVendor = null;
		if(mspVendorList != null && !mspVendorList.isEmpty())
		{
			mspVendor = (MspVendor) mspVendorList.iterator().next();
		}
		return mspVendor;
	}


	@Override
	public List<MspVendorDTO> getMspClientlistByOrgLocationId(Long mspOrgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " select orgRelations.ID as orgLocationId, org.ORG_NAME clientName from MSP_VENDOR msp, ORG_LOCATIONS org, ORG_RELATIONS orgRelations "
				      + " where msp.ORG_LOCATION_ID = org.ID and msp.MSP_RELATION_ID =orgRelations.ID and msp.MSP_VENDOR_ORG_LOCATION_ID =:mspOrgLocationId";
		List<MspVendorDTO> clientList = session.createSQLQuery(query).setLong("mspOrgLocationId", mspOrgLocationId).setResultTransformer(Transformers.aliasToBean(MspVendorDTO.class)).list();
		return clientList;
	}


	@Override
	public List<OrgRelations> selectedMSPVendorListByOrgLocationId(MspVendorDTO mspVendorDTO) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgRelations orgrelation  where orgrelation.id IN("+String.join(",", mspVendorDTO.getOrgRelationIds())+")");
	       List<OrgRelations> orgRelationsList = session.createQuery(query).list();
	       return orgRelationsList;
	}


	@Override
	public MspVendorHistory saveMspVendorHistory(MspVendorHistory mspVendorHistory) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (mspVendorHistory != null) {
				session.saveOrUpdate(mspVendorHistory);
			}
			logger.debug("Saving MspVendor");
		} catch (Exception exception) {
			exception.printStackTrace();
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"MspVendorHistory"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return mspVendorHistory;
	
	}


	@Override
	public MspVendorHistory getMspVendorHistoryDetailsByMSPRelationIdAndOrgRelationId(Long mpsRealtionId, Long orgRelationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from MspVendorHistory mvh where mvh.mspRelationId =:mpsRealtionId and mvh.orgRelationId =:orgRelationId and mvh.status =1 ";
		List<MspVendorHistory> mspVendorHistoryList = session.createQuery(query).setLong("orgRelationId", orgRelationId).setLong("mpsRealtionId", mpsRealtionId).list();
		MspVendorHistory mspVendorHis = null;
		if(mspVendorHistoryList != null && !mspVendorHistoryList.isEmpty())
		{
			mspVendorHis = (MspVendorHistory) mspVendorHistoryList.iterator().next();
		}
		return mspVendorHis;
	}


	@Override
	public List<MspVendorHistory> mspVendorHistoryListByMspRelationIdMspOrgLocationId(Long mspRelationId, Long mspOrgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from MspVendorHistory mvh left join fetch mvh.orgRelationId where mvh.mspRelationId =:mspRelationId and mvh.mspOrgLocationId =:mspOrgLocationId and mvh.status =1";
		List<MspVendorHistory> mspVendorHistoryList = session.createQuery(query).setLong("mspOrgLocationId", mspOrgLocationId).setLong("mspRelationId", mspRelationId).list();
	return mspVendorHistoryList;
	}

}
