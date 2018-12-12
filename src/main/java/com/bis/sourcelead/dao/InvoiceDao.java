package com.bis.sourcelead.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.CreditDebitNoteDTO;
import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SearchDTO;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.CreditDebitLineItems;
import com.bis.sourcelead.entity.CreditDebitNote;
import com.bis.sourcelead.entity.CustomInvoiceTemplate;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.InvoiceTimelineHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;

public interface InvoiceDao{

	Invoice saveInvoce(Invoice invoice)throws Exception;
	
	CreditDebitNote saveCreditDebitNote(CreditDebitNote creditDebitNote)throws Exception;
	
	ResponseList<Invoice> invoiceListByOrgLocationId(OrgLocations orgLocations,Date startDateUTC,Date endDateUTC,InvoiceDTO invoiceDTO) throws Exception;
	
	ResponseList<CreditDebitNote> creditDebtListByOrgLocationId(OrgLocations orgLocations,Date startDateUTC,Date endDateUTC,CreditDebitNoteDTO creditDebitNoteDTO) throws Exception;

	ResponseList<Invoice> invoiceListByOrgLocationIdAndRelationId(InvoiceDTO invoiceDTO,OrgLocations orgLocations, Long orgRelationId) throws Exception;
	
	Invoice getInvoicetById(Long invoiceId) throws Exception;
	
	List<Invoice> getInvoiceListByIdsAndLocationId(List<Long> invoiceIds,Long locationId)throws Exception;
	
	public Boolean getInvoiceByInvoiceId(String invoiceId, Long orgLocationsId,  String invoiceType) throws Exception;

	Invoice getInvoicetByParentInvoiceId(Long parentInvoiceId) throws Exception;
	
	List<Contracts> getSowcontractListByAssociateId(Long candidateId, Long orgLocationId, Long orgRelationId,String contractType)throws Exception;
	
	Invoice getInvoicetByInvoiceUUID(String invoiceUUID) throws Exception;
	
	Invoice updateInvoice(Invoice invoice) throws Exception;

	List<Invoice> getViewInvoiceListByInvoiceIdAndOrglocationId(Long invoiceId, Long orgLocationId) throws Exception;

	Invoice getInvoicetBySelfJoinInvoiceId(Long parentInvoiceId) throws Exception;

	List<Invoice> getInvoiceByOrgLocationId(Long orgLocationId, Date startDateUTC, Date endDateUTC, String invoiceTYpe)throws Exception;

	List<Invoice> getClientBasedInvoiceLists(Long orgRelationId,Long orgLocationId, String invoiceType);
	
	List<Invoice> getInvoiceByOrgRelationId(Long orgRelationId, Long clientLocationId,Date startDateUTC, Date endDateUTC,String invoiceStatus, String paymentStatus, String invoiceType);

	List<Invoice> getInvoiceByOrgLocationIdNetIncome(Long orgLocationId, Date startDateUTC, Date endDateUTC)throws Exception;

	List<Invoice> getDueDateCompletedInvoicesList() throws Exception;

	List<InvoicePayment> getPaymentHistroyByInvoiceId(Long invoiceId)throws Exception;

	Invoice getInvoicetByParentId(Long invoiceId)throws Exception;

	Invoice getParentInvoiceByInvoiceId(Long invoiceId)throws Exception;
	
	CreditDebitNote getParentCreditDebitNoteById(Long noteId)throws Exception;
	
	 CreditDebitNote getCreditDebitNoteById(Long noteId)throws Exception;
	 
	 CreditDebitNote getCreditDebitNoteByUUID(String noteUUID)throws Exception;
	 
	 List<CreditDebitLineItems> getCreditDebitLineItemsListByNoteIdAndLocationId(Long noteId,Long locationId)throws Exception;
	 
	 CreditDebitNote getCreditDebitNoteByIdAndOrglocationId(Long id, Long orgLocationId) throws Exception;

	List<Invoice> getAllBillsAndInvoicesBYProjectIdOrgLocationIdInvoiceStatusStartDateAndEndDate(Long projectId, Long orgLocationId, String invoiceStatus, Date startDate, Date endDate);

	List<Invoice> getInvoiceListUsingMultipleStringIdsAndLocationId(String ids, Long orgLocationId) throws Exception;
	
	List<Invoice> getInvoiceListUsingMultipleIds(String ids) throws Exception;
	
	public List<Invoice> getInvoiceListUsingMultipleIdsForChild(String ids) throws Exception;
	
	void saveInvoiceTimelineHistory(InvoiceTimelineHistory timelineHistory);

	List<InvoiceTimelineHistory> getInvoiceTimelineHistoryList(Long id, String searchVal, String orderVal, Date[] datesRange, String[] checkedStatus) throws ParseException;

	public Long checkInvoiceOrBillIdNumberInDatebase(Long orgLocationId,Integer generatedIdcount,String invoiceType);
	
	public String checkCreditDebitIdNumberInDatebase(Long orgLocationId,String type);
	
	CreditDebitLineItems getCreditDebitLineItemById(Long id);
	
	void deleteCreditDebitLineItem(CreditDebitLineItems creditDebitLineItems);

	public CustomInvoiceTemplate getCustomInvoiceTemplateById(long customInvoiceTemplateId);

	public long saveCustomInvoiceTemplate(CustomInvoiceTemplate customInvoiceTemplate);

	public ResponseList<CustomInvoiceTemplate> getCustomInvoiceTemplateByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId, SearchDTO searchDTO);

	public CustomInvoiceTemplate getCustomInvoiceTemplateListByUUIDAndOrgRelationIdAndOrgLocationId(String uuid, Long orgRelationId, Long orgLocationId);
	
	public CustomInvoiceTemplate getCustomInvoiceTemplateByNameAndOrgRelationIdAndOrgLocationId(String name, Long orgRelationId, Long orgLocationId);

	public boolean deleteCustomInvoiceTemplate(CustomInvoiceTemplate customInvoiceTemplate);
}
