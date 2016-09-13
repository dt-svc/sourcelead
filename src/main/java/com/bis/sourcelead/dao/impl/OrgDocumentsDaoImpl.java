package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgDocumentsDao;
import com.bis.sourcelead.entity.OrgDocuments;

/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */
@Repository
public class OrgDocumentsDaoImpl implements OrgDocumentsDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 *  to save the documents of the client and vendors
	 * @author Prasanna Vadla
	 *
	 */
	@Override
	public void saveDocuments() {
		
		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgDocuments> getClientDocumentsListByResponderLocationIdAndOrgLocationID(Long responderLocationId, Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from OrgDocuments od left join fetch od.spocUser left join fetch od.ownerUser where od.locationId =:orgLocationId and od.responderLocationId.id =:responderLocationId ";  
		List<OrgDocuments> orgDocuments = session.createQuery(query).setLong("responderLocationId", responderLocationId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return orgDocuments;	
	}
	
	


}
