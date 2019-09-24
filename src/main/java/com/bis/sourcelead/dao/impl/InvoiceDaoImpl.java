package com.bis.sourcelead.dao.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.dto.CreditDebitNoteDTO;
import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SearchDTO;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.CandidateFamilyDetails;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.CreditDebitLineItems;
import com.bis.sourcelead.entity.CreditDebitNote;
import com.bis.sourcelead.entity.CustomInvoiceTemplate;
import com.bis.sourcelead.entity.DashboardItems;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.InvoiceTimelineHistory;
import com.bis.sourcelead.entity.OrgLocations;

@Repository
public class InvoiceDaoImpl implements InvoiceDao {

	private static final Logger logger = LoggerFactory.getLogger(InvoiceDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${jdbc.schema}")
	private String schema;

	@Override
	@Transactional
	public CreditDebitNote saveCreditDebitNote(CreditDebitNote creditDebitNote) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (creditDebitNote != null) {
				// for new Record increment generatedId based on orglocationId
				if (creditDebitNote.getIsNewRecord() !=null && creditDebitNote.getIsNewRecord().equalsIgnoreCase("Y") ) {
					Integer maxValue = 1;
					String type = null;
					if(creditDebitNote.getNoteType().equals(Constants.CREDIT_NOTE)){
					type = Constants.CREDIT_ENTITY_SHORTCODE;	
					}else{
					type=Constants.DEBIT_ENTITY_SHORTCODE;	
					}
					String generatedId = checkCreditDebitIdNumberInDatebase(creditDebitNote.getOrgLocations().getId(),type);
					if (generatedId != null) {
						maxValue = Integer.parseInt(generatedId.substring(generatedId.lastIndexOf("-") + 1));
							maxValue = maxValue + 1;
							creditDebitNote.setGeneratedId(creditDebitNote.getGeneratedId().concat(maxValue.toString()));
					} else {
						creditDebitNote.setGeneratedId(creditDebitNote.getGeneratedId().concat(maxValue.toString()));
					}
					session.saveOrUpdate(creditDebitNote);
				}else{
					session.saveOrUpdate(creditDebitNote);	
				}
				
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return creditDebitNote;
	}
	
	@Override
	@Transactional
	public Invoice saveInvoce(Invoice invoice) throws Exception {
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
	@Transactional
	public Boolean getInvoiceByInvoiceId(String invoiceId, Long orgLocationsId, String invoiceType) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		if (invoiceType.equalsIgnoreCase("Payables")) {

			query = "from Invoice invoice where invoice.orgLocation.id = :orgLocationsId and invoice.invoiceId = :invoiceId and invoice.invoiceType = 'Payables'  and invoice.status='1'";
		} else {

			query = "from Invoice invoice where invoice.orgLocation.id = :orgLocationsId and invoice.invoiceId = :invoiceId and invoice.invoiceType = 'Receivable' and invoice.status='1'";

		}
		List<Invoice> invoiceList = session.createQuery(query.toString()).setLong("orgLocationsId", orgLocationsId)
				.setString("invoiceId", invoiceId).list();

		if (invoiceList.size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<Invoice> invoiceListByOrgLocationId(OrgLocations orgLocations, Date startDateUTC,
			Date endDateUTC, InvoiceDTO invoiceDTO) throws Exception {
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
		if (Arrays.asList(invoiceTokens).contains("VOID")) {
			subQuery = subQuery + "invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus = 'Void' or ";
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
		
		String subQuery4 = "";
		if (invoiceDTO.getInvoiceDeleteStatus().equals("ACTIVE")) {
			subQuery4 = " and invoice.status = 1 ";
		}else{
			subQuery4 = " and invoice.status = 0 ";
		}

		String subQuery2 = "";

		if (invoiceDTO.getInvoiceType().equalsIgnoreCase("payables")) {
			subQuery2 = " and invoice.invoiceType = 'Payables' ";
		} else {
			subQuery2 = " and invoice.invoiceType = 'Receivable' ";
		}

		String subQuery3 = "";
		if ("".equalsIgnoreCase(invoiceDTO.getInvoiceStatus()) || invoiceDTO.getInvoiceStatus() == null
				|| invoiceDTO.getInvoiceStatus().equalsIgnoreCase("undefined")) {
			subQuery3 = " and invoice.invoiceStatus <> 'Linked' ";
		}

		List<String> tagsList = new ArrayList<>();
		if (invoiceDTO.getTags() != null && !invoiceDTO.getTags().isEmpty()) {
			String[] tags = invoiceDTO.getTags().split(",");
			for (int i = 0; i < tags.length; i++) {
				tagsList.add(tags[i]);
			}
		} else {
			tagsList.add("");
		}
		String tagsQuery = " and ((:varString  IN (:tagsList)) OR (:varString NOT IN (:tagsList) and invoice.id IN (select st.entityId from SourceleadTags st where st.tagName IN (:tagsList) and st.status ='1' ))) ";

		List<Invoice> invoiceList = null;
		ResponseList<Invoice> response = new ResponseList<>();

		String qry = "";
		if (StringUtils.isNotBlank(invoiceDTO.getSortColumn())) {
			qry = " order by invoice." + invoiceDTO.getSortColumn() + " " + invoiceDTO.getSortOrder();
		}

		String query = "from Invoice invoice  left join fetch  invoice.orgRelationID left join fetch invoice.projectMemberId pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user "
				+ "left join fetch invoice.expensesId left join fetch invoice.projectID where  invoice.orgLocation.id =:orgLocationId "
				+ subQuery + " " + subQuery1 + " " + subQuery2 + " " +subQuery4 + " " + subQuery3 +" "+ tagsQuery
				+ " and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC";
		String countQuery = "";
		if (invoiceDTO.getInvoiceType().equalsIgnoreCase(Constants.RECEIVABLE)) {
			countQuery = "from Invoice invoice  where invoice.orgLocation.id =:orgLocationId and invoice.invoiceType='Receivable' "
					+ subQuery + " " + subQuery1 + " " + subQuery2 + " " +subQuery4 + " " + subQuery3 + " " +subQuery4 + " " + tagsQuery
					+ " and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC";
		} else {
			countQuery = "from Invoice invoice  where invoice.orgLocation.id =:orgLocationId and invoice.invoiceType='payables' "
					+ subQuery + " " + subQuery1 + " " + subQuery2 + " " +subQuery4 + " " + subQuery3 + " " +subQuery4 + " " + tagsQuery
					+ " and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC";
		}

		StringBuilder query1 = new StringBuilder("");
		if (StringUtils.isNotBlank(invoiceDTO.getSearchValue())) {
			if (invoiceDTO.getSearchValue().contains("/") || invoiceDTO.getSearchValue().contains("-")) {
				String convertedDate = invoiceDTO.getSearchValue();
				if (invoiceDTO.getSearchValue().contains("/")) {
					convertedDate = invoiceDTO.getSearchValue().replaceAll("/", "-");
				}
				DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat())
						.toFormatter();
				LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
				DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String newDate = parsedDate.format(f2);
				Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
				java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
				query1.append(" and (invoice.dueDate like '%" + sqlDate + "%'  ")
					  .append("OR invoice.invoiceDate like '%" + sqlDate + "%' )");
			} else {
				if (invoiceDTO.getSearchValue() != null && !"".equalsIgnoreCase(invoiceDTO.getSearchValue())) {
					query1.append(" and (invoice.invoiceId like '%" + invoiceDTO.getSearchValue() + "%' ")
							.append("OR invoice.responderLocationId.orgLocationName like '%" + invoiceDTO.getSearchValue() + "%' ")
							.append("OR invoice.orgLocation.orgLocationName like '%" + invoiceDTO.getSearchValue() + "%' ")
							.append("OR invoice.projectID.projectName like '%" + invoiceDTO.getSearchValue() + "%' ")
							.append("OR invoice.total like '%" + invoiceDTO.getSearchValue() + "%' ")
							.append("OR invoice.dueAmount like '%" + invoiceDTO.getSearchValue() + "%' ")
							.append("OR invoice.invoiceStatus like '%" + invoiceDTO.getSearchValue() + "%' ")
							.append("OR invoice.paymentStatus like '%" + invoiceDTO.getSearchValue() + "%' )");

				}
			}
		}
		Query queryObj = session.createQuery(query + query1 + qry);
		if (invoiceDTO != null && invoiceDTO.getLength() > 0) {
			queryObj.setFirstResult(invoiceDTO.getStart());
			queryObj.setMaxResults(invoiceDTO.getLength());
			// queryObj.setLong("orgLocationId", orgLocationId).setParameter("startDateUTC",
			// startDateUTC).setParameter("endDateUTC", endDateUTC).list();
		}
		invoiceList = queryObj.setLong("orgLocationId", orgLocations.getId()).setParameter("startDateUTC", startDateUTC)
				.setParameter("endDateUTC", endDateUTC).setString("varString", "")
				.setParameterList("tagsList", tagsList).list();
		int count = 0;
		int filteredCount = 0;

		List<Long> countlist = session.createQuery("select count(*) " + countQuery)
				.setLong("orgLocationId", orgLocations.getId()).setParameter("startDateUTC", startDateUTC)
				.setParameter("endDateUTC", endDateUTC).setString("varString", "")
				.setParameterList("tagsList", tagsList).list();

		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (invoiceDTO != null) {
			List<Long> filteredCountlist = session.createQuery("select count(*) " + countQuery + query1)
					.setLong("orgLocationId", orgLocations.getId()).setParameter("startDateUTC", startDateUTC)
					.setParameter("endDateUTC", endDateUTC).setString("varString", "")
					.setParameterList("tagsList", tagsList).list();
			if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		}
		response.setData(invoiceList);
		return response;

	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ResponseList<CreditDebitNote> creditDebtListByOrgLocationId(OrgLocations orgLocations,Date startDateUTC,Date endDateUTC,CreditDebitNoteDTO creditDebitNoteDTO) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String[] noteTokens = creditDebitNoteDTO.getCreditDebitNoteStatus().split(",");

		String subQuery = "";

		if (Arrays.asList(noteTokens).contains("Draft")) {
			subQuery = subQuery + "creditDebitNote.noteStatus = 'Draft' or ";
		}
		if (Arrays.asList(noteTokens).contains("Send")) {
			subQuery = subQuery + "creditDebitNote.noteStatus = 'Send' or ";
		}
		if (Arrays.asList(noteTokens).contains("Received")) {
			subQuery = subQuery + "creditDebitNote.noteStatus = 'Received' or ";
		}
		if (Arrays.asList(noteTokens).contains("Rejected")) {
			subQuery = subQuery + "creditDebitNote.noteStatus = 'Rejected' or ";
		}
		
		if ((noteTokens[0] != null && noteTokens[0].length() > 0)) {
			subQuery = " and (" + subQuery.substring(0, subQuery.length() - 4) + ")";
		}

		
		String subQuery1 = "";
		if (creditDebitNoteDTO.getStatus().equals("ACTIVE")) {
			subQuery1 = " and creditDebitNote.status = 1 ";
		}else{
			subQuery1 = " and creditDebitNote.status = 0 ";
		}

		String subQuery2 = "";

		if (creditDebitNoteDTO.getNoteType().equalsIgnoreCase(Constants.CREDIT_NOTE)) {
			subQuery2 = " and creditDebitNote.noteType = 'Credit' ";
		} else {
			subQuery2 = " and creditDebitNote.noteType = 'Debit' ";
		}


		/*List<String> tagsList = new ArrayList<>();
		if (creditDebitNoteDTO.getTags() != null && !creditDebitNoteDTO.getTags().isEmpty()) {
			String[] tags = creditDebitNoteDTO.getTags().split(",");
			for (int i = 0; i < tags.length; i++) {
				tagsList.add(tags[i]);
			}
		} else {
			tagsList.add("");
		}*/
		//String tagsQuery = " and ((:varString  IN (:tagsList)) OR (:varString NOT IN (:tagsList) and creditDebitNote.id IN (select st.entityId from SourceleadTags st where st.tagName IN (:tagsList) and st.status ='1' ))) ";

		List<CreditDebitNote> notesList = null;
		ResponseList<CreditDebitNote> response = new ResponseList<>();

		String qry = "";
		if (StringUtils.isNotBlank(creditDebitNoteDTO.getSortColumn())) {
			String sortColumnName = creditDebitNoteDTO.getSortColumn();
			if(sortColumnName.equalsIgnoreCase("clientName")){
				qry = " order by creditDebitNote.responderLocation.orgLocationName " + creditDebitNoteDTO.getSortOrder();
			}else{
				qry = " order by creditDebitNote." + sortColumnName + " " + creditDebitNoteDTO.getSortOrder();
			}
		}

		String query = "from CreditDebitNote creditDebitNote  left join fetch  creditDebitNote.orgRelations left join fetch  creditDebitNote.orgRelations where  creditDebitNote.orgLocations.id =:orgLocationId"
				+ subQuery + " " + subQuery1 + " " + subQuery2+ " and creditDebitNote.createdDate >= :startDateUTC and creditDebitNote.createdDate <= :endDateUTC";
				
				
		
		String countQuery = "";
		if (creditDebitNoteDTO.getNoteType().equalsIgnoreCase(Constants.CREDIT_NOTE)) {
			countQuery = "from CreditDebitNote creditDebitNote  where creditDebitNote.orgLocations.id =:orgLocationId and creditDebitNote.noteType='Credit' "
					+ subQuery + " " + subQuery1 + " " + subQuery2+" and creditDebitNote.createdDate >= :startDateUTC and creditDebitNote.createdDate <= :endDateUTC";
		} else {
			countQuery = "from CreditDebitNote creditDebitNote  where creditDebitNote.orgLocations.id =:orgLocationId and creditDebitNote.noteType='Debit'"
					+ subQuery + " " + subQuery1 + " " + subQuery2+" and creditDebitNote.createdDate >= :startDateUTC and creditDebitNote.createdDate <= :endDateUTC";
		}
		
		
		StringBuilder query1 = new StringBuilder("");
		if (StringUtils.isNotBlank(creditDebitNoteDTO.getSearchValue())) {
			if ((creditDebitNoteDTO.getSearchValue().contains("/") || creditDebitNoteDTO.getSearchValue().contains("-")) && (creditDebitNoteDTO.getSearchValue().matches("^[0-9 \\-]+$"))) {
				String convertedDate = creditDebitNoteDTO.getSearchValue();
				if (creditDebitNoteDTO.getSearchValue().contains("/")) {
					convertedDate = creditDebitNoteDTO.getSearchValue().replaceAll("/", "-");
				}
				DateTimeFormatter orgFormat = new DateTimeFormatterBuilder().appendPattern(orgLocations.getDateFormat()).toFormatter();
				LocalDate parsedDate = LocalDate.parse(convertedDate, orgFormat);
				DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String newDate = parsedDate.format(f2);
				Date searchDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDate);
				java.sql.Date sqlDate = new java.sql.Date(searchDate.getTime());
				query1.append(" and (creditDebitNote.creditNoteDate like '%" + sqlDate + "%' )");
			}else{
			
				if (creditDebitNoteDTO.getSearchValue() != null && !"".equalsIgnoreCase(creditDebitNoteDTO.getSearchValue())) {
					query1.append(" and (creditDebitNote.referenceNumber like '%" + creditDebitNoteDTO.getSearchValue() + "%' ")
							.append("OR creditDebitNote.responderLocation.orgLocationName like '%" + creditDebitNoteDTO.getSearchValue() + "%' ")
							.append("OR creditDebitNote.orgLocations.orgLocationName like '%" + creditDebitNoteDTO.getSearchValue() + "%' ")
							.append("OR creditDebitNote.generatedId like '%" + creditDebitNoteDTO.getSearchValue() + "%' ")
							.append("OR creditDebitNote.total like '%" + creditDebitNoteDTO.getSearchValue() + "%' ")
							.append("OR creditDebitNote.date like '%" + creditDebitNoteDTO.getSearchValue() + "%' ")
							.append("OR creditDebitNote.noteStatus like '%" + creditDebitNoteDTO.getSearchValue() + "%' )");

				}
			}
		}
		
		Query queryObj = session.createQuery(query+query1+ qry);
		if (creditDebitNoteDTO != null && creditDebitNoteDTO.getLength() > 0) {
			queryObj.setFirstResult(creditDebitNoteDTO.getStart());
			queryObj.setMaxResults(creditDebitNoteDTO.getLength());
		}                                                                  //set Date used for to set the time as 00:00:00 for the start date comparison.
		notesList = queryObj.setLong("orgLocationId", orgLocations.getId()).setDate("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
		int count = 0;
		int filteredCount = 0;
																														//set Date used for to set the time as 00:00:00 for the start date comparison.
		List<Long> countlist = session.createQuery("select count(*) " + countQuery)	.setLong("orgLocationId", orgLocations.getId()).setDate("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();

		if (countlist != null && !countlist.isEmpty()) {
			count = countlist.iterator().next().intValue();
		}
		response.setRecordsTotal(count);
		if (creditDebitNoteDTO != null) {																											//set Date used for to set the time as 00:00:00 for the start date comparison.
			List<Long> filteredCountlist = session.createQuery("select count(*) " + countQuery+query1).setLong("orgLocationId", orgLocations.getId()).setDate("startDateUTC", startDateUTC).setParameter("endDateUTC", endDateUTC).list();
			if (filteredCountlist != null && !filteredCountlist.isEmpty()) {
				filteredCount = filteredCountlist.iterator().next().intValue();
			}
			response.setRecordsFiltered(filteredCount);
		}
		response.setData(notesList);
		return response;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,
			String contractType) throws Exception {
		List<Contracts> contractsList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from Contracts contract left join fetch contract.requirementSubmission submission left join fetch submission.requirement where contract.orgLocationId.id = :orgLocationId and "
					+ "contract.orgRelations.id = :orgRelationId and contract.candidate.id = :candidateId and contract.contractType = :contractType and contract.templateSubmissionStatus = 'FINALIZED'";
			contractsList = session.createQuery(query.toString()).setLong("candidateId", candidateId)
					.setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId)
					.setString("contractType", contractType).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contractsList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Invoice getInvoicetById(Long invoiceId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> invoice = session.createQuery("from Invoice invoice  where invoice.id =:invoiceId")
				.setLong("invoiceId", invoiceId).list();
		if (invoice.size() > 0) {
			return invoice.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Invoice> getInvoiceListByIdsAndLocationId(List<Long> invoiceIds, Long locationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Invoice invoice  where invoice.id IN (:invoiceIds) and invoice.orgLocation.id = :locationId";
		List<Invoice> invoiceList = session.createQuery(query).setParameter("locationId", locationId)
				.setParameterList("invoiceIds", invoiceIds).list();

		return invoiceList;
	}

	@Override
	@Transactional
	public List<Invoice> getViewInvoiceListByInvoiceIdAndOrglocationId(Long invoiceId, Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Invoice invoice  where invoice.id = :invoiceId and invoice.orgLocation.id = :orgLocationId ";
		List<Invoice> viewInvoiceList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId)
				.setLong("orgLocationId", orgLocationId).list();
		return viewInvoiceList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> invoice = session.createQuery("from Invoice invoice  where invoice.invoiceUUID =:invoiceUUID")
				.setString("invoiceUUID", invoiceUUID).list();
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
		List<Invoice> parentInvoice = session.createQuery("from Invoice invoice  where invoice.id =:parentInvoiceId")
				.setLong("parentInvoiceId", parentInvoiceId).list();
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
		List<Invoice> parentInvoice = session
				.createQuery("from Invoice invoice  where invoice.parentInvoiceId =:parentInvoiceId")
				.setLong("parentInvoiceId", parentInvoiceId).list();
		if (parentInvoice.size() > 0) {
			return parentInvoice.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Invoice> getInvoiceByOrgLocationId(Long orgLocationId, Date startDateUTC, Date endDateUTC,
			String invoiceType) throws Exception {
		String subQuery = "";
		Session session = this.sessionFactory.getCurrentSession();

		Map<String, Object> params = new HashMap<String, Object>();
		if (null != orgLocationId) {
			params.put("orgLocationId", orgLocationId);
			params.put("invoiceType", invoiceType);
		}
		if (null != startDateUTC && null != endDateUTC) {
			params.put("startDateUTC", startDateUTC);
			params.put("endDateUTC", endDateUTC);
			subQuery = "and invoice.invoiceDate >= :startDateUTC and invoice.invoiceDate <= :endDateUTC";
		}
		String query = "from Invoice invoice  where invoice.orgLocation =:orgLocationId and invoice.invoiceType = :invoiceType and invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus <> 'Rejected' "
				+ subQuery;

		Query qry = session.createQuery(query);
		for (String str : qry.getNamedParameters()) {
			if ("orgLocationId".equalsIgnoreCase(str)) {
				qry.setLong(str, (long) params.get("orgLocationId"));
			}
			if ("startDateUTC".equalsIgnoreCase(str)) {
				qry.setParameter(str, (Date) params.get("startDateUTC"));
			}
			if ("endDateUTC".equalsIgnoreCase(str)) {
				qry.setParameter(str, (Date) params.get("endDateUTC"));
			}
			if ("invoiceType".equalsIgnoreCase(str)) {
				qry.setString(str, (String) params.get("invoiceType"));
			}
		}
		return qry.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Invoice> getClientBasedInvoiceLists(Long orgRelationId, Long orgLocationId, String invoiceType) {
		String subQuery = "";
		if (invoiceType.equalsIgnoreCase("payables")) {
			subQuery = " and invoice.invoiceType = 'Payables' ";
		} else {
			subQuery = " and invoice.invoiceType = 'Receivable' ";
		}

		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Invoice invoice  where invoice.orgRelationID.id = :orgRelationId and invoice.orgLocation.id = :orgLocationId and invoice.linkInvoiceReferenceId IS NULL and invoice.status = '1' and invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus <> 'DRAFT' and invoice.invoiceStatus <> 'Rejected'"
				+ subQuery + "";
		List<Invoice> clientInvoiceList = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId)
				.setLong("orgRelationId", orgRelationId).list();
		return clientInvoiceList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Invoice> getInvoiceByOrgRelationId(Long orgRelationId, Long clientLocationId, Date startDateUTC,
			Date endDateUTC, String invoiceStatus, String paymentStatus, String invoiceType) {
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
		if (invoiceType.equalsIgnoreCase("payables")) {
			subQuery2 = " and invoice.invoiceType = 'Payables' ";
		} else {
			subQuery3 = " and invoice.invoiceType = 'Receivable' ";
		}

		String subQuery4 = "";
		if ("".equalsIgnoreCase(invoiceStatus) || invoiceStatus == null
				|| invoiceStatus.equalsIgnoreCase("undefined")) {
			subQuery4 = " and invoice.invoiceStatus <> 'Linked' ";
		}

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		if (invoiceType.equalsIgnoreCase("payables")) {
			query = "from Invoice invoice  where invoice.orgRelationID =:orgRelationId and invoice.responderLocationId =:clientLocationId and invoice.status = '1' "
					+ subQuery + " " + subQuery1 + " " + subQuery2 + " " + subQuery4
					+ " and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC";
		} else {
			query = "from Invoice invoice  where invoice.orgRelationID =:orgRelationId and invoice.responderLocationId =:clientLocationId and invoice.status = '1' "
					+ subQuery + " " + subQuery1 + " " + subQuery3 + " " + subQuery4
					+ " and invoice.createdDate >= :startDateUTC and invoice.createdDate <= :endDateUTC";
		}
		List<Invoice> invoiceList = session.createQuery(query).setLong("clientLocationId", clientLocationId)
				.setLong("orgRelationId", orgRelationId).setParameter("startDateUTC", startDateUTC)
				.setParameter("endDateUTC", endDateUTC).list();
		return invoiceList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Invoice> getInvoiceByOrgLocationIdNetIncome(Long orgLocationId, Date startDateUTC, Date endDateUTC)
			throws Exception {
		String subQuery = "";
		Session session = this.sessionFactory.getCurrentSession();

		Map<String, Object> params = new HashMap<String, Object>();
		if (null != orgLocationId) {
			params.put("orgLocationId", orgLocationId);
		}
		if (null != startDateUTC && null != endDateUTC) {
			params.put("startDateUTC", startDateUTC);
			params.put("endDateUTC", endDateUTC);
			subQuery = "and invoice.invoiceDate >= :startDateUTC and invoice.invoiceDate <= :endDateUTC";
		}
		String query = "from Invoice invoice  where invoice.orgLocation.id =:orgLocationId and invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus <> 'Rejected' "
				+ subQuery;

		Query qry = session.createQuery(query);
		for (String str : qry.getNamedParameters()) {
			if ("orgLocationId".equalsIgnoreCase(str)) {
				qry.setLong(str, (long) params.get("orgLocationId"));
			}
			if ("startDateUTC".equalsIgnoreCase(str)) {
				qry.setParameter(str, (Date) params.get("startDateUTC"));
			}
			if ("endDateUTC".equalsIgnoreCase(str)) {
				qry.setParameter(str, (Date) params.get("endDateUTC"));
			}
		}
		return qry.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Invoice> getDueDateCompletedInvoicesList() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Invoice invoice  where invoice.invoiceStatus <> 'Linked' and invoice.invoiceStatus <> 'Rejected' and invoice.invoiceStatus <> 'DRAFT' and invoice.paymentStatus <> 'Paid'";
		@SuppressWarnings("unchecked")
		List<Invoice> clientInvoiceList = session.createQuery(query.toString()).list();
		return clientInvoiceList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<InvoicePayment> getPaymentHistroyByInvoiceId(Long invoiceId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from InvoicePayment invoicePayment left join fetch invoicePayment.invoiceId where invoicePayment.invoiceId.id = :invoiceId";
		List<InvoicePayment> invoicePaymentList = session.createQuery(query.toString()).setLong("invoiceId", invoiceId)
				.list();
		return invoicePaymentList;

	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Invoice getInvoicetByParentId(Long parentInvoiceId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> parentInvoice = session
				.createQuery("from Invoice invoice  where invoice.parentInvoiceId.id =:parentInvoiceId")
				.setLong("parentInvoiceId", parentInvoiceId).list();
		if (parentInvoice.size() > 0) {
			return parentInvoice.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Invoice getParentInvoiceByInvoiceId(Long invoiceId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Invoice> parentInvoice = session
				.createQuery("from Invoice invoice  where invoice.parentInvoiceId =:invoiceId")
				.setLong("invoiceId", invoiceId).list();
		if (parentInvoice.size() > 0) {
			return parentInvoice.get(0);
		} else {
			return null;
		}
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CreditDebitNote getParentCreditDebitNoteById(Long noteId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CreditDebitNote note  where note.parentCreditDebitNote =:noteId";
		CreditDebitNote parentCreditDebitNote = (CreditDebitNote) session.createQuery(query).setLong("noteId", noteId).uniqueResult();
		return parentCreditDebitNote;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	@SuppressWarnings("unchecked")
	public List<CreditDebitLineItems> getCreditDebitLineItemsListByNoteIdAndLocationId(Long noteId,Long locationId)throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CreditDebitLineItems noteLineItems  where noteLineItems.creditDebitNote.id =:noteId and noteLineItems.orgLocation.id =:locationId";
		List<CreditDebitLineItems> list = session.createQuery(query).setLong("noteId", noteId).setParameter("locationId", locationId).list();
		return list;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	@SuppressWarnings("unchecked")
	public List<Invoice> getInvoiceListUsingMultipleStringIdsAndLocationId(String ids, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String[] idsValues = ids.split(",");
		// convert string array to long array
		Long[] data = new Long[idsValues.length];
		for (int i = 0; i < idsValues.length; i++) {
		  data[i] = Long.valueOf(idsValues[i]);
		}
		
		String query = "from Invoice invoice  where invoice.id IN ( :ids ) and invoice.orgLocation.id =:orgLocationId";
		List<Invoice> list = session.createQuery(query).setParameterList("ids", data).setParameter("orgLocationId", orgLocationId).list();
		return list;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	@SuppressWarnings("unchecked")
	public List<Invoice> getInvoiceListUsingMultipleIds(String ids) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String[] idsValues = ids.split(",");
		// convert string array to long array
		Long[] data = new Long[idsValues.length];
		for (int i = 0; i < idsValues.length; i++) {
		  data[i] = Long.valueOf(idsValues[i]);
		}
		
		String query = "from Invoice invoice  where invoice.id IN ( :ids )";
		List<Invoice> list = session.createQuery(query).setParameterList("ids", data).list();
		return list;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	@SuppressWarnings("unchecked")
	public List<Invoice> getInvoiceListUsingMultipleIdsForChild(String ids) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String[] idsValues = ids.split(",");
		// convert string array to long array
		Long[] data = new Long[idsValues.length];
		for (int i = 0; i < idsValues.length; i++) {
		  data[i] = Long.valueOf(idsValues[i]);
		}
		
		String query = "from Invoice invoice  where invoice.parentInvoiceId.id IN ( :ids )";
		List<Invoice> list = session.createQuery(query).setParameterList("ids", data).list();
		return list;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CreditDebitNote getCreditDebitNoteByUUID(String noteUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CreditDebitNote note  where note.creditDebitUUID =:noteUUID";
		CreditDebitNote creditDebitNote = (CreditDebitNote) session.createQuery(query).setParameter("noteUUID", noteUUID).uniqueResult();
		return creditDebitNote;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	@SuppressWarnings("unchecked")
	public CreditDebitNote getCreditDebitNoteByIdAndOrglocationId(Long id, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CreditDebitNote note  where note.id =:id and note.orgLocations.id =:orgLocationId";
		CreditDebitNote creditDebitNote = (CreditDebitNote) session.createQuery(query).setParameter("id", id).setParameter("orgLocationId", orgLocationId).uniqueResult();
		return creditDebitNote;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CreditDebitNote getCreditDebitNoteById(Long noteId) {
			Session session = this.sessionFactory.getCurrentSession();
			CreditDebitNote	note = (CreditDebitNote) session.get(CreditDebitNote.class, noteId);
		return note;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Invoice> getAllBillsAndInvoicesBYProjectIdOrgLocationIdInvoiceStatusStartDateAndEndDate(Long projectId,
			Long orgLocationId, String invoiceStatus, Date startDate, Date endDate) {
		Session session = this.sessionFactory.getCurrentSession();
		java.sql.Date sqlStartDate = null;
		if (startDate != null) {
			sqlStartDate = new java.sql.Date(startDate.getTime());
		}
		java.sql.Date sqlEndDate = null;
		if (endDate != null) {
			sqlEndDate = new java.sql.Date(endDate.getTime());
		}
		String query = "from Invoice invoice  where invoice.projectID.id = :projectId and invoice.invoiceStatus =:invoiceStatus and invoice.orgLocation.id = :orgLocationId and date(invoice.createdDate) BETWEEN :startDate and :endDate";
		List<Invoice> viewInvoiceList = session.createQuery(query.toString()).setString("invoiceStatus", invoiceStatus)
				.setLong("projectId", projectId).setLong("orgLocationId", orgLocationId)
				.setParameter("startDate", sqlStartDate).setParameter("endDate", sqlEndDate).list();
		return viewInvoiceList;
	}

	@Override
	@Transactional
	public ResponseList<Invoice> invoiceListByOrgLocationIdAndRelationId(InvoiceDTO invoiceDTO,
			OrgLocations orgLocations, Long orgRelationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();

		String subQuery = "";
		if (invoiceDTO.getInvoiceType().equalsIgnoreCase("payables")) {
			subQuery = " and invoice.invoiceType = 'Payables'";
		} else {
			subQuery = " and invoice.invoiceType = 'Receivable'";
		}

		ResponseList<Invoice> response = new ResponseList<>();
		String query = "from Invoice invoice  left join fetch  invoice.orgRelationID left join fetch invoice.projectMemberId pm left join fetch pm.projects left join fetch pm.employee emp left join fetch emp.user "
				+ "left join fetch invoice.expensesId left join fetch invoice.projectID where  invoice.orgLocation.id =:orgLocationId and invoice.orgRelationID.id = :orgRelationId and invoice.status = '1' "
				+ subQuery
				+ " and (invoice.invoiceStatus = 'SENT' or invoice.invoiceStatus = 'ACCEPTED') and (invoice.paymentStatus = 'Unpaid' or invoice.paymentStatus = 'Partial Paid' or invoice.paymentStatus = 'Overdue') and invoice.dueAmount !=00.00";
		List<Invoice> invoiceList = session.createQuery(query).setParameter("orgRelationId", orgRelationId)
				.setParameter("orgLocationId", orgLocations.getId()).list();
		response.setData(invoiceList);
		return response;
	}

	@Override
	@Transactional
	public void saveInvoiceTimelineHistory(InvoiceTimelineHistory timelineHistory) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (timelineHistory != null) {
				session.saveOrUpdate(timelineHistory);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
	}

	/*
	 * @Override
	 * 
	 * @Transactional public List<InvoiceTimelineHistory>
	 * getInvoiceTimelineHistoryList(Long id, String searchVal, String orderVal) {
	 * Session session = this.sessionFactory.getCurrentSession(); String query =
	 * "from InvoiceTimelineHistory ith where ith.invoice.id = :invoiceId order by ith.createdDate :orderVal"
	 * ; List<InvoiceTimelineHistory> timelineHistories =
	 * session.createQuery(query).setParameter("invoiceId", id).list();
	 * if(!searchVal.isEmpty()) { timelineHistories =
	 * timelineHistories.stream().filter(p ->
	 * p.getInvoiceStatus().equals(searchVal)).collect(Collectors.toList()); }
	 * return timelineHistories; }
	 */

	@Override
	@Transactional
	public List<InvoiceTimelineHistory> getInvoiceTimelineHistoryList(Long id, String searchVal, String orderVal,
			Date[] dateRange, String[] checkedStatus) throws ParseException {
		Session session = this.sessionFactory.getCurrentSession();
		String betweenDateQuery = "";

		String queryOrder = null;
		String searchValQuery = "";
		String query = "from InvoiceTimelineHistory ith where ith.invoice.id = :invoiceId";
		String resultQuery = "";
		String checkedStatusQuery = "";
		
		if (orderVal.equalsIgnoreCase("desc")) {
			queryOrder = " order by ith.createdDate desc";
		} else if (orderVal.equalsIgnoreCase("asc")) {
			queryOrder = " order by ith.createdDate asc";
		}
		if(checkedStatus.length != 0 ) {
			if( checkedStatus[0] != null && !checkedStatus[0].equals("null")) {
				checkedStatusQuery = " and ith.invoiceStatus in ( :checkedStatusVal )";
			}
		}
		if (!searchVal.isEmpty()) {
			searchValQuery = " and (ith.invoiceStatus like :searchVal or ith.rejectReason like :searchVal)";
		}
		
		if (dateRange[0] != null) {
			betweenDateQuery = " and ith.createdDate BETWEEN :fromDate AND :toDate";
		}
		resultQuery = query.concat(searchValQuery).concat(betweenDateQuery).concat(checkedStatusQuery).concat(checkedStatusQuery)
				.concat(queryOrder);
		Query hqlQuery = session.createQuery(resultQuery).setParameter("invoiceId", id);
		if (!searchVal.isEmpty()) {
			hqlQuery.setString("searchVal", "%"+searchVal+"%");
		}
		if(checkedStatus.length != 0 ) {
			if( checkedStatus[0] != null && !checkedStatus[0].equals("null")) {
				hqlQuery.setParameterList("checkedStatusVal", checkedStatus);
			}
		}
		if (dateRange[0] != null) {
			hqlQuery.setParameter("fromDate", dateRange[0]).setParameter("toDate", dateRange[1]);
		}
		List<InvoiceTimelineHistory> timelineHistories = hqlQuery.list();
		return timelineHistories;
	}

	@Override
	@Transactional
	public Long checkInvoiceOrBillIdNumberInDatebase(Long orgLocationId, Integer generatedIdcount, String invoiceType) {
		Session session = this.sessionFactory.getCurrentSession();
		//String query = "SELECT MAX(CAST(SUBSTR(TRIM(EXPENSE_ID),'"+generatedIdcount+"') AS UNSIGNED)) FROM "+schema+".EXPENSES WHERE LOCATION_ID = '"+ orgLocationId + "'"; /*OLD CODE*/
		String query = "SELECT MAX(CAST(SUBSTR(TRIM(INVOICE_ID),'"+generatedIdcount+"') AS UNSIGNED)) FROM "+schema+".INVOICE WHERE ORG_LOCATION_ID = '"+ orgLocationId + "' AND INVOICE_TYPE = '"+invoiceType+"'";
		List<BigInteger> maxCountList = session.createSQLQuery(query).list();
		Long count = 0L;
		if (maxCountList != null && !maxCountList.isEmpty() && !maxCountList.contains(null)) {
			count = (Long) maxCountList.iterator().next().longValue();
		}
		return count;	
	}
	
	@Override
	@Transactional
	public String checkCreditDebitIdNumberInDatebase(Long orgLocationId,String type) {
		Session session = this.sessionFactory.getCurrentSession();

			String query = "SELECT GENERATED_ID FROM "+schema+".CREDIT_DEBIT_NOTE WHERE ORG_LOCATIONS_ID = '"+ orgLocationId + "' AND  ID = (SELECT MAX(ID) FROM "+schema+".CREDIT_DEBIT_NOTE WHERE ORG_LOCATIONS_ID = '"+ orgLocationId + "' AND GENERATED_ID LIKE '%-"+type+"-%')" ;
			String generatedId = (String) session.createSQLQuery(query).uniqueResult();
		return generatedId;	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CreditDebitLineItems getCreditDebitLineItemById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from CreditDebitLineItems lineItems where lineItems.id =:id";
		CreditDebitLineItems noteLineItem = (CreditDebitLineItems) session.createQuery(query).setParameter("id", id).uniqueResult();
		return noteLineItem;
	}

	@Override
	@Transactional
	public void deleteCreditDebitLineItem(CreditDebitLineItems creditDebitLineItems) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.delete(creditDebitLineItems);
			session.flush();
		} catch (HibernateException e) {
			logger.error("SQL Exception occured while doing delete -->" + e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public CustomInvoiceTemplate getCustomInvoiceTemplateById(long customInvoiceTemplateId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			return session.get(CustomInvoiceTemplate.class, customInvoiceTemplateId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public long saveCustomInvoiceTemplate(CustomInvoiceTemplate customInvoiceTemplate) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(customInvoiceTemplate);
			return customInvoiceTemplate.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	@Transactional
	public ResponseList<CustomInvoiceTemplate> getCustomInvoiceTemplateByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId, SearchDTO searchDTO) {
		ResponseList<CustomInvoiceTemplate> responseList = new ResponseList<>();
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CustomInvoiceTemplate invoiceTemplate where invoiceTemplate.orgRelations.id= :orgRelationId and invoiceTemplate.orgLocations.id=:orgLocationId";
			Query queryObj = session.createQuery(query);
			queryObj.setLong("orgRelationId", orgRelationId);
			queryObj.setLong("orgLocationId", orgLocationId);
			List<CustomInvoiceTemplate> customInvoiceTemplateList = queryObj.list();
			
			
			StringBuilder query1 = new StringBuilder("");
			if(searchDTO != null) {
				if(searchDTO.getSearchValue() != null && !"".equalsIgnoreCase(searchDTO.getSearchValue())) {
				 query1.append("and (invoiceTemplate.name like '%" + searchDTO.getSearchValue() + "%' ")
					   .append("OR invoiceTemplate.venue like '%" + searchDTO.getSearchValue() + "%' )");
				}
				query1.append(" order by " + (searchDTO.getSortColumn() != null && !searchDTO.getSortColumn().isEmpty() ? searchDTO.getSortColumn() : "id") + " " + (searchDTO.getSortOrder() != null && !searchDTO.getSortOrder().isEmpty() ? searchDTO.getSortOrder() : "desc"));
			}
			
			
			List<Long> countlist = null;
			String countQuery = "select count(*) from CustomInvoiceTemplate invoiceTemplate where invoiceTemplate.orgRelations.id= :orgRelationId and invoiceTemplate.orgLocations.id=:orgLocationId";
			
			Query countQueryObj = session.createQuery(countQuery + query1);
			countQueryObj.setLong("orgRelationId", orgRelationId);
			countQueryObj.setLong("orgLocationId", orgLocationId);
			countlist = countQueryObj.list();
			
			int totalCount = 0;
//			if (jobFairDTO != null) {
				if(countlist != null && !countlist.isEmpty()) {
					totalCount = countlist.iterator().next().intValue();
				}
//			}
			responseList.setRecordsFiltered(totalCount);
			responseList.setRecordsTotal(totalCount);
			
			if (customInvoiceTemplateList != null && !customInvoiceTemplateList.isEmpty())
				responseList.setData(customInvoiceTemplateList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseList;
	}

	@Override
	@Transactional
	public CustomInvoiceTemplate getCustomInvoiceTemplateListByUUIDAndOrgRelationIdAndOrgLocationId(String uuid, Long orgRelationId, Long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CustomInvoiceTemplate invoiceTemplate where invoiceTemplate.uuid=:uuid and invoiceTemplate.orgRelations.id=:orgRelationId and invoiceTemplate.orgLocations.id=:orgLocationId";
			Query queryObj = session.createQuery(query);
			queryObj.setString("uuid", uuid);
			queryObj.setLong("orgRelationId", orgRelationId);
			queryObj.setLong("orgLocationId", orgLocationId);
			List<CustomInvoiceTemplate> customInvoiceTemplateList = queryObj.list();
			if (customInvoiceTemplateList != null && !customInvoiceTemplateList.isEmpty()) {
				return customInvoiceTemplateList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public CustomInvoiceTemplate getCustomInvoiceTemplateByNameAndOrgRelationIdAndOrgLocationId(String name,
			Long orgRelationId, Long orgLocationId) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query = "from CustomInvoiceTemplate invoiceTemplate where invoiceTemplate.name=:name and invoiceTemplate.orgRelations.id=:orgRelationId and invoiceTemplate.orgLocations.id=:orgLocationId";
			Query queryObj = session.createQuery(query);
			queryObj.setString("name", name);
			queryObj.setLong("orgRelationId", orgRelationId);
			queryObj.setLong("orgLocationId", orgLocationId);
			List<CustomInvoiceTemplate> customInvoiceTemplateList = queryObj.list();
			if (customInvoiceTemplateList != null && !customInvoiceTemplateList.isEmpty()) {
				return customInvoiceTemplateList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public boolean deleteCustomInvoiceTemplate(CustomInvoiceTemplate customInvoiceTemplate) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.delete(customInvoiceTemplate);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
}
