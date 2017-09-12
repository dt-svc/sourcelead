package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.payroll.entity.Checks;

@Repository
public class InvoiceDaoImpl implements InvoiceDao {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public Invoice saveInvoce(Invoice invoice) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (invoice != null) {
				session.saveOrUpdate(invoice);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return invoice;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public ResponseList<Invoice> invoiceListByOrgLocationId(Long orgLocationId,Date startDateUTC,Date endDateUTC,InvoiceDTO invoiceDTO) {
		Session session = this.sessionFactory.getCurrentSession();
		
			
			String[] invoiceTokens = invoiceDTO.getInvoiceStatus().split(",");
			String[] paymentTokens = invoiceDTO.getPaymentStatus().split(",");
			
			String subQuery = "";
			String subQuery1 = "";
			
			if (Arrays.asList(invoiceTokens).contains("DRAFT")) {
				subQuery = subQuery + "invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus = 'DRAFT' or ";
			}
			if (Arrays.asList(invoiceTokens).contains("SENT")) {
				subQuery = subQuery + "invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus = 'SENT' or ";
			}
			if (Arrays.asList(invoiceTokens).contains("RECEIVED")) {
				subQuery = subQuery + "invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus = 'Received' or ";
			}
			if (Arrays.asList(invoiceTokens).contains("ACCEPTED")) {
				subQuery = subQuery + "invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus = 'Accepted' or ";
			}
			if (Arrays.asList(invoiceTokens).contains("REJECTED")) {
				subQuery = subQuery + "invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus = 'Rejected' or ";
			}
			if (Arrays.asList(invoiceTokens).contains("LINKED")) {
				subQuery = subQuery + "invoice.invoiceStatus = 'Linked' or ";
			} 
			if ((invoiceTokens[0] != null && invoiceTokens[0].length() > 0)) {
				subQuery = "and (" + subQuery.substring(0, subQuery.length() - 4) + ")";
			} 
			
			if (Arrays.asList(paymentTokens).contains("Unpaid")) {
				subQuery1 = subQuery1 + "invoice.paymentStatus = 'Unpaid' or ";
			}
			if (Arrays.asList(paymentTokens).contains("Partial Paid")) {
				subQuery1 = subQuery1 + "invoice.paymentStatus = 'Partial Paid' or ";
			}
			if (Arrays.asList(paymentTokens).contains("Paid")) {
				subQuery1 = subQuery1 + "invoice.paymentStatus = 'Paid' or ";
			}
			if (Arrays.asList(paymentTokens).contains("Overdue")) {
				subQuery1 = subQuery1 + "invoice.paymentStatus = 'Overdue' or ";
			}
			if ((paymentTokens[0] != null && paymentTokens[0].length() > 0)) {
				subQuery1 = "and (" + subQuery1.substring(0, subQuery1.length() - 4) + ")";
			} 
			
		
		String subQuery2 = "";
		
		if(invoiceDTO.getInvoiceType().equalsIgnoreCase("payables")){
			subQuery2 = " and invoice.invoiceType = 'Payables' ";
		}else{
			subQuery2 = " and invoice.invoiceType = 'Receivable' ";
		}
		
		String subQuery3 = "";
		if("".equalsIgnoreCase(invoiceDTO.getInvoiceStatus()) || invoiceDTO.getInvoiceStatus() == null || invoiceDTO.getInvoiceStatus().equalsIgnoreCase("undefined")){
			subQuery3 = " and invoice.invoiceStatus <> 'Linked' ";
		}
		
		List<Invoice> invoiceList = null;
		ResponseList<Invoice> response = new ResponseList<>();
		String query = "from Invoice invoice  left join fetch invoice.projectMemberId pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user "
		 		+ "left join fetch invoice.expensesId left join fetch invoice.projectID where  invoice.orgLocation.id =:orgLocationId and invoice.status = '1' "+subQuery+" "+subQuery1+" "+subQuery2+" "+subQuery3+" and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC";
		String countQuery = "";
		if(invoiceDTO.getInvoiceType().equalsIgnoreCase(Constants.RECEIVABLE)){
			countQuery = "from Invoice invoice  where invoice.orgLocation.id =:orgLocationId and invoice.invoiceType='Receivable'";
		}else{
		    countQuery = "from Invoice invoice  where invoice.orgLocation.id =:orgLocationId and invoice.invoiceType='payables'";
		}
		
		if(invoiceDTO.getSearchValue().contains("/")){
			invoiceDTO.getSearchValue().replaceAll("/", "-");
		}
		StringBuilder query1 = new StringBuilder("");
			if(invoiceDTO.getSearchValue() !=null && !"".equalsIgnoreCase(invoiceDTO.getSearchValue())) {
				query1.append(" and (invoice.invoiceId like '%" + invoiceDTO.getSearchValue() + "%' ")
				.append("OR invoice.responderLocationId.orgLocationName like '%" + invoiceDTO.getSearchValue() + "%' ")
				.append("OR invoice.orgLocation.orgLocationName like '%" + invoiceDTO.getSearchValue() + "%' ")
				.append("OR invoice.contactUser.firstName like '%" + invoiceDTO.getSearchValue() + "%' ")
				.append("OR invoice.contactUser.lastName like '%" + invoiceDTO.getSearchValue() + "%' ")
				.append("OR invoice.projectID.projectName like '%" + invoiceDTO.getSearchValue() + "%' ")
				.append("OR invoice.invoiceDate like '%" + invoiceDTO.getSearchValue() + "%' ")
				.append("OR invoice.total like '%" + invoiceDTO.getSearchValue() + "%' ")
				.append("OR invoice.dueAmount like '%" + invoiceDTO.getSearchValue() + "%' ")
				.append("OR invoice.dueDate like '%" + invoiceDTO.getSearchValue() + "%' ")
				.append("OR invoice.invoiceStatus like '%" + invoiceDTO.getSearchValue() + "%' ")
				.append("OR invoice.paymentStatus like '%" + invoiceDTO.getSearchValue() + "%' )");
						
			}
		Query queryObj = session.createQuery(query + query1.toString() + "  ORDER BY invoice.id DESC");
		if (invoiceDTO != null && invoiceDTO.getLength() > 0) {
			queryObj.setFirstResult(invoiceDTO.getStart());
			queryObj.setMaxResults(invoiceDTO.getLength());
//			queryObj.setLong("orgLocationId", orgLocationId).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).setCacheable(true).list();
		}
		invoiceList = queryObj.setLong("orgLocationId", orgLocationId).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).setCacheable(true).list();
		int count = 0;
		int filteredCount = 0;
		List<Long> countlist = session.createQuery("select count(*) " + countQuery).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		if(countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (invoiceDTO != null) {
			List<Long> filteredCountlist  = session.createQuery("select count(*) " + countQuery + query1.toString()).setLong("orgLocationId", orgLocationId).list();
			if(filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		} else {
			response.setRecordsFiltered(filteredCount);
		}

			response.setData(invoiceList);
			return response;
		
		
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,String contractType) throws Exception
	{
		List<Contracts> contractsList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Contracts contract left join fetch contract.requirementSubmission submission left join fetch submission.requirement where contract.orgLocationId.id = :orgLocationId and "
					+ "contract.orgRelations.id = :orgRelationId and submission.candidate.id = :candidateId and contract.contractType = :contractType and contract.templateSubmissionStatus = 'FINALIZED'";
		     contractsList = session.createQuery(query.toString()).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).setString("contractType", contractType).setCacheable(true).list();
		  if(null != contractsList){
			  return contractsList;
		  }
		  return contractsList;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return contractsList;
		
		 
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Invoice getInvoicetById(Long invoiceId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> invoice = session.createQuery("from Invoice invoice  where invoice.id =:invoiceId" ).setLong( "invoiceId", invoiceId ).setCacheable(true).list();
		if (invoice.size() > 0) {
			return invoice.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional
	public List<Invoice> getViewInvoiceListByInvoiceIdAndOrglocationId(Long invoiceId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Invoice invoice  where invoice.id = :invoiceId and invoice.orgLocation.id = :orgLocationId ";
		List<Invoice> viewInvoiceList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return viewInvoiceList;
}
	@Override 
	@Transactional(rollbackFor = { Exception.class })
	public Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> invoice = session.createQuery("from Invoice invoice  where invoice.invoiceUUID =:invoiceUUID" ).setString( "invoiceUUID", invoiceUUID ).setCacheable(true).list();
		if (invoice.size() > 0) {
			return invoice.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Invoice updateInvoice(Invoice invoice) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (invoice != null) {
				session.saveOrUpdate(invoice);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return invoice;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Invoice getInvoicetByParentInvoiceId(Long parentInvoiceId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> parentInvoice = session.createQuery("from Invoice invoice  where invoice.id =:parentInvoiceId" ).setLong( "parentInvoiceId", parentInvoiceId ).setCacheable(true).list();
		if (parentInvoice.size() > 0) {
			return parentInvoice.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Invoice getInvoicetBySelfJoinInvoiceId(Long parentInvoiceId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> parentInvoice = session.createQuery("from Invoice invoice  where invoice.parentInvoiceId =:parentInvoiceId" ).setLong( "parentInvoiceId", parentInvoiceId ).setCacheable(true).list();
		if (parentInvoice.size() > 0) {
			return parentInvoice.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Invoice> getInvoiceByOrgLocationId(Long orgLocationId,Date startDateUTC, Date endDateUTC,String invoiceType) throws Exception
	{
		String subQuery = "";
		Session session = this.sessionFactory.getCurrentSession();
		
		Map<String, Object> params = new HashMap<String,Object>();
		if(null != orgLocationId){
			params.put("orgLocationId", orgLocationId);
			params.put("invoiceType",invoiceType);
		}
		if(null != startDateUTC && null != endDateUTC){
			params.put("startDateUTC", startDateUTC);
			params.put("endDateUTC", endDateUTC);
			subQuery = "and invoice.invoiceDate >= :startDateUTC and invoice.invoiceDate <= :endDateUTC";
		}
		String query = "from Invoice invoice  where invoice.orgLocation =:orgLocationId and invoice.invoiceType = :invoiceType and invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus <> 'Rejected' "+subQuery;
		
		Query qry = session.createQuery(query);
		for (String str : qry.getNamedParameters())
        {
			if("orgLocationId".equalsIgnoreCase(str)){
				qry.setLong(str, (long) params.get("orgLocationId"));
			}
			if("startDateUTC".equalsIgnoreCase(str)){
				qry.setParameter(str, (Date) params.get("startDateUTC"));
			}
			if("endDateUTC".equalsIgnoreCase(str)){
				qry.setParameter(str, (Date) params.get("endDateUTC"));
			}
			if("invoiceType".equalsIgnoreCase(str)){
				qry.setString(str, (String) params.get("invoiceType"));
			}
        }
		return qry.setCacheable(true).list();
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Invoice> getClientBasedInvoiceLists(Long orgRelationId,Long orgLocationId,String invoiceType)
	{
		String subQuery = "";
		if(invoiceType.equalsIgnoreCase("payables")){
			subQuery = " and invoice.invoiceType = 'Payables' ";
		}else{
			subQuery = " and invoice.invoiceType = 'Receivable' ";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Invoice invoice  where invoice.orgRelationID.id = :orgRelationId and invoice.orgLocation.id = :orgLocationId and invoice.linkInvoiceReferenceId IS NULL and invoice.invoiceStatus <> 'Linked'"+subQuery+"";
		List<Invoice> clientInvoiceList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).setCacheable(true).list();
		return clientInvoiceList;

	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Invoice> getInvoiceByOrgRelationId(Long orgRelationId,Long clientLocationId,Date startDateUTC, Date endDateUTC,String invoiceStatus, String paymentStatus,String invoiceType)
	{
		String[] invoiceTokens = invoiceStatus.split(",");
		String[] paymentTokens = paymentStatus.split(",");
		
		String subQuery = "";
		String subQuery1 = "";
		if (Arrays.asList(invoiceTokens).contains("DRAFT")) {
			subQuery = subQuery + "invoice.invoiceStatus = 'DRAFT' or ";
		}
		if (Arrays.asList(invoiceTokens).contains("SENT")) {
			subQuery = subQuery + "invoice.invoiceStatus = 'SENT' or ";
		}
		if (Arrays.asList(invoiceTokens).contains("RECEIVED")) {
			subQuery = subQuery + "invoice.invoiceStatus = 'Received' or ";
		}
		if (Arrays.asList(invoiceTokens).contains("ACCEPTED")) {
			subQuery = subQuery + "invoice.invoiceStatus = 'Accepted' or ";
		}
		if (Arrays.asList(invoiceTokens).contains("REJECTED")) {
			subQuery = subQuery + "invoice.invoiceStatus = 'Rejected' or ";
		}
		if (Arrays.asList(invoiceTokens).contains("LINKED")) {
			subQuery = subQuery + "invoice.invoiceStatus = 'Linked' or ";
		}
		
		if ((invoiceTokens[0] != null && invoiceTokens[0].length() > 0)) {
			subQuery = "and (" + subQuery.substring(0, subQuery.length() - 4) + ")";
		} 
		
		if (Arrays.asList(paymentTokens).contains("Unpaid")) {
			subQuery1 = subQuery1 + "invoice.paymentStatus = 'Unpaid' or ";
		}
		if (Arrays.asList(paymentTokens).contains("Partial Paid")) {
			subQuery1 = subQuery1 + "invoice.paymentStatus = 'Partial Paid' or ";
		}
		if (Arrays.asList(paymentTokens).contains("Paid")) {
			subQuery1 = subQuery1 + "invoice.paymentStatus = 'Paid' or ";
		}
		if (Arrays.asList(paymentTokens).contains("OverDue")) {
			subQuery1 = subQuery1 + "invoice.paymentStatus = 'OverDue' or ";
		}
		
		if ((paymentTokens[0] != null && paymentTokens[0].length() > 0)) {
			subQuery1 = "and (" + subQuery1.substring(0, subQuery1.length() - 4) + ")";
		} 
		
		String subQuery2 = "";
		String subQuery3 = "";
		if(invoiceType.equalsIgnoreCase("payables")){
			subQuery2 = " and invoice.invoiceType = 'Payables' ";
		}else{
			subQuery3 = " and invoice.invoiceType = 'Receivable' ";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		String query =null;
		if(invoiceType.equalsIgnoreCase("payables")){
		 query = "from Invoice invoice  where invoice.orgRelationID =:orgRelationId and invoice.responderLocationId =:clientLocationId and invoice.status = '1' "+subQuery+" "+subQuery1+" "+subQuery2+" and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC";
		}else{
		 query = "from Invoice invoice  where invoice.orgRelationID =:orgRelationId and invoice.responderLocationId =:clientLocationId and invoice.status = '1' "+subQuery+" "+subQuery1+" "+subQuery3+" and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC";
		}
		List<Invoice> invoiceList = session.createQuery(query).setLong( "clientLocationId", clientLocationId ).setLong( "orgRelationId", orgRelationId ).setParameter("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).setCacheable(true).list();
		return invoiceList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Invoice> getInvoiceByOrgLocationIdNetIncome(Long orgLocationId, Date startDateUTC, Date endDateUTC) throws Exception
	{
		String subQuery = "";
		Session session = this.sessionFactory.getCurrentSession();
		
		Map<String, Object> params = new HashMap<String,Object>();
		if(null != orgLocationId){
			params.put("orgLocationId", orgLocationId);
		}
		if(null != startDateUTC && null != endDateUTC){
			params.put("startDateUTC", startDateUTC);
			params.put("endDateUTC", endDateUTC);
			subQuery = "and invoice.invoiceDate >= :startDateUTC and invoice.invoiceDate <= :endDateUTC";
		}
		String query = "from Invoice invoice  where invoice.orgLocation =:orgLocationId and invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus <> 'Rejected' "+subQuery;
		
		Query qry = session.createQuery(query);
		for (String str : qry.getNamedParameters())
        {
			if("orgLocationId".equalsIgnoreCase(str)){
				qry.setLong(str, (long) params.get("orgLocationId"));
			}
			if("startDateUTC".equalsIgnoreCase(str)){
				qry.setParameter(str, (Date) params.get("startDateUTC"));
			}
			if("endDateUTC".equalsIgnoreCase(str)){
				qry.setParameter(str, (Date) params.get("endDateUTC"));
			}
        }
		return qry.setCacheable(true).list();
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<Invoice> getDueDateCompletedInvoicesList() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Invoice invoice  where invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus <> 'Rejected' and invoice.invoiceStatus <> 'DRAFT' and invoice.paymentStatus <> 'Paid'";
		@SuppressWarnings("unchecked")
		List<Invoice> clientInvoiceList = session.createQuery(query.toString()).setCacheable(true).list();
		return clientInvoiceList;
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<InvoicePayment> getPaymentHistroyByInvoiceId(Long invoiceId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from InvoicePayment invoicePayment left join fetch invoicePayment.invoiceId where invoicePayment.invoiceId = :invoiceId";
		List<InvoicePayment> invoicePaymentList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId).setCacheable(true).list();
		return invoicePaymentList;
		
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Invoice getInvoicetByParentId(Long parentInvoiceId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> parentInvoice = session.createQuery("from Invoice invoice  where invoice.parentInvoiceId.id =:parentInvoiceId" ).setLong( "parentInvoiceId", parentInvoiceId ).setCacheable(true).list();
		if (parentInvoice.size() > 0) {
			return parentInvoice.get(0);
		} else {
			return null;
		}
	}


	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Invoice getParentInvoiceByInvoiceId(Long invoiceId) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> parentInvoice = session.createQuery("from Invoice invoice  where invoice.parentInvoiceId =:invoiceId" ).setLong( "invoiceId", invoiceId ).setCacheable(true).list();
		if (parentInvoice.size() > 0) {
			return parentInvoice.get(0);
		} else {
			return null;
		}
	}
	 
}
